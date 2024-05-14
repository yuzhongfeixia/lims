Ext.define('gpersist.base.listflowform', {
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
  checkUrl: '',
  uncheckUrl: '',
  saveParams: {},
  hasExitPrompt: false,
  
  deferredRender: true,
  
  busflow: '',
  busflownode: '',
  flownode: null,
  button:null,
  
  winWidth: 600,
  winHeight: 220,
  winPrompt: null,
  nowsubmit: true,
  nowdetail: false,
  
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
    me.checkUrl = '';
    me.uncheckUrl = '';
    me.saveParams = {};
    me.hasExitPrompt = false;
    
    me.deferredRender = true;
    
    me.busflow =  '';
    me.busflownode = '';
    me.flownode = null;
    me.button = null;
    
    me.winWidth = 500;
    me.winHeight = 250;
    me.winPrompt = null;
    
    me.nowsubmit = true;
    me.nowdetail = false;
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
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id : mep + 'btnSubmitBatch', text : me.flownode.submittitle, iconCls : 'icon-deal', handler : me.OnSubmitBatchAction, scope : me },      
      ' ', { id: mep + 'btnCancelBatch', text: me.flownode.canceltitle, iconCls: 'icon-deal', handler: me.OnCancelBatchAction, scope: me },
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
    
    tools.SetToolbar(items, mep);
    
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

      
    tools.log(me.busflow);
    
    me.flownode = tools.JsonGet('FlowGetFlowNode.do?fn.busflow=' + me.busflow + '&fn.flownode=' + me.busflownode);
    
    tools.log(me.flownode.busflow);
    
    if ((me.flownode == null) || Ext.isEmpty(me.flownode.busflow)) {
      tools.alert('错误的流程配置');
      return;
    }
    
    mainpanel.removeAll();
    tools.log('OnFormLoad1', date);
    // 生成列表的字段属性
    me.columns = [];
    me.fields = []; 
    tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_'); 
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields); 
    
    // 加载主页面前
    me.OnBeforeFormLoad();
    tools.log('OnFormLoad2', date);
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
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners : {
        'itemdblclick' : { fn : me.OnDeal, scope : me }
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
    
    tools.log('OnFormLoad4', date);
    
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
    
    tools.log('OnFormLoad6', date);
    
    me.tabMain.getTabBar().setVisible(false);
    
    tools.log('OnFormLoad5', date);
    mainpanel.add(me.tabMain);
    tools.log('OnFormLoad7', date);
    
    if (me.hasAutoLoad)
      me.OnSearch();
      
    tools.log('OnFormLoad8', date);
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
      ' ', { id : mep + 'btnSubmit', text :  me.flownode.submittitle, iconCls : 'icon-deal', handler : me.OnSubmitAction, scope : me },      
      ' ', { id: mep + 'btnCancel', text: me.flownode.canceltitle, iconCls: 'icon-deal', handler: me.OnCancelAction, scope: me },
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
    
    var businfo = tools.HtmlGet(me.flownode.htmldatafunction + '.do?tranid=' + id);
    businfo = businfo || '';
    Ext.fly(Ext.getCmp(mep + 'businfo').getEl()).update(businfo);
    
    return true;  
  },
  
  OnDeal: function () {
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
//          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
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
//          me.OnFormValidShow();
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
//          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
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
//          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnSave : function(haswin) {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.apply(me.saveParams, { 
      'pubflow.busflow': me.busflow,
      'pubflow.flownode': me.busflownode
    });
    
    Ext.Ajax.request({
      url : tools.GetUrl((me.nowsubmit ? me.flownode.submitaction : me.flownode.cancelaction) + '.do'),
			params : me.saveParams,
			async : false,
			method : 'POST',
			success : function(response, opts) {
  			tools.ResponseAlert(response, function() {
          me.OnSearch();
           
          if (haswin)
            me.winPrompt.hide();
            
          if (me.nowdetail) {
            me.OnSetData(tools.GetValue(mep + 'tranid'), me.cancelRecord);
          }
  			});
			},
			failure : function(response) {
			  tools.ResponseAlert(response);
			}
		});
  },
  
  OnSubmitAction : function() {
    var me = this, mep = me.tranPrefix;
  
    me.saveParams = { 'pubflow.tranid': tools.GetValue(mep + 'tranid') };
    
    if (!Ext.isEmpty(me.flownode.submitmsg)) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, me.flownode.submitmsg, function(btn) {
        if (btn == 'yes') {
          me.OnSubmit(true, true);
        }
      });
    }
    else
      me.OnSubmit(true, true);
  },
  
  OnCancelAction : function() {
    var me = this, mep = me.tranPrefix;
  
    me.saveParams = { 'pubflow.tranid': tools.GetValue(mep + 'tranid') };
    
    if (!Ext.isEmpty(me.flownode.cancelmsg)) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, me.flownode.cancelmsg, function(btn) {
        if (btn == 'yes') {
          me.OnSubmit(false, true);
        }
      });
    }
    else
      me.OnSubmit(false, true);
  },
  
  OnSubmitBatchAction: function () {
    var me = this, mep = me.tranPrefix;
    
    if (me.plGrid) {    
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        var tranids = [];
        for (i = 0; i < me.plGrid.store.getCount(); i++) {
          var record = me.plGrid.store.getAt(i).data;
          
          tranids.push(eval('record.' + me.idPrevNext));
        }
      
        me.saveParams = { 'pubflow.tranid': Ext.encode(tranids) };
        
        if (!Ext.isEmpty(me.flownode.submitmsg)) {
          Ext.Msg.confirm(gpersist.CONFIRM_TITLE, me.flownode.submitmsg, function(btn) {
            if (btn == 'yes') {
              me.OnSubmit(true, false);
            }
          });
        }
        else
          me.OnSubmit(true, false);
      }
      else {
        tools.alert(me.flownode.submittitle + '没有选择需要处理的数据！');
      }
    }
    else
      return;    
  },
  
  OnCancelBatchAction: function () {
    var me = this, mep = me.tranPrefix;
    
    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        var tranids = [];
        for (i = 0; i < me.plGrid.store.getCount(); i++) {
          var record = me.plGrid.store.getAt(i).data;
          
          tranids.push(eval('record.' + me.idPrevNext));
        }
      
        me.saveParams = { 'pubflow.tranid': Ext.encode(tranids) };
        
        if (!Ext.isEmpty(me.flownode.cancelmsg)) {
          Ext.Msg.confirm(gpersist.CONFIRM_TITLE, me.flownode.cancelmsg, function(btn) {
            if (btn == 'yes') {
              me.OnSubmit(false, false);
            }
          });
        }
        else
          me.OnSubmit(false, false);
      }
      else {
        tools.alert(me.flownode.canceltitle + '没有选择需要处理的数据！');
      }
    }
    else
      return;    
  },
  
  OnSubmit: function (nowsubmit, nowdetail) {
    var me = this, mep = me.tranPrefix;
    
    me.nowsubmit = nowsubmit;
    me.nowdetail = nowdetail;
    
    if ((me.nowsubmit? me.flownode.issubmitenter : me.flownode.iscancelenter)) {
      if (!me.winPrompt)
        me.OnCreatePrompt();
        
      if(me.winPrompt) {       
        
        me.winPrompt.setTitle((me.nowsubmit ? me.flownode.submittitle : me.flownode.canceltitle) + '信息');
        var tododesc = Ext.getCmp(mep + 'tododesc');
        if (tododesc) {
          tododesc.setFieldLabel((me.nowsubmit ? me.flownode.submittitle : me.flownode.canceltitle) + '说明');
          tododesc.reset();
        }
        
        me.winPrompt.show();
      }
    }
    else    
      me.OnSave(false);
  },
  
  OnCreatePrompt: function () {
    var me = this, mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'promptwin')) {
      Ext.getCmp(mep + 'promptwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnSave', text: '提交', iconCls: 'icon-save', handler: me.OnSaveInfo, scope: me },
      '-', ' ', { text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winPrompt.hide(); } }
    ];
    
    var plPrompt = Ext.create('Ext.form.Panel', {
      id:mep + 'plprompt',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items, border: false}),
      items: [tools.FormTextArea((me.nowsubmit ? me.flownode.submittitle : me.flownode.canceltitle) + '说明', 
        'flow.tododesc', mep + 'tododesc', 200, '100%', true, 101, 10)]    
    });
    
    me.winPrompt = Ext.create('Ext.Window', {
      id: mep + 'promptwin',
      title: (me.nowsubmit ? me.flownode.submittitle : me.flownode.canceltitle) + '信息',
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [plPrompt]
    });
  },
  
  OnSaveInfo: function () {
    var me = this, mep = me.tranPrefix;
    
    var plPrompt = Ext.getCmp(mep + 'plprompt');
    
    if (plPrompt && tools.InvalidForm(plPrompt.getForm())) {
      Ext.apply(me.saveParams, { 'pubflow.flowdesc': tools.GetValue(mep + 'tododesc') });
      
      me.OnSave(true);
    }
  },
  
  OnFormClose : function() {
    var me = this;
    
    me.tabMain.setActiveTab(0);
  }
  
  // endregion Button Methods

});