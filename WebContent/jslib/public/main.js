Ext.define('gpersist.main', {
  extend: 'gpersist.base.basemain',
  
  constructor : function() {
    var me = this;
    
    Ext.apply(me, { 
      topHeight: 60,
      mainMargin: '0 2 2 0',
      outlookStyle: false,
      mainIcon: 'icon-outlook',
      mainTip: '左右布局'
    });
    
    me.callParent();
  },
  
  OnSwitch: function () {
    gpersist.OnHMain();
  },
  
  OnCretaeMenu: function () {
    var me = this;
    
    var tbMenus = Ext.create('Ext.toolbar.Toolbar');
    
    var record = tools.JsonGet(gpersist.ACTION_USER_GETMENU);
    if (record && record.success && record.data) {
      var ms = record.data;
      for (var i = 0; i < ms.length; i++) {        
        tbMenus.add({text:ms[i].title,menu:me.GetMenus(ms[i].root)});
      }
    }
    
    return tbMenus;
  },
  
  GetMenus: function (root) {  
    var me = this;
    
    var menus = Ext.create('Ext.menu.Menu', {
      style: { overflow: 'visible' }
    }); 

    if (root) {
      Ext.each(root, function (menu, index) {    
        menus.add(me.GetSubMenus(menu));
      });
    }
    
    return menus;
  },
  
  GetSubMenus: function (menu) {
    var me = this;
    var subs = [];
    
    if (menu.children) {
      if (menu.children.length > 0) {
        for (var i = 0; i < menu.children.length; i++) {
          subs.push(me.GetSubMenus(menu.children[i]));
        }

        return {
          text: menu.text,
          iconCls: menu.iconCls,
          menu: {items: subs}
        };
      }
      else {
       return {
          text: menu.text,
          iconCls: menu.iconCls,
          handler: function () { me.OnMenuClick(menu); },
          scope: me
        };
      }
    }
  }
});