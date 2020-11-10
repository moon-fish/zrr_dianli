<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    okText="确定"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row style="margin-bottom: 10px;" :gutter="24">
          <!--发电侧基本信息-->
          <a-col :xl="8" :md="8" :sm="24">
            <div :bordered="true" style="margin-right: 5px;padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">发电侧基本信息</span>
              </div>
              <a-form-item label="电站名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="[ 'stationName', validatorRules.stationName]" placeholder="请输入电站名称"></a-input>
              </a-form-item>
              <a-form-item label="装机容量" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="[ 'installedCapacity', validatorRules.installedCapacity]" placeholder="请输入装机容量"></a-input>
              </a-form-item>
              <a-form-item label="联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入联系人姓名"></a-input>
              </a-form-item>
              <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码" ></a-input>
              </a-form-item>
            </div>
          </a-col>
          <!--电量负荷-->
          <a-col :xl="16" :md="16" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">交易电量</span>
                <!--<span style="float: right">-->
                  <!--<a-radio-group v-model="loadValue" @change="loadOnChange">-->
                    <!--<a-radio-button value="a">-->
                      <!--本年-->
                    <!--</a-radio-button>-->
                    <!--<a-radio-button value="b">-->
                      <!--本月-->
                    <!--</a-radio-button>-->
                  <!--</a-radio-group>-->
                <!--</span>-->
              </div>
              <!--<bar-and-line2></bar-and-line2>-->
              <bar-multid :dataSource="barData" :fields="barFields"></bar-multid>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-bottom: 10px;" :gutter="24">
          <!--合同统计-->
          <a-col :xl="8" :md="8" :sm="24">
            <div :bordered="true" style="margin-right: 5px;padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">交易统计</span>
              </div>
              <a-form-item label="合同数量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.contractNum.toString()" placeholder="请输入企业全称"></a-input>
              </a-form-item>
              <a-form-item label="合同量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.contractAll.toFixed(2)" placeholder="请输入行业类别" ></a-input>
              </a-form-item>
              <a-form-item label="合同均价" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.contractAvg.toFixed(2)" placeholder="请输入联系人姓名" ></a-input>
              </a-form-item>
              <!--<a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">-->
              <!--<a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码" ></a-input>-->
              <!--</a-form-item>-->
            </div>
          </a-col>
          <!--合同详细-->
          <a-col :xl="16" :md="16" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">交易信息</span>
                <a-button type="primary" icon="download" @click="handleExportXls('电力用户购电合同')" style="float: right;">导出</a-button>
                <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel" style="float: right;margin-right: 10px;">
                  <a-button type="primary" icon="import">导入</a-button>
                </a-upload>
              </div>
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource1"
                :pagination="ipagination"
                :loading="loading"

                @change="handleTableChange">

                <template slot="htmlSlot" slot-scope="text">
                  <div v-html="text"></div>
                </template>
                <template slot="imgSlot" slot-scope="text">
                  <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
                  <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
                </template>
                <template slot="fileSlot" slot-scope="text">
                  <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
                  <a-button
                    v-else
                    :ghost="true"
                    type="primary"
                    icon="download"
                    size="small"
                    @click="uploadFile(text)">
                    下载
                  </a-button>
                </template>

                <span slot="action" slot-scope="text, record">
                  <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

                  <a-divider type="vertical" />
                  <a-dropdown v-has="'admin:delete'">
                    <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
                    <a-menu slot="overlay">
                      <a-menu-item>
                        <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                          <a>删除</a>
                        </a-popconfirm>
                      </a-menu-item>
                    </a-menu>
                  </a-dropdown>
                </span>

              </a-table>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>


  </a-modal>
</template>

