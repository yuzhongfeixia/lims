Ext.define('alms.stkcheck',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材盘点',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_stkcheck',
          storeUrl:'PrdSearchStkCheck.do',
          saveUrl:'PrdSaveStkCheck.do',
          expUrl:'PrdSearchStkCheck.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '耗材明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_stkcheckdetail',
          urlDetail: 'PrdGetListStkCheckDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '盘点单号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '出库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE))
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
    
     var tranusername = tools.UserPicker(mep + 'tranusername','stkcheck.tranusername','盘点人');
     
     tranusername.on('griditemclick', me.OnUserSelect, me);
    
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('盘点单号', 'stkcheck.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('仓库', 'stkcheck.storeid', mep + 'storeid', tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tranusername,
              tools.FormCombo('检测室', 'stkcheck.deptid', mep + 'deptid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormDate('盘点日期', 'stkcheck.checkdate', mep + 'checkdate', 'Y-m-d H:i', '98%', false, 5,nowdate),
              {xtype:'hiddenfield',name:'stkcheck.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'stkcheck.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'stkcheck.recouser',id: mep + 'recouser'},
              {xtype:'hiddenfield',name:'stkcheck.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'stkcheck.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('备注', 'stkcheck.remark', mep + 'remark',  200, '100%',true, 5,3)
     ];
     me.disNews = ['tranid','recouser', 'recousername'];
     me.disEdits = ['recouser', 'recousername','tranid'];
     me.enNews = [ 'storeid','deptid', 'checkdate', 'flowaction', 'flowstatus', 'tranuser', 'tranusername', 'checkuser', 'checkusername', 'remark', 'recodate', 'ismodify', 'transerial'];
     me.enEdits = [ 'storeid','deptid', 'checkdate', 'flowaction', 'flowstatus', 'tranuser', 'tranusername', 'checkuser', 'checkusername', 'remark', 'recodate', 'ismodify', 'transerial'];
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
           'stkcheck.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'stkcheck.storeid':tools.GetValueEncode(mep+'searchstoreid')
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
      tools.SetValue(mep+"deptid",item.deptid);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'storeid', item.storeid);
    tools.SetValue(mep + 'deptid', item.deptid);
    tools.SetValue(mep + 'checkdate', item.checkdate);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'recouser', item.recouser);
    tools.SetValue(mep + 'recousername', item.recousername);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'recodate', item.recodate);
    tools.SetValue(mep + 'ismodify', item.ismodify);
    tools.SetValue(mep + 'transerial', item.transerial);
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
      fieldLabel: '物品', name: 'detail.prdname', id: mep + 'prdname', winTitle: '选择物品',
      maxLength: 50, maxLengthText: '物品名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
      blankText: '物品为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
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
            prdname,
            tools.FormText('库存数量', 'detail.kcnumber', mep + 'kcnumber',12, '96%', false, 23,'is12p2'),
            tools.FormText('盘盈盘亏', 'detail.yknumber', mep + 'yknumber',12, '96%', false, 24,'is12p2')
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('规格型号', 'detail.prdstandard', mep + 'prdstandard',40, '100%', false, 22),
            tools.FormText('盘点数量', 'detail.prdnumber', mep + 'prdnumber',12, '100%', false, 22,'is12p2'),
            {xtype:'hiddenfield',name:'detail.prdid',id: mep + 'prdid'}
        ]}
       ]},      
       tools.FormTextArea('备注', 'detail.remark', mep + 'detailremark', 200, '100%', true, 25,4)
    ];

     me.disDetailControls = ['prdstandard'];
     me.enDetailControls = ['prdname','prdid', 'kcnumber', 'prdnumber', 'yknumber',  'detailremark'];
   },
   
   OnPrdSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.prdid){
        tools.SetValue(mep+"prdid",item.prdid);
        tools.SetValue(mep+"prdname",item.prdname);
        tools.SetValue(mep+"prdstandard",item.prdstandard);
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
      tools.SetValue(mep + 'prdid', item.prdid);
      tools.SetValue(mep + 'prdstandard', item.prdstandard);
      tools.SetValue(mep + 'kcnumber', item.kcnumber);
      tools.SetValue(mep + 'prdnumber', item.prdnumber);
      tools.SetValue(mep + 'yknumber', item.yknumber);
      tools.SetValue(mep + 'detailremark', item.remark);
      tools.SetValue(mep + 'prdname', item.prdname);
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
     record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
     record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
     record.data.prdstandard = Ext.getCmp(mep + 'prdstandard').getValue();
     record.data.prdnumber = Ext.getCmp(mep + 'prdnumber').getValue();
     record.data.kcnumber = Ext.getCmp(mep + 'kcnumber').getValue();
     record.data.yknumber = Ext.getCmp(mep + 'yknumber').getValue();
     record.data.remark = Ext.getCmp(mep + 'detailremark').getValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'storeid', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'deptid', gpersist.UserInfo.user.deptid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username);
   },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListStkCheckDetail.do?chdetail.tranid=') + tools.GetValue(mep + 'tranid');
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
    var storeid = Ext.getCmp(mep+'storeid').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(storeid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择仓库！');
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
    tools.SetValue(mep + 'deptid', gpersist.UserInfo.user.deptid);
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
  }
//  
//  //提交后单击gird 按钮判断
//  OnItemClick:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    var record = tools.OnGridLoad(me.plGrid);
//    
//    if(record.flowstatus != '01'){
//      tools.BtnsDisable(['btnEdit'], mep);
//    } else{
//      tools.BtnsEnable(['btnEdit'], mep);
//    }
//  },
//  
//  //双击grid 按钮判断
//  OnAfterShow:function(record){
//    var me = this;
//    var mep = me.tranPrefix;
//     if(record.flowstatus != '01'){
//      tools.BtnsDisable(['btnFormEdit'], mep);
//      tools.BtnsDisable(['btnSubmit'], mep);
//    } else{
//      tools.BtnsEnable(['btnFormEdit'], mep);
//      tools.BtnsEnable(['btnSubmit'], mep);
//    }
//  },
//  
//  //提交后按钮判断
//  OnAfterSubmit:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    tools.BtnsDisable(['btnFormEdit'], mep);
//    tools.BtnsDisable(['btnSubmit'], mep);
//  }
});