Ext.define('alms.labparameter', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;

      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      
      Ext.getCmp('tpanel' + me.mid).removeAll();
      
      var nowdate = new Date();
      
//      me.editToolItems = [
//        { xtype:'datefield',fieldLabel:'开始日期',labelWidth:60,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
//              format:'Y-m-d',value:new Date(nowdate.getFullYear(),0,1),selectOnFocus: false, allowBlank: true},
//          '-', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:60,width:180,name:'searchenddate',id:mep + 'searchenddate',
//              format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
//          ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]
//      
      me.editToolItems = [
        { xtype:'datefield',fieldLabel:'开始日期',labelWidth:60,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
              format:'Y-m-d',value:'2019-01-01',selectOnFocus: false, allowBlank: true},
          '-', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:60,width:180,name:'searchenddate',id:mep + 'searchenddate',
              format:'Y-m-d',value:'2019-12-31',selectOnFocus:false,allowBlank:true},
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
      
      me.OnServerCountyChart();
      
      var chart = Ext.create('Ext.ux.SOChartsComponent');
      
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
      
      var deptid = gpersist.GetUserInfo().dept.deptid;
      var nowyear = tools.GetValue(mep + 'searchenddate').getFullYear();
      
      var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
      var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
      
      var labparaminfo = tools.JsonGet('FormGetLabParameterByYear.do?lp.deptid='+deptid+'&lp.begindate='+begindate+'&lp.enddate='+enddate).data;
      
      var testusernames = new Array();
      var paramcounts = new Array();
      
      for(var i = labparaminfo.length - 1; i >= 0; i--){
        testusernames.push(labparaminfo[i].testusername);
        paramcounts.push(labparaminfo[i].parametercount);
      }
      
      me.chart = tools.ServerInfo(nowyear,testusernames,paramcounts);
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