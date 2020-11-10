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
        <a-form-item label="合同名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contractName', validatorRules.contractName]" placeholder="请输入合同名称"></a-input>
        </a-form-item>
        <!--<a-form-item label="电力用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--&lt;!&ndash;<a-input v-decorator="[ 'userName', validatorRules.userName]" placeholder="请输入电力用户名称"></a-input>&ndash;&gt;-->
          <!--<j-select-elec-user v-decorator="[ 'userName', validatorRules.userName]" placeholder="请输入电力用户名称" :multi="true"></j-select-elec-user>-->
        <!--</a-form-item>-->
        <!--<a-form-item label="交易类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--&lt;!&ndash;<a-input v-decorator="[ 'transactionType', validatorRules.transactionType]" placeholder="请输入交易类型"></a-input>&ndash;&gt;-->
          <!--<a-select @change="handType" v-decorator="[ 'transactionType', {initialValue:this.transactionType,rules: validatorRules.transactionType.rules}]">-->
            <!--<a-select-option value="sb">双边</a-select-option>-->
            <!--<a-select-option value="a">合同转让</a-select-option>-->
            <!--<a-select-option value="b">挂牌</a-select-option>-->
          <!--</a-select>-->
        <!--</a-form-item>-->
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始时间" v-decorator="[ 'beginDate', validatorRules.beginDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="售电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'purchaseElectricity', validatorRules.purchaseElectricity]" placeholder="请输入售电量"></a-input>
        </a-form-item>
        <a-form-item label="售电价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'purchasePrice', validatorRules.purchasePrice]" placeholder="请输入售电量"></a-input>
        </a-form-item>
        <a-form-item label="分月电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'monthElectricity', validatorRules.monthElectricity]" placeholder="请输入分月电量"></a-input>-->
          <a-button @click="monthAdd" style="margin-right: 20px;">新增</a-button>
          <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel" >
            <a-button type="primary" icon="import">导入</a-button>
          </a-upload>
          <!--<a-button @click="monthImport">导入</a-button>-->
        </a-form-item>
        <a-form-item label="联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入联系人姓名"></a-input>
        </a-form-item>
        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码"></a-input>
        </a-form-item>
        <a-form-item label="办公电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'officePhone', validatorRules.officePhone]" placeholder="请输入办公电话"></a-input>
        </a-form-item>
        <a-form-item label="联系人职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'duty', validatorRules.duty]" placeholder="请输入联系人职务"></a-input>
        </a-form-item>
        <a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'depoaitAccount', validatorRules.depoaitAccount]" placeholder="请输入开户账号"></a-input>
        </a-form-item>
        <a-form-item label="合同附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contractFile', validatorRules.contractFile]" placeholder="请输入合同附件"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
    <momth-elec-add-modal ref="monthAdd" :aaa="this.cusname1"></momth-elec-add-modal>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JSelectElecUser from '../JSelectElecUser'
  import MomthElecAddModal from './MomthElecAddModal'
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"

  export default {
    name: "UserPurchaseContractBModal",
    components: { 
      JDate,
      JSelectElecUser,
      MomthElecAddModal
    },
    props:{
      cusname1:{
        type:String,
        default:'',
      }
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
          contractName: {rules: [
          ]},
          userName: {rules: [
          ]},
          transactionType: {rules: [
          ]},
          beginDate: {rules: [
          ]},
          endDate: {rules: [
          ]},
          purchaseElectricity: {rules: [
              { required: true, message: '请输入购电量!' },
              { pattern:/^([1-9]\d*|0)(\.\d{1,2})?$/, message: '请输入整数或者小数!'}
          ]},
          purchasePrice: {
            rules: [
              { required: true, message: '请输入购电价!' },
              { pattern:/^([1-9]\d*|0)(\.\d{1,2})?$/, message: '请输入整数或者小数!'}
            ]
          },
          monthElectricity: {rules: [
          ]},
          name: {rules: [
          ]},
          phone: {rules: [
            {pattern:/^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
          ]},
          officePhone: {rules: [
          ]},
          duty: {rules: [
          ]},
          depoaitAccount: {rules: [
            { validator: (rule, value, callback) => validateDuplicateValue('user_purchase_contract_b', 'depoait_account', value, this.model.id, callback)},
          ]},
          contractFile: {rules: [
          ]},
        },
        url: {
          add: "/userpurchasecontractb/userPurchaseContractB/add",
          edit: "/userpurchasecontractb/userPurchaseContractB/edit",
          importExcelUrl: "userpurchasecontractb/userPurchaseContractB/importExcel",
        },
        transactionType: 'sb',
        tokenHeader: {'X-Access-Token': Vue.ls.get(ACCESS_TOKEN)},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
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
          this.form.setFieldsValue(pick(this.model,'contractName','userName','transactionType','beginDate','endDate','purchaseElectricity','purchasePrice','monthElectricity','name','phone','officePhone','duty','depoaitAccount','contractFile'))
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
            values.userName = this.cusname1;
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                console.log("ccccccccccccccc=")
                that.$emit('ok1','aaaaa');
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
        this.form.setFieldsValue(pick(row,'contractName','userName','transactionType','beginDate','endDate','purchaseElectricity','purchasePrice','monthElectricity','name','phone','officePhone','duty','depoaitAccount','contractFile'))
      },
      //交易类型
      handType(e){
        console.log(e)
        this.transactionType = e
      },
      monthAdd(){
        this.$refs.monthAdd.add();
      },
      handleImportExcel(){
        console.log("sssssss")
      }
      
    }
  }
</script>