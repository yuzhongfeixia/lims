    Ext.define('alms.postexam',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
        editInfo:'上岗资格考核报告',
        winWidth:750,
        winHeight:200,
        hasColumnUrl:true,
        columnUrl:'SysSqlColumn.do?sqlid=p_get_userexamreport',
        storeUrl:'StaffSearchUserExamReport.do',
        saveUrl:'StaffSaveUserExamReport.do',
        expUrl:'StaffSearchUserExamReport.do',
        idPrevNext:'examid',
        hasGridSelect:true,
        hasPage:true,
        hasPrevNext : true,
        hasEdit: true,
        hasDetail: true,
        hasSubmit: true,
        detailTitle: '小组成员',
        urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_userexamgroup',
        urlDetail: 'StaffGetListUserExamGroup.do',
        detailTabs: 3,
        hasDateSearch: true,
        hasDetailEdit: true
      });
      me.callParent(arguments);
   },
   
   // 设备明细
   devGrid: null,
   devStore: null,
   cellEdit: null,
   fileGrid: null,
   fileStore: null,
   plAttDetailGrid: null,
   
   OnInitConfig:function(){
    var  me = this;
    me.devGrid = null;
    me.devStore = null;
    me.cellEdit = null;
    me.fileGrid = null;
    me.fileStore = null;
    me.plAttDetailGrid = null;
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '考核编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchexamid', id: mep + 'searchexamid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '被考核人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchusername', id: mep + 'searchusername', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '部门机构', 70, tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE))
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
     
    var username = tools.UserPicker(mep + 'username','examreport.username','被考核人');
     
    username.on('griditemclick', me.OnUserSelect, me);
    
    var examleadername = tools.UserPicker(mep + 'examleadername','examreport.examleadername','考核组长');
     
    examleadername.on('griditemclick', me.OnExamLeaderSelect, me);
    
    me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('考核编号', 'examreport.examid', mep + 'examid', 20, '96%', false, 1),
              tools.FormCombo('技术职务', 'examreport.userduty', mep + 'userduty', tools.ComboStore('UserDuty', gpersist.SELECT_MUST_VALUE), '96%', false,4),
              tools.FormDate('考核日期', 'examreport.examdate', mep + 'examdate', 'Y-m-d', '96%', false, 7,nowdate)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              username,
              tools.FormCombo('部门机构', 'examreport.deptid', mep + 'deptid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false,5),
              examleadername
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormCombo('岗位', 'examreport.userpost', mep + 'userpost', tools.ComboStore('UserPost', gpersist.SELECT_MUST_VALUE), '100%', false,3),
              tools.FormCombo('考核形式', 'examreport.examtype', mep + 'examtype', tools.ComboStore('ExamType', gpersist.SELECT_MUST_VALUE), '100%', false,6),
              {xtype:'hiddenfield',name:'examreport.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'examreport.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'examreport.userid',id: mep + 'userid'},
              {xtype:'hiddenfield',name:'examreport.examleader',id: mep + 'examleader'},
              {xtype:'hiddenfield',name:'examreport.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('考核内容', 'examreport.examcontent', mep + 'examcontent', 200, '100%', false, 10,5),
         tools.FormTextArea('综合评定', 'examreport.examdesc', mep + 'examdesc', 100, '100%', true, 11,5),
         tools.FormTextArea('考核结论', 'examreport.examresult', mep + 'examresult', 200, '100%', true, 12,5)
     ];
     me.disNews = ['examid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['examid', 'tranuser', 'tranusername', 'trandate' ,'tranyear'];
     me.enNews = ['userid', 'username','deptid', 'userpost', 'userduty', 'examtype', 'examdate', 'examcontent', 'examdesc', 'examleader', 'examleadername', 'examresult', 'examleaderdate', 'techuser',
     'techusername', 'techdesc', 'techdate', 'flowaction', 'flowstatus'];
     me.enEdits = ['userid','username', 'deptid', 'userpost', 'userduty', 'examtype', 'examdate', 'examcontent', 'examdesc', 'examleader', 'examleadername', 'examresult', 'examleaderdate', 'techuser',
     'techusername', 'techdesc', 'techdate', 'flowaction', 'flowstatus'];
     
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"userid",item.userid);
      tools.SetValue(mep+"username",item.username);
      tools.SetValue(mep+"deptid",item.deptid);
      tools.SetValue(mep+"userpost",item.userpost);
    }
   },
   
   OnExamLeaderSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"examleader",item.userid);
      tools.SetValue(mep+"examleadername",item.username);
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
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'examreport.deptid':tools.GetValueEncode(mep+'searchdeptid'),
           'examreport.username':tools.GetValueEncode(mep+'searchusername'),
           'examreport.examid':tools.GetValueEncode(mep+'searchexamid')
         })
       });
     }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'examid', item.examid);
    tools.SetValue(mep + 'userid', item.userid);
    tools.SetValue(mep + 'username', item.username);
    tools.SetValue(mep + 'deptid', item.deptid);
    tools.SetValue(mep + 'userpost', item.userpost);
    tools.SetValue(mep + 'userduty', item.userduty);
    tools.SetValue(mep + 'examtype', item.examtype);
    tools.SetValue(mep + 'examdate', item.examdate);
    tools.SetValue(mep + 'examcontent', item.examcontent);
    tools.SetValue(mep + 'examdesc', item.examdesc);
    tools.SetValue(mep + 'examleader', item.examleader);
    tools.SetValue(mep + 'examleadername', item.examleadername);
    tools.SetValue(mep + 'examresult', item.examresult);
    tools.SetValue(mep + 'examleaderdate', item.examleaderdate);
    tools.SetValue(mep + 'techuser', item.techuser);
    tools.SetValue(mep + 'techusername', item.techusername);
    tools.SetValue(mep + 'techdesc', item.techdesc);
    tools.SetValue(mep + 'techdate', item.techdate);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var username = tools.UserPicker(mep + 'usernamedetail','examgroup.username','考核人');
     
     username.on('griditemclick', me.OnLeaderSelect, me);
    
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            username
         ]},
         {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormCombo('机构部门', 'examgroup.deptid', mep + 'deptiddetail', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 21),
            {xtype:'hiddenfield',name:'examgroup.userid',id: mep + 'useriddetail'}
        ]},
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormCombo('技术职务', 'examgroup.userduty', mep + 'userdutydetail', tools.ComboStore('UserDuty', gpersist.SELECT_MUST_VALUE), '96%', false, 22)
         ]}
       ]}
    ];

     me.disDetailControls = [ 'deptiddetail'];
     me.enDetailControls = ['usernamedetail', 'userdutydetail'];
  },
  
  OnLeaderSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"useriddetail",item.userid);
      tools.SetValue(mep+"usernamedetail",item.username);
      tools.SetValue(mep+"deptiddetail",item.deptid);
    }
   },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'useriddetail', item.userid);
    tools.SetValue(mep + 'usernamedetail', item.username);
    tools.SetValue(mep + 'deptiddetail', item.deptid);
    tools.SetValue(mep + 'userdutydetail', item.userduty);
  },
   
  OnListNew: function() {
    var me = this;
    var mep = me.tranPrefix;
    
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
     record.data.userid =  Ext.getCmp(mep + 'useriddetail').getValue();
     record.data.username =  Ext.getCmp(mep + 'usernamedetail').getValue();
     record.data.deptid = Ext.getCmp(mep + 'deptiddetail').getValue();
     record.data.userduty = Ext.getCmp(mep + 'userdutydetail').getValue();
     record.data.deptname = Ext.getCmp(mep + 'deptiddetail').getDisplayValue();
     record.data.userdutyname = Ext.getCmp(mep + 'userdutydetail').getDisplayValue();
   },
   
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      var deptid = Ext.getCmp(mep+'deptiddetail').getValue();
      var userduty = Ext.getCmp(mep+'userdutydetail').getValue();
      
      if(deptid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择部门！');
        return;
      }
      
      if(userduty == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择技术职务！');
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
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
     
   },
   
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('StaffGetListUserExamGroup.do?examgroup.examid=') + tools.GetValue(mep + 'examid');
        me.plDetailGrid.store.load();
     }
     
     if (me.devGrid && me.devGrid.store) {
        me.devGrid.store.proxy.url = tools.GetUrl('StaffGetListUserExamDev.do?examdev.examid=') + tools.GetValue(mep + 'examid');
        me.devGrid.store.load();
     }
     
     if (me.fileGrid && me.fileGrid.store) {
        me.fileGrid.store.proxy.url = tools.GetUrl('StaffGetListUserExamFile.do?uxf.examid=') + tools.GetValue(mep + 'examid');
        me.fileGrid.store.load();
     }
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;

     var details = [];
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     var devdetails = [];
     for(var i = 0; i < me.devGrid.store.getCount(); i++){
       devdetails.push(me.devGrid.store.getAt(i).data);
     }
     
     var filedetails = [];
     for(var i = 0; i < me.fileGrid.store.getCount(); i++){
       filedetails.push(me.fileGrid.store.getAt(i).data);
     }
     me.saveParams = { details: Ext.encode(details) ,devdetails: Ext.encode(devdetails),filedetails: Ext.encode(filedetails)  };
   },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var deptid = Ext.getCmp(mep+'deptid').getValue();
    var userpost = Ext.getCmp(mep+'userpost').getValue();
    var userduty = Ext.getCmp(mep+'userduty').getValue();
    var examtype = Ext.getCmp(mep+'examtype').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(deptid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择部门机构！');
        return;
      }
      if(userpost == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择岗位！');
        return;
      }
      if(userduty == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择技术职务！');
        return;
      }
      if(examtype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择考核形式！');
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
          tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
          tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
        }
      }
    }
    me.OnDetailRefresh();
  },
  
