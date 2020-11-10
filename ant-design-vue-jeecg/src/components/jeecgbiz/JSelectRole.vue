<template>
  <j-select-biz-component
    :value="value"

    name="角色"
    displayKey="stationName"

    :returnKeys="returnKeys"
    :listUrl="url.list"
    :columns="columns"
    queryParamText="电站名称"

    v-on="$listeners"
    v-bind="$attrs"
  />
</template>

<script>
  import JSelectBizComponent from './JSelectBizComponent'

  export default {
    name: 'JSelectMultiUser',
    components: { JSelectBizComponent },
    props: ['value'],
    data() {
      return {
        returnKeys: ['id', 'stationName'],
        url: { list: '/generationsideInfob/generationSideInfoB/list' },
        // columns: [
        //   { title: '角色名称', dataIndex: 'roleName', align: 'center', width: 120 },
        //   { title: '角色编码', dataIndex: 'roleCode', align: 'center', width: 120 }
        // ]
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
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
      }
    }
  }
</script>

<style lang="less" scoped></style>