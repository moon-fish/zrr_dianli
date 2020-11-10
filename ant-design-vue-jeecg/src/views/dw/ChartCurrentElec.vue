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
              <!--<a-input-search style="margin-bottom: 8px" placeholder="Search" @change="onChange" />-->
              <!--<a-tree-->
                <!--checkable-->
                <!--v-model="checkedKeys"-->
                <!--@expand="onExpand"-->
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
            <a-card :bordered="false">
              <div class="table-page-search-wrapper">
                <a-form layout="inline">
                  <div style="text-align: center;font-weight: bold;font-size: large;margin-bottom: 20px;">客户实时用电量</div>
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
                      <a-form-item>选择用户</a-form-item>
                    </a-col>
                    <a-col :md="4" :sm="24">
                      <a-form-item>
                        <a-select @change="companyChange" :value="companyType" style="width: 100%;">
                          <a-select-option v-for="(item,index) in this.companys" :key="item" >
                            {{ item }}
                          </a-select-option>
                        </a-select>
                      </a-form-item>
                    </a-col>
                    <a-col :md="2" :sm="24"></a-col>
                    <a-col :md="4" :sm="24">
                      <a-form-item v-if="this.ziShow">
                        <a-select @change="ziComChange" :value="ziComType" style="width: 100%;">
                          <a-select-option v-for="(item,index) in this.ziCom" :key="item" >
                            {{ item }}
                          </a-select-option>
                        </a-select>
                      </a-form-item>
                    </a-col>

                    <a-col :md="6" :sm="24"></a-col>
                    <a-col :md="2" :sm="24">
                      <a-form-item>
                        <a-button type="primary" @click="xhQuery">查询</a-button>
                      </a-form-item>
                    </a-col>
                  </a-row>
                  <a-row :gutter="24">
                    <a-col  :md="24" :sm="24" >
                      <div style="float: right;">(mWh)</div>
                      <line-chart-multid :title="title" :height="height" :fields="visitFields" :dataSource="visitInfo" style="padding-top: 50px;"></line-chart-multid>
                    </a-col>
                  </a-row>
                  <a-row>
                    <a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
                      <!-- table区域-begin -->
                      <a-tabs type="card" @change="callback" style="margin-left: 20px;">
                        <a-tab-pane key="1" tab="今日电量">
                          <!--00:00 - 01:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns"
                            :pagination="ipagination"
                            :dataSource="dataSource"
                            @change="handleTableChange">

                          </a-table>
                          <!--02:00 - 03:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                        </a-tab-pane>
                        <a-tab-pane key="2" tab="昨日电量">
                          <!--00:00 - 01:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <!--02:00 - 03:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                        </a-tab-pane>
                        <a-tab-pane key="3" tab="预测电量">
                          <!--00:00 - 01:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <!--02:00 - 03:45-->
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">

                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                          <a-table
                            ref="table"
                            size="middle"
                            rowKey="id"
                            bordered
                            :columns="columns2"
                            :pagination="ipagination"
                            :dataSource="dataSource2"
                            @change="handleTableChange">
                            <!--<span slot="action" slot-scope="text, record">-->
                            <!--<a @click="handleLook(record)">详情</a>-->

                            <!--<a-divider type="vertical" />-->
                            <!--<a-dropdown>-->
                            <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                            <!--<a>删除</a>-->
                            <!--</a-popconfirm>-->
                            <!--</a-dropdown>-->
                            <!--</span>-->
                          </a-table>
                        </a-tab-pane>
                      </a-tabs>
                      <!-- table区域-end -->
                    </a-col>
                  </a-row>
                </a-form>
              </div>
              <!--<div><a-button type="primary" style="float: right">导出</a-button></div>-->
              <!---->
              <!--<line-chart-multid :fields="visitFields" :dataSource="visitInfo" title="" style="padding-top: 50px;" :height="height"></line-chart-multid>-->
              <!--&lt;!&ndash;列表区域&ndash;&gt;-->

            </a-card>
          <!--</a-layout>-->
        <!--</a-layout>-->
      <!--</a-row>-->
    <!--</a-form>-->
  <!--</div>-->
</template>

