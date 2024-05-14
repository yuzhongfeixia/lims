 Ext.define('alms.workexam',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'人员岗位考核',
          winWidth:750,
          winHeight:200,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_userexamrecord',
          storeUrl:'StaffSearchUserExamRecord.do',
          saveUrl:'StaffSaveUserExamRecord.do',
          expUrl:'StaffSearchUserExamRecord.do',
          idPrevNext:'examid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '考核明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_userexamdetail',
          urlDetail: 'StaffGetListUserExamDetail.do',
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
       ' ', { xtype: 'textfield', fieldLabel: '考核编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchexamid', id: mep + 'searchexamid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '考核人编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchuserid', id: mep + 'searchuserid', allowBlank: true }
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
     
     var username = tools.UserPicker(mep + 'username','examrecord.username','被考核人');
     
     username.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('考核编号', 'examrecord.examid', mep + 'examid', 20, '97%', false, 1),
              tools.FormText('人员岗位', 'examrecord.userpostname', mep + 'userpostname', 20, '97%', false, 2)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              username,
              tools.FormDate('考核日期', 'examrecord.examdate', mep + 'examdate', 'Y-m-d', '96%', false, 3,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('所属部门', 'examrecord.deptname', mep + 'deptname', 20, '100%', false, 4,'',90),
              tools.FormCombo('综合考核意见', 'examrecord.examadvice', mep + 'examadvice', tools.ComboStore('ExamAdvice', gpersist.SELECT_MUST_VALUE), '100%', false, 7,90),
//              {xtype:'hiddenfield',name:'examrecord.flowstatus',id: mep + 'flowstatus'},
//              {xtype:'hiddenfield',name:'examrecord.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'examrecord.userid',id: mep + 'userid'},
              {xtype:'hiddenfield',name:'examrecord.deptid',id: mep + 'deptid'},
              {xtype:'hiddenfield',name:'examrecord.userpost',id: mep + 'userpost'},
              {xtype:'hiddenfield',name:'examrecord.approveuser',id: mep + 'approveuser'},
              {xtype:'hiddenfield',name:'examrecord.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('考核内容', 'examrecord.examcontent', mep + 'examcontent', 200, '100%', true, 5,4),
        tools.FormTextArea('需要整改的问题', 'examrecord.examdesc', mep + 'examdesc', 100, '100%', true, 7,4)
     ];
     me.disNews = ['examid', 'deptname', 'userpostname','approvedate','approveusername'];
     me.disEdits = ['examid', 'deptname', 'userpostname','approvedate','approveusername'];
     me.enNews = ['username', 'examdate', 'examcontent', 'examadvice', 'examdesc'],
     me.enEdits = [ 'username', 'examdate', 'examcontent', 'examadvice', 'examdesc'];
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
           'examrecord.examid':tools.GetValueEncode(mep+'searchexamid'),
           'examrecord.userid':tools.GetValueEncode(mep+'searchuserid')
         })
       });
     }
   },
   
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"userid",item.userid);
      tools.SetValue(mep+"username",item.username);
      tools.SetValue(mep+"userpost",item.userpost);
      tools.SetValue(mep+"deptid",item.deptid);
      tools.SetValue(mep+"userpostname",item.userpostname);
      tools.SetValue(mep+"deptname",item.deptname);
    }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'examid', item.examid);
    tools.SetValue(mep + 'userid', item.userid);
    tools.SetValue(mep + 'deptid', item.deptid);
    tools.SetValue(mep + 'userpost', item.userpost);
    tools.SetValue(mep + 'examdate', item.examdate);
    tools.SetValue(mep + 'examcontent', item.examcontent);
    tools.SetValue(mep + 'examadvice', item.examadvice);
    tools.SetValue(mep + 'examdesc', item.examdesc);
    tools.SetValue(mep + 'approveuser', item.approveuser);
    tools.SetValue(mep + 'approveusername', item.approveusername);
    tools.SetValue(mep + 'approvedate', item.approvedate);
    tools.SetValue(mep + 'teamuser', item.teamuser);
    tools.SetValue(mep + 'teamusername', item.teamusername);
    tools.SetValue(mep + 'teamdate', item.teamdate);
    tools.SetValue(mep + 'monitoruser', item.monitoruser);
    tools.SetValue(mep + 'monitorusername', item.monitorusername);
    tools.SetValue(mep + 'monitordate', item.monitordate);
    tools.SetValue(mep + 'username', item.username);
    tools.SetValue(mep + 'deptname', item.deptname);
    tools.SetValue(mep + 'userpostname', item.userpostname);
    me.OnDetailRefresh();
    return true;
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'examadvice', gpersist.SELECT_MUST_VALUE);
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var examItem = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '考核项目', name: 'examdetail.examitemname', id: mep + 'examitemname', winTitle: '选择考核项目',
      maxLength: 20, maxLengthText: '考核项目不能超过00字！', selectOnFocus: false, labelWidth: 80,
      blankText: '考核项目为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userexamitem',
      storeUrl: 'StaffSearchUserExamItem.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     examItem.on('griditemclick', me.OnItemSelect, me);
     examItem.on('gridbeforeload', me.OnItemLoad, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            examItem
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormCombo('考核成绩', 'examdetail.examscore', mep + 'examscore', tools.ComboStore('ExamScore', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
            {xtype:'hiddenfield',name:'examdetail.examitem',id: mep + 'examitem'}
        ]}
       ]},      
       tools.FormTextArea('备注', 'examdetail.examitemdesc', mep + 'examitemdesc', 100, '98%', true, 6,4)
    ];

     me.disDetailControls = [];
     me.enDetailControls = ['examitem', 'examscore', 'examitemdesc','examitemname'];
  },
  
  OnItemSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.examitem){
        tools.SetValue(mep+"examitem",item.examitem);
        tools.SetValue(mep+"examitemname",item.examitemname);
     }
  },
  
  OnItemLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var me = this;
    var mep = me.tranPrefix;
    var examitemname = Ext.getCmp(mep + 'examitemname');
    examitemname.store.load({ callback: function(records, options, success){
      if(success==true){
        for(var i = 0; i<examitemname.store.getCount(); i++){
             for(var j = 0; j<me.plDetailGrid.store.getCount(); j++){
                 if(me.plDetailGrid.store.getAt(j).get('examitem') == examitemname.store.getAt(i).get('examitem')){
                   examitemname.store.remove(examitemname.store.getAt(i));
                   break;
                 }
             }
         }
       }
      }
   });
  },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'examid', item.examid);
    tools.SetValue(mep + 'examitemname', item.examitemname);
    tools.SetValue(mep + 'examitem', item.examitem);
    tools.SetValue(mep + 'examscore', item.examscore);
    tools.SetValue(mep + 'examitemdesc', item.examitemdesc);
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
     record.data.examid =  Ext.getCmp(mep + 'examid').getValue();
     record.data.examitem = Ext.getCmp(mep + 'examitem').getValue();
     record.data.examitemname = Ext.getCmp(mep + 'examitemname').getValue();
     record.data.examscore = Ext.getCmp(mep + 'examscore').getValue();
     record.data.examscorename = Ext.getCmp(mep + 'examscore').getDisplayValue();
     record.data.examitemdesc = Ext.getCmp(mep + 'examitemdesc').getValue();
   },
   
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      var examscore = Ext.getCmp(mep+'examscore').getValue();
      if(examscore == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择考核成绩！');
        return;
      }
      
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        
        var examitem = Ext.getCmp(mep+'examitem').getValue();
        for(var i=0;i<me.plDetailGrid.store.getCount();i++){
          if(me.plDetailGrid.store.getAt(i).get('examitem') ==examitem){
            tools.alert('该考核项目已存在');
            return;
          }
        }
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
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'examscore', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'examitem', gpersist.SELECT_MUST_VALUE);
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('StaffGetListUserExamDetail.do?examdetail.examid=') + tools.GetValue(mep + 'examid');
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
    var examadvice = Ext.getCmp(mep+'examadvice').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(examadvice == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择综合考核意见！');
        return;
      }
    }
    return true;
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'examid').reset();
    Ext.getCmp(mep + 'examid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.examid) {
          tools.SetValue(mep + 'examid', action.result.data.examid);
//          tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
//          tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
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