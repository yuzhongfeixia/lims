Ext.define('gpersist.base.busform', {
  extend : 'gpersist.base.baseform',
  
  columnUrl: '',
  storeUrl: '',
  expUrl: '',
  
  columns: [],
  fields: [],
  plGrid: null,   
  tbGrid: null,
  gridStore: null,
  hasPage: true,
  hasPageSize: true,
  hasGridSelect: false,
  
  hasDateSearch: false,
  idBeginDate: 'searchbegindate',
  idEndDate: 'searchenddate',
  
  hasExportBegin: true,
  hasExportPage: false,
  
  hasEdit: true,
  plEdit: null,
  tbEdit: null,
  
  hasDetail: false,
  plDetail: null,
  
  hasPrevNext: true,
  hasSubmit: false,
  editPadding: 5,
  editControls: [],
  
  detailTabs: 1,
  hasAutoLoad: true,
  detailTitle: '',
  editInfo: '',
  plDetailGrid: null,
  hasDetailGridSelect: true,
  urlDetailColumn: '',
  detailFuncs: null,
  columnDetails: [],
  fieldDetails: [],
  hasDetailCheck: false,
  gridDetailStore: null,
  urlDetail: '',
  hasDetailEdit: false,
  hasDetailGridEdit: false,
  cellEditing: null,
  hasPageDetail: false,
  
  cancelRecord: null,
  idPrevNext: '',
  deleteUrl: '',
  deleteParams: {},
  idCheck: '',
  saveUrl: '',
  saveParams: {},
  checkUrl: '',
  hasExitPrompt: true,
  
  winDetail: null,
  winWidth: 600,
  winHeight: 250,
  plDetailEdit: null,
  editDetailControls: [],
  detailEditType: 1, // 1--New, 2--Modify
  detailRecord: null,
  disDetailControls: [],
  enDetailControls: [],
  deferredRender: true,
  isNowCopy: false,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columnUrl = '';
    me.storeUrl = '';
    me.expUrl = '';
  
    me.columns = [];
    me.fields = [];
    me.plGrid = null;   
    me.tbGrid = null;
    me.gridStore = null;
    me.hasPage = true;
    me.hasPageSize = true;
    me.hasGridSelect = false;
  
    me.hasDateSearch = false;
    me.idBeginDate = 'searchbegindate';
    me.idEndDate = 'searchenddate';
  
    me.hasExportBegin = true;
    me.hasExportPage = false;
  
    me.hasEdit = true;
    me.plEdit = null;
    me.tbEdit = null;
  
    me.hasDetail = false;
    me.plDetail = null;
  
    me.hasPrevNext = true;
    me.hasSubmit = false;
    me.editPadding = 5;
    me.editControls = [];
  
    me.detailTabs = 1;
    me.hasAutoLoad = true;
    me.detailTitle = '';
    me.editInfo = '';
    me.plDetailGrid = null;
    me.hasDetailGridSelect = true;
    me.urlDetailColumn = '';
    me.detailFuncs = null;
    me.columnDetails = [];
    me.fieldDetails = [];
    me.hasDetailCheck = false;
    me.gridDetailStore = null;
    me.urlDetail = '';
    me.hasDetailEdit = false;
    me.hasDetailGridEdit = false;
    me.cellEditing = null;
    me.hasPageDetail = false;
  
    me.cancelRecord = null;
    me.idPrevNext = '';
    me.deleteUrl = '';
    me.deleteParams = {};
    me.idCheck = '';
    me.saveUrl = '';
    me.saveParams = {};
    me.checkUrl = '';
    me.hasExitPrompt = true;
    
    me.winDetail = null;
    me.winWidth = 600;
    me.winHeight = 250;
    me.plDetailEdit = null;
    me.editDetailControls = [];
    me.detailEditType = 1;
    me.detailRecord = null;
    me.disDetailControls = [];
    me.enDetailControls = [];
    me.deferredRender = true;
  },
  
  // 初始化列表页面工具条容器
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
  
  // 加载页面前，初始化工具条
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },      
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnCheck,scope: me},
      ' ', { id: mep + 'btnUnCheck', text: '取消审核', iconCls: 'icon-unaudit', handler: me.OnUnCheck,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    if (me.hasDateSearch)
      Ext.Array.insert(items, 2, [
        ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
        ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }
      ]);
        
    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  // 加载页面后处理
  OnAfterFormLoad: function () {
    
  },
  
  // 加载主页面
  OnFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    var date = new Date();
    var mainpanel = Ext.getCmp('tpanel' + me.mid);
    
    if (!Ext.isDefined(mainpanel))
      return;

    mainpanel.removeAll();
    tools.log('OnFormLoad1', date);
    // 生成列表的字段属性
    me.columns = [];
    me.fields = []; 
    tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_'); tools.log('OnFormLoad2', date);
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields); tools.log('OnFormLoad2', date);

    // 加载主页面前
    me.OnBeforeFormLoad();
    tools.log('OnFormLoad2', date);
    // 生成列表
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id : mep + 'grid',
      region : 'center',
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : me.columns,
      store : me.gridStore,
      tbar : me.tbGrid,
      enableColumnMove: false, 
      enableColumnHide: false,
      suspendLayout: false,
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners : {
        'itemdblclick' : { fn : me.OnShow, scope : me },
        'itemclick' : { fn : me.OnItemClick, scope : me }
      }
    });
    
    // 分页处理
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store : me.gridStore,
        displayInfo : true,
        displayMsg : gpersist.STR_PAGE_FMT,
        emptyMsg : gpersist.STR_NO_DATA, suspendLayout: true,
        dock : 'bottom'
      }));
    }
    tools.log('OnFormLoad2-0', date);
    // 主界面生成后处理
    me.OnAfterFormLoad();
    tools.log('OnFormLoad2', date);
    // 建立编辑界面
    me.OnCreateEditWin();
    tools.log('OnFormLoad3', date);
    var pleditview = Ext.create('Ext.Panel', {
      frame : false,
      autoScroll : false,
      region : 'center',
      border : false,
      layout : 'border',
      margins : '0 0 0 0',
      padding : '0 0 0 0'
    });
    
    if (me.hasEdit)
      pleditview.add(me.plEdit);
      
    if (me.hasDetail) {
      if (me.plDetail && !me.hasEdit)
        me.plDetail.region = 'center';
        
      pleditview.add(me.plDetail);
    }
    
    tools.log('OnFormLoad4', date);
    
    me.tabMain = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : me.deferredRender,
      items :
        [me.plGrid, pleditview]
    });    
    
    tools.log('OnFormLoad6', date);
    
    me.tabMain.getTabBar().setVisible(false);
    
    tools.log('OnFormLoad5', date);
    mainpanel.add(me.tabMain);
    tools.log('OnFormLoad7', date);
      
    if (me.hasAutoLoad)
      me.OnSearch();
      
    tools.log('OnFormLoad8', date);
  },  
  
  // region Create Edit Panel
  
  OnBeforeCreateEditToolBar: function () {
    
  },
  
  OnAfterCreateEditToolBar: function () {
    
  },
  
  OnBeforeCreateEdit: function () {
    
  },
  
  OnAfterCreateEdit: function () {
    
  },
  
  OnGetDetailFunction: function () {
    
  },
  
  OnBeforeCreateDetail: function () {
    
  },
  
  OnBeforeDetailEdit: function () {
    return false;
  },
  
  OnDetailEdit: function () {
    var me = this;
    me.plDetailGrid.getView().refresh();
  },
  
  OnAfterCreateDetailToolBar: function () {
    
  },
  
  OnAfterCreateDetail: function () {
    
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnBeforeCreateEditToolBar();
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
    if (me.hasSubmit) {
      Ext.Array.insert(me.editToolItems, 2, [
        ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
      ]);
    }
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
    me.OnAfterCreateEditToolBar();
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
      items : me.editToolItems
    });
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      autoScroll: true,
      region : 'north',
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
    
    if (me.hasDetail) {
      me.detailFuncs = Ext.create('Ext.util.MixedCollection');
      me.columnDetails = [];
      me.fieldDetails = [];
      
      me.OnGetDetailFunction();      

      tools.GetGridColumnsByUrl(me.urlDetailColumn, me.columnDetails, me.fieldDetails, mep + '_d_', me.detailFuncs, me.hasDetailCheck);

      me.gridDetailStore = tools.CreateGridStore(tools.GetUrl(this.urlDetail), me.fieldDetails);

      Ext.each(me.columnDetails, function (col, index) {
        if (col.xtype == 'checkcolumn') {
          col.on('beforecheck', function(check) { 
            if (me.dataDeal == gpersist.DATA_DEAL_SELECT) 
              check.isCancel = true;
            else 
              check.isCancel = false;
          });
        }
      });

      me.OnBeforeCreateDetail();
      
      plugins = [];
      
      if (me.hasDetailGridEdit) {
        me.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
          clicksToEdit: 1,
          listeners: {
            edit: { fn: me.OnDetailEdit, scope: me},
            beforeedit: function (e, editor) {
              if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
                return false;
              
              if (me.OnBeforeDetailEdit(e, editor))
                return false;
            }
          }
        });

        plugins = [me.cellEditing];
      }

      me.plDetailGrid = Ext.create('Ext.grid.Panel', {
        id : mep + 'detailgrid',
        region : 'center',
        title : me.detailTitle,
        autoScroll : true,
        frame : false,
        border : false,
        margins : '0 0 0 0',
        padding : '0 0 0 0',
        loadMask : true,
        columnLines : true,
        viewConfig : {
          autoFill : true,
          stripeRows : true
        },
        features : [{ ftype : 'summary' }],
        columns : me.columnDetails,
        store : me.gridDetailStore,
        selModel: me.hasDetailGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }) : {},
        plugins: plugins,
        listeners : {
          'itemdblclick' : { fn : me.OnListSelect, scope : me }
        }        
      });

      me.deitems = [
        ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
//        ' ', { id : mep + 'btnDetailCopy', text : gpersist.STR_BTN_COPY, iconCls : 'icon-add', handler : me.OnListCopy, scope : me },
        ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me }
      ];
      
      me.OnAfterCreateDetailToolBar();
      
      tools.SetToolbar(me.deitems, mep);
        
      var tbdetailedit = Ext.create('Ext.toolbar.Toolbar', {
        dock : 'top',
        items : me.deitems
      });
        
      if (me.hasDetailEdit)
        me.plDetailGrid.insertDocked(0, tbdetailedit);
      
      if (me.hasPageDetail) {
        me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
          store : me.gridDetailStore,
          displayInfo : true,
          displayMsg : gpersist.STR_PAGE_FMT,
          emptyMsg : gpersist.STR_NO_DATA,
          dock : 'bottom'
        }));
      }
    
      me.gridDetailStore.on('load', me.OnNowAfterCopy, me);
      
      if (me.detailTabs > 1) {
        me.plDetail = Ext.create('Ext.tab.Panel', {
          border : false,
          activeTab : 0,
          bodyBorder : false,
          defaults : {
            bodyStyle : 'border:0px;padding:0px;'
          },
          margins : '0 0 0 0',
          region : 'center',
          deferredRender : false,
          items : [me.plDetailGrid]
        });
      }
      else
        me.plDetail = me.plDetailGrid;
        
      me.OnAfterCreateDetail();
    }
  },
  
  // endregion Create Edit Panel
  
  // region Detail Methods  
  
  OnAuthDetailEditForm : function(islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (islayout)
      me.plDetailEdit.suspendLayouts();
    
    switch (me.dataDeal) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disDetailControls, me.enDetailControls, mep);        
        tools.BtnsDisable(['btnDetailSave'], mep);     
        break;
      
      default:
        tools.FormInit(me.disDetailControls, me.enDetailControls, mep);
        tools.BtnsEnable(['btnDetailSave'], mep);
        break;
    }
    
    if (islayout) {
      me.plDetailEdit.resumeLayouts(true);
      me.plDetailEdit.doLayout();
    }
  },
  
  OnBeforeCreateDetailEdit: function () {
    
  },
  
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); } }
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
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
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit]
    });
  },
  
  OnBeforeListSave : function() {
    
  },
   
  OnInitDetailData: function () {
    
  },
  
  OnLoadDetailData: function (record) {
    
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this;
    
    me.detailRecord = record;
    
    if (!me.winDetail)
      me.OnCreateDetailWin();
      
    if(me.winDetail && record) {      
      me.winDetail.show();
      
      me.detailEditType = 2;
      me.OnLoadDetailData(record.data);  
      
      me.OnAuthDetailEditForm(true);
    }
  },
  
  OnListNew: function() {
    var me = this;
    
    var date = new Date();
    tools.log('OnListNew1', date);
    
    if (!me.winDetail)
      me.OnCreateDetailWin();
     
    tools.log('OnListNew2', date);
    
    if(me.winDetail){      
      me.winDetail.show();
      tools.log('OnListNew3', date);
      me.detailEditType = 1;
      me.OnInitDetailData();   
      tools.log('OnListNew4', date);
      me.OnAuthDetailEditForm(false);
      tools.log('OnListNew5', date);
    }
  },
  
  OnListDelete : function() {
    var me = this;
    
    var j = me.plDetailGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    }
    
    me.plDetailGrid.getView().refresh();
  },
  
  OnListSave: function () {
    var me = this;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        
        me.OnBeforeListSave(record);
        
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      }
      else {
        me.OnBeforeListSave(me.detailRecord);
        
        me.plDetailGrid.getView().refresh();
      }
      
      me.winDetail.hide();
    };
  },
  
  // endregion Detail Methods
  
  // region Button Methods
  
  // 页面空间授权处理
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  // 获得查询和导出的参数
  OnGetSearchParam: function () {    
    return {};
  },
  
  // 查询前处理
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid.store) {
      me.plGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, me.OnGetSearchParam());
      });
    }
  },
  
  // 查询处理
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
  
  // 上一天查询
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;    
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, -1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, -1));

    me.OnSearch();
  },

  // 后一天查询
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, 1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, 1));

    me.OnSearch();
  },
  
  // 导出前判断
  OnBeforeExportCheck: function () {
    return false;
  },
  
  // 获得导出的查询参数
  OnGetExportParams: function (params) {
    var me = this;
    
    Ext.apply(params, me.OnGetSearchParam());
  },
  
  // 导出前处理
  OnBeforeExport: function () {
    
  },
  
  // 导出处理
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
  
  // 列表打印前处理
  OnBeforePrint: function () {
    
  },
  
  // 列表打印
  OnPrint: function () {
    var me = this;
    
    me.OnBeforePrint();
    
    Ext.ux.grid.Printer.print(me.plGrid);
  },  
  
  OnDetailRefresh : function() {
    var me = this;
    
    if (me.plDetailGrid && me.plDetailGrid.store)
      me.plDetailGrid.store.load();
  },
  
  OnItemClick: function () {
    
  },
  
  OnShow: function () {
    var me = this;
    var date = new Date();
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      
      tools.log('n', date);
      me.tabMain.setActiveTab(1);
      
      me.isNowCopy = false;
      
      if (!me.OnLoadData(record)) {
        me.tabMain.setActiveTab(0);
        return;
      }
      
      tools.log('n', date);
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      
      me.cancelRecord = record;
      me.OnFormValidShow();
      tools.log('n', date);
      me.OnAfterShow(record);
    }
  },
  
  OnAfterShow:function(record){},
  
  OnInitData : function() {
    var me = this; 
    me.plEdit.getForm().reset(); 
    me.OnDetailRefresh(); 
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isEmpty(eval('record.' + me.idPrevNext))) {        
      return me.OnSetData(eval('record.' + me.idPrevNext), record);
    }
    else {
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_NOLOAD, me.editInfo));
    }
    
    return false;
  },
  
  OnSetData: function (id, record) {
    return true;  
  },
  
  OnNew : function() {
    var me = this;
    
    if (me.tabMain) {      
      var date = new Date();
      me.tabMain.setActiveTab(1);
      tools.log('n', date);
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
      tools.log('n', date);
      me.OnInitData();
      tools.log('n', date);
      me.isNowCopy = false;
      me.cancelRecord = null;
      me.OnFormValidShow();
      tools.log('n', date);                  
    }
  },
  
  OnNowAfterCopy: function () {
    var me = this;

    if (me.isNowCopy)
      me.OnAfterCopy();
  },
  
  OnAfterCopy: function () {
    
  },
  
  OnCopy: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要复制的数据！');
        
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      me.tabMain.setActiveTab(1);
      
      me.isNowCopy = true;
      
      if (!me.OnLoadData(record)) {
        me.tabMain.setActiveTab(0);
        return;
      }      
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
      
      me.cancelRecord= record;
      me.OnFormValidShow();
      
      // 当不存在列表时，执行复制后操作
      if (!me.hasDetail) 
        me.OnAfterCopy();
    }
  },
  
  OnBeforeEdit: function () {
    return true;
  },
  
  OnEdit: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (me.tabMain && record) {
      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      me.tabMain.setActiveTab(1);
      
      me.isNowCopy = false;
      
      if (!me.OnLoadData(record)) {
        me.tabMain.setActiveTab(0);
        return;
      }
      
      me.OnAuthEditForm(me.dataDeal, false);
      
      me.cancelRecord= record;
      me.OnFormValidShow();      
    }
  },
  
  OnDeal: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要处理的数据！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      me.tabMain.setActiveTab(1);
      if (!me.OnLoadData(record)) {
        me.tabMain.setActiveTab(0);
        return;
      }
      
      me.OnAuthEditForm(me.dataDeal, false);
      
      me.cancelRecord= record;
      me.OnFormValidShow();
    }
  },
  
  OnBeforeDelete: function (items) {
    return true;
  },
  
  OnDelete : function() {
    var me = this, mep = me.tranPrefix;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();
      if (datas.length > 0) {
        tools.SetValue(mep + 'datadeal', me.dataDeal);
        
        if (!me.OnBeforeDelete(datas)) 
          return;
      
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.STR_MSG_BATCHDELETE, function (btn) {
          if (btn == 'yes') {            
            Ext.Ajax.request({
              url: tools.GetUrl(me.deleteUrl),
              params: me.deleteParams,
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnSearch();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else 
        tools.alert(gpersist.STR_MSG_BATCHDELETETIP);
    }
  },
  
  OnFormEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeEdit()) 
      return;
    
    if (!Ext.isEmpty(me.idPrevNext)) {
      me.dataDeal = gpersist.DATA_DEAL_EDIT;

      me.isNowCopy = false;
      
      if (me.OnSetData(tools.GetValue(mep + me.idPrevNext))) {
        if (me.dataDeal == gpersist.DATA_DEAL_EDIT)
          me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
      }   
    }
  },
  
  OnFormCopy: function () {
    var me = this;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
    
    me.isNowCopy = true;
    
    me.OnAfterCopy();
  },
  
  OnCheckPrevNext : function(record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!me.hasPrevNext || Ext.isEmpty(me.idPrevNext))
      return false;
    
    return (eval('record.' + me.idPrevNext) == tools.GetValue(mep + me.idPrevNext));
  },
  
  OnPrevRecord : function() {
    var me = this;
    
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
          
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnCheck: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        if (!me.OnBeforeDelete(datas)) 
          return;
          
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量审核操作？', function (btn) {
          if (btn == 'yes') {                     
            Ext.apply(me.deleteParams, {checktype: 'Y'});
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.checkUrl),
              params: me.deleteParams,
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnSearch();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else 
        tools.alert('批量审核前请先选择需要进行审核的数据！');
    }
  },
  
  OnUnCheck: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        if (!me.OnBeforeDelete(datas)) 
          return;
          
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量取消审核操作？', function (btn) {
          if (btn == 'yes') {               
            Ext.apply(me.deleteParams, {checktype: 'N'});
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.checkUrl),
              params: me.deleteParams,
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnSearch();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else 
        tools.alert('批量取消审核前请先选择需要进行取消审核的数据！');
    }
  },
  
  OnAfterCheckSave: function (type) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (!Ext.isEmpty(me.idCheck)) {
      if (type == gpersist.DATA_DEAL_CHECK)
        tools.SetValue(mep + me.idCheck, gpersist.STR_CHECK);
      else
        tools.SetValue(mep + me.idCheck, gpersist.STR_UNCHECK);
    }
  },
  
  OnFormCheck: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    if (!Ext.isEmpty(me.idCheck)) {
      if (tools.GetValue(mep + me.idCheck) ==  gpersist.STR_CHECK)
        me.OnFormCheckSave(gpersist.DATA_DEAL_UNCHECK);
      else
        me.OnFormCheckSave(gpersist.DATA_DEAL_CHECK);
    }
  },
  
  OnFormCheckSave: function (type) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', type);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        params: me.saveParams,
        async: false,
        method: 'POST',
        success: function (form, action) {
          me.OnAfterCheckSave(type);          
          me.dataDeal = gpersist.DATA_DEAL_SELECT;
          me.OnSearch();
          me.OnFormValidShow();
          
          if (type == gpersist.DATA_DEAL_UNCHECK)
            tools.alert(Ext.String.format(gpersist.STR_FMT_SET_OK, '取消审核'));
          else
            tools.alert(Ext.String.format(gpersist.STR_FMT_SET_OK, '审核'));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });       
    }
  },

  OnFormValidShow: function() {
    var me = this, mep = me.tranPrefix;
    
    var btnCheck = Ext.getCmp(mep + 'btnFormCheck');
    
    if (!Ext.isEmpty(me.idCheck) && Ext.isDefined(btnCheck)) {
      var checktype = tools.GetValue(mep + me.idCheck);
      
      if (checktype == gpersist.STR_CHECK) {
        btnCheck.setText('取消审核');
        btnCheck.setIconCls('icon-unaudit');
      }
      else {
        btnCheck.setText('审核');
        btnCheck.setIconCls('icon-audit');
      }
      
      tools.BtnsDisable(['btnFormCheck'], mep);
      
      if (me.dataDeal == gpersist.DATA_DEAL_SELECT) {
        if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_CHECK) && (checktype == gpersist.STR_UNCHECK))
          tools.BtnsEnable(['btnFormCheck'], mep);
        
        if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_UNCHECK) && (checktype == gpersist.STR_CHECK))
          tools.BtnsEnable(['btnFormCheck'], mep);
      }
      else {
        if (checktype == gpersist.STR_CHECK) 
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
      }
      
    }
  },
  
  OnBeforeSave : function() {
    return true;
  },
  
  OnAfterSave : function(action) {
    
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.hasDetail) {
      var details = [];
      for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
        details.push(me.plDetailGrid.store.getAt(i).data);
      }
      
      me.saveParams = { details: Ext.encode(details) };
    }
  },
  
  OnSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.isNowCopy = false;
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnSubmit : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SUBMIT);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          me.OnAfterSubmit();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SUBMIT_OK, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnAfterSubmit:function(){},
  
  OnFormClose : function() {
    var me = this;
    if (me.tabMain) {
      if (me.hasExitPrompt) {
        if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
          Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_EXITLIST, function(btn) {
            if (btn == 'yes') {
              me.dataDeal = gpersist.DATA_DEAL_SELECT;
              me.tabMain.setActiveTab(0);
            }
          });
        } else
          me.tabMain.setActiveTab(0);
      }
    }
  }
  
  // endregion Button Methods
});