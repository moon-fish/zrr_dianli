package org.jeecg.modules.dw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.jeecg.modules.dw.entity.ElectricityFilerB;
import org.jeecg.modules.dw.entity.MomthElec;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IElectricityFilerBService;
import org.jeecg.modules.dw.service.IMomthElecService;
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
 * @Description: 电量申报
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="电量申报")
@RestController
@RequestMapping("/electricityfilerb/electricityFilerB")
@Slf4j
public class ElectricityFilerBController extends JeecgController<ElectricityFilerB, IElectricityFilerBService> {
	@Autowired
	private IElectricityFilerBService electricityFilerBService;
	@Autowired
	private IMomthElecService momthElecService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private IUserInfoBService userInfoBService;
	@Autowired
	private Auth auth;
	
	/**
	 * 分页列表查询
	 *
	 * @param electricityFilerB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "电量申报-分页列表查询")
	@ApiOperation(value="电量申报-分页列表查询", notes="电量申报-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ElectricityFilerB electricityFilerB,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ElectricityFilerB> queryWrapper = QueryGenerator.initQueryWrapper(electricityFilerB, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		//权限设置 
		//根据登录人的信息查出区域内的公司名称，再根据公司名称过滤数据
		//获取登录人信息
//		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//		//登录人id
//		String id = sysUser.getId();
//		//登录人角色id
//		String roleId = sysUserRoleService.queryByUserId(id);
//		//登录人   角色信息
//		SysRole sysrole = sysRoleService.getById(roleId);
//		//登录人 角色code
//		String roleCode = sysrole.getRoleCode();
//		QueryWrapper<UserInfoB> queryWrapperUserInfo = new QueryWrapper<UserInfoB>();
//		//晋城、长治、阳泉区域   根据角色编码设置其区域 ---写死的，可能得改
//		if(roleCode.equals("areaManagerMin")) {
//			queryWrapperUserInfo.like("address_info", "晋城").or().like("address_info","长治").or().like("address_info","阳泉");
//		}else if(roleCode.equals("areaManageMi")){
//			//运城、忻州、吕梁、晋中
//			queryWrapperUserInfo.like("address_info", "运城").or().like("address_info","忻州").or().like("address_info","吕梁").or().like("address_info","晋中");
//		}
//		List<UserInfoB> list = userInfoBService.list(queryWrapperUserInfo);
//		List<String> companys = new ArrayList<String>();
//		for(UserInfoB userInfoB : list) {
//			companys.add(userInfoB.getComAName());
//		}
		List<String> companys = auth.queryCompanys();
		queryWrapper.in("filer_company_name", companys);
		
		Page<ElectricityFilerB> page = new Page<ElectricityFilerB>(pageNo, pageSize);
		IPage<ElectricityFilerB> pageList = electricityFilerBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<ElectricityFilerB>> waterqueryall(String companyName) {
		Result<List<ElectricityFilerB>> result = new Result<>();
		QueryWrapper<ElectricityFilerB> queryWrapper = new QueryWrapper<ElectricityFilerB>();
		queryWrapper.orderByDesc("create_time");
		if(!oConvertUtils.isEmpty(companyName)) {
			queryWrapper.eq("filer_company_name", companyName);
		}
		List<ElectricityFilerB> list = electricityFilerBService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 *   添加
	 *   用户 电量申报
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "电量申报-添加")
	@ApiOperation(value="电量申报-添加", notes="电量申报-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ElectricityFilerB electricityFilerB,MomthElec momthElec) {
		System.out.println("==========");
		System.out.println(electricityFilerB);
		electricityFilerBService.save(electricityFilerB);
//		String companyName = electricityFilerB.getFilerCompanyName();
//		String market = electricityFilerB.getMarketQuotation();
//		String month = electricityFilerB.getFilerMonth();
//		//获取改公司最新的数据
//		Result<List<MomthElec>> result = new Result<>();
//		QueryWrapper<MomthElec> queryWrapper = new QueryWrapper<MomthElec>();
//		queryWrapper.orderByDesc("create_time");
//		List<MomthElec> list = momthElecService.list(queryWrapper);
//		for(int i=0;i<list.size();i++) {
//			if(companyName.equals(list.get(i).getUsername())) {
//				momthElec = list.get(i);
//			}
//			break;
//		}
//		momthElec.setId("");
//		momthElec.setCreateBy("");
//		momthElec.setCreateTime(null);
//		momthElec.setUsername(companyName);
//		momthElec.setStat("2");
//		if(month.equals("yi")) {
//			momthElec.setYi(market);
//		}else if(month.equals("er")) {
//			momthElec.setEr(market);
//		}else if(month.equals("san")) {
//			momthElec.setSan(market);
//		}else if(month.equals("si")) {
//			momthElec.setSi(market);
//		}else if(month.equals("wu")) {
//			momthElec.setWu(market);
//		}else if(month.equals("liu")) {
//			momthElec.setLiu(market);
//		}else if(month.equals("qi")) {
//			momthElec.setQi(market);
//		}else if(month.equals("ba")) {
//			momthElec.setBa(market);
//		}else if(month.equals("jiu")) {
//			momthElec.setJiu(market);
//		}else if(month.equals("shi")) {
//			momthElec.setShi(market);
//		}else if(month.equals("shiyi")) {
//			momthElec.setShiyi(market);
//		}else if(month.equals("shier")) {
//			momthElec.setShier(market);
//		}
//		momthElecService.save(momthElec);
		return Result.ok("添加成功！");
	}
	
	/**
	 *   添加
	 *   售电公司 替用户申报电量
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "售电公司电量申报-添加")
	@ApiOperation(value="售电公司电量申报-添加", notes="售电公司电量申报-添加")
	@PostMapping(value = "/elecAdd")
	public Result<?> elecAdd(@RequestBody ElectricityFilerB electricityFilerB) {
		electricityFilerBService.save(electricityFilerB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "电量申报-编辑")
	@ApiOperation(value="电量申报-编辑", notes="电量申报-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ElectricityFilerB electricityFilerB,String filerStatus) {
		electricityFilerBService.updateById(electricityFilerB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *  批量修改
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电量申报-批量修改")
	@ApiOperation(value="电量申报-批量修改", notes="电量申报-批量修改")
	@PutMapping(value = "/updateBatch")
	public Result<?> updateBatch(@RequestBody List<ElectricityFilerB> entityList) {
		System.out.println("==============");
		System.out.println(entityList);
		this.electricityFilerBService.updateBatchById(entityList);
		return Result.ok("批量修改成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电量申报-通过id删除")
	@ApiOperation(value="电量申报-通过id删除", notes="电量申报-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		electricityFilerBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电量申报-批量删除")
	@ApiOperation(value="电量申报-批量删除", notes="电量申报-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.electricityFilerBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电量申报-通过id查询")
	@ApiOperation(value="电量申报-通过id查询", notes="电量申报-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ElectricityFilerB electricityFilerB = electricityFilerBService.getById(id);
		if(electricityFilerB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(electricityFilerB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param electricityFilerB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElectricityFilerB electricityFilerB) {
        return super.exportXls(request, electricityFilerB, ElectricityFilerB.class, "电量申报");
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
        return super.importExcel(request, response, ElectricityFilerB.class);
    }

}
