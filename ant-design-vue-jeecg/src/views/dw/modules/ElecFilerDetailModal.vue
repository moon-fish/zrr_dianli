<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-card :bordered="false">
      用户账号:
      <a-input-search
        :style="{width:'150px',marginBottom:'15px'}"
        placeholder="请输入账号"
        v-model="queryParam.username"
        @search="searchQuery"
      ></a-input-search>
      <a-button @click="searchReset()" style="margin-left: 20px" icon="redo">重置</a-button>
      <!--用户列表-->
      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource1"
        :pagination="ipagination"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :loading="loading"
        @change="handleTableChange">
      </a-table>
    </a-card>
  </a-modal>
</template>

<script>
  import { getAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'ElecFilerDetailModal',
    mixins:[JeecgListMixin],
    data () {
      return {
        description: '电量申报管理页面',
        // 表头
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
            title:'申报企业名称',
            align:"center",
            dataIndex: 'filerCompanyName'
          },
          {
            title:'负荷预测',
            align:"center",
            dataIndex: 'loadForecasting'
          },
          {
            title:'交易类型',
            align:"center",
            dataIndex: 'transactionType'
          },
          {
            title:'市场报价',
            align:"center",
            dataIndex: 'marketQuotation'
          },
          {
            title:'申报开始时间',
            align:"center",
            dataIndex: 'beginDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'申报结束时间',
          //   align:"center",
          //   dataIndex: 'endDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'申报人',
            align:"center",
            dataIndex: 'filer'
          },
          // {
          //   title:'申报人所属公司',
          //   align:"center",
          //   dataIndex: 'filerCompany'
          // },
          {
            title:'申报结果',
            align:"center",
            dataIndex: 'filerResult'
          },
          // {
          //   title:'状态',
          //   align:"center",
          //   dataIndex: 'filerStatus'
          // },
          // {
          //   title:'日志记录',
          //   align:"center",
          //   dataIndex: 'logRecords'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        url: {
          list: "/electricityfilerb/electricityFilerB/list",
          delete: "/electricityfilerb/electricityFilerB/delete",
          deleteBatch: "/electricityfilerb/electricityFilerB/deleteBatch",
          exportXlsUrl: "/electricityfilerb/electricityFilerB/exportXls",
          importExcelUrl: "electricityfilerb/electricityFilerB/importExcel",
        },
        dictOptions:{},
        width:1200,
        visible: false,
        title:'电量申报详情',
        confirmLoading: false,
        dataSource1: [],
      }
    },
    mounted(){
    },
    methods: {
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
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
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
      setData(){
        this.visible = true;
        getAction(this.url.list,{}).then((res) => {
          if(res.success){
            console.log("++++++++")
            console.log(res)
            let companynames = [];
            let arr = [];
            let arr2 = [];
            this.dataSource1 = [];
            for(let i=0;i<res.result.records.length;i++){
              // console.log(usernames.indexOf(res.result.records[i]['username']));
              //对返回的值去重，提取父节点
              if(companynames.indexOf(res.result.records[i]['filerCompanyName']) === -1){
                companynames.push(res.result.records[i]['filerCompanyName'])
                arr.push(res.result.records[i])
              }else{
                arr2.push(res.result.records[i])
              }
            }
            for(let i=0;i<arr.length;i++){
              let childs = [];
              for(let j=0;j<arr2.length;j++){
                if(arr[i]['filerCompanyName'] === arr2[j]['filerCompanyName']){
                  childs.push(arr2[j])
                }
              }
              arr[i]['children'] = childs;
            }
            // console.log("==============")
            // console.log(companynames)
            // console.log(arr)
            this.dataSource1 = arr;

          }
        })
      },
    }
  }
</script>

<style scoped>

</style>