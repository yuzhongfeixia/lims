Ext.define('alms.manflowauth', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '授权管理',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowauth',
      storeUrl: 'FlowSearchFlowAuth.do',
      saveUrl: 'FlowSaveFlowAuth.do',
      expUrl: 'FlowSearchFlowAuth.do',
      hasPage: true,
      hasPrevNext: true,
      deleteUrl: 'FlowCancelFlowAuth.do',
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var roleitems = [
     ' ', { xtype: 'textfield', fieldLabel: '角色编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchrole', id: mep + 'searchrole', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnRoleSearch, scope: me }
    ];
    
    var role = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '被授权角色', name: 'fa.benoderolename', id: mep + 'benoderolename', winTitle: '选择被授权角色',
      maxLength: 20, maxLengthText: '授权业务不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '被授权角色为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowroleuser',
      storeUrl: 'FlowSearchFlowRoleUser.do',
      editable:false,
      searchTools:roleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    role.on('griditemclick', me.OnRoleSelect, me);
     
    var busflow = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '授权业务', name: 'fa.busflowname', id: mep + 'busflowname', winTitle: '选择授权业务',
      maxLength: 20, maxLengthText: '授权业务不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '授权业务为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flownoderole',
      storeUrl: 'FlowSearchRoleAuth.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    busflow.on('griditemclick', me.OnBusFlowSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            busflow,
            role
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('节点名称', 'fa.flownodename', mep + 'flownodename', 10, '100%', false, 2),
            tools.FormText('被授权人', 'fa.beauthname', mep + 'beauthname', 10, '100%', false, 3),
            {xtype:'hiddenfield',name:'fa.tranid',id: mep + 'tranid'},
            {xtype:'hiddenfield',name:'fa.busflow',id: mep + 'busflow'},
            {xtype:'hiddenfield',name:'fa.benoderole',id: mep + 'benoderole'},
            {xtype:'hiddenfield',name:'fa.beauth',id: mep + 'beauth'},
            {xtype:'hiddenfield',name:'fa.noderole',id: mep + 'noderole'},
            {xtype:'hiddenfield',name:'fa.flownode',id: mep + 'flownode'},
            {xtype:'hiddenfield',name:'fa.deal.action',id: mep + 'datadeal'}
        ]}     
      ]}
    ];
    
    me.disNews = ['tranid', 'flownode', 'flownodename', 'beauth', 'beauthname'];
    me.disEdits = ['tranid', 'flownode', 'flownodename', 'beauth', 'beauthname'];
    me.enNews = ['noderole', 'authuser', 'authusername', 'benoderole', 'authdate', 'busflow', 'canceldate','busflowname','benoderolename'];
    me.enEdits = ['noderole', 'authuser', 'authusername', 'benoderole', 'authdate', 'busflow', 'canceldate','busflowname','benoderolename'];
  },
  
  OnBusFlowSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.busflow){
        tools.SetValue(mep+"busflow",item.busflow);
        tools.SetValue(mep+"busflowname",item.busflowname);
        tools.SetValue(mep+"flownode",item.flownode);
        tools.SetValue(mep+"flownodename",item.flownodename);
        tools.SetValue(mep+"noderole",item.noderole);
     }
  },
   
  OnRoleSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.noderole){
        tools.SetValue(mep+"benoderole",item.noderole);
        tools.SetValue(mep+"benoderolename",item.noderolename);
        tools.SetValue(mep+"beauth",item.userid);
        tools.SetValue(mep+"beauthname",item.username);
     }
  },
   
  OnRoleSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnRoleBeforeLoad();
      var noderolename = Ext.getCmp(mep + 'noderolename');
      noderolename.store.loadPage(1);
  },
   
  OnRoleBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var noderolename = Ext.getCmp(mep + 'noderolename');
      if(noderolename.store){
        noderolename.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'fr.noderole':tools.GetValueEncode(mep + 'searchrole')
            });
        });
      }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '授权业务', labelWidth: 80, width: 200, maxLength: 40, name: 'searchbusflowname', id: mep + 'searchbusflowname', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '被授权人姓名', labelWidth: 80, width: 200, maxLength: 40, name: 'searchbeauthname', id: mep + 'searchbeauthname', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
      ' ', { id: mep + 'btnDelete', text: '取消授权', iconCls: 'icon-delete', handler: me.OnCancel,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
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
          'fnr.busflowname':tools.GetValueEncode(mep+'searchbusflowname'),
          'fa.beauthname':tools.GetValueEncode(mep+'searchbeauthname')
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
    }
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'noderole', item.noderole);
    tools.SetValue(mep + 'authuser', item.authuser);
    tools.SetValue(mep + 'authusername', item.authusername);
    tools.SetValue(mep + 'benoderole', item.benoderole);
    tools.SetValue(mep + 'benoderolename', item.benoderolename);
    tools.SetValue(mep + 'beauth', item.beauth);
    tools.SetValue(mep + 'beauthname', item.beauthname);
    tools.SetValue(mep + 'authdate', item.authdate);
    tools.SetValue(mep + 'busflow', item.busflow);
    tools.SetValue(mep + 'flownode', item.flownode);
    tools.SetValue(mep + 'busflowname', item.busflowname);
    tools.SetValue(mep + 'flownodename', item.flownodename);
    tools.SetValue(mep + 'canceldate', item.canceldate);
    tools.SetValue(mep + 'iscancel', item.iscancel);
    
    return true;
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
  },
  
  OnCancel: function () {
    var me = this;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length > 0) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '是否取消授权？', function (btn) {
          if (btn == 'yes') {            
            me.OnBeforeDelete(datas);
            
            Ext.Ajax.request({
              url: tools.GetUrl(me.deleteUrl),
              params: me.deleteParams,
              async: false,
              method: 'POST',
              success: function (response, opts) {
                tools.ResponseAlert(response, function () {
                  me.OnSearch();
                });
              },
              failure: function (response) {
                tools.ResponseAlert(response);
              }
            });
          }
        });
      }
      else 
        tools.alert('请选择需要取消授权的业务！');
    }
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({tranid: datas[i].data.tranid});
    }

    me.deleteParams = {deletes : Ext.encode(json)};
    
    return true;
  }
  
});