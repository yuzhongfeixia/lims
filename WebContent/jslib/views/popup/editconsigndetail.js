Ext.define('alms.editconsigndetail', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '委托单明细',
      winWidth: 900,
      winHeight: 700
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;

    var nowdate = new Date();
    
    var paramcols = [];
    var paramfields = [];
    
    var paramitem = [
      ' ', { id: mep + 'btnAddParamBatch', text: '新增', iconCls: 'icon-add', handler: me.OnListNewParamBatch, scope: me },
      ' ', { id: mep + 'btnDeleteParam', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteParam, scope: me }
      ];
    
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_get_paramtestjudge', paramcols, paramfields, mep + '_param_');

    var paramStore = tools.CreateGridStore(tools.GetUrl(), paramfields);
    
    me.plParam = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '检测参数设置',
      margin: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 450,
//      enableColumnMove: true, 
//      suspendLayout:true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: paramcols,
      store: paramStore,
      tbar: paramitem,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListParam, scope : me }
      } 
    });
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    var sampleitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
    ];
    
    var sample = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'samplename', id: mep + 'samplename', winTitle: '选择样品',
      maxLength: 20, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: null,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:true,
      searchTools:sampleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    sample.on('griditemclick', me.OnSampleSelect, me);
    
    me.editControls = [
       { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .7, layout: 'anchor', items: [
            { xtype: 'container', anchor: '100%', layout: 'column', items: [
              { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
                sample,
                tools.FormText('样品基数', 'samplebase', mep + 'samplebase', 20, '96%', true),
                tools.FormText('生产单位', 'factname', mep + 'factname', 200, '96%', true),
                tools.FormText('样品数量', 'samplecount', mep + 'samplecount', 20, '96%', true)
              ]},
              { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
                tools.FormText('商标', 'trademark', mep + 'trademark', 20, '96%', true),
                tools.FormText('样品等级', 'samplelevel', mep + 'samplelevel', 20, '96%', true),
                tools.FormText('生产批号', 'prdcode', mep + 'prdcode', 20, '96%', true)
              ]}
           ]},
            
          { xtype:'hiddenfield',name:'sampleid',id: mep + 'sampleid'},
            tools.FormTextArea('样品状态', 'samplestatus', mep + 'samplestatus', 200, '98%', true, 8,2),
            tools.FormTextArea('抽样地点', 'getaddr', mep + 'getaddr', 200, '98%', true, 8,2)
      ]},
      
      { xtype: 'container', columnWidth: .3, layout: 'anchor', items: [
         { xtype: 'fieldset', id:mep+'gtstand', hidden:false, collapsible: true, title: '规格型号', anchor: '100%', height:150, items: [
         ]}
       ]}
     ]},
      
      
     { xtype: 'container', anchor: '100%', layout: 'column', items: [
       { xtype: 'container', columnWidth: 1, layout: 'anchor',items: [me.plParam]}
    ]}
    ];
  
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['samplename','sampleid','samplestand','factname','samplecount','getaddr',
                 'trademark','samplelevel','prdcode','samplestatus','samplebase']; 
    me.enEdits = ['samplename','sampleid','samplestand','factname','samplecount','getaddr',
                  'trademark','samplelevel','prdcode','samplestatus','samplebase'];

  },
  
  OnSampleSearch:function(){
    var me = this;
    var mep = me.mep;
    var sample = Ext.getCmp(mep+'samplename');
    
    if(sample.store){
      sample.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
//          'bsample.sampleid': tools.GetValueEncode(mep + 'searchsampleid'),
          'bsample.samplename': tools.GetValueEncode(mep + 'searchsamplename')
        });
      });
    }
    sample.store.loadPage(1);
  },
  
  paramdetail: null,
  parambatchdetail: null,
  parameterrecord: null,
  
  OnListNewParam: function () {
    var me = this, mep = me.mep;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'selectbustest', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.paramdetail.on('formlast', me.OnParameterSave, me);
      me.paramdetail.OnFormLoad();
    }
    else{
      me.paramdetail.OnFormShow();
    }
    
    var standtype1 = tools.GetValue(mep+'standtype1');
    var standtype2 = tools.GetValue(mep+'standtype2');
    var standtype3 = tools.GetValue(mep+'standtype3');
    var standtype4 = tools.GetValue(mep+'standtype4');
    var standtype5 = tools.GetValue(mep+'standtype5');

    if(standtype1 == null){
      standtype1 = '';
    }
    
    if(standtype2 == null){
      standtype2 = '';
    }
    
    if(standtype3 == null){
      standtype3 = '';
    }
    
    if(standtype4 == null){
      standtype4 = '';
    }
    
    if(standtype5 == null){
      standtype5 = '';
    }
    
    me.paramdetail.OnInitData(tools.GetValue(mep + 'sampleid'),standtype1,standtype2,standtype3,standtype4,standtype5);    
    me.paramdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.parameterrecord = null;    
  },
  
  OnParameterBatchSave: function (e, datas) {
    var me = this, mep = me.mep;
    
    if (datas) {
      for (var i = 0; i < datas.length; i++) {
        var data = datas[i];
        
        var record = me.plParam.store.model.create({});
        record.data.parameterid = data.parameterid;
        record.data.parametername = data.parametername;
        record.data.teststandard = data.teststandard;
        record.data.teststandardname = data.teststandardname;
        record.data.judgestandard = data.judgestandard;
        record.data.judgestandardname = data.judgestandardname;
        record.data.testjudge = data.testjudge;
        record.data.standvalue = data.standvalue;
        
        var newparameterid = data.parameterid;
        var newparametername = data.parametername;
        var store = me.plParam.store;
        for(var j=0;j<me.plParam.store.getCount();j++){
          var oldparameterid = store.getAt(j).data.parameterid;
          if(newparameterid == oldparameterid){
            tools.alert('"'+newparametername+'"检测参数已存在，不能再次添加！');
            return false;
          }
        }
        
        me.plParam.store.insert(me.plParam.store.getCount(), record);
      }
    }
  },
  
  OnListNewParamBatch: function () {
    var me = this, mep = me.mep;
    
    
    var standtype1 = tools.GetValue(mep+'standtype1');
    var standtype2 = tools.GetValue(mep+'standtype2');
    var standtype3 = tools.GetValue(mep+'standtype3');
    var standtype4 = tools.GetValue(mep+'standtype4');
    var standtype5 = tools.GetValue(mep+'standtype5');
   
    if(true){
    	if(standtype1 == '-2'){
    	      tools.alert('请先选择样品规格型号！');
    	      return
    	    }else if(standtype1 == null){
    	      standtype1 = '';
    	    }
    	    
    	    if(standtype2 == '-2'){
    	      tools.alert('请先选择样品规格型号！');
    	      return
    	    }else if(standtype2 == null){
    	      standtype2 = '';
    	    }
    	    
    	    if(standtype3 == '-2'){
    	      tools.alert('请先选择样品规格型号！');
    	      return
    	    }else if(standtype3 == null){
    	      standtype3 = '';
    	    }
    	    
    	    if(standtype4 == '-2'){
    	      tools.alert('请先选择样品规格型号！');
    	      return
    	    }else if(standtype4 == null){
    	      standtype4 = '';
    	    }
    	    
    	    if(standtype5 == '-2'){
    	      tools.alert('请先选择样品规格型号！');
    	      return
    	    }else if(standtype5 == null){
    	      standtype5 = '';
    	    }	
    }
    
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.parambatchdetail) {
      me.parambatchdetail = tools.GetPopupWindow('alms', 'selectbatchparam', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbtb', dataDeal: me.dataDeal})
      
      me.parambatchdetail.on('formlast', me.OnParameterBatchSave, me);
      me.parambatchdetail.OnFormLoad();
    }
    else{
      me.parambatchdetail.OnFormShow();
    }
      
    me.parambatchdetail.OnInitData(tools.GetValue(mep + 'sampleid'), standtype1, standtype2, standtype3, standtype4, standtype5);
    
    
    me.parambatchdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);   
  },
  
  OnListParam: function (e, record, item, index) {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'selectbustest', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.paramdetail.on('formlast', me.OnParameterSave, me);
      me.paramdetail.OnFormLoad();
    }
    else
      me.paramdetail.OnFormShow();
    
    me.paramdetail.OnSetData(record,tools.GetValue(mep + 'sampleid'));    
    me.paramdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.parameterrecord = record;
  },
  
  OnParameterSave: function (e, data) {
    var me = this, mep = me.mep;
    var record = me.parameterrecord;
    if (record == null) record = me.plParam.store.model.create({});
      
    record.data.parameterid = data.parameterid;
    record.data.parametername = data.parametername;
    record.data.teststandard = data.teststandard;
    record.data.teststandardname = data.teststandardname;
    record.data.judgestandard = data.judgestandard;
    record.data.judgestandardname = data.judgestandardname;
    record.data.testjudge = data.testjudge;
    record.data.standvalue = data.standvalue;
    
    var newparameterid = data.parameterid;
    var newparametername = data.parametername;
    var store = me.plParam.store;
    for(var j=0;j<me.plParam.store.getCount();j++){
      var oldparameterid = store.getAt(j).data.parameterid;
      if(newparameterid == oldparameterid){
        tools.alert('"'+newparametername+'"检测参数已存在，不能再次添加！');
        return false;
      }
    }
    
    if (me.parameterrecord)
      me.plParam.getView().refresh();
    else
      me.plParam.store.insert(me.plParam.store.getCount(), record);
  },
  
  OnListDeleteParam : function() {
    var me = this;
    
    var j = me.plParam.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plParam.store.remove(me.plParam.selModel.selected.items[0]);
    }
    
    me.plParam.getView().refresh();
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var parameterids = '';
      var parameternames = '';
      var standtype1 = '';
      var standtype2 = '';
      var standtype3 = '';
      var standtype4 = '';
      var standtype5 = '';
      var samplestand = '';
      
      for (i = 0; i < me.plParam.store.getCount(); i++) {
        var record = me.plParam.store.getAt(i).data;
        
        if (!Ext.isEmpty(parameterids)){
          parameterids += ';';
        }
              
        if (!Ext.isEmpty(parameternames)){
          parameternames += ';';
        }
          
        parameterids += record.parameterid + ':' + record.parametername + 
          ':' + record.teststandard + ':' + record.teststandardname + 
          ':' + record.judgestandard + ':' + record.judgestandardname + 
          ':' + record.testjudge + ':' + record.standvalue;
          
        parameternames += record.parametername;
      }
      
      var stand = Ext.getCmp(mep + 'gtstand');
      for(var i = 0; i < stand.items.getCount(); i++){
        if(i < stand.items.getCount()){
          if(i == 0){
            if(stand.items.items[i].getValue()!='-2'){
              standtype1 = stand.items.items[i].getValue();
//              alert(standtype1+'standtype1')
              samplestand = stand.items.items[i].getDisplayValue();
            }else{
              standtype1 = stand.items.items[i].getValue();
            }
          }
          if(i == 1){
            if(stand.items.items[i].getValue()!='-2'){
              standtype2 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }else{
              standtype2 = stand.items.items[i].getValue();
            }
          }
          if(i == 2){
            if(stand.items.items[i].getValue()!='-2'){
              standtype3 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }else{
              standtype3 = stand.items.items[i].getValue();
            }
          }
          if(i == 3){
            if(stand.items.items[i].getValue()!='-2'){
              standtype4 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }else{
              standtype4 = stand.items.items[i].getValue();
            }
          }
          if(i == 4){
            if(stand.items.items[i].getValue()!='-2'){
              standtype5 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }else{
              standtype5 = stand.items.items[i].getValue();
            }
          }
        }
      }
      
      if(standtype1 == '-2' || standtype2 == '-2' || standtype3 == '-2' || standtype4 == '-2' || standtype5 == '-2'){
        tools.alert('请选择样品规格型号！');
        return false;
      }
      
      if(tools.GetValue(mep + 'sampleid') == ''){
        tools.alert('请选择需要的样品...');
        Ext.getCmp(mep+'samplename').reset();
      }else{
        var data = { 
          sampleid: tools.GetValue(mep + 'sampleid'),
          factname: tools.GetValue(mep + 'factname'),
          samplestand: samplestand,
          samplecount:tools.GetValue(mep + 'samplecount'),
          getaddr:tools.GetValue(mep + 'getaddr'),
          samplename: tools.GetValue(mep + 'samplename'),
          trademark: tools.GetValue(mep + 'trademark'),
          samplelevel: tools.GetValue(mep + 'samplelevel'),
          prdcode: tools.GetValue(mep + 'prdcode'),
          samplestatus: tools.GetValue(mep + 'samplestatus'),
          getaddr: tools.GetValue(mep + 'getaddr'),
          samplebase: tools.GetValue(mep + 'samplebase'),
          standtype1:standtype1,
          standtype2:standtype2,
          standtype3:standtype3,
          standtype4:standtype4,
          standtype5:standtype5,
          parameternames: parameternames,
          parameterids: parameterids
        };
          
        me.fireEvent('formlast', me, data); 
        me.winEdit.hide();
      }    
    }
  },
  
  OnSampleSelect:function(render,sample){
    var me = this, mep = me.mep;
    tools.SetValue(mep + 'sampleid',sample.sampleid);
    tools.SetValue(mep + 'samplename',sample.samplename);
    
    if(sample.samplename.indexOf("水") != -1){
    	tools.SetValue(mep + 'samplecount','3-5L');
    }else if(sample.samplename.indexOf("土壤") != -1){
    	tools.SetValue(mep + 'samplecount','1KG');
    }
    
    
    me.OnGetStand();
    
    me.plParam.store.removeAll();
    me.plParam.getView().refresh();
  },
  
  OnGetStand:function(){
    var me = this, mep = me.mep;
    var sampleid = tools.GetValue(mep + 'sampleid');
    var stand = Ext.getCmp(mep + 'gtstand');
    stand.removeAll();
//    if(Ext.isEmpty(sampleid) || sampleid == gpersist.SELECT.MUST_VALUE)
//      return;
    
    var stands  = tools.JsonGet('BasGetSampleStandCount.do?bss.sampleid=' + sampleid);
    var idx = 130;
    for(var i = 0; i < stands.samplecount; i++){
      var standlabel = '';
      var standid = '';
      var typedata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
      var items = tools.JsonGet('BasGetSampleStandByLevel.do?bss.sampleid=' + sampleid + '&bss.standlevel=' + (i+1));
      if (items && items.data){
      	  
          standlabel = items.data[0].standlabel;
          standid = items.data[0].standid;
          Ext.each(items.data, function (item, index) { 
            typedata.push({ id: item.standtype, name: item.standtypename });
          });
        }
  	
  	  var item = tools.FormCombo(standlabel,standid, mep + standid, tools.GetNullStore(), '100%', false, 302);
        item.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: typedata}));
        item.setValue(gpersist.SELECT_MUST_VALUE); 
        item.on('select', me.OnStandSelect, me); 
        console.log(stand);
        stand.add(item);
   
