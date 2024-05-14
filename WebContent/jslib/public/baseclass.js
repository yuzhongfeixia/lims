// region gpersist.base

Ext.define('gpersist.base', {

  mid: 0,  
  tranPrefix: '0000',
  selectParams: [],
  
  constructor : function(config) {
    var me = this;
    
    me.OnInitConfig();
    
    Ext.apply(me, config);

    tools.GetParams(me.selectParams);
    me.OnFormLoad();
  },
  
  OnFormLoad : function() {
    
  },
  
  OnInitConfig: function () {
    var me = this;
    
    me.mid = 0;
    me.tranPrefix = '0000';
    me.selectParams = [];
  }
});

// endregion


Ext.define('gpersist.listform', {
  extend :'gpersist.base',
  
  plGrid: null,    
  columnUrl: '',  
  storeUrl: '',    
  expUrl: '',    
  tbGrid: null,    
  columns: [],    
  fields: [],    
  gridStore: null,    
  hasPage: true,    
  hasExportBegin: false,  
  hasExportPage: false,    
  hasIconOnly: false,    
  hasPageSize: true,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columns = [];
    me.fields = [];
    me.plGrid = null;
    me.columnUrl = '';
    me.storeUrl = '';
    me.expUrl = '';
    me.gridStore = null;
    me.hasPage = true;
    me.hasExportBegin = false;
    me.hasExportPage = false;
    me.hasIconOnly = false;
    me.hasPageSize = true;
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep);
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);
    
    me.OnBeforeCreateGrid();
    
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
      listeners: {'itemdblclick' : {fn: me.OnShow, scope:me } }
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
    
    me.OnAfterCreateGrid();
    
    Ext.getCmp('tpanel' + me.mid).add(me.plGrid);
    
    me.OnSearch();
    
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
  
  OnBeforeCreateGrid: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-refresh', handler: me.OnResetForm, scope: me }
    ];    
               
    if (me.hasIconOnly) {
      items = [
        ' ', { id: mep + 'btnSearch', text: '', tooltip: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnPrint', text: '', tooltip: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        ' ', { id: mep + 'btnExport', text: '', tooltip: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
        ' ', { text: '', tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-refresh', handler: me.OnResetForm, scope: me }
      ];  
    }
    
    if (me.hasPageSize)
      Ext.Array.insert(items, 6, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.CON_PAGESIZE_MIN, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
               
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items});
               
    me.tbGrid.add(toolbar);
  },
  
  OnAfterCreateGrid: function () {
    
  },
  
  OnBeforeReset: function () {
    return true;
  },
  
  OnResetForm: function () {
    var me = this;
    
    if (!me.OnBeforeReset())
      return;
    
    me.OnFormLoad(me.mid);
  },
  
  OnBeforeSearch: function () {
    
  },
  
  OnSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid && me.plGrid.store) {
      
      me.OnBeforeSearch();
      
      if (me.hasPage) {
        if (me.hasPageSize)
          me.plGrid.store.pageSize = tools.GetValue(mep + 'btnPageSize') || 20;
        me.plGrid.store.loadPage(1);
      }
      else
        me.plGrid.store.load();
    }
  },
  
  OnShow: function () {
    
  },
  
  OnGetExportParams: function (params) {
    return params || {};
  },
  
  OnBeforeExport: function () {
    
  },
  
  OnExport: function () {
    var me = this;

    if (me.plGrid.store.getCount() <= 0) {
      tools.alert(gpersist.STR_MSG_NOEXP);
      return;
    }
    
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
      expcnt: me.hasExportPage ? 0 : me.plGrid.store.pageSize
    };
    
    params = me.OnGetExportParams(params);
    
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

Ext.define('gpersist.listdateform', {
  extend :'gpersist.listform',
  
  OnBeforeCreateGrid : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield',fieldLabel:gpersist.STR_BTN_BEGINDATE,labelWidth:60,width:160,name:'begindate',id:mep + 'begindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'enddate',id:mep + 'enddate',
        format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
      ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me },       
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPageSize)
      Ext.Array.insert(items, 14, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.CON_PAGESIZE_MIN, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    me.plGrid.store.on('beforeload', function (store, options) {      
      Ext.apply(store.proxy.extraParams, {
        searchbegin: tools.GetValue(mep + 'begindate') ? Ext.Date.format(tools.GetValue(mep + 'begindate'), 'Y-m-d') : '', 
        searchend: tools.GetValue(mep + 'enddate') ? Ext.Date.format(tools.GetValue(mep + 'enddate'), 'Y-m-d') : ''});
    });
  },
  
  OnBeforeExport: function (params) {
    params = params || {};
    
    Ext.apply({
      searchbegin: tools.GetValue(mep + 'begindate') ? Ext.Date.format(tools.GetValue(mep + 'begindate'), 'Y-m-d') : '', 
      searchend: tools.GetValue(mep + 'enddate') ? Ext.Date.format(tools.GetValue(mep + 'enddate'), 'Y-m-d') : ''} 
    , params);
    
    return params;
  },
  
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'begindate', Ext.Date.add(tools.GetValue(mep + 'begindate'), Ext.Date.DAY, -1));
    tools.SetValue(mep + 'enddate', Ext.Date.add(tools.GetValue(mep + 'enddate'), Ext.Date.DAY, -1));

    me.OnSearch();
  },
  
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'begindate', Ext.Date.add(tools.GetValue(mep + 'begindate'), Ext.Date.DAY, 1));
    tools.SetValue(mep + 'enddate', Ext.Date.add(tools.GetValue(mep + 'enddate'), Ext.Date.DAY, 1));

    me.OnSearch();
  }
});

