Ext.define('alms.maninneryear',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'内部审核年度计划',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_inneryear',
          storeUrl:'InnerSearchInnerYear.do',
          saveUrl:'InnerSaveInnerYear.do',
          expUrl:'InnerSearchInnerYear.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '审核日程',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_inneryeardetail',
          urlDetail: 'InnerGetListInnerYearDetail.do',
          detailTabs: 1,
          hasDateSearch: true,
          hasDetailEdit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
      var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '计划编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
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
     
     var groupitems = [
     ' ', { xtype: 'textfield', fieldLabel: '小组名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchgroupname', id: mep + 'searchgroupname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnGroupSearch, scope: me }
    ];
     
     var groupname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '小组', name: 'detail.groupname', id: mep + 'groupname', winTitle: '选择小组',
      maxLength: 20, maxLengthText: '小组名称不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '小组为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_innergroup',
      storeUrl: 'InnerSearchInnerGroup.do',
      editable:false,
      searchTools:groupitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     groupname.on('griditemclick', me.OnGroupSelect, me);
     
     var auditleadname = tools.UserPicker(mep + 'auditleadname','inneryear.auditleadname','内审组长');
     
     auditleadname.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('计划编号', 'inneryear.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormText('业务员', 'inneryear.tranusername', mep + 'tranusername', 20, '96%', false, 1)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              auditleadname,
              groupname
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormDate('记录时间', 'inneryear.trandate', mep + 'trandate', 'Y-m-d H:i', '100%', false, 2,nowdate),
              {xtype:'hiddenfield',name:'inneryear.auditlead',id: mep + 'auditlead'},
              {xtype:'hiddenfield',name:'inneryear.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'inneryear.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'inneryear.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'inneryear.groupid',id: mep + 'groupid'},
              {xtype:'hiddenfield',name:'inneryear.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
       tools.FormTextArea('审核目的', 'inneryear.auditgoal', mep + 'auditgoal', 50, '100%', false,9,3),
       tools.FormTextArea('审核范围', 'inneryear.auditscope', mep + 'auditscope', 100, '100%', false,10,3),
       { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: "htmleditor", name: "inneryear.auditby", id: mep + 'auditby', tabIndex: 11, enableFont: false,
          fieldLabel: "审核依据", height: 200, width:'100%', labelWidth: 80, margin: '0 0 10 0', fontFamilies: ["宋体", "隶书", "黑体"]}
      ]}
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.enNews = [ 'auditgoal', 'auditscope', 'auditby', 'auditlead', 'auditleadname','groupname', 'flowaction', 'flowstatus', 'allowuser', 'allowusername', 'allowdate', 'allowdesc' ];
     me.enEdits = [ 'auditgoal', 'auditscope', 'auditby', 'auditlead', 'auditleadname','groupname', 'flowaction', 'flowstatus', 'allowuser', 'allowusername','allowdate', 'allowdesc'];
   },
   
   OnGroupSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.groupid){
        tools.SetValue(mep+"groupid",item.groupid);
        tools.SetValue(mep+"groupname",item.groupname);
     }
   },
   
   OnGroupSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnGroupBeforeLoad();
      var groupname = Ext.getCmp(mep + 'groupname');
      groupname.store.loadPage(1);
   },
   
   OnGroupBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var groupname = Ext.getCmp(mep + 'groupname');
      if(groupname.store){
        groupname.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'innergroup.groupname':tools.GetValueEncode(mep + 'searchgroupname')
            });
        });
      }
   },
   
   OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height : 400,
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
           'inneryear.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
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
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"auditlead",item.userid);
      tools.SetValue(mep+"auditleadname",item.username);
    }
   },
   
   OnLoadData:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet(tools.GetUrl('InnerGetInnerYear.do?inneryear.tranid=') + record.tranid);
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'auditgoal', item.auditgoal);
      tools.SetValue(mep + 'auditscope', item.auditscope);
      tools.SetValue(mep + 'auditby', Ext.util.Format.htmlDecode(item.auditby));
      tools.SetValue(mep + 'auditlead', item.auditlead);
      tools.SetValue(mep + 'auditleadname', item.auditleadname);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
      tools.SetValue(mep + 'allowuser', item.allowuser);
      tools.SetValue(mep + 'allowusername', item.allowusername);
      tools.SetValue(mep + 'allowdate', item.allowdate);
      tools.SetValue(mep + 'allowdesc', item.allowdesc);
      tools.SetValue(mep + 'groupid', item.groupid);
      tools.SetValue(mep + 'groupname', item.groupname);
      me.OnDetailRefresh();
    }
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('组别', 'detail.groupinner', mep + 'groupinner', 10, '96%', false, 1)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormDate('审核时间', 'detail.auditdate', mep + 'auditdate', 'Y-m-d', '96%', false, 20,nowdate)
        ]}
       ]},  
       tools.FormTextArea('审核内容', 'detail.auditcontent', mep + 'auditcontent', 200, '98%', true, 21,4),
       tools.FormTextArea('备注', 'detail.auditremark', mep + 'auditremark', 200, '98%', true,22,4)
    ];

     me.disDetailControls = [];
     me.enDetailControls = ['groupinner', 'auditdate', 'auditcontent', 'auditremark'];
   },
   
   OnLoadDetailData: function (item) {
     var me = this;
     var mep = me.tranPrefix;
     tools.SetValue(mep + 'tranid', item.tranid);
     tools.SetValue(mep + 'groupinner', item.groupinner);
     tools.SetValue(mep + 'auditdate', item.auditdate);
     tools.SetValue(mep + 'auditcontent', item.auditcontent);
     tools.SetValue(mep + 'auditremark', item.auditremark);
   },
   
   OnListNew: function() {
    var me = this;
    
    var date = new Date();
    
    me.OnCreateDetailWin();
    
    if(me.winDetail){      
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnInitDetailData();   
      me.OnAuthDetailEditForm(false);
    }
  },
   
   OnBeforeListSave: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     record.data.tranid =  Ext.getCmp(mep + 'tranid').getValue();
     record.data.groupinner = Ext.getCmp(mep + 'groupinner').getValue();
     record.data.auditdate = Ext.getCmp(mep + 'auditdate').getValue();
     record.data.auditcontent = Ext.getCmp(mep + 'auditcontent').getValue();
     record.data.auditremark = Ext.getCmp(mep + 'auditremark').getValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('InnerGetListInnerYearDetail.do?innerdetail.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     var auditby = Ext.util.Format.htmlEncode(Ext.getCmp(mep + 'auditby').getValue()); 
     var details = [];
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     me.saveParams = { details: Ext.encode(details),auditby:auditby };
   },
   
   OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
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
           tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
           tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
         }
       }
     }
     me.OnDetailRefresh();
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