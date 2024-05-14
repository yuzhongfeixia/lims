Ext.define('alms.setpwd', {
  extend: 'gpersist.base.editform',  
  plPwd: null,
  winPwd: null,
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      title: '文件授权密码',
      winWidth: 500,
      winHeight: 150,
      saveUrl: 'IncFileSaveIncFilePassword.do'
    });
    
    me.callParent(arguments);
  },
  
  OnFormLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'a_pwd')) {
      Ext.getCmp(mep + 'a_pwd').destroy();
    }
    
    if (!me.plPwd) {
      me.plPwd = Ext.create('Ext.form.Panel', {
        id: mep + 'a_plpwd',
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
          fieldLabel: gpersist.STR_COL_PASSWD,
          name: 'ifp.filepassword',
          id: mep + 'file_passwd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_REPASSWD,
          name: 'ifp.repassword',
          id: mep + 'file_repasswd',
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
    
    if (!me.winPwd) {
      me.winPwd = Ext.create('Ext.window.Window', {
        id: mep + 'a_pwd',
        title: '文件授权密码设置',
        width: 400,
        height: 150,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plPwd],
        buttons: [{ text: gpersist.STR_BTN_SAVE, id: mep +  'btnSave', handler: me.OnSavePasswd, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winPwd.hide(); } }]
      });
    }
    
    me.winPwd.show();
    Ext.getCmp(mep + 'file_passwd').reset();
    Ext.getCmp(mep + 'file_repasswd').reset();
    
  },
  
  OnSavePasswd: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (tools.GetValue(mep + 'file_passwd') != tools.GetValue(mep + 'file_repasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD1);
      return;
    }
    
//    if (!tools.CheckPasswd(tools.GetValue(mep + 'file_passwd'))) {
//      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD2);
//      return;
//    }

    if(me.plPwd) {
      if (me.plPwd.form.isValid()) {
        me.plPwd.form.submit({
          url: tools.GetUrl(me.saveUrl),
          waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert(Ext.String.format('{0}设置成功！', gpersist.STR_COL_PASSWD));
            me.winPwd.hide();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  }
});