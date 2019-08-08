Page({
  
  data: {
    recordStatus: 0,
    recordList:[],
    //包含id，duration,playStatus:当前是否在播放
    count:0,
    //当前有多少个录音
    maxRecord:-1,
    //当前最大的录音id
    code:'',
    token:''
  },

  onLoad: function (options) {
    
    var that = this;
    that.data.token = getApp().globalData.passcard.token;
    console.log(that.data.token);
    this.recorderManager = wx.getRecorderManager();
    this.recorderManager.onError(function () {
      that.tip("录音失败！")
    });
    this.recorderManager.onStop(function (res) {
      var that2 = that;
      var duration = Math.round(res.duration / 1000);
      var id = that2.data.maxRecord + 1;
      var newRecord = [{id:id,duration:duration,
        playStatus: 0, playimageurl: '../image/icon_play.png',
        sendimageurl:'../image/send2.png',
                    src:res.tempFilePath}]
      that2.data.recordList = that2.data.recordList.concat(newRecord);
      that2.data.maxRecord = id;
      that2.setData({
        recordList:that2.data.recordList,
      })

      console.log(res.tempFilePath)
      that2.tip("录音完成！")
    });

    this.innerAudioContext = wx.createInnerAudioContext();
    this.innerAudioContext.onError((res) => {
      that.tip("加载播放器失败");
    })

  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
  * 提示
  */
   tip: function (msg) {
    wx.showModal({
      title: '提示',
      content: msg,
      showCancel: false
    })
  },
  handleVoiceInput:function(){
    var that = this;
    var recordStatus = this.data.recordStatus
    if(recordStatus == 0){
      that.setData({
        recordStatus:1
      })
    }
    this.recorderManager.start({
      duration: 6000,
      sampleRate: 16000,
      numberOfChannels: 1,
      encodeBitRate: 96000,
      format: 'mp3',
      frameSize: 50
    });
  },
  handleVoiceEnd: function () {
    var that = this;
    var recordStatus = this.data.recordStatus
    if (recordStatus == 1) {
      that.setData({
        recordStatus: 0
      })
    }
    this.recorderManager.stop()
  },
  handleTapRecord:function(){
    var playStatus = this.data.playStatus;
    if(playStatus == 0){
      that.setData({
        playStatus: 1
      })
    }
    else if (playStatus == 1){
      that.setData({
        playStatus: 0
      })
    }
  },
  setPlayOrStop:function(e){
    var that = this;
    var i = e.currentTarget.dataset.id;
    that.innerAudioContext.src = that.data.recordList[i].src;
    this.innerAudioContext.play()
    if (that.data.recordList[i].playStatus == 0) {
      //开始播放
      that.data.recordList[i].playStatus = 1;
      that.data.recordList[i].playimageurl = '../image/icon_stop.png';
      that.setData({
        recordList : that.data.recordList
      })
    }
    else if (that.data.recordList[i].playStatus == 1) {
      //停止播放
      this.innerAudioContext.stop();
      that.data.recordList[i].playStatus = 0;
      that.data.recordList[i].playimageurl = '../image/icon_play.png';
      that.setData({
        recordList: that.data.recordList
      })
    }
  },
  sendRecord:function(e){
    var that = this;
    var i = e.currentTarget.dataset.id;
    that.data.recordList[i].sendimageurl = "../image/upload.png";
    that.setData({
      recordList: that.data.recordList
    })
    console.log(that.data.token);
    wx.uploadFile({
      
      //url:  "http://127.0.0.1:8080/personalfinancial-0.0.1-SNAPSHOT/sendVoice",
      url: "http://98d681b9.ngrok.io/receivevoice?token=" + that.data.token,
      filePath: this.data.recordList[i].src,
      name: 'voicefile',
      method: 'POST',
      header: {
        'content-type': 'multipart/form-data'
      },
      success: function (res) {
        that.tip("发送成功");
      }
    }) 
  },
  deleteRecord:function(e){
      var that = this;
      var i = e.currentTarget.dataset.id;
      that.data.recordList.splice(i, 1);
      var temp = that.data.maxRecord;
      if (i == that.data.maxRecord) {
        that.data.maxRecord = that.data.maxRecord - 1;
      }
      else {
        for (var j = i; j < that.data.maxRecord; j++) {
          that.data.recordList[j].id = that.data.recordList[j].id - 1;

        }
        that.data.maxRecord = that.data.maxRecord - 1;
      }
      this.setData({
        recordList: that.data.recordList
      })
      that.tip("删除成功!");}
    
})