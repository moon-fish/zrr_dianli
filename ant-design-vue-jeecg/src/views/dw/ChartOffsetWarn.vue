<template>
  <div class="table-page-search-wrapper">
    <a-form layout="inline">
            <a-card :bordered="false" style="overflow: auto;">
              <div style="text-align: center;font-weight: bold;font-size: large;margin-bottom: 20px;">{{this.text}}</div>
              <a-row :gutter="24">
                <a-col :md="4" :sm="24">
                  <a-form-item>
                    <a-select :value="checkType" @change="handType">
                      <!--<a-select-option value="current">实时电量偏差</a-select-option>-->
                      <a-select-option value="day">每日电量偏差</a-select-option>
                      <a-select-option value="month">月度电量偏差</a-select-option>
                      <a-select-option value="year">年度电量偏差</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
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
                  <a-form-item>选择用户</a-form-item>
                </a-col>
                <a-col :md="4" :sm="24">
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
                <a-col :md="2" :sm="24"></a-col>
                <a-col :md="2" :sm="24">
                  <a-form-item>
                    <a-button type="primary" @click="xhQuery">查询</a-button>
                  </a-form-item>
                </a-col>
              </a-row>
              <!--图表区域-->
              <a-row :gutter="24">
                <a-col  :md="24" :sm="24" >
                  <div style="float: right;">(mWh)</div>
                  <line-chart-multid :title="title" :height="height" :fields="lineFields" :dataSource="lineData" style="padding-top: 50px;"></line-chart-multid>
                </a-col>
              </a-row>
              <!--<a-row :gutter="24">-->
                <!--<a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">-->
                  <!--&lt;!&ndash; table区域-begin &ndash;&gt;-->
                  <!--<a-table-->
                    <!--ref="table"-->
                    <!--size="middle"-->
                    <!--rowKey="id"-->
                    <!--bordered-->
                    <!--:columns="columns"-->
                    <!--:pagination="ipagination"-->
                    <!--:dataSource="dataSource"-->
                    <!--@change="handleTableChange">-->
                  <!--</a-table>-->
                  <!--&lt;!&ndash; table区域-end &ndash;&gt;-->
                <!--</a-col>-->
              <!--</a-row>-->
              <!-- 表单区域 -->
              <!--<irrbRiverR-modal ref="modalForm"></irrbRiverR-modal>-->
            </a-card>

    </a-form>
  </div>
</template>

