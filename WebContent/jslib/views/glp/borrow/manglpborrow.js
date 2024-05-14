Ext.define('alms.manglpborrow', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '文件查看',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_incfileregister',
      storeUrl: 'GlpSearchGlpFileRegister.do',
      expUrl: 'GlpSearchGlpFileRegister.do?type='+'reading',
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
    
    me.editControls = [
          {xtype:'hiddenfield',name:'gfre.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'gfre.fileid',id: mep + 'fileid'}
    ];
    
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-edit', handler: me.OnShow, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnShow: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的文件！');
    if(record.borrowtype != '06'){ //06 在线阅读
      tools.alert('未申请在线阅读');
    }else{
      var file = tools.JsonGet('GlpGetGlpFile.do?gf.fileid='+record.fileid);
      
      if(file && file.fileid){
        alms.PopupFileShow('文件预览', 'FileDownFile.do', file.fileurl, record.filename);
      }else{
        tools.alert('该文件不存在');
      }
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'gfre.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename')),
          'gfre.userid': tools.GetEncode(gpersist.UserInfo.user.userid),
          'type': 'reading'
        });
      });
    };
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'fileid', item.fileid);
    return true;
  }
  
});