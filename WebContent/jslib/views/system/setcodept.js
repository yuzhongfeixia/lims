Ext.define('gpersist.setcodept', {
  extend: 'gpersist.base.listeditform',

  plLeft: null,
  plRight: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plLeft = null;
    me.plRight = null;
  },
  
  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '公司与机构关系',
      winWidth: 750,
      winHeight: 500,
      columnUrl: 'p_get_company',
      storeUrl: 'OrgGetLastCompany.do',
      saveUrl: 'OrgSaveSetDept.do',
      hasPage: false
    });

    me.callParent(arguments);
  },

  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnDeal', text: '分配', iconCls: 'icon-deal', handler: me.OnEdit,scope: me },
      ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSetDept', text: '保存分配', iconCls: 'icon-save', handler: me.OnSave, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    var leftcols = [];
    var leftfields = [];
    var rightcols = [];
    var rightfields = [];
    tools.GetGridColumns('p_set_dept', leftcols, leftfields, mep + '_left_');
    tools.GetGridColumns('p_set_dept', rightcols, rightfields, mep + '_right_');

    var leftStore = tools.CreateGridStore(tools.GetUrl(gpersist.ACTION_DEPT_UNSET), rightfields);
    var rightStore = tools.CreateGridStore(tools.GetUrl(gpersist.ACTION_DEPT_SET), rightfields);
    
    me.plLeft = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '未分配机构',
      margin: '0 5 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 400,
      viewConfig: {
        plugins: {
          ptype: 'gridviewdragdrop',
          dragGroup: 'firstGridDDGroup',
          dropGroup: 'secondGridDDGroup'
        },
        autoFill: true,
        stripeRows: true
      },
      columns: leftcols,
      store: leftStore
    });
    
    me.plRight = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '已分配机构',
      margin: '0 0 0 5',
      padding: '0 0 0 0',
      loadMask: true,
      height: 400,
      columnLines: true,
      multiSelect: true,
      viewConfig: {
        plugins: {
          ptype: 'gridviewdragdrop',
          dragGroup: 'secondGridDDGroup',
          dropGroup: 'firstGridDDGroup'
        },
        autoFill: true,
        stripeRows: true
      },
      columns: rightcols,
      store: rightStore
    });
    
    me.editControls = [{xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('公司编号', 'co.coid', mep + 'coid', 2, '96%', false, 1)]
        },
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('公司名称', 'co.coname', mep + 'coname', 30, '100%', false, 2)]
        }
        ]},
        {xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plLeft]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plRight]}
        ]}
      ];

    me.disNews = ['coid', 'coname'];
    me.disEdits = ['coid', 'coname'];
    me.enNews = [];
    me.enEdits = [];
  },

  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;

    if (item && !Ext.isEmpty(item.coid)) {
      tools.SetValue(mep + 'coid', item.coid);
      tools.SetValue(mep + 'coname', item.coname);
      
      me.plLeft.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          coid: item.coid });
      });
      
      me.plRight.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          coid: item.coid });
      });
      
      me.plLeft.store.load();
      me.plRight.store.load();
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '公司'));
  },
  
  OnGetSaveParams: function () {
    var me = this;
    var json = [];
    for (var i = 0; i < me.plRight.store.getCount(); i++) {
      json.push(me.plRight.store.getAt(i).data);
    }
    
    me.saveParams = {setdepts: Ext.encode(json)};
  }
});