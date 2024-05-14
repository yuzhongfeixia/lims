Ext.define('gpersist.base.listdetailform', {
  extend : 'gpersist.base.listform',
  
  tabMain : null,
  plEdit : null,
  winEdit : null,
  dataDeal : gpersist.DATA_DEAL_SELECT,
  disNews : [],
  disEdits : [],
  enNews : [],
  enEdits : [],
  saveUrl : '',
  editInfo : '',
  editToolItems : [],
  tbEdit : null,
  editControls : [],
  hasCopy : false,
  hasPrevNext : false,
  idPrevNext: '',
  winWidth : 600,
  winHeight : 250,
  hasExitPrompt : true,
  saveParams : {},
  hasDetailList : false,
  plDetailGrid : null,
  urlDetail : '',
  urlDetailColumn : '',
  columnDetails : [],
  fieldDetails : [],
  gridDetailStore : null,
  detailTitle : '',
  hasPageDetail : false,
  detailFuncs : null,
  editPadding : 5,
  hasDetailCheck: true,
  winDetail: null,
  plDetialEdit: null,
  detailControls: [],
  cancelRecord: null,
  deleteUrl: '',
  deleleParams: {},
  validUrl: '',
  idValid: 'validstatus',
  hasOtherSearch: false,
  hasDetailEdit: true,
  cellEditing: null,
  deitems: [],
  hasDetailGridSelect: true,
  idCheck: '',
  checkUrl: '',
  
  OnInitConfig : function() {
    var me = this;
    
    me.callParent(arguments);
    
    me.plGrid = null;
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
    me.idPrevNext = '';
    me.winWidth = 600;
    me.winHeight = 250;
    me.hasExitPrompt = true;
    me.saveParams = {};
    me.plDetailGrid = null;
    me.urlDetail = '';
    me.urlDetailColumn = '';
    me.columnDetails = [];
    me.fieldDetails = [];
    me.hasDetailList = false;
    me.gridDetailStore = null;
    me.detailTitle = '';
    me.hasPageDetail = false;
    me.detailFuncs = null;    
    me.editPadding = 5;
    me.hasDetailCheck = true;
    me.winDetail = null;
    me.plDetialEdit = null;
    me.detailControls = [];
    
    me.cancelRecord = null;
    me.deleteUrl = '';
    me.deleleParams = {};
    me.validUrl = '';
    me.idValid = 'validstatus';
    me.hasOtherSearch = false;
    me.idCheck = '';
    me.checkUrl = '';
    
    me.hasDetailEdit = true;
    me.cellEditing = null;
    me.deitems = [];
    me.hasDetailGridSelect = true;
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnValid', text: '有效', iconCls: 'icon-valid', handler: me.OnValid,scope: me},
      ' ', { id: mep + 'btnInValid', text: '无效', iconCls: 'icon-invalid', handler: me.OnInValid,scope: me},
      '-', ' ', { id: mep + 'btnCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnCheck,scope: me},
      ' ', { id: mep + 'btnUnCheck', text: '取消审核', iconCls: 'icon-unaudit', handler: me.OnUnCheck,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (!me.hasOtherSearch)
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }, '-']);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;

    Ext.getCmp('tpanel' + me.mid).removeAll();
    
    me.columns = [];
    me.fields = [];
    if (me.hasColumnUrl) 
      tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_');
    else 
      tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
    
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);
    
    me.OnBeforeFormLoad();
    
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
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners : {
        'itemdblclick' : { fn : me.OnShow, scope : me }
      }
    });
    
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store : me.gridStore,
        displayInfo : true,
        displayMsg : gpersist.STR_PAGE_FMT,
        emptyMsg : gpersist.STR_NO_DATA,
        dock : 'bottom'
      }));
    }
    
    me.OnAfterFormLoad();
    
    me.OnCreateEditWin();
    
    var pleditview = Ext.create('Ext.Panel', {
      frame : false,
      autoScroll : false,
      region : 'center',
      border : false,
      items :
        [me.plEdit],
      layout : "border",
      margins : '0 0 0 0',
      padding : '0 0 0 0'
    });
    
    if (me.hasDetailList)
      pleditview.add(me.plDetailGrid);
    
    me.tabMain = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : false,
      items :
        [me.plGrid,pleditview]
    });
    
    var mainpanel = Ext.getCmp('tpanel' + me.mid);

    mainpanel.add(me.tabMain);
    me.tabMain.getTabBar().setVisible(false);

    if (me.hasAutoLoad)
      me.OnSearch();
  },
  
  OnBeforeCreateEditToolBar : function() {
    
  },
  
  OnAfterCreateEditToolBar : function() {
    
  },
  
  OnBeforeCreateEdit : function() {
    
  },
  
  OnAfterCreateEdit : function() {
    
  },
  
  OnBeforeCreateDetail : function() {
    
  },
  
  OnAfterCreateDetail : function() {
    
  },
  
  OnGetDetailFunction : function() {
    
  },
  
  OnAfterCreateDetailToolBar: function () {
    
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnBeforeCreateEditToolBar();
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnFormValid', text: '有效', iconCls: 'icon-valid', handler: me.OnFormValid, scope: me },
      ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
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
    
    if (me.hasDetailList) {
      me.detailFuncs = Ext.create('Ext.util.MixedCollection');
      
      me.OnGetDetailFunction();

      me.columnDetails = [];
      me.fieldDetails = [];
      if (me.hasColumnUrl)
        tools.GetGridColumnsByUrl(this.urlDetailColumn, me.columnDetails, me.fieldDetails, mep + '_d_', me.detailFuncs, me.hasDetailCheck);
      else
        tools.GetGridColumns(this.urlDetailColumn, me.columnDetails, me.fieldDetails, mep + '_d_', me.detailFuncs, me.hasDetailCheck);
      
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
      
      if (me.hasDetailEdit) {
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
        features :
          [{
            ftype : 'summary'
          }],
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
        ' ', { id : mep + 'btnDetailInsert', text : gpersist.STR_BTN_INSERT, iconCls : 'icon-add', handler : me.OnListInsert, scope : me },
        ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me },
        ' ', { id : mep + 'btnDetailReset', text : gpersist.STR_BTN_RESET, iconCls : 'icon-reset', handler : me.OnDetailRefresh, scope : me }
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
      
      me.OnAfterCreateDetail();
    }
  },
  
  OnBeforeDetailEdit: function (e, editor) {
    return false;
  },
  
  OnDetailEdit: function (editor, e) {
    var me = this;
    me.plDetailGrid.getView().refresh();
  },
  
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
  },
  
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave','btnFormCancel'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete','btnDetailReset','btnDetailInsert'], mep);        
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnDetailReset','btnDetailInsert'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnDetailReset','btnDetailInsert'], mep);
        break;
    }
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnDetailRefresh : function() {
    var me = this;
    
    if (me.plDetailGrid && me.plDetailGrid.store)
      me.plDetailGrid.store.load();
  },
  
  OnShow : function() {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid);

    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      }
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
      
      me.cancelRecord = record;
      me.OnFormValidShow();
      
      me.tabMain.setActiveTab(1);
    }
  },
  
  OnInitData : function() {
    var me = this;
    
    tools.ResetForm(me.plEdit.getForm());
    
    me.OnDetailRefresh();
  },
  
  OnLoadData : function(record) {
    return true;
  },
  
  OnSetData : function(item) {
    
  },
  
  OnNew : function() {
    var me = this;
    
    if (me.tabMain) {      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
      
      me.OnInitData();
      me.cancelRecord = null;
      me.OnFormValidShow();
      
      me.tabMain.setActiveTab(1);
            
    }
  },
  
  OnEdit : function() {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    if (me.tabMain && record) {
      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      if (!me.OnLoadData(record)) {
        return;
      }
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
      
      me.cancelRecord= record;
      me.OnFormValidShow();
      
      me.tabMain.setActiveTab(1);
    }
  },
  
  OnDeal: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要处理的数据！');
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      if (!me.OnLoadData(record)) {
        return;
      }
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
      
      me.cancelRecord= record;
      me.OnFormValidShow();
      
      me.tabMain.setActiveTab(1);
    }
  },
  
  OnBeforeDelete: function () {
    
  },
  
  OnDelete : function() {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.STR_MSG_BATCHDELETE, function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
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
  
  OnFormEdit : function() {
    var me = this;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
  },
  
  OnFormReset : function() {
    
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
  
  OnBeforeSave : function() {
    return true;
  },
  
  OnAfterSave : function(action) {
    
  },
  
  OnGetSaveParams : function() {
    
  },
  
  OnSave : function() {
    var me = this;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
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
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  // region Detail Methods
  
  OnListSelect: function(e, record, item, index) {
    var me = this;
    var selModel = me.plDetailGrid.getSelectionModel();
    selModel.select(record);
  },
  
  OnBeforeListNew : function() {
    
  },
   
  OnListNew: function() {
    var me = this;
    
    var record = me.plDetailGrid.store.model.create({});
    me.OnBeforeListNew(record);
    me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
    
    if (me.cellEditing) {
      me.cellEditing.startEditByPosition({
        row: me.plDetailGrid.store.getCount() - 1,
        column: 0
    });
    }
  },
  
  OnListInsert: function() {
    var me = this;

    var selModel = me.plDetailGrid.getSelectionModel();
    
    var row = me.plDetailGrid.store.getCount();
    
    if (selModel.hasSelection()) {
      var selected = selModel.getSelection();
      
      row = me.plDetailGrid.store.indexOf(selected[0]);
      
      if (row < 0)
        row = 0;
    }

    var record = me.plDetailGrid.store.model.create({});

    me.OnBeforeListNew(record);
    me.plDetailGrid.store.insert(row, record);
    
    me.plDetailGrid.getView().refresh();
    
    if (me.cellEditing)
      me.cellEditing.startEditByPosition({
        row: row,
        column: 0
    });
  },
  
  OnListDelete : function() {
    var me = this;
    
    var j = me.plDetailGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    }
    
    me.plDetailGrid.getView().refresh();
  },
  
  // endregion Detail Methods
  
  OnBeforePrint : function() {
    Ext.ux.grid.Printer.printTitle = this.editInfo;
  },
  
  OnFormValidShow: function() {
    var me = this, mep = me.tranPrefix;
    
    var btnValid = Ext.getCmp(mep + 'btnFormValid');
    
    if (!Ext.isEmpty(me.idValid) && Ext.isDefined(btnValid)) {
      var validstatus = tools.GetValue(mep + me.idValid);
      
      if (validstatus == gpersist.STR_VALID) {
        btnValid.setText('无效');
        btnValid.setIconCls('icon-invalid');
      }
      else {
        btnValid.setText('有效');
        btnValid.setIconCls('icon-valid');
      }
      
      tools.BtnsDisable(['btnFormValid'], mep);
      
      if (me.dataDeal == gpersist.DATA_DEAL_SELECT) {
        if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_VALID) && (validstatus == gpersist.STR_INVALID))
          tools.BtnsEnable(['btnFormValid'], mep);
        
        if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_INVALID) && (validstatus == gpersist.STR_VALID))
          tools.BtnsEnable(['btnFormValid'], mep);
      }
    }
    
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
  
  OnValid: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量设置有效标记？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
            Ext.apply(me.deleteParams, {validtype: 'Y'});
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.validUrl),
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
        tools.alert('批量设置有效标记前请先选择需要进行设置的数据！');
    }
  },
  
  OnInValid: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量设置无效标记？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
            Ext.apply(me.deleteParams, {validtype: 'N'});
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.validUrl),
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
        tools.alert('批量设置无效标记前请先选择需要进行设置的数据！');
    }
  },
  
  OnCheck: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量审核操作？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
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
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否需要进行批量取消审核操作？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
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
  
  OnAfterCopy: function () {
    
  },
  
  OnCopy: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要复制的数据！');
        
    if (me.tabMain && record) {
      
      me.dataDeal = gpersist.DATA_DEAL_EDIT;
      if (!me.OnLoadData(record)) {
        return;
      }
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
      
      me.cancelRecord= record;
      me.OnFormValidShow();
      
      me.tabMain.setActiveTab(1);
      
      me.OnAfterCopy();
    }
  },
  
  OnFormCancel: function () {
    var me = this;
    
    if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.STR_MSG_NEWCANCEL, function (btn) {
        if (btn == 'yes') {
          if (me.tabMain) {
            me.dataDeal = gpersist.DATA_DEAL_SELECT;
            me.tabMain.setActiveTab(0);
          }            
        }
      });
    }
    else {
      if (me.cancelRecord) {
        me.OnLoadData(me.cancelRecord);
        me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
        me.OnFormValidShow();
      }
    }
  },
  
  OnFormCopy: function () {
    var me = this;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
    me.OnAfterCopy();
  },
  
  OnAfterValidSave: function (type) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (!Ext.isEmpty(me.idValid)) {
      if (type == gpersist.DATA_DEAL_VALID)
        tools.SetValue(mep + me.idValid, gpersist.STR_VALID);
      else
        tools.SetValue(mep + me.idValid, gpersist.STR_INVALID);
    }
  },
  
  OnFormValid: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    if (!Ext.isEmpty(me.idValid)) {
      if (tools.GetValue(mep + me.idValid) ==  gpersist.STR_VALID)
        me.OnFormValidSave(gpersist.DATA_DEAL_INVALID);
      else
        me.OnFormValidSave(gpersist.DATA_DEAL_VALID);
    }
  },
  
  OnFormValidSave: function (type) {
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
          me.OnAfterValidSave(type);          
          me.dataDeal = gpersist.DATA_DEAL_SELECT;
          me.OnSearch();
          me.OnFormValidShow();
          
          if (type == gpersist.DATA_DEAL_INVALID)
            tools.alert(Ext.String.format(gpersist.STR_FMT_SET_OK, '无效标记'));
          else
            tools.alert(Ext.String.format(gpersist.STR_FMT_SET_OK, '有效标记'));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });       
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
  
  OnOperSearch: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var cbuser = Ext.getCmp(mep + 'selectbusoper'); 
    
    if (cbuser.store) {
      cbuser.store.on('beforeload', function (store, options) {      
          Ext.apply(store.proxy.extraParams, {
            'oper.opername': tools.GetValueEncode(mep + 'searchopername'),
            'oper.wsid': tools.GetValue(mep + 'searchoperws')
          });
        });
      }
    
    cbuser.store.loadPage(1);
  },
  
  OnOperBeforeLoad: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var cbuser = Ext.getCmp(mep + 'selectbusoper'); 
    
    if (cbuser.store) {
      cbuser.store.on('beforeload', function (store, options) {      
          Ext.apply(store.proxy.extraParams, {
            'oper.opername': tools.GetValueEncode(mep + 'searchopername'),
            'oper.wsid': tools.GetValue(mep + 'searchoperws')
          });
        });
      }
  },
  
  OnOperFocus: function (render) {
    var me = this;
    
    render.record = me.cellEditing.getActiveRecord();
    render.hasSelect = false;
  },
  
  OnOperValid: function(render) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
      return;
    
    var operid = tools.GetValue(mep + 'selectbusoper');

    var now = render.record;
    
    if (now && now.data) {      
      if (Ext.isEmpty(operid)) {
        now.data.operid = '';
        now.data.opername = '';
        tools.SetValue(mep + 'selectbusoper', '');
      }
      else {
        var item = tools.JsonGet(tools.GetUrl('BusGetBusUser.do?oper.operid=') + operid);
  
        if (item && !Ext.isEmpty(item.operid)) {
          now.data.operid = Ext.util.Format.trim(item.operid);
          now.data.opername = Ext.util.Format.trim(item.opername);
        }
        else {
          now.data.operid = '';
          now.data.opername = '';
          tools.SetValue(mep + 'selectbusoper', '');
        }
      }
  
      me.plDetailGrid.getView().refresh();
      
      render.record = null;
    }
  },
  
  OnOperSelect: function (render, item) {
    var me = this;
    
    if (item && item.operid && me.cellEditing) {
      var now = me.cellEditing.getActiveRecord();

      if (now) {
        me.cellEditing.getActiveEditor().setValue(Ext.util.Format.trim(item.opername));
        
        now.data.operid = Ext.util.Format.trim(item.operid);
        
        me.plDetailGrid.getView().refresh();
      }
    }
  }
});