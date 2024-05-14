Ext.define('alms.manbusflow', {
  extend: 'gpersist.base.busform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '流程定义',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busflow',
      storeUrl: 'FlowSearchBusFlow.do',
      saveUrl: 'FlowSaveBusFlow.do',
      expUrl: 'FlowSearchBusFlow.do',
      deleteUrl:'FlowDeleteBusFlow.do',
      idPrevNext: 'busflow',
      hasEdit: true,
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '业务节点',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_flownode',
      urlDetail: 'FlowGetListFlowNode.do',
      detailTabs: 1,
      hasDateSearch: false,
      hasDetailEdit: true,
      winWidth: 900,
      winHeight: 350
    });    

    me.callParent(arguments);
  },
  
  flowdetail : null,
  buttons : null,
  
  OnInitConfig:function(){
    var me = this;
    me.flowdetail = null;
    me.buttons = Ext.create('Ext.util.MixedCollection');
  },
  
  OnInitGridToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.callParent();

    var nowdate = new Date();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务名称', labelWidth: 60, width: 200, name: 'searchbusflowname', id: mep + 'searchbusflowname', allowBlank: true }
    ];
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'flow.busflowname': tools.GetValueEncode(mep + 'searchbusflowname')
    };
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('业务代码'), 'flow.busflow', mep + 'busflow', 20, '96%', false, 1, null, 100)                                                  
        ]},
       {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('业务名称'), 'flow.busflowname', mep + 'busflowname', 32, '100%', false, 2)                                                                       
       ]}
     ]},
     tools.FormText('内容数据接口', 'flow.datafunction', mep + 'datafunction', 30, '100%', true, 4, null, 100),
     tools.FormText('内容显示接口', 'flow.htmldatafunction', mep + 'htmldatafunction', 30, '100%', true, 3, null, 100),
     {xtype: 'hiddenfield', name: 'flow.deal.action', id: mep + 'datadeal'}   
    ];

    me.disNews = [];
    me.disEdits = ['busflow'];
    me.enNews = ['busflow', 'busflowname',  'busfunction', 'datafunction', 'htmldatafunction'];
    me.enEdits = [ 'busflowname', 'busfunction', 'datafunction', 'htmldatafunction'];
    
  },
 
  OnDetailRefresh: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'fn.busflow': tools.GetValue(mep + 'busflow')
        });
      });
    }
    
    me.callParent();
  },
  
  OnSetData: function (id, record) {
    var me = this;
    var mep = me.tranPrefix;
    
    var item = tools.JsonGet(tools.GetUrl('FlowGetBusFlow.do?flow.busflow=') + id);
    
    if (item && !Ext.isEmpty(item.busflow)) {
      tools.SetValue(mep + 'busflow', item.busflow);
      tools.SetValue(mep + 'busflowname', item.busflowname);
      tools.SetValue(mep + 'datafunction', item.datafunction);
      tools.SetValue(mep + 'htmldatafunction', item.htmldatafunction);
      
      me.buttons.clear();
      
      var dbuttons = tools.JsonGet(tools.GetUrl('FlowGetListFlowNodeButtonByFlow.do?nodebutton.busflow=') + id);

      if (dbuttons && dbuttons.data) {
        for (i = 0; i < dbuttons.data.length; i++) {
          var button = dbuttons.data[i];
          
          if (me.buttons.get(button.flownode) != null) {
            me.buttons.get(button.flownode).push({flowbtn: button.flowbtn,
               btntitle: button.btntitle,
               nodebutton: button.nodebutton,
               btnorder: button.btnorder,
               btnmsg: button.btnmsg,
               isenter: button.isenter,
               btnaction: button.btnaction,
               todostatusdesc: button.todostatusdesc,
               nodestep: button.nodestep});
          }else {
            me.buttons.add(button.flownode, [{flowbtn: button.flowbtn,
               btntitle: button.btntitle,
               nodebutton: button.nodebutton,
               btnorder: button.btnorder,
               btnmsg: button.btnmsg,
               isenter: button.isenter,
               btnaction: button.btnaction,
               todostatusdesc: button.todostatusdesc,
               nodestep: button.nodestep}]);
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
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'busflow').reset();
    Ext.getCmp(mep + 'busflow').focus(true, 500);
  },
  
  OnListNew : function(){
    var me = this;
    var mep = this.tranPrefix;
    
    var busflow = tools.GetValue(mep + 'busflow');
    
    if (!me.flowdetail) {
      me.flowdetail = tools.GetPopupWindow('alms', 'editbusflow', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'gd', dataDeal: gpersist.DATA_DEAL_NEW})
      
      me.flowdetail.on('formlast', me.OnDetailSave, me);
      me.flowdetail.OnFormLoad();
    }
    else
      me.flowdetail.OnFormShow();
      
    me.flowdetail.OnInitData(me.buttons);
    me.flowdetail.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    
    me.detailRecord = null;
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this, mep = me.tranPrefix;
    if (!me.flowdetail) {
      me.flowdetail = tools.GetPopupWindow('alms', 'editbusflow', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'gd', dataDeal: me.dataDeal})
      
      me.flowdetail.on('formlast', me.OnDetailSave, me);
      me.flowdetail.OnFormLoad();
    }
    else
      me.flowdetail.OnFormShow();
    
    me.flowdetail.OnSetData(record, tools.GetValue(mep + 'busflow'), me.buttons);
    me.flowdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.detailRecord = record;
  },
  
  OnDetailSave: function (e, data) {
    var me = this;
    var mep = me.tranPrefix;
    var record = me.detailRecord;
    
    if (record == null){
      record = me.plDetailGrid.store.model.create({});
    }
    
    record.data.flownode = data.flownode;
    record.data.flownodename = data.flownodename;
    record.data.nodetype = data.nodetype;
    record.data.nodetypename = data.nodetypename;
    record.data.busflow = data.busflow;
    record.data.nodeserial = data.nodeserial;
    record.data.firstdays = data.firstdays;
    record.data.seconddays = data.seconddays;
    record.data.dataauth = data.dataauth;
    record.data.dataauthname = data.dataauthname;
    record.data.nodeoper = data.nodeoper;
    record.data.nodeopername = data.nodeopername;
    record.data.issms = data.issms;
    record.data.isemail = data.isemail;
    record.data.isfile = data.isfile;
    record.data.trancode = data.trancode;
    record.data.worktime = data.worktime;
    record.data.isemail = data.isemail;
    record.data.noderolenames = data.noderolenames;
    record.data.noderole = data.noderole;
    
    if (me.detailRecord)
      me.plDetailGrid.getView().refresh();
    else
      me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record); 
  },
  
  // 页面空间授权处理
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete','btnAddBtn','btnDeleteBtn'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnAddBtn','btnDeleteBtn'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnAddBtn','btnDeleteBtn'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnGetSaveParams : function() {
    var me = this, mep = me.tranPrefix;
    var details = [];
    var dbuttons = [];
    var i = 0, j = 0;
    
    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      details.push(me.plDetailGrid.store.getAt(i).data);
      
      var buttons = me.buttons.get(me.plDetailGrid.store.getAt(i).data.flownode);
      if (buttons) {
        for (j = 0; j < buttons.length; j++) {
          
          dbuttons.push({flownode: me.plDetailGrid.store.getAt(i).data.flownode,
               flowbtn: buttons[j].flowbtn,
               btntitle: buttons[j].btntitle,
               nodebutton: buttons[j].nodebutton,
               btnorder: buttons[j].btnorder,
               btnmsg: buttons[j].btnmsg,
               isenter: buttons[j].isenter,
               btnaction: buttons[j].btnaction,
               todostatusdesc: buttons[j].todostatusdesc,
               nodestep: buttons[j].nodestep })
        }
      }
    } 
    
    me.saveParams = { dbuttons: Ext.encode(dbuttons), details: Ext.encode(details)};
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({busflow: datas[i].data.busflow});
    }

    me.deleteParams = {deletes : Ext.encode(json)};
    
    return true;
  }
  
});