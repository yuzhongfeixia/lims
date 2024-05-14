Ext.define('alms.stduse', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '耗材试剂使用记录',
      winWidth: 750,
      winHeight: 350,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stduse',
      storeUrl: 'StdSearchStdUse.do',
      saveUrl: 'StdSaveStdUse.do',
      hasGridSelect: true,
      expUrl: 'StdSearchStdUse.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var prditems = [
        ' ', { xtype: 'textfield', fieldLabel: '物质名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
        ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnPrdSearch, scope: me }
    ];
    
    var prdname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '库存物品', name: 'su.stdname', id: mep + 'stdname', winTitle: '选择库存物品',
      maxLength: 50, maxLengthText: '库存物品名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
      blankText: '库存物品为空！', allowBlank: false, anchor: '100%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_prdcode',
      storeUrl: 'PrdSearchPrdCode.do',
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     
     var samitems = [
         ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamcode', id: mep + 'searchsamcode', allowBlank: true },
         ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamname', id: mep + 'searchsamname', allowBlank: true },
         ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSamSearch, scope: me }
     ];
     
     var samplename = Ext.create('Ext.ux.GridPicker', {
       fieldLabel: '检测样品', name: 'su.samplename', id: mep + 'samplename', winTitle: '选择检测样品',
       maxLength: 50, maxLengthText: '样品名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
       blankText: '样品为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
       columnUrl: 'SysSqlColumn.do?sqlid=p_get_buslab',
       storeUrl: 'LabSearchBusTaskForUse.do',
       editable:false,
       searchTools:samitems,
       hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
     
     samplename.on('griditemclick', me.OnSampleSelect, me);
     
     var url = '';
     url = 'BasGetListBasSampleParameterDetail.do'
     me.samParStore = new Ext.data.JsonStore({
      proxy:{
        type:'ajax',
        url:url,
        reader:{
          type:'json',
          root:'data'
        }
      },
      fields:[{name:'id',mapping:'parameterid'},{name:'name',mapping:'parametername'},{name:'standvalue',mapping:'standvalue'}],
      autoLoad:true
    });
     
     var samParCombo =tools.FormCheckCombo('检测项目', 'su.parameterid', mep + 'parameterid', me.samParStore,'100%', false, 6);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'su.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('规格', 'su.stdsize', mep + 'stdsize', 30, '96%', false, 3),
          tools.FormDate('使用时间', 'su.indate', mep + 'indate', 'Y-m-d', '96%', false, 5),
          samplename
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
//          tools.FormText('物质名称', 'su.stdname', mep + 'stdname', 30, '100%', false, 2),
          prdname,
          tools.FormText('使用数量', 'su.stdquanity', mep + 'stdquanity', 5, '100%', false, 4),
          tools.FormText('有效期(月)', 'su.validmonth', mep + 'validmonth', 12, '100%', false, 6,'isnumber'),
          samParCombo,
          {xtype:'hiddenfield',name:'su.sampletran',id: mep + 'sampletran'},
          {xtype:'hiddenfield',name:'su.sampleid',id: mep + 'sampleid'},
          {xtype:'hiddenfield',name:'su.parametername',id: mep + 'parametername'},
          {xtype:'hiddenfield',name:'su.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('使用情况', 'su.usesituation', mep + 'usesituation', 200, '100%', true, 9, 4),
      tools.FormTextArea('备注', 'su.remark', mep + 'remark', 200, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = [ 'stdname', 'stdsize', 'stdquanity', 'indate', 'validmonth', 'usesituation', 'remark', 'samplename','parameterid'];
    me.enEdits = [ 'stdname', 'stdsize', 'stdquanity', 'indate', 'validmonth', 'usesituation', 'remark', 'samplename','parameterid'];
    
    Ext.getCmp(mep+'parameterid').on('select',function(){
      tools.SetValue(mep+'parametername',Ext.getCmp(mep+'parameterid').getDisplayValue());
    })
  },
  
  OnSampleSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var sampleid = item.sampleid;
    
    if(item && !Ext.isEmpty(item.sampleid)){
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'sampletran', item.sampletran);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'parameterid', '');
      me.samParStore.load({params:{sampleid:sampleid}});
      
      var parameterid = Ext.getCmp(mep + 'parameterid');        
      if (parameterid) {
        parameterid.reset();
      }
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnPrdSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnPrdBeforeLoad();
    var prd = Ext.getCmp(mep + 'stdname');
    prd.store.loadPage(1);
 },
 
 OnPrdBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var prd = Ext.getCmp(mep + 'stdname');
    if(prd.store){
      prd.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'pc.prdname':tools.GetValueEncode(mep + 'searchprdname')
          });
      });
    }
 },
 
 OnSamSearch:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.OnSamBeforeLoad();
   var sam = Ext.getCmp(mep + 'samplename');
   sam.store.loadPage(1);
 },

 OnSamBeforeLoad:function(){
   var me = this;
   var mep = me.tranPrefix;
   var sam = Ext.getCmp(mep + 'samplename');
   if(sam.store){
     sam.store.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'btsg.samplename':tools.GetValueEncode(mep + 'searchsamname'),
           'btsg.samplecode':tools.GetValueEncode(mep + 'searchsamcode')
         });
     });
   }
 },
  
 OnPrdSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item){
       tools.SetValue(mep+"prdid",item.prdid);
       tools.SetValue(mep+"stdname",item.prdname);
       tools.SetValue(mep+"stdsize",item.prdstandard);
       tools.SetValue(mep+"validmonth",item.validmonth);
    }
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '物质名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchstdname', id: mep + 'searchstdname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'su.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'su.stdname': tools.GetEncode(tools.GetValue(mep + 'searchstdname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'stdname', item.stdname);
      tools.SetValue(mep + 'stdsize', item.stdsize);
      tools.SetValue(mep + 'stdquanity', item.stdquanity);
      tools.SetValue(mep + 'indate', item.indate);
      tools.SetValue(mep + 'validmonth', item.validmonth);
      tools.SetValue(mep + 'usesituation', item.usesituation);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
      me.samParStore.load({url:'BasGetListBasSampleParameterDetail.do',params:{sampleid:item.sampleid}});
      tools.SetValue(mep + 'parameterid', item.parameterid.split(','));
      tools.SetValue(mep + 'parametername', item.parametername);
      tools.SetValue(mep + 'sampletran', item.sampletran);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  }
   
});