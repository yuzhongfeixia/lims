Ext.define('alms.prdverify', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '试剂耗材验证',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdverify',
      storeUrl: 'PrdSearchPrdVerify.do',
      saveUrl: 'PrdSavePrdVerify.do',
      expUrl: 'PrdSearchPrdVerify.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'verifyid',
      hasSubmit: true,
      hasGridSelect: true,
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusTaskAttach.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '耗材验证明细',
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      hasDetailCheck:false,
      hasPageDetail: false
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var prditems = [
      ' ', { xtype: 'textfield', fieldLabel: '试剂耗材名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchprdnames', id: mep + 'searchprdnames', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnPrdNameSearch, scope: me }
     ];
    
    var prdname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '试剂耗材', name: 'prdverify.prdname', id: mep + 'prdname', winTitle: '选择试剂耗材',
      maxLength: 30, maxLengthText: '试剂耗材不能超过20！', selectOnFocus: false, labelWidth: 80,
      blankText: '试剂耗材为空！', allowBlank: false, anchor: '98%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdapplydetails',
      storeUrl: 'PrdSearchPrdApplyDetailForVerify.do',
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 750, pickerHeight: 500
    });
    
    prdname.on('griditemclick', me.OnPrdSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('验证编号', 'prdverify.verifyid', mep + 'verifyid',20, '96%', false, 1),
          tools.FormCombo('耗材类别', 'prdverify.prdtype', mep + 'prdtype', tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE), '96%', false, 2),
          tools.FormText('生产厂家', 'prdverify.factoryname', mep + 'factoryname', 40, '96%', false, 5),
          tools.FormText('供应商', 'prdverify.tradename', mep + 'tradename', 40, '96%', true, 7),
          tools.FormText('进货量', 'prdverify.buycount', mep + 'buycount', 5, '96%', false, 7,'isinteger')
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          prdname,
          tools.FormText('规格型号', 'prdverify.prdstandard', mep + 'prdstandard', 40, '98%', true, 4),
          tools.FormText('出厂批号', 'prdverify.factorycode', mep + 'factorycode', 40, '98%', true, 6),
          tools.FormDate('进货时间', 'prdverify.buydate', mep + 'buydate', 'Y-m-d', '98%', false, 8,nowdate),
          tools.FormCombo('单位', 'prdverify.prdunit', mep + 'prdunit', tools.ComboStore('PrdUnit', gpersist.SELECT_MUST_VALUE), '98%', false, 2),
          {xtype:'hiddenfield',name:'prdverify.prdid',id: mep + 'prdid'},
          {xtype:'hiddenfield',name:'prdverify.comfirmuser',id: mep + 'comfirmuser'},
          {xtype:'hiddenfield',name:'prdverify.flowaction',id: mep + 'flowaction'},
          {xtype:'hiddenfield',name:'prdverify.flowstatus',id: mep + 'flowstatus'},
          {xtype:'hiddenfield',name:'prdverify.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'prdverify.prdserial',id: mep + 'prdserial'},
          {xtype:'hiddenfield',name:'prdverify.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      
      tools.FormTextArea('备注', 'prdverify.remark', mep + 'remark', 200, '99%', true,11,5)
    ];
    
    me.disNews = ['verifyid'];
    me.disEdits = ['verifyid'];
    me.enNews = [ 'tranid', 'prdid','prdname', 'factoryname', 'factorycode', 'tradename', 'buydate', 'buycount', 'flowaction', 'flowstatus', 
                  'tranuser', 'tranusername', 'trandate', 'trandesc','remark', 'prdstandard','prdtype','prdunit'];
    me.enEdits = [ 'tranid', 'prdid', 'prdname','factoryname', 'factorycode', 'tradename', 'buydate', 'buycount', 'flowaction', 'flowstatus', 
                   'tranuser', 'tranusername', 'trandate', 'trandesc','remark', 'prdstandard','prdtype','prdunit'];
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
  
  OnPrdNameSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnPrdNameBeforeLoad();
    var prdname = Ext.getCmp(mep + 'prdname');
    prdname.store.loadPage(1);
    
  },
 
  OnPrdNameBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var prdname = Ext.getCmp(mep + 'prdname');
    
    if(prdname.store){
      prdname.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'prddetail.prdname':tools.GetValueEncode(mep + 'searchprdnames')
          });
      });
    }
  },
  
  OnPrdSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.tranid){
      tools.SetValue(mep+"tranid",item.tranid);
      tools.SetValue(mep+"prdunit",item.prdunit);
      tools.SetValue(mep+"prdid",item.prdid);
      tools.SetValue(mep+"prdname",item.prdname);
      tools.SetValue(mep+"prdstandard",item.prdstandard);
      tools.SetValue(mep+"prdtype",item.prdtype);
      tools.SetValue(mep+"prdserial",item.prdserial);
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '验证编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchverifyid', id: mep + 'searchverifyid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '申请编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid1', id: mep + 'searchtranid1', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '试剂耗材', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '供应商', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtradeid', id: mep + 'searchtradeid', allowBlank: true }
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
  
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdverify.tranid':tools.GetValueEncode(mep+'searchtranid1'),
           'prdverify.verifyid':tools.GetValueEncode(mep+'searchverifyid'),
           'prdverify.prdname':tools.GetValueEncode(mep+'searchprdname'),
           'prdverify.tradename':tools.GetValueEncode(mep+'searchtradeid')
         })
       });
     }
  },
  
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'verifyid', item.verifyid);
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'prdserial', item.prdserial);
    tools.SetValue(mep + 'prdname', item.prdname);
    tools.SetValue(mep + 'prdstandard', item.prdstandard);
    tools.SetValue(mep + 'factoryname', item.factoryname);
    tools.SetValue(mep + 'factorycode', item.factorycode);
    tools.SetValue(mep + 'tradename', item.tradename);
    tools.SetValue(mep + 'buydate', item.buydate);
    tools.SetValue(mep + 'buycount', item.buycount);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'trandesc', item.trandesc);
    tools.SetValue(mep + 'audituser', item.audituser);
    tools.SetValue(mep + 'auditusername', item.auditusername);
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'auditdesc', item.auditdesc);
    tools.SetValue(mep + 'comfirmuser', item.comfirmuser);
    tools.SetValue(mep + 'comfirmusername', item.comfirmusername);
    tools.SetValue(mep + 'comfirmdate', item.comfirmdate);
    tools.SetValue(mep + 'comfirmdesc', item.comfirmdesc);
    tools.SetValue(mep + 'checkuser', item.checkuser);
    tools.SetValue(mep + 'checkusername', item.checkusername);
    tools.SetValue(mep + 'checkdate', item.checkdate);
    tools.SetValue(mep + 'checkdesc', item.checkdesc);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'prdtype', item.prdtype);
    tools.SetValue(mep + 'prdunit', item.prdunit);
    me.OnDetailRefresh();
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'verifyid').reset();
    Ext.getCmp(mep + 'verifyid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.verifyid) {
          tools.SetValue(mep + 'verifyid', action.result.data.verifyid);
          tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
          tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
        }
      }
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
   if (me.hasPrevNext) {
     Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
       ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
   }
   if (me.hasSubmit) {
     Ext.Array.insert(me.editToolItems, 2, [
       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
     ]);
   }
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
    me.OnDetailRefresh();
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
    me.OnDetailRefresh();
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.getForm().reset();
    //此處trainid不要改正!
//    me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'verifyid');
//    me.plDetailGrid.store.load();
  
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
     me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'verifyid');
     me.plDetailGrid.store.load();
   };
 

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
//      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
//      height : '18%',
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
            record.data.attachtype = '06';
            record.data.attachtypename = '耗材验证明细';
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
  OnGetSaveParams : function() {
   var me = this;
   var mep = me.tranPrefix;
   
   if (me.hasDetail) {
     var details = [];
     for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     
//     var filedetails = [];
//     for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
//       filedetails.push( me.fileGrid.store.getAt(i).data);
//     }
     
     me.saveParams = { details: Ext.encode(details)}//, filedetails: Ext.encode(filedetails)};
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
 
 }
  
});