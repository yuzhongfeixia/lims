Ext.define('alms.reviewplan', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '评审工作计划',
      winWidth: 750,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewplan',
      storeUrl: 'RewSearchReviewPlan.do',
      saveUrl: 'RewSaveReviewPlan.do',
      hasGridSelect: true,
      expUrl: 'RewSearchReviewPlan.do',
      hasPage: true,
      hasPrevNext: true,
      hasSubmit: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
//    var reviewuser =  Ext.create('Ext.ux.UserPicker', {
//      fieldLabel: '主持人', name: 'rp.reviewuser', id:mep + 'reviewuser', winTitle: '选择主持人',
//      maxLength: 14, maxLengthText: '编号不能超过6个字符！',selectOnFocus: false, labelWidth: 80,
//      allowBlank: false, anchor: '96%', tabIndex: 3, editable: false,
//      columnUrl: 'SysSqlColumn.do?sqlid=p_get_user',
//      storeUrl: 'UserSearchUser.do',
//      hasPage: true, pickerWidth: 600, pickerHeight: 450
//    });
//    reviewuser.on('griditemclick', me.OnUserSelect, me);
    
    var reviewuser = tools.UserPicker(mep + 'reviewuser','rp.reviewuser','主持人');
    
    reviewuser.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('计划编号', 'rp.tranid', mep + 'tranid', 30, '96%', false, 1),
          reviewuser,
          tools.FormText('参加人员', 'rp.joinuser', mep + 'joinuser', 400, '96%', false, 5),
          tools.FormCheckCombo('评审依据', 'rp.reviewtype', mep + 'reviewtype',tools.ComboStore('ReviewType'), '96%', false, 7)
         
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormDate('评审日期', 'rp.reviewdate', mep + 'reviewdate', 'Y-m-d ', '100%', false, 2),
          tools.FormText('主持人姓名', 'rp.reviewusername', mep + 'reviewusername', 40, '100%', false, 4),
          tools.FormText('评审地点', 'rp.reviewaddr', mep + 'reviewaddr', 40, '100%', false, 6),
          tools.FormCheckCombo('评审内容', 'rp.reviewcontent', mep + 'reviewcontent',tools.ComboStore('ReviewContent'), '100%', false, 8),
          {xtype:'hiddenfield',name:'rp.deal.action',id: mep + 'datadeal'},
          {xtype:'hiddenfield',name:'rp.reviewtypename',id: mep + 'reviewtypename'},
          {xtype:'hiddenfield',name:'rp.reviewcontentname',id: mep + 'reviewcontentname'}
        ]}
      ]},
      tools.FormTextArea('其他', 'rp.othercontent', mep + 'othercontent', 400, '100%', true, 9),
      tools.FormTextArea('评审目的', 'rp.reviewgoal', mep + 'reviewgoal', 100, '100%', true, 10, 4),
      tools.FormTextArea('收集信息要求', 'rp.inforequire', mep + 'inforequire', 300, '100%', true, 11, 12)
    ];
    me.disNews = ['tranid','reviewusername'];
    me.disEdits = ['tranid','reviewusername'];
    me.enNews = [ 'reviewdate', 'reviewaddr', 'reviewuser','reviewgoal', 'reviewtype', 
                  'joinuser', 'reviewcontent', 'othercontent', 'inforequire' ];
    me.enEdits = [ 'reviewdate', 'reviewaddr', 'reviewuser', 'reviewgoal', 'reviewtype', 
                   'joinuser', 'reviewcontent', 'othercontent', 'inforequire'];
    
    Ext.getCmp(mep + 'reviewtype').on('change', me.SetReviewTypeName, me);
    Ext.getCmp(mep + 'reviewcontent').on('change', me.SetReviewContentName, me);
  },
  
  SetReviewTypeName: function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'reviewtypename', Ext.getCmp(mep + 'reviewtype').getDisplayValue());
  },
  
  SetReviewContentName: function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'reviewcontentname', Ext.getCmp(mep + 'reviewcontent').getDisplayValue());
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'reviewtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'reviewcontent', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '计划编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
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
          'rp.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
  
  OnBeforeSave: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if(tools.GetValue(mep + 'reviewtype') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择评审依据！');
      return false;
    }
    
    if(tools.GetValue(mep + 'reviewcontent') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择评审内容！');
      return false;
    }
    return true;
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      height:'100%',
      autoScroll: true,
      region : 'north',
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
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'reviewdate', item.reviewdate);
      tools.SetValue(mep + 'reviewaddr', item.reviewaddr);
      tools.SetValue(mep + 'reviewuser', item.reviewuser);
      tools.SetValue(mep + 'reviewusername', item.reviewusername);
      tools.SetValue(mep + 'reviewtype', item.reviewtype.split(','));
      tools.SetValue(mep + 'reviewcontent', item.reviewcontent.split(','));
      tools.SetValue(mep + 'reviewgoal', item.reviewgoal);
      tools.SetValue(mep + 'joinuser', item.joinuser);
      tools.SetValue(mep + 'othercontent', item.othercontent);
      tools.SetValue(mep + 'inforequire', item.inforequire);
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
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+"reviewuser",item.userid);
     tools.SetValue(mep+"reviewusername",item.username);
   }
  },
  
  //提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);
    if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnEdit'], mep);
    } else{
      tools.BtnsEnable(['btnEdit'], mep);
    }
  },
  
  //双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnSubmit'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmit'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  },
  
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
     '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
     '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
   ];
   
   if (me.hasSubmit) {
     Ext.Array.insert(me.editToolItems, 2, [
       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
     ]);
   }
   
   if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
  },
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
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
          if(me.plGrid.store.getAt(i - 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
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
    var mep = me.tranPrefix;
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
          if(me.plGrid.store.getAt(i + 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  }
   
});