Ext.define('gpersist.base.viewflowform', {
  extend : 'gpersist.base.baseform',
  
  columnUrl: '',
  storeUrl: '',
  expUrl: '',
  
  columns: [],
  fields: [],
  plGrid: null,   
  tbGrid: null,
  gridStore: null,
  hasPage: true,
  hasPageSize: true,
  hasGridSelect: true,
  
  hasDateSearch: false,
  idBeginDate: 'searchbegindate',
  idEndDate: 'searchenddate',
  
  hasExportBegin: true,
  hasExportPage: false,
  
  hasEdit: true,
  plEdit: null,
  tbEdit: null,
  
  hasPrevNext: true,
  editPadding: 5,
  editControls: [],
  
  hasAutoLoad: true,
  editInfo: '',
  
  cancelRecord: null,
  idPrevNext: '', 
  
  deferredRender: true,
  
  busflow: '',
  htmlUrl: '',
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.columnUrl = '';
    me.storeUrl = '';
    me.expUrl = '';
  
    me.columns = [];
    me.fields = [];
    me.plGrid = null;   
    me.tbGrid = null;
    me.gridStore = null;
    me.hasPage = true;
    me.hasPageSize = true;
    me.hasGridSelect = true;
  
    me.hasDateSearch = false;
    me.idBeginDate = 'searchbegindate';
    me.idEndDate = 'searchenddate';
  
    me.hasExportBegin = true;
    me.hasExportPage = false;
  
    me.hasEdit = true;
    me.plEdit = null;
    me.tbEdit = null;
  
    me.hasPrevNext = true;
    me.editPadding = 5;
    me.editControls = [];
  
    me.hasAutoLoad = true;
    me.editInfo = '';
    
    me.cancelRecord = null;
    me.idPrevNext = '';
    
    me.deferredRender = true;
    
    me.busflow = '';
    me.htmlUrl = '';
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
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-soft', handler: me.OnView, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    if (me.hasDateSearch)
      Ext.Array.insert(items, 2, [
        ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
        ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }
      ]);
        
    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
        
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
    
    // 生成列表的字段属性
    me.columns = [];
    me.fields = []; 
    tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_'); 
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields); 
    
    // 加载主页面前
    me.OnBeforeFormLoad();
    
    // 生成列表
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id : mep + 'grid',
      region : 'center',
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : me.columns,
      store : me.gridStore,
      tbar : me.tbGrid,
      enableColumnMove: false, 
      enableColumnHide: false,
      suspendLayout: false,
      listeners : {
        'itemdblclick' : { fn : me.OnView, scope : me }
      }
    });
    
    // 分页处理
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store : me.gridStore,
        displayInfo : true,
        displayMsg : gpersist.STR_PAGE_FMT,
        emptyMsg : gpersist.STR_NO_DATA, suspendLayout: true,
        dock : 'bottom'
      }));
    }

    // 主界面生成后处理
    me.OnAfterFormLoad();

    // 建立编辑界面
    me.OnCreateEditWin();

    var pleditview = Ext.create('Ext.Panel', {
      frame : false,
      autoScroll : false,
      region : 'center',
      border : false,
      layout : 'border',
      margins : '0 0 0 0',
      padding : '0 0 0 0'
    });
    
    if (me.hasEdit)
      pleditview.add(me.plEdit);
      
    if (me.hasDetail) {
      if (me.plDetail && !me.hasEdit)
        me.plDetail.region = 'center';
        
      pleditview.add(me.plDetail);
    }
    
    me.tabMain = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : me.deferredRender,
      items :
        [me.plGrid, pleditview]
    });    
    
    me.tabMain.getTabBar().setVisible(false);
    
    mainpanel.add(me.tabMain);
    
    if (me.hasAutoLoad)
      me.OnSearch();
    
  },  
  
  // region Create Edit Panel
  
  OnBeforeCreateEdit: function () {
    
  },
  
  OnAfterCreateEdit: function () {
    
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
      items : me.editToolItems
    });
    
    me.editControls = [
      {xtype: 'label', id: mep + 'flowinfo', html: ''},
      {xtype: 'label', id: mep + 'businfo', html: ''},
      {xtype: 'label', id: mep + 'busfile', html: ''},
      {xtype: 'hiddenfield', name: 'todo.tranid', id: mep + 'tranid'}
    ];
    
    me.OnBeforeCreateEdit();
    
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
      tbar : me.tbEdit,
      items : me.editControls
    });
    
    me.OnAfterCreateEdit();
    
  },
  
  // endregion Create Edit Panel
    
  // region Button Methods
  
  // 获得查询和导出的参数
  OnGetSearchParam: function () {    
    return {};
  },
  
  // 查询前处理
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid.store) {
      me.plGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, me.OnGetSearchParam());
      });
    }
  },
  
  // 查询处理
  OnSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plGrid && me.plGrid.store) {
      me.OnBeforeExportCheck();
      
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
  
  // 上一天查询
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;    
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, -1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, -1));

    me.OnSearch();
  },

  // 后一天查询
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.GetValue(mep + me.idBeginDate))
      tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, 1));
    if (tools.GetValue(mep + me.idEndDate))
      tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, 1));

    me.OnSearch();
  },
  
  // 导出前判断
  OnBeforeExportCheck: function () {
    return false;
  },
  
  // 获得导出的查询参数
  OnGetExportParams: function (params) {
    var me = this;
    
    Ext.apply(params, me.OnGetSearchParam());
  },
  
  // 导出前处理
  OnBeforeExport: function () {
    
  },
  
  // 导出处理
  OnExport: function () {
    var me = this;

    if (me.plGrid.store.getCount() <= 0) {
      tools.alert(gpersist.STR_MSG_NOEXP);
      return;
    }
    
    me.OnBeforeExportCheck();
    
    if (Ext.isEmpty(me.expUrl))
      return;
    
//    var maskExcel = new Ext.LoadMask(Ext.getBody(), {   
//      msg : gpersist.STR_MSG_EXCEL  
//    });
//    
//    maskExcel.show();
//    
//    var iframe = document.getElementById('export');
//    
//    iframe.onreadystatechange = function () {maskExcel.hide();};

    var plExport = Ext.getCmp('plexport');
    
    var params = {
      columns: tools.GetExportColumn(me.plGrid),
      start: me.hasExportBegin ? 0 : (me.plGrid.store.currentPage - 1) * me.plGrid.store.pageSize,
      expcnt: me.hasExportPage ? me.plGrid.store.pageSize : 0,
      hasexport: true
    };
    
    me.OnGetExportParams(params);

    plExport.form.submit({
      url: me.expUrl,
      params: params,
      target: 'export'
    });
  },
  
  // 列表打印前处理
  OnBeforePrint: function () {
    
  },
  
  // 列表打印
  OnPrint: function () {
    var me = this;
    
    me.OnBeforePrint();
    
    Ext.ux.grid.Printer.print(me.plGrid);
  }, 
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isEmpty(eval('record.' + me.idPrevNext))) {        
      return me.OnSetData(eval('record.' + me.idPrevNext), record);
    }
    else {
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_NOLOAD, me.editInfo));
    }
    
    return false;
  },
  
  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix;
    
    tools.SetValue(mep + 'tranid', id);
    
    var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=' + me.busflow + '&todo.tranid=' + id);
    flowinfo = flowinfo || '';
    Ext.fly(Ext.getCmp(mep + 'flowinfo').getEl()).update(flowinfo + '<br />');
    
    var businfo = tools.HtmlGet(me.htmlUrl + '.do?tranid=' + id);
    businfo = businfo || '';
    Ext.fly(Ext.getCmp(mep + 'businfo').getEl()).update(businfo);
    
    return true;  
  },
  
  OnView: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要处理的数据！');
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      
      me.tabMain.setActiveTab(1);
      
      if (!me.OnLoadData(record)) {
        me.tabMain.setActiveTab(0);
        return;
      }
      
      me.cancelRecord= record;
    }
  },
  
  OnCheckPrevNext : function(record) {
    var me = this;
    var mep = me.tranPrefix;

    if (!me.hasPrevNext || Ext.isEmpty(me.idPrevNext))
      return false;
    
    return (eval('record.' + me.idPrevNext) == tools.GetValue(mep + me.idPrevNext));
  },
  
  OnPrevRecord : function() {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnPrevRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) {
            tools.alert('已经是当前列表第一条数据！');
            return;
          }
          
          me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnNextRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
        
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) {
            tools.alert('已经是当前列表最后一条数据！');
            return;
          }
          
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnFormClose : function() {
    var me = this;
    
    me.tabMain.setActiveTab(0);
  }
  
  // endregion Button Methods
});