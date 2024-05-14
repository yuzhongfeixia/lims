Ext.define('alms.glpresetpwd', {
  extend: 'gpersist.base.editform', 
  plResetPwd: null,
  winResetPwd: null,
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      title: '文件授权密码',
      winWidth: 500,
      winHeight: 150,
      saveUrl: 'GlpResetGlpFilePassword.do'
    });
    
    me.callParent(arguments);
  },
  
  OnFormLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'a_resetpwd')) {
      Ext.getCmp(mep + 'a_resetpwd').destroy();
    }
    
    if (!me.plResetPwd) {
      me.plResetPwd = Ext.create('Ext.form.Panel', {
        id: mep + 'a_plresetpwd',
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
          fieldLabel: '用户编号',
          name: 'gfp.userid',
          id: mep +  'userid',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_OLDPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: '用户名称',
          name: 'gfp.username',
          id: mep + 'username',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_NEWPASSWD),
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
    
    if (!me.winResetPwd) {
      me.winResetPwd = Ext.create('Ext.window.Window', {
        id: mep + 'a_resetpwd',
        title: 'GLP文件授权密码重置',
        width: 400,
        height: 150,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plResetPwd],
        buttons: [{ text: gpersist.STR_BTN_RESETPASSWD, id:mep +  'btnSave', handler: me.OnSavePasswd, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winResetPwd.hide(); } }]
      });
    }
    
    me.winResetPwd.show();
    tools.Disabled(['username'], mep);
    tools.Disabled(['userid'], mep);
    tools.SetValue(mep + 'userid',gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'username',gpersist.UserInfo.user.username);
    
    if (Ext.getCmp(mep + 'userid')){
      Ext.getCmp(mep + 'userid').on('blur', tools.GetUserName, me);
    }
   },
  
  OnSavePasswd: function () {
    var me = this;
    var mep = me.tranPrefix;

    if(me.plResetPwd) {
      if (me.plResetPwd.form.isValid()) {
        me.plResetPwd.form.submit({
          url: tools.GetUrl(me.saveUrl),
          waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert(Ext.String.format('{0}重置成功！', gpersist.STR_COL_PASSWD));
            me.winResetPwd.hide();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  }
    
  
  });