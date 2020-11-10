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
 * @Description: 发电侧信息
 * @Author: jeecg-boot
 * @Date:   2020-04-26
 * @Version: V1.0
 */
@Data
@TableName("generation_side_info_b")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="generation_side_info_b对象", description="发电侧信息")
public class GenerationSideInfoB implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
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
	/**电站名称*/
	@Excel(name = "电站名称", width = 15)
    @ApiModelProperty(value = "电站名称")
    private java.lang.String stationName;
	/**装机容量*/
	@Excel(name = "装机容量", width = 15)
    @ApiModelProperty(value = "装机容量")
    private java.lang.String installedCapacity;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String address;
	/**离市区距离*/
	@Excel(name = "离市区距离", width = 15)
    @ApiModelProperty(value = "离市区距离")
    private java.math.BigDecimal distance;
	/**联系人姓名*/
	@Excel(name = "联系人姓名", width = 15)
    @ApiModelProperty(value = "联系人姓名")
    private java.lang.String name;
	/**联系人职务*/
	@Excel(name = "联系人职务", width = 15)
    @ApiModelProperty(value = "联系人职务")
    private java.lang.String duty;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
    @ApiModelProperty(value = "手机号码")
    private java.lang.String phone;
	/**办公电话*/
	@Excel(name = "办公电话", width = 15)
    @ApiModelProperty(value = "办公电话")
    private java.lang.String officePhone;
	/**总装机容量*/
	@Excel(name = "总装机容量", width = 15)
    @ApiModelProperty(value = "总装机容量")
    private java.lang.String installedCapacityAll;
	/**机组类型*/
	@Excel(name = "机组类型", width = 15)
    @ApiModelProperty(value = "机组类型")
    private java.lang.String unitType;
}
