Ext.define('alms.editbusflow', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '节点策略',
      winWidth: 800,
      winHeight: 600
    });

    me.callParent(arguments);
  },
  
  plButton:null,
  buttons: null,
  
  OnInitConfig: function () {
    var me = this;
    me.plButton = null;
    me.buttons = null;
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;

    var nowdate = new Date();
    
    var btncols = [];
    var btnfields = [];
    
    var btnitem = [
      ' ', { id: mep + 'btnAddBtn', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNewBtn, scope: me },
      ' ', { id: mep + 'btnDeleteBtn', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteBtn, scope: me }
    ];
    
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_get_flownodebutton', btncols, btnfields, mep + '_btn_');

    var btnStore = tools.CreateGridStore(tools.GetUrl(), btnfields);
    
    me.plButton = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '按钮',
      margin: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 370,
      enableColumnMove: false,
      suspendLayout:true,
      autoScroll : true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: btncols,
      store: btnStore,
      tbar: btnitem,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListBtn, scope : me }
      } 
    });
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('节点编号'), 'node.flownode', mep + 'flownode', 10, '96%', false, 100),
          tools.FormText(tools.MustTitle('执行序号'), 'node.nodeserial', mep + 'nodeserial', 3, '96%', false, 103, 'isnumber'),
          tools.FormCombo(tools.MustTitle('操作类型'), 'node.nodeoper', mep + 'nodeoper', tools.ComboStore('NodeOper', gpersist.SELECT_MUST_VALUE), '96%', false, 109),
          tools.FormText('工时', 'node.worktime', mep + 'worktime', 8, '96%', true, 110, 'ismoney')
        ]},
        {xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('节点名称'), 'node.flownodename', mep + 'flownodename', 20, '96%', false, 101),
          tools.FormText(tools.MustTitle('业务编号'), 'node.trancode', mep + 'trancode', 10, '96%', false, 104),
          tools.FormText(tools.MustTitle('首次超期天数'), 'node.firstdays', mep + 'firstdays', 8, '96%', false, 107, 'isnumber',100)
         
          
        ]},
        {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormCombo(tools.MustTitle('节点类型'), 'node.nodetype', mep + 'nodetype', tools.ComboStore('NodeType', gpersist.SELECT_MUST_VALUE), '100%', false, 102),
          tools.FormCombo(tools.MustTitle('数据操作范围'), 'node.dataauth', mep + 'dataauth', tools.ComboStore('DataAuth', gpersist.SELECT_MUST_VALUE), '100%', false, 105,100),
          tools.FormText(tools.MustTitle('二次超期天数'), 'node.seconddays', mep + 'seconddays', 8, '100%', false, 108, 'isnumber',100)
        ]}
      ]},
      tools.FormCheckCombo('执行角色', 'node.noderole', mep + 'noderole', tools.ComboStore('NodeRole'), '100%', true, 21),
      { xtype: 'fieldcontainer', fieldLabel: '', layout: 'hbox', defaults: { labelStyle: 'font-weight:bold;', margins: '0 15 0 0' }, items: [
        tools.FormCheck(tools.MustTitle('邮件提醒'), 'node.isemail', mep + 'isemail', false, 120),
        tools.FormCheck(tools.MustTitle('短信提醒'), 'node.issms', mep + 'issms', false, 121),
        tools.FormCheck(tools.MustTitle('超期短信提醒'), 'node.isoversms', mep + 'isoversms', false, 122),
        tools.FormCheck(tools.MustTitle('上传附件'), 'node.isfile', mep + 'isfile', false, 123)]},
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: 1, layout: 'anchor',items: [me.plButton]}
      ]}
    ];
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['flownode',  'flownodename', 'nodetype', 'nodeserial', 'firstdays', 'seconddays', 'dataauth',
    'nodeoper', 'issms', 'isemail', 'isoversms', 'isfile', 'trancode', 'worktime','noderole'];
    me.enEdits = ['flownode',  'flownodename', 'nodetype', 'nodeserial', 'firstdays', 'seconddays', 'dataauth',
    'nodeoper', 'issms', 'isemail', 'isoversms', 'isfile', 'trancode', 'worktime','noderole'];
    
  },
  
  btndetail: null,
  btnrecord: null,
  
  OnListNewBtn: function () {
    var me = this, mep = me.mep;
    
    if (!me.btndetail) {
      me.btndetail = tools.GetPopupWindow('alms', 'editbusflowbtn', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.btndetail.on('formlast', me.OnBtnSave, me);
      me.btndetail.OnFormLoad();
    }
    else
      me.btndetail.OnFormShow();
    
    me.btndetail.OnInitData(tools.GetValue(mep + 'flownode'));    
    me.btndetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.btnrecord = null;    
  },
  
  OnListBtn: function (e, record, item, index) {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.btndetail) {
      me.btndetail = tools.GetPopupWindow('alms', 'editbusflowbtn', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.btndetail.on('formlast', me.OnBtnSave, me);
      me.btndetail.OnFormLoad();
    }
    else
      me.btndetail.OnFormShow();
    
    me.btndetail.OnSetData(record,tools.GetValue(mep + 'flownode'));    
    me.btndetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.btnrecord = record;
  },
  
  OnBtnSave: function (e, data) {
    var me = this, mep = me.mep;
    var record = me.btnrecord;
    
    if (record == null) record = me.plButton.store.model.create({});
    
    for(var j = 0;j < me.plButton.store.getCount();j++ ){
          if(data.flowbtn == me.plButton.store.getAt(j).data.flowbtn){
            me.plButton.store.removeAt(j);
          }
     }  
     
    record.data.flowbtn = data.flowbtn;
    record.data.btntitle = data.btntitle;
    record.data.nodebutton = data.nodebutton;
    record.data.btnorder = data.btnorder;
    record.data.btnmsg = data.btnmsg;
    record.data.isenter = data.isenter;
    record.data.btnaction = data.btnaction;
    record.data.todostatusdesc = data.todostatusdesc;
    record.data.nodestep = data.nodestep;
    
    me.plButton.store.insert(me.plButton.store.getCount(), record);
      
    me.plButton.getView().refresh();
  },
  
  OnListDeleteBtn : function() {
    var me = this;
    
    var j = me.plButton.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plButton.store.remove(me.plButton.selModel.selected.items[0]);
    }
    
    me.plButton.getView().refresh();
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      if(tools.GetValue(mep + 'nodetype') == gpersist.SELECT_MUST_VALUE){
        tools.alert('请选择节点类型!');
        return;
      }
      if(tools.GetValue(mep + 'dataauth') == gpersist.SELECT_MUST_VALUE){
      
        tools.alert('请选择数据操作范围!');
        return;
      }
      if(tools.GetValue(mep + 'nodeoper') == gpersist.SELECT_MUST_VALUE){
        tools.alert('请选择操作类型!');
        return;
      }
      
      var btns = [];
      for (i = 0; i < me.plButton.store.getCount(); i++) {
        btns.push({flowbtn: me.plButton.store.getAt(i).data.flowbtn,
               btntitle: me.plButton.store.getAt(i).data.btntitle,
               nodebutton: me.plButton.store.getAt(i).data.nodebutton,
               btnorder: me.plButton.store.getAt(i).data.btnorder,
               btnmsg: me.plButton.store.getAt(i).data.btnmsg,
               isenter: me.plButton.store.getAt(i).data.isenter,
               btnaction: me.plButton.store.getAt(i).data.btnaction,
               todostatusdesc: me.plButton.store.getAt(i).data.todostatusdesc,
               nodestep: me.plButton.store.getAt(i).data.nodestep});
      }
      
      me.buttons.removeAtKey(tools.GetValue(mep + 'flownode'));
      me.buttons.add(tools.GetValue(mep + 'flownode'), btns);
      
      var data = {flownode: tools.GetValue(mep + 'flownode'),
          flownodename: tools.GetValue(mep + 'flownodename'),
          nodetype : tools.GetValue(mep + 'nodetype'),
          nodetypename : Ext.getCmp(mep + 'nodetype').getDisplayValue(),
          busflow : tools.GetValue(mep + 'busflow'),
          nodeserial : tools.GetValue(mep + 'nodeserial'),
          firstdays : tools.GetValue(mep + 'firstdays'),
          seconddays : tools.GetValue(mep + 'seconddays'),
          dataauth : tools.GetValue(mep + 'dataauth'),
          dataauthname : Ext.getCmp(mep + 'dataauth').getDisplayValue(),
          nodeoper : tools.GetValue(mep + 'nodeoper'),
          nodeopername : Ext.getCmp(mep + 'nodeoper').getDisplayValue(),
          issms : tools.GetValue(mep + 'issms'),
          isemail : tools.GetValue(mep + 'isemail'),
          isoversms : tools.GetValue(mep + 'isoversms'),
          isfile : tools.GetValue(mep + 'isfile'),
          trancode : tools.GetValue(mep + 'trancode'),
          worktime : tools.GetValue(mep + 'worktime'),
          noderolenames : tools.GetDisplayValue(mep + 'noderole'),
          noderole : tools.GetValue(mep + 'noderole')};
        
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (buttons) {
    var me = this, mep = me.mep;
    me.callParent();
    me.plButton.store.removeAll();
    me.plButton.getView().refresh();
    me.buttons = buttons;
    tools.SetValue(mep + 'dataauth', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'nodetype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'nodeoper', gpersist.SELECT_MUST_VALUE);
  },
  
  OnSetData: function (record,busflow,buttons) {
    var me = this, mep = me.mep;
    var item = record.data;
    
    tools.SetValue(mep + 'flownode', item.flownode);
    tools.SetValue(mep + 'busflow', item.busflow);
    tools.SetValue(mep + 'flownodename', item.flownodename);
    tools.SetValue(mep + 'nodetype', item.nodetype);
    tools.SetValue(mep + 'nodeserial', item.nodeserial);
    tools.SetValue(mep + 'firstdays', item.firstdays);
    tools.SetValue(mep + 'seconddays', item.seconddays);
    tools.SetValue(mep + 'dataauth', item.dataauth);
    tools.SetValue(mep + 'nodeoper', item.nodeoper);
    tools.SetValue(mep + 'issms', item.issms);
    tools.SetValue(mep + 'isemail', item.isemail);
    tools.SetValue(mep + 'isoversms', item.isoversms);
    tools.SetValue(mep + 'isfile', item.isfile);
    tools.SetValue(mep + 'trancode', item.trancode);
    tools.SetValue(mep + 'worktime', item.worktime);
    tools.SetValue(mep + 'noderole', item.noderole.split(','));
    me.plButton.store.removeAll();
    
    me.buttons = buttons;
    
    if (buttons.get(record.data.flownode) != null) {      
       var listbtns = buttons.get(record.data.flownode); 
      
      for (var i = 0; i < listbtns.length; i++) {
        var btn = listbtns[i];
        
        var rbtn = me.plButton.store.model.create({});
        
        rbtn.data.flowbtn = btn.flowbtn;
        rbtn.data.btntitle = btn.btntitle;
        rbtn.data.nodebutton = btn.nodebutton;
        rbtn.data.btnorder = btn.btnorder;
        rbtn.data.btnmsg = btn.btnmsg;
        rbtn.data.isenter = btn.isenter;
        rbtn.data.btnaction = btn.btnaction;
        rbtn.data.todostatusdesc = btn.todostatusdesc;
        rbtn.data.nodestep = btn.nodestep;
            
        me.plButton.store.insert(me.plButton.store.getCount(), rbtn); 
      }
    }
    me.plButton.getView().refresh();
  }
  
});