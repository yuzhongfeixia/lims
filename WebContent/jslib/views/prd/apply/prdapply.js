Ext.define('alms.prdapply',{
  extend:'gpersist.base.busform',
  
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
        editInfo:'耗材申请',
        winWidth:750,
        winHeight:250,
        hasColumnUrl:true,
        columnUrl:'SysSqlColumn.do?sqlid=p_get_prdapply',
        storeUrl:'PrdSearchPrdApply.do',
        saveUrl:'PrdSavePrdApply.do',
        expUrl:'PrdSearchPrdApply.do',
        idPrevNext:'tranid',
        hasGridSelect:true,
        hasPage:true,
        hasPrevNext : true,
        hasEdit: true,
        hasDetail: true,
        hasSubmit: true,
        detailTitle: '耗材采购明细',
        urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_prdapplydetail',
        urlDetail: 'PrdGetListPrdApplyDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '使用项目', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprojectid', id: mep + 'searchprojectid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchprdtype', mep + 'searchprdtype', 210, '耗材分类', 70, tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE))
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
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'prdapply.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('申请人', 'prdapply.tranuser', mep + 'tranuser',  tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
              
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormCombo('耗材类别', 'prdapply.prdtype', mep + 'prdtype', tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE), '96%', false, 2),
              //tools.FormDate('申请时间', 'prdapply.trandate', mep + 'trandate', 'Y-m-d H:i', '96%', false, 5,nowdate)
              tools.FormDate('申请时间', 'prdapply.trandate', mep + 'trandate', 'Y-m-d', '96%', false, 5,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('使用项目', 'prdapply.projectid', mep + 'projectid', 20, '100%', false, 3),
              tools.FormCombo('招标情况', 'prdapply.prdsource', mep + 'prdsource', tools.ComboStore('PrdSource', gpersist.SELECT_MUST_VALUE), '100%', false, 11),
              {xtype:'hiddenfield',name:'prdapply.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'prdapply.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'prdapply.tranusername',id: mep + 'tranusername'},
              {xtype:'hiddenfield',name:'prdapply.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('室主任意见', 'prdapply.confirmdesc', mep + 'confirmdesc', 200, '100%', true, 6,2),
         tools.FormTextArea('经费渠道', 'prdapply.fundsource', mep + 'fundsource',  100, '100%',true,7,2),
         tools.FormTextArea('特殊要求说明', 'prdapply.tranremark', mep + 'tranremark',  200, '100%',true, 8,3)
     ];
     me.disNews = ['tranid','confirmdesc'];
     me.disEdits = ['tranid','confirmdesc'];
     me.enNews = [ 'projectid', 'prdtype', 'flowaction', 'flowstatus', 'tranuser', 'tranusername', 'trandate', 'confirmuser', 'confirmusername',
     'confirmdate', 'confirmdesc', 'fundsource',  'tranremark','prdsource'];
     me.enEdits = [ 'projectid', 'prdtype', 'flowaction', 'flowstatus', 'tranuser', 'tranusername', 'trandate', 'confirmuser', 'confirmusername',
     'confirmdate', 'confirmdesc', 'fundsource',  'tranremark','prdsource'];
     
     Ext.getCmp(mep + 'tranuser').on('change',me.OnTranSelect,me);
   },
   
   OnTranSelect:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranusername',item.rawValue);
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
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdapply.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'prdapply.projectid':tools.GetValueEncode(mep+'searchprojectid'),
           'prdapply.prdtype':tools.GetValueEncode(mep+'searchprdtype')
         })
       });
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
   
   OnAfterCreateEdit:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.plEdit.height = '30%';
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
//    me.OnGetUser(gpersist.UserInfo.user.deptid);
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'projectid', item.projectid);
    tools.SetValue(mep + 'prdtype', item.prdtype);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'confirmuser', item.confirmuser);
    tools.SetValue(mep + 'confirmusername', item.confirmusername);
    tools.SetValue(mep + 'confirmdate', item.confirmdate);
    tools.SetValue(mep + 'confirmdesc', item.confirmdesc);
    tools.SetValue(mep + 'fundsource', item.fundsource);
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
    tools.SetValue(mep + 'tranremark', item.tranremark);
    tools.SetValue(mep + 'prdsource', item.prdsource);
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
      blankText: '耗材为空！', allowBlank: false, anchor: '100%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basprd',
      storeUrl: 'PrdSearchBasPrd.do',
      editable:true,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('序号', 'detail.prdserial', mep + 'prdserial',20, '96%', false, 1,'isnumber'),
            tools.FormText('购买数量', 'detail.prdcount', mep + 'prdcount',15, '96%', false, 3,'isinteger'),
            tools.FormText('规格型号', 'detail.prdstandard', mep + 'prdstandard',20, '96%', true, 1),
            tools.FormCombo('是否验收 ', 'detail.isverify', mep + 'isverify', tools.ComboStore('IsVerify', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [	
            prdname,
//            tools.FormText('耗材剩余数量', 'detail.buycount', mep + 'buycount',15, '100%', false, 4,'isnumber',90),
            
            tools.FormCombo('计量单位', 'detail.prdunit', mep + 'prdunitdetail', tools.ComboStore('PrdUnit', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
            tools.FormCombo('级别 ', 'detail.level', mep + 'level', tools.ComboStore('PrdLevel', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
//            tools.FormDate('最后购买时间', 'detail.buydate', mep + 'buydate', 'Y-m-d ', '100%', true, 5,'',90),
            {xtype:'hiddenfield',name:'detail.prdid',id: mep + 'prdid'}
        ]}
       ]},      
       tools.FormTextArea('备注', 'detail.remark', mep + 'remark', 200, '100%', true, 6,4)
    ];

     me.disDetailControls = ['prdserial'];
     me.enDetailControls = ['prdcount','prdname','remark','prdunitdetail','prdstandard','level','isverify'];
     
   },
   
 OnPrdSelect:function(render,item){
   var me = this;
   var mep = me.tranPrefix;
   if(item && item.prdid){
     tools.SetValue(mep+"prdid",item.prdid);
     tools.SetValue(mep+"prdname",item.prdname);
     tools.SetValue(mep+"prdunitdetail",item.prdunit);
     tools.Disabled(["prdunitdetail"],mep);
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
   tools.SetValue(mep + 'tranid', item.tranid);
   tools.SetValue(mep + 'prdserial', item.prdserial);
   tools.SetValue(mep + 'prdid', item.prdid);
   tools.SetValue(mep + 'prdname', item.prdname);
   tools.SetValue(mep + 'prdcount', item.prdcount);
   tools.SetValue(mep + 'remark', item.remark);
   tools.SetValue(mep + 'prdunitdetail', item.prdunit);
   tools.SetValue(mep + 'level',item.level);
   tools.SetValue(mep + 'prdstandard',item.prdstandard);
   tools.SetValue(mep + 'isverify',item.isverify);
    
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
    if(Ext.getCmp(mep + 'isverify').getValue() == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择耗材是否验收！');
      }
    if(Ext.getCmp(mep + 'level').getValue() == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择耗材级别！');
      }
    record.data.tranid =  Ext.getCmp(mep + 'tranid').getValue();
    record.data.prdserial = Ext.getCmp(mep + 'prdserial').getValue();
    record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
    record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
    record.data.prdcount = Ext.getCmp(mep + 'prdcount').getValue();
    record.data.remark = Ext.getCmp(mep + 'remark').getValue();
    record.data.prdunit = Ext.getCmp(mep + 'prdunitdetail').getValue();
    record.data.prdunitname = Ext.getCmp(mep + 'prdunitdetail').getDisplayValue();
    record.data.prdstandard = Ext.getCmp(mep + 'prdstandard').getValue();
    record.data.isverify = Ext.getCmp(mep + 'isverify').getValue();
    record.data.level = Ext.getCmp(mep + 'level').getValue();
    record.data.levelname = Ext.getCmp(mep + 'level').getDisplayValue();
    record.data.isverifyname = Ext.getCmp(mep + 'isverify').getDisplayValue();
 },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'prdtype', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'flowaction', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'flowstatus', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'confirmdesc', '意见由室主任填写');
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
  },
   
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    var serial = me.plDetailGrid.store.getCount() + 1;
    tools.SetValue(mep + 'prdserial', serial);
    
   },
   
  OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListPrdApplyDetail.do?prddetail.tranid=') + tools.GetValue(mep + 'tranid');
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
    var prdtype = Ext.getCmp(mep+'prdtype').getValue();
    var tranuser = Ext.getCmp(mep+'tranuser').getValue();
    var count = me.plDetailGrid.store.getCount();
    if(count == 0){
      tools.alert('请填写申请耗材！');
      return;
    }
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(prdtype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择耗材类别！');
        return;
      }
    }
    
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