Ext.define('alms.devquery', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备信息综合查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDev.do',
      expUrl: 'DevSearchBasDev.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'devid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var nowdate = new Date();
    
    me.OnInitGridToolBar();

    var items = [
        ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
        ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
        ' ', tools.GetToolBarCombo('searchdevstatus', mep + 'searchdevstatus', 160, '设备状态', 60, tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE))
    ];
    
    var items1 = [
        '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
        '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
          id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
        ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
      ];
     
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'devqueryhtml', html: '' },
      {xtype:'hiddenfield',name:'bd.devid',id: mep + 'devid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bd.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'bd.devstatus': tools.GetEncode(tools.GetValue(mep + 'searchdevstatus'))
        });
      });
    };
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    me.html = alms.ShowDevQueryHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devqueryhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.devid)) {
      tools.SetValue(mep + 'devid',item.devid);
    }
    
    me.OnShowTask(item);
    me.OnDetailRefresh();
    return true;
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  }
  
});
