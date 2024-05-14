Ext.define('gpersist.base.baseform', {

  mid: 0,  
  tranPrefix: '0000',
  selectParams: [],
  title: '',
  sauth: '',
  
  constructor : function(config) {
    var me = this;
    
    me.callParent(arguments);
    
    me.OnInitConfig();
    
    Ext.apply(me, config);

    me.sauth = tools.GetMenuAuth(me.tranPrefix);
    tools.GetParams(me.selectParams);
    me.OnFormLoad();
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
  },
  
  OnBeforeReset: function () {
    return true;
  },
  
  OnResetForm: function () {
    var me = this;
    
    if (!me.OnBeforeReset())
      return;
    
    me.OnFormLoad(me.mid);
  },
  
  OnBlurDecimal2: function (e) {
    e.setValue(Ext.util.Format.number(e.getValue(), lms.FMT_DECIMAL2));
  },
  
  OnBlurDecimal3: function (e) {
    e.setValue(Ext.util.Format.number(e.getValue(), lms.FMT_DECIMAL3));
  }
});