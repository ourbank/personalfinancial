// pages/login/login.js
var app = getApp();
Page({

 
  data: {
    username: null,
    password: null,
    result:null,
    code:'',
    token:''
  },




  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  tip: function (msg) {
    wx.showModal({
      title: '小提示',
      content: msg,
      showCancel: false
    })
  },

  loginBtnClick: function () {
   
    var that = this;
    wx.request({
      url: 'http://98d681b9.ngrok.io/loginwx',
      method: 'POST',
      header: {
        "Content-Type": "application/json"
      },
      data: {
        account:that.data.username,
        password:that.data.password
      },
       success: function (res) {
         var resdata = res.data;
         that.setData({
           code:resdata.code,
           token:resdata.token
         });
         //console.log(that.data.code);
         //console.log(that.data.token);
         that.tip("返回的验证码为："+that.data.code);
         if (resdata.result) {
           app.globalData.passcard = { code: that.data.code, token: 
           that.data.token };
           wx.navigateTo({
             url: '../Intelligent/Intelligent'
           })
         }
         else {
           that.tip("密码错误，请重新输入")
         } 
      }})
   },
      
  
  passwordInput: function (e) {
    this.setData({ password: e.detail.value })
  },
  usernameInput: function (e) {
    this.setData({ username: e.detail.value })
  },
  changepassword:function(){
    this.tip("此功能尚未添加！")
  }
})