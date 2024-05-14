Ext.define('alms.viewstkcheck',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材盘点',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_stkcheck',
          storeUrl:'PrdSearchStkCheck.do',
          expUrl:'PrdSearchStkCheck.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '盘点单号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '出库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE))
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
           'stkcheck.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'stkcheck.storeid':tools.GetValueEncode(mep+'searchstoreid')
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
    var record = tools.JsonGet('PrdGetStkCheck.do?stkcheck.tranid='+item.tranid);
    var checks = tools.JsonGet('PrdGetListStkCheckDetail.do?chdetail.tranid='+item.tranid).data;
      
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">'
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>'+item.deptname+'耗材盘点</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan=2 align="center" width="10%">物品名称</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">库存数量</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">盘点数量</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">盘盈盘亏数量</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">备注</td></tr>';
    
    for(var i=0;i<checks.length;i++){
        var check = checks[i];
        html += '<tr class="infotr">' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(check.prdname) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(check.kcnumber) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(check.prdnumber) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(check.yknumber) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(check.remark) +'</td></tr>';
    }
    
    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">盘点单号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranid) +'</td>'+
    '<td class="infoat" align="center" width="10%">仓库</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.storename) +'</td>'+
    '<td class="infoat" align="center" width="10%">盘点人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">盘点日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.checkdate) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测室</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.deptname) +'</td>'+
    '<td class="infoat" align="center" width="10%">记录人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.recousername) +'</td>'+
    '<td class="infoat" align="center" width="10%">记录日期</td>' +
    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemDateData(item.recodate) +'</td></tr>';
     
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:40px;">备注</td>' +
    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.remark) +'</td></tr>';
    
    
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
 }  
  
});