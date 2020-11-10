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
import org.jeecg.modules.dw.entity.BalanceManage;
import org.jeecg.modules.dw.service.IBalanceManageService;

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
 * @Description: 结算管理
 * @Author: jeecg-boot
 * @Date:   2020-04-29
 * @Version: V1.0
 */
@Api(tags="结算管理")
@RestController
@RequestMapping("/balancemanage/balanceManage")
@Slf4j
public class BalanceManageController extends JeecgController<BalanceManage, IBalanceManageService> {
	@Autowired
	private IBalanceManageService balanceManageService;
	
	/**
	 * 分页列表查询
	 *
	 * @param balanceManage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "结算管理-分页列表查询")
	@ApiOperation(value="结算管理-分页列表查询", notes="结算管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BalanceManage balanceManage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BalanceManage> queryWrapper = QueryGenerator.initQueryWrapper(balanceManage, req.getParameterMap());
		Page<BalanceManage> page = new Page<BalanceManage>(pageNo, pageSize);
		IPage<BalanceManage> pageList = balanceManageService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param balanceManage
	 * @return
	 */
	@AutoLog(value = "结算管理-添加")
	@ApiOperation(value="结算管理-添加", notes="结算管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BalanceManage balanceManage) {
		balanceManageService.save(balanceManage);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param balanceManage
	 * @return
	 */
	@AutoLog(value = "结算管理-编辑")
	@ApiOperation(value="结算管理-编辑", notes="结算管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BalanceManage balanceManage) {
		balanceManageService.updateById(balanceManage);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "结算管理-通过id删除")
	@ApiOperation(value="结算管理-通过id删除", notes="结算管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		balanceManageService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "结算管理-批量删除")
	@ApiOperation(value="结算管理-批量删除", notes="结算管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.balanceManageService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "结算管理-通过id查询")
	@ApiOperation(value="结算管理-通过id查询", notes="结算管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BalanceManage balanceManage = balanceManageService.getById(id);
		if(balanceManage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(balanceManage);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param balanceManage
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BalanceManage balanceManage) {
        return super.exportXls(request, balanceManage, BalanceManage.class, "结算管理");
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
        return super.importExcel(request, response, BalanceManage.class);
    }

}
