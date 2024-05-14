Ext.define('gpersist.base.viewform', {
  extend : 'gpersist.base.baseform',
  
  plEdit: null,
  
  editPadding: 5,
  editControls: [],
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plEdit = null;
    
    me.editPadding = 5;
    me.editControls = [];
  },
  
  // 初始化列表页面工具条容器
  OnInitGridToolBar: function () {
    var me = this;
    
    me.tbGrid = Ext.create('Ext.container.Container', {
      listeners: {
        afterRender: function (form, options) {
          this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
            enter: me.OnSearch,
            scope: me
          });
        }
      }
    });
  },
  
  // 加载页面前，初始化工具条
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  // 加载页面后处理
  OnAfterFormLoad: function () {
    
  },
  
  // 加载主页面
  OnFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var date = new Date();
    var mainpanel = Ext.getCmp('tpanel' + me.mid);
    
    if (!Ext.isDefined(mainpanel))
      return;
    
    mainpanel.removeAll();
    
    me.OnBeforeFormLoad();
    
    me.editControls = [{xtype: 'label', id: mep + 'infocontect', html: ''}];
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      autoScroll: true,
      region : 'center',
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbGrid,
      items : me.editControls
    });
    
    mainpanel.add(me.plEdit);    
  },  
  
  OnSearch: function () {

  }

});