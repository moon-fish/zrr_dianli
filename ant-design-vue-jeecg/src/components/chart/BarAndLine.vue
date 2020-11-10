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
        data-key="合同均价"
        :grid="axisGrid"
        :label="axisLabel"
      />
      <v-bar position="type*购电量" color="#3182bd" />
      <v-smooth-line position="type*合同均价" color="#fdae6b" :size="gemoSize"/>
      <v-point shape="circle" position="type*合同均价" color="#fdae6b" :size="gemoSize" />
      <!--<v-bar position="type*偏差电量"/>-->
      <!--<v-line position="type*申报电量" color="red" :size="1"/>-->
      <!--<v-line position="type*使用电量" color="#2fc25b" :size="1"/>-->

    </v-chart>
  <!--</div>-->
</template>

<script>
  import { ChartEventMixins } from './mixins/ChartMixins'

  const scale = [{
    dataKey: 'call',
    min: 0
  }, {
    dataKey: '购电量',
    min: 0
  }, {
    dataKey: '合同均价',
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
        default: () => []
      },
      height: {
        type: Number,
        default: 254
      }
    },
    data() {
      return {
        padding: { top:50, right:50, bottom:100, left:50 },
        scale,
        legendCustom: true,
        legendAllowAllCanceled: true,
        legendItems: [
          {value: '购电量', marker: {symbol: 'square', fill: '#3182bd', radius: 5} },
          {value: '合同均价', marker: {symbol: 'hyphen', stroke: '#fdae6b', radius: 5, lineWidth: 3} }
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