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

        <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'customerName', validatorRules.customerName]" placeholder="请输入客户名称"></a-input>-->
<!--          <j-select-elec-factory v-decorator="[ 'customerName', validatorRules.customerName]" :multi="true"></j-select-elec-factory>-->
          <j-select-role v-decorator="[ 'customerName', validatorRules.customerName]"></j-select-role>
        </a-form-item>
        <a-form-item label="交易类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'tradeType', validatorRules.tradeType]" placeholder="请输入交易类型"></a-input>-->
          <a-select @change="handType" v-decorator="[ 'tradeType', {initialValue:this.defaultType,rules: validatorRules.tradeType.rules}]">
            <a-select-option value="双边">双边</a-select-option>
            <a-select-option value="合同转让">合同转让</a-select-option>
            <a-select-option value="挂牌">挂牌</a-select-option>
          </a-select>
        </a-form-item>
        <template v-if="this.htzr">
          <a-form-item label="转让类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-decorator="[ 'zrType', {initialValue:this.zrType}]" @change="zrChange">
              <a-radio value="1">
                转入
              </a-radio>
              <a-radio value="2">
                转出
              </a-radio>
            </a-radio-group>
          </a-form-item>
          <a-form-item :label="this.zrName" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <a-input v-decorator="[ 'zrName', validatorRules.zrName]" placeholder="请输入转让方" style="width: 100%"/>
          </a-form-item>
        </template>
        <a-form-item label="交易开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择交易开始时间" v-decorator="[ 'tradeBegin', validatorRules.tradeBegin]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="交易结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择交易结束时间" v-decorator="[ 'tradeEnd', validatorRules.tradeEnd]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="交易电量" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input-number v-decorator="[ 'tradeElec', validatorRules.tradeElec]" ref="tradeElec" placeholder="请输入交易电量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="交易单价" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'tradePrice', validatorRules.tradePrice]" ref="tradePrice" placeholder="请输入交易单价" style="width: 100%"/>
        </a-form-item>
        <!--<a-form-item label="交易金额" :labelCol="labelCol" :wrapperCol="wrapperCol" >-->
          <!--<a-input-number v-decorator="[ 'tradeMoney',{initialValue:this.tradeMoney,rules: validatorRules.tradeMoney.rules}]" placeholder="请输入交易金额" style="width: 100%" disabled/>-->
        <!--</a-form-item>-->

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  // import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JSelectElecFactory from '@/views/dw/JSelectElecFactory'
  import JSelectRole from '@/components/jeecgbiz/JSelectRole'

  export default {
    name: "MyTradeModal",
    components: { 
      JDate,
      JSelectElecFactory,
      JSelectRole
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
          tradeType: {rules: [
          ]},
          tradeMoney: {rules: [
           {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ]},
          tradeDate: {rules: [
          ]},
          tradeBegin: {rules: [
              { required: true, message: '请输入开始时间!' },
            ]},
          tradeEnd: {rules: [
              { required: true, message: '请输入结束时间!' },
            ]},
          customerName: {rules: [
              { required: true, message: '请选择电厂名称!' },
          ]},
          tradePrice: {rules: [
             { required: true, message: '请输入交易单价!' },
             {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的交易单价!'},
          ]},
          zrType: {rules: [
          ]},
          zrName: {rules: [
          ]},
          tradeElec: {rules: [
              { required: true, message: '请输入交易电量!' },
              {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的交易电量!'},
          ]},
        },
        url: {
          add: "/mytrade/myTrade/add",
          edit: "/mytrade/myTrade/edit",
        },
        defaultType: '双边',
        // tradeMoney:0,
        htzr: false,
        zrType: "1",
        zrName: '出让方',
      }
    },
    created () {
    },
    methods: {
      add () {
        this.defaultType = '双边'
        this.zrType = "1"
        this.htzr = false
        this.edit({});
      },
      edit (record) {
        console.log(record.id)
        if(record.id !== undefined){
          console.log("=============")
          if(record.tradeType === '合同转让'){
            this.htzr = true
          }else{
            this.htzr = false
          }
          //给单选框赋值
          this.zrType = record.zrType
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'tradeType','zrName','zrType','tradeMoney','tradePrice','tradeBegin','tradeEnd','customerName','tradeElec'))
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
        this.form.setFieldsValue(pick(row,'tradeType','zrName','zrType','tradeMoney','tradePrice','tradeBegin','tradeEnd','customerName','tradeElec'))
      },
      //交易类型
      handType(e){
        console.log(e)
        this.defaultType = e
        if(this.defaultType === '合同转让'){
          this.htzr = true
        }else{
          this.htzr = false
        }
      },
      //转让类型
      zrChange(e){
        this.zrType = e.target.value;
        if(this.zrType === "1"){
          this.zrName = '出让方'
        }else{
          this.zrName = '受让方'
        }
      }

      
    }
  }
</script>