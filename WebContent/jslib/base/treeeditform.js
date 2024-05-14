Ext.define('gpersist.base.treeeditform', {
  extend: 'gpersist.base.baseform',
  
  disNews: [],  
  disEdits: [],  
  enNews: [], 
  enEdits: [],  
  saveUrl: '',
  dataDeal: gpersist.DATA_DEAL_SELECT,
  cancelRecord: null,
  treeTitle: '',
  treeUrl: '',
  editControls: [],
  editTitle: '',
  existID: '',
  existPID: '',
  existTitle: '',
  idUrl: '',
  
  plTree: null,
  plMain: null,
  plEdit: null,
  
  OnInitConfig: function () {
    var me = this;

    me.callParent(arguments);

    me.plTree = null;
    me.plMain = null;
    me.plEdit = null;
    me.saveUrl = '';
    me.dataDeal = gpersist.DATA_DEAL_SELECT;
    me.cancelRecord = null;
    me.treeTitle = '';
    me.treeUrl = '';
    me.editControls = [];
    me.editTitle = '';
    me.existID = '';
    me.existPID = '';
    me.existTitle = '';
    me.idUrl = '';
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews = [];
    me.enEdits = [];
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    var items = [
      ' ', { id: mep + 'btnRefresh', text: gpersist.STR_BTN_REFRESH, iconCls: 'icon-refresh', handler: me.OnRefresh, scope: me }, 
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_ADD_SAME, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnAddSub', text: gpersist.STR_BTN_ADD_SUB, iconCls: 'icon-add', handler: me.OnNewSub, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
      '-', ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },
      ' ', { id: mep + 'btnCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-reset', handler: me.OnCancel, scope: me },     
      '-', ' ', { text: '', tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    var sauth = tools.GetAuth(mep);
    
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnAdd', 2, tools.ValidSeparator(sauth, 2));
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnAddSub', 2);
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnFormEdit', 3, tools.ValidSeparator(sauth, 4));
    
    if (!tools.ValidSeparator(sauth, 6)) {
      tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnSave', 2, tools.ValidSeparator(sauth, 2));
      tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnCancel', 2);
    }
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
      items: items
    });
    
    me.OnBeforeCreateEdit();
    
    me.plTree = Ext.create('Ext.tree.Panel', {
      id:mep + 'treelist',
      rootVisible: false,
      region: 'west',
      title: me.treeTitle,
      width: 250,
      autoScroll: true,
      border: true,
      margins: '2 0 2 2',
      layout: 'fit',
      animate: false,
      store: Ext.create('Ext.data.TreeStore', {
        model:'gpersist.gfdeptmodel',
        proxy: {
          type: 'ajax',
          url: tools.GetUrl(me.treeUrl)
        }
      }),
      listeners: {
        'itemclick': {fn: function (view, record) {
          me.OnLoadData(record);
        }, scope:me}
      } 
    });

    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: 80,
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
      items: me.editControls,
      listeners: {
        'afterrender': { fn: me.OnInit, scope: me }
      }
    });
    
    me.plMain = Ext.create('Ext.Panel', {
      frame:false,
      autoScroll : false,
      region: 'center',
      border:true,
      items:[me.plEdit],
      layout:'column',
      title: me.editTitle,
      tbar: toolbar,
      margins: '2 2 2 5',
      padding: '0 0 0 0'
    });
    
    
    Ext.getCmp('tpanel' + me.mid).add(me.plTree);
    Ext.getCmp('tpanel' + me.mid).add(me.plMain); 

  },
  
  OnBeforeCreateEdit: function () {
    
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
        tools.BtnsEnable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);        
        tools.BtnsDisable(['btnSave','btnCancel'], mep);
        break;
        
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
        tools.BtnsEnable(['btnSave','btnCancel'], mep);
        break;
        
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
        tools.BtnsEnable(['btnSave','btnCancel'], mep);
        break;
    }
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnBeforeHide: function () {
    var me = this;    

    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '正在数据编辑状态，退出将不会保存数据，是否退出？', function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
        }
      });

      return false;
    }
    
    return true;
  }, 
  
  OnLoadData: function (record) {
    var me = this;
    
    me.cancelRecord = record;
  },
  
  OnInitData: function () {
    var me = this;
    
    tools.ResetForm(me.plEdit.getForm());
  },
  
  OnInit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
    me.OnInitData();   
    me.cancelRecord = null;
    
    me.OnRefreshPID();
  },
  
  OnCancel: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '正在编辑的数据未保存，是否继续取消当前操作？', function (btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true); 
          
          if (me.cancelRecord) {
            me.OnLoadData(me.cancelRecord);
          }
          else
            me.OnInitData();
        }
      });
    }
  },
  
  OnRefresh: function () {
    var me = this;
    
    if (me.plTree) {
      me.plTree.getRootNode().removeAll(false);
      var proxy = me.plTree.store.getProxy();
      proxy.url = tools.GetUrl(me.treeUrl);
      me.plTree.store.setProxy(proxy);
      me.plTree.store.load();
    }
  },
  
  OnNew: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(me.existID))
      retrun;
    
    if (Ext.isEmpty(me.existPID))
      retrun;
      
    var idvalue = tools.GetValue(mep + me.existID);
    var pidvalue = tools.GetValue(mep + me.existPID);
    
    if (Ext.isEmpty(idvalue))
      tools.alert(Ext.String.format(gpersist.STR_FMT_ADDSAME, me.existTitle));
    else {
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
      me.cancelRecord = null;
      
      if (idvalue == pidvalue)
        tools.SetValue(mep + me.existPID, gpersist.SELECT_NULL_VALUE);
      else
        tools.SetValue(mep + me.existPID, pidvalue);
        
      me.OnAfterNew();
    }
  },
  
  OnAfterNew: function () {
    
  },
  
  OnNewSub: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(me.existID))
      retrun;
    
    if (Ext.isEmpty(me.existPID))
      retrun;
      
    var idvalue = tools.GetValue(mep + me.existID);
    
    if (Ext.isEmpty(idvalue))
      tools.alert(Ext.String.format(gpersist.STR_FMT_ADDSUB, me.existTitle));
    else {      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
      me.cancelRecord = null;   
      
      tools.SetValue(mep + me.existPID, idvalue);
      
      me.OnAfterNewSub(); 
    }
  },
  
  OnAfterNewSub: function () {
    
  },
  
  OnEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(me.existID))
      retrun;
    
    if (Ext.isEmpty(me.existPID))
      retrun;
      
    if (Ext.isEmpty(tools.GetValue(mep + me.existID)))
      tools.alert(Ext.String.format(gpersist.STR_FMT_EDIT, me.existTitle));
    else {
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT);
      
      me.OnAfterEdit();
    }
  },
  
  OnAfterEdit: function () {
    
  },
  
  OnRefreshPID: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var pidvalue = tools.GetValue(mep + me.existPID);
    var cbpid = Ext.getCmp(mep + me.existPID);
    
    if (cbpid) {
      var store = tools.ComboStoreByUrl(me.idUrl, {}, gpersist.SELECT_NULL_VALUE);
      cbpid.bindStore(store);

      if (Ext.isEmpty(pidvalue))
        tools.SetValue(mep + me.existPID, gpersist.SELECT_NULL_VALUE);
      else
        tools.SetValue(mep + me.existPID, pidvalue);
    }
  },
  
  OnSave: function () {
    var me = this;
    var mep = me.tranPrefix;
        
    tools.SetValue(mep + 'datadeal', me.dataDeal);
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle: gpersist.STR_DATA_WAIT_TITLE,
        timeout: 3000,
        success: function (form, action) {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnRefresh();
          me.OnRefreshPID();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.existTitle));
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });        
    }
  }
});