Ext.define('alms.basparameter', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '检测参数',
      winWidth: 750,
      winHeight:240,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basparameter',
      storeUrl: 'BasSearchBasParameter.do',
      saveUrl: 'BasSaveBasParameter.do',
      expUrl: 'BasSearchBasParameter.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'parameterid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
  
    var formitems = [
       ' ', { xtype: 'textfield', fieldLabel: '原始记录表名称', labelWidth: 100, width: 200, maxLength: 40, name: 'searchrecordname', id: mep + 'searchrecordname', allowBlank: true },
       ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnRecordSearch, scope: me }
    ];
    var connectitems = [
                     ' ', { xtype: 'textfield', fieldLabel: '关联参数名称', labelWidth: 100, width: 200, maxLength: 40, name: 'searchconnectparameter', id: mep + 'searchconnectparameter', allowBlank: true },
                     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnparameterSearch, scope: me }
                  ];

    var formname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '原始记录表', name: 'bpara.formname', id: mep + 'formname', winTitle: '选择原始记录表',
      maxLength: 40, maxLengthText: '原始记录表名称不能超过40个字符！', selectOnFocus: false, labelWidth: 90,
      blankText: '原始记录表不能为空！', allowBlank: true, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_frmrecord',
      storeUrl: 'FormSearchFrmRecord.do',
      editable:false,
      searchTools:formitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    formname.on('griditemclick', me.OnFormSelect, me);
    
   
                 
    var connectparameter = Ext.create('Ext.ux.GridPicker', {
        fieldLabel: '关联参数', name: 'bpara.connectparameter', id: mep + 'connectparameter', winTitle: '选择关联参数',
        maxLength: 40, maxLengthText: '参数名称不能超过40个字符！', selectOnFocus: false, labelWidth: 90,
        blankText: '参数不能为空！', allowBlank: true, anchor: '96%', tabIndex: 2,
        columnUrl: 'SysSqlColumn.do?sqlid=p_get_basparameter',
        storeUrl: 'BasSearchBasParameter.do',
        editable:false,
        searchTools:connectitems,
        hasPage: true, pickerWidth: 600, pickerHeight: 450
      });
      
    connectparameter.on('griditemclick', me.OnParamSelect, me);
      
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('检测参数编号', 'bpara.parameterid', mep + 'parameterid', 6, '96%', true, 1,'',90),
          tools.FormCombo('检测室', 'bpara.labid', mep + 'labid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, 5,90),
          tools.FormText('检测参数检索', 'bpara.parametercn', mep + 'parametercn', 100, '96%', true, 3,'',90),
          tools.FormCombo('参数状态', 'bpara.parameterstatus', mep + 'parameterstatus', tools.ComboStore('parameterstatus', gpersist.SELECT_MUST_VALUE), '96%', true, 5,90)
        
          ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('检测参数名称', 'bpara.parametername', mep + 'parametername', 50, '96%', false, 2,'',90),                                                           
          tools.FormText('英文名称', 'bpara.parameteren', mep + 'parameteren',40, '96%', true, 4,'',90),
          formname,
          connectparameter,
          {xtype:'hiddenfield',name:'bpara.formid',id: mep + 'formid'},
          {xtype:'hiddenfield',name:'bpara.deal.action',id: mep + 'datadeal'}
          
        ]}     
      ]}
    ];
    
    me.disNews = ['parameterid'];
    me.disEdits = ['parameterid'];
    me.enNews = ['parametername', 'parametercn', 'parameteren', 'parameterstatus','parametername','formname','labid','connectparameter'];
    me.enEdits = [ 'parametername', 'parametercn', 'parameteren', 'parameterstatus','parametername','formname','labid','connectparameter'];
  },
  
  OnRecordSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    var formname = Ext.getCmp(mep+'formname');
    if(formname.store){
      formname.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'fr.formname': tools.GetValueEncode(mep + 'searchrecordname')
        });
      });
    }
    formname.store.loadPage(1);
  },
  
  OnparameterSearch:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    var connectparameter = Ext.getCmp(mep+'connectparameter');
	    if(connectparameter.store){
	    	connectparameter.store.on('beforeload', function (store, options) {   
	        Ext.apply(store.proxy.extraParams, {
	          'bpara.connectparameter': tools.GetValueEncode(mep + 'searchconnectparameter')
	        });
	      });
	    }
	    connectparameter.store.loadPage(1);
	  },
  
 OnParamSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.parameterid)){
      tools.SetValue(mep + 'connectparameter', item.parametername);
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
    
  },
  
   OnFormSelect: function(render, item){
	    var me = this;
	    var mep = me.tranPrefix;
	    if(item && !Ext.isEmpty(item.formid)){
	        tools.SetValue(mep + 'formid', item.formid);
	        tools.SetValue(mep + 'formname', item.formname);
	      } else{
	        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
	      }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '检测参数编号', labelWidth: 90, width: 200, maxLength: 40, name: 'searchparameterid', id: mep + 'searchparameterid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchparametername', id: mep + 'searchparametername', allowBlank: true },
      '-', tools.GetToolBarCombo('searchlabid', mep + 'searchlabid', 200, '检测室名称', 90, tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE)),
      '-', tools.GetToolBarCombo('searchparameterstatus', mep + 'searchparameterstatus', 200, '参数状态', 90, tools.ComboStore('parameterstatus', gpersist.SELECT_MUST_VALUE)),
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
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
//    tools.SetValue(mep + 'testunit', gpersist.SELECT_MUST_VALUE);
// tools.SetValue(mep + 'connectparameter', gpersist.SELECT_MUST_VALUE);

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {
    	 if((tools.GetValue(mep + 'searchlabid')>0)&&(tools.GetValue(mep + 'searchparameterstatus')>0)){
    		 
    		 Ext.apply(store.proxy.extraParams, {
    	          'bpara.parameterid': tools.GetEncode(tools.GetValue(mep + 'searchparameterid')),
    	          'bpara.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparametername')),
    	          'bpara.labid': tools.GetEncode(tools.GetValue(mep + 'searchlabid')),
    	          'bpara.parameterstatus': tools.GetEncode(tools.GetValue(mep + 'searchparameterstatus'))
   	        });
    	 }else if((tools.GetValue(mep + 'searchlabid')>0)&&(tools.GetValue(mep + 'searchparameterstatus')<0)){
    		 Ext.apply(store.proxy.extraParams, {
   	          'bpara.parameterid': tools.GetEncode(tools.GetValue(mep + 'searchparameterid')),
   	          'bpara.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparametername')),
   	          'bpara.labid': tools.GetEncode(tools.GetValue(mep + 'searchlabid')),
   	          'bpara.parameterstatus': tools.GetEncode('null')
   	        }); 
    	 }
    	 else if((tools.GetValue(mep + 'searchlabid')<0)&&(tools.GetValue(mep + 'searchparameterstatus')>0)){
    		 Ext.apply(store.proxy.extraParams, {
      	          'bpara.parameterid': tools.GetEncode(tools.GetValue(mep + 'searchparameterid')),
      	          'bpara.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparametername')),
      	          'bpara.labid': tools.GetEncode('null'),
      	        'bpara.parameterstatus': tools.GetEncode(tools.GetValue(mep + 'searchparameterstatus'))
      });
    
   }else {
		 Ext.apply(store.proxy.extraParams, {
 	          'bpara.parameterid': tools.GetEncode(tools.GetValue(mep + 'searchparameterid')),
 	          'bpara.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparametername')),
 	          'bpara.labid': tools.GetEncode('null'),
 	         'bpara.parameterstatus': tools.GetEncode('null')
              });
          }
      });
    }
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'parameterid', item.parameterid);
    tools.SetValue(mep + 'parametername', item.parametername);
    tools.SetValue(mep + 'formid', item.formid);
    tools.SetValue(mep + 'formname', item.formname);
    tools.SetValue(mep + 'labid', item.labid);
    tools.SetValue(mep + 'parameterstatus', item.parameterstatus);
    tools.SetValue(mep + 'connectparameter', item.connectparameter);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'parameterid').reset();
    Ext.getCmp(mep + 'parameterid').focus(true, 500);
  },
  
  OnBeforeSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
//    var testunit = Ext.getCmp(mep+'testunit').getValue();
    
//    if (tools.InvalidForm(me.plEdit.getForm())) {
//      if(testunit == gpersist.SELECT_MUST_VALUE ){
//        tools.alert('请选择计量单位！');
//        return;
//      }
//    }
    return true;
  },
   
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.parameterid) {
          tools.SetValue(mep + 'parameterid', action.result.data.parameterid);
        }
      }
    }
  }
  
});