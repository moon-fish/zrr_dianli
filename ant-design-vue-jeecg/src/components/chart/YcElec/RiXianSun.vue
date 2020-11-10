<template>
  <div>
    <v-chart :forceFit="true" :height="height" :data="data" :background="bg" theme="dark" onClick="aaa(a)">
      <v-coord type="rect" direction="LB" />
      <v-tooltip :shared="true"/>
      <v-axis dataKey="country" :label="label" />
      <v-axis dataKey="线损" :label="label" :grid="axisGrid" show="true"/>
      <v-interval position="country*线损" :opacity="1" :label="labelInterval" :color="color"/>
    </v-chart>
  </div>
</template>

<script>
  const DataSet = require('@antv/data-set');

  const sourceData = [
    { country: '中国', 线损: 131 },
    { country: '印度', 线损: 104 },
    { country: '美国', 线损: 2903 },
    { country: '印尼', 线损: 2348 },
    { country: '巴西', 线损: 1820 },
    { country: '中国1', 线损: 13174 },
    { country: '印度1', 线损: 1049 },
    { country: '美国1', 线损: 290 },
    { country: '印尼1', 线损: 23489 },
    { country: '巴西1', 线损: 18203 },
    { country: '中国2', 线损: 1317 },
    { country: '印度2', 线损: 10497 },
  ];

  const dv = new DataSet.View().source(sourceData);
  dv.transform({
    type: 'sort',
    callback(a, b) {
      return a.线损 - b.线损 > 0;
    },
  });
  const data = dv.rows;

  // const label = { offset: 12 };
  const label = {
    offset: 12,
    textStyle: {
      fill: '#fff'
    }
  }
  const bg={
    fill: 'red', //整个chart的背景色
    lineWidth: 10,
  }



  export default {
    data() {
      return {
        data,
        height: 600,
        label,
        axisGrid: null,
        labelInterval:
          ['线损',{
            offset: 0,
            textStyle: {
              fill: '#fff'
            }
          }
          // '线损',function (name) {
          //   if(name>10000){
          //     let aaa = {
          //       offset: -4,
          //       textStyle: {
          //         fill: '#fff'
          //       }
          //     }
          //     return aaa
          //   }else{
          //     let aaa = {
          //       offset: 4,
          //       textStyle: {
          //         fill: '#fff'
          //       }
          //     }
          //     return aaa
          //   }
          // }
        ],
        bg,
        color:['country',function(c){
          if(c === '中国1'){
            return 'red'
          }else{
            return 'green'
          }
        }],
      };
    },
    methods:{
      aaa(a){
        console.log("wwwwwwwwwwwwwww")
        a = "wwwwwwwwwwwwwww"
        return a
      }

    }
  };
</script>