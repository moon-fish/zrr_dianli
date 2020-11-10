<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24" style="margin-bottom: 20px;">
          <a-col :md="2" :sm="24"><span style="font-size: 18px;">月份:</span></a-col>
          <a-col :md="4" :sm="24">
            <a-select @change="monthChange" :value="monthSelected" >
              <a-select-option value="1">一月</a-select-option>
              <a-select-option value="2">二月</a-select-option>
              <a-select-option value="3">三月</a-select-option>
              <a-select-option value="4">四月</a-select-option>
              <a-select-option value="5">五月</a-select-option>
              <a-select-option value="6">六月</a-select-option>
              <a-select-option value="7">七月</a-select-option>
              <a-select-option value="8">八月</a-select-option>
              <a-select-option value="9">九月</a-select-option>
              <a-select-option value="10">十月</a-select-option>
              <a-select-option value="11">十一月</a-select-option>
              <a-select-option value="12">十二月</a-select-option>
            </a-select>
          </a-col>
          <a-col :md="14" :sm="24"></a-col>
          <a-col :md="2" :sm="24">
            <!--<a-button type="primary">查询</a-button>-->
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <!--<div class="table-operator">-->
      <!--<a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <!--<a-button type="primary" icon="download" @click="handleExportXls('售电公司结算')">导出</a-button>-->
      <!--<a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
        <!--<a-button type="primary" icon="import">导入</a-button>-->
      <!--</a-upload>-->
      <!--<a-dropdown v-if="selectedRowKeys.length > 0">-->
        <!--<a-menu slot="overlay">-->
          <!--<a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
        <!--</a-menu>-->
        <!--<a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
      <!--</a-dropdown>-->
    <!--</div>-->

    <!-- table区域-begin -->
    <div>
      <!--<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
        <!--<i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
        <!--<a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
      <!--</div>-->
      <div style="margin-bottom: 20px;">
        <a-row :gutter="24">
          <a-col :xl="24" :md="24" :sm="24" style="text-align:center;padding:10px;font-size: large;font-weight: bold">xx售电公司</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="24" :md="24" :sm="24" style="text-align:right;padding:10px;">单位:  电量(兆瓦时)  &nbsp电价(元/兆瓦时)  &nbsp电费(元)</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="4" :md="4" :sm="4" :xs="4" style="text-align: center;background-color: #e6f7ff;padding:5px;">项目</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;background-color: #e6f7ff;padding:5px;">成交电量</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;background-color: #e6f7ff;padding:5px;">结算电量</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;background-color: #e6f7ff;padding:5px;">成交均价</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;background-color: #e6f7ff;padding:5px;">超用均价</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="4" :md="4" :sm="4" :xs="4" style="text-align: center;padding:5px;">批发市场</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;padding:5px;">{{ this.cjElec.toFixed(2) }}</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;padding:5px;">{{ this.jsElec.toFixed(2) }}</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;padding:5px;">{{ (this.contractAverageMon).toFixed(2) }}</a-col>
          <a-col :xl="5" :md="5" :sm="5" :xs="5" style="text-align: center;padding:5px;">332.00</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">批发市场结算电费</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">整体偏差考核电量</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">整体偏差考核电费</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">{{ (this.jsElec*this.contractAverageMon).toFixed(2) }}</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">{{ (this.cjElec*0.98-this.jsElec).toFixed(2) }}</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">{{ ((this.cjElec*0.98-this.jsElec)*this.contractAverageMon*0.1).toFixed(2) }}</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">支付单位</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">售电公司结算电量</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;background-color: #e6f7ff;padding:5px;">售电公司结算电费</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">电网公司</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">{{ this.jsElec.toFixed(2) }}</a-col>
          <a-col :xl="8" :md="8" :sm="8" :xs="8" style="text-align: center;padding:5px;">{{ (this.lsMoney-this.jsElec*this.contractAverageMon).toFixed(2) }}</a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :xl="12" :md="12" :sm="12" :xs="12" style="text-align: center;background-color: #e6f7ff;padding:5px;">售电公司承担偏差考核电费</a-col>
          <a-col :xl="12" :md="12" :sm="12" :xs="12" style="text-align: center;padding:5px;">{{ ((this.cjElec*0.98-this.jsElec)*this.contractAverageMon*0.1).toFixed(2) }}</a-col>
        </a-row>
        <!--<table border="1px solid #e8e8e8" style="width: 100%;text-align: center;border-color: #e8e8e8;">-->
          <!--<tr>-->
            <!--<th colspan="6" style="font-size: medium;">xx售电公司</th>-->
            <!--<th colspan="2">单位: <span style="margin-left: 20px;">电量(兆瓦时)  &nbsp电价(元/兆瓦时)  &nbsp电费(元)</span></th>-->
          <!--</tr>-->
          <!--<tr>-->
            <!--<th>项目</th>-->
            <!--<th>成交电量</th>-->
            <!--<th>结算电量</th>-->
            <!--<th>成交均价</th>-->
            <!--<th>超用均价</th>-->
            <!--<th>批发市场结算电费</th>-->
            <!--<th>整体偏差考核电量</th>-->
            <!--<th>整体偏差考核电费</th>-->
          <!--</tr>-->
          <!--<tr>-->
            <!--<td>批发市场</td>-->
            <!--<td>{{ this.cjElec.toFixed(2) }}</td>-->
            <!--<td>{{ this.jsElec.toFixed(2) }}</td>-->
            <!--<td>{{ (this.contractAverageMon).toFixed(2) }}</td>-->
            <!--<td>332.00</td>-->
            <!--<td>{{ (this.jsElec*this.contractAverageMon).toFixed(2) }}</td>-->
            <!--<td>{{ (this.cjElec*0.98-this.jsElec).toFixed(2) }}</td>-->
            <!--<td>{{ ((this.cjElec*0.98-this.jsElec)*this.contractAverageMon*0.1).toFixed(2) }}</td>-->
          <!--</tr>-->
          <!--<tr>-->
            <!--<th colspan="2">支付单位</th>-->
            <!--<th colspan="2">售电公司结算电量</th>-->
            <!--<th colspan="2">售电公司结算电费</th>-->
            <!--<th colspan="2">售电公司承担偏差考核电费</th>-->
          <!--</tr>-->
          <!--<tr>-->
            <!--<td colspan="2">电网公司</td>-->
            <!--<td colspan="2">{{ this.jsElec.toFixed(2) }}</td>-->
            <!--<td colspan="2">{{ (this.lsMoney-this.jsElec*this.contractAverageMon).toFixed(2) }}</td>-->
            <!--<td colspan="2">{{ ((this.cjElec*0.98-this.jsElec)*this.contractAverageMon*0.1).toFixed(2) }}</td>-->
          <!--</tr>-->
        <!--</table>-->
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
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
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

    <balanceCompany-modal ref="modalForm" @ok="modalFormOk"></balanceCompany-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BalanceCompanyModal from './modules/BalanceCompanyModal'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { getAction } from '../../api/manage'
  import ARow from 'ant-design-vue/es/grid/Row'

  export default {
    name: "BalanceCompanyList",
    mixins:[JeecgListMixin],
    components: {
      ARow,
      ACol,
      BalanceCompanyModal
    },
    data () {
      return {
        description: '售电公司结算管理页面',
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'用户名称',
            align:"center",
            dataIndex: 'userName'
          },
          {
            title:'批发成交电量',
            align:"center",
            dataIndex: 'sucElec'
          },
          // {
          //   title:'成交均价',
          //   align:"center",
          //   dataIndex: 'sucPrice'
          // },
          // {
          //   title:'批发结算电费',
          //   align:"center",
          //   dataIndex: 'sucMoney'
          // },
          {
            title:'实际用电量',
            align:"center",
            dataIndex: 'cusElec'
          },
          {
            title:'协议电价',
            align:"center",
            dataIndex: 'cusPrice'
          },
          {
            title:'零售结算电费',
            align:"center",
            dataIndex: 'cusMoney'
          },
          // {
          //   title:'偏差考核电量',
          //   align:"center",
          //   dataIndex: 'allOffsetElec'
          // },
          // {
          //   title:'偏差考核电费',
          //   align:"center",
          //   dataIndex: 'allOffsetMoney'
          // },
          {
            title:'用户偏差电量',
            align:"center",
            dataIndex: 'cusOffsetElec'
          },
          {
            title:'用户偏差电费',
            align:"center",
            dataIndex: 'cusOffsetMoney',
            customRender:function (t,r,index) {
              if(t !== undefined){
                return t.toFixed(2);
              }

            }

          },
          // {
          //   title:'售电公司结算电量',
          //   align:"center",
          //   dataIndex: 'sucBalanceElec'
          // },
          // {
          //   title:'售电公司结算电费',
          //   align:"center",
          //   dataIndex: 'sucBalanceMoney'
          // },
          // {
          //   title:'售电公司偏差电费',
          //   align:"center",
          //   dataIndex: 'sucOffsetMoney'
          // },
          // {
          //   title:'备注',
          //   align:"center",
          //   dataIndex: 'market'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        url: {
          list: "/balancecompany/balanceCompany/list",
          delete: "/balancecompany/balanceCompany/delete",
          deleteBatch: "/balancecompany/balanceCompany/deleteBatch",
          exportXlsUrl: "/balancecompany/balanceCompany/exportXls",
          importExcelUrl: "balancecompany/balanceCompany/importExcel",
        },
        dictOptions:{},
        dataSource1:[],
        monthSelected:(new Date().getMonth()+1).toString(),
        cjElec: 0,
        jsElec: 0,
        contractAverage: 0,
        contractAverageMon: 0,
        lsMoney: 0,
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        this.cjElec =  0
        this.jsElec = 0
        this.dataSource1 = [];
        let date = new Date()
        let year = date.getFullYear();
        let month = parseInt(this.monthSelected)
        if(month<10){
          month = '0'+month
        }
        //中间变量
        let aaa = []
        //查询申报电量（计划电量）
        getAction('/monthelec/momthElec/queryall2',{}).then((res) => {
          if(res.success){
            //console.log(res.result)
            let usernames = [];
            let n = 0;
            for(let i=0;i<res.result.length;i++){
              if(usernames.indexOf(res.result[i].username) === -1){
                usernames.push(res.result[i].username)
                aaa.push({})
                //添加用户名称
                aaa[n]['userName'] = res.result[i].username
                //添加成交电量
                if(this.monthSelected === '1'){
                  aaa[n]['sucElec'] = res.result[i].yi
                }else if(this.monthSelected === '2'){
                  aaa[n]['sucElec'] = res.result[i].er
                }else if(this.monthSelected === '3'){
                  aaa[n]['sucElec'] = res.result[i].san
                }else if(this.monthSelected === '4'){
                  aaa[n]['sucElec'] = res.result[i].si
                }else if(this.monthSelected === '5'){
                  aaa[n]['sucElec'] = res.result[i].wu
                }else if(this.monthSelected === '6'){
                  aaa[n]['sucElec'] = res.result[i].liu
                }else if(this.monthSelected === '7'){
                  aaa[n]['sucElec'] = res.result[i].qi
                }else if(this.monthSelected === '8'){
                  aaa[n]['sucElec'] = res.result[i].ba
                }else if(this.monthSelected === '9'){
                  aaa[n]['sucElec'] = res.result[i].jiu
                }else if(this.monthSelected === '10'){
                  aaa[n]['sucElec'] = res.result[i].shi
                }else if(this.monthSelected === '11'){
                  aaa[n]['sucElec'] = res.result[i].shiyi
                }else if(this.monthSelected === '12'){
                  aaa[n]['sucElec'] = res.result[i].shier
                }
                n++
              }
            }
            //查询实际电量
            let begin = year+'-'+month+'-01'
            let end = year+'-'+month+'-31'
            getAction('/hiselec/hiselec/queryall',{elecBegin:begin,elecEnd:end}).then((res) => {
              if(res.success){
                //console.log(res)
                for(let i=0;i<aaa.length;i++){
                  for(let j=0;j<res.result.length;j++){
                    if(aaa[i]['userName'] === res.result[j].username){
                      if(aaa[i]['cusElec'] === undefined){
                        aaa[i]['cusElec'] = 0;
                      }
                      aaa[i]['cusElec'] += parseFloat((res.result[j].ldll+res.result[j].ldyw+res.result[j].ldss+res.result[j].ldsw+
                        res.result[j].ydll+res.result[j].ydyw+res.result[j].ydss+res.result[j].ydsw+
                        res.result[j].edll+res.result[j].edyw+res.result[j].edss+res.result[j].edsw+
                        res.result[j].sdll+res.result[j].sdyw+res.result[j].sdss+res.result[j].sssw+
                        res.result[j].sidll+res.result[j].sidyw+res.result[j].sidss+res.result[j].sidsw+
                        res.result[j].wdll+res.result[j].wdyw+res.result[j].wdss+res.result[j].wdsw+
                        res.result[j].ldll+res.result[j].ldyw+res.result[j].ldss+res.result[j].ldsw+
                        res.result[j].qdll+res.result[j].qdyw+res.result[j].qdss+res.result[j].qdsw+
                        res.result[j].bdll+res.result[j].bdyw+res.result[j].bdss+res.result[j].bdsw+
                        res.result[j].jdll+res.result[j].jdyw+res.result[j].jdss+res.result[j].jdsw+
                        res.result[j].shidll+res.result[j].shidyw+res.result[j].shidss+res.result[j].shidsw+
                        res.result[j].sydll+res.result[j].sydyw+res.result[j].sydss+res.result[j].sydsw+
                        res.result[j].sedll+res.result[j].sedyw+res.result[j].sedss+res.result[j].sedsw+
                        res.result[j].ssdll+res.result[j].ssdyw+res.result[j].ssdss+res.result[j].ssdsw+
                        res.result[j].ssidll+res.result[j].ssidyw+res.result[j].ssidss+res.result[j].ssidsw+
                        res.result[j].swdll+res.result[j].swdyw+res.result[j].swdss+res.result[j].swdsw+
                        res.result[j].sldll+res.result[j].sldyw+res.result[j].sldss+res.result[j].sldsw+
                        res.result[j].sqdll+res.result[j].sqdyw+res.result[j].sqdss+res.result[j].sqdsw+
                        res.result[j].sbdll+res.result[j].sbdyw+res.result[j].sbdss+res.result[j].sbdsw+
                        res.result[j].sjdll+res.result[j].sjdyw+res.result[j].sjdss+res.result[j].sjdsw+
                        res.result[j].esdll+res.result[j].esdyw+res.result[j].esdss+res.result[j].esdsw+
                        res.result[j].eydll+res.result[j].eydyw+res.result[j].eydss+res.result[j].eydsw+
                        res.result[j].eedll+res.result[j].eedyw+res.result[j].eedss+res.result[j].eedsw+
                        res.result[j].essdll+res.result[j].essdyw+res.result[j].essdss+res.result[j].essdsw).toFixed(2))
                    }
                  }
                }
                //查询协议电价
                getAction('/userpurchasecontractb/userPurchaseContractB/queryall',{}).then((res) => {
                  //console.log(res)
                  if(res.success){
                    for(let i=0;i<aaa.length;i++){
                      for(let j=0;j<res.result.length;j++){
                        if(aaa[i]['userName'] === res.result[j].userName){
                          aaa[i]['cusPrice'] = res.result[j].purchasePrice
                        }
                      }
                      // for(let i=0;i<aaa.length;i++){
                        // console.log(aaa[i]['cusPrice'])
                        // console.log(aaa[i]['cusElec'])
                        if(aaa[i]['cusElec'] !== undefined && aaa[i]['cusPrice'] !== undefined ){
                          aaa[i]['cusMoney'] = parseFloat((aaa[i]['cusPrice']*aaa[i]['cusElec']).toFixed(2))
                          aaa[i]['cusOffsetElec'] = parseFloat((aaa[i]['sucElec']*0.98-aaa[i]['cusElec']).toFixed(2))
                          this.lsMoney += aaa[i]['cusPrice']*aaa[i]['cusElec']

                          if(aaa[i]['cusElec'] - aaa[i]['sucElec']>0){
                            aaa[i]['cusOffsetMoney'] = (332-aaa[i]['cusPrice'])*(aaa[i]['cusElec'] - aaa[i]['sucElec'])
                          }
                        }

                      // }
                    }
                    this.dataSource1 = aaa
                    for(let i=0;i<this.dataSource1.length;i++){
                      //实际用电量 保留两位小数
                      if(this.dataSource1[i]['cusElec'] !== undefined){
                        this.dataSource1[i]['cusElec'] = parseFloat(this.dataSource1[i]['cusElec'].toFixed(2))
                      }
                      //计算总成交电量
                      if(this.dataSource1[i].sucElec !== undefined){
                        this.cjElec += parseFloat(this.dataSource1[i].sucElec)
                      }
                      //计算总结算电量
                      if(this.dataSource1[i].cusElec !== undefined){
                        this.jsElec += this.dataSource1[i].cusElec
                      }


                    }
                  }
                })
              }
            })

            // let bool2 = true

          }
        })
        //成交电价
        let contractAve = 0;
        let contractAveMon = 0;
        this.contractAverageMon = 0;
        this.contractElec = 0;
        this.contractElecMon = 0;
        let y = date.getFullYear()
        //购电合同
        getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year:y }).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              // 年合同总电量  (合同总电量+交易电量)
              this.contractElec += parseInt(res.result[i].purchaseElectricity)
              // 月合同总电量
              this.contractElecMon = this.contractElec/12
              // 合同均价
              contractAve += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice
              //月合同均价
              contractAveMon += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice/12
              // console.log(contractAve)
            }
            // 我的交易
            getAction('/mytrade/myTrade/queryall',{ year:y }).then((res) => {
              // console.log(res)
              if(res.success){
                for(let i=0;i<res.result.length;i++){
                  // 总电量 减去转出电量
                  if(res.result[i].zrType === '2'){
                    this.contractElec -= parseInt(res.result[i].tradeElec)
                    contractAve -= res.result[i].tradeElec*res.result[i].tradePrice
                  }else{
                    this.contractElec += parseInt(res.result[i].tradeElec)
                    contractAve += res.result[i].tradeElec*res.result[i].tradePrice
                  }
                  // 月电量
                  //console.log(parseInt(this.monthSelected))
                  if(parseInt(res.result[i].tradeBegin.split('-')[1]) === parseInt(this.monthSelected)){
                    if(res.result[i].zrType === '2'){
                      this.contractElecMon -= parseInt(res.result[i].tradeElec)
                      contractAveMon -= res.result[i].tradeElec*res.result[i].tradePrice
                    }else{
                      this.contractElecMon += parseInt(res.result[i].tradeElec)
                      contractAveMon += res.result[i].tradeElec*res.result[i].tradePrice
                    }
                  }
                }
                console.log(contractAveMon)
                console.log("aaaaaaaaaaaaaaaaa")
                console.log(this.contractElecMon)
                this.contractAverage = contractAve/this.contractElec
                this.contractAverageMon = contractAveMon/this.contractElecMon

              }
            })
          }
        })

      },

      monthChange(e){
        this.monthSelected = e;
        this.initDictConfig();
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  td {
    padding: 10px;
  }
  th {
    padding: 10px;
  }
</style>