<script>
  import BarMultid from '@/components/chart/BarMultid'
  import BarAndLine2 from '@/components/chart/BarAndLine2'
  import pick from 'lodash.pick'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'

  export default {
    name: 'GenerationSideDetailModal',
    mixins:[JeecgListMixin],
    components: {
      BarMultid,
      BarAndLine2
    },
    data () {
      return {
        title:"操作",
        width:1200,
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
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
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
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
            title:'用户名称',
            align:"center",
            dataIndex: 'userName'
          },
          {
            title:'交易类型',
            align:"center",
            dataIndex: 'transactionType',
            customRender:function (t,r){
              if(r.transactionType === 'sb'){
                r.transactionType = '双边'
              }
              return r.transactionType
            }
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'结束时间',
          //   align:"center",
          //   dataIndex: 'endDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'购电量',
            align:"center",
            dataIndex: 'purchaseElectricity'
          },
          // {
          //   title:'开户银行',
          //   align:"center",
          //   dataIndex: 'depoaitBank'
          // },
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
            title:'购电价',
            align:"center",
            dataIndex: 'purchasePrice'
          },
          // {
          //   title:'状态',
          //   align:"center",
          //   dataIndex: 'contractStatus',
          //   scopedSlots: { customRender: 'contractStatus' }
          // },
          // {
          //   title:'累计执行电量',
          //   align:"center",
          //   dataIndex: 'b'
          // },
          // {
          //   title:'已获收益',
          //   align:"center",
          //   dataIndex: 'c'
          // },

          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        url: {
          list: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/list",
          delete: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/delete",
          deleteBatch: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/deleteBatch",
          exportXlsUrl: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/exportXls",
          importExcelUrl: "sellpowerpurchasecontractb/sellpowerPurchaseContractB/importExcel",
        },
        ipagination:{
          current: 1,
          pageSize: 3,
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: false,
          total: 0
        },
        lineValue: 'a',
        loadValue: 'a',
        barFields:['1月', '2月', '3月', '4月', '5月', '6月'],
        barData: [],
        dataSource1: [],
        cusName:'',
        contractNum: 0,
        contractAll: 0,
        contractAvg: 0,
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      show(record){
        //加载合同信息
        this.cusName = record.stationName
        this.loadContract();
        //加载柱状图数据
        this.loadBar();
        //赋值
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'stationName','installedCapacity','address','distance','name','duty','phone','officePhone','installedCapacityAll','unitType'))
        })

      },
      handleOk () {
        this.close()
      },
      handleCancel(){
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      //交易电量
      // loadOnChange(e){
      //   console.log(`checked = ${e.target.value}`);
      //   this.loadValue = e.target.value;
      //   //加载柱状图信息
      //   this.loadBar();
      // },
      //柱状图
      loadBar(){
        this.barFields = []
        this.barData = [];
        console.log(this.loadValue)
        let date = new Date();
        let y = date.getFullYear();
        getAction('/mytrade/myTrade/queryall',{ elecname: this.cusName,year:y }).then((res) => {
          if(res.success){
            console.log(res)
            //横坐标
            for(let i=1;i<=12;i++){
              this.barFields.push(i+'月');
            }
            console.log(this.barFields)
            //给柱状图赋值
            for(let i=0;i<3;i++){
              this.barData.push({})
            }
            this.barData[0]['type'] = '双边'
            this.barData[1]['type'] = '挂牌'
            this.barData[2]['type'] = '合同转让'
            for(let i=0;i<this.barData.length;i++){
              for(let j=0;j<res.result.length;j++){
                if(this.barData[i]['type'] === res.result[j].tradeType){
                  if(this.barData[i][parseInt(res.result[j].tradeBegin.split("-")[1])+'月'] === undefined){
                    this.barData[i][parseInt(res.result[j].tradeBegin.split("-")[1])+'月'] = 0;
                  }
                  this.barData[i][parseInt(res.result[j].tradeBegin.split("-")[1])+'月'] += res.result[j].tradeElec
                }
              }
            }

          }
        })
      },
      //加载合同信息
      loadContract(){
        this.dataSource1 = [];
        this.contractAll = 0;
        this.contractAvg = 0;
        this.contractNum = 0;
        console.log(this.cusName)
        getAction(this.url.list,{ cusname: this.cusName }).then((res) => {
          if(res.success){
            if(res.result.records.length>0){
              let contractPriceAll = 0;
              this.dataSource1 = res.result.records
              this.ipagination.total = res.result.total;
              this.contractNum = res.result.total
              for(let i=0;i<res.result.records.length;i++){
                this.contractAll += parseFloat(res.result.records[i].purchaseElectricity)
                contractPriceAll += res.result.records[i].purchasePrice
              }
              this.contractAvg = contractPriceAll/res.result.records.length
            }

          }
        })
      },

    },
  }
</script>

<style scoped>

</style>