<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    okText="推送"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'username', {initialValue:this.username} ]" placeholder="请输入用户名称" disabled></a-input>
          <!--<j-select-user-by-dep v-decorator="[ 'username', validatorRules.username]"></j-select-user-by-dep>-->
        </a-form-item>
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select @change="handType" :value="checkType">
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
        <a-form-item label="原始电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :value="ysElec" placeholder="请输入原始电量"></a-input>
        </a-form-item>
        <a-form-item label="上次电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input :value="scElec" placeholder="请输入上次电量"></a-input>
        </a-form-item>
        <a-form-item label="调整电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <div v-if="this.checkType === 'yi'">
            <a-input v-decorator="[ 'yi', validatorRules.yi]" placeholder="请输入一月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'er'">
            <a-input v-decorator="[ 'er', validatorRules.er]" placeholder="请输入二月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'san'">
            <a-input v-decorator="[ 'san', validatorRules.san]" placeholder="请输入三月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'si'">
            <a-input v-decorator="[ 'si', validatorRules.si]" placeholder="请输入四月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'wu'">
            <a-input v-decorator="[ 'wu', validatorRules.wu]" placeholder="请输入五月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'liu'">
            <a-input v-decorator="[ 'liu', validatorRules.liu]" placeholder="请输入六月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'qi'">
            <a-input v-decorator="[ 'qi', validatorRules.qi]" placeholder="请输入七月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'ba'">
            <a-input v-decorator="[ 'ba', validatorRules.ba]" placeholder="请输入八月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'jiu'">
            <a-input v-decorator="[ 'jiu', validatorRules.jiu]" placeholder="请输入九月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'shi'">
            <a-input v-decorator="[ 'shi', validatorRules.shi]" placeholder="请输入十月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'shiyi'">
            <a-input v-decorator="[ 'shiyi', validatorRules.shiyi]" placeholder="请输入十一月电量"></a-input>
          </div>
          <div v-if="this.checkType === 'shier'">
            <a-input v-decorator="[ 'shier', validatorRules.shier]" placeholder="请输入腊月电量"></a-input>
          </div>
        </a-form-item>
        <a-form-item label="预测电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ba', validatorRules.ba]" placeholder="请输入预测电量"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,postAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import { doReleaseData } from '@/api/api'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

  export default {
    name: "MomthElecModal",
    components: {
      JSelectUserByDep
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        checkType:'yi',
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
          username: {rules: [
          ]},
          yi: {rules: [
          ]},
          er: {rules: [
          ]},
          san: {rules: [
          ]},
          si: {rules: [
          ]},
          wu: {rules: [
          ]},
          liu: {rules: [
          ]},
          qi: {rules: [
          ]},
          ba: {rules: [
          ]},
          jiu: {rules: [
          ]},
          shi: {rules: [
          ]},
          shiyi: {rules: [
          ]},
          shier: {rules: [
          ]},
          stat: {rules: [
          ]},
          byzd: {rules: [
          ]},
        },
        checkValue:'',
        url: {
          add: "/monthelec/momthElec/add",
          edit: "/monthelec/momthElec/edit",
        },
        sendData: {},
        username:'',
        num: 0,
        res:[],
        numRec: 0,
        ysElec:'222',
        scElec:'111',
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        console.log(record)
        this.username = record.username;
        this.updateNum();
        // record.id = '';
        // record.createTime = '';
        // record.createBy = '';
        this.form.resetFields();
        this.model = Object.assign({}, record);
        // console.log(!this.model.id)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'username','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','stat','byzd'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      //发送消息 需要先把消息添加到sys_announcement表中，再根据消息id进行发送
      sendMessage(data){
        //添加数据
        postAction('/sys/annountCement/add',data).then((res) => {
          if(res.success){
            //获取id并发送数据
            getAction("/sys/annountCement/list",data).then((res) => {
              console.log(res)
              if(res.success){
                let id = res.result.records[0].id;
                console.log(id)
                doReleaseData({id: id}).then((res1) => {
                  if (res1.success) {
                    console.log("ok")
                  } else {
                    console.log("false")
                  }
                });
              }
            })
          }
        })

      },
      //修改次数
      updateNum(){
        getAction('/monthelec/momthElec/queryall2',{ companyName:this.username }).then((res) => {
          if(res.success){
            console.log(res)
            if(res.result.length>0){
              this.num = res.result[0].updateNum
              //初始电量
              for(let i=0;i<res.result.length;i++){
                if(res.result[i].updateNum === 0){
                  if(this.checkType === 'yi'){
                    this.ysElec = res.result[i].yi
                  }else if(this.checkType === 'er'){
                    this.ysElec = res.result[i].er
                  }else if(this.checkType === 'san'){
                    this.ysElec = res.result[i].san
                  }else if(this.checkType === 'si'){
                    this.ysElec = res.result[i].si
                  }else if(this.checkType === 'wu'){
                    this.ysElec = res.result[i].wu
                  }else if(this.checkType === 'liu'){
                    this.ysElec = res.result[i].liu
                  }else if(this.checkType === 'qi'){
                    this.ysElec = res.result[i].qi
                  }else if(this.checkType === 'ba'){
                    this.ysElec = res.result[i].ba
                  }else if(this.checkType === 'jiu'){
                    this.ysElec = res.result[i].jiu
                  }else if(this.checkType === 'shi'){
                    this.ysElec = res.result[i].shi
                  }else if(this.checkType === 'shiyi'){
                    this.ysElec = res.result[i].shiyi
                  }else if(this.checkType === 'shier'){
                    this.ysElec = res.result[i].shier
                  }
                }
              }

              //上次电量
              if(res.result[0].updateNum.toString().length === 1){
                this.scElec = res.result[0].yi
              }else if(res.result[0].updateNum.toString().length === 2){
                this.scElec = res.result[0].er
              }else if(res.result[0].updateNum.toString().length === 3){
                this.scElec = res.result[0].san
              }else if(res.result[0].updateNum.toString().length === 4){
                this.scElec = res.result[0].si
              }else if(res.result[0].updateNum.toString().length === 5){
                this.scElec = res.result[0].wu
              }else if(res.result[0].updateNum.toString().length === 6){
                this.scElec = res.result[0].liu
              }else if(res.result[0].updateNum.toString().length === 7){
                this.scElec = res.result[0].qi
              }else if(res.result[0].updateNum.toString().length === 8){
                this.scElec = res.result[0].ba
              }else if(res.result[0].updateNum.toString().length === 9){
                this.scElec = res.result[0].jiu
              }else if(res.result[0].updateNum.toString().length === 10){
                this.scElec = res.result[0].shi
              }else if(res.result[0].updateNum.toString().length === 11){
                this.scElec = res.result[0].shiyi
              }else if(res.result[0].updateNum.toString().length === 12){
                this.scElec = res.result[0].shier
              }

              this.res = res.result
              this.numRec = res.result.length
            }
          }
        })
      },
      updateModel(n){
        for(let i=0;i<this.res.length;i++){
          if(this.res[i].updateNum === parseInt(this.num.toString().substring(0,n-1))){
            this.model = Object.assign({}, this.res[i]);
          }
        }
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            // if(!this.model.id){
            //   httpurl = this.url.add;
            //   method = 'post';
            // }else{
            //   httpurl+=this.url.edit;
            //    method = 'put';
            // }
            //根据用户名称获取用户id
            getAction('/sys/user/list',{username:values.username}).then((res) => {
              if(res.success){
                console.log(res)
                this.sendData = {
                  titile:"电量申报",
                  msgContent:"<p>备注</p>",
                  startTime:'2020-5-12 17:54:55',
                  endTime:'2020-6-20 17:54:55',
                  priority:'M',
                  msgCategory:'1',
                  msgType:'USER',
                  userIds:'',
                }
                if(res.result.records.length>0){
                  let id = res.result.records[0].id;
                  this.sendData.userIds = id+',';
                  that.sendMessage(this.sendData)
                }

              }
            })
            //修改次数
            console.log(this.num)
            console.log(this.num.toString().split("").length)
            let len = this.num.toString().split("").length
            if(len === 1){
              if(this.checkType === 'yi'){
                this.num += 1
                values.id = '';
                values.createTime = '';
                values.createBy = '';
                httpurl = this.url.add;
                method = 'post';
              }else if(this.checkType === 'er'){
                this.num = 11
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(2)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'san'){
                this.num = 111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(3)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'si'){
                this.num = 1111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(4)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'wu'){
                this.num = 11111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(5)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'liu'){
                this.num = 111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(6)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'qi'){
                this.num = 1111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(7)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'ba'){
                this.num = 11111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(8)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'jiu'){
                this.num = 111111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(9)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'shi'){
                this.num = 1111111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(10)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'shiyi'){
                this.num = 11111111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(11)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }else if(this.checkType === 'shier'){
                this.num = 111111111111
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(12)
                }else{
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                  httpurl = this.url.add;
                  method = 'post';
                }
              }

            }else if(len === 2){
              if(this.checkType === 'er'){
                this.num = this.num+10
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(2)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'san'){
                this.num = 111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(3)
              }
            }else if(len === 3){
              if(this.checkType === 'san'){
                this.num = this.num+100
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(3)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'si'){
                this.num = 1111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(4)
              }
            }else if(len === 4){
              if(this.checkType === 'si'){
                this.num = this.num+1000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(4)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'wu'){
                this.num = 11111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(5)
              }
            }else if(len === 5){
              if(this.checkType === 'wu'){
                this.num = this.num+10000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(5)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'liu'){
                this.num = 111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(6)
              }
            }else if(len === 6){
              if(this.checkType === 'liu'){
                this.num = this.num+100000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(6)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'qi'){
                this.num = 1111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(7)
              }
            }else if(len === 7){
              if(this.checkType === 'qi'){
                this.num = this.num+1000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(7)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'ba'){
                this.num = 11111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(8)
              }
            }else if(len === 8){
              if(this.checkType === 'ba'){
                this.num = this.num+10000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(8)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'jiu'){
                this.num = 111111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(9)
              }
            }else if(len === 9){
              if(this.checkType === 'jiu'){
                this.num = this.num+100000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(9)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'shi'){
                this.num = 1111111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(10)
              }
            }else if(len === 10){
              if(this.checkType === 'shi'){
                this.num = this.num+1000000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(10)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'shiyi'){
                this.num = 111111111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(11)
              }
            }else if(len === 11){
              if(this.checkType === 'shiyi'){
                this.num = this.num+10000000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(11)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }else if(this.checkType === 'shier'){
                this.num = 1111111111111
                httpurl = this.url.edit;
                method = 'put';
                this.updateModel(12)
              }
            }else if(len === 12){
              if(this.checkType === 'shier'){
                this.num = this.num+100000000000
                if(this.numRec === 4){
                  httpurl = this.url.edit;
                  method = 'put';
                  this.updateModel(12)
                }else{
                  httpurl = this.url.add;
                  method = 'post';
                  values.id = '';
                  values.createTime = '';
                  values.createBy = '';
                }
              }
            }

            values.updateNum = this.num
            values.stat = '1';
            let formData = Object.assign(this.model, values);
            console.log(httpurl)
            console.log(method)
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
            //电量申报添加一条数据
            //获取申报的电量
            this.checkValue = '';
            if(this.checkType === "yi") {
              this.checkValue = this.model.yi
            }else if(this.checkType === "er") {
              this.checkValue = this.model.er
            }else if(this.checkType === "san") {
              this.checkValue = this.model.san
            }else if(this.checkType === "si") {
              this.checkValue = this.model.si
            }else if(this.checkType === "wu") {
              this.checkValue = this.model.wu
            }else if(this.checkType === "liu") {
              this.checkValue = this.model.liu
            }else if(this.checkType === "qi") {
              this.checkValue = this.model.qi
            }else if(this.checkType === "ba") {
              this.checkValue = this.model.ba
            }else if(this.checkType === "jiu") {
              this.checkValue = this.model.jiu
            }else if(this.checkType === "shi") {
              this.checkValue = this.model.shi
            }else if(this.checkType === "shiyi") {
              this.checkValue = this.model.shiyi
            }else if(this.checkType === "shier") {
              this.checkValue = this.model.shier
            }

            postAction('/electricityfilerb/electricityFilerB/elecAdd',{ filerCompanyName:this.model.username,filerMonth:this.checkType,marketQuotation:this.checkValue,filerStatus:2 }).then((res) => {
              if(res.success){
                that.$message.success("申报成功");
              }else{
                that.$message.success("申报失败");
              }
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'username','yi','er','san','si','wu','liu','qi','ba','jiu','shi','shiyi','shier','stat','byzd'))
      },
      handType(e){
        console.log(e)
        this.checkType = e
        this.edit(this.model)
      },

      
    }
  }
</script>