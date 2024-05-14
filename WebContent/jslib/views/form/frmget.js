Ext.define('alms.frmget', {
  extend: 'gpersist.base.busform',
  winpreview: null,
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      selectParams: ['BasSample', 'GetType', 'FormDirect', 'ValueSource', 'ValueType', 'AlignType'],
      editInfo: '原始记录',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_frmrecord',
      storeUrl: 'FormSearchFrmGet.do',
      saveUrl: 'FormSaveFrmGet.do',
      expUrl: 'FormSearchFrmGet.do',
      idPrevNext: 'formid',
      hasEdit: true,
      hasDetail: true,
      hasSubmit: false,
      detailTitle: '原始记录明细',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_frmrecorddetail',
      urlDetail: 'FormGetFrmGetDetailByForm.do',
      detailTabs: 1,
      hasDateSearch: false,
      hasDetailEdit: true,
      winWidth: 850,
      winHeight: 300
    });    
    me.callParent(arguments);
  },
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.winpreview = null;
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '表单名称', labelWidth: 60, width: 200, name: 'searchformname', id: mep + 'searchformname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },      
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
        
    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'fg.formname': tools.GetValueEncode(mep + 'searchformname')
    };
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var nowdate = new Date();
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('表单编号'), 'fg.formid', mep + 'formid', 12, '96%', false, 1),
          tools.FormCombo(tools.MustTitle('样品'), 'fg.sampleid', mep + 'sampleid', tools.ComboStore('BasSample', gpersist.SELECT_MUST_VALUE), '96%', true, 5),
          tools.FormText(tools.MustTitle('长度隔断'), 'fg.formlength', mep + 'formlength', 8, '96%', false, 7, 'isnumber'),
          tools.FormCombo(tools.MustTitle('表单方向'), 'fg.formdirect', mep + 'formdirect', tools.ComboStore('FormDirect', gpersist.SELECT_MUST_VALUE), '96%', false, 9),
          tools.FormText('方法值', 'fg.methodcode', mep + 'methodcode', 20, '96%', true, 11)         
        ]},
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('表单名称'), 'fg.formname', mep + 'formname', 50, '100%', false, 2),
          tools.FormCombo(tools.MustTitle('文档类型'), 'fg.gettype', mep + 'gettype', tools.ComboStore('GetType', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
          tools.FormText(tools.MustTitle('宽度隔断'), 'fg.formwidth', mep + 'formwidth', 8, '100%', false, 8, 'isnumber'),
          tools.FormText(tools.MustTitle('分页组数'), 'fg.pagegroup', mep + 'pagegroup', 8, '100%', false, 10, 'isnumber'),
          tools.FormText(tools.MustTitle('分页试件数'), 'fg.pagespec', mep + 'pagespec', 8, '100%', false, 12, 'isnumber'),
          {xtype: 'hiddenfield', name: 'fg.deal.action', id: mep + 'datadeal'},
          {xtype: 'hiddenfield', name: 'fg.createusername', id: mep + 'createusername'},
          {xtype: 'hiddenfield', name: 'fg.modiyserial', id: mep + 'modiyserial'}
        ]}
       
     ]}   
   ];
     
    me.disNews = [];
    me.disEdits = ['formid'];
    me.enNews = ['formid', 'formname', 'sampleid', 'gettype', 'formlength', 'formwidth', 'formdirect', 'pagegroup', 'pagespec', 'methodcode'];
    me.enEdits = ['formname', 'sampleid', 'gettype', 'formlength', 'formwidth', 'formdirect', 'pagegroup', 'pagespec', 'methodcode'];
  },
  
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editDetailControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('序号'), 'frdetail.cellserial', mep + 'cellserial', 8, '96%', false, 1, 'isnumber'),
          tools.FormText(tools.MustTitle('开始行数'), 'frdetail.beginrow', mep + 'beginrow', 8, '96%', false, 4, 'isnumber'),
          tools.FormText(tools.MustTitle('开始列数'), 'frdetail.begincolumn', mep + 'begincolumn', 8, '96%', false, 7, 'isnumber'),
          tools.FormText('所属类编号', 'frdetail.classsource', mep + 'classsource', 20, '96%', true, 10),
          tools.FormText('显示数据', 'frdetail.celltext', mep + 'celltext', 200, '96%', true, 13),
          tools.FormCombo(tools.MustTitle('对齐方式'), 'frdetail.aligntype', mep + 'aligntype', tools.ComboStore('AlignType', gpersist.SELECT_MUST_VALUE), '96%', false, 16),
          { xtype: 'fieldcontainer', fieldLabel: '', layout: 'hbox', defaults: { labelStyle: 'font-weight:bold;', margins: '0 15 0 0' }, items: [
            tools.FormCheck(tools.MustTitle('线框'), 'frdetail.isborder', mep + 'isborder', false, 19),
            tools.FormCheck(tools.MustTitle('下划线'), 'frdetail.isline', mep + 'isline', false, 20),
            tools.FormCheck(tools.MustTitle('加粗'), 'frdetail.isbold', mep + 'isbold', false, 21),
            tools.FormCheck(tools.MustTitle('多点检测'), 'frdetail.ismutil', mep + 'ismutil', false, 22)]}
        ]},
        {xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('项目名称'), 'frdetail.cellname', mep + 'cellname', 400, '96%', false, 2),
          tools.FormText(tools.MustTitle('结束行数'), 'frdetail.endrow', mep + 'endrow', 8, '96%', false, 5, 'isnumber'),
          tools.FormText(tools.MustTitle('结束列数'), 'frdetail.endcolumn', mep + 'endcolumn', 8, '96%', false, 8, 'isnumber'),
          tools.FormText(tools.MustTitle('组号'), 'frdetail.groupserial', mep + 'groupserial', 8, '96%', false, 11, 'isnumber'),
          tools.FormText('显示格式', 'frdetail.cellformat', mep + 'cellformat', 20, '96%', true, 14),
          tools.FormText('前缀', 'frdetail.prefixtext', mep + 'prefixtext', 10, '96%', true, 17)
        ]},
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('字段编号', 'frdetail.fieldcode', mep + 'fieldcode', 20, '100%', true, 3),
          tools.FormCombo(tools.MustTitle('数据来源'), 'frdetail.valuesource', mep + 'valuesource', tools.ComboStore('ValueSource', gpersist.SELECT_MUST_VALUE), '100%', false, 7),
          tools.FormCombo(tools.MustTitle('数据类型'), 'frdetail.valuetype', mep + 'valuetype', tools.ComboStore('ValueType', gpersist.SELECT_MUST_VALUE), '100%', false, 9),
          tools.FormText(tools.MustTitle('试件号'), 'frdetail.specserial', mep + 'specserial', 8, '100%', false, 12, 'isnumber'),
          tools.FormText(tools.MustTitle('字体大小'), 'frdetail.fontsize', mep + 'fontsize', 8, '100%', false, 15, 'isnumber'), 
          tools.FormText('后缀', 'frdetail.postfixtext', mep + 'postfixtext', 10, '100%', true, 18)
        ]}  
      ]}      
   ];

    me.disDetailControls = [];
    me.enDetailControls = ['cellserial', 'beginrow', 'endrow', 'begincolumn', 'endcolumn', 'cellname', 'valuesource', 
      'valuetype', 'classsource', 'fieldcode', 'groupserial', 'specserial', 'celltext', 'cellformat', 'isborder', 
      'isline', 'isbold', 'fontsize', 'aligntype', 'prefixtext', 'postfixtext', 'ismutil'];
  },
  
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep + 'cellserial', item.cellserial);
    tools.SetValue(mep + 'beginrow', item.beginrow);
    tools.SetValue(mep + 'endrow', item.endrow);
    tools.SetValue(mep + 'begincolumn', item.begincolumn);
    tools.SetValue(mep + 'endcolumn', item.endcolumn);
    tools.SetValue(mep + 'cellname', item.cellname);
    tools.SetValue(mep + 'valuesource', item.valuesource);
    tools.SetValue(mep + 'valuetype', item.valuetype);
    tools.SetValue(mep + 'classsource', item.classsource);
    tools.SetValue(mep + 'fieldcode', item.fieldcode);
    tools.SetValue(mep + 'groupserial', item.groupserial);
    tools.SetValue(mep + 'specserial', item.specserial);
    tools.SetValue(mep + 'celltext', item.celltext);
    tools.SetValue(mep + 'cellformat', item.cellformat);
    tools.SetValue(mep + 'isborder', item.isborder);
    tools.SetValue(mep + 'isline', item.isline);
    tools.SetValue(mep + 'isbold', item.isbold);
    tools.SetValue(mep + 'fontsize', item.fontsize);
    tools.SetValue(mep + 'aligntype', item.aligntype);
    tools.SetValue(mep + 'prefixtext', item.prefixtext);
    tools.SetValue(mep + 'postfixtext', item.postfixtext);
    tools.SetValue(mep + 'ismutil', item.ismutil);
  },
  
  OnBeforeListSave: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    
    record.data.cellserial = tools.GetValue(mep + 'cellserial');
    record.data.beginrow = tools.GetValue(mep + 'beginrow');
    record.data.endrow = tools.GetValue(mep + 'endrow');
    record.data.begincolumn = tools.GetValue(mep + 'begincolumn');
    record.data.endcolumn = tools.GetValue(mep + 'endcolumn');
    record.data.cellname = tools.GetValue(mep + 'cellname');
    record.data.valuesource = tools.GetValue(mep + 'valuesource');
    record.data.valuesourcename = Ext.getCmp(mep + 'valuesource').getDisplayValue();
    record.data.valuetype = tools.GetValue(mep + 'valuetype');
    record.data.valuetypename = Ext.getCmp(mep + 'valuetype').getDisplayValue();
    record.data.classsource = tools.GetValue(mep + 'classsource');
    record.data.fieldcode = tools.GetValue(mep + 'fieldcode');
    record.data.groupserial = tools.GetValue(mep + 'groupserial');
    record.data.specserial = tools.GetValue(mep + 'specserial');
    record.data.celltext = tools.GetValue(mep + 'celltext');
    record.data.cellformat = tools.GetValue(mep + 'cellformat');
    record.data.isborder = tools.GetValue(mep + 'isborder');
    record.data.isline = tools.GetValue(mep + 'isline');
    record.data.isbold = tools.GetValue(mep + 'isbold');
    record.data.fontsize = tools.GetValue(mep + 'fontsize');
    record.data.aligntype = tools.GetValue(mep + 'aligntype');
    record.data.aligntypename = Ext.getCmp(mep + 'aligntype').getDisplayValue();
    record.data.prefixtext = tools.GetValue(mep + 'prefixtext');
    record.data.postfixtext = tools.GetValue(mep + 'postfixtext');
    record.data.ismutil = tools.GetValue(mep + 'ismutil');
  },
  
  OnDetailRefresh: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'fg.formid': tools.GetValue(mep + 'formid')
        });
      });
    }
    
    me.callParent();
  },
  
  OnBeforeCreateDealEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editDealControls = [
      { xtype: 'filefield', fieldLabel: '选择文件', id: mep + 'upload', buttonOnly: false, name: 'uf.upload', 
          readOnly: false, buttonText: '选择', anchor: '100%' }       
    ];
  },
  
  OnCreateEditDealWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editdealwin')) {
      Ext.getCmp(mep + 'editdealwin').destroy();
    }
    
    me.editDealToolItems = [
      ' ', { id: mep + 'btnDealSave', text: '上传并保存', iconCls: 'icon-save', handler: me.OnDealSave, scope: me },  
      '-', ' ', { id: mep + 'btnDealClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDealEdit.hide(); } }
    ];
    
    me.OnBeforeCreateDealEdit();
    
    me.plDealEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldealedit',
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
      tbar : me.editDealToolItems,
      items: me.editDealControls    
    });
    
    me.winDealEdit = Ext.create('Ext.Window', {
      id: mep + 'editdealwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: 600,
      height: 100,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDealEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeDealHide, scope:me } }
    });
    
  },
  
  OnBeforeDealHide: function () {
    var me = this;
    
    if (me.hasExitPrompt) {
      if (me.dataDeal != gpersist.DATA_DEAL_SELECT)  {
           me.dataDeal = gpersist.DATA_DEAL_SELECT; 
           me.winDealEdit.hide();
      }   
    }
    me.dataDeal = gpersist.DATA_DEAL_SELECT; 
    
    return true;
  },
  
  OnDeal: function () {
    var me = this, mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editdealwin')) {
      Ext.getCmp(mep + 'editdealwin').destroy();
    }
    
    //if (!me.winDealEdit)
    me.OnCreateEditDealWin();
    
    if(me.winDealEdit){      
      me.winDealEdit.show();
    }
  },
  
  OnDealSave: function () {
    var me = this;
    var mep = me.tranPrefix;
   
    var file = Ext.getCmp(mep + 'upload');
    var fileInput = file.fileInputEl.dom;
    
    if (tools.InvalidForm(me.plDealEdit.getForm())) {        
      me.plDealEdit.form.submit({
        url: tools.GetUrl('FormSaveFrmRecordDetailExcel.do'),
        waitTitle: '提示',
        waitMsg: '正在上传文件......',
        method: 'POST',
        success: function (form, action) {
          file.fileInputEl.dom = fileInput;
          tools.ErrorAlert(action);
          if (action.result && action.result.success) {
            me.OnDetailRefresh();
          }            
        },
        failure: function (form, action) {
          file.fileInputEl.dom = fileInput;
          tools.ErrorAlert(action);
        }
      });       
    }
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
//      '-', ' ', { id: mep + 'btnDeal', text: '明细模板导入', iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnPreview', text: '模板预览', iconCls: 'icon-list', handler: me.OnPreview, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    tools.SetValue(mep + 'sampleid', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'formdirect', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'gettype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'pagegroup', 0);
    tools.SetValue(mep + 'pagespec', 0);
    tools.SetValue(mep + 'formwidth', 0);
    tools.SetValue(mep + 'formlength', 0);
  },
  
  OnSetData: function (id, record) {
    var me = this;
    var mep = me.tranPrefix;
    
    var item = tools.JsonGet(tools.GetUrl('FormGetFrmGet.do?fg.formid=') + id);
    if (item && !Ext.isEmpty(item.formid)) {
      tools.SetValue(mep + 'formid', item.formid);
      tools.SetValue(mep + 'formname', item.formname);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'gettype', item.gettype);
      tools.SetValue(mep + 'formlength', item.formlength);
      tools.SetValue(mep + 'formwidth', item.formwidth);
      tools.SetValue(mep + 'formdirect', item.formdirect);
      tools.SetValue(mep + 'modiyserial', item.modiyserial);
      tools.SetValue(mep + 'pagegroup', item.pagegroup);
      tools.SetValue(mep + 'pagespec', item.pagespec);
      tools.SetValue(mep + 'methodcode', item.methodcode);
      
      me.OnDetailRefresh();
      return true;
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'formid').reset();
    Ext.getCmp(mep + 'formid').focus(true, 500);
  },
  
  OnPreview: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!me.winpreview) {
      me.winpreview = tools.GetPopupWindow('alms', 'previewget', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'pr', dataDeal: gpersist.DATA_DEAL_NEW});
      
      me.winpreview.OnFormLoad();
    }
    else
      me.winpreview.OnFormShow();
      
    me.winpreview.OnSetData(tools.GetValue(mep + 'formid'));
  }
});