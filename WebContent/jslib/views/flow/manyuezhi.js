Ext.define('alms.manyuezhi', {
  extend: 'gpersist.base.listflowform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '阅知事项',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busmyapply',
      storeUrl: 'FlowSearchYueZhi.do',
      expUrl: 'FlowSearchYueZhi.do',
      hasPage: true,
      idPrevNext: 'tranid',
      hasDateSearch: true,
      busflow: 'devapply',
      busflownode: 'check'
    });

    me.callParent(arguments);
  },

  OnInitGridToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.callParent();

    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield', fieldLabel:gpersist.STR_BTN_BEGINDATE, labelWidth:60, width:160, name:'searchbegindate', id:mep + 'searchbegindate',
        format:'Y-m-d', value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1), selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield', fieldLabel:gpersist.STR_BTN_ENDDATE, labelWidth:60, width:160, name:'searchenddate', id:mep + 'searchenddate',
        format:'Y-m-d', value:nowdate, selectOnFocus:false, allowBlank:true},
//      ' ', alms.BarComboLabProject('searchlabid', mep + 'searchlabid', 300, lims.LAB_PROJECT, 60, gpersist.SELECT_ALL_VALUE),
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
    ];
    
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
    
//    Ext.getCmp(mep + 'searchlabid').on('select', me.OnSelectLab, me);
  },
  
//  OnSelectLab: function () {
//    var me = this, mep = me.tranPrefix;
//    
//    var lab = tools.GetValue(mep + 'searchlabid');
//    
//    if (!Ext.isEmpty(lab) && lab != gpersist.SELECT_NULL_VALUE){
//      me.OnSearch();
//    }
//  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'todo.search.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '',
      'todo.search.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : '',
      'todo.tranid': tools.GetValue(mep + 'searchtranid'),
      'todo.labid': tools.GetValue(mep + 'searchlabid')
    };
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_VIEW, iconCls: 'icon-outlook', handler: me.OnDeal, scope: me },
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
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me },
      '-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFile, scope : me }
    ];
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
      items : me.editToolItems
    });
    
    me.editControls = [
      {xtype: 'label', id: mep + 'flowinfo', html: ''},
      {xtype: 'label', id: mep + 'businfo', html: ''},
      {xtype: 'label', id: mep + 'busfile', html: ''},
      {xtype: 'hiddenfield', name: 'todo.tranid', id: mep + 'tranid'},
      {xtype: 'hiddenfield', name: 'todo.busflow', id: mep + 'busflow'},
      {xtype: 'hiddenfield', name: 'todo.flownode', id: mep + 'flownode'},
      {xtype: 'hiddenfield', name: 'todo.todoserial', id: mep + 'todoserial'}
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
  OnViewFile: function () {
	    var me = this, mep = me.tranPrefix;
	    var busfilesss = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data[0];
	    if(!Ext.isEmpty(busfilesss)){
	     alms.PopupFileShow('文件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname);
	    }else{
	    	tools.alert('没有附件');
	        return false;
	    }
	    
	  },

  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix;
    
    me.busflow = record.busflow;
    me.busflownode = record.flownode;
    
    me.flownode = tools.JsonGet('FlowGetFlowNode.do?fn.busflow=' + me.busflow + '&fn.flownode=' + me.busflownode);
    
    var flow = tools.JsonGet('FlowGetBusFlow.do?flow.busflow=' + me.busflow);
    
    if ((me.flownode == null) || Ext.isEmpty(me.flownode.busflow)) {
      tools.alert('错误的流程配置');
      return false;
    }
   
    tools.SetValue(mep + 'tranid', id);
    tools.SetValue(mep + 'busflow', me.busflow);
    tools.SetValue(mep + 'flownode', me.busflownode);
    
    var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=' + me.busflow + '&todo.tranid=' + tools.GetEncode(tools.GetEncode(id)));
    flowinfo = flowinfo || '';    
    Ext.fly(Ext.getCmp(mep + 'flowinfo').getEl()).update(flowinfo + '<br />');
    
    var businfo = tools.HtmlGet(flow.htmldatafunction + '.do?tranid=' + tools.GetEncode(tools.GetEncode(id)));
    businfo = businfo || '';
    Ext.fly(Ext.getCmp(mep + 'businfo').getEl()).update(businfo);
    
    viewFile = function (fileurl,filename) {
       alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
    }
    
    return true;  
  }
});