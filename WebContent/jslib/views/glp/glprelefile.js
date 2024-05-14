Ext.define('alms.glprelefile', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP受控文件',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_relefile',
      storeUrl: 'GlpSearchGlpFileRele.do',
      saveUrl: 'GlpSaveGlpFileRele.do',
      hasGridSelect: true,
      expUrl: 'GlpSearchGlpFileRele.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var fileid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '文件编号', name: 'gfr.fileid', id: mep + 'fileid', winTitle: '选择文件',
      maxLength: 20, maxLengthText: '文件编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '文件编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basfile',
      storeUrl: 'GlpSearchGlpFile.do?gf.filetype=00',
      editable:false,
      //searchTools:bookitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
   });
    fileid.on('griditemclick', me.OnFileSelect, me);
    
    var fileowner = tools.UserPicker(mep + 'fileowner','gfr.fileowner','持有人');
    
    fileowner.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'gfr.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('文件名称', 'gfr.filename', mep + 'filename', 60, '96%', false, 3),
          tools.FormDate('发放时间', 'gfr.reledate', mep + 'reledate', 'Y-m-d H:i', '96%', true, 5),
          fileowner
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          fileid,
          tools.FormText('文件数量', 'gfr.filequan', mep + 'filequan', 30, '100%', true, 4, 'isnumber'),
          tools.FormCombo('发放回收', 'gfr.reletype', mep + 'reletype', tools.ComboStore('ReleType', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
          tools.FormText('持有人姓名', 'gfr.fileownername', mep + 'fileownername', 10, '100%', false, 8),
          {xtype:'hiddenfield',name:'gfr.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('备注', 'gfr.fileremark', mep + 'fileremark', 100, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid','filename', 'fileownername'];
    me.disEdits = ['tranid','filename', 'fileownername'];
    me.enNews = ['filequan', 'reledate', 'fileowner', 'fileremark', 'reletype','fileid','fileremark'];
    me.enEdits = ['filequan', 'reledate', 'fileowner', 'fileremark', 'reletype','fileid','fileremark'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'reletype', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '文件编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfileid', id: mep + 'searchfileid', allowBlank: true },
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
          'gfr.fileid': tools.GetEncode(tools.GetValue(mep + 'searchfileid')),
          'gfr.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
        });
      });
    };
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var reletype = Ext.getCmp(mep+'reletype').getValue();
    var fileowner = Ext.getCmp(mep+'fileowner').getValue();
    if(reletype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择发放回收类型！');
        return;
    }
    
    if(fileowner==null||fileowner==''){
      tools.alert('持有人不能为空！');
      return;
    }
      
    return true;
   },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'fileid', item.fileid);
      tools.SetValue(mep + 'filename', item.filename);
      tools.SetValue(mep + 'filequan', item.filequan);
      tools.SetValue(mep + 'reledate', item.reledate);
      tools.SetValue(mep + 'fileowner', item.fileowner);
      tools.SetValue(mep + 'fileownername', item.fileownername);
      tools.SetValue(mep + 'fileremark', item.fileremark);
      tools.SetValue(mep + 'reletype', item.reletype);

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
    var items = tools.JsonGet(tools.GetUrl('GlpGetGlpFile.do?gf.fileid=') + item.fileid);
    if (item && item.fileid) {
      tools.SetValue(mep+'fileid',item.fileid);
      tools.SetValue(mep+'filename',items.filename);
    }
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'fileowner',item.userid);
      tools.SetValue(mep+'fileownername',item.username);
   }
  }
  
  
  
});