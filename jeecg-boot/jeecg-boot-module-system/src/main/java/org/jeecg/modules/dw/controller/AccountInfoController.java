package org.jeecg.modules.dw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.dw.entity.AccountInfo;
import org.jeecg.modules.dw.entity.AccountUseInfo;
import org.jeecg.modules.dw.entity.BalanceCompany;
import org.jeecg.modules.dw.service.IAccountInfoService;
import org.jeecg.modules.dw.service.IAccountUseInfoService;

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
 * @Description: 户号信息
 * @Author: jeecg-boot
 * @Date:   2020-09-22
 * @Version: V1.0
 */
@Api(tags="户号信息")
@RestController
@RequestMapping("/accountinfo/accountInfo")
@Slf4j
public class AccountInfoController extends JeecgController<AccountInfo, IAccountInfoService> {
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IAccountUseInfoService accountUseInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param accountInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "户号信息-分页列表查询")
	@ApiOperation(value="户号信息-分页列表查询", notes="户号信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AccountInfo accountInfo,String num,String month,String m,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AccountInfo> queryWrapper = QueryGenerator.initQueryWrapper(accountInfo, req.getParameterMap());
		/*
		 * 户号信息
		 * 
		 * */
		//户号信息，点击不同按钮显示不同信息  1：某个地市，新增户号、2：某个地市，减少户号、3：详情，某个地市的所有户号
		if(oConvertUtils.isNotEmpty(accountInfo.getAddressShi())) {
			queryWrapper.eq("address_shi", accountInfo.getAddressShi());
		}
		//根据num 的值判断其新增减少用户
		if(oConvertUtils.isNotEmpty(num)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-14 00:00:00");
			Calendar cal = Calendar.getInstance();
			Date d = new Date();
			d.setMonth(Integer.parseInt(m)-1);
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
	        
			Result<List<AccountInfo>> r9 = this.queryall(accountInfo.getAddressShi(),monthTwoBefore, monthOneBefore,"",true);
			Result<List<AccountInfo>> r8 = this.queryall(accountInfo.getAddressShi(),monthThreeBefore, monthTwoBefore,"",true);
			//存放r8户号
			List<String> list = new ArrayList<String>();
			for(int i=0;i<r8.getResult().size();i++) {
				list.add(r8.getResult().get(i).getAccountNumber());
			}
			//存放r9户号
			List<String> list2 = new ArrayList<String>();
			for(int i=0;i<r9.getResult().size();i++) {
				list2.add(r9.getResult().get(i).getAccountNumber());
			}
			//新增户号
			if(num.equals("1")) {
				//r9户号去除跟r8重复的户号，得到新增户号
				for(int j=0;j<r8.getResult().size();j++) {
					if(list2.indexOf(r8.getResult().get(j).getAccountNumber()) != -1) {
						list2.remove(r8.getResult().get(j).getAccountNumber());
					}
				}
				queryWrapper.in("account_number", list2);
			}else{
				//r8户号去除跟r9重复的户号，得到减少户号
				for(int j=0;j<r9.getResult().size();j++) {
					if(list.indexOf(r9.getResult().get(j).getAccountNumber()) != -1) {
						list.remove(r9.getResult().get(j).getAccountNumber());
					}
				}
				queryWrapper.in("account_number", list);
			} 
		}
		/*
		 * 户号审核
		 * 
		 * */
		List<AccountInfo> list = new ArrayList<AccountInfo>();
		long start = System.currentTimeMillis();
		if(oConvertUtils.isNotEmpty(month)) {
			/*
			 * 1.根据选择的月份计算上个月13号到上上个月13号的时间 --》
			 * 2.查询出这个时间段的数据 --》 
			 * 3.筛选
			 **/
			//1.
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
	        //2.
	        Result<List<AccountInfo>> dataMonth = this.queryall("",monthTwoBefore, monthOneBefore,"",true);
	        List<AccountInfo> dataMonthCopy = new ArrayList<AccountInfo>();
	        System.out.println("22222222222222222");
	        //3.
	        Result<List<AccountInfo>> dataDisabled = this.queryall("","", "","4",false);
	        System.out.println("333333333333333333333");
	        List<AccountInfo> dataCf = new ArrayList<AccountInfo>();
	        if(dataMonth.isSuccess()) {
	        	//1.筛选禁用数据 与原始禁用数据（状态为4）进行对比，若户号一直，剔除该户号，并修改该户号状态为1
		        dataMonthCopy.addAll(dataMonth.getResult());
	        	for(int i=0;i<dataMonth.getResult().size();i++) {
		        	for(int j=0;j<dataDisabled.getResult().size();j++) {
		        		if(dataMonth.getResult().get(i).getAccountNumber().equals(dataDisabled.getResult().get(j).getAccountNumber())) {
		        			dataMonth.getResult().get(i).setAccountStatus("0");
		        			dataCf.add(dataMonth.getResult().get(i));
		        			dataMonthCopy.remove(dataMonth.getResult().get(i));
		        		}
		        	}
		        }
	        	if(dataCf.size()>0) {
	        		accountInfoService.updateBatchById(dataCf);
	        	}
	        	System.out.println("444444444444444");
//	        	System.out.println(dataMonthCopy);
	        	//2.筛选选择月的重复数据
//	        	List<String> accountNum = new ArrayList<String>(); //存放提取出来的不重复的户号
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
	        	System.out.println(accountNum2);
//	        	for(int i=0;i<dataMonthCopy.size();i++) {
//	        		
//	        		System.out.println(accountNum.indexOf(dataMonthCopy.get(i).getAccountNumber()));
//	        		 if(accountNum.indexOf(dataMonthCopy.get(i).getAccountNumber()) == -1) {
//	        			 accountNum.add(dataMonthCopy.get(i).getAccountNumber());
//	        		 }else {
//	        			 if(accountNum2.indexOf(dataMonthCopy.get(i).getAccountNumber()) == -1) { //去重
//	        				 accountNum2.add(dataMonthCopy.get(i).getAccountNumber());
//	        			 }
//	        		 }
//	        		 
//	        	}
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
	        	System.out.println("55555555555555555555");
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
		        	if(dataCopy.size()>0) {
		        		accountInfoService.updateBatchById(dataCopy);
		        	}
		        	
	        	}else {
	        		dataNoCopy.addAll(dataMonthCopy);
	        	}
	        	
	        	System.out.println("666666666666666666666");
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
	        	//3.把不重复的数据dataNoCopy与上个月的数据进行对比，筛选出新增和减少的
	        	Result<List<AccountInfo>> dataBefore = this.queryall2(monthThreeBefore, monthTwoBefore,false);
	        	List<AccountInfo> accountAdd = new ArrayList<AccountInfo>();
	        	List<AccountInfo> accountReduce = new ArrayList<AccountInfo>();
	        	accountAdd.addAll(dataNoCopy); //选择月数据
	        	System.out.println("77777777777777777777");
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");	
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
	        	
	        	System.out.println("8888888888888888888888");
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
	        	if(accountAdd.size()>0) {
	        		for(int i=0;i<accountAdd.size();i++) { //修改新增状态
		        		accountAdd.get(i).setAccountStatus("2");
		        	}
		        	accountInfoService.updateBatchById(accountAdd,5000); //新增批量修改
	        	}
	        	System.out.println("99999999999999993332222222");
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
	        	if(accountReduce.size()>0) {
	        		for(int i=0;i<accountReduce.size();i++) { //修改减少状态
		        		accountReduce.get(i).setAccountStatus("3"); 
		        	}
		        	accountInfoService.updateBatchById(accountReduce,5000); //减少批量修改
	        	}
	        	System.out.println("kkkkkkkkkkkkkkkkkkkkk");
	        	log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
//	        	if(num) {
//	        		
//	        	}
	    		if(accountReduce.size()>0) {
	    			list.addAll(accountReduce);
	    		}
	    		if(dataMonth.getResult().size()>0) {
	    			list.addAll(dataMonth.getResult());
	    		}
	    		System.out.println("bbbbbbbbbbbbbbbbb");
	    		log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
	        }
	        
	        
		}
		List<String> ids = new ArrayList<String>();
		List<AccountUseInfo> accountUseInfo = new ArrayList<AccountUseInfo>();
		if(list.size()>0){
			for(AccountInfo a:list) {
				ids.add(a.getId());
			}
			queryWrapper.in("id", ids);
			//将对比出来的数据添加到account_use_info
			for(int i=0;i<list.size();i++) {
				AccountUseInfo aui = new AccountUseInfo();
				aui.setId(list.get(i).getId());
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
				aui.setIsUseful(list.get(i).getIsUseful());
				accountUseInfo.add(aui);
			}
			System.out.println("123456789");
			accountUseInfoService.saveBatch(accountUseInfo);
			
		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxx");
		log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
		Page<AccountInfo> page = new Page<AccountInfo>(pageNo, pageSize);
		IPage<AccountInfo> pageList = accountInfoService.page(page, queryWrapper);
//		pageList.setRecords(list);
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
		return Result.ok(pageList);
	}
	
	//查询所有数据  根据某个状态查询
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<AccountInfo>> queryall(String addressShi,String time1,String time2,String statusNum,Boolean isUseFul) {
		Result<List<AccountInfo>> result = new Result<>();
		QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<AccountInfo>();
		if(oConvertUtils.isNotEmpty(time1)&&oConvertUtils.isNotEmpty(time2)) {
//			queryWrapper.between("create_time", time1, time2);
			if(isUseFul) {
//				queryWrapper.eq("is_useful", "2").or().isNull("is_useful").or().eq("is_useful", "3");
				queryWrapper.between("create_time", time1, time2).and(qw -> qw.eq("is_useful", "2").or().isNull("is_useful").or().eq("is_useful", "3"));
			}
		}
		if(oConvertUtils.isNotEmpty(addressShi)) {
			queryWrapper.eq("address_shi", addressShi);
		}
		if(oConvertUtils.isNotEmpty(statusNum)) {
			queryWrapper.eq("account_status", statusNum);
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
	
	//查询所有数据  查询上个月的可用值（如果isUse为true为可用2，null，否则为不可用0,1,3）
	@RequestMapping(value = "/queryall2", method = RequestMethod.GET)
	public Result<List<AccountInfo>> queryall2(String time1,String time2,Boolean isUse) {
		Result<List<AccountInfo>> result = new Result<>();
		QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<AccountInfo>();
		if(oConvertUtils.isNotEmpty(time1)&&oConvertUtils.isNotEmpty(time2)) {
			
			List<String> str = new ArrayList<String>();
			str.add("1");
			str.add("0");
			if(isUse) {
				queryWrapper.in("account_status", str);
//				queryWrapper.eq("account_status", "");
			}else {
				queryWrapper.between("create_time", time1, time2).and(qw -> qw.eq("account_status", "2").or().isNull("account_status").or().eq("account_status", "3"));	
			}
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
	
	/**
	 *   添加
	 *
	 * @param accountInfo
	 * @return
	 */
	@AutoLog(value = "户号信息-添加")
	@ApiOperation(value="户号信息-添加", notes="户号信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AccountInfo accountInfo) {
		accountInfoService.save(accountInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param accountInfo
	 * @return
	 */
	@AutoLog(value = "户号信息-编辑")
	@ApiOperation(value="户号信息-编辑", notes="户号信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AccountInfo accountInfo) {
		accountInfoService.updateById(accountInfo);
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
	public Result<?> batchUpdate(@RequestBody List<AccountInfo> accountInfo) {
		
		accountInfoService.updateBatchById(accountInfo);
		return Result.ok("编辑成功!");
		
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "户号信息-通过id删除")
	@ApiOperation(value="户号信息-通过id删除", notes="户号信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		accountInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "户号信息-批量删除")
	@ApiOperation(value="户号信息-批量删除", notes="户号信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.accountInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "户号信息-通过id查询")
	@ApiOperation(value="户号信息-通过id查询", notes="户号信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AccountInfo accountInfo = accountInfoService.getById(id);
		if(accountInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(accountInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param accountInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AccountInfo accountInfo,String m) {
    	System.out.println("ppppppppp");
    	//        return super.exportXls(request, accountInfo, AccountInfo.class, "户号信息");
    	/*重写导出方法，直接给pageList赋值*/
    	// Step.1 组装查询条件
    	LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
    	// Step.2 获取导出数据
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-14 00:00:00");
		Calendar cal = Calendar.getInstance();
		Date d = new Date();
		d.setMonth(Integer.parseInt(m)-1);
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);
		Date m1 = cal.getTime();
        String monthOneBefore = format.format(m1);
        cal.add(Calendar.MONTH, -1);
		Date m2 = cal.getTime();
        String monthTwoBefore = format.format(m2);
        System.out.println(monthOneBefore);
        System.out.println(monthTwoBefore);
    	Result<List<AccountInfo>> list = this.queryall2(monthTwoBefore, monthOneBefore, false);
    	System.out.println("ssssssss");
    	List<AccountInfo> pageList = list.getResult();
    
//        List<AccountInfo> exportList = null;
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "户号信息"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, AccountInfo.class);
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
        return super.importExcel(request, response, AccountInfo.class);
    }

}
