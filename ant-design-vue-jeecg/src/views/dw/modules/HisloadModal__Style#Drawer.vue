<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      :closable="false"
      @close="close"
      :visible="visible"
  >

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属部门">
          <a-input placeholder="请输入所属部门" v-decorator="['sysOrgCode', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名称">
          <a-input placeholder="请输入用户名称" v-decorator="['username', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用电单元名称">
          <a-input placeholder="请输入用电单元名称" v-decorator="['usernameUnit', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'elecDate', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="0:00:00">
          <a-input-number v-decorator="[ 'ldll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="0:15:00">
          <a-input-number v-decorator="[ 'ldyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="0:30:00">
          <a-input-number v-decorator="[ 'ldss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="0:45:00">
          <a-input-number v-decorator="[ 'ldsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="1:00:00">
          <a-input-number v-decorator="[ 'ydll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="1:15:00">
          <a-input-number v-decorator="[ 'ydyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="1:30:00">
          <a-input-number v-decorator="[ 'ydss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="1:45:00">
          <a-input-number v-decorator="[ 'ydsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="2:00:00">
          <a-input-number v-decorator="[ 'edll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="2:15:00">
          <a-input-number v-decorator="[ 'edyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="2:30:00">
          <a-input-number v-decorator="[ 'edss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="2:45:00">
          <a-input-number v-decorator="[ 'edsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="3:00:00">
          <a-input-number v-decorator="[ 'sdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="3:15:00">
          <a-input-number v-decorator="[ 'sdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="3:30:00">
          <a-input-number v-decorator="[ 'sdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="3:45:00">
          <a-input-number v-decorator="[ 'sssw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="4:00:00">
          <a-input-number v-decorator="[ 'sidll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="4:15:00">
          <a-input-number v-decorator="[ 'sidyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="4:30:00">
          <a-input-number v-decorator="[ 'sidss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="4:45:00">
          <a-input-number v-decorator="[ 'sidsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="5:00:00">
          <a-input-number v-decorator="[ 'wdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="5:15:00">
          <a-input-number v-decorator="[ 'wdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="5:30:00">
          <a-input-number v-decorator="[ 'wdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="5:45:00">
          <a-input-number v-decorator="[ 'wdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="6:00:00">
          <a-input-number v-decorator="[ 'liudll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="6:15:00">
          <a-input-number v-decorator="[ 'liudyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="6:30:00">
          <a-input-number v-decorator="[ 'liudss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="6:45:00">
          <a-input-number v-decorator="[ 'liudsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="7:00:00">
          <a-input-number v-decorator="[ 'qdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="7:15:00">
          <a-input-number v-decorator="[ 'qdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="7:30:00">
          <a-input-number v-decorator="[ 'qdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="7:45:00">
          <a-input-number v-decorator="[ 'qdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="8:00:00">
          <a-input-number v-decorator="[ 'bdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="8:15:00">
          <a-input-number v-decorator="[ 'bdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="8:30:00">
          <a-input-number v-decorator="[ 'bdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="8:45:00">
          <a-input-number v-decorator="[ 'bdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="9:00:00">
          <a-input-number v-decorator="[ 'jdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="9:15:00">
          <a-input-number v-decorator="[ 'jdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="9:30:00">
          <a-input-number v-decorator="[ 'jdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="9:45:00">
          <a-input-number v-decorator="[ 'jdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="10:00:00">
          <a-input-number v-decorator="[ 'shidll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="10:15:00">
          <a-input-number v-decorator="[ 'shidyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="10:30:00">
          <a-input-number v-decorator="[ 'shidss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="10:45:00">
          <a-input-number v-decorator="[ 'shidsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="11:00:00">
          <a-input-number v-decorator="[ 'sydll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="11:15:00">
          <a-input-number v-decorator="[ 'sydyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="11:30:00">
          <a-input-number v-decorator="[ 'sydss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="11:45:00">
          <a-input-number v-decorator="[ 'sydsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="12:00:00">
          <a-input-number v-decorator="[ 'sedll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="12:15:00">
          <a-input-number v-decorator="[ 'sedyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="12:30:00">
          <a-input-number v-decorator="[ 'sedss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="12:45:00">
          <a-input-number v-decorator="[ 'sedsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="13:00:00">
          <a-input-number v-decorator="[ 'ssdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="13:15:00">
          <a-input-number v-decorator="[ 'ssdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="13:30:00">
          <a-input-number v-decorator="[ 'ssdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="13:45:00">
          <a-input-number v-decorator="[ 'ssdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="14:00:00">
          <a-input-number v-decorator="[ 'ssidll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="14:15:00">
          <a-input-number v-decorator="[ 'ssidyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="14:30:00">
          <a-input-number v-decorator="[ 'ssidss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="14:45:00">
          <a-input-number v-decorator="[ 'ssidsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="15:00:00">
          <a-input-number v-decorator="[ 'swdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="15:15:00">
          <a-input-number v-decorator="[ 'swdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="15:30:00">
          <a-input-number v-decorator="[ 'swdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="15:45:00">
          <a-input-number v-decorator="[ 'swdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="16:00:00">
          <a-input-number v-decorator="[ 'sldll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="16:15:00">
          <a-input-number v-decorator="[ 'sldyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="16:30:00">
          <a-input-number v-decorator="[ 'sldss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="16:45:00">
          <a-input-number v-decorator="[ 'sldsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="17:00:00">
          <a-input-number v-decorator="[ 'sqdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="17:15:00">
          <a-input-number v-decorator="[ 'sqdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="17:30:00">
          <a-input-number v-decorator="[ 'sqdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="17:45:00">
          <a-input-number v-decorator="[ 'sqdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="18:00:00">
          <a-input-number v-decorator="[ 'sbdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="18:15:00">
          <a-input-number v-decorator="[ 'sbdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="18:30:00">
          <a-input-number v-decorator="[ 'sbdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="18:45:00">
          <a-input-number v-decorator="[ 'sbdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="19:00:00">
          <a-input-number v-decorator="[ 'sjdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="19:15:00">
          <a-input-number v-decorator="[ 'sjdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="19:30:00">
          <a-input-number v-decorator="[ 'sjdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="19:45:00">
          <a-input-number v-decorator="[ 'sjdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="20:00:00">
          <a-input-number v-decorator="[ 'esdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="20:15:00">
          <a-input-number v-decorator="[ 'esdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="20:30:00">
          <a-input-number v-decorator="[ 'esdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="20:45:00">
          <a-input-number v-decorator="[ 'esdsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="21:00:00">
          <a-input-number v-decorator="[ 'eydll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="21:15:00">
          <a-input-number v-decorator="[ 'eydyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="21:30:00">
          <a-input-number v-decorator="[ 'eydss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="21:45:00">
          <a-input-number v-decorator="[ 'eydsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="22:00:00">
          <a-input-number v-decorator="[ 'eedll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="22:15:00">
          <a-input-number v-decorator="[ 'eedyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="22:30:00">
          <a-input-number v-decorator="[ 'eedss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="22:45:00">
          <a-input-number v-decorator="[ 'eedsw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="23:00:00">
          <a-input-number v-decorator="[ 'essdll', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="23:15:00">
          <a-input-number v-decorator="[ 'essdyw', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="23:30:00">
          <a-input-number v-decorator="[ 'essdss', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="23:45:00">
          <a-input-number v-decorator="[ 'essdsw', {}]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "HisloadModal",
    data () {
      return {
        title:"操作",
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
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/hisload/hisload/add",
          edit: "/hisload/hisload/edit",
        },
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
          this.form.setFieldsValue(pick(this.model,'sysOrgCode','username','usernameUnit','ldll','ldyw','ldss','ldsw','ydll','ydyw','ydss','ydsw','edll','edyw','edss','edsw','sdll','sdyw','sdss','sssw','sidll','sidyw','sidss','sidsw','wdll','wdyw','wdss','wdsw','liudll','liudyw','liudss','liudsw','qdll','qdyw','qdss','qdsw','bdll','bdyw','bdss','bdsw','jdll','jdyw','jdss','jdsw','shidll','shidyw','shidss','shidsw','sydll','sydyw','sydss','sydsw','sedll','sedyw','sedss','sedsw','ssdll','ssdyw','ssdss','ssdsw','ssidll','ssidyw','ssidss','ssidsw','swdll','swdyw','swdss','swdsw','sldll','sldyw','sldss','sldsw','sqdll','sqdyw','sqdss','sqdsw','sbdll','sbdyw','sbdss','sbdsw','sjdll','sjdyw','sjdss','sjdsw','esdll','esdyw','esdss','esdsw','eydll','eydyw','eydss','eydsw','eedll','eedyw','eedss','eedsw','essdll','essdyw','essdss','essdsw'))
		  //时间格式化
          this.form.setFieldsValue({elecDate:this.model.elecDate?moment(this.model.elecDate):null})
        });

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
            //时间格式化
            formData.elecDate = formData.elecDate?formData.elecDate.format('YYYY-MM-DD HH:mm:ss'):null;
            
            console.log(formData)
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


    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>