package org.jeecg.modules.dw.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.Hiselec;
import org.jeecg.modules.dw.entity.Hisload;
import org.jeecg.modules.dw.service.IHisloadService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 负荷数据
 * @Author: jeecg-boot
 * @Date:   2020-06-03
 * @Version: V1.0
 */
@Slf4j
@Api(tags="负荷数据")
@RestController
@RequestMapping("/hisload/hisload")
public class HisloadController extends JeecgController<Hisload, IHisloadService> {
	@Autowired
	private IHisloadService hisloadService;
	
	/**
	 * 分页列表查询
	 *
	 * @param hisload
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "负荷数据-分页列表查询")
	@ApiOperation(value="负荷数据-分页列表查询", notes="负荷数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Hisload hisload,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Hisload> queryWrapper = QueryGenerator.initQueryWrapper(hisload, req.getParameterMap());
		Page<Hisload> page = new Page<Hisload>(pageNo, pageSize);
		IPage<Hisload> pageList = hisloadService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<Hisload>> waterqueryall(String username,String elecDate,String elecBegin,String elecEnd) {
		Result<List<Hisload>> result = new Result<>();
		QueryWrapper<Hisload> queryWrapper = new QueryWrapper<Hisload>();
		//电量分析 根据选择的公司生成图表和列表
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("username", username);
		}
		//电量分析 根据选择的时间段生成数据
		if(!oConvertUtils.isEmpty(elecDate)) {
			queryWrapper.eq("elec_date", elecDate); 
		}
		if(!oConvertUtils.isEmpty(elecBegin) && !oConvertUtils.isEmpty(elecEnd)) {
			queryWrapper.ge("elec_date", elecBegin);
			queryWrapper.le("elec_date", elecEnd);
		}
		List<Hisload> list = hisloadService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 * 添加
	 *
	 * @param hisload
	 * @return
	 */
	@AutoLog(value = "负荷数据-添加")
	@ApiOperation(value="负荷数据-添加", notes="负荷数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Hisload hisload) {
		hisloadService.save(hisload);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param hisload
	 * @return
	 */
	@AutoLog(value = "负荷数据-编辑")
	@ApiOperation(value="负荷数据-编辑", notes="负荷数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Hisload hisload) {
		hisloadService.updateById(hisload);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "负荷数据-通过id删除")
	@ApiOperation(value="负荷数据-通过id删除", notes="负荷数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		hisloadService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "负荷数据-批量删除")
	@ApiOperation(value="负荷数据-批量删除", notes="负荷数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hisloadService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "负荷数据-通过id查询")
	@ApiOperation(value="负荷数据-通过id查询", notes="负荷数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Hisload hisload = hisloadService.getById(id);
		return Result.ok(hisload);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param hisload
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Hisload hisload) {
      return super.exportXls(request, hisload, Hisload.class, "负荷数据");
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
      return super.importExcel(request, response, Hisload.class);
  }

}
