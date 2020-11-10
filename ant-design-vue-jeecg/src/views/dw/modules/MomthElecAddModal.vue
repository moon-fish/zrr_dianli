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

        <!--<a-form-item label="用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
          <!--&lt;!&ndash;<a-input v-decorator="[ 'username', validatorRules.username]" placeholder="请输入用户名称"></a-input>&ndash;&gt;-->
          <!--<j-select-elec-user v-decorator="[ 'username', validatorRules.username]"></j-select-elec-user>-->
        <!--</a-form-item>-->
        <a-form-item label="年度合同电量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'byzd', validatorRules.byzd]" placeholder="请输入年度合同电量"></a-input>
        </a-form-item>
        <a-form-item label="填报类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-model="addType" @change="addTypeChange">
              <a-radio :value="1">分解曲线</a-radio>
              <a-radio :value="2">自定义</a-radio>
            </a-radio-group>
        </a-form-item>
        <a-form-item label="一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'yi', validatorRules.yi]" placeholder="请输入一月"></a-input>
        </a-form-item>
        <a-form-item label="二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'er', validatorRules.er]" placeholder="请输入二月"></a-input>
        </a-form-item>
        <a-form-item label="三月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'san', validatorRules.san]" placeholder="请输入三月"></a-input>
        </a-form-item>
        <a-form-item label="四月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'si', validatorRules.si]" placeholder="请输入四月"></a-input>
        </a-form-item>
        <a-form-item label="五月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'wu', validatorRules.wu]" placeholder="请输入五月"></a-input>
        </a-form-item>
        <a-form-item label="六月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'liu', validatorRules.liu]" placeholder="请输入六月"></a-input>
        </a-form-item>
        <a-form-item label="七月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'qi', validatorRules.qi]" placeholder="请输入七月"></a-input>
        </a-form-item>
        <a-form-item label="八月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ba', validatorRules.ba]" placeholder="请输入八月"></a-input>
        </a-form-item>
        <a-form-item label="九月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'jiu', validatorRules.jiu]" placeholder="请输入九月"></a-input>
        </a-form-item>
        <a-form-item label="十月" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'shi', validatorRules.shi]" placeholder="请输入十月"></a-input>
        </a-form-item>
        <a-form-item label="十一月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shiyi', validatorRules.shiyi]" placeholder="请输入十一月"></a-input>
        </a-form-item>
        <a-form-item label="十二月" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shier', validatorRules.shier]" placeholder="请输入十二月"></a-input>
        </a-form-item>
        <!--<a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">-->
        <!--<a-input v-decorator="[ 'stat', validatorRules.stat]" placeholder="请输入状态"></a-input>-->
        <!--</a-form-item>-->


      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction,postAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import { doReleaseData } from '@/api/api'
  import JSelectElecUser from '../JSelectElecUser'

  export default {
    name: "MomthElecModal",
    components: {
      JSelectElecUser,
    },
    props:{
      aaa:{
        type:String,
        default:'',
      }
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
        addType: 1,
        radioStyle: {
          display: 'block',
          height: '30px',
          lineHeight: '30px',
        },
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
        record.id = '';
        record.createTime = '';
        record.createBy = '';
        this.form.resetFields();
        this.model = Object.assign({}, record);
        console.log("==================")
        console.log(!this.model.id)
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
            console.log("tianjiacg")
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
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            // if(!this.model.id){
            httpurl+=this.url.add;
            method = 'post';
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
                let id = res.result.records[0].id;
                this.sendData.userIds = id+',';
                that.sendMessage(this.sendData)

              }
            })

            values.stat = '4';
            values.username = this.aaa;
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
      addTypeChange(e){
        console.log(e.target.value)
        this.addType = e.target.value;
      },


    }
  }
</script>