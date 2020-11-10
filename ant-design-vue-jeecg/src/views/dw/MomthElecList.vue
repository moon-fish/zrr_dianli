<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="用户名称">
              <a-input placeholder="请输入用户名称" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>
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
      <!--<a-button @click="handleAddElecMonth" type="primary" icon="plus">新增</a-button>-->
      <a-button type="primary" icon="download" @click="handleExportXls('分月电量')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
      <!--<a-button @click="handleAdd" type="primary" icon="plus" style="float: right">年份</a-button>-->
      <div style="float: right;width: 40px;margin-right: 32px;">
        <a-select @change="handType" :value="checkType">
          <a-select-option value="yi">2020</a-select-option>
          <a-select-option value="er">2021</a-select-option>
        </a-select>
      </div>

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
        :pagination="false"
        :loading="loading"
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

        <span slot="stat" slot-scope="text, record">
          <div v-if="text === '1'">
            <span style="color: #ffa95e">等待中</span>
          </div>
          <div v-if="text === '2'">
            <span style="color: red" @click="confirming(record)">待确认</span>
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
    </div>
    <!--电量调整页面-->
    <momthElec-modal ref="modalForm" @ok="modalFormOk"></momthElec-modal>
    <!--用户电量申报详细页面-->
    <elec-filer-b-modal ref="elecSbModal" @ok="elecSbOk"></elec-filer-b-modal>
    <!--分月电量新增页面-->
    <momth-elec-modal-add-modal ref="elecMonthAdd" @ok="elecMonthAddOk"></momth-elec-modal-add-modal>
    <!--分月电量调整记录-->
    <month-records-modal ref="monthRecords"></month-records-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MomthElecModal from './modules/MomthElecModal'
  import MomthElecModalAddModal from './modules/MomthElecAddModal'
  import { getAction,deleteAction } from '../../api/manage'
  import ElecFilerBModal from './modules/ElecFilerBModal'
  import MonthRecordsModal from './modules/MonthRecordsModal'
  import store from '@/store/'

  export default {
    name: "MomthElecList",
    mixins:[JeecgListMixin],
    components: {
      MonthRecordsModal,
      MomthElecModal,
      ElecFilerBModal,
      MomthElecModalAddModal
    },
    data () {
      return {
        description: '分月电量管理页面',
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
          {
            title:'状态',
            align:"center",
            dataIndex: 'stat',
            scopedSlots: { customRender: 'stat' }
          },
          // {
          //   title:'备用',
          //   align:"center",
          //   dataIndex: 'byzd'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/monthelec/momthElec/list",
          delete: "/monthelec/momthElec/delete",
          deleteBatch: "/monthelec/momthElec/deleteBatch",
          exportXlsUrl: "/monthelec/momthElec/exportXls",
          importExcelUrl: "monthelec/momthElec/importExcel",
        },
        dictOptions:{},
        dataSource1:[],
        checkType: 'yi',
        companys:[],
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    created(){
      this.setData();
    },
    methods: {
      initDictConfig(){
      },
      setData(){
        let usernames = [];
        let arr = [];
        let arr2 = [];
        let arr3 = [];
        this.dataSource1 = [];
        getAction('/monthelec/momthElec/queryall2',{}).then((res) => {
          if(res.success){
            for(let i=0;i<res.result.length;i++){
              // console.log(usernames.indexOf(res.result.records[i]['username']));
              //对返回的值去重，提取父节点 月份最新数据
              if(usernames.indexOf(res.result[i]['username']) === -1){
                usernames.push(res.result[i]['username'])
                arr.push(res.result[i])
                arr2.push(res.result[i])
              }
              //每月最终数据
              if(res.result[i].updateNum.toString().substring(0,1) === '3'){
                arr3.push(res.result[i])
              }
            }
            //提取每个月最终数据
            for(let i=0;i<arr.length;i++){
              for(let j=0;j<arr3.length;j++){
                if(arr[i].username === arr3[j].username){
                  arr[i] = arr3[j]
                }
              }
            }
            //组合数据
            for(let i=0;i<arr.length;i++){
              for(let j=0;j<arr2.length;j++){
                if(arr[i].username === arr2[j].username){
                  arr[i].stat = arr2[j].stat
                  if(arr2[j].updateNum.toString().length === 1){
                    arr[i].yi = arr2[j].yi
                  }else if(arr2[j].updateNum.toString().length === 2){
                    arr[i].er = arr2[j].er
                  }else if(arr2[j].updateNum.toString().length === 3){
                    arr[i].san = arr2[j].san
                  }else if(arr2[j].updateNum.toString().length === 4){
                    arr[i].si = arr2[j].si
                  }else if(arr2[j].updateNum.toString().length === 5){
                    arr[i].wu = arr2[j].wu
                  }else if(arr2[j].updateNum.toString().length === 6){
                    arr[i].liu = arr2[j].liu
                  }else if(arr2[j].updateNum.toString().length === 7){
                    arr[i].qi = arr2[j].qi
                  }else if(arr2[j].updateNum.toString().length === 8){
                    arr[i].ba = arr2[j].ba
                  }else if(arr2[j].updateNum.toString().length === 9){
                    arr[i].jiu = arr2[j].jiu
                  }else if(arr2[j].updateNum.toString().length === 10){
                    arr[i].shi = arr2[j].shi
                  }else if(arr2[j].updateNum.toString().length === 11){
                    arr[i].shiyi = arr2[j].shiyi
                  }else if(arr2[j].updateNum.toString().length === 12){
                    arr[i].shier = arr2[j].shier
                  }

                }
              }
            }
            // console.log(usernames)
            // for(let i=0;i<arr.length;i++){
            //   let childs = [];
            //   for(let j=0;j<arr2.length;j++){
            //     if(arr[i]['username'] === arr2[j]['username']){
            //       childs.push(arr2[j])
            //     }
            //   }
            //   arr[i]['children'] = childs;
            // }
            // console.log("==============")
            // console.log(usernames)
            //权限设置  只看当前权限的区域
            console.log(this.companys)
            //权限设置 筛选该角色下的公司名称
            this.companys = [];
            let userInfo = store.getters.userInfo;
            let id = userInfo.id
            getAction('/userinfob/userInfoB/queryall',{id:id}).then((res) => {
              if(res.success){
                //获取该角色允许区域的公司名称
                for(let i=0;i<res.result.length;i++){
                  // console.log(res.result[i].username)
                  if(this.companys.indexOf(res.result[i].comAName) === -1){
                    this.companys.push(res.result[i].comAName)
                  }
                }
                //过滤数据
                for(let i=0;i<this.companys.length;i++){
                  for(let j=0;j<arr.length;j++){
                    if(this.companys[i] === arr[j].username){
                      this.dataSource1.push(arr[j]);
                    }
                  }
                }

              }
            })

          }
        })
      },
      modalFormOk(){
        this.setData();
      },
      handleDelete(id){
        deleteAction(this.url.delete, {id: id}).then((res) => {
          if (res.success) {
            this.$message.success(res.message);
            this.setData();
          } else {
            this.$message.warning(res.message);
          }
        });
      },
      //点击待确认按钮，弹框:电量申报的详细页面
      confirming(record){
        this.$refs.elecSbModal.edit(record);
      },
      elecSbOk(){
        this.setData();
      },
      handType(e){
        console.log(e)
        this.checkType = e
        this.edit(this.model)
      },
      handleAddElecMonth(){
        this.$refs.elecMonthAdd.add();
      },
      elecMonthAddOk(){
        this.setData();
      },
      datailRecords(record){
        console.log("=============")
        this.$refs.monthRecords.show(record);
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>