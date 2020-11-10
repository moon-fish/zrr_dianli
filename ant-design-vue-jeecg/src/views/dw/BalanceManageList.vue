<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名称">
              <a-input placeholder="请输入用户名称" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="费用月份">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.monthDate_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.monthDate_end"></j-date>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="费用年份">
                <j-date placeholder="请选择费用年份" v-model="queryParam.yearDate"></j-date>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('结算管理')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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

    <balanceManage-modal ref="modalForm" @ok="modalFormOk"></balanceManage-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BalanceManageModal from './modules/BalanceManageModal'
  import JDate from '@/components/jeecg/JDate.vue'
  import store from '@/store/'
  import { getAction } from '@/api/manage'

  export default {
    name: "BalanceManageList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      BalanceManageModal
    },
    data () {
      return {
        description: '结算管理管理页面',
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
            title:'用户名称',
            align:"center",
            dataIndex: 'username'
          },
          {
            title:'结算月份',
            align:"center",
            dataIndex: 'monthDate',
            customRender:function (text) {
              return !text?"":(text.length>7?text.substr(0,7):text)
            }
          },
          {
            title:'申报电量',
            align:"center",
            dataIndex: 'elecFiler'
          },
          {
            title:'电量',
            align:"center",
            dataIndex: 'elecNum'
          },
          {
            title:'单价',
            align:"center",
            dataIndex: 'elecPrice'
          },
          // {
          //   title:'电费',
          //   align:"center",
          //   dataIndex: 'elecCharge'
          // },
          // {
          //   title:'费用年份',
          //   align:"center",
          //   dataIndex: 'yearDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },\
          {
            title:'区域',
            align:"center",
            dataIndex: 'area'
          },
          {
            title:'考核电量',
            align:"center",
            dataIndex: 'byzd'
          },
          {
            title:'考核电费',
            align:"center",
            dataIndex: 'byzdt'
          },
          {
            title:'节省',
            align:"center",
            dataIndex: 'elecSave'
          },
          {
            title:'负偏',
            align:"center",
            dataIndex: 'percent'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          },
        ],
        url: {
          list: "/balancemanage/balanceManage/list",
          delete: "/balancemanage/balanceManage/delete",
          deleteBatch: "/balancemanage/balanceManage/deleteBatch",
          exportXlsUrl: "/balancemanage/balanceManage/exportXls",
          importExcelUrl: "balancemanage/balanceManage/importExcel",
        },
        dictOptions:{},
        dataSource1:[],
        companys:[],
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        //权限管理
        this.companys = [];
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        //获取该用户区域的公司名称
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              // console.log(res.result[i].username)
              if(this.companys.indexOf(res.result[i].comAName) === -1){
                this.companys.push(res.result[i].comAName)
              }
            }
            //查询列表数据，过滤数据
            getAction('/balancemanage/balanceManage/list',{}).then((res) => {
              if(res.success){
                console.log(res.result.records)
                for(let i=0;i<this.companys.length;i++){
                  for(let j=0;j<res.result.records.length;j++){
                    if(this.companys[i] === res.result.records[j].username){
                      this.dataSource1.push(res.result.records[j])
                    }
                  }
                }
              }
            })
          }
        })


      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>