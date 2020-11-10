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

        <a-form-item label="电站名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stationName', validatorRules.stationName]" placeholder="请输入电站名称"></a-input>
        </a-form-item>
        <a-form-item label="装机容量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'installedCapacity', validatorRules.installedCapacity]" placeholder="请输入装机容量"></a-input>
        </a-form-item>
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
        </a-form-item>
        <a-form-item label="离市区距离" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'distance', validatorRules.distance]" placeholder="请输入离市区距离" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入联系人姓名"></a-input>
        </a-form-item>
        <a-form-item label="联系人职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'duty', validatorRules.duty]" placeholder="请输入联系人职务"></a-input>
        </a-form-item>
        <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码"></a-input>
        </a-form-item>
        <a-form-item label="办公电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'officePhone', validatorRules.officePhone]" placeholder="请输入办公电话"></a-input>
        </a-form-item>
        <a-form-item label="总装机容量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'installedCapacityAll', validatorRules.installedCapacityAll]" placeholder="请输入总装机容量"></a-input>
        </a-form-item>
        <a-form-item label="机组类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'unitType', validatorRules.unitType]" placeholder="请输入机组类型"></a-input>
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
  
  export default {
    name: "GenerationSideInfoBModal",
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
          stationName: {rules: [
          ]},
          installedCapacity: {rules: [
          ]},
          address: {rules: [
          ]},
          distance: {rules: [
          ]},
          name: {rules: [
          ]},
          duty: {rules: [
          ]},
          phone: {rules: [
          ]},
          officePhone: {rules: [
          ]},
          installedCapacityAll: {rules: [
          ]},
          unitType: {rules: [
          ]},
        },
        url: {
          add: "/generationsideInfob/generationSideInfoB/add",
          edit: "/generationsideInfob/generationSideInfoB/edit",
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
          this.form.setFieldsValue(pick(this.model,'stationName','installedCapacity','address','distance','name','duty','phone','officePhone','installedCapacityAll','unitType'))
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
        this.form.setFieldsValue(pick(row,'stationName','installedCapacity','address','distance','name','duty','phone','officePhone','installedCapacityAll','unitType'))
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