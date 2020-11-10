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

        <a-form-item label="申报企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-elec-user v-decorator="[ 'filerCompanyName', validatorRules.filerCompanyName]" :multi="true"></j-select-elec-user>
        </a-form-item>
        <a-form-item label="负荷预测" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'loadForecasting', validatorRules.loadForecasting]" placeholder="请输入负荷预测"></a-input>
        </a-form-item>
        <!--<a-form-item label="交易类型" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--&lt;!&ndash;<a-input v-decorator="[ 'transactionType', validatorRules.transactionType]" placeholder="请输入交易类型"></a-input>&ndash;&gt;-->
          <!--<a-select @change="handType" v-decorator="[ 'transactionType', {initialValue:this.transactionType,rules: validatorRules.transactionType.rules}]">-->
            <!--<a-select-option value="sb">双边</a-select-option>-->
            <!--<a-select-option value="a">合同转让</a-select-option>-->
            <!--<a-select-option value="b">挂牌</a-select-option>-->
          <!--</a-select>-->
        <!--</a-form-item>-->
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'filerMonth', validatorRules.filerMonth]" placeholder="请输入月份"></a-input>-->
          <a-select @change="monthChange" v-decorator="[ 'filerMonth', {initialValue:this.filerMonth,rules: validatorRules.filerMonth.rules}]">
            <a-select-option value="yi">一月</a-select-option>
            <a-select-option value="er">二月</a-select-option>
            <a-select-option value="san">三月</a-select-option>
            <a-select-option value="si">四月</a-select-option>
            <a-select-option value="wu">五月</a-select-option>
            <a-select-option value="liu">六月</a-select-option>
            <a-select-option value="qi">七月</a-select-option>
            <a-select-option value="ba">八月</a-select-option>
            <a-select-option value="jiu">九月</a-select-option>
            <a-select-option value="shi">十月</a-select-option>
            <a-select-option value="shiyi">十一月</a-select-option>
            <a-select-option value="shier">十二月</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="申报电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'marketQuotation', validatorRules.marketQuotation]" placeholder="请输入申报电量"></a-input>
          <!--<a-button type="primary" @click="sendMessage">推送</a-button>-->
          <!--<a-button type="primary" @click="hisData">历史数据</a-button>-->
        </a-form-item>
        <a-form-item label="申报开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申报开始时间" v-decorator="[ 'beginDate', validatorRules.beginDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="申报结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申报结束时间" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="申报人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filer', validatorRules.filer]" placeholder="请输入申报人"></a-input>
        </a-form-item>
        <a-form-item label="申报人所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerCompany', validatorRules.filerCompany]" placeholder="请输入申报人所属公司"></a-input>
        </a-form-item>
        <div v-if="this.backyy">
          <a-form-item label="驳回原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'filerResult', validatorRules.filerResult]" placeholder="请输入申报结果"></a-input>
          </a-form-item>
        </div>
        <a-form-item label="申报状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'filerStatus', validatorRules.filerStatus]" placeholder="请输入申报结果"></a-input>
        </a-form-item>
        <a-form-item label="日志记录" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'logRecords', validatorRules.logRecords]" placeholder="请输入日志记录"></a-input>
        </a-form-item>
      </a-form>
    </a-spin>
    <div v-if="this.backStatus">
      <a-button @click="handleBack" style="bottom:10px;position: absolute;background-color: #026e6e;color: #fff">驳回</a-button>
    </div>

    <user-modal ref="usera"></user-modal>
  </a-modal>

</template>

