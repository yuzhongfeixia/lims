Ext.define('gpersist.hmain', {
  extend: 'gpersist.base.basemain',
  
  constructor : function() {
    var me = this;
    
    Ext.apply(me, { 
      outlookStyle: true,
      mainIcon: 'icon-soft',
      mainTip: '上下布局'
    });
    
    me.callParent();
  },
  
  OnSwitch: function () {
    gpersist.OnMain();
  
  },
  
  OnCretaeMenu: function () {
    var me = this;
    
    var plMenu = Ext.create('Ext.Panel',{
      region: 'west',
      id: 'mainMenu',
      title: gpersist.STR_FUNC_TITLE,
      split: true,
      width: 200,
      minSize: 150,
      maxSize: 400,
      collapsible: true,
      margins: '0 0 2 2',
      layout: {type:'accordion',animate: false},
      listeners: { 'beforerender': function () {
        var record = tools.JsonGet(gpersist.ACTION_USER_GETMENU);
        
        if (record && record.success && record.data) {
          var ms = record.data;
          for (var i = 0; i < ms.length; i++) {
            var tmpPanel = Ext.create('Ext.tree.Panel', {
              rootVisible: false,
              title: ms[i].title,
              autoScroll: true,
              border: false,
              id: 'mg' + i,
              layout: 'fit',
              store: Ext.create('Ext.data.TreeStore', {
                model: 'gpersist.mtreemodel',
                root: {expanded: true,children: ms[i].root}
              }),
              listeners: {
                'itemclick': function (view, record) {
                  me.OnMenuClick(record.data);
                }
              }
            });
            Ext.getCmp('mainMenu').add(tmpPanel);
          }
          Ext.getCmp('mainMenu').doLayout();
        }
      }}
    });
    
    return plMenu;
  }
});
