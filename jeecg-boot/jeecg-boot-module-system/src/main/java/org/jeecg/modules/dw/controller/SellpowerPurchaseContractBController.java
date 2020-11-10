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
import org.jeecg.modules.dw.entity.SellpowerPurchaseContractB;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.ISellpowerPurchaseContractBService;

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
 * @Description: 售电公司购电合同
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="售电公司购电合同")
@RestController
@RequestMapping("/sellpowerpurchasecontractb/sellpowerPurchaseContractB")
@Slf4j
public class SellpowerPurchaseContractBController extends JeecgController<SellpowerPurchaseContractB, ISellpowerPurchaseContractBService> {
	@Autowired
	private ISellpowerPurchaseContractBService sellpowerPurchaseContractBService;
	
	/**
	 * 分页列表查询
	 *
	 * @param sellpowerPurchaseContractB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-分页列表查询")
	@ApiOperation(value="售电公司购电合同-分页列表查询", notes="售电公司购电合同-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SellpowerPurchaseContractB sellpowerPurchaseContractB,String cusname,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SellpowerPurchaseContractB> queryWrapper = QueryGenerator.initQueryWrapper(sellpowerPurchaseContractB, req.getParameterMap());
		if(!oConvertUtils.isEmpty(cusname)) {
			queryWrapper.eq("user_name", cusname);
		}
		Page<SellpowerPurchaseContractB> page = new Page<SellpowerPurchaseContractB>(pageNo, pageSize);
		IPage<SellpowerPurchaseContractB> pageList = sellpowerPurchaseContractBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<SellpowerPurchaseContractB>> queryall(String cusname,int year) {
		Result<List<SellpowerPurchaseContractB>> result = new Result<>();
		QueryWrapper<SellpowerPurchaseContractB> queryWrapper = new QueryWrapper<SellpowerPurchaseContractB>();
		if(!oConvertUtils.isEmpty(cusname)) {
			queryWrapper.eq("user_name", cusname);
		}
		if(!oConvertUtils.isEmpty(year)) {
			queryWrapper.gt("begin_date", year+"-01-01 00:00:00");
			queryWrapper.lt("end_date", year+1+"-01-01 00:00:00");
		}
		List<SellpowerPurchaseContractB> list = sellpowerPurchaseContractBService.list(queryWrapper);
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
	 * @param sellpowerPurchaseContractB
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-添加")
	@ApiOperation(value="售电公司购电合同-添加", notes="售电公司购电合同-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SellpowerPurchaseContractB sellpowerPurchaseContractB) {
		sellpowerPurchaseContractBService.save(sellpowerPurchaseContractB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param sellpowerPurchaseContractB
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-编辑")
	@ApiOperation(value="售电公司购电合同-编辑", notes="售电公司购电合同-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SellpowerPurchaseContractB sellpowerPurchaseContractB) {
		sellpowerPurchaseContractBService.updateById(sellpowerPurchaseContractB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-通过id删除")
	@ApiOperation(value="售电公司购电合同-通过id删除", notes="售电公司购电合同-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sellpowerPurchaseContractBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-批量删除")
	@ApiOperation(value="售电公司购电合同-批量删除", notes="售电公司购电合同-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sellpowerPurchaseContractBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "售电公司购电合同-通过id查询")
	@ApiOperation(value="售电公司购电合同-通过id查询", notes="售电公司购电合同-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SellpowerPurchaseContractB sellpowerPurchaseContractB = sellpowerPurchaseContractBService.getById(id);
		if(sellpowerPurchaseContractB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(sellpowerPurchaseContractB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param sellpowerPurchaseContractB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SellpowerPurchaseContractB sellpowerPurchaseContractB) {
        return super.exportXls(request, sellpowerPurchaseContractB, SellpowerPurchaseContractB.class, "售电公司购电合同");
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
        return super.importExcel(request, response, SellpowerPurchaseContractB.class);
    }

}
