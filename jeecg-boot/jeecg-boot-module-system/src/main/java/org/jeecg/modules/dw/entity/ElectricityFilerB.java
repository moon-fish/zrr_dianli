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
 * @Description: 电量申报
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("electricity_filer_b")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="electricity_filer_b对象", description="电量申报")
public class ElectricityFilerB implements Serializable {
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
	/**申报企业名称*/
	@Excel(name = "申报企业名称", width = 15)
    @ApiModelProperty(value = "申报企业名称")
    private java.lang.String filerCompanyName;
	/**负荷预测*/
	@Excel(name = "负荷预测", width = 15)
    @ApiModelProperty(value = "负荷预测")
    private java.lang.String loadForecasting;
	/**交易类型*/
	@Excel(name = "交易类型", width = 15)
    @ApiModelProperty(value = "交易类型")
    private java.lang.String transactionType;
	/**月份*/
	@Excel(name = "月份", width = 15)
    @ApiModelProperty(value = "月份")
    private java.lang.String filerMonth;
	/**申报电量*/
	@Excel(name = "申报电量", width = 15)
    @ApiModelProperty(value = "申报电量")
    private java.lang.String marketQuotation;
	/**申报开始时间*/
	@Excel(name = "申报开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申报开始时间")
    private java.util.Date beginDate;
	/**申报结束时间*/
	@Excel(name = "申报结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申报结束时间")
    private java.util.Date endDate;
	/**申报人*/
	@Excel(name = "申报人", width = 15)
    @ApiModelProperty(value = "申报人")
    private java.lang.String filer;
	/**申报人所属公司*/
	@Excel(name = "申报人所属公司", width = 15)
    @ApiModelProperty(value = "申报人所属公司")
    private java.lang.String filerCompany;
	/**申报结果*/
	@Excel(name = "申报结果", width = 15)
    @ApiModelProperty(value = "申报结果")
    private java.lang.String filerResult;
	/**申报状态*/
	@Excel(name = "申报状态", width = 15)
    @ApiModelProperty(value = "申报状态")
    private java.lang.String filerStatus;
	/**日志记录*/
	@Excel(name = "日志记录", width = 15)
    @ApiModelProperty(value = "日志记录")
    private java.lang.String logRecords;
}
