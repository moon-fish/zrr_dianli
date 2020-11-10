<template>
  <div>
    <v-chart :forceFit="true" :height="height" :data="data">
      <v-tooltip />
      <v-axis />
      <v-legend />
      <v-stack-bar position="月份*月均降雨量" color="name" />
    </v-chart>
  </div>
</template>

<script>
  const DataSet = require('@antv/data-set');

  const sourceData = [
    // { name: '尖时段', '2020-01': 28.9, '2020-02': 23.9, '2020-03': 5.3, '2020-04': 81.4, '2020-05': 52, '2020-06': 25.3, '2020-07': 53, '2020-08': 35.6, '2020-09': 25.3, '2020-10': 35.6, '2020-11': 53.1, '2020-12': 63.2 },
    { name: '峰时段', '2018': 18.9, '2019': 15.6, '2020': 45.2 },
    { name: '平时段', '2018': 18.9, '2019': 5.3, '2020': 56.3, },
    { name: '谷时段', '2018': 8.9, '2019': 53.6, '2020': 23.6,  },
  ];

  const dv = new DataSet.View().source(sourceData);
  dv.transform({
    type: 'fold',
    fields: ['2018', '2019', '2020'],
    key: '月份',
    value: '月均降雨量',
  });
  const data = dv.rows;
  export default {
    name: 'StackBar2',
    data() {
      return {
        data,
        height: 400,
      };
    }
  }
</script>

<style scoped>

</style>