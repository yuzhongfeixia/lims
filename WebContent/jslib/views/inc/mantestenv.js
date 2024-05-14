Ext.define('alms.mantestenv', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '特定检测环境',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_inctestenv',
      storeUrl: 'IncSearchIncTestEnv.do',
      saveUrl: 'IncSaveIncTestEnv.do',
      hasGridSelect: true,
      expUrl: 'IncSearchIncTestEnv.do',
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
    
    var paraitems = [
       ' ', { xtype: 'textfield', fieldLabel: '检测参数编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchparamid', id: mep + 'searchparamid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchparamname', id: mep + 'searchparamname', allowBlank: true },
       ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnParaSearch, scope: me }
     ];
     
    var parametername = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '检测参数', name: 'ite.parametername', id: mep + 'parametername', winTitle: '请选检测参数',
      maxLength: 30, maxLengthText: '检测参数不能超过30字！', selectOnFocus: false, labelWidth: 80,
      blankText: '检测参数！', allowBlank: false, anchor: '100%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basparameter',
      storeUrl: 'BasSearchBasParameter.do',
      editable:false,
      searchTools:paraitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
    parametername.on('griditemclick', me.OnParaSelect, me);
     
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'ite.tranid', mep + 'tranid', 20, '96%', false, 1,'',90),
          tools.FormCombo('检测室', 'ite.labid', mep + 'labid',  tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),
          tools.FormText('验收记录编号', 'ite.acceptnum', mep + 'acceptnum', 20, '96%', true, 5,'',90),
          tools.FormDate('记录日期', 'ite.trandate', mep + 'trandate', 'Y-m-d', '96%', false, 7,nowdate,90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          parametername,
          tools.FormText('房号', 'ite.labnum', mep + 'labnum', 10, '100%', false, 4),
          tools.FormText('记录人', 'ite.tranusername', mep + 'tranusername', 10, '100%', false,6),
          {xtype:'hiddenfield',name:'ite.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'ite.parameterid',id: mep + 'parameterid'},
          {xtype:'hiddenfield',name:'ite.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('检测环境指标', 'ite.envindica', mep + 'envindica', 100, '100%', false, 8, 6,90),
      tools.FormTextArea('设施与措施', 'ite.facmeasure', mep + 'facmeasure', 200, '100%', true, 9,8,90)
    ];

    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['parameterid', 'parametername', 'labid', 'labnum', 'envindica', 'facmeasure', 'acceptnum'];
    me.enEdits = ['parameterid', 'parametername', 'labid', 'labnum', 'envindica', 'facmeasure', 'acceptnum '];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername',gpersist.UserInfo.user.username );
    tools.SetValue(mep + 'labid', gpersist.SELECT_MUST_VALUE);
  },
  
  OnParaSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnParaBeforeLoad();
      var parametername = Ext.getCmp(mep + 'parametername');
      parametername.store.loadPage(1);
  },
   
  OnParaBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var parametername = Ext.getCmp(mep + 'parametername');
      if(parametername.store){
        parametername.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'bpara.parameterid':tools.GetValueEncode(mep + 'searchparamid'),
              'bpara.parametername':tools.GetValueEncode(mep + 'searchparamname')
            });
        });
      }
   },
   
  OnParaSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.parameterid){
        tools.SetValue(mep+"parameterid",item.parameterid);
        tools.SetValue(mep+"parametername",item.parametername);
     }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 80, width: 180, maxLength: 40, name: 'searchparametername', id: mep + 'searchparametername', allowBlank: true },
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
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'ite.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'ite.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparametername'))
        });
      });
    };
  },
   
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    if(record && !Ext.isEmpty(record.tranid)){
      var item = tools.JsonGet(tools.GetUrl('IncGetIncTestEnv.do?ite.tranid=') + record.tranid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'parameterid', item.parameterid);
      tools.SetValue(mep + 'parametername', item.parametername);
      tools.SetValue(mep + 'labid', item.labid);
      tools.SetValue(mep + 'labnum', item.labnum);
      tools.SetValue(mep + 'envindica', item.envindica);
      tools.SetValue(mep + 'facmeasure', item.facmeasure);
      tools.SetValue(mep + 'acceptnum', item.acceptnum);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
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
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var labid = Ext.getCmp(mep+'labid').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(labid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择检测室！');
        return;
      }
    }
    
    return true;
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