//  提交后单击gird 按钮判断
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
  
  // 双击grid 按钮判断
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
  
  // 添加明细 中 的设备 及 附件
  OnAfterCreateDetail: function () {
       var me = this, mep = this.tranPrefix;
       
       var devColumn = [];
       var devField = [];    

       tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_userexamdev'), devColumn, devField, mep + '_a_');

       me.devStore = tools.CreateGridStore(tools.GetUrl('StaffGetListUserExamDev.do'), devField);
       me.cellEdit = Ext.create('Ext.grid.plugin.CellEditing', {
          clicksToEdit: 1,
          listeners: {
            beforeedit: function (e, editor) {
              if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
                return false;
            }
          }
       });
       
       Ext.each(devColumn, function (col) {
         if (col.dataIndex == 'devname') {
            var testitems = [
              ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 70, width: 220, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
              ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
            ];
      
            col.editor = {
              xtype: 'gridpicker', name: mep + 'devname', id: mep + 'devname', winTitle: '选择设备',
              maxLength: 30, maxLengthText: '设备长度不能超过30个字符！', selectOnFocus: false,
              blankText: '设备不能为空！', allowBlank: false, editable: false, 
              columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
              storeUrl: 'DevSearchBasDev.do',
              hasPage: true, pickerWidth: 600, pickerHeight: 450, searchTools: testitems,
              listeners: {
                'griditemclick': { fn: me.OnDevSelect, scope: me },
                'gridbeforeload': { fn: me.OnDevBeforeLoad, scope: me }
              }
            };
          }
       });
       
       me.devGrid = Ext.create('Ext.grid.Panel', {
         region : 'center',
         title : '仪器设备',
         autoScroll : true,
         frame : false,
         border : false,
         margins : '0 0 0 0',
         padding : '0 0 0 0',
         loadMask : true,
         columnLines : true,
         viewConfig : {
           autoFill : true,
           stripeRows : true
         },
         columns : devColumn,
         store : me.devStore,
         plugins: [me.cellEdit],
         selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true })
       });
       me.devStore.load();
       me.plDetail.add(me.devGrid);
       me.devitems = [
         ' ', { id : mep + 'btnDevAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewDev, scope : me },
         ' ', { id : mep + 'btnDevDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteDev, scope : me }
       ];
       
       me.OnAfterCreateDetailToolBar();
       
       tools.SetToolbar(me.devitems, mep);
         
       var tbdev = Ext.create('Ext.toolbar.Toolbar', {
         dock : 'top',
         items : me.devitems
       });
       me.devGrid.insertDocked(0, tbdev);
       
       // 附件
       var fileColumn = [];
       var fileField = [];    

       tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_userexamfile'), fileColumn, fileField, mep + '_b_');

       me.fileStore = tools.CreateGridStore(tools.GetUrl(''), fileField);
       
       me.fileGrid = Ext.create('Ext.grid.Panel', {
         region : 'center',
         title : '考核报告',
         autoScroll : true,
         frame : false,
         border : false,
         margins : '0 0 0 0',
         padding : '0 0 0 0',
         loadMask : true,
         columnLines : true,
         viewConfig : {
           autoFill : true,
           stripeRows : true
         },
         columns : fileColumn,
         store : me.fileStore,
         selModel: me.hasDetailGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }) : {},
         listeners : {
             'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
         }   
       });
       me.fileStore.load();
       me.plDetail.add(me.fileGrid);
       me.fileitems = [
         ' ', { id : mep + 'btnFileAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewFile, scope : me },
         ' ', { id : mep + 'btnFileDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteFile, scope : me },
         '-',' ', { id: mep + 'btnDownAll', text: '下载所有文件', iconCls: 'icon-down', handler: me.OnDownZip, scope: me }
       ];
       
       me.OnAfterCreateDetailToolBar();
       
       tools.SetToolbar(me.fileitems, mep);
         
       var tbfile = Ext.create('Ext.toolbar.Toolbar', {
         dock : 'top',
         items : me.fileitems
       });
         
       me.fileGrid.insertDocked(0, tbfile);
   },
   
   OnListNewDev:function(){
    var me = this;
    
    var record = me.devGrid.store.model.create({});
    
    me.devGrid.store.insert(me.devGrid.store.getCount(), record);
    
    if (me.cellEdit) {
      me.cellEdit.startEditByPosition({
        row: me.devGrid.store.getCount() - 1,
        column: 0
      });
    }
   },
   
   OnListDeleteDev : function() {
    var me = this;
    
    var j = me.devGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.devGrid.store.remove(me.devGrid.selModel.selected.items[0]);
    }
    
    me.devGrid.getView().refresh();
  },
  
  OnDevSearch: function () {
    var me = this, mep = me.tranPrefix;
    
    me.OnDevBeforeLoad();
    
    var devname = Ext.getCmp(mep + 'devname');
    
    devname.store.loadPage(1);    
  },
  
  OnDevBeforeLoad: function () {    
    var me = this, mep = me.tranPrefix;
    var devname = Ext.getCmp(mep + 'devname');

    if (devname.store) {      
      devname.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bd.devname': tools.GetValueEncode(mep + 'searchdevname') 
        });
      });
    }
  },
  
  OnDevSelect: function (render, item) {
    var me = this;
    
    if (item && !Ext.isEmpty(item.devname) && me.cellEdit) {
      var now = me.cellEdit.getActiveRecord();

      if (now) {
        me.cellEdit.getActiveEditor().setValue(item.devname);
        
        now.data.devid = item.devid;
        
        me.devGrid.getView().refresh();
      }
    }
  },
  
  // 页面空间授权处理
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDevAdd','btnDevDelete','btnFileAdd','btnFileDelete','btnDetailDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDevAdd','btnDevDelete','btnFileAdd','btnFileDelete','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDevAdd','btnDevDelete','btnFileAdd','btnFileDelete','btnDetailDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnCreateDetailWinFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveFile, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEditFile();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
      height : '40%',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    var upload = tools.SwfUpload();
    me.plAttDetailGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'attdetailgrid',
      region : 'center',
      title:'上传文件',
      columnWidth:1,
      scope: me,
      items: [upload]    
    });
    
    upload.on('showdetail',me.OnShowDetail,me);
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: '文件',
      width: 600,
      height: 500,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: false,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit,me.plAttDetailGrid]
    });
    
  },
  
  OnListNewFile : function(){
    var me = this;
    me.OnCreateDetailWinFile();
    me.OnInitDetailDataFile();
    if(me.winDetail){      
      me.winDetail.show();
    }
  },
  
  OnListSelectFile: function (view, record) {
    alms.PopupFileShow('考核报告', 'FileDownFile.do', record.data.fileurl, record.data.filename);
  },
  
  OnDownZip: function () {
    var me = this, mep = me.tranPrefix;
    
    var fileurls = [];
    
    for (i = 0; i < me.fileGrid.store.getCount(); i++) {
      fileurls.push(me.fileGrid.store.getAt(i).data.filename + ':' +me.fileGrid.store.getAt(i).data.fileurl);
    }
    
    if (fileurls.length <= 0) {
      tools.alert('没有需要下载的文件！');
      return;      
    }
    
    var filename = tools.GetValue(mep + 'username') + "_考核报告";
    var iframe = document.getElementById('export');
    var plExport = Ext.getCmp('plexport');
    
    plExport.form.submit({
      url: 'FileDownZipFile.do',
      params: { filename: filename, fileurl: fileurls.join() },
      target: 'export'
    });
  },
  
  OnBeforeCreateDetailEditFile: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('附件类别', 'bbd.filecate', mep + 'filecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('附件类型', 'bbd.filetype', mep + 'filetype', tools.ComboStore('filetype', gpersist.SELECT_MUST_VALUE), '100%', false, 2)
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'bbd.filename', id: mep + 'filename' },
      { xtype: 'hiddenfield', name: 'bbd.fileurl', id: mep + 'fileurl' },
      { xtype: 'hiddenfield', name: 'bbd.fileno', id: mep + 'fileno' },
      tools.FormTextArea('备注', 'bbd.fileremark', mep + 'fileremark', 200, '100%', true, 3, 7)
      
    ]
    me.disDetailControls = [];
    me.enDetailControls = ['filechtype', 'filename', 'fileurl', 'fileno', 'filecate', 'fileremark'];  
  },
  
  OnListSaveFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    var filetype = Ext.getCmp(mep + 'filetype').getValue();
    var filecate = Ext.getCmp(mep + 'filecate').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(filetype == gpersist.SELECT_MUST_VALUE || filecate == gpersist.SELECT_MUST_VALUE){
        tools.alert('请选择附件类型或附件类别');
      }else{
        if(filename == "" || filename == null || filename == undefined){
          tools.alert('请上传附件！');
          return;
        }else{
          if (me.detailEditType == 1) {
            //可能有多个附件的情况
            var filenames = filename.split(",");
            var fileurls = fileurl.split(",");
            for(i = 0; i <filenames.length; i++){
              var record = me.fileGrid.store.model.create({});
              record.data.filename = filenames[i];
              record.data.filetype = Ext.getCmp(mep + 'filetype').getValue();
              record.data.filetypename = Ext.getCmp(mep + 'filetype').rawValue;
              record.data.fileurl = fileurls[i];
              record.data.filecatename = Ext.getCmp(mep + 'filecate').rawValue;
              record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
              record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
              record.data.fileno = i + parseInt(Ext.getCmp(mep + 'fileno').getValue());
              me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);      
            }
          }
          else {
            me.OnBeforeListSaveFile(me.detailRecord);
            me.fileGrid.getView().refresh();
          }
        }
        me.winDetail.hide();
      }
    }
  },

  OnShowDetail:function(render, item){
    var me = this;
    var mep = this.tranPrefix;
    
    var filename = Ext.getCmp(mep+'filename').getValue();
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    
    if(item){
      if(filename == ""){
        filename = item.name;
      }else{
        filename = filename+','+item.name
      }
      if(fileurl == ""){
        fileurl = item.url;
      }else{
        fileurl = fileurl+','+item.url;
      }
      
      tools.SetValue(mep + 'filename',filename);
      tools.SetValue(mep + 'fileurl',fileurl);
    };
  },
  
  OnListDeleteFile:function(){
    var me = this;
    var j = me.fileGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
//      var fileurl = me.fileGrid.selModel.selected.items[0].data.fileurl;
//      fileurl = tools.GetEncode(tools.GetEncode(fileurl));
//      tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
    }
    
    me.fileGrid.getView().refresh();
  },
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    fileurl = tools.GetEncode(tools.GetEncode(fileurl));
    tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
    me.winDetail.hide();
    me.detailEditType = 1;
  },
  
  OnInitDetailDataFile: function () {
    var me = this;
    var mep = me.tranPrefix;   
    var serial = me.fileGrid.store.getCount() + 1;
    tools.SetValue(mep + 'fileno', serial);
    tools.SetValue(mep + 'filetype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'filecate', gpersist.SELECT_MUST_VALUE);
  },

  OnBeforeListSaveFile : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.filetypename = Ext.getCmp(mep + 'filetype').getDisplayValue();
    record.data.filetype = Ext.getCmp(mep + 'filetype').getValue();
    record.data.filename = Ext.getCmp(mep + 'filename').getValue();
    record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
    record.data.filechurl = Ext.getCmp(mep + 'fileurl').getValue();
    record.data.filechno = Ext.getCmp(mep + 'fileno').getValue();
    record.data.filecatename = Ext.getCmp(mep + 'filecate').getDisplayValue();
    record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
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