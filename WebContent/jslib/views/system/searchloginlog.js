Ext.define('gpersist.searchloginlog', {
  extend: 'gpersist.base.listdateform',  
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, { 
      columnUrl: 'p_get_loginlog', 
      storeUrl: gpersist.ACTION_SEARCH_LOGIN, 
      expUrl: gpersist.ACTION_SEARCH_LOGIN
    });
    
    me.callParent(arguments);
  },
  
  OnBeforePrint: function (params) {
    Ext.ux.grid.Printer.printTitle = '登录日志';
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    me.plGrid.store.on('beforeload', function (store, options) {      
      Ext.apply(store.proxy.extraParams, {
        'req.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '', 
        'req.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : ''});
    });
  }
});