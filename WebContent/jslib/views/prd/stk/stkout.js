Ext.define('alms.stkout',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材出库',
          winWidth:750,
          winHeight:350,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_stkout',
          storeUrl:'PrdSearchStkOut.do',
          saveUrl:'PrdSaveStkOut.do',
          expUrl:'PrdSearchStkOut.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '耗材明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_stkoutdetail',
          urlDetail: 'PrdGetListStkOutDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '出库单号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '实际单据号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchoutfact', id: mep + 'searchoutfact', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchstoreid', mep + 'searchstoreid', 210, '出库仓库', 70, tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE)),
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '领出部门', 70, tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE))
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
    
     var outusername = tools.UserPicker(mep + 'outusername','stkout.outusername','领出人');
     
     outusername.on('griditemclick', me.OnUserSelect, me);
    
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('出库单号', 'stkout.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('出库仓库', 'stkout.storeid', mep + 'storeid', tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE), '96%', false, 4),
              outusername
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('实际单据号', 'stkout.outfact', mep + 'outfact', 20, '96%', true, 2,'',90),
              tools.FormDate('出库日期', 'stkout.outdate', mep + 'outdate', 'Y-m-d', '96%', false, 5,nowdate,90)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('记账员', 'stkout.recousername', mep + 'recousername', 20, '98%', false, 3),
              tools.FormCombo('出库类型', 'stkout.stockouttype', mep + 'stockouttype', tools.ComboStore('StockOutType', gpersist.SELECT_MUST_VALUE), '98%', false,6),
              {xtype:'hiddenfield',name:'stkout.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'stkout.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'stkout.recouser',id: mep + 'recouser'},
              {xtype:'hiddenfield',name:'stkout.outuser',id: mep + 'outuser'},
              {xtype:'hiddenfield',name:'stkout.storename',id: mep + 'storename'},
              {xtype:'hiddenfield',name:'stkout.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('备注', 'stkout.remark', mep + 'remark',  200, '99%',true, 5,3)
     ];
     me.disNews = ['tranid','recouser', 'recousername'];
     me.disEdits = ['recouser', 'recousername','tranid'];
     me.enNews = ['outfact', 'storeid', 'outdate', 'stockouttype', 'flowaction', 'flowstatus', 'outuser', 'outusername', 'checkuser', 
                   'checkusername', 'checkdesc', 'recouser', 'recousername', 'recodate', 'remark'];
     me.enEdits = ['outfact', 'storeid', 'outdate', 'stockouttype', 'flowaction', 'flowstatus', 'outuser', 'outusername', 'checkuser', 
                    'checkusername', 'checkdesc', 'recouser', 'recousername', 'recodate', 'remark'];
     
     Ext.getCmp(mep + 'storeid').on('change',me.OnStoreSelect,me);
   },
   
   OnStoreSelect:function(item,s){
      var me = this;
      var mep = me.tranPrefix;
      tools.SetValue(mep + 'storename',item.rawValue);
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
           'stkout.storeid':tools.GetValueEncode(mep+'searchstoreid'),
           'stkout.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'stkout.outfact':tools.GetValueEncode(mep+'searchoutfact'),
           'stkout.deptid':tools.GetValueEncode(mep+'searchdeptid')
         })
       });
     }
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"outuser",item.userid);
      tools.SetValue(mep+"outusername",item.username);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'outfact', item.outfact);
    tools.SetValue(mep + 'storeid', item.storeid);
    tools.SetValue(mep + 'storename', item.storename);
    tools.SetValue(mep + 'outdate', item.outdate);
    tools.SetValue(mep + 'stockouttype', item.stockouttype);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'outuser', item.outuser);
    tools.SetValue(mep + 'outusername', item.outusername);
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
     ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnPrdSearch, scope: me }
    ];
     
     var prdname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '物品', name: 'detail.prdname', id: mep + 'prdname', winTitle: '选择物品',
      maxLength: 50, maxLengthText: '物品名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
      blankText: '物品为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdcode',
      storeUrl: 'PrdSearchPrdCode.do',
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     prdname.on('gridbeforeload', me.OnPrdBeforeLoad, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            prdname,
            tools.FormText('出库数量', 'detail.prdnumber', mep + 'prdnumber',12, '96%', false, 22,'is12p2'),
//            tools.FormText('出库总数', 'detail.factnumber', mep + 'factnumber',12, '96%', false, 24,'is12p2')
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('耗材条码', 'detail.prdcode', mep + 'prdcode',20, '100%', true, 20),
//            tools.FormText('单位数量', 'detail.unitnumber', mep + 'unitnumber',12, '100%', false, 23,'is12p2'),
            tools.FormCombo('计量单位', 'detail.prdunit', mep + 'prdunit', tools.ComboStore('PrdUnit',gpersist.SELECT_MUST_VALUE), '100%', false,25),
            {xtype:'hiddenfield',name:'detail.prdid',id: mep + 'prdid'},
            {xtype:'hiddenfield',name:'detail.unitnumber',id: mep + 'unitnumber'},
            {xtype:'hiddenfield',name:'detail.factnumber',id: mep + 'factnumber'},
            {xtype:'hiddenfield',name:'numcompare',id: mep + 'numcompare'}
        ]}
       ]},   
       tools.FormTextArea('规格型号', 'detail.prdstandard', mep + 'prdstandard', 40, '100%', true, 26,4),
       tools.FormTextArea('备注', 'detail.remark', mep + 'detailremark', 200, '100%', true, 27,4)
    ];

     me.disDetailControls = ['factnumber','prdunit','prdstandard','prdcode'];
     me.enDetailControls = ['prdcode', 'prdid', 'prdnumber', 'unitnumber', 'detailremark','prdname'];
     tools.SetValue(mep+"unitnumber",1);
     Ext.getCmp(mep +'prdnumber').on('blur',me.OnPrdNumber,me);
