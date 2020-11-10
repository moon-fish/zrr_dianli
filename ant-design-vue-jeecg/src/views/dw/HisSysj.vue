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
            <a-form-item>选择公司</a-form-item>
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

          <!--<a-col :md="2" :sm="24">-->
            <!--<a-form-item>时间间隔</a-form-item>-->
          <!--</a-col>-->
          <!--<a-col :md="4" :sm="24">-->
            <!--<a-form-item>-->
              <!--<a-radio-group :value="size" @change="handleSizeChange">-->
                <!--<a-radio-button value="month">月</a-radio-button>-->
                <!--<a-radio-button value="year">年</a-radio-button>-->
              <!--</a-radio-group>-->
            <!--</a-form-item>-->
          <!--</a-col>-->
          <!--<a-col :md="2" :sm="24">-->
            <!--<a-form-item>-->
              <!--日期-->
            <!--</a-form-item>-->
          <!--</a-col>-->
          <!--<a-col :md="4" :sm="24">-->
            <!--<a-form-item>-->

              <!--<div v-if="this.size === 'month'">-->
                <!--<a-range-picker @change="onChange" />-->
              <!--</div>-->
              <!--<div v-if="this.size === 'year'">-->
                <!--<a-range-picker-->
                  <!--:placeholder="['开始月份', '结束月份']"-->
                  <!--format="YYYY-MM"-->
                  <!--:value="valueMonth"-->
                  <!--:mode="modeMonth"-->
                  <!--@panelChange="handlePanelChange2"-->
                  <!--@change="handleChangeMonth"-->
                <!--/>-->
              <!--</div>-->
            <!--</a-form-item>-->
          <!--</a-col>-->
          <a-col :md="16" :sm="24">
          </a-col>
          <a-col :md="2" :sm="24">
            <a-form-item>
              <a-button type="primary" @click="xhQuery">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <!--图表区域-->
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <div style="float: right;">(元)</div>
            <line-chart-multid :title="title" :height="height" :dataSource="lineData" :fields="lineFields"></line-chart-multid>
          </a-col>
        </a-row>
        <!--<line-chart-multid :height="height" :fields="lineFields" :dataSource="lineData" title="用户负荷叠加曲线"/>-->
        <!--<bar :height="height" :dataSource="barData" title="用户最高负荷"></bar>-->
        <a-row>
          <a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :columns="columns2"
              :dataSource="dataSource2"
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

              <span slot="effective" slot-scope="text, record">
            <div v-if="text === '0'">
              <span style="color: limegreen">正常</span>
            </div>
            <div v-if="text === '1'">
              <span style="color: #ffa95e">即将过期</span>
            </div>
            <div v-if="text === '2'">
              <span style="color: red">到期</span>
            </div>
          </span>
              <span slot="action" slot-scope="text, record">
          <a @click="userDetail(record)" v-has="'admin:edit'">用户详情</a>
            <a-divider type="vertical" />
          <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown v-has="'admin:delete'">
            <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => cusDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

            </a-table>
          </a-col>
        </a-row>
      </a-form>
    </div>
  </a-card>

</template>

