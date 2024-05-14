Ext.define('alms.stdreplrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '标准查新记录',
      winWidth: 750,
      winHeight: '100%',	
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdreplrecord',
      storeUrl: 'StdSearchStdReplRecord.do',
      saveUrl: 'StdSaveStdReplRecord.do',
      expUrl: 'StdSearchStdReplRecord.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasSubmit: true,
      hasGridSelect: true,
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusTaskAttach.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '标准查新明细',
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      hasDetailCheck:false,
      hasPageDetail: false
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  
  OnBeforeFormLoad : function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.OnInitGridToolBar();
	    
	    var searchitems = [
	                       ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
	                       ' ', { xtype: 'textfield', fieldLabel: '标准名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchstdname', id: mep + 'searchstdname', allowBlank: true }
	                       
	                       ];
	     
	    var items = [
	                 ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
	                 '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
	                 ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
	                 ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
	                 '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
//	                 '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//	                 ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
	                 '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
	              ]; 
	    
	    
	    tools.SetToolbar(searchitems,mep);
	    tools.SetToolbar(items, mep);
	     
	    var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
	     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
	     
	     me.tbGrid.add(searchtoolbar);
	     me.tbGrid.add(toolbar);
	  },
  
  
  
  
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    me.editControls=[
                     { xtype: 'container', anchor: '100%', layout: 'column', items: [
                       { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
                           tools.FormText('业务编号', 'replrecord.tranid', mep + 'tranid', 20, '96%', false, 1)
                       ]},
                       { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
                           tools.FormText('业务员', 'replrecord.tranusername', mep + 'tranusername', 20, '96%', false, 3)
                       ]},
                       { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
                           tools.FormDate('申请时间', 'replrecord.trandate', mep + 'trandate', 'Y-m-d ', '100%', false, 5,nowdate),
                           {xtype:'hiddenfield',name:'replrecord.flowstatus',id: mep + 'flowstatus'},
                           {xtype:'hiddenfield',name:'replrecord.flowaction',id: mep + 'flowaction'},
                           {xtype:'hiddenfield',name:'replrecord.tranuser',id: mep + 'tranuser'},
                           {xtype:'hiddenfield',name:'replrecord.deal.action',id: mep + 'datadeal'}
                        ]}
                      ]},
                      tools.FormTextArea('查新描述', 'replrecord.remark', mep + 'remark',  200, '100%',true, 8,5)
                  ];
                  me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
                  me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
                  me.enNews = [ 'flowstatus', 'flowaction', 'remark'];
                  me.enEdits = [ 'flowstatus', 'flowaction', 'remark'];
                    
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
	  
	  OnBeforeSearch: function () {
		    var me = this;
		    var mep = me.tranPrefix;
		    
		    if (me.gridStore) {
		      me.gridStore.on('beforeload', function (store, options) {      
		        Ext.apply(store.proxy.extraParams, {
		          'replrecord.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
		          'replrecord.stdname': tools.GetEncode(tools.GetValue(mep + 'searchstdname'))
		        });
		      });
		    };
		  },
  
	  OnLoadData:function(item){
		    var me = this;
		    var mep = me.tranPrefix;
		    tools.SetValue(mep + 'tranid', item.tranid);
		    tools.SetValue(mep + 'flowstatus', item.flowstatus);
		    tools.SetValue(mep + 'flowaction', item.flowaction);
		    tools.SetValue(mep + 'remark', item.remark);
		    tools.SetValue(mep + 'tranuser', item.tranuser);
		    tools.SetValue(mep + 'tranusername', item.tranusername);
		    tools.SetValue(mep + 'trandate', item.trandate);
		    me.OnDetailRefresh();
		    return true;
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
	        }
	      }
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
    me.callParent(arguments);
    me.plEdit.getForm().reset();
    
    //此處trainid不要改正!
    me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'trainid');
    me.plDetailGrid.store.load();
    tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username);
  
  },
  
 OnDetailRefresh: function () {
   var me = this;
   var mep = me.tranPrefix;

   if (me.plDetailGrid && me.plDetailGrid.store) {
     me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'tranid');
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
            record.data.attachtype = '04';
            record.data.attachtypename = '标准变更附件';
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
     me.saveParams = { details: Ext.encode(details)}
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

