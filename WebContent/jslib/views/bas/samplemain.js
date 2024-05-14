Ext.define('alms.samplemain', {
  extend: 'gpersist.base.busform',
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editInfo: '样品主类',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassamplemain',
      storeUrl: 'BasSearchBasSampleMain.do',
      saveUrl: 'BasSaveBasSampleMain.do',
      expUrl: 'BasSearchBasSampleMain.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'samplemain',
      hasGridSelect: true,
      hasEdit: true,
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '检测参数',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_basmainparameter',
      urlDetail: 'BasGetListBasMainParameter.do',
      detailTabs: 1,
      hasDateSearch: true,
      hasDetailEdit: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  selectparameter: null,
  tests: null,
  judges: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.selectparameter = null;
    me.tests = Ext.create('Ext.util.MixedCollection');
    me.judges = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var sampitems = [
      ' ', { xtype: 'textfield', fieldLabel: '样品大类编号', labelWidth: 90, width: 200, maxLength: 40, name: 'searchcatalog', id: mep + 'searchcatalog', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品大类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchcatalogname', id: mep + 'searchcatalogname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSamSearch, scope: me }
    ];
    
    var samplecatalog = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品大类', name: 'bsmain.samplecatalogname', id: mep + 'samplecatalogname', winTitle: '选择大类编号',
      maxLength: 20, maxLengthText: '样品大类不能超过20！', selectOnFocus: false, labelWidth: 90,
      blankText: '样品大类为空！', allowBlank: false, anchor: '96%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassamplecatalog',
      storeUrl: 'BasSearchBasSampleCatalog.do',
      editable:false,
      searchTools:sampitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    samplecatalog.on('griditemclick', me.OnCatalogSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品主类编号', 'bsmain.samplemain', mep + 'samplemain', 6, '96%', true, 1,'',90),
          samplecatalog,
          tools.FormText('检测标准名称', 'bsmain.mainstandname', mep + 'mainstandname',200, '96%', true, 4,'',90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品主类名称', 'bsmain.samplemainname', mep + 'samplemainname', 20, '100%', false, 2,'',90),
          tools.FormText('检测标准编号', 'bsmain.mainstand', mep + 'mainstand',200, '100%', true, 4,'',90),
          {xtype:'hiddenfield',name:'bsmain.samplecatalog',id: mep + 'samplecatalog'},
          {xtype:'hiddenfield',name:'bsmain.deal.action',id: mep + 'datadeal'}
        ]}     
      ]}
    ];
    
    me.disNews = ['samplemain'];
    me.disEdits = ['samplemain'];
    me.enNews = ['samplemainname', 'samplemainstand','samplecatalogname','mainstand','mainstandname'];
    me.enEdits = ['samplemainname', 'samplemainstand','samplecatalogname','mainstand','mainstandname'];
  },
  
  OnCatalogSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.samplecatalog){
      tools.SetValue(mep+"samplecatalog",item.samplecatalog);
      tools.SetValue(mep+"samplecatalogname",item.samplecatalogname);
    }
    
  },
  
  OnSamSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnSamBeforeLoad();
    var samplecatalogname = Ext.getCmp(mep + 'samplecatalogname');
    samplecatalogname.store.loadPage(1);
    
  },
 
  OnSamBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var samplecatalogname = Ext.getCmp(mep + 'samplecatalogname');
    
    if(samplecatalogname.store){
      samplecatalogname.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'bscatalog.samplecatalog':tools.GetValueEncode(mep + 'searchcatalog'),
            'bscatalog.samplecatalogname':tools.GetValueEncode(mep + 'searchcatalogname')
          });
      });
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品大类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemain', id: mep + 'searchsamplemain', allowBlank: true },
// 原始查询主类编号   修改为查询大类名称   直接改的后台      ' ', { xtype: 'textfield', fieldLabel: '样品主类编号', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemain', id: mep + 'searchsamplemain', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品主类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemainname', id: mep + 'searchsamplemainname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bsmain.samplemain': tools.GetEncode(tools.GetValue(mep + 'searchsamplemain')),
          'bsmain.samplemainname': tools.GetEncode(tools.GetValue(mep + 'searchsamplemainname'))
        });
      });
    };
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'samplemain').reset();
    Ext.getCmp(mep + 'samplemain').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.samplemain) {
          tools.SetValue(mep + 'samplemain', action.result.data.samplemain);
        }
      }
    }
  },
  
  //新增检测参数
  OnListNew: function() {
    var me = this;
    
    if (!me.selectparameter) {
      me.selectparameter = tools.GetPopupWindow('alms', 'selectparameter', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'para', dataDeal: gpersist.DATA_DEAL_NEW})
      
      me.selectparameter.on('formlast', me.OnParameterSave, me);
      me.selectparameter.OnFormLoad();
    }
    else
      me.selectparameter.OnFormShow();
      
    me.selectparameter.OnInitData(me.tests, me.judges);
    me.selectparameter.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    
    me.detailRecord = null;
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this, mep = me.tranPrefix;
    
    if (!me.selectparameter) {
      me.selectparameter = tools.GetPopupWindow('alms', 'selectparameter', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'para', dataDeal: me.dataDeal})
      
      me.selectparameter.on('formlast', me.OnParameterSave, me);
      me.selectparameter.OnFormLoad();
    }
    else
      me.selectparameter.OnFormShow();
    me.selectparameter.OnSetData(record, tools.GetValue(mep + 'samplemain'), me.tests, me.judges);
    me.selectparameter.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.detailRecord = record;
  },
  
  //ondetailrefresh
  OnDetailRefresh : function() {
    var me = this, mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'basmp.samplemain': tools.GetValue(mep + 'samplemain') 
        });
      });
      
      me.plDetailGrid.store.load();
    }
  },
  
  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix, i = 0;
    var item = tools.JsonGet(tools.GetUrl('BasGetBasSampleMain.do?bsmain.samplemain=') + id);
    if (item && !Ext.isEmpty(item.samplemain)) {
      
      tools.SetValue(mep + 'samplemain', item.samplemain);
      tools.SetValue(mep + 'samplecatalog', item.samplecatalog);
      tools.SetValue(mep + 'samplecatalogname', item.samplecatalogname);
      tools.SetValue(mep + 'samplemainname', item.samplemainname);
      tools.SetValue(mep + 'mainstand', item.mainstand);
      tools.SetValue(mep + 'mainstandname', item.mainstandname);
      
      me.tests.clear();
      var dtests = tools.JsonGet(tools.GetUrl('BasGetListBasMainParameter.do?basmp.samplemain=') + id);

      if (dtests && dtests.data) {
        for (i = 0; i < dtests.data.length; i++) {
          var test = dtests.data[i];
          if (me.tests.get(test.parameterid) != null) {
            me.tests.get(test.parameterid).push({teststandard: test.teststandard, 
              teststandardname: test.teststandardname, teststandardcode: test.teststandardcode
            });
          }
          else {
            me.tests.add(test.parameterid, [{teststandard: test.teststandard, 
              teststandardname: test.teststandardname, teststandardcode: test.teststandardcode
            }]);
          }
        }
      }
      
      me.judges.clear();
      
      var djudges = tools.JsonGet(tools.GetUrl('BasGetListBasMainParameter.do?basmp.samplemain=') + id);

      if (djudges && djudges.data) {
        for (i = 0; i < djudges.data.length; i++) {
          var judge = djudges.data[i];
          
          if (me.judges.get(judge.parameterid) != null) {
            me.judges.get(judge.parameterid).push({judgestandard: judge.judgestandard, 
              judgestandardname: judge.judgestandardname, judgestandardcode: judge.judgestandardcode
            });
          }
          else {
            me.judges.add(judge.parameterid, [{judgestandard: judge.judgestandard, 
              judgestandardname: judge.judgestandardname, judgestandardcode: judge.judgestandardcode
            }]);
          }
        }
      }
      
      me.OnDetailRefresh();
      
      return true;
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnParameterSave: function (e, data) {
    var me = this;
    var mep = me.tranPrefix;
    var record = me.detailRecord;
    
    if (record == null) record = me.plDetailGrid.store.model.create({});

    record.data.parameterid = data.parameterid;
    record.data.parametername = data.parametername;
    record.data.testjudge = data.testjudge;
    record.data.paramunit = data.paramunit;
    record.data.belongtype = data.belongtype;
    record.data.belongtypename = data.belongtypename;
    record.data.standvalue = data.standvalue;

    if (me.detailRecord)
      me.plDetailGrid.getView().refresh();
    else
      me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record); 
  },
  
  OnGetSaveParams : function() {
    var me = this, mep = me.tranPrefix;
    
    var params = [];
    var stests = [];
    var sjudges = [];
    var i = 0, j = 0;
    
    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      params.push(me.plDetailGrid.store.getAt(i).data);

      var dtests = me.tests.get(me.plDetailGrid.store.getAt(i).data.parameterid);
      if (dtests) {
        for (j = 0; j < dtests.length; j++) {

          stests.push({parameterid: me.plDetailGrid.store.getAt(i).data.parameterid, teststandard: dtests[j].teststandard })
        }
      }
      
      var djudges = me.judges.get(me.plDetailGrid.store.getAt(i).data.parameterid);
      
      if (djudges) {
        for (j = 0; j < djudges.length; j++) {
          sjudges.push({parameterid: me.plDetailGrid.store.getAt(i).data.parameterid, judgestandard: djudges[j].judgestandard })
        }
      }
    }
    
    me.saveParams = { params: Ext.encode(params), tests: Ext.encode(stests), judges: Ext.encode(sjudges)};
  }
  
});