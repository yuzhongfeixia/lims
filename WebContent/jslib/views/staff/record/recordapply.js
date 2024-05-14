    Ext.define('alms.recordapply',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'人员培训记录',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_usertrain',
          storeUrl:'StaffSearchUserTrain.do',
          saveUrl:'StaffSaveUserTrain.do',
          expUrl:'StaffSearchUserTrain.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
//          hasDetail: true,
          hasSubmit: true
//          detailTitle: '培训计划',
//          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_trainyeardetail',
//          urlDetail: 'StaffGetListtrainYearDetail.do',
//          detailTabs: 1,
//          hasDateSearch: true,
//          hasDetailEdit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '培训项目', labelWidth: 60, width: 250, maxLength: 40, name: 'searchtraincontent', id: mep + 'searchtraincontent', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchtraintype', mep + 'searchtraintype', 210, '培训形式', 70, tools.ComboStore('TrainType', gpersist.SELECT_MUST_VALUE))
     ];
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        //'-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
     
     var trainitems = [
     ' ', { xtype: 'textfield', fieldLabel: '培训编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchplantrainid', id: mep + 'searchplantrainid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '培训项目', labelWidth: 70, width: 200, maxLength: 40, name: 'searchplantraincontent', id: mep + 'searchplantraincontent', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTrainSearch, scope: me }
     ];
     
//     var train = Ext.create('Ext.ux.GridPicker', {
//      fieldLabel: '培训编号', name: 'train.relaid', id: mep + 'relaid', winTitle: '培训编号',
//      maxLength: 20, maxLengthText: '培训编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
//      blankText: '培训编号为空！', allowBlank: false, anchor: '97%', tabIndex: 4,
//      columnUrl: 'SysSqlColumn.do?sqlid=P_GET_USERPLANYEARDETAIL',
//      storeUrl: 'StaffSearchUserPlanYearDetail.do',
//      editable:false,
//      searchTools:trainitems,
//      hasPage: true, pickerWidth: 600, pickerHeight: 500
//     });
//    
//      train.on('griditemclick', me.OnTrainSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'train.tranid', mep + 'tranid', 20, '97%', false, 1),
              tools.FormText('业务员', 'train.tranusername', mep + 'tranusername', 20, '97%', false, 2)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//              train,
              tools.FormDate('培训日期', 'train.begindate', mep + 'begindate', 'Y-m-d', '97%', false, 3,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormCombo('培训形式', 'train.traintype', mep + 'traintype', tools.ComboStore('TrainType', gpersist.SELECT_MUST_VALUE), '100%', false, 7),
              tools.FormDate('记录日期', 'train.trandate', mep + 'trandate', 'Y-m-d', '100%', false, 3,nowdate),
              {xtype:'hiddenfield',name:'train.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'train.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'train.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'train.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('培训目标', 'train.traintarget', mep + 'traintarget', 100, '100%', true, 5,8),
        tools.FormTextArea('培训对象', 'train.trainobject', mep + 'trainobject', 100, '100%', true, 6,8),
        tools.FormTextArea('培训项目', 'train.traincontent', mep + 'traincontent', 50, '100%', true, 7,8),
        tools.FormTextArea('实施情况', 'train.trainresult', mep + 'trainresult', 50, '100%', true, 8,8),
        tools.FormTextArea('教员', 'train.trainteacher', mep + 'trainteacher', 50, '100%', true, 9,3),
        tools.FormTextArea('考核', 'train.trainexam', mep + 'trainexam', 50, '100%', true, 10,3),
        tools.FormTextArea('主持人', 'train.traintaker', mep + 'traintaker', 50, '100%', true, 11,3),
        tools.FormCheckCombo('参与培训员工', 'users', mep + 'users', tools.ComboStore('User'), '100%', false, 12)
     ];
     me.disNews = ['tranid', 'tranuser','tranusername','trandate'];
     me.disEdits = ['tranid', 'tranuser','tranusername','trandate'];
     me.enNews = [ 'relaid', 'begindate', 'trainresult', 'trainteacher', 'trainexam', 'traintaker','users', 'traintarget', 'trainobject','traintype',  'traincontent'],
     me.enEdits = [ 'begindate', 'traintype', 'trainresult', 'trainteacher', 'trainexam', 'traintaker','users', 'traintarget', 'trainobject', 'traincontent'];
   },
   
   OnTrainSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnTrainBeforeLoad();
      var relaid = Ext.getCmp(mep + 'relaid');
      relaid.store.loadPage(1);
   },
   
   OnTrainBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var relaid = Ext.getCmp(mep + 'relaid');
      if(relaid.store){
        relaid.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'plandetail.tranid':tools.GetValueEncode(mep + 'searchplantrainid'),
              'plandetail.traincontent':tools.GetValueEncode(mep + 'searchplantraincontent')
            });
        });
      }
   },
   
//   OnTrainSelect:function(render,item){
//     var me = this;
//     var mep = me.tranPrefix;
//     if(item && item.tranid){
//        tools.SetValue(mep+"relaid",item.tranid);
//        tools.SetValue(mep+"traintarget",item.traintarget);
//        tools.SetValue(mep+"trainobject",item.trainobject);
//        tools.SetValue(mep+"traincontent",item.traincontent);
//        tools.SetValue(mep+"traintype",item.traintype);
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
           'train.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'train.traincontent':tools.GetValueEncode(mep+'searchtraincontent'),
           'train.traintype':tools.GetValueEncode(mep+'searchtraintype')
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
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'relaid', item.relaid);
    tools.SetValue(mep + 'begindate', item.begindate);
    tools.SetValue(mep + 'traintarget', item.traintarget);
    tools.SetValue(mep + 'trainobject', item.trainobject);
    tools.SetValue(mep + 'traincontent', item.traincontent);
    tools.SetValue(mep + 'traintype', item.traintype);
    tools.SetValue(mep + 'trainresult', item.trainresult);
    tools.SetValue(mep + 'trainteacher', item.trainteacher);
    tools.SetValue(mep + 'trainexam', item.trainexam);
    tools.SetValue(mep + 'traintaker', item.traintaker);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'aduituser', item.aduituser);
    tools.SetValue(mep + 'aduitusername', item.aduitusername);
    tools.SetValue(mep + 'aduitdate', item.aduitdate);
    tools.SetValue(mep + 'aduitremark', item.aduitremark);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'checkdate', item.checkdate);
    tools.SetValue(mep + 'checkremark', item.checkremark);
    var users = tools.JsonGet(tools.GetUrl('StaffGetListUserTrainDetail.do?traindetail.tranid=')+item.tranid).data;
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
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
     
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
    var traintype = Ext.getCmp(mep+'traintype').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(traintype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择培训形式！');
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