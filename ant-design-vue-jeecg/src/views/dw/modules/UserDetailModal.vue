<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    okText="确定"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-row style="margin-bottom: 10px;" :gutter="24">
          <!--用户基本信息-->
          <a-col :xl="8" :md="8" :sm="24" >
            <div :bordered="true" style="margin-right: 5px;padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">用户基本信息</span>
              </div>
              <a-form-item label="企业全称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input v-decorator="[ 'comAName', validatorRules.comAName]" placeholder="请输入企业全称"></a-input>
              </a-form-item>
              <a-form-item label="企业地址" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input v-decorator="[ 'addressInfo', validatorRules.addressInfo]" placeholder="请输入企业地址" ></a-input>
                <!--<a-select @change="handType" v-decorator="[ 'industry', {initialValue:this.industry,rules: validatorRules.industry.rules}]">-->
                <!--<a-select-option value="sb">农业</a-select-option>-->
                <!--<a-select-option value="a">林业</a-select-option>-->
                <!--<a-select-option value="b">牧业</a-select-option>-->
                <!--<a-select-option value="c">渔业</a-select-option>-->
                <!--</a-select>-->
              </a-form-item>
              <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入联系人姓名" ></a-input>
              </a-form-item>
              <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码" ></a-input>
              </a-form-item>
            </div>
          </a-col>
          <!--电量负荷-->
          <a-col :xl="16" :md="16" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">负荷</span>
                <span style="float: right;">
                  <a-radio-group v-model="loadCompare" @change="loadCompareChange">
                    <a-radio-button value="a">
                      同比
                    </a-radio-button>
                    <a-radio-button value="b">
                      环比
                    </a-radio-button>
                  </a-radio-group>
                </span>
                <span style="margin-right: 50px;float: right">
                  <a-radio-group v-model="loadTime" @change="loadTimeChange">
                    <a-radio-button value="a">
                      本年
                    </a-radio-button>
                    <a-radio-button value="b">
                      本月
                    </a-radio-button>
                  </a-radio-group>
                </span>
              </div>
              <line-chart-multid :dataSource="lineData" :fields="lineFields"></line-chart-multid>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-bottom: 10px;">
          <!--电量 饼图-->
          <a-col :xl="8" :md="8" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">电量-饼图</span>
                <span style="float: right">
                  <a-radio-group v-model="pieValue" @change="pieOnChange">
                    <a-radio-button value="a">
                      本年
                    </a-radio-button>
                    <a-radio-button value="b">
                      本月
                    </a-radio-button>
                  </a-radio-group>
                </span>
              </div>
              <Pie :dataSource="pieData" style="margin-left: -100px;"></Pie>
            </div>
          </a-col>
          <!--电量 折线图-->
          <a-col :xl="16" :md="16" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">电量-折线图</span>
                <span style="float: right">
                  <a-radio-group v-model="lineCompare" @change="lineCompareChange">
                    <a-radio-button value="a">
                      同比
                    </a-radio-button>
                    <a-radio-button value="b">
                      环比
                    </a-radio-button>
                  </a-radio-group>
                </span>
                <span style="float: right;margin-right: 50px;">
                  <a-radio-group v-model="lineTime" @change="lineTimeChange">
                    <a-radio-button value="a">
                      本年
                    </a-radio-button>
                    <a-radio-button value="b">
                      本月
                    </a-radio-button>
                  </a-radio-group>
                </span>
              </div>
              <div v-if="this.lineTime === 'a'">
                <!--<bar-and-line4 :dataSource="elecLine" ></bar-and-line4>-->
                <line-chart-multid :dataSource="elecLine" :fields="elecFields"></line-chart-multid>
              </div>
              <div v-if="this.lineTime === 'b'">
                <bar-multid :fields="barFields" :dataSource="barData"></bar-multid>
              </div>
            </div>
          </a-col>
        </a-row>
        <a-row style="margin-bottom: 10px;">
          <!--合同统计-->
          <a-col :xl="8" :md="8" :sm="24">
            <div :bordered="true" style="margin-right: 5px;padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">合同信息</span>
              </div>
              <a-form-item label="合同数量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.contractNum" placeholder="请输入企业全称"></a-input>
              </a-form-item>
              <a-form-item label="累计合同量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.contractTotal" placeholder="请输入行业类别" ></a-input>
                <!--<a-select @change="handType" v-decorator="[ 'industry', {initialValue:this.industry,rules: validatorRules.industry.rules}]">-->
                <!--<a-select-option value="sb">农业</a-select-option>-->
                <!--<a-select-option value="a">林业</a-select-option>-->
                <!--<a-select-option value="b">牧业</a-select-option>-->
                <!--<a-select-option value="c">渔业</a-select-option>-->
                <!--</a-select>-->
              </a-form-item>
              <a-form-item label="累计执行量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">
                <a-input :value="this.useElec.toFixed(3)" placeholder="请输入联系人姓名" ></a-input>
              </a-form-item>
              <!--<a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px;">-->
                <!--<a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码" ></a-input>-->
              <!--</a-form-item>-->
            </div>
          </a-col>
          <!--交易信息-->
          <a-col :xl="16" :md="16" :sm="24">
            <div :bordered="true" style="padding: 10px;">
              <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
                <span style="margin-left: 10px;">交易信息</span>
              </div>
              <a-table
                ref="table"
                size="middle"
                bordered
                rowKey="id"
                :columns="columns"
                :dataSource="dataSource"
                :pagination="ipagination"
                :loading="loading"
                :scroll="{ x: 714 }"
                :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
                @change="handleTableChange">

                <template slot="htmlSlot" slot-scope="text">
                  <div v-html="text"></div>
                </template>
                <template slot="imgSlot" slot-scope="text">
                  <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
                  <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
                </template>
                <template slot="fileSlot" slot-scope="text">
                  <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
                  <a-button
                    v-else
                    :ghost="true"
                    type="primary"
                    icon="download"
                    size="small"
                    @click="uploadFile(text)">
                    下载
                  </a-button>
                </template>

                <span slot="contractStatus" slot-scope="text, record">
                  <div v-if="text == '0'">
                    <span style="color: limegreen">正常</span>
                  </div>
                  <div v-if="text === '1'">
                    <span style="color: #ffa95e">即将过期</span>
                  </div>
                  <div v-if="text === '2'">
                    <span style="color: red">到期</span>
                  </div>
                </span>
                <span slot="action" slot-scope="text, record">
                  <a @click="userDetail(record)" v-has="'admin:edit'">用户详情</a>
                    <a-divider type="vertical" />
                  <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

                  <a-divider type="vertical" />
                  <a-dropdown v-has="'admin:delete'">
                    <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
                    <a-menu slot="overlay">
                      <a-menu-item>
                        <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                          <a>删除</a>
                        </a-popconfirm>
                      </a-menu-item>
                    </a-menu>
                  </a-dropdown>
                </span>

              </a-table>
            </div>
          </a-col>
        </a-row>
        <a-row>
          <!--结算信息-->
          <div :bordered="true" style="padding: 10px;">
            <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
              <span style="margin-left: 10px;">结算信息</span>
            </div>
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :columns="columns2"
              :dataSource="dataSource2"
              :pagination="ipagination"
              :scroll="{ x: 1098 }"
              :loading="loading"

              @change="handleTableChange">

              <template slot="htmlSlot" slot-scope="text">
                <div v-html="text"></div>
              </template>
              <template slot="imgSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
                <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
              </template>
              <template slot="fileSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
                <a-button
                  v-else
                  :ghost="true"
                  type="primary"
                  icon="download"
                  size="small"
                  @click="uploadFile(text)">
                  下载
                </a-button>
              </template>

              <span slot="action" slot-scope="text, record">
                <a @click="userDetail(record)" v-has="'admin:edit'">用户详情</a>
                  <a-divider type="vertical" />
                <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

                <a-divider type="vertical" />
                <a-dropdown v-has="'admin:delete'">
                  <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
                  <a-menu slot="overlay">
                    <a-menu-item>
                      <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                        <a>删除</a>
                      </a-popconfirm>
                    </a-menu-item>
                  </a-menu>
                </a-dropdown>
              </span>

            </a-table>
          </div>
        </a-row>
        <a-row>
          <!--分公司信息-->
          <div :bordered="true" style="padding: 10px;" v-if="this.isShowZiComs">
            <div style="border-left: 3px solid #025b61;font-weight: bold;font-size: 18px;margin-bottom: 10px;">
              <span style="margin-left: 10px;">分公司信息</span>
            </div>
            <a-table
              ref="table"
              size="middle"
              bordered
              rowKey="id"
              :columns="columns3"
              :dataSource="dataSource3"
              :pagination="ipagination"
              :scroll="{ x: 1098 }"
              :loading="loading"

              @change="handleTableChange">

              <template slot="htmlSlot" slot-scope="text">
                <div v-html="text"></div>
              </template>
              <template slot="imgSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
                <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
              </template>
              <template slot="fileSlot" slot-scope="text">
                <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
                <a-button
                  v-else
                  :ghost="true"
                  type="primary"
                  icon="download"
                  size="small"
                  @click="uploadFile(text)">
                  下载
                </a-button>
              </template>

              <span slot="action" slot-scope="text, record">
                <a @click="userDetail(record)" v-has="'admin:edit'">用户详情</a>
                  <a-divider type="vertical" />
                <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

                <a-divider type="vertical" />
                <a-dropdown v-has="'admin:delete'">
                  <a class="ant-dropdown-link" >更多 <a-icon type="down" /></a>
                  <a-menu slot="overlay">
                    <a-menu-item>
                      <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                        <a>删除</a>
                      </a-popconfirm>
                    </a-menu-item>
                  </a-menu>
                </a-dropdown>
              </span>

            </a-table>
          </div>
        </a-row>
      </a-form>
    </a-spin>


  </a-modal>
