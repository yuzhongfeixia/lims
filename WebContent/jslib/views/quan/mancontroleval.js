Ext.define('alms.mancontroleval',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'质量控制评价',
          winWidth:750,
          winHeight:300,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quancontroleval',
          storeUrl:'QuanSearchQuanControlEval.do',
          saveUrl:'QuanSaveQuanControlEval.do',
          expUrl:'QuanSearchQuanControlEval.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '样品',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_quancontrolsamp',
          urlDetail: 'QuanGetListQuanControlEval.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '项目名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprojname', id: mep + 'searchprojname', allowBlank: true }
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
     
     var evalusername = tools.UserPicker(mep + 'evalusername','qce.evalusername','评价人员');
     
     evalusername.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'qce.tranid', mep + 'tranid', 20, '97%', false, 1),
              tools.FormDate('完成时间', 'qce.finishdate', mep + 'finishdate', 'Y-m-d', '97%', false,6,nowdate)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('项目名称', 'qce.projname', mep + 'projname', 20, '96%', false, 2)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('项目类别', 'qce.projtype', mep + 'projtype', 10, '100%', false, 3),
              {xtype:'hiddenfield',name:'qce.evaluser',id: mep + 'evaluser'},
              {xtype:'hiddenfield',name:'qce.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'qce.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'qce.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'qce.deal.action',id: mep + 'datadeal'}
          ]}
       ]},
       tools.FormCheckCombo('参加成员', 'users', mep + 'users', tools.ComboStore('User'), '100%', false, 4),
       tools.FormTextArea('备注', 'qce.remark', mep + 'remark', 200, '100%', true,9,3)
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.enNews = ['users','projname', 'projtype', 'finishdate', 'flowaction', 'flowstatus', 'evalation', 'evaluser', 'evalusername', 'evaldate', 'directoradv', 'directoruser', 'directorusername', 'directordate', 'remark'];
     me.enEdits = ['users', 'projname', 'projtype', 'finishdate', 'flowaction', 'flowstatus', 'evalation', 'evaluser', 'evalusername', 'evaldate', 'directoradv', 'directoruser', 'directorusername', 'directordate', 'remark'];
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'qce.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'qce.projname':tools.GetValueEncode(mep+'searchprojname')
         })
       });
     }
   },
   
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"evaluser",item.userid);
      tools.SetValue(mep+"evalusername",item.username);
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
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'projname', item.projname);
    tools.SetValue(mep + 'projtype', item.projtype);
    tools.SetValue(mep + 'finishdate', item.finishdate);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'evalation', item.evalation);
    tools.SetValue(mep + 'evaluser', item.evaluser);
    tools.SetValue(mep + 'evalusername', item.evalusername);
    tools.SetValue(mep + 'evaldate', item.evaldate);
    tools.SetValue(mep + 'directoradv', item.directoradv);
    tools.SetValue(mep + 'directoruser', item.directoruser);
    tools.SetValue(mep + 'directorusername', item.directorusername);
    tools.SetValue(mep + 'directordate', item.directordate);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    var users = tools.JsonGet(tools.GetUrl('QuanGetListQuanControlUser.do?qcu.tranid=')+item.tranid).data;
    var user = [];
    for(var i = 0; i < users.length; i++){
      user.push(users[i].userid);
    }
    tools.SetValue(mep + 'users', user);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var sampitems = [
     ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsample', id: mep + 'searchsample', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
     ];
     
     var samplename = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品编号', name: 'detail.sampleid', id: mep + 'sampleid', winTitle: '样品编号',
      maxLength: 14, maxLengthText: '样品名称不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品编号！', allowBlank: false, anchor: '97%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     samplename.on('griditemclick', me.OnSampleSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            samplename,
            tools.FormText('样品状态', 'detail.samplestatus', mep + 'samplestatus', 20, '97%', false, 3)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText('样品名称', 'detail.samplename', mep + 'samplename', 20, '100%', false, 3),
           tools.FormText('样品来源', 'detail.samplesource', mep + 'samplesource', 20, '100%', false, 3)
        ]}
       ]},   
       tools.FormTextArea('使用标准或方法', 'detail.usestdmethod', mep + 'usestdmethod', 200, '100%', false, 6,4),
       tools.FormTextArea('测试/试验结果', 'detail.testresult', mep + 'testresult', 200, '100%', false, 6,8)
    ];

     me.disDetailControls = ['samplename'];
     me.enDetailControls = ['sampleid', 'samplestatus', 'samplesource', 'usestdmethod', 'testresult'];
  },
   
  OnSampleSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnSampleBeforeLoad();
      var samplename = Ext.getCmp(mep + 'sampleid');
      samplename.store.loadPage(1);
   },
   
  OnSampleBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var samplename = Ext.getCmp(mep + 'sampleid');
      if(samplename.store){
        samplename.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'bsample.sampleid':tools.GetValueEncode(mep + 'searchsampleid'),
              'bsample.samplename':tools.GetValueEncode(mep + 'searchsample')
            });
        });
      }
   },
   
  OnSampleSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.sampleid){
        tools.SetValue(mep+"samplename",item.samplename);
        tools.SetValue(mep+"sampleid",item.sampleid);
     }
   },
   
   OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'sampleid', item.sampleid);
    tools.SetValue(mep + 'samplename', item.samplename);
    tools.SetValue(mep + 'samplestatus', item.samplestatus);
    tools.SetValue(mep + 'samplesource', item.samplesource);
    tools.SetValue(mep + 'usestdmethod', item.usestdmethod);
    tools.SetValue(mep + 'testresult', item.testresult);

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
     record.data.sampleid = Ext.getCmp(mep + 'sampleid').getValue();
     record.data.samplename = Ext.getCmp(mep + 'samplename').getValue();
     record.data.samplestatus = Ext.getCmp(mep + 'samplestatus').getValue();
     record.data.samplesource = Ext.getCmp(mep + 'samplesource').getValue();
     record.data.usestdmethod = Ext.getCmp(mep + 'usestdmethod').getValue();
     record.data.testresult = Ext.getCmp(mep + 'testresult').getValue();
  },
   
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
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
    };
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
        me.plDetailGrid.store.proxy.url = tools.GetUrl('QuanGetListQuanControlSamp.do?qcs.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     var details = [];
     var users = [];
     var partusers = Ext.getCmp(mep + 'users').getValue().split(',');
     
     if(partusers.length > 0){
        Ext.each(partusers, function (item, index) { 
          if(!Ext.isEmpty(item)){
             users.push({userid: item});
          }
        });
     }
     
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     
     me.saveParams = { userdetail: Ext.encode(users), details: Ext.encode(details)};
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