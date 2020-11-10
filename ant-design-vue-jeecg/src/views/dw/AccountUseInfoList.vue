<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="地市">
              <a-input placeholder="请输入地市" v-model="queryParam.addressShi"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="区县">
              <a-input placeholder="请输入区县" v-model="queryParam.addressXian"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="站址名称">
              <a-input placeholder="请输入站址名称" v-model="queryParam.addressDetail"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="状态">
              <!--<a-input placeholder="请输入状态" v-model="queryParam.accountStatus"></a-input>-->
              <j-dict-select-tag placeholder="请输入状态" v-model="queryParam.accountStatus" dictCode="accountStatus"/>
            </a-form-item>
          </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <!--<a-button type="primary" @click="searchQuery" icon="search">查询</a-button>-->
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="accountUseExportXls('存放对比后的数据')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
          <a-menu-item key="2" @click="batchAccess"><a-icon type="check"/>通过</a-menu-item>
          <a-menu-item key="3" @click="batchBack"><a-icon type="close"/>反驳</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
      <span style="margin-left: 300px">
          <a-select :value="month" style="width: 100px;margin-right: 20px;" @change="monthChange" >
            <a-select-option value="1">一月</a-select-option>
            <a-select-option value="2">二月</a-select-option>
            <a-select-option value="3">三月</a-select-option>
            <a-select-option value="4">四月</a-select-option>
            <a-select-option value="5">五月</a-select-option>
            <a-select-option value="6">六月</a-select-option>
            <a-select-option value="7">七月</a-select-option>
            <a-select-option value="8">八月</a-select-option>
            <a-select-option value="9">九月</a-select-option>
            <a-select-option value="10">十月</a-select-option>
            <a-select-option value="11">十一月</a-select-option>
            <a-select-option value="12">十二月</a-select-option>
          </a-select>
        <a-button @click="dataCheck" type="primary" icon="vertical-align-middle">数据对比</a-button>

        <a-popover title="自定义列" trigger="click" placement="leftBottom">
            <template slot="content">
              <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
                <a-row>
                  <template v-for="(item,index) in defColumns">
                    <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                        <a-col :span="12"><a-checkbox :value="item.dataIndex">{{ item.title }}</a-checkbox></a-col>
                    </template>
                  </template>
                </a-row>
              </a-checkbox-group>
            </template>
            <a style="float:right;"><a-icon type="setting" />自定义列</a>
          </a-popover>
      </span>
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
        :dataSource="dataSource1"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="accountStatus" slot-scope="text, record">
          <div v-if="text === '0'">
            <span style="color: #c6e7ff">禁用</span>
          </div>
          <div v-if="text === '1'">
            <span style="color: #ff1829">重复</span>
          </div>
          <div v-if="text === '2'">
            <span style="color: #ffa95e">新增</span>
          </div>
          <div v-if="text === '3'">
            <span style="color: #259cff">减少</span>
          </div>
        </span>
        <span slot="action" slot-scope="text, record">
          <!--<a @click="handleEdit(record)">编辑</a>-->

          <a @click="oneAccesss(record)">通过</a>
          <a-divider type="vertical" />
          <a @click="oneBack(record)">驳回</a>
          <!--<a-divider type="vertical" />-->
          <!--<a-dropdown>-->
            <!--<a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
            <!--<a-menu slot="overlay">-->
              <!--<a-menu-item>-->
                <!--<a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">-->
                  <!--<a>删除</a>-->
                <!--</a-popconfirm>-->
              <!--</a-menu-item>-->
            <!--</a-menu>-->
          <!--</a-dropdown>-->
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <accountUseInfo-modal ref="modalForm" @ok="modalFormOk"></accountUseInfo-modal>
  </a-card>
</template>

