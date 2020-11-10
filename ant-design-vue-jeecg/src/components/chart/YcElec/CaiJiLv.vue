<template>
  <div v-if="data.length">
    <v-chart
      :forceFit="true"
      height="150"
      :data="data"
      padding="auto"
    >
      <v-tooltip></v-tooltip>
      <v-facet
        type="rect"
        :fields="['type']"
        :padding="20"
        :showTitle="false"
        :eachView="eachView"
      />
    </v-chart>
  </div>
</template>

<script>
  const data = [{
    type: '变电站',
    value: 56.4
  }, {
    type: '共线',
    value: 36.2
  }, {
    type: '专线',
    value: 36.2
  }, {
    type: '公变',
    value: 36.2
  }, {
    type: '专变',
    value: 36.2
  }, {
    type: '配变到户',
    value: 36.2
  }];

  const eachView = function (view, facet) {
    var data = facet.data;
    var color = void 0;
    if (data[0].type === '男性') {
      color = '#0a9afe';
    }else{
      color = '#625fef';
    }
    data.push({
      type: '其他',
      value: 22.3
    });
    view.source(data);
    view.coord('theta', {
      radius: 0.9,
      innerRadius: 0.7
    });
    view.intervalStack().position('value').color('type', [color, '#eceef1']).opacity(1);
    view.guide().html({
      position: ['50%', '50%'],
      html: '<div class="g2-guide-html"><p class="title">' + data[0].type + '</p><p class="value">' + (data[0].value + '%') + '</p></div>'
    });
  }
  export default {
    mounted(){
      this.setStyle();
    },
    methods:{
      setStyle(){
        const id = 'legend-html';
        if (document.getElementById(id)) {
          return;
        }
        const styleTxt = `
        .g2-guide-html {
            width: 50px;
            height: 50px;
            vertical-align: middle;
            text-align: center;
            line-height: 1.5
        }

        .g2-guide-html .title {
            font-size: 12px;
            color: #8c8c8c;
            font-weight: 400;
        }

        .g2-guide-html .value {
            font-size: 18px;
            color: #000;
            font-weight: bold;
        }
      `;
        const style = document.createElement('style');
        style.setAttribute('id', id);
        style.innerHTML = styleTxt;
        document.getElementsByTagName('head')[0].appendChild(style);
      }
    },
    data() {
      return {
        data: data,
        eachView
      }
    }
  };
</script>

