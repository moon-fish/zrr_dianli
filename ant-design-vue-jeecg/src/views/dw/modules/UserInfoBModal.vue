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

        <a-tabs :activeKey="this.activeKey" @change="callback">
          <a-tab-pane tab="基本信息" key="1">
            <a-form-item label="企业全称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'comAName', validatorRules.comAName]" placeholder="请输入企业全称"></a-input>
            </a-form-item>
            <a-form-item label="公司总名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'comTName', validatorRules.comTName]" placeholder="请输入公司总名称"></a-input>
            </a-form-item>
            <a-form-item label="企业简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'comSName', validatorRules.comSName]" placeholder="请输入企业简称"></a-input>
            </a-form-item>
            <a-form-item label="地域信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'addressInfo', validatorRules.addressInfo]" placeholder="请输入地域信息"></a-input>-->
              <a-select @change="handArea" v-decorator="[ 'addressInfo', {initialValue:this.addressInfo,rules: validatorRules.addressInfo.rules}]">
                <a-select-option value="太原">太原</a-select-option>
                <a-select-option value="大同">大同</a-select-option>
                <a-select-option value="朔州">朔州</a-select-option>
                <a-select-option value="忻州">忻州</a-select-option>
                <a-select-option value="阳泉">阳泉</a-select-option>
                <a-select-option value="吕梁">吕梁</a-select-option>
                <a-select-option value="晋中">晋中</a-select-option>
                <a-select-option value="临汾">临汾</a-select-option>
                <a-select-option value="运城">运城</a-select-option>
                <a-select-option value="晋城">晋城</a-select-option>
                <a-select-option value="长治">长治</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="行业类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'industry', validatorRules.industry]" placeholder="请输入行业类别"></a-input>-->
              <a-select @change="handType" v-decorator="[ 'industry', {initialValue:this.industry,rules: validatorRules.industry.rules}]">
                <a-select-option value="sb">农业</a-select-option>
                <a-select-option value="a">林业</a-select-option>
                <a-select-option value="b">牧业</a-select-option>
                <a-select-option value="c">渔业</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="企业所在工业园区" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'industrialSite', validatorRules.industrialSite]" placeholder="请输入企业所在工业园区"></a-input>
            </a-form-item>
            <a-form-item label="客户质量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'cusQuality', validatorRules.cusQuality]" placeholder="请输入客户质量"></a-input>-->
              <a-select @change="cusQualityChange" v-decorator="[ 'cusQuality', {initialValue:this.cusQuality,rules: validatorRules.cusQuality.rules}]">
                <a-select-option value="0">优质</a-select-option>
                <a-select-option value="1">普通</a-select-option>
                <a-select-option value="2">劣质</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="供电区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'supplyArea', validatorRules.supplyArea]" placeholder="请输入供电区域"></a-input>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane tab="通讯信息" key="2" forceRender>
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
          </a-tab-pane>
          <!--<a-tab-pane tab="商务信息" key="3">-->
            <!--<a-form-item label="统一社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'socialCredit', validatorRules.socialCredit]" placeholder="请输入统一社会信用代码"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<j-date placeholder="请选择成立日期" v-decorator="[ 'establishment', validatorRules.establishment]" :trigger-change="true" style="width: 100%"/>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="法人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'legalName', validatorRules.legalName]" placeholder="请输入法人名称"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="企业注册地址" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'registeredAddress', validatorRules.registeredAddress]" placeholder="请输入企业注册地址"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="法定代表人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'legalRepresentativeName', validatorRules.legalRepresentativeName]" placeholder="请输入法定代表人姓名"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="组织机构代码" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'orgCode', validatorRules.orgCode]" placeholder="请输入组织机构代码"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="开户银行" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'depositBank', validatorRules.depositBank]" placeholder="请输入开户银行"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="开户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'depositName', validatorRules.depositName]" placeholder="请输入开户名称"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="开户账号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'depoaitAccount', validatorRules.depoaitAccount]" placeholder="请输入开户账号"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="税务登记证号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'registerNumber', validatorRules.registerNumber]" placeholder="请输入税务登记证号"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="注册资本" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input-number v-decorator="[ 'registerMoney', validatorRules.registerMoney]" placeholder="请输入注册资本" style="width: 100%"/>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="资产总额" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input-number v-decorator="[ 'totalAssets', validatorRules.totalAssets]" placeholder="请输入资产总额" style="width: 100%"/>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="电力业务许可证编号" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'licenceNo', validatorRules.licenceNo]" placeholder="请输入电力业务许可证编号"></a-input>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="许可证生效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<j-date placeholder="请选择许可证生效日期" v-decorator="[ 'effectiveDate', validatorRules.effectiveDate]" :trigger-change="true" style="width: 100%"/>-->
            <!--</a-form-item>-->
            <!--<a-form-item label="许可证失效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<j-date placeholder="请选择许可证失效日期" v-decorator="[ 'expiryDate', validatorRules.expiryDate]" :trigger-change="true" style="width: 100%"/>-->
            <!--</a-form-item>-->
          <!--</a-tab-pane>-->
          <a-tab-pane tab="参数信息" key="3">
            <a-form-item label="合同容量（MVA）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'contractCapacity', validatorRules.contractCapacity]" placeholder="请输入合同容量（MVA）"></a-input>
            </a-form-item>
            <a-form-item label="运行容量（MVA）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'operatingCapacity', validatorRules.operatingCapacity]" placeholder="请输入运行容量（MVA）"></a-input>
            </a-form-item>
            <a-form-item label="最高供电电压" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'maxVoltage', validatorRules.maxVoltage]" placeholder="请输入最高供电电压" style="width: 100%"/>
            </a-form-item>
            <a-form-item label="用户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'userType', validatorRules.userType]" placeholder="请输入运行容量（MVA）"></a-input>
            </a-form-item>
            <a-form-item label="电压等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'voltageLevel', validatorRules.voltageLevel]" placeholder="请输入最高供电电压" style="width: 100%"/>
            </a-form-item>
            <a-form-item label="用户序列" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!--<a-input v-decorator="[ 'userOrder', validatorRules.userOrder]" placeholder="请输入月度用电信息" style="width: 100%"/>-->
              <a-select @change="userOrderChange" v-decorator="[ 'userOrder', {initialValue:this.userOrder,rules: validatorRules.userOrder.rules}]">
                <a-select-option value="0">年度</a-select-option>
                <a-select-option value="1">月度</a-select-option>
                <a-select-option value="2">地电年度</a-select-option>
                <a-select-option value="3">地电月度</a-select-option>
              </a-select>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane tab="用电信息" key="4">
            <a-form-item label="月度用电信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'monthElecInfo', validatorRules.monthElecInfo]" placeholder="请输入月度用电信息" style="width: 100%"/>
            </a-form-item>
            <a-form-item label="年度用电信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'yearElecInfo', validatorRules.yearElecInfo]" placeholder="请输入年度用电信息" style="width: 100%"/>
            </a-form-item>
          </a-tab-pane>

          <a-tab-pane tab="合同信息" key="5" v-if="this.isShow">
            <user-purchase-contract-b-list :cusname="this.cusName"></user-purchase-contract-b-list>
          </a-tab-pane >

        </a-tabs>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import UserPurchaseContractBList from '../UserPurchaseContractBList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import UserPurchaseContractBModal from '../modules/UserPurchaseContractBModal'

  export default {
    name: "UserInfoBModal",
    mixins:[JeecgListMixin],
    components: { 
      JDate,
      UserPurchaseContractBList,
      UserPurchaseContractBModal
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:1200,
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
          comTName: {rules: [
          ]},
          comSName: {rules: [
          ]},
          cusQuality: {rules: [
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
              {pattern:/^([1-9]\d*|0)(\.\d{1,2})?$/, message: '请输入整数或者小数!'},
          ]},
          operatingCapacity: {rules: [
              {pattern:/^([1-9]\d*|0)(\.\d{1,2})?$/, message: '请输入整数或者小数!'},
          ]},
          maxVoltage: {rules: [
              {pattern:/^([1-9]\d*|0)(\.\d{1,2})?$/, message: '请输入整数或者小数!'},
          ]},
          monthElecInfo: {rules: [
          ]},
          yearElecInfo: {rules: [
          ]},
          userType: {rules: [
          ]},
          voltageLevel: {rules: [
          ]},
          userOrder: {rules: [
          ]},
        },
        url2: {
          add: "/userinfob/userInfoB/add",
          edit: "/userinfob/userInfoB/edit",
        },
        industry: 'sb',
        addressInfo: '太原',
        cusQuality: '0',
        userOrder:'0',
        cusName:'',
        isShow: false,
        activeKey: "1",
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'合同名称',
            align:"center",
            dataIndex: 'contractName'
          },
          {
            title:'电力用户名称',
            align:"center",
            dataIndex: 'userName'
          },
          // {
          //   title:'交易类型',
          //   align:"center",
          //   dataIndex: 'transactionType'
          // },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束时间',
            align:"center",
            dataIndex: 'endDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'购电量',
            align:"center",
            dataIndex: 'purchaseElectricity'
          },
          {
            title:'分月电量',
            align:"center",
            dataIndex: 'monthElectricity'
          },
          // {
          //   title:'联系人姓名',
          //   align:"center",
          //   dataIndex: 'name'
          // },
          // {
          //   title:'手机号码',
          //   align:"center",
          //   dataIndex: 'phone'
          // },
          // {
          //   title:'办公电话',
          //   align:"center",
          //   dataIndex: 'officePhone'
          // },
          // {
          //   title:'联系人职务',
          //   align:"center",
          //   dataIndex: 'duty'
          // },
          // {
          //   title:'开户账号',
          //   align:"center",
          //   dataIndex: 'depoaitAccount'
          // },
          // {
          //   title:'合同附件',
          //   align:"center",
          //   dataIndex: 'contractFile'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/userpurchasecontractb/userPurchaseContractB/list",
          delete: "/userpurchasecontractb/userPurchaseContractB/delete",
          deleteBatch: "/userpurchasecontractb/userPurchaseContractB/deleteBatch",
          exportXlsUrl: "/userpurchasecontractb/userPurchaseContractB/exportXls",
          importExcelUrl: "userpurchasecontractb/userPurchaseContractB/importExcel",
        },
        dictOptions:{},
        dataSource1: [],
      }
    },
    created () {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      initData(){
        console.log(this.cusName)
        this.dataSource1 = [];
        getAction(this.url.list,{ cusname: this.cusName }).then((res) => {
          if(res.success){
            console.log(res.result.records)
            this.dataSource1 = res.result.records
            this.ipagination.total = res.result.total;
          }
        })
      },
      add () {
        this.edit({});
      },
      edit (record) {
        //新增时不显示合同信息
        console.log(record.id)
        if(record.id !== undefined){
          this.isShow = true
          this.cusName = record.comAName
          this.initData()
        }else{
          this.isShow = false
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'comAName','comTName','comSName','voltageLevel','userOrder','userType','cusQuality','addressInfo','industry','industrialSite','supplyArea','address','email','name','phone','officePhone','duty','ems','internet','socialCredit','establishment','legalName','registeredAddress','legalRepresentativeName','orgCode','depositBank','depositName','depoaitAccount','registerNumber','registerMoney','totalAssets','licenceNo','effectiveDate','expiryDate','contractCapacity','operatingCapacity','maxVoltage','monthElecInfo','yearElecInfo'))
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
              httpurl+=this.url2.add;
              method = 'post';
            }else{
              httpurl+=this.url2.edit;
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
        this.form.setFieldsValue(pick(row,'comAName','comTName','comSName','cusQuality','userOrder','voltageLevel','userType','addressInfo','industry','industrialSite','supplyArea','address','email','name','phone','officePhone','duty','ems','internet','socialCredit','establishment','legalName','registeredAddress','legalRepresentativeName','orgCode','depositBank','depositName','depoaitAccount','registerNumber','registerMoney','totalAssets','licenceNo','effectiveDate','expiryDate','contractCapacity','operatingCapacity','maxVoltage','monthElecInfo','yearElecInfo'))
      },
      callback(key) {
        console.log(key);
        this.activeKey = key
      },
      handType(e){
        console.log(e)
        this.industry = e
      },
      cusQualityChange(e){
        console.log(e)
        this.cusQuality = e
      },
      handArea(e){
        console.log(e)
        this.addressInfo = e
      },
      userOrderChange(e){
        console.log(e)
        this.userOrder = e;
      },
      modalFormOk1(){
        console.log("okkkkkkkkkkkkkkkkkkk");
        this.initData();
      }

      
    }
  }
</script>