Ext.define('alms.sendsample', {
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
      
      var lschart = Ext.create('Ext.ux.LSChartsComponent');
      
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
      var deptid = gpersist.GetUserInfo().dept.deptid;
      var nowmonth = tools.GetValue(mep + 'searchenddate').getMonth();
      var nowyear = tools.GetValue(mep + 'searchenddate').getFullYear();
      
      var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
      var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
      
      var sendsampleinfo = tools.JsonGet('FormGetSendSample.do?ss.begindate='+begindate+'&ss.enddate='+enddate).data;

      var monthinfo = new Array();
      var sendinfoone = new Array();
      var sendinfofive = new Array();
      var sendinfotwo = new Array();
      var sendinfothree = new Array();
      var sendinfofour = new Array();
      var sendinfosix = new Array();
      var sendinfoseven = new Array();
      var sendinfoeight = new Array();
      var sendinfonine = new Array();
      var sendinfoten = new Array();
      var sendyearone = new Array();
      var sendyearthree = new Array();
      var sendyearfour = new Array();
      var sendyearfive = new Array();
      var sendyeartwo = new Array();
      var sendyearsix = new Array();
      var sendyearseven = new Array();
      var sendyeareight = new Array();
      var sendyearnine = new Array();
      var sendyearten = new Array();

      
      for(i = 0; i < 12; i++){
        monthinfo.push((i+1)+'月');
        sendinfoone.push({name:(i+1),value:0});
        sendinfotwo.push({name:(i+1),value:0});
        sendinfothree.push({name:(i+1),value:0});
        sendinfofour.push({name:(i+1),value:0});
        sendinfofive.push({name:(i+1),value:0});
        sendinfosix.push({name:(i+1),value:0});
        sendinfoseven.push({name:(i+1),value:0});
        sendinfoeight.push({name:(i+1),value:0});
        sendinfonine.push({name:(i+1),value:0});
        sendinfoten.push({name:(i+1),value:0});
      }
      
      if(sendsampleinfo.length>0){
        for(var i = 0; i < 12; i++){
          for(var j = 0; j < sendsampleinfo.length; j++){
            if(i >= 0 && i <= nowmonth) {
              if(sendsampleinfo[j].gettype == '01'){
                if(sendinfoone[i].name.toString() == sendsampleinfo[j].month){
                  sendinfoone[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '02'){
                if(sendinfotwo[i].name.toString() == sendsampleinfo[j].month){
                  sendinfotwo[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '03'){
                if(sendinfothree[i].name.toString() == sendsampleinfo[j].month){
                  sendinfothree[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '04'){
                if(sendinfofour[i].name.toString() == sendsampleinfo[j].month){
                  sendinfofour[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '05'){
                if(sendinfofive[i].name.toString() == sendsampleinfo[j].month){
                  sendinfofive[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '06'){
                if(sendinfosix[i].name.toString() == sendsampleinfo[j].month){
                  sendinfosix[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '07'){
                if(sendinfoseven[i].name.toString() == sendsampleinfo[j].month){
                  sendinfoseven[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '08'){
                if(sendinfoeight[i].name.toString() == sendsampleinfo[j].month){
                  sendinfoeight[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '09'){
                if(sendinfonine[i].name.toString() == sendsampleinfo[j].month){
                  sendinfonine[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
              if(sendsampleinfo[j].gettype == '10'){
                if(sendinfoten[i].name.toString() == sendsampleinfo[j].month){
                  sendinfoten[i].value = sendsampleinfo[j].growcount;
                } 
              }
              
            } else{
              sendinfoone[i].value = '';
              sendinfotwo[i].value = '';
              sendinfothree[i].value = '';
              sendinfofour[i].value = '';
              sendinfofive[i].value = '';
              sendinfosix[i].value = '';
              sendinfoseven[i].value = '';
              sendinfoeight[i].value = '';
              sendinfonine[i].value = '';
              sendinfoten[i].value = '';
            }
          }
        }
      }

      for(var i = 0; i < sendinfoone.length; i++){
        sendyearone.push(sendinfoone[i].value);
      }
      
      for(var i = 0; i < sendinfotwo.length; i++){
        sendyeartwo.push(sendinfotwo[i].value);
      }
      
      for(var i = 0; i < sendinfothree.length; i++){
        sendyearthree.push(sendinfothree[i].value);
      }
      
      for(var i = 0; i < sendinfofour.length; i++){
        sendyearfour.push(sendinfofour[i].value);
      }
      
      for(var i = 0; i < sendinfofive.length; i++){
        sendyearfive.push(sendinfofive[i].value);
      }
      
      for(var i = 0; i < sendinfosix.length; i++){
        sendyearsix.push(sendinfosix[i].value);
      }
      
      for(var i = 0; i < sendinfoseven.length; i++){
        sendyearseven.push(sendinfoseven[i].value);
      }
      
      for(var i = 0; i < sendinfoeight.length; i++){
        sendyeareight.push(sendinfoeight[i].value);
      }
      
      for(var i = 0; i < sendinfonine.length; i++){
        sendyearnine.push(sendinfonine[i].value);
      }
      
      for(var i = 0; i < sendinfoten.length; i++){
        sendyearten.push(sendinfoten[i].value);
      }
      
      me.lschart = tools.SendSample(nowyear,monthinfo,sendyearone,sendyeartwo,sendyearthree,sendyearfour,sendyearfive,
        sendyearsix,sendyearseven,sendyeareight,sendyearnine,sendyearten);
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