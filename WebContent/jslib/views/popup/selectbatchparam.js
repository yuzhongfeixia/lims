Ext.define('alms.selectbatchparam', {
  extend: 'gpersist.base.editwin',  
  
  labid: '',
  sampleid: '',
  getmethod: '',
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '批量选择检测参数',
      winWidth: 400,
      winHeight: 250
    });

    me.callParent(arguments);
    
    me.labid = '';
    me.sampleid = '';
    me.getmethod = '';
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      tools.FormCheckCombo('检测参数', 'bcsample.parameterid', mep + 'parameterid', tools.GetNullStore(), '100%', true, 501)
    ];
        
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['parameterid'];
    me.enEdits = ['parameterid'];
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var parameterids = tools.GetValue(mep + 'parameterid');
      
      
      if (Ext.isEmpty(parameterids)) {
        tools.alert('请选择检测参数！')  
        return;
      }
      
      var datas = [];
      var connectdatas=[];
      var params = parameterids.split(',');

      for (var i = 0; i < params.length; i++) {
        
        var item = tools.JsonGet('BasGetListBasTestCollect.do?btc.sampleid=' + me.sampleid + '&btc.parameterid=' + params[i]).data[0];
        
        datas.push({
          parameterid: item.parameterid,
          parametername: item.parametername,
          teststandard: item.teststandard, 
          teststandardname: item.teststandardname,
          judgestandard: item.judgestandard, 
          judgestandardname: item.judgestandardname,
          testjudge: item.testjudge,
          standvalue: item.standvalue
        });
      }

      if (datas.length <= 0) {
        tools.alert('请选择检测参数！')  
        return;
      }
      
      for (var i = 0; i < params.length; i++) {
          
         var item = tools.JsonGet('BasSearchBasParameter.do?bpara.parameterid=' + params[i] +'&start=0&limit=50').data[0] ;
       if(item.parameterstatus==3||me.sampleid=='001283'){
    	   
    	   if(!Ext.isEmpty(item.connectparameter)){
          	 var flag=false;
          	 for (var j = 0; j < params.length; j++) {
          		 
          		 if(params[j]==item.connectparameterID){
          			var item2 = tools.JsonGet('BasSearchBasParameter.do?bpara.parameterid=' + params[j] +'&start=0&limit=50').data[0] ;
          			if(!Ext.isEmpty(item2.connectparameter)){
          	          	 var flag2=false;
          	          	 for (var n = 0; n < params.length; n++) {
          	          		if(params[n]==item2.connectparameterID){
          	          		flag2=true;
          	          		}
          	          	 }
          	           if(!flag2){
                			 tools.alert('请选择关联参数'+item2.connectparameter+'！') ;
                			 
                			 return;
                		 }
          	          	 }
          			 flag=true;
          		 }
          	 }
          		 if(!flag){
          			 tools.alert('请选择关联参数'+item.connectparameter+'！') ;
          			 
          			 return;
          		 }
             }
       }
        
         
         
         
        }
        
      me.fireEvent('formlast', me, datas); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (sampleid,standtype1,standtype2,standtype3,standtype4,standtype5) {
    this.plEdit.getForm().reset(); 
    this.OnGetParameter(sampleid,standtype1,standtype2,standtype3,standtype4,standtype5);
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
  }
});