Ext.define('gpersist.listeditform', {
  extend :'gpersist.listform',
  
  plEdit : null,  
  winEdit: null,  
  dataDeal: gpersist.DATA_DEAL_SELECT,  
  disNews: [],  
  disEdits: [],  
  enNews: [], 
  enEdits: [],  
  saveUrl: '',  
  editInfo: '',  
  editToolItems: [],  
  tbEdit: null,  
  editControls: [],
  hasCopy: false,
  hasPrevNext: false,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plEdit = null;
    me.winEdit = null;
    me.dataDeal = gpersist.DATA_DEAL_SELECT;
    me.disNews = [];
    me.disEdits = [];
    me.enNews = [];
    me.enEdits = [];
    me.saveUrl = '';
    me.editInfo = '';
    me.editToolItems = [];
    me.tbEdit = null;
    me.editControls = [];
    me.hasCopy = false;
    me.hasPrevNext = false;
  },
  
  OnBeforeCreateGrid : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPageSize)
      Ext.Array.insert(items, 12, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.CON_PAGESIZE_MIN, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items});
    
    me.tbGrid.add(toolbar);
  },
  
  OnAfterCreateEditToolBar: function () {
    
  },
  
  OnCreateEditControls: function () {
    
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editwin')) {
      Ext.getCmp(mep + 'editwin').destroy();
    }
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, 4, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnFormEdit, scope: me }]);
    }
    
    if (me.hasCopy) {
      Ext.Array.insert(me.editToolItems, 4, [' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormEdit, scope: me }]);
    }
    
    me.OnAfterCreateEditToolBar();
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.OnCreateEditControls();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : me.tbEdit,
      items: me.editControls    
    });
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: 750,
      height: 250,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      items : [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });
  },
  
  OnBeforeCloseEditWin: function () {
    return true;
  },
  
  OnBeforeHide: function () {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_EXITWIN, function (btn) {
        if (btn == 'yes') {
          me.dataDeal = gpersist.DATA_DEAL_SELECT; 
          me.winEdit.hide();
        }
      });
      
      return false;
    }      
    
    me.dataDeal = gpersist.DATA_DEAL_SELECT; 
    
    return true;
  }, 
  
  OnAuthEditForm: function(type) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);        
        tools.BtnsEnable(['btnFormEdit','btnCopy'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        break;
        
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnCopy'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        break;
        
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnCopy'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        break;
    }
  },
  
  OnShow: function () {
    var me = this;
    
    var record = tools.OnGridLoad(me.plGrid);
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {        
      me.winEdit.show();
      
      me.OnLoadData(record);
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
    }
  },
  
  OnInitData: function () {
    var me = this;
    
    tools.ResetForm(me.plEdit.getForm());
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    tools.SetValue(mep + 'devid', record.logindate);
  },
  
  OnNew: function () {
    var me = this;
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if(me.winEdit){
      me.OnInitData();
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
      me.winEdit.show(); 
    }
  },
  
  OnEdit: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid);
    
    if (!me.winEdit)
      me.OnCreateWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();
        me.OnLoadData(record);
        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT);
      }
    }
  },
  
  OnDelete: function () {
    
  },
  
  OnFormEdit: function () {
    var me = this;
    me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT);
  },
  
  OnAfterCopy: function () {
    
  },
  
  OnCopy: function () {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)
      return;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    
    me.OnAfterCopy();
  },
  
  OnCheckPrevNext: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (record.logindate == tools.GetValue(mep + 'devid'))
      return true;
    
    return false;
  },
  
  OnPrevRecord: function () {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
          me.OnPrevRecord();
        }
      });
    }      
    else {
      var j = me.plGrid.store.getCount(), record;
      for (var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) return;
          
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          return;
        }
      }
    }
  },
  
  OnNextRecord: function () {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
          me.OnNextRecord();
        }
      });
    }      
    else {
      var j = me.plGrid.store.getCount(), record;
      for (var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) return;
          
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          return;
        }
      }
    }
  },
  
  OnBeforeSave: function () {
    return true;
  },
  
  OnSave: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        async: false,
        method: 'POST',
        success: function (form, action) {
          tools.FormDisable(me.disNews, me.enNews, mep);
          tools.BtnsDisable(['btnSave'], mep);
          tools.BtnsEnable(['btnFormEdit'], mep);
          me.OnRefresh();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });       
    }
  }
});