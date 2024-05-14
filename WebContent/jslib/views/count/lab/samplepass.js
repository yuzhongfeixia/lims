Ext.define('alms.samplepass', {
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
      
      var begindate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
      var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
      
      var sampassinfo = tools.JsonGet('FormGetSamplePass.do?sp.begindate='+begindate+'&sp.enddate='+enddate).data;
      if(sampassinfo){
        var kindnames = [];
        var sampass = [];
        var samnopass = [];
        var pass = new Array();
        var nopass = new Array();
        
        for(var i = 0; i < sampassinfo.length; i++){ 
          if (kindnames.indexOf(sampassinfo[i].kindname) == -1){
            kindnames.push(sampassinfo[i].kindname);
            pass.push({name:sampassinfo[i].kindname,value:0});
            nopass.push({name:sampassinfo[i].kindname,value:0});
          }
        }
        
        for(var i = 0; i < pass.length; i++){
          for(var j = 0; j < sampassinfo.length; j++){
            if(sampassinfo[j].ispass == '合格'){
              if(pass[i].name.toString() == sampassinfo[j].kindname){
                pass[i].value = sampassinfo[j].passcount;
              }
            } 
          }
        }
        
        for(var i = 0; i < nopass.length; i++){
          for(var j = 0; j < sampassinfo.length; j++){
            if(sampassinfo[j].ispass == '不合格'){
              if(nopass[i].name.toString() == sampassinfo[j].kindname){
                nopass[i].value = sampassinfo[j].passcount;
              }
            } 
          }
        }
        
        for(var i = 0; i < pass.length; i++){
          sampass.push(pass[i].value);
        }

        for(var i = 0; i < nopass.length; i++){
          samnopass.push(nopass[i].value);
        }
      }
      me.lschart = tools.SamplePass(kindnames,sampass,samnopass);
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