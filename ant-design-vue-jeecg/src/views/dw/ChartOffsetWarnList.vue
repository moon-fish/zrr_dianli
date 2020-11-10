<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="地市">
              <a-input placeholder="请输入地市" v-model="queryParam.addressShi"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="供电单位">
              <a-input placeholder="请输入供电单位" v-model="queryParam.supplyUnit"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('偏差预警')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-select style="width: 120px" @change="monthChange" :value="this.month">
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
      <a-dropdown v-if="selectedRowKeys.length > 0">
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
        @change="handleTableChange">

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
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <chartOffsetWarn-modal ref="modalForm" @ok="modalFormOk"></chartOffsetWarn-modal>

  </a-card>
</template>

<script>
  import ChartOffsetWarnModal from './modules/ChartOffsetWarnModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import {getAction} from '@/api/manage'

  export default {
    name: "ChartOffsetWarnList",
    mixins:[JeecgListMixin],
    components: {
      ChartOffsetWarnModal,
      LineChartMultid
    },
    data () {
      return {
        description: '偏差预警管理页面',
        // 表头
        // columns: [
        //   {
        //     title: '#',
        //     dataIndex: '',
        //     key:'rowIndex',
        //     width:60,
        //     align:"center",
        //     customRender:function (t,r,index) {
        //       return parseInt(index)+1;
        //     }
        //    },
		 //   {
        //     title: '序号',
        //     align:"center",
        //     dataIndex: 'serNumber'
        //    },
		 //   {
        //     title: '地市',
        //     align:"center",
        //     dataIndex: 'addressShi'
        //    },
		 //   {
        //     title: '区县',
        //     align:"center",
        //     dataIndex: 'addressXian'
        //    },
		 //   {
        //     title: '站址名称',
        //     align:"center",
        //     dataIndex: 'addressDetail'
        //    },
		 //   {
        //     title: '站址编码',
        //     align:"center",
        //     dataIndex: 'addressCode'
        //    },
		 //   {
        //     title: '供电方式',
        //     align:"center",
        //     dataIndex: 'supplyType'
        //    },
		 //   {
        //     title: '户名',
        //     align:"center",
        //     dataIndex: 'accountName'
        //    },
		 //   {
        //     title: '户号',
        //     align:"center",
        //     dataIndex: 'accountNumber'
        //    },
		 //   {
        //     title: '计量点供电电压',
        //     align:"center",
        //     dataIndex: 'supplyVoltage'
        //    },
		 //   {
        //     title: '用电地址',
        //     align:"center",
        //     dataIndex: 'addressElecuse'
        //    },
		 //   {
        //     title: '供电单位',
        //     align:"center",
        //     dataIndex: 'supplyUnit'
        //    },
		 //   {
        //     title: '实际用电量',
        //     align:"center",
        //     dataIndex: 'elecUse'
        //    },
		 //   {
        //     title: '备注',
        //     align:"center",
        //     dataIndex: 'maker'
        //    },
        //    // {
        //    //  title: '状态',
        //    //  align:"center",
        //    //  dataIndex: 'accountStatus'
        //    // },
        //    {
        //     title: '用电月份',
        //     align:"center",
        //     dataIndex: 'elecMonth'
        //    },
        //   {
        //     title: '操作',
        //     dataIndex: 'action',
        //     align:"center",
        //     scopedSlots: { customRender: 'action' },
        //   }
        // ],
        columns: [
            {
              title: '#',
              dataIndex: '',
              key:'rowIndex',
              align:"center",
              customRender:function (t,r,index) {
                return parseInt(index)+1;
              }
             },
          {
            title: '地市',
            align:"center",
            dataIndex: 'shi'
           },
          {
            title: '供电单位',
            align:"center",
            dataIndex: 'supplyUnit'
          },
          {
            title: '实际用电量',
            align:"center",
            dataIndex: 'elecUse'
          },
          {
            title: '申报电量',
            align:"center",
            dataIndex: 'elecFiler'
          },
        ],
		    url: {
          list: "/chartoffsetwarn/chartOffsetWarn/list",
          delete: "/chartoffsetwarn/chartOffsetWarn/delete",
          deleteBatch: "/chartoffsetwarn/chartOffsetWarn/deleteBatch",
          exportXlsUrl: "chartoffsetwarn/chartOffsetWarn/exportXls",
          importExcelUrl: "chartoffsetwarn/chartOffsetWarn/importExcel",
       },
        month:(new Date().getMonth()+1).toString(),
        title:'电量',
        dataSource1:[],
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`+'?elecMonth='+this.month;
    }
  },
    created(){
      this.loadCount();
    },
    methods: {
      monthChange(value){
        // console.log(`selected ${value}`);
        this.month = value
        this.loadCount();
      },
      //根据查询数据拼接数据 组成新的表头：统计国网11个国网（八个市全是国网的，另外3个存在国网和地电，只统计国网）和1个地电（3个市的地电总和）
      loadCount(){
        this.dataSource1 = [];
        let that = this;
        getAction('/accountuseinfo/accountUseInfo/queryAccountUseInfo',{m:this.month,b:true}).then((res) => {
          getAction('/chartoffsetwarn/chartOffsetWarn/queryall',{month:this.month}).then((res1) => {
            //设置地市和供电单位
            let s = ['太原','临汾','运城','晋城','晋中','朔州','吕梁','阳泉','大同','忻州','长治','吕梁/朔州/临汾']
            for(let i=0;i<s.length;i++){
              this.dataSource1.push({});
              this.dataSource1[i].shi = s[i];
              this.dataSource1[i].supplyUnit = '国网';
              if(s[i] === '吕梁/朔州/临汾'){
                this.dataSource1[i].supplyUnit = '地电';
              }
            }
            //申报电量
            if(res.success){
              for(let i=0;i<that.dataSource1.length;i++){
                if(that.dataSource1[i].elecFiler === undefined){
                  that.dataSource1[i].elecFiler = 0;
                }
                for(let j=0;j<res.result.length;j++){
                  if(res.result[j].supplyUnit === '地电'){
                    if(that.dataSource1[i].supplyUnit === '地电'){
                      if(that.month === '1'){
                        that.dataSource1[i].elecFiler += res.result[j].yi
                      }else if(that.month === '2'){
                        that.dataSource1[i].elecFiler += res.result[j].er
                      }else if(that.month === '3'){
                        that.dataSource1[i].elecFiler += res.result[j].san
                      }else if(that.month === '4'){
                        that.dataSource1[i].elecFiler += res.result[j].si
                      }else if(that.month === '5'){
                        that.dataSource1[i].elecFiler += res.result[j].wu
                      }else if(that.month === '6'){
                        that.dataSource1[i].elecFiler += res.result[j].liu
                      }else if(that.month === '7'){
                        that.dataSource1[i].elecFiler += res.result[j].qi
                      }else if(that.month === '8'){
                        that.dataSource1[i].elecFiler += res.result[j].ba
                      }else if(that.month === '9'){
                        that.dataSource1[i].elecFiler += res.result[j].jiu
                      }else if(that.month === '10'){
                        that.dataSource1[i].elecFiler += res.result[j].shi
                      }else if(that.month === '11'){
                        that.dataSource1[i].elecFiler += res.result[j].shiyi
                      }else if(that.month === '12'){
                        that.dataSource1[i].elecFiler += res.result[j].shier
                      }
                    }
                  }else{
                    if(that.dataSource1[i].shi === res.result[j].addressShi){
                      if(that.month === '1'){
                        that.dataSource1[i].elecFiler += res.result[j].yi
                      }else if(that.month === '2'){
                        that.dataSource1[i].elecFiler += res.result[j].er
                      }else if(that.month === '3'){
                        that.dataSource1[i].elecFiler += res.result[j].san
                      }else if(that.month === '4'){
                        that.dataSource1[i].elecFiler += res.result[j].si
                      }else if(that.month === '5'){
                        that.dataSource1[i].elecFiler += res.result[j].wu
                      }else if(that.month === '6'){
                        that.dataSource1[i].elecFiler += res.result[j].liu
                      }else if(that.month === '7'){
                        that.dataSource1[i].elecFiler += res.result[j].qi
                      }else if(that.month === '8'){
                        that.dataSource1[i].elecFiler += res.result[j].ba
                      }else if(that.month === '9'){
                        that.dataSource1[i].elecFiler += res.result[j].jiu
                      }else if(that.month === '10'){
                        that.dataSource1[i].elecFiler += res.result[j].shi
                      }else if(that.month === '11'){
                        that.dataSource1[i].elecFiler += res.result[j].shiyi
                      }else if(that.month === '12'){
                        that.dataSource1[i].elecFiler += res.result[j].shier
                      }

                    }
                  }

                }
              }
            }
            //实际用电量
            if(res1.success){
              for(let i=0;i<that.dataSource1.length;i++){
                if(that.dataSource1[i].elecUse === undefined){
                  that.dataSource1[i].elecUse = 0;
                }
                for(let j=0;j<res1.result.length;j++){
                  if(that.dataSource1[i].shi === res1.result[j].addressShi){
                    that.dataSource1[i].elecUse += res1.result[j].elecUse
                  }
                }

              }
            }
          })

        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>