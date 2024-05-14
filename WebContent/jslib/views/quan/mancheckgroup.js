    Ext.define('alms.mancheckgroup',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'监督小组',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_checkgroup',
          storeUrl:'QuanSearchCheckGroup.do',
          saveUrl:'QuanSaveCheckGroup.do',
          expUrl:'QuanSearchCheckGroup.do',
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
     
     var leadername = tools.UserPicker(mep + 'leadername','cg.leadername','组长名称');
     
     leadername.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('小组编号', 'cg.groupid', mep + 'groupid', 10, '96%', false, 1),
              leadername
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('小组名称', 'cg.groupname', mep + 'groupname', 20, '96%', false, 2),
              tools.FormCheck('是否使用', 'cg.isuse', mep + 'isuse', true, 4),
              {xtype:'hiddenfield',name:'cg.groupleader',id: mep + 'groupleader'},
              {xtype:'hiddenfield',name:'cg.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormCheckCombo('小组成员', 'users', mep + 'users', tools.ComboStore('User'), '98%', false, 4),
        tools.FormTextArea('备注', 'cg.remark', mep + 'remark', 50, '98%', true, 3,4)
     ];
     me.disNews = ['groupid'];
     me.disEdits = ['groupid'];
     me.enNews = ['groupname','remark','users','isuse','leadername'],
     me.enEdits = ['groupname','remark','users','isuse','leadername'];
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"groupleader",item.userid);
      tools.SetValue(mep+"leadername",item.username);
    }
   },
   
   //修改编辑面的按钮菜单
   OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
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
           'cg.groupid':tools.GetValueEncode(mep+'searchgroupid'),
           'cg.groupname':tools.GetValueEncode(mep+'searchgroupname')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'groupid', item.groupid);
    tools.SetValue(mep + 'groupname', item.groupname);
    tools.SetValue(mep + 'groupleader', item.groupleader);
    tools.SetValue(mep + 'leadername', item.leadername);
    tools.SetValue(mep + 'isuse', item.isuse);
    tools.SetValue(mep + 'remark', item.remark);

    var users = tools.JsonGet(tools.GetUrl('QuanGetListCheckGroupMember.do?cgm.groupid=')+item.groupid).data;
    var user = [];
    for(var i = 0; i < users.length; i++){
      user.push(users[i].userid);
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
     var users = []; 
     var partusers = Ext.getCmp(mep + 'users').getValue().split(',');
     Ext.each(partusers, function (item, index) { 
       if(!Ext.isEmpty(item)){
         users.push({ userid: item});
       }
     });
     me.saveParams = { details: Ext.encode(users) };
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
//    var cgtype = Ext.getCmp(mep+'cgtype').getValue();
//    
//    if (tools.InvalidForm(me.plEdit.getForm())) {
//      if(cgtype == gpersist.SELECT_MUST_VALUE ){
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
  }
});