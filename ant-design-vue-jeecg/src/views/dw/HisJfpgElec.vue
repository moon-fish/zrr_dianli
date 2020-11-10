<template>
  <div class="table-page-search-wrapper">
    <a-form layout="inline">
      <a-row :gutter="24" style="background-color: #ececec;padding: 10px; margin: -10px">
        <a-layout id="components-layout-demo-custom-trigger">
          <a-layout-sider
            collapsible
            collapsedWidth="0"
            style="background-color: #fff"
          >
            <a-card :bordered="true" >
              <a-input-search style="margin-bottom: 8px" placeholder="Search" @change="onChange" />
              <a-tree
                checkable
                v-model="checkedKeys"
                @expand="onExpand"
                :expandedKeys="expandedKeys"
                :autoExpandParent="autoExpandParent"
                :treeData="treeData"
              >
                <template slot="title" slot-scope="{ title }">
                  <span v-if="title.indexOf(searchValue) > -1">
                    {{ title.substr(0, title.indexOf(searchValue)) }}
                    <span style="color: #f50">{{ searchValue }}</span>
                    {{ title.substr(title.indexOf(searchValue) + searchValue.length) }}
                  </span>
                  <span v-else>{{ title }}</span>
                </template>
              </a-tree>
            </a-card>
          </a-layout-sider>
          <a-layout :style="{ padding: '8px', background: '#fff', minHeight: '280px' }">
            <a-card :bordered="false" >
              <!-- 查询区域 -->
              <div class="table-page-search-wrapper">
                <a-form layout="inline">
                  <a-row :gutter="24">
                    <a-col :md="2" :sm="24">
                      <a-form-item>时间间隔 :</a-form-item>
                    </a-col>
                    <a-col :md="6" :sm="24">
                      <a-form-item>
                        <a-radio-group :value="size" @change="handleSizeChange">
                          <a-radio-button value="day">日</a-radio-button>
                          <a-radio-button value="month">月</a-radio-button>
                          <a-radio-button value="year">年</a-radio-button>
                          <a-radio-button value="self">自定义</a-radio-button>
                        </a-radio-group>
                      </a-form-item>
                    </a-col>
                    <a-col :md="2" :sm="24">
                      <a-form-item>
                        日期 :
                      </a-form-item>
                    </a-col>
                    <a-col :md="8" :sm="24">
                      <a-form-item>
                        <a-range-picker @change="onChange" />
                      </a-form-item>
                    </a-col>
                    <a-col :md="4" :sm="24">
                    </a-col>
                    <a-col :md="4" :sm="24">
                      <a-form-item>
                        <a-button type="primary">查询</a-button>
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <!--图表区域-->
                  <div style="text-align: center;font-weight: bold;font-size: large;">{{this.text}}</div>
                  <div v-if="this.size==='day'">
                    <stack-bar1 :height="height"></stack-bar1>
                  </div>
                  <div v-if="this.size==='month'">
                    <stack-bar :height="height"></stack-bar>
                  </div>
                  <div v-if="this.size==='year'">
                    <stack-bar2 :height="height"></stack-bar2>
                  </div>
                  <!--<line-chart-multid :height="height" :fields="lineFields" :dataSource="lineData" title="用户负荷叠加曲线"/>-->
                  <!--<bar :height="height" :dataSource="barData" title="用户最高负荷"></bar>-->
                  <a-row>
                    <a-col :xl="16" :lg="16" :md="16" :sm="24" :xs="24">
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns"
                        :dataSource="dataSource"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                    </a-col>
                    <a-col :xl="8" :lg="8" :md="8" :sm="24" :xs="24">
                      <coord-pie ></coord-pie>
                    </a-col>
                  </a-row>
                </a-form>
              </div>
            </a-card>
          </a-layout>
        </a-layout>
      </a-row>
    </a-form>
  </div>
</template>

