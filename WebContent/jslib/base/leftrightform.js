Ext.define('gpersist.base.leftrightform', {
  extend: 'gpersist.base.listeditform',

  plLeft: null,
  plRight: null,
  leftColumnUrl: '',
  rightColumnUrl: '',
  leftStoreUrl: '',
  rightStoreUrl: '',
  leftTitle: '',
  rightTitle: '',
  leftrightParams: {},
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plLeft = null;
    me.plRight = null;
    me.leftColumnUrl = '';
    me.rightColumnUrl = '';
    me.leftStoreUrl = '';
    me.rightStoreUrl = '';
    me.leftTitle = '';
    me.rightTitle = '';
    me.leftrightParams = {};
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSaveDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnSave, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
  },
  
  OnCreateLeftRight: function () {
    var me = this;
    var mep = this.tranPrefix;

    var leftcols = [];
    var leftfields = [];
    var rightcols = [];
    var rightfields = [];
    
    tools.GetGridColumnsByUrl(me.leftColumnUrl, leftcols, leftfields, mep + '_left_');
    tools.GetGridColumnsByUrl(me.rightColumnUrl, rightcols, rightfields, mep + '_right_');

    var leftStore = tools.CreateGridStore(tools.GetUrl(me.leftStoreUrl), rightfields);
    var rightStore = tools.CreateGridStore(tools.GetUrl(me.rightStoreUrl), rightfields);
    
    me.plLeft = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: me.leftTitle,
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
      title: me.rightTitle,
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
    
    Ext.Array.push(me.editControls, 
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plLeft]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plRight]}
      ]}
    );
  },
  
  OnLoadLeftRight: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    me.plLeft.store.on('beforeload', function(store, options) {
      Ext.apply(store.proxy.extraParams, me.leftrightParams);
    });

    me.plRight.store.on('beforeload', function(store, options) {
      Ext.apply(store.proxy.extraParams, me.leftrightParams);
    });

    me.plLeft.store.load();
    me.plRight.store.load(); 
  },
  
  OnGetSaveParams: function () {
    var me = this;
    var json = [];
    for (var i = 0; i < me.plRight.store.getCount(); i++) {
      json.push(me.plRight.store.getAt(i).data);
    }
    
    me.saveParams = {details: Ext.encode(json)};
  }
});