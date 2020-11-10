<template>

  <a-card :bordered="false" >
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <!--<a-col :md="2" :sm="24">-->
            <!--<a-form-item>选择地域</a-form-item>-->
          <!--</a-col>-->
          <!--<a-col :md="2" :sm="24">-->
            <!--<a-form-item>-->
              <!--<a-select @change="areaChange" :value="areaType">-->
                <!--<a-select-option value="太原">太原</a-select-option>-->
                <!--<a-select-option value="大同">大同</a-select-option>-->
                <!--<a-select-option value="朔州">朔州</a-select-option>-->
                <!--<a-select-option value="忻州">忻州</a-select-option>-->
                <!--<a-select-option value="阳泉">阳泉</a-select-option>-->
                <!--<a-select-option value="吕梁">吕梁</a-select-option>-->
                <!--<a-select-option value="晋中">晋中</a-select-option>-->
                <!--<a-select-option value="临汾">临汾</a-select-option>-->
                <!--<a-select-option value="运城">运城</a-select-option>-->
                <!--<a-select-option value="晋城">晋城</a-select-option>-->
                <!--<a-select-option value="长治">长治</a-select-option>-->
              <!--</a-select>-->
            <!--</a-form-item>-->
          <!--</a-col>-->
          <a-col :md="2" :sm="24">
            <a-form-item>选择公司:</a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item>
              <a-select @change="companyChange" :value="companyType" style="width: 100%;">
                <a-select-option v-for="(item,index) in this.companys" :key="item" >
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="2" :sm="24"></a-col>
          <a-col :md="4" :sm="24">
            <a-form-item v-if="this.ziShow">
              <a-select @change="ziComChange" :value="ziComType" style="width: 100%;">
                <a-select-option v-for="(item,index) in this.ziCom" :key="item" >
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :md="2" :sm="24">
            <a-form-item>时间间隔:</a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <a-form-item>
              <a-radio-group :value="size" @change="handleSizeChange">
                <a-radio-button value="day">日</a-radio-button>
                <a-radio-button value="month">月</a-radio-button>
                <a-radio-button value="year">年</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="1" :sm="24"></a-col>
          <a-col :md="1" :sm="24">
            <a-form-item>
              日期:
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <a-form-item>
              <div v-if="this.size === 'day'">
                <a-date-picker @change="dayChange" />
              </div>
              <div v-if="this.size === 'month'">
                <a-range-picker @change="monthChange" />
              </div>
              <div v-if="this.size === 'year'">
                <a-range-picker
                  :placeholder="['开始月份', '结束月份']"
                  format="YYYY-MM"
                  :value="valueMonth"
                  :mode="modeMonth"
                  @panelChange="handlePanelChange2"
                  @change="handleChangeMonth"
                />
              </div>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24"></a-col>
          <a-col :md="2" :sm="24">
            <a-form-item>
              <a-button type="primary" @click="xhQuery">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <!--图表区域-->
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <div style="float: right;">(kWh)</div>
            <!--<line-chart-multid :title="title" :height="height" :dataSource="lineData" :fields="lineFields"></line-chart-multid>-->
            <bar-and-line3 :dataSource="barLineData"></bar-and-line3>
          </a-col>
        </a-row>
        <!--列表区域-->
        <a-row>
          <a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
            <div v-if="this.size === 'day'">
              <!-- 操作按钮区域 -->
              <div class="table-operator">
                <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
                <a-button type="primary" icon="download" @click="handleExportXls('历史用电量')">导出</a-button>
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
                  :dataSource="dataSource2"
                  :pagination="ipagination"
                  :loading="loading"
                  :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
                  :scroll="tableScroll"
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
              <!-- table区域-end -->
              <hisload-modal ref="modalForm" @ok="modalFormOk"></hisload-modal>
            </div>
            <div v-if="this.size === 'month'">
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :columns="columns2"
                :dataSource="dataSource1"
                :pagination="ipagination"
                @change="handleTableChange">
              </a-table>
            </div>
          </a-col>
        </a-row>
      </a-form>
    </div>
  </a-card>
  <!--</a-layout>-->
  <!--</a-layout>-->
  <!--</a-row>-->
  <!--</a-form>-->
  <!--</div>-->
</template>

