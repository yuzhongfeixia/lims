Ext.define('alms.stdmethoddevi', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '检测方法偏离确认',
      winWidth: 750,
      winHeight: 430,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stddevi',
      storeUrl: 'StdSearchStdMethodDevi.do',
      saveUrl: 'StdSaveStdMethodDevi.do',
      hasGridSelect: true,
      expUrl: 'StdSearchStdMethodDevi.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasSubmit: true,
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusTaskAttach.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '图谱明细',
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      detailTabs: 2
    });
    me.callParent(arguments);
  },
  
  fileGrid:null,
  fileStore:null,
  fileDetail:null,
  params:null,
  
  OnInitConfig:function(){
    var me = this;
    me.fileGrid = null;
    me.fileStore = null;
    me.fileDetail = null;
    me.params = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var propuser = tools.UserPicker(mep + 'propuser','stddevi.propuser','提出人');
    
    propuser.on('griditemclick', me.OnPropUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'stddevi.tranid', mep + 'tranid', 20, '96%', false, 1),
          propuser,
          tools.FormDate('提议日期', 'stddevi.propdate', mep + 'propdate', 'Y-m-d', '96%', false, 5)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('检测方法名称', 'stddevi.methodname', mep + 'methodname', 30, '100%', false, 2,'',90),
          tools.FormText('提出人姓名', 'stddevi.propusername', mep + 'propusername', 30, '100%', false, 2,'',90),
          {xtype:'hiddenfield',name:'stddevi.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('方法偏离情况', 'stddevi.methodsource', mep + 'methodsource', 100, '100%', true, 9, 4),
      tools.FormTextArea('理由', 'stddevi.reason', mep + 'reason', 100, '100%', true, 9, 4),
      tools.FormTextArea('试验记录', 'stddevi.trialrecord', mep + 'trialrecord', 100, '100%', true, 9, 4),
      tools.FormTextArea('备注', 'stddevi.remark', mep + 'remark', 100, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid','propusername'];
    me.disEdits = ['tranid','propusername'];
    me.enNews = [ 'methodname', 'methodsource', 'reason', 'trialrecord', 'remark','propuser','propdate'];
    me.enEdits = [ 'methodname', 'methodsource', 'reason', 'trialrecord', 'remark','propuser','propdate'];
    
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 90, width: 180, maxLength: 40, name: 'searchmethodname', id: mep + 'searchmethodname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'stddevi.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'stddevi.methodname': tools.GetEncode(tools.GetValue(mep + 'searchmethodname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      var record = tools.JsonGet(tools.GetUrl('StdGetStdMethodDevi.do?stddevi.tranid=') + item.tranid);
      me.html =  me.OnCreateStdMethodHtml(record);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'methodname', item.methodname);
      tools.SetValue(mep + 'methodsource', item.methodsource);
      tools.SetValue(mep + 'reason', item.reason);
      tools.SetValue(mep + 'trialrecord', item.trialrecord);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'propusername', record.propusername);
      tools.SetValue(mep + 'propuser', record.propuser);
      tools.SetValue(mep + 'propdate', record.propdate);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
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
        }
      }
    }
  },
  
  OnPropUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'propuser',item.userid);
      tools.SetValue(mep+'propusername',item.username);
   }
  },
   
