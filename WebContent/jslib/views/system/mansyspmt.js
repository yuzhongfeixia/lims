Ext.define('gpersist.mansyspmt', {
  extend: 'gpersist.base.basepmt',
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, { 
      columnUrl: 'p_get_pmtlist', 
      pmtTitle: '系统参数',
      pmtType: 'SYS'
    });
    
    me.callParent(arguments);
  }
});