package org.jeecg.modules.dw.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.ElectricityFilerB;
import org.jeecg.modules.dw.entity.MomthElec;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IElectricityFilerBService;
import org.jeecg.modules.dw.service.IMomthElecService;
import org.jeecg.modules.dw.service.IUserInfoBService;
import org.jeecg.modules.dw.utils.Auth;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserRoleService;

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
 * @Description: 电量申报
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Api(tags="电量申报")
@RestController
@RequestMapping("/electricityfilerb/electricityFilerB")
@Slf4j
public class ElectricityFilerBController extends JeecgController<ElectricityFilerB, IElectricityFilerBService> {
	@Autowired
	private IElectricityFilerBService electricityFilerBService;
	@Autowired
	private IMomthElecService momthElecService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private IUserInfoBService userInfoBService;
	@Autowired
	private Auth auth;
	
	/**
	 * 分页列表查询
	 *
	 * @param electricityFilerB
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "电量申报-分页列表查询")
	@ApiOperation(value="电量申报-分页列表查询", notes="电量申报-分页列表查询")
	@GetMapping(value = "/list/{num}")
	public Result<?> queryPageList(ElectricityFilerB electricityFilerB,@PathVariable int num,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ElectricityFilerB> queryWrapper = QueryGenerator.initQueryWrapper(electricityFilerB, req.getParameterMap());

		queryWrapper.orderByDesc("create_time");

//		if(oConvertUtils.isNotEmpty(electricityFilerB.getBeginDate())){
//			queryWrapper.ge("create_time",electricityFilerB.getBeginDate());
//		}
//		if(oConvertUtils.isNotEmpty(electricityFilerB.getEndDate())){
//			queryWrapper.le("create_time",electricityFilerB.getEndDate());
//		}
		//num为1时，显示的是通知信息
		if(num == 1){
			int date0 = 1;
			int date1 = 10;
			int date2 = 20;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Calendar calendar=Calendar.getInstance();
			String currentTime  = sdf.format(calendar.getTime()); //当前时间
			calendar.set(Calendar.DAY_OF_MONTH, date0);
			String oneTime = sdf2.format(calendar.getTime()); //本月1号的时间
			calendar.set(Calendar.DAY_OF_MONTH, date1);
			String tenTime = sdf2.format(calendar.getTime()); //本月10号的时间
			calendar.set(Calendar.DAY_OF_MONTH, date2);
			String twentyTime = sdf2.format(calendar.getTime()); //本月20号的时间
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			String nextMonth = sdf2.format(calendar.getTime()); //下一个月1号

			if(currentTime.compareTo(tenTime)<0){ //当前时间小于本月10号
				queryWrapper.between("create_time",oneTime,tenTime);
			}else if(currentTime.compareTo(twentyTime)>0){ //当前时间大于本月20号
				queryWrapper.between("create_time",twentyTime,nextMonth);
			}else{ //当前时间介于本月10号到20号
				queryWrapper.between("create_time",oneTime,twentyTime);
			}
		}



		//权限设置
		List<String> companys = auth.queryCompanys();
		queryWrapper.in("filer_company_name", companys);
		
		Page<ElectricityFilerB> page = new Page<ElectricityFilerB>(pageNo, pageSize);
		IPage<ElectricityFilerB> pageList = electricityFilerBService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	//查询所有数据
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<ElectricityFilerB>> waterqueryall(String companyName,String begin,String end) {
		Result<List<ElectricityFilerB>> result = new Result<>();
		QueryWrapper<ElectricityFilerB> queryWrapper = new QueryWrapper<ElectricityFilerB>();
		queryWrapper.orderByDesc("create_time");
		if(!oConvertUtils.isEmpty(companyName)) {
			queryWrapper.eq("filer_company_name", companyName);
		}
		if(!oConvertUtils.isEmpty(begin)&&!oConvertUtils.isEmpty(end)){
			queryWrapper.between("create_time",begin,end);
		}
		List<ElectricityFilerB> list = electricityFilerBService.list(queryWrapper);
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
	 *   用户 电量申报
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "电量申报-添加")
	@ApiOperation(value="电量申报-添加", notes="电量申报-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ElectricityFilerB electricityFilerB) {

		//电量申报，按月申报，当在某个时间段内时，若这个时间内无数据则进行添加，否则进行修改
		Result<List<ElectricityFilerB>> result = null;
		int date0 = 1;
		int date1 = 10;
		int date2 = 20;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar calendar=Calendar.getInstance();
		String currentTime  = sdf.format(calendar.getTime()); //当前时间
		calendar.set(Calendar.DAY_OF_MONTH, date0);
		String oneTime = sdf2.format(calendar.getTime()); //本月1号的时间
		calendar.set(Calendar.DAY_OF_MONTH, date1);
		String tenTime = sdf2.format(calendar.getTime()); //本月10号的时间
		calendar.set(Calendar.DAY_OF_MONTH, date2);
		String twentyTime = sdf2.format(calendar.getTime()); //本月20号的时间
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String nextMonth = sdf2.format(calendar.getTime()); //下一个月1号

		if(currentTime.compareTo(tenTime)<0){ //当前时间小于本月10号
			result = this.waterqueryall(electricityFilerB.getFilerCompanyName(),oneTime,tenTime);
			//电量申报的同时，修改分月电量表数据（申报公司，状态，申报月份，申报量）
			this.updateMonthElec(electricityFilerB.getFilerCompanyName(),"1",currentTime.split("-")[1],electricityFilerB.getMarketQuotation());
		}else if(currentTime.compareTo(twentyTime)>0){ //当前时间大于本月20号
			result = this.waterqueryall(electricityFilerB.getFilerCompanyName(),twentyTime,nextMonth);
			this.updateMonthElec(electricityFilerB.getFilerCompanyName(),"2",currentTime.split("-")[1],electricityFilerB.getMarketQuotation());
		}else{ //当前时间介于本月10号到20号
			result = this.waterqueryall(electricityFilerB.getFilerCompanyName(),tenTime,twentyTime);
			this.updateMonthElec(electricityFilerB.getFilerCompanyName(),"3",currentTime.split("-")[1],electricityFilerB.getMarketQuotation());
		}
		if(result.isSuccess()){
			electricityFilerB.setId(result.getResult().get(0).getId());
		}
		electricityFilerB.setFilerStatus("1");
		electricityFilerBService.saveOrUpdate(electricityFilerB);

		return Result.ok("添加成功！");
	}

	 /**
	  *   添加
	  *   根据电量申报 修改 分月电量
	  * @return
	  */
	 private void updateMonthElec(String companyName,String num,String month,String marketQuotation){
//		 Result<List<MomthElec>> result = new Result<>();
		 QueryWrapper<MomthElec> queryWrapper = new QueryWrapper<MomthElec>();
		 queryWrapper.orderByDesc("update_num");
		 if(!oConvertUtils.isEmpty(companyName)) {
			 queryWrapper.eq("username", companyName);
		 }
		 if(!oConvertUtils.isEmpty(num)) {
			 queryWrapper.eq("update_num", num);
		 }
		 List<MomthElec> list = momthElecService.list(queryWrapper);
		 MomthElec me = null;
		 if(list==null||list.size()<=0) {
			 me = new MomthElec();
			 me.setUsername(companyName);
		 }else {
		 	me = list.get(0);
		 }
		 me.setUpdateNum(Integer.valueOf(num));
		 if(month.equals("1")){
			 me.setYi(marketQuotation);
		 }else if(month.equals("2")){
			 me.setEr(marketQuotation);
		 }else if(month.equals("3")){
			 me.setSan(marketQuotation);
		 }else if(month.equals("4")){
			 me.setSi(marketQuotation);
		 }else if(month.equals("5")){
			 me.setWu(marketQuotation);
		 }else if(month.equals("6")){
			 me.setLiu(marketQuotation);
		 }else if(month.equals("7")){
			 me.setQi(marketQuotation);
		 }else if(month.equals("8")){
			 me.setBa(marketQuotation);
		 }else if(month.equals("9")){
			 me.setJiu(marketQuotation);
		 }else if(month.equals("10")){
			 me.setShi(marketQuotation);
		 }else if(month.equals("11")){
			 me.setShiyi(marketQuotation);
		 }else if(month.equals("12")){
			 me.setShier(marketQuotation);
		 }
		 momthElecService.saveOrUpdate(me);
	 }

