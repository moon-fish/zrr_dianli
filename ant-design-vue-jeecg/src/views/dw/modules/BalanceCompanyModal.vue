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
          <a-input v-decorator="[ 'userName', validatorRules.userName]" placeholder="请输入用户名称"></a-input>
        </a-form-item>
        <a-form-item label="批发成交电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucElec', validatorRules.sucElec]" placeholder="请输入批发成交电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="成交均价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucPrice', validatorRules.sucPrice]" placeholder="请输入成交均价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批发结算电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucMoney', validatorRules.sucMoney]" placeholder="请输入批发结算电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="实际用电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cusElec', validatorRules.cusElec]" placeholder="请输入实际用电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="协议电价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cusPrice', validatorRules.cusPrice]" placeholder="请输入协议电价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="零售结算电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cusMoney', validatorRules.cusMoney]" placeholder="请输入零售结算电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="偏差考核电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'allOffsetElec', validatorRules.allOffsetElec]" placeholder="请输入偏差考核电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="偏差考核电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'allOffsetMoney', validatorRules.allOffsetMoney]" placeholder="请输入偏差考核电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="用户偏差电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cusOffsetElec', validatorRules.cusOffsetElec]" placeholder="请输入用户偏差电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="用户偏差电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'cusOffsetMoney', validatorRules.cusOffsetMoney]" placeholder="请输入用户偏差电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="售电公司结算电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucBalanceElec', validatorRules.sucBalanceElec]" placeholder="请输入售电公司结算电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="售电公司结算电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucBalanceMoney', validatorRules.sucBalanceMoney]" placeholder="请输入售电公司结算电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="售电公司偏差电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'sucOffsetMoney', validatorRules.sucOffsetMoney]" placeholder="请输入售电公司偏差电费" style="width: 100%"/>
        </a-form-item>
        <!--<a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--<a-input v-decorator="[ 'market', validatorRules.market]" placeholder="请输入备注"></a-input>-->
        <!--</a-form-item>-->

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: "BalanceCompanyModal",
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
          userName: {rules: [
          ]},
          sucElec: {rules: [
          ]},
          sucPrice: {rules: [
          ]},
          sucMoney: {rules: [
          ]},
          cusElec: {rules: [
          ]},
          cusPrice: {rules: [
          ]},
          cusMoney: {rules: [
          ]},
          allOffsetElec: {rules: [
          ]},
          allOffsetMoney: {rules: [
          ]},
          cusOffsetElec: {rules: [
          ]},
          cusOffsetMoney: {rules: [
          ]},
          sucBalanceElec: {rules: [
          ]},
          sucBalanceMoney: {rules: [
          ]},
          sucOffsetMoney: {rules: [
          ]},
          market: {rules: [
          ]},
        },
        url: {
          add: "/balancecompany/balanceCompany/add",
          edit: "/balancecompany/balanceCompany/edit",
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
          this.form.setFieldsValue(pick(this.model,'userName','sucElec','sucPrice','sucMoney','cusElec','cusPrice','cusMoney','allOffsetElec','allOffsetMoney','cusOffsetElec','cusOffsetMoney','sucBalanceElec','sucBalanceMoney','sucOffsetMoney','market'))
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
        this.form.setFieldsValue(pick(row,'userName','sucElec','sucPrice','sucMoney','cusElec','cusPrice','cusMoney','allOffsetElec','allOffsetMoney','cusOffsetElec','cusOffsetMoney','sucBalanceElec','sucBalanceMoney','sucOffsetMoney','market'))
      },

      
    }
  }
</script>