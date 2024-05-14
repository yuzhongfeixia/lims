Ext.define('alms.manbasfile', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件档案管理',
      detailTitle:'文件新增',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basfile',
      storeUrl: 'IncFileSearchBasFile.do',
      saveUrl: 'IncFileSaveBasFile.do',
      saveAllUrl:'IncFileSaveAllBasFile.do',
      expUrl: 'IncFileSearchBasFile.do',
      deleteUrl:'IncFileDeleteBasFile.do',
      hasGridSelect: true,
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'mffileid'
    });
    me.callParent(arguments);
  },
  
  winUpload: null,
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('文件编号', 'bf.fileid', mep + 'mffileid', 20, '96%', false, 1),
          tools.FormCombo('文件类别', 'bf.filecate', mep + 'mffilecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '96%', false, 6),
          tools.FormCombo('文件状态', 'bf.filestatus', mep + 'mffilestatus', tools.ComboStore('FileStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 6),
          tools.FormDate('上传时间', 'bf.uploaddate', mep + 'mfuploaddate', 'Y-m-d H:i', '96%', false, 4)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('文件名称', 'bf.filename', mep + 'mffilename', 50, '96%', false, 4),
          tools.FormCombo('文件类型', 'bf.filetype', mep + 'mffiletype', tools.ComboStore('FileType', gpersist.SELECT_MUST_VALUE), '96%', false, 5),
          tools.FormDate('文件时间', 'bf.filedate', mep + 'mffiledate', 'Y-m-d H:i', '96%', false, 4),
          {xtype:'hiddenfield',name:'bf.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('文件地址', 'bf.fileurl', mep + 'mffileurl', 50, '98%', true, 9, 3)
    ];
    
    me.disNews = ['mffileid', 'mffileurl', 'mffilename'];
    me.disEdits = ['mffileid', 'mffileurl', 'mffilename'];
    me.enNews = ['mffilecate', 'mffilestatus', 'mffiledate','mffiletype','mfuploaddate'];
    me.enEdits = ['mffilecate', 'mffilestatus', 'mffiledate','mffiletype','mfuploaddate'];
  },
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
//    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
//    fileurl = tools.GetEncode(tools.GetEncode(fileurl));
//    tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
    me.winDetail.hide();
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', {xtype:'textfield',fieldLabel:'文件编号',labelWidth:60,width:180,maxLength:20,name:'searchfileid',id:mep + 'searchfileid',allowBlank:true},
      ' ', {xtype:'textfield',fieldLabel:'文件名称',labelWidth:60,width:180,maxLength:20,name:'searchfilename',id:mep + 'searchfilename',allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: '销毁', iconCls: 'icon-delete', handler: me.OnDelete,scope: me}, 
//      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 
    tools.SetValue(mep + 'attachtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'attachcate', gpersist.SELECT_MUST_VALUE);
  },
  
  OnDelete: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '确定销毁该文件？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.deleteUrl),
              params: me.deleteParams,
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnSearch();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else 
        tools.alert(gpersist.STR_MSG_BATCHDELETETIP);
    }
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({fileid: datas[i].data.fileid});
    }

    me.deleteParams = {deletes : Ext.encode(json)};
    
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'mffileid', item.fileid);
    tools.SetValue(mep + 'mffilecate', item.filecate);
    tools.SetValue(mep + 'mffilestatus', item.filestatus);
    tools.SetValue(mep + 'mffiledate', item.filedate);
    tools.SetValue(mep + 'mfuploaddate', item.uploaddate);
    tools.SetValue(mep + 'mffilename', item.filename);
    tools.SetValue(mep + 'mffiletype', item.filetype);
    tools.SetValue(mep + 'mffileurl', item.fileurl);
    return true;
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'attachid').reset();
    Ext.getCmp(mep + 'attachid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.attachid) {
          tools.SetValue(mep + 'attachid', action.result.data.attachid);
        }
      }
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (me.gridStore) {
      me.gridStore.on('beforeload', function(store, options) {
        Ext.apply(store.proxy.extraParams, {
          'bf.fileid' : tools.GetValueEncode(mep + 'searchfileid'),
          'bf.filename' : tools.GetValueEncode(mep + 'searchfilename')
        });
      });
    }
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnDetailUpload', text: '上传', iconCls: 'icon-deal', handler: me.OnListUploadAtt, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 2, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
  },
  
  OnListUploadAtt:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var upload = Ext.create('Ext.ux.uploadPanel.UploadPanel',{  
        addFileBtnText : '选择文件...',  
        uploadBtnText : '上传',  
        removeBtnText : '移除所有',  
        cancelBtnText : '取消上传',
        file_upload_limit : 1
      });
      
      me.winUpload = Ext.create('Ext.Window', {
        id: mep + 'uploadwin',
        title: '上传',
        width: 700,
        height: 260,
        maximizable: true,
        closeAction: 'destroy',
        modal: true,
        layout: 'fit',
        plain: false,
        closable: true,
        draggable: true,
        constrain: true,
        items : [upload]
      });
      
      upload.store.removeAll();
      upload.on('showdetail',me.OnShowDetail,me);
      upload.on('closewin',me.OnCloseWin,me);
      
      me.winUpload.show();
  },
  
  OnShowDetail: function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep+'mfuploaddate').setValue(new Date());
    
    var filename = Ext.getCmp(mep+'mffilename').getValue();
    var fileurl = Ext.getCmp(mep+'mffileurl').getValue();
    filename = item.name;
    fileurl = item.url;
    tools.SetValue(mep + 'mffilename',filename);
    tools.SetValue(mep + 'mffileurl',fileurl);
  },
  
  OnCloseWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.winUpload.hide();
    if (Ext.getCmp(mep + 'uploadwin')) {
      Ext.getCmp(mep + 'uploadwin').destroy();
    };
  },
  
  OnShow: function (view, record) {
    alms.PopupFileShow('文件附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
  },
  
  OnAuthEditForm: function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;

    if (islayout){
      me.plEdit.suspendLayouts(); 
    }
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);        
        tools.BtnsEnable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave','btnFormCancel','btnFormDeal','btnDetailUpload'], mep);
        me.plEdit.updateLayout();
        break;
        
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel','btnFormDeal','btnDetailUpload'], mep);
        break;
        
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnFormCopy','btnFormValid','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave','btnFormCancel','btnFormDeal','btnDetailUpload'], mep);
        break;
    }
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
//附件上传
  OnNew: function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnCreateDetailWin();
    me.OnInitDetailData();
    me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
    if(me.winDetail){      
      me.winDetail.show();
    }
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
  },
  
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
      height : '30%',
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
    
    upload.on('showdetail',me.OnMShowDetail,me);
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
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
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
//          tools.FormText('文件编号', 'bf.fileid', mep + 'fileid', 30, '96%', false, 12),
          tools.FormCombo('文件类别', 'bf.filecate', mep + 'filecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '96%', false, 1),
           tools.FormCombo('文件状态', 'bf.filestatus', mep + 'filestatus', tools.ComboStore('FileStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 2)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('文件类型', 'bf.filetype', mep + 'filetype', tools.ComboStore('FileType', gpersist.SELECT_MUST_VALUE), '96%', false, 1)
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'bf.filename', id: mep + 'filename' },
      { xtype: 'hiddenfield', name: 'bf.fileurl', id: mep + 'fileurl' },
      {xtype:'hiddenfield',name:'bf.deal.action',id: mep + 'mdatadeal'}
      
    ]
//    me.disDetailControls = ['fileid'];
//    me.enDetailControls = [ 'filecate', 'filetype', 'filestatus'];
    
  },
  
  OnMShowDetail: function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var attachname = Ext.getCmp(mep+'filename').getValue();
    var attachurl = Ext.getCmp(mep+'fileurl').getValue();
    if(item){
      if(attachname == ""){
        attachname = item.name;
      }else{
        attachname = attachname+','+item.name
      }
      if(attachurl == ""){
        attachurl = item.url;
      }else{
        attachurl = attachurl+','+item.url;
      }
      tools.SetValue(mep + 'filename',attachname);
      tools.SetValue(mep + 'fileurl',attachurl);
    }
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (!me.OnBeforeListSave()){
        return;
      }
      tools.SetValue(mep + 'mdatadeal', me.dataDeal);
      me.OnGetListSaveParams();
      
      me.plDetailEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveAllUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
      me.winDetail.hide();
     }
  },
  
  OnBeforeListSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var filetype = Ext.getCmp(mep+ 'filetype').getValue();
    var filecate = Ext.getCmp(mep + 'filecate').getValue();
    var filestatus = Ext.getCmp(mep + 'filestatus').getValue();
    var filename = Ext.getCmp(mep + 'filename').getValue();
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(filetype == gpersist.SELECT_MUST_VALUE || filecate == gpersist.SELECT_MUST_VALUE||filestatus == gpersist.SELECT_MUST_VALUE){
        tools.alert('请选择文件类型、文件类别或文件状态');
        return;
      }else{
        if(filename == "" || filename == null || filename == undefined){
          tools.alert('请上传文件！');
          return;
        }
      }
    }
    return true;
  },
  
  OnGetListSaveParams:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    me.saveParams = { filename :filename,fileurl: fileurl
    };
  },
  
  
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'fileid').reset();
    Ext.getCmp(mep + 'fileid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.fileid) {
          tools.SetValue(mep + 'fileid', action.result.data.fileid);
        }
      }
    }
  }
   
});