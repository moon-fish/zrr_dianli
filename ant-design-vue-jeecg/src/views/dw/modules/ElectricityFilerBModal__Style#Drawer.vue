<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="申报企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerCompanyName', validatorRules.filerCompanyName]" placeholder="请输入申报企业名称"></a-input>
        </a-form-item>
        <a-form-item label="负荷预测" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'loadForecasting', validatorRules.loadForecasting]" placeholder="请输入负荷预测"></a-input>
        </a-form-item>
        <a-form-item label="交易类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transactionType', validatorRules.transactionType]" placeholder="请输入交易类型"></a-input>
        </a-form-item>
        <a-form-item label="市场报价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marketQuotation', validatorRules.marketQuotation]" placeholder="请输入市场报价"></a-input>
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
        <a-form-item label="日志记录" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'logRecords', validatorRules.logRecords]" placeholder="请输入日志记录"></a-input>
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
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "ElectricityFilerBModal",
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
          filerCompanyName: {rules: [
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
          logRecords: {rules: [
          ]},
        },
        url: {
          add: "/electricityfilerb/electricityFilerB/add",
          edit: "/electricityfilerb/electricityFilerB/edit",
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
          this.form.setFieldsValue(pick(this.model,'filerCompanyName','loadForecasting','transactionType','marketQuotation','beginDate','endDate','filer','filerCompany','filerResult','logRecords'))
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
        this.form.setFieldsValue(pick(row,'filerCompanyName','loadForecasting','transactionType','marketQuotation','beginDate','endDate','filer','filerCompany','filerResult','logRecords'))
      }
      
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