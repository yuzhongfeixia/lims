Ext.define('alms.busgetaudit', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '抽样单审核',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busget',
      storeUrl: 'LabSearchBusGet.do',
      saveUrl: 'LabSaveBusGetAudit.do',
      expUrl: 'LabSearchBusGet.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'busgethtml', html: '' },
      {xtype:'hiddenfield',name:'bg.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bg.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnPass', text :'审核通过', iconCls : 'icon-deal', handler : me.OnPass, scope : me },
      ' ', { id : mep + 'btnReject', text :'审核驳回', iconCls : 'icon-deal', handler : me.OnReject, scope : me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bg.flowstatus': '02'
        });
      });
    };
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
    me.OnAfterCreateEdit();
  },
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择取样单！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.tabMain.setActiveTab(1);
      me.OnShowGet(record);
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
  },
  
  OnShowGet:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = alms.ShowGetHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'busgethtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      tools.BtnsEnable(['btnPass','btnReject'],mep);
    }
    return true;
  },
  
  OnPass:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'flowstatus','30');
    me.OnSubmit();
  },
  
  OnReject:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'flowstatus','31');
    me.OnSubmit();
  },
  
  OnSubmit : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SUBMIT);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          me.OnAfterSubmit();
          tools.alert(Ext.String.format('审核成功', me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnPass','btnReject'],mep);
  }
  
});
