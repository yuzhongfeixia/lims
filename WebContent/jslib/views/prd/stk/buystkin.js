Ext.define('alms.buystkin',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'购买耗材试剂入库',
          winWidth:750,
          winHeight:370,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_stkin',
          storeUrl:'PrdSearchStkIn.do',
          saveUrl:'PrdSaveStkIn.do',
          expUrl:'PrdSearchStkIn.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '耗材明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_stkindetail',
          urlDetail: 'PrdGetListStkInDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '入库单号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '实际单据号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchinfact', id: mep + 'searchinfact', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '入库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE))
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
    
     var inusername = tools.UserPicker(mep + 'inusername','stkin.inusername','入库人员');
     
     inusername.on('griditemclick', me.OnUserSelect, me);
    
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('入库单号', 'stkin.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('入库仓库', 'stkin.storeid', mep + 'storeid', tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE), '96%', false, 4),
              inusername
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('实际单据编码', 'stkin.infact', mep + 'infact', 20, '96%', true, 2,'',90),
              tools.FormDate('入库日期', 'stkin.indate', mep + 'indate', 'Y-m-d', '96%', false, 5,nowdate,90)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('记账员', 'stkin.recousername', mep + 'recousername', 20, '96%', false, 3),
              tools.FormCombo('入库类型', 'stkin.stockintype', mep + 'stockintype', tools.ComboStore('StockInType', gpersist.SELECT_MUST_VALUE), '96%', false,6),
              {xtype:'hiddenfield',name:'stkin.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'stkin.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'stkin.recouser',id: mep + 'recouser'},
              {xtype:'hiddenfield',name:'stkin.inuser',id: mep + 'inuser'},
              {xtype:'hiddenfield',name:'stkin.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('备注', 'stkin.remark', mep + 'remark',  200, '99%',true, 5,3)
     ];
     me.disNews = ['tranid','recouser','storeid', 'recousername'];
     me.disEdits = ['recouser', 'recousername','storeid','tranid'];
     me.enNews = ['infact',  'flowaction', 'flowstatus', 'indate', 'stockintype', 'inuser', 'inusername', 'checkuser', 'checkusername', 'checkdesc', 
      'recodate', 'remark'];
     me.enEdits = [ 'infact',  'flowaction', 'flowstatus', 'indate', 'stockintype', 'inuser', 'inusername', 'checkuser', 'checkusername', 'checkdesc', 
      'recodate', 'remark'];
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
           'stkin.storeid':tools.GetValueEncode(mep+'searchstoreid'),
           'stkin.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'stkin.infact':tools.GetValueEncode(mep+'searchinfact')
         })
       });
     }
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"inuser",item.userid);
      tools.SetValue(mep+"inusername",item.username);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'infact', item.infact);
    tools.SetValue(mep + 'storeid', item.storeid);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'indate', item.indate);
    tools.SetValue(mep + 'stockintype', item.stockintype);
    tools.SetValue(mep + 'inuser', item.inuser);
    tools.SetValue(mep + 'inusername', item.inusername);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'checkdesc', item.checkdesc);
    tools.SetValue(mep + 'recouser', item.recouser);
    tools.SetValue(mep + 'recousername', item.recousername);
    tools.SetValue(mep + 'recodate', item.recodate);
    tools.SetValue(mep + 'remark', item.remark);
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
      maxLength: 50, maxLengthText: '物品名称不能超过50字！', selectOnFocus: false, labelWidth: 90,
      blankText: '物品为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdverifyforin',
      storeUrl: 'PrdSearchPrdVerifyForIn.do',
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            prdname,
            tools.FormText('入库数量', 'detail.prdnumber', mep + 'prdnumber',4, '96%', true, 22,'is12p2',90),
//            tools.FormText('单位数量', 'detail.unitnumber', mep + 'unitnumber',4, '96%', true, 24,'is12p2',90),
            tools.FormText('入库单价(元)', 'detail.prdprice', mep + 'prdprice',6, '96%', true, 21,'is12p2',90),
            tools.FormText('生产厂商', 'detail.factoryname', mep + 'factoryname',40, '96%', true, 28,'',90),
            tools.FormText('有效期(月)', 'detail.validdate', mep + 'validdate',12, '96%', true, 30,'isnumber',90),
            tools.FormCombo('购买人', 'detail.buyuser', mep + 'buyuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false,29,90),
            tools.FormCombo('耗材类型', 'detail.prdtype', mep + 'prdtype', tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE), '96%', false,29,90)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('条码前缀', 'detail.prdcodeprefix', mep + 'prdcodeprefix',20, '100%', true, 20,'',90),
            tools.FormCombo('计量单位', 'detail.prdunit', mep + 'prdunit', tools.ComboStore('PrdUnit',gpersist.SELECT_MUST_VALUE), '100%', false,29,90),
