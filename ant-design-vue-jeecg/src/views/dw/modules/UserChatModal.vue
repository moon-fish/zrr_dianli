<template>
  <a-modal
    :title="title"
    :width="width"
    id="body"
    :bodyStyle="bodystyle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <div id="xxx" style="overflow: auto">
      <a-row  style="overflow: auto">
        <div v-for="(item,index) in this.chatRecords" :key="index" id="aaa1" >
          <div class="bubble-container" v-if="item.messageType === 1 ">
            <div class="icon">
              <div class="caret-left"></div>
              <div class="photo">
                <img src="item.url" style="width:36px;height:36px;" />
              </div>
            </div>
            <div class="content">
              <div>{{ item.message }}</div>
            </div>
          </div>
          <div class="container-right" v-if="item.messageType === 0 ">
            <div class="content-right">
              <div>{{ item.message }}</div>
            </div>
            <div class="icon-right">
              <div class="photo">
                <img src="" style="width:36px;height:36px;" />
              </div>
              <div class="caret-right"></div>
            </div>
          </div>
        </div>
      </a-row>
    </div>
    <div style="float: bottom;bottom: 5px;position: absolute;width: 50%;">
      <a-row style="background-color: #F0F0F0;">
        <a-col :span="19"><a-input placeholder="" ref="inputValue" /></a-col>
        <a-col :span="1"></a-col>
        <a-col :span="4"><a-button type="primary" @click="sendMessage()" style="width: 100%;">发送</a-button></a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
  import store from '@/store/'
  import ARow from 'ant-design-vue/es/grid/Row'
  import { getAction } from '../../../api/manage'

  export default {
    name: 'UserChatModal',
    components: { ARow },
    data () {
      return {
        title:"聊天室",
        width:800,
        bodystyle:{ height:'400px',overflow: 'scroll' },
        visible: false,
        confirmLoading: false,
        websock: null,
        lockReconnect:false,
        comName:'',
        chatRecords:[],
        toUser:'',
      }
    },
    mounted() {
      // this.loadData();
      //this.timerFun();
      // this.heartCheckFun();
    },
    destroyed: function () { // 离开页面生命周期函数
      this.websocketOnclose();
    },
    methods: {
      initDictConfig(){
      },
      chat(records){
        // localStorage.removeItem(this.comName);
        this.initWebSocket();
        getAction('/sys/user/queryall',{userName:records.comAName}).then((res) => {
          if(res.success){
            console.log(records)
            this.comName = records.comAName
            this.visible = true
            this.toUser = res.result[0].id
            let aaa = localStorage.getItem(this.comName);
            // let aaa = localStorage.getItem(this.comName);
            // console.log(aaa)
            this.chatRecords = JSON.parse(aaa)
          }else{
            this.$message.warning("该公司无账号！！！")
          }
        })

      },
      handleOk(){
        console.log("handleOk")
        this.close()
      },
      handleCancel(){
        console.log("handleCancel")
        this.close()
      },
      close(){
        this.visible = false
      },

      //websockets
      initWebSocket: function () {
        // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
        this.userId = store.getters.userInfo.id;
        var url = window._CONFIG['domianURL'].replace("https://","wss://").replace("http://","ws://")+"/websocket/"+this.userId;
        console.log(url);
        this.websock = new WebSocket(url);
        this.websock.onopen = this.websocketOnopen;
        this.websock.onerror = this.websocketOnerror;
        this.websock.onmessage = this.websocketOnmessage;
        this.websock.onclose = this.websocketOnclose;
      },
      websocketOnopen: function () {
        console.log("WebSocket连接成功");
        //心跳检测重置
        // this.heartCheck.reset().start();
      },
      websocketOnerror: function (e) {
        console.log("WebSocket连接发生错误");
        this.reconnect();
      },
      reconnect() {
        var that = this;
        if(that.lockReconnect) return;
        that.lockReconnect = true;
        //没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function () {
          console.info("尝试重连...");
          that.initWebSocket();
          that.lockReconnect = false;
        }, 5000);
      },
      websocketOnmessage: function (e) {
        console.log(e)
        let d = JSON.parse(e.data)
        let mess = JSON.parse(d.message)
        let r = []
        r.push(mess)
        this.chatRecords = this.chatRecords.concat(r)
        localStorage.setItem(this.comName, JSON.stringify(this.chatRecords));
        // console.log("-----接收消息-------",e.data);
        // var data = eval("(" + e.data + ")"); //解析对象
        // console.log(data)
        // if(data.cmd == "topic"){
        //   //系统通知
        //   this.loadData();
        // }else if(data.cmd == "user"){
        //   //用户消息
        //   this.loadData();
        // }
        //心跳检测重置
        // this.heartCheck.reset().start();
      },
      websocketOnclose: function (e) {
        //console.log("connection closed (" + e.code + ")");
        this.reconnect();
      },
      sendMessage(){
        let body = document.getElementById("body")
        console.log(body)
        this.$nextTick(()=>{

          body.scrollTop = body.scrollHeight
          body.scrollIntoView()

        })

        if(this.chatRecords === null){
          this.chatRecords = []
        }
        let num = this.chatRecords.length
        let iv = this.$refs.inputValue.stateValue
        if(iv === null||iv === ''){
          this.$message.warning("发送内容不能为空！")
        }else{
          let m = {
            id: "msg"+(num+1),
            message: iv,
            messageType: 1,
            url: '',
            toUser:this.toUser
          }
          this.websock.send(JSON.stringify(m));
          //保存聊天记录
          let r = []
          r.push(m)
          this.chatRecords = this.chatRecords.concat(r)

          //localStorage.clear();
          localStorage.setItem(this.comName, JSON.stringify(this.chatRecords));
          this.$refs.inputValue.stateValue = '';
        }

      },


    }

  }
</script>

<style scoped>
  .bubble-container {
    position: relative;
    display: flex;
    padding: 10px;
  }
  .content {
    max-width: 55%;
    background-color: #f0f0f0;
    border-radius: 4px;
    padding: 6px 10px;
    word-break: break-all;
    font-size: 14px;
  }
  .icon {
    width: 36px;
    height: 36px;
    position: relative;
    margin-right: 28px;
  }
  .caret-left {
    width: 0;
    height: 0;
    border-width: 12px;
    border-color: transparent #f0f0f0 transparent transparent;
    border-style: solid;
    position: absolute;
    right: -28px;
    top: 50%;
    transform: translateY(-50%);
  }
  .container-right {
    position: relative;
    display: flex;
    justify-content: flex-end;
    padding: 5px;
  }
  .content-right {
    max-width: 55%;
    background-color: #aedb44;
    border-radius: 4px;
    padding: 6px 10px;
    word-break: break-all;
    font-size: 14px;
  }
  .content > text, .content-right > text {
    line-height: 24px;
  }
  .icon-right {
    width: 36px;
    height: 36px;
    position: relative;
    margin-left: 28px;
  }
  .photo {
    width: 36px;
    height: 36px;
    border-radius: 4px;
    overflow: hidden;
  }
  .caret-right {
    width: 0;
    height: 0;
    border-width: 12px;
    border-color: transparent transparent transparent #aedb44;
    border-style: solid;
    position: absolute;
    left: -28px;
    top: 50%;
    transform: translateY(-50%);
    z-index: 1;
  }
</style>