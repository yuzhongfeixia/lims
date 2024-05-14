Ext.define('gpersist.base.listwinform', {
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
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      ' ', { id: mep + 'btnValid', text: '有效', iconCls: 'icon-valid', handler: me.OnValid,scope: me},
      ' ', { id: mep + 'btnInValid', text: '无效', iconCls: 'icon-invalid', handler: me.OnInValid,scope: me},
      ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
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
    
    Ext.getCmp('tpanel' + me.mid).add(me.tabMain);
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
        listeners : {
          'itemdblclick' : { fn : me.OnListEdit, scope : me }
        }
      });
      
      var deitems = [
        ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
        ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me },
        ' ', { id : mep + 'btnDetailReset', text : gpersist.STR_BTN_RESET, iconCls : 'icon-reset', handler : me.OnDetailRefresh, scope : me }
      ];
        
      tools.SetToolbar(deitems, mep);
        
      var tbdetailedit = Ext.create('Ext.toolbar.Toolbar', {
        dock : 'top',
        items : deitems
      });
        
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
        tools.BtnsEnable(['btnFormEdit','btnFormCopy','btnFormValid'], mep);        
        tools.BtnsDisable(['btnSave','btnFormCancel'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete','btnDetailReset'], mep);        
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnDetailReset'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnDetailReset'], mep);
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
    var record = tools.OnGridLoad(me.plGrid);
    
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
  
  OnBeforeListNew : function() {
    
  },
  
  OnCreateDetailWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnBeforeCreateEditToolBar();
    
    var toolbar = [
      ' ', { id : mep + 'btnDetailSave', text : '确定', iconCls : 'icon-save', handler : me.OnDetailSave, scope : me },
      '-', ' ', { id : mep + 'btnDetailClose', text : '退出', iconCls : 'icon-close', handler : me.OnDetailClose, scope : me }
    ];
    
    me.OnBeforeCreateDetialEdit();
    
    me.plDetialEdit = Ext.create('Ext.form.Panel', {
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
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: toolbar}),
      items: me.detailControls    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.detailTitle),
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      items : [me.plDetialEdit]
    });
    
    me.OnAfterCreateDetailEdit();
  },
  
  OnBeforeCreateDetialEdit: function() {
    
  },
  
  OnAfterCreateDetailEdit: function() {
    
  },
  
  OnDetailSave: function() {
    var me = this;
    
    if (tools.InvalidForm(me.plDetialEdit.getForm())) {
      var record = me.plDetailGrid.store.model.create({});
      
      me.OnSaveListNew(record);
      me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
      
      me.OnInitDetailData();
    }
    else
      tools.alert('有未符合填写要求的内容！');
  },
  
  OnSaveListNew: function(record) {
    
  },
  
  OnDetailClose: function() {
    var me = this;
    
    me.winDetail.hide();
  },
  
  OnListNew: function() {
    var me = this;
    
    if (!me.winDetail)
      me.OnCreateDetailWin();
    
    if(me.winDetail){      
      me.winDetail.show();
      me.OnInitDetailData(); 
    }
  },
  
  OnListEdit: function() {
    var me = this;
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
      return;
    
    var record = tools.OnGridLoad(me.plDetailGrid, '请选择需要修改的数据！');
    
    if (!me.winDetail)
      me.OnCreateDetailWin();
    
    if (me.winDetail) {
      if (record) {        
        me.winDetail.show();
        me.OnLoadDetailData(record);
      }
    }
  },
  
  OnInitDetailData: function() {
    var me = this;
    
    tools.ResetForm(me.plDetialEdit.getForm());
  },
  
  OnLoadDetailData: function() {
    
  },
  
  OnListDelete : function() {
    var me = this;
    
    var j = me.plDetailGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    }
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
      
      if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_VALID) && (validstatus == gpersist.STR_INVALID))
        tools.BtnsEnable(['btnFormValid'], mep);
      
      if (tools.ValidMenuAuth(me.sauth, gpersist.DATA_DEAL_INVALID) && (validstatus == gpersist.STR_VALID))
        tools.BtnsEnable(['btnFormValid'], mep);
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
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
      
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
      if (me.cancelRecord)
        me.OnLoadData(me.cancelRecord);
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
  }
});