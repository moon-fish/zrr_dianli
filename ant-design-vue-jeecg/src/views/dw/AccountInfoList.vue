<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <div class="table-page-search-wrapper">
            <a-form layout="inline" @keyup.enter.native="searchQuery">
              <a-row :gutter="24">
                <a-col :xl="6" :lg="7" :md="8" :sm="24">
                  <a-form-item label="地市">
                    <a-input placeholder="请输入地市" v-model="queryParam.username"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :xl="6" :lg="7" :md="8" :sm="24">
                  <a-form-item label="用户区县">
                    <a-input placeholder="请输入区县" v-model="queryParam.username"></a-input>
                  </a-form-item>
                </a-col>
                <template v-if="toggleSearchStatus">
                  <a-col :xl="6" :lg="7" :md="8" :sm="24">
                    <a-form-item label="站址名称">
                      <a-input placeholder="请输入站址名称" v-model="queryParam.username"></a-input>
                    </a-form-item>
                  </a-col>
                  <a-col :xl="6" :lg="7" :md="8" :sm="24">
                    <a-form-item label="站址编码">
                      <a-input placeholder="请输入站址编码" v-model="queryParam.username"></a-input>
                    </a-form-item>
                  </a-col>
                  <a-col :xl="6" :lg="7" :md="8" :sm="24">
                    <a-form-item label="供电方式">
                      <a-input placeholder="请输入供电方式" v-model="queryParam.username"></a-input>
                    </a-form-item>
                  </a-col>
                  <a-col :xl="6" :lg="7" :md="8" :sm="24">
                    <a-form-item label="月份">
                      <a-input placeholder="请输入月份" v-model="queryParam.username"></a-input>
                    </a-form-item>
                  </a-col>
                  <a-col :xl="6" :lg="7" :md="8" :sm="24">
                    <a-form-item label="状态">
                      <a-input placeholder="请输入状态" v-model="queryParam.username"></a-input>
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

        <span slot="action" slot-scope="text, record" v-if="iNum === '1'">

          <!--<a @click="handleEdit(record)">编辑11</a>-->
          <a @click="handleEdit(record)">通过</a>
          <a-divider type="vertical" />
          <a @click="handleEdit(record)">驳回</a>
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

    <accountInfo-modal ref="modalForm" @ok="modalFormOk"></accountInfo-modal>
  </a-card>
  </a-modal>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AccountInfoModal from './modules/AccountInfoModal'
  import { getAction } from '../../api/manage'

  export default {
    name: "AccountInfoList",
    mixins:[JeecgListMixin],
    components: {
      AccountInfoModal
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
            title:'序号',
            align:"center",
            dataIndex: 'serNumber'
          },
          {
            title:'地市',
            align:"center",
            dataIndex: 'addressShi'
          },
          {
            title:'区县',
            align:"center",
            dataIndex: 'addressXian'
          },
          {
            title:'站址名称',
            align:"center",
            dataIndex: 'addressDetail'
          },
          {
            title:'站址编码',
            align:"center",
            dataIndex: 'addressCode'
          },
          {
            title:'供电方式',
            align:"center",
            dataIndex: 'supplyType'
          },
          {
            title:'户名',
            align:"center",
            dataIndex: 'accountName'
          },
          {
            title:'户号',
            align:"center",
            dataIndex: 'accountNumber'
          },
          {
            title:'计量点供电电压',
            align:"center",
            dataIndex: 'supplyVoltage'
          },
          {
            title:'用电地址',
            align:"center",
            dataIndex: 'addressElecuse'
          },
          {
            title:'供电单位',
            align:"center",
            dataIndex: 'supplyUnit'
          },
          // {
          //   title:'一月',
          //   align:"center",
          //   dataIndex: 'yi'
          // },
          // {
          //   title:'二月',
          //   align:"center",
          //   dataIndex: 'er'
          // },
          // {
          //   title:'三月',
          //   align:"center",
          //   dataIndex: 'san'
          // },
          // {
          //   title:'四月',
          //   align:"center",
          //   dataIndex: 'si'
          // },
          // {
          //   title:'五月',
          //   align:"center",
          //   dataIndex: 'wu'
          // },
          // {
          //   title:'六月',
          //   align:"center",
          //   dataIndex: 'liu'
          // },
          // {
          //   title:'七月',
          //   align:"center",
          //   dataIndex: 'qi'
          // },
          {
            title:'八月',
            align:"center",
            dataIndex: 'ba'
          },
          {
            title:'九月',
            align:"center",
            dataIndex: 'jiu'
          },
          {
            title:'十月',
            align:"center",
            dataIndex: 'shi'
          },
          // {
          //   title:'十一月',
          //   align:"center",
          //   dataIndex: 'shiyi'
          // },
          // {
          //   title:'十二月',
          //   align:"center",
          //   dataIndex: 'shier'
          // },
          {
            title:'备注',
            align:"center",
            dataIndex: 'maker'
          },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        url: {
          list: "/accountinfo/accountInfo/list",
          delete: "/accountinfo/accountInfo/delete",
          deleteBatch: "/accountinfo/accountInfo/deleteBatch",
          exportXlsUrl: "/accountinfo/accountInfo/exportXls",
          importExcelUrl: "accountinfo/accountInfo/importExcel",
        },
        dictOptions:{},
        /*弹出框属性*/
        title:'',
        width:1200,
        visible: false,
        confirmLoading: false,
        //是否显示操作列
        iNum:'',
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){

    },
    methods: {
      initDictConfig(){
      },
      show(record,i,month){
        this.visible = true;
        //获取i的值，当为新增时，操作列为审批/驳回，否则不显示
        this.iNum = i
        console.log(i)
        console.log(record)
        console.log(month)
        getAction('/accountuseinfo/accountUseInfo/list',{addressShi:record.address,num:this.iNum,month:month}).then((res) => {
          console.log(res)
          if(res.success){
            this.dataSource = res.result.records
          }
        })
      },
      handleOk(){
        this.close();
      },
      handleCancel(){
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>