//  OnAfterCreateEdit:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    me.plEdit = Ext.create('Ext.form.Panel', {
//      id : mep + 'pledit',
//      header : false,
//      height:'100%',
//      autoScroll: true,
//      region : 'north',
//      fieldDefaults : {
//        labelSeparator : '：',
//        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
//        labelPad : 0,
//        labelStyle : 'font-weight:bold;'
//      },
//      frame : true,
//      title : '',
//      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
//      defaultType : 'textfield',
//      closable : false,
//      header : false,
//      unstyled : true,
//      scope : me,
//      tbar : me.tbEdit,
//      items : me.editControls
//    });
//  },
  
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
        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },                                                               
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
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
  },
  OnAfterCreateDetailToolBar:function(){
	   var me = this;
	   var mep = me.tranPrefix;

	   me.deitems = [
	     ' ', { id : mep + 'btnDetailAdds', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
	     ' ', { id : mep + 'btnDetailDeletes', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me }
	   ];
	   
},
OnDetailRefresh: function () {
  var me = this;
  var mep = me.tranPrefix;

  if (me.plDetailGrid && me.plDetailGrid.store) {
    me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'tranid');
    me.plDetailGrid.store.load();
  };

  if (me.fileGrid && me.fileGrid.store) {
    me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusRecordFile.do?brf.tranid=') + tools.GetValue(mep + 'tranid');
    me.fileGrid.store.load();
 }
},
OnBeforeCreateDetailEdit: function () {
   var me = this;
   var mep = this.tranPrefix;
   
   me.OnInitGridToolBar();
   me.editDetailControls = [
     { xtype: 'hiddenfield', name: 'attachtype', id: mep + 'attachtype' },
     { xtype: 'hiddenfield', name: 'attachurl', id: mep + 'attachurl' },
     { xtype: 'hiddenfield', name: 'attachname', id: mep + 'attachname' }
   ];
   me.disDetailControls = ['attachname'];
   me.enDetailControls = ['attachtype', 'attachurl'];  
 },
 
 OnCreateDetailWin: function () {
   var me = this;
   var mep = me.tranPrefix;

   if (Ext.getCmp(mep + 'detailwin')) {
     Ext.getCmp(mep + 'detailwin').destroy();
   };
   
   var items = [
     ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me }
//     '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
   ];
   
   me.OnBeforeCreateDetailEdit();
   
   me.plDetailEdit = Ext.create('Ext.form.Panel', {
     id:mep + 'pldetailedit',
     region : 'north',
//     height : '18%',
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
     columnWidth:1,
     scope: me,
     items: [upload]    
   });
   
   upload.on('showdetail',me.OnShowDetail,me);
   upload.on('closewin',me.OnCloseWin,me);
   
   me.winDetail = Ext.create('Ext.Window', {
     id: mep + 'detailwin',
     title: me.detailTitle,
     width: 600,
     height: 370,
     maximizable: false,
     closeAction: 'hide',
     modal: true,
     layout: 'border',
     plain: false,
     closable: true,
     draggable: true,
     constrain: true,
     items : [me.plDetailEdit,me.plAttDetailGrid]
   });
 },
 
 OnListNew : function(){
   var me = this;
   me.OnCreateDetailWin();
   if(me.winDetail){      
     me.winDetail.show();
   };
 },
 
 OnCloseWin:function(){
   var me = this;
   var mep = this.tranPrefix;
   me.winDetail.hide();
 },
 
 OnShowDetail:function(render, item){
   var me = this;
   var mep = this.tranPrefix;
   
   var attachname = Ext.getCmp(mep+'attachname').getValue();
   var attachurl = Ext.getCmp(mep+'attachurl').getValue();
   
   if(item){
     if(attachname == ""){
       attachname = item.name;
     }else{
       attachname = attachname+','+item.name
     };
     if(attachurl == ""){
       attachurl = item.url;
     }else{
       attachurl = attachurl+','+item.url;
     };
     tools.SetValue(mep + 'attachname',attachname);
     tools.SetValue(mep + 'attachurl',attachurl);
   };
 },
 
 OnListSave: function () {
   var me = this;
   var mep = me.tranPrefix;
   
   var attachname = Ext.getCmp(mep+ 'attachname').getValue();
   var attachurl = Ext.getCmp(mep + 'attachurl').getValue();
   var attachtype = Ext.getCmp(mep + 'attachtype').getValue();
   
   if (tools.InvalidForm(me.plDetailEdit.getForm())) {
     if(attachname == "" || attachname == null || attachname == undefined){
       tools.alert('请上传附件！');
       return;
     }else{
       if (me.detailEditType == 1) {
         //可能有多个附件的情况
         var attachnames = attachname.split(",");
         var attachurls = attachurl.split(",");
         for(i = 0; i <attachnames.length; i++){
           var record = me.plDetailGrid.store.model.create({});
           record.data.attachname = attachnames[i];
           record.data.attachtype = '01';
           record.data.attachtypename = '图谱';
           record.data.attachurl = attachurls[i];
           me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
         };
       }
       else {
         me.OnBeforeListSave(me.detailRecord);
         me.plDetailGrid.getView().refresh();
       };
     };
     me.winDetail.hide();
   };
 },
 
 OnListDelete:function(){
   var me = this;
   var j = me.plDetailGrid.selModel.selected.items.length;
   
   for ( var i = 0; i < j; i++) {
     me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
   };
   
   me.plDetailGrid.getView().refresh();
 },
 
 OnListSelect: function (view, record) {
   alms.PopupFileShow(record.data.attachtypename+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
 },
 
 OnAfterCreateDetail: function () {
   var me = this, mep = this.tranPrefix;
   
   var fileColumn = [];
   var fileField = [];    

   tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_bustaskattach'), fileColumn, fileField, mep + '_recordfile_');

   me.fileStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusRecordFile.do'), fileField);
   
   me.fileGrid = Ext.create('Ext.grid.Panel', {
     region : 'center',
     title : '原始记录表',
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
     selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
     listeners : {
       'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
     }    
   });
   me.fileStore.load();
   me.plDetail.add(me.fileGrid);
   me.fileitemstar = [
     ' ', { id : mep + 'btnFileAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewFile, scope : me },
     ' ', { id : mep + 'btnFileDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteFile, scope : me }
   ];
   
   tools.SetToolbar(me.fileitemstar, mep);
     
   var tbfile = Ext.create('Ext.toolbar.Toolbar', {
     dock : 'top',
     items : me.fileitemstar
   });
   me.fileGrid.insertDocked(0, tbfile);
},
OnGetSaveParams : function() {
  var me = this;
  var mep = me.tranPrefix;
  
  if (me.hasDetail) {
    var details = [];
    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      details.push(me.plDetailGrid.store.getAt(i).data);
    }
    
    var filedetails = [];
    for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
      filedetails.push( me.fileGrid.store.getAt(i).data);
    }
    
    me.saveParams = { details: Ext.encode(details), filedetails: Ext.encode(filedetails)};
      //signerpassword: Ext.getCmp(mep + 'signer_passwd').getValue(), approveusers: Ext.getCmp(mep + 'approveuser').getValue() };
  }
},
OnAfterCreateDetailToolBar:function(){
  var me = this;
  var mep = me.tranPrefix;
  me.deitems = [
    ' ', { id : mep + 'btnDetailAdds', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
    ' ', { id : mep + 'btnDetailDeletes', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me }
  ];

},
OnBeforeCreateDetailEditFile: function () {
	    var me = this;
	    var mep = this.tranPrefix;

	    me.OnInitGridToolBar();
	    me.editDetailControlsFile = [
	      { xtype: 'hiddenfield', name: 'attachtype', id: mep + 'attachtypefile' },
	      { xtype: 'hiddenfield', name: 'attachurl', id: mep + 'attachurlfile' },
	      { xtype: 'hiddenfield', name: 'attachname', id: mep + 'attachnamefile' }
	    ];
	    me.disDetailControlsFile = ['attachnamefile'];
	    me.enDetailControlsFile = ['attachtypefile', 'attachurlfile'];  
	  },
	  
	  OnCreateDetailWinFile: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    if (Ext.getCmp(mep + 'detailwinfile')) {
	      Ext.getCmp(mep + 'detailwinfile').destroy();
	    };
	    
	    var itemsfile = [
	      ' ', { id: mep + 'btnDetailSaveFile', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveFile, scope: me }
//	      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
	    ];
	    
	    me.OnBeforeCreateDetailEditFile();
	    
	    me.plDetailEditFile = Ext.create('Ext.form.Panel', {
	      id:mep + 'pldetaileditfile',
	      region : 'north',
//	      height : '18%',
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
	      tbar : Ext.create('Ext.toolbar.Toolbar', {items: itemsfile}),
	      items: me.editDetailControlsFile    
	    });
	    
	    var uploadFile = tools.SwfUpload();
	    me.plFileDetailGrid = Ext.create('Ext.form.Panel', {
	      id:mep + 'filedetailgrid',
	      region : 'center',
	      columnWidth:1,
	      scope: me,
	      items: [uploadFile]    
	    });
	    
	    uploadFile.on('showdetail',me.OnShowDetailFile,me);
	    uploadFile.on('closewin',me.OnCloseWinFile,me);
	    
	    me.winDetailFile = Ext.create('Ext.Window', {
	      id: mep + 'detailwinfile',
	      title: '原始记录表',
	      width: 600,
	      height: 370,
	      maximizable: false,
	      closeAction: 'hide',
	      modal: true,
	      layout: 'border',
	      plain: false,
	      closable: true,
	      draggable: true,
	      constrain: true,
	      items : [me.plDetailEditFile,me.plFileDetailGrid]
	    });
	  },
	  
	  OnListNewFile : function(){
	    var me = this;
	    me.OnCreateDetailWinFile();
	    if(me.winDetailFile){      
	      me.winDetailFile.show();
	    };
	  },
	  
	  OnCloseWinFile:function(){
	    var me = this;
	    var mep = this.tranPrefix;
	    me.winDetailFile.hide();
	  },
	  
	  OnListSelectFile: function (view, record) {
	    alms.PopupFileShow(record.data.attachname+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
	  },
	  
	  OnShowDetailFile:function(render, item){
	    var me = this;
	    var mep = this.tranPrefix;
	    
	    var attachname = Ext.getCmp(mep+'attachnamefile').getValue();
	    var attachurl = Ext.getCmp(mep+'attachurlfile').getValue();
	    
	    if(item){
	      if(attachname == ""){
	        attachname = item.name;
	      }else{
	        attachname = attachname+','+item.name
	      };
	      if(attachurl == ""){
	        attachurl = item.url;
	      }else{
	        attachurl = attachurl+','+item.url;
	      };
	      tools.SetValue(mep + 'attachnamefile',attachname);
	      tools.SetValue(mep + 'attachurlfile',attachurl);
	    };
	  },
	  
	  OnListSaveFile: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    var attachname = Ext.getCmp(mep+ 'attachnamefile').getValue();
	    var attachurl = Ext.getCmp(mep + 'attachurlfile').getValue();
	    var attachtype = Ext.getCmp(mep + 'attachtypefile').getValue();
	    
	    if (tools.InvalidForm(me.plDetailEditFile.getForm())) {
	      if(attachname == "" || attachname == null || attachname == undefined){
	        tools.alert('请上传附件！');
	        return;
	      }else{
	        if (me.detailEditType == 1) {
	          //可能有多个附件的情况
	          var attachnames = attachname.split(",");
	          var attachurls = attachurl.split(",");
	          for(i = 0; i <attachnames.length; i++){
	            var record = me.fileGrid.store.model.create({});
	            record.data.attachname = attachnames[i];
	            record.data.attachtype = '02';
	            record.data.attachtypename = '原始记录表';
	            record.data.attachurl = attachurls[i];
	            me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);      
	          };
	        }
	        else {
	          me.OnBeforeListSaveFile(me.detailRecord);
	          me.fileGrid.getView().refresh();
	        };
	      };
	      me.winDetailFile.hide();
	    };
	  },
	  
	  OnListDeleteFile:function(){
	    var me = this;
	    var j = me.fileGrid.selModel.selected.items.length;
	    
	    for ( var i = 0; i < j; i++) {
	      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
	    };
	    
	    me.fileGrid.getView().refresh();
	  },
	  OnInitData : function() {
		    var me = this;
		    var mep = me.tranPrefix;
		    me.plEdit.getForm().reset();
		    //此處trainid不要改正!
		    me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'trainid');
		    me.plDetailGrid.store.load();
		    me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusRecordFile.do?brf.tranid=') + tools.GetValue(mep + 'trainid');
		    me.fileGrid.store.load();
	  },
	  OnPrintTask: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    if (!Ext.isEmpty(me.html)) {
	      var LODOP = getLodop();
	      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
	      LODOP.PRINT_INIT("样品任务单打印");
	      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
	      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
	      LODOP.SET_PRINTER_INDEXA(-1);
	      LODOP.PREVIEW();//预览功能
//	        LODOP.PRINT();//打印功能
	    }
	  },
	  OnCreateStdMethodHtml:function(item){
	    
	     var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
	      html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>检测方法偏离确认表</b></td></tr>';
	      html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 5-3-7</td></tr>';
	      html += '</table>';
	      
	      html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
	      
	    
	      html += '<tr class="infotr"style="height: 90px" ><td class="infoat" align="center"  width="10%">检测方法名称</td>' +
	      '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.methodname) +'</td></tr>';
	      
	      html += '<tr class="infotr" style="height: 90px"><td class="infoat" align="center"  width="10%">方法偏离情况</td>' +
	      '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.methodsource) +'</td></tr>';
	      
	      html += '<tr class="infotr" style="height: 200px"><td class="infoat" align="center"  width="10%">理   由</td>' +
	      '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.reason)+'</td></tr>';
	      
	      html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">试验记录</td>' +
	      '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(item.trialrecord) +'</td></tr>';
	      html + '</td></tr>';
	      
	      var checkor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=MehtodDevi&htodo.flownode=check');
	      html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  > 提出人：</td>' +
	      '<td class="infoc infoleft" width="30%"   align="center" >'+item.propusername+'<td class="infoat" align="center" width="10%"> 审查人：</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemData(checkor.tranusername)+'</td></tr>';
	      var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=MehtodDevi&htodo.flownode=judge');
	      html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">确认结果</td>' +
        '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(judger.tododesc) +'</td></tr>';
        html + '</td></tr>';
        
        
        html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >  技术负责人签字： </td>' +
        '<td class="infoc infoleft" width="30%"   align="center" >'+alms.GetItemData(judger.tranusername)+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(judger.trandate)+'</td></tr>';
        
        
        html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">备注</td>' +
        '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(item.remark) +'</td></tr>';
        html + '</td></tr>';
        
	      html += '</table>'
	    
	      return html;
	  }

});
