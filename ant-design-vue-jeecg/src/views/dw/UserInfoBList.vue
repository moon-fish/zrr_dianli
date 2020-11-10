<template>
  <a-card :bordered="false">
    <div>
      <a-row :gutter="24">
        <a-col :md="12" :sm="24">
          <div style="font-size: large;font-weight: bold;">用户数量:</div>
          <Pie :dataSource="pieData" ></Pie>
        </a-col>
        <a-col :md="12" :sm="24">
          <div style="font-size: large;font-weight: bold;">电量使用情况:
            <span style="float: right;">
              <a-radio-group v-model="dateValue" @change="dateOnChange">
                <a-radio-button value="year">
                  本年
                </a-radio-button>
                <a-radio-button value="month">
                  本月
                </a-radio-button>
              </a-radio-group>
            </span>
            <Pie :title="title2" :dataSource="pieData2"></Pie>
          </div>

        </a-col>
      </a-row>
    </div>
    <a-card>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="企业全称">
                <a-input placeholder="请输入企业全称" v-model="queryParam.comAName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="地域信息">
                <a-input placeholder="请输入地域信息" v-model="queryParam.addressInfo"></a-input>
              </a-form-item>
            </a-col>
            <template v-if="toggleSearchStatus">
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="行业类别">
                  <a-input placeholder="请输入行业类别" v-model="queryParam.industry"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="供电区域">
                  <a-input placeholder="请输入供电区域" v-model="queryParam.supplyArea"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="联系人">
                  <a-input placeholder="请输入联系人" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="手机号码">
                  <a-input placeholder="请输入手机号码" v-model="queryParam.phone"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="客户质量">
                  <a-input placeholder="请输入客户质量" v-model="queryParam.cusQuality"></a-input>
                </a-form-item>
              </a-col>
            </template>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a @click="handleToggleSearch" style="margin-left: 8px;margin-right: 20px;">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <!-- 查询区域-END -->

      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button @click="handleAdd" type="primary" icon="plus" v-has="'admin:add'">新增</a-button>
        <a-button type="primary" icon="download" @click="handleExportXls('电力用户信息')">导出</a-button>
        <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
          <a-button type="primary" icon="import">导入</a-button>
        </a-upload>
        <a-dropdown v-if="selectedRowKeys.length > 0" v-has="'admin:delete'">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
        </a-dropdown>
      </div>

      <!-- table区域-begin -->
      <div>
        <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
          <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
          <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        </div>

        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

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

          <span slot="effective" slot-scope="text, record">
            <div v-if="text === '0'">
              <span style="color: limegreen">正常</span>
            </div>
            <div v-if="text === '1'">
              <span style="color: #ffa95e">即将过期</span>
            </div>
            <div v-if="text === '2'">
              <span style="color: red">到期</span>
            </div>
          </span>
          <span slot="action" slot-scope="text, record">
          <a @click="userDetail(record)" v-has="'admin:edit'">详情</a>
            <!--<a-divider type="vertical" />-->
          <!--<a @click="handleChat(record)" v-has="'admin:edit'">聊天</a>-->
            <a-divider type="vertical" />
          <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown v-has="'admin:delete'">
            <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => cusDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

        </a-table>
      </div>

      <userInfoB-modal ref="modalForm" @ok="modalFormOk"></userInfoB-modal>
      <user-detail-modal ref="userDetail"></user-detail-modal>
      <user-chat-modal ref="userChat"></user-chat-modal>
    </a-card>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import UserInfoBModal from './modules/UserInfoBModal'
  import UserChatModal from './modules/UserChatModal'
  import UserDetailModal from './modules/UserDetailModal'
  import Pie from '@/components/chart/Pie'
  import ARow from 'ant-design-vue/es/grid/Row'
  import { getAction } from '../../api/manage'

  export default {
    name: "UserInfoBList",
    mixins:[JeecgListMixin],
    components: {
      ARow,
      UserInfoBModal,
      Pie,
      UserDetailModal,
      UserChatModal
    },
    data () {
      return {
        description: '电力用户信息管理页面',
        title1: 'dasd',
        title2: 'das',
        // 表头
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
            title:'企业全称',
            align:"center",
            width:120,
            dataIndex: 'comAName'
          },
          // {
          //   title:'总公司名称',
          //   align:"center",
          //   width:120,
          //   dataIndex: 'comTName'
          // },
          // {
          //   title:'企业简称',
          //   align:"center",
          //   dataIndex: 'comSName'
          // },
          {
            title:'地域信息',
            align:"center",
            dataIndex: 'addressInfo'
          },
          // {
          //   title:'行业类别',
          //   align:"center",
          //   dataIndex: 'industry'
          // },
          // {
          //   title:'企业所在工业园区',
          //   align:"center",
          //   dataIndex: 'industrialSite'
          // },
          {
            title:'供电区域',
            align:"center",
            dataIndex: 'supplyArea'
          },
          // {
          //   title:'通讯地址',
          //   align:"center",
          //   dataIndex: 'address'
          // },
          // {
          //   title:'邮政编码',
          //   align:"center",
          //   dataIndex: 'email'
          // },
          {
            title:'联系人',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'手机号码',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'客户质量',
            align:"center",
            dataIndex: 'cusQuality',
            customRender:function (t,r,index) {
              if(r.cusQuality === '0'){
                r.cusQuality = '优质'
              }else if(r.cusQuality === '1'){
                r.cusQuality = '普通'
              }else if(r.cusQuality === '2'){
                r.cusQuality = '劣质'
              }
              return r.cusQuality
            }
          },
          {
            title:'合同有效期',
            align:"center",
            dataIndex: 'effective',
            scopedSlots: { customRender: 'effective' }
          },
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
          //   title:'传真',
          //   align:"center",
          //   dataIndex: 'ems'
          // },
          // {
          //   title:'网址',
          //   align:"center",
          //   dataIndex: 'internet'
          // },
          // {
          //   title:'统一社会信用代码',
          //   align:"center",
          //   dataIndex: 'socialCredit'
          // },
          // {
          //   title:'成立日期',
          //   align:"center",
          //   dataIndex: 'establishment',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'法人名称',
          //   align:"center",
          //   dataIndex: 'legalName'
          // },
          // {
          //   title:'企业注册地址',
          //   align:"center",
          //   dataIndex: 'registeredAddress'
          // },
          // {
          //   title:'法定代表人姓名',
          //   align:"center",
          //   dataIndex: 'legalRepresentativeName'
          // },
          // {
          //   title:'组织机构代码',
          //   align:"center",
          //   dataIndex: 'orgCode'
          // },
          // {
          //   title:'开户银行',
          //   align:"center",
          //   dataIndex: 'depositBank'
          // },
          // {
          //   title:'开户名称',
          //   align:"center",
          //   dataIndex: 'depositName'
          // },
          // {
          //   title:'开户账号',
          //   align:"center",
          //   dataIndex: 'depoaitAccount'
          // },
          // {
          //   title:'税务登记证号',
          //   align:"center",
          //   dataIndex: 'registerNumber'
          // },
          // {
          //   title:'注册资本',
          //   align:"center",
          //   dataIndex: 'registerMoney'
          // },
          // {
          //   title:'资产总额',
          //   align:"center",
          //   dataIndex: 'totalAssets'
          // },
          // {
          //   title:'电力业务许可证编号',
          //   align:"center",
          //   dataIndex: 'licenceNo'
          // },
          // {
          //   title:'许可证生效日期',
          //   align:"center",
          //   dataIndex: 'effectiveDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'许可证失效日期',
          //   align:"center",
          //   dataIndex: 'expiryDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'合同容量（MVA）',
          //   align:"center",
          //   dataIndex: 'contractCapacity'
          // },
          // {
          //   title:'运行容量（MVA）',
          //   align:"center",
          //   dataIndex: 'operatingCapacity'
          // },
          // {
          //   title:'最高供电电压',
          //   align:"center",
          //   dataIndex: 'maxVoltage'
          // },
          // {
          //   title:'月度用电信息',
          //   align:"center",
          //   dataIndex: 'monthElecInfo'
          // },
          // {
          //   title:'年度用电信息',
          //   align:"center",
          //   dataIndex: 'yearElecInfo'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/userinfob/userInfoB/list",
          delete: "/userinfob/userInfoB/delete",
          deleteBatch: "/userinfob/userInfoB/deleteBatch",
          exportXlsUrl: "/userinfoexport/userInfoExport/exportXls",
          importExcelUrl: "userinfob/userInfoB/importExcel",
        },
        dictOptions:{},
        pieData:[
          { item: '优质', count: 0 },
          { item: '普通', count: 0 },
          { item: '劣质', count: 0 },
        ],
        pieData2:[
          { item: '使用电量', count: 120 },
          { item: '剩余电量', count: -20 }
        ],
        dateValue:'year',
        contractElec: 0,
        contractElecMon: 0,
        elecMonth: 0,
        elecYear: 0,
        // dataSource1:[],
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){
      this.initData();
    },
    methods: {
      initDictConfig(){
        //测试树状图数据能不能显示完全
        getAction(this.url.list).then((res) =>{
          if(res.success){
            console.log(res.result.records)
          }
        })
        // getAction('/userinfob/userInfoB/queryall').then((res) =>{
        //   if(res.success){
        //     // console.log(res)
        //     //去除总公司名称不为空的
        //     let resZong = [];
        //     let resZi = [];
        //     for(let i=0;i<res.result.length;i++){
        //       if(res.result[i].comTName === null){
        //         resZong.push(res.result[i])
        //       }else{
        //         resZi.push(res.result[i])
        //       }
        //     }
        //     for(let i=0;i<resZong.length;i++){
        //       let chlid = [];
        //       for(let j=0;j<resZi.length;j++){
        //         if(resZong[i].comAName === resZi[j].comTName){
        //           chlid.push(resZi[j])
        //           resZong[i].children = chlid
        //         }
        //       }
        //     }
        //     this.dataSource1 = resZong
        //     console.log(this.dataSource1.length)
        //     this.ipagination.total = this.dataSource1.length;
        //   }
        // })
      },
      //加载饼图数据
      initData(){
        //用户数量
        this.pieData = [
          { item: '优质', count: 0 },
          { item: '普通', count: 0 },
          { item: '劣质', count: 0 }
        ]
        getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
          //console.log(res)
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].cusQuality === '0'){
                this.pieData[0].count += 1/(res.result.length)
              }else if(res.result[i].cusQuality === '1'){
                this.pieData[1].count += 1/(res.result.length)
              }else if(res.result[i].cusQuality === '2'){
                this.pieData[2].count += 1/(res.result.length)
              }
            }
            console.log(this.pieData)
          }
        })
        //电量使用情况
        this.pieData2 = [
          { item: '使用电量', count: 0 },
          { item: '剩余电量', count: 0 }
        ]
        //合同总电量
        this.contractElec = 0;
        this.contractElecMon = 0;
        this.elecYear = 0;
        this.elecMonth = 0;
        let date = new Date();
        let y = date.getFullYear()
        let mon = date.getMonth()+1
        //购电合同
        getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year:y }).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              // 年合同总电量  (合同总电量+交易电量)
              this.contractElec += parseInt(res.result[i].purchaseElectricity)
              // 月合同总电量
              this.contractElecMon = this.contractElec/12
            }
            // 我的交易
            getAction('/mytrade/myTrade/queryall',{ year:y }).then((res) => {
              // console.log(res)
              if(res.success){
                for(let i=0;i<res.result.length;i++){
                  // 总电量 减去转出电量
                  if(res.result[i].zrType === '2'){
                    this.contractElec -= parseInt(res.result[i].tradeElec)
                  }else{
                    this.contractElec += parseInt(res.result[i].tradeElec)
                  }
                  // 月电量
                  if(parseInt(res.result[i].tradeBegin.split('-')[1]) === mon){
                    if(res.result[i].zrType === '2'){
                      this.contractElecMon -= parseInt(res.result[i].tradeElec)
                    }else{
                      this.contractElecMon += parseInt(res.result[i].tradeElec)
                    }
                  }
                }
                //使用电量
                getAction('/hiselec/hiselec/queryall',{}).then((res) => {
                  if(res.success){
                    for(let i=0;i<res.result.length;i++){
                      let elecUse = res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                        res.result[i].ydll+res.result[i].ydyw+res.result[i].ydss+res.result[i].ydsw+
                        res.result[i].edll+res.result[i].edyw+res.result[i].edss+res.result[i].edsw+
                        res.result[i].sdll+res.result[i].sdyw+res.result[i].sdss+res.result[i].sssw+
                        res.result[i].sidll+res.result[i].sidyw+res.result[i].sidss+res.result[i].sidsw+
                        res.result[i].wdll+res.result[i].wdyw+res.result[i].wdss+res.result[i].wdsw+
                        res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                        res.result[i].qdll+res.result[i].qdyw+res.result[i].qdss+res.result[i].qdsw+
                        res.result[i].bdll+res.result[i].bdyw+res.result[i].bdss+res.result[i].bdsw+
                        res.result[i].jdll+res.result[i].jdyw+res.result[i].jdss+res.result[i].jdsw+
                        res.result[i].shidll+res.result[i].shidyw+res.result[i].shidss+res.result[i].shidsw+
                        res.result[i].sydll+res.result[i].sydyw+res.result[i].sydss+res.result[i].sydsw+
                        res.result[i].sedll+res.result[i].sedyw+res.result[i].sedss+res.result[i].sedsw+
                        res.result[i].ssdll+res.result[i].ssdyw+res.result[i].ssdss+res.result[i].ssdsw+
                        res.result[i].ssidll+res.result[i].ssidyw+res.result[i].ssidss+res.result[i].ssidsw+
                        res.result[i].swdll+res.result[i].swdyw+res.result[i].swdss+res.result[i].swdsw+
                        res.result[i].sldll+res.result[i].sldyw+res.result[i].sldss+res.result[i].sldsw+
                        res.result[i].sqdll+res.result[i].sqdyw+res.result[i].sqdss+res.result[i].sqdsw+
                        res.result[i].sbdll+res.result[i].sbdyw+res.result[i].sbdss+res.result[i].sbdsw+
                        res.result[i].sjdll+res.result[i].sjdyw+res.result[i].sjdss+res.result[i].sjdsw+
                        res.result[i].esdll+res.result[i].esdyw+res.result[i].esdss+res.result[i].esdsw+
                        res.result[i].eydll+res.result[i].eydyw+res.result[i].eydss+res.result[i].eydsw+
                        res.result[i].eedll+res.result[i].eedyw+res.result[i].eedss+res.result[i].eedsw+
                        res.result[i].essdll+res.result[i].essdyw+res.result[i].essdss+res.result[i].essdsw
                      if(res.result[i].elecDate.split('-')[0] === y.toString()){
                        this.elecYear += elecUse
                      }
                      if(res.result[i].elecDate.split('-')[0]+'-'+parseInt(res.result[i].elecDate.split('-')[1]) === y+'-'+mon){
                        this.elecMonth += elecUse
                      }
                    }
                    //给饼图赋值
                    if(this.dateValue === 'month'){
                      this.pieData2[0]['count'] = this.elecMonth/this.contractElecMon
                      this.pieData2[1]['count'] = 1-this.elecMonth/this.contractElecMon
                    }
                    if(this.dateValue === 'year'){
                      this.pieData2[0]['count'] = this.elecYear/this.contractElec
                      this.pieData2[1]['count'] = 1-this.elecYear/this.contractElec
                    }
                  }
                })

              }
            })
          }
        })
      },
      //新增时刷新数据
      modalFormOk(){
        this.loadData();
        this.initData();
      },
      //删除数据时刷新饼图
      cusDelete(id){
        this.handleDelete(id)
        setTimeout(this.initData(),5000)
      },
      userDetail(record){
        console.log("detail")
        this.$refs.userDetail.show(record);
      },
      callback(key) {
        console.log(key);
      },
      dateOnChange(e){
        console.log(`checked = ${e.target.value}`);
        this.dateValue = e.target.value
        this.initData();
      },
      //聊天
      handleChat(records){
        this.$refs.userChat.chat(records);
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>