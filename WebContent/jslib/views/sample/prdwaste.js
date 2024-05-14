Ext.define('alms.prdwaste',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'废弃物申请',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdwaste',
          storeUrl:'PrdSearchPrdWaste.do',
          saveUrl:'PrdSavePrdWaste.do',
          expUrl:'PrdSearchPrdWaste.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '废弃物明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_prdwastedetail',
          urlDetail: 'PrdGetListPrdWasteDetail.do',
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
     
//     var tranusername = tools.UserPicker(mep + 'tranusername','prdwaste.tranusername','申请人');
//     
//     tranusername.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('申请编号', 'prdwaste.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('科室名称', 'prdwaste.labid', mep + 'labid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 3)
              
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormCombo('申请人', 'prdwaste.tranuser', mep + 'tranuser',  tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormDate('申请时间', 'prdwaste.trandate', mep + 'trandate', 'Y-m-d', '100%', false, 2,nowdate),
              {xtype:'hiddenfield',name:'prdwaste.tranusername',id: mep + 'tranusername'},
              {xtype:'hiddenfield',name:'prdwaste.deal.action',id: mep + 'datadeal'}
           ]}
         ]}
     ];
     me.disNews = ['tranid', 'labid'];
     me.disEdits = ['tranid', 'labid'];
     me.enNews = [ 'tranuser', 'tranusername', 'trandate', 'flowaction', 'flowstatus', 'comfirmuser', 'comfirmusername'];
     me.enEdits = [ 'tranuser', 'tranusername', 'trandate', 'flowaction', 'flowstatus', 'comfirmuser', 'comfirmusername' ];
  
     Ext.getCmp(mep + 'tranuser').on('change',me.OnTranSelect,me);
   },
   
   OnTranSelect:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranusername',item.rawValue);
   },
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdwaste.tranid':tools.GetValueEncode(mep+'searchtranid')
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
      tools.SetValue(mep+"tranuser",item.userid);
      tools.SetValue(mep+"tranusername",item.username);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    me.OnGetUser(gpersist.UserInfo.user.deptid);
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'labid', item.labid);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'comfirmuser', item.comfirmuser);
    tools.SetValue(mep + 'comfirmusername', item.comfirmusername);
    tools.SetValue(mep + 'audituser', item.audituser);
    tools.SetValue(mep + 'auditusername', item.auditusername);
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'auditdesc', item.auditdesc);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'checkdate', item.checkdate);
    tools.SetValue(mep + 'checkdesc', item.checkdesc);
    tools.SetValue(mep + 'agreeuser', item.agreeuser);
    tools.SetValue(mep + 'agreeusername', item.agreeusername);
    tools.SetValue(mep + 'agreedate', item.agreedate);
    tools.SetValue(mep + 'wasterunit', item.wasterunit);
    tools.SetValue(mep + 'wastersign', item.wastersign);
    tools.SetValue(mep + 'wasterdate', item.wasterdate);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var prditems = [
     ' ', { xtype: 'textfield', fieldLabel: '耗材编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdid', id: mep + 'searchprdid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnPrdSearch, scope: me }
    ];
     
     var prdname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '耗材', name: 'detail.prdname', id: mep + 'prdname', winTitle: '选择耗材',
      maxLength: 50, maxLengthText: '耗材名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
      blankText: '耗材为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basprd',
      storeUrl: 'PrdSearchBasPrd.do',
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('废弃物编号', 'detail.prdid', mep + 'prdid',20, '96%', false, 101),
            tools.FormText('废弃物规格', 'detail.prdstd', mep + 'prdstd',20, '96%', false, 103)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('废弃物名称', 'detail.prdname', mep + 'prdname',30, '100%', false, 102),
            tools.FormText('废弃物数量', 'detail.prdcount', mep + 'prdcount',15, '100%', false, 104)
        ]}
       ]},      
       tools.FormTextArea('备注', 'detail.tranremark', mep + 'tranremark', 200, '100%', true, 105,4)
    ];

     me.disDetailControls = [];
     me.enDetailControls = ['prdname','prdid','prdstd', 'prdcount', 'tranremark'];
   },
   
   OnPrdSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.prdid){
        tools.SetValue(mep+"prdid",item.prdid);
        tools.SetValue(mep+"prdname",item.prdname);
     }
   },
   
   OnPrdSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnPrdBeforeLoad();
      var prd = Ext.getCmp(mep + 'prdname');
      prd.store.loadPage(1);
   },
   
   OnPrdBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var prd = Ext.getCmp(mep + 'prdname');
      if(prd.store){
        prd.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'basprd.prdid':tools.GetValueEncode(mep + 'searchprdid'),
              'basprd.prdname':tools.GetValueEncode(mep + 'searchprdname')
            });
        });
      }
   },
   
   OnLoadDetailData: function (item) {
     var me = this;
     var mep = me.tranPrefix;
     tools.SetValue(mep + 'prdid', item.prdid);
     tools.SetValue(mep + 'prdcount', item.prdcount);
     tools.SetValue(mep + 'tranremark', item.tranremark);
     tools.SetValue(mep + 'prdname', item.prdname);
     tools.SetValue(mep + 'prdstd', item.prdstd);
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
     record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
     record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
     record.data.prdstd = Ext.getCmp(mep + 'prdstd').getValue();
     record.data.prdcount = Ext.getCmp(mep + 'prdcount').getValue();
     record.data.tranremark = Ext.getCmp(mep + 'tranremark').getValue();
  },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'labid', gpersist.UserInfo.user.deptid);
     me.OnGetUser(gpersist.UserInfo.user.deptid);
  },
   
  OnGetUser: function (deptid) {
    var me = this, mep = me.tranPrefix;
    
    var tranuser = Ext.getCmp(mep + 'tranuser');
    
    if (Ext.isEmpty(deptid) || (deptid == gpersist.SELECT_MUST_VALUE))
      return;
    
    if (tranuser) {
      if (deptid == gpersist.SELECT_MUST_VALUE) {
        tranuser.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetUserCombo(gpersist.SELECT_MUST_VALUE, deptid, mep + 'tranuser');
      }
    }
  },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListPrdWasteDetail.do?wastedetail.tranid=') + tools.GetValue(mep + 'tranid');
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
     
     me.saveParams = { details: Ext.encode(details) };
   },
   
   OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var tranuser = Ext.getCmp(mep+'tranuser').getValue();
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(tranuser == gpersist.SELECT_MUST_VALUE ){
          tools.alert('请选择申请人！');
          return;
      }
    }
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