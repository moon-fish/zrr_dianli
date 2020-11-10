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

        <a-form-item label="用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'username', validatorRules.username]" placeholder="请输入用户名称"></a-input>
        </a-form-item>
        <a-form-item label="电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecNum', validatorRules.elecNum]" placeholder="请输入电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecPrice', validatorRules.elecPrice]" placeholder="请输入单价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecCharge', validatorRules.elecCharge]" placeholder="请输入电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="节省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecSave', validatorRules.elecSave]" placeholder="请输入节省" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="费用月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择费用月份" v-decorator="[ 'monthDate', validatorRules.monthDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="费用年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择费用年份" v-decorator="[ 'yearDate', validatorRules.yearDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="备用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzd', validatorRules.byzd]" placeholder="请输入备用"></a-input>
        </a-form-item>
        <a-form-item label="备用2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzdt', validatorRules.byzdt]" placeholder="请输入备用2"></a-input>
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
    name: "BalanceManageModal",
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
          elecNum: {rules: [
          ]},
          elecPrice: {rules: [
          ]},
          elecCharge: {rules: [
          ]},
          elecSave: {rules: [
          ]},
          monthDate: {rules: [
          ]},
          yearDate: {rules: [
          ]},
          byzd: {rules: [
          ]},
          byzdt: {rules: [
          ]},
        },
        url: {
          add: "/balancemanage/balanceManage/add",
          edit: "/balancemanage/balanceManage/edit",
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
          this.form.setFieldsValue(pick(this.model,'username','elecNum','elecPrice','elecCharge','elecSave','monthDate','yearDate','byzd','byzdt'))
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
        this.form.setFieldsValue(pick(row,'username','elecNum','elecPrice','elecCharge','elecSave','monthDate','yearDate','byzd','byzdt'))
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