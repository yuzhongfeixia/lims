Ext.define('alms.prdquery', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '耗材信息综合查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdcode',
      storeUrl: 'PrdSearchPrdCode.do',
      expUrl: 'PrdSearchPrdCode.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'prdid',
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
       ' ', { xtype:'datefield', fieldLabel:'开始时间(购买)', labelWidth:100, width:200, name:'searchbegindate', id:mep + 'searchbegindate',
         format:'Y-m-d', value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1), selectOnFocus: false, allowBlank: true},
       ' ', {xtype:'datefield', fieldLabel:'结束时间(购买)', labelWidth:100, width:200, name:'searchenddate', id:mep + 'searchenddate',
         format:'Y-m-d', value:nowdate, selectOnFocus:false, allowBlank:true},
       ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 60, width: 200, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
       '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
       '', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
       '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
         id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
       ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'prdqueryhtml', html: '' },
      {xtype:'hiddenfield',name:'pc.prdid',id: mep + 'prdid'}
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
          'pc.search.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '',
          'pc.search.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : '',
          'pc.prdname': tools.GetValueEncode(mep + 'searchprdname')
        });
      });
    };
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    me.html = alms.ShowPrdQueryHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'prdqueryhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.prdid)) {
      tools.SetValue(mep + 'prdid',item.prdid);
    }
    
    me.OnShowTask(item);
    return true;
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  }
  
});
