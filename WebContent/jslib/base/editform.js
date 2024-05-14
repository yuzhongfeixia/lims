Ext.define('gpersist.base.editform', {
  extend :'gpersist.base.baseform',
  
  plEdit: null,
  winEdit: null,
  customButtons: [],
  editControls: [],
  winWidth: 600,
  winHeight: 250,
  hasExitPrompt: false,
  dataDeal: gpersist.DATA_DEAL_SELECT,
  saveUrl: '',
  saveParams: {},
  originalData: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plEdit = null;
    me.winEdit = null;
    me.customButtons = [];
    me.editControls = [];
    me.winWidth = 600;
    me.winHeight = 250;
    me.hasExitPrompt = false;
    me.dataDeal = gpersist.DATA_DEAL_SELECT;
    me.saveUrl = '';
    me.saveParams = {};
    me.originalData = null;
  },

  OnBeforeCreateEdit: function () {
    
  },
  
  OnAfterCreateEdit: function () {
    
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
   
    if (Ext.getCmp(mep + 'winEdit')) {
      Ext.getCmp(mep + 'winEdit').destroy();
    }
    
    var items = [
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    me.OnBeforeCreateEdit();
    
    Ext.Array.insert(items, 0, me.customButtons);
    
    tools.SetToolbar(items, mep);
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.DEFAULT_LABELWIDTH,
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
      tbar:Ext.create('Ext.toolbar.Toolbar', { items: items }),   
      items: me.editControls
    });
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'winEdit',
      title: me.title,
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      items: [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });

    me.winEdit.show();
    me.OnAfterCreateEdit();
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
      
      me.dataDeal = gpersist.DATA_DEAL_SELECT; 
    }
    
    return true;
  }, 
  
  OnBeforeSave: function () {
    return true;
  },
  
  OnAfterSave: function () {
    
  },
  
  OnGetSaveParams: function () {
    
  },
  
  OnSave: function () {
    var me = this;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(me.saveUrl),
        params: me.saveParams,
        waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle: gpersist.STR_DATA_WAIT_TITLE,
        timeout: 3000,
        success: function (form, action) {
          me.OnAfterSave();
          tools.ErrorAlert(action);
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });        
    }
  }
});