Ext.define('gpersist.base.listeditform', {
  extend :'gpersist.base.listform',
  
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
  idPrevNext: '',
  winWidth: 600,
  winHeight: 250,
  hasExitPrompt: true,
  saveParams: {},
  cancelRecord: null,
  deleteUrl: '',
  deleleParams: {},
  validUrl: '',
  idValid: 'validstatus',
  hasOtherSearch: false,
  idCheck: '',
  checkUrl: '',
  
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
    me.idPrevNext = '';
    me.winWidth = 600;
    me.winHeight = 250;
    me.hasExitPrompt = true;
    me.saveParams = {};
    me.cancelRecord = null;
    me.deleteUrl = '';
    me.deleleParams = {};
    me.validUrl = '';
    me.idValid = 'validstatus';
    me.hasOtherSearch = false;
    me.idCheck = '';
    me.checkUrl = '';
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
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
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
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
      '-', ' ', { id: mep + 'btnFormValid', text: '有效', iconCls: 'icon-valid', handler: me.OnFormValid, scope: me },
      '-', ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
    
    me.OnBeforeCreateEditToolBar();
    
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
      draggable: true,
      constrain: true,
      items : [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });
    
    me.OnAfterCreateEdit();
  },
  
  OnBeforeCreateEditToolBar:function(){
    
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
  
  OnAuthEditForm: function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;

    if (islayout)
      me.plEdit.suspendLayouts();
      
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);        
        tools.BtnsEnable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave','btnFormCancel','btnFormDeal'], mep);
        me.plEdit.updateLayout();
        break;
        
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel','btnFormDeal'], mep);
        break;
        
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel','btnFormDeal'], mep);
        break;
    }
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnFormValidShow: function() {
    var me = this, mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.idValid)) {
      var btnValid = Ext.getCmp(mep + 'btnFormValid');
      
      if (Ext.isDefined(btnValid)) {
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
    }
    
    if (!Ext.isEmpty(me.idCheck)) {
      var btnCheck = Ext.getCmp(mep + 'btnFormCheck');
      
      if (Ext.isDefined(btnCheck)) {
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
    }
  },
  
  OnShow: function () {
    var me = this;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {        
      me.winEdit.show();      
    
      if (!me.OnLoadData(record)) 
        return;
        
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);

      me.cancelRecord = record;
      me.OnFormValidShow();
    }
  },
  
  OnInitData: function () {
    var me = this;

    me.plEdit.getForm().reset();
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isEmpty(eval('record.' + me.idPrevNext))) {        
      return me.OnSetData(eval('record.' + me.idPrevNext), record);
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_NOLOAD, me.editInfo));
    }
    
    return false;
  },
  
  OnNew: function () {
    var me = this;
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if(me.winEdit) {     
      var date = new Date();
      
      me.winEdit.show();
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
      
      tools.log('New1', date);
      
      me.cancelRecord = null;
      me.OnFormValidShow();
      
      tools.log('New2', date);
      
      me.OnInitData();      
      
      tools.log('New3', date);
    }
  },
  
  OnBeforeEdit: function () {
    return true;
  },
  
  OnEdit: function () {
    var me = this;
    var date = new Date();
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    tools.log('OnEdit1', date);
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!Ext.isEmpty(me.idValid)) {
      if (record && Ext.isDefined(record.validstatus) && (record.validstatus == gpersist.STR_INVALID)) {
        tools.alert('无效状态的数据不能被修改！');
        return;
      }
    }
    
    tools.log('OnEdit2', date);
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();     
        
        tools.log('OnEdit3', date);
        
        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
        
        tools.log('OnEdit4', date);
        
        if (!me.OnLoadData(record)) 
          return;
            
        tools.log('OnEdit5', date);
        me.cancelRecord= record;
        me.OnFormValidShow();
        
        tools.log('OnEdit6', date);
      }
    }
  },
  
  OnDeal: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要处理的数据！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {
      me.winEdit.show();
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
      if (!me.OnLoadData(record))
        return;
        
      me.cancelRecord = record;
      me.OnFormValidShow();
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
    
    if (!Ext.isEmpty(me.idValid)) {
      if (record && Ext.isDefined(record.validstatus) && (record.validstatus == gpersist.STR_INVALID)) {
        tools.alert('无效状态的数据不能被复制！');
        return;
      }
    }
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();        
        me.plEdit.suspendLayouts();
        
        me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
        me.OnLoadData(record);        
        me.cancelRecord = null;
        me.OnFormValidShow();
        me.plEdit.resumeLayouts(true);
        me.plEdit.doLayout();
        
        me.OnAfterCopy();
      }
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
        me.OnFormValidShow();
      }
    }
  },
  
  OnFormEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeEdit()) 
      return;
    
    if (!Ext.isEmpty(me.idValid)) {
      var validvalue = tools.GetValue(mep + me.idValid);
      if (validvalue == gpersist.STR_INVALID) {
        tools.alert('无效状态的数据不能被修改！');
        return;
      }
    }
    
    if (!Ext.isEmpty(me.idPrevNext)) {
      me.dataDeal = gpersist.DATA_DEAL_EDIT;

      if (me.OnSetData(tools.GetValue(mep + me.idPrevNext))) {
        if (me.dataDeal == gpersist.DATA_DEAL_EDIT)
          me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
      }   
    }
  },
  
  OnSetData: function (id, record) {
    return true;  
  },
  
  OnFormCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.idValid)) {
      var validvalue = tools.GetValue(mep + me.idValid);
      if (validvalue == gpersist.STR_INVALID) {
        tools.alert('无效状态的数据不能被修改！');
        return;
      }
    }
    
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
          me.OnFormValidShow();
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
          me.OnFormValidShow();
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
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      me.OnGetSaveParams();
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        params: me.saveParams,
        waitTitle: '提示',
        waitMsg: '正在提交......',
        async: false,
        method: 'POST',
        success: function (form, action) {
          me.OnAfterSave(action);          
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });       
    }
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
  
  OnBeforePrint: function () {
    Ext.ux.grid.Printer.printTitle = this.editInfo;
  } 
});