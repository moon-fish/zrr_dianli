package org.jeecg.modules.dw.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.dw.entity.DayElec;
import org.jeecg.modules.dw.entity.UserPurchaseContractB;
import org.jeecg.modules.dw.service.IDayElecService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 日用电量
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="日用电量")
@RestController
@RequestMapping("/dayelec/dayElec")
public class DayElecController extends JeecgController<DayElec, IDayElecService> {
	@Autowired
	private IDayElecService dayElecService;
	
	/**
	 * 分页列表查询
	 *
	 * @param dayElec
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "日用电量-分页列表查询")
	@ApiOperation(value="日用电量-分页列表查询", notes="日用电量-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DayElec dayElec,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DayElec> queryWrapper = QueryGenerator.initQueryWrapper(dayElec, req.getParameterMap());
		Page<DayElec> page = new Page<DayElec>(pageNo, pageSize);
		IPage<DayElec> pageList = dayElecService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<DayElec>> queryall(String username,String elecBegin,String elecEnd,String elecDate) {
		Result<List<DayElec>> result = new Result<>();
		QueryWrapper<DayElec> queryWrapper = new QueryWrapper<DayElec>();
		queryWrapper.orderByDesc("create_time");
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("username", username);
		}
		//日电量根据日期查询>=elecBegin,  <elecEnd
		if(!oConvertUtils.isEmpty(elecBegin)) {
			queryWrapper.ge("elec_date", elecBegin);
		}
		if(!oConvertUtils.isEmpty(elecEnd)) {
			queryWrapper.le("elec_date", elecEnd);
		}
		//pc 日电量根据选择的日期,用户名筛选数据
		if(!oConvertUtils.isEmpty(elecDate)) {
			queryWrapper.eq("elec_date", elecDate);
		}
		List<DayElec> list = dayElecService.list(queryWrapper);
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
	 * @param dayElec
	 * @return
	 */
	@AutoLog(value = "日用电量-添加")
	@ApiOperation(value="日用电量-添加", notes="日用电量-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DayElec dayElec) {
		dayElecService.save(dayElec);
		return Result.ok("添加成功！");
	}

	 /**
	  * 批量添加
	  *
	  * @param dayElec
	  * @return
	  */
	 @AutoLog(value = "日用电量-批量添加/修改")
	 @ApiOperation(value="日用电量-批量添加/修改", notes="日用电量-批量添加/修改")
	 @PostMapping(value = "/saveOrUpdateBatch")
	 public Result<?> saveOrUpdateBatch(@RequestBody List<DayElec> dayElec) {
	 	log.info("dsadasaaaaaa打打aaa"+dayElec);
		 dayElecService.saveOrUpdateBatch(dayElec);
		 return Result.ok("添加成功！");
	 }
	
	/**
	 * 编辑
	 *
	 * @param dayElec
	 * @return
	 */
	@AutoLog(value = "日用电量-编辑")
	@ApiOperation(value="日用电量-编辑", notes="日用电量-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DayElec dayElec) {
		dayElecService.updateById(dayElec);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "日用电量-通过id删除")
	@ApiOperation(value="日用电量-通过id删除", notes="日用电量-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dayElecService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "日用电量-批量删除")
	@ApiOperation(value="日用电量-批量删除", notes="日用电量-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dayElecService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "日用电量-通过id查询")
	@ApiOperation(value="日用电量-通过id查询", notes="日用电量-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DayElec dayElec = dayElecService.getById(id);
		return Result.ok(dayElec);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param dayElec
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, DayElec dayElec) {
      return super.exportXls(request, dayElec, DayElec.class, "日用电量");
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
      return super.importExcel(request, response, DayElec.class);
  }

}
