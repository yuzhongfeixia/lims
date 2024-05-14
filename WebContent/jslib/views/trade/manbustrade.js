Ext.define('alms.manbustrade', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '供应商管理',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustrade',
      storeUrl: 'DevSearchBusTrade.do',
      saveUrl: 'DevSaveBusTrade.do',
      hasGridSelect: true,
      expUrl: 'DevSearchBusTrade.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tradeid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('供应商编号', 'bustrade.tradeid', mep + 'tradeid', 20, '96%', false),
          tools.FormText('联 系 人', 'bustrade.linkman', mep + 'linkman', 10, '96%', false, 2),
          tools.FormText('邮政编码', 'bustrade.linkpost', mep + 'linkpost', 6, '96%', false, 4)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('供应商名称', 'bustrade.tradename', mep + 'tradename', 40, '100%', false, 1),
          tools.FormText('联系电话', 'bustrade.linktele', mep + 'linktele', 32, '100%', false, 3,'isphone'),
          tools.FormCombo('供应商状态', 'bustrade.tradestatus', mep + 'tradestatus', tools.ComboStore('TradeStatus', gpersist.SELECT_MUST_VALUE), '100%', false, 5),
          {xtype:'hiddenfield',name:'bustrade.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('单位地址', 'bustrade.linkaddress', mep + 'linkaddress', 120, '100%', true, 6, 3),
      tools.FormTextArea('内容和评价', 'bustrade.tradecontent', mep + 'tradecontent', 500, '100%', true, 6, 4),
      tools.FormTextArea('备注', 'bustrade.remark', mep + 'remark', 120, '100%', true, 7, 3)
    ];
    
    me.disNews = ['tradeid'];
    me.disEdits = ['tradeid'];
    me.enNews = ['linkman','linkpost','tradename','linktele','tradestatus','linkaddress','tradecontent','remark'];
    me.enEdits = [ 'linkman','linkpost','tradename','linktele','tradestatus','linkaddress','tradecontent','remark'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [

      ' ', {xtype:'textfield',fieldLabel:'供应商名称',labelWidth:70,width:180,maxLength:20,name:'searchtradename',id:mep + 'searchtradename',allowBlank:true},           

      ' ', {xtype:'textfield',fieldLabel:'供应商名称',labelWidth:70,width:200,maxLength:20,name:'searchtradename',id:mep + 'searchtradename',allowBlank:true},           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
     tools.SetToolbar(items, mep);
    
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
     me.tbGrid.add(toolbar);
   },
   
   OnInitData: function () {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tradestatus', gpersist.SELECT_MUST_VALUE);

   },
   
   OnBeforeSearch: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.gridStore) {
       me.gridStore.on('beforeload', function (store, options) {      
         Ext.apply(store.proxy.extraParams, {
           'bustrade.tradename': tools.GetEncode(tools.GetValue(mep + 'searchtradename'))
         });
       });
     };
   },
   
   OnBeforeSave:function(){
	  var me = this;
	  var mep = me.tranPrefix;
	  var tradestatus = Ext.getCmp(mep+'tradestatus').getValue();
	    if(tradestatus == gpersist.SELECT_MUST_VALUE ){
	        tools.alert('请选择供应商状态！');
	        return;
	    }
	  return true;
   },
   
   OnLoadData: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     var item = tools.JsonGet(tools.GetUrl('DevGetBusTrade.do?bustrade.tradeid=') + record.tradeid);
     tools.SetValue(mep + 'tradeid', item.tradeid);
	   tools.SetValue(mep + 'tradename', item.tradename);
	   tools.SetValue(mep + 'linkman', item.linkman);
	   tools.SetValue(mep + 'linktele', item.linktele);
	   tools.SetValue(mep + 'linkaddress', item.linkaddress);
	   tools.SetValue(mep + 'tradecontent', item.tradecontent);
	   tools.SetValue(mep + 'linkpost', item.linkpost);
	   tools.SetValue(mep + 'tradestatus', item.tradestatus);
	   tools.SetValue(mep + 'remark', item.remark);
     return true;
   },
   
   OnAfterCopy: function () {
     var me = this;
     var mep = me.tranPrefix;
    
     Ext.getCmp(mep + 'tradeid').reset();
     Ext.getCmp(mep + 'tradeid').focus(true, 500);
   },
   
   OnAfterSave : function(action) {
     var me = this;
     var mep = me.tranPrefix;
    
     if (action.result && action.result.data) {
       if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
         if (action.result.data.tradeid) {
           tools.SetValue(mep + 'tradeid', action.result.data.tradeid);
         }
       }
     }
   }
   
});
