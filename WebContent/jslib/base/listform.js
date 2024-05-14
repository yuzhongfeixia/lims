Ext.define('gpersist.base.listform', {
  extend :'gpersist.base.baseform',
  
  plGrid: null,    
  columnUrl: '',  
  storeUrl: '',    
  expUrl: '',    
  tbGrid: null,    
  columns: [],    
  fields: [],    
  gridStore: null,    
  hasPage: true,
  plDownGrid: null,
  downStore: null,
  hasExportBegin: false,  
  hasExportPage: false,    
  hasIconOnly: false,    
  hasPageSize: true,
  hasAutoLoad: true,
  hasColumnUrl: false,
  hasRptGrid: false,
  hasGridSelect: false,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columns = [];
    me.fields = [];
    me.plGrid = null;
    me.columnUrl = '';
    me.storeUrl = '';
    me.plDownGrid = null;
    me.downStore = null;
    me.expUrl = '';
    me.gridStore = null;
    me.hasPage = true;
    me.hasExportBegin = false;
    me.hasExportPage = false;
    me.hasIconOnly = false;
    me.hasPageSize = true;
    me.hasAutoLoad = true;
    me.hasColumnUrl = false;
    me.hasRptGrid = false;
    me.hasGridSelect = false;
  },
  
  OnInitGridToolBar: function () {
    var me = this;
    
    me.tbGrid = Ext.create('Ext.container.Container', {
      listeners: {
        afterRender: function (form, options) {
          this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
            enter: me.OnSearch,
            scope: me
          });
        }
      }
    });
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
               
    if (me.hasIconOnly) {
      items = [
        ' ', { id: mep + 'btnSearch', text: '', tooltip: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnPrint', text: '', tooltip: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        ' ', { id: mep + 'btnExport', text: '', tooltip: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
        ' ', { text: '', tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
      ];  
    }

    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
               
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items});
               
    me.tbGrid.add(toolbar);
  },
  
  OnAfterFormLoad: function () {
    
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var date = new Date();
    
    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    me.fields = [];
    me.columns = [];
    
    if (me.hasColumnUrl) 
      tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_');
    else {
      if (!me.hasRptGrid)
        tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
      else
        tools.GetRptColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
    }
    
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', me.OnStoreBeforeLoad, me);
    }
      
    me.OnBeforeFormLoad();

    tools.log('OnFormLoad1', date);
    
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id: mep + 'grid',
      region: 'center',
      frame: false,
      border: false,
      margins: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: me.columns,
      store: me.gridStore,
      tbar: me.tbGrid,
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners: {
    	  'itemdblclick' : {fn: me.OnShow, scope:me },
    	  'itemclick' : { fn : me.OnItemClick, scope : me }
      }
    });
        
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store: me.gridStore,
        displayInfo: true,
        displayMsg: gpersist.STR_PAGE_FMT,
        emptyMsg: gpersist.STR_NO_DATA,
        dock: 'bottom'
      }));
    }
    
    tools.log('OnFormLoad2', date);
    
    me.OnAfterFormLoad();
    
    tools.log('OnFormLoad3', date);
    
    Ext.getCmp('tpanel' + me.mid).add(me.plGrid);
    
    tools.log('OnFormLoad4', date);
    
    if (me.hasAutoLoad)
      me.OnSearch();    
    
    tools.log('OnFormLoad5', date);
  },
  
  OnGetSearchParam: function () {    
    return {};
  },
  
  OnStoreBeforeLoad: function (store, options) { 
    var me = this;
    
    Ext.apply(store.proxy.extraParams, me.OnGetSearchParam());
  },
  
  OnBeforeSearch: function () {
    //var me = this, mep = me.tranPrefix;
    
    //if (me.plGrid.store) {
    //  me.plGrid.store.un('beforeload', me.OnStoreBeforeLoad, me);
      
    //  me.plGrid.store.on('beforeload', me.OnStoreBeforeLoad, me);
    //}
  },
  
  OnSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid && me.plGrid.store) {
      me.OnBeforeExportCheck();
      
      me.OnBeforeSearch();
      
      if (me.hasPage) {
        if (me.hasPageSize)
          me.plGrid.store.pageSize = tools.GetValue(mep + 'btnPageSize') || gpersist.DEFAULT_PAGESIZE;
        me.plGrid.store.loadPage(1);
      }
      else
        me.plGrid.store.load();
    }
  },
  
  OnShow: function () {
    
  },
  
  OnItemClick: function () {
	    
  },
  
  OnBeforeExportCheck: function () {
    return false;
  },
  
  OnGetExportParams: function (params) {
    var me = this;
    
    Ext.apply(params, me.OnGetSearchParam());
  },
  
  OnBeforeExport: function () {
    
  },
  
  OnExport: function () {
    var me = this;

    if (me.plGrid.store.getCount() <= 0) {
      tools.alert(gpersist.STR_MSG_NOEXP);
      return;
    }
    
    me.OnBeforeExportCheck();
    
    if (Ext.isEmpty(me.expUrl))
      return;
    
//    var maskExcel = new Ext.LoadMask(Ext.getBody(), {   
//      msg : gpersist.STR_MSG_EXCEL  
//    });
//    
//    maskExcel.show();
//    
//    var iframe = document.getElementById('export');
//    
//    iframe.onreadystatechange = function () {maskExcel.hide();};

    var plExport = Ext.getCmp('plexport');
    
    var params = {
      columns: tools.GetExportColumn(me.plGrid),
      start: me.hasExportBegin ? 0 : (me.plGrid.store.currentPage - 1) * me.plGrid.store.pageSize,
      expcnt: me.hasExportPage ? me.plGrid.store.pageSize : 0,
      hasexport: true
    };
    
    me.OnGetExportParams(params);

    plExport.form.submit({
      url: me.expUrl,
      params: params,
      target: 'export'
    });
  },
  
  OnBeforePrint: function () {
    
  },
  
  OnPrint: function () {
    var me = this;
    
    me.OnBeforePrint();
    Ext.ux.grid.Printer.print(me.plGrid);
  }
});