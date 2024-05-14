    Ext.define('alms.maninnergroup',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'内审小组',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_innergroup',
          storeUrl:'InnerSearchInnerGroup.do',
          saveUrl:'InnerSaveInnerGroup.do',
          expUrl:'InnerSearchInnerGroup.do',
          idPrevNext:'groupid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '小组编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchgroupid', id: mep + 'searchgroupid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '小组名称', labelWidth: 60, width: 250, maxLength: 40, name: 'searchgroupname', id: mep + 'searchgroupname', allowBlank: true }
     ];
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
   OnBeforeCreateEdit:function(){
     var me = this;
     var mep = this.tranPrefix;
     var nowdate = new Date();
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('小组编号', 'innergroup.groupid', mep + 'groupid', 20, '96%', false, 1)
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('小组名称', 'innergroup.groupname', mep + 'groupname', 20, '96%', false, 2),
              {xtype:'hiddenfield',name:'innergroup.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormCheckCombo('小组成员', 'users', mep + 'users', tools.ComboStore('User'), '98%', false, 4),
        tools.FormTextArea('备注', 'innergroup.groupremark', mep + 'groupremark', 200, '98%', true, 3,4)
     ];
     me.disNews = ['groupid'];
     me.disEdits = ['groupid'];
     me.enNews = ['groupname','groupremark','users'],
     me.enEdits = ['groupname','groupremark','users'];
   },
   
   //修改编辑面的按钮菜单
   OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
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
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'innergroup.groupid':tools.GetValueEncode(mep+'searchgroupid'),
           'innergroup.groupname':tools.GetValueEncode(mep+'searchgroupname')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'groupid', item.groupid);
    tools.SetValue(mep + 'groupname', item.groupname);
    tools.SetValue(mep + 'groupremark', item.groupremark);
    var users = tools.JsonGet(tools.GetUrl('InnerGetListInnerGroupMember.do?groupmember.groupid=')+item.groupid).data;
    var user = [];
    for(var i = 0; i < users.length; i++){
      user.push(users[i].memberuser);
    }
    tools.SetValue(mep + 'users', user);
    return true;
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     var users = Ext.getCmp(mep + 'users').getValue();
     
     me.saveParams = { details: users };
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
//    var innergrouptype = Ext.getCmp(mep+'innergrouptype').getValue();
//    
//    if (tools.InvalidForm(me.plEdit.getForm())) {
//      if(innergrouptype == gpersist.SELECT_MUST_VALUE ){
//        tools.alert('请选择小组成员！');
//        return;
//      }
//    }
    return true;
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'groupid').reset();
    Ext.getCmp(mep + 'groupid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.groupid) {
          tools.SetValue(mep + 'groupid', action.result.data.groupid)
        }
      }
    }
    me.OnDetailRefresh();
  }
});