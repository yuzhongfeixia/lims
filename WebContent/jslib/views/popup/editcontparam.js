Ext.define('alms.editcontparam', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '选择检测参数',
      winWidth: 400,
      winHeight: 250
    });

    me.callParent(arguments);
    
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      tools.FormCombo('检测参数', 'bcsample.parameterid', mep + 'parameterid', tools.GetNullStore(), '100%', false, 301)
    ];
        
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['parameterid'];
    me.enEdits = ['parameterid'];
    
  },
  
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var parameterid = tools.GetValue(mep + 'parameterid');
      if (Ext.isEmpty(parameterid) || (parameterid == gpersist.SELECT_MUST_VALUE)) {
        tools.alert('请选择检测参数！')  
        return;
      }
      
      var data = {parameterid: parameterid, parametername: Ext.getCmp(mep + 'parameterid').getDisplayValue()};
        
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (sampleid) {
    this.plEdit.getForm().reset(); 
    
    this.OnGetParameter(sampleid);
  },
  
  OnSetData: function (record,sampleid) {
    var me = this, mep = me.mep;
    
    me.OnGetParameter(sampleid);   
    tools.SetValue(mep + 'parameterid', record.data.parameterid);
    
  },
  
  OnGetParameter: function (sampleid) {
    var me = this, mep = me.mep;
    me.sampleid = sampleid;
    
    var parameter = Ext.getCmp(mep + 'parameterid');
    
    if (Ext.isEmpty(me.sampleid) || (me.sampleid == gpersist.SELECT_MUST_VALUE))
      return;
      
    if (parameter) {
      if (sampleid == gpersist.SELECT_MUST_VALUE) {
        parameter.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetParameterCombo(gpersist.SELECT_MUST_VALUE, me.sampleid, mep + 'parameterid');
      }
    }
  }
});