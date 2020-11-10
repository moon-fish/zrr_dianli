<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'username', validatorRules.username]" placeholder="请输入用户名称"></a-input>
        </a-form-item>
        <a-form-item label="用电单元名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'usernameUnit', validatorRules.usernameUnit]" placeholder="请输入用电单元名称"></a-input>
        </a-form-item>
        <a-form-item label="时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择时间" v-decorator="[ 'elecDate', validatorRules.elecDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="0:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ldll', validatorRules.ldll]" placeholder="请输入0:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="0:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ldyw', validatorRules.ldyw]" placeholder="请输入0:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="0:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ldss', validatorRules.ldss]" placeholder="请输入0:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="0:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ldsw', validatorRules.ldsw]" placeholder="请输入0:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="1:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ydll', validatorRules.ydll]" placeholder="请输入1:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="1:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ydyw', validatorRules.ydyw]" placeholder="请输入1:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="1:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ydss', validatorRules.ydss]" placeholder="请输入1:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="1:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ydsw', validatorRules.ydsw]" placeholder="请输入1:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'edll', validatorRules.edll]" placeholder="请输入2:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'edyw', validatorRules.edyw]" placeholder="请输入2:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'edss', validatorRules.edss]" placeholder="请输入2:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="2:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'edsw', validatorRules.edsw]" placeholder="请输入2:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sdll', validatorRules.sdll]" placeholder="请输入3:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sdyw', validatorRules.sdyw]" placeholder="请输入3:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sdss', validatorRules.sdss]" placeholder="请输入3:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="3:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sssw', validatorRules.sssw]" placeholder="请输入3:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sidll', validatorRules.sidll]" placeholder="请输入4:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sidyw', validatorRules.sidyw]" placeholder="请输入4:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sidss', validatorRules.sidss]" placeholder="请输入4:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="4:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sidsw', validatorRules.sidsw]" placeholder="请输入4:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'wdll', validatorRules.wdll]" placeholder="请输入5:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'wdyw', validatorRules.wdyw]" placeholder="请输入5:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'wdss', validatorRules.wdss]" placeholder="请输入5:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="5:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'wdsw', validatorRules.wdsw]" placeholder="请输入5:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'liudll', validatorRules.liudll]" placeholder="请输入6:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'liudyw', validatorRules.liudyw]" placeholder="请输入6:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'liudss', validatorRules.liudss]" placeholder="请输入6:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="6:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'liudsw', validatorRules.liudsw]" placeholder="请输入6:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'qdll', validatorRules.qdll]" placeholder="请输入7:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'qdyw', validatorRules.qdyw]" placeholder="请输入7:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择7:30:00" v-decorator="[ 'qdss', validatorRules.qdss]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="7:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'qdsw', validatorRules.qdsw]" placeholder="请输入7:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bdll', validatorRules.bdll]" placeholder="请输入8:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bdyw', validatorRules.bdyw]" placeholder="请输入8:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bdss', validatorRules.bdss]" placeholder="请输入8:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="8:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'bdsw', validatorRules.bdsw]" placeholder="请输入8:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'jdll', validatorRules.jdll]" placeholder="请输入9:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'jdyw', validatorRules.jdyw]" placeholder="请输入9:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'jdss', validatorRules.jdss]" placeholder="请输入9:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="9:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'jdsw', validatorRules.jdsw]" placeholder="请输入9:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shidll', validatorRules.shidll]" placeholder="请输入10:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shidyw', validatorRules.shidyw]" placeholder="请输入10:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shidss', validatorRules.shidss]" placeholder="请输入10:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="10:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shidsw', validatorRules.shidsw]" placeholder="请输入10:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sydll', validatorRules.sydll]" placeholder="请输入11:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sydyw', validatorRules.sydyw]" placeholder="请输入11:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sydss', validatorRules.sydss]" placeholder="请输入11:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="11:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sydsw', validatorRules.sydsw]" placeholder="请输入11:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sedll', validatorRules.sedll]" placeholder="请输入12:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sedyw', validatorRules.sedyw]" placeholder="请输入12:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sedss', validatorRules.sedss]" placeholder="请输入12:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="12:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sedsw', validatorRules.sedsw]" placeholder="请输入12:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="13:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssdll', validatorRules.ssdll]" placeholder="请输入13:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="13:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssdyw', validatorRules.ssdyw]" placeholder="请输入13:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="13:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssdss', validatorRules.ssdss]" placeholder="请输入13:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="13:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssdsw', validatorRules.ssdsw]" placeholder="请输入13:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="14:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssidll', validatorRules.ssidll]" placeholder="请输入14:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="14:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssidyw', validatorRules.ssidyw]" placeholder="请输入14:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="14:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssidss', validatorRules.ssidss]" placeholder="请输入14:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="14:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ssidsw', validatorRules.ssidsw]" placeholder="请输入14:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="15:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'swdll', validatorRules.swdll]" placeholder="请输入15:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="15:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'swdyw', validatorRules.swdyw]" placeholder="请输入15:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="15:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'swdss', validatorRules.swdss]" placeholder="请输入15:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="15:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'swdsw', validatorRules.swdsw]" placeholder="请输入15:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="16:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sldll', validatorRules.sldll]" placeholder="请输入16:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="16:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sldyw', validatorRules.sldyw]" placeholder="请输入16:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="16:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sldss', validatorRules.sldss]" placeholder="请输入16:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="16:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sldsw', validatorRules.sldsw]" placeholder="请输入16:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="17:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sqdll', validatorRules.sqdll]" placeholder="请输入17:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="17:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sqdyw', validatorRules.sqdyw]" placeholder="请输入17:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="17:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sqdss', validatorRules.sqdss]" placeholder="请输入17:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="17:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sqdsw', validatorRules.sqdsw]" placeholder="请输入17:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="18:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sbdll', validatorRules.sbdll]" placeholder="请输入18:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="18:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sbdyw', validatorRules.sbdyw]" placeholder="请输入18:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="18:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sbdss', validatorRules.sbdss]" placeholder="请输入18:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="18:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sbdsw', validatorRules.sbdsw]" placeholder="请输入18:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="19:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sjdll', validatorRules.sjdll]" placeholder="请输入19:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="19:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sjdyw', validatorRules.sjdyw]" placeholder="请输入19:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="19:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sjdss', validatorRules.sjdss]" placeholder="请输入19:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="19:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sjdsw', validatorRules.sjdsw]" placeholder="请输入19:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="20:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'esdll', validatorRules.esdll]" placeholder="请输入20:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="20:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'esdyw', validatorRules.esdyw]" placeholder="请输入20:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="20:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'esdss', validatorRules.esdss]" placeholder="请输入20:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="20:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'esdsw', validatorRules.esdsw]" placeholder="请输入20:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="21:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eydll', validatorRules.eydll]" placeholder="请输入21:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="21:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eydyw', validatorRules.eydyw]" placeholder="请输入21:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="21:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eydss', validatorRules.eydss]" placeholder="请输入21:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="21:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eydsw', validatorRules.eydsw]" placeholder="请输入21:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="22:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eedll', validatorRules.eedll]" placeholder="请输入22:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="22:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eedyw', validatorRules.eedyw]" placeholder="请输入22:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="22:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eedss', validatorRules.eedss]" placeholder="请输入22:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="22:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'eedsw', validatorRules.eedsw]" placeholder="请输入22:45:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="23:00:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'essdll', validatorRules.essdll]" placeholder="请输入23:00:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="23:15:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'essdyw', validatorRules.essdyw]" placeholder="请输入23:15:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="23:30:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'essdss', validatorRules.essdss]" placeholder="请输入23:30:00" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="23:45:00" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'essdsw', validatorRules.essdsw]" placeholder="请输入23:45:00" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: "HiselecModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          username: {rules: [
          ]},
          usernameUnit: {rules: [
          ]},
          elecDate: {rules: [
          ]},
          ldll: {rules: [
          ]},
          ldyw: {rules: [
          ]},
          ldss: {rules: [
          ]},
          ldsw: {rules: [
          ]},
          ydll: {rules: [
          ]},
          ydyw: {rules: [
          ]},
          ydss: {rules: [
          ]},
          ydsw: {rules: [
          ]},
          edll: {rules: [
          ]},
          edyw: {rules: [
          ]},
          edss: {rules: [
          ]},
          edsw: {rules: [
          ]},
          sdll: {rules: [
          ]},
          sdyw: {rules: [
          ]},
          sdss: {rules: [
          ]},
          sssw: {rules: [
          ]},
          sidll: {rules: [
          ]},
          sidyw: {rules: [
          ]},
          sidss: {rules: [
          ]},
          sidsw: {rules: [
          ]},
          wdll: {rules: [
          ]},
          wdyw: {rules: [
          ]},
          wdss: {rules: [
          ]},
          wdsw: {rules: [
          ]},
          liudll: {rules: [
          ]},
          liudyw: {rules: [
          ]},
          liudss: {rules: [
          ]},
          liudsw: {rules: [
          ]},
          qdll: {rules: [
          ]},
          qdyw: {rules: [
          ]},
          qdss: {rules: [
          ]},
          qdsw: {rules: [
          ]},
          bdll: {rules: [
          ]},
          bdyw: {rules: [
          ]},
          bdss: {rules: [
          ]},
          bdsw: {rules: [
          ]},
          jdll: {rules: [
          ]},
          jdyw: {rules: [
          ]},
          jdss: {rules: [
          ]},
          jdsw: {rules: [
          ]},
          shidll: {rules: [
          ]},
          shidyw: {rules: [
          ]},
          shidss: {rules: [
          ]},
          shidsw: {rules: [
          ]},
          sydll: {rules: [
          ]},
          sydyw: {rules: [
          ]},
          sydss: {rules: [
          ]},
          sydsw: {rules: [
          ]},
          sedll: {rules: [
          ]},
          sedyw: {rules: [
          ]},
          sedss: {rules: [
          ]},
          sedsw: {rules: [
          ]},
          ssdll: {rules: [
          ]},
          ssdyw: {rules: [
          ]},
          ssdss: {rules: [
          ]},
          ssdsw: {rules: [
          ]},
          ssidll: {rules: [
          ]},
          ssidyw: {rules: [
          ]},
          ssidss: {rules: [
          ]},
          ssidsw: {rules: [
          ]},
          swdll: {rules: [
          ]},
          swdyw: {rules: [
          ]},
          swdss: {rules: [
          ]},
          swdsw: {rules: [
          ]},
          sldll: {rules: [
          ]},
          sldyw: {rules: [
          ]},
          sldss: {rules: [
          ]},
          sldsw: {rules: [
          ]},
          sqdll: {rules: [
          ]},
          sqdyw: {rules: [
          ]},
          sqdss: {rules: [
          ]},
          sqdsw: {rules: [
          ]},
          sbdll: {rules: [
          ]},
          sbdyw: {rules: [
          ]},
          sbdss: {rules: [
          ]},
          sbdsw: {rules: [
          ]},
          sjdll: {rules: [
          ]},
          sjdyw: {rules: [
          ]},
          sjdss: {rules: [
          ]},
          sjdsw: {rules: [
          ]},
          esdll: {rules: [
          ]},
          esdyw: {rules: [
          ]},
          esdss: {rules: [
          ]},
          esdsw: {rules: [
          ]},
          eydll: {rules: [
          ]},
          eydyw: {rules: [
          ]},
          eydss: {rules: [
          ]},
          eydsw: {rules: [
          ]},
          eedll: {rules: [
          ]},
          eedyw: {rules: [
          ]},
          eedss: {rules: [
          ]},
          eedsw: {rules: [
          ]},
          essdll: {rules: [
          ]},
          essdyw: {rules: [
          ]},
          essdss: {rules: [
          ]},
          essdsw: {rules: [
          ]},
        },
        url: {
          add: "/hiselec/hiselec/add",
          edit: "/hiselec/hiselec/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'username','usernameUnit','elecDate','ldll','ldyw','ldss','ldsw','ydll','ydyw','ydss','ydsw','edll','edyw','edss','edsw','sdll','sdyw','sdss','sssw','sidll','sidyw','sidss','sidsw','wdll','wdyw','wdss','wdsw','liudll','liudyw','liudss','liudsw','qdll','qdyw','qdss','qdsw','bdll','bdyw','bdss','bdsw','jdll','jdyw','jdss','jdsw','shidll','shidyw','shidss','shidsw','sydll','sydyw','sydss','sydsw','sedll','sedyw','sedss','sedsw','ssdll','ssdyw','ssdss','ssdsw','ssidll','ssidyw','ssidss','ssidsw','swdll','swdyw','swdss','swdsw','sldll','sldyw','sldss','sldsw','sqdll','sqdyw','sqdss','sqdsw','sbdll','sbdyw','sbdss','sbdsw','sjdll','sjdyw','sjdss','sjdsw','esdll','esdyw','esdss','esdsw','eydll','eydyw','eydss','eydsw','eedll','eedyw','eedss','eedsw','essdll','essdyw','essdss','essdsw'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'username','usernameUnit','elecDate','ldll','ldyw','ldss','ldsw','ydll','ydyw','ydss','ydsw','edll','edyw','edss','edsw','sdll','sdyw','sdss','sssw','sidll','sidyw','sidss','sidsw','wdll','wdyw','wdss','wdsw','liudll','liudyw','liudss','liudsw','qdll','qdyw','qdss','qdsw','bdll','bdyw','bdss','bdsw','jdll','jdyw','jdss','jdsw','shidll','shidyw','shidss','shidsw','sydll','sydyw','sydss','sydsw','sedll','sedyw','sedss','sedsw','ssdll','ssdyw','ssdss','ssdsw','ssidll','ssidyw','ssidss','ssidsw','swdll','swdyw','swdss','swdsw','sldll','sldyw','sldss','sldsw','sqdll','sqdyw','sqdss','sqdsw','sbdll','sbdyw','sbdss','sbdsw','sjdll','sjdyw','sjdss','sjdsw','esdll','esdyw','esdss','esdsw','eydll','eydyw','eydss','eydsw','eedll','eedyw','eedss','eedsw','essdll','essdyw','essdss','essdsw'))
      },

      
    }
  }
</script>