Ext.define('gpersist.createmenudetail', {
  extend: 'gpersist.base.editform',  
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      title: '功能操作权限生成',
      winWidth: 500,
      winHeight: 150,
      saveUrl: 'UserCreateMenuDetail.do'
    });
    
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.customButtons = [
      ' ', { id: mep + 'btnDeal', text: '生成', iconCls: 'icon-deal', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'label', id: mep + 'displinfo', html: '点击生成按钮开始重新生成菜单操作权限明细数据！' }]
      }];
  }
});