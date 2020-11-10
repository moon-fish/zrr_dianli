<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="合同名称">
              <a-input placeholder="请输入合同名称" v-model="queryParam.contractName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="电力用户名称">
              <a-input placeholder="请输入电力用户名称" v-model="queryParam.userName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="交易类型">
                <a-input placeholder="请输入交易类型" v-model="queryParam.transactionType"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="开始时间">
                <a-input placeholder="请输入开始时间" v-model="queryParam.beginDate"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="结束时间">
                <a-input placeholder="请输入结束时间" v-model="queryParam.endDate"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="购电量">
                <a-input placeholder="请输入购电量" v-model="queryParam.purchaseElectricity"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="分月电量">
                <a-input placeholder="请输入分月电量" v-model="queryParam.monthElectricity"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('电力用户购电合同')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0" v-has="'admin:delete'">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDelContract"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px" > 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
        {{this.cusname}}
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="this.dataSource1"
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
          <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown v-has="'admin:delete'">
            <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDeleteContract(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <userPurchaseContractB-modal ref="modalForm" @ok1="modalFormOk" :cusname1="this.cusname"></userPurchaseContractB-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import UserPurchaseContractBModal from './modules/UserPurchaseContractBModal'
  import { getAction } from '../../api/manage'

  export default {
    name: "UserPurchaseContractBList",
    mixins:[JeecgListMixin],
    components: {
      UserPurchaseContractBModal
    },
    props:{
      cusname:{
        type:String,
        default:'',
      }
    },
    data () {
      return {
        description: '电力用户购电合同管理页面',
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
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    mounted(){
      this.initData();
    },
    methods: {
      initDictConfig(){
      },
      initData(){
        console.log(this.cusname)
        this.dataSource1 = [];
        getAction('/userpurchasecontractb/userPurchaseContractB/list',{ cusname: this.cusname }).then((res) => {
          if(res.success){
            console.log(res.result.records)
            this.dataSource1 = res.result.records
            this.ipagination.total = res.result.total;
          }
        })
      },
      modalFormOk(aa){
        console.log(aa)
        this.initData();
      },
      handleDeleteContract(id){
        this.handleDelete(id);
        this.initData();
      },
      batchDelContract(){
        this.batchDel();
        this.initData();
      }

    },

  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less';*/
</style>