Ext.define('gpersist.base.basewin', {

  extend: 'Ext.util.Observable',

  mid: 0,  
  tranPrefix: '0000',
  childPrefix: '',
  selectParams: [],
  title: '',
  sauth: '',
  mep: '',
  
  constructor : function(config) {
    var me = this;
    
    me.callParent(arguments);
    
    me.OnInitConfig();
    
    Ext.apply(me, config);

    me.sauth = tools.GetMenuAuth(me.tranPrefix);
    tools.GetParams(me.selectParams);
    
    me.mep = me.tranPrefix + me.childPrefix;
    
    this.addEvents(
      'formlast'
    );  
  },
  
  OnFormLoad : function() {
    
  },
  
  OnInitConfig: function () {
    var me = this;
    
    me.mid = 0;
    me.tranPrefix = '0000';
    me.selectParams = [];
    me.title = '';
    me.sauth = '';
    me.childPrefix = '';
    me.mep = '';
  },
  
  OnBeforeReset: function () {
    return true;
  },
  
  OnResetForm: function () {
    var me = this;
    
    if (!me.OnBeforeReset())
      return;
    
    me.OnFormLoad(me.mid);
  }
});