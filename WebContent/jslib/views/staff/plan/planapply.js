 Ext.define('alms.planapply',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'年度培训计划申请',
          winWidth:900,
          winHeight:700,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_userplanyear',
          storeUrl:'StaffSearchUserPlanYear.do',
          saveUrl:'StaffSaveUserPlanYear.do',
          expUrl:'StaffSearchUserPlanYear.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '培训计划',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_userplanyeardetail',
          urlDetail: 'StaffGetListUserPlanYearDetail.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '年度', labelWidth: 40, width: 200, maxLength: 40, name: 'searchtranyear', id: mep + 'searchtranyear', allowBlank: true }
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
              tools.FormText('业务编号', 'userplan.tranid', mep + 'tranid', 20, '96%', false, 1)
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('计划年度', 'userplan.tranyear', mep + 'tranyear', 4, '100%', false, 3),
//              tranusername,
              {xtype:'hiddenfield',name:'userplan.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'userplan.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'userplan.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'userplan.modifyserial',id: mep + 'modifyserial'},
              {xtype:'hiddenfield',name:'userplan.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('申请说明', 'userplan.tranremark', mep + 'tranremark', 200, '100%', true, 6,5)
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['deptid','tranid', 'tranuser', 'tranusername', 'trandate' ,'tranyear'];
     me.enNews = [ 'deptid','flowaction', 'flowstatus', 'tranremark','tranyear' ];
     me.enEdits = [  'flowaction', 'flowstatus', 'tranremark'];
     
//     //判断该年度是否存在
//     Ext.getCmp(mep + 'tranyear').on('blur',me.OnIsHavePlanYear,me);
   },
   
//   OnIsHavePlanYear:function(){
//     var me = this;
//     var mep = me.tranPrefix;
//     var tranyear = tools.GetValue(mep + 'tranyear');
//     var item = tools.JsonGet('StaffGetUserPlanYear.do?userplan.tranyear='+tranyear);
//     if(item && item.tranyear){
//        tools.alert(tranyear + '度计划已存在！');
//        Ext.getCmp(mep + 'tranyear').reset();
//     }
//   },
   
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
           'userplan.tranyear':tools.GetValueEncode(mep+'searchtranyear'),
           'userplan.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"tranusera",item.userid);
      tools.SetValue(mep+"tranusernamea",item.username);
      tools.SetValue(mep+"deptida",item.deptid);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'deptid', item.deptid);
    tools.SetValue(mep + 'tranyear', item.tranyear);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'tranremark', item.tranremark);
    tools.SetValue(mep + 'aduituser', item.aduituser);
    tools.SetValue(mep + 'aduitusername', item.aduitusername);
    tools.SetValue(mep + 'aduitdate', item.aduitdate);
    tools.SetValue(mep + 'aduitremark', item.aduitremark);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'checkdate', item.checkdate);
    tools.SetValue(mep + 'checkremark', item.checkremark);
    tools.SetValue(mep + 'modifyserial', item.modifyserial);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var tranusername = tools.UserPicker(mep + 'tranusernamea','plandetail.tranusername','计划建立人');
     
     tranusername.on('griditemclick', me.OnUserSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormDate('培训时间', 'plandetail.traindate', mep + 'traindate', 'Y-m-d', '96%', false, 20,nowdate),
            tranusername
         ]},
         {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormCombo('培训形式', 'plandetail.traintype', mep + 'traintype', tools.ComboStore('TrainType', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
            tools.FormCombo('检测室', 'plandetail.deptid', mep + 'deptida', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
            {xtype:'hiddenfield',name:'plandetail.tranuser',id: mep + 'tranusera'},
            {xtype:'hiddenfield',name:'plandetail.modifyserial',id: mep + 'modifyserialdetail'},
            {xtype:'hiddenfield',name:'plandetail.trainstatus',id: mep + 'trainstatus'},
            {xtype:'hiddenfield',name:'plandetail.tranid',id: mep + 'tranida'},
            {xtype:'hiddenfield',name:'plandetail.relaid',id: mep + 'relaid'}
        ]},
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormText('经费预算(元)', 'plandetail.trainfee', mep + 'trainfee',14 , '100%', false, 21,'is9p2'),
            tools.FormText('拟培训人', 'plandetail.trainer', mep + 'trainer',30 , '100%', false, 21)
         ]}
       ]},
       tools.FormTextArea('总体目标', 'plandetail.traintarget', mep + 'traintarget', 1000, '100%', true, 22,8),
       tools.FormTextArea('原则与要求', 'plandetail.trainobject', mep + 'trainobject', 1000, '100%', true, 23,8),
       tools.FormTextArea('培训内容与方式', 'plandetail.traincontent', mep + 'traincontent', 1000, '100%', true, 24,8),
       tools.FormTextArea('监督与考核', 'plandetail.trainrequest', mep + 'trainrequest', 1000, '100%', true, 25,8),
       tools.FormTextArea('备注', 'plandetail.trainremark', mep + 'trainremark', 100, '100%', true, 26,4)
    ];

     me.disDetailControls = ['deptida'];
     me.enDetailControls = ['trainer','tranusernamea','relaid', 'traindate', 'traintarget', 'trainobject', 'traincontent', 'traintype', 'trainfee', 'trainrequest', 'trainremark', 'trainstatus', 'modifyserialdetail', 'trandate', 'tranuser'];
   },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranida', item.tranid);
    tools.SetValue(mep + 'relaid', item.relaid);
    tools.SetValue(mep + 'tranyear', item.tranyear);
    tools.SetValue(mep + 'trainer', item.trainer);
    tools.SetValue(mep + 'deptida', item.deptid);
    tools.SetValue(mep + 'traindate', item.traindate);
    tools.SetValue(mep + 'traintarget', item.traintarget);
    tools.SetValue(mep + 'trainobject', item.trainobject);
    tools.SetValue(mep + 'traincontent', item.traincontent);
    tools.SetValue(mep + 'traintype', item.traintype);
    tools.SetValue(mep + 'trainfee', item.trainfee);
    tools.SetValue(mep + 'trainrequest', item.trainrequest);
    tools.SetValue(mep + 'trainremark', item.trainremark);
