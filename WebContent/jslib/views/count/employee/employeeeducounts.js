Ext.define('alms.employeeeducounts', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;
      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      Ext.getCmp('tpanel' + me.mid).removeAll();
      me.plMain = Ext.create('Ext.Panel', {
        id:'plMain',
        html:'<div id="showplace" style="width:100%;height:100%;"></div>',
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
            document.getElementById('showplace').style.width = window.innerWidth*0.9+'px';
            document.getElementById('showplace').style.height = window.innerHeight*0.8+'px';
        };
        //设置div容器高宽
         resizeMainContainer();
  
          var eduChart = echarts.init(document.getElementById('showplace'));
          window.resize =function(){
            //屏幕大小自适应，重置容器高宽
            resizeMainContainer();
            eduChart.resize();
          }
          var data2show =  tools.JsonGet(tools.GetUrl('UserGetUserEduCounts.do'));
          var option = {
              title: {
                  text: '在职人员学历情况'
              },
              tooltip: {},
              legend: {
                  data:['人数(个)']
              },
              xAxis: {
                  data: ["小学","初中","高中","大专","本科","硕士研究生","博士研究生"]
              },
              yAxis: {},
              series: [{
                  name: '人数(个)',
                  type: 'bar',
                  data: [data2show.xx, data2show.cz, data2show.gz, data2show.dz,data2show.bk,data2show.ss, data2show.bs]
              }]
          };

          eduChart.setOption(option);
        }
      });     
      Ext.getCmp('tpanel' + me.mid).add(me.plMain); 

    }

});