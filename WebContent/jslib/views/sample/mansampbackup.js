Ext.define('alms.mansampbackup',{
  extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'备份样品拆封现场记录',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_samplebackup',
          storeUrl:'SampSearchSampleBackup.do',
          saveUrl:'SampSaveSampleBackup.do',
          expUrl:'SampSearchSampleBackup.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasSubmit: true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true }
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
     
     var comitems = [
     ' ', { xtype: 'textfield', fieldLabel: '处理编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnComplaintSearch, scope: me }
     ];
     
     var complaint = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '投诉编号', name: 'backup.complaintid', id: mep + 'complaintid', winTitle: '投诉编号',
      maxLength: 20, maxLengthText: '投诉编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '投诉编号！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=P_GET_CRMRECEPTDEAL',
      storeUrl: 'CrmSearchCrmReceptDealForComplaint.do',
      editable:false,
      searchTools:comitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     complaint.on('griditemclick', me.OnComplaintSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'backup.tranid', mep + 'tranid', 20, '96%', false, 1),
              complaint
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('样品编号', 'backup.sampletran', mep + 'sampletran', 20, '96%', false, 1),
              tools.FormCombo('备样状态', 'backup.backupstatus', mep + 'backupstatus', tools.ComboStore('BackupStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 3)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('样品名称', 'backup.samplename', mep + 'samplename', 20, '100%', false, 2),
              {xtype:'hiddenfield',name:'backup.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'backup.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'backup.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('申领原因', 'backup.backupreason', mep + 'backupreason', 200, '100%', true, 4,10)
     ];
     me.disNews = ['tranid'];
     me.disEdits = ['tranid'];
     me.enNews = [ 'sampletran','samplename','complaintid', 'backupstatus', 'backupreason', 'flowaction', 'flowstatus', 'qualityuser', 'qualityusername', 'qualitydate', 'backupbefore', 
     'certuser', 'certusername', 'certdate', 'backuprecord', 'sealuser', 'sealusername', 'sealdate', 'backupdate'],
     me.enEdits = [ 'sampletran', 'samplename','complaintid','backupstatus', 'backupreason', 'flowaction', 'flowstatus', 'qualityuser', 'qualityusername', 'qualitydate', 'backupbefore', 
     'certuser', 'certusername', 'certdate', 'backuprecord', 'sealuser', 'sealusername', 'sealdate', 'backupdate'];
   },
   
  OnComplaintSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(render.id,item.tranid);
     }
  },
   
  OnComplaintSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnComplaintBeforeLoad();
      var complaintid = Ext.getCmp(mep + 'complaintid');
      complaintid.store.loadPage(1);
   },
   
  OnComplaintBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var complaintid = Ext.getCmp(mep + 'complaintid');
      if(complaintid.store){
        complaintid.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
             'receptdeal.tranid':tools.GetValueEncode(mep+'searchtranid')
            });
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
           'backup.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'backup.samplename':tools.GetValueEncode(mep+'searchsamplename')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'sampletran', item.sampletran);
    tools.SetValue(mep + 'complaintid', item.complaintid);
    tools.SetValue(mep + 'backupstatus', item.backupstatus);
    tools.SetValue(mep + 'backupreason', item.backupreason);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'qualityuser', item.qualityuser);
    tools.SetValue(mep + 'qualityusername', item.qualityusername);
    tools.SetValue(mep + 'qualitydate', item.qualitydate);
    tools.SetValue(mep + 'backupbefore', item.backupbefore);
    tools.SetValue(mep + 'certuser', item.certuser);
    tools.SetValue(mep + 'certusername', item.certusername);
    tools.SetValue(mep + 'certdate', item.certdate);
    tools.SetValue(mep + 'backuprecord', item.backuprecord);
    tools.SetValue(mep + 'sealuser', item.sealuser);
    tools.SetValue(mep + 'sealusername', item.sealusername);
    tools.SetValue(mep + 'sealdate', item.sealdate);
    tools.SetValue(mep + 'backupdate', item.backupdate);
    tools.SetValue(mep + 'samplename',item.samplename);
    return true;
   },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'dealuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'dealusername', gpersist.UserInfo.user.username );
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var backupstatus = Ext.getCmp(mep+'backupstatus').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(backupstatus == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择备样状态！');
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