//     Ext.getCmp(mep +'unitnumber').on('blur',me.OnPrdNumber,me); 
     
   },
   
   OnPrdNumber:function(){
     var me = this;
     var mep = me.tranPrefix;
     var prdnumber = tools.GetValue(mep + 'prdnumber');
     var prdname = tools.GetValue(mep + 'prdname');
     var numcompare = tools.GetValue(mep + 'numcompare');
     var factnumber;
     var prdnumbers = 0;
     var detailnumber= 0;
     
     var gridlength = me.plDetailGrid.store.getCount();
     if(gridlength>0){
       for(var i =0;i<gridlength;i++ ){
         var detailprdname = me.plDetailGrid.store.getAt(i).get('prdname');
         if(prdname == detailprdname){
           
           detailnumber += parseFloat(me.plDetailGrid.store.getAt(i).get('prdnumber')); 
           prdnumbers += parseFloat(me.plDetailGrid.store.getAt(i).get('prdnumber'));
         }
       }
       prdnumbers = parseFloat(prdnumbers)+parseFloat(prdnumber);
     }else{
       prdnumbers = prdnumber;
     }
     
     if(prdnumbers*1 > numcompare*1 ){
        tools.alert('库存不足,数量还有'+parseFloat(numcompare));
        Ext.getCmp(mep + 'prdnumber').reset();
        Ext.getCmp(mep + 'factnumber').reset();
     }
     
     if(!isNaN(prdnumber)){
         factnumber = prdnumber;
      }else{
        factnumber = 0;
      }
     
     tools.SetValue(mep + 'factnumber',factnumber);
   },
   
   OnPrdSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.prdid){
        tools.SetValue(mep+"prdid",item.prdid);
        tools.SetValue(mep+"prdname",item.prdname);
        tools.SetValue(mep + 'prdstandard', item.prdstandard);
        tools.SetValue(mep + 'prdunit', item.prdunit);
        tools.SetValue(mep + 'prdcode', item.prdcode);
        tools.SetValue(mep + 'numcompare', item.prdnumber);
        tools.SetValue(mep + 'unitnumber', item.unitnumber);
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
      var storeid = tools.GetValue(mep + 'storeid');
      if(prd.store){
        prd.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'pc.storeid': storeid,
              'pc.prdname':tools.GetValueEncode(mep + 'searchprdname')
            });
        });
      }
   },
   
   OnLoadDetailData: function (item) {
      var me = this;
      var mep = me.tranPrefix;

      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'prdcode', item.prdcode);
      tools.SetValue(mep + 'prdid', item.prdid);
      tools.SetValue(mep + 'prdstandard', item.prdstandard);
      tools.SetValue(mep + 'prdunit', item.prdunit);
      tools.SetValue(mep + 'prdnumber', item.prdnumber);
      tools.SetValue(mep + 'unitnumber', item.unitnumber);
      tools.SetValue(mep + 'factnumber', item.factnumber);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'prdname', item.prdname);
   },
   
   OnListNew: function() {
    var me = this;
    var mep = me.tranPrefix;
    var date = new Date();
    var storeid = tools.GetValue(mep + 'storeid');
    if(storeid == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择出库仓库！');
      return;
    }
    
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
     var mep = me.tranPrefix
     record.data.tranid =  Ext.getCmp(mep + 'tranid').getValue();
     record.data.prdcode = Ext.getCmp(mep + 'prdcode').getValue();
     record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
     record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
     record.data.prdnumber = Ext.getCmp(mep + 'prdnumber').getValue();
     record.data.unitnumber = Ext.getCmp(mep + 'unitnumber').getValue();
     record.data.factnumber = Ext.getCmp(mep + 'factnumber').getValue();
     record.data.remark = Ext.getCmp(mep + 'detailremark').getValue();
     record.data.prdstandard = Ext.getCmp(mep + 'prdstandard').getValue();
     record.data.prdunit = Ext.getCmp(mep + 'prdunit').getValue();
     record.data.prdunitname = Ext.getCmp(mep + 'prdunit').getDisplayValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'storeid', '01');
     tools.SetValue(mep + 'stockouttype', '01');
//     tools.SetValue(mep + 'storeid', gpersist.SELECT_MUST_VALUE);
//     tools.SetValue(mep + 'stockouttype', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'recouser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'recousername', gpersist.UserInfo.user.username );
   },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListStkOutDetail.do?outdetail.tranid=') + tools.GetValue(mep + 'tranid');
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
    var stockouttype = Ext.getCmp(mep+'stockouttype').getValue();
    var storeid = Ext.getCmp(mep+'storeid').getValue();
    var count = me.plDetailGrid.store.getCount();
    if(count == 0){
      tools.alert('请填写出库耗材明细！');
      return;
    }
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(stockouttype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择出库类型！');
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
  }
});