Ext.define('alms.devusecount', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();
    
    var nowdate = new Date();
    
//    me.editToolItems = [
//      { xtype:'datefield',fieldLabel:'开始日期',labelWidth:60,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
//            format:'Y-m-d',value:new Date(nowdate.getFullYear(),0,1),selectOnFocus: false, allowBlank: true},
//        '-', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:60,width:180,name:'searchenddate',id:mep + 'searchenddate',
//            format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
//        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]
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
    
    var chart = Ext.create('Ext.ux.DUCChartsComponent');
    
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
    var deptid = gpersist.GetUserInfo().dept.deptid;
    var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
    var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
    
    var devuseinfo = tools.JsonGet('FormGetDevUseCount.do?duc.deptid='+deptid+'&duc.begindate='+begindate+'&duc.enddate='+enddate).data;
    var devnames = new Array();
    var devusecount = new Array();

    for(var i = devuseinfo.length - 1; i >= 0; i--){
      devnames.push(devuseinfo[i].devname);
      devusecount.push(devuseinfo[i].devusecount);
    }

    me.chart = tools.DevUseCount(nowyear,devnames,devusecount);
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