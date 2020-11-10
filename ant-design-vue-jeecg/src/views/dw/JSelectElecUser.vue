<template>
  <j-select-biz-component
    :value="value"

    name="角色"
    displayKey="comAName"

    :returnKeys="returnKeys"
    :listUrl="url.list"
    :columns="columns"
    queryParamText="企业全称"

    v-on="$listeners"
    v-bind="$attrs"
  />
</template>

<script>
  import JSelectBizComponent from '@/components/jeecgbiz//JSelectBizComponent'

  export default {
    name: 'JSelectMultiUser',
    components: { JSelectBizComponent },
    props: ['value'],
    data() {
      return {
        returnKeys: ['id', 'comAName'],
        url: { list: '/userinfob/userInfoB/list' },
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
            title:'企业全称',
            align:"center",
            width:120,
            dataIndex: 'comAName'
          },
          // {
          //   title:'总公司名称',
          //   align:"center",
          //   width:120,
          //   dataIndex: 'comTName'
          // },
          // {
          //   title:'企业简称',
          //   align:"center",
          //   dataIndex: 'comSName'
          // },
          {
            title:'地域信息',
            align:"center",
            dataIndex: 'addressInfo'
          },
          // {
          //   title:'行业类别',
          //   align:"center",
          //   dataIndex: 'industry'
          // },
          // {
          //   title:'企业所在工业园区',
          //   align:"center",
          //   dataIndex: 'industrialSite'
          // },
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
          //   title:'传真',
          //   align:"center",
          //   dataIndex: 'ems'
          // },
          // {
          //   title:'网址',
          //   align:"center",
          //   dataIndex: 'internet'
          // },
          // {
          //   title:'统一社会信用代码',
          //   align:"center",
          //   dataIndex: 'socialCredit'
          // },
          // {
          //   title:'成立日期',
          //   align:"center",
          //   dataIndex: 'establishment',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'法人名称',
          //   align:"center",
          //   dataIndex: 'legalName'
          // },
          // {
          //   title:'企业注册地址',
          //   align:"center",
          //   dataIndex: 'registeredAddress'
          // },
          // {
          //   title:'法定代表人姓名',
          //   align:"center",
          //   dataIndex: 'legalRepresentativeName'
          // },
          // {
          //   title:'组织机构代码',
          //   align:"center",
          //   dataIndex: 'orgCode'
          // },
          // {
          //   title:'开户银行',
          //   align:"center",
          //   dataIndex: 'depositBank'
          // },
          // {
          //   title:'开户名称',
          //   align:"center",
          //   dataIndex: 'depositName'
          // },
          // {
          //   title:'开户账号',
          //   align:"center",
          //   dataIndex: 'depoaitAccount'
          // },
          // {
          //   title:'税务登记证号',
          //   align:"center",
          //   dataIndex: 'registerNumber'
          // },
          // {
          //   title:'注册资本',
          //   align:"center",
          //   dataIndex: 'registerMoney'
          // },
          // {
          //   title:'资产总额',
          //   align:"center",
          //   dataIndex: 'totalAssets'
          // },
          // {
          //   title:'电力业务许可证编号',
          //   align:"center",
          //   dataIndex: 'licenceNo'
          // },
          // {
          //   title:'许可证生效日期',
          //   align:"center",
          //   dataIndex: 'effectiveDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'许可证失效日期',
          //   align:"center",
          //   dataIndex: 'expiryDate',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'合同容量（MVA）',
          //   align:"center",
          //   dataIndex: 'contractCapacity'
          // },
          // {
          //   title:'运行容量（MVA）',
          //   align:"center",
          //   dataIndex: 'operatingCapacity'
          // },
          // {
          //   title:'最高供电电压',
          //   align:"center",
          //   dataIndex: 'maxVoltage'
          // },
          // {
          //   title:'月度用电信息',
          //   align:"center",
          //   dataIndex: 'monthElecInfo'
          // },
          // {
          //   title:'年度用电信息',
          //   align:"center",
          //   dataIndex: 'yearElecInfo'
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