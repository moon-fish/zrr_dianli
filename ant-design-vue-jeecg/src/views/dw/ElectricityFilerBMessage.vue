<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
<!--    <div class="table-page-search-wrapper">-->
<!--      <a-form layout="inline" @keyup.enter.native="searchQuery">-->
<!--        <a-row :gutter="24">-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="申报企业名称">-->
<!--              <a-input placeholder="请输入申报企业名称" v-model="queryParam.filerCompanyName"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <a-form-item label="负荷预测">-->
<!--              <a-input placeholder="请输入负荷预测" v-model="queryParam.loadForecasting"></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <template v-if="toggleSearchStatus">-->
<!--            &lt;!&ndash;<a-col :xl="6" :lg="7" :md="8" :sm="24">&ndash;&gt;-->
<!--              &lt;!&ndash;<a-form-item label="交易类型">&ndash;&gt;-->
<!--                &lt;!&ndash;<a-input placeholder="请输入交易类型" v-model="queryParam.transactionType"></a-input>&ndash;&gt;-->
<!--              &lt;!&ndash;</a-form-item>&ndash;&gt;-->
<!--            &lt;!&ndash;</a-col>&ndash;&gt;-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="市场报价">-->
<!--                <a-input placeholder="请输入市场报价" v-model="queryParam.marketQuotation"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="申报开始时间">-->
<!--                <a-input placeholder="请输入申报开始时间" v-model="queryParam.beginDate"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="申报结束时间">-->
<!--                <a-input placeholder="请输入申报结束时间" v-model="queryParam.endDate"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="申报人">-->
<!--                <a-input placeholder="申报人" v-model="queryParam.filer"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="申报人所属公司">-->
<!--                <a-input placeholder="申报人所属公司" v-model="queryParam.filerCompany"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="申报结果">-->
<!--                <a-input placeholder="申报结果" v-model="queryParam.filerResult"></a-input>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--          </template>-->
<!--          <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">-->
<!--              <a @click="handleToggleSearch" style="margin-left: 8px;margin-right: 20px;">-->
<!--                {{ toggleSearchStatus ? '收起' : '展开' }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
<!--              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>-->
<!--              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>-->

<!--            </span>-->
<!--          </a-col>-->
<!--        </a-row>-->
<!--      </a-form>-->
<!--    </div>-->
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
<!--    <div class="table-operator">-->
<!--      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>-->
<!--      <a-button type="primary" icon="download" @click="handleExportXls('电量申报')">导出</a-button>-->
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">导入</a-button>-->
<!--      </a-upload>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>-->
<!--          <a-menu-item key="2" @click="batchAccess"><a-icon type="check"/>通过</a-menu-item>-->
<!--          <a-menu-item key="3" @click="batchBack"><a-icon type="close"/>反驳</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--    </div>-->

    <!-- table区域-begin -->
    <div>
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
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
        <!--设置状态-->
        <span slot="filerStatus" slot-scope="text, record">
          <div v-if="text === '1'">
            <span style="color: green">已调整</span>
          </div>
          <div v-else>
            <span style="color: red">未调整</span>
          </div>
<!--          <div v-if="text === '2'">-->
<!--            <span style="color: orangered">待确认</span>-->
<!--          </div>-->
<!--          <div v-if="text === '3'">-->
<!--            <span style="color: limegreen">申报成功</span>-->
<!--          </div>-->
<!--          <div v-if="text === '4'">-->
<!--            <span style="color: red">未通过</span>-->
<!--          </div>-->
        </span>
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
              <a-menu-item>
                  <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <electricityFilerB-modal ref="modalForm" @ok="modalFormOk"></electricityFilerB-modal>
    <!--<elec-filer-detail-modal ref="modalDetail"></elec-filer-detail-modal>-->
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import ElectricityFilerBModal from './modules/ElectricityFilerBModal'
  import ElecFilerDetailModal from './modules/ElecFilerDetailModal'
  import { putAction } from '@/api/manage'

  export default {
    name: "ElectricityFilerBList",
    mixins:[JeecgListMixin],
    components: {
      ElectricityFilerBModal,
      ElecFilerDetailModal
    },
    data () {
      return {
        description: '电量申报管理页面',
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
            title:'申报企业名称',
            align:"center",
            dataIndex: 'filerCompanyName'
          },
          {
            title:'负荷预测',
            align:"center",
            dataIndex: 'loadForecasting'
          },
          // {
          //   title:'交易类型',
          //   align:"center",
          //   dataIndex: 'transactionType'
          // },
          {
            title:'月份',
            align:"center",
            dataIndex: 'filerMonth'
          },
          {
            title:'申报电量',
            align:"center",
            dataIndex: 'marketQuotation'
          },
          {
            title:'申报开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'申报结束时间',
          //   align:"center",
          //   dataIndex: 'endDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'申报人',
            align:"center",
            dataIndex: 'filer'
          },
          // {
          //   title:'申报人所属公司',
          //   align:"center",
          //   dataIndex: 'filerCompany'
          // },
          {
            title:'申报结果',
            align:"center",
            dataIndex: 'filerResult'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'filerStatus',
            scopedSlots: { customRender: 'filerStatus' }
          },
          // {
          //   title:'日志记录',
          //   align:"center",
          //   dataIndex: 'logRecords'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/electricityfilerb/electricityFilerB/list/1",
          delete: "/electricityfilerb/electricityFilerB/delete",
          deleteBatch: "/electricityfilerb/electricityFilerB/deleteBatch",
          exportXlsUrl: "/electricityfilerb/electricityFilerB/exportXls",
          importExcelUrl: "electricityfilerb/electricityFilerB/importExcel",
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      // DetailElecFiler(){
      //   console.log("dsad")
      //   this.$refs.modalDetail.setData();
      // }
      batchAccess(){
        //console.log(this.selectionRows)
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var entityList = [] ;
          for (var a = 0; a < this.selectionRows.length; a++) {
            this.selectionRows[a].filerStatus = '3'
            entityList.push(this.selectionRows[a]);
          }
          var that = this;
          // console.log(entityList)
          this.$confirm({
            title: "确认通过",
            content: "是否通过选中数据?",
            onOk: function () {
              that.loading = true;
              putAction('/electricityfilerb/electricityFilerB/updateBatch', entityList).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });
        }
      },
      batchBack(){
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var entityList = [] ;
          for (var a = 0; a < this.selectionRows.length; a++) {
            this.selectionRows[a].filerStatus = '4'
            entityList.push(this.selectionRows[a]);
          }
          var that = this;
          console.log(entityList)
          this.$confirm({
            title: "确认通过",
            content: "是否通过选中数据?",
            onOk: function () {
              that.loading = true;
              putAction('/electricityfilerb/electricityFilerB/updateBatch', entityList).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.loadData();
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });
        }

      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>