<script>
  import AccountUseInfoModal from './modules/AccountUseInfoModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction,putAction,downFile } from '@/api/manage'
  import JDictSelectTag from  '@/components/dict/JDictSelectTag'
  import Vue from 'vue'

  export default {
    name: "AccountUseInfoList",
    mixins:[JeecgListMixin],
    components: {
      AccountUseInfoModal,
      JDictSelectTag
    },
    data () {
      return {
        description: '存放对比后的数据管理页面',
        // 表头:列表展示的列，初始为空。
        columns:[],
        //列设置:保存勾选的列设置
        settingColumns:[],
        //列定义:定义列表可以展示的列信息
        defColumns: [
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
            title: '序号',
            align:"center",
            dataIndex: 'serNumber'
           },
		   {
            title: '地市',
            align:"center",
            dataIndex: 'addressShi'
           },
		   {
            title: '区县',
            align:"center",
            dataIndex: 'addressXian'
           },
		   {
            title: '站址名称',
            align:"center",
            width:80,
            dataIndex: 'addressDetail'
           },
		   {
            title: '站址编码',
            align:"center",
            dataIndex: 'addressCode'
           },
		   {
            title: '供电方式',
            align:"center",
            dataIndex: 'supplyType'
           },
		   {
            title: '户名',
            align:"center",
            width:120,
            dataIndex: 'accountName'
           },
		   {
            title: '户号',
            align:"center",
            dataIndex: 'accountNumber'
           },
		   {
            title: '计量点供电电压',
            align:"center",
            dataIndex: 'supplyVoltage'
           },
		   {
            title: '用电地址',
            align:"center",
            width:180,
            dataIndex: 'addressElecuse'
           },
		   {
            title: '供电单位',
            align:"center",
            dataIndex: 'supplyUnit'
           },
		   {
            title: '一月',
            align:"center",
            dataIndex: 'yi'
           },
		   {
            title: '二月',
            align:"center",
            dataIndex: 'er'
           },
		   {
            title: '三月',
            align:"center",
            dataIndex: 'san'
           },
		   {
            title: '四月',
            align:"center",
            dataIndex: 'si'
           },
		   {
            title: '五月',
            align:"center",
            dataIndex: 'wu'
           },
		   {
            title: '六月',
            align:"center",
            dataIndex: 'liu'
           },
		   {
            title: '七月',
            align:"center",
            dataIndex: 'qi'
           },
		   {
            title: '八月',
            align:"center",
            dataIndex: 'ba'
           },
		   {
            title: '九月',
            align:"center",
            dataIndex: 'jiu'
           },
		   {
            title: '十月',
            align:"center",
            dataIndex: 'shi'
           },
		   {
            title: '十一月',
            align:"center",
            dataIndex: 'shiyi'
           },
		   {
            title: '十二月',
            align:"center",
            dataIndex: 'shier'
           },
		   {
            title: '备注',
            align:"center",
            dataIndex: 'maker'
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'accountStatus',
            scopedSlots: { customRender: 'accountStatus' }
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width:80,
            scopedSlots: { customRender: 'action' },
          }
        ],
		    url: {
          list: "/accountuseinfo/accountUseInfo/list",
          delete: "/accountuseinfo/accountUseInfo/delete",
          deleteBatch: "/accountuseinfo/accountUseInfo/deleteBatch",
          exportXlsUrl: "accountuseinfo/accountUseInfo/exportXls",
          importExcelUrl: "accountinfo/accountInfo/importExcel",
       },
        month:(new Date().getMonth()+1).toString(),
        dataSource1:[],
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    mounted(){
      this.dataCheck();
      this.initColumns();
    },
    methods: {

      dataCheck(){
        console.log("数据对比")
        this.dataSource1 = [];
        var params = this.getQueryParams();//查询条件
        params.month = this.month
        console.log(this.month)
        console.log(params)
        //根据选择的月份提取相应的数据进行对比，如果是9月，则提取7.13-8.13的数据
        getAction('/accountuseinfo/accountUseInfo/list',params).then((res) => {
          console.log(res.result)
          if(res.success){
            this.dataSource1 = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })

      },
      //重写重置方法
      searchReset(){
        this.queryParam = {}
        getAction(this.url.list, {month:this.month}).then((res) => {
          if(res.success){
            this.dataSource1 = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      //月份选择发生变化
      monthChange(value){
        console.log(`selected ${value}`);
        this.month = value
        console.log(this.month)
      },
      //单条通过
      oneAccesss(record){
        console.log("sssssssssssssssssss")
        record.accountStatus = '2'
        this.model = Object.assign({}, record);
        putAction('/accountuseinfo/accountUseInfo/edit',this.model).then((res) => {
          console.log(res)
        })
      },
      //批量通过
      batchAccess(){
        console.log(this.selectionRows)
        if(this.selectionRows.length <= 0){
          this.$message.warning('请选择一条记录！');
        }else{
          for(let i=0;i<this.selectionRows.length;i++){
            this.selectionRows[i].accountStatus = '2'
          }
          var that = this;
          this.$confirm({
            title: "确认通过",
            content: "是否通过选中数据?",
            onOk: function () {
              that.loading = true;
              putAction('/accountuseinfo/accountUseInfo/batchUpdate',that.selectionRows).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });

        }
      },
      //单条驳回
      oneBack(record){
        console.log("sssssssssssssssssss")
        record.accountStatus = '0'
        this.model = Object.assign({}, record);
        putAction('/accountuseinfo/accountUseInfo/edit',this.model).then((res) => {
          console.log(res)
        })
      },
      //批量驳回
      batchBack(){
        console.log(this.selectionRows)
        if(this.selectionRows.length <= 0){
          this.$message.warning('请选择一条记录！');
        }else{
          for(let i=0;i<this.selectionRows.length;i++){
            this.selectionRows[i].accountStatus = '0'
          }
          console.log(this.selectionRows)
          let that = this;
          console.log(that.selectionRows)
          this.$confirm({
            title: "确认驳回",
            content: "是否驳回选中数据?",
            onOk: function () {
              that.loading = true;
              putAction('/accountuseinfo/accountUseInfo/batchUpdate',that.selectionRows).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.onClearSelected();
                } else {
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.loading = false;
              });
            }
          });

        }

      },
      //户号导出  导出可用的户号信息
      accountUseExportXls(fileName){
        console.log(this.month)
        if(!fileName || typeof fileName !== "string"){
          fileName = "导出文件"
        }
        // let param = {...this.queryParam};
        // console.log(this.selectedRowKeys.length)
        // if(this.selectedRowKeys && this.selectedRowKeys.length>0){
        //   param['selections'] = this.selectedRowKeys.join(",")
        // }
        let param = {};
        param.m = this.month;
        console.log("导出参数",param)
        console.log(this.url.exportXlsUrl)
        downFile(this.url.exportXlsUrl,param).then((data)=>{
          console.log("ooolkkkkkkkkkkkkkkk")
          if (!data) {
            this.$message.warning("文件下载失败")
            return
          }
          if (typeof window.navigator.msSaveBlob !== 'undefined') {
            window.navigator.msSaveBlob(new Blob([data],{type: 'application/vnd.ms-excel'}), fileName+'.xls')
          }else{
            console.log("vvvvvvvvvvvvvvv")
            let url = window.URL.createObjectURL(new Blob([data],{type: 'application/vnd.ms-excel'}))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', fileName+'.xls')
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link); //下载完成移除元素
            window.URL.revokeObjectURL(url); //释放掉blob对象
          }
        })
      },
      //列设置更改事件
      onColSettingsChange (checkedValues) {
        let key = this.$route.name+":colsettings";
        Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
        this.settingColumns = checkedValues;
        const cols = this.defColumns.filter(item => {
          if(item.key ==='rowIndex'|| item.dataIndex==='action'){
            return true
          }
          if(this.settingColumns.includes(item.dataIndex)) {
            return true
          }
          return false
        })
        this.columns =  cols;
      },
      initColumns(){
        //权限过滤（列权限控制时打开，修改第二个参数为授权码前缀）
        //this.defColumns = colAuthFilter(this.defColumns,'testdemo:');

        let key = this.$route.name+":colsettings";
        let colSettings= Vue.ls.get(key);
        if(colSettings==null||colSettings===undefined){
          let allSettingColumns = [];
          this.defColumns.forEach(function (item,i,array ) {
            allSettingColumns.push(item.dataIndex);
          })
          this.settingColumns = allSettingColumns;
          this.columns = this.defColumns;
        }else{
          this.settingColumns = colSettings;
          const cols = this.defColumns.filter(item => {
            if(item.key ==='rowIndex'|| item.dataIndex==='action'){
              return true;
            }
            if(colSettings.includes(item.dataIndex)) {
              return true;
            }
            return false;
          })
          this.columns =  cols;
        }
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>