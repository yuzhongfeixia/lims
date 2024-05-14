Ext.define('alms.editcontractsamp', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '样品',
      winWidth: 800,
      winHeight: 500
    });

    me.callParent(arguments);
  },
  
  plParam:null,
  params: null,
  
  OnInitConfig: function () {
    var me = this;
    me.plParam = null;
    me.params = null;
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
    
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_get_contractreviewparam', paramcols, paramfields, mep + '_param_');

    var paramStore = tools.CreateGridStore(tools.GetUrl(), paramfields);
    
    me.plParam = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '检测项目',
      margin: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 400,
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
    
     var sampitems = [
     ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampid', id: mep + 'searchsampid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampname', id: mep + 'searchsampname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampSearch, scope: me }
    ];
     
     var sampleid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品编号', name: 'sampleid', id: mep + 'sampleid', winTitle: '请选择样品编号',
      maxLength: 10, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品编号为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     sampleid.on('griditemclick', me.OnSampSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          sampleid
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品名称', 'samplename', mep + 'samplename', 20, '100%', true)
        ]}
      ]},
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: 1, layout: 'anchor',items: [me.plParam]}
      ]}
    ];
  
    me.disNews = ['samplename'];
    me.disEdits = ['samplename'];
    me.enNews = ['sampleid']; 
    me.enEdits = ['sampleid'];
    
  },
  
  OnSampSelect:function(render,item){
     var me = this;
     var mep = me.mep;
     
     if(item && item.sampleid){
        tools.SetValue(mep+"sampleid",item.sampleid);
        tools.SetValue(mep+"samplename",item.samplename);
        me.plParam.store.removeAll();
     }
   },
   
  OnSampSearch:function(){
      var me = this;
      var mep = me.mep;
      me.OnSampBeforeLoad();
      var sampleid = Ext.getCmp(mep + 'sampleid');
      sampleid.store.loadPage(1);
   },
   
  OnSampBeforeLoad:function(){
      var me = this;
      var mep = me.mep;
      var sampleid = Ext.getCmp(mep + 'sampleid');
      if(sampleid.store){
        sampleid.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'bsample.sampleid':tools.GetValueEncode(mep + 'searchsampid'),
              'bsample.samplename':tools.GetValueEncode(mep + 'searchsampname')
            });
        });
      }
   },
  
  paramdetail: null,
  parambatchdetail: null,
  parameterrecord: null,
  
  OnListNewParam: function () {
    var me = this, mep = me.mep;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'editcontparam', 
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
        for(var j = 0;j < me.plParam.store.getCount();j++ ){
          if(data.parameterid == me.plParam.store.getAt(j).data.parameterid){
            me.plParam.store.removeAt(j);
          }
        }
        var record = me.plParam.store.model.create({});
        
        record.data.parameterid = data.parameterid;
        record.data.parametername = data.parametername;
        
        me.plParam.store.insert(me.plParam.store.getCount(), record);
      }
      me.plParam.getView().refresh();
    }
  },
  
  OnListNewParamBatch: function () {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.parambatchdetail) {
      me.parambatchdetail = tools.GetPopupWindow('alms', 'editcontbatchparam', 
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
      me.paramdetail = tools.GetPopupWindow('alms', 'editcontparam', 
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
    
    for(var j = 0;j < me.plParam.store.getCount();j++ ){
          if(data.parameterid == me.plParam.store.getAt(j).data.parameterid){
            me.plParam.store.removeAt(j);
          }
     }  
    record.data.parameterid = data.parameterid;
    record.data.parametername = data.parametername;
    
    if (me.parameterrecord)
      me.plParam.getView().refresh();
    else
      me.plParam.store.insert(me.plParam.store.getCount(), record);
      
     me.plParam.getView().refresh();
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
      
      var lefts = [];
      for (i = 0; i < me.plParam.store.getCount(); i++) {
        lefts.push({parameterid: me.plParam.store.getAt(i).data.parameterid, parametername: me.plParam.store.getAt(i).data.parametername});
      }
      
      me.params.removeAtKey(tools.GetValue(mep + 'sampleid'));
      me.params.add(tools.GetValue(mep + 'sampleid'), lefts);
      
      var data = {sampleid: tools.GetValue(mep + 'sampleid'),
        samplename: tools.GetValue(mep + 'samplename')};
        
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (params) {
    var me = this, mep = me.mep;
    me.callParent();
    me.plParam.store.removeAll();
    me.plParam.getView().refresh();
    me.params = params;
  },
  
  OnSetData: function (record,tranid,params) {
    var me = this, mep = me.mep;
    var item = record.data;
    
    tools.SetValue(mep + 'sampleid',item.sampleid);
    tools.SetValue(mep + 'samplename',item.samplename);
    me.plParam.store.removeAll();
    
    me.params = params;
    
    if (params.get(record.data.sampleid) != null) {      
       var listparams = params.get(record.data.sampleid); 
      
      for (var i = 0; i < listparams.length; i++) {
        var param = listparams[i];
        
        var rparam = me.plParam.store.model.create({});
  
        rparam.data.parameterid = param.parameterid;
        rparam.data.parametername = param.parametername;
            
        me.plParam.store.insert(me.plParam.store.getCount(), rparam); 
      }
    }
    me.plParam.getView().refresh();
  }
  
});