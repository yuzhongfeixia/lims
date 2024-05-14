Ext.define('alms.manbascity', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '城市管理',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bascity',
      storeUrl: 'BasSearchBasCity.do',
      saveUrl: 'BasSaveBasCity.do',
      expUrl: 'BasSearchBasCity.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'cityid',
      hasGridSelect: true,
      deleteUrl: 'BasDeleteBasCity.do'
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('城市编号', 'bct.cityid', mep + 'cityid', 14, '96%', false, 1),
          tools.FormTreeCombo('所属省份', tools.GetUrl('BasGetLocation.do?property=city'), 'bct.provinceid', mep + 'provinceid', '96%', false, 2)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('城市名称', 'bct.cityname', mep + 'cityname', 20, '100%', false, 3),
          {xtype:'hiddenfield',name:'bct.deal.action',id: mep + 'datadeal'}
        ]}
      ]}
    ];
    
    me.disNews = ['cityid'];
    me.disEdits = ['cityid'];
    me.enNews = [ 'cityname', 'provinceid'];
    me.enEdits = ['cityname', 'provinceid'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', {xtype:'textfield',fieldLabel:'城市名称',labelWidth:60,width:180,maxLength:20,name:'searchcityname',id:mep + 'searchcityname',allowBlank:true},
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
      Ext.Array.insert(items, 2, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }]);
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 
    

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (me.gridStore) {
      me.gridStore.on('beforeload', function(store, options) {
        Ext.apply(store.proxy.extraParams, {
          'bct.cityname' : tools.GetValueEncode(mep + 'searchcityname')
        });
      });
    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep + 'cityid', item.cityid);
    tools.SetValue(mep + 'cityname', item.cityname);
    tools.SetValue(mep + 'provinceid', item.provinceid);
    tools.SetValue(mep + 'provicnename', item.provicnename);
    
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'cityid').reset();
    Ext.getCmp(mep + 'cityid').focus(true, 500);
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({cityid: datas[i].data.cityid});
    }
    
    me.deleteParams = {deletes : Ext.encode(json)};
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.cityid) {
          tools.SetValue(mep + 'cityid', action.result.data.cityid);
        }
      }
    }
  }
  
});