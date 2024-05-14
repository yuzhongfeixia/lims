Ext.define('alms.editcountrygetdetail', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '样品明细',
      winWidth: 800,
      winHeight: 440
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;

    var nowdate = new Date();
    
    var paramcols = [];
    var paramfields = [];
    
    var paramitem = [
      ' ', { id: mep + 'btnAddParam', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNewParam, scope: me },
      ' ', { id: mep + 'btnDeleteParam', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteParam, scope: me },
      ' ', { id: mep + 'btnAddParamBatch', text: '批量新增', iconCls: 'icon-add', handler: me.OnListNewParamBatch, scope: me }
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
      height: 210,
      enableColumnMove: false, suspendLayout:true,
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
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
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
              tools.FormCombo('包装', 'packstatus', mep + 'packstatus', tools.ComboStore('PackStatus', gpersist.SELECT_MUST_VALUE), '96%', false, null),
              tools.FormText('等级', 'samplelevel', mep + 'samplelevel', 20, '96%', false,19),
              tools.FormDate('生产日期', 'prddate', mep + 'prddate', 'Y-m-d', '96%', true, 5,nowdate,80),
              tools.FormText('抽样量', 'samplecount', mep + 'samplecount', 20, '96%', false,23),
              tools.FormCheckCombo('抽样场所', 'getsource', mep + 'getsource', tools.ComboStore('GetSource', ''), '96%', false),
              tools.FormCombo('单位性质', 'unitcharacte', mep + 'unitcharacte', tools.ComboStore('UnitCharacte', gpersist.SELECT_MUST_VALUE), '96%', false, null),
              tools.FormText('单位名称', 'factname', mep + 'factname', 20, '96%', false,26,null),
              tools.FormText('邮编', 'factpost', mep + 'factpost', 20, '96%', false,26,null)
            ]},
            { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('商标', 'trademark', mep + 'trademark', 20, '96%', false,13),
              tools.FormCombo('标识', 'markstatus', mep + 'markstatus', tools.ComboStore('MarkStatus', gpersist.SELECT_MUST_VALUE), '96%', false, null),
              tools.FormText('证书编号', 'certcode', mep + 'certcode', 20, '96%', false,19),
              tools.FormText('生产批号', 'prdcode', mep + 'prdcode', 20, '96%', false,19),
              tools.FormText('样本基数', 'samplebase', mep + 'samplebase', 20, '96%', false,24),
              tools.FormCombo('产品认证', 'certstatus', mep + 'certstatus', tools.ComboStore('CertStatus', gpersist.SELECT_MUST_VALUE), '96%', false, null),
              tools.FormText('联系人', 'factlink', mep + 'factlink', 20, '96%', false,26,null),
              tools.FormText('电话', 'facttele', mep + 'facttele', 20, '96%', false,26,null),
              tools.FormText('传真', 'factfax', mep + 'factfax', 20, '96%', false,16,null)
            ]}
          ]},
          tools.FormTextArea('通讯地址', 'factaddr', mep + 'factaddr', 200, '98%', false, 29, 2),
          tools.FormTextArea('抽样地点', 'getaddr', mep + 'getaddr', 200, '98%', false, 29,2),
          tools.FormTextArea('样品状态', 'samplestatus', mep + 'samplestatus', 200, '98%', false, 29, 2),
          {xtype:'hiddenfield',name:'sampleid',id: mep + 'sampleid'}
        ]},
        { xtype: 'container', columnWidth: .3, layout: 'anchor', items: [
          { xtype: 'fieldset', id:mep+'gtstand', hidden:false, collapsible: true, title: '规格型号', anchor: '100%', height:360, items: [
          ]}
        ]}
      ]}
    