</template>

<script>
  import Pie from '@/components/chart/Pie'
  import BarAndLine4 from '@/components/chart/BarAndLine4'
  import BarAndLine2 from '@/components/chart/BarAndLine2'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import BarMultid from '@/components/chart/BarMultid'
  import pick from 'lodash.pick'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction } from '@/api/manage'

  export default {
    name: 'UserDetailModal',
    mixins:[JeecgListMixin],
    components: {
      Pie,
      BarAndLine4,
      BarAndLine2,
      BarMultid,
      LineChartMultid
    },
    data () {
      return {
        title:"操作",
        width:1200,
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
          comAName: {rules: [
            ]},
          comSName: {rules: [
            ]},
          addressInfo: {rules: [
            ]},
          industry: {rules: [
            ]},
          industrialSite: {rules: [
            ]},
          supplyArea: {rules: [
            ]},
          address: {rules: [
            ]},
          email: {rules: [
              {pattern:/^[1-9]\d{5}$/, message: '请输入正确的邮政编码!'},
            ]},
          name: {rules: [
            ]},
          phone: {rules: [
              {pattern:/^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]},
          officePhone: {rules: [
            ]},
          duty: {rules: [
            ]},
          ems: {rules: [
            ]},
          internet: {rules: [
              {pattern:/^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/, message: '请输入正确的网址!'},
            ]},
          socialCredit: {rules: [
            ]},
          establishment: {rules: [
            ]},
          legalName: {rules: [
            ]},
          registeredAddress: {rules: [
            ]},
          legalRepresentativeName: {rules: [
            ]},
          orgCode: {rules: [
            ]},
          depositBank: {rules: [
            ]},
          depositName: {rules: [
            ]},
          depoaitAccount: {rules: [
            ]},
          registerNumber: {rules: [
            ]},
          registerMoney: {rules: [
              {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]},
          totalAssets: {rules: [
              {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
            ]},
          licenceNo: {rules: [
            ]},
          effectiveDate: {rules: [
            ]},
          expiryDate: {rules: [
            ]},
          contractCapacity: {rules: [
            ]},
          operatingCapacity: {rules: [
            ]},
          maxVoltage: {rules: [
              {pattern:/^-?\d+$/, message: '请输入整数!'},
            ]},
          monthElecInfo: {rules: [
            ]},
          yearElecInfo: {rules: [
            ]},
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'合同名称',
            align:"center",
            dataIndex: 'contractName'
          },
          {
            title:'电力用户名称',
            align:"center",
            dataIndex: 'userName'
          },
          // {
          //   title:'交易类型',
          //   align:"center",
          //   dataIndex: 'transactionType'
          // },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'结束时间',
          //   align:"center",
          //   dataIndex: 'endDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'合同电量',
            align:"center",
            dataIndex: 'purchaseElectricity'
          },
          {
            title:'用户购电价',
            align:"center",
            dataIndex: 'purchasePrice'
          },
          // {
          //   title:'开户银行',
          //   align:"center",
          //   dataIndex: 'depoaitBank'
          // },
          // {
          //   title:'联系人姓名',
          //   align:"center",
          //   dataIndex: 'name'
          // },
          // {
          //   title:'手机号码',
          //   align:"center",
          //   dataIndex: 'phone'
          // },
          // {
          //   title:'办公电话',
          //   align:"center",
          //   dataIndex: 'officePhone'
          // },
          // {
          //   title:'联系人职务',
          //   align:"center",
          //   dataIndex: 'duty'
          // },
          // {
          //   title:'开户账号',
          //   align:"center",
          //   dataIndex: 'depoaitAccount'
          // },
          // {
          //   title:'合同附件',
          //   align:"center",
          //   dataIndex: 'contractFile'
          // },
          {
            title:'电厂电价',
            align:"center",
            dataIndex: 'b'
          },
          {
            title:'预测收益',
            align:"center",
            dataIndex: 'c',
            customRender:function (text) {
              if(text !== undefined){
                return text.toFixed(2)
              }
            }
          },
          {
            title:'累计执行电量',
            align:"center",
            dataIndex: 'd'
          },
          {
            title:'已获收益',
            align:"center",
            dataIndex: 'e'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'contractStatus',
            scopedSlots: { customRender: 'contractStatus' }
          },

          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        columns2: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'用户名称',
            align:"center",
            dataIndex: 'username'
          },
          {
            title:'结算时间',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,7):text)
            }
          },
          {
            title:'电量',
            align:"center",
            dataIndex: 'elecNum'
          },
          {
            title:'单价',
            align:"center",
            dataIndex: 'elecPrice'
          },
          {
            title:'电费',
            align:"center",
            dataIndex: 'elecCharge'
          },
          // {
          //   title:'费用月份',
          //   align:"center",
          //   dataIndex: 'monthDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'费用年份',
          //   align:"center",
          //   dataIndex: 'yearDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'考核电量',
            align:"center",
            dataIndex: 'byzd'
          },
          {
            title:'考核电费',
            align:"center",
            dataIndex: 'byzdt'
          },
          {
            title:'节省',
            align:"center",
            dataIndex: 'elecSave'
          },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        columns3: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'企业全称',
            align:"center",
            width:120,
            dataIndex: 'comAName'
          },
          {
            title:'地域信息',
            align:"center",
            dataIndex: 'addressInfo'
          },
          {
            title:'供电区域',
            align:"center",
            dataIndex: 'supplyArea'
          },
          // {
          //   title:'通讯地址',
          //   align:"center",
          //   dataIndex: 'address'
          // },
          // {
          //   title:'邮政编码',
          //   align:"center",
          //   dataIndex: 'email'
          // },
          {
            title:'联系人',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'手机号码',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'客户质量',
            align:"center",
            dataIndex: 'cusQuality',
            customRender:function (t,r,index) {
              if(r.cusQuality === '0'){
                r.cusQuality = '优质'
              }else if(r.cusQuality === '1'){
                r.cusQuality = '普通'
              }else if(r.cusQuality === '2'){
                r.cusQuality = '劣质'
              }
              return r.cusQuality
            }
          },
          {
            title:'合同有效期',
            align:"center",
            dataIndex: 'effective',
            scopedSlots: { customRender: 'effective' }
          },

        ],
        url: {
          list: "/userpurchasecontractb/userPurchaseContractB/list",
          delete: "/userpurchasecontractb/userPurchaseContractB/delete",
          deleteBatch: "/userpurchasecontractb/userPurchaseContractB/deleteBatch",
          exportXlsUrl: "/userpurchasecontractb/userPurchaseContractB/exportXls",
          importExcelUrl: "/userpurchasecontractb/userPurchaseContractB/importExcel",
        },
        ipagination:{
          current: 1,
          pageSize: 3,
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: false,
          total: 0
        },
        dataSource2: [],
        dataSource3: [],
        pieValue: 'a',
        lineFields:['用电负荷'],
        lineData:[],
        barFields:['申报电量','使用电量','偏差电量'],
        barData:[],
        pieData:[],
        barLineData:[
          { type: '1月', 偏差电量: 200, 申报电量: 1000},
          { type: '2月', 偏差电量: 600, 申报电量: 1000 },
          { type: '3月', 偏差电量: 200, 申报电量: 1000 },
          { type: '4月', 偏差电量: 900, 申报电量: 1000 },
          { type: '5月', 偏差电量: 200, 申报电量: 1000 },
          { type: '6月', 偏差电量: 200, 申报电量: 1000 },

        ],
        loadCompare: 'a',
        loadTime: 'a',
        lineCompare: 'a',
        lineTime: 'a',
        username:'',
        data1: [],
        aelec: 0,
        alelec: 0,
        contractNum: 0,
        contractTotal: 0,
        useElec: 0,
        elecLine:[],
        elecFields:['使用电量','去年电量'],
        contractAverage: 0,
        isShowZiComs:false, //是否显示分公司列表，若分公司存在，则显示否则不显示
        // scale:[],
      }
    },
    methods: {
      show(record){
        console.log(record)
        this.username = record.comAName;
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'comAName','comSName','addressInfo','industry','industrialSite','supplyArea','address','email','name','phone','officePhone','duty','ems','internet','socialCredit','establishment','legalName','registeredAddress','legalRepresentativeName','orgCode','depositBank','depositName','depoaitAccount','registerNumber','registerMoney','totalAssets','licenceNo','effectiveDate','expiryDate','contractCapacity','operatingCapacity','maxVoltage','monthElecInfo','yearElecInfo'))
        })
        //加载结算信息
        this.loadElecMoney();
        //加载负荷
        this.loadData();
        //加载饼图
        this.loadPie();
        //加载合同信息
        this.loadContract();
        //加载电量信息
        this.loadElecLine();
        //加载交易信息
        this.loadTrade();
        //加载分公司信息
        this.loadZiComs();
      },
      //加载结算信息
      loadElecMoney(){
        getAction('/balancemanage/balanceManage/list',{ username:this.username }).then((res) => {
          if(res.success){
            this.dataSource2 = res.result.records;
            // this.ipagination.total = res.result.total;
          }
        })
      },
      handleOk () {
        this.close()
      },
      handleCancel(){
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      //负荷同比环比
      loadCompareChange(e){
        console.log(e.target.value)
        this.loadCompare = e.target.value;
        this.loadData()
      },
      //负荷 年月
      loadTimeChange(e){
        this.loadTime = e.target.value;
        this.loadData()
      },
      //负荷条件查询
      loadData(){
        this.lineData = [];
        this.lineFields = [];
        //给相应的横坐标赋值
        getAction('/hisload/hisload/queryall',{username:this.username}).then((res) => {
          if(res.success){
            this.data1 = res.result
            //年数据
            if(this.loadTime === 'a'){
              // 横坐标
              for(let i=0;i<12;i++){
                this.lineData.push({});
                this.lineData[i]['type'] = i+1+'月'
              }
              // 给横坐标赋值
              for(let i=0;i<this.lineData.length;i++){
                for(let j=0;j<res.result.length;j++){
                  let mon = parseInt(res.result[j].elecDate.split("-")[1])
                  if(this.lineData[i]['type'] === mon+'月'){
                    if(this.lineData[i]['本年用电负荷'] === undefined){
                      this.lineData[i]['本年用电负荷'] = 0;
                    }
                    if(this.lineData[i]['去年用电负荷'] === undefined){
                      this.lineData[i]['去年用电负荷'] = 0;
                    }
                    //区分今年与去年数据
                    let date = new Date();
                    let elecUse = this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                      this.data1[j].ydll+this.data1[j].ydyw+this.data1[j].ydss+this.data1[j].ydsw+
                      this.data1[j].edll+this.data1[j].edyw+this.data1[j].edss+this.data1[j].edsw+
                      this.data1[j].sdll+this.data1[j].sdyw+this.data1[j].sdss+this.data1[j].sssw+
                      this.data1[j].sidll+this.data1[j].sidyw+this.data1[j].sidss+this.data1[j].sidsw+
                      this.data1[j].wdll+this.data1[j].wdyw+this.data1[j].wdss+this.data1[j].wdsw+
                      this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                      this.data1[j].qdll+this.data1[j].qdyw+this.data1[j].qdss+this.data1[j].qdsw+
                      this.data1[j].bdll+this.data1[j].bdyw+this.data1[j].bdss+this.data1[j].bdsw+
                      this.data1[j].jdll+this.data1[j].jdyw+this.data1[j].jdss+this.data1[j].jdsw+
                      this.data1[j].shidll+this.data1[j].shidyw+this.data1[j].shidss+this.data1[j].shidsw+
                      this.data1[j].sydll+this.data1[j].sydyw+this.data1[j].sydss+this.data1[j].sydsw+
                      this.data1[j].sedll+this.data1[j].sedyw+this.data1[j].sedss+this.data1[j].sedsw+
                      this.data1[j].ssdll+this.data1[j].ssdyw+this.data1[j].ssdss+this.data1[j].ssdsw+
                      this.data1[j].ssidll+this.data1[j].ssidyw+this.data1[j].ssidss+this.data1[j].ssidsw+
                      this.data1[j].swdll+this.data1[j].swdyw+this.data1[j].swdss+this.data1[j].swdsw+
                      this.data1[j].sldll+this.data1[j].sldyw+this.data1[j].sldss+this.data1[j].sldsw+
                      this.data1[j].sqdll+this.data1[j].sqdyw+this.data1[j].sqdss+this.data1[j].sqdsw+
                      this.data1[j].sbdll+this.data1[j].sbdyw+this.data1[j].sbdss+this.data1[j].sbdsw+
                      this.data1[j].sjdll+this.data1[j].sjdyw+this.data1[j].sjdss+this.data1[j].sjdsw+
                      this.data1[j].esdll+this.data1[j].esdyw+this.data1[j].esdss+this.data1[j].esdsw+
                      this.data1[j].eydll+this.data1[j].eydyw+this.data1[j].eydss+this.data1[j].eydsw+
                      this.data1[j].eedll+this.data1[j].eedyw+this.data1[j].eedss+this.data1[j].eedsw+
                      this.data1[j].essdll+this.data1[j].essdyw+this.data1[j].essdss+this.data1[j].essdsw
                    //区分同比环比
                    if(this.loadCompare === 'a'){
                      this.lineFields = ['本年用电负荷','去年用电负荷']
                      if(parseInt(this.data1[j].elecDate.split("-")[0]) === date.getFullYear()){
                        this.lineData[i]['本年用电负荷'] += parseFloat(elecUse.toFixed(2))
                      }else if(parseInt(this.data1[j].elecDate.split("-")[0]) === date.getFullYear()-1){
                        this.lineData[i]['去年年用电负荷'] += parseFloat(elecUse.toFixed(2))
                      }
                    }else{
                      this.lineFields = ['本年用电负荷']
                      if(parseInt(this.data1[j].elecDate.split("-")[0]) === date.getFullYear()){
                        this.lineData[i]['本年用电负荷'] += parseFloat(elecUse.toFixed(2))
                      }
                    }

                  }
                }
              }
            }else{
              //月数据 横坐标 同比
              if(this.loadCompare === 'a'){
                this.lineFields = ['本月用电负荷','同比用电负荷']

                let d = new Date();
                let year = d.getFullYear();
                let month = d.getMonth()+1;
                if(month<10){
                  month = "0"+month;
                }
                let date = new Date(year,month,0)
                let ts = date.getDate();
                console.log(ts)
                for(let i=0;i<ts;i++){
                  this.lineData.push({});
                  if(i<9){
                    this.lineData[i]['type'] = month+"-0"+(i+1)
                  }else{
                    this.lineData[i]['type'] = month+"-"+(i+1)
                  }
                }
                //月数据 给横坐标赋值
                for(let i=0;i<this.lineData.length;i++){
                  console.log(res.result.length)
                  for(let j=0;j<res.result.length;j++){
                    let date = new Date();
                    let year = date.getFullYear();
                    let y = parseInt(res.result[j].elecDate.split("-")[0])
                    let bef = res.result[j].elecDate.split(" ")[0]
                    let day = bef.split("-")[1]+"-"+bef.split("-")[2]
                    if(this.lineData[i]['type'] === day){
                      if(this.lineData[i]['本月用电负荷'] === undefined){
                        this.lineData[i]['本月用电负荷'] = 0;
                      }
                      if(this.lineData[i]['同比用电负荷'] === undefined){
                        this.lineData[i]['同比用电负荷'] = 0;
                      }
                      let elecUse = this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].ydll+this.data1[j].ydyw+this.data1[j].ydss+this.data1[j].ydsw+
                        this.data1[j].edll+this.data1[j].edyw+this.data1[j].edss+this.data1[j].edsw+
                        this.data1[j].sdll+this.data1[j].sdyw+this.data1[j].sdss+this.data1[j].sssw+
                        this.data1[j].sidll+this.data1[j].sidyw+this.data1[j].sidss+this.data1[j].sidsw+
                        this.data1[j].wdll+this.data1[j].wdyw+this.data1[j].wdss+this.data1[j].wdsw+
                        this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                        this.data1[j].qdll+this.data1[j].qdyw+this.data1[j].qdss+this.data1[j].qdsw+
                        this.data1[j].bdll+this.data1[j].bdyw+this.data1[j].bdss+this.data1[j].bdsw+
                        this.data1[j].jdll+this.data1[j].jdyw+this.data1[j].jdss+this.data1[j].jdsw+
                        this.data1[j].shidll+this.data1[j].shidyw+this.data1[j].shidss+this.data1[j].shidsw+
                        this.data1[j].sydll+this.data1[j].sydyw+this.data1[j].sydss+this.data1[j].sydsw+
                        this.data1[j].sedll+this.data1[j].sedyw+this.data1[j].sedss+this.data1[j].sedsw+
                        this.data1[j].ssdll+this.data1[j].ssdyw+this.data1[j].ssdss+this.data1[j].ssdsw+
                        this.data1[j].ssidll+this.data1[j].ssidyw+this.data1[j].ssidss+this.data1[j].ssidsw+
                        this.data1[j].swdll+this.data1[j].swdyw+this.data1[j].swdss+this.data1[j].swdsw+
                        this.data1[j].sldll+this.data1[j].sldyw+this.data1[j].sldss+this.data1[j].sldsw+
                        this.data1[j].sqdll+this.data1[j].sqdyw+this.data1[j].sqdss+this.data1[j].sqdsw+
                        this.data1[j].sbdll+this.data1[j].sbdyw+this.data1[j].sbdss+this.data1[j].sbdsw+
                        this.data1[j].sjdll+this.data1[j].sjdyw+this.data1[j].sjdss+this.data1[j].sjdsw+
                        this.data1[j].esdll+this.data1[j].esdyw+this.data1[j].esdss+this.data1[j].esdsw+
                        this.data1[j].eydll+this.data1[j].eydyw+this.data1[j].eydss+this.data1[j].eydsw+
                        this.data1[j].eedll+this.data1[j].eedyw+this.data1[j].eedss+this.data1[j].eedsw+
                        this.data1[j].essdll+this.data1[j].essdyw+this.data1[j].essdss+this.data1[j].essdsw
                      if(year === y){
                        this.lineData[i]['本月用电负荷'] += parseFloat(elecUse.toFixed(2))
                      }
                      if(year-1 === y){
                        this.lineData[i]['同比用电负荷'] += parseFloat(elecUse.toFixed(2))
                      }
                    }else{
                      this.lineData[i]['本月用电负荷'] = 0
                      this.lineData[i]['同比用电负荷'] = 0
                    }
                  }
                }
              }else{
                //横坐标  环比
                this.lineFields = ['本月负荷','上月负荷']
                for(let i=0;i<31;i++){
                  this.lineData.push({});
                  if(i<9){
                    this.lineData[i]['type'] = "0"+(i+1)
                  }else{
                    this.lineData[i]['type'] = i+1
                  }
                }
                let date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth();
                for(let i=0;i<this.lineData.length;i++){
                  for(let j=0;j<this.data1.length;j++){
                    let d = this.data1[j].elecDate.split("-")[0]+"-"+parseInt(this.data1[j].elecDate.split("-")[1])
                    let d1 = year+"-"+month
                    let d2 = year+"-"+(month+1)
                    if(this.lineData[i]['本月负荷'] === undefined){
                      this.lineData[i]['本月负荷'] = 0;
                    }
                    if(this.lineData[i]['上月负荷'] === undefined){
                      this.lineData[i]['上月负荷'] = 0;
                    }
                    let elecUse = this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                      this.data1[j].ydll+this.data1[j].ydyw+this.data1[j].ydss+this.data1[j].ydsw+
                      this.data1[j].edll+this.data1[j].edyw+this.data1[j].edss+this.data1[j].edsw+
                      this.data1[j].sdll+this.data1[j].sdyw+this.data1[j].sdss+this.data1[j].sssw+
                      this.data1[j].sidll+this.data1[j].sidyw+this.data1[j].sidss+this.data1[j].sidsw+
                      this.data1[j].wdll+this.data1[j].wdyw+this.data1[j].wdss+this.data1[j].wdsw+
                      this.data1[j].ldll+this.data1[j].ldyw+this.data1[j].ldss+this.data1[j].ldsw+
                      this.data1[j].qdll+this.data1[j].qdyw+this.data1[j].qdss+this.data1[j].qdsw+
                      this.data1[j].bdll+this.data1[j].bdyw+this.data1[j].bdss+this.data1[j].bdsw+
                      this.data1[j].jdll+this.data1[j].jdyw+this.data1[j].jdss+this.data1[j].jdsw+
                      this.data1[j].shidll+this.data1[j].shidyw+this.data1[j].shidss+this.data1[j].shidsw+
                      this.data1[j].sydll+this.data1[j].sydyw+this.data1[j].sydss+this.data1[j].sydsw+
                      this.data1[j].sedll+this.data1[j].sedyw+this.data1[j].sedss+this.data1[j].sedsw+
                      this.data1[j].ssdll+this.data1[j].ssdyw+this.data1[j].ssdss+this.data1[j].ssdsw+
                      this.data1[j].ssidll+this.data1[j].ssidyw+this.data1[j].ssidss+this.data1[j].ssidsw+
                      this.data1[j].swdll+this.data1[j].swdyw+this.data1[j].swdss+this.data1[j].swdsw+
                      this.data1[j].sldll+this.data1[j].sldyw+this.data1[j].sldss+this.data1[j].sldsw+
                      this.data1[j].sqdll+this.data1[j].sqdyw+this.data1[j].sqdss+this.data1[j].sqdsw+
                      this.data1[j].sbdll+this.data1[j].sbdyw+this.data1[j].sbdss+this.data1[j].sbdsw+
                      this.data1[j].sjdll+this.data1[j].sjdyw+this.data1[j].sjdss+this.data1[j].sjdsw+
                      this.data1[j].esdll+this.data1[j].esdyw+this.data1[j].esdss+this.data1[j].esdsw+
                      this.data1[j].eydll+this.data1[j].eydyw+this.data1[j].eydss+this.data1[j].eydsw+
                      this.data1[j].eedll+this.data1[j].eedyw+this.data1[j].eedss+this.data1[j].eedsw+
                      this.data1[j].essdll+this.data1[j].essdyw+this.data1[j].essdss+this.data1[j].essdsw
                    if(d === d1){
                      if(this.lineData[i]['type'].toString() === this.data1[j].elecDate.split(" ")[0].split("-")[2]){
                        this.lineData[i]['上月负荷'] = parseFloat(elecUse.toFixed(2));
                      }
                    }
                    console.log(d)
                    console.log(d2)
                    console.log("++++++++++++")
                    if(d === d2){
                      console.log("=============")
                      if(this.lineData[i]['type'].toString() === this.data1[j].elecDate.split(" ")[0].split("-")[2]){
                        this.lineData[i]['本月负荷'] = parseFloat(elecUse.toFixed(2));
                      }
                    }

                  }
                }

              }

            }

          }
        })
      },
      //饼图
      pieOnChange(e) {
        console.log(`checked = ${e.target.value}`);
        this.pieValue = e.target.value
        this.loadPie();
      },
      //饼图数据
      loadPie(){
        let date = new Date();
        let year = date.getFullYear().toString();
        let month = date.getMonth()+1
        let that = this
        this.pieData = [];
        that.alelec = 0;
        that.aelec = 0;
        //总电量
        getAction('/userpurchasecontractb/userPurchaseContractB/queryall',{cusname:this.username}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].beginDate.split("-")[0] === year && res.result[i].beginDate.split("-")[0]){
                that.alelec += parseInt(res.result[i].purchaseElectricity)
              }
              if(this.pieValue === 'b'){
                that.alelec = that.alelec/12
              }
            }
            //实际使用电量
            getAction('/hiselec/hiselec/queryall',{username:this.username}).then((res) => {
              if(res.success){
                for(let i=0;i<res.result.length;i++){
                  if(this.pieValue === 'b'){
                    let mon = res.result[i].elecDate.split("-")[0]+"-"+parseInt(res.result[i].elecDate.split("-")[1])
                    let m = year+"-"+month
                    if(mon === m){
                      that.aelec += res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                                    res.result[i].ydll+res.result[i].ydyw+res.result[i].ydss+res.result[i].ydsw+
                                    res.result[i].edll+res.result[i].edyw+res.result[i].edss+res.result[i].edsw+
                                    res.result[i].sdll+res.result[i].sdyw+res.result[i].sdss+res.result[i].sssw+
                                    res.result[i].sidll+res.result[i].sidyw+res.result[i].sidss+res.result[i].sidsw+
                                    res.result[i].wdll+res.result[i].wdyw+res.result[i].wdss+res.result[i].wdsw+
                                    res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                                    res.result[i].qdll+res.result[i].qdyw+res.result[i].qdss+res.result[i].qdsw+
                                    res.result[i].bdll+res.result[i].bdyw+res.result[i].bdss+res.result[i].bdsw+
                                    res.result[i].jdll+res.result[i].jdyw+res.result[i].jdss+res.result[i].jdsw+
                                    res.result[i].shidll+res.result[i].shidyw+res.result[i].shidss+res.result[i].shidsw+
                                    res.result[i].sydll+res.result[i].sydyw+res.result[i].sydss+res.result[i].sydsw+
                                    res.result[i].sedll+res.result[i].sedyw+res.result[i].sedss+res.result[i].sedsw+
                                    res.result[i].ssdll+res.result[i].ssdyw+res.result[i].ssdss+res.result[i].ssdsw+
                                    res.result[i].ssidll+res.result[i].ssidyw+res.result[i].ssidss+res.result[i].ssidsw+
                                    res.result[i].swdll+res.result[i].swdyw+res.result[i].swdss+res.result[i].swdsw+
                                    res.result[i].sldll+res.result[i].sldyw+res.result[i].sldss+res.result[i].sldsw+
                                    res.result[i].sqdll+res.result[i].sqdyw+res.result[i].sqdss+res.result[i].sqdsw+
                                    res.result[i].sbdll+res.result[i].sbdyw+res.result[i].sbdss+res.result[i].sbdsw+
                                    res.result[i].sjdll+res.result[i].sjdyw+res.result[i].sjdss+res.result[i].sjdsw+
                                    res.result[i].esdll+res.result[i].esdyw+res.result[i].esdss+res.result[i].esdsw+
                                    res.result[i].eydll+res.result[i].eydyw+res.result[i].eydss+res.result[i].eydsw+
                                    res.result[i].eedll+res.result[i].eedyw+res.result[i].eedss+res.result[i].eedsw+
                                    res.result[i].essdll+res.result[i].essdyw+res.result[i].essdss+res.result[i].essdsw
                    }
                  }else{
                    if(res.result[i].elecDate.split("-")[0] === year){
                      that.aelec += res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                        res.result[i].ydll+res.result[i].ydyw+res.result[i].ydss+res.result[i].ydsw+
                        res.result[i].edll+res.result[i].edyw+res.result[i].edss+res.result[i].edsw+
                        res.result[i].sdll+res.result[i].sdyw+res.result[i].sdss+res.result[i].sssw+
                        res.result[i].sidll+res.result[i].sidyw+res.result[i].sidss+res.result[i].sidsw+
                        res.result[i].wdll+res.result[i].wdyw+res.result[i].wdss+res.result[i].wdsw+
                        res.result[i].ldll+res.result[i].ldyw+res.result[i].ldss+res.result[i].ldsw+
                        res.result[i].qdll+res.result[i].qdyw+res.result[i].qdss+res.result[i].qdsw+
                        res.result[i].bdll+res.result[i].bdyw+res.result[i].bdss+res.result[i].bdsw+
                        res.result[i].jdll+res.result[i].jdyw+res.result[i].jdss+res.result[i].jdsw+
                        res.result[i].shidll+res.result[i].shidyw+res.result[i].shidss+res.result[i].shidsw+
                        res.result[i].sydll+res.result[i].sydyw+res.result[i].sydss+res.result[i].sydsw+
                        res.result[i].sedll+res.result[i].sedyw+res.result[i].sedss+res.result[i].sedsw+
                        res.result[i].ssdll+res.result[i].ssdyw+res.result[i].ssdss+res.result[i].ssdsw+
                        res.result[i].ssidll+res.result[i].ssidyw+res.result[i].ssidss+res.result[i].ssidsw+
                        res.result[i].swdll+res.result[i].swdyw+res.result[i].swdss+res.result[i].swdsw+
                        res.result[i].sldll+res.result[i].sldyw+res.result[i].sldss+res.result[i].sldsw+
                        res.result[i].sqdll+res.result[i].sqdyw+res.result[i].sqdss+res.result[i].sqdsw+
                        res.result[i].sbdll+res.result[i].sbdyw+res.result[i].sbdss+res.result[i].sbdsw+
                        res.result[i].sjdll+res.result[i].sjdyw+res.result[i].sjdss+res.result[i].sjdsw+
                        res.result[i].esdll+res.result[i].esdyw+res.result[i].esdss+res.result[i].esdsw+
                        res.result[i].eydll+res.result[i].eydyw+res.result[i].eydss+res.result[i].eydsw+
                        res.result[i].eedll+res.result[i].eedyw+res.result[i].eedss+res.result[i].eedsw+
                        res.result[i].essdll+res.result[i].essdyw+res.result[i].essdss+res.result[i].essdsw
                      this.useElec = that.aelec
                    }
                  }

                }
                for(let m=0;m<2;m++){
                  this.pieData.push({})
                }
                this.pieData[0]['item'] = '使用电量'
                this.pieData[0]['count'] = parseFloat((that.aelec/that.alelec).toFixed(2));
                this.pieData[1]['item'] = '剩余电量'
                this.pieData[1]['count'] = parseFloat(((that.alelec-that.aelec)/that.alelec).toFixed(2));
              }
            })
          }
        })
      },
      //电量同比环比
      lineCompareChange(e){
        this.lineCompare = e.target.value;
        this.loadElecLine();
      },
      //电量 年月
      lineTimeChange(e){
        this.lineTime = e.target.value;
        this.loadElecLine();
      },
      //加载合同信息
      loadContract(){
        this.contractTotal = 0
        getAction('/userpurchasecontractb/userPurchaseContractB/list',{ cusname:this.username }).then((res) => {
          if(res.success){
            this.dataSource = res.result.records;
            // this.ipagination.total = res.result.total;
            this.contractNum = res.result.records.length;
            for(let i=0;i<res.result.records.length;i++){
              this.contractTotal += parseFloat(res.result.records[i].purchaseElectricity);
            }
            //console.log(this.contractTotal)
          }
        })
      },
      //加载电量折线图
      loadElecLine(){
        this.elecLine = [];
        this.barData = [];
        var aaa = [];
        let date = new Date();
        let year = date.getFullYear()
        let monthNow = date.getMonth()+1
        let monthYes = date.getMonth()
        //横坐标
        if(this.lineTime === 'a'){
          for(let i=0;i<12;i++){
            // this.elecLine.push({})
            // this.elecLine[i]['type'] = i+1+'月'
            aaa.push({})
            aaa[i]['type'] = i+1+'月'
          }
        }else if(this.lineTime === 'b'){
          for(let i=0;i<2;i++){
            // this.elecLine.push({})
            // this.elecLine[i]['type'] = i+1+'月'
            aaa.push({})
          }
          if(this.lineCompare === 'a'){
            aaa[0]['type'] = '本月'
            aaa[1]['type'] = '去年本月'
          }else{
            aaa[0]['type'] = '本月'
            aaa[1]['type'] = '上月'
          }

        }
        //查询申报电量
        getAction('/monthelec/momthElec/queryall2',{ companyName:this.username }).then((res) => {
          if(res.success){
            // console.log(res)
            let n = [];
            let nYes = [];
            //查询今年的最新数据和去年的最新数据
            for(let i=0;i<res.result.length;i++){
              if(res.result[i].createTime.split('-')[0] === year.toString()){
                if(res.result[i].updateNum.toString().substring(0,1) === '3'){
                  n = res.result[i];
                }
              }else if(res.result[i].createTime.split('-')[0] === (year-1).toString()){
                if(res.result[i].updateNum.toString().substring(0,1) === '3'){
                  nYes = res.result[i];
                }
              }

            }
            let len = res.result[0].updateNum.toString().length;
            if(this.lineTime === 'a'){
              //年 申报电量
              // for(let i=0;i<aaa.length;i++){
              //   // console.log(this.elecLine[i]['type'])
              //   let e = aaa[i]['type'].substring(0,aaa[i]['type'].length-1)
              //   if(e >= len.toString() ){
              //     if(e === '1'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].yi)
              //     }else if(e === '2'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].er)
              //     }else if(e === '3'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].san)
              //     }else if(e === '4'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].si)
              //     }else if(e === '5'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].wu)
              //     }else if(e === '6'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].liu)
              //     }else if(e === '7'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].qi)
              //     }else if(e === '8'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].ba)
              //     }else if(e === '9'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].jiu)
              //     }else if(e === '10'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].shi)
              //     }else if(e === '11'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].shiyi)
              //     }else if(e === '12'){
              //       aaa[i]['申报电量'] = parseFloat(res.result[0].shier)
              //     }
              //   }else{
              //     if(e === '1'){
              //       aaa[i]['申报电量'] = parseFloat(n.yi)
              //     }else if(e === '2'){
              //       aaa[i]['申报电量'] = parseFloat(n.er)
              //     }else if(e === '3'){
              //       aaa[i]['申报电量'] = parseFloat(n.san)
              //     }else if(e === '4'){
              //       aaa[i]['申报电量'] = parseFloat(n.si)
              //     }else if(e === '5'){
              //       aaa[i]['申报电量'] = parseFloat(n.wu)
              //     }else if(e === '6'){
              //       aaa[i]['申报电量'] = parseFloat(n.liu)
              //     }else if(e === '7'){
              //       aaa[i]['申报电量'] = parseFloat(n.qi)
              //     }else if(e === '8'){
              //       aaa[i]['申报电量'] = parseFloat(n.ba)
              //     }else if(e === '9'){
              //       aaa[i]['申报电量'] = parseFloat(n.jiu)
              //     }else if(e === '10'){
              //       aaa[i]['申报电量'] = parseFloat(n.shi)
              //     }else if(e === '11'){
              //       aaa[i]['申报电量'] = parseFloat(n.shiyi)
              //     }else if(e === '12'){
              //       aaa[i]['申报电量'] = parseFloat(n.shier)
              //     }
              //   }
              // }
              //年 使用电量
              getAction('/hiselec/hiselec/queryall',{ username:this.username }).then((res) => {
                if(res.success){
                  // console.log(res.result)
                  let data1 = []
                  let data2 = []
                  //查询本年数据
                  for(let i=0;i<res.result.length;i++){
                    if(res.result[i].elecDate.split("-")[0] === year.toString()){
                      data1.push(res.result[i])
                    }
                  }
                  //查询去年数据
                  for(let i=0;i<res.result.length;i++){
                    if(res.result[i].elecDate.split("-")[0] === (year-1).toString()){
                      data2.push(res.result[i])
                    }
                  }
                  for(let i=0;i<aaa.length;i++){
                    let e = aaa[i]['type'].substring(0,aaa[i]['type'].length-1)
                    //本年数据
                    for(let j=0;j<data1.length;j++){
                      if(parseInt(e) === parseInt(data1[j].elecDate.split("-")[1])){
                        if(aaa[i]['使用电量'] === undefined){
                          aaa[i]['使用电量'] = 0;
                        }
                        aaa[i]['使用电量'] += parseInt(data1[j].ldll+data1[j].ldyw+data1[j].ldss+data1[j].ldsw+
                          data1[j].ydll+data1[j].ydyw+data1[j].ydss+data1[j].ydsw+
                          data1[j].edll+data1[j].edyw+data1[j].edss+data1[j].edsw+
                          data1[j].sdll+data1[j].sdyw+data1[j].sdss+data1[j].sssw+
                          data1[j].sidll+data1[j].sidyw+data1[j].sidss+data1[j].sidsw+
                          data1[j].wdll+data1[j].wdyw+data1[j].wdss+data1[j].wdsw+
                          data1[j].ldll+data1[j].ldyw+data1[j].ldss+data1[j].ldsw+
                          data1[j].qdll+data1[j].qdyw+data1[j].qdss+data1[j].qdsw+
                          data1[j].bdll+data1[j].bdyw+data1[j].bdss+data1[j].bdsw+
                          data1[j].jdll+data1[j].jdyw+data1[j].jdss+data1[j].jdsw+
                          data1[j].shidll+data1[j].shidyw+data1[j].shidss+data1[j].shidsw+
                          data1[j].sydll+data1[j].sydyw+data1[j].sydss+data1[j].sydsw+
                          data1[j].sedll+data1[j].sedyw+data1[j].sedss+data1[j].sedsw+
                          data1[j].ssdll+data1[j].ssdyw+data1[j].ssdss+data1[j].ssdsw+
                          data1[j].ssidll+data1[j].ssidyw+data1[j].ssidss+data1[j].ssidsw+
                          data1[j].swdll+data1[j].swdyw+data1[j].swdss+data1[j].swdsw+
                          data1[j].sldll+data1[j].sldyw+data1[j].sldss+data1[j].sldsw+
                          data1[j].sqdll+data1[j].sqdyw+data1[j].sqdss+data1[j].sqdsw+
                          data1[j].sbdll+data1[j].sbdyw+data1[j].sbdss+data1[j].sbdsw+
                          data1[j].sjdll+data1[j].sjdyw+data1[j].sjdss+data1[j].sjdsw+
                          data1[j].esdll+data1[j].esdyw+data1[j].esdss+data1[j].esdsw+
                          data1[j].eydll+data1[j].eydyw+data1[j].eydss+data1[j].eydsw+
                          data1[j].eedll+data1[j].eedyw+data1[j].eedss+data1[j].eedsw+
                          data1[j].essdll+data1[j].essdyw+data1[j].essdss+data1[j].essdsw)
                        // aaa[i]['偏差电量'] = aaa[i]['申报电量'] - aaa[i]['使用电量']
                      }
                    }
                    //去年数据
                    if(this.lineCompare === 'a'){
                      for(let j=0;j<data2.length;j++){
                        if(parseInt(e) === parseInt(data2[j].elecDate.split("-")[1])){
                          if(aaa[i]['去年电量'] === undefined){
                            aaa[i]['去年电量'] = 0;
                          }
                          aaa[i]['去年电量'] += parseInt(data2[j].ldll+data2[j].ldyw+data2[j].ldss+data2[j].ldsw+
                            data2[j].ydll+data2[j].ydyw+data2[j].ydss+data2[j].ydsw+
                            data2[j].edll+data2[j].edyw+data2[j].edss+data2[j].edsw+
                            data2[j].sdll+data2[j].sdyw+data2[j].sdss+data2[j].sssw+
                            data2[j].sidll+data2[j].sidyw+data2[j].sidss+data2[j].sidsw+
                            data2[j].wdll+data2[j].wdyw+data2[j].wdss+data2[j].wdsw+
                            data2[j].ldll+data2[j].ldyw+data2[j].ldss+data2[j].ldsw+
                            data2[j].qdll+data2[j].qdyw+data2[j].qdss+data2[j].qdsw+
                            data2[j].bdll+data2[j].bdyw+data2[j].bdss+data2[j].bdsw+
                            data2[j].jdll+data2[j].jdyw+data2[j].jdss+data2[j].jdsw+
                            data2[j].shidll+data2[j].shidyw+data2[j].shidss+data2[j].shidsw+
                            data2[j].sydll+data2[j].sydyw+data2[j].sydss+data2[j].sydsw+
                            data2[j].sedll+data2[j].sedyw+data2[j].sedss+data2[j].sedsw+
                            data2[j].ssdll+data2[j].ssdyw+data2[j].ssdss+data2[j].ssdsw+
                            data2[j].ssidll+data2[j].ssidyw+data2[j].ssidss+data2[j].ssidsw+
                            data2[j].swdll+data2[j].swdyw+data2[j].swdss+data2[j].swdsw+
                            data2[j].sldll+data2[j].sldyw+data2[j].sldss+data2[j].sldsw+
                            data2[j].sqdll+data2[j].sqdyw+data2[j].sqdss+data2[j].sqdsw+
                            data2[j].sbdll+data2[j].sbdyw+data2[j].sbdss+data2[j].sbdsw+
                            data2[j].sjdll+data2[j].sjdyw+data2[j].sjdss+data2[j].sjdsw+
                            data2[j].esdll+data2[j].esdyw+data2[j].esdss+data2[j].esdsw+
                            data2[j].eydll+data2[j].eydyw+data2[j].eydss+data2[j].eydsw+
                            data2[j].eedll+data2[j].eedyw+data2[j].eedss+data2[j].eedsw+
                            data2[j].essdll+data2[j].essdyw+data2[j].essdss+data2[j].essdsw)
                        }
                      }
                    }
                  }
                  this.elecLine = aaa
                  console.log(this.elecLine)
                }
              })
            }else if(this.lineTime === 'b'){
              //月 申报电量
              if(monthNow === 1){
                aaa[0]['申报电量'] = parseInt(res.result[0].yi)
                if(this.lineCompare === 'a'){
                  if(nYes.yi === undefined){
                    nYes.yi = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.yi)
                }
              }else if(monthNow === 2){
                aaa[0]['申报电量'] = parseInt(res.result[0].er)
                if(this.lineCompare === 'a'){
                  if(nYes.er === undefined){
                    nYes.er = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.er)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].yi)
                }
              }else if(monthNow === 3){
                aaa[0]['申报电量'] = parseInt(res.result[0].san)
                if(this.lineCompare === 'a'){
                  if(nYes.san === undefined){
                    nYes.san = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.san)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].er)
                }
              }else if(monthNow === 4){
                aaa[0]['申报电量'] = parseInt(res.result[0].si)
                if(this.lineCompare === 'a'){
                  if(nYes.si === undefined){
                    nYes.si = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.si)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].san)
                }
              }else if(monthNow === 5){
                aaa[0]['申报电量'] = parseInt(res.result[0].wu)
                if(this.lineCompare === 'a'){
                  if(nYes.wu === undefined){
                    nYes.wu = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.wu)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].si)
                }
              }else if(monthNow === 6){
                aaa[0]['申报电量'] = parseInt(res.result[0].liu)
                if(this.lineCompare === 'a'){
                  if(nYes.liu === undefined){
                    nYes.liu = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.liu)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].wu)
                }
              }else if(monthNow === 7){
                aaa[0]['申报电量'] = parseInt(res.result[0].qi)
                if(this.lineCompare === 'a'){
                  if(nYes.qi === undefined){
                    nYes.qi = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.qi)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].liu)
                }

              }else if(monthNow === 8){
                aaa[0]['申报电量'] = parseInt(res.result[0].ba)
                if(this.lineCompare === 'a'){
                  if(nYes.ba === undefined){
                    nYes.ba = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.ba)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].qi)
                }
              }else if(monthNow === 9){
                aaa[0]['申报电量'] = parseInt(res.result[0].jiu)
                if(this.lineCompare === 'a'){
                  if(nYes.jiu === undefined){
                    nYes.jiu = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.jiu)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].ba)
                }
              }else if(monthNow === 10){
                aaa[0]['申报电量'] = parseInt(res.result[0].shi)
                if(this.lineCompare === 'a'){
                  if(nYes.shi === undefined){
                    nYes.shi = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.shi)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].jiu)
                }
              }else if(monthNow === 11){
                aaa[0]['申报电量'] = parseInt(res.result[0].shiyi)
                if(this.lineCompare === 'a'){
                  if(nYes.shiyi === undefined){
                    nYes.shiyi = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.shiyi)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].shi)
                }
              }else if(monthNow === 12){
                aaa[0]['申报电量'] = parseInt(res.result[0].shier)
                if(this.lineCompare === 'a'){
                  if(nYes.shier === undefined){
                    nYes.shier = '0'
                  }
                  aaa[1]['申报电量'] = parseInt(nYes.shier)
                }else{
                  aaa[1]['申报电量'] = parseInt(res.result[0].shiyi)
                }

              }
              //月 使用电量
              getAction('/hiselec/hiselec/queryall',{ username:this.username }).then((res) => {
                if(res.success){
                  // console.log(res.result)
                  let data1 = []
                  let data2 = []
                  if(monthNow<=9){
                    monthNow = '0'+ monthNow
                  }
                  if(monthYes<=9){
                    monthYes = '0'+ monthYes
                  }
                  //查询本年数据
                  for(let i=0;i<res.result.length;i++){
                    if(res.result[i].elecDate.substring(0,7) === year+'-'+monthNow){
                      data1.push(res.result[i])
                    }
                  }
                  //查询上月数据
                  if(this.lineCompare === 'b'){
                    for(let i=0;i<res.result.length;i++){
                      if(res.result[i].elecDate.substring(0,7) === year+'-'+monthYes){
                        data2.push(res.result[i])
                      }
                    }
                  }
                  //查询去年数据
                  if(this.lineCompare === 'a'){
                    for(let i=0;i<res.result.length;i++){
                      if(res.result[i].elecDate.substring(0,7) === (year-1)+'-'+monthNow){
                        data2.push(res.result[i])
                      }
                    }
                  }
                  //本年数据
                  for(let j=0;j<data1.length;j++){
                      if(aaa[0]['使用电量'] === undefined){
                        aaa[0]['使用电量'] = 0;
                      }
                      aaa[0]['使用电量'] += parseInt(data1[j].ldll+data1[j].ldyw+data1[j].ldss+data1[j].ldsw+
                        data1[j].ydll+data1[j].ydyw+data1[j].ydss+data1[j].ydsw+
                        data1[j].edll+data1[j].edyw+data1[j].edss+data1[j].edsw+
                        data1[j].sdll+data1[j].sdyw+data1[j].sdss+data1[j].sssw+
                        data1[j].sidll+data1[j].sidyw+data1[j].sidss+data1[j].sidsw+
                        data1[j].wdll+data1[j].wdyw+data1[j].wdss+data1[j].wdsw+
                        data1[j].ldll+data1[j].ldyw+data1[j].ldss+data1[j].ldsw+
                        data1[j].qdll+data1[j].qdyw+data1[j].qdss+data1[j].qdsw+
                        data1[j].bdll+data1[j].bdyw+data1[j].bdss+data1[j].bdsw+
                        data1[j].jdll+data1[j].jdyw+data1[j].jdss+data1[j].jdsw+
                        data1[j].shidll+data1[j].shidyw+data1[j].shidss+data1[j].shidsw+
                        data1[j].sydll+data1[j].sydyw+data1[j].sydss+data1[j].sydsw+
                        data1[j].sedll+data1[j].sedyw+data1[j].sedss+data1[j].sedsw+
                        data1[j].ssdll+data1[j].ssdyw+data1[j].ssdss+data1[j].ssdsw+
                        data1[j].ssidll+data1[j].ssidyw+data1[j].ssidss+data1[j].ssidsw+
                        data1[j].swdll+data1[j].swdyw+data1[j].swdss+data1[j].swdsw+
                        data1[j].sldll+data1[j].sldyw+data1[j].sldss+data1[j].sldsw+
                        data1[j].sqdll+data1[j].sqdyw+data1[j].sqdss+data1[j].sqdsw+
                        data1[j].sbdll+data1[j].sbdyw+data1[j].sbdss+data1[j].sbdsw+
                        data1[j].sjdll+data1[j].sjdyw+data1[j].sjdss+data1[j].sjdsw+
                        data1[j].esdll+data1[j].esdyw+data1[j].esdss+data1[j].esdsw+
                        data1[j].eydll+data1[j].eydyw+data1[j].eydss+data1[j].eydsw+
                        data1[j].eedll+data1[j].eedyw+data1[j].eedss+data1[j].eedsw+
                        data1[j].essdll+data1[j].essdyw+data1[j].essdss+data1[j].essdsw)
                      aaa[0]['偏差电量'] = aaa[0]['申报电量'] - aaa[0]['使用电量']
                  }
                  //去年数据
                  for(let j=0;j<data2.length;j++){
                        if(aaa[1]['使用电量'] === undefined){
                          aaa[1]['使用电量'] = 0;
                        }
                        aaa[1]['使用电量'] += parseInt(data2[j].ldll+data2[j].ldyw+data2[j].ldss+data2[j].ldsw+
                          data2[j].ydll+data2[j].ydyw+data2[j].ydss+data2[j].ydsw+
                          data2[j].edll+data2[j].edyw+data2[j].edss+data2[j].edsw+
                          data2[j].sdll+data2[j].sdyw+data2[j].sdss+data2[j].sssw+
                          data2[j].sidll+data2[j].sidyw+data2[j].sidss+data2[j].sidsw+
                          data2[j].wdll+data2[j].wdyw+data2[j].wdss+data2[j].wdsw+
                          data2[j].ldll+data2[j].ldyw+data2[j].ldss+data2[j].ldsw+
                          data2[j].qdll+data2[j].qdyw+data2[j].qdss+data2[j].qdsw+
                          data2[j].bdll+data2[j].bdyw+data2[j].bdss+data2[j].bdsw+
                          data2[j].jdll+data2[j].jdyw+data2[j].jdss+data2[j].jdsw+
                          data2[j].shidll+data2[j].shidyw+data2[j].shidss+data2[j].shidsw+
                          data2[j].sydll+data2[j].sydyw+data2[j].sydss+data2[j].sydsw+
                          data2[j].sedll+data2[j].sedyw+data2[j].sedss+data2[j].sedsw+
                          data2[j].ssdll+data2[j].ssdyw+data2[j].ssdss+data2[j].ssdsw+
                          data2[j].ssidll+data2[j].ssidyw+data2[j].ssidss+data2[j].ssidsw+
                          data2[j].swdll+data2[j].swdyw+data2[j].swdss+data2[j].swdsw+
                          data2[j].sldll+data2[j].sldyw+data2[j].sldss+data2[j].sldsw+
                          data2[j].sqdll+data2[j].sqdyw+data2[j].sqdss+data2[j].sqdsw+
                          data2[j].sbdll+data2[j].sbdyw+data2[j].sbdss+data2[j].sbdsw+
                          data2[j].sjdll+data2[j].sjdyw+data2[j].sjdss+data2[j].sjdsw+
                          data2[j].esdll+data2[j].esdyw+data2[j].esdss+data2[j].esdsw+
                          data2[j].eydll+data2[j].eydyw+data2[j].eydss+data2[j].eydsw+
                          data2[j].eedll+data2[j].eedyw+data2[j].eedss+data2[j].eedsw+
                          data2[j].essdll+data2[j].essdyw+data2[j].essdss+data2[j].essdsw)
                    aaa[1]['偏差电量'] = aaa[1]['申报电量'] - aaa[1]['使用电量']
                    }
                  this.barData = aaa
                  console.log(this.barData)
                }
              })

            }

          }
        })
      },
      //交易信息
      loadTrade(){
        let date = new Date();
        let y = date.getFullYear();
        //中间变量
        let aaa = []
        let contractAve = 0
        this.contractElec = 0;
        getAction(this.url.list,{ cusname:this.username }).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.records.length;i++){
              if(res.result.records[i].beginDate.split('-')[0] === y.toString()){
                aaa.push(res.result.records[i]);
                // this.ipagination.total = aaa.length;
              }
            }
            //计算合同均价
            //购电合同
            getAction('/sellpowerpurchasecontractb/sellpowerPurchaseContractB/queryall',{ year:y }).then((res) => {
              if(res.success){
                for(let i=0;i<res.result.length;i++){
                  // 年合同总电量  (合同总电量+交易电量)
                  this.contractElec += parseInt(res.result[i].purchaseElectricity)
                  // 合同均价
                  contractAve += parseInt(res.result[i].purchaseElectricity)*res.result[i].purchasePrice
                }
                // 我的交易
                getAction('/mytrade/myTrade/queryall',{ year:y }).then((res) => {
                  // console.log(res)
                  if(res.success){
                    for(let i=0;i<res.result.length;i++){
                      // 总电量 减去转出电量
                      if(res.result[i].zrType === '2'){
                        this.contractElec -= parseInt(res.result[i].tradeElec)
                        contractAve -= res.result[i].tradeElec*res.result[i].tradePrice
                      }else{
                        this.contractElec += parseInt(res.result[i].tradeElec)
                        contractAve += res.result[i].tradeElec*res.result[i].tradePrice
                      }
                    }
                    this.contractAverage = contractAve/this.contractElec
                    for(let i=0;i<aaa.length;i++){
                      aaa[i]['b'] = this.contractAverage.toFixed(2)
                      aaa[i]['c'] = aaa[i]['purchaseElectricity']*(aaa[i]['purchasePrice'] - aaa[i]['b'])
                      aaa[i]['d'] = this.useElec.toFixed(2)
                      aaa[i]['e'] = (aaa[i]['d']*(aaa[i]['purchasePrice'] - aaa[i]['b'])).toFixed(2)
                    }
                    this.dataSource = aaa

                  }
                })
              }
            })

          }
        })
      },
      //分公司信息
      loadZiComs(){
        this.isShowZiComs = false
        getAction('/userinfob/userInfoB/listZi',{username:this.username}).then((res)=> {
          console.log(res)
          if(res.success){
            if(res.result.records.length>0){
              this.isShowZiComs = true;
              this.dataSource3 = res.result.records
            }else{
              this.isShowZiComs = false
            }
          }
        })
      }

    },
  }
</script>

<style scoped>

</style>