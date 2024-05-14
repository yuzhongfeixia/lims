Ext.define('alms.mannoderole', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '流程定义角色',
      winWidth: 750,
      winHeight: 120,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_noderole',
      storeUrl: 'FlowSearchFlowRole.do',
      saveUrl: 'FlowSaveFlowRole.do',
      expUrl: 'FlowSearchFlowRole.do',
      deleteUrl:'FlowDeleteFlowRole.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'noderole'
    });    

    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(tools.MustTitle('业务角色编号'), 'fr.noderole', mep + 'noderole', 4, '96%', false, 1, null, 100)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(tools.MustTitle('业务角色名称'), 'fr.noderolename', mep + 'noderolename', 30, '100%', false, 2,  null, 100),           
           {xtype: 'hiddenfield', name: 'fr.deal.action', id: mep + 'datadeal'}
         ]}
      ]}
    ];
    
    me.disNews = [];
    me.disEdits = ['noderole'];
    me.enNews = ['noderole', 'noderolename'];
    me.enEdits = ['noderolename'];
  },
  
  OnSetData: function(id, record) {
    var me = this, mep = me.tranPrefix;  
    
    tools.SetValue(mep + 'noderole', record.noderole);
    tools.SetValue(mep + 'noderolename', record.noderolename);
    
    return true;
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'noderole').reset();
    Ext.getCmp(mep + 'noderole').focus(true, 500);
  },
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    var searchitems = [
                       ' ', { xtype: 'textfield', fieldLabel: '业务角色名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchnoderolename', id: mep + 'searchnoderolename', allowBlank: true }                              
     ];
    var items = [
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnValid', text: '有效', iconCls: 'icon-valid', handler: me.OnValid,scope: me},
      ' ', { id: mep + 'btnInValid', text: '无效', iconCls: 'icon-invalid', handler: me.OnInValid,scope: me},
      '-', ' ', { id: mep + 'btnCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnCheck,scope: me},
      ' ', { id: mep + 'btnUnCheck', text: '取消审核', iconCls: 'icon-unaudit', handler: me.OnUnCheck,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (!me.hasOtherSearch)
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    tools.SetToolbar(searchitems,mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var searchbar = Ext.create('Ext.toolbar.Toolbar',{items:searchitems,border: false});
    me.tbGrid.add(searchbar);
    me.tbGrid.add(toolbar);
  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'fr.noderolename':tools.GetValueEncode(mep+'searchnoderolename')
        });
      });
    };
  },
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({noderole: datas[i].data.noderole});
    }

    me.deleteParams = {deletes : Ext.encode(json)};
    
    return true;
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
     // '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnFormValid', text: '有效', iconCls: 'icon-valid', handler: me.OnFormValid, scope: me },
      '-', ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
    
    me.OnBeforeCreateEditToolBar();
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnAfterCreateEditToolBar();
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
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
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });
    
    me.OnAfterCreateEdit();
  }
});