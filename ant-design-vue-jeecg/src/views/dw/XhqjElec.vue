<template>
  <!--<div class="table-page-search-wrapper">-->
    <!--<a-form layout="inline">-->
      <!--<a-row :gutter="24" style="background-color: #ececec;padding: 10px; margin: -10px">-->
        <!--<a-layout id="components-layout-demo-custom-trigger">-->
          <!--<a-layout-sider-->
            <!--collapsible-->
            <!--collapsedWidth="0"-->
            <!--style="background-color: #fff"-->
          <!--&gt;-->
            <!--<a-card :bordered="true" >-->
              <!--<a-input-search style="margin-bottom: 8px" placeholder="Search" @change="areaChange" />-->
              <!--<a-tree-->
                <!--v-model="checkedKeys"-->
                <!--@expand="onExpand"-->
                <!--@select="onSelect"-->
                <!--:expandedKeys="expandedKeys"-->
                <!--:autoExpandParent="autoExpandParent"-->
                <!--:treeData="treeData"-->
              <!--&gt;-->
                <!--<template slot="title" slot-scope="{ title }">-->
                  <!--<span v-if="title.indexOf(searchValue) > -1">-->
                    <!--{{ title.substr(0, title.indexOf(searchValue)) }}-->
                    <!--<span style="color: #f50">{{ searchValue }}</span>-->
                    <!--{{ title.substr(title.indexOf(searchValue) + searchValue.length) }}-->
                  <!--</span>-->
                  <!--<span v-else>{{ title }}</span>-->
                <!--</template>-->
              <!--</a-tree>-->
            <!--</a-card>-->
          <!--</a-layout-sider>-->
          <!--<a-layout :style="{ padding: '8px', background: '#fff', minHeight: '280px' }">-->
            <a-card :bordered="false" >
              <!-- 查询区域 -->
              <div class="table-page-search-wrapper">
                <a-form layout="inline">
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
                      <a-form-item>选择公司</a-form-item>
                    </a-col>
                    <a-col :md="3" :sm="24">
                      <a-form-item>
                        <a-select @change="companyChange" :value="companyType" style="width: 100%;">
                          <a-select-option v-for="(item,index) in this.companys" :key="item" >
                            {{ item }}
                          </a-select-option>
                        </a-select>
                      </a-form-item>
                    </a-col>

                    <a-col :md="2" :sm="24">
                      <a-form-item>时间间隔</a-form-item>
                    </a-col>
                    <a-col :md="4" :sm="24">
                      <a-form-item>
                        <a-radio-group :value="size" @change="handleSizeChange">
                          <a-radio-button value="day">日</a-radio-button>
                          <a-radio-button value="month">月</a-radio-button>
                          <a-radio-button value="year">年</a-radio-button>
                        </a-radio-group>
                      </a-form-item>
                    </a-col>
                    <a-col :md="2" :sm="24">
                      <a-form-item>
                        日期
                      </a-form-item>
                    </a-col>
                    <a-col :md="4" :sm="24">
                      <a-form-item>
                        <div v-if="this.size === 'day'">
                          <a-range-picker @change="onChange" />
                        </div>
                        <div v-if="this.size === 'month'">
                          <a-range-picker @change="onChange" />
                        </div>
                        <div v-if="this.size === 'year'">
                          <a-range-picker
                            :placeholder="['开始月份', '结束月份']"
                            format="YYYY-MM"
                            :value="valueMonth"
                            :mode="modeMonth"
                            @panelChange="handlePanelChange2"
                            @change="handleChangeMonth"
                          />
                        </div>
                      </a-form-item>
                    </a-col>
                    <!--<a-col :md="2" :sm="24">-->
                    <!--</a-col>-->
                    <a-col :md="2" :sm="24">
                      <a-form-item>
                        <a-button type="primary" @click="xhQuery">查询</a-button>
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <!--图表区域-->
                  <a-row :gutter="24">
                    <a-col :md="24" :sm="24">
                      <div style="float: right;">(kWh)</div>
                      <line-chart-multid :title="title" :height="height" :dataSource="lineData" :fields="lineFields"></line-chart-multid>
                    </a-col>
                  </a-row>
                  <!--<line-chart-multid :height="height" :fields="lineFields" :dataSource="lineData" title="用户负荷叠加曲线"/>-->
                  <!--<bar :height="height" :dataSource="barData" title="用户最高负荷"></bar>-->
                  <a-row>
                    <a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns2"
                        :dataSource="dataSource2"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns3"
                        :dataSource="dataSource3"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns4"
                        :dataSource="dataSource4"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns5"
                        :dataSource="dataSource5"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns6"
                        :dataSource="dataSource6"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                      <a-table
                        ref="table"
                        size="middle"
                        bordered
                        rowKey="id"
                        :columns="columns7"
                        :dataSource="dataSource7"
                        :pagination="ipagination"
                        @change="handleTableChange">
                      </a-table>
                    </a-col>
                  </a-row>
                </a-form>
              </div>
            </a-card>
          <!--</a-layout>-->
        <!--</a-layout>-->
      <!--</a-row>-->
    <!--</a-form>-->
  <!--</div>-->
