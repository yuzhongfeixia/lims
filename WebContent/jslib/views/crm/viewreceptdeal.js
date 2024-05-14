Ext.define('alms.viewreceptdeal',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'投诉处理',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_crmreceptdeal',
          storeUrl:'CrmSearchCrmReceptDeal.do',
          expUrl:'CrmSearchCrmReceptDeal.do',
          idPrevNext:'tranid',
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
     
//     var searchitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '接待编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
//     ];
//     
     var items = [
        ' ', { xtype: 'textfield', fieldLabel: '处理编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },              
        ' ', { xtype: 'textfield', fieldLabel: '接待编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchreceptid', id: mep + 'searchreceptid', allowBlank: true },          
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnView', text: gpersist.STR_BTN_VIEW, iconCls: 'icon-outlook', handler: me.OnShow,scope: me},
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
     
//     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
//     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     //me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'receptdeal.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
           'receptdeal.receptid': tools.GetEncode(tools.GetValue(mep + 'searchreceptid'))
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'tranid',id: mep + 'tranid'}
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
    var record = tools.OnGridLoad(me.plGrid, '请选择数据！');
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
  
  OnShowGetHtml:function(item){
    var me = this;
    var mep = me.tranPrefix;
    var height = 20;
    var record = tools.JsonGet('CrmGetCrmReceptDeal.do?receptdeal.tranid='+item.tranid);
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>投诉（申诉）受理处理结论表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR 4-7-2</td></tr>';
    html += '</table>';
    
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >申(投)诉人或申(投)诉单位</td>' +
    '<td class="infoc infoleft" colspan=6 align="center" width="40%">'+alms.GetItemData(record.complainobject)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >申(投)诉时间</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemDateData(record.complaindate)+'</td>' +
    '<td class="infoat" align="center" width="20%" >申(投)诉时效</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.prescription)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >受理时间</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemDateData(record.acceptdate)+'</td>' +
    '<td class="infoat" align="center" width="20%" >联系人</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.linkman)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >联系电话</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.linktele)+'</td>' +
    '<td class="infoat" align="center" width="20%" >邮编</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.linkpost)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >地址</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.linkaddr)+'</td></tr>';
     
     html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >申诉或投诉内容</td>' +
   '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.complaindesc)+'</td></tr>';
     html += '<tr class="infotr" ><td class="infoat"  align="center" width="20%" >接受人</td>' +
     '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.acceptusername)+'</td></tr>';
     
     
     html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >受理后<br>处理</td>' +
     '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.acceptdesc)+'</td></tr>';
       html += '<tr class="infotr" ><td class="infoat"  align="center" width="20%" >批准人</td>' +
       '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.acceptdesc)+'</td></tr>';
       
       
       html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >处理结论</td>' +
       '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(record.dealresult)+'</td></tr>';
         html += '<tr class="infotr" ><td class="infoat"  align="center" width="40%" >主任签字:'+alms.GetItemData(record.allowusername)+'</td>' +
         '<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(record.allowdate)+'</td></tr>';
     
    //rowspan=2
//    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="10%" >申诉或投诉内容</td>' +
//    '<td class="infoc infoleft" colspan=3 align="center" width="40%">'+alms.GetItemData(record.complaindesc)+'</td></tr>';
//    //rowspan=2
//    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="10%" >接收人</td>' +
//    '<td class="infoc infoleft" colspan=3 align="center" width="40%">'+alms.GetItemData(record.acceptusername)+'</td></tr>';
    
//    html += '<tr class="infotr" style="height: 100px;"><td class="infoat" rowspan=2 align="center" width="10%" >受理后处理</td>' +
//    '<td class="infoc infoleft" colspan=3 align="center" width="40%">'+alms.GetItemData(record.acceptdesc)+'</td></tr>';
//    
//    html += '<tr class="infotr">' +
//    '<td class="infoat" align="center" width="10%" >批准人</td>' +
//    '<td class="infoc infoleft" colspan=2 align="center" width="40%">'+alms.GetItemData(record.acceptdesc)+'</td></tr>';
//    
//     html += '<tr class="infotr" style="height: 100px;"><td class="infoat" rowspan=2 align="center" width="10%" >处理结论</td>' +
//    '<td class="infoc infoleft" colspan=3 align="center" width="40%">'+alms.GetItemData(record.dealresult)+'</td></tr>';
//   
//    html += '<tr class="infotr">' +
//    '<td class="infoat" align="center" width="10%" >主任</td>' +
//    '<td class="infoc infoleft" align="center" width="20%">'+alms.GetItemData(record.allowusername)+'</td>' +
//    '<td class="infoc infoleft" align="center" width="20%">'+alms.GetItemDateData(record.allowdate)+'</td></tr>';
    
     html += '<tr class="infotr" style="height: 100px;"><td class="infoat" rowspan=2 align="center" width="10%" >备注</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.remark)+'</td></tr>';
    
    html += '</table>';
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
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
//       LODOP.PRINT();//打印功能
   }
 } 
 
  
});