Ext.define('alms.basjudge', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '判定方法',
      winWidth: 600,
      winHeight:530,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basjudge',
      storeUrl: 'BasSearchBasJudge.do',
      saveUrl: 'BasSaveBasJudge.do',
      expUrl: 'BasSearchBasJudge.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'judgestandard',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('判定标准编号', 'bjudge.judgestandard', mep + 'judgestandard', 6, '96%', true, 1,'',90),
          tools.FormText('标准编号', 'bjudge.standardnum', mep + 'standardnum', 100, '96%', true, 1,'',90),
          tools.FormText('产品/项目/参数', 'bjudge.judgestandardname', mep + 'judgestandardname', 100, '96%', true, 1,'',90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('类别（产品/项目/参数）', 'bjudge.judgestandardcode', mep + 'judgestandardcode', 40, '100%', true, 2,'',90),
          tools.FormText('限制范围', 'bjudge.judgestandardrange', mep + 'judgestandardrange', 40, '100%', true, 2,'',90),
          tools.FormDate('登记时间', 'bjudge.registerdate', mep + 'registerdate', 'Y-m-d', '100%', true, 11,'',90),
          {xtype:'hiddenfield',name:'bjudge.deal.action',id: mep + 'datadeal'},
          {xtype:'hiddenfield',name:'bjudge.judgestandardurl',id: mep + 'judgestandardurl'}
        ]}     
      ]},
      tools.FormTextArea('说明', 'bjudge.judgestandardexplain', mep + 'judgestandardexplain', 200, '100%', true, 3,2,90)                                                           
    ];
    
    me.disNews = ['judgestandard', 'judgestandardurl'];
    me.disEdits = ['judgestandard', 'judgestandardurl'];
    me.enNews = [ 'judgestandardcode', 'judgestandardname','standardnum','registerdate','judgestandardrange','judgestandardexplain'];
    me.enEdits = [ 'judgestandardcode', 'judgestandardname','standardnum','registerdate','judgestandardrange','judgestandardexplain'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '判定方法编号', labelWidth: 100, width: 200, maxLength: 40, name: 'searchjudgestandard', id: mep + 'searchjudgestandard', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '判定方法名称', labelWidth: 100, width: 200, maxLength: 40, name: 'searchjudgestandardname', id: mep + 'searchjudgestandardname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnPreview', text:'文件预览', iconCls: 'icon-outlook', handler: me.OnPreview, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bjudge.judgestandard': tools.GetEncode(tools.GetValue(mep + 'searchjudgestandard')),
          'bjudge.judgestandardname': tools.GetEncode(tools.GetValue(mep + 'searchjudgestandardname'))
        });
      });
    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'judgestandard', item.judgestandard);
    tools.SetValue(mep + 'judgestandardcode', item.judgestandardcode);
    tools.SetValue(mep + 'judgestandardname', item.judgestandardname);
    tools.SetValue(mep + 'judgestandardurl', item.judgestandardurl);
    tools.SetValue(mep + 'standardnum', item.standardnum);
    tools.SetValue(mep + 'judgestandardrange', item.judgestandardrange);
    tools.SetValue(mep + 'judgestandardexplain', item.judgestandardexplain);
    tools.SetValue(mep + 'registerdate', item.registerdate);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'judgestandard').reset();
    Ext.getCmp(mep + 'judgestandard').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.judgestandard) {
          tools.SetValue(mep + 'judgestandard', action.result.data.judgestandard);
        }
      }
    }
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
//      ' ', { id: mep + 'btnDetailUpload', text: '上传', iconCls: 'icon-deal', handler: me.OnListUploadAtt, scope: me },
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
        width: 600,
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
      
      upload.on('showdetail',me.OnShowDetail,me);
      upload.on('closewin',me.OnCloseWin,me);
      
      me.winUpload.show();
  },
  
//  OnShowDetail: function(render, item){
//    var me = this;
//    var mep = me.tranPrefix;
//    var judgestandardurl = Ext.getCmp(mep+'judgestandardurl').getValue();
//    console.log(judgestandardurl)
//    judgestandardurl = item.url;
//    console.log(judgestandardurl+"sssssdadadaaaaaaa")
//    tools.SetValue(mep + 'judgestandardurl',judgestandardurl);
//  },
  
  OnCloseWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.winUpload.hide();
    if (Ext.getCmp(mep + 'uploadwin')) {
      Ext.getCmp(mep + 'uploadwin').destroy();
    }
  },
  
  OnAuthEditForm: function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;

    if (islayout)
      me.plEdit.suspendLayouts();
      
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
  
  //预览
  OnPreview: function (view, record) {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    if(Ext.isEmpty(record)){
   	 tools.alert('请选择需要查看的数据');
        return;
   }else{
//    console.log(record)
    
    if(Ext.isEmpty(record.judgestandardurl)){
    	 tools.alert('该标准没有上传源文件！');
         return;
    }
    alms.PopupFileShow('预览', 'FileDownFile.do', record.judgestandardurl, record.judgestandardname+'.'+ record.judgestandardurl.substring(record.judgestandardurl.lastIndexOf('.') + 1));
    }
   },
  
  OnNew: function () {
    var me = this;
    me.OnCreateEditWin();
    
    if(me.winEdit) {     
      var date = new Date();
      me.winEdit.show();
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
      me.cancelRecord = null;
      me.OnFormValidShow();
      me.OnInitData();      
    }
  },
  
  OnEdit:function(){
    var me = this;
    var date = new Date();
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!Ext.isEmpty(me.idValid)) {
      if (record && Ext.isDefined(record.validstatus) && (record.validstatus == gpersist.STR_INVALID)) {
        tools.alert('无效状态的数据不能被修改！');
        return;
      }
    }
    
    me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();     
        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
        if (!me.OnLoadData(record)) 
          return;
        me.cancelRecord= record;
        me.OnFormValidShow();
      }
    }
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editwin')) {
      Ext.getCmp(mep + 'editwin').destroy();
    }
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnAfterCreateEditToolBar();
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
      region:'north',
      height:'40%',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : me.tbEdit,
      items: me.editControls    
    });
    
    var upload = tools.SwfUpload(1);
    upload.dockedItems.items[1].items.items.pop();
    me.plAttGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'attdetailgrid',
      region : 'center',
      title:'上传文件',
      columnWidth:1,
      scope: me,
      items: [upload]    
    });
    
    upload.on('showdetail',me.OnInsertData,me);
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plEdit,me.plAttGrid]
    });
    
    me.OnAfterCreateEdit();
  },
  
  OnInsertData:function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'judgestandardurl',item.url);
//    tools.SetValue(mep+'judgestandardname',item.name.substring(0,item.name.lastIndexOf('.')));
  },
  
  OnShow: function () {
    var me = this;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
   
    me.OnCreateEditWin();
    
    if (me.winEdit && record) {        
      me.winEdit.show();      
      
      if (!me.OnLoadData(record)) 
        return;
        
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);

      me.cancelRecord = record;
      me.OnFormValidShow();
    }
  }
  
});