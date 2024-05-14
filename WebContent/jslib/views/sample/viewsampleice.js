Ext.define('alms.viewsampleice',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'样品贮存冰柜温度记录',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_sampleice',
          storeUrl:'SampSearchSampleIce.do',
          expUrl:'SampSearchSampleIce.do',
          idPrevNext:'iceid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [ 
       ' ', { xtype: 'textfield', fieldLabel: '冰柜编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchiceid', id: mep + 'searchiceid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '冰柜名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchicename', id: mep + 'searchicename', allowBlank: true }
       ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnView', text: gpersist.STR_BTN_VIEW, iconCls: 'icon-outlook', handler: me.OnShow,scope: me},
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
     
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'sampice.iceid':tools.GetValueEncode(mep+'searchiceid'),
           'sampice.icename':tools.GetValueEncode(mep+'searchicename')
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'iceid',id: mep + 'iceid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
    me.OnAfterCreateEdit();
  },
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择冰柜！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      }
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    }
  },
  
  OnShowGetHtml:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    var icedetail = tools.JsonGet('SampGetListSampleIceDetail.do','sid.iceid='+record.iceid).data;
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; margin:50 0 20 0;" width="70%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>样品贮存冰柜温度记录表</b></td></tr>';
    html += '</table>';  
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">冰柜品名与型号</td>' +
    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(record.icename)+","+alms.GetItemData(record.icespec)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">容量</td>' +
    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(record.icecapa)+'L</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">出厂日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%" colspan=3 style="height:30px;">'+alms.GetItemDateData(record.factorydate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" colspan="1" width="10%" style="height:30px;">冰柜编号</td>' +
    '<td class="infoc infoleft" align="left" colspan="2" width="15%" colspan=3 style="height:30px;">' +alms.GetItemData(record.iceid)+ '</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%" style="height:30px;">存放地点</td>' +
    '<td class="infoc infoleft" colspan="4" align="left" width="15%" colspan=5 style="height:30px;">'+alms.GetItemData(record.icestore)+'</td></tr>';
    
   
    
    if(icedetail.length > 0){
//      html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
      html += '<tr class="infotr"><td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">日期</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">温度<br/>(℃)min/max</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">签字</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">备注</td>';
      
      html += '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">日期</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">温度<br/>(℃)min/max</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">签字</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:40px;">备注</td></tr>';
      
      for(var i = 0; i < icedetail.length; i++){
        var icetemp = icedetail[i];
        
        if(i%2 == 0){
           html += '<tr class="infotr">' +
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">' +alms.GetItemDateData(icetemp.icedate)+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.lowtemp)+"/"+alms.GetItemData(icetemp.hightemp)+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.iceusername)+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.iceremark)+'</td>';
        }else{
           html += '' +
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">' +alms.GetItemDateData(icetemp.icedate)+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.lowtemp)+"/"+alms.GetItemData(icetemp.hightemp)+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.iceusername)+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(icetemp.iceremark)+'</td>';
        }
      }
      
      if(icedetail.length%2 != 0){
        html += '' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td></tr>';
      }
      
      html += '</tr>';
      
      for(var i = icedetail.length-1; i < 24; i++){
         html += '<tr class="infotr">' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;"></td></tr>';
      }
      
      html += '<tr class="infoc infoleft"><td colspan="8" align="left" width="100%" style="height: 60px;">备注：如有意外情况发生，如停电、故障等，需在备注中记载时间、温度变化及处理措施等。</td></tr>';
      
      html += '</table>';
    }
    me.html =  html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.iceid)) {
      tools.SetValue(mep + 'iceid',item.iceid);
      me.tabMain.setActiveTab(1);
      me.OnShowGetHtml(item);
    }
    return true;
 },
    OnPrintTask: function () {
     var me = this;
     var mep = me.tranPrefix;
     if (!Ext.isEmpty(me.html)) {
       var LODOP = getLodop();
       LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
       LODOP.PRINT_INIT("样品任务单打印");
       LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
       LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
       LODOP.SET_PRINTER_INDEXA(-1);
       LODOP.PREVIEW();//预览功能
//         LODOP.PRINT();//打印功能
     }
   }
  
});