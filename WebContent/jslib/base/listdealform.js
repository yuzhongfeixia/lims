Ext.define('gpersist.base.listdealform', {
  extend :'gpersist.base.baseform',
  
  plGrid: null,    
  columnUrl: '',  
  storeUrl: '',  
  tbGrid: null,    
  columns: [],    
  fields: [],    
  gridStore: null,    
  hasPage: true,     
  hasPageSize: true,
  hasAutoLoad: true,
  hasColumnUrl: false,
  hasGridSelect: false,
  cellEditing: null,
  hasDetailEdit: true,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columns = [];
    me.fields = [];
    me.plGrid = null;
    me.columnUrl = '';
    me.storeUrl = '';
    me.gridStore = null;
    me.hasPage = true;
    me.hasPageSize = true;
    me.hasAutoLoad = true;
    me.hasColumnUrl = false;
    me.hasGridSelect = false;
    me.cellEditing = null;
    me.hasDetailEdit = true;
  },
  
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
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];   

    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
               
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items});
               
    me.tbGrid.add(toolbar);
  },
  
  OnAfterFormLoad: function () {
    
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    me.fields = [];
    me.columns = [];
    
    if (me.hasColumnUrl) 
      tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_');
    else 
      tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
    
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);
    
    me.OnBeforeFormLoad();

    if (me.hasDetailEdit) {
      me.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1,
        listeners: {
          edit: { fn: me.OnDetailEdit, scope: me}
        }
      });

      plugins = [me.cellEditing];
    }
    
    me.OnBeforeCreateDetail();
    
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id: mep + 'grid',
      region: 'center',
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
      features :
        [{
          ftype : 'summary'
        }],
      columns: me.columns,
      store: me.gridStore,
      tbar: me.tbGrid,
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      plugins: plugins
    });
        
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store: me.gridStore,
        displayInfo: true,
        displayMsg: gpersist.STR_PAGE_FMT,
        emptyMsg: gpersist.STR_NO_DATA,
        dock: 'bottom'
      }));
    }
    
    me.OnAfterFormLoad();
    
    Ext.getCmp('tpanel' + me.mid).add(me.plGrid);
    
    if (me.hasAutoLoad)
      me.OnSearch();    
  },
  
  OnDetailEdit: function (editor, e) {
    var me = this;
    me.plGrid.getView().refresh();
  },
  
  OnBeforeSearch: function () {
    
  },
  
  OnBeforeCreateDetail : function() {
    
  },
  
  OnBeforeExportCheck: function () {
    
  },
  
  OnSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid && me.plGrid.store) {
      
      if (me.OnBeforeExportCheck())
        return;
      
      me.OnBeforeSearch();
      
      if (me.hasPage) {
        if (me.hasPageSize)
          me.plGrid.store.pageSize = tools.GetValue(mep + 'btnPageSize') || gpersist.DEFAULT_PAGESIZE;
        me.plGrid.store.loadPage(1);
      }
      else
        me.plGrid.store.load();
    }
  },
  
  OnDeal: function () {
    
  },
  
  OnOperSearch: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var cbuser = Ext.getCmp(mep + 'selectbusoper'); 
    
    if (cbuser.store) {
      cbuser.store.on('beforeload', function (store, options) {      
          Ext.apply(store.proxy.extraParams, {
            'oper.opername': tools.GetValueEncode(mep + 'searchopername'),
            'oper.wsid': tools.GetValue(mep + 'searchoperws')
          });
        });
      }
    
    cbuser.store.loadPage(1);
  },
  
  OnOperBeforeLoad: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var cbuser = Ext.getCmp(mep + 'selectbusoper'); 
    
    if (cbuser.store) {
      cbuser.store.on('beforeload', function (store, options) {      
          Ext.apply(store.proxy.extraParams, {
            'oper.opername': tools.GetValueEncode(mep + 'searchopername'),
            'oper.wsid': tools.GetValue(mep + 'searchoperws')
          });
        });
      }
  },
  
  OnOperFocus: function (render) {
    var me = this;
    
    render.record = me.cellEditing.getActiveRecord();
    render.hasSelect = false;
  },
  
  OnOperValid: function(render) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
      return;
    
    var operid = tools.GetValue(mep + 'selectbusoper');

    var now = render.record;
    
    if (now && now.data) {      
      if (Ext.isEmpty(operid)) {
        now.data.operid = '';
        now.data.opername = '';
        tools.SetValue(mep + 'selectbusoper', '');
      }
      else {
        var item = tools.JsonGet(tools.GetUrl('BusGetBusUser.do?oper.operid=') + operid);
  
        if (item && !Ext.isEmpty(item.operid)) {
          now.data.operid = Ext.util.Format.trim(item.operid);
          now.data.opername = Ext.util.Format.trim(item.opername);
        }
        else {
          now.data.operid = '';
          now.data.opername = '';
          tools.SetValue(mep + 'selectbusoper', '');
        }
      }
  
      me.plGrid.getView().refresh();
      
      render.record = null;
    }
  },
  
  OnOperSelect: function (render, item) {
    var me = this;
    
    if (item && item.operid && me.cellEditing) {
      var now = me.cellEditing.getActiveRecord();

      if (now) {
        me.cellEditing.getActiveEditor().setValue(Ext.util.Format.trim(item.opername));
        
        now.data.operid = Ext.util.Format.trim(item.operid);
        
        me.plGrid.getView().refresh();
      }
    }
  }
});