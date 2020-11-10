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
import org.jeecg.modules.dw.entity.MyTrade;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IMyTradeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.lang.Console;
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
 * @Description: 我的交易
 * @Author: jeecg-boot
 * @Date:   2020-05-06
 * @Version: V1.0
 */
@Api(tags="我的交易")
@RestController
@RequestMapping("/mytrade/myTrade")
@Slf4j
public class MyTradeController extends JeecgController<MyTrade, IMyTradeService> {
	@Autowired
	private IMyTradeService myTradeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param myTrade
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "我的交易-分页列表查询")
	@ApiOperation(value="我的交易-分页列表查询", notes="我的交易-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MyTrade myTrade,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="-1") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MyTrade> queryWrapper = QueryGenerator.initQueryWrapper(myTrade, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		Page<MyTrade> page = new Page<MyTrade>(pageNo, pageSize);
		IPage<MyTrade> pageList = myTradeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 不分页列表查询
	 *
	 * @param myTrade
	 * @param req
	 * @return
	 */
	@AutoLog(value = "我的交易-不分页列表查询")
	@ApiOperation(value="我的交易-不分页列表查询", notes="我的交易-不分页列表查询")
	@GetMapping(value = "/listTotal")
	public Result<List<MyTrade>> listTotal(MyTrade myTrade,HttpServletRequest req,String tradeDate) {
		Result<List<MyTrade>> result = new Result<>();
		QueryWrapper<MyTrade> queryWrapper = QueryGenerator.initQueryWrapper(myTrade, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		if(oConvertUtils.isNotEmpty(tradeDate)) {
			queryWrapper.le("trade_begin", tradeDate);
			queryWrapper.ge("trade_end", tradeDate);
		}
		List<MyTrade> list = myTradeService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<MyTrade>> waterqueryall(String elecname,	int year,MyTrade myTrade,HttpServletRequest req) {
		Result<List<MyTrade>> result = new Result<>();
		QueryWrapper<MyTrade> queryWrapper = new QueryWrapper<MyTrade>();
		if(!oConvertUtils.isEmpty(elecname)) {
			queryWrapper.eq("customer_name", elecname);
		}
		if(!oConvertUtils.isEmpty(year)) {
			queryWrapper.gt("trade_begin", year+"-01-01 00:00:00");
			queryWrapper.lt("trade_end", year+1+"-01-01 00:00:00");
		}
		List<MyTrade> list = myTradeService.list(queryWrapper);
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
	 * @param myTrade
	 * @return
	 */
	@AutoLog(value = "我的交易-添加")
	@ApiOperation(value="我的交易-添加", notes="我的交易-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MyTrade myTrade) {
		System.out.println("myTrade"+myTrade);
		myTradeService.save(myTrade);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param myTrade
	 * @return
	 */
	@AutoLog(value = "我的交易-编辑")
	@ApiOperation(value="我的交易-编辑", notes="我的交易-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MyTrade myTrade) {
		myTradeService.updateById(myTrade);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "我的交易-通过id删除")
	@ApiOperation(value="我的交易-通过id删除", notes="我的交易-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		myTradeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "我的交易-批量删除")
	@ApiOperation(value="我的交易-批量删除", notes="我的交易-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.myTradeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "我的交易-通过id查询")
	@ApiOperation(value="我的交易-通过id查询", notes="我的交易-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MyTrade myTrade = myTradeService.getById(id);
		if(myTrade==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(myTrade);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param myTrade
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MyTrade myTrade) {
        return super.exportXls(request, myTrade, MyTrade.class, "我的交易");
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
        return super.importExcel(request, response, MyTrade.class);
    }

}
