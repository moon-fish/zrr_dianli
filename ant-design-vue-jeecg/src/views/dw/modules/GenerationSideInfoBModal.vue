<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-tabs defaultActiveKey="1" @change="callback">
          <a-tab-pane tab="基本信息" key="1">
            <a-form-item label="电站名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'stationName', validatorRules.stationName]" placeholder="请输入电站名称"></a-input>
            </a-form-item>
            <a-form-item label="装机容量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'installedCapacity', validatorRules.installedCapacity]" placeholder="请输入装机容量"></a-input>
            </a-form-item>
            <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'address', validatorRules.address]" placeholder="请输入地址"></a-input>
            </a-form-item>
            <!--<a-form-item label="离市区距离" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
            <!--<a-input-number v-decorator="[ 'distance', validatorRules.distance]" placeholder="请输入离市区距离" style="width: 100%"/>-->
            <!--</a-form-item>-->
            <a-form-item label="联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入联系人姓名"></a-input>
            </a-form-item>
            <a-form-item label="联系人职务" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'duty', validatorRules.duty]" placeholder="请输入联系人职务"></a-input>
            </a-form-item>
            <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'phone', validatorRules.phone]" placeholder="请输入手机号码"></a-input>
            </a-form-item>
            <a-form-item label="办公电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'officePhone', validatorRules.officePhone]" placeholder="请输入办公电话"></a-input>
            </a-form-item>
            <!--<a-form-item label="总装机容量" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
              <!--<a-input v-decorator="[ 'installedCapacityAll', validatorRules.installedCapacityAll]" placeholder="请输入总装机容量"></a-input>-->
            <!--</a-form-item>-->
            <a-form-item label="机组类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'unitType', validatorRules.unitType]" placeholder="请输入机组类型"></a-input>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane tab="合同信息" key="2" v-if="this.isShow">
            <!--<sellpower-purchase-contract-b-list></sellpower-purchase-contract-b-list>-->
            <a-card :bordered="false">
              <!-- 查询区域 -->
              <div class="table-page-search-wrapper">
                <a-form layout="inline" @keyup.enter.native="searchQuery">
                  <a-row :gutter="24">
                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
                      <a-form-item label="合同名称">
                        <a-input placeholder="请输入合同名称" v-model="queryParam.contractName"></a-input>
                      </a-form-item>
                    </a-col>
                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
                      <a-form-item label="电力用户名称">
                        <a-input placeholder="请输入电力用户名称" v-model="queryParam.userName"></a-input>
                      </a-form-item>
                    </a-col>
                    <template v-if="toggleSearchStatus">
                      <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="交易类型">
                          <a-input placeholder="请输入交易类型" v-model="queryParam.transactionType"></a-input>
                        </a-form-item>
                      </a-col>
                      <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="开始时间">
                          <a-input placeholder="请输入开始时间" v-model="queryParam.beginDate"></a-input>
                        </a-form-item>
                      </a-col>
                      <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="结束时间">
                          <a-input placeholder="请输入结束时间" v-model="queryParam.endDate"></a-input>
                        </a-form-item>
                      </a-col>
                      <a-col :xl="6" :lg="7" :md="8" :sm="24">
                        <a-form-item label="购电量">
                          <a-input placeholder="请输入购电量" v-model="queryParam.purchaseElectricity"></a-input>
                        </a-form-item>
                      </a-col>
                    </template>
                    <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a @click="handleToggleSearch" style="margin-left: 8px;margin-right: 20px;">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>

            </span>
                    </a-col>
                  </a-row>
                </a-form>
              </div>
              <!-- 查询区域-END -->

              <!-- 操作按钮区域 -->
              <div class="table-operator">
                <a-button @click="handleAddCon" type="primary" icon="plus" v-has="'admin:add'">新增</a-button>
                <a-button type="primary" icon="download" @click="handleExportXls('售电公司购电合同')">导出</a-button>
                <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
                  <a-button type="primary" icon="import">导入</a-button>
                </a-upload>
                <a-dropdown v-if="selectedRowKeys.length > 0" v-has="'admin:delete'">
                  <a-menu slot="overlay">
                    <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
                  </a-menu>
                  <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
                </a-dropdown>
              </div>

              <!-- table区域-begin -->
              <div>
                <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
                  <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
                  <a style="margin-left: 24px" @click="onClearSelected">清空</a>
                </div>

                <a-table
                  ref="table"
                  size="middle"
                  bordered
                  rowKey="id"
                  :columns="columns"
                  :dataSource="this.dataSource1"
                  :pagination="ipagination"
                  :loading="loading"
                  :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

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
                    <a @click="handleEdit(record)" v-has="'admin:edit'">编辑</a>

                    <a-divider type="vertical" />
                    <a-dropdown v-has="'admin:delete'">
                      <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
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

              <sellpowerPurchaseContractB-modal ref="modalForm" @ok="modalFormOk" :cusname1="this.cusName"></sellpowerPurchaseContractB-modal>
            </a-card>
          </a-tab-pane>
        </a-tabs>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  // import SellpowerPurchaseContractBList from '../SellpowerPurchaseContractBList'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SellpowerPurchaseContractBModal from '../modules/SellpowerPurchaseContractBModal'

  export default {
    name: "GenerationSideInfoBModal",
    mixins:[JeecgListMixin],
    components: {
      // SellpowerPurchaseContractBList
      SellpowerPurchaseContractBModal
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width: 1200,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          stationName: {rules: [
          ]},
          installedCapacity: {rules: [
          ]},
          address: {rules: [
          ]},
          distance: {rules: [
          ]},
          name: {rules: [
          ]},
          duty: {rules: [
          ]},
          phone: {rules: [
          ]},
          officePhone: {rules: [
          ]},
          installedCapacityAll: {rules: [
          ]},
          unitType: {rules: [
          ]},
        },
        url2: {
          add: "/generationsideInfob/generationSideInfoB/add",
          edit: "/generationsideInfob/generationSideInfoB/edit",
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
          {
            title:'交易类型',
            align:"center",
            dataIndex: 'transactionType'
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束时间',
            align:"center",
            dataIndex: 'endDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'购电量',
            align:"center",
            dataIndex: 'purchaseElectricity'
          },
          {
            title:'购电价',
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/list",
          delete: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/delete",
          deleteBatch: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/deleteBatch",
          exportXlsUrl: "/sellpowerpurchasecontractb/sellpowerPurchaseContractB/exportXls",
          importExcelUrl: "sellpowerpurchasecontractb/sellpowerPurchaseContractB/importExcel",
        },
        dictOptions:{},
        dataSource1: [],
        isShow: false,
        cusName: '',
      }
    },
    created () {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      add () {
        this.edit({});
      },
      edit (record) {
        console.log(record)
        //加载合同信息
        if(record.id !== undefined){
          this.isShow = true
          this.cusName = record.stationName
          this.initContract()
        }else{
          this.isShow = false
        }
        //赋值
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'stationName','installedCapacity','address','distance','name','duty','phone','officePhone','installedCapacityAll','unitType'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url2.add;
              method = 'post';
            }else{
              httpurl+=this.url2.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'stationName','installedCapacity','address','distance','name','duty','phone','officePhone','installedCapacityAll','unitType'))
      },
      callback(key) {
        console.log(key);
      },
      initContract(){
        this.dataSource1 = [];
        console.log(this.cusName)
        getAction(this.url.list,{ cusname: this.cusName }).then((res) => {
          console.log(res)
          if(res.success){
            this.dataSource1 = res.result.records
            this.ipagination.total = res.result.total;
          }
        })
      },
      //新增后刷新数据
      handleAddCon(){
        this.handleAdd();
        setTimeout(this.initContract(),3000)
      },
      
    }
  }
</script>