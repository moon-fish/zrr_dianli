<template>

  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <div style="text-align: center;font-weight: bold;font-size: large;margin-bottom: 20px;">客户负荷监测</div>
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
          <a-col :md="6" :sm="24"></a-col>
          <a-col :md="2" :sm="24">
            <a-form-item>
              <a-button type="primary" @click="xhQuery">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col  :md="24" :sm="24" >
            <div style="float: right;">(kW)</div>
            <line-chart-multid :title="title" :height="height" :fields="visitFields" :dataSource="visitInfo" style="padding-top: 50px;"></line-chart-multid>
          </a-col>
        </a-row>

      </a-form>
    </div>
    <!--<div><a-button type="primary" style="float: right">导出</a-button></div>-->
    <!---->
    <!--<line-chart-multid :fields="visitFields" :dataSource="visitInfo" title="" style="padding-top: 50px;" :height="height"></line-chart-multid>-->
    <!--&lt;!&ndash;列表区域&ndash;&gt;-->

  </a-card>
</template>

<script>
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import ARow from 'ant-design-vue/es/grid/Row'
  import { getAction } from '@/api/manage'
  import ACol from 'ant-design-vue/es/grid/Col'
  import store from '@/store/'

  export default {
    name: 'ChartLoadInfo',
    components: {
      ACol,
      ARow,
      LineChartMultid,
    },
    data() {
      return {
        visitFields: ['今天', '昨天','预测'],
        visitInfo: [],
        height: 350,
        areaType:'太原',
        companyType:'',
        companys:[],
        title:'负荷',
        /*分公司相关*/
        ziCom:[],
        ziComType:'',
        ziShow:false,
      }

    },
    created(){
      this.initArea();
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
        this.companys = [];
        this.ziCom = [];
        // this.companyType = '';
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
            // this.companyType = this.companys[0];
            this.xhQuery();
          }
        })
      },
      //选择区域，公司
      areaChange(e){
        this.areaType = e;
        console.log(this.areaType)
      },
      companyChange(e){
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
        var totalTime = 24*3600*1000;
        var spanTime = totalTime / 96;
        var startTime = new Date();
        startTime.setHours(0, 0, 0, 0);
        var currentTime = startTime.getTime();
        for(var i = 0; i < 96; i++) {
          currentTime += spanTime;
          let d = new Date(currentTime)
          let d1 = d.getHours()+':'+d.getMinutes()
          this.visitInfo.push({})
          this.visitInfo[i]['type'] = d1
          this.visitInfo[i][this.visitFields[0]] = Math.round(Math.random()*10)+30
          this.visitInfo[i][this.visitFields[1]] = Math.round(Math.random()*10)+20
          this.visitInfo[i][this.visitFields[2]] = Math.round(Math.random()*10)+25
        }
      },


      callback(key) {
        console.log(key);
      },

    },
  }
</script>

<style scoped>

</style>