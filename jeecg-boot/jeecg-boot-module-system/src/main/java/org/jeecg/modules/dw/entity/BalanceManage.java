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
 * @Description: 结算管理
 * @Author: jeecg-boot
 * @Date:   2020-05-19
 * @Version: V1.0
 */
@Data
@TableName("balance_manage")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="balance_manage对象", description="结算管理")
public class BalanceManage implements Serializable {
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
	/**用户名称*/
	@Excel(name = "用户名称", width = 15)
    @ApiModelProperty(value = "用户名称")
    private java.lang.String username;
	/**电量*/
	@Excel(name = "电量", width = 15)
    @ApiModelProperty(value = "电量")
    private java.math.BigDecimal elecNum;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal elecPrice;
	/**电费*/
	@Excel(name = "电费", width = 15)
    @ApiModelProperty(value = "电费")
    private java.math.BigDecimal elecCharge;
	/**节省*/
	@Excel(name = "节省", width = 15)
    @ApiModelProperty(value = "节省")
    private java.math.BigDecimal elecSave;
	/**费用月份*/
	@Excel(name = "费用月份", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "费用月份")
    private java.util.Date monthDate;
	/**费用年份*/
	@Excel(name = "费用年份", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "费用年份")
    private java.util.Date yearDate;
	/**考核电量*/
	@Excel(name = "考核电量", width = 15)
    @ApiModelProperty(value = "考核电量")
    private java.lang.String byzd;
	/**考核电费*/
	@Excel(name = "考核电费", width = 15)
    @ApiModelProperty(value = "考核电费")
    private java.lang.String byzdt;
	/**区域*/
	@Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
    private java.lang.String area;
	/**申报电量*/
	@Excel(name = "申报电量", width = 15)
    @ApiModelProperty(value = "申报电量")
	private java.math.BigDecimal elecFiler;
	/**负偏*/
	@Excel(name = "负偏", width = 15)
    @ApiModelProperty(value = "负偏")
    private java.lang.String percent;
}
