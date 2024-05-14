Ext.define('alms.prdoutput', {
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
//              ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '领出部门', 70, tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE)),
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
      
      me.OnYearChar();
      
      var lschart = Ext.create('Ext.ux.POPChartsComponent');
      
      me.plBottom = Ext.create('Ext.Panel', {
        frame:false,
        autoScroll : false,
        region: 'south',
        width:'100%',
        height:'97%',
        items:me.lschart,
        title: me.editTitle,
        margins: '2 2 2 5',
        padding: '0 0 0 0'
      });
      
      Ext.getCmp('tpanel' + me.mid).add(me.plBottom);
    },
    
    OnYearChar:function(){
      var me = this;
      var mep = me.tranPrefix;

      var nowmonth = tools.GetValue(mep + 'searchenddate').getMonth();
      var nowyear = tools.GetValue(mep + 'searchenddate').getFullYear();
      var deptid = tools.GetValue(mep + 'searchdeptid')
      
      var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
      var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
      
      var prdininfo = tools.JsonGet('FormGetPrdIn.do?pi.begindate='+begindate+'&pi.enddate='+enddate).data;
      var prdoutinfo = tools.JsonGet('FormGetPrdOut.do?po.begindate='+begindate+'&po.enddate='+enddate+'&po.deptid='+deptid).data;

      var monthinfo = new Array();
      var prdins = new Array();
      var prdouts = new Array();
      var prdinsyear = new Array();
      var prdoutsyear = new Array();
      
      for(i = 0; i < 12; i++){
        monthinfo.push((i+1)+'月份');
        prdins.push({name:(i+1),value:0});
        prdouts.push({name:(i+1),value:0});
      }
      
      for(var i = 0; i < prdins.length; i++){
        for(var j = 0; j < prdininfo.length; j++){
          if(i >= 0 && i <= nowmonth) {
            if(prdins[i].name.toString() == prdininfo[j].month){
              prdins[i].value = prdininfo[j].incount;
            } 
          } else{
            prdins[i].value = '';
          }
        }
      }
      
      for(var i = 0; i < prdouts.length; i++){
        for(var j = 0; j < prdoutinfo.length; j++){
          if(i >= 0 && i <= nowmonth) {
            if(prdouts[i].name.toString() == prdoutinfo[j].month){
              prdouts[i].value = prdoutinfo[j].outcount;
            } 
            
          } else{
            prdouts[i].value = '';
          }
        }
      }

      for(var i = 0; i < prdins.length; i++){
        prdinsyear.push(prdins[i].value);
      }
      
      for(var i = 0; i < prdouts.length; i++){
        prdoutsyear.push(prdouts[i].value);
      }
      
      me.lschart = tools.PrdOutPut(nowyear, monthinfo,prdinsyear,prdoutsyear);
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