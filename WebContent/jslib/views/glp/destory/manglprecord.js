Ext.define('alms.manglprecord', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件销毁登记',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_glpfiledestroyrecord',
      storeUrl: 'GlpSearchGlpFileDestroyRecord.do',
      saveUrl: 'GlpSaveGlpFileDestroyRecord.do',
      hasGridSelect: true,
      expUrl: 'GlpSearchGlpFileDestroyRecord.do',
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
    
    var devitems = [
      ' ', { xtype: 'textfield', fieldLabel: '销毁编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var applyid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '申请编号', name: 'gfdr.applyid', id: mep + 'applyid', winTitle: '选择申请编号',
      maxLength: 20, maxLengthText: '申请编号不能超过20个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '申请编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_destoryapply',
      storeUrl: 'GlpSearchGlpFileDestroyForRecord.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    applyid.on('griditemclick', me.OnApplySelect, me);
    
    var allowusername = tools.UserPicker(mep + 'allowusername','gfdr.allowusername','批准人');
     
    allowusername.on('griditemclick', me.OnUserSelect, me);
    
    var monitusername = tools.UserPicker(mep + 'monitusername','gfdr.monitusername','监销人');
     
    monitusername.on('griditemclick', me.OnMonitUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('登记编号', 'gfdr.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('文件名称', 'gfdr.filename', mep + 'filename', 30, '96%', false, 3),
          allowusername,
          monitusername
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          applyid,
          tools.FormDate('销毁日期', 'gfdr.destroydate', mep + 'destroydate', 'Y-m-d', '100%', false, 4,nowdate),
          tools.FormDate('批准日期', 'gfdr.allowdate', mep + 'allowdate', 'Y-m-d', '100%', false, 6,nowdate),
          tools.FormDate('监销日期', 'gfdr.monitdate', mep + 'monitdate', 'Y-m-d', '100%', false, 8,nowdate),
          {xtype:'hiddenfield',name:'gfdr.fileid',id: mep + 'fileid'},
          {xtype:'hiddenfield',name:'gfdr.allowuser',id: mep + 'allowuser'},
          {xtype:'hiddenfield',name:'gfdr.monituser',id: mep + 'monituser'},
          {xtype:'hiddenfield',name:'gfdr.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('销毁原因', 'gfdr.destroyreason', mep + 'destroyreason', 200, '100%', true, 9, 4),
      tools.FormTextArea('备注', 'gfdr.remark', mep + 'remark', 200, '100%', true, 10, 4)
    ];
    
    me.disNews = ['tranid','filename','fileid', 'destroyreason'];
    me.disEdits = ['tranid','filename','fileid', 'destroyreason','applyid'];
    me.enNews = ['applyid','destroydate', 'allowuser', 'allowusername', 'allowdate', 'moniuser', 'monitusername', 'monitdate', 'remark', 'tranuser', 'tranusername', 'trandate'];
    me.enEdits = [ 'destroydate', 'allowuser', 'allowusername', 'allowdate', 'moniuser', 'monitusername', 'monitdate', 'remark', 'tranuser', 'tranusername', 'trandate'];
  },
  
  OnApplySelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.fileid) {
      tools.SetValue(mep+'applyid',item.tranid);
      tools.SetValue(mep+'filename',item.filename);
      tools.SetValue(mep+'fileid',item.fileid);
      tools.SetValue(mep+'destroyreason',item.destroyreason);
    }
  },
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
       tools.SetValue(mep+"allowuser",item.userid);
       tools.SetValue(mep+"allowusername",item.username);
    }
   },
   
  OnMonitUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"monituser",item.userid);
      tools.SetValue(mep+"monitusername",item.username);
    }
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
      ' ', { xtype: 'textfield', fieldLabel: '资料名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
//      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
//      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
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
          'gfdr.filename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'applyid', item.applyid);
      tools.SetValue(mep + 'fileid', item.fileid);
      tools.SetValue(mep + 'destroyreason', item.destroyreason);
      tools.SetValue(mep + 'destroydate', item.destroydate);
      tools.SetValue(mep + 'allowuser', item.allowuser);
      tools.SetValue(mep + 'allowusername', item.allowusername);
      tools.SetValue(mep + 'allowdate', item.allowdate);
      tools.SetValue(mep + 'moniuser', item.moniuser);
      tools.SetValue(mep + 'monitusername', item.monitusername);
      tools.SetValue(mep + 'monitdate', item.monitdate);
      tools.SetValue(mep + 'remark', item.remark);
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
  
  OnDevSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnDevBeforeLoad();
    var applyid = Ext.getCmp(mep + 'applyid');
    applyid.store.loadPage(1);
 },
 
OnDevBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var applyid = Ext.getCmp(mep + 'applyid');
    if(applyid.store){
      applyid.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'gfd.tranid':tools.GetValueEncode(mep + 'searchtranid')
          });
      });
    }
 }
  
});