Ext.define('alms.reviewrecord', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '评审记录',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewrecord',
      storeUrl: 'RewSearchReviewRecord.do',
      saveUrl: 'RewSaveReviewRecord.do',
      hasGridSelect: true,
      expUrl: 'RewSearchReviewRecord.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var meetid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '评审会议', name: 'rerd.meetid', id: mep + 'meetid', winTitle: '选择评审会议',
      maxLength: 20, maxLengthText: '评审会议编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '评审会议编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewmeetsign',
      storeUrl: 'RewSearchMeetSignForRecord.do',
      editable:false,
//      searchTools:partsitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    meetid.on('griditemclick', me.OnMeetSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('记录编号', 'rerd.tranid', mep + 'tranid', 30, '96%', false, 1),
          tools.FormText('评审地点', 'rerd.reviewaddr', mep + 'reviewaddr', 30, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          meetid,
          tools.FormDate('评审日期', 'rerd.reviewdate', mep + 'reviewdate', 'Y-m-d', '100%', false, 2),
          {xtype:'hiddenfield',name:'rerd.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('评审内容', 'rerd.reviewcontent', mep + 'reviewcontent', 400, '100%', true, 11, 18)
    ];
    me.disNews = ['tranid','reviewaddr','reviewdate'];
    me.disEdits = ['tranid','reviewaddr','reviewdate','meetid'];
    me.enNews = ['reviewcontent','meetid'];
    me.enEdits = ['reviewcontent'];
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
      ' ', { xtype: 'textfield', fieldLabel: '  记录编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
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
          'rerd.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;

    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'meetid', item.meetid);
      tools.SetValue(mep + 'reviewdate', item.reviewdate);
      tools.SetValue(mep + 'reviewaddr', item.reviewaddr);
      tools.SetValue(mep + 'reviewcontent', item.reviewcontent);
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
  
  OnMeetSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
//    var record = tools.JsonGet(tools.GetUrl('GetBuyApply.do?bd.devid=') + item.devid);  
    if (item && item.meetid) {
      tools.SetValue(mep+'meetid',item.meetid);
      tools.SetValue(mep+'reviewdate',item.meetdate);
      tools.SetValue(mep+'reviewaddr',item.meetplace);
    }
  },
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : '提交', iconCls : 'icon-deal', handler : me.OnSave, scope : me },  
     //'-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSave, scope: me },
     //'-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
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