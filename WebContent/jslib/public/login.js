Ext.define('gpersist.login', {
  
  plLogin: null,  
  plChangePwd: null,  
  winChangePwd: null,
  plDisplay: null,
  loginHtml: '',
   
  constructor : function() {
    var me = this;

    me.plLogin = Ext.create('Ext.form.Panel', {
      id: 'plLogin',
      fieldDefaults: {
        width: 190,
        labelSeparator: '：',
        labelWidth: 45,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:3px;background:transparent repeat-y 0 0',
      width: 200,
      defaultType: 'textfield',
      buttonAlign: 'right',
      closable: false,
      header: false,
      unstyled: true,
      items: [{
        fieldLabel: gpersist.STR_COL_USER,
        name: 'user.userid',
        id: 'name',
        maxLength: 14,
        maxLengthText: Ext.String.format(gpersist.STR_FMT_MAXLEN, gpersist.STR_COL_USER, '14'),
        selectOnFocus: true,
        blankText: Ext.String.format(gpersist.STR_FMT_NOTNUL, gpersist.STR_COL_USER),
        emptyText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_USER),
        allowBlank: false
      }, {
        fieldLabel: gpersist.STR_COL_PASSWD,
        name: 'user.userpassword',
        id: 'password',
        type: 'password',
        inputType: 'password',
        selectOnFocus: true,
        blankText: Ext.String.format(gpersist.STR_FMT_NOTNUL, gpersist.STR_COL_PASSWD),
        allowBlank: false
      },
      tools.FormCheck('记住我', '', 'iscookie', true, 3)],      
      buttons: [{text: gpersist.STR_BTN_LOGIN, iconCls: 'icon-login-user', handler: me.OnLogin, scope: me }, 
        {text: gpersist.STR_BTN_RESET, iconCls: 'icon-login-reset', handler: me.OnReset, scope: me}],      
      listeners: {
        afterRender: function (form, options) {
          this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
            enter: me.OnLogin,
            scope: me
          });
        }
      }
    });

    var viewport = Ext.getCmp('mainView');
    
    if (viewport) {
      me.loginHtml = '<div style="width:100%;height:100%;">' 
        + '<div style="width:100%;height:50%;background-color:' + gpersist.LOGIN_TOP_COLOR + ';"></div>'
        + '<div style="width:100%;height:50%;background-color:' + gpersist.LOGIN_BOTTOM_COLOR + ';margin-bottom:-1px;">'
        + '<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">'
        + '<tr><td height="100%" align="right" valign="bottom" style="font-size:12px">'
        + '版权所有  © 2016&nbsp;&nbsp;</td></tr></table></div>'
        + '</div>'
        + '<div style="width:100%;height:100%;z-index:1000; position:absolute; top:0px;">'
        + '<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">'
        + '<tr><td valign="middle">'
        + '<table cellpadding="0" cellspacing="0" border="0" width="100%" height="458">'
        + '<tr style="background-image:url(images/bg.jpg)">'
        + '<td align="center">'
        + '<table cellpadding="0" cellspacing="0" border="0" width="933" height="458">'
        + '<tr style="background-image:url(images/login.jpg)"><td  valign="bottom" align="right">'
        + '<div style="width:350;height:284px;">'
        + '<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">'
        + '<tr><td align="left" valign="top" height="24px"><span id="errMsg" style="font-size:12px;color:#DE4E4E;padding-left:5px"></span></td>'
        + '<td align="left" valign="top" height="24px"><span id="errMsg1" style="font-size:12px;color:#DE4E4E;padding-left:5px"></span></td></tr>'
        + '<tr><td id="login" align="left" valign="top" height="110" colspan="2"></td></tr><tr><td></td></tr></table>'
        + '</div></td></tr></table></td></tr></table></td></tr></table></div>';
        
      me.plDisplay = Ext.create('Ext.form.Panel', {
        region:'center',
        frame: true,
        title: '',
        header: false,
        unstyled: true,
        bodyStyle: { 'z-index': -10000 },
        html:me.loginHtml
      });
      
      viewport.on('resize', me.OnResize, me); 
      viewport.removeAll();
      viewport.add(me.plDisplay);
      viewport.add(me.plLogin);
      me.plLogin.on('afterlayout', me.OnResize, me);
      me.OnResize();
      
      
      if (!Ext.isEmpty(Ext.util.Cookies.get('userid')))
        tools.SetValue('name', Ext.util.Cookies.get('userid'));

      Ext.getCmp('password').focus(true, 500);
    }
  },
  
  OnResize: function () { 
    try {
      this.plLogin.setPosition(document.getElementById("login").getBoundingClientRect().left,document.getElementById("login").getBoundingClientRect().top);
    }
    catch (e) { };      
  },
  
  OnReset: function() {
    var me = this;
    
    if (me.plLogin)
      me.plLogin.form.reset();
  },
  
  OnLogin: function() {    
    var me = this;    
    
    if (me.plLogin && me.plLogin.form.isValid()) {
      me.plLogin.form.submit({
        url: tools.GetUrl(gpersist.ACTION_USER_LOGIN),
        waitMsg: gpersist.STR_LOGIN_WAIT_MESSAGE,
        waitTitle: gpersist.STR_LOGIN_WAIT_TITLE,
        timeout: gpersist.CON_WAIT_TIMEOUT,
        success: function (form, action) {
          if (action.result && action.result.data && action.result.success) {
            gpersist.UserInfo = action.result.data;
            if (gpersist.UserInfo.user.remark == 'TenDays')
              tools.alert(gpersist.STR_TIP_PASSWD_VALID);
            
            var viewport = Ext.getCmp('mainView');
            viewport.un('resize', me.OnResize, me);
            
            if (tools.GetValue('iscookie')) {
              Ext.util.Cookies.set('userid', tools.GetValue('name'), Ext.Date.add(new Date(), Ext.Date.YEAR, 1));
            }

            gpersist.OnHMain();
          }            
          else
            tools.actionalert(action);
        },
        failure: function (form, action) {
          if (action.result && action.result.data && (action.result.data == 'ChangePassword')) {
            me.OnChangePwd(action.result.msg);
          }
          else
            tools.actionalert(action);
        }
      });
    }
  },
  
  OnChangePwd: function(title) {
    var me = this;
    
    if (!me.plChangePwd) {      
      me.plChangePwd = Ext.create('Ext.form.Panel', {
        id: 'login_plchangepwd',
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
          fieldLabel: gpersist.STR_COL_USER,
          name: 'user.userid',
          id: 'login_userid',
          selectOnFocus: true,
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_USER),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_OLDPASSWD,
          name: 'user.username',
          id: 'login_oldpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_OLDPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_NEWPASSWD,
          name: 'user.userpassword',
          id: 'login_newpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_NEWPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_REPASSWD,
          name: 'user.remark',
          id: 'login_repasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
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
    
    if (!me.winChangePwd) {
      me.winChangePwd = Ext.create('Ext.window.Window', {
        id: 'login_changepwd',
        title: title,
        width: 400,
        height: 180,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plChangePwd],
        buttons: [{ text: gpersist.STR_BTN_SAVE, id: 'btnLoginSave', handler: me.OnSavePasswd, scope: me },
          { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winChangePwd.hide(); } }]
      });
    }
    
    me.winChangePwd.show();
    Ext.getCmp('login_userid').setValue(Ext.getCmp('name').getValue());
    Ext.getCmp('login_userid').setReadOnly(true);
    Ext.getCmp('login_userid').inputEl.addCls('editdisabled');
    Ext.getCmp('login_oldpasswd').reset();
    Ext.getCmp('login_newpasswd').reset();
    Ext.getCmp('login_repasswd').reset();
  },
  
  OnSavePasswd: function () {
    var me = this;

    if (tools.GetValue('login_newpasswd') != tools.GetValue('login_repasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD1);
      return;
    }
    
    if (!tools.CheckPasswd(tools.GetValue('login_newpasswd'))) {
      //tools.alert(gpersist.STR_ERR_CHANGE_PASSWD2);
      //return;
    }

    if (tools.GetValue('login_newpasswd') == tools.GetValue('login_oldpasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD3);
      return;
    }
    
    if(me.plChangePwd) {
      if (me.plChangePwd.form.isValid()) {
        me.plChangePwd.form.submit({
          url: tools.GetUrl(gpersist.ACTION_USER_MUSTCHANGEPWD),
          waitMsg: gpersist.STR_CHANGEPWD_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert(Ext.String.format(gpersist.STR_FMT_MODIFY_OK, gpersist.STR_COL_PASSWD));
            if (Ext.getCmp('login_changepwd'))
              Ext.getCmp('login_changepwd').hide();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  }
});