Ext.define('gpersist.base.basepmt', {
  extend: 'gpersist.base.baseform',
  
  columnUrl: '',
  storeUrl: '',
  pmtTitle: '',
  pmtType: '',
  
  cellEditing: Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
  }),
  
  plMain: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columnUrl = '';
    me.storeUrl = '';
    me.pmtTitle = '';
    me.pmtType = '';
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();
    
    var items = [
      ' ', { id: mep + 'btnRefresh', text: gpersist.STR_BTN_REFRESH, iconCls: 'icon-refresh', handler: me.OnRefresh, scope: me }, '-',
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete, scope: me },
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }, '-',
      ' ', { id: mep + 'btnExport', text: '导出', iconCls: 'icon-export', menu: {items:[
         { id: mep + 'btnExportExcel', text: '导出为EXCEL', iconCls: 'icon-excel', handler: me.OnExport, scope: me },
         { id: mep + 'btnExportSql', text: '导出为SQL文件', iconCls: 'icon-sql', handler: me.OnSql, scope: me },
         { id: mep + 'btnExportSqlAll', text: '全部导出为SQL文件', iconCls: 'icon-sql', handler: me.OnSqlAll, scope: me }]} }, '-', 
      ' ', { text: '', tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];

    //tools.SetToolbar(items, mep);
    tools.SetToolbarSave(items, mep, 'btnSave');
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
      items: items
    });
    
    var lcols = [];
    var lfields = [];
    tools.GetGridColumns(me.columnUrl, lcols, lfields, mep + '_l_');
    var listStore = tools.CreateGridStore(tools.GetUrl(gpersist.ACTION_PMT_LIST) + '?pmttype=' + me.pmtType, lfields);
    
    var plList = Ext.create('Ext.grid.Panel', {
      id: mep + 'pllist',
      title: Ext.String.format(gpersist.STR_FMT_LIST, me.pmtTitle),
      autoScroll: true,
      region: 'west',
      frame: false,
      border: true,
      margins: '2 0 2 2',
      padding: '0 0 0 0',
      width: 250,
      loadMask: true,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: lcols,
      store: listStore,
      listeners: { itemclick: function (v, r) { me.OnList(r); } }
    });
    
    plList.store.on('load', function () {
      if (plList.store.getCount() > 0) {
        var record = plList.store.getAt(0);
        
        plList.selModel.selectByPosition({ row: 0, column: 0 });
        me.OnList(record);
      }
    });

    me.plMain = Ext.create('Ext.grid.Panel', {
      id: mep + 'plmain',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.pmtTitle),
      autoScroll: true,
      region: 'center',
      frame: false,
      border: true,
      margins: '2 2 2 5',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: [],
      tbar: toolbar,
      selModel: new Ext.selection.CheckboxModel({checkOnly:true,injectCheckbox:1}),
      plugins: [me.cellEditing]
    });
    
    Ext.getCmp('tpanel' + me.mid).add(plList);
    Ext.getCmp('tpanel' + me.mid).add(me.plMain);
    
    listStore.load();
  },
  
  OnRefresh: function() {
    var me = this;

    if (me.plMain) {
      me.plMain.store.load();
    }
  },
  
  OnList: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    var cols = [];
    var fields = [];
    
    if (me.plMain) {
      tools.GetGridColumns(record.data.pmtcode, cols, fields, mep + '_m_');
      Ext.each(cols, function (col, idx) {
        if (idx > 0)
          col.text = record.data.pmtname + col.text;
      });

      var store = tools.CreateGridStore(tools.GetUrl(gpersist.ACTION_PMT_TABLE) + '?pmtid=' + record.data.pmtid + '&pmtcode=' + record.data.pmtcode, fields);

      me.plMain.reconfigure(store, cols);

      store.load();
    }
  },
  
  OnNew: function () {
    var me = this;

    if (me.plMain) {
      var record = me.plMain.store.model.create({});
      me.plMain.store.insert(me.plMain.store.getCount(), record);
      me.cellEditing.startEditByPosition({ row: me.plMain.store.getCount() - 1, column: 2 });
    }
  },
  
  OnDelete: function () {
    var me = this;
    var mep = me.tranPrefix;
    var plMain = Ext.getCmp(mep + 'plmain');
    
    if (plMain) {
      var data = plMain.getView().getSelectionModel().getSelection();
      
      if (data.length > 0) {
        Ext.each(data,function(record){
          plMain.store.remove(record);
        });
        
        plMain.getView().refresh();
      }
    }
  },
  
  OnSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    var plList = Ext.getCmp(mep + 'pllist');
    var plMain = Ext.getCmp(mep + 'plmain');
    
    if (plList && plMain) {
      var data = plList.getView().getSelectionModel().getSelection();
      
      if (data.length > 0) {
        var table = data[0];
        
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, Ext.String.format(gpersist.STR_FMT_SAVE_CONFIRM, gpersist.STR_COL_PMT), function (btn) {
          if (btn == 'yes') {
            var json = [];
            for (var i = 0; i < plMain.store.getCount(); i++) {
              json.push(plMain.store.getAt(i).data);
            }
            
            Ext.Ajax.request({
              url: tools.GetUrl(gpersist.ACTION_PMT_SAVE) + '?pmttable=' + table.data.pmttable + '&pmtcode=' + table.data.pmtcode,
              params: { listdata: Ext.encode(json) },
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnRefresh();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else {
        tools.alert(gpersist.STR_TIP_NOSELECTPMT);
      }
    }
  },
  
  OnExport: function () {
    var me = this;
    var mep = me.tranPrefix;
    var plList = Ext.getCmp(mep + 'pllist');
    var plMain = Ext.getCmp(mep + 'plmain');
    
    if (plList && plMain) {
      var data = plList.getView().getSelectionModel().getSelection();
      
      if (data.length > 0) {
        var table = data[0];
        
//        var maskExcel = new Ext.LoadMask(Ext.getBody(), {   
//          msg : gpersist.STR_MSG_EXCEL  
//        });
//        
//        maskExcel.show();
//        
//        var iframe = document.getElementById('export');
//        
//        iframe.onreadystatechange = function () {maskExcel.hide();};
        
        var plExport = Ext.getCmp('plexport');
        
        plExport.form.submit({
          url: tools.GetUrl(gpersist.ACTION_PMT_EXPORT) + '?pmtid=' + table.data.pmtid,
          target: 'export'
        });
      }
    }
  },
  
  OnSql: function () {
    var me = this;
    var mep = me.tranPrefix;
    var plList = Ext.getCmp(mep + 'pllist');
    var plMain = Ext.getCmp(mep + 'plmain');
    
    if (plList && plMain) {
      var data = plList.getView().getSelectionModel().getSelection();
      
      if (data.length > 0) {
        var table = data[0];
        
        var maskExcel = new Ext.LoadMask(Ext.getBody(), {   
          msg : gpersist.STR_MSG_FILE  
        });
        
        maskExcel.show();
        
        var iframe = document.getElementById('export');
        
        iframe.onreadystatechange = function () {maskExcel.hide();};
        
        var plExport = Ext.getCmp('plexport');
        
        plExport.form.submit({
          url: tools.GetUrl(gpersist.ACTION_PMT_SQL) + '?pmtid=' + table.data.pmtid,
          target: 'export'
        });
      }
    }
  },
  
  OnSqlAll: function () {
    var me = this;

    var maskExcel = new Ext.LoadMask(Ext.getBody(), {   
      msg : gpersist.STR_MSG_FILE  
    });
    
    maskExcel.show();
    
    var iframe = document.getElementById('export');
    
    iframe.onreadystatechange = function () {maskExcel.hide();};
    
    var plExport = Ext.getCmp('plexport');
    
    plExport.form.submit({
      url: tools.GetUrl(gpersist.ACTION_PMT_ALLSQL) + '?pmttype=' + me.pmtType,
      target: 'export'
    });
  }
});