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
 * @Description: 负荷数据
 * @Author: jeecg-boot
 * @Date:   2020-06-03
 * @Version: V1.0
 */
@Data
@TableName("hisload")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="hisload对象", description="负荷数据")
public class Hisload {
    
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
	/**用电单元名称*/
	@Excel(name = "负荷单元名称", width = 15)
    @ApiModelProperty(value = "用电单元名称")
	private java.lang.String usernameUnit;
	/**时间*/
	@Excel(name = "时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "时间")
	private java.util.Date elecDate;
	/**0:00:00*/
	@Excel(name = "0:00:00", width = 15)
    @ApiModelProperty(value = "0:00:00")
	private java.math.BigDecimal ldll;
	/**0:15:00*/
	@Excel(name = "0:15:00", width = 15)
    @ApiModelProperty(value = "0:15:00")
	private java.math.BigDecimal ldyw;
	/**0:30:00*/
	@Excel(name = "0:30:00", width = 15)
    @ApiModelProperty(value = "0:30:00")
	private java.math.BigDecimal ldss;
	/**0:45:00*/
	@Excel(name = "0:45:00", width = 15)
    @ApiModelProperty(value = "0:45:00")
	private java.math.BigDecimal ldsw;
	/**1:00:00*/
	@Excel(name = "1:00:00", width = 15)
    @ApiModelProperty(value = "1:00:00")
	private java.math.BigDecimal ydll;
	/**1:15:00*/
	@Excel(name = "1:15:00", width = 15)
    @ApiModelProperty(value = "1:15:00")
	private java.math.BigDecimal ydyw;
	/**1:30:00*/
	@Excel(name = "1:30:00", width = 15)
    @ApiModelProperty(value = "1:30:00")
	private java.math.BigDecimal ydss;
	/**1:45:00*/
	@Excel(name = "1:45:00", width = 15)
    @ApiModelProperty(value = "1:45:00")
	private java.math.BigDecimal ydsw;
	/**2:00:00*/
	@Excel(name = "2:00:00", width = 15)
    @ApiModelProperty(value = "2:00:00")
	private java.math.BigDecimal edll;
	/**2:15:00*/
	@Excel(name = "2:15:00", width = 15)
    @ApiModelProperty(value = "2:15:00")
	private java.math.BigDecimal edyw;
	/**2:30:00*/
	@Excel(name = "2:30:00", width = 15)
    @ApiModelProperty(value = "2:30:00")
	private java.math.BigDecimal edss;
	/**2:45:00*/
	@Excel(name = "2:45:00", width = 15)
    @ApiModelProperty(value = "2:45:00")
	private java.math.BigDecimal edsw;
	/**3:00:00*/
	@Excel(name = "3:00:00", width = 15)
    @ApiModelProperty(value = "3:00:00")
	private java.math.BigDecimal sdll;
	/**3:15:00*/
	@Excel(name = "3:15:00", width = 15)
    @ApiModelProperty(value = "3:15:00")
	private java.math.BigDecimal sdyw;
	/**3:30:00*/
	@Excel(name = "3:30:00", width = 15)
    @ApiModelProperty(value = "3:30:00")
	private java.math.BigDecimal sdss;
	/**3:45:00*/
	@Excel(name = "3:45:00", width = 15)
    @ApiModelProperty(value = "3:45:00")
	private java.math.BigDecimal sssw;
	/**4:00:00*/
	@Excel(name = "4:00:00", width = 15)
    @ApiModelProperty(value = "4:00:00")
	private java.math.BigDecimal sidll;
	/**4:15:00*/
	@Excel(name = "4:15:00", width = 15)
    @ApiModelProperty(value = "4:15:00")
	private java.math.BigDecimal sidyw;
	/**4:30:00*/
	@Excel(name = "4:30:00", width = 15)
    @ApiModelProperty(value = "4:30:00")
	private java.math.BigDecimal sidss;
	/**4:45:00*/
	@Excel(name = "4:45:00", width = 15)
    @ApiModelProperty(value = "4:45:00")
	private java.math.BigDecimal sidsw;
	/**5:00:00*/
	@Excel(name = "5:00:00", width = 15)
    @ApiModelProperty(value = "5:00:00")
	private java.math.BigDecimal wdll;
	/**5:15:00*/
	@Excel(name = "5:15:00", width = 15)
    @ApiModelProperty(value = "5:15:00")
	private java.math.BigDecimal wdyw;
	/**5:30:00*/
	@Excel(name = "5:30:00", width = 15)
    @ApiModelProperty(value = "5:30:00")
	private java.math.BigDecimal wdss;
	/**5:45:00*/
	@Excel(name = "5:45:00", width = 15)
    @ApiModelProperty(value = "5:45:00")
	private java.math.BigDecimal wdsw;
	/**6:00:00*/
	@Excel(name = "6:00:00", width = 15)
    @ApiModelProperty(value = "6:00:00")
	private java.math.BigDecimal liudll;
	/**6:15:00*/
	@Excel(name = "6:15:00", width = 15)
    @ApiModelProperty(value = "6:15:00")
	private java.math.BigDecimal liudyw;
	/**6:30:00*/
	@Excel(name = "6:30:00", width = 15)
    @ApiModelProperty(value = "6:30:00")
	private java.math.BigDecimal liudss;
	/**6:45:00*/
	@Excel(name = "6:45:00", width = 15)
    @ApiModelProperty(value = "6:45:00")
	private java.math.BigDecimal liudsw;
	/**7:00:00*/
	@Excel(name = "7:00:00", width = 15)
    @ApiModelProperty(value = "7:00:00")
	private java.math.BigDecimal qdll;
	/**7:15:00*/
	@Excel(name = "7:15:00", width = 15)
    @ApiModelProperty(value = "7:15:00")
	private java.math.BigDecimal qdyw;
	/**7:30:00*/
	@Excel(name = "7:30:00", width = 15)
    @ApiModelProperty(value = "7:30:00")
	private java.math.BigDecimal qdss;
	/**7:45:00*/
	@Excel(name = "7:45:00", width = 15)
    @ApiModelProperty(value = "7:45:00")
	private java.math.BigDecimal qdsw;
	/**8:00:00*/
	@Excel(name = "8:00:00", width = 15)
    @ApiModelProperty(value = "8:00:00")
	private java.math.BigDecimal bdll;
	/**8:15:00*/
	@Excel(name = "8:15:00", width = 15)
    @ApiModelProperty(value = "8:15:00")
	private java.math.BigDecimal bdyw;
	/**8:30:00*/
	@Excel(name = "8:30:00", width = 15)
    @ApiModelProperty(value = "8:30:00")
	private java.math.BigDecimal bdss;
	/**8:45:00*/
	@Excel(name = "8:45:00", width = 15)
    @ApiModelProperty(value = "8:45:00")
	private java.math.BigDecimal bdsw;
	/**9:00:00*/
	@Excel(name = "9:00:00", width = 15)
    @ApiModelProperty(value = "9:00:00")
	private java.math.BigDecimal jdll;
	/**9:15:00*/
	@Excel(name = "9:15:00", width = 15)
    @ApiModelProperty(value = "9:15:00")
	private java.math.BigDecimal jdyw;
	/**9:30:00*/
	@Excel(name = "9:30:00", width = 15)
    @ApiModelProperty(value = "9:30:00")
	private java.math.BigDecimal jdss;
	/**9:45:00*/
	@Excel(name = "9:45:00", width = 15)
    @ApiModelProperty(value = "9:45:00")
	private java.math.BigDecimal jdsw;
	/**10:00:00*/
	@Excel(name = "10:00:00", width = 15)
    @ApiModelProperty(value = "10:00:00")
	private java.math.BigDecimal shidll;
	/**10:15:00*/
	@Excel(name = "10:15:00", width = 15)
    @ApiModelProperty(value = "10:15:00")
	private java.math.BigDecimal shidyw;
	/**10:30:00*/
	@Excel(name = "10:30:00", width = 15)
    @ApiModelProperty(value = "10:30:00")
	private java.math.BigDecimal shidss;
	/**10:45:00*/
	@Excel(name = "10:45:00", width = 15)
    @ApiModelProperty(value = "10:45:00")
	private java.math.BigDecimal shidsw;
	/**11:00:00*/
	@Excel(name = "11:00:00", width = 15)
    @ApiModelProperty(value = "11:00:00")
	private java.math.BigDecimal sydll;
	/**11:15:00*/
	@Excel(name = "11:15:00", width = 15)
    @ApiModelProperty(value = "11:15:00")
	private java.math.BigDecimal sydyw;
	/**11:30:00*/
	@Excel(name = "11:30:00", width = 15)
    @ApiModelProperty(value = "11:30:00")
	private java.math.BigDecimal sydss;
	/**11:45:00*/
	@Excel(name = "11:45:00", width = 15)
    @ApiModelProperty(value = "11:45:00")
	private java.math.BigDecimal sydsw;
	/**12:00:00*/
	@Excel(name = "12:00:00", width = 15)
    @ApiModelProperty(value = "12:00:00")
	private java.math.BigDecimal sedll;
	/**12:15:00*/
	@Excel(name = "12:15:00", width = 15)
    @ApiModelProperty(value = "12:15:00")
	private java.math.BigDecimal sedyw;
	/**12:30:00*/
	@Excel(name = "12:30:00", width = 15)
    @ApiModelProperty(value = "12:30:00")
	private java.math.BigDecimal sedss;
	/**12:45:00*/
	@Excel(name = "12:45:00", width = 15)
    @ApiModelProperty(value = "12:45:00")
	private java.math.BigDecimal sedsw;
	/**13:00:00*/
	@Excel(name = "13:00:00", width = 15)
    @ApiModelProperty(value = "13:00:00")
	private java.math.BigDecimal ssdll;
	/**13:15:00*/
	@Excel(name = "13:15:00", width = 15)
    @ApiModelProperty(value = "13:15:00")
	private java.math.BigDecimal ssdyw;
	/**13:30:00*/
	@Excel(name = "13:30:00", width = 15)
    @ApiModelProperty(value = "13:30:00")
	private java.math.BigDecimal ssdss;
	/**13:45:00*/
	@Excel(name = "13:45:00", width = 15)
    @ApiModelProperty(value = "13:45:00")
	private java.math.BigDecimal ssdsw;
	/**14:00:00*/
	@Excel(name = "14:00:00", width = 15)
    @ApiModelProperty(value = "14:00:00")
	private java.math.BigDecimal ssidll;
	/**14:15:00*/
	@Excel(name = "14:15:00", width = 15)
    @ApiModelProperty(value = "14:15:00")
	private java.math.BigDecimal ssidyw;
	/**14:30:00*/
	@Excel(name = "14:30:00", width = 15)
    @ApiModelProperty(value = "14:30:00")
	private java.math.BigDecimal ssidss;
	/**14:45:00*/
	@Excel(name = "14:45:00", width = 15)
    @ApiModelProperty(value = "14:45:00")
	private java.math.BigDecimal ssidsw;
	/**15:00:00*/
	@Excel(name = "15:00:00", width = 15)
    @ApiModelProperty(value = "15:00:00")
	private java.math.BigDecimal swdll;
	/**15:15:00*/
	@Excel(name = "15:15:00", width = 15)
    @ApiModelProperty(value = "15:15:00")
	private java.math.BigDecimal swdyw;
	/**15:30:00*/
	@Excel(name = "15:30:00", width = 15)
    @ApiModelProperty(value = "15:30:00")
	private java.math.BigDecimal swdss;
	/**15:45:00*/
	@Excel(name = "15:45:00", width = 15)
    @ApiModelProperty(value = "15:45:00")
	private java.math.BigDecimal swdsw;
	/**16:00:00*/
	@Excel(name = "16:00:00", width = 15)
    @ApiModelProperty(value = "16:00:00")
	private java.math.BigDecimal sldll;
	/**16:15:00*/
	@Excel(name = "16:15:00", width = 15)
    @ApiModelProperty(value = "16:15:00")
	private java.math.BigDecimal sldyw;
	/**16:30:00*/
	@Excel(name = "16:30:00", width = 15)
    @ApiModelProperty(value = "16:30:00")
	private java.math.BigDecimal sldss;
	/**16:45:00*/
	@Excel(name = "16:45:00", width = 15)
    @ApiModelProperty(value = "16:45:00")
	private java.math.BigDecimal sldsw;
	/**17:00:00*/
	@Excel(name = "17:00:00", width = 15)
    @ApiModelProperty(value = "17:00:00")
	private java.math.BigDecimal sqdll;
	/**17:15:00*/
	@Excel(name = "17:15:00", width = 15)
    @ApiModelProperty(value = "17:15:00")
	private java.math.BigDecimal sqdyw;
	/**17:30:00*/
	@Excel(name = "17:30:00", width = 15)
    @ApiModelProperty(value = "17:30:00")
	private java.math.BigDecimal sqdss;
	/**17:45:00*/
	@Excel(name = "17:45:00", width = 15)
    @ApiModelProperty(value = "17:45:00")
	private java.math.BigDecimal sqdsw;
	/**18:00:00*/
	@Excel(name = "18:00:00", width = 15)
    @ApiModelProperty(value = "18:00:00")
	private java.math.BigDecimal sbdll;
	/**18:15:00*/
	@Excel(name = "18:15:00", width = 15)
    @ApiModelProperty(value = "18:15:00")
	private java.math.BigDecimal sbdyw;
	/**18:30:00*/
	@Excel(name = "18:30:00", width = 15)
    @ApiModelProperty(value = "18:30:00")
	private java.math.BigDecimal sbdss;
	/**18:45:00*/
	@Excel(name = "18:45:00", width = 15)
    @ApiModelProperty(value = "18:45:00")
	private java.math.BigDecimal sbdsw;
	/**19:00:00*/
	@Excel(name = "19:00:00", width = 15)
    @ApiModelProperty(value = "19:00:00")
	private java.math.BigDecimal sjdll;
	/**19:15:00*/
	@Excel(name = "19:15:00", width = 15)
    @ApiModelProperty(value = "19:15:00")
	private java.math.BigDecimal sjdyw;
	/**19:30:00*/
	@Excel(name = "19:30:00", width = 15)
    @ApiModelProperty(value = "19:30:00")
	private java.math.BigDecimal sjdss;
	/**19:45:00*/
	@Excel(name = "19:45:00", width = 15)
    @ApiModelProperty(value = "19:45:00")
	private java.math.BigDecimal sjdsw;
	/**20:00:00*/
	@Excel(name = "20:00:00", width = 15)
    @ApiModelProperty(value = "20:00:00")
	private java.math.BigDecimal esdll;
	/**20:15:00*/
	@Excel(name = "20:15:00", width = 15)
    @ApiModelProperty(value = "20:15:00")
	private java.math.BigDecimal esdyw;
	/**20:30:00*/
	@Excel(name = "20:30:00", width = 15)
    @ApiModelProperty(value = "20:30:00")
	private java.math.BigDecimal esdss;
	/**20:45:00*/
	@Excel(name = "20:45:00", width = 15)
    @ApiModelProperty(value = "20:45:00")
	private java.math.BigDecimal esdsw;
	/**21:00:00*/
	@Excel(name = "21:00:00", width = 15)
    @ApiModelProperty(value = "21:00:00")
	private java.math.BigDecimal eydll;
	/**21:15:00*/
	@Excel(name = "21:15:00", width = 15)
    @ApiModelProperty(value = "21:15:00")
	private java.math.BigDecimal eydyw;
	/**21:30:00*/
	@Excel(name = "21:30:00", width = 15)
    @ApiModelProperty(value = "21:30:00")
	private java.math.BigDecimal eydss;
	/**21:45:00*/
	@Excel(name = "21:45:00", width = 15)
    @ApiModelProperty(value = "21:45:00")
	private java.math.BigDecimal eydsw;
	/**22:00:00*/
	@Excel(name = "22:00:00", width = 15)
    @ApiModelProperty(value = "22:00:00")
	private java.math.BigDecimal eedll;
	/**22:15:00*/
	@Excel(name = "22:15:00", width = 15)
    @ApiModelProperty(value = "22:15:00")
	private java.math.BigDecimal eedyw;
	/**22:30:00*/
	@Excel(name = "22:30:00", width = 15)
    @ApiModelProperty(value = "22:30:00")
	private java.math.BigDecimal eedss;
	/**22:45:00*/
	@Excel(name = "22:45:00", width = 15)
    @ApiModelProperty(value = "22:45:00")
	private java.math.BigDecimal eedsw;
	/**23:00:00*/
	@Excel(name = "23:00:00", width = 15)
    @ApiModelProperty(value = "23:00:00")
	private java.math.BigDecimal essdll;
	/**23:15:00*/
	@Excel(name = "23:15:00", width = 15)
    @ApiModelProperty(value = "23:15:00")
	private java.math.BigDecimal essdyw;
	/**23:30:00*/
	@Excel(name = "23:30:00", width = 15)
    @ApiModelProperty(value = "23:30:00")
	private java.math.BigDecimal essdss;
	/**23:45:00*/
	@Excel(name = "23:45:00", width = 15)
    @ApiModelProperty(value = "23:45:00")
	private java.math.BigDecimal essdsw;
}
