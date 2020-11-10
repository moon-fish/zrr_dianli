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

        <a-form-item label="企业全称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'comAName', validatorRules.comAName]" placeholder="请输入企业全称"></a-input>
        </a-form-item>
        <a-form-item label="企业简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'comSName', validatorRules.comSName]" placeholder="请输入企业简称"></a-input>
        </a-form-item>
        <a-form-item label="地域信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'addressInfo', validatorRules.addressInfo]" placeholder="请输入地域信息"></a-input>
        </a-form-item>
        <a-form-item label="行业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'industry', validatorRules.industry]" placeholder="请输入行业类别"></a-input>
        </a-form-item>
        <a-form-item label="企业所在工业园区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'industrialSite', validatorRules.industrialSite]" placeholder="请输入企业所在工业园区"></a-input>
        </a-form-item>
        <a-form-item label="供电区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'supplyArea', validatorRules.supplyArea]" placeholder="请输入供电区域"></a-input>
        </a-form-item>
        <a-form-item label="通讯地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入通讯地址"></a-input>
        </a-form-item>
        <a-form-item label="邮政编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'email', validatorRules.email]" placeholder="请输入邮政编码"></a-input>
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
        <a-form-item label="传真" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ems', validatorRules.ems]" placeholder="请输入传真"></a-input>
        </a-form-item>
        <a-form-item label="网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'internet', validatorRules.internet]" placeholder="请输入网址"></a-input>
        </a-form-item>
        <a-form-item label="统一社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'socialCredit', validatorRules.socialCredit]" placeholder="请输入统一社会信用代码"></a-input>
        </a-form-item>
        <a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择成立日期" v-decorator="[ 'establishment', validatorRules.establishment]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="法人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'legalName', validatorRules.legalName]" placeholder="请输入法人名称"></a-input>
        </a-form-item>
        <a-form-item label="企业注册地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'registeredAddress', validatorRules.registeredAddress]" placeholder="请输入企业注册地址"></a-input>
        </a-form-item>
        <a-form-item label="法定代表人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'legalRepresentativeName', validatorRules.legalRepresentativeName]" placeholder="请输入法定代表人姓名"></a-input>
        </a-form-item>
        <a-form-item label="组织机构代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'orgCode', validatorRules.orgCode]" placeholder="请输入组织机构代码"></a-input>
        </a-form-item>
        <a-form-item label="开户银行" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'depositBank', validatorRules.depositBank]" placeholder="请输入开户银行"></a-input>
        </a-form-item>
        <a-form-item label="开户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'depositName', validatorRules.depositName]" placeholder="请输入开户名称"></a-input>
        </a-form-item>
        <a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'depoaitAccount', validatorRules.depoaitAccount]" placeholder="请输入开户账号"></a-input>
        </a-form-item>
        <a-form-item label="税务登记证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'registerNumber', validatorRules.registerNumber]" placeholder="请输入税务登记证号"></a-input>
        </a-form-item>
        <a-form-item label="注册资本" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'registerMoney', validatorRules.registerMoney]" placeholder="请输入注册资本" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="资产总额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'totalAssets', validatorRules.totalAssets]" placeholder="请输入资产总额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="电力业务许可证编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'licenceNo', validatorRules.licenceNo]" placeholder="请输入电力业务许可证编号"></a-input>
        </a-form-item>
        <a-form-item label="许可证生效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择许可证生效日期" v-decorator="[ 'effectiveDate', validatorRules.effectiveDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="许可证失效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择许可证失效日期" v-decorator="[ 'expiryDate', validatorRules.expiryDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="合同容量（MVA）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'contractCapacity', validatorRules.contractCapacity]" placeholder="请输入合同容量（MVA）"></a-input>
        </a-form-item>
        <a-form-item label="运行容量（MVA）" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'operatingCapacity', validatorRules.operatingCapacity]" placeholder="请输入运行容量（MVA）"></a-input>
        </a-form-item>
        <a-form-item label="最高供电电压" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maxVoltage', validatorRules.maxVoltage]" placeholder="请输入最高供电电压" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="月度用电信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'monthElecInfo', validatorRules.monthElecInfo]" placeholder="请输入月度用电信息" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年度用电信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'yearElecInfo', validatorRules.yearElecInfo]" placeholder="请输入年度用电信息" style="width: 100%"/>
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
    name: "UserInfoBModal",
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
          comAName: {rules: [
          ]},
          comSName: {rules: [
          ]},
          addressInfo: {rules: [
          ]},
          industry: {rules: [
          ]},
          industrialSite: {rules: [
          ]},
          supplyArea: {rules: [
          ]},
          address: {rules: [
          ]},
          email: {rules: [
           {pattern:/^[1-9]\d{5}$/, message: '请输入正确的邮政编码!'},
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
          ems: {rules: [
          ]},
          internet: {rules: [
            {pattern:/^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/, message: '请输入正确的网址!'},
          ]},
          socialCredit: {rules: [
          ]},
          establishment: {rules: [
          ]},
          legalName: {rules: [
          ]},
          registeredAddress: {rules: [
          ]},
          legalRepresentativeName: {rules: [
          ]},
          orgCode: {rules: [
          ]},
          depositBank: {rules: [
          ]},
          depositName: {rules: [
          ]},
          depoaitAccount: {rules: [
          ]},
          registerNumber: {rules: [
          ]},
          registerMoney: {rules: [
           {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ]},
          totalAssets: {rules: [
           {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ]},
          licenceNo: {rules: [
          ]},
          effectiveDate: {rules: [
          ]},
          expiryDate: {rules: [
          ]},
          contractCapacity: {rules: [
          ]},
          operatingCapacity: {rules: [
          ]},
          maxVoltage: {rules: [
            {pattern:/^-?\d+$/, message: '请输入整数!'},
          ]},
          monthElecInfo: {rules: [
          ]},
          yearElecInfo: {rules: [
          ]},
        },
        url: {
          add: "/userinfob/userInfoB/add",
          edit: "/userinfob/userInfoB/edit",
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
          this.form.setFieldsValue(pick(this.model,'comAName','comSName','addressInfo','industry','industrialSite','supplyArea','address','email','name','phone','officePhone','duty','ems','internet','socialCredit','establishment','legalName','registeredAddress','legalRepresentativeName','orgCode','depositBank','depositName','depoaitAccount','registerNumber','registerMoney','totalAssets','licenceNo','effectiveDate','expiryDate','contractCapacity','operatingCapacity','maxVoltage','monthElecInfo','yearElecInfo'))
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
        this.form.setFieldsValue(pick(row,'comAName','comSName','addressInfo','industry','industrialSite','supplyArea','address','email','name','phone','officePhone','duty','ems','internet','socialCredit','establishment','legalName','registeredAddress','legalRepresentativeName','orgCode','depositBank','depositName','depoaitAccount','registerNumber','registerMoney','totalAssets','licenceNo','effectiveDate','expiryDate','contractCapacity','operatingCapacity','maxVoltage','monthElecInfo','yearElecInfo'))
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