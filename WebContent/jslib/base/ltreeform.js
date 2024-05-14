Ext.define('gpersist.base.ltreeform', {
  extend :'gpersist.base.listform',
  
  plTree: null,
  modelName: '',
  treeUrl: '',
  treeTitle: '',
  treeWidth: 250,
  plMain: null,
  mainTitle: '',
  nowtid: '',
  nowtname: '',
  hasColumnUrl: false,
  hasAutoRefresh: false,
  treeTools: [],
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plTree = null;
    me.modelName = '';
    me.treeUrl = '';
    me.treeTitle = '';
    me.treeWidth = 250;
    me.plMain = null;
    me.mainTitle = '';
    me.nowtid = '';
    me.nowtname = '';
    me.hasColumnUrl = false;
    me.hasAutoRefresh = false;
    me.treeTools = [];
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var tpanle = Ext.getCmp('tpanel' + me.mid);
    
    if (!Ext.isDefined(tpanle))
      return;
    var date = new Date();
    
    
    
    tpanle.removeAll();
    tools.log('OnFormLoad1', date);
    
    if (me.hasColumnUrl) 
      tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_');
    else {
      if (!me.hasRptGrid)
        tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
      else
        tools.GetRptColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
    }
    
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);

    tools.log('OnFormLoad2', date);
    me.OnBeforeFormLoad();

    tools.log('OnFormLoad3', date);
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id: mep + 'grid',
      region: 'center',
      title:me.mainTitle,
      frame: false,
      border: false,
      margins: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: me.columns,
      store: me.gridStore,
      tbar: me.tbGrid,
      listeners: {'itemdblclick' : {fn: me.OnShow, scope:me } }
    });

    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        id: mep + 'gpage',
        store: me.gridStore,
        displayInfo: true,
        displayMsg: gpersist.STR_PAGE_FMT,
        emptyMsg: gpersist.STR_NO_DATA,
        dock: 'bottom'
      }));
    }
    
    me.plMain = Ext.create('Ext.Panel', {
      frame:false,
      autoScroll : false,
      region: 'center',
      border:true,
      items:[me.plGrid],
      layout:'border',
      margins: '2 2 2 5',
      padding: '0 0 0 0'
    });

    me.OnBeforeCreateTree();
    tools.log('OnFormLoad4', date);
    
    me.plTree = Ext.create('Ext.tree.Panel', {
      id:mep + 'pltree',
      rootVisible: false,
      region: 'west',
      title: me.treeTitle,
      width: me.treeWidth,
      autoScroll: true,
      border: true,
      margins: '2 0 2 2',
      layout: 'fit',
      animate: false,
      tbar: Ext.create('Ext.toolbar.Toolbar', { items: me.treeTools }),
      store: Ext.create('Ext.data.TreeStore', {
        model:me.modelName,
        proxy: {
            type: 'ajax',
            url: tools.GetUrl(me.treeUrl)
        }
      }),
      listeners: {
        'itemclick': {fn: function (view, record) {
          me.OnTreeClick(record);
        }, scope:me}
      } 
    });
    
    tools.log('OnFormLoad5', date);
    me.OnAfterFormLoad();
    tools.log('OnFormLoad6', date);
    tpanle.add(me.plTree);
    tools.log('OnFormLoad7', date);
    tpanle.add(me.plMain);
    tools.log('OnFormLoad8', date);
    
    if (me.hasAutoLoad)
      me.OnSearch();    
    tools.log('OnFormLoad9', date);
  },
    
  OnBeforeCreateTree: function () {
  
  },
  
  OnTreeClick: function (record) {
    var me = this;

    me.nowtid = record.data.id;
    me.nowtname = record.data.text;
    
    me.OnSearch();
  },
  
  OnCheckTreeSelect: function (isedit) {
    var me = this;
    
    if (Ext.isEmpty(me.nowtid)) {
      tools.alert('请在树形列表中进行选择！');
      return true;
    }
    
    return false;
  },
  
  OnSearch: function () {
    var me = this;
   
    if (me.OnCheckTreeSelect(false))
      return;
    
    me.callParent();
  },
  
  OnExport: function () {
    var me = this;
   
    if (me.OnCheckTreeSelect(false))
      return;
    
    me.callParent();
  },
  
  OnPrint: function () {
    var me = this;
   
    if (me.OnCheckTreeSelect(false))
      return;

    me.callParent();
  }
});