//   /**************修改环境ph****************/   
//    
//	  var flag=true;
//	  for (var int = 0; int <(items.data).length; int++) {
//		  if(items.data[int].standtypename.indexOf('pH')!=-1){
//			flag=false;
//			break;
//			 
//		  }
//	  }
//	  
//	  if (items && items.data){
//      	  
//          standlabel = items.data[0].standlabel;
//          standid = items.data[0].standid;
//          Ext.each(items.data, function (item, index) { 
//            typedata.push({ id: item.standtype, name: item.standtypename });
//          });
//        }
//    if(flag){
//    	  var item = tools.FormCombo(standlabel,standid, mep + standid, tools.GetNullStore(), '100%', false, 302);
//          item.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: typedata}));
//          item.setValue(gpersist.SELECT_MUST_VALUE); 
//          item.on('select', me.OnStandSelect, me); 
//          stand.add(item);
//    }
//      
//      
//      
//
//  /******************************/ 
  
       }
      }, 
  
  
  
  OnStandSelect:function(){
    var me = this, mep = me.mep;
    
    me.plParam.store.removeAll();
    me.plParam.getView().refresh();
   
  },
  
  OnSetData: function (record,tranid) {
    var me = this, mep = me.mep;
    var item = record.data;
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT){
      tools.BtnsDisable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }else{
      tools.BtnsEnable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }
    tools.SetValue(mep + 'sampleid',item.sampleid);
    tools.SetValue(mep + 'factname',item.factname);
    tools.SetValue(mep + 'samplestand',item.samplestand);
    tools.SetValue(mep + 'samplecount',item.samplecount);
    tools.SetValue(mep + 'samplename',item.samplename);
    tools.SetValue(mep + 'trademark',item.trademark);       
    tools.SetValue(mep + 'prdcode',item.prdcode);
    tools.SetValue(mep + 'samplelevel',item.samplelevel);
    tools.SetValue(mep + 'samplestatus',item.samplestatus);
    tools.SetValue(mep + 'getaddr',item.getaddr);
    tools.SetValue(mep + 'samplebase',item.samplebase);
    
    me.OnGetStand();
 
    
    tools.SetValue(mep + 'standtype1',item.standtype1);
    tools.SetValue(mep + 'standtype2',item.standtype2);
    tools.SetValue(mep + 'standtype3',item.standtype3);
    tools.SetValue(mep + 'standtype4',item.standtype4);
    tools.SetValue(mep + 'standtype5',item.standtype5);
    
    me.plParam.store.removeAll();

    if(!Ext.isEmpty(item.parameterids)){
      var items = item.parameterids.split(';');
      for (var i = 0; i < items.length; i++) {
//        alert(items[i]);
        var params = items[i].split(':');
        var precord = me.plParam.store.model.create({});
        precord.data.parameterid = params[0];
        precord.data.parametername = params[1];
        precord.data.teststandard = params[2];
        precord.data.teststandardname = params[3];
        precord.data.judgestandard = params[4];
        precord.data.judgestandardname = params[5];
        precord.data.testjudge = params[6];
        precord.data.standvalue = params[7];
        
        me.plParam.store.insert(me.plParam.store.getCount(), precord);
      }
    } else {
      var param = tools.JsonGet('LabGetListBusSampleParam.do?bsp.samplecode='+item.samplecode ).data;
      if (param.length > 0) {
        for (var i = 0; i < param.length; i++) {
          var precord = me.plParam.store.model.create({});
          precord.data.parameterid = param[i].parameterid;
          precord.data.parametername = param[i].parametername;
          precord.data.judgestandard = param[i].judgestandard;
          precord.data.judgestandardname = param[i].judgestandardname;
          precord.data.teststandard = param[i].teststandard;
          precord.data.teststandardname = param[i].teststandardname;
          precord.data.testjudge = param[i].testjudge;
          precord.data.standvalue = param[i].standvalue;
          
          me.plParam.store.insert(me.plParam.store.getCount(), precord);
        }     
      }
    }
    me.plParam.getView().refresh();
  }
  
});
