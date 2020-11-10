<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="12" :sm="24">

              <a-row :gutter="24">
                <a-col :xl="12" :lg="7" :md="8" :sm="24">
                  <a-form-item label="客户名称">
                    <!--<a-input placeholder="请输入客户名称" v-model="queryParam.customerName"></a-input>-->
                    <a-select v-model="queryParam.customerName" placeholder="-- 请选择 --" @change="customerNameChange">
                      <a-select-option value="">-- 请选择 --</a-select-option>
                      <a-select-option v-for="(item,index) in this.customerNames" :key="item" :value="item">
                        <span>{{ item }}</span>
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :xl="12" :lg="7" :md="8" :sm="24">
                  <a-form-item label="交易类型">
                    <!--<a-input placeholder="请输入交易类型" v-model="queryParam.tradeType"></a-input>-->
                    <j-dict-select-tag v-model="queryParam.tradeType" placeholder="-- 请选择 --" dictCode="tradeType" />
                  </a-form-item>
                </a-col>
                <a-col :xl="12" :lg="7" :md="8" :sm="18">
                  <a-form-item label="交易时间">
                    <!--<a-input placeholder="请输入交易时间" v-model="queryParam.tradeDate"></a-input>-->
                    <a-date-picker @change="tradeDateChange" />
                  </a-form-item>
                </a-col>
                <a-col :xl="12" :lg="7" :md="8" :sm="18">
                  <a-form-item label="交易电量">
                    <a-input placeholder="请输入交易电量" v-model="queryParam.tradeElec"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :xl="12" :lg="7" :md="8" :sm="18">
                  <a-form-item label="交易单价">
                    <a-input placeholder="请输入交易单价" v-model="queryParam.tradePrice"></a-input>
                  </a-form-item>
                </a-col>
                <!--<a-col :xl="12" :lg="7" :md="8" :sm="18">-->
                  <!--<a-form-item label="交易金额">-->
                    <!--<a-input placeholder="请输入交易金额" v-model="queryParam.tradeMoney"></a-input>-->
                  <!--</a-form-item>-->
                <!--</a-col>-->
                <a-col :xl="12" :lg="7" :md="8" :sm="18"></a-col>
                <a-col :xl="12" :lg="7" :md="8" :sm="18">
                  <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <!--<a @click="handleToggleSearch" style="margin-left: 8px;margin-right: 20px;">-->
                  <!--{{ toggleSearchStatus ? '收起' : '展开' }}-->
                  <!--<a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
                  <!--</a>-->
                  <a-button type="primary" @click="searchQueryTrade" icon="search">查询</a-button>
                  <a-button type="primary" @click="searchResetTrade" icon="reload" style="margin-left: 8px">重置</a-button>
                  </span>
                </a-col>
              </a-row>


          </a-col>
          <a-col :md="12" :sm="24">
            <Pie :dataSource="pieData"></Pie>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'admin:add'">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('我的交易')" >导出</a-button>
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
        :dataSource="dataSource1"
        :pagination="false"
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown v-has="'admin:delete'">
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => tradeDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>

        </span>

      </a-table>
    </div>

    <myTrade-modal ref="modalForm" @ok="modalFormOk"></myTrade-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MyTradeModal from './modules/MyTradeModal'
  import Pie from '@/components/chart/Pie'
  import { getAction } from  '@/api/manage'
  import JDictSelectTag from  '@/components/dict/JDictSelectTag'

  export default {
    name: "MyTradeList",
    mixins:[JeecgListMixin],
    components: {
      MyTradeModal,
      Pie,
      JDictSelectTag
    },
    data () {
      return {
        description: '我的交易管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: 'rowIndex',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender: function (text, r, index) {
              return (text !== '合计') ? (parseInt(index) + 1) : text
            }
          },
          {
            title:'电厂名称',
            align:"center",
            dataIndex: 'customerName'
          },
          {
            title:'交易类型',
            align:"center",
            dataIndex: 'tradeType',
            customRender:function (text,r) {
              if(text === 'sb'){
                r.tradeType = '双边'
              }else if(text === 'a'){
                r.tradeType = '合同转让'
              }else if(text === 'b'){
                r.tradeType = '挂牌'
              }
              return r.tradeType
            }
          },
          {
            title:'交易周期',
            align:"center",
            dataIndex: 'tradeDate',
            customRender:function (text,record) {
              // let tb = !record.tradeBegin?"":(record.tradeBegin.length>10?record.tradeBegin.substr(0,10):record.tradeBegin)
              // let te = !record.tradeEnd?"":(record.tradeEnd.length>10?record.tradeEnd.substr(0,10):record.tradeEnd)
              return record.tradeBegin +" 至 "+ record.tradeEnd
            }
          },
          {
            title:'交易电量',
            align:"center",
            dataIndex: 'tradeElec'
          },
          {
            title:'交易单价',
            align:"center",
            dataIndex: 'tradePrice'
          },
          // {
          //   title:'交易金额',
          //   align:"center",
          //   dataIndex: 'tradeMoney'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/mytrade/myTrade/listTotal",
          delete: "/mytrade/myTrade/delete",
          deleteBatch: "/mytrade/myTrade/deleteBatch",
          exportXlsUrl: "/mytrade/myTrade/exportXls",
          importExcelUrl: "mytrade/myTrade/importExcel",
        },
        dictOptions:{},
        pieData:[
          { item: '合同转让', count: 0 },
          { item: '挂牌', count: 0 },
          { item: '双边', count: 0 }
        ],
        dataSource1:[],
        customerNames:[],
        customerNa:'',
        tradeDa:'',
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    mounted() {
      this.addTotal()
      this.initData()
    },
    methods: {
      //获取电厂名称
      customerNameChange(e){
        // console.log(`selected ${e}`);
        this.customerNa = `${e}`
      },
      //交易时间
      tradeDateChange(date, dateString){
        this.tradeDa = dateString
      },
      //查询
      searchQueryTrade(){
        if(this.customerNa!== ''){
          this.queryParam.customerName = this.customerNa
        }
        this.addTotal();
      },
      //重置
      searchResetTrade() {
        this.tradeDa = '';
        this.queryParam = {}
        this.addTotal();
      },
      //添加合计横
      addTotal(){
        let date = new Date();
        let year = date.getFullYear();
        this.dataSource1 = [];
        var params = this.getQueryParams();//查询条件
        params.year = year;
        if(this.tradeDa !==  ''){
          params.tradeDate = this.tradeDa
        }
        getAction('/mytrade/myTrade/listTotal',params).then((res) => {
          // console.log(res)
          if(res.success){
            this.dataSource1 = res.result
            this.tableAddTotalRow(this.columns, this.dataSource1)
          }
        })
      },
      initDictConfig(){
        //客户名称下拉项
        getAction('/generationsideInfob/generationSideInfoB/queryall',{}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              this.customerNames.push(res.result[i].stationName)
            }
          }

        })
      },
      /** 表格增加合计行 */
      tableAddTotalRow(columns, ds) {
        let numKey = 'rowIndex'
        let totalRow = { [numKey]: '合计' }
        columns.forEach(column => {
          let { key, dataIndex } = column
          if (![key, dataIndex].includes(numKey)) {

            let total = 0
            ds.forEach(data => {
              total += /^\d+\.?\d?$/.test(data[dataIndex]) ? Number.parseInt(data[dataIndex]) : Number.NaN
              // console.log(data[dataIndex], ':', (/^\d+\.?\d?$/.test(data[dataIndex]) ? Number.parseInt(data[dataIndex]) : Number.NaN))
            })

            if (Number.isNaN(total)) {
              total = '-'
            }
            totalRow[dataIndex] = total
          }
        })

        this.dataSource1.push(totalRow)
      },
      //获取饼图数据
      initData(){
        this.pieData = [
          { item: '合同转让', count: 0 },
          { item: '挂牌', count: 0 },
          { item: '双边', count: 0 }
        ]
        let date = new Date();
        let year = date.getFullYear();
        getAction('/mytrade/myTrade/queryall',{year: year}).then((res) => {
          // console.log(res.result)
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].tradeType === 'sb'|| res.result[i].tradeType === '双边'){
                this.pieData[2].count += res.result[i].tradeElec
              }else if(res.result[i].tradeType === 'a' || res.result[i].tradeType === '合同转让'){
                this.pieData[0].count += res.result[i].tradeElec
              }else if(res.result[i].tradeType === 'b' || res.result[i].tradeType === '挂牌'){
                this.pieData[1].count += res.result[i].tradeElec
              }
            }
            // console.log(this.pieData)
          }
        })
      },
      //添加后更新饼图
      modalFormOk(){
        this.loadData();
        this.initData();
        setTimeout(this.addTotal(),1000)
      },
      //删除后更新饼图
      tradeDelete(id){
        this.handleDelete(id)
        setTimeout(this.initData(),1000)
        setTimeout(this.addTotal(),3000)
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>