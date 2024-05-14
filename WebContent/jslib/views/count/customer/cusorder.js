Ext.define('alms.cusorder', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;

      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      
      Ext.getCmp('tpanel' + me.mid).removeAll();
      
var nowdate = new Date();
      
      me.editToolItems = [
        { xtype:'datefield',fieldLabel:'开始日期',labelWidth:60,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
              format:'Y-m-d',value:'2019-01-01',selectOnFocus: false, allowBlank: true},
          '-', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:60,width:180,name:'searchenddate',id:mep + 'searchenddate',
              format:'Y-m-d',value:'2019-12-31',selectOnFocus:false,allowBlank:true},
          ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]
//      me.editToolItems = [
//        { xtype:'datefield',fieldLabel:'开始日期',labelWidth:60,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
//          format:'Y-m-d',value:new Date(nowdate.getFullYear(),0,1),selectOnFocus: false, allowBlank: true},
//          '-', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:60,width:180,name:'searchenddate',id:mep + 'searchenddate',
//            format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
//            ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]
      
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
      
      me.OnServerCountyChart();
      
      var chart = Ext.create('Ext.ux.COChartsComponent');
      
      me.plBottom = Ext.create('Ext.Panel', {
        frame:false,
        autoScroll : false,
        region: 'south',
        width:'100%',
        height:'97%',
        items:me.chart,
        title: me.editTitle,
        margins: '2 2 2 5',
        padding: '0 0 0 0'
      });
      
      Ext.getCmp('tpanel' + me.mid).add(me.plBottom);
    },
    
    OnServerCountyChart:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var nowyear = tools.GetValue(mep + 'searchenddate').getFullYear();
      
      var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
      var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
      
      var cuorderinfo = tools.JsonGet('FormGetCusOrder.do?co.begindate='+begindate+'&co.enddate='+enddate).data;

      var provincenames = new Array();
      var procounts = new Array();
      
      if(cuorderinfo.length>0){
        var maxcount = cuorderinfo[0].procount;
      }else{
        var maxcount = 10;
      }
      
      var serverdata = [
             {name: '北京', value: 0},
             {name: '天津', value: 0},
             {name: '上海', value: 0},
             {name: '重庆', value: 0},
             {name: '河北', value: 0},
             {name: '河南', value: 0},
             {name: '云南', value: 0},
             {name: '辽宁', value: 0},
             {name: '黑龙江', value: 0},
             {name: '湖南', value: 0},
             {name: '安徽', value: 0},
             {name: '山东', value: 0},
             {name: '新疆', value: 0},
             {name: '江苏', value: 0},
             {name: '浙江', value: 0},
             {name: '江西', value: 0},
             {name: '湖北', value: 0},
             {name: '广西', value: 0},
             {name: '甘肃', value: 0},
             {name: '山西', value: 0},
             {name: '内蒙古', value: 0},
             {name: '陕西', value: 0},
             {name: '吉林', value: 0},
             {name: '福建', value: 0},
             {name: '贵州', value: 0},
             {name: '广东', value: 0},
             {name: '青海', value: 0},
             {name: '西藏', value: 0},
             {name: '四川', value: 0},
             {name: '宁夏', value: 0},
             {name: '海南', value: 0},
             {name: '台湾', value: 0},
             {name: '香港', value: 0},
             {name: '澳门', value: 0}
         ]
      
      for(var i = 0; i < serverdata.length; i++){
        for(var j = 0; j < cuorderinfo.length; j++){
          if(cuorderinfo[j].provincename.indexOf(serverdata[i].name)!=-1){
            serverdata[i].value = cuorderinfo[j].procount;
          }
        }
      }
      
      for(var i = cuorderinfo.length - 1; i >= 0; i--){
        provincenames.push(cuorderinfo[i].provincename);
        procounts.push(cuorderinfo[i].procount);
      }
      me.chart = tools.CusOrder(nowyear,maxcount,serverdata,provincenames,procounts);
    },
    
    OnSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var begindate = tools.GetValue(mep + 'searchbegindate');
      var enddate = tools.GetValue(mep + 'searchenddate');
      
      if(enddate<begindate){
        tools.alert('开始日期不能大于结束日期，请修改！');
        
        return false;
      }
      
      if(me.plBottom){
        me.plBottom.hide();
      }
      
      me.CreateBottom();
    }
  
});