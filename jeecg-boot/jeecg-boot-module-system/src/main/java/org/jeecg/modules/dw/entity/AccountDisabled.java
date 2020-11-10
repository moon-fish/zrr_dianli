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
 * @Description: 户号禁用数据
 * @Author: jeecg-boot
 * @Date:   2020-10-21
 * @Version: V1.0
 */
@Data
@TableName("account_disabled")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="account_disabled对象", description="户号禁用数据")
public class AccountDisabled {
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
	private java.lang.String sysOrgCode;
	/**序号*/
	@Excel(name = "序号", width = 15)
    @ApiModelProperty(value = "序号")
	private java.lang.String serNumber;
	/**地市*/
	@Excel(name = "地市", width = 15)
    @ApiModelProperty(value = "地市")
	private java.lang.String addressShi;
	/**区县*/
	@Excel(name = "区县", width = 15)
    @ApiModelProperty(value = "区县")
	private java.lang.String addressXian;
	/**站址名称*/
	@Excel(name = "站址名称", width = 15)
    @ApiModelProperty(value = "站址名称")
	private java.lang.String addressDetail;
	/**站址编码*/
	@Excel(name = "站址编码", width = 15)
    @ApiModelProperty(value = "站址编码")
	private java.lang.String addressCode;
	/**供电方式*/
	@Excel(name = "供电方式", width = 15)
    @ApiModelProperty(value = "供电方式")
	private java.lang.String supplyType;
	/**户名*/
	@Excel(name = "户名", width = 15)
    @ApiModelProperty(value = "户名")
	private java.lang.String accountName;
	/**户号*/
	@Excel(name = "户号", width = 15)
    @ApiModelProperty(value = "户号")
	private java.lang.String accountNumber;
	/**计量点供电电压*/
	@Excel(name = "计量点供电电压", width = 15)
    @ApiModelProperty(value = "计量点供电电压")
	private java.lang.String supplyVoltage;
	/**用电地址*/
	@Excel(name = "用电地址", width = 15)
    @ApiModelProperty(value = "用电地址")
	private java.lang.String addressElecuse;
	/**供电单位*/
	@Excel(name = "供电单位", width = 15)
    @ApiModelProperty(value = "供电单位")
	private java.lang.String supplyUnit;
	/**一月*/
	@Excel(name = "一月", width = 15)
    @ApiModelProperty(value = "一月")
	private java.math.BigDecimal yi;
	/**二月*/
	@Excel(name = "二月", width = 15)
    @ApiModelProperty(value = "二月")
	private java.math.BigDecimal er;
	/**三月*/
	@Excel(name = "三月", width = 15)
    @ApiModelProperty(value = "三月")
	private java.math.BigDecimal san;
	/**四月*/
	@Excel(name = "四月", width = 15)
    @ApiModelProperty(value = "四月")
	private java.math.BigDecimal si;
	/**五月*/
	@Excel(name = "五月", width = 15)
    @ApiModelProperty(value = "五月")
	private java.math.BigDecimal wu;
	/**六月*/
	@Excel(name = "六月", width = 15)
    @ApiModelProperty(value = "六月")
	private java.math.BigDecimal liu;
	/**七月*/
	@Excel(name = "七月", width = 15)
    @ApiModelProperty(value = "七月")
	private java.math.BigDecimal qi;
	/**八月*/
	@Excel(name = "八月", width = 15)
    @ApiModelProperty(value = "八月")
	private java.math.BigDecimal ba;
	/**九月*/
	@Excel(name = "九月", width = 15)
    @ApiModelProperty(value = "九月")
	private java.math.BigDecimal jiu;
	/**十月*/
	@Excel(name = "十月", width = 15)
    @ApiModelProperty(value = "十月")
	private java.math.BigDecimal shi;
	/**十一月*/
	@Excel(name = "十一月", width = 15)
    @ApiModelProperty(value = "十一月")
	private java.math.BigDecimal shiyi;
	/**十二月*/
	@Excel(name = "十二月", width = 15)
    @ApiModelProperty(value = "十二月")
	private java.math.BigDecimal shier;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private java.lang.String maker;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private java.lang.String accountStatus;
	/**是否可用*/
	@Excel(name = "是否可用", width = 15)
    @ApiModelProperty(value = "是否可用")
	private java.lang.String isUseful;
}
