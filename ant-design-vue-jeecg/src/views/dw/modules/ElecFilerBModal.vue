<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="drawerWidth"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">

    <template slot="title">
      <div style="width: 100%;">
        <span>{{ title }}</span>
        <span style="display:inline-block;width:calc(100% - 51px);padding-right:10px;text-align: right">
          <a-button @click="toggleScreen" icon="appstore" style="height:20px;width:20px;border:0px"></a-button>
        </span>
      </div>

    </template>

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="申报企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerCompanyName', validatorRules.filerCompanyName]" placeholder="请输入企业名称"></a-input>
        </a-form-item>
        <a-form-item label="负荷预测" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'loadForecasting', validatorRules.loadForecasting]" placeholder="请输入负荷预测"></a-input>
        </a-form-item>
        <a-form-item label="交易类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transactionType', validatorRules.transactionType]" placeholder="请输入交易类型"></a-input>
        </a-form-item>
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerMonth', validatorRules.filerMonth]" placeholder="请输入月份"></a-input>
        </a-form-item>
        <a-form-item label="申报电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marketQuotation', validatorRules.marketQuotation]" placeholder="请输入申报电量"></a-input>
        </a-form-item>
        <a-form-item label="申报开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申报开始时间" v-decorator="[ 'beginDate', validatorRules.beginDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="申报结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申报结束时间" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="申报人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filer', validatorRules.filer]" placeholder="请输入申报人"></a-input>
        </a-form-item>
        <a-form-item label="申报人所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerCompany', validatorRules.filerCompany]" placeholder="请输入申报人所属公司"></a-input>
        </a-form-item>
        <a-form-item label="申报结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerResult', validatorRules.filerResult]" placeholder="请输入申报结果"></a-input>
        </a-form-item>
        <a-form-item label="申报状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerStatus', validatorRules.filerStatus]" placeholder="请输入申报结果"></a-input>
        </a-form-item>
        <a-form-item label="日志记录" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'logRecords', validatorRules.logRecords]" placeholder="请输入日志记录"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>


    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-button @click="handleCancel" :loading="confirmLoading" style="margin-right: .8rem">取消</a-button>
      <a-popconfirm title="确定驳回吗？" @confirm="handleBack" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem;color:#ffa95e;">驳回</a-button>
      </a-popconfirm>
      <!--<a-button @click="handleBack" type="primary" :loading="confirmLoading" style="margin-right: .8rem">驳回</a-button>-->
      <a-button @click="handleSubmit" type="primary" :loading="confirmLoading">确认</a-button>
    </div>
  </a-drawer>
</template>

