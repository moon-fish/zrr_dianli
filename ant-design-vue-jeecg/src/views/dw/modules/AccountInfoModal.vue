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

        <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'serNumber', validatorRules.serNumber]" placeholder="请输入序号"></a-input>
        </a-form-item>
        <a-form-item label="地市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressShi', validatorRules.addressShi]" placeholder="请输入地市"></a-input>
        </a-form-item>
        <a-form-item label="区县" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressXian', validatorRules.addressXian]" placeholder="请输入区县"></a-input>
        </a-form-item>
        <a-form-item label="站址名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressDetail', validatorRules.addressDetail]" placeholder="请输入站址名称"></a-input>
        </a-form-item>
        <a-form-item label="站址编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressCode', validatorRules.addressCode]" placeholder="请输入站址编码"></a-input>
        </a-form-item>
        <a-form-item label="供电方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'supplyType', validatorRules.supplyType]" placeholder="请输入供电方式"></a-input>
        </a-form-item>
        <a-form-item label="户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'accountName', validatorRules.accountName]" placeholder="请输入户名"></a-input>
        </a-form-item>
        <a-form-item label="户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'accountNumber', validatorRules.accountNumber]" placeholder="请输入户号"></a-input>
        </a-form-item>
        <a-form-item label="计量点供电电压" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'supplyVoltage', validatorRules.supplyVoltage]" placeholder="请输入计量点供电电压"></a-input>
        </a-form-item>
        <a-form-item label="用电地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressElecuse', validatorRules.addressElecuse]" placeholder="请输入用电地址"></a-input>
        </a-form-item>
        <a-form-item label="供电单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'supplyUnit', validatorRules.supplyUnit]" placeholder="请输入供电单位"></a-input>
        </a-form-item>
        <a-form-item label="一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'yi', validatorRules.yi]" placeholder="请输入一月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'er', validatorRules.er]" placeholder="请输入二月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="三月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'san', validatorRules.san]" placeholder="请输入三月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="四月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'si', validatorRules.si]" placeholder="请输入四月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="五月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'wu', validatorRules.wu]" placeholder="请输入五月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="六月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'liu', validatorRules.liu]" placeholder="请输入六月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="七月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'qi', validatorRules.qi]" placeholder="请输入七月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="八月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ba', validatorRules.ba]" placeholder="请输入八月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="九月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'jiu', validatorRules.jiu]" placeholder="请输入九月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="十月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shi', validatorRules.shi]" placeholder="请输入十月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="十一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shiyi', validatorRules.shiyi]" placeholder="请输入十一月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="十二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'shier', validatorRules.shier]" placeholder="请输入十二月" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'maker', validatorRules.maker]" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "AccountInfoModal",
    components: { 
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
          serNumber: {rules: [
          ]},
          addressShi: {rules: [
          ]},
          addressXian: {rules: [
          ]},
          addressDetail: {rules: [
          ]},
          addressCode: {rules: [
          ]},
          supplyType: {rules: [
          ]},
          accountName: {rules: [
          ]},
          accountNumber: {rules: [
          ]},
          supplyVoltage: {rules: [
          ]},
          addressElecuse: {rules: [
          ]},
          supplyUnit: {rules: [
          ]},
          yi: {rules: [
          ]},
          er: {rules: [
          ]},
          san: {rules: [
          ]},
          si: {rules: [
          ]},
          wu: {rules: [
          ]},
          liu: {rules: [
          ]},
          qi: {rules: [
          ]},
          ba: {rules: [
          ]},
          jiu: {rules: [
          ]},
          shi: {rules: [
          ]},
          shiyi: {rules: [
          ]},
          shier: {rules: [
          ]},
          maker: {rules: [
          ]},
        },
        url: {
          add: "/accountinfo/accountInfo/add",
          edit: "/accountinfo/accountInfo/edit",
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
          this.form.setFieldsValue(pick(this.model,'serNumber','addressShi','addressXian','addressDetail','addressCode','supplyType','accountName','accountNumber','supplyVoltage','addressElecuse','supplyUnit','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','maker'))
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
        this.form.setFieldsValue(pick(row,'serNumber','addressShi','addressXian','addressDetail','addressCode','supplyType','accountName','accountNumber','supplyVoltage','addressElecuse','supplyUnit','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','maker'))
      },

      
    }
  }
</script>