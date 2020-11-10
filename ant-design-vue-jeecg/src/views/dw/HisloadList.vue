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
            <a-form-item label="用户名称">
              <a-input placeholder="请输入用户名称" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用电单元名称">
              <a-input placeholder="请输入用电单元名称" v-model="queryParam.usernameUnit"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="时间">
              <a-input placeholder="请输入时间" v-model="queryParam.elecDate"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="0:00:00">
              <a-input placeholder="请输入0:00:00" v-model="queryParam.ldll"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('负荷数据')">导出</a-button>
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
        :scroll="tableScroll"
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
    <hisload-modal ref="modalForm" @ok="modalFormOk"></hisload-modal>
  </a-card>
</template>

<script>
  import HisloadModal from './modules/HisloadModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "HisloadList",
    mixins:[JeecgListMixin],
    components: {
      HisloadModal
    },
    data () {
      return {
        description: '负荷数据管理页面',
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
            title: '用户名称',
            align:"center",
            dataIndex: 'username'
           },
		   {
            title: '负荷单元名称',
            align:"center",
            dataIndex: 'usernameUnit'
           },
		   {
            title: '时间',
            align:"center",
            dataIndex: 'elecDate'
           },
		   {
            title: '0:00:00',
            align:"center",
            dataIndex: 'ldll'
           },
		   {
            title: '0:15:00',
            align:"center",
            dataIndex: 'ldyw'
           },
		   {
            title: '0:30:00',
            align:"center",
            dataIndex: 'ldss'
           },
		   {
            title: '0:45:00',
            align:"center",
            dataIndex: 'ldsw'
           },
		   {
            title: '1:00:00',
            align:"center",
            dataIndex: 'ydll'
           },
		   {
            title: '1:15:00',
            align:"center",
            dataIndex: 'ydyw'
           },
		   {
            title: '1:30:00',
            align:"center",
            dataIndex: 'ydss'
           },
		   {
            title: '1:45:00',
            align:"center",
            dataIndex: 'ydsw'
           },
		   {
            title: '2:00:00',
            align:"center",
            dataIndex: 'edll'
           },
		   {
            title: '2:15:00',
            align:"center",
            dataIndex: 'edyw'
           },
		   {
            title: '2:30:00',
            align:"center",
            dataIndex: 'edss'
           },
		   {
            title: '2:45:00',
            align:"center",
            dataIndex: 'edsw'
           },
		   {
            title: '3:00:00',
            align:"center",
            dataIndex: 'sdll'
           },
		   {
            title: '3:15:00',
            align:"center",
            dataIndex: 'sdyw'
           },
		   {
            title: '3:30:00',
            align:"center",
            dataIndex: 'sdss'
           },
		   {
            title: '3:45:00',
            align:"center",
            dataIndex: 'sssw'
           },
		   {
            title: '4:00:00',
            align:"center",
            dataIndex: 'sidll'
           },
		   {
            title: '4:15:00',
            align:"center",
            dataIndex: 'sidyw'
           },
		   {
            title: '4:30:00',
            align:"center",
            dataIndex: 'sidss'
           },
		   {
            title: '4:45:00',
            align:"center",
            dataIndex: 'sidsw'
           },
		   {
            title: '5:00:00',
            align:"center",
            dataIndex: 'wdll'
           },
		   {
            title: '5:15:00',
            align:"center",
            dataIndex: 'wdyw'
           },
		   {
            title: '5:30:00',
            align:"center",
            dataIndex: 'wdss'
           },
		   {
            title: '5:45:00',
            align:"center",
            dataIndex: 'wdsw'
           },
		   {
            title: '6:00:00',
            align:"center",
            dataIndex: 'liudll'
           },
		   {
            title: '6:15:00',
            align:"center",
            dataIndex: 'liudyw'
           },
		   {
            title: '6:30:00',
            align:"center",
            dataIndex: 'liudss'
           },
		   {
            title: '6:45:00',
            align:"center",
            dataIndex: 'liudsw'
           },
		   {
            title: '7:00:00',
            align:"center",
            dataIndex: 'qdll'
           },
		   {
            title: '7:15:00',
            align:"center",
            dataIndex: 'qdyw'
           },
		   {
            title: '7:30:00',
            align:"center",
            dataIndex: 'qdss'
           },
		   {
            title: '7:45:00',
            align:"center",
            dataIndex: 'qdsw'
           },
		   {
            title: '8:00:00',
            align:"center",
            dataIndex: 'bdll'
           },
		   {
            title: '8:15:00',
            align:"center",
            dataIndex: 'bdyw'
           },
		   {
            title: '8:30:00',
            align:"center",
            dataIndex: 'bdss'
           },
		   {
            title: '8:45:00',
            align:"center",
            dataIndex: 'bdsw'
           },
		   {
            title: '9:00:00',
            align:"center",
            dataIndex: 'jdll'
           },
		   {
            title: '9:15:00',
            align:"center",
            dataIndex: 'jdyw'
           },
		   {
            title: '9:30:00',
            align:"center",
            dataIndex: 'jdss'
           },
		   {
            title: '9:45:00',
            align:"center",
            dataIndex: 'jdsw'
           },
		   {
            title: '10:00:00',
            align:"center",
            dataIndex: 'shidll'
           },
		   {
            title: '10:15:00',
            align:"center",
            dataIndex: 'shidyw'
           },
		   {
            title: '10:30:00',
            align:"center",
            dataIndex: 'shidss'
           },
		   {
            title: '10:45:00',
            align:"center",
            dataIndex: 'shidsw'
           },
		   {
            title: '11:00:00',
            align:"center",
            dataIndex: 'sydll'
           },
		   {
            title: '11:15:00',
            align:"center",
            dataIndex: 'sydyw'
           },
		   {
            title: '11:30:00',
            align:"center",
            dataIndex: 'sydss'
           },
		   {
            title: '11:45:00',
            align:"center",
            dataIndex: 'sydsw'
           },
		   {
            title: '12:00:00',
            align:"center",
            dataIndex: 'sedll'
           },
		   {
            title: '12:15:00',
            align:"center",
            dataIndex: 'sedyw'
           },
		   {
            title: '12:30:00',
            align:"center",
            dataIndex: 'sedss'
           },
		   {
            title: '12:45:00',
            align:"center",
            dataIndex: 'sedsw'
           },
		   {
            title: '13:00:00',
            align:"center",
            dataIndex: 'ssdll'
           },
		   {
            title: '13:15:00',
            align:"center",
            dataIndex: 'ssdyw'
           },
		   {
            title: '13:30:00',
            align:"center",
            dataIndex: 'ssdss'
           },
		   {
            title: '13:45:00',
            align:"center",
            dataIndex: 'ssdsw'
           },
		   {
            title: '14:00:00',
            align:"center",
            dataIndex: 'ssidll'
           },
		   {
            title: '14:15:00',
            align:"center",
            dataIndex: 'ssidyw'
           },
		   {
            title: '14:30:00',
            align:"center",
            dataIndex: 'ssidss'
           },
		   {
            title: '14:45:00',
            align:"center",
            dataIndex: 'ssidsw'
           },
		   {
            title: '15:00:00',
            align:"center",
            dataIndex: 'swdll'
           },
		   {
            title: '15:15:00',
            align:"center",
            dataIndex: 'swdyw'
           },
		   {
            title: '15:30:00',
            align:"center",
            dataIndex: 'swdss'
           },
		   {
            title: '15:45:00',
            align:"center",
            dataIndex: 'swdsw'
           },
		   {
            title: '16:00:00',
            align:"center",
            dataIndex: 'sldll'
           },
		   {
            title: '16:15:00',
            align:"center",
            dataIndex: 'sldyw'
           },
		   {
            title: '16:30:00',
            align:"center",
            dataIndex: 'sldss'
           },
		   {
            title: '16:45:00',
            align:"center",
            dataIndex: 'sldsw'
           },
		   {
            title: '17:00:00',
            align:"center",
            dataIndex: 'sqdll'
           },
		   {
            title: '17:15:00',
            align:"center",
            dataIndex: 'sqdyw'
           },
		   {
            title: '17:30:00',
            align:"center",
            dataIndex: 'sqdss'
           },
		   {
            title: '17:45:00',
            align:"center",
            dataIndex: 'sqdsw'
           },
		   {
            title: '18:00:00',
            align:"center",
            dataIndex: 'sbdll'
           },
		   {
            title: '18:15:00',
            align:"center",
            dataIndex: 'sbdyw'
           },
		   {
            title: '18:30:00',
            align:"center",
            dataIndex: 'sbdss'
           },
		   {
            title: '18:45:00',
            align:"center",
            dataIndex: 'sbdsw'
           },
		   {
            title: '19:00:00',
            align:"center",
            dataIndex: 'sjdll'
           },
		   {
            title: '19:15:00',
            align:"center",
            dataIndex: 'sjdyw'
           },
		   {
            title: '19:30:00',
            align:"center",
            dataIndex: 'sjdss'
           },
		   {
            title: '19:45:00',
            align:"center",
            dataIndex: 'sjdsw'
           },
		   {
            title: '20:00:00',
            align:"center",
            dataIndex: 'esdll'
           },
		   {
            title: '20:15:00',
            align:"center",
            dataIndex: 'esdyw'
           },
		   {
            title: '20:30:00',
            align:"center",
            dataIndex: 'esdss'
           },
		   {
            title: '20:45:00',
            align:"center",
            dataIndex: 'esdsw'
           },
		   {
            title: '21:00:00',
            align:"center",
            dataIndex: 'eydll'
           },
		   {
            title: '21:15:00',
            align:"center",
            dataIndex: 'eydyw'
           },
		   {
            title: '21:30:00',
            align:"center",
            dataIndex: 'eydss'
           },
		   {
            title: '21:45:00',
            align:"center",
            dataIndex: 'eydsw'
           },
		   {
            title: '22:00:00',
            align:"center",
            dataIndex: 'eedll'
           },
		   {
            title: '22:15:00',
            align:"center",
            dataIndex: 'eedyw'
           },
		   {
            title: '22:30:00',
            align:"center",
            dataIndex: 'eedss'
           },
		   {
            title: '22:45:00',
            align:"center",
            dataIndex: 'eedsw'
           },
		   {
            title: '23:00:00',
            align:"center",
            dataIndex: 'essdll'
           },
		   {
            title: '23:15:00',
            align:"center",
            dataIndex: 'essdyw'
           },
		   {
            title: '23:30:00',
            align:"center",
            dataIndex: 'essdss'
           },
		   {
            title: '23:45:00',
            align:"center",
            dataIndex: 'essdsw'
           },
          {
            title: '操作',
            dataIndex: 'action',
            fixed:"right",
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
              list: "/hisload/hisload/list",
              delete: "/hisload/hisload/delete",
              deleteBatch: "/hisload/hisload/deleteBatch",
              exportXlsUrl: "hisload/hisload/exportXls",
              importExcelUrl: "hisload/hisload/importExcel",
           },
        tableScroll:{x :99*88+50}
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