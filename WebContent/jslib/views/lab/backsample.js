Ext.define('alms.backsample', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '退样管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustask',
      storeUrl: 'LabSearchBusTaskBack.do',
      saveUrl: 'LabSaveBusTaskBack.do',
      expUrl: 'LabSearchBusTaskBack.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'taskid',
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
      ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'bustaskhtml', html: '' },
      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'bt.backcount',id: mep + 'backcount'},
      {xtype:'hiddenfield',name:'bt.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bt.deal.action',id: mep + 'datadeal'}
    ];
  },
  

  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-',  { id: mep + 'btnBack', text: '退样', iconCls: 'icon-deal', handler: me.OnBack, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    me.deitems = [];
  },
  
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择任务单！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.tabMain.setActiveTab(1);
      me.OnShowBusTask(record);
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
    
    tools.BtnsEnable(['btnDetailSave','btnBack'],mep);
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    if (record && !Ext.isEmpty(record.taskid)) {
      tools.SetValue(mep + 'taskid', record.taskid);
      tools.SetValue(mep + 'flowstatus', record.flowstatus);
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
    
  },
  
  OnShowBusTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = alms.ShowTaskHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(html);
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.region = 'center';
  },
  
  
  OnBack:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnCreateDetailWin();
    
    if(me.winDetail){      
      me.winDetail.show();
    };
    me.dataDeal = gpersist.DATA_DEAL_SUBMIT;
    me.OnAuthDetailEditForm(false);
  },
  
  OnCreateDetailWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    var nowdate = new Date();
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    };
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: '退样', iconCls: 'icon-save', handler: me.OnFormBack, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
    ];
    
    
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: 1, layout: 'anchor', items: [
          tools.FormText('退样量', 'fbackcount', mep + 'fbackcount', 20, '96%', false, 8,null)
        ]}
      ]}
    ];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews =  ['fbackcount'];
    me.enEdits = ['fbackcount'];
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: '退样',
      width: 400,
      height: 100,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit]
    });
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_SUBMIT) {
        if (action.result.data.taskid) {
          var taskid = action.result.data.taskid;
          tools.SetValue(mep + 'taskid', taskid);
          var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+taskid);
          me.OnShowBusTask(bustask);
        };
      };
    };
  },
  
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnDetailSave','btnBack'],mep);
  },
  
  OnSubmit : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
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
          tools.alert(Ext.String.format('退样成功！', me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnFormBack:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'backcount',tools.GetValue(mep + 'fbackcount'));
    me.OnSubmit();
  }
  
});
