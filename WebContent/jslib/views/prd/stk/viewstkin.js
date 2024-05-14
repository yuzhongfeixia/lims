Ext.define('alms.viewstkin',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材出库',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_stkin',
          storeUrl:'PrdSearchStkIn.do',
          expUrl:'PrdSearchStkIn.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '入库单号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '实际单据号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchinfact', id: mep + 'searchinfact', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '入库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE))
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
           'stkin.storeid':tools.GetValueEncode(mep+'searchstoreid'),
           'stkin.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'stkin.infact':tools.GetValueEncode(mep+'searchinfact')
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
    var record = tools.JsonGet('PrdGetStkIn.do?stkin.tranid='+item.tranid);
    var ins = tools.JsonGet('PrdGetListStkInDetail.do?indetail.tranid='+item.tranid).data;
      
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">'
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>耗材入库</b></td></tr>';
    
    for(var i=0;i<ins.length;i++){
        var stkin = ins[i];
        
        html += '<tr class="infotr"><td class="infoat"  align="center" width="10%">条码前缀</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">物品名称</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">入库数量</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">入库单位数量</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">入库总数</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">入库单价</td>' +
        '<td class="infoat" align="center" colspan="2" width="10%">入库金额</td></tr>';
        
        html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(stkin.prdcodeprefix) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.prdname) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.prdnumber) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.unitnumber) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.factnumber) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.prdprice) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(stkin.prdamount) +'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat"  align="center" width="10%">供应商</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">生产厂商</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">生产日期</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">有效期</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">购买人</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">购买日期</td>' +
        '<td class="infoat" align="center" colspan="2" width="10%">备注</td></tr>';
    
        html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(stkin.tradename) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.factoryname) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemDateData(stkin.factorydate) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.validdate) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(stkin.buyusername) +'</td>' +
        '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemDateData(stkin.buydate) +'</td>' +
        '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(stkin.remark) +'</td></tr>';
    }
    
    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出库单号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranid) +'</td>'+
    '<td class="infoat" align="center" width="10%">实际单据号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.infact) +'</td>'+
    '<td class="infoat" align="center" width="10%">仓库</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.storename) +'</td>'+
    '<td class="infoat" align="center" width="10%">出库日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.indate) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出库类型</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.stockintypename) +'</td>'+
    '<td class="infoat" align="center" width="10%">入库人编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.inuser) +'</td>'+
    '<td class="infoat" align="center" width="10%">入库人姓名</td>' +
    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemData(item.inusername) +'</td></tr>';
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">审核人意见</td>' +
//    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.checkdesc) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">审核人</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.checkusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">记录人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.recousername) +'</td>'+
    '<td class="infoat" align="center" width="10%">记录日期</td>' +
    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemDateData(item.recodate) +'</td></tr>';
     
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
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