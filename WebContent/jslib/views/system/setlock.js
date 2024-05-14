Ext.define('gpersist.setlock', {
  extend: 'gpersist.base.editform',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: gpersist.STR_WIN_UNLOCK,
      winWidth: 500,
      winHeight: 150,
      saveUrl: gpersist.ACTION_USER_UNLOCK
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.customButtons = [
      ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_UNLOCK, iconCls: 'icon-deal', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor',
          items: [tools.FormText(gpersist.STR_COL_USERID, 'user.userid', mep + 'userid', 14, '96%', false, 1,null,90)]
        },
        { xtype: 'container', columnWidth: .5, layout: 'anchor',
          items: [tools.FormText(gpersist.STR_COL_USERNAME, 'user.username', mep + 'username', 20, '96%', false, 2,null,90)]
        }]
      }];
  },
  
  OnAfterCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    tools.Disabled(['username'], mep);
    
    if (Ext.getCmp(mep + 'userid'))
      Ext.getCmp(mep + 'userid').on('blur', tools.GetUserName, me);
  }
});