//      { xtype: 'fieldset', collapsible: true, title: '样品信息', anchor: '100%', items: [  
//        { xtype: 'container', anchor: '100%', layout: 'column', items: [
//          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//            sample,
//            tools.FormCombo('包装', 'packstatus', mep + 'packstatus', tools.ComboStore('PackStatus', gpersist.SELECT_MUST_VALUE), '96%', false, null),
//            tools.FormDate('生产日期', 'prddate', mep + 'prddate', 'Y-m-d', '96%', true, 5,nowdate,80),
//            tools.FormText('抽样量', 'samplecount', mep + 'samplecount', 20, '96%', false,23),
//            tools.FormCombo('抽样场所', 'getsource', mep + 'getsource', tools.ComboStore('GetSource', gpersist.SELECT_MUST_VALUE), '96%', false, null)
//          ]},
//          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//            tools.FormText('商标', 'trademark', mep + 'trademark', 20, '96%', false,13),
//            tools.FormCombo('标识', 'markstatus', mep + 'markstatus', tools.ComboStore('MarkStatus', gpersist.SELECT_MUST_VALUE), '96%', false, null),
//            tools.FormText('生产批号', 'prdcode', mep + 'prdcode', 20, '96%', false,19),
//            tools.FormText('样本基数', 'samplebase', mep + 'samplebase', 20, '96%', false,24)
//          ]},
//          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
//            tools.FormText('规格型号', 'samplestand', mep + 'samplestand', 20, '100%', false,19),
//            tools.FormText('等级', 'samplelevel', mep + 'samplelevel', 20, '100%', false,19),
//            tools.FormText('证书编号', 'certcode', mep + 'certcode', 20, '100%', false,19),
//            tools.FormCombo('产品认证', 'certstatus', mep + 'certstatus', tools.ComboStore('CertStatus', gpersist.SELECT_MUST_VALUE), '100%', false, null)
//          ]}
//        ]},
//        tools.FormTextArea('抽样地点', 'getaddr', mep + 'getaddr', 200, '100%', false, 29,2),
//        tools.FormTextArea('样品状态', 'samplestatus', mep + 'samplestatus', 200, '100%', false, 29, 2)
//      ]},
//      { xtype: 'fieldset', collapsible: true, title: '生产单位', anchor: '100%', items: [  
//        { xtype: 'container', anchor: '100%', layout: 'column', items: [
//          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//            tools.FormText('生产/进货单位', 'factname', mep + 'factname', 20, '96%', false,26,null,100),
//            tools.FormText('联系人', 'factlink', mep + 'factlink', 20, '96%', false,26,null,100)
//          ]},
//          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//            tools.FormText('电话', 'facttele', mep + 'facttele', 20, '96%', false,26,null,50),
//            tools.FormText('邮编', 'factpost', mep + 'factpost', 20, '96%', false,26,null,50)
//          ]},
//          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
//            tools.FormText('传真', 'factfax', mep + 'factfax', 20, '100%', false,16,null,50)
//          ]}
//        ]},
//        tools.FormTextArea('通讯地址', 'factaddr', mep + 'factaddr', 200, '100%', false, 29, 2,100)
//      ]},
//      {xtype:'hiddenfield',name:'sampleid',id: mep + 'sampleid'},
//      {xtype: 'container', anchor: '100%', layout: 'column', items: [
//        { xtype: 'container', columnWidth: 1, layout: 'anchor',items: [me.plParam]}
//      ]}
    ];
  
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['sampleid','samplename','packstatus','prddate','samplecount',
                 'getsource','trademark','markstatus','prdcode','samplebase','unitcharacte',
                 'samplestand','samplelevel','certcode','certstatus','getaddr','samplestatus',
                 'factname','factlink','facttele','factpost','factfax','factaddr']; 
    me.enEdits = ['sampleid','samplename','packstatus','prddate','samplecount',
                  'getsource','trademark','markstatus','prdcode','samplebase','unitcharacte',
                  'samplestand','samplelevel','certcode','certstatus','getaddr','samplestatus',
                  'factname','factlink','facttele','factpost','factfax','factaddr'];
    
  },
  
  OnSampleSearch:function(){
    var me = this;
    var mep = me.mep;
    
    var sample = Ext.getCmp(mep+'samplename');
    
    if(sample.store){
      sample.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'bsample.sampleid': tools.GetValueEncode(mep + 'searchsampleid'),
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
    else
      me.paramdetail.OnFormShow();
    
    me.paramdetail.OnInitData(tools.GetValue(mep + 'sampleid'));    
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
        me.plParam.store.insert(me.plParam.store.getCount(), record);
      }
    }
  },
  
  OnListNewParamBatch: function () {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.parambatchdetail) {
      me.parambatchdetail = tools.GetPopupWindow('alms', 'selectbatchparam', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbtb', dataDeal: me.dataDeal})
      
      me.parambatchdetail.on('formlast', me.OnParameterBatchSave, me);
      me.parambatchdetail.OnFormLoad();
    }
    else
      me.parambatchdetail.OnFormShow();

    me.parambatchdetail.OnInitData(tools.GetValue(mep + 'sampleid'));    
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
          parameterids += ',';
        }
              
        if (!Ext.isEmpty(parameternames)){
          parameternames += ',';
        }
          
        parameterids += record.parameterid + ':' + record.parametername + 
          ':' + record.teststandard + ':' + record.teststandardname + 
          ':' + record.judgestandard + ':' + record.judgestandardname;
        parameternames += record.parametername;
      }
      
      var stand = Ext.getCmp(mep + 'gtstand');
      for(var i = 0; i < stand.items.getCount(); i++){
        if(i < stand.items.getCount()){
          if(i == 0){
            if(stand.items.items[i].getValue()!='-2'){
              standtype1 = stand.items.items[i].getValue();
              samplestand = stand.items.items[i].getDisplayValue();
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
            }
          }
        }
      }
      
      if(tools.GetValue(mep + 'sampleid') == ''){
        tools.alert('请选择需要的样品...');
        Ext.getCmp(mep+'samplename').reset();
      }else{
        var data = {
          sampleid: tools.GetValue(mep + 'sampleid'),
          samplename: tools.GetValue(mep + 'samplename'),
          packstatus: tools.GetValue(mep + 'packstatus'),
          prddate: tools.GetValue(mep + 'prddate'),
          samplecount:tools.GetValue(mep + 'samplecount'),
          getsource: tools.GetValue(mep + 'getsource'),
          trademark: tools.GetValue(mep + 'trademark'),
          markstatus: tools.GetValue(mep + 'markstatus'),
          prdcode:tools.GetValue(mep+'prdcode'),
          samplebase:tools.GetValue(mep+'samplebase'),
          unitcharacte:tools.GetValue(mep+'unitcharacte'),
          samplestand:samplestand,
          samplelevel:tools.GetValue(mep+'samplelevel'),
          certcode:tools.GetValue(mep+'certcode'),
          certstatus:tools.GetValue(mep+'certstatus'),
          getaddr:tools.GetValue(mep+'getaddr'),
          samplestatus:tools.GetValue(mep+'samplestatus'),
          factname:tools.GetValue(mep+'factname'),
          factlink:tools.GetValue(mep+'factlink'),
          facttele:tools.GetValue(mep+'facttele'),
          factpost:tools.GetValue(mep+'factpost'),
          factfax:tools.GetValue(mep+'factfax'),
          factaddr:tools.GetValue(mep+'factaddr'),
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
//    me.plParam.store.removeAll();
//    me.plParam.getView().refresh();
    me.OnGetStand();
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
//      item.on('select', me.OnStandSelect, me); 
      stand.add(item);  
    }
  },
  
  OnSetData: function (record,tranid) {
    var me = this, mep = me.mep;
    var item = record.data;
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT){
      tools.BtnsDisable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }else{
      tools.BtnsEnable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }
   
    tools.SetValue(mep + 'sampleid', item.sampleid);
    tools.SetValue(mep + 'samplename', item.samplename);
    tools.SetValue(mep + 'packstatus', item.packstatus);
    tools.SetValue(mep + 'prddate', item.prddate);
    tools.SetValue(mep + 'samplecount', item.samplecount);
    tools.SetValue(mep + 'getsource', item.getsource.split(','));
    tools.SetValue(mep + 'trademark', item.trademark);
    tools.SetValue(mep + 'markstatus', item.markstatus);
    tools.SetValue(mep + 'prdcode', item.prdcode);
    tools.SetValue(mep + 'samplebase', item.samplebase);
    tools.SetValue(mep + 'samplestand', item.samplestand);
    tools.SetValue(mep + 'samplelevel', item.samplelevel);
    tools.SetValue(mep + 'certcode', item.certcode);
    tools.SetValue(mep + 'certstatus', item.certstatus);
    tools.SetValue(mep + 'getaddr', item.getaddr);
    tools.SetValue(mep + 'samplestatus', item.samplestatus);
    tools.SetValue(mep + 'factname', item.factname);
    tools.SetValue(mep + 'factlink', item.factlink);
    tools.SetValue(mep + 'facttele', item.facttele);
    tools.SetValue(mep + 'factpost', item.factpost);
    tools.SetValue(mep + 'factfax', item.factfax);
    tools.SetValue(mep + 'factaddr', item.factaddr);
    tools.SetValue(mep + 'unitcharacte', item.unitcharacte);
    me.OnGetStand();
    
    tools.SetValue(mep + 'standtype1',item.standtype1);
    tools.SetValue(mep + 'standtype2',item.standtype2);
    tools.SetValue(mep + 'standtype3',item.standtype3);
    tools.SetValue(mep + 'standtype4',item.standtype4);
    tools.SetValue(mep + 'standtype5',item.standtype5);
    me.plParam.store.removeAll();
    
    if(!Ext.isEmpty(item.parameterids)){
      var items = item.parameterids.split(',');
      for (var i = 0; i < items.length; i++) {
        var params = items[i].split(':');
        var precord = me.plParam.store.model.create({});
        precord.data.parameterid = params[0];
        precord.data.parametername = params[1];
        precord.data.teststandard = params[2];
        precord.data.teststandardname = params[3];
        precord.data.judgestandard = params[4];
        precord.data.judgestandardname = params[5];
        me.plParam.store.insert(me.plParam.store.getCount(), precord);
      }
    } else {
      var param = tools.JsonGet('LabGetListBusSampleParam.do?bsp.tranid='+tranid + '&bsp.sampleid='+item.sampleid).data;
      if (param.length > 0) {
        for (var i = 0; i < param.length; i++) {
          var precord = me.plParam.store.model.create({});
          precord.data.parameterid = param[i].parameterid;
          precord.data.parametername = param[i].parametername;
          precord.data.judgestandard = param[i].judgestandard;
          precord.data.judgestandardname = param[i].judgestandardname;
          precord.data.teststandard = param[i].teststandard;
          precord.data.teststandardname = param[i].teststandardname;
          me.plParam.store.insert(me.plParam.store.getCount(), precord);
        }     
      }
    }
    me.plParam.getView().refresh();
  }
  
});