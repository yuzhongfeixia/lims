Ext.define('alms.reviewnotice', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '管理评审实施通知',
      winWidth: 750,
      winHeight: 500,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewnotice',
      storeUrl: 'RewSearchReviewNotice.do',
      saveUrl: 'RewSaveReviewNotice.do',
      hasGridSelect: true,
      expUrl: 'RewSearchReviewNotice.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var planid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '评审计划', name: 'rn.planid', id: mep + 'planid', winTitle: '选择评审计划',
      maxLength: 20, maxLengthText: '评审计划编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '评审计划编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewplan',
      storeUrl: 'RewSearchReviewPlanForNotice.do',
      editable:false,
//      searchTools:partsitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    planid.on('griditemclick', me.OnPlanSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'rn.tranid', mep + 'tranid', 30, '96%', true, 1),
          tools.FormDate('评审时间', 'rn.reivewdate', mep + 'reivewdate', 'Y-m-d', '96%', false, 3),
          tools.FormText('称呼', 'rn.noticeobj', mep + 'noticeobj', 40, '96%', false, 5),
          tools.FormDate('通知时间', 'rn.noticedate', mep + 'noticedate', 'Y-m-d', '96%', false,7)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          planid,
          tools.FormText('评审地点', 'rn.reviewplace', mep + 'reviewplace', 20, '100%', false, 4),
          tools.FormText('年度', 'rn.reviewyear', mep + 'reviewyear', 40, '100%', false, 6, 'isyear'),
          {xtype:'hiddenfield',name:'rn.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: "htmleditor", name: "rn.noticetext", id: mep + 'noticetext', tabIndex: 9, enableFont: true,
          fieldLabel: "通知正文", height: 300, width:'100%', labelWidth: 80, margin: '0 0 10 0', fontFamilies: ["宋体", "隶书", "黑体"]
        }
     ]}
    ];
    me.disNews = ['tranid','reviewplace', 'reivewdate'];
    me.disEdits = ['tranid','reviewplace', 'reivewdate'];
    me.enNews = [ 'noticeobj',  'reviewyear', 'noticetext', 'noticedate','planid'];
    me.enEdits = ['noticeobj',  'reviewyear', 'noticetext', 'noticedate','planid'];
    
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
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
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
          'rn.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.JsonGet(tools.GetUrl('RewGetReviewNotice.do?rn.tranid=') + item.tranid);  
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'planid', record.planid);
      tools.SetValue(mep + 'noticeobj', item.noticeobj);
      tools.SetValue(mep + 'reivewdate', item.reivewdate);
      tools.SetValue(mep + 'reviewplace', item.reviewplace);
      tools.SetValue(mep + 'reviewyear', item.reviewyear);
      tools.SetValue(mep + 'noticetext', Ext.util.Format.htmlDecode(item.noticetext));
      tools.SetValue(mep + 'noticedate', item.noticedate);
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
  
  OnPlanSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tranid) {
      tools.SetValue(mep+'planid',item.tranid);
      tools.SetValue(mep+'reivewdate',item.reviewdate);
      tools.SetValue(mep+'reviewplace',item.reviewaddr);
    }
  }
  
   
});