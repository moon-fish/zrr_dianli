package org.jeecg.modules.dw.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IUserInfoBService;
import org.jeecg.modules.dw.utils.Auth;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserRoleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 电力用户信息
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="电力用户信息")
@RestController
@RequestMapping("/userinfob/userInfoB")
@Slf4j
public class UserInfoBController extends JeecgController<UserInfoB, IUserInfoBService> {
	@Autowired
	private IUserInfoBService userInfoBService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private Auth auth;
	
	/**
	 * 分页列表查询
	 *
	 * @param userInfoB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "电力用户信息-分页列表查询")
	@ApiOperation(value="电力用户信息-分页列表查询", notes="电力用户信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(UserInfoB userInfoB,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,String username,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserInfoB> queryWrapper = new QueryWrapper<UserInfoB>();
		//选择按钮  选择电力用户时用到
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("com_a_name", username);
		}
		String roleCode = auth.queryRoleCode();
		//晋城、长治、阳泉区域   根据角色编码设置其区域 ---写死的，可能得改
		if(roleCode.equals("areaManagerMin")) {
			queryWrapper.like("address_info", "晋城").or().like("address_info","长治").or().like("address_info","阳泉");
		}else if(roleCode.equals("areaManageMi")){
			//运城、忻州、吕梁、晋中
			queryWrapper.like("address_info", "运城").or().like("address_info","忻州").or().like("address_info","吕梁").or().like("address_info","晋中");
		}
		//只查询总公司名称，过滤分公司  用户信息列表
		queryWrapper.isNull("com_t_name");
		Page<UserInfoB> page = new Page<UserInfoB>(pageNo, pageSize);
		IPage<UserInfoB> pageList = userInfoBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 分页列表查询
	 *
	 * @param userInfoB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "电力用户信息-分页列表查询")
	@ApiOperation(value="电力用户信息-分页列表查询", notes="电力用户信息-分页列表查询")
	@GetMapping(value = "/listZi")
	public Result<?> listZi(UserInfoB userInfoB,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,String username,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserInfoB> queryWrapper = new QueryWrapper<UserInfoB>();
		//选择按钮  选择电力用户时用到
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("com_t_name", username);
		}
		Page<UserInfoB> page = new Page<UserInfoB>(pageNo, pageSize);
		IPage<UserInfoB> pageList = userInfoBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<UserInfoB>> queryall(String company,String id) {
		Result<List<UserInfoB>> result = new Result<>();
		QueryWrapper<UserInfoB> queryWrapper = new QueryWrapper<UserInfoB>();
		//微信小程序注册页面，对输入的公司名称进行查询
		if(!oConvertUtils.isEmpty(company)) {
			queryWrapper.eq("com_a_name", company).or().eq("com_s_name", company);
		}
		//权限设置
		if(!oConvertUtils.isEmpty(id)) {
			//登录人角色id
			String roleId = sysUserRoleService.queryByUserId(id);
			//登录人   角色信息
			SysRole sysrole = sysRoleService.getById(roleId);
			//登录人 角色code
			String roleCode = sysrole.getRoleCode();
			//晋城、长治、阳泉区域   根据角色编码设置其区域 ---写死的，可能得改
			if(roleCode.equals("areaManagerMin")) {
				queryWrapper.like("address_info", "晋城").or().like("address_info","长治").or().like("address_info","阳泉");
			}else if(roleCode.equals("areaManageMi")){
				//运城、忻州、吕梁、晋中
				queryWrapper.like("address_info", "运城").or().like("address_info","忻州").or().like("address_info","吕梁").or().like("address_info","晋中");
			}
		}
		List<UserInfoB> list = userInfoBService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 * 根据登录的用户id判断其角色，再根据角色设置其权限  --权限分配
	 * param
	 * return role_code
	 */
//	public String queryRoleCode() {
//		//获取登录人信息
//		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//		//登录人id
//		String id = sysUser.getId();
//		//登录人角色id
//		String roleId = sysUserRoleService.queryByUserId(id);
//		//登录人   角色信息
//		SysRole sysrole = sysRoleService.getById(roleId);
//		//登录人 角色code
//		String roleCode = sysrole.getRoleCode();
//		return roleCode;
//	}
	
	/**
	 *   添加
	 *
	 * @param userInfoB
	 * @return
	 */
	@AutoLog(value = "电力用户信息-添加")
	@ApiOperation(value="电力用户信息-添加", notes="电力用户信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserInfoB userInfoB) {
		userInfoBService.save(userInfoB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userInfoB
	 * @return
	 */
	@AutoLog(value = "电力用户信息-编辑")
	@ApiOperation(value="电力用户信息-编辑", notes="电力用户信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserInfoB userInfoB) {
		userInfoBService.updateById(userInfoB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电力用户信息-通过id删除")
	@ApiOperation(value="电力用户信息-通过id删除", notes="电力用户信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userInfoBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电力用户信息-批量删除")
	@ApiOperation(value="电力用户信息-批量删除", notes="电力用户信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userInfoBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电力用户信息-通过id查询")
	@ApiOperation(value="电力用户信息-通过id查询", notes="电力用户信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		UserInfoB userInfoB = userInfoBService.getById(id);
		if(userInfoB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(userInfoB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userInfoB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserInfoB userInfoB) {
        return super.exportXls(request, userInfoB, UserInfoB.class, "电力用户信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserInfoB.class);
    }

}
