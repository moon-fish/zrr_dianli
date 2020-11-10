<template>
  <div>
    <v-chart :forceFit="true" :height="400" :padding="padding" :scale="scale">
      <v-tooltip :showTitle="false" />
      <v-view :data="geoData" :scale="scale">
        <v-polygon position="longitude*latitude" color="gray" :label="label" />
      </v-view>
      <v-view :data="data">
        <v-heatmap
          :position="'longitude*latitude'" :size="18"
          :color="color"
          :vStyle="style"
          tooltip="name*value"
        />
      </v-view>
    </v-chart>
  </div>
</template>

<script>
  import * as $ from 'jquery';
  const DataSet = require('@antv/data-set');
  import shanxi from '../../assets/shan1xi.json'

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
    name: "HeadMap",
    components: {
      shanxi
    },
    mounted() {
      $.getJSON('https://gw.alipayobjects.com/os/antvdemo/assets/data/china.json').then((geoData) => {
        geoData = shanxi
        const userData = [];
        const geoDv = new DataSet.View().source(geoData, {
          type: 'GeoJSON'
        }).transform({
          type: 'map',
          callback(row) {
            userData.push({
              longitude: row.centroidX,
              latitude: row.centroidY,
              name: row.name,
              value: Math.floor(Math.random() * (100 - 1))
            });
            return row;
          }
        });

        this.$data.geoData = geoDv;
        this.$data.data = userData;
        // console.log(userData)
      });
    },
    data() {
      return {
        data: [],
        padding:[0, 20, 0],
        label:['name', {offset: 0}],
        style:{
          blur: 23,
        },
        geoData: {},
        scale,
        color:['value', '#F51D27-#FA541C-#FF8C12-#FFC838-#FAFFA8-#80FF73-#12CCCC-#1890FF-#6E32C2']
      };
    },
  };
</script>
