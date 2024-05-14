Ext.define('alms.selectdevtest', {
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
      tools.FormCombo('样品名称', 'devtest.sampleid', mep + 'sampleid', tools.GetNullStore(), '100%', false, 301),
      tools.FormCombo('检测参数', 'devtest.parameterid', mep + 'parameterid', tools.GetNullStore(), '100%', false, 302)
    ];
        
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['sampleid','parameterid'];
    me.enEdits = ['sampleid','parameterid'];
    
    Ext.getCmp(mep + 'sampleid').on('select', me.OnParameterSelect, me);
  },
  
  OnParameterSelect: function () {
    var me = this, mep = me.mep;
    
    var parameterdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var sampleid = tools.GetValue(mep + 'sampleid');
    var parameterid = Ext.getCmp(mep + 'parameterid');
    
    if (sampleid == gpersist.SELECT_MUST_VALUE) {  
      if (parameterid) {
        parameterid.bindStore(tools.GetNullStore());
      }
    }
    else {
      if (parameterid) {
        var parameter = tools.JsonGet('BasGetListBasSampleParameter.do?bsampara.sampleid=' + sampleid);
        if (parameter && parameter.data)
          Ext.each(parameter.data, function (item, index) { 
            parameterdata.push({ id: item.parameterid, name: item.parametername });
          });
        
        parameterid.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: parameterdata}))
        tools.SetValue(mep + 'parameterid',gpersist.SELECT_MUST_VALUE);
      }
    }
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var sampleid = tools.GetValue(mep + 'sampleid');
      if (Ext.isEmpty(sampleid) || (sampleid == gpersist.SELECT_MUST_VALUE)) {
        tools.alert('请选择样品编号！')  
        return;
      }
      
      var parameterid = tools.GetValue(mep + 'parameterid');
      if (Ext.isEmpty(parameterid) || (parameterid == gpersist.SELECT_MUST_VALUE)) {
        tools.alert('请选择检测参数！')  
        return;
      }
      
      var data = {sampleid: sampleid, samplename: Ext.getCmp(mep + 'sampleid').getDisplayValue(),
        parameterid: parameterid, parametername: Ext.getCmp(mep + 'parameterid').getDisplayValue()};
        
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
    tools.SetValue(mep + 'sampleid', record.data.sampleid);
    
    me.OnParameterSelect();
    
//    if (!Ext.isEmpty(record.data.teststandard))
//      tools.SetValue(mep + 'teststandard', record.data.teststandard);
//    
  },
  
  OnGetParameter: function (sampleid) {
    var me = this, mep = me.mep;
    var sampleid = Ext.getCmp(mep + 'sampleid');
    var sampleids = Ext.getCmp(mep + 'sampleid').getValue();
    if (sampleid) {    
        me.SetParameterCombo(gpersist.SELECT_MUST_VALUE, mep + 'sampleid');
        
    }
    me.OnParameterSelect();
  },
  
  SetParameterCombo :function (type, id) {
    var typedata = tools.GetTypeList(type);
    var items = tools.JsonGet('BasGetListBasSample.do');
    if (items && items.data)
      Ext.each(items.data, function (item, index) { 
        typedata.push({ id: item.sampleid, name: item.samplename });
      });
    
    alms.SetStoreToCombo(type, id, typedata);
  }
  
  
  
});