<script>
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import ARow from 'ant-design-vue/es/grid/Row'
  import { getAction } from '@/api/manage'
  import ACol from 'ant-design-vue/es/grid/Col'
  import store from '@/store/'

  export default {
    name: 'ChartCurrentElec',
    components: {
      ACol,
      ARow,
      LineChartMultid,
    },
    data() {
      return {
        visitFields: ['今天', '昨天','预测'],
        visitInfo: [],
        height: 254,
        ipagination:false,
        columns:[
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
            title: '00:00',
            align:"center",
            dataIndex: 'stcd',
          },
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
        ],
        dataSource:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42 },
        ],
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
            title: '03:00',
            align:"center",
            dataIndex: 'stcd',
          },
          {
            title: '03:15',
            align:"center",
            dataIndex: 'tm'
          },
          {
            title: '03:30',
            align:"center",
            dataIndex: 'z'
          },
          {
            title: '03:45',
            align:"center",
            dataIndex: 'q'
          },
          {
            title: '04:00',
            align:"center",
            dataIndex: 'q1'
          },
          {
            title: '04:15',
            align:"center",
            dataIndex: 'q2'
          },
          {
            title: '04:30',
            align:"center",
            dataIndex: 'q3'
          },
          {
            title: '04:45',
            align:"center",
            dataIndex: 'q4'
          },
          {
            title: '05:00',
            align:"center",
            dataIndex: 'q5'
          },
          {
            title: '05:15',
            align:"center",
            dataIndex: 'q6'
          },
          {
            title: '05:30',
            align:"center",
            dataIndex: 'q7'
          },
          {
            title: '05:45',
            align:"center",
            dataIndex: 'q8'
          },
        ],
        dataSource2:[
          {stcd:21,tm:523.4,z:25263.00,q:28652.56,q1:3399.56,q2:3.42,q3:3399.56,q4:3.42 },
        ],
        areaType:'太原',
        companyType:'',
        companys:[],
        title:'电量',
        /*分公司相关*/
        ziCom:[],
        ziComType:'',
        ziShow:false,
      }

    },
    created(){
      this.initArea();
    },
    methods: {
      //初始化公司选择 并进行查询
      initArea(e){
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
        // this.companyType = '';
        this.ziCom = [];
        let userInfo = store.getters.userInfo;
        let id = userInfo.id
        getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
          if(res.success){
            console.log(res.result)
            console.log(this.companyType)
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].comTName === null){
                this.companys.push(res.result[i].comAName)
              }else{
                if(e === res.result[i].comTName){
                  this.ziCom.push(res.result[i].comAName)
                  this.ziShow = true;
                }
              }
            }
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
        this.ziShow = false;
        this.companyType = e;
        //当公司发生变化时，判断该公司是否为总公司，若是总公司，显示其子公司，若没有则不显示
        this.loadZiComs(e);
      },
      loadZiComs(e){
        this.initArea(e);
      },
      //子公司发生变化
      ziComChange(e){
        this.ziComType = e;
      },
      xhQuery(){
        var totalTime = 24*3600*1000;
        var spanTime = totalTime / 96;
        var startTime = new Date();
        startTime.setHours(0, 0, 0, 0);
        var currentTime = startTime.getTime();
        for(var i = 0; i < 96; i++) {
          currentTime += spanTime;
          let d = new Date(currentTime)
          let d1 = d.getHours()+':'+d.getMinutes()
          this.visitInfo.push({})
          this.visitInfo[i]['type'] = d1
          this.visitInfo[i][this.visitFields[0]] = Math.round(Math.random()*10)+30
          this.visitInfo[i][this.visitFields[1]] = Math.round(Math.random()*10)+20
          this.visitInfo[i][this.visitFields[2]] = Math.round(Math.random()*10)+25
        }
      },


      onExpand(expandedKeys) {
        console.log('onExpand', expandedKeys);
        // if not set autoExpandParent to false, if children expanded, parent can not collapse.
        // or, you can remove all expanded children keys.
        this.expandedKeys = expandedKeys;
        this.autoExpandParent = false;
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
      handleTableChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" === sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
      },
      callback(key) {
        console.log(key);
      },

    },
  }
</script>

<style scoped>

</style>