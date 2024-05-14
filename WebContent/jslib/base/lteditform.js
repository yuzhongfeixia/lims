Ext.define('gpersist.base.lteditform', {
  extend :'gpersist.base.ltreeform',
  
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
  winWidth: 600,
  winHeight: 250,
  hasExitPrompt: true,
  saveParams: {},
  hasOtherSearch: false,
  editScorll: false,
  customButtons: [],
  cancelRecord: null,
  deleteUrl: '',
  deleteParams: {},
  idPrevNext: '',
  
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
    me.winWidth = 600;
    me.winHeight = 250;
    me.hasExitPrompt = true;
    me.saveParams = {};
    me.hasOtherSearch = false;
    me.editScorll = false;
    me.customButtons = [];
    me.cancelRecord = null;
    me.deleteUrl = '';
    me.deleteParams = {};
    me.idPrevNext = '';
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}
      ]);
    
    tools.SetToolbar(items, mep);
    
    if (me.hasOtherSearch)
      tools.DeleteToolbarItemByAuth('0', items, mep + 'btnSearch', 1);
    
    if (me.customButtons.length > 0)
      Ext.Array.insert(items, 0, me.customButtons);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnAfterCreateEditToolBar: function () {
    
  },
  
  OnBeforeCreateEdit: function () {
    
  },
  
  OnAfterCreateEdit: function () {
    
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editwin')) {
      Ext.getCmp(mep + 'editwin').destroy();
    }
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me },  
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnAfterCreateEditToolBar();
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.OnBeforeCreateEdit();
    
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
      autoScroll: me.editScorll,
      items: me.editControls    
    });
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      items : [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });
    
    me.OnAfterCreateEdit();
  },
  
  OnBeforeCloseEditWin: function () {
    return true;
  },
  
  OnBeforeHide: function () {
    var me = this;
    
    if (me.hasExitPrompt) {
      if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_EXITWIN, function (btn) {
          if (btn == 'yes') {
            me.dataDeal = gpersist.DATA_DEAL_SELECT; 
            me.winEdit.hide();
          }
        });
        
        return false;
      }    
    }
    
    me.dataDeal = gpersist.DATA_DEAL_SELECT; 
    
    return true;
  }, 
  
  OnAuthEditForm: function (type, islayout) {
    var me = this;
    var mep = this.tranPrefix;

    me.dataDeal = type;

    if (islayout)
      me.plEdit.suspendLayouts();

    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnFormCopy','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsDisable(['btnSave','btnFormCancel'], mep);        
        break;

      case gpersist.DATA_DEAL_NEW:
        
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        break;

      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel'], mep);
        break;
    }

    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnShow: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {   
      var date = new Date();         
      me.winEdit.show();
      tools.log('OnFormLoad', date);
      
      me.OnLoadData(record);
      tools.log('OnFormLoad', date);
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
      tools.log('OnFormLoad', date);
    }
  },
  
  OnInitData: function () {
    var me = this;

    me.plEdit.getForm().reset();
  },
  
  OnLoadData: function (record) {
    
  },
  
  OnNew: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
        
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if(me.winEdit){
      var date = new Date();
      me.winEdit.show();
      tools.log('OnFormLoad', date);
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);       
      me.cancelRecord = null;      
      tools.log('OnFormLoad', date);
      me.OnInitData();
      tools.log('OnFormLoad', date);
    }
  },
  
  OnEdit: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {
      var date = new Date();  
      me.winEdit.show();
      tools.log('OnFormLoad', date);
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
      tools.log('OnFormLoad', date);
      me.cancelRecord= record;        
      me.OnLoadData(record);
      tools.log('OnFormLoad', date);
    }
  },
  
  OnDeal: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要处理的数据！');
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();
        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
        me.OnLoadData(record);
        me.cancelRecord= record;
      }
    }
  },
  
  OnBeforeDelete: function (datas) {
    
  },
  
  OnDelete: function () {
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
  
  OnFormCancel: function () {
    var me = this;
    
    if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.STR_MSG_NEWCANCEL, function (btn) {
        if (btn == 'yes') {
          if (me.winEdit) {
            me.dataDeal = gpersist.DATA_DEAL_SELECT;
            me.winEdit.hide();
          }            
        }
      });
    }
    else {
      if (me.cancelRecord) {
        me.OnLoadData(me.cancelRecord);
        me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
      }
    }
  },
  
  OnFormEdit: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
  },
  
  OnAfterCopy: function () {
    
  },
  
  OnCopy: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要复制的数据！');
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();        
        me.plEdit.suspendLayouts();
        
        me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
        me.OnLoadData(record);        
        me.cancelRecord = null;
        me.plEdit.resumeLayouts(true);
        me.plEdit.doLayout();
        
        me.OnAfterCopy();
      }
    }
  },
  
  OnFormCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
    me.OnAfterCopy();
  },
  
  OnCheckPrevNext: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!me.hasPrevNext || Ext.isEmpty(me.idPrevNext))
      return false;
    
    return (eval('record.' + me.idPrevNext) == tools.GetValue(mep + me.idPrevNext));
  },
  
  OnPrevRecord: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;

    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnPrevRecord();
        }
      });
    }      
    else {
      var j = me.plGrid.store.getCount(), record;
      for (var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) {
            tools.alert('已经是当前列表第一条数据！');
            return;
          }
          
          me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          return;
        }
      }
    }
  },
  
  OnNextRecord: function () {
    var me = this;
    
    if (me.OnCheckTreeSelect(true))
      return;

    if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnNextRecord();
        }
      });
    }      
    else {
      var j = me.plGrid.store.getCount(), record;
      for (var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) {
            tools.alert('已经是当前列表最后一条数据！');
            return;
          }
          
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          return;
        }
      }
    }
  },
  
  OnBeforeSave: function () {
    return true;
  },

  OnAfterSave: function (action) {
    
  },
  
  OnGetSaveParams: function () {
    
  },
  
  OnSave: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    if (me.OnCheckTreeSelect(true))
      return;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      me.OnGetSaveParams();
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        params: me.saveParams,
        async: false,
        method: 'POST',
        success: function (form, action) {
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
          me.OnSearch();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });       
    }
  },

  OnBeforePrint: function () {
    Ext.ux.grid.Printer.printTitle = this.editInfo;
  }
});