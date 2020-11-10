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
import org.jeecg.modules.dw.entity.Hiselec;
import org.jeecg.modules.dw.service.IHiselecService;

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
 * @Description: 历史用电量
 * @Author: jeecg-boot
 * @Date:   2020-06-01
 * @Version: V1.0
 */
@Api(tags="历史用电量")
@RestController
@RequestMapping("/hiselec/hiselec")
@Slf4j
public class HiselecController extends JeecgController<Hiselec, IHiselecService> {
	@Autowired
	private IHiselecService hiselecService;
	
	/**
	 * 分页列表查询
	 *
	 * @param hiselec
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "历史用电量-分页列表查询")
	@ApiOperation(value="历史用电量-分页列表查询", notes="历史用电量-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Hiselec hiselec,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Hiselec> queryWrapper = QueryGenerator.initQueryWrapper(hiselec, req.getParameterMap());
		queryWrapper.orderByAsc("username");
		Page<Hiselec> page = new Page<Hiselec>(pageNo, pageSize);
		IPage<Hiselec> pageList = hiselecService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<Hiselec>> waterqueryall(String username,String elecDate,String elecBegin,String elecEnd) {
		Result<List<Hiselec>> result = new Result<>();
		QueryWrapper<Hiselec> queryWrapper = new QueryWrapper<Hiselec>();
		//电量分析 根据选择的公司生成图表和列表
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("username", username);
		}
		//电量分析 根据选择的时间段生成数据
		if(!oConvertUtils.isEmpty(elecDate)) {
			System.out.println("选择的是日");
			queryWrapper.eq("elec_date", elecDate); 
		}
		if(!oConvertUtils.isEmpty(elecBegin) && !oConvertUtils.isEmpty(elecEnd)) {
			System.out.println("选择的是月或者年");
			System.out.println(elecBegin);
			System.out.println(elecEnd);
			queryWrapper.ge("elec_date", elecBegin);
			queryWrapper.lt("elec_date", elecEnd);
		}
		List<Hiselec> list = hiselecService.list(queryWrapper);
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
	 * @param hiselec
	 * @return
	 */
	@AutoLog(value = "历史用电量-添加")
	@ApiOperation(value="历史用电量-添加", notes="历史用电量-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Hiselec hiselec) {
		hiselecService.save(hiselec);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param hiselec
	 * @return
	 */
	@AutoLog(value = "历史用电量-编辑")
	@ApiOperation(value="历史用电量-编辑", notes="历史用电量-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Hiselec hiselec) {
		hiselecService.updateById(hiselec);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "历史用电量-通过id删除")
	@ApiOperation(value="历史用电量-通过id删除", notes="历史用电量-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		hiselecService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "历史用电量-批量删除")
	@ApiOperation(value="历史用电量-批量删除", notes="历史用电量-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hiselecService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "历史用电量-通过id查询")
	@ApiOperation(value="历史用电量-通过id查询", notes="历史用电量-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Hiselec hiselec = hiselecService.getById(id);
		if(hiselec==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(hiselec);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param hiselec
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Hiselec hiselec) {
        return super.exportXls(request, hiselec, Hiselec.class, "历史用电量");
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
        return super.importExcel(request, response, Hiselec.class);
    }

}
