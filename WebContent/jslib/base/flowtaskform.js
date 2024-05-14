Ext.define('gpersist.base.flowtaskform', {
  extend :'gpersist.base.listflowform',
  
  OnInitGridToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.callParent();

    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield', fieldLabel:gpersist.STR_BTN_BEGINDATE, labelWidth:60, width:160, name:'searchbegindate', id:mep + 'searchbegindate',
        format:'Y-m-d', value:Ext.Date.add(nowdate, Ext.Date.DAY, 1 - nowdate.getDate()), selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield', fieldLabel:gpersist.STR_BTN_ENDDATE, labelWidth:60, width:160, name:'searchenddate', id:mep + 'searchenddate',
        format:'Y-m-d', value:nowdate, selectOnFocus:false, allowBlank:true},
      ' ', lims.BarComboLabProject('searchlabid', mep + 'searchlabid', 300, lims.LAB_PROJECT, 60, gpersist.SELECT_ALL_VALUE),
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
    ];
    
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
    
    Ext.getCmp(mep + 'searchlabid').on('select', me.OnSelectLab, me);
  },
  
  OnSelectLab: function () {
    var me = this, mep = me.tranPrefix;
    
    var lab = tools.GetValue(mep + 'searchlabid');
    
    if (!Ext.isEmpty(lab) && lab != gpersist.SELECT_NULL_VALUE){
      me.OnSearch();
    }
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'todo.search.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '',
      'todo.search.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : '',
      'todo.tranid': tools.GetValue(mep + 'searchtranid'),
      'todo.labid': tools.GetValue(mep + 'searchlabid'),
      'todo.todotype': '2',
      'todo.busflow': me.busflow,
      'todo.flownode': me.busflownode
    };
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSubmit', text :  me.flownode.submittitle, iconCls : 'icon-deal', handler : me.OnSubmitAction, scope : me },      
      ' ', { id: mep + 'btnCancel', text: me.flownode.canceltitle, iconCls: 'icon-deal', handler: me.OnCancelAction, scope: me },
      '-', ' ', { id: mep + 'btnViewRecord', text: '查看原始记录', iconCls: 'icon-deal', handler: me.OnViewRecord, scope: me },
      ' ', { id: mep + 'btnViewReport', text: '查看试验报告', iconCls: 'icon-deal', handler: me.OnViewReport, scope: me },
      ' ', { id: mep + 'btnViewTodo', text: '查看流程', iconCls: 'icon-deal', handler: me.OnViewTodo, scope: me },
      '-', ' ', { id: mep + 'btnDownAndPrint', text: '打印和下载', iconCls: 'icon-print', menu: { items: [
          { id: mep + 'btnPrintRecord', text: '打印原始记录表', iconCls: 'icon-print', handler: me.OnPrintRecord, scope: me },
          { id: mep + 'btnDownPdf', text: '下载原始记录表', iconCls: 'icon-down', handler: me.OnDownPdf, scope: me },
          { id: mep + 'btnDownExcel', text: '下载原始记录为电子表格', iconCls: 'icon-excel', handler: me.OnDownExcel, scope: me },
          { id: mep + 'btnPrintReport', text: '打印试验报告', iconCls: 'icon-print', handler: me.OnPrintReport, scope: me },
          { id: mep + 'btnDownPdfReport', text: '下载试验报告', iconCls: 'icon-down', handler: me.OnDownPdfReport, scope: me },
          { id: mep + 'btnDownExcelReport', text: '下载试验报告为电子表格', iconCls: 'icon-excel', handler: me.OnDownExcelReport, scope: me }
        ]}},
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
  
  OnViewRecord: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.cancelRecord)
      return;
    
    lims.GetRecordHtml(me.cancelRecord.tranid, mep + 'businfo');
  },
  
  OnViewReport: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.cancelRecord)
      return;
    
    lims.GetReportHtml(me.cancelRecord.tranid, mep + 'businfo');
  },
  
  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix;
    
    tools.SetValue(mep + 'tranid', id);
    
    lims.GetRecordHtml(id, mep + 'businfo');
    
    return true;  
  },
  
  OnViewTodo: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.cancelRecord)
      return;
    
    var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTest.do?todo.tranid=' + me.cancelRecord.tranid);
    flowinfo = flowinfo || '';
    Ext.fly(Ext.getCmp(mep + 'businfo').getEl()).update(flowinfo);
  },
  
  OnPrintRecord: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.cancelRecord)
      return;
    
    var html = lims.GetRecordHtmlByTask(me.cancelRecord.tranid, false);
    
    if (!Ext.isEmpty(html)) {
      lims.PrintRecordHtml(html);
    }
    else
      tools.alert('无原始记录打印内容');
  },
  
  OnDownExcel: function () {
    var me = this, mep = me.tranPrefix;

    if (!me.cancelRecord)
      return;
      
    var plExport = Ext.getCmp('plexport');
    plExport.form.submit({
      url: 'DatGetExcelByTask.do',
      params: { 'task.taskid': me.cancelRecord.tranid },
      target: 'export'
    });
  },
  
  OnDownPdf: function () {
    var me = this, mep = me.tranPrefix;

    if (!me.cancelRecord)
      return;
      
    var plExport = Ext.getCmp('plexport');
    plExport.form.submit({
      url: 'DatGetPdfByTask.do',
      params: { 'task.taskid': me.cancelRecord.tranid },
      target: 'export'
    });
  },
  
  OnPrintReport: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.cancelRecord)
      return;
      
    var html = lims.GetReportHtmlByTask(me.cancelRecord.tranid, false);
    
    if (!Ext.isEmpty(html)) {
      lims.PrintRecordHtml(html, false);
    }
    else
      tools.alert('无试验报告打印内容');
  },
  
  OnDownExcelReport: function () {
    var me = this, mep = me.tranPrefix;

    if (!me.cancelRecord)
      return;
      
    var plExport = Ext.getCmp('plexport');
    plExport.form.submit({
      url: 'DatGetReportExcelByTask.do',
      params: { 'task.taskid': me.cancelRecord.tranid },
      target: 'export'
    });
  },
  
  OnDownPdfReport: function () {
    var me = this, mep = me.tranPrefix;

    if (!me.cancelRecord)
      return;
      
    var plExport = Ext.getCmp('plexport');
    plExport.form.submit({
      url: 'DatGetReportPdfByTask.do',
      params: { 'task.taskid': me.cancelRecord.tranid },
      target: 'export'
    });
  }
});