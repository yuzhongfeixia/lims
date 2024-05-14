Ext.define('alms.employeedutycounts', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;
      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      Ext.getCmp('tpanel' + me.mid).removeAll();
      me.plMain = Ext.create('Ext.Panel', {
        id:'plMain2',
        html:'<div id="showplace2" style="width:100%;height:100%;"></div>',
        frame:false,
        autoScroll : false,
        region: 'center',
        width:'100%',
        height:'100%',
        items:me.chart,
        title: me.editTitle,
        margins: '2 2 2 2',
        padding: '0 0 0 0',
        afterRender:function(){
          //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
        var resizeMainContainer = function () {
            document.getElementById('showplace2').style.width = window.innerWidth*0.9+'px';
            document.getElementById('showplace2').style.height = window.innerHeight*0.8+'px';
        };
        //设置div容器高宽
         resizeMainContainer();
  
          var dutyChart = echarts.init(document.getElementById('showplace2'));
          window.resize =function(){
            //屏幕大小自适应，重置容器高宽
            resizeMainContainer();
            dutyChart.resize();
          }
          var data2show =  tools.JsonGet(tools.GetUrl('UserGetUserDutyCounts.do'));
          var option = {
              
              title: {
                  text: '人员职称情况'
              },
              tooltip: {},
              legend: {
                  data:['人数(个)']
              },
              toolbox:{
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                feature: {
                  saveAsImage:{
                    show : true
                  }
                }
              },
              xAxis: {
                  data: ["推广研究员","高级农艺师","技师","助理农艺师","农艺师"]
              },
              yAxis: {},
              series: [{
                  name: '人数(个)',
                  type: 'bar',
                  data: [data2show.tgyjy, data2show.gjnys, data2show.js, data2show.zlnys,data2show.nys]
              }]
          };

          dutyChart.setOption(option);
        }
      });     
      Ext.getCmp('tpanel' + me.mid).add(me.plMain); 

    }

});