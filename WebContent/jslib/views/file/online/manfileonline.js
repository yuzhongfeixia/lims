Ext.define('alms.manfileonline', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '电子文件阅读申请',
      winWidth: 750,
      winHeight: 220,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basfile',
      storeUrl: 'IncFileSearchBasFile.do',
      saveUrl: 'IncFileSaveIncFileOnline.do',
      expUrl: 'IncFileSearchBasFile.do',
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
      fieldLabel: '申请编号', name: 'ifo.applyid', id: mep + 'applyid', winTitle: '选择申请编号',
      maxLength: 20, maxLengthText: '申请编号不能超过20个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '申请编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_loanapply',
      storeUrl: 'IncFileSearchLoanApplyForRegister.do',
      editable:false,
//      searchTools:fileitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    applyid.on('griditemclick', me.OnFileSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('申请编号', 'ifo.tranid', mep + 'tranid', 30, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('文件名称', 'ifo.filename', mep + 'filename', 50, '100%', false,3),
          {xtype:'hiddenfield',name:'ifo.deal.action',id: mep + 'datadeal'},
          {xtype:'hiddenfield',name:'ifo.fileid',id: mep + 'fileid'}
        ]}     
      ]},
      tools.FormTextArea('申请理由', 'ifo.applyreason', mep + 'applyreason', 200, '100%', true,11,8)
    ];
    
    me.disNews = ['tranid', 'fileid', 'filename'];
    me.disEdits = ['tranid','fileid', 'filename'];
    me.enNews = ['applyereason'];
    me.enEdits = ['applyereason'];
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
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: '申请', iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnView', text:'查看', iconCls: 'icon-edit', handler: me.OnShow,scope: me}, 
      //'-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
  
  OnShow: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的文件！');
    
    if(record){
      var file = tools.JsonGet('IncFileHasAuthToRead.do',{'ifo.tranuser':gpersist.UserInfo.user.userid,'ifo.fileid':record.fileid});
      if(file && file.fileid){
        alms.PopupFileShow('文件预览', 'FileDownFile.do', file.fileurl, file.filename);
      }else{
        tools.alert('您无权查看该文件！');
      }
    }
  },
  
  OnNew: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要申请的文件！');
    
    if (!me.OnBeforeEdit(record)) 
      return;
    
    if (!me.winEdit)
      me.OnCreateEditWin();
    
    if(me.winEdit && record) {     
      var date = new Date();
      
      me.winEdit.show();
      
      me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, false);
      
      if (!me.OnLoadData(record))
        return;
        
      me.cancelRecord = record;
      me.OnFormValidShow();
//      me.OnInitData();      
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bf.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
//    if (me.hasPrevNext) {
//      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
//        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
//        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
//      ]);
//    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'fileid', item.fileid);
    tools.SetValue(mep + 'filename', item.filename);
    tools.SetValue(mep + 'applyreason', item.applyreason);

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