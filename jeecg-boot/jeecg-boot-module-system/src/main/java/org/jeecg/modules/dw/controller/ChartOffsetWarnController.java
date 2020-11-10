package org.jeecg.modules.dw.controller;

import java.util.ArrayList;
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
import org.jeecg.modules.dw.entity.AccountInfo;
import org.jeecg.modules.dw.entity.AccountUseInfo;
import org.jeecg.modules.dw.entity.ChartOffsetWarn;
import org.jeecg.modules.dw.service.IAccountDisabledService;
import org.jeecg.modules.dw.service.IChartOffsetWarnService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;

import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 偏差预警
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Slf4j
@Api(tags="偏差预警")
@RestController
@RequestMapping("/chartoffsetwarn/chartOffsetWarn")
public class ChartOffsetWarnController extends JeecgController<ChartOffsetWarn, IChartOffsetWarnService> {
	@Autowired
	private IChartOffsetWarnService chartOffsetWarnService;
	 @Autowired
	 private AccountUseInfoController accountUseInfoController;
	
	/**
	 * 分页列表查询
	 *
	 * @param chartOffsetWarn
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "偏差预警-分页列表查询")
	@ApiOperation(value="偏差预警-分页列表查询", notes="偏差预警-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ChartOffsetWarn chartOffsetWarn,String month,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ChartOffsetWarn> queryWrapper = QueryGenerator.initQueryWrapper(chartOffsetWarn, req.getParameterMap());
		//偏差预警表保存的是实际使用电量 需要返回实际电量与申报电量
		//1.查询申报电量表account_use_account
		Result<List<AccountUseInfo>> auir = accountUseInfoController.queryAccountUseInfo(month,true);
		//实际电量chart_offset_warn
		Result<List<ChartOffsetWarn>> cowr = this.queryall(month);
		//根据户号对比数据，筛选出 申报但未使用或者 使用但未申报的户号
		List<AccountUseInfo> aui = new ArrayList<AccountUseInfo>(); //存储申报数据
		List<ChartOffsetWarn> cow = new ArrayList<ChartOffsetWarn>(); //存储使用数据
		if(auir.isSuccess()&&cowr.isSuccess()){
			for(int i=0;i<auir.getResult().size();i++){
				for(int j=0;j<cowr.getResult().size();j++){
					if(auir.getResult().get(i).getAccountNumber().equals(cowr.getResult().get(j).getAccountNumber())){
						aui.add(auir.getResult().get(i));
						cow.add(cowr.getResult().get(j));
					}
				}
			}
		}
		//根据实际需求优化，未完成--------

		Page<ChartOffsetWarn> page = new Page<ChartOffsetWarn>(pageNo, pageSize);
		IPage<ChartOffsetWarn> pageList = chartOffsetWarnService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 查询所有数据  根据某个状态查询
	 * 
	 */
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<ChartOffsetWarn>> queryall(String month) {
		Result<List<ChartOffsetWarn>> result = new Result<>();
		QueryWrapper<ChartOffsetWarn> queryWrapper = new QueryWrapper<ChartOffsetWarn>();
		if(oConvertUtils.isNotEmpty(month)) {
			queryWrapper.eq("elec_month", month);
		}
		List<ChartOffsetWarn> list = chartOffsetWarnService.list(queryWrapper);
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
	 * @param chartOffsetWarn
	 * @return
	 */
	@AutoLog(value = "偏差预警-添加")
	@ApiOperation(value="偏差预警-添加", notes="偏差预警-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ChartOffsetWarn chartOffsetWarn) {
		chartOffsetWarnService.save(chartOffsetWarn);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param chartOffsetWarn
	 * @return
	 */
	@AutoLog(value = "偏差预警-编辑")
	@ApiOperation(value="偏差预警-编辑", notes="偏差预警-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ChartOffsetWarn chartOffsetWarn) {
		chartOffsetWarnService.updateById(chartOffsetWarn);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "偏差预警-通过id删除")
	@ApiOperation(value="偏差预警-通过id删除", notes="偏差预警-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		chartOffsetWarnService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "偏差预警-批量删除")
	@ApiOperation(value="偏差预警-批量删除", notes="偏差预警-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.chartOffsetWarnService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "偏差预警-通过id查询")
	@ApiOperation(value="偏差预警-通过id查询", notes="偏差预警-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ChartOffsetWarn chartOffsetWarn = chartOffsetWarnService.getById(id);
		return Result.ok(chartOffsetWarn);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param chartOffsetWarn
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, ChartOffsetWarn chartOffsetWarn) {
      return super.exportXls(request, chartOffsetWarn, ChartOffsetWarn.class, "偏差预警");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response,String elecMonth) {
	  //重写导入方法，导入时将月份修改为相应的月份
//      return super.importExcel(request, response, ChartOffsetWarn.class);
	  MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ChartOffsetWarn> list = ExcelImportUtil.importExcel(file.getInputStream(), ChartOffsetWarn.class, params);
              //update-begin-author:taoyan date:20190528 for:批量插入数据
              long start = System.currentTimeMillis();
              System.out.println("mmmmmmmmmmmmmm00000000");
              List<ChartOffsetWarn> cow = new ArrayList<ChartOffsetWarn>();
              for(ChartOffsetWarn c:list) {
            	  c.setElecMonth(Integer.valueOf(elecMonth));
            	  cow.add(c);
              }
              System.out.println(cow);
              chartOffsetWarnService.saveBatch(cow);
              //400条 saveBatch消耗时间1592毫秒  循环插入消耗时间1947毫秒
              //1200条  saveBatch消耗时间3687毫秒 循环插入消耗时间5212毫秒
              log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
              //update-end-author:taoyan date:20190528 for:批量插入数据
              return Result.ok("文件导入成功！数据行数：" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(), e);
              return Result.error("文件导入失败:" + e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.error("文件导入失败！");
  }

}
