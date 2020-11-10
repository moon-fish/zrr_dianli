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
import org.jeecg.modules.dw.entity.GenerationSideInfoB;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IGenerationSideInfoBService;

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
 * @Description: 发电侧信息
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="发电侧信息")
@RestController
@RequestMapping("/generationsideInfob/generationSideInfoB")
@Slf4j
public class GenerationSideInfoBController extends JeecgController<GenerationSideInfoB, IGenerationSideInfoBService> {
	@Autowired
	private IGenerationSideInfoBService generationSideInfoBService;
	
	/**
	 * 分页列表查询
	 *
	 * @param generationSideInfoB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "发电侧信息-分页列表查询")
	@ApiOperation(value="发电侧信息-分页列表查询", notes="发电侧信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(GenerationSideInfoB generationSideInfoB,String username,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GenerationSideInfoB> queryWrapper = QueryGenerator.initQueryWrapper(generationSideInfoB, req.getParameterMap());
		//选择电厂 弹出框
		if(!oConvertUtils.isEmpty(username)) {
			queryWrapper.eq("station_name", username);
		}
		Page<GenerationSideInfoB> page = new Page<GenerationSideInfoB>(pageNo, pageSize);
		IPage<GenerationSideInfoB> pageList = generationSideInfoBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据 
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<GenerationSideInfoB>> queryall() {
		Result<List<GenerationSideInfoB>> result = new Result<>();
		QueryWrapper<GenerationSideInfoB> queryWrapper = new QueryWrapper<GenerationSideInfoB>();
		List<GenerationSideInfoB> list = generationSideInfoBService.list(queryWrapper);
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
	 * @param generationSideInfoB
	 * @return
	 */
	@AutoLog(value = "发电侧信息-添加")
	@ApiOperation(value="发电侧信息-添加", notes="发电侧信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GenerationSideInfoB generationSideInfoB) {
		generationSideInfoBService.save(generationSideInfoB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param generationSideInfoB
	 * @return
	 */
	@AutoLog(value = "发电侧信息-编辑")
	@ApiOperation(value="发电侧信息-编辑", notes="发电侧信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GenerationSideInfoB generationSideInfoB) {
		generationSideInfoBService.updateById(generationSideInfoB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发电侧信息-通过id删除")
	@ApiOperation(value="发电侧信息-通过id删除", notes="发电侧信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		generationSideInfoBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "发电侧信息-批量删除")
	@ApiOperation(value="发电侧信息-批量删除", notes="发电侧信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.generationSideInfoBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发电侧信息-通过id查询")
	@ApiOperation(value="发电侧信息-通过id查询", notes="发电侧信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GenerationSideInfoB generationSideInfoB = generationSideInfoBService.getById(id);
		if(generationSideInfoB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(generationSideInfoB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param generationSideInfoB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, GenerationSideInfoB generationSideInfoB) {
        return super.exportXls(request, generationSideInfoB, GenerationSideInfoB.class, "发电侧信息");
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
        return super.importExcel(request, response, GenerationSideInfoB.class);
    }

}
