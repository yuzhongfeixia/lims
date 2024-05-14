Ext.define('alms.labsample', {
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
      
      var labsample = tools.JsonGet('FormGetLabSampleByMonth.do?ls.deptid='+deptid+'&ls.begindate='+begindate+'&ls.enddate='+enddate).data;
      var monthinfo = new Array();
      var labinfoone = new Array();
      var labinfofive = new Array();
      var labinfotwo = new Array();
      var labinfothree = new Array();
      var labinfofour = new Array();
      var labyearone = new Array();
      var labyearthree = new Array();
      var labyearfour = new Array();
      var labyearfive = new Array();
      var labyeartwo = new Array();
      
      for(i = 0; i < 12; i++){
        monthinfo.push((i+1)+'月');
        labinfoone.push({name:(i+1),value:0});
        labinfotwo.push({name:(i+1),value:0});
        labinfothree.push({name:(i+1),value:0});
        labinfofour.push({name:(i+1),value:0});
        labinfofive.push({name:(i+1),value:0});
      }
      
      if(labsample.length>0){
        for(var i = 0; i < 12; i++){
          for(var j = 0; j < labsample.length; j++){
            if(i >= 0 && i <= nowmonth) {
              if(labsample[j].deptid == '8001'){
                if(labinfoone[i].name.toString() == labsample[j].month){
                  labinfoone[i].value = labsample[j].growcount;
                } 
              }
              
              if(labsample[j].deptid == '8002'){
                if(labinfotwo[i].name.toString() == labsample[j].month){
                  labinfotwo[i].value = labsample[j].growcount;
                } 
              }
              
              if(labsample[j].deptid == '8003'){
                if(labinfothree[i].name.toString() == labsample[j].month){
                  labinfothree[i].value = labsample[j].growcount;
                } 
              }
              
              if(labsample[j].deptid == '8004'){
                if(labinfofour[i].name.toString() == labsample[j].month){
                  labinfofour[i].value = labsample[j].growcount;
                } 
              }
              
              if(labsample[j].deptid == '8005'){
                if(labinfofive[i].name.toString() == labsample[j].month){
                  labinfofive[i].value = labsample[j].growcount;
                } 
              }
            } else{
              labinfoone[i].value = '';
              labinfotwo[i].value = '';
              labinfothree[i].value = '';
              labinfofour[i].value = '';
              labinfofive[i].value = '';
            }
          }
        }
      }

      for(var i = 0; i < labinfoone.length; i++){
        labyearone.push(labinfoone[i].value);
      }
      
      for(var i = 0; i < labinfotwo.length; i++){
        labyeartwo.push(labinfotwo[i].value);
      }
      
      for(var i = 0; i < labinfothree.length; i++){
        labyearthree.push(labinfothree[i].value);
      }
      
      for(var i = 0; i < labinfofour.length; i++){
        labyearfour.push(labinfofour[i].value);
      }
      
      for(var i = 0; i < labinfofive.length; i++){
        labyearfive.push(labinfofive[i].value);
      }
      
      me.lschart = tools.LabSample(nowyear,monthinfo,labyearone,labyeartwo,labyearthree,labyearfour,labyearfive);
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