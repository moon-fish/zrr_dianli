<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <!--合同总电量-->
      <a-col :sm="24" :md="12" :xl="4" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="合同总电量" :total="this.contractElec.toFixed(2)">
          <a-tooltip title="指标说明" slot="action">mWh</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom">
              <template slot="title">
                {{ this.contractElecMon.toFixed(2) | NumberFormat }}
              </template>
              月合同总电量
              <span>
                {{ this.contractElecMon.toFixed(2) | NumberFormat }}
              </span>
            </a-tooltip>
          </template>
        </chart-card>
      </a-col>
      <!--合同均价-->
      <a-col :sm="24" :md="12" :xl="4" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="合同均价" :total="this.contractAverage.toFixed(2)">
          <!--<a-tooltip title="指标说明" slot="action">-->
            <!--<a-icon type="info-circle-o" />-->
          <!--</a-tooltip>-->
          <!--<div>-->
            <!--<trend flag="up" style="margin-right: 16px;">-->
              <!--<span slot="term">周同比</span>-->
              <!--12%-->
            <!--</trend>-->
            <!--<trend flag="down">-->
              <!--<span slot="term">日同比</span>-->
              <!--11%-->
            <!--</trend>-->
          <!--</div>-->
          <a-tooltip title="指标说明" slot="action">元</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom">
              <template slot="title">
                {{ this.contractAverageMon.toFixed(2) }}
              </template>
              月合同均价
              <span>
                {{ this.contractAverageMon.toFixed(2) }}
              </span>
            </a-tooltip>
          </template>
        </chart-card>
      </a-col>
      <!--购电量-->
      <!--<a-col :sm="24" :md="12" :xl="4" :style="{ marginBottom: '24px' }">-->
        <!--<chart-card :loading="loading" title="累计购电量" :total="5842 | NumberFormat" >-->

          <!--<template slot="footer">-->
            <!--&lt;!&ndash;<trend style="margin-right: 16px;">&ndash;&gt;-->
            <!--<span slot="term">月累计购电量</span>-->
            <!--523-->
          <!--</template>-->
        <!--</chart-card>-->
      <!--</a-col>-->
      <!--售电量-->
      <a-col :sm="24" :md="12" :xl="4" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="累计成交量" :total="this.jsElec.toFixed(2) | NumberFormat">
          <a-tooltip title="成交量单位" slot="action">mWh</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom">
              <template slot="title">
                {{ this.jsElecMon.toFixed(2) }}
              </template>
              月累计售电量
              <span>
                {{ this.jsElecMon.toFixed(2) }}
              </span>
            </a-tooltip>
          </template>
        </chart-card>
      </a-col>
      <!--剩余电量-->
      <a-col :sm="24" :md="12" :xl="4" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="剩余电量" :total="this.leftElec.toFixed(2) | NumberFormat" >
          <a-tooltip title="指标说明" slot="action">mWh</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom">
              <template slot="title">
                {{ this.leftElecMon.toFixed(2) }}
              </template>
              月剩余电量
              <span>
                {{ this.leftElecMon.toFixed(2) }}
              </span>
            </a-tooltip>
          </template>
        </chart-card>
      </a-col>
      <!--用户总数-->
      <a-col :sm="24" :md="12" :xl="3" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="用户总数" :total="this.userNum | NumberFormat">
          <a-tooltip title="用户单位" slot="action">个</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom">
              <template slot="title">
                {{ this.userNumMon.toString() | NumberFormat }}
              </template>
              月新增用户数
              <span>
                {{ this.userNumMon.toString() | NumberFormat }}
              </span>
            </a-tooltip>

          </template>
        </chart-card>
      </a-col>
      <!--收益-->
      <a-col :sm="24" :md="12" :xl="5" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="公司收益" :total="this.shouyi.toFixed(2) | NumberFormat">
          <a-tooltip title="指标说明" slot="action">元</a-tooltip>
          <template slot="footer">
            <a-tooltip placement="bottom" >
              <template slot="title">
                {{ this.shouyiMon.toFixed(2) | NumberFormat }}
              </template>
              月收益
              <span>
                {{ this.shouyiMon.toFixed(2) | NumberFormat }}
              </span>
            </a-tooltip>
          </template>
        </chart-card>
      </a-col>

    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">

            <a-row>
              <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
                <a-tabs type="card" @change="callback" style="margin-left: 20px;">
                  <a-tab-pane key="1" tab="本年月度合同量">
                    <div style="text-align: center;font-weight: bold;font-size: medium;margin-top: 20px;">购电量及合同均价</div>
                    <!--<bar-multid title="" :dataSource="barDataMonth" :height="height" :fields="barFieldsMonth"/>-->
                    <bar-and-line :dataSource="DataMonth" :height="height"></bar-and-line>
                  </a-tab-pane>
                  <a-tab-pane key="2" tab="历年合同量">
                    <div style="text-align: center;font-weight: bold;font-size: medium;margin-top: 20px;">购电量及合同均价</div>
                    <!--<bar-multid title="" :dataSource="barDataYear" :height="height" :fields="barFieldsYear"/>-->
                    <bar-and-line :dataSource="DataYear" :height="height"></bar-and-line>
                  </a-tab-pane>

                </a-tabs>

              </a-col>
              <a-col :xl="3" :lg="12" :md="12" :sm="24" :xs="24">
                <!--<img src="./sandian02.jpg">-->
                <!--热力图-->
              </a-col>
              <a-col :xl="6" :lg="12" :md="12" :sm="24" :xs="24">
                <!--<img src="./sandian02.jpg">-->
                <!--热力图-->
                <head-map style="margin-top: 40px;"></head-map>
              </a-col>
            </a-row>

      </div>
    </a-card>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import HeadMap from '@/components/chart/HeadMap'
  import BarMultid from '@/components/chart/BarMultid'
  import BarAndLine from '@/components/chart/BarAndLine'
  import { mapGetters } from 'vuex'
  import { postAction,getAction } from '@/api/manage'
  import { doReleaseData } from '@/api/api'
  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

    const scale = [{
      dataKey: 'latitude',
      sync: true,
      nice: false,
    }, {
      dataKey: 'longitude',
      sync: true,
      nice: false,
    }];

  export default {
    name: "IndexChart",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo,
      HeadMap,
      BarMultid,
      BarAndLine
    },
    data() {
      return {
        loading: true,
        center: null,
        // barFieldsMonth: ['1月','2月','3月','4月','5月','6月'],
        DataMonth: [],
        DataYear: [],
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        height: 400,
        data: [],
        padding:[0, 20, 0],
        label:['name', {offset: 0}],
        style:{
          blur: 23,
        },
        geoData: {},
        scale,
        color:['value', '#F51D27-#FA541C-#FF8C12-#FFC838-#FAFFA8-#80FF73-#12CCCC-#1890FF-#6E32C2'],
        // indicator: <a-icon type="loading" style="font-size: 24px" spin />
        contractElec: 0,
        contractElecMon: 0,
        userNum: 0,
        userNumMon: 0,
        contractAverage: 0,
        contractAverageMon: 0,
        data1:[],
        shouyi: 0,
        shouyiMon: 0,
        jsElec: 0,
        jsElecMon: 0,
        leftElec: 0,
        leftElecMon: 0,
        tab: '1',
      }
    },
    created() {
      //提示合同即将到期的用户
      this.systemMessage();
      //加载合同总电量等数据
      this.totalData();

      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
    },
    methods: {
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             // console.log("aaaaaa",res.result)
             this.visitInfo = res.result;
           }
         })
      },
      callback(key) {
        // console.log(key);
        this.tab = key
        this.totalData()
      },
      ...mapGetters(["nickname", "avatar","userInfo"]),
      //发送系统消息
      systemMessage(){
        // console.log(this.userInfo())
        let effectives = [];
        getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
          if(res.success){
            // console.log(res.result)
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].effective === '1'){
                effectives.push(res.result[i])
                // console.log(effectives)
              }
            }
            if(effectives.length > 0){
              let sendData = {
                titile:"系统提示",
                startTime:'2020-5-12 17:54:55',
                endTime:'2020-6-20 17:54:55',
                priority:'M',
                msgCategory:'2',
                msgType:'USER',
                userIds:'',
              }
              for(let i=0;i<effectives.length;i++){
                if(sendData.msgContent === undefined){
                  sendData.msgContent = ''
                }
                sendData.msgContent += "<p>用户 "+effectives[i].comAName+" 合同有效期即将到期,请注意更改<br></p>"
              }
              let id = this.userInfo().id;
              sendData.userIds = id+',';
              postAction('/sys/annountCement/add',sendData).then((res) => {
                if(res.success){
                  //获取id并发送数据
                  getAction("/sys/annountCement/list",sendData).then((res) => {
                    // console.log(res)
                    if(res.success){
                      let id = res.result.records[0].id;
                      // console.log(id)
                      doReleaseData({id: id}).then((res1) => {
                        if (res1.success) {
                          // console.log("ok")
                        } else {
                          // console.log("false")
                        }
                      });
                    }
                  })
                }
              })
            }

          }
        })



      },
      //加载数据
      totalData(){
        let contractAve = 0;
        let contractAveMon = 0;
        let conAveMon = 0;
        this.contractAverage = 0;
        this.contractAverageMon = 0;
        this.contractElec = 0;
        this.contractElecMon = 0;
        this.shouyi = 0;
        this.shouyiMon = 0;
        this.jsElec = 0;
        this.jsElecMon = 0;
        this.leftElec = 0;
        this.leftElecMon = 0;
        let date = new Date();
        let y = date.getFullYear()
        let mon = date.getMonth()+1
        //购电合同
        getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year:y }).then((res) => {
          if(res.success){
            // console.log(res)
            for(let i=0;i<res.result.length;i++){
              // 年合同总电量  (合同总电量+交易电量)
              this.contractElec += parseInt(res.result[i].purchaseElectricity)
              // 月合同总电量
              this.contractElecMon = this.contractElec/12
              // 合同均价
              contractAve += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice
              //月合同均价
              contractAveMon += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice/12
              conAveMon += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice/12
              // console.log(contractAve)
            }
            // 我的交易 修改月合同均价NAN，即购电合同还没执行完就执行我的交易
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
                  // 月电量
                  let date = new Date();
                  let month = date.getMonth()+1;
                  if(parseInt(res.result[i].tradeBegin.split('-')[1]) === month){
                    if(res.result[i].zrType === '2'){
                      this.contractElecMon -= parseInt(res.result[i].tradeElec)
                      contractAveMon -= res.result[i].tradeElec*res.result[i].tradePrice
                    }else{
                      this.contractElecMon += parseInt(res.result[i].tradeElec)
                      contractAveMon += res.result[i].tradeElec*res.result[i].tradePrice
                    }
                  }
                }
                // console.log(contractAveMon)
                // console.log(this.contractElecMon)
                this.contractAverage = contractAve/this.contractElec
                this.contractAverageMon = contractAveMon/this.contractElecMon
                // console.log(this.contractAverageMon)
                //图表数据
                this.DataMonth = [];
                this.DataYear = [];
                // console.log(this.tab)
                if(this.tab === '1'){
                  //遍历月份（截止到当前月），添加横坐标，并添加中长期合同的平均值
                  let elecM = [];
                  for(let j=0;j<mon;j++){
                    this.DataMonth.push({});
                    this.DataMonth[j]['type'] = j+1+'月';
                    this.DataMonth[j]['购电量'] = this.contractElec/12
                    this.DataMonth[j]['合同均价'] = conAveMon //this.contractElecMon
                    //合同均价计算逻辑：合同均价计算方法：A X B+ C X D / A+C (ac为电量，BD为单价) ；逻辑：分别结算出A X B+ C X D 和A+C的值，再计算除法得出数据
                    //计算A X B+ C X D
                    for(let i=0;i<res.result.length;i++){
                      if(j+1 === parseInt(res.result[i].tradeBegin.split('-')[1]) ){
                        if(elecM[j] === undefined){
                          elecM[j] = 0
                        }
                        elecM[j] += parseInt(res.result[i].tradeElec)
                        if(this.DataMonth[j]['购电量'] === undefined){
                          this.DataMonth[j]['购电量'] = 0
                        }
                        if(this.DataMonth[j]['合同均价'] === undefined){
                          this.DataMonth[j]['合同均价'] = 0
                        }
                        // console.log(res.result[i].tradeElec)

                        if(res.result[i].zrType === '2'){
                          this.DataMonth[j]['购电量'] -= parseInt(res.result[i].tradeElec)
                          this.DataMonth[j]['合同均价'] -= res.result[i].tradeElec*res.result[i].tradePrice//this.contractElecMon
                        }else{
                          this.DataMonth[j]['购电量'] += parseInt(res.result[i].tradeElec)
                          this.DataMonth[j]['合同均价'] += res.result[i].tradeElec*res.result[i].tradePrice//this.contractElecMon
                        }
                      }
                    }
                  }
                  //除法 （购买电量1*购电单价1+购买电量2*购电单价2+…… )/总购买电量
                  for(let i=0;i<this.DataMonth.length;i++){
                    // console.log(elecM[i])
                    if(elecM[i] === undefined){
                      elecM[i] = 0
                    }
                    this.DataMonth[i]['合同均价'] = this.DataMonth[i]['合同均价']/(elecM[i]+this.contractElecMon)
                  }
                }else if(this.tab === '2'){

                  for(let j=0;j<3;j++){
                    this.DataYear.push({});
                    this.DataYear[j]['type'] = y+j-2+'年';
                    for(let i=0;i<res.result.length;i++){
                      if(y+j-2 === parseInt(res.result[i].tradeBegin.split('-')[0]) ){
                        if(this.DataYear[j]['购电量'] === undefined){
                          this.DataYear[j]['购电量'] = 0
                        }
                        if(this.DataYear[j]['合同均价'] === undefined){
                          this.DataYear[j]['合同均价'] = 0
                        }
                        // console.log(res.result[i].tradeElec)
                        if(res.result[i].zrType === '2'){
                          this.DataYear[j]['购电量'] -= parseInt(res.result[i].tradeElec)
                          this.DataYear[j]['合同均价'] -= res.result[i].tradeElec*res.result[i].tradePrice/this.contractElec
                        }else{
                          this.DataYear[j]['购电量'] += parseInt(res.result[i].tradeElec)
                          this.DataYear[j]['合同均价'] += res.result[i].tradeElec*res.result[i].tradePrice/this.contractElec
                        }
                      }
                    }
                  }
                }
                if(this.DataMonth.length>0){
                  for(let i=0;i<this.DataMonth.length;i++){
                    this.DataMonth[i]['合同均价'] = parseFloat(this.DataMonth[i]['合同均价'].toFixed(2))
                    this.DataMonth[i]['购电量'] = parseFloat(this.DataMonth[i]['购电量'].toFixed(2))
                  }
                }
                console.log(this.DataYear)
                if(this.DataYear.length>0){
                  for(let i=0;i<this.DataYear.length;i++){
                    if(this.DataYear[i]['合同均价'] === undefined){
                      this.DataYear[i]['合同均价'] = 0
                    }
                    if(this.DataYear[i]['购电量'] === undefined){
                      this.DataYear[i]['购电量'] = 0
                    }
                    this.DataYear[i]['合同均价'] = parseFloat(this.DataYear[i]['合同均价'].toFixed(2))
                    this.DataYear[i]['购电量'] = parseFloat(this.DataYear[i]['购电量'].toFixed(2))
                  }
                }

              }
            })
          }
        })

        // 用户总数
        getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
          if(res.success){
            //用户总数
            this.userNum = res.result.length
            //月新增用户数
            let date = new Date();
            let month = date.getMonth()+1;
            for(let i=0;i<res.result.length;i++){
              if(parseInt(res.result[i].createTime.split('-')[1]) === month){
                this.userNumMon += 1;
              }
            }

          }
        })
        //收益 (通过申报电量与结算电量对比判断为正负偏差，再计算收益)
        let filer = 0;
        let jiesuan = 0;
        let jiesuanMon = 0;
        let usernames = [];
        let companys = [];
        let arr = [];
        //申报电量 确保每个月份数据不为null
        getAction('/monthelec/momthElec/queryall',{}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              // console.log(usernames.indexOf(res.result.records[i]['username']));
              //对返回的值去重，提取父节点
              if(usernames.indexOf(res.result[i]['username']) === -1){
                usernames.push(res.result[i]['username'])
                arr.push(res.result[i])
              }
            }
            // console.log(arr)
            let date = new Date();
            let month = date.getMonth()+1;
            let mon1 = date.getMonth()+1
            if(mon1<=9){
              mon1 = '0'+mon1
            }
            for(let i=0;i<arr.length;i++){
              if(month === 1){
                filer += parseInt(arr[i].yi)
              }
              if(month === 2){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)
              }
              if(month === 3){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)
              }
              if(month === 4){
                filer += parseFloat(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)
              }
              if(month === 5){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseInt(arr[i].wu)
              }
              if(month === 6){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseInt(arr[i].wu)+parseFloat(arr[i].liu)
              }
              if(month === 7){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseInt(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)
              }
              if(month === 8){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseFloat(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)+parseFloat(arr[i].ba)
              }
              if(month === 9){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseFloat(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)+parseFloat(arr[i].ba)+parseFloat(arr[i].jiu)
              }
              if(month === 10){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseFloat(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)+parseFloat(arr[i].ba)+parseFloat(arr[i].jiu)+parseFloat(arr[i].shi)
              }
              if(month === 11){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseFloat(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)+parseFloat(arr[i].ba)+parseFloat(arr[i].jiu)+parseFloat(arr[i].shi)+parseFloat(arr[i].shiyi)
              }
              if(month === 12){
                filer += parseInt(arr[i].yi)+parseInt(arr[i].er)+parseInt(arr[i].san)+parseInt(arr[i].si)+parseFloat(arr[i].wu)+parseFloat(arr[i].liu)+parseFloat(arr[i].qi)+parseFloat(arr[i].ba)+parseFloat(arr[i].jiu)+parseFloat(arr[i].shi)+parseFloat(arr[i].shiyi)+parseFloat(arr[i].shier)
              }

            }
            // console.log(filer)
            //结算电量 计算1月1号至昨天的使用电量
            let dateBegin = date.getFullYear()+'-01-01';
            let dateEnd = date.getFullYear()+"-"+month+"-"+date.getDate();
            // console.log(dateEnd)
            getAction('/hiselec/hiselec/queryall',{ dateBegin:dateBegin,dateEnd:dateEnd }).then((res) => {
              if(res.success){
                this.data1 = res.result
                //查询公司
                for(let i=0;i<res.result.length;i++){
                  if(companys.indexOf(res.result[i]['username']) === -1){
                    companys.push(res.result[i]['username'])
                  }
                }
                //查询所有电量
                for(let j=0;j<this.data1.length;j++){
                  let eu = this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
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
                  jiesuan += eu
                  if(this.data1[j].elecDate.substring(0,7) === y+'-'+mon1){
                    jiesuanMon += eu
                  }
                }
                let js = parseFloat(jiesuan.toFixed(3))
                // console.log(js)
                // console.log(js-filer)
                // console.log(companys)
                //查询各个公司的电费
                let d = [];
                for(let i=0;i<companys.length;i++){
                  d.push({})
                  // console.log(d[i][companys[i]])
                  if(d[i][companys[i]] === undefined){
                    d[i][companys[i]] = 0;
                  }
                  for(let j=0;j<this.data1.length;j++){
                    if(companys[i] === this.data1[j].username){
                      d[i][companys[i]] += this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
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
                // console.log(d)
                //查询用户购电合同，查询用户购电价
                let total = 0;
                let totalMon = 0;
                getAction('/userpurchasecontractb/userPurchaseContractB/queryall',{}).then((res) => {
                  if(res.success){
                    for(let i=0;i<companys.length;i++){
                      for(let j=0;j<res.result.length;j++){
                        if(companys[i] === res.result[j].userName){
                          total += d[i][companys[i]] * res.result[j].purchasePrice
                          if(res.result[j].beginDate.substring(0,7) === y+'-'+mon1){
                            totalMon += d[i][companys[i]] * res.result[j].purchasePrice
                          }
                        }
                      }
                    }
                    // console.log(total)
                    //大于0为正偏差，小于0位负偏差
                    if(js-filer > 0){
                      this.shouyi = total - jiesuan * this.contractAverage - (js-filer)* 332
                      this.shouyiMon = totalMon - jiesuanMon * this.contractAverage - (js-filer)* 332
                    }else{
                      this.shouyi = total - jiesuan * this.contractAverage
                      this.shouyiMon = totalMon - jiesuanMon * this.contractAverage
                    }
                  }
                })

              }
            })
          }
        })
        //累计成交量（结算电量）
        getAction('/hiselec/hiselec/queryall',{ elecBegin:y+"-01-01 00:00:00",elecEnd:y+1+"-01-01 00:00:00" }).then((res) => {
          if(res.success){
            // console.log(res.result)
            for(let i=0;i<res.result.length;i++){
              let elecAll = res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                              res.result[i].ydll+res.result[i].ydyw+res.result[i].ydss+res.result[i].ydsw+
                              res.result[i].edll+res.result[i].edyw+res.result[i].edss+res.result[i].edsw+
                              res.result[i].sdll+res.result[i].sdyw+res.result[i].sdss+res.result[i].sssw+
                              res.result[i].sidll+res.result[i].sidyw+res.result[i].sidss+res.result[i].sidsw+
                              res.result[i].wdll+res.result[i].wdyw+res.result[i].wdss+res.result[i].wdsw+
                              res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                              res.result[i].qdll+res.result[i].qdyw+res.result[i].qdss+res.result[i].qdsw+
                              res.result[i].bdll+res.result[i].bdyw+res.result[i].bdss+res.result[i].bdsw+
                              res.result[i].jdll+res.result[i].jdyw+res.result[i].jdss+res.result[i].jdsw+
                              res.result[i].shidll+res.result[i].shidyw+res.result[i].shidss+res.result[i].shidsw+
                              res.result[i].sydll+res.result[i].sydyw+res.result[i].sydss+res.result[i].sydsw+
                              res.result[i].sedll+res.result[i].sedyw+res.result[i].sedss+res.result[i].sedsw+
                              res.result[i].ssdll+res.result[i].ssdyw+res.result[i].ssdss+res.result[i].ssdsw+
                              res.result[i].ssidll+res.result[i].ssidyw+res.result[i].ssidss+res.result[i].ssidsw+
                              res.result[i].swdll+res.result[i].swdyw+res.result[i].swdss+res.result[i].swdsw+
                              res.result[i].sldll+res.result[i].sldyw+res.result[i].sldss+res.result[i].sldsw+
                              res.result[i].sqdll+res.result[i].sqdyw+res.result[i].sqdss+res.result[i].sqdsw+
                              res.result[i].sbdll+res.result[i].sbdyw+res.result[i].sbdss+res.result[i].sbdsw+
                              res.result[i].sjdll+res.result[i].sjdyw+res.result[i].sjdss+res.result[i].sjdsw+
                              res.result[i].esdll+res.result[i].esdyw+res.result[i].esdss+res.result[i].esdsw+
                              res.result[i].eydll+res.result[i].eydyw+res.result[i].eydss+res.result[i].eydsw+
                              res.result[i].eedll+res.result[i].eedyw+res.result[i].eedss+res.result[i].eedsw+
                              res.result[i].essdll+res.result[i].essdyw+res.result[i].essdss+res.result[i].essdsw
              //总用电量
              this.jsElec += elecAll
              //月用电量
              if(parseInt(res.result[i].elecDate.split("-")[1]) === mon){
                this.jsElecMon += elecAll
              }
            }
            //总剩余电量
            this.leftElec = this.contractElec - this.jsElec
            //月剩余电量
            this.leftElecMon = this.contractElecMon - this.jsElecMon
          }
        })

      },
    }
  }
</script>

<style lang="less" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>