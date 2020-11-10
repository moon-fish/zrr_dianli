package org.jeecg.modules.dw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.AccountDisabled;
import org.jeecg.modules.dw.entity.AccountInfo;
import org.jeecg.modules.dw.entity.AccountUseInfo;
import org.jeecg.modules.dw.service.IAccountDisabledService;
import org.jeecg.modules.dw.service.IAccountInfoService;
import org.jeecg.modules.dw.service.IAccountUseInfoService;
import java.util.Date;
import java.util.HashMap;

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
 * @Description: 存放对比后的数据
 * @Author: jeecg-boot
 * @Date:   2020-10-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="存放对比后的数据")
@RestController
@RequestMapping("/accountuseinfo/accountUseInfo")
public class AccountUseInfoController extends JeecgController<AccountUseInfo, IAccountUseInfoService> {
	@Autowired
	private IAccountUseInfoService accountUseInfoService;
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IAccountDisabledService accountDisabledService;
	
	/**
	 * 分页列表查询
	 *
	 * @param accountUseInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-分页列表查询")
	@ApiOperation(value="存放对比后的数据-分页列表查询", notes="存放对比后的数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AccountUseInfo accountUseInfo,String month,String num,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AccountUseInfo> queryWrapper = QueryGenerator.initQueryWrapper(accountUseInfo, req.getParameterMap());
		/*
		 * 户号审核 --数据对比
		 * 
		 * */
		Result<List<AccountUseInfo>> accountUInfo = this.queryAccountUseInfo(month,false);
		List<AccountInfo> list = new ArrayList<AccountInfo>();
		if(oConvertUtils.isNotEmpty(month)) {
			//判断表中数据是否存在该月份数据，若存在，直接显示，否则进行数据对比
			if(accountUInfo.isSuccess()) {
				log.info("月份数据已存在");
				if(oConvertUtils.isEmpty(num)) {
					queryWrapper.eq("compare_month", month);
				}else {
					if(num.equals("1")) {
//						java  JDK8的lambda表达式
						queryWrapper.eq("compare_month", month).and(qw -> qw.eq("account_status", "2"));
					}else if(num.equals("2")) {
						queryWrapper.eq("compare_month", month).and(qw -> qw.eq("account_status", "3"));
					}
				}
				
			}else {
				
				//1.获取本月时间（例：9.14-10.14 ）及上个月时间（例：8.14-9.14）
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-14 00:00:00");
				Calendar cal = Calendar.getInstance();
				Date d = new Date();
				d.setMonth(Integer.parseInt(month)-1);
				cal.setTime(d);
				cal.add(Calendar.MONTH, -1);
				Date m1 = cal.getTime();
		        String monthOneBefore = format.format(m1);
		        cal.add(Calendar.MONTH, -1);
				Date m2 = cal.getTime();
		        String monthTwoBefore = format.format(m2);
		        cal.add(Calendar.MONTH, -1);
				Date m3 = cal.getTime();
		        String monthThreeBefore = format.format(m3);
		        System.out.println("1111111111111111111");
		        //2.查询禁用数据，上个月数据及本月数据
		        Result<List<AccountInfo>> dataMonth = this.queryAccountInfo("",monthTwoBefore, monthOneBefore);//本月数据
		        Result<List<AccountUseInfo>> dataBefore = this.queryAccountUseInfo(monthOneBefore.split("-")[1],true);//上月数据
		        Result<List<AccountDisabled>> dataDisabled = this.queryDisabled(); //禁用数据
		        //对比数据 0:禁用,1:重复,2:增加,3:减少
		        if(dataMonth.getResult().size()>0) {
		        	//1.对比本月数据中的禁用数据
		        	List<AccountInfo> dataMonthCopy = new ArrayList<AccountInfo>();
		        	dataMonthCopy.addAll(dataMonth.getResult());
		        	for(int i=0;i<dataMonth.getResult().size();i++) {
			        	for(int j=0;j<dataDisabled.getResult().size();j++) {
			        		if(dataMonth.getResult().get(i).getAccountNumber().equals(dataDisabled.getResult().get(j).getAccountNumber())) {
			        			dataMonth.getResult().get(i).setAccountStatus("0");
			        			dataMonthCopy.remove(dataMonth.getResult().get(i));
			        		}
			        	}
			        }
		        	//2.对比本月数据中的重复数据
		        	List<AccountInfo> accountNum2 = new ArrayList<AccountInfo>(); //存放提取出来的重复的户号
		        	List<AccountInfo> dataCopy = new ArrayList<AccountInfo>(); //存放重复数据
		        	List<AccountInfo> dataNoCopy = new ArrayList<AccountInfo>(); //存放不重复数据
		        	Map<String, Integer> map = new HashMap<>(); 
		        	for(AccountInfo a:dataMonthCopy) {
		        		if(map.containsKey(a.getAccountNumber())){
		        			accountNum2.add(a);//获取重复的学生名称
		        			int n = map.get(a.getAccountNumber());
		    				map.put(a.getAccountNumber(), n+1);
		    			}else{
		    				map.put(a.getAccountNumber(), 1);
		    			}
		        	}
		        	if(accountNum2.size()>0) { //判断是否有重复数据，有的话排除
		        		for(int i=0;i<accountNum2.size();i++) {
			        		for(int j=0;j<dataMonthCopy.size();j++) {
			        			if(accountNum2.get(i).getAccountNumber().equals(dataMonthCopy.get(j).getAccountNumber()) ) {
			        				dataMonthCopy.get(j).setAccountStatus("1"); //设置重复数据状态为1
			        				dataCopy.add(dataMonthCopy.get(j)); //重复数据
			        			}
			        		}
			        	}
		        		dataMonthCopy.removeAll(dataCopy);
		        		dataNoCopy.addAll(dataMonthCopy);
			        	
		        	}else {
		        		dataNoCopy.addAll(dataMonthCopy);
		        	}
		        	//3.本月数据与上月数据对比，获取增加的和减少的
		        	List<AccountInfo> accountAdd = new ArrayList<AccountInfo>();
		        	List<AccountUseInfo> accountReduce = new ArrayList<AccountUseInfo>();
		        	accountAdd.addAll(dataNoCopy); //选择月数据
		        	if(dataBefore.isSuccess()) {
		        		accountReduce.addAll(dataBefore.getResult()); //上月数据
		        		for(int i=0;i<dataNoCopy.size();i++) { //筛选新增/减少数据
			        		for(int j=0;j<dataBefore.getResult().size();j++) {
			        			if(dataNoCopy.get(i).getAccountNumber().equals(dataBefore.getResult().get(j).getAccountNumber())) {
			        				accountAdd.remove(dataNoCopy.get(i)); 
			        				accountReduce.remove(dataBefore.getResult().get(j));
			        				dataBefore.getResult().remove(j);
			        				break;
			        			}
			        		}
			        	}
		        	}
		        	if(accountAdd.size()>0) {
		        		for(int i=0;i<accountAdd.size();i++) { //修改新增状态
			        		accountAdd.get(i).setAccountStatus("2");
			        	}
		        	}
		        	if(accountReduce.size()>0) {
		        		for(int i=0;i<accountReduce.size();i++) { //修改减少状态
			        		accountReduce.get(i).setAccountStatus("3"); 
			        	}
		        	}
		    		if(dataMonth.getResult().size()>0) {
		    			list.addAll(dataMonth.getResult());
		    		}
		    		//将list的数据存放到account_use_info中
		    		List<AccountUseInfo> accountuseinfo = new ArrayList<AccountUseInfo>();
		    		if(list.size()>0){
		    			//将对比出来的数据添加到account_use_info
		    			for(int i=0;i<list.size();i++) {
		    				AccountUseInfo aui = new AccountUseInfo();
		    				aui.setSerNumber(list.get(i).getSerNumber());
		    				aui.setAddressShi(list.get(i).getAddressShi());
		    				aui.setCompareMonth(month);
		    				aui.setAddressXian(list.get(i).getAddressXian());
		    				aui.setAddressDetail(list.get(i).getAddressDetail());
		    				aui.setAddressCode(list.get(i).getAddressCode());
		    				
		    				aui.setSupplyType(list.get(i).getSupplyType());
		    				aui.setAccountName(list.get(i).getAccountName());
		    				aui.setAccountNumber(list.get(i).getAccountNumber());
		    				aui.setSupplyVoltage(list.get(i).getSupplyVoltage());
		    				aui.setAddressElecuse(list.get(i).getAddressElecuse());
		    				aui.setSupplyUnit(list.get(i).getSupplyUnit());
		    				
		    				aui.setYi(list.get(i).getYi());
		    				aui.setEr(list.get(i).getEr());
		    				aui.setSan(list.get(i).getSan());
		    				aui.setSi(list.get(i).getSi());
		    				aui.setWu(list.get(i).getWu());
		    				aui.setLiu(list.get(i).getLiu());
		    				aui.setQi(list.get(i).getQi());
		    				aui.setBa(list.get(i).getBa());
		    				aui.setJiu(list.get(i).getJiu());
		    				aui.setShi(list.get(i).getShi());
		    				aui.setShiyi(list.get(i).getShiyi());
		    				aui.setShier(list.get(i).getShier());
		    				
		    				aui.setMaker(list.get(i).getMaker());
		    				aui.setAccountStatus(list.get(i).getAccountStatus());
		    				accountuseinfo.add(aui);
		    			}
		    			
		    			
		    		}
		    		//addAll会把id也穿进去，数据库会主键重复
		    		if(accountReduce.size()>0) {
		    			for(int i=0;i<accountReduce.size();i++) {
		    				AccountUseInfo aui = new AccountUseInfo();
		    				aui.setSerNumber(accountReduce.get(i).getSerNumber());
		    				aui.setAddressShi(accountReduce.get(i).getAddressShi());
		    				aui.setCompareMonth(month);
		    				aui.setAddressXian(accountReduce.get(i).getAddressXian());
		    				aui.setAddressDetail(accountReduce.get(i).getAddressDetail());
		    				aui.setAddressCode(accountReduce.get(i).getAddressCode());
		    				
		    				aui.setSupplyType(accountReduce.get(i).getSupplyType());
		    				aui.setAccountName(accountReduce.get(i).getAccountName());
		    				aui.setAccountNumber(accountReduce.get(i).getAccountNumber());
		    				aui.setSupplyVoltage(accountReduce.get(i).getSupplyVoltage());
		    				aui.setAddressElecuse(accountReduce.get(i).getAddressElecuse());
		    				aui.setSupplyUnit(accountReduce.get(i).getSupplyUnit());
		    				
		    				aui.setYi(accountReduce.get(i).getYi());
		    				aui.setEr(accountReduce.get(i).getEr());
		    				aui.setSan(accountReduce.get(i).getSan());
		    				aui.setSi(accountReduce.get(i).getSi());
		    				aui.setWu(accountReduce.get(i).getWu());
		    				aui.setLiu(accountReduce.get(i).getLiu());
		    				aui.setQi(accountReduce.get(i).getQi());
		    				aui.setBa(accountReduce.get(i).getBa());
		    				aui.setJiu(accountReduce.get(i).getJiu());
		    				aui.setShi(accountReduce.get(i).getShi());
		    				aui.setShiyi(accountReduce.get(i).getShiyi());
		    				aui.setShier(accountReduce.get(i).getShier());
		    				
		    				aui.setMaker(accountReduce.get(i).getMaker());
		    				aui.setAccountStatus("3");
		    				accountuseinfo.add(aui);
		    			}
	    			}
	    			accountUseInfoService.saveBatch(accountuseinfo);
		        	
		        }else {
		        	log.info("本月数据为空");
		        }
		        
			}
			
		}else {
			log.info("month不能为空");
		}
		
