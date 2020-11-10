<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="地市">
              <a-input placeholder="请输入地市" v-model="queryParam.address"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="月份">
              <a-select :value="month" style="width: 100px;margin-right: 20px;" @change="monthChange" >
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
            </a-form-item>
          </a-col>
          <!--<template v-if="toggleSearchStatus">-->
            <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
              <!--<a-form-item label="开始时间">-->
                <!--<a-input placeholder="请输入开始时间" v-model="queryParam.username"></a-input>-->
              <!--</a-form-item>-->
            <!--</a-col>-->
            <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
              <!--<a-form-item label="结束时间">-->
                <!--<a-input placeholder="请输入结束时间" v-model="queryParam.username"></a-input>-->
              <!--</a-form-item>-->
            <!--</a-col>-->
            <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
              <!--<a-form-item label="提交时间">-->
                <!--<a-input placeholder="请输入提交时间" v-model="queryParam.username"></a-input>-->
              <!--</a-form-item>-->
            <!--</a-col>-->
          <!--</template>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <!--<span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">-->
              <!--<a @click="handleToggleSearch" style="margin-left: 8px;margin-right: 20px;">-->
                <!--{{ toggleSearchStatus ? '收起' : '展开' }}-->
                <!--<a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
              <!--</a>-->
              <a-button type="primary" @click="searchAccount" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>

            <!--</span>-->
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <!--<div class="table-operator">-->
      <!--<a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
      <!--<a-button type="primary" icon="download" @click="handleExportXls('户号信息')">导出</a-button>-->
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
        <!--新增用户数量-->
        <a slot="addNumber" slot-scope="text,record" @click="accountInfo(record,'1')">{{ text }}</a>
        <!--减少用户数量-->
        <a slot="reduceNumber" slot-scope="text,record" @click="accountInfo(record,'2')">{{ text }}</a>
        <!--操作列-->
        <span slot="action" slot-scope="text, record">
          <a @click="accountInfo(record)">详情</a>
          <!--<a-divider type="vertical" />-->
          <!--<a @click="handleEdit(record)">编辑</a>-->

          <!--<a-divider type="vertical" />-->
          <!--<a-dropdown>-->
            <!--<a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
            <!--<a-menu slot="overlay">-->
              <!--<a-menu-item>-->
                <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                  <!--<a>删除</a>-->
                <!--</a-popconfirm>-->
              <!--</a-menu-item>-->
            <!--</a-menu>-->
          <!--</a-dropdown>-->
        </span>

      </a-table>
    </div>

    <account-modal ref="modalForm" @ok="modalFormOk"></account-modal>
    <account-info-list ref="accountInfoList"></account-info-list>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AccountModal from './modules/AccountModal'
  import AccountInfoList from './AccountInfoList'
  import {getAction} from '@/api/manage'

  export default {
    name: "AccountList",
    mixins:[JeecgListMixin],
    components: {
      AccountModal,
      AccountInfoList
    },
    data () {
      return {
        description: '户号信息管理页面',
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
            title:'地市',
            align:"center",
            dataIndex: 'address'
          },
          // {
          //   title:'开始时间',
          //   align:"center",
          //   dataIndex: 'dateBegin',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'结束时间',
          //   align:"center",
          //   dataIndex: 'dateEnd',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'供电单位',
          //   align:"center",
          //   dataIndex: 'elecApply'
          // },
          {
            title:'户号数量',
            align:"center",
            dataIndex: 'accountNumber'
          },
          {
            title:'新增用户数量',
            align:"center",
            dataIndex: 'addNumber',
            scopedSlots: { customRender: 'addNumber' }
          },
          {
            title:'减少用户数量',
            align:"center",
            dataIndex: 'reduceNumber',
            scopedSlots: { customRender: 'reduceNumber' }
          },
          {
            title:'新增用户电量',
            align:"center",
            dataIndex: 'addElec'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'marker'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/account/account/list",
          delete: "/account/account/delete",
          deleteBatch: "/account/account/deleteBatch",
          exportXlsUrl: "/account/account/exportXls",
          importExcelUrl: "account/account/importExcel",
        },
        dictOptions:{},
        month:(new Date().getMonth()+1).toString(),
        dataSource1:[],
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    mounted(){
      this.searchAccount();
    },
    methods: {
      initDictConfig(){

      },
      //重写查询方法查询
      searchAccount(){
        var params = this.getQueryParams();//查询条件
        params.month = this.month;
        console.log(params)
        getAction(this.url.list,params).then((res) => {
          if(res.success){
            this.dataSource1 = res.result.records;
            console.log(res.result.total)
            this.ipagination.total = res.result.total;
          }
        })
      },
      //重写重置方法
      searchReset(){
        this.queryParam = {}
        getAction(this.url.list, {month:this.month}).then((res) => {
          if(res.success){
            this.dataSource1 = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      accountInfo(record,i){
        //点击不同的按钮显示不同的户号数据，分为新增，减少和区域
        console.log("dianjijiiiiiiiiiiiii")
        this.$refs.accountInfoList.show(record,i,this.month);
        this.$refs.accountInfoList.title = "户号信息";
      },
      //月份选择发生变化
      monthChange(value){
        console.log(`selected ${value}`);
        this.month = value
        console.log(this.month)
      },


    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>