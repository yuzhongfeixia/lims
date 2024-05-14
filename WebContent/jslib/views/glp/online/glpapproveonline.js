Ext.define('alms.glpapproveonline', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: 'GLP电子文件阅读审批',
      winWidth: 400,
      winHeight: 100,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_incfileonline',
      storeUrl: 'GlpSearchGlpFileOnline.do',
      saveUrl: 'GlpApproveGlpFileOnline.do',
      expUrl: 'GlpSearchGlpFileOnline.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: '同意', iconCls: 'icon-edit', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnView', text:'驳回', iconCls: 'icon-edit', handler: me.OnBack,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'gfo.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename')),
          'gfo.flowstatus': '30'  // 30 审核通过
        });
      });
    }
  },
  
  OnBack: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要驳回的glp文件申请！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
      
    if(record){
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '确定驳回申请', function(btn) {
        if (btn == 'yes') {
            Ext.Ajax.request({
                url: 'GlpDisapproveGlpFileOnline.do',
                params: {'gfo.tranid':record.tranid},
                async: false,
                method: 'POST',
                success: function (response, opts) {
                  tools.ResponseAlert(response,function(){
                    me.OnSearch();
                  }) 
                },
                failure: function (response) {
                  tools.ErrorAlert('提交失败！');
                }
            });
        }
     });
    }
   },
  
  OnNew:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要授权的glp文件！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
      
    if (!me.authPwd)
      me.OnCreateEditWin();
    
    if (me.authPwd && record) {
     me.authPwd.show();
     Ext.getCmp(mep + 'file_passwd').reset();
     tools.SetValue(mep + 'tranid',record.tranid);
    }
  },
  
  OnCreateEditWin: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.plAuthPwd) {
      me.plAuthPwd = Ext.create('Ext.form.Panel', {
        id: mep + 'plauthpwd',
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
          fieldLabel: '密码',
          name: 'filepassword',
          id: mep + 'file_passwd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
          allowBlank: false
        },
        {
          xtype:'hiddenfield',name:'gfo.tranid',id: mep + 'tranid'
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
    
    if (!me.authPwd) {
      me.authPwd = Ext.create('Ext.window.Window', {
        id: mep + 'authpwd',
        title: 'GLP文件查阅授权',
        width: 400,
        height: 100,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plAuthPwd],
        buttons: [{ text: gpersist.STR_BTN_SUBMIT, id: mep +  'btnSave', handler: me.OnSavePasswd, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.authPwd.hide(); } }]
      });
    }
  },
  
  OnSavePasswd: function () {
    var me = this;
    var mep = me.tranPrefix;

    if(me.plAuthPwd) {
      if (me.plAuthPwd.form.isValid()) {
        me.plAuthPwd.form.submit({
          url: tools.GetUrl(me.saveUrl),
          waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert('提交成功！');
            me.authPwd.hide();
            me.OnSearch();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  }

});