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
 * @Description: 售电公司结算
 * @Author: jeecg-boot
 * @Date:   2020-06-11
 * @Version: V1.0
 */
@Data
@TableName("balance_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="balance_company对象", description="售电公司结算")
public class BalanceCompany implements Serializable {
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
    private java.lang.String userName;
	/**批发成交电量*/
	@Excel(name = "批发成交电量", width = 15)
    @ApiModelProperty(value = "批发成交电量")
    private java.math.BigDecimal sucElec;
	/**成交均价*/
	@Excel(name = "成交均价", width = 15)
    @ApiModelProperty(value = "成交均价")
    private java.math.BigDecimal sucPrice;
	/**批发结算电费*/
	@Excel(name = "批发结算电费", width = 15)
    @ApiModelProperty(value = "批发结算电费")
    private java.math.BigDecimal sucMoney;
	/**实际用电量*/
	@Excel(name = "实际用电量", width = 15)
    @ApiModelProperty(value = "实际用电量")
    private java.math.BigDecimal cusElec;
	/**协议电价*/
	@Excel(name = "协议电价", width = 15)
    @ApiModelProperty(value = "协议电价")
    private java.math.BigDecimal cusPrice;
	/**零售结算电费*/
	@Excel(name = "零售结算电费", width = 15)
    @ApiModelProperty(value = "零售结算电费")
    private java.math.BigDecimal cusMoney;
	/**偏差考核电量*/
	@Excel(name = "偏差考核电量", width = 15)
    @ApiModelProperty(value = "偏差考核电量")
    private java.math.BigDecimal allOffsetElec;
	/**偏差考核电费*/
	@Excel(name = "偏差考核电费", width = 15)
    @ApiModelProperty(value = "偏差考核电费")
    private java.math.BigDecimal allOffsetMoney;
	/**用户偏差电量*/
	@Excel(name = "用户偏差电量", width = 15)
    @ApiModelProperty(value = "用户偏差电量")
    private java.math.BigDecimal cusOffsetElec;
	/**用户偏差电费*/
	@Excel(name = "用户偏差电费", width = 15)
    @ApiModelProperty(value = "用户偏差电费")
    private java.math.BigDecimal cusOffsetMoney;
	/**售电公司结算电量*/
	@Excel(name = "售电公司结算电量", width = 15)
    @ApiModelProperty(value = "售电公司结算电量")
    private java.math.BigDecimal sucBalanceElec;
	/**售电公司结算电费*/
	@Excel(name = "售电公司结算电费", width = 15)
    @ApiModelProperty(value = "售电公司结算电费")
    private java.math.BigDecimal sucBalanceMoney;
	/**售电公司偏差电费*/
	@Excel(name = "售电公司偏差电费", width = 15)
    @ApiModelProperty(value = "售电公司偏差电费")
    private java.math.BigDecimal sucOffsetMoney;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String market;
}
