Ext.define('alms.selectbustest', {
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
      tools.FormCombo('检测参数', 'bcsample.parameterid', mep + 'parameterid', tools.GetNullStore(), '100%', false, 301),
      tools.FormCombo('检测方法', 'bcsample.teststandard', mep + 'teststandard', tools.GetNullStore(), '100%', false, 302),
      tools.FormCombo('判定依据', 'bcsample.judgestandard', mep + 'judgestandard', tools.GetNullStore(), '100%', false, 303)
    ];
        
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['parameterid','teststandard','judgestandard'];
    me.enEdits = ['parameterid','teststandard','judgestandard'];
    
    Ext.getCmp(mep + 'parameterid').on('select', me.OnParameterSelect, me);
  },
  
  OnParameterSelect: function () {
    var me = this, mep = me.mep;
    
    var testdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var judgedata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var param = tools.GetValue(mep + 'parameterid');
    var judge = Ext.getCmp(mep + 'judgestandard');
    var test = Ext.getCmp(mep + 'teststandard');
    
    if (param == gpersist.SELECT_MUST_VALUE) {  
      if (judge) {
        judge.bindStore(tools.GetNullStore());
      }
      if (test) {
        test.bindStore(tools.GetNullStore());
      }
    }
    else {
      if (test) {
        var tests = tools.JsonGet('BasGetListBasSampleTestByParam.do?bst.parameterid=' + param+ '&bst.sampleid=' +  me.sampleid);
        if (tests && tests.data)
          Ext.each(tests.data, function (item, index) { 
            testdata.push({ id: item.teststandard, name: item.teststandardname });
          });
        
        test.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: testdata}))
        tools.SetValue(mep + 'teststandard',gpersist.SELECT_MUST_VALUE);
      }
      
      if (judge) {
        var judges = tools.JsonGet('BasGetListBasSampleJudgeByParam.do?bsj.sampleid=' + me.sampleid+ '&bsj.parameterid=' + param);
        
        if (judges && judges.data)
          Ext.each(judges.data, function (item, index) { 
            judgedata.push({ id: item.judgestandard, name: item.judgestandardname });
          });
        
        judge.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: judgedata}))
        tools.SetValue(mep + 'judgestandard',gpersist.SELECT_MUST_VALUE);
      }
    }
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var parameterid = tools.GetValue(mep + 'parameterid');
      if (Ext.isEmpty(parameterid) || (parameterid == gpersist.SELECT_MUST_VALUE)) {
        tools.alert('请选择检测参数！')  
        return;
      }
      
      var item = tools.JsonGet('BasGetListBasTestCollect.do?btc.sampleid=' + me.sampleid + '&btc.parameterid=' + parameterid).data[0];
      
      var data = {parameterid: item.parameterid,
        parametername: item.parametername,
        teststandard: item.teststandard, 
        teststandardname: item.teststandardname,
        judgestandard: item.judgestandard, 
        judgestandardname: item.judgestandardname,
        testjudge: item.testjudge,
        standvalue: item.standvalue};
        
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (sampleid,standtype1,standtype2,standtype3,standtype4,standtype5) {
    this.plEdit.getForm().reset(); 
    this.OnGetParameter(sampleid,standtype1,standtype2,standtype3,standtype4,standtype5);
  },
  
  OnSetData: function (record,sampleid) {
    var me = this, mep = me.mep;
    
    me.OnGetParameter(sampleid);   
    tools.SetValue(mep + 'parameterid', record.data.parameterid);
    
    me.OnParameterSelect();
    
    if (!Ext.isEmpty(record.data.teststandard))
      tools.SetValue(mep + 'teststandard', record.data.teststandard);
    
    if (!Ext.isEmpty(record.data.judgestandard))
      tools.SetValue(mep + 'judgestandard', record.data.judgestandard);   
    
  },
  
  OnGetParameter: function (sampleid,standtype1,standtype2,standtype3,standtype4,standtype5) {
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
        alms.SetParameterCombo(null, me.sampleid, mep + 'parameterid',standtype1,standtype2,standtype3,standtype4,standtype5);
      }
    }
    
    me.OnParameterSelect();
  }
});