<script>
  import { getAction } from '@/api/manage'
  import BarMultid from '@/components/chart/BarMultid'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import CoordPie from '@/components/chart/CoordPie'
  import moment from "moment"
  import ACol from 'ant-design-vue/es/grid/Col'
  import store from '@/store/'

  export default {
    name: 'XhqjElec',
    components: {
      ACol,
      BarMultid,
      LineChartMultid,
      CoordPie,
    },
    data () {
      return {
        description: '过滤数据管理页面',
        title:'',
        // 表头
        columns2:[],
        dataSource2:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42 },
        ],
        ipagination:false,
        url: {
          list: "/irrigation/river/list",
          delete: "/irrigation/river/delete",
          deleteBatch: "/irrigation/river/deleteBatch",
          exportXlsUrl: "irrigation/river/exportXls",
          importExcelUrl: "irrigation/river/importExcel",
        },
        height: 350,
        lineData: [],
        lineFields:['总收益'],
        checkType:'current',
        text: '',
        searchValue: '',
        autoExpandParent: true,
        expandedKeys: [],
        checkedKeys: [],
        selectedKeys: [],
        treeData:[],
        size: 'month',
        valueMonth:[],
        modeMonth: ['month', 'month'],
        areaType:'太原',
        companys:[],
        companyType:'',
        loading: false,
        xyElecPrice: 0,
        contractAverage: 0,
        sellPrice: 0,
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
      //加载树
      // this.initArea();
      this.aaa();
    },
    methods: {
      modalFormOk() {
        // 新增/修改 成功时，重载列表
        // this.loadData();
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
      aaa(e){
        this.ziCom = [];
        this.companys = [];
        // this.companyType = '';
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
            if(this.companyType === ''){
              this.companyType = '总负荷';
              this.xhQuery();
            }

          }
        })

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
      onExpand(expandedKeys) {
        // console.log('onExpand', expandedKeys);
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.expandedKeys = expandedKeys;
        this.autoExpandParent = false;
      },
      onCheck(checkedKeys) {
        // console.log('onCheck', checkedKeys);
        this.checkedKeys = checkedKeys;
      },
      onSelect(selectedKeys, info) {
        // console.log('onSelect', info);
        this.selectedKeys = selectedKeys;
      },
      handleSizeChange(e) {
        this.size = e.target.value;
        // console.log(this.size)

      },
      dateChange(date, dateString) {
        // console.log(date, dateString);
      },
      onChange(date, dateString) {
        // console.log(date, dateString);
      },
      //查询
      xhQuery(){
        this.lineFields = ['总收益'];
        this.lineData = []
        // console.log(this.size);
        // console.log(this.companyType);
        let bool = true
        if(this.ziCom.length === 0){
          this.lineFields.push(this.companyType)
        }else{
          if(this.ziComType === ''){
            this.$message.warning("请选择分公司！")
            bool = false
          }else{
            this.lineFields.push(this.ziComType)
          }
        }

        // console.log(this.lineFields)
        // if(this.size === 'month'){
        //   var d = new Date(2020, 5, 0);
        //   let tianshu = d.getDate();
        //   for(let i=0;i<tianshu;i++){
        //     this.lineData.push({})
        //     if(i<10){
        //       this.lineData[i]['type'] = "2020-05-0"+(i+1)
        //     }else{
        //       this.lineData[i]['type'] = "2020-05-"+(i+1)
        //     }
        //     this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*300)+2700
        //     this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*300)
        //   }
        // }else if(this.size === 'year'){
        if(bool){
          //获取协议电价
          this.sellPrice = 0;
          this.xyElecPrice = 0;
          getAction('/userpurchasecontractb/userPurchaseContractB/queryall',{}).then((res) => {
            if(res.success){
              // console.log(res.result)
              //用户供电量总和
              let elecA = 0;
              for(let i=0;i<res.result.length;i++){
                elecA += parseFloat(res.result[i].purchaseElectricity)
              }
              //sellPrice 用户平均购电价：各个用户电量*电价/总电量
              // console.log(elecA)
              for(let i=0;i<res.result.length;i++){
                //查询本年数据
                if(res.result[i].beginDate.split("-")[0] === y.toString()){
                  this.sellPrice += res.result[i].purchasePrice*parseFloat(res.result[i].purchaseElectricity)/elecA
                  if(res.result[i].userName === this.companyType){
                    this.xyElecPrice = res.result[i].purchasePrice
                  }
                }
              }
              // console.log(this.sellPrice)
            }
          })
          //合同均价（通过购电合同与我的交易综合计算）
          //购电合同
          let contractAve = 0;
          this.contractElec = 0;
          let date = new Date();
          let y = date.getFullYear();
          getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year:y }).then((res) => {
            if(res.success){
              for(let i=0;i<res.result.length;i++){
                // 月合同总电量  (合同总电量+交易电量)
                this.contractElec += parseInt(res.result[i].purchaseElectricity)
                // 月合同均价
                contractAve += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice
                // console.log(contractAve)
              }
            }
          })
          // 我的交易
          getAction('/mytrade/myTrade/queryall',{ year:y }).then((res) => {
            // console.log(res)
            if(res.success){
              for(let i=0;i<res.result.length;i++){
                // 总电量 减去转出电量
                if(res.result[i].zrType === '2'){
                  this.contractElec -= parseInt(res.result[i].tradeElec)
                  contractAve -= res.result[i].tradeElec*res.result[i].tradePrice
                }else{
                  this.contractElec += parseInt(res.result[i].tradeElec)
                  contractAve += res.result[i].tradeElec*res.result[i].tradePrice
                }
              }
              // console.log(contractAve)
              // console.log(this.contractElec)
              this.contractAverage = contractAve/this.contractElec

            }
          })
          //赋值
          getAction('/hiselec/hiselec/queryall',{}).then((res) => {
            if(res.success){
              //横坐标
              for(let i=0;i<12;i++){
                this.lineData.push({})
                if(i<9){
                  this.lineData[i]['type'] = y+"-0"+(i+1)
                }else{
                  this.lineData[i]['type'] = y+"-"+(i+1)
                }
                // this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*500)+4000
                // this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*500)
              }
              for(let i=0;i<this.lineData.length;i++){
                for(let j=0;j<res.result.length;j++){
                  if(this.lineData[i]['type'] === res.result[j].elecDate.split(" ")[0].substring(0,7)){
                    if(this.lineData[i]['总收益'] === undefined){
                      this.lineData[i]['总收益'] = 0;
                    }
                    if(this.lineData[i][this.lineFields[1]] === undefined){
                      this.lineData[i][this.lineFields[1]] = 0;
                    }
                    let elecUse = res.result[j].ldll+res.result[j].ldyw+res.result[j].ldss+res.result[j].ldsw+
                      res.result[j].ydll+res.result[j].ydyw+res.result[j].ydss+res.result[j].ydsw+
                      res.result[j].edll+res.result[j].edyw+res.result[j].edss+res.result[j].edsw+
                      res.result[j].sdll+res.result[j].sdyw+res.result[j].sdss+res.result[j].sssw+
                      res.result[j].sidll+res.result[j].sidyw+res.result[j].sidss+res.result[j].sidsw+
                      res.result[j].wdll+res.result[j].wdyw+res.result[j].wdss+res.result[j].wdsw+
                      res.result[j].ldll+res.result[j].ldyw+res.result[j].ldss+res.result[j].ldsw+
                      res.result[j].qdll+res.result[j].qdyw+res.result[j].qdss+res.result[j].qdsw+
                      res.result[j].bdll+res.result[j].bdyw+res.result[j].bdss+res.result[j].bdsw+
                      res.result[j].jdll+res.result[j].jdyw+res.result[j].jdss+res.result[j].jdsw+
                      res.result[j].shidll+res.result[j].shidyw+res.result[j].shidss+res.result[j].shidsw+
                      res.result[j].sydll+res.result[j].sydyw+res.result[j].sydss+res.result[j].sydsw+
                      res.result[j].sedll+res.result[j].sedyw+res.result[j].sedss+res.result[j].sedsw+
                      res.result[j].ssdll+res.result[j].ssdyw+res.result[j].ssdss+res.result[j].ssdsw+
                      res.result[j].ssidll+res.result[j].ssidyw+res.result[j].ssidss+res.result[j].ssidsw+
                      res.result[j].swdll+res.result[j].swdyw+res.result[j].swdss+res.result[j].swdsw+
                      res.result[j].sldll+res.result[j].sldyw+res.result[j].sldss+res.result[j].sldsw+
                      res.result[j].sqdll+res.result[j].sqdyw+res.result[j].sqdss+res.result[j].sqdsw+
                      res.result[j].sbdll+res.result[j].sbdyw+res.result[j].sbdss+res.result[j].sbdsw+
                      res.result[j].sjdll+res.result[j].sjdyw+res.result[j].sjdss+res.result[j].sjdsw+
                      res.result[j].esdll+res.result[j].esdyw+res.result[j].esdss+res.result[j].esdsw+
                      res.result[j].eydll+res.result[j].eydyw+res.result[j].eydss+res.result[j].eydsw+
                      res.result[j].eedll+res.result[j].eedyw+res.result[j].eedss+res.result[j].eedsw+
                      res.result[j].essdll+res.result[j].essdyw+res.result[j].essdss+res.result[j].essdsw
                    // console.log(this.sellPrice)
                    // console.log(this.contractAverage)
                    if(res.result[j].username === this.lineFields[1]){
                      this.lineData[i][this.lineFields[1]] += elecUse*(this.xyElecPrice-this.contractAverage)
                    }
                    this.lineData[i]['总收益'] += elecUse*(this.sellPrice-this.contractAverage)
                  }
                }
              }
              // console.log(this.lineData)
              //保留两位小数
              for(let i=0;i<this.lineData.length;i++){
                if(this.lineData[i]['总收益'] !== undefined){
                  this.lineData[i]['总收益'] = parseFloat(this.lineData[i]['总收益'].toFixed(2))
                }
                if(this.lineData[i][this.lineFields[1]] !== undefined){
                  this.lineData[i][this.lineFields[1]] = parseFloat(this.lineData[i][this.lineFields[1]].toFixed(2))
                }
              }
              //表单列
              this.columns2 = [];
              this.columns2 = [{title:'用户名称',align:'center',dataIndex:'username'}]
              for(let i=0;i<this.lineData.length;i++){
                this.columns2.push({});
                this.columns2[i+1]['title'] = this.lineData[i]['type'];
                this.columns2[i+1]['align'] = 'center'
                this.columns2[i+1]['dataIndex'] = this.lineData[i]['type'];
              }
              // console.log(this.columns2)
              // console.log(this.lineFields)
              //给表单赋值
              this.dataSource2 = []
              for(let i=0;i<this.lineFields.length;i++){
                // console.log(this.lineFields[i])
                this.dataSource2.push({})
                this.dataSource2[i]['username'] = this.lineFields[i];
                for(let j=0;j<this.lineData.length;j++){
                  if(this.lineData[j][this.lineFields[i]] != null){
                    this.dataSource2[i][this.lineData[j]['type']] = parseInt(this.lineData[j][this.lineFields[i]])
                  }
                }
              }
            }
          })
        }else{

        }

        // }

      },
      //月份调整
      handlePanelChange2(value, mode) {
        this.valueMonth = value;
        this.modeMonth = [mode[0] === 'date' ? 'month' : mode[0], mode[1] === 'date' ? 'month' : mode[1]];
      },
      handleChangeMonth(value) {
        this.valueMonth = value;
        // console.log(this.valueMonth)
      },

    }
  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less'*/
</style>


