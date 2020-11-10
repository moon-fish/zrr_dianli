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
 * @Description: 分月电量
 * @Author: jeecg-boot
 * @Date:   2020-04-29
 * @Version: V1.0
 */
@Data
@TableName("momth_elec")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="momth_elec对象", description="分月电量")
public class MomthElec implements Serializable {
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
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String stat;
	/**备用*/
	@Excel(name = "备用", width = 15)
    @ApiModelProperty(value = "备用")
    private java.lang.String byzd;
	/**次数*/
	@Excel(name = "次数", width = 15)
    @ApiModelProperty(value = "次数")
    private java.lang.Integer updateNum;
	
}
