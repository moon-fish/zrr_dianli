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
        <a-form-item label="一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'yi', validatorRules.yi]" placeholder="请输入一月"></a-input>
        </a-form-item>
        <a-form-item label="二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'er', validatorRules.er]" placeholder="请输入二月"></a-input>
        </a-form-item>
        <a-form-item label="三月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'san', validatorRules.san]" placeholder="请输入三月"></a-input>
        </a-form-item>
        <a-form-item label="四月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'si', validatorRules.si]" placeholder="请输入四月"></a-input>
        </a-form-item>
        <a-form-item label="五月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wu', validatorRules.wu]" placeholder="请输入五月"></a-input>
        </a-form-item>
        <a-form-item label="六月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'liu', validatorRules.liu]" placeholder="请输入六月"></a-input>
        </a-form-item>
        <a-form-item label="七月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'qi', validatorRules.qi]" placeholder="请输入七月"></a-input>
        </a-form-item>
        <a-form-item label="八月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ba', validatorRules.ba]" placeholder="请输入八月"></a-input>
        </a-form-item>
        <a-form-item label="九月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'jiu', validatorRules.jiu]" placeholder="请输入九月"></a-input>
        </a-form-item>
        <a-form-item label="十月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shi', validatorRules.shi]" placeholder="请输入十月"></a-input>
        </a-form-item>
        <a-form-item label="十一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shiyi', validatorRules.shiyi]" placeholder="请输入十一月"></a-input>
        </a-form-item>
        <a-form-item label="十二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shier', validatorRules.shier]" placeholder="请输入十二月"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'stat', validatorRules.stat]" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="备用" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzd', validatorRules.byzd]" placeholder="请输入备用"></a-input>
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
    name: "MomthElecModal",
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
          username: {rules: [
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
          stat: {rules: [
          ]},
          byzd: {rules: [
          ]},
        },
        url: {
          add: "/monthelec/momthElec/add",
          edit: "/monthelec/momthElec/edit",
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
          this.form.setFieldsValue(pick(this.model,'username','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','stat','byzd'))
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
        this.form.setFieldsValue(pick(row,'username','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','stat','byzd'))
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