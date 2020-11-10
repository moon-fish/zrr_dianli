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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.entity.UserPurchaseContractB;
import org.jeecg.modules.dw.service.IUserPurchaseContractBService;

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
 * @Description: 电力用户购电合同
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="电力用户购电合同")
@RestController
@RequestMapping("/userpurchasecontractb/userPurchaseContractB")
@Slf4j
public class UserPurchaseContractBController extends JeecgController<UserPurchaseContractB, IUserPurchaseContractBService> {
	@Autowired
	private IUserPurchaseContractBService userPurchaseContractBService;
	
	/**
	 * 分页列表查询
	 *
	 * @param userPurchaseContractB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-分页列表查询")
	@ApiOperation(value="电力用户购电合同-分页列表查询", notes="电力用户购电合同-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(UserPurchaseContractB userPurchaseContractB,String cusname,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserPurchaseContractB> queryWrapper = QueryGenerator.initQueryWrapper(userPurchaseContractB, req.getParameterMap());
		if(!oConvertUtils.isEmpty(cusname)) {
			queryWrapper.eq("user_name", cusname);
		}
		Page<UserPurchaseContractB> page = new Page<UserPurchaseContractB>(pageNo, pageSize);
		IPage<UserPurchaseContractB> pageList = userPurchaseContractBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<UserPurchaseContractB>> queryall(String cusname,String contractName) {
		Result<List<UserPurchaseContractB>> result = new Result<>();
		QueryWrapper<UserPurchaseContractB> queryWrapper = new QueryWrapper<UserPurchaseContractB>();
		if(!oConvertUtils.isEmpty(cusname)) {
			queryWrapper.eq("user_name", cusname);
		}
		if(!oConvertUtils.isEmpty(contractName)) {
			queryWrapper.eq("contract_name", contractName);
		}
		List<UserPurchaseContractB> list = userPurchaseContractBService.list(queryWrapper);
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
	 *
	 * @param userPurchaseContractB
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-添加")
	@ApiOperation(value="电力用户购电合同-添加", notes="电力用户购电合同-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserPurchaseContractB userPurchaseContractB) {
		userPurchaseContractBService.save(userPurchaseContractB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userPurchaseContractB
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-编辑")
	@ApiOperation(value="电力用户购电合同-编辑", notes="电力用户购电合同-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserPurchaseContractB userPurchaseContractB) {
		userPurchaseContractBService.updateById(userPurchaseContractB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-通过id删除")
	@ApiOperation(value="电力用户购电合同-通过id删除", notes="电力用户购电合同-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userPurchaseContractBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-批量删除")
	@ApiOperation(value="电力用户购电合同-批量删除", notes="电力用户购电合同-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userPurchaseContractBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电力用户购电合同-通过id查询")
	@ApiOperation(value="电力用户购电合同-通过id查询", notes="电力用户购电合同-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		UserPurchaseContractB userPurchaseContractB = userPurchaseContractBService.getById(id);
		if(userPurchaseContractB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(userPurchaseContractB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userPurchaseContractB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserPurchaseContractB userPurchaseContractB) {
        return super.exportXls(request, userPurchaseContractB, UserPurchaseContractB.class, "电力用户购电合同");
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
        return super.importExcel(request, response, UserPurchaseContractB.class);
    }

}
