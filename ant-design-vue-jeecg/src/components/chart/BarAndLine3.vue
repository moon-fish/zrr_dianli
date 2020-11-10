<template>
  <!--<div :style="{ padding: '0 0 32px 32px' }">-->
  <!--<h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>-->
  <v-chart :forceFit="true" :height="height" :data="data" :scale="scale" :padding="padding" :onClick="handleClick">
    <v-tooltip/>
    <v-legend
      :custom="legendCustom"
      :allow-all-canceled="legendAllowAllCanceled"
      :items="legendItems"
      :on-click="legendOnClick"
    />
    <v-axis
      data-key="申报电量"
      :grid="axisGrid"
      :label="axisLabel"
      :tick="axisTick"
    />
    <v-axis
      data-key="使用电量"
      :grid="axisGrid"
      :label="axisLabel"
      :tick="axisTick"
    />
    <v-axis
      data-key="偏差电量"
      :grid="axisGrid"
      :label="axisLabel"
      :tick="axisTick"
    />
    <v-axis
      data-key="偏差率"
      :grid="axisGridPer"
      :label="axisGridPer"
      :tick="axisTick"
    />


    <v-smooth-line position="type*申报电量" color="red" :size="gemoSize"/>
    <v-point shape="circle" position="type*申报电量" color="red" :size="gemoSize" />
    <!--<v-line position="type*总电量" color="black" :size="1"/>-->
    <v-smooth-line position="type*使用电量" color="#2fc25b" :size="gemoSize"/>
    <v-point shape="circle" position="type*使用电量" color="#2fc25b" :size="gemoSize" />
    <v-smooth-line position="type*偏差电量" color="orange" :size="gemoSize"/>
    <v-point shape="circle" position="type*偏差电量" color="orange" :size="gemoSize" />

    <v-bar position="type*偏差率(%)"/>

  </v-chart>
  <!--</div>-->
</template>

<script>
  import { ChartEventMixins } from './mixins/ChartMixins'

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
        default: 350
      }
    },
    data() {
      return {
        padding: { top:50, right:50, bottom:100, left:50 },
        scale: [
          {
            dataKey: '申报电量',
            min: 0,
          }, {
            dataKey: '使用电量',
            min: 0,
          }, {
            dataKey: '偏差率(%)',
            min: 0
          }, {
            dataKey: '偏差电量',
            min: 0
          }, {
            dataKey: 'type',
            tickCount: 12
          }],
        axisLabel: {
          textStyle: {
            fill: 'black'
          },
        },

        axisGrid: null,
        axisTick: {
          tickInterval: 3
        },
        axisLabelPer: {
          textStyle: {
            fill: '#2fc25b',
            left:30
          }
        },
        axisGridPer: null,
        gemoSize: 3,
        legendCustom: true,
        legendAllowAllCanceled: true,
        legendItems: [
          {value: '偏差率(%)', marker: {symbol: 'square', fill: '#3182bd', radius: 5} },
          {value: '申报电量', marker: {symbol: 'hyphen', stroke: 'red', radius: 5, lineWidth: 3} },
          {value: '使用电量', marker: {symbol: 'hyphen', stroke: '#2fc25b', radius: 5, lineWidth: 3} },
          {value: '偏差电量', marker: {symbol: 'hyphen', stroke: 'orange', radius: 5, lineWidth: 3} }
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
      }
    },
    computed: {
      data() {
        return this.dataSource
      }
    }
  }
</script>