</template>

<script>
  import { getAction } from '@/api/manage'
  import BarMultid from '@/components/chart/BarMultid'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import CoordPie from '@/components/chart/CoordPie'
  import moment from "moment"
  import ACol from 'ant-design-vue/es/grid/Col'
  import store from '@/store/'

  export default {
    name: 'XhqjElec',
    components: {
      ACol,
      BarMultid,
      LineChartMultid,
      CoordPie,
    },
    data () {
      return {
        description: '过滤数据管理页面',
        title:'电量',
        // 表头
        columns2:[
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
            title: '00:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '00:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '00:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '01:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '01:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '01:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '01:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '02:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '02:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '02:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '02:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '03:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '03:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '03:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '03:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '04:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource2:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        columns3:[
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
            title: '04:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '04:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '04:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '05:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '05:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '05:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '05:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '06:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '06:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '06:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '06:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '07:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '07:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '07:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '07:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '08:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource3:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        columns4:[
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
            title: '08:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '08:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '08:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '09:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '09:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '09:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '09:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '10:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '10:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '10:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '10:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '11:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '11:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '11:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '11:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '12:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource4:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        columns5:[
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
            title: '12:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '12:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '12:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '12:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '13:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '13:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '13:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '14:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '14:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '14:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '14:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '15:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '15:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '15:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '15:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '16:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource5:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        columns6:[
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
            title: '16:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '16:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '16:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '17:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '17:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '17:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '17:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '18:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '18:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '18:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '18:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '19:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '19:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '19:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '19:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '20:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource6:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        columns7:[
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
            title: '20:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '20:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '20:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '21:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '21:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '21:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '21:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '22:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '22:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '22:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '22:45',
            align:"center",
            dataIndex: 'q8'
          },
          {
            title: '23:00',
            align:"center",
            dataIndex: 'q9'
          },
          {
            title: '23:15',
            align:"center",
            dataIndex: 'q10'
          },
          {
            title: '23:30',
            align:"center",
            dataIndex: 'q11'
          },
          {
            title: '23:45',
            align:"center",
            dataIndex: 'q12'
          },
          {
            title: '00:00',
            align:"center",
            dataIndex: 'stcd',
          },
        ],
        dataSource7:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42,q5:3.42,q6:3.42,q7:3.42,q8:3.42,q9:3.42,q10:3.42,q11:3.42,q12:3.42 },
        ],
        ipagination:false,
        url: {
          list: "/irrigation/river/list",
          delete: "/irrigation/river/delete",
          deleteBatch: "/irrigation/river/deleteBatch",
          exportXlsUrl: "irrigation/river/exportXls",
          importExcelUrl: "irrigation/river/importExcel",
        },
        height: 350,
        lineData: [],
        lineFields:['中长期','实时','日前'],
        checkType:'current',
        text: '',
        searchValue: '',
        autoExpandParent: true,
        expandedKeys: [],
        checkedKeys: [],
        selectedKeys: [],
        treeData:[],
        size: 'day',
        valueMonth:[],
        modeMonth: ['month', 'month'],
        areaType:'太原',
        companys:[],
        companyType:'',
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){
      //加载树
      // this.loadTree();
      this.initArea();
    },
    methods: {
      // loadTree(){
      //   this.treeData = [
      //     {
      //       title: '太原',
      //       key: '0-0',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '大同',
      //       key: '0-1',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '朔州',
      //       key: '0-2',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '忻州',
      //       key: '0-3',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '阳泉',
      //       key: '0-4',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '吕梁',
      //       key: '0-5',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '晋中',
      //       key: '0-6',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '长治',
      //       key: '0-7',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '临汾',
      //       key: '0-8',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '运城',
      //       key: '0-9',
      //       disabled: true,
      //       children: [],
      //     },
      //     {
      //       title: '晋城',
      //       key: '0-10',
      //       disabled: true,
      //       children: [],
      //     },
      //   ];
      //   console.log(this.treeData)
      //   getAction('/userinfob/userInfoB/queryall',{}).then((res) => {
      //     if(res.success){
      //       console.log(res.result)
      //       for(let i=0;i<this.treeData.length;i++){
      //         let m = 0;
      //         for(let j=0;j<res.result.length;j++){
      //           if(this.treeData[i].title === res.result[j].addressInfo){
      //             this.treeData[i].children.push({})
      //             this.treeData[i].children[m].title = res.result[j].comAName
      //             this.treeData[i].children[m].key = res.result[j].comAName
      //             m++
      //           }
      //         }
      //       }
      //       console.log(this.treeData)
      //     }
      //   })
      // },
      modalFormOk() {
        // 新增/修改 成功时，重载列表
        // this.loadData();
      },
      //初始化公司选择 并进行查询
      initArea(){
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
        this.companyType = '';
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          if(res.success){
            console.log(res.result)
            for(let i=0;i<res.result.length;i++){
              // if(res.result[i].addressInfo === this.areaType){
                this.companys.push(res.result[i].comAName)

              // }
            }
            this.companyType = this.companys[0];
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
        this.companyType = e;
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
      // areaChange(e) {
      //   const value = e.target.value;
      //   const expandedKeys = dataList
      //     .map(item => {
      //       if (item.key.indexOf(value) > -1) {
      //         return getParentKey(item.key, gData);
      //       }
      //       return null;
      //     })
      //     .filter((item, i, self) => item && self.indexOf(item) === i);
      //   Object.assign(this, {
      //     expandedKeys,
      //     searchValue: value,
      //     autoExpandParent: true,
      //   });
      // },
      handleSizeChange(e) {
        this.size = e.target.value;
        console.log(this.size)

      },
      dateChange(date, dateString) {
        console.log(date, dateString);
      },
      onChange(date, dateString) {
        console.log(date, dateString);
      },
      //树 根据选择用户进行对比
      onSelect(selectedKeys,info) {
        this.lineData = [
          { type: '1号',  总电量: 26.3 },
          { type: '2号',  总电量: 39.3 },
          { type: '3号',  总电量: 45.0 },
          { type: '4号',  总电量: 25 },
          { type: '5号',  总电量: 22 },
          { type: '6号',  总电量: 45 },
          { type: '7号',  总电量: 34 },
        ];
        this.lineFields = ['中长期','实时','日前'];
        console.log(selectedKeys)
        this.selectedKeys = selectedKeys;//点击出现选中效果
        this.lineFields.push(this.selectedKeys[0])
        for(let i=0;i<this.lineData.length;i++){
          this.lineData[i][this.selectedKeys[0]] = Math.floor(Math.random()*50)
        }

      },
      //查询
      xhQuery(){
        this.lineFields = ['中长期','实时','日前'];
        this.lineData = []
        console.log(this.size);
        console.log(this.companyType);
        this.lineFields.push(this.companyType)
        console.log(this.lineFields)
        if(this.size === 'day'){
          var totalTime = 24*3600*1000;
          var spanTime = totalTime / 96;
          var startTime = new Date();
          startTime.setHours(0, 0, 0, 0);
          var currentTime = startTime.getTime();
          for(var i = 0; i < 96; i++) {
            currentTime += spanTime;
            let d = new Date(currentTime)
            let d1 = d.getHours()+':'+d.getMinutes()
            this.lineData.push({})
            this.lineData[i]['type'] = d1
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*100)+500
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*100)+500
            this.lineData[i][this.lineFields[2]] = Math.round(Math.random()*100)+500
            this.lineData[i][this.lineFields[3]] = Math.round(Math.random()*50)
          }
        }else if(this.size === 'month'){
          var d = new Date(2020, 5, 0);
          let tianshu = d.getDate();
          for(let i=0;i<tianshu;i++){
            this.lineData.push({})
            if(i<10){
              this.lineData[i]['type'] = "2020-05-0"+(i+1)
            }else{
              this.lineData[i]['type'] = "2020-05-"+(i+1)
            }
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*300)+2700
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*300)+2700
            this.lineData[i][this.lineFields[2]] = Math.round(Math.random()*300)+2700
            this.lineData[i][this.lineFields[3]] = Math.round(Math.random()*300)
          }
        }else if(this.size === 'year'){
          for(let i=0;i<12;i++){
            this.lineData.push({})
            if(i<10){
              this.lineData[i]['type'] = "2020-0"+(i+1)
            }else{
              this.lineData[i]['type'] = "2020-"+(i+1)
            }
            this.lineData[i][this.lineFields[0]] = Math.round(Math.random()*500)+5000
            this.lineData[i][this.lineFields[1]] = Math.round(Math.random()*500)+5000
            this.lineData[i][this.lineFields[2]] = Math.round(Math.random()*500)+5000
            this.lineData[i][this.lineFields[3]] = Math.round(Math.random()*500)
          }
        }

      },
      //月份调整
      handlePanelChange2(value, mode) {
        this.valueMonth = value;
        this.modeMonth = [mode[0] === 'date' ? 'month' : mode[0], mode[1] === 'date' ? 'month' : mode[1]];
      },
      handleChangeMonth(value) {
        this.valueMonth = value;
        console.log(this.valueMonth)
      },

    }
  }
</script>
<style scoped>
  /*@import '~@assets/less/common.less'*/
</style>