<script>
  import { getAction } from '@/api/manage'
  import BarMultid from '@/components/chart/BarMultid'
  import StackBar from '@/components/chart/StackBar'
  import StackBar1 from '@/components/chart/StackBar1'
  import StackBar2 from '@/components/chart/StackBar2'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import CoordPie from '@/components/chart/CoordPie'
  import moment from "moment"

  const treeData = [
    {
      title: '一、农、林、木、渔业',
      key: '0-0',
      children: [
        {
          title: 'a公司',
          key: '0-0-0',
        },
        {
          title: 'b公司',
          key: '0-0-1',
        },
        {
          title: 'c公司',
          key: '0-0-2',
        },
        {
          title: 'd公司',
          key: '0-0-2',
        },
        {
          title: 'e公司',
          key: '0-0-2',
        },
        {
          title: 'f公司',
          key: '0-0-2',
        },
      ],
    },
    {
      title: '二、工业',
      key: '0-1',
      children: [
        { title: 'xxx工业有限公司', key: '0-1-0-0' },
        { title: 'xxx矿物公司', key: '0-1-0-1' },
        { title: 'xxx责任有限公司', key: '0-1-0-2' },
      ],
    },
    {
      title: '三、建筑业',
      key: '0-2',
      children: [
        { title: 's钢筋产业基地', key: '0-2-0-0' },
      ],
    },
    {
      title: '四、交通运输、仓储和邮政业',
      key: '0-3',
      children: [
        { title: '邮政速运', key: '0-3-0-0' },
      ],
    },
    {
      title: '五、信息传输、计算机传输和软件',
      key: '0-4',
      children: [
        { title: 'xx科技有限公司', key: '0-4-0-0' },
      ],
    },
    {
      title: '六、商业、住宿和餐饮业',
      key: '0-5',
      children: [
        { title: 'ff大酒店', key: '0-5-0-0' },
      ],
    },
    {
      title: '七、金融、房地产、商务及居民住所',
      key: '0-6',
      children: [
        { title: '中国银行', key: '0-6-0-0' },
      ],
    },
    {
      title: '八、公共事业及管理组织',
      key: '0-7',
      children: [
        { title: 'kk慈善', key: '0-7-0-0' },
      ],
    },
  ];

  export default {
    name: "ChartLoadInfo",
    components: {
      BarMultid,
      LineChartMultid,
      CoordPie,
      StackBar1,
      StackBar2,
      StackBar
    },
    data () {
      return {
        description: '过滤数据管理页面',
        title:'水位|流量趋势图',
        // 表头
        columns: [
          // {
          //   title: '序号',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title: '日期',
            align:"center",
            dataIndex: 'stcd',
          },
          // {
          //   title: '尖时段(kWh)',
          //   align:"center",
          //   dataIndex: 'z'
          // },
          {
            title: '峰时段(kWh)',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '平时段(kWh)',
            align:"center",
            dataIndex: 'qa'
          },
          {
            title: '谷时段(kWh)',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '总电量(kWh)',
            align:"center",
            dataIndex: 'qt'
          },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' },
          // }
        ],
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '15'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        url: {
          list: "/irrigation/river/list",
          delete: "/irrigation/river/delete",
          deleteBatch: "/irrigation/river/deleteBatch",
          exportXlsUrl: "irrigation/river/exportXls",
          importExcelUrl: "irrigation/river/importExcel",
        },
        height: 254,
        lineData: [
          { type: '0:00', 叠加负荷: 7.0 },
          { type: '1:00', 叠加负荷: 6.9 },
          { type: '2:00', 叠加负荷: 9.5 },
          { type: '3:00', 叠加负荷: 14.5 },
          { type: '4:00', 叠加负荷: 18.4 },
          { type: '5:00', 叠加负荷: 21.5 },
          { type: '6:00', 叠加负荷: 25.2 },
          { type: '7:00', 叠加负荷: 26.5 },
          { type: '8:00', 叠加负荷: 23.3 },
          { type: '9:00', 叠加负荷: 10.3 },
          { type: '10:00', 叠加负荷: 6.6 },
          { type: '11:00', 叠加负荷: 4.8 },
          { type: '12:00', 叠加负荷: 23.3 },
        ],
        barData: [
          {x:'a公司',y:532},
          {x:'b公司',y:654},
          {x:'c公司',y:482},
          {x:'d公司',y:591},
          {x:'e公司',y:263},
          {x:'f公司',y:399},
        ],
        checkType:'current',
        lineFields:['叠加负荷'],//柱状图存放横坐标（时间）
        text: '',
        searchValue: '',
        autoExpandParent: true,
        expandedKeys: [],
        checkedKeys: ['0-0'],
        selectedKeys: [],
        treeData,
        size: 'month',
      }
    },
    created(){
      this.dataSource = [
        {stcd:'2020-01',tm:236.23,q:28652.56,qa:3399.56,qt:52361.2},
        {stcd:'2020-02',tm:2514.3,q:526932.20,qa:25763,qt:1102635.9},
        {stcd:'2020-03',tm:25.3,q:243652.83,qa:10001.83,qt:492356.96}
      ]
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {

      modalFormOk() {
        // 新增/修改 成功时，重载列表
        // this.loadData();
      },
      //选择查询的类型
      handType(e){
        this.checkType = e;
        console.log(this.checkType)
        if(this.checkType==='current'){
          this.text = '实时电量偏差';
          this.lineData = [
            { type: '0:00', 客户申报电量: 7.0, 客户实际用电量: 3.9, 偏差电量: 1.2},
            { type: '0:15', 客户申报电量: 6.9, 客户实际用电量: 4.2, 偏差电量: 1.2},
            { type: '0:30', 客户申报电量: 9.5, 客户实际用电量: 5.7, 偏差电量: 1.2},
            { type: '0:45', 客户申报电量: 14.5, 客户实际用电量: 12.5, 偏差电量: 1.2},
            { type: '1:00', 客户申报电量: 18.4, 客户实际用电量: 15.9, 偏差电量: 1.2},
            { type: '1:15', 客户申报电量: 21.5, 客户实际用电量: 22.2, 偏差电量: 1.2},
            { type: '1:30', 客户申报电量: 25.2, 客户实际用电量: 22.0, 偏差电量: 1.2},
            { type: '1:45', 客户申报电量: 26.5, 客户实际用电量: 27.6, 偏差电量: 1.2},
            { type: '2:00', 客户申报电量: 23.3, 客户实际用电量: 25.2, 偏差电量: 1.2},
            { type: '2:15', 客户申报电量: 10.3 },
            { type: '2:30', 客户申报电量: 6.6 },
            { type: '2:45', 客户申报电量: 4.8 }
          ]

        }
        if(this.checkType==='day'){
          this.text = '每日电量偏差';
          this.lineData = [
            { type: '16日', 客户申报电量: 7.0, 客户实际用电量: 3.9, 偏差电量: 1.2},
            { type: '17日', 客户申报电量: 6.9, 客户实际用电量: 4.2, 偏差电量: 1.3},
            { type: '18日', 客户申报电量: 9.5, 客户实际用电量: 5.7, 偏差电量: 1.4},
            { type: '19日', 客户申报电量: 14.5, 客户实际用电量: 12.5, 偏差电量: 1.5},
            { type: '20日', 客户申报电量: 18.4, 客户实际用电量: 15.9, 偏差电量: 1.6},
            { type: '21日', 客户申报电量: 21.5, 客户实际用电量: 22.2, 偏差电量: 1.7},
            { type: '22日', 客户申报电量: 25.2, 客户实际用电量: 22.0, 偏差电量: 1.8},
            { type: '23日', 客户申报电量: 26.5, 客户实际用电量: 27.6, 偏差电量: 1.9},
            { type: '24日', 客户申报电量: 23.3, 客户实际用电量: 25.2, 偏差电量: 1.4},
            { type: '25日', 客户申报电量: 10.3, 客户实际用电量: 22.2, 偏差电量: 1.7 },
            { type: '26日', 客户申报电量: 6.6, 客户实际用电量: 15.2, 偏差电量: 1.3 },
            { type: '27日', 客户申报电量: 4.8, 客户实际用电量: 23.2, 偏差电量: 2.3 }
          ]
        }
        if(this.checkType==='month'){
          this.text = '月度电量偏差';
          this.lineData = [
            { type: '1月', 客户申报电量: 7.0, 客户实际用电量: 3.9, 偏差电量: 1.2},
            { type: '2月', 客户申报电量: 6.9, 客户实际用电量: 4.2, 偏差电量: 1.3},
            { type: '3月', 客户申报电量: 9.5, 客户实际用电量: 5.7, 偏差电量: 1.4},
            { type: '4月', 客户申报电量: 14.5, 客户实际用电量: 12.5, 偏差电量: 1.5},
            { type: '5月', 客户申报电量: 18.4, 客户实际用电量: 15.9, 偏差电量: 1.6},
            { type: '6月', 客户申报电量: 21.5, 客户实际用电量: 22.2, 偏差电量: 1.7},
            { type: '7月', 客户申报电量: 25.2, 客户实际用电量: 22.0, 偏差电量: 1.8},
            { type: '8月', 客户申报电量: 26.5, 客户实际用电量: 27.6, 偏差电量: 1.9},
            { type: '9月', 客户申报电量: 23.3, 客户实际用电量: 25.2, 偏差电量: 1.4},
            { type: '10月', 客户申报电量: 10.3, 客户实际用电量: 22.2, 偏差电量: 1.7 },
            { type: '11月', 客户申报电量: 6.6, 客户实际用电量: 15.2, 偏差电量: 1.3 },
            { type: '12月', 客户申报电量: 4.8, 客户实际用电量: 23.2, 偏差电量: 2.3 }
          ]
        }

      },
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" === sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
      },
      onExpand(expandedKeys) {
        console.log('onExpand', expandedKeys);
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.expandedKeys = expandedKeys;
        this.autoExpandParent = false;
      },
      onCheck(checkedKeys) {
        console.log('onCheck', checkedKeys);
        this.checkedKeys = checkedKeys;
      },
      onSelect(selectedKeys, info) {
        console.log('onSelect', info);
        this.selectedKeys = selectedKeys;
      },
      onChange(e) {
        const value = e.target.value;
        const expandedKeys = dataList
          .map(item => {
            if (item.key.indexOf(value) > -1) {
              return getParentKey(item.key, gData);
            }
            return null;
          })
          .filter((item, i, self) => item && self.indexOf(item) === i);
        Object.assign(this, {
          expandedKeys,
          searchValue: value,
          autoExpandParent: true,
        });
      },
      handleSizeChange(e) {
        this.size = e.target.value;
        if(this.size === 'day'){
          this.dataSource = [
            {stcd:'2020-05-01',tm:236.23,q:28652.56,qa:3399.56,qt:52361.2},
            {stcd:'2020-05-02',tm:2514.3,q:526932.20,qa:25763,qt:1102635.9},
            {stcd:'2020-05-03',tm:25.3,q:243652.83,qa:10001.83,qt:492356.96}
          ]
        }else if(this.size === 'month'){
          this.dataSource = [
            {stcd:'2020-01',tm:236.23,q:28652.56,qa:3399.56,qt:52361.2},
            {stcd:'2020-02',tm:2514.3,q:526932.20,qa:25763,qt:1102635.9},
            {stcd:'2020-03',tm:25.3,q:243652.83,qa:10001.83,qt:492356.96}
          ]
        }else if(this.size === 'year'){
          this.dataSource = [
            {stcd:'2018',tm:236.23,q:28652.56,qa:3399.56,qt:52361.2},
            {stcd:'2019',tm:2514.3,q:526932.20,qa:25763,qt:1102635.9},
            {stcd:'2020',tm:25.3,q:243652.83,qa:10001.83,qt:492356.96}
          ]
        }

      },
      dateChange(date, dateString) {
        console.log(date, dateString);
      },

    }
  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less'*/
</style>