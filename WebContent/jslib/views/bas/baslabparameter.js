Ext.define('alms.baslabparameter', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editInfo: '检测室可检测项目',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_baslabparameter',
      storeUrl: 'BasSearchBasLabParameter.do',
      saveUrl: 'BasSaveBasLabParameter.do',
      expUrl: 'BasSearchBasLabParameter.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext:'labid',
      hasPrevNext:false
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('检测室', 'blp.labid', mep + 'labid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90),
          tools.FormText('检测室主任', 'blp.labuser', mep + 'labuser', 6, '96%', true, 1,'',90)
        ]},
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCheckCombo('检测参数', 'blp.parameterid', mep + 'parameterid', tools.ComboStore('LabParams'), '100%', true, 4,100),
          tools.FormCheckCombo('检测样品分类', 'blp.labcate', mep + 'labcate', tools.ComboStore('SampleCate'), '100%', true, 3,100),
          {xtype: 'hiddenfield', name: 'blp.deal.action', id: mep + 'datadeal'},
          {xtype: 'hiddenfield', name: 'blp.labcatename', id: mep + 'labcatename'}
        ]} 
      ]},
      tools.FormTextArea('具备功能', 'blp.labremark', mep + 'labremark', 200, '100%', true, null, 2,90)
    ];
    
    me.disNews = [];
    me.disEdits = ['labid'];
    me.enNews = ['labid','parameterid','labuser','labcate','labremark'];
    me.enEdits = ['parameterid','labuser','labcate','labremark'];
    
    Ext.getCmp(mep+'labcate').on('select',function(){
      tools.SetValue(mep+'labcatename',Ext.getCmp(mep+'labcate').getDisplayValue().split(','));
    });
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (record && !Ext.isEmpty(record.labid)) {
      var item = tools.JsonGet(tools.GetUrl('BasGetBasLabParameterForInfo.do?blp.labid=') + record.labid);
      tools.SetValue(mep + 'labid', record.labid);
      tools.SetValue(mep + 'labuser', item.labuser);
      tools.SetValue(mep + 'labcate', item.labcate.split(','));
      tools.SetValue(mep + 'labcatename', Ext.getCmp(mep+'labcate').getDisplayValue().split(','));
      tools.SetValue(mep + 'labremark', item.labremark);
      tools.SetValue(mep + 'parameterid', record.parameterid.split(','));
      return true;
    }
    else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '检测项目'));
    }
  }
  
});