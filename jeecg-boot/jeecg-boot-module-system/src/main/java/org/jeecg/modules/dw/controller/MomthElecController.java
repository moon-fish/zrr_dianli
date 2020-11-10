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
import org.jeecg.modules.dw.entity.ElectricityFilerB;
import org.jeecg.modules.dw.entity.MomthElec;
import org.jeecg.modules.dw.service.IMomthElecService;

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
 * @Description: 分月电量
 * @Author: jeecg-boot
 * @Date:   2020-04-29
 * @Version: V1.0
 */
@Api(tags="分月电量")
@RestController
@RequestMapping("/monthelec/momthElec")
@Slf4j
public class MomthElecController extends JeecgController<MomthElec, IMomthElecService> {
	@Autowired
	private IMomthElecService momthElecService;
	
	/**
	 * 分页列表查询
	 *
	 * @param momthElec
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "分月电量-分页列表查询")
	@ApiOperation(value="分月电量-分页列表查询", notes="分月电量-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MomthElec momthElec,String username,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MomthElec> queryWrapper = QueryGenerator.initQueryWrapper(momthElec, req.getParameterMap());
		queryWrapper.orderByDesc("create_time");
		//分月电量表 调整记录 
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("username", username);
		}
		Page<MomthElec> page = new Page<MomthElec>(pageNo, pageSize);
		IPage<MomthElec> pageList = momthElecService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<MomthElec>> waterqueryall(String companyName,String dateBegin,String dateEnd) {
		Result<List<MomthElec>> result = new Result<>();
		QueryWrapper<MomthElec> queryWrapper = new QueryWrapper<MomthElec>();
		queryWrapper.orderByDesc("create_time");
		if(!oConvertUtils.isEmpty(companyName)) {
			queryWrapper.eq("username", companyName);
		}
		//首页查询结算电量
		if(!oConvertUtils.isEmpty(dateBegin) && !oConvertUtils.isEmpty(dateEnd)) {
			queryWrapper.le("elec_date", dateEnd);
			queryWrapper.ge("elec_date", dateBegin);
		}
		List<MomthElec> list = momthElecService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	//查询所有数据 调整记录修改时，修改次数
	@RequestMapping(value = "/queryall2", method = RequestMethod.GET)
	public Result<List<MomthElec>> queryall2(String companyName) {
		Result<List<MomthElec>> result = new Result<>();
		QueryWrapper<MomthElec> queryWrapper = new QueryWrapper<MomthElec>();
		queryWrapper.orderByDesc("update_num");
		if(!oConvertUtils.isEmpty(companyName)) {
			queryWrapper.eq("username", companyName);
		}
		List<MomthElec> list = momthElecService.list(queryWrapper);
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
	 * @param momthElec
	 * @return
	 */
	@AutoLog(value = "分月电量-添加")
	@ApiOperation(value="分月电量-添加", notes="分月电量-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MomthElec momthElec) {
		momthElecService.save(momthElec);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param momthElec
	 * @return
	 */
	@AutoLog(value = "分月电量-编辑")
	@ApiOperation(value="分月电量-编辑", notes="分月电量-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MomthElec momthElec) {
		momthElecService.updateById(momthElec);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分月电量-通过id删除")
	@ApiOperation(value="分月电量-通过id删除", notes="分月电量-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		momthElecService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "分月电量-批量删除")
	@ApiOperation(value="分月电量-批量删除", notes="分月电量-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.momthElecService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "分月电量-通过id查询")
	@ApiOperation(value="分月电量-通过id查询", notes="分月电量-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MomthElec momthElec = momthElecService.getById(id);
		if(momthElec==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(momthElec);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param momthElec
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MomthElec momthElec) {
        return super.exportXls(request, momthElec, MomthElec.class, "分月电量");
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
        return super.importExcel(request, response, MomthElec.class);
    }

}