<script>

  import { httpAction,postAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import UserModal from '@/views/system/modules/UserModal'
  import {doReleaseData, doReovkeData} from '@/api/api'
  import JSelectElecUser from '../JSelectElecUser'

  export default {
    name: "ElectricityFilerBModal",
    components: { 
      JDate,
      JSelectUserByDep,
      UserModal,
      JSelectElecUser
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        model2: {},
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
          filerCompanyName: {rules: [
          ]},
          filerMonth: {rules: [
          ]},
          loadForecasting: {rules: [
          ]},
          transactionType: {rules: [
          ]},
          marketQuotation: {rules: [
          ]},
          beginDate: {rules: [
          ]},
          endDate: {rules: [
          ]},
          filer: {rules: [
          ]},
          filerCompany: {rules: [
          ]},
          filerResult: {rules: [{required:true,message:"请填写驳回理由！"}
          ]},
          filerStatus: {rules: [
          ]},
          logRecords: {rules: [
          ]},
        },
        url: {
          add: "/electricityfilerb/electricityFilerB/add",
          edit: "/electricityfilerb/electricityFilerB/edit",
        },
        filerCompanyName: 'admin',
        transactionType:'sb',
        sendId:'',
        filerMonth: 'yi',
        backStatus: false,
        backyy: false,
        num: 0,
        res: [],
        numRec:0,
      }
    },
    created () {
    },
    methods: {
      //推送消息
      // sendMessage(){
      //   console.log("点击推送")
      //   console.log(this.sendId)
      //   doReleaseData({id: 'a75d45a015c44384a04449ee80dc3503'}).then((res) => {
      //     if (res.success) {
      //       this.$message.success(res.message);
      //     } else {
      //       this.$message.warning(res.message);
      //     }
      //   });
      // },
      // hisData(){
      //   console.log("lishi shuju ")
      //   this.$refs.usera.add();
      // },
      //交易类型
      handType(e){
        console.log(e)
        this.transactionType = e
      },
      //月份
      monthChange(e){
        console.log(e)
        this.filerMonth = e
      },
      add () {
        this.edit({});
      },
      edit (record) {
        console.log(record.id)
        if(record.id!= undefined){
          this.backStatus = true;
        }else{
          this.backStatus = false;
        }
        console.log(this.backStatus)
        this.sendId = record.id;
        // record.id = '';
        // record.createTime = '';
        // record.createBy = '';
        this.form.resetFields();
        this.model = Object.assign({}, record);
        console.log(this.model)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'filerCompanyName','filerMonth','loadForecasting','transactionType','marketQuotation','beginDate','endDate','filer','filerCompany','filerResult','filerStatus','logRecords'))
        })
        //获取分月电量最新
        // getAction('/monthelec/momthElec/queryall2',{ companyName:record.filerCompanyName }).then((res) => {
        //   if(res.success){
        //     let record2 = res.result[0];
        //     this.model2 = Object.assign({}, record2);
        //     console.log(this.model2)
        //   }
        // })
      },
      updateModel(n,filerElec){
        for(let i=0;i<this.res.length;i++){
          if(this.res[i].updateNum === parseInt(this.num.toString().substring(0,n-1))){
            this.model2 = Object.assign({}, this.res[i]);
            this.model2.updateNum = this.num
            this.model2.stat = 2
            if(this.filerMonth === 'yi'){
              this.model2.yi = filerElec
            }else if(this.filerMonth === 'er'){
              this.model2.er = filerElec
            }else if(this.filerMonth === 'san'){
              this.model2.san = filerElec
            }else if(this.filerMonth === 'si'){
              this.model2.si = filerElec
            }else if(this.filerMonth === 'wu'){
              this.model2.wu = filerElec
            }else if(this.filerMonth === 'liu'){
              this.model2.liu = filerElec
            }else if(this.filerMonth === 'qi'){
              this.model2.qi = filerElec
            }else if(this.filerMonth === 'ba'){
              this.model2.ba = filerElec
            }else if(this.filerMonth === 'jiu'){
              this.model2.jiu = filerElec
            }else if(this.filerMonth === 'shi'){
              this.model2.shi = filerElec
            }else if(this.filerMonth === 'shiyi'){
              this.model2.shiyi = filerElec
            }else if(this.filerMonth === 'shier'){
              this.model2.shier = filerElec
            }
            console.log(this.model2)
          }
        }
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk (e) {
        e.preventDefault();
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            console.log(values)
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            let httpurl2 = '';
            let method2 = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            //当为 待确认时，点击确定按钮为完成，否则为 等待中
            if(values.filerStatus === '2'){
              console.log("================")
              values.filerStatus = '3';
              //修改分月电量状态
              this.model2.stat = '3';
              getAction('/monthelec/momthElec/queryall2',{ companyName:values.filerCompanyName }).then((res) => {
                if(res.success){
                  this.model2 = res.result[0]
                  httpurl2 = '/monthelec/momthElec/edit';
                  method2 = 'put';
                  console.log(httpurl2)
                  httpAction(httpurl2,this.model2,method2).then((res)=>{
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
              // if(!this.model2.id){
              //   console.log("+++++++")
              //   httpurl2+='/monthelec/momthElec/add';
              //   method2 = 'post';
              // }else{
              //   console.log("========")
              //   httpurl2+='/monthelec/momthElec/edit';
              //   method2 = 'put';
              // }

            }else{
              values.filerStatus = '1';
              //给model2赋值
              console.log(values.filerCompanyName)
              getAction('/monthelec/momthElec/queryall2',{ companyName:values.filerCompanyName }).then((res) => {
                if(res.success){
                  console.log(res)
                  if(res.result.length>0){
                    this.num = res.result[0].updateNum
                    this.res = res.result
                    this.numRec = res.result.length
                    this.model2 = res.result[0]
                    //给model2赋值
                    let len = this.num.toString().split("").length
                    if(len === 1){
                      if(this.filerMonth === 'yi'){
                        this.num += 1
                        httpurl2 = '/monthelec/momthElec/add';
                        method2 = 'post';
                        this.model2.id = '';
                        this.model2.createTime = ''
                        this.model2.createBy = ''
                        this.model2.yi = values.marketQuotation
                      }else if(this.filerMonth === 'er'){
                        this.num = 11
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(2,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.er = values.marketQuotation
                        }

                      }else if(this.filerMonth === 'san'){
                        this.num = 111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(3,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.san = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'si'){
                        this.num = 1111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(4,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.si = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'wu'){
                        this.num = 11111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(5,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.wu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'liu'){
                        this.num = 111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(6,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.liu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'qi'){
                        this.num = 1111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(7,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.qi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'ba'){
                        this.num = 11111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(8,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.ba = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'jiu'){
                        this.num = 111111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(9,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.jiu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shi'){
                        this.num = 1111111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(10,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shiyi'){
                        this.num = 11111111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(11,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shiyi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shier'){
                        this.num = 111111111111
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(12,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shier = values.marketQuotation
                        }
                      }

                    }else if(len === 2){
                      if(this.filerMonth === 'er'){
                        this.num = this.num+10
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(2,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.er = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'san'){
                        this.num = 111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(3,values.marketQuotation)
                      }
                    }else if(len === 3){
                      if(this.filerMonth === 'san'){
                        this.num = this.num+100
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(3,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.san = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'si'){
                        this.num = 1111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(4,values.marketQuotation)
                      }
                    }else if(len === 4){
                      if(this.filerMonth === 'si'){
                        this.num = this.num+1000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(4,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.si = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'wu'){
                        this.num = 11111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(5,values.marketQuotation)
                      }
                    }else if(len === 5){
                      if(this.filerMonth === 'wu'){
                        this.num = this.num+10000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(5,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.wu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'liu'){
                        this.num = 111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(6,values.marketQuotation)
                      }
                    }else if(len === 6){
                      if(this.filerMonth === 'liu'){
                        this.num = this.num+100000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(6,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.liu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'qi'){
                        this.num = 1111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(7,values.marketQuotation)
                      }
                    }else if(len === 7){
                      if(this.filerMonth === 'qi'){
                        this.num = this.num+1000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(7,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.qi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'ba'){
                        this.num = 11111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(8,values.marketQuotation)
                      }
                    }else if(len === 8){
                      if(this.filerMonth === 'ba'){
                        this.num = this.num+10000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(8,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.ba = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'jiu'){
                        this.num = 111111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(9,values.marketQuotation)
                      }
                    }else if(len === 9){
                      if(this.filerMonth === 'jiu'){
                        this.num = this.num+100000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(9,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.jiu = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shi'){
                        this.num = 1111111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(10,values.marketQuotation)
                      }
                    }else if(len === 10){
                      if(this.filerMonth === 'shi'){
                        this.num = this.num+1000000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(10,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shiyi'){
                        this.num = 111111111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(11,values.marketQuotation)
                      }
                    }else if(len === 11){
                      if(this.filerMonth === 'shiyi'){
                        this.num = this.num+10000000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(11,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shiyi = values.marketQuotation
                        }
                      }else if(this.filerMonth === 'shier'){
                        this.num = 1111111111111
                        httpurl2 = '/monthelec/momthElec/edit';
                        method2 = 'put';
                        this.updateModel(12,values.marketQuotation)
                      }
                    }else if(len === 12){
                      if(this.checkType === 'shier'){
                        this.num = this.num+100000000000
                        if(this.numRec === 4){
                          httpurl2 = '/monthelec/momthElec/edit';
                          method2 = 'put';
                          this.updateModel(12,values.marketQuotation)
                        }else{
                          httpurl2 = '/monthelec/momthElec/add';
                          method2 = 'post';
                          this.model2.id = '';
                          this.model2.createTime = ''
                          this.model2.createBy = ''
                          this.model2.shier = values.marketQuotation
                        }
                      }
                    }
                  }
                  this.model2.stat = 2
                  console.log(httpurl2)
                  console.log(this.model2)
                  this.model2.updateNum = this.num
                  httpAction(httpurl2,this.model2,method2).then((res)=>{
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
        this.form.setFieldsValue(pick(row,'filerCompanyName','filerMonth','loadForecasting','transactionType','marketQuotation','beginDate','endDate','filer','filerCompany','filerResult','filerStatus','logRecords'))
      },
      handleBack(){
        console.log("bohui========")
        this.backyy = true;
        const that = this;
        // 触发表单验证
        this.form.validateFields(['filerResult'],{force:true},(err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            let httpurl2 = '';
            let method2 = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
              method = 'put';
            }
            values.filerStatus = '1';
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
            //修改分月电量状态
            this.model2.stat = '2';
            let month = this.model.filerMonth;
            if(month === "yi") {
              this.model2.yi = this.model.marketQuotation;
            }else if(month === "er") {
              this.model2.er = this.model.marketQuotation;
            }else if(month === "san") {
              this.model2.san = this.model.marketQuotation;
            }else if(month === "si") {
              this.model2.si = this.model.marketQuotation;
            }else if(month === "wu") {
              this.model2.wu = this.model.marketQuotation;
            }else if(month === "liu") {
              this.model2.liu = this.model.marketQuotation;
            }else if(month === "qi") {
              this.model2.qi = this.model.marketQuotation;
            }else if(month === "ba") {
              this.model2.ba = this.model.marketQuotation;
            }else if(month === "jiu") {
              this.model2.jiu = this.model.marketQuotation;
            }else if(month === "shi") {
              this.model2.shi = this.model.marketQuotation;
            }else if(month === "shiyi") {
              this.model2.shiyi = this.model.marketQuotation;
            }else if(month === "shier") {
              this.model2.shier = this.model.marketQuotation;
            }
            console.log(this.model2)
            if(!this.model2.id){
              console.log("+++++++")
              httpurl2+='/monthelec/momthElec/add';
              method2 = 'post';
            }else{
              console.log("========")
              httpurl2+='/monthelec/momthElec/edit';
              method2 = 'put';
            }
            httpAction(httpurl2,this.model2,method2).then((res)=>{
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

      
    }
  }
</script>