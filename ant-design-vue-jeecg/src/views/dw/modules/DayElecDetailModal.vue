<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="false"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-card>
      <!-- table区域-begin -->
      <div>
        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSourceDetail"
          :pagination="ipagination"
          :loading="loading"
          :scroll="{ x: 1086 }"
          @change="handleDayElecChange">

        </a-table>
      </div>
      <!-- table区域-end -->
    </a-card>
  </a-modal>
</template>

<script>
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { filterObj } from '@/utils/util';

  export default {
    name: "DayElecModal",
    mixins:[JeecgListMixin],
    data () {
      return {
        title:"操作",
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
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          list: "/dayelec/dayElec/list",
          add: "/dayelec/dayElec/add",
          edit: "/dayelec/dayElec/edit",
        },
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            align:"center",
            customRender: function (text, r, index) {
              return parseInt(index) + 1
            }
          },
          {
            title: '用户名称',
            align:"center",
            dataIndex: 'username'
          },
          {
            title: '用电单元名称',
            align:"center",
            dataIndex: 'usernameUnit'
          },
          {
            title: '时间',
            align:"center",
            dataIndex: 'elecDate'
          },
          {
            title: '日电量',
            align:"center",
            dataIndex: 'dayElec'
          },
          {
            title: '创建时间',
            align:"center",
            dataIndex: 'createTime'
          }
        ],
        dataSourceDetail:[],
        username:'',

      }
    },
    created () {
    },
    methods: {
      show(record){
        console.log("详情=============")
        console.log(record)
        this.visible = true;
        this.username = record.username
        //获取表单数据
        this.loadDayElecDetail(this.username)
      },
      loadDayElecDetail(name){
        this.loading = true;
        var params = this.getQueryParams(name);//查询条件
        getAction(this.url.list,params).then((res) => {
          if(res.success){
            console.log(res.result.records)
            this.dataSourceDetail = res.result.records
            this.ipagination.total = res.result.total;
          }
          this.loading = false;
        })
      },
      getQueryParams(name) {
        //获取查询条件
        let sqp = {}
        if(this.superQueryParams){
          sqp['superQueryParams']=encodeURI(this.superQueryParams)
        }
        for(var p in this.queryParam){//遍历json对象的每个key/value对,p为key
          this.queryParam[p] = '*'+this.queryParam[p]+'*';
        }
        this.queryParam.username = name;
        console.log(this.queryParam)
        var param = Object.assign(sqp, this.queryParam, this.isorter ,this.filters);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        //TODO 字段权限控制
        var str = "id,";
        this.columns.forEach(function (value) {
          str += "," + value.dataIndex;
        });
        return str;
      },
      handleDayElecChange(pagination, filters, sorter) {
        //分页、排序、筛选变化时触发
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = "ascend" === sorter.order ? "asc" : "desc"
        }
        this.ipagination = pagination;
        this.loadDayElecDetail(this.username);
      },

      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'sysOrgCode','username','usernameUnit','ldll','ldyw','ldss','ldsw','ydll','ydyw','ydss','ydsw','edll','edyw','edss','edsw','sdll','sdyw','sdss','sssw','sidll','sidyw','sidss','sidsw','wdll','wdyw','wdss','wdsw','liudll','liudyw','liudss','liudsw','qdll','qdyw','qdss','qdsw','bdll','bdyw','bdss','bdsw','jdll','jdyw','jdss','jdsw','shidll','shidyw','shidss','shidsw','sydll','sydyw','sydss','sydsw','sedll','sedyw','sedss','sedsw','ssdll','ssdyw','ssdss','ssdsw','ssidll','ssidyw','ssidss','ssidsw','swdll','swdyw','swdss','swdsw','sldll','sldyw','sldss','sldsw','sqdll','sqdyw','sqdss','sqdsw','sbdll','sbdyw','sbdss','sbdsw','sjdll','sjdyw','sjdss','sjdsw','esdll','esdyw','esdss','esdsw','eydll','eydyw','eydss','eydsw','eedll','eedyw','eedss','eedsw','essdll','essdyw','essdss','essdsw'))
		  //时间格式化
          this.form.setFieldsValue({elecDate:this.model.elecDate?moment(this.model.elecDate):null})
        });

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
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.elecDate = formData.elecDate?formData.elecDate.format('YYYY-MM-DD HH:mm:ss'):null;
            
            console.log(formData)
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


    }
  }
</script>

<style lang="less" scoped>

</style>