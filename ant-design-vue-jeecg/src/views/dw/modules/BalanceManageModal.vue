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
          <!--<a-input v-decorator="[ 'username', validatorRules.username]" placeholder="请输入用户名称"></a-input>-->
          <j-select-elec-user v-decorator="[ 'username', validatorRules.username]"></j-select-elec-user>
        </a-form-item>
        <a-form-item label="结算月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-month-picker placeholder="请选择结算月份" v-decorator="[ 'monthDate', validatorRules.monthDate]" :trigger-change="true" style="width: 100%" @change="onChange"/>
        </a-form-item>
        <a-form-item label="申报电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecFiler', validatorRules.elecFiler]" placeholder="请输入申报电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input ref="elecNum" v-decorator="[ 'elecNum', {initialValue:this.elecNum}]" @blur="num" placeholder="请输入电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input ref="elecPrice" v-decorator="[ 'elecPrice', {initialValue:this.elecPrice}]" @blur="price" placeholder="请输入单价" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'elecCharge', validatorRules.elecCharge]" placeholder="请输入电费" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="节省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'elecSave', {initialValue:this.elecSave}]" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'area', validatorRules.area]" placeholder="请输入区域" style="width: 100%"/>
        </a-form-item>
        <!--<a-form-item label="费用年份" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--<j-date placeholder="请选择费用年份" v-decorator="[ 'yearDate', validatorRules.yearDate]" :trigger-change="true" style="width: 100%"/>-->
        <!--</a-form-item>-->
        <a-form-item label="考核电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzd', validatorRules.byzd]" placeholder="请输入备用"></a-input>
        </a-form-item>
        <a-form-item label="考核电费" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzdt', validatorRules.byzdt]" placeholder="请输入备用2"></a-input>
        </a-form-item>
        <a-form-item label="负偏" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'percent', validatorRules.percent]" placeholder="请输入备用2"></a-input>
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
  import moment from "moment"
  import JSelectElecUser from '../JSelectElecUser'

  export default {
    name: "BalanceManageModal",
    components: { 
      JDate,
      JSelectElecUser
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
          area: {rules: [
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
          elecFiler: {rules: [
          ]},
          percent: {rules: [
          ]},
        },
        url: {
          add: "/balancemanage/balanceManage/add",
          edit: "/balancemanage/balanceManage/edit",
        },
        elecPrice: 0,
        elecNum: 0,
      }
    },
    created () {
    },
    computed:{
      elecSave: function(){
        let save = this.elecNum * (346.05-this.elecPrice)
        return parseFloat(save.toFixed(3))
      }
    },
    methods: {
      num(){
        this.elecNum = this.$refs.elecNum.value
        console.log(this.elecNum)
      },
      price(){
        this.elecPrice = this.$refs.elecPrice.value
        console.log(this.elecPrice)
      },
      add () {
        this.edit({});
      },
      edit (record) {
        let num = this.$refs.elecNum
        console.log(num)
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'username','percent','elecFiler','area','elecNum','elecPrice','elecCharge','monthDate','yearDate','byzd','byzdt'))
          this.form.setFieldsValue({monthDate:this.model.monthDate?moment(this.model.monthDate):null})
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
        this.form.setFieldsValue(pick(row,'username','percent','elecFiler','area','elecNum','elecPrice','elecCharge','elecSave','monthDate','yearDate','byzd','byzdt'))
      },
      onChange(date, dateString) {
        console.log(date, dateString);
      },
      
    }
  }
</script>