	/**
	 *   添加
	 *   售电公司 替用户申报电量
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "售电公司电量申报-添加")
	@ApiOperation(value="售电公司电量申报-添加", notes="售电公司电量申报-添加")
	@PostMapping(value = "/elecAdd")
	public Result<?> elecAdd(@RequestBody ElectricityFilerB electricityFilerB) {
		electricityFilerBService.save(electricityFilerB);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param electricityFilerB
	 * @return
	 */
	@AutoLog(value = "电量申报-编辑")
	@ApiOperation(value="电量申报-编辑", notes="电量申报-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ElectricityFilerB electricityFilerB,String filerStatus) {
		electricityFilerBService.updateById(electricityFilerB);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *  批量修改
	 *
	 * @param entityList
	 * @return
	 */
	@AutoLog(value = "电量申报-批量修改")
	@ApiOperation(value="电量申报-批量修改", notes="电量申报-批量修改")
	@PutMapping(value = "/updateBatch")
	public Result<?> updateBatch(@RequestBody List<ElectricityFilerB> entityList) {
		this.electricityFilerBService.updateBatchById(entityList);
		return Result.ok("批量修改成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电量申报-通过id删除")
	@ApiOperation(value="电量申报-通过id删除", notes="电量申报-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		electricityFilerBService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "电量申报-批量删除")
	@ApiOperation(value="电量申报-批量删除", notes="电量申报-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.electricityFilerBService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "电量申报-通过id查询")
	@ApiOperation(value="电量申报-通过id查询", notes="电量申报-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ElectricityFilerB electricityFilerB = electricityFilerBService.getById(id);
		if(electricityFilerB==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(electricityFilerB);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param electricityFilerB
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElectricityFilerB electricityFilerB) {
        return super.exportXls(request, electricityFilerB, ElectricityFilerB.class, "电量申报");
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
        return super.importExcel(request, response, ElectricityFilerB.class);
    }

}
