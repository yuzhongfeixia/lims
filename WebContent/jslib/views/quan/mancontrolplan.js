Ext.define('alms.mancontrolplan',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'能力验证(质量控制)计划',
          winWidth:750,
          winHeight:300,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quancontrolplan',
          storeUrl:'QuanSearchQuanControlPlan.do',
          saveUrl:'QuanSaveQuanControlPlan.do',
          expUrl:'QuanSearchQuanControlPlan.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '计划详细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_quancontrolplandetail',
          urlDetail: 'QuanGetListQuanControlPlanDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
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
     var editusername = tools.UserPicker(mep + 'editusername','qcp.editusername','编制人');
     
     editusername.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'qcp.tranid', mep + 'tranid', 20, '96%', false, 1),
              editusername
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('年度', 'qcp.contyear', mep + 'contyear', 4, '97%', false, 2,'isnumber')
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormDate('编制日期', 'qcp.editdate', mep + 'editdate', 'Y-m-d', '100%', false,3,nowdate),
              {xtype:'hiddenfield',name:'qcp.edituser',id: mep + 'edituser'},
              {xtype:'hiddenfield',name:'qcp.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'qcp.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'qcp.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'qcp.deal.action',id: mep + 'datadeal'}
           ]}
         ]}
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.enNews = [  'contyear', 'flowstatus', 'flowaction', 'edituser', 'editusername', 'editdate', 'audituser', 'auditusername', 'auditdate', 'approuser', 'approusername', 'approdate', 'remark'];
     me.enEdits = [ 'contyear', 'flowstatus', 'flowaction', 'edituser', 'editusername', 'editdate', 'audituser', 'auditusername', 'auditdate', 'approuser', 'approusername', 'approdate', 'remark'];
   },
   
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"edituser",item.userid);
      tools.SetValue(mep+"editusername",item.username);
    }
  },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'qcp.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
   },
   
//  //修改编辑面的按钮菜单
//  OnAfterCreateEditToolBar:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    me.editToolItems = [
//      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
//      '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
//      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
//      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
//      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
//    ];
//    
//    if (me.hasSubmit) {
//      Ext.Array.insert(me.editToolItems, 2, [
//        ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
//      ]);
//    }
//    if (me.hasPrevNext) {
//      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
//        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
//        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
//      ]);
//    }
//    
//   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'contyear', item.contyear);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'edituser', item.edituser);
    tools.SetValue(mep + 'editusername', item.editusername);
    tools.SetValue(mep + 'editdate', item.editdate);
    tools.SetValue(mep + 'audituser', item.audituser);
    tools.SetValue(mep + 'auditusername', item.auditusername);
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'approuser', item.approuser);
    tools.SetValue(mep + 'approusername', item.approusername);
    tools.SetValue(mep + 'approdate', item.approdate);
    tools.SetValue(mep + 'remark', item.remark);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     me.editDetailControls = [
       tools.FormTextArea('时间', 'detail.plantime', mep + 'plantime', 10, '100%', false, 21,2),
       tools.FormTextArea('内容', 'detail.plancontent', mep + 'plancontent', 100, '100%', false, 22,4),
       tools.FormTextArea('组织机构人员', 'detail.orgdept', mep + 'orgdept', 50, '100%', false, 23,4),
       tools.FormTextArea('备注', 'detail.planremark', mep + 'planremark', 200, '100%', true, 24,4)
    ];

     me.disDetailControls = [];
     me.enDetailControls = ['plantime', 'plancontent', 'orgdept', 'planremark'];
  },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'plantime', item.plantime);
    tools.SetValue(mep + 'plancontent', item.plancontent);
    tools.SetValue(mep + 'orgdept', item.orgdept);
    tools.SetValue(mep + 'planremark', item.planremark);
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
     record.data.plantime = Ext.getCmp(mep + 'plantime').getValue();
     record.data.plancontent = Ext.getCmp(mep + 'plancontent').getValue();
     record.data.orgdept = Ext.getCmp(mep + 'orgdept').getValue();
     record.data.planremark = Ext.getCmp(mep + 'planremark').getValue();
  },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
   },
   
   OnInitDetailData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('QuanGetListQuanControlPlanDetail.do?qcpd.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     var details = [];
     
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     
     me.saveParams = {details: Ext.encode(details)};
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