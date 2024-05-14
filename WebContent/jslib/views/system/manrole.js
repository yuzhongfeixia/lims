Ext.define('gpersist.manrole', {
  extend: 'gpersist.base.listeditform',
  
  authGrid : null,
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      editInfo: gpersist.STR_COL_ROLE,
      winWidth: 750,
      winHeight: 500,
      columnUrl: 'p_get_role',
      storeUrl: gpersist.ACTION_ROLE_GET,
      expUrl:gpersist.ACTION_ROLE_GET,
      hasPage: false,
       idPrevNext:'roleid',
      saveUrl: gpersist.ACTION_ROLE_SAVE
    });
    
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit : function() {
    var me = this;
    var mep = this.tranPrefix;
    
    var authcols = [];
    var authfields = [];
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_get_auth', authcols, authfields, mep + 'auth');
    var authStore = tools.CreateGridStore(tools.GetUrl(gpersist.ACTION_ROLE_GETDETAIL), authfields);
    
    Ext.each(authcols, function (col, index) {
      if (col.xtype == 'checkcolumn') {
        col.on('beforecheck', function(check) { 
          if (me.dataDeal == gpersist.DATA_DEAL_SELECT) 
            check.isCancel = true;
          else 
            check.isCancel = false;
        });
      }
    });
    
    me.authGrid = Ext.create('Ext.grid.Panel', {
      id: mep + 'authgrid',
      region: 'center',
      frame: false,
      border: true,
      margins: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      height: 400,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: authcols,
      store : authStore,
      selModel: {selType: 'cellmodel'}
    });
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(gpersist.STR_COL_ROLEID, 'role.roleid', mep + 'editroleid', 4, '96%', false, 1, 'isnumber')
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(gpersist.STR_COL_ROLENAME, 'role.rolename', mep + 'editrolename', 16, '100%', false, 2),                             
          {xtype:'hiddenfield',name:'role.deal.action',id: mep + 'datadeal'}]
        }]
     },
     me.authGrid
    ];
    
    me.disNews = [];
    me.disEdits = ['editroleid'];
    me.enNews = ['editroleid','editrolename'];
    me.enEdits = ['editrolename'];
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    me.authGrid.store.on('beforeload', function (store, options) {      
      Ext.apply(store.proxy.extraParams, {
        roleid: 0 });
    });
    me.authGrid.store.load();
    
    tools.SetValue(mep + 'editroleid', me.plGrid.store.getCount() + 1);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && item.roleid && !Ext.isEmpty(item.roleid)) {
      tools.SetValue(mep + 'editroleid', item.roleid);
      tools.SetValue(mep + 'editrolename', item.rolename); 
      
      if(me.authGrid && me.authGrid.store){
        me.authGrid.store.on('beforeload', function (store, options) {      
          Ext.apply(store.proxy.extraParams, {
            roleid: item.roleid });
        });
        me.authGrid.store.load();
      }
      return true;
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_ROLE));
  },
  
  OnGetSaveParams: function () {
    var me = this;
    
    var json = [];
    for (var i = 0; i < me.authGrid.store.getCount(); i++) {
      if (me.authGrid.store.getAt(i).data.isselect)
        json.push(me.authGrid.store.getAt(i).data);
    }

    me.saveParams = { auths: Ext.encode(json) };
  },
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnValid', text: '有效', iconCls: 'icon-valid', handler: me.OnValid,scope: me},
      ' ', { id: mep + 'btnInValid', text: '无效', iconCls: 'icon-invalid', handler: me.OnInValid,scope: me},
      '-', ' ', { id: mep + 'btnCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnCheck,scope: me},
      ' ', { id: mep + 'btnUnCheck', text: '取消审核', iconCls: 'icon-unaudit', handler: me.OnUnCheck,scope: me},
      //'-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (!me.hasOtherSearch)
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  }
 });