//            tools.FormText('入库总数', 'detail.factnumber', mep + 'factnumber',12, '100%', true, 23,'is12p2',90),
            tools.FormText('入库金额(元)', 'detail.prdamount', mep + 'prdamount',12, '100%', true,26,'is12p2',90),
            tools.FormText('供应商', 'detail.tradename', mep + 'tradename',12, '100%', true,26,'',90),
            tools.FormDate('生产日期', 'detail.factorydate', mep + 'factorydate', 'Y-m-d', '100%', true, 27,nowdate,90),
            tools.FormDate('购买日期', 'detail.buydate', mep + 'buydate', 'Y-m-d', '100%', true, 31,nowdate,90),
            tools.FormText('规格型号', 'detail.prdstandard', mep + 'prdstandard',12, '100%', true,26,'',90),
            {xtype:'hiddenfield',name:'detail.unitnumber',id: mep + 'unitnumber'},
            {xtype:'hiddenfield',name:'detail.factnumber',id: mep + 'factnumber'},
            {xtype:'hiddenfield',name:'detail.tradeid',id: mep + 'tradeid'},
            {xtype:'hiddenfield',name:'detail.prdid',id: mep + 'prdid'},
            {xtype:'hiddenfield',name:'detail.prdserial',id: mep + 'prdserial'},
            {xtype:'hiddenfield',name:'detail.applyid',id: mep + 'applyid'},
            {xtype:'hiddenfield',name:'detail.verifyid',id: mep + 'verifyid'}
        ]}
       ]},  
       tools.FormTextArea('备注', 'detail.remark', mep + 'detailremark', 200, '100%', true, 31,4,90)
    ];

     me.disDetailControls = ['prdamount'];
     me.enDetailControls = ['prdcodeprefix', 'prdname','prdid', 'prdnumber', 'prdprice', 'unitnumber', 'tradeid','tradename', 
                            'factoryname', 'factorydate', 'validdate', 'buyuser', 'buyusername', 'buydate', 'detailremark','prdunit','prdtype',
                            'factnumber','prdstandard'];
     tools.SetValue(mep+"unitnumber",1);
     Ext.getCmp(mep +'prdnumber').on('change',me.OnPrdNumber,me);
     Ext.getCmp(mep +'prdprice').on('change',me.OnPrdPrice,me);
     Ext.getCmp(mep +'prdnumber').on('change',me.OnPrdPrice,me);
     
   },
   
   OnPrdNumber:function(){
     var me = this;
     var mep = me.tranPrefix;
     var prdnumber = tools.GetValue(mep + 'prdnumber');
     var unitnumber = tools.GetValue(mep + 'unitnumber');
     var factnumber;
     
//   去掉单位数量
//   if(!isNaN(prdnumber) && !isNaN(unitnumber)){
//      factnumber = prdnumber*unitnumber;
//   }else{
//     factnumber = 0;
//   }
   if(!isNaN(prdnumber) ){
      factnumber = prdnumber;
   }else{
     factnumber = 0;
   }
   
     
     tools.SetValue(mep + 'factnumber',factnumber);
   },
   
   OnPrdPrice:function(){
     var me = this;
     var mep = me.tranPrefix;
     var prdnumber = tools.GetValue(mep + 'prdnumber');
     var prdprice = tools.GetValue(mep + 'prdprice');
     var prdamount;
     
     if(!isNaN(prdnumber) && !isNaN(prdprice)){
        prdamount = prdnumber*prdprice;
     }else{
       prdamount = 0;
     }
     
     tools.SetValue(mep + 'prdamount',prdamount);
   },
   
   OnPrdSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item){
        tools.SetValue(mep+"prdid",item.prdid);
        tools.SetValue(mep+"verifyid",item.verifyid);
        tools.SetValue(mep+"prdname",item.prdname);
        tools.SetValue(mep+"prdunit",item.prdunit);
        tools.SetValue(mep+"prdstandard",item.prdstandard);
        tools.SetValue(mep+"prdnumber",item.buycount);
        tools.SetValue(mep+"factoryname",item.factoryname);
        tools.SetValue(mep+"tradename",item.tradename);
        tools.SetValue(mep+"buydate",item.buydate);
        tools.SetValue(mep+"buyuser",item.tranuser);
        tools.SetValue(mep+"prdtype",item.prdtype);
        tools.SetValue(mep+"prdserial",item.prdserial);
        tools.SetValue(mep+"applyid",item.tranid);
        tools.SetValue(mep+"storeid",item.storeid);
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
              'prdverify.prdid':tools.GetValueEncode(mep + 'searchprdid'),
              'prdverify.prdname':tools.GetValueEncode(mep + 'searchprdname')
            });
        });
      }
   },
   
   OnLoadDetailData: function (item) {
     var me = this;
     var mep = me.tranPrefix;
     tools.SetValue(mep + 'tranid', item.tranid);
     tools.SetValue(mep + 'prdcodeprefix', item.prdcodeprefix);
     tools.SetValue(mep + 'prdid', item.prdid);
     tools.SetValue(mep + 'prdname', item.prdname);
     tools.SetValue(mep + 'verifyid', item.verifyid);
     tools.SetValue(mep + 'prdunit', item.prdunit);
     tools.SetValue(mep + 'prdstandard', item.prdstandard);
     tools.SetValue(mep + 'prdtype', item.prdtype);
     tools.SetValue(mep + 'prdnumber', item.prdnumber);
     tools.SetValue(mep + 'prdprice', item.prdprice);
     tools.SetValue(mep + 'unitnumber', item.unitnumber);
     tools.SetValue(mep + 'factnumber', item.factnumber);
     tools.SetValue(mep + 'prdamount', item.prdamount);
     tools.SetValue(mep + 'tradename', item.tradename);
     tools.SetValue(mep + 'factoryname', item.factoryname);
     tools.SetValue(mep + 'factorydate', item.factorydate);
     tools.SetValue(mep + 'validdate', item.validdate);
     tools.SetValue(mep + 'buyuser', item.buyuser);
     tools.SetValue(mep + 'buyusername', item.buyusername);
     tools.SetValue(mep + 'buydate', item.buydate);
     tools.SetValue(mep + 'detailremark', item.remark);
     tools.SetValue(mep + 'prdserial', item.prdserial);
     tools.SetValue(mep + 'applyid', item.applyid);
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
     record.data.prdcodeprefix = Ext.getCmp(mep + 'prdcodeprefix').getValue();
     record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
     record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
     record.data.verifyid = Ext.getCmp(mep + 'verifyid').getValue();
     record.data.prdstandard = Ext.getCmp(mep + 'prdstandard').getValue();
     record.data.prdtype = Ext.getCmp(mep + 'prdtype').getValue();
     record.data.prdtypename = Ext.getCmp(mep + 'prdtype').getDisplayValue();
     record.data.prdunit = Ext.getCmp(mep + 'prdunit').getValue();
     record.data.prdunitname = Ext.getCmp(mep + 'prdunit').getDisplayValue();
     record.data.prdnumber = Ext.getCmp(mep + 'prdnumber').getValue();
     record.data.prdprice = Ext.getCmp(mep + 'prdprice').getValue();
     record.data.unitnumber = Ext.getCmp(mep + 'unitnumber').getValue();
     record.data.factnumber = Ext.getCmp(mep + 'factnumber').getValue();
     record.data.prdamount = Ext.getCmp(mep + 'prdamount').getValue();
     record.data.tradename = Ext.getCmp(mep + 'tradename').getValue();
     record.data.tradename = Ext.getCmp(mep + 'tradename').getValue();
     record.data.factoryname = Ext.getCmp(mep + 'factoryname').getValue();
     record.data.factorydate = Ext.getCmp(mep + 'factorydate').getValue();
     record.data.validdate = Ext.getCmp(mep + 'validdate').getValue();
     record.data.buyuser = Ext.getCmp(mep + 'buyuser').getValue();
     record.data.buyusername = Ext.getCmp(mep + 'buyuser').getDisplayValue();
     record.data.buydate = Ext.getCmp(mep + 'buydate').getValue();
     record.data.remark = Ext.getCmp(mep + 'detailremark').getValue();
     record.data.prdserial = Ext.getCmp(mep + 'prdserial').getValue();
     record.data.applyid = Ext.getCmp(mep + 'applyid').getValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
//     tools.SetValue(mep + 'storeid', '01');
     tools.SetValue(mep + 'stockintype', '01');
//     tools.SetValue(mep + 'storeid', gpersist.SELECT_MUST_VALUE);
//     tools.SetValue(mep + 'stockintype', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'recouser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'recousername', gpersist.UserInfo.user.username );
     tools.SetValue(mep + 'inuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'inusername', gpersist.UserInfo.user.username );
   },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListStkInDetail.do?indetail.tranid=') + tools.GetValue(mep + 'tranid');
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
    var stockintype = Ext.getCmp(mep+'stockintype').getValue();
    var storeid = Ext.getCmp(mep+'storeid').getValue();
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(stockintype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择入库类型！');
        return;
      }
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
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '25%';
  }
});
