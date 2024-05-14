Ext.define('alms.abilityform', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '中心能力表',
      winWidth: 620,
      winHeight:600,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_abilityform',
      storeUrl: 'BasSearchAbilityForm.do',
      saveUrl: 'BasSaveAbilityForm.do',
      expUrl: 'BasSearchAbilityForm.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'abilityformid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: 1, layout: 'anchor', items: [
          tools.FormText('能力表编号', 'aform.abilityformid', mep + 'abilityformid', 6, '100%', true, 1,'',90),
          tools.FormText('能力表名称', 'aform.abilityformname', mep + 'abilityformname', 200, '100%', true, 1,'',90),
          tools.FormText('能力表版本号', 'aform.abilityformnum', mep + 'abilityformnum', 50, '100%', true, 1,'',90)
        ]}
         
      ]},
      tools.FormTextArea('版本描述', 'aform.abilityformdesc', mep + 'abilityformdesc', 200, '100%', true, 3,3,90),
      {xtype:'hiddenfield',name:'aform.abilityformurl',id: mep + 'abilityformurl'},
      {xtype:'hiddenfield',name:'aform.deal.action',id: mep + 'datadeal'}
    ];
    
    me.disNews = ['abilityformid'];
    me.disEdits = ['abilityformid'];
    me.enNews = [ 'abilityformname', 'abilityformdesc', 'abilityformurl', 'abilityformnum'];
    me.enEdits = [ 'abilityformname','abilityformdesc', 'abilityformurl', 'abilityformnum'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '能力表版本号', labelWidth: 100, width: 200, maxLength: 40, name: 'searchabilityformnum', id: mep + 'searchabilityformnum', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
//      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
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
      height:'30%',
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
//    upload.on('closewin',me.OnCloseEditWin,me);
    
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
    tools.SetValue(mep+'abilityformurl',item.url);
  },
  
  OnCloseEditWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.winEdit.hide();
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
          'aform.abilityformnum': tools.GetEncode(tools.GetValue(mep + 'searchabilityformnum')),
        });
      });
    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'abilityformid', item.abilityformid);
    tools.SetValue(mep + 'abilityformname', item.abilityformname);
    tools.SetValue(mep + 'abilityformdesc', item.abilityformdesc);
    tools.SetValue(mep + 'abilityformurl', item.abilityformurl);
    tools.SetValue(mep + 'abilityformnum', item.abilityformnum);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'abilityformid').reset();
    Ext.getCmp(mep + 'abilityformid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.abilityformid) {
          tools.SetValue(mep + 'abilityformid', action.result.data.abilityformid);
        }
      }
    }
  },
  
  OnShow: function () {
    var me = this;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    console.log(record)
    if (record) {
    	if(Ext.isEmpty(record.abilityformurl)){
       	 tools.alert('该能力表没有上传源文件！');
            return;
       }
    	var str = record.abilityformurl.split(".");
      alms.PopupFileShow('预约附件预览', 'FileDownFile.do', record.abilityformurl, record.abilityformname+"."+str[str.length-1]);
    }
  }
  
});