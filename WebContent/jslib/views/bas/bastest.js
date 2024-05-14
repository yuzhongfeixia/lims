Ext.define('alms.bastest', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '检测方法',
      winWidth: 620,
      winHeight:540,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bastest',
      storeUrl: 'BasSearchBasTest.do',
      saveUrl: 'BasSaveBasTest.do',
      expUrl: 'BasSearchBasTest.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'teststandard',
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
          tools.FormText('检测方法编号', 'btest.teststandard', mep + 'teststandard', 6, '96%', true, 1,'',90),
          tools.FormText('检测方法名称', 'btest.teststandardname', mep + 'teststandardname', 200, '96%', true, 1,'',90),
          tools.FormText('检出限', 'btest.testlimit', mep + 'testlimit', 50, '96%', true, 1,'',90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('标准号', 'btest.teststandardcode', mep + 'teststandardcode', 200, '100%', true, 2,'',90),
          tools.FormText('类别(产品/项目/参数)', 'btest.standardnum', mep + 'standardnum', 50, '100%', true, 1,'',90),
          tools.FormText('定量限', 'btest.testloq', mep + 'testloq', 50, '100%', true, 1,'',90),
          {xtype:'hiddenfield',name:'btest.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('方法描述', 'btest.teststandarddesc', mep + 'teststandarddesc', 200, '100%', true, 3,2,90),
      {xtype:'hiddenfield',name:'btest.teststandardurl',id: mep + 'teststandardurl'}
    ];
    
    me.disNews = ['teststandard'];
    me.disEdits = ['teststandard'];
    me.enNews = [ 'teststandardname', 'teststandardcode','teststandarddesc', 'teststandardurl', 'testlimit','standardnum', 'testloq'];
    me.enEdits = [ 'teststandardname', 'teststandardcode','teststandarddesc', 'teststandardurl', 'testlimit','standardnum', 'testloq'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '检测方法编号', labelWidth: 100, width: 200, maxLength: 40, name: 'searchteststandard', id: mep + 'searchteststandard', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 100, width: 200, maxLength: 40, name: 'searchteststandardname', id: mep + 'searchteststandardname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
    tools.SetValue(mep+'teststandardurl',item.url);
//    tools.SetValue(mep+'teststandardname',item.name);
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
          'btest.teststandard': tools.GetEncode(tools.GetValue(mep + 'searchteststandard')),
          'btest.teststandardname': tools.GetEncode(tools.GetValue(mep + 'searchteststandardname'))
        });
      });
    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'teststandard', item.teststandard);
    tools.SetValue(mep + 'teststandardcode', item.teststandardcode);
    tools.SetValue(mep + 'teststandardname', item.teststandardname);
    tools.SetValue(mep + 'teststandarddesc', item.teststandarddesc);
    tools.SetValue(mep + 'teststandardurl', item.teststandardurl);
    tools.SetValue(mep + 'testlimit', item.testlimit);
    tools.SetValue(mep + 'standardnum', item.standardnum);
    tools.SetValue(mep + 'testloq', item.testloq);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'teststandard').reset();
    Ext.getCmp(mep + 'teststandard').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.teststandard) {
          tools.SetValue(mep + 'teststandard', action.result.data.teststandard);
        }
      }
    }
  },
  
  OnShow: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
    if (record) {        
      alms.PopupFileShow('预约附件预览', 'FileDownFile.do', record.teststandardurl, record.teststandardname);
    }
  }
  
});