		Page<AccountUseInfo> page = new Page<AccountUseInfo>(pageNo, pageSize);
		IPage<AccountUseInfo> pageList = accountUseInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/*
	 * 查询所有禁用数据
	 * 
	 * */
	@RequestMapping(value = "/queryDisabled", method = RequestMethod.GET)
	public Result<List<AccountDisabled>> queryDisabled() {
		Result<List<AccountDisabled>> result = new Result<>();
		QueryWrapper<AccountDisabled> queryWrapper = new QueryWrapper<AccountDisabled>();
		List<AccountDisabled> list = accountDisabledService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	/*
	 * 根据create_time查询某时间段内account_info表中的数据，进行对比
	 * 查询所有数据  根据某个状态查询
	 * 
	 * */
	@RequestMapping(value = "/queryAccountInfo", method = RequestMethod.GET)
	public Result<List<AccountInfo>> queryAccountInfo(String addressShi,String time1,String time2) {
		Result<List<AccountInfo>> result = new Result<>();
		QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<AccountInfo>();
		if(oConvertUtils.isNotEmpty(time1)&&oConvertUtils.isNotEmpty(time2)) {
			queryWrapper.between("create_time", time1, time2);
		}
		if(oConvertUtils.isNotEmpty(addressShi)) {
			queryWrapper.eq("address_shi", addressShi);
		}
		
		List<AccountInfo> list = accountInfoService.list(queryWrapper);
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	/*
	 * 根据月份查询该月数据是否为空判断是否进行数据对比
	 * 查询所有数据
	 * 
	 * */
	@RequestMapping(value = "/queryAccountUseInfo", method = RequestMethod.GET)
	public Result<List<AccountUseInfo>> queryAccountUseInfo(String m,Boolean b) {
		Result<List<AccountUseInfo>> result = new Result<>();
		QueryWrapper<AccountUseInfo> queryWrapper = new QueryWrapper<AccountUseInfo>();
		
		if(oConvertUtils.isNotEmpty(m)) {
			if(b) {
				queryWrapper.eq("compare_month", m).and(qw -> qw.eq("account_status", "2").or().isNull("account_status"));
			}else {
				queryWrapper.eq("compare_month", m);
			}
			
		}
		
		List<AccountUseInfo> list = accountUseInfoService.list(queryWrapper);
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
	 * @param accountUseInfo
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-添加")
	@ApiOperation(value="存放对比后的数据-添加", notes="存放对比后的数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AccountUseInfo accountUseInfo) {
		accountUseInfoService.save(accountUseInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param accountUseInfo
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-编辑")
	@ApiOperation(value="存放对比后的数据-编辑", notes="存放对比后的数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AccountUseInfo accountUseInfo) {
		accountUseInfoService.updateById(accountUseInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *  批量编辑
	 *
	 * @param accountInfo
	 * @return
	 */
	@AutoLog(value = "户号信息-编辑")
	@ApiOperation(value="户号信息-编辑", notes="户号信息-编辑")
	@PutMapping(value = "/batchUpdate")
	public Result<?> batchUpdate(@RequestBody List<AccountUseInfo> accountUseInfo) {
		
		accountUseInfoService.updateBatchById(accountUseInfo);
		return Result.ok("编辑成功!");
		
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-通过id删除")
	@ApiOperation(value="存放对比后的数据-通过id删除", notes="存放对比后的数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		accountUseInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-批量删除")
	@ApiOperation(value="存放对比后的数据-批量删除", notes="存放对比后的数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.accountUseInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "存放对比后的数据-通过id查询")
	@ApiOperation(value="存放对比后的数据-通过id查询", notes="存放对比后的数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AccountUseInfo accountUseInfo = accountUseInfoService.getById(id);
		return Result.ok(accountUseInfo);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param accountUseInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, AccountUseInfo accountUseInfo,String m) {
//      return super.exportXls(request, accountUseInfo, AccountUseInfo.class, "存放对比后的数据");
	  	/*重写导出方法，直接给pageList赋值*/
  		// Step.1 组装查询条件
  		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
  		// Step.2 获取导出数据
	  	Result<List<AccountUseInfo>> list = this.queryAccountUseInfo(m, true);
	  	System.out.println("ssssssss");
	  	List<AccountUseInfo> pageList = list.getResult();
	  	// Step.3 AutoPoi 导出Excel
	    ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	    mv.addObject(NormalExcelConstants.FILE_NAME, "户号信息"); //此处设置的filename无效 ,前端会重更新设置一下
	    mv.addObject(NormalExcelConstants.CLASS, AccountUseInfo.class);
	    mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("户号信息" + "报表", "导出人:" + sysUser.getRealname(), "户号信息"));
	    mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
	    return mv;
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
      return super.importExcel(request, response, AccountUseInfo.class);
  }

}
