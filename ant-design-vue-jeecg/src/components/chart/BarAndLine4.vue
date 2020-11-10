<template>
  <!--<div :style="{ padding: '0 0 32px 32px' }">-->
  <!--<h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>-->
  <v-chart :forceFit="true" :height="height" :data="data" :scale="scale">
    <v-tooltip/>
    <v-legend
      :custom="legendCustom"
      :allow-all-canceled="legendAllowAllCanceled"
      :items="legendItems"
      :on-click="legendOnClick"
    />
    <v-axis
      data-key="去年电量"
      :grid="axisGrid"
      :label="axisLabel"
    />

    <!--<v-bar position="type*偏差电量"/>-->
    <!--<v-line position="type*申报电量" color="red" :size="1"/>-->
    <v-bar position="type*使用电量" color="#3182bd" />

    <v-line position="type*去年电量" color="#fdae6b" :size="gemoSize" />
    <v-point shape="circle" position="type*去年电量" color="#fdae6b" :size="gemoSize" />

  </v-chart>
  <!--</div>-->
</template>

<script>
  import { ChartEventMixins } from './mixins/ChartMixins'

  const scale = [{
    dataKey: 'call',
    min: 0
  }, {
    dataKey: '使用电量',
    min: 0
  }, {
    dataKey: '去年电量',
    min: 0
  }];
  export default {
    name: 'BarAndLine',
    mixins: [ChartEventMixins],
    props: {
      title: {
        type: String,
        default: ''
      },
      dataSource: {
        type: Array,
        default: () => [
          { type: '10:10', 偏差电量: 200, 申报电量: 1000,使用电量: 400, },
          { type: '10:15', 偏差电量: 600, 申报电量: 1000,使用电量: 500 },
          { type: '10:20', 偏差电量: 200, 申报电量: 1000,使用电量: 500 },
          { type: '10:25', 偏差电量: 900, 申报电量: 1000,使用电量: 600 },
          { type: '10:30', 偏差电量: 200, 申报电量: 1000,使用电量: 523 },
          { type: '10:35', 偏差电量: 200, 申报电量: 1000,使用电量: 550 },
          { type: '10:40', 偏差电量: 100, 申报电量: 1000,使用电量: 590 }
        ]
      },
      height: {
        type: Number,
        default: 400
      },

    },
    data() {
      return {
        padding: { top:50, right:50, bottom:100, left:50 },
        scale,
        legendCustom: true,
        legendAllowAllCanceled: true,
        legendItems: [
          {value: '使用电量', marker: {symbol: 'square', fill: '#3182bd', radius: 5} },
          {value: '去年电量', marker: {symbol: 'hyphen', stroke: '#fdae6b', radius: 5, lineWidth: 3} }
        ],
        legendOnClick: (ev, chart) => {
          const item = ev.item;
          const value = item.value;
          const checked = ev.checked;
          const geoms = chart.getAllGeoms();
          for (let i = 0; i < geoms.length; i++) {
            const geom = geoms[i];
            if (geom.getYScale().field === value) {
              if (checked) {
                geom.show();
              } else {
                geom.hide();
              }
            }
          }
        },
        gemoSize: 3,
        axisLabel: {
          textStyle: {
            fill: '#fdae6b'
          }
        },
        axisGrid: null,
      }
    },
    computed: {
      data() {
        return this.dataSource
      }
    }
  }
</script>