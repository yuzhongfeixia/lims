Ext.define('alms.manglpregister', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: ['BorrowType'],
      editInfo: 'GLP文件借阅登记',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_incfileregister',
      storeUrl: 'GlpSearchGlpFileRegister.do',
      saveUrl: 'GlpSaveGlpFileRegister.do',
      expUrl: 'GlpSearchGlpFileRegister.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var applyid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '申请编号', name: 'gfre.applyid', id: mep + 'applyid', winTitle: '选择申请编号',
      maxLength: 20, maxLengthText: '申请编号不能超过20个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '申请编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_loanapply',
      storeUrl: 'GlpSearchGlpLoanForRegister.do',
      editable:false,
//      searchTools:fileitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    applyid.on('griditemclick', me.OnFileSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('登记编号', 'gfre.tranid', mep + 'tranid', 30, '96%', false, 1),
          tools.FormText('文件名称', 'gfre.filename', mep + 'filename', 50, '96%', false,3),
          tools.FormText('借阅人', 'gfre.username', mep + 'username', 10, '96%', false,5)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          applyid,
          tools.FormCombo('借阅类型', 'gfre.borrowtype', mep + 'borrowtype', tools.ComboStore('BorrowType', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
          {xtype:'hiddenfield',name:'gfre.deal.action',id: mep + 'datadeal'},
          {xtype:'hiddenfield',name:'gfre.userid',id: mep + 'userid'},
          {xtype:'hiddenfield',name:'gfre.fileid',id: mep + 'fileid'}
        ]}     
      ]},
      tools.FormTextArea('备注', 'gfre.remark', mep + 'remark', 200, '100%', true,11,3)
    ];
    
    me.disNews = ['tranid', 'fileid', 'filename', 'userid', 'username'];
    me.disEdits = ['tranid','applyid' ,'fileid', 'filename', 'userid', 'username'];
    me.enNews = [ 'applyid', 'borrowdate', 'borrowtype', 'isborrow', 'isreturn', 'returndate', 'remark', 'tranuser', 'tranusername', 'trandate'];
    me.enEdits = [ 'borrowdate', 'borrowtype', 'isborrow', 'isreturn', 'returndate', 'remark', 'tranuser', 'tranusername', 'trandate'];
  },
  
  OnFileSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tranid) {
      tools.SetValue(mep+'applyid',item.tranid);
      tools.SetValue(mep+'filename',item.filename);
      tools.SetValue(mep+'fileid',item.fileid);
      tools.SetValue(mep+'userid',item.tranuser);
      tools.SetValue(mep+'username',item.tranusername);
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '登记编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchuserid', id: mep + 'searchuserid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnView', text: '归还', iconCls: 'icon-edit', handler: me.OnDeal, scope: me },
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
    tools.SetValue(mep + 'borrowtype',gpersist.SELECT_MUST_VALUE);

  },
  
  OnDeal: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要归还的文件！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if (me.winEdit && record) {
      me.winEdit.show();
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
      if (!me.OnLoadData(record))
        return;
      
      me.dataDeal = gpersist.DATA_DEAL_DEAL;
      me.cancelRecord = record;
      me.OnFormValidShow();
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'gfre.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename')),
          'gfre.userid': tools.GetEncode(tools.GetValue(mep + 'searchuserid'))
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var borrowtype = Ext.getCmp(mep+'borrowtype').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(borrowtype == gpersist.SELECT_MUST_VALUE ){
          tools.alert('请选借阅类型！');
          return;
      }
    }
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'applyid', item.applyid);
    tools.SetValue(mep + 'fileid', item.fileid);
    tools.SetValue(mep + 'filename', item.filename);
    tools.SetValue(mep + 'userid', item.userid);
    tools.SetValue(mep + 'username', item.username);
    tools.SetValue(mep + 'borrowdate', item.borrowdate);
    tools.SetValue(mep + 'borrowtype', item.borrowtype);
    tools.SetValue(mep + 'isborrow', item.isborrow);
    tools.SetValue(mep + 'isreturn', item.isreturn);
    tools.SetValue(mep + 'returndate', item.returndate);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);

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
  }
  
});