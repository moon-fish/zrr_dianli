package org.jeecg.modules.dw.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 户号管理
 * @Author: jeecg-boot
 * @Date:   2020-09-22
 * @Version: V1.0
 */
@Data
@TableName("account")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="account对象", description="户号管理")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
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
	/**地市*/
	@Excel(name = "地市", width = 15)
    @ApiModelProperty(value = "地市")
    private java.lang.String address;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date dateBegin;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date dateEnd;
	/**供电单位*/
	@Excel(name = "供电单位", width = 15)
    @ApiModelProperty(value = "供电单位")
    private java.lang.String elecApply;
	/**户号数量*/
	@Excel(name = "户号数量", width = 15)
    @ApiModelProperty(value = "户号数量")
    private java.lang.Integer accountNumber;
	/**新增用户数量*/
	@Excel(name = "新增用户数量", width = 15)
    @ApiModelProperty(value = "新增用户数量")
    private java.lang.Integer addNumber;
	/**减少用户数量*/
	@Excel(name = "减少用户数量", width = 15)
    @ApiModelProperty(value = "减少用户数量")
    private java.lang.Integer reduceNumber;
	/**新增用户电量*/
	@Excel(name = "新增用户电量", width = 15)
    @ApiModelProperty(value = "新增用户电量")
    private java.math.BigDecimal addElec;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String marker;
	/**月份*/
    @ApiModelProperty(value = "月份")
    private java.lang.String month;
}
