<template>
  <a-card :bordered="false">
    <div style="margin-bottom: 20px;padding:10px;border:1px solid #e8e8e8">
      <a-row :gutter="24">
        <!--合同总电量-->
        <a-col :sm="24" :md="12" :xl="6" >
          <chart-card :loading="loading" title="发电厂数量" :total="this.generationNum.toString() | NumberFormat">
            <a-tooltip title="" slot="action">个</a-tooltip>
          </chart-card>
        </a-col>
        <!--合同均价-->
        <a-col :sm="24" :md="12" :xl="6" >
          <chart-card :loading="loading" title="合同均价" :total="this.purchasePriceAvg.toFixed(2)">
            <a-tooltip title="" slot="action">mKh</a-tooltip>
          </chart-card>
        </a-col>
        <!--购电量-->
        <a-col :sm="24" :md="12" :xl="6" >
          <chart-card :loading="loading" title="总机组容量" :total="this.installedCapacityAll.toFixed(2) | NumberFormat" >
            <a-tooltip title="" slot="action">KW</a-tooltip>
          </chart-card>
        </a-col>
        <!--售电量-->
        <a-col :sm="24" :md="12" :xl="6" >
          <chart-card :loading="loading" title="交易电量" :total="this.purchaseElectricityAll.toFixed(3) | NumberFormat">
            <a-tooltip title="" slot="action">mWh</a-tooltip>
          </chart-card>
        </a-col>

      </a-row>
    </div>
    <a-card>
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="电站名称">
                <a-input placeholder="请输入电站名称" v-model="queryParam.stationName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="装机容量">
                <a-input placeholder="请输入装机容量" v-model="queryParam.installedCapacity"></a-input>
              </a-form-item>
            </a-col>
            <template v-if="toggleSearchStatus">
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="地址">
                  <a-input placeholder="请输入地址" v-model="queryParam.address"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="离市区距离">
                  <a-input placeholder="请输入离市区距离" v-model="queryParam.distance"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="联系人姓名">
                  <a-input placeholder="请输入联系人姓名" v-model="queryParam.name"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="联系人职务">
                  <a-input placeholder="请输入联系人职务" v-model="queryParam.duty"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="手机号码">
                  <a-input placeholder="请输入手机号码" v-model="queryParam.phone"></a-input>
                </a-form-item>
              </a-col>
              <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <a-form-item label="办公电话">
                  <a-input placeholder="请输入办公电话" v-model="queryParam.officePhone"></a-input>
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
        <a-button @click="handleAddGen" type="primary" icon="plus" v-has="'admin:add'">新增</a-button>
        <a-button type="primary" icon="download" @click="handleExportXls('发电侧信息')">导出</a-button>
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

          <span slot="action" slot-scope="text, record">
          <a @click="elecDetail(record)" v-has="'admin:edit'">详情</a>
          <a-divider type="vertical" />
          <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>
          <a-divider type="vertical" />
          <a-dropdown v-has="'admin:delete'">
            <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDeleteGen(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

        </a-table>
      </div>

      <generationSideInfoB-modal ref="modalForm" @ok="modalFormOkGen"></generationSideInfoB-modal>
      <generation-side-detail-modal ref="elecDetail"></generation-side-detail-modal>
    </a-card>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import GenerationSideInfoBModal from './modules/GenerationSideInfoBModal'
  import GenerationSideDetailModal from './modules/GenerationSideDetailModal'
  import ChartCard from '@/components/ChartCard'
  import { getAction } from '../../api/manage'

  export default {
    name: "GenerationSideInfoBList",
    mixins:[JeecgListMixin],
    components: {
      GenerationSideInfoBModal,
      ChartCard,
      GenerationSideDetailModal
    },
    data () {
      return {
        description: '发电侧信息管理页面',
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
            title:'电站名称',
            align:"center",
            dataIndex: 'stationName'
          },
          {
            title:'装机容量',
            align:"center",
            dataIndex: 'installedCapacity'
          },
          {
            title:'地址',
            align:"center",
            dataIndex: 'address'
          },
          // {
          //   title:'离市区距离',
          //   align:"center",
          //   dataIndex: 'distance'
          // },
          {
            title:'联系人姓名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'联系人职务',
            align:"center",
            dataIndex: 'duty'
          },
          {
            title:'手机号码',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'办公电话',
            align:"center",
            dataIndex: 'officePhone'
          },
          // {
          //   title:'总装机容量',
          //   align:"center",
          //   dataIndex: 'installedCapacityAll'
          // },
          // {
          //   title:'机组类型',
          //   align:"center",
          //   dataIndex: 'unitType'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/generationsideInfob/generationSideInfoB/list",
          delete: "/generationsideInfob/generationSideInfoB/delete",
          deleteBatch: "/generationsideInfob/generationSideInfoB/deleteBatch",
          exportXlsUrl: "/generationsideInfob/generationSideInfoB/exportXls",
          importExcelUrl: "generationsideInfob/generationSideInfoB/importExcel",
        },
        dictOptions:{},
        installedCapacityAll: 0,
        generationNum: 0,
        purchaseElectricityAll: 0,
        purchasePriceAvg: 0,
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
        console.log("初始化=========")
        this.purchaseElectricityAll = 0;
        this.purchasePriceAvg = 0;
        this.installedCapacityAll = 0;
        //发电厂数量 总装机容量
        getAction('/generationsideInfob/generationSideInfoB/queryall',{}).then((res) => {
          if(res.success){
            this.generationNum = res.result.length;
            for(let i=0;i<res.result.length;i++){
              this.installedCapacityAll += parseFloat(res.result[i].installedCapacity)
            }

          }
        })
        //合同均价 交易电量
        let date = new Date();
        let year = date.getFullYear();
        getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year: year }).then((res) => {
          console.log(res)
          if(res.success){
            let purchasePriceAll = 0;
            for(let i=0;i<res.result.length;i++){
              purchasePriceAll += res.result[i].purchasePrice
              this.purchaseElectricityAll += parseFloat(res.result[i].purchaseElectricity)
            }
            this.purchasePriceAvg = purchasePriceAll/res.result.length
          }
        })

      },
      elecDetail(record){
        this.$refs.elecDetail.show(record);
      },
      //添加时更新上方数据
      handleAddGen(){
        this.handleAdd();
        this.initDictConfig();
      },
      //编辑时更新上方数据
      modalFormOkGen(){
        this.modalFormOk();
        this.initDictConfig();
      },
      //删除时更新上方数据
      handleDeleteGen(id){
        this.handleDelete(id);
        setTimeout(this.initDictConfig(),3000);
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>