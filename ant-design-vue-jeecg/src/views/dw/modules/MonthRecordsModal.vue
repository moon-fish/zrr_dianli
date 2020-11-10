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
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource1"
        :pagination="false"
        :loading="loading"
        :scroll="tableScroll"
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

        <span slot="stat" slot-scope="text, record">
          <div v-if="text === '1'">
            <span style="color: #ffa95e">等待中</span>
          </div>
          <div v-if="text === '2'">
            <span style="color: red" >待确认</span>
          </div>
          <div v-if="text === '3'">
            <span style="color: limegreen">已完成</span>
          </div>
          <div v-if="text === '4'">
            <span style="color: darkgrey">初始化</span>
          </div>
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">调整</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a @click="datailRecords(record)">调整记录</a>
              </a-menu-item>

            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </a-spin>
  </a-modal>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { getAction,deleteAction } from '@/api/manage'

  export default {
    name: 'MonthRecordsModal',
    mixins:[JeecgListMixin],
    data () {
      return {
        form: this.$form.createForm(this),
        title: "操作",
        width: 1200,
        /*弹出框宽度1200-24X2 = 1152 24为padding值*/
        tableScroll:{x :1152},
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
        validatorRules: {},
        url: {
          list: "/monthelec/momthElec/list",
          delete: "/monthelec/momthElec/delete",
          deleteBatch: "/monthelec/momthElec/deleteBatch",
          exportXlsUrl: "/monthelec/momthElec/exportXls",
          importExcelUrl: "monthelec/momthElec/importExcel",
        },
        columns: [
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
            title:'用户名称',
            align:"center",
            dataIndex: 'username'
          },
          {
            title:'一月',
            align:"center",
            dataIndex: 'yi'
          },
          {
            title:'二月',
            align:"center",
            dataIndex: 'er'
          },
          {
            title:'三月',
            align:"center",
            dataIndex: 'san'
          },
          {
            title:'四月',
            align:"center",
            dataIndex: 'si'
          },
          {
            title:'五月',
            align:"center",
            dataIndex: 'wu'
          },
          {
            title:'六月',
            align:"center",
            dataIndex: 'liu'
          },
          {
            title:'七月',
            align:"center",
            dataIndex: 'qi'
          },
          {
            title:'八月',
            align:"center",
            dataIndex: 'ba'
          },
          {
            title:'九月',
            align:"center",
            dataIndex: 'jiu'
          },
          {
            title:'十月',
            align:"center",
            dataIndex: 'shi'
          },
          {
            title:'十一月',
            align:"center",
            dataIndex: 'shiyi'
          },
          {
            title:'十二月',
            align:"center",
            dataIndex: 'shier'
          },
          // {
          //   title:'状态',
          //   align:"center",
          //   dataIndex: 'stat',
          //   scopedSlots: { customRender: 'stat' }
          // },
          // {
          //   title:'备用',
          //   align:"center",
          //   dataIndex: 'byzd'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        dataSource1:[],
      }
    },
    created () {

    },
    methods: {
      show(record){
        this.visible = true;
        console.log(record.username)
        getAction(this.url.list,{ username:record.username }).then((res) => {
          console.log(res)
          if(res.success){
            console.log(res.result.records)
            this.dataSource1 = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      handleOk(){
        this.close()
      },
      handleCancel () {
        this.close()
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
    },
  }
</script>

<style scoped>

</style>