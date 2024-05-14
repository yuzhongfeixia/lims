Ext.define('gpersist.setworkgroup', {
  extend: 'gpersist.base.lteditform',

  cbCompany: null,
  plLeft: null,
  plRight: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plLeft = null;
    me.plRight = null;
    me.cbCompany = null;
  },
  
  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: ['Company'],
      treeTitle: '机构列表',
      mainTitle: '操作员列表',
      editInfo: '分配工作组',
      modelName: 'gpersist.gfdeptmodel',
      treeUrl: 'OrgDeptTree.do',
      winWidth: 750,
      winHeight: 500,
      columnUrl: 'p_get_user',
      storeUrl: 'UserSearchUser.do',
      saveUrl: 'UserSaveSetWorkGroup.do',
      hasAutoLoad: false,
      hasPage: true,
      hasPrevNext: true
    });

    me.callParent(arguments);
  },
  
   OnBeforeCreateTree: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.cbCompany = Ext.create('Ext.form.field.ComboBox', {
      id: mep + 'cbCompany',
      name: 'cbCompany',
      hideLabel: true,
      store: tools.GetParam('Company'),
      displayField: 'name',
      valueField: 'id',
      typeAhead: true,
      queryMode: 'local',
      triggerAction: 'all',
      selectOnFocus: true,
      editable: false,
      width: 240,
      listeners: {
        'select': { fn: function (record, index) {
            me.plTree.getRootNode().removeAll(false);
            var proxy = me.plTree.store.getProxy();
            proxy.url = tools.GetUrl(gpersist.ACTION_DEPT_TREE) + '?coid=' + me.cbCompany.getValue();
            me.plTree.store.setProxy(proxy);
            me.plTree.store.load();
          }, scope: me
        }
      }
    });

    if (me.cbCompany.store.data.length > 0)
      me.cbCompany.setValue(me.cbCompany.store.getAt(0).data.id);
      
    me.treeTools = [me.cbCompany];
    
    me.treeUrl = tools.GetUrl(gpersist.ACTION_DEPT_TREE) + '?coid=' + me.cbCompany.getValue();
  },

  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', {xtype: 'textfield', fieldLabel:'操作员姓名',labelWidth:75,width:200,maxLength:22,name:'listusername',id:mep + 'listusername',allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnDeal', text: '分配', iconCls: 'icon-deal', handler: me.OnEdit,scope: me},
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}
      ]);
    
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
    tools.GetGridColumns('p_get_workgroup', leftcols, leftfields, mep + '_left_');
    tools.GetGridColumns('p_get_workgroup', rightcols, rightfields, mep + '_right_');

    var leftStore = tools.CreateGridStore(tools.GetUrl('UserGetUnSetWorkGroup.do'), rightfields);
    var rightStore = tools.CreateGridStore(tools.GetUrl('UserGetSetWorkGroup.do'), rightfields);
    
    me.plLeft = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '未分配工作组',
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
      title: '已分配工作组',
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
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('操作员编号', 'user.userid', mep + 'userid', 14, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('操作员姓名', 'user.username', mep + 'username', 20, '100%', false, 2),
          {xtype:'hiddenfield',name:'user.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plLeft]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plRight]}
      ]}
    ];

    me.disNews = ['userid','username'];
    me.disEdits = ['userid','username'];
    me.enNews = [];
    me.enEdits = [];
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'user.deptid': me.nowtid,
          'user.username': tools.GetValueEncode(mep + 'listusername')   
        });
      });
    }
  },
  
  OnCheckTreeSelect: function (isedit) {
    var me = this;
    
    if (isedit)
      return false;
    
    if (Ext.isEmpty(me.nowtid)) {
      tools.alert('请先在树形列表中选择机构再进行查询！');
      return true;
    }
    
    return false;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;

    if (item && item.userid && !Ext.isEmpty(item.userid)) {

      tools.SetValue(mep + 'userid', item.userid);
      tools.SetValue(mep + 'username', item.username);
      
      me.plLeft.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          userid: item.userid });
      });
      
      me.plRight.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          userid: item.userid });
      });
      
      me.plLeft.store.load();
      me.plRight.store.load();
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_USER));
  },
  
  OnGetSaveParams: function () {
    var me = this;
    var json = [];
    for (var i = 0; i < me.plRight.store.getCount(); i++) {
      json.push(me.plRight.store.getAt(i).data);
    }
    
    me.saveParams = {sets: Ext.encode(json)};
  }
});