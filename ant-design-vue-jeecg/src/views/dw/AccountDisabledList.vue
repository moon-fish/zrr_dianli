<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属部门">
              <a-input placeholder="请输入所属部门" v-model="queryParam.sysOrgCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="序号">
              <a-input placeholder="请输入序号" v-model="queryParam.serNumber"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="地市">
              <a-input placeholder="请输入地市" v-model="queryParam.addressShi"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="区县">
              <a-input placeholder="请输入区县" v-model="queryParam.addressXian"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="站址名称">
              <a-input placeholder="请输入站址名称" v-model="queryParam.addressDetail"></a-input>
            </a-form-item>
          </a-col>
          </template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('户号禁用数据')">导出</a-button>
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
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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
    <accountDisabled-modal ref="modalForm" @ok="modalFormOk"></accountDisabled-modal>
  </a-card>
</template>

<script>
  import AccountDisabledModal from './modules/AccountDisabledModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "AccountDisabledList",
    mixins:[JeecgListMixin],
    components: {
      AccountDisabledModal
    },
    data () {
      return {
        description: '户号禁用数据管理页面',
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
            title: '所属部门',
            align:"center",
            dataIndex: 'sysOrgCode'
           },
		   {
            title: '序号',
            align:"center",
            dataIndex: 'serNumber'
           },
		   {
            title: '地市',
            align:"center",
            dataIndex: 'addressShi'
           },
		   {
            title: '区县',
            align:"center",
            dataIndex: 'addressXian'
           },
		   {
            title: '站址名称',
            align:"center",
            dataIndex: 'addressDetail'
           },
		   {
            title: '站址编码',
            align:"center",
            dataIndex: 'addressCode'
           },
		   {
            title: '供电方式',
            align:"center",
            dataIndex: 'supplyType'
           },
		   {
            title: '户名',
            align:"center",
            dataIndex: 'accountName'
           },
		   {
            title: '户号',
            align:"center",
            dataIndex: 'accountNumber'
           },
		   {
            title: '计量点供电电压',
            align:"center",
            dataIndex: 'supplyVoltage'
           },
		   {
            title: '用电地址',
            align:"center",
            dataIndex: 'addressElecuse'
           },
		   {
            title: '供电单位',
            align:"center",
            dataIndex: 'supplyUnit'
           },
		   {
            title: '一月',
            align:"center",
            dataIndex: 'yi'
           },
		   {
            title: '二月',
            align:"center",
            dataIndex: 'er'
           },
		   {
            title: '三月',
            align:"center",
            dataIndex: 'san'
           },
		   {
            title: '四月',
            align:"center",
            dataIndex: 'si'
           },
		   {
            title: '五月',
            align:"center",
            dataIndex: 'wu'
           },
		   {
            title: '六月',
            align:"center",
            dataIndex: 'liu'
           },
		   {
            title: '七月',
            align:"center",
            dataIndex: 'qi'
           },
		   {
            title: '八月',
            align:"center",
            dataIndex: 'ba'
           },
		   {
            title: '九月',
            align:"center",
            dataIndex: 'jiu'
           },
		   {
            title: '十月',
            align:"center",
            dataIndex: 'shi'
           },
		   {
            title: '十一月',
            align:"center",
            dataIndex: 'shiyi'
           },
		   {
            title: '十二月',
            align:"center",
            dataIndex: 'shier'
           },
		   {
            title: '备注',
            align:"center",
            dataIndex: 'maker'
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'accountStatus'
           },
		   {
            title: '是否可用',
            align:"center",
            dataIndex: 'isUseful'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/accountdisabled/accountDisabled/list",
          delete: "/accountdisabled/accountDisabled/delete",
          deleteBatch: "/accountdisabled/accountDisabled/deleteBatch",
          exportXlsUrl: "accountdisabled/accountDisabled/exportXls",
          importExcelUrl: "accountdisabled/accountDisabled/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>