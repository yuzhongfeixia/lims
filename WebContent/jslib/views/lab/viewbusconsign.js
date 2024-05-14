Ext.define('alms.viewbusconsign', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '检验委托书查看',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busconsign',
      storeUrl: 'LabSearchBusGet.do',
      expUrl: 'LabSearchBusGet.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      detailTabs: 0,
      hasDetail: false,
      hasGridSelect: true
    });

    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'label', id: mep + 'busconsignhtml', html: '' },
      {xtype:'hiddenfield',name:'bg.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'bg.deal.action',id: mep + 'datadeal'}
    ];
    
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnReview', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { text: '刷新', iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    if (me.gridStore) {
	      me.gridStore.on('beforeload', function (store, options) {      
	        Ext.apply(store.proxy.extraParams, {
	          'bg.gettype': '10',
	          'bg.testedname':tools.GetValueEncode(mep+'searchtestedname')
	        });
	      });
	    };
	  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnPrint', text : '打印', iconCls : 'icon-deal', handler : me.OnPrint, scope : me },      
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.region = 'center';
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusGet.do?bg.tranid='+record.tranid);
    if(item && item.tranid){
      me.displayhtml = almsprint.ConsignHtml(item);
      Ext.fly(Ext.getCmp(mep + 'busconsignhtml').getEl()).update(me.displayhtml);
      tools.SetValue(mep + 'tranid', item.tranid);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnPrint:function(){
    var me = this;
    if (!Ext.isEmpty(me.displayhtml)) {
      Ext.ux.Print.ReportPrint.print(me.displayhtml);
    }
  }
});