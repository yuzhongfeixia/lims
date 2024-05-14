Ext.define('gpersist.mancompany', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: ['CoType','CoStatus'],
      editInfo: gpersist.STR_COL_COMPANY,
      winWidth: 750,
      winHeight: 250,
      columnUrl: 'p_get_company',
      storeUrl: gpersist.ACTION_COMPANY_LIST,
      saveUrl: gpersist.ACTION_COMPANY_SAVE,
      expUrl: gpersist.ACTION_COMPANY_LIST,
      hasPage: false,
      hasPrevNext: true
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('公司编码', 'co.coid', mep + 'coid', 2, '96%', false, 1),
          tools.FormCombo('公司类型', 'co.cotype', mep + 'cotype', tools.ComboStore('CoType', gpersist.SELECT_MUST_VALUE), '96%', true, 3),
          tools.FormText('打印抬头', 'co.coheader', mep + 'coheader', 40, '96%', false, 5),
          tools.FormCombo('公司状态', 'co.costatus', mep + 'costatus', tools.ComboStore('CoStatus', gpersist.SELECT_MUST_VALUE), '96%', true, 7)  
        ]},
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('公司名称', 'co.coname', mep + 'coname', 40, '100%', false, 2),
          tools.FormText('公司简称', 'co.coshort', mep + 'coshort', 6, '100%', false, 4),
          tools.FormText('排序序号', 'co.sortorder', mep + 'sortorder', 3, '100%', false, 6, 'isnumber'),
          {xtype: 'hiddenfield', name: 'co.deal.action', id: mep + 'datadeal'}
        ]}
      ]}
    ];

    me.disNews = [];
    me.disEdits = ['coid'];
    me.enNews = ['coid', 'coname', 'coshort', 'coheader', 'sortorder', 'cotype', 'costatus'];
    me.enEdits = ['coname', 'coshort', 'coheader', 'sortorder', 'cotype', 'costatus'];
  },

  OnCheckPrevNext: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    return (record.coid == tools.GetValue(mep + 'coid'));
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments);

    tools.SetValue(mep + 'cotype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'costatus', gpersist.SELECT_MUST_VALUE);
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (record && !Ext.isEmpty(record.coid)) {
      var item = tools.JsonGet(tools.GetUrl(gpersist.ACTION_COMPANY_GET + '?coid=') + record.coid);
      
      if (item && !Ext.isEmpty(item.coid)) {
        tools.Disabled(['coid','coname','coshort','coheader','sortorder','cotype','costatus'],mep);
        tools.SetValue(mep + 'coid', item.coid);
        tools.SetValue(mep + 'coname', item.coname);
        tools.SetValue(mep + 'coshort', item.coshort);
        tools.SetValue(mep + 'coheader', item.coheader);
        tools.SetValue(mep + 'sortorder', item.sortorder);
        tools.SetValue(mep + 'cotype', item.cotype); 
        tools.SetValue(mep + 'costatus', item.costatus);
      }
      else
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_COMPANY));
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_COMPANY));
  },

  OnBeforePrint: function () {
    Ext.ux.grid.Printer.printTitle = gpersist.STR_COL_COMPANY;
  },
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
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
   
   if (!me.hasOtherSearch){
    
    //Ext.Array.insert(items, 0, [' ', { xtype: 'textfield', fieldLabel: '公司名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchcompanyname', id: mep + 'searchcompanyname', allowBlank: true },' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);  
   }
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
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
    //  '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
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
  },
  
  OnEdit: function () {
    var me = this;
    var date = new Date();
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    tools.log('OnEdit1', date);
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!Ext.isEmpty(me.idValid)) {
      if (record && Ext.isDefined(record.validstatus) && (record.validstatus == gpersist.STR_INVALID)) {
        tools.alert('无效状态的数据不能被修改！');
        return;
      }
    }
    
    tools.log('OnEdit2', date);
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit) {
      if (record) {        
        me.winEdit.show();     
        
        tools.log('OnEdit3', date);
        
        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
        
        tools.log('OnEdit4', date);
        tools.Enabled(['coname','coshort','coheader','sortorder','cotype','costatus'],mep);
//        if (!me.OnLoadData(record)) 
//          return;
            
        tools.log('OnEdit5', date);
        me.cancelRecord= record;
        me.OnFormValidShow();
        
        tools.log('OnEdit6', date);
      }
    }
  }
  
  
});