//    tools.SetValue(mep + 'trainstatus', item.trainstatus);
    tools.SetValue(mep + 'modifyserialdetail', item.modifyserial);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'tranusera', item.tranuser);
    tools.SetValue(mep + 'tranusernamea', item.tranusername);

   },
   
   OnListNew: function() {
    var me = this;
    var mep = me.tranPrefix;
    var tranyear = Ext.getCmp(mep + 'tranyear').getValue();
    
    if(Ext.isEmpty(tranyear)){
       tools.alert('请填写年度');
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
     var mep = me.tranPrefix;
     record.data.tranid =  Ext.getCmp(mep + 'tranida').getValue();
     record.data.relaid =  Ext.getCmp(mep + 'relaid').getValue();
     record.data.tranyear = Ext.getCmp(mep + 'tranyear').getValue();
     record.data.trainer = Ext.getCmp(mep + 'trainer').getValue();
     record.data.deptid = Ext.getCmp(mep + 'deptida').getValue();
     record.data.deptname = Ext.getCmp(mep + 'deptida').getDisplayValue();
     record.data.traindate = Ext.getCmp(mep + 'traindate').getValue();
     record.data.traintarget = Ext.getCmp(mep + 'traintarget').getValue();
     record.data.trainobject = Ext.getCmp(mep + 'trainobject').getValue();
     record.data.traincontent = Ext.getCmp(mep + 'traincontent').getValue();
     record.data.traintype = Ext.getCmp(mep + 'traintype').getValue();
     record.data.traintypename = Ext.getCmp(mep + 'traintype').getDisplayValue();
     record.data.trainfee = Ext.getCmp(mep + 'trainfee').getValue();
     record.data.trainrequest = Ext.getCmp(mep + 'trainrequest').getValue();
     record.data.trainremark = Ext.getCmp(mep + 'trainremark').getValue();
     record.data.trainstatus = Ext.getCmp(mep + 'trainstatus').getValue();
//     record.data.trainstatusname = Ext.getCmp(mep + 'trainstatus').getDisplayValue();
     record.data.modifyserial = Ext.getCmp(mep + 'modifyserialdetail').getValue();
     record.data.tranuser = Ext.getCmp(mep + 'tranusera').getValue();
     record.data.tranusername = Ext.getCmp(mep + 'tranusernamea').getValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
     tools.SetValue(mep + 'deptid', gpersist.SELECT_MUST_VALUE);
     
   },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    //初始化状态 未培训
    tools.SetValue(mep + 'trainstatus','01');
    tools.SetValue(mep + 'deptida', '');
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('StaffGetListUserPlanYearDetail.do?plandetail.relaid=') + tools.GetValue(mep + 'tranid');
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
    var count = me.plDetailGrid.store.getCount();
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(count == 0 ){
        tools.alert('请填写培训计划！');
        return;
      }
    }
    return true;
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      
      var traintype = Ext.getCmp(mep+'traintype').getValue();
      if(traintype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择培训形式！');
        return;
      }
      
      var deptid = Ext.getCmp(mep+'deptida').getValue();
    
      if(deptid == gpersist.SELECT_MUST_VALUE ){
          tools.alert('请选择部门机构！');
          return;
      }
      
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        
        me.OnBeforeListSave(record);
        
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      }
      else {
        me.OnBeforeListSave(me.detailRecord);
        
        me.plDetailGrid.getView().refresh();
      }
      
      me.winDetail.hide();
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