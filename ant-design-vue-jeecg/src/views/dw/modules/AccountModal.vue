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

        <a-form-item label="地市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地市"></a-input>
        </a-form-item>
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始时间" v-decorator="[ 'dateBegin', validatorRules.dateBegin]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'dateEnd', validatorRules.dateEnd]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="供电单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'elecApply', validatorRules.elecApply]" placeholder="请输入供电单位"></a-input>
        </a-form-item>
        <a-form-item label="户号数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'accountNumber', validatorRules.accountNumber]" placeholder="请输入户号数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="新增用户数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'addNumber', validatorRules.addNumber]" placeholder="请输入新增用户数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="减少用户数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'reduceNumber', validatorRules.reduceNumber]" placeholder="请输入减少用户数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="新增用户电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'addElec', validatorRules.addElec]" placeholder="请输入新增用户电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marker', validatorRules.marker]" placeholder="请输入备注"></a-input>
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
    name: "AccountModal",
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
          address: {rules: [
          ]},
          dateBegin: {rules: [
          ]},
          dateEnd: {rules: [
          ]},
          elecApply: {rules: [
          ]},
          accountNumber: {rules: [
          ]},
          addNumber: {rules: [
          ]},
          reduceNumber: {rules: [
          ]},
          addElec: {rules: [
          ]},
          marker: {rules: [
          ]},
        },
        url: {
          add: "/account/account/add",
          edit: "/account/account/edit",
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
          this.form.setFieldsValue(pick(this.model,'address','dateBegin','dateEnd','elecApply','accountNumber','addNumber','reduceNumber','addElec','marker'))
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
        this.form.setFieldsValue(pick(row,'address','dateBegin','dateEnd','elecApply','accountNumber','addNumber','reduceNumber','addElec','marker'))
      },

      
    }
  }
</script>