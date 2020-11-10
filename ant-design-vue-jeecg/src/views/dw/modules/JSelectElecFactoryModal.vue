<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="margin-top: -70px"
    wrapClassName="ant-modal-cust-warp"
  >
    <a-row :gutter="10" style="background-color: #ececec; padding: 10px; margin: -10px">
      <!--<a-col :md="6" :sm="24">-->
      <!--<a-card :bordered="false">-->
      <!--&lt;!&ndash;组织机构&ndash;&gt;-->
      <!--<a-directory-tree-->
      <!--selectable-->
      <!--:selectedKeys="selectedDepIds"-->
      <!--:checkStrictly="true"-->
      <!--:dropdownStyle="{maxHeight:'200px',overflow:'auto'}"-->
      <!--:treeData="departTree"-->
      <!--:expandAction="false"-->
      <!--:expandedKeys.sync="expandedKeys"-->
      <!--@select="onDepSelect"-->
      <!--/>-->
      <!--</a-card>-->
      <!--</a-col>-->
      <a-col :md="24" :sm="24">
        <a-card :bordered="false">
          用户账号:
          <a-input-search
            :style="{width:'150px',marginBottom:'15px'}"
            placeholder="请输入账号"
            v-model="queryParam.username"
            @search="onSearch"
          ></a-input-search>
          <a-button @click="searchReset(1)" style="margin-left: 20px" icon="redo">重置</a-button>
          <!--用户列表-->
          <a-table
            ref="table"
            :scroll="scrollTrigger"
            size="middle"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type: getType}"
            :loading="loading"
            @change="handleTableChange">
          </a-table>
        </a-card>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import {filterObj} from '@/utils/util'
  import {queryDepartTreeList, getUserList, queryUserByDepId} from '@/api/api'
  import { getAction } from '@/api/manage'

  export default {
    name: 'JSelectElecFactoryModal',
    components: {},
    props: ['modalWidth', 'multi', 'userIds'],
    data() {
      return {
        queryParam: {
          username: "",
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
            title:'电站名称',
            align:"center",
            dataIndex: 'stationName'
          },
          {
            title:'装机容量',
            align:"center",
            dataIndex: 'installedCapacity'
          },
          {
            title:'地址',
            align:"center",
            dataIndex: 'address'
          },
          // {
          //   title:'离市区距离',
          //   align:"center",
          //   dataIndex: 'distance'
          // },
          {
            title:'联系人姓名',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'联系人职务',
            align:"center",
            dataIndex: 'duty'
          },
          {
            title:'手机号码',
            align:"center",
            dataIndex: 'phone'
          },
          {
            title:'办公电话',
            align:"center",
            dataIndex: 'officePhone'
          },
          // {
          //   title:'总装机容量',
          //   align:"center",
          //   dataIndex: 'installedCapacityAll'
          // },
          // {
          //   title:'机组类型',
          //   align:"center",
          //   dataIndex: 'unitType'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        scrollTrigger: {},
        dataSource: [],
        selectedRowKeys: [],
        selectUserRows: [],
        selectUserIds: [],
        title: '根据部门选择用户',
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter: {
          column: 'createTime',
          order: 'desc'
        },
        selectedDepIds: [],
        departTree: [],
        visible: false,
        form: this.$form.createForm(this),
        loading: false,
        expandedKeys: [],
      }
    },
    computed: {
      // 计算属性的 getter
      getType: function () {
        return this.multi === true ? 'checkbox' : 'radio';
      }
    },
    watch: {
      userIds: {
        immediate: true,
        handler() {
          this.initUserNames()
        }
      },
    },
    created() {
      // 该方法触发屏幕自适应
      this.resetScreenSize();
      this.loadData()
    },
    methods: {
      initUserNames() {
        if (this.userIds) {
          // 这里最后加一个 , 的原因是因为无论如何都要使用 in 查询，防止后台进行了模糊匹配，导致查询结果不准确
          let values = this.userIds.split(',')
          console.log(values)
          getAction('/generationsideInfob/generationSideInfoB/list',{
            username: values[0],
          }).then((res) => {
            if (res.success) {
              console.log("查询的user信息")
              console.log(res.result)
              let selectedRowKeys = []
              let realNames = []
              res.result.records.forEach(user => {
                realNames.push(user['stationName'])
                selectedRowKeys.push(user['id'])
              })
              this.selectedRowKeys = selectedRowKeys
              this.$emit('initComp', realNames.join(','))
            }
          })
        } else {
          // JSelectUserByDep组件bug issues/I16634
          this.$emit('initComp', '')
        }
      },
      async loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        if (this.selectedDepIds && this.selectedDepIds.length > 0) {
          await this.initQueryUserByDepId(this.selectedDepIds)
        } else {
          this.loading = true
          let params = this.getQueryParams()//查询条件
          await getAction('/generationsideInfob/generationSideInfoB/list',params).then((res) => {
            if (res.success) {
              this.dataSource = res.result.records
              this.ipagination.total = res.result.total
            }
          }).finally(() => {
            this.loading = false
          })
        }
      },
      // 触发屏幕自适应
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 500) {
          this.scrollTrigger = {x: 800};
        } else {
          this.scrollTrigger = {};
        }
      },
      showModal() {
        this.visible = true;
        // this.queryDepartTree();
        this.initUserNames()
        this.loadData();
        this.form.resetFields();
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        let str = 'id,';
        for (let a = 0; a < this.columns.length; a++) {
          str += ',' + this.columns[a].dataIndex;
        }
        return str;
      },
      searchReset(num) {
        let that = this;
        if (num !== 0) {
          that.queryParam = {};
          that.loadData(1);
        }
        that.selectedRowKeys = [];
        that.selectUserIds = [];
        that.selectedDepIds = [];
      },
      close() {
        this.searchReset(0);
        this.visible = false;
      },
      handleTableChange(pagination, filters, sorter) {
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = 'ascend' === sorter.order ? 'asc' : 'desc';
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleSubmit() {
        let that = this;
        this.getSelectUserRows();
        that.$emit('ok', that.selectUserRows, that.selectUserIds);
        that.searchReset(0)
        that.close();
      },
      //获取选择用户信息
      getSelectUserRows(rowId) {
        let dataSource = this.dataSource;
        let userIds = "";
        this.selectUserRows = [];
        console.log(dataSource)
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedRowKeys.includes(dataSource[i].id)) {
            this.selectUserRows.push(dataSource[i]);
            userIds = userIds + "," + dataSource[i].stationName
          }
        }
        console.log(userIds)
        this.selectUserIds = userIds.substring(1);
        console.log(this.selectUserIds)
      },
      // 点击树节点,筛选出对应的用户
      // onDepSelect(selectedDepIds) {
      //   if (selectedDepIds[0] != null) {
      //     this.initQueryUserByDepId(selectedDepIds); // 调用方法根据选选择的id查询用户信息
      //     if (this.selectedDepIds[0] !== selectedDepIds[0]) {
      //       this.selectedDepIds = [selectedDepIds[0]];
      //     }
      //   }
      // },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onSearch() {
        this.loadData(1);
      },
      // 根据选择的id来查询用户信息
      initQueryUserByDepId(selectedDepIds) {
        this.loading = true
        return queryUserByDepId({id: selectedDepIds.toString()}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
            this.ipagination.total = res.result.length;
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // queryDepartTree() {
      //   queryDepartTreeList().then((res) => {
      //     if (res.success) {
      //       this.departTree = res.result;
      //       // 默认展开父节点
      //       this.expandedKeys = this.departTree.map(item => item.id)
      //     }
      //   })
      // },
      // modalFormOk() {
      //   this.loadData();
      // }
    }
  }
</script>

<style scoped>
  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
</style>