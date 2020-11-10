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
import org.jeecg.modules.dw.entity.BalanceCompany;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IBalanceCompanyService;

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
 * @Description: 售电公司结算
 * @Author: jeecg-boot
 * @Date:   2020-06-11
 * @Version: V1.0
 */
@Api(tags="售电公司结算")
@RestController
@RequestMapping("/balancecompany/balanceCompany")
@Slf4j
public class BalanceCompanyController extends JeecgController<BalanceCompany, IBalanceCompanyService> {
	@Autowired
	private IBalanceCompanyService balanceCompanyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param balanceCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "售电公司结算-分页列表查询")
	@ApiOperation(value="售电公司结算-分页列表查询", notes="售电公司结算-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BalanceCompany balanceCompany,String username,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BalanceCompany> queryWrapper = QueryGenerator.initQueryWrapper(balanceCompany, req.getParameterMap());
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("user_name", username);
		}
		Page<BalanceCompany> page = new Page<BalanceCompany>(pageNo, pageSize);
		IPage<BalanceCompany> pageList = balanceCompanyService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<BalanceCompany>> queryall() {
		Result<List<BalanceCompany>> result = new Result<>();
		QueryWrapper<BalanceCompany> queryWrapper = new QueryWrapper<BalanceCompany>();
		List<BalanceCompany> list = balanceCompanyService.list(queryWrapper);
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
	 * @param balanceCompany
	 * @return
	 */
	@AutoLog(value = "售电公司结算-添加")
	@ApiOperation(value="售电公司结算-添加", notes="售电公司结算-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BalanceCompany balanceCompany) {
		balanceCompanyService.save(balanceCompany);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param balanceCompany
	 * @return
	 */
	@AutoLog(value = "售电公司结算-编辑")
	@ApiOperation(value="售电公司结算-编辑", notes="售电公司结算-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BalanceCompany balanceCompany) {
		balanceCompanyService.updateById(balanceCompany);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "售电公司结算-通过id删除")
	@ApiOperation(value="售电公司结算-通过id删除", notes="售电公司结算-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		balanceCompanyService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "售电公司结算-批量删除")
	@ApiOperation(value="售电公司结算-批量删除", notes="售电公司结算-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.balanceCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "售电公司结算-通过id查询")
	@ApiOperation(value="售电公司结算-通过id查询", notes="售电公司结算-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BalanceCompany balanceCompany = balanceCompanyService.getById(id);
		if(balanceCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(balanceCompany);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param balanceCompany
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BalanceCompany balanceCompany) {
        return super.exportXls(request, balanceCompany, BalanceCompany.class, "售电公司结算");
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
        return super.importExcel(request, response, BalanceCompany.class);
    }

}
