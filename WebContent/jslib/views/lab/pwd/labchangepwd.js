Ext.define('alms.labchangepwd', {
  extend: 'gpersist.base.editform', 
  plChPwd: null,
  winChPwd: null,
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      title: '修改签名授权密码',
      winWidth: 500,
      winHeight: 150,
      saveUrl: 'LabChangeSignerPassword.do'
    });
    
    me.callParent(arguments);
  },
  
  OnFormLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep +  'signer_changepwd')) {
      Ext.getCmp(mep +  'signer_changepwd').destroy();
    }
    
    if (!me.plChPwd) {
      me.plChPwd = Ext.create('Ext.form.Panel', {
        id: mep + 'signer_plChPwd',
        fieldDefaults: {
          width: 370,
          labelSeparator: '：',
          labelWidth: 90,
          labelPad: 0,
          labelStyle: 'font-weight:bold;'
        },
        title: '',
        border: false,
        bodyStyle: 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width: 400,
        defaultType: 'textfield',
        items: [{
          fieldLabel: gpersist.STR_COL_OLDPASSWD,
          name: 'lsp.oldpassword',
          id: mep + 'signer_oldpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_OLDPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_NEWPASSWD,
          name: 'lsp.signerpassword',
          id: mep + 'signer_newpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_NEWPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_REPASSWD,
          name: 'lsp.repassword',
          id: mep + 'signer_repasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_REPASSWD),
          allowBlank: false
        }],
        listeners: {
          afterRender: function (form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter: me.OnSavePasswd,
              scope: me
            });
          }
        }
      });
    }
    
    if (!me.winChPwd) {
      me.winChPwd = Ext.create('Ext.window.Window', {
        id: mep +  'signer_changepwd',
        title: '修改签名授权密码',
        width: 400,
        height: 150,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plChPwd],
        buttons: [{ text: gpersist.STR_BTN_SAVE, id: mep + 'btnSave', handler: me.OnSavePasswd, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winChPwd.hide(); } }]
      });
    }
    
    me.winChPwd.show();
    Ext.getCmp(mep + 'signer_oldpasswd').reset();
    Ext.getCmp(mep + 'signer_newpasswd').reset();
    Ext.getCmp(mep + 'signer_repasswd').reset();
    
  },
  
  OnSavePasswd: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (tools.GetValue(mep + 'signer_newpasswd') != tools.GetValue(mep + 'signer_repasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD1);
      return;
    }
    
    if (!tools.CheckPasswd(tools.GetValue(mep + 'signer_newpasswd'))) {
      //tools.alert(gpersist.STR_ERR_CHANGE_PASSWD2);
      //return;
    }

    if (tools.GetValue(mep + 'signer_newpasswd') == tools.GetValue(mep + 'signer_oldpasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD3);
      return;
    }

     if(me.plChPwd) {
      if (me.plChPwd.form.isValid()) {
        me.plChPwd.form.submit({
          url: tools.GetUrl(me.saveUrl),
          waitMsg: gpersist.STR_CHANGEPWD_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert(Ext.String.format(gpersist.STR_FMT_MODIFY_OK, gpersist.STR_COL_PASSWD));
            me.winChPwd.hide();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  }
});