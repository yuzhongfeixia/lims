Ext.define('alms.glpdestoryapply', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件销毁申请',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_destoryapply',
      storeUrl: 'GlpSearchGlpFileDestroy.do',
      saveUrl: 'GlpSaveGlpFileDestroy.do',
      hasGridSelect: true,
      expUrl: 'GlpSearchGlpFileDestroy.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var fileitems = [
      ' ', { xtype: 'textfield', fieldLabel: '文件编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchfile', id: mep + 'searchfile', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchnamefile', id: mep + 'searchnamefile', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnFileSearch, scope: me }
    ];
    
    var fileid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '文件编号', name: 'gfd.fileid', id: mep + 'fileid', winTitle: '选择文件',
      maxLength: 20, maxLengthText: '文件编号不能超过20个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '文件编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basfile',
      storeUrl: 'GlpSearchGlpFile.do',
      editable:false,
      searchTools:fileitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    fileid.on('griditemclick', me.OnFileSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('销毁编号', 'gfd.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('文件名称', 'gfd.filename', mep + 'filename', 30, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          fileid,
          tools.FormDate('销毁时间', 'gfd.destroydate', mep + 'destroydate', 'Y-m-d', '100%', true, 9),
          {xtype:'hiddenfield',name:'gfd.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('销毁原因', 'gfd.destroyreason', mep + 'destroyreason', 200, '100%', true, 2, 6)
    ];
    
    me.disNews = ['tranid','filename'];
    me.disEdits = ['tranid','filename'];
    me.enNews = ['destroydate','fileid','destroyreason'];
    me.enEdits = ['destroydate','fileid','destroyreason'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '销毁编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchttranid', id: mep + 'searchtranid', allowBlank: true },
//      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'gfd.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'fileid', item.fileid);
      tools.SetValue(mep + 'filename', item.filename);
      tools.SetValue(mep + 'destroyreason', item.destroyreason);
      tools.SetValue(mep + 'destroydate', item.destroydate);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+'tranuser',item.username);
   }
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
  
  OnFileSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnFileBeforeLoad();
      var file = Ext.getCmp(mep + 'fileid');
      file.store.loadPage(1);
   },
   
  OnFileBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var file = Ext.getCmp(mep + 'fileid');
      if(file.store){
        file.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'gf.fileid':tools.GetValueEncode(mep + 'searchfile'),
              'gf.filename':tools.GetValueEncode(mep + 'searchnamefile')
            });
        });
      }
   },
  
  OnFileSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.fileid) {
      tools.SetValue(mep+'fileid',item.fileid);
      tools.SetValue(mep+'filename',item.filename);
    }
  },
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : '提交', iconCls : 'icon-deal', handler : me.OnSave, scope : me },  
     //'-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSave, scope: me },
     '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler: function () { me.winEdit.hide(); }, scope : me }
   ];
   
//   if (me.hasSubmit) {
//     Ext.Array.insert(me.editToolItems, 2, [
//       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
//     ]);
//   }
   
   if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
  }

   
   
});