<script>
  import { getAction } from '@/api/manage'
  import BarMultid from '@/components/chart/BarMultid'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import moment from "moment"
  import ARow from 'ant-design-vue/es/grid/Row'
  import ACol from 'ant-design-vue/es/grid/Col'
  import store from '@/store/'

  export default {
    name: "ChartOffsetWarn",
    components: {
      ACol,
      ARow,
      BarMultid,
      LineChartMultid,
    },
    data () {
      return {
        description: '过滤数据管理页面',
        title:'水位|流量趋势图',
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title: '日期',
            align:"center",
            dataIndex: 'stcd',
          },
          {
            title: '用电企业',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '申报电量(kWh)',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '抄见电量(kWh)',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '偏差电量(kWh)',
            align:"center",
            dataIndex: 'qa'
          },
          {
            title: '偏差率',
            align:"center",
            dataIndex: 'qt'
          },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' },
          // }
        ],
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '15'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        url: {
          list: "/irrigation/river/list",
          delete: "/irrigation/river/delete",
          deleteBatch: "/irrigation/river/deleteBatch",
          exportXlsUrl: "irrigation/river/exportXls",
          importExcelUrl: "irrigation/river/importExcel",
        },
        height: 420,
        lineData:[],
        checkType:'day',
        lineFields:['客户申报电量','客户实际用电量','偏差电量'],//柱状图存放横坐标（时间）
        text: '电量偏差',
        areaType:'太原',
        companyType:'',
        companys:[],
        title:'电量',
        /*分公司相关*/
        ziCom:[],
        ziComType:'',
        ziShow:false,
      }
    },
    created(){
      this.dataSource = [
        {stcd:'2020-03',tm:'a科技有限公司',z:25263.00,q:28652.56,qa:3399.56,qt:3.42},
        {stcd:'2020-04',tm:'b产业集团',z:552695.00,q:526932.20,qa:-25763,qt:-20.5},
        {stcd:'2020-04',tm:'c有限公司',z:233651.00,q:243652.83,qa:10001.83,qt:10.26}
      ]
    },
    mounted(){
      this.initArea();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },

    methods: {
      //初始化公司选择 并进行查询
      initArea(e){
        // getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
        //   if(res.success){
        //     for(let i=0;i<res.result.length;i++){
        //       if(res.result[i].addressInfo === '太原'){
        //         this.companyType = res.result[i].comAName
        //         break;
        //       }
        //     }
        //     this.xhQuery();
        //   }
        // })
        console.log(e)
        this.companys = [];
        this.ziCom = [];
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          if(res.success){
            console.log(res.result)
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
            this.xhQuery();
          }
        })
      },
      //选择区域，公司
      areaChange(e){
        this.areaType = e;
        console.log(this.areaType)
      },
      //总公司发生变化
      companyChange(e){
        console.log(e)
        this.ziShow = false;
        this.companyType = e;
        //当公司发生变化时，判断该公司是否为总公司，若是总公司，显示其子公司，若没有则不显示
        this.loadZiComs(e);
      },
      loadZiComs(e){
        this.initArea(e);
      },
      //子公司发生变化
      ziComChange(e){
        this.ziComType = e;
      },
      xhQuery(){
        if(this.checkType === undefined){
          this.checkType = 'day'
        }
        this.lineData = [];

        if(this.checkType==='day'){
          this.text = '电量偏差';
          var totalTime = 24*3600*1000;
          var spanTime = totalTime / 96;
          var startTime = new Date();
          startTime.setHours(0, 0, 0, 0);
          var currentTime = startTime.getTime();
          for(var i = 0; i < 96; i++) {
            currentTime += spanTime;
            let d = new Date(currentTime)
            let d1 = d.getHours()+':'+d.getMinutes()
            this.lineData.push({})
            this.lineData[i]['type'] = d1
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*10)+58
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*10)+50
            this.lineData[i][this.lineFields[2]] = this.lineData[i][this.lineFields[0]] - this.lineData[i][this.lineFields[1]]
          }
        }
        if(this.checkType==='month'){
          this.text = '电量偏差';
          var d = new Date(2020, 5, 0);
          let tianshu = d.getDate();
          for(let i=0;i<tianshu;i++){
            this.lineData.push({})
            if(i<10){
              this.lineData[i]['type'] = "2020-05-0"+(i+1)
            }else{
              this.lineData[i]['type'] = "2020-05-"+(i+1)
            }
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*300)
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*300)
            this.lineData[i][this.lineFields[2]] = this.lineData[i][this.lineFields[0]] - this.lineData[i][this.lineFields[1]]
          }
        }
        if(this.checkType==='year'){
          this.text = '电量偏差';
          for(let i=0;i<12;i++){
            this.lineData.push({})
            if(i<10){
              this.lineData[i]['type'] = "2020-0"+(i+1)
            }else{
              this.lineData[i]['type'] = "2020-"+(i+1)
            }
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*500)
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*500)
            this.lineData[i][this.lineFields[2]] = this.lineData[i][this.lineFields[0]] - this.lineData[i][this.lineFields[1]]
          }

        }

      },

      modalFormOk() {
        // 新增/修改 成功时，重载列表
        // this.loadData();
      },
      //选择查询的类型
      handType(e){
        this.checkType = e;
        console.log(this.checkType)
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

    }
  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less'*/
</style>