<script>
  import { getAction } from '@/api/manage'
  import BarMultid from '@/components/chart/BarMultid'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import BarAndLine3 from '@/components/chart/BarAndLine3'
  import CoordPie from '@/components/chart/CoordPie'
  import moment from "moment"
  import ACol from 'ant-design-vue/es/grid/Col'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import HisloadModal from './modules/HisloadModal'
  import store from '@/store/'
  import ARow from 'ant-design-vue/es/grid/Row'

  export default {
    name: 'HisPcdl',
    mixins:[JeecgListMixin],
    components: {
      ARow,
      ACol,
      LineChartMultid,
      CoordPie,
      BarAndLine3,
      HisloadModal
    },
    data () {
      return {
        description: '过滤数据管理页面',
        title:'水位|流量趋势图',
        url: {
          list: "/hisload/hisload/list",
          queryall: "/hisload/hisload/queryall",
          delete: "/hisload/hisload/delete",
          deleteBatch: "/hisload/hisload/deleteBatch",
          exportXlsUrl: "hisload/hisload/exportXls",
          importExcelUrl: "hisload/hisload/importExcel",
        },
        dictOptions:{},
        tableScroll:{x :99*88+50},
        height: 350,
        barLineData: [],
        checkType:'current',
        text: '',
        searchValue: '',
        autoExpandParent: true,
        expandedKeys: [],
        checkedKeys: [],
        selectedKeys: [],
        treeData:[],
        size: 'day',
        valueMonth:[],
        modeMonth: ['month', 'month'],
        areaType:'太原',
        companys:[],
        companyType:'',
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
            dataIndex: 'username',
          },
          {
            title:'用电单元名称',
            align:"center",
            dataIndex: 'usernameUnit',
          },
          {
            title:'时间',
            align:"center",
            dataIndex: 'elecDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'0:00:00',
            align:"center",
            dataIndex: 'ldll'
          },
          {
            title:'0:15:00',
            align:"center",
            dataIndex: 'ldyw'
          },
          {
            title:'0:30:00',
            align:"center",
            dataIndex: 'ldss'
          },
          {
            title:'0:45:00',
            align:"center",
            dataIndex: 'ldsw'
          },
          {
            title:'1:00:00',
            align:"center",
            dataIndex: 'ydll'
          },
          {
            title:'1:15:00',
            align:"center",
            dataIndex: 'ydyw'
          },
          {
            title:'1:30:00',
            align:"center",
            dataIndex: 'ydss'
          },
          {
            title:'1:45:00',
            align:"center",
            dataIndex: 'ydsw'
          },
          {
            title:'2:00:00',
            align:"center",
            dataIndex: 'edll'
          },
          {
            title:'2:15:00',
            align:"center",
            dataIndex: 'edyw'
          },
          {
            title:'2:30:00',
            align:"center",
            dataIndex: 'edss'
          },
          {
            title:'2:45:00',
            align:"center",
            dataIndex: 'edsw'
          },
          {
            title:'3:00:00',
            align:"center",
            dataIndex: 'sdll'
          },
          {
            title:'3:15:00',
            align:"center",
            dataIndex: 'sdyw'
          },
          {
            title:'3:30:00',
            align:"center",
            dataIndex: 'sdss'
          },
          {
            title:'3:45:00',
            align:"center",
            dataIndex: 'sssw'
          },
          {
            title:'4:00:00',
            align:"center",
            dataIndex: 'sidll'
          },
          {
            title:'4:15:00',
            align:"center",
            dataIndex: 'sidyw'
          },
          {
            title:'4:30:00',
            align:"center",
            dataIndex: 'sidss'
          },
          {
            title:'4:45:00',
            align:"center",
            dataIndex: 'sidsw'
          },
          {
            title:'5:00:00',
            align:"center",
            dataIndex: 'wdll'
          },
          {
            title:'5:15:00',
            align:"center",
            dataIndex: 'wdyw'
          },
          {
            title:'5:30:00',
            align:"center",
            dataIndex: 'wdss'
          },
          {
            title:'5:45:00',
            align:"center",
            dataIndex: 'wdsw'
          },
          {
            title:'6:00:00',
            align:"center",
            dataIndex: 'liudll'
          },
          {
            title:'6:15:00',
            align:"center",
            dataIndex: 'liudyw'
          },
          {
            title:'6:30:00',
            align:"center",
            dataIndex: 'liudss'
          },
          {
            title:'6:45:00',
            align:"center",
            dataIndex: 'liudsw'
          },
          {
            title:'7:00:00',
            align:"center",
            dataIndex: 'qdll'
          },
          {
            title:'7:15:00',
            align:"center",
            dataIndex: 'qdyw'
          },
          {
            title:'7:30:00',
            align:"center",
            dataIndex: 'qdss',
          },
          {
            title:'7:45:00',
            align:"center",
            dataIndex: 'qdsw'
          },
          {
            title:'8:00:00',
            align:"center",
            dataIndex: 'bdll'
          },
          {
            title:'8:15:00',
            align:"center",
            dataIndex: 'bdyw'
          },
          {
            title:'8:30:00',
            align:"center",
            dataIndex: 'bdss'
          },
          {
            title:'8:45:00',
            align:"center",
            dataIndex: 'bdsw'
          },
          {
            title:'9:00:00',
            align:"center",
            dataIndex: 'jdll'
          },
          {
            title:'9:15:00',
            align:"center",
            dataIndex: 'jdyw'
          },
          {
            title:'9:30:00',
            align:"center",
            dataIndex: 'jdss'
          },
          {
            title:'9:45:00',
            align:"center",
            dataIndex: 'jdsw'
          },
          {
            title:'10:00:00',
            align:"center",
            dataIndex: 'shidll'
          },
          {
            title:'10:15:00',
            align:"center",
            dataIndex: 'shidyw'
          },
          {
            title:'10:30:00',
            align:"center",
            dataIndex: 'shidss'
          },
          {
            title:'10:45:00',
            align:"center",
            dataIndex: 'shidsw'
          },
          {
            title:'11:00:00',
            align:"center",
            dataIndex: 'sydll'
          },
          {
            title:'11:15:00',
            align:"center",
            dataIndex: 'sydyw'
          },
          {
            title:'11:30:00',
            align:"center",
            dataIndex: 'sydss'
          },
          {
            title:'11:45:00',
            align:"center",
            dataIndex: 'sydsw'
          },
          {
            title:'12:00:00',
            align:"center",
            dataIndex: 'sedll'
          },
          {
            title:'12:15:00',
            align:"center",
            dataIndex: 'sedyw'
          },
          {
            title:'12:30:00',
            align:"center",
            dataIndex: 'sedss'
          },
          {
            title:'12:45:00',
            align:"center",
            dataIndex: 'sedsw'
          },
          {
            title:'13:00:00',
            align:"center",
            dataIndex: 'ssdll'
          },
          {
            title:'13:15:00',
            align:"center",
            dataIndex: 'ssdyw'
          },
          {
            title:'13:30:00',
            align:"center",
            dataIndex: 'ssdss'
          },
          {
            title:'13:45:00',
            align:"center",
            dataIndex: 'ssdsw'
          },
          {
            title:'14:00:00',
            align:"center",
            dataIndex: 'ssidll'
          },
          {
            title:'14:15:00',
            align:"center",
            dataIndex: 'ssidyw'
          },
          {
            title:'14:30:00',
            align:"center",
            dataIndex: 'ssidss'
          },
          {
            title:'14:45:00',
            align:"center",
            dataIndex: 'ssidsw'
          },
          {
            title:'15:00:00',
            align:"center",
            dataIndex: 'swdll'
          },
          {
            title:'15:15:00',
            align:"center",
            dataIndex: 'swdyw'
          },
          {
            title:'15:30:00',
            align:"center",
            dataIndex: 'swdss'
          },
          {
            title:'15:45:00',
            align:"center",
            dataIndex: 'swdsw'
          },
          {
            title:'16:00:00',
            align:"center",
            dataIndex: 'sldll'
          },
          {
            title:'16:15:00',
            align:"center",
            dataIndex: 'sldyw'
          },
          {
            title:'16:30:00',
            align:"center",
            dataIndex: 'sldss'
          },
          {
            title:'16:45:00',
            align:"center",
            dataIndex: 'sldsw'
          },
          {
            title:'17:00:00',
            align:"center",
            dataIndex: 'sqdll'
          },
          {
            title:'17:15:00',
            align:"center",
            dataIndex: 'sqdyw'
          },
          {
            title:'17:30:00',
            align:"center",
            dataIndex: 'sqdss'
          },
          {
            title:'17:45:00',
            align:"center",
            dataIndex: 'sqdsw'
          },
          {
            title:'18:00:00',
            align:"center",
            dataIndex: 'sbdll'
          },
          {
            title:'18:15:00',
            align:"center",
            dataIndex: 'sbdyw'
          },
          {
            title:'18:30:00',
            align:"center",
            dataIndex: 'sbdss'
          },
          {
            title:'18:45:00',
            align:"center",
            dataIndex: 'sbdsw'
          },
          {
            title:'19:00:00',
            align:"center",
            dataIndex: 'sjdll'
          },
          {
            title:'19:15:00',
            align:"center",
            dataIndex: 'sjdyw'
          },
          {
            title:'19:30:00',
            align:"center",
            dataIndex: 'sjdss'
          },
          {
            title:'19:45:00',
            align:"center",
            dataIndex: 'sjdsw'
          },
          {
            title:'20:00:00',
            align:"center",
            dataIndex: 'esdll'
          },
          {
            title:'20:15:00',
            align:"center",
            dataIndex: 'esdyw'
          },
          {
            title:'20:30:00',
            align:"center",
            dataIndex: 'esdss'
          },
          {
            title:'20:45:00',
            align:"center",
            dataIndex: 'esdsw'
          },
          {
            title:'21:00:00',
            align:"center",
            dataIndex: 'eydll'
          },
          {
            title:'21:15:00',
            align:"center",
            dataIndex: 'eydyw'
          },
          {
            title:'21:30:00',
            align:"center",
            dataIndex: 'eydss'
          },
          {
            title:'21:45:00',
            align:"center",
            dataIndex: 'eydsw'
          },
          {
            title:'22:00:00',
            align:"center",
            dataIndex: 'eedll'
          },
          {
            title:'22:15:00',
            align:"center",
            dataIndex: 'eedyw'
          },
          {
            title:'22:30:00',
            align:"center",
            dataIndex: 'eedss'
          },
          {
            title:'22:45:00',
            align:"center",
            dataIndex: 'eedsw'
          },
          {
            title:'23:00:00',
            align:"center",
            dataIndex: 'essdll'
          },
          {
            title:'23:15:00',
            align:"center",
            dataIndex: 'essdyw'
          },
          {
            title:'23:30:00',
            align:"center",
            dataIndex: 'essdss'
          },
          {
            title:'23:45:00',
            align:"center",
            dataIndex: 'essdsw'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        columns2:[
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
            dataIndex: 'username',
          },
          {
            title:'用电单元名称',
            align:"center",
            dataIndex: 'usernameUnit',
          },
          {
            title:'时间',
            align:"center",
            dataIndex: 'elecDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'申报电量',
            align:"center",
            dataIndex: 'elecFiler',
          },
          {
            title:'使用电量',
            align:"center",
            dataIndex: 'elecUse',
          },
          {
            title:'偏差电量',
            align:"center",
            dataIndex: 'elecOffset',
          },
          {
            title:'偏差率',
            align:"center",
            dataIndex: 'perOffset',
          },

        ],
        data1: [],
        dataSource1: [],
        dataSource2: [],
        elecDate:'',
        param:{},
        /*分公司相关*/
        ziCom:[],
        ziComType:'',
        ziShow:false,
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){
      // this.initArea();
      this.aaa();
    },
    methods: {
      initDictConfig(){
      },
      modalFormOk() {
        // 新增/修改 成功时，重载列表
        // this.loadData();
      },
      aaa(e){
        this.companys = [];
        // this.companyType = '';
        this.ziCom = [];
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          console.log(res)
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].comTName === null){
                this.companys.push(res.result[i].comAName)
              }else{
                if(e === res.result[i].comTName){
                  this.ziCom.push(res.result[i].comAName)
                  this.ziShow = true;
                }
              }
            }
            //初始加载页面时，赋初始值
            console.log(this.companyType)
            if(this.companyType === ''){
              this.companyType = '总负荷';
              this.xhQuery();
            }

          }
        })
      },
      //初始化公司选择 并进行查询
      initArea(){
        getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].addressInfo === '太原'){
                this.companyType = res.result[i].comAName
                break;
              }
            }
            this.xhQuery();
          }
        })
      },
      //选择区域，公司
      areaChange(e){
        this.areaType = e;
        console.log(this.areaType)
        this.companys = [];
        this.companyType = '';
        //原始的
        // getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
        //   if(res.success){
        //     console.log(res.result)
        //     for(let i=0;i<res.result.length;i++){
        //       if(res.result[i].addressInfo === this.areaType){
        //         this.companys.push(res.result[i].comAName)
        //
        //       }
        //     }
        //     this.companyType = this.companys[0];
        //   }
        // })
      },
      companyChange(e){
        this.ziShow = false;
        this.ziComType = '';
        this.companyType = e;
        //当公司发生变化时，判断该公司是否为总公司，若是总公司，显示其子公司，若没有则不显示
        this.loadZiComs(e);
      },
      loadZiComs(e){
        this.aaa(e);
      },
      //子公司发生变化
      ziComChange(e){
        this.ziComType = e;
      },

      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" === sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
      },
      handleSizeChange(e) {
        this.size = e.target.value;
        console.log(this.size)
        this.elecDate = '';
      },
      dateChange(date, dateString) {
        console.log(date, dateString);
      },
      dayChange(date, dateString){
        console.log(date, dateString);
        this.elecDate = dateString
      },
      //根据月选择日期
      monthChange(date, dateString) {
        // console.log(date, dateString);
        this.elecDate = dateString
      },
      //查询
      xhQuery(){
        this.barLineData = []
        this.dataSource1 = []
        this.dataSource2 = []
        this.param = {}
        console.log(this.size);
        //默认日期为天
        if(this.size === undefined){
          this.size = 'day'
        }
        let bool = true
        //设置参数
        if(this.companyType === '总负荷'){
          if(this.elecDate.length === 2){
            this.param.elecBegin = this.elecDate[0]
            this.param.elecEnd = this.elecDate[1]
          }else{
            this.param.elecDate = this.elecDate
          }
        }else{
          //设置时间
          if(this.elecDate.length === 2){
            this.param.elecBegin = this.elecDate[0]
            this.param.elecEnd = this.elecDate[1]
          }else{
            this.param.username = this.companyType
            this.param.elecDate = this.elecDate
          }
          //判断
          if(this.ziCom.length === 0){
            this.param.username = this.companyType
          }else{
            if(this.ziComType === ''){
              this.$message.warning("请选择分公司！")
              bool = false
            }else{
              this.param.username = this.ziComType
            }
          }
        }
        console.log(this.param)
        if(bool){
          getAction(this.url.queryall,this.param).then((res) => {
            if(res.success){
              this.data1 = [];
              for(let i=0;i<res.result.length;i++){
                this.data1.push(res.result[i])
              }
              if(this.size === 'day'){
                //给列表赋值
                for(let i=0;i<this.companys.length;i++){
                  for(let j=0;j<res.result.length;j++){
                    if(this.companys[i] === res.result[j].username){
                      this.dataSource2.push(res.result[j])
                    }
                  }
                }

                // this.dataSource = res.result
                this.ipagination.total = this.dataSource2.length;
                // console.log(this.dataSource)
                //给图表赋值
                var totalTime = 24*3600*1000;
                var spanTime = totalTime / 96;
                var startTime = new Date();
                startTime.setHours(0, 0, 0, 0);
                var currentTime = startTime.getTime();
                for(var i = 0; i < 96; i++) {
                  currentTime += spanTime;
                  let d = new Date(currentTime)
                  let d1 = d.getHours()+':'+d.getMinutes()
                  this.barLineData.push({})
                  this.barLineData[i]['type'] = d1
                  if(this.barLineData[i]['使用电量'] === undefined){
                    this.barLineData[i]['使用电量'] = 0;
                  }
                  if(this.companyType === '总电量'){
                    for(let j=0;j<this.data1.length;j++){
                      if(d1 === '0:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ldll
                      }
                      if(d1 === '0:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ldyw
                      }
                      if(d1 === '0:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ldss
                      }
                      if(d1 === '0:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ldsw
                      }
                      if(d1 === '1:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydll
                      }
                      if(d1 === '1:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '1:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '1:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '2:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '2:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '2:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '2:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '3:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '3:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '3:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '3:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '4:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '4:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '4:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '4:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '5:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '5:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '5:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '5:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '6:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '6:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '6:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '6:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '7:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '7:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '7:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '7:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '8:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '8:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '8:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '8:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '9:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '9:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '9:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '9:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '10:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '10:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '10:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '10:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '11:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '11:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '11:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '11:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '12:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '12:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '12:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '12:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '13:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '13:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '13:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '13:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '14:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '14:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '14:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '14:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '15:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '15:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '15:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '15:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '16:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '16:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '16:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '16:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '17:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '17:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '17:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '17:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '18:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '18:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '18:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '18:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '19:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '19:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '19:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '19:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '20:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '20:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '20:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '20:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '21:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '21:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '21:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '21:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '22:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '22:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '22:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '22:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }
                      if(d1 === '23:0'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edll
                      }
                      if(d1 === '23:15'){
                        this.barLineData[i]['使用电量'] += this.data1[j].edyw
                      }
                      if(d1 === '23:30'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydss
                      }
                      if(d1 === '23:45'){
                        this.barLineData[i]['使用电量'] += this.data1[j].ydsw
                      }

                    }
                  }else{
                    for(let j=0;j<this.data1.length;j++){
                      if(d1 === '0:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ldll
                      }
                      if(d1 === '0:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ldyw
                      }
                      if(d1 === '0:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ldss
                      }
                      if(d1 === '0:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ldsw
                      }
                      if(d1 === '1:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydll
                      }
                      if(d1 === '1:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '1:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '1:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '2:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '2:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '2:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '2:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '3:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '3:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '3:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '3:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '4:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '4:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '4:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '4:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '5:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '5:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '5:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '5:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '6:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '6:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '6:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '6:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '7:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '7:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '7:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '7:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '8:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '8:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '8:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '8:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '9:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '9:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '9:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '9:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '10:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '10:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '10:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '10:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '11:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '11:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '11:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '11:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '12:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '12:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '12:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '12:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '13:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '13:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '13:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '13:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '14:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '14:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '14:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '14:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '15:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '15:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '15:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '15:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '16:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '16:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '16:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '16:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '17:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '17:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '17:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '17:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '18:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '18:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '18:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '18:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '19:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '19:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '19:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '19:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '20:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '20:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '20:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '20:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '21:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '21:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '21:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '21:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '22:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '22:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '22:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '22:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }
                      if(d1 === '23:0'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edll
                      }
                      if(d1 === '23:15'){
                        this.barLineData[i]['使用电量'] = this.data1[j].edyw
                      }
                      if(d1 === '23:30'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydss
                      }
                      if(d1 === '23:45'){
                        this.barLineData[i]['使用电量'] = this.data1[j].ydsw
                      }

                    }
                  }

                }
                // console.log(this.barLineData)
              }else if(this.size === 'month'){
                console.log(this.elecDate)
                //设置横坐标
                if(this.elecDate.length>0){
                  console.log(this.data1)
                  let n = 0;
                  for(let i=0;i<this.data1.length;i++){
                    this.barLineData.push({})
                    this.barLineData[n]['type'] = this.data1[i].elecDate
                    n++
                  }
                }else{
                  let date = new Date();
                  let mon = date.getMonth();
                  let year = date.getFullYear();
                  var d = new Date(year, mon, 0);
                  let tianshu = d.getDate();
                  //横坐标
                  for(let i=0;i<tianshu;i++){
                    this.barLineData.push({})
                    if(i<9){
                      if(mon<9){
                        this.barLineData[i]['type'] = "2020-0"+mon+"-0"+(i+1)
                      }else{
                        this.barLineData[i]['type'] = "2020-"+mon+"-0"+(i+1)
                      }
                    }else{
                      if(mon<9){
                        this.barLineData[i]['type'] = "2020-0"+mon+"-"+(i+1)
                      }else{
                        this.barLineData[i]['type'] = "2020-"+mon+"-"+(i+1)
                      }
                    }
                  }
                }
                // console.log(this.barLineData)
                //给横坐标赋值
                let m = 0;
                for(let i=0;i<this.barLineData.length;i++){
                  let elecUse = 0;
                  for(let j=0;j<this.data1.length;j++){
                    // console.log(this.data1[j].elecDate)
                    if(this.barLineData[i]['type'] === this.data1[j].elecDate){
                      elecUse += this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].ydll+this.data1[j].ydyw+this.data1[j].ydss+this.data1[j].ydsw+
                        this.data1[j].edll+this.data1[j].edyw+this.data1[j].edss+this.data1[j].edsw+
                        this.data1[j].sdll+this.data1[j].sdyw+this.data1[j].sdss+this.data1[j].sssw+
                        this.data1[j].sidll+this.data1[j].sidyw+this.data1[j].sidss+this.data1[j].sidsw+
                        this.data1[j].wdll+this.data1[j].wdyw+this.data1[j].wdss+this.data1[j].wdsw+
                        this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].qdll+this.data1[j].qdyw+this.data1[j].qdss+this.data1[j].qdsw+
                        this.data1[j].bdll+this.data1[j].bdyw+this.data1[j].bdss+this.data1[j].bdsw+
                        this.data1[j].jdll+this.data1[j].jdyw+this.data1[j].jdss+this.data1[j].jdsw+
                        this.data1[j].shidll+this.data1[j].shidyw+this.data1[j].shidss+this.data1[j].shidsw+
                        this.data1[j].sydll+this.data1[j].sydyw+this.data1[j].sydss+this.data1[j].sydsw+
                        this.data1[j].sedll+this.data1[j].sedyw+this.data1[j].sedss+this.data1[j].sedsw+
                        this.data1[j].ssdll+this.data1[j].ssdyw+this.data1[j].ssdss+this.data1[j].ssdsw+
                        this.data1[j].ssidll+this.data1[j].ssidyw+this.data1[j].ssidss+this.data1[j].ssidsw+
                        this.data1[j].swdll+this.data1[j].swdyw+this.data1[j].swdss+this.data1[j].swdsw+
                        this.data1[j].sldll+this.data1[j].sldyw+this.data1[j].sldss+this.data1[j].sldsw+
                        this.data1[j].sqdll+this.data1[j].sqdyw+this.data1[j].sqdss+this.data1[j].sqdsw+
                        this.data1[j].sbdll+this.data1[j].sbdyw+this.data1[j].sbdss+this.data1[j].sbdsw+
                        this.data1[j].sjdll+this.data1[j].sjdyw+this.data1[j].sjdss+this.data1[j].sjdsw+
                        this.data1[j].esdll+this.data1[j].esdyw+this.data1[j].esdss+this.data1[j].esdsw+
                        this.data1[j].eydll+this.data1[j].eydyw+this.data1[j].eydss+this.data1[j].eydsw+
                        this.data1[j].eedll+this.data1[j].eedyw+this.data1[j].eedss+this.data1[j].eedsw+
                        this.data1[j].essdll+this.data1[j].essdyw+this.data1[j].essdss+this.data1[j].essdsw
                      // console.log(elecUse)
                      //给图表赋值
                      this.barLineData[i]['申报电量'] = Math.round(Math.random()*10)+250
                      this.barLineData[i]['使用电量'] = parseFloat(elecUse.toFixed(3))
                      this.barLineData[i]['偏差电量'] = parseFloat((this.barLineData[i]['申报电量'] - this.barLineData[i]['使用电量']).toFixed(3))
                      this.barLineData[i]['偏差率(%)'] = parseFloat((100*this.barLineData[i]['偏差电量']/this.barLineData[i]['申报电量']).toFixed(3))
                      //给列表赋值
                      this.dataSource1.push({})
                      this.dataSource1[m].username = this.data1[j].username
                      this.dataSource1[m].usernameUnit = this.data1[j].usernameUnit
                      this.dataSource1[m].elecDate = this.data1[j].elecDate
                      this.dataSource1[m].elecFiler = this.barLineData[i]['申报电量']
                      this.dataSource1[m].elecUse = this.barLineData[i]['使用电量']
                      this.dataSource1[m].elecOffset = this.barLineData[i]['偏差电量']
                      this.dataSource1[m].perOffset = this.barLineData[i]['偏差率(%)']
                      m++
                    }
                  }
                }
                this.ipagination.total = this.dataSource1.length
                console.log(this.barLineData)
              }else if(this.size === 'year'){
                let d = new Date();
                let year = d.getFullYear();
                //横坐标
                for(let i=0;i<12;i++){
                  this.barLineData.push({})
                  if(i<9){
                    this.barLineData[i]['type'] = year+"-0"+(i+1)
                  }else{
                    this.barLineData[i]['type'] = year+"-"+(i+1)
                  }
                  // this.barLineData[i]['申报电量'] = Math.round(Math.random()*5000)+500
                  // this.barLineData[i]['使用电量'] = Math.round(Math.random()*500)
                }
                //赋值
                for(let i=0;i<this.barLineData.length;i++){
                  for(let j=0;j<this.data1.length;j++){
                    if(this.barLineData[i]['type'] === this.data1[j].elecDate.substring(0,7)){
                      if(this.barLineData[i]['使用电量'] === undefined){
                        this.barLineData[i]['使用电量'] = 0;
                      }
                      this.barLineData[i]['使用电量'] += this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].ydll+this.data1[j].ydyw+this.data1[j].ydss+this.data1[j].ydsw+
                        this.data1[j].edll+this.data1[j].edyw+this.data1[j].edss+this.data1[j].edsw+
                        this.data1[j].sdll+this.data1[j].sdyw+this.data1[j].sdss+this.data1[j].sssw+
                        this.data1[j].sidll+this.data1[j].sidyw+this.data1[j].sidss+this.data1[j].sidsw+
                        this.data1[j].wdll+this.data1[j].wdyw+this.data1[j].wdss+this.data1[j].wdsw+
                        this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].qdll+this.data1[j].qdyw+this.data1[j].qdss+this.data1[j].qdsw+
                        this.data1[j].bdll+this.data1[j].bdyw+this.data1[j].bdss+this.data1[j].bdsw+
                        this.data1[j].jdll+this.data1[j].jdyw+this.data1[j].jdss+this.data1[j].jdsw+
                        this.data1[j].shidll+this.data1[j].shidyw+this.data1[j].shidss+this.data1[j].shidsw+
                        this.data1[j].sydll+this.data1[j].sydyw+this.data1[j].sydss+this.data1[j].sydsw+
                        this.data1[j].sedll+this.data1[j].sedyw+this.data1[j].sedss+this.data1[j].sedsw+
                        this.data1[j].ssdll+this.data1[j].ssdyw+this.data1[j].ssdss+this.data1[j].ssdsw+
                        this.data1[j].ssidll+this.data1[j].ssidyw+this.data1[j].ssidss+this.data1[j].ssidsw+
                        this.data1[j].swdll+this.data1[j].swdyw+this.data1[j].swdss+this.data1[j].swdsw+
                        this.data1[j].sldll+this.data1[j].sldyw+this.data1[j].sldss+this.data1[j].sldsw+
                        this.data1[j].sqdll+this.data1[j].sqdyw+this.data1[j].sqdss+this.data1[j].sqdsw+
                        this.data1[j].sbdll+this.data1[j].sbdyw+this.data1[j].sbdss+this.data1[j].sbdsw+
                        this.data1[j].sjdll+this.data1[j].sjdyw+this.data1[j].sjdss+this.data1[j].sjdsw+
                        this.data1[j].esdll+this.data1[j].esdyw+this.data1[j].esdss+this.data1[j].esdsw+
                        this.data1[j].eydll+this.data1[j].eydyw+this.data1[j].eydss+this.data1[j].eydsw+
                        this.data1[j].eedll+this.data1[j].eedyw+this.data1[j].eedss+this.data1[j].eedsw+
                        this.data1[j].essdll+this.data1[j].essdyw+this.data1[j].essdss+this.data1[j].essdsw
                    }
                  }
                }

              }

            }
          })
        }

      },
      //月份调整
      handlePanelChange2(value, mode) {
        this.valueMonth = value;
        this.modeMonth = [mode[0] === 'date' ? 'month' : mode[0], mode[1] === 'date' ? 'month' : mode[1]];
      },
      handleChangeMonth(value) {
        this.valueMonth = value;
        console.log(this.valueMonth)
      },
    }
  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less'*/
</style>


