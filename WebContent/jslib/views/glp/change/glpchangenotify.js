Ext.define('alms.glpchangenotify', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件更改通知',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_changenotify',
      storeUrl: 'GlpSearchGlpFileNotify.do',
      saveUrl: 'GlpSaveGlpFileNotify.do',
      hasGridSelect: true,
      expUrl: 'GlpSearchGlpFileNotify.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var fileid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '文件编号', name: 'gfn.fileid', id: mep + 'fileid', winTitle: '选择文件',
      maxLength: 20, maxLengthText: '文件编号不能超过30个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '文件编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_changeapply',
      storeUrl: 'GlpGetListGlpFileChangeForNotify.do',
      editable:false,
//      searchTools:fileitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    fileid.on('griditemclick', me.OnFileSelect, me);
    
    var writeuser = tools.UserPicker(mep + 'writeuser','gfn.writeuser','填表人');
    
    writeuser.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'gfn.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('文件名称', 'gfn.filename', mep + 'filename', 60, '96%', false, 3),
          tools.FormText('通知书编号', 'gfn.notifyid', mep + 'notifyid', 20, '96%', false, 5),
          writeuser,
          //tools.FormDate('填表日期', 'gfn.writedate', mep + 'writedate', 'Y-m-d H:i', '96%', true, 9)
          tools.FormDate('填表日期', 'gfn.writedate', mep + 'writedate', 'Y-m-d', '96%', true, 9)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          fileid,
          tools.FormText('替代文件', 'gfn.replacefile', mep + 'replacefile', 60, '100%', false, 4),
          //tools.FormDate('生效日期', 'gfn.effectdate', mep + 'effectdate', 'Y-m-d H:i', '100%', true, 6),
          tools.FormDate('生效日期', 'gfn.effectdate', mep + 'effectdate', 'Y-m-d', '100%', true, 6),
          tools.FormText('填表人姓名', 'gfn.writeusername', mep + 'writeusername', 10, '100%', false, 8),
          {xtype:'hiddenfield',name:'gfn.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('更改情况', 'gfn.filechange', mep + 'filechange', 200, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid','filename','replacefile', 'writeusername'];
    me.disEdits = ['tranid','filename','replacefile', 'writeusername'];
    me.enNews = [ 'fileid', 'filechange', 'effectdate', 'notifyid', 'writeuser', 'writedate', 'tranuser', 'tranusername', 'trandate'];
    me.enEdits = ['fileid', 'filechange', 'effectdate', 'notifyid', 'writeuser', 'writedate', 'tranuser', 'tranusername', 'trandate'];
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
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
          'gfn.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'gfn.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
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
      tools.SetValue(mep + 'filechange', item.filechange);
      tools.SetValue(mep + 'effectdate', item.effectdate);
      tools.SetValue(mep + 'notifyid', item.notifyid);
      tools.SetValue(mep + 'replacefile', item.replacefile);
      tools.SetValue(mep + 'writeuser', item.writeuser);
      tools.SetValue(mep + 'writeusername', item.writeusername);
      tools.SetValue(mep + 'writedate', item.writedate);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);

      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
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
  
  OnFileSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && item.changefileid) {
      tools.SetValue(mep+'fileid',item.changefileid);
      tools.SetValue(mep+'filename',item.changefilename);
      tools.SetValue(mep+'replacefile',item.replacefilename);
    }
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'writeuser',item.userid);
      tools.SetValue(mep+'writeusername',item.username);
   }
  }
   
});