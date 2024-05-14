Ext.define('alms.viewprdpoison',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'剧毒易制毒化学品使用',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdpoison',
          storeUrl:'PrdSearchPrdPoison.do',
          expUrl:'PrdSearchPrdPoison.do',
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
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '申请编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
//      ' ', { xtype: 'textfield', fieldLabel: '实际单据号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchoutfact', id: mep + 'searchoutfact', allowBlank: true },
//      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '出库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE))
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
           
         'pp.tranid':tools.GetValueEncode(mep+'searchtranid'),
           
//           'stkout.storeid':tools.GetValueEncode(mep+'searchstoreid'),
//           'stkout.tranid':tools.GetValueEncode(mep+'searchtranid'),
//           'stkout.outfact':tools.GetValueEncode(mep+'searchoutfact')
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
    var height = 40;
    var record = tools.JsonGet('PrdGetPrdPoison.do?pp.tranid='+item.tranid);
    var ppds = tools.JsonGet('PrdGetListPrdPoisonDetail.do?ppd.tranid='+item.tranid).data;
    var ppfs = tools.JsonGet('PrdGetListPrdPoisonFile.do?ppf.tranid='+item.tranid).data;
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">'
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>剧毒易制毒化学品使用申请表</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat"  align="center" width="10%">申请编号</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">明细编号</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">耗材名称</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">耗材使用量</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">数量单位</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">备注</td></tr>';
    for(var i=0;i<ppds.length;i++){
        var ppd = ppds[i];
        html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(ppd.tranid) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(ppd.prdserial) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppd.prdname) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppd.prdcount) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppd.prdunit) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(ppd.poiremark) +'</td></tr>';
    }
    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
    html += '<tr class="infotr"><td class="infoat"  align="center" width="10%">申请编号</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">文件序号</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">文件名称</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">文件类别</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">文件类型</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">备注</td></tr>';
    for(var i=0;i<ppfs.length;i++){
      var ppf = ppfs[i];
        html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(ppf.tranid) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(ppf.fileno) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppf.filename) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppf.filecatename) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(ppf.filetypename ) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(ppf.fileremark) +'</td></tr>';
    }
    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranid) +'</td>'+
    '<td class="infoat" align="center" width="10%">申请人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">申请时间</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.trandate) +'</td>'+
    '<td class="infoat" align="center" width="10%">使用项目</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.projectid) +'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">室主任</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.confirmusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">室主任意见</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.confirmdesc) +'</td>'+
    '<td class="infoat" align="center" width="10%">特殊要求说明</td>' +
    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemData(item.remark) +'</td></tr>';
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">审核人意见</td>' +
//    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.checkdesc) +'</td></tr>';
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">审核人</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.checkusername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">记录人</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.recousername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">记录日期</td>' +
//    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemDateData(item.recodate) +'</td></tr>';
//     
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
//    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.remark) +'</td></tr>';
//    
    
    html += '</table>';
    html + '</td></tr>';
    html += '</table>';
    
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
 OnPrevRecord : function() {
   var me = this;
   var mep = me.tranPrefix;
   if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
     Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
       if (btn == 'yes') {
         me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
         me.OnPrevRecord();
       }
     });
   } else {
     var j = me.plGrid.store.getCount(), record;
     for ( var i = 0; i < j; i++) {
       record = me.plGrid.store.getAt(i).data;
   
       if (me.OnCheckPrevNext(record)) {
         if (i == 0) {
           tools.alert('已经是当前列表第一条数据！');
           return;
         }
         if(me.plGrid.store.getAt(i - 1).data.flowstatus != '01'){
           tools.BtnsDisable(['btnFormEdit'], mep);
           tools.BtnsDisable(['btnSubmit'], mep);
         } else{
           tools.BtnsEnable(['btnFormEdit'], mep);
           tools.BtnsEnable(['btnSubmit'], mep);
         }
         me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
         me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
         me.OnFormValidShow();
         return;
       }
     }
   }
 },
 OnNextRecord : function() {
   var me = this;
   var mep = me.tranPrefix;
   if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
     Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
       if (btn == 'yes') {
         me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
         me.OnNextRecord();
       }
     });
   } else {
     var j = me.plGrid.store.getCount(), record;
     for ( var i = 0; i < j; i++) {
       record = me.plGrid.store.getAt(i).data;
     
       if (me.OnCheckPrevNext(record)) {
         if (i == j - 1) {
           tools.alert('已经是当前列表最后一条数据！');
           return;
         }
         if(me.plGrid.store.getAt(i + 1).data.flowstatus != '01'){
           tools.BtnsDisable(['btnFormEdit'], mep);
           tools.BtnsDisable(['btnSubmit'], mep);
         } else{
           tools.BtnsEnable(['btnFormEdit'], mep);
           tools.BtnsEnable(['btnSubmit'], mep);
         }
         me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
         me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
         me.OnFormValidShow();
         return;
       }
     }
   }
 }

  
});