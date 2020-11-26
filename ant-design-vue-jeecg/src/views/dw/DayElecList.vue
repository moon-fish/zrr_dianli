<template>
  <a-card :bordered="false">
    <a-row :gutter="24">
      <a-col :md="3" :sm="24">
        <a-form-item>客户名称:</a-form-item>
      </a-col>
      <a-col :md="4" :sm="24">
        <a-form-item>
          <a-select @change="handleCustomChange" :value="custom" style="width: 100%">
            <a-select-option v-for="(item,index) in this.companys" :key="item" >
              {{ item }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :md="3" :sm="24">
        <a-form-item>查询方式:</a-form-item>
      </a-col>
      <a-col :md="6" :sm="24">
        <a-form-item>
          <a-radio-group :value="size" @change="handleSizeChange">
            <a-radio-button value="month">按月查询</a-radio-button>
            <a-radio-button value="day">按日查询</a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-col>
      <a-col :md="6" :sm="24">
        <a-form-item>
          <div v-if="this.size === 'day'">
            <a-date-picker @change="dayChange" />
          </div>
          <div v-if="this.size === 'month'">
            <a-month-picker @change="monthChange" />
          </div>
        </a-form-item>
      </a-col>
      <a-col :md="2" :sm="24">
        <a-form-item>
          <a-button type="primary" @click="xhQuery">查询</a-button>
        </a-form-item>
      </a-col>
    </a-row>
    <div>
      <line-chart-multid :dataSource="lineData" :fields="lineFields" :position1="position1" :content1="content1" :position2="position2" :content2="content2" :position3="position3" :content3="content3"></line-chart-multid>
    </div>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--<a-form-item label="日期选择">-->
              <!--<a-range-picker @change="onDateChange" />-->
            <!--</a-form-item>-->
          <!--</a-col>-->
          <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--<a-form-item label="用户名称">-->
              <!--<a-input placeholder="请输入用户名称" ref="username" v-model="queryParam.username"></a-input>-->
            <!--</a-form-item>-->
          <!--</a-col>-->
        <!--<template v-if="toggleSearchStatus">-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="时间">
              <!--<a-input placeholder="请输入时间" v-model="queryParam.elecDate"></a-input>-->
              <a-date-picker @change="dayListChange"/>
            </a-form-item>
          </a-col>
          <!--</template>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="loadDataByCompany" icon="search">查询</a-button>
              <!--<a-button type="primary" @click="loadDataByCompany" icon="reload" style="margin-left: 8px">重置</a-button>-->
              <!--<a @click="handleToggleSearch" style="margin-left: 8px">-->
                <!--{{ toggleSearchStatus ? '收起' : '展开' }}-->
                <!--<a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
              <!--</a>-->
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXlsDayElec('日用电量')">导出</a-button>
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
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->
      <!--:scroll="tableScroll"-->
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSourceDayElec"
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
                <a @click="dayElecDetail(record)">详情</a>
              </a-menu-item>
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
    <dayElec-modal ref="modalForm" @ok="modalFormOk"></dayElec-modal>
    <day-elec-detail-modal ref="DayElecDetailForm"></day-elec-detail-modal>
  </a-card>
</template>

<script>
  import DayElecModal from './modules/DayElecModal'
  import DayElecDetailModal from './modules/DayElecDetailModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import { getAction,downFile } from '../../api/manage'
  import store from '@/store/'

  export default {
    name: "DayElecList",
    mixins:[JeecgListMixin],
    components: {
      DayElecModal,
      LineChartMultid,
      DayElecDetailModal
    },
    data () {
      return {
        description: '日用电量管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            align:"center",
            customRender: function (text, r, index) {
              return (r.rowIndex === '合计') ?  r.rowIndex : (parseInt(index) + 1)
            }
          },
          {
            title: '用户名称',
            align:"center",
            dataIndex: 'username'
          },
          {
            title: '用电单元名称',
            align:"center",
            dataIndex: 'usernameUnit'
          },
          {
            title: '时间',
            align:"center",
            dataIndex: 'elecDate'
          },
          {
            title: '日电量',
            align:"center",
            dataIndex: 'dayElec'
          },
          {
            title: '创建时间',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        columns2: [
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
            fixed:"left",
            width:100,
            dataIndex: 'username'
           },
           // {
           //  title: '用电单元名称',
           //  align:"center",
           //  dataIndex: 'usernameUnit'
           // },
		   {
            title: '时间',
            align:"center",
            fixed:"left",
            width:180,
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
            align:"center",
            fixed:"right",
            width:200,
            scopedSlots: { customRender: 'action' },
          }
        ],
		    url: {
          list: "/dayelec/dayElec/list",
          delete: "/dayelec/dayElec/delete",
          deleteBatch: "/dayelec/dayElec/deleteBatch",
          exportXlsUrl: "dayelec/dayElec/exportXls",
          importExcelUrl: "dayelec/dayElec/importExcel",
       },
        // tableScroll:{x :99*88+50},
        size: 'month',
        custom:'',
        companys:[],
        lineData:[],
        lineFields:[],
        /* 图表根据时间查询*/
        elecDate:'',
        elecBegin:'',
        elecEnd:'',
        dataSourceDayElec:[],
        /* table选中keys*/
        selectedRowKeys: [],
        /* table选中records*/
        selectionRows: [],
        /* 列表根据时间查询*/
        elecListDate:'',
        /* 分页参数
        ipagination1:{
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },*/
        username:'',
        position1: [],
        content1:'',
        position2: [],
        content2:'',
        position3: [],
        content3:'',
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      initDictConfig(){
        this.companys = [];
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        //公司下拉列表
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          if(res.success){
            //console.log(res)
            for(let i=0;i<res.result.length;i++){
              this.companys.push(res.result[i].comAName)
            }
            this.custom = this.companys[0]
            //图表 默认查询当天的日电量申报
            this.xhQuery();
          }
        })
        //列表 加载dataSourceDayElec（显示某一天内所有用户的数据），默认加载当天数据
        this.loadDataByCompany()
      },
      //向详情页跳转
      dayElecDetail(record){
        this.$refs.DayElecDetailForm.show(record)
        this.$refs.DayElecDetailForm.title = "日电量申报历史记录";
      },
      //日期查询发生改变
      onDateChange(date, dateString) {
        console.log(date, dateString);
      },
      handleSizeChange(e) {
        this.size = e.target.value;
        this.elecDate = '';
      },
      dayChange(date, dateString){
        //console.log(date, dateString);
        this.elecDate = dateString
      },
      dayListChange(date, dateString){
        console.log(dateString)
        this.elecListDate = dateString+' 00:00:00'
      },
      //根据月选择日期
      monthChange(date, dateString) {
        // console.log(date, dateString);
        this.elecDate = dateString
      },
      //客户选择发生改变
      handleCustomChange(e){
        this.custom = e
      },
      //图表查询
      xhQuery(){
        let that = this;
        //清空提示
        this.position1 = [];
        this.content1 = '';
        //加载图表
        this.lineFields = ['all'];
        this.lineData = [];
        this.lineFields.push(this.custom)
        //查询所有用户的申报电量
        //console.log(new Date().getFullYear()+"-"+(parseInt(new Date().getMonth())+1)+"-"+new Date().getDate()+" 00:00:00")
        //初始加载或者按天、月查询发生改变时，elecDate为空
        if(this.elecDate === ''){
          let mon = ''
          if(parseInt(new Date().getMonth())+1 < 10){
            mon = '0'+(parseInt(new Date().getMonth())+1)
          }
          if(this.size === 'day'){
            this.elecBegin = new Date().getFullYear()+"-"+mon+"-"+new Date().getDate()+" 00:00:00"
            this.elecEnd = new Date().getFullYear()+"-"+mon+"-"+(parseInt(new Date().getDate())+1)+" 00:00:00"
          }else{
            this.elecBegin = new Date().getFullYear()+"-"+mon+"-01 00:00:00"
            this.elecEnd = new Date().getFullYear()+"-"+mon+"-31 23:59:59"
          }

        }else{
          if(this.size === 'day'){
            this.elecBegin = this.elecDate.split(" ")[0]+" 00:00:00"
            this.elecEnd = this.elecDate.split(" ")[0]+" 23:59:59"
          }else{
            this.elecBegin = this.elecDate.split("-")[0]+"-"+this.elecDate.split("-")[1]+"-01 00:00:00"
            this.elecEnd = this.elecDate.split("-")[0]+"-"+this.elecDate.split("-")[1]+"-31 23:59:59"
          }
        }
        getAction('/dayelec/dayElec/queryall',{elecBegin:this.elecBegin,elecEnd:this.elecEnd}).then((res) => {
          if(res.success){
            //console.log(res.result)
            let company = null;
            //按天查询
            if(this.size === 'day'){
              var totalTime = 24*3600*1000;
              var spanTime = totalTime / 96;
              var startTime = new Date();
              startTime.setHours(0, 0, 0, 0);
              var currentTime = startTime.getTime();
              //96点横坐标
              for(var i = 0; i < 96; i++) {
                currentTime += spanTime;
                let d = new Date(currentTime)
                let d1 = d.getHours() + ':' + d.getMinutes()
                this.lineData.push({})
                this.lineData[i]['type'] = d1
                if(this.lineData[i]['all'] === undefined){
                  this.lineData[i]['all'] = 0
                }
                //赋值

                for(let j=0;j<res.result.length;j++){
                  if(this.custom === res.result[j].username){
                    company = res.result[j]
                  }
                  if(this.lineData[i]['type'] === '0:0'){
                    this.lineData[i]['all'] += res.result[j].ldll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ldll
                    }
                  }else if(this.lineData[i]['type'] === '0:15'){
                    this.lineData[i]['all'] += res.result[j].ldyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ldyw
                    }
                  }else if(this.lineData[i]['type'] === '0:30'){
                    this.lineData[i]['all'] += res.result[j].ldss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ldss
                    }
                  }else if(this.lineData[i]['type'] === '0:45'){
                    this.lineData[i]['all'] += res.result[j].ldsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ldsw
                    }
                  }else if(this.lineData[i]['type'] === '1:0'){
                    this.lineData[i]['all'] += res.result[j].ydll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ydll
                    }
                  }else if(this.lineData[i]['type'] === '1:15'){
                    this.lineData[i]['all'] += res.result[j].ydyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ydyw
                    }
                  }else if(this.lineData[i]['type'] === '1:30'){
                    this.lineData[i]['all'] += res.result[j].ydss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ydss
                    }
                  }else if(this.lineData[i]['type'] === '1:45'){
                    this.lineData[i]['all'] += res.result[j].ydsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ydsw
                    }
                  }else if(this.lineData[i]['type'] === '2:0'){
                    this.lineData[i]['all'] += res.result[j].edll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.edll
                    }
                  }else if(this.lineData[i]['type'] === '2:15'){
                    this.lineData[i]['all'] += res.result[j].edyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.edyw
                    }
                  }else if(this.lineData[i]['type'] === '2:30'){
                    this.lineData[i]['all'] += res.result[j].edss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.edss
                    }
                  }else if(this.lineData[i]['type'] === '2:45'){
                    this.lineData[i]['all'] += res.result[j].edsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.edsw
                    }
                  }else if(this.lineData[i]['type'] === '3:0'){
                    this.lineData[i]['all'] += res.result[j].sdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sdll
                    }
                  }else if(this.lineData[i]['type'] === '3:15'){
                    this.lineData[i]['all'] += res.result[j].sdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sdyw
                    }
                  }else if(this.lineData[i]['type'] === '3:30'){
                    this.lineData[i]['all'] += res.result[j].sdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sdss
                    }
                  }else if(this.lineData[i]['type'] === '3:45'){
                    this.lineData[i]['all'] += res.result[j].sssw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sssw
                    }
                  }else if(this.lineData[i]['type'] === '4:0'){
                    this.lineData[i]['all'] += res.result[j].sidll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sidll
                    }
                  }else if(this.lineData[i]['type'] === '4:15'){
                    this.lineData[i]['all'] += res.result[j].sidyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sidyw
                    }
                  }else if(this.lineData[i]['type'] === '4:30'){
                    this.lineData[i]['all'] += res.result[j].sidss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sidss
                    }
                  }else if(this.lineData[i]['type'] === '4:45'){
                    this.lineData[i]['all'] += res.result[j].sidsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sidsw
                    }
                  }else if(this.lineData[i]['type'] === '5:0'){
                    this.lineData[i]['all'] += res.result[j].wdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.wdll
                    }
                  }else if(this.lineData[i]['type'] === '5:15'){
                    this.lineData[i]['all'] += res.result[j].wdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.wdyw
                    }
                  }else if(this.lineData[i]['type'] === '5:30'){
                    this.lineData[i]['all'] += res.result[j].wdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.wdss
                    }
                  }else if(this.lineData[i]['type'] === '5:45'){
                    this.lineData[i]['all'] += res.result[j].wdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.wdsw
                    }
                  }else if(this.lineData[i]['type'] === '6:0'){
                    this.lineData[i]['all'] += res.result[j].liudll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.liudll
                    }
                  }else if(this.lineData[i]['type'] === '6:15'){
                    this.lineData[i]['all'] += res.result[j].liudyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.liudyw
                    }
                  }else if(this.lineData[i]['type'] === '6:30'){
                    this.lineData[i]['all'] += res.result[j].liudss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.liudss
                    }
                  }else if(this.lineData[i]['type'] === '6:45'){
                    this.lineData[i]['all'] += res.result[j].liudsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.liudsw
                    }
                  }else if(this.lineData[i]['type'] === '7:0'){
                    this.lineData[i]['all'] += res.result[j].qdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.qdll
                    }
                  }else if(this.lineData[i]['type'] === '7:15'){
                    this.lineData[i]['all'] += res.result[j].qdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.qdyw
                    }
                  }else if(this.lineData[i]['type'] === '7:30'){
                    this.lineData[i]['all'] += res.result[j].qdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.qdss
                    }
                  }else if(this.lineData[i]['type'] === '7:45'){
                    this.lineData[i]['all'] += res.result[j].qdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.qdsw
                    }
                  }else if(this.lineData[i]['type'] === '8:0'){
                    this.lineData[i]['all'] += res.result[j].bdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.bdll
                    }
                  }else if(this.lineData[i]['type'] === '8:15'){
                    this.lineData[i]['all'] += res.result[j].bdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.bdyw
                    }
                  }else if(this.lineData[i]['type'] === '8:30'){
                    this.lineData[i]['all'] += res.result[j].bdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.bdss
                    }
                  }else if(this.lineData[i]['type'] === '8:45'){
                    this.lineData[i]['all'] += res.result[j].bdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.bdsw
                    }
                  }else if(this.lineData[i]['type'] === '9:0'){
                    this.lineData[i]['all'] += res.result[j].jdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.jdll
                    }
                  }else if(this.lineData[i]['type'] === '9:15'){
                    this.lineData[i]['all'] += res.result[j].jdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.jdyw
                    }
                  }else if(this.lineData[i]['type'] === '9:30'){
                    this.lineData[i]['all'] += res.result[j].jdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.jdss
                    }
                  }else if(this.lineData[i]['type'] === '9:45'){
                    this.lineData[i]['all'] += res.result[j].jdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.jdsw
                    }
                  }else if(this.lineData[i]['type'] === '10:0'){
                    this.lineData[i]['all'] += res.result[j].shidll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.shidll
                    }
                  }else if(this.lineData[i]['type'] === '10:15'){
                    this.lineData[i]['all'] += res.result[j].shidyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.shidyw
                    }
                  }else if(this.lineData[i]['type'] === '10:30'){
                    this.lineData[i]['all'] += res.result[j].shidss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.shidss
                    }
                  }else if(this.lineData[i]['type'] === '10:45'){
                    this.lineData[i]['all'] += res.result[j].shidsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.shidsw
                    }
                  }else if(this.lineData[i]['type'] === '11:0'){
                    this.lineData[i]['all'] += res.result[j].sydll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sydll
                    }
                  }else if(this.lineData[i]['type'] === '11:15'){
                    this.lineData[i]['all'] += res.result[j].sydyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sydyw
                    }
                  }else if(this.lineData[i]['type'] === '11:30'){
                    this.lineData[i]['all'] += res.result[j].sydss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sydss
                    }
                  }else if(this.lineData[i]['type'] === '11:45'){
                    this.lineData[i]['all'] += res.result[j].sydsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sydsw
                    }
                  }else if(this.lineData[i]['type'] === '12:0'){
                    this.lineData[i]['all'] += res.result[j].sedll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sedll
                    }
                  }else if(this.lineData[i]['type'] === '12:15'){
                    this.lineData[i]['all'] += res.result[j].sedyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sedyw
                    }
                  }else if(this.lineData[i]['type'] === '12:30'){
                    this.lineData[i]['all'] += res.result[j].sedss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sedss
                    }
                  }else if(this.lineData[i]['type'] === '12:45'){
                    this.lineData[i]['all'] += res.result[j].sedsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sedsw
                    }
                  }else if(this.lineData[i]['type'] === '13:0'){
                    this.lineData[i]['all'] += res.result[j].ssdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssdll
                    }
                  }else if(this.lineData[i]['type'] === '13:15'){
                    this.lineData[i]['all'] += res.result[j].ssdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssdyw
                    }
                  }else if(this.lineData[i]['type'] === '13:30'){
                    this.lineData[i]['all'] += res.result[j].ssdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssdss
                    }
                  }else if(this.lineData[i]['type'] === '13:45'){
                    this.lineData[i]['all'] += res.result[j].ssdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssdsw
                    }
                  }else if(this.lineData[i]['type'] === '14:0'){
                    this.lineData[i]['all'] += res.result[j].ssidll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssidll
                    }
                  }else if(this.lineData[i]['type'] === '14:15'){
                    this.lineData[i]['all'] += res.result[j].ssidyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssidyw
                    }
                  }else if(this.lineData[i]['type'] === '14:30'){
                    this.lineData[i]['all'] += res.result[j].ssidss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssidss
                    }
                  }else if(this.lineData[i]['type'] === '14:45'){
                    this.lineData[i]['all'] += res.result[j].ssidsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.ssidsw
                    }
                  }else if(this.lineData[i]['type'] === '15:0'){
                    this.lineData[i]['all'] += res.result[j].swdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.swdll
                    }
                  }else if(this.lineData[i]['type'] === '15:15'){
                    this.lineData[i]['all'] += res.result[j].swdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.swdyw
                    }
                  }else if(this.lineData[i]['type'] === '15:30'){
                    this.lineData[i]['all'] += res.result[j].swdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.swdss
                    }
                  }else if(this.lineData[i]['type'] === '15:45'){
                    this.lineData[i]['all'] += res.result[j].swdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.swdsw
                    }
                  }else if(this.lineData[i]['type'] === '16:0'){
                    this.lineData[i]['all'] += res.result[j].sldll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sldll
                    }
                  }else if(this.lineData[i]['type'] === '16:15'){
                    this.lineData[i]['all'] += res.result[j].sldyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sldyw
                    }
                  }else if(this.lineData[i]['type'] === '16:30'){
                    this.lineData[i]['all'] += res.result[j].sldss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sldss
                    }
                  }else if(this.lineData[i]['type'] === '16:45'){
                    this.lineData[i]['all'] += res.result[j].sldsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sldsw
                    }
                  }else if(this.lineData[i]['type'] === '17:0'){
                    this.lineData[i]['all'] += res.result[j].sqdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sqdll
                    }
                  }else if(this.lineData[i]['type'] === '17:15'){
                    this.lineData[i]['all'] += res.result[j].sqdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sqdyw
                    }
                  }else if(this.lineData[i]['type'] === '17:30'){
                    this.lineData[i]['all'] += res.result[j].sqdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sqdss
                    }
                  }else if(this.lineData[i]['type'] === '17:45'){
                    this.lineData[i]['all'] += res.result[j].sqdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sqdsw
                    }
                  }else if(this.lineData[i]['type'] === '18:0'){
                    this.lineData[i]['all'] += res.result[j].sbdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sbdll
                    }
                  }else if(this.lineData[i]['type'] === '18:15'){
                    this.lineData[i]['all'] += res.result[j].sbdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sbdyw
                    }
                  }else if(this.lineData[i]['type'] === '18:30'){
                    this.lineData[i]['all'] += res.result[j].sbdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sbdss
                    }
                  }else if(this.lineData[i]['type'] === '18:45'){
                    this.lineData[i]['all'] += res.result[j].sbdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sbdsw
                    }
                  }else if(this.lineData[i]['type'] === '19:0'){
                    this.lineData[i]['all'] += res.result[j].sjdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sjdll
                    }
                  }else if(this.lineData[i]['type'] === '19:15'){
                    this.lineData[i]['all'] += res.result[j].sjdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sjdyw
                    }
                  }else if(this.lineData[i]['type'] === '19:30'){
                    this.lineData[i]['all'] += res.result[j].sjdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sjdss
                    }
                  }else if(this.lineData[i]['type'] === '19:45'){
                    this.lineData[i]['all'] += res.result[j].sjdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.sjdsw
                    }
                  }else if(this.lineData[i]['type'] === '20:0'){
                    this.lineData[i]['all'] += res.result[j].esdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.esdll
                    }
                  }else if(this.lineData[i]['type'] === '20:15'){
                    this.lineData[i]['all'] += res.result[j].esdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.esdyw
                    }
                  }else if(this.lineData[i]['type'] === '20:30'){
                    this.lineData[i]['all'] += res.result[j].esdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.esdss
                    }
                  }else if(this.lineData[i]['type'] === '20:45'){
                    this.lineData[i]['all'] += res.result[j].esdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.esdsw
                    }
                  }else if(this.lineData[i]['type'] === '21:0'){
                    this.lineData[i]['all'] += res.result[j].eydll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eydll
                    }
                  }else if(this.lineData[i]['type'] === '21:15'){
                    this.lineData[i]['all'] += res.result[j].eydyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eydyw
                    }
                  }else if(this.lineData[i]['type'] === '21:30'){
                    this.lineData[i]['all'] += res.result[j].eydss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eydss
                    }
                  }else if(this.lineData[i]['type'] === '21:45'){
                    this.lineData[i]['all'] += res.result[j].eydsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eydsw
                    }
                  }else if(this.lineData[i]['type'] === '22:0'){
                    this.lineData[i]['all'] += res.result[j].eedll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eedll
                    }
                  }else if(this.lineData[i]['type'] === '22:15'){
                    this.lineData[i]['all'] += res.result[j].eedyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eedyw
                    }
                  }else if(this.lineData[i]['type'] === '22:30'){
                    this.lineData[i]['all'] += res.result[j].eedss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eedss
                    }
                  }else if(this.lineData[i]['type'] === '22:45'){
                    this.lineData[i]['all'] += res.result[j].eedsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.eedsw
                    }
                  }else if(this.lineData[i]['type'] === '23:0'){
                    this.lineData[i]['all'] += res.result[j].essdll
                    if(company !== null){
                      this.lineData[i][this.custom] = company.essdll
                    }
                  }else if(this.lineData[i]['type'] === '23:15'){
                    this.lineData[i]['all'] += res.result[j].essdyw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.essdyw
                    }
                  }else if(this.lineData[i]['type'] === '23:30'){
                    this.lineData[i]['all'] += res.result[j].essdss
                    if(company !== null){
                      this.lineData[i][this.custom] = company.essdss
                    }
                  }else if(this.lineData[i]['type'] === '23:45'){
                    this.lineData[i]['all'] += res.result[j].essdsw
                    if(company !== null){
                      this.lineData[i][this.custom] = company.essdsw
                    }
                  }
                }
              }
            }else{
              //按月查询 根据选择的月份获取天数，作为横坐标
              let mon = this.elecBegin.split("-")[1];
              let year = this.elecBegin.split("-")[0];
              var d = new Date(year, mon, 0);
              let tianshu = d.getDate();
              for(let i=0;i<tianshu;i++){
                this.lineData.push({});
                if(i<9){
                  this.lineData[i]['type'] = this.elecBegin.substring(0,7)+"-0"+(i+1)
                }else{
                  this.lineData[i]['type'] = this.elecBegin.substring(0,7)+"-"+(i+1)
                }
              }
              console.log(res.result)
              let m = 1
              for(let i=0;i<this.lineData.length;i++){
                for(let j=0;j<res.result.length;j++){
                  if(this.lineData[i]['all'] === undefined){
                    this.lineData[i]['all'] = 0
                  }
                  if(this.lineData[i][this.custom] === undefined){
                    this.lineData[i][this.custom] = 0
                  }
                  if(this.lineData[i]['type'] === res.result[j].elecDate.split(" ")[0]){
                    this.lineData[i]['all'] += res.result[j].dayElec
                    if(this.custom === res.result[j].username){
                      this.lineData[i][this.custom] += res.result[j].dayElec
                      //添加备注信息
                      if(res.result[j].remark !== null){
                        let p = "position"+m
                        if(p === 'position1'){
                          this.position1 = [this.lineData[i]['type'],this.lineData[i][this.custom]];
                          this.content1 = res.result[j].remark
                        }else if(p === 'position2'){
                          this.position2 = [this.lineData[i]['type'],this.lineData[i][this.custom]];
                          this.content2 = res.result[j].remark
                        }else if(p === 'position3'){
                          this.position3 = [this.lineData[i]['type'],this.lineData[i][this.custom]];
                          this.content3 = res.result[j].remark
                        }
                        m++
                      }
                    }

                  }
                }
              }
              //console.log(this.lineData)
            }

          }else{
            that.$message.info("该日期无数据，请核实！")
          }
        })
      },
      //加载某天数据，并添加合计
      loadDataByCompany(){
        let usernames = [];
        this.dataSourceDayElec = [];

        if(this.elecListDate === ''){
          this.elecListDate = "2020-05-16 00:00:00"
        }
        getAction('/dayelec/dayElec/queryall',{elecDate:this.elecListDate}).then((res) => {
          if(res.success){
            //console.log(res)
            console.log(this.companys)
            for(let m=0;m<this.companys.length;m++){

            }
            for(let i=0;i<res.result.length;i++){
              if(usernames.indexOf(res.result[i]['username']) === -1){
                for(let m=0;m<this.companys.length;m++){
                  if(res.result[i]['username'] === this.companys[m]){
                    usernames.push(res.result[i]['username'])
                    this.dataSourceDayElec.push(res.result[i])
                  }
                }

              }
            }

            this.tableAddTotalRow(this.columns, this.dataSourceDayElec)
            // this.ipagination1.total = this.dataSourceDayElec.total;
          }
        })
      },
      /** 表格增加合计行 */
      tableAddTotalRow(columns, ds) {
        let numKey = 'rowIndex'
        let totalRow = { [numKey]: '合计' }
        columns.forEach(column => {
          let { key, dataIndex } = column
          if (![key, dataIndex].includes(numKey)) {

            let total = 0
            ds.forEach(data => {
              total += /^[0-9]+([.]{1}[0-9]+){0,1}$/.test(data[dataIndex]) ? Number.parseFloat(data[dataIndex]) : Number.NaN
              // console.log(data[dataIndex], ':', (/^\d+\.?\d?$/.test(data[dataIndex]) ? Number.parseInt(data[dataIndex]) : Number.NaN))
            })

            if (Number.isNaN(total)) {
              total = '-'
            }
            totalRow[dataIndex] = total
          }
        })

        this.dataSourceDayElec.push(totalRow)
      },
      //重新写导出方法
      handleExportXlsDayElec(fileName){
        if(!fileName || typeof fileName != "string"){
          fileName = "导出文件"
        }
        let param = {...this.queryParam};
        //导出时，若没有进行选择，则默认导出全部
        console.log(this.selectedRowKeys)
        if(this.selectedRowKeys && this.selectedRowKeys.length>0){
          param['selections'] = this.selectedRowKeys.join(",")
        }else{
          for(let i=0;i<this.dataSourceDayElec.length;i++){
            console.log(this.dataSourceDayElec[i])
            this.selectedRowKeys.push(this.dataSourceDayElec[i].id)
          }
          param['selections'] = this.selectedRowKeys.join(",")
        }
        console.log("导出参数",param)
        console.log(this.url.exportXlsUrl)
        downFile(this.url.exportXlsUrl,param).then((data)=>{
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data],{type: 'application/vnd.ms-excel'}), fileName+'.xls')
          }else{
            let url = window.URL.createObjectURL(new Blob([data],{type: 'application/vnd.ms-excel'}))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName+'.xls')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
            this.selectedRowKeys = [];
          }
        })

      },
      onSelectChange(selectedRowKeys, selectionRows) {
        console.log(selectedRowKeys)
        console.log(selectionRows)
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>