<script>
  import pick from 'lodash.pick'
  import moment from 'moment'
  import Vue from 'vue'
  // 引入搜索部门弹出框的组件
  // import departWindow from './DepartWindow'
  import JSelectPosition from '@/components/jeecgbiz/JSelectPosition'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction,httpAction } from '@/api/manage'
  import {addUser,editUser,queryUserRole,queryall } from '@/api/api'
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {duplicateCheck } from '@/api/api'
  import JDate from '@/components/jeecg/JDate'
  // import JImageUpload from '../../../components/jeecg/JImageUpload'

  export default {
    name: "ElecFilerBModal",
    components: {
      JSelectPosition,
      JDate
    },
    data () {
      return {
        departDisabled: false, //是否是我的部门调用该页面
        roleDisabled: false, //是否是角色维护调用该页面
        modalWidth:800,
        drawerWidth:700,
        modaltoggleFlag:true,
        confirmDirty: false,
        selectedDepartKeys:[], //保存用户选择部门id
        checkedDepartKeys:[],
        checkedDepartNames:[], // 保存部门的名称 =>title
        checkedDepartNameString:"", // 保存部门的名称 =>title
        resultDepartOptions:[],
        userId:"", //保存用户id
        disableSubmit:false,
        userDepartModel:{userId:'',departIdList:[]}, // 保存SysUserDepart的用户部门中间表数据需要的对象
        dateFormat:"YYYY-MM-DD",
        validatorRules: {
          filerCompanyName: {rules: [
            ]},
          filerMonth: {rules: [
            ]},
          loadForecasting: {rules: [
            ]},
          transactionType: {rules: [
            ]},
          marketQuotation: {rules: [
            ]},
          beginDate: {rules: [
            ]},
          endDate: {rules: [
            ]},
          filer: {rules: [
            ]},
          filerCompany: {rules: [
            ]},
          filerResult: {rules: [
            ]},
          filerStatus: {rules: [
            ]},
          logRecords: {rules: [
            ]},
        },
        departIdShow:false,
        departIds:[], //负责部门id
        title:"操作",
        visible: false,
        model: {},
        model2: {},
        roleList:[],
        selectedRole:[],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        uploadLoading:false,
        confirmLoading: false,
        headers:{},
        form:this.$form.createForm(this),
        picUrl: "",
        url: {
          fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
          imgerver: window._CONFIG['staticDomainURL'],
          userWithDepart: "/sys/user/userDepartList", // 引入为指定用户查看部门信息需要的url
          userId:"/sys/user/generateUserId", // 引入生成添加用户情况下的url
          syncUserByUserName:"/process/extActProcess/doSyncUserByUserName",//同步用户到工作流
        },
        identity:"1",
        fileList:[],
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}

    },
    computed:{
      uploadAction:function () {
        return this.url.fileUpload;
      }
    },
    methods: {
      isDisabledAuth(code){
        return disabledAuthFilter(code);
      },
      //窗口最大化切换
      toggleScreen(){
        if(this.modaltoggleFlag){
          this.modalWidth = window.innerWidth;
        }else{
          this.modalWidth = 800;
        }
        this.modaltoggleFlag = !this.modaltoggleFlag;
      },
      refresh () {
        this.selectedDepartKeys=[];
        this.checkedDepartKeys=[];
        this.checkedDepartNames=[];
        this.checkedDepartNameString = "";
        this.userId=""
        this.resultDepartOptions=[];
        this.departId=[];
        this.departIdShow=false;
      },
      add () {
        this.picUrl = "";
        this.refresh();
        this.edit({activitiSync:'1'});
      },
      edit (record) {

        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        console.log(record)
        this.model2 = Object.assign({}, record);
        getAction('/electricityfilerb/electricityFilerB/queryall',{companyName:record.username}).then((res) => {
          if(res.success){
            let record2 = res.result[0];
            console.log(record2)
            this.form.resetFields();
            this.model = Object.assign({}, record2);
            this.visible = true;
            this.$nextTick(() => {
              this.form.setFieldsValue(pick(this.model,'filerCompanyName','filerMonth','loadForecasting','transactionType','marketQuotation','beginDate','endDate','filer','filerCompany','filerResult','filerStatus','logRecords'))
            })
          }
        })

      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
        this.selectedRole = [];
        this.userDepartModel = {userId:'',departIdList:[]};
        this.checkedDepartNames = [];
        this.checkedDepartNameString='';
        this.checkedDepartKeys = [];
        this.selectedDepartKeys = [];
        this.resultDepartOptions=[];
        this.departIds=[];
        this.departIdShow=false;
        this.identity="1";
        this.fileList=[];
      },
      moment,
      //驳回
      handleBack(){
        console.log("驳回——————");
        const that = this;
        // 触发表单验证  修改状态
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            let httpurl2 = '';
            let method2 = '';
            if(!this.model.id){
              httpurl+='/electricityfilerb/electricityFilerB/add';
              method = 'post';
            }else{
              httpurl+='/electricityfilerb/electricityFilerB/edit';
              method = 'put';
            }
            //针对用户 1 等待中 ；2 待确认；3 完成
            values.filerStatus = '2';
            values.logRecords = 'dasda adsd a ad';
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
            //修改分月电量状态
            this.model2.stat = '1';
            console.log(this.model.filerMonth)
            if(this.model.filerMonth === 'yi'){
              this.model2.yi = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'er'){
              this.model2.er = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'san'){
              this.model2.san = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'si'){
              this.model2.si = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'wu'){
              this.model2.wu = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'liu'){
              this.model2.liu = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'qi'){
              this.model2.qi = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'ba'){
              this.model2.ba = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'jiu'){
              this.model2.jiu = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'shi'){
              this.model2.shi = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'shiyi'){
              this.model2.shiyi = this.model.marketQuotation;
            }else if(this.model.filerMonth === 'shier'){
              this.model2.shier = this.model.marketQuotation;
            }

            if(!this.model2.id){
              httpurl2+='/monthelec/momthElec/add';
              method2 = 'post';
            }else{
              httpurl2+='/monthelec/momthElec/edit';
              method2 = 'put';
            }
            httpAction(httpurl2,this.model2,method2).then((res)=>{
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
      handleSubmit () {

        const that = this;
        // 触发表单验证  修改状态
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            let httpurl2 = '';
            let method2 = '';
            if(!this.model.id){
              httpurl+='/electricityfilerb/electricityFilerB/add';
              method = 'post';
            }else{
              httpurl+='/electricityfilerb/electricityFilerB/edit';
              method = 'put';
            }
            //针对用户 1 等待中 ；2 待确认；3 完成
            values.filerStatus = '3';
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
            //修改分月电量状态
            this.model2.stat = '3';
            if(!this.model2.id){
              httpurl2+='/monthelec/momthElec/add';
              method2 = 'post';
            }else{
              httpurl2+='/monthelec/momthElec/edit';
              method2 = 'put';
            }
            httpAction(httpurl2,this.model2,method2).then((res)=>{
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

      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 700;
        }
      },
    }
  }
</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width:104px;
    height:104px;
  }
  .ant-upload-select-picture-card i {
    font-size: 49px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }

  .ant-table-tbody .ant-table-row td{
    padding-top:10px;
    padding-bottom:10px;
  }

  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>