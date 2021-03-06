package org.jeecg.modules.dw.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 用户信息导出包含分月电量初始信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("user_info_export")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="user_info_export对象", description="用户信息导出包含分月电量初始信息")
public class UserInfoExport {
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**创建人*/
//	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
//	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
//	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
//	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
//	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**企业全称*/
	@Excel(name = "企业全称", width = 15)
    @ApiModelProperty(value = "企业全称")
	private java.lang.String comAName;
	/**企业简称*/
//	@Excel(name = "企业简称", width = 15)
    @ApiModelProperty(value = "企业简称")
	private java.lang.String comSName;
	/**地域信息*/
	@Excel(name = "地域信息", width = 15)
    @ApiModelProperty(value = "地域信息")
	private java.lang.String addressInfo;
	/**行业类别*/
//	@Excel(name = "行业类别", width = 15)
    @ApiModelProperty(value = "行业类别")
	private java.lang.String industry;
	/**企业所在工业园区*/
//	@Excel(name = "企业所在工业园区", width = 15)
    @ApiModelProperty(value = "企业所在工业园区")
	private java.lang.String industrialSite;
	/**供电区域*/
//	@Excel(name = "供电区域", width = 15)
    @ApiModelProperty(value = "供电区域")
	private java.lang.String supplyArea;
	/**通讯地址*/
//	@Excel(name = "通讯地址", width = 15)
    @ApiModelProperty(value = "通讯地址")
	private java.lang.String address;
	/**邮政编码*/
//	@Excel(name = "邮政编码", width = 15)
    @ApiModelProperty(value = "邮政编码")
	private java.lang.String email;
	/**联系人姓名*/
	@Excel(name = "联系人姓名", width = 15)
    @ApiModelProperty(value = "联系人姓名")
	private java.lang.String name;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    @ApiModelProperty(value = "手机号码")
	private java.lang.String phone;
	/**办公电话*/
//	@Excel(name = "办公电话", width = 15)
    @ApiModelProperty(value = "办公电话")
	private java.lang.String officePhone;
	/**联系人职务*/
//	@Excel(name = "联系人职务", width = 15)
    @ApiModelProperty(value = "联系人职务")
	private java.lang.String duty;
	/**传真*/
//	@Excel(name = "传真", width = 15)
    @ApiModelProperty(value = "传真")
	private java.lang.String ems;
	/**网址*/
//	@Excel(name = "网址", width = 15)
    @ApiModelProperty(value = "网址")
	private java.lang.String internet;
	/**统一社会信用代码*/
//	@Excel(name = "统一社会信用代码", width = 15)
    @ApiModelProperty(value = "统一社会信用代码")
	private java.lang.String socialCredit;
	/**成立日期*/
//	@Excel(name = "成立日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "成立日期")
	private java.util.Date establishment;
	/**法人名称*/
//	@Excel(name = "法人名称", width = 15)、
    @ApiModelProperty(value = "法人名称")
	private java.lang.String legalName;
	/**企业注册地址*/
//	@Excel(name = "企业注册地址", width = 15)
    @ApiModelProperty(value = "企业注册地址")
	private java.lang.String registeredAddress;
	/**法定代表人姓名*/
//	@Excel(name = "法定代表人姓名", width = 15)
    @ApiModelProperty(value = "法定代表人姓名")
	private java.lang.String legalRepresentativeName;
	/**组织机构代码*/
//	@Excel(name = "组织机构代码", width = 15)
    @ApiModelProperty(value = "组织机构代码")
	private java.lang.String orgCode;
	/**开户银行*/
//	@Excel(name = "开户银行", width = 15)
    @ApiModelProperty(value = "开户银行")
	private java.lang.String depositBank;
	/**开户名称*/
//	@Excel(name = "开户名称", width = 15)
    @ApiModelProperty(value = "开户名称")
	private java.lang.String depositName;
	/**开户账号*/
//	@Excel(name = "开户账号", width = 15)
    @ApiModelProperty(value = "开户账号")
	private java.lang.String depoaitAccount;
	/**税务登记证号*/
//	@Excel(name = "税务登记证号", width = 15)
    @ApiModelProperty(value = "税务登记证号")
	private java.lang.String registerNumber;
	/**注册资本*/
//	@Excel(name = "注册资本", width = 15)
    @ApiModelProperty(value = "注册资本")
	private java.math.BigDecimal registerMoney;
	/**资产总额*/
//	@Excel(name = "资产总额", width = 15)
    @ApiModelProperty(value = "资产总额")
	private java.math.BigDecimal totalAssets;
	/**电力业务许可证编号*/
//	@Excel(name = "电力业务许可证编号", width = 15)
    @ApiModelProperty(value = "电力业务许可证编号")
	private java.lang.String licenceNo;
	/**许可证生效日期*/
//	@Excel(name = "许可证生效日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "许可证生效日期")
	private java.util.Date effectiveDate;
	/**许可证失效日期*/
//	@Excel(name = "许可证失效日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "许可证失效日期")
	private java.util.Date expiryDate;
	/**合同容量（MVA）*/
//	@Excel(name = "合同容量（MVA）", width = 15)
    @ApiModelProperty(value = "合同容量（MVA）")
	private java.lang.String contractCapacity;
	/**运行容量（MVA）*/
//	@Excel(name = "运行容量（MVA）", width = 15)
    @ApiModelProperty(value = "运行容量（MVA）")
	private java.lang.String operatingCapacity;
	/**最高供电电压*/
//	@Excel(name = "最高供电电压", width = 15)
    @ApiModelProperty(value = "最高供电电压")
	private java.lang.Integer maxVoltage;
	/**月度用电信息*/
//	@Excel(name = "月度用电信息", width = 15)
    @ApiModelProperty(value = "月度用电信息")
	private java.math.BigDecimal monthElecInfo;
	/**年度用电信息*/
//	@Excel(name = "年度用电信息", width = 15)
    @ApiModelProperty(value = "年度用电信息")
	private java.math.BigDecimal yearElecInfo;
	/**合同有效期*/
//	@Excel(name = "合同有效期", width = 15)
    @ApiModelProperty(value = "合同有效期")
	private java.lang.String effective;
	/**合同质量*/
//	@Excel(name = "合同质量", width = 15)
    @ApiModelProperty(value = "合同质量")
	private java.lang.String cusQuality;
	/**用户类型*/
//	@Excel(name = "用户类型", width = 15)
    @ApiModelProperty(value = "用户类型")
	private java.lang.String userType;
	/**电压等级*/
//	@Excel(name = "电压等级", width = 15)
    @ApiModelProperty(value = "电压等级")
	private java.lang.String voltageLevel;
	/**用户序列*/
//	@Excel(name = "用户序列", width = 15)
    @ApiModelProperty(value = "用户序列")
	private java.lang.String userOrder;
	/**一月*/
	@Excel(name = "一月", width = 15)
    @ApiModelProperty(value = "一月")
	private java.lang.String yi;
	/**二月*/
	@Excel(name = "二月", width = 15)
    @ApiModelProperty(value = "二月")
	private java.lang.String er;
	/**三月*/
	@Excel(name = "三月", width = 15)
    @ApiModelProperty(value = "三月")
	private java.lang.String san;
	/**四月*/
	@Excel(name = "四月", width = 15)
    @ApiModelProperty(value = "四月")
	private java.lang.String si;
	/**五月*/
	@Excel(name = "五月", width = 15)
    @ApiModelProperty(value = "五月")
	private java.lang.String wu;
	/**六月*/
	@Excel(name = "六月", width = 15)
    @ApiModelProperty(value = "六月")
	private java.lang.String liu;
	/**七月*/
	@Excel(name = "七月", width = 15)
    @ApiModelProperty(value = "七月")
	private java.lang.String qi;
	/**八月*/
	@Excel(name = "八月", width = 15)
    @ApiModelProperty(value = "八月")
	private java.lang.String ba;
	/**九月*/
	@Excel(name = "九月", width = 15)
    @ApiModelProperty(value = "九月")
	private java.lang.String jiu;
	/**十月*/
	@Excel(name = "十月", width = 15)
    @ApiModelProperty(value = "十月")
	private java.lang.String shi;
	/**十一月*/
	@Excel(name = "十一月", width = 15)
    @ApiModelProperty(value = "十一月")
	private java.lang.String shiyi;
	/**十二月*/
	@Excel(name = "十二月", width = 15)
    @ApiModelProperty(value = "十二月")
	private java.lang.String shier;
	/**备用*/
	@Excel(name = "年用电量", width = 15)
    @ApiModelProperty(value = "年用电量")
	private java.lang.String byzd;
	/**备用*/
	@Excel(name = "购电价", width = 15)
    @ApiModelProperty(value = "购电价")
	private java.lang.String purchasePrice;
}
