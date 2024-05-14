Ext.define('alms.reviewimprove', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '改进措施',
      winWidth: 750,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewimprove',
      storeUrl: 'RewSearchReviewImprove.do',
      saveUrl: 'RewSaveReviewImprove.do',
      hasGridSelect: true,
      expUrl: 'RewSearchReviewImprove.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var reportid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '评审报告', name: 'ri.reportid', id: mep + 'reportid', winTitle: '选择评审报告',
      maxLength: 20, maxLengthText: '评审报告编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '评审报告编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewreport',
      storeUrl: 'RewSearchReviewReportForImprove.do',
      editable:false,
//      searchTools:partsitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    reportid.on('griditemclick', me.OnReportSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'ri.tranid', mep + 'tranid', 30, '96%', false, 1),
          tools.FormCombo('责任部门', 'ri.respdept', mep + 'respdept', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 3)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          reportid,
          {xtype:'hiddenfield',name:'ri.respdeptname',id: mep + 'respdeptname'},
          {xtype:'hiddenfield',name:'ri.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('质量改进内容', 'ri.improvecontent', mep + 'improvecontent', 100, '100%', true, 9,12),
      tools.FormTextArea('期限要求', 'ri.timerequire', mep + 'timerequire', 100, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = [ 'improvecontent', 'timerequire','respdept','respdeptname','reportid'];
    me.enEdits = [ 'improvecontent', 'timerequire','respdept','respdeptname','reportid'];
    
    Ext.getCmp(mep + 'respdept').on('select',me.OnDeptSelect,me);
  },
  
  OnDeptSelect:function(combo, records, eOpts){
    var me = this;
    var mep = me.tranPrefix;
    
    if(records && records[0].data.id){
        tools.SetValue(mep+'respdept',records[0].data.id);
        tools.SetValue(mep+'respdeptname',records[0].data.name);
     }
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'respdept',gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
     // ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'ri.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.JsonGet(tools.GetUrl('RewGetReviewImprove.do?ri.tranid=') + item.tranid);
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'reportid', record.reportid);
      tools.SetValue(mep + 'improvecontent', item.improvecontent);
      tools.SetValue(mep + 'timerequire', item.timerequire);
      tools.SetValue(mep + 'respdept', item.respdept);
      tools.SetValue(mep + 'respdeptname', item.respdeptname);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var respdept = Ext.getCmp(mep+'respdept').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(respdept == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择责任部门！');
        return;
      }
    }
    
    return true;
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  },
  
  OnReportSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tranid) {
      tools.SetValue(mep+'reportid',item.tranid);
    }
  },
//修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : '提交', iconCls : 'icon-deal', handler : me.OnSave, scope : me },  
     //'-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSave, scope: me },
   //  '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler: function () { me.winEdit.hide(); }, scope : me }
   ];
   
//   if (me.hasSubmit) {
//     Ext.Array.insert(me.editToolItems, 2, [
//       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
//     ]);
//   }
   
   if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
  }
  
   
});