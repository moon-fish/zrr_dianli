<template>
  <!--仪表盘变化图-->
  <div>
    <v-chart :forceFit="true" :height="height" :data="chartData" :scale="scale">
      <v-coord type="polar" :startAngle="-202.5" :endAngle="22.5" :radius="1"></v-coord>
      <v-axis
        dataKey="value"
        :zIndex="2"
        :line="null"
        :label="axisLabel"
        :subTickCount="4"
        :subTickLine="axisSubTickLine"
        :tickLine="axisTickLine"
        :grid="null"
      ></v-axis>
      <v-axis dataKey="1" :show="false"></v-axis>
      <v-series
        gemo="point"
        position="value*1"
        shape="pointer"
        color="#1890FF"
        :active="false"
      ></v-series>
      <v-guide
        type="arc"
        :zIndex="0"
        :top="false"
        :start="arcGuide1Start"
        :end="arcGuide1End"
        :vStyle="arcGuide1Style"
      ></v-guide>
      <v-guide
        type="arc"
        :zIndex="1"
        :start="arcGuide2Start"
        :end="getArcGuide2End"
        :vStyle="arcGuide2Style"
      ></v-guide>
      <v-guide
        type="html"
        :position="htmlGuidePosition"
        :html="getHtmlGuideHtml()"
      ></v-guide>
    </v-chart>
  </div>
</template>

<script>
  import { registerShape } from 'viser-vue';

  registerShape('point', 'pointer', {
    draw(cfg, container) {
      let point = cfg.points[0];
      point = this.parsePoint(point);
      const center = this.parsePoint({
        x: 0,
        y: 0,
      });
      /*指针参数*/
      container.addShape('line', {
        attrs: {
          x1: center.x,
          y1: center.y,
          x2: point.x,
          y2: point.y,
          stroke: cfg.color,
          lineWidth: 5,
          lineCap: 'round',
        }
      });
      /*转盘参数*/
      return container.addShape('circle', {
        attrs: {
          x: center.x,
          y: center.y,
          r: 5.75,
          stroke: cfg.color,
          lineWidth: 3.5,
          fill: '#fff',
        }
      });
    }
  });

  const scale = [{
    dataKey: 'value',
    min: 0,
    max: 9,
    tickInterval: 1,
    nice: false,
  }];

  const data = [
    { value: 7.0 },
  ];

  export default {
    name:"DashChartDemo",
    props:{
      datasource:{
        type: Number,
        default:7
      },
      title: {
        type: String,
        default: ''
      }
    },
    created(){
      if(!this.datasource){
        this.chartData = data;
      }else{
        this.chartData = [
          { value: this.datasource },
        ];
      }
      this.getChartData()
    },
    watch: {
      'datasource': function (val) {
        this.chartData = [
          { value: val},
        ];
        this.getChartData();
      }
    },
    methods:{
      getChartData(){
        if(this.chartData && this.chartData.length>0){
          this.abcd = this.chartData[0].value * 10
        }else{
          this.abcd = 70
        }
      },
      getHtmlGuideHtml(){
        return '<div style="width: 250px;text-align: center;">\n' +
          '<p style="font-size: 14px;color: #fff0b7;margin: 0;">'+this.title+'</p>\n' +
          '<p style="font-size: 18px;color: #fff0b7;margin: 0;">'+this.abcd+'%</p>\n' +
          '</div>'
      },
      getArcGuide2End(){
        return [this.chartData[0].value, 0.945]
      }
    },
    data() {
      return {
        chartData:[],
        height: 180,
        scale: scale,
        abcd:70,
        /*刻度-数字*/
        axisLabel: {
          offset: -8,
          textStyle: {
            fontSize: 10,
            textAlign: 'center',
            textBaseline: 'middle',
            fill:'white'

          }
        },
        axisSubTickLine: {
          length: -8,
          stroke: '#fff',
          strokeOpacity: 1,
        },
        /*刻度*/
        axisTickLine: {
          length: -13,
          stroke: '#fff',
          strokeOpacity: 1,
        },
        arcGuide1Start: [0, 0.945],
        arcGuide1End: [15, 0.945],
        arcGuide1Style: {
          stroke: '#CBCBCB',
          lineWidth: 10,
        },
        arcGuide2Start: [0, 0.945],
        arcGuide2Style: {
          stroke: '#1890FF',
          lineWidth: 12,
        },
        htmlGuidePosition: ['50%', '100%'],
        htmlGuideHtml: `
        <div style="width: 250px;text-align: center;">
          <p style="font-size: 14px;color: #fff0b7;margin: 0;">${this.title}</p>
          <p style="font-size: 18px;color: #fff0b7;margin: 0;">${this.abcd}%</p>
        </div>
      `,
      };
    },
  };
</script>
