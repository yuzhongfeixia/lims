Ext.define('alms.employeeagecounts', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;
      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      Ext.getCmp('tpanel' + me.mid).removeAll();
      
      me.editToolItems = [
    ' ', { xtype: 'textfield', fieldLabel: '最小年龄', labelWidth: 60, width: 180, maxLength: 20, name: 'searchbeginage', id: mep + 'searchbeginage', allowBlank: true },
    ' ', { xtype: 'textfield', fieldLabel: '最大年龄', labelWidth: 60, width: 180, maxLength: 20, name: 'searchendage', id: mep + 'searchendage', allowBlank: true },

          ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]
      
      me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
        items : me.editToolItems
      });
      me.plEdit = Ext.create('Ext.form.Panel', {
          id : mep + 'pledit',
          header : false,
          autoScroll: true,
          region : 'center',
          frame : true,
          unstyled : true,
          scope : me,
          tbar : me.tbEdit
        });
      me.plTop = Ext.create('Ext.Panel', {
          frame:false,
          autoScroll : false,
          region: 'north',
          width:'100%',
          height:'3%',
          items:me.plEdit,
          title: me.editTitle,
          margins: '2 2 2 5',
          padding: '0 0 0 0'
        });
        
        Ext.getCmp('tpanel' + me.mid).add(me.plTop);
        
        me.CreateBottom();
  

    },
CreateBottom:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plMain = Ext.create('Ext.Panel', {
        id:'plMain1',
        html:'<div id="showplace1" style="width:100%;height:100%;"></div>',
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
            document.getElementById('showplace1').style.width = window.innerWidth*0.9+'px';
            document.getElementById('showplace1').style.height = window.innerHeight*0.8+'px';
        };
        //设置div容器高宽
         resizeMainContainer();
  
          var eduChart = echarts.init(document.getElementById('showplace1'));
          window.resize =function(){
            //屏幕大小自适应，重置容器高宽
            resizeMainContainer();
            eduChart.resize();
          }
          var searchbeginage = tools.GetValue(mep + 'searchbeginage');
          var searchendage = tools.GetValue(mep + 'searchendage');
          var nowdate = new Date();
          var begindate = nowdate.getFullYear()-searchbeginage;
          var enddate = nowdate.getFullYear()-searchendage;
          if (!Ext.isEmpty(searchbeginage)&&!Ext.isEmpty(searchendage)){
        	  var data2show =  tools.JsonGet(tools.GetUrl('UserGetUserAgeCounts.do?begindate='+begindate+'&enddate='+enddate));
              console.log(data2show)
        	  var option = {
                  title: {
                      text: '在职人员年龄情况'
                  },
                  tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                  },
                  toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                  },
                  legend: {
                      data:['人数(个)']
                  },
                  xAxis: {
                      data: [enddate+"-"+begindate+"年"]
                  },
                  yAxis: {},
                  series: [{
                      name: '人数(个)',
                      type: 'bar',
                      data: [data2show.custom]
                  }]
              }; 
          }else{
          var data2show =  tools.JsonGet(tools.GetUrl('UserGetUserAgeCounts.do'));
          var option = {
              title: {
                  text: '在职人员年龄情况'
              },
              tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              toolbox: {
                feature: {
                    saveAsImage: {}
                }
              },
              legend: {
                  data:['人数(个)']
              },
              xAxis: {
                  data: ["1950-1960年","1960-1970年","1970-1980年","1980-1990年","1990-2000年"]
              },
              yAxis: {},
              series: [{
                  name: '人数(个)',
                  type: 'bar',
                  data: [data2show.fifties, data2show.sixties, data2show.seventies, data2show.eighties,data2show.nineties]
              }]
          };
          }
          eduChart.setOption(option);
        }
      });     
      Ext.getCmp('tpanel' + me.mid).add(me.plMain); 
  },
  OnSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var searchbeginage = tools.GetValue(mep + 'searchbeginage')*1;
      var searchendage = tools.GetValue(mep + 'searchendage')*1;
      if(searchendage<searchbeginage){
        tools.alert('最小年龄不能大于最大年龄，请修改！');
        return false;
      }
      console.log(me.plMain)
      if(me.plMain){
        me.plMain.hide();
      }
      
      me.CreateBottom();
    }
});