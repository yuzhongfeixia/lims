Ext.define('alms.selectparameter', {
  extend: 'gpersist.base.editwin',  
  
  plLeft: null,
  plRight: null,
  cellEditLeft: null,
  cellEditRight: null,
  testurl: '',
  judgeurl: '',
  tests: null,
  judges: null,
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: ['CostUnit'], 
      title: '选择检测参数',
      winWidth: 800,
      winHeight: 520
    });

    me.callParent(arguments);
  },
  
  OnInitConfig: function () {
    var me = this;
    
    me.plLeft = null;
    me.plRight = null;
    me.cellEditLeft = null;
    me.cellEditRight = null;
    me.testurl = 'BasGetListBasSampleTest.do';
    me.judgeurl = 'BasGetListBasSampleJudge.do';
    me.tests = null;
    me.judges = null;
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    var leftcols = [];
    var leftfields = [];
    var rightcols = [];
    var rightfields = [];
    
    var testitem = [
      ' ', { id: mep + 'btnAddTest', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNewTest, scope: me },
      ' ', { id: mep + 'btnDeleteTest', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteTest, scope: me }
    ];
    
    var judgeitem = [
      ' ', { id: mep + 'btnAddJud', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNewJud, scope: me },
      ' ', { id: mep + 'btnDeleteJud', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteJud, scope: me }
    ];
    
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_select_bastest', leftcols, leftfields, mep + '_test_');
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_select_basjudge', rightcols, rightfields, mep + '_jduge_');

    var leftStore = tools.CreateGridStore(tools.GetUrl(me.testurl), leftfields);
    var rightStore = tools.CreateGridStore(tools.GetUrl(me.judgeurl), rightfields);
    
    me.cellEditLeft = Ext.create('Ext.grid.plugin.CellEditing', {
      clicksToEdit: 1,
      listeners: {
        beforeedit: function (e, editor) {
          if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
            return false;
        }
      }
    });

    me.cellEditRight = Ext.create('Ext.grid.plugin.CellEditing', {
      clicksToEdit: 1,
      listeners: {
        beforeedit: function (e, editor) {
          if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
            return false;
        }
      }
    });
      
    Ext.each(leftcols, function (col) {
      if (col.dataIndex == 'teststandard') {
        var testitems = [
          ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 90, width: 220, maxLength: 40, name: 'searchteststandardname', id: mep + 'searchteststandardname', allowBlank: true },
          ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTestSearch, scope: me }
        ];
  
        col.editor = {
          xtype: 'gridpicker', name: mep + 'selecttest', id: mep + 'selecttest', winTitle: '选择检测方法',
          maxLength: 6, maxLengthText: '检测方法编号长度不能超过6个字符！', selectOnFocus: false,
          blankText: '检测方法编号不能为空！', allowBlank: false, editable: false, 
          columnUrl: 'SysSqlColumn.do?sqlid=p_get_bastest',
          storeUrl: 'BasSearchBasTest.do',
          hasPage: true, pickerWidth: 600, pickerHeight: 450, searchTools: testitems,
          listeners: {
            'griditemclick': { fn: me.OnTestSelect, scope: me },
            'gridbeforeload': { fn: me.OnTestBeforeLoad, scope: me }
          }
        };
      }
    });
    
    Ext.each(rightcols, function (col) {
      if (col.dataIndex == 'judgestandard') {
        var judgeitems = [
          ' ', { xtype: 'textfield', fieldLabel: '判定依据名称', labelWidth: 90, width: 220, maxLength: 40, name: 'searchjudgestandardname', id: mep + 'searchjudgestandardname', allowBlank: true },
          ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnJudgeSearch, scope: me }
        ];
  
        col.editor = {
          xtype: 'gridpicker', name: mep + 'selectjudge', id: mep + 'selectjudge', winTitle: '选择判定依据',
          maxLength: 6, maxLengthText: '判定依据编号长度不能超过6个字符！', selectOnFocus: false,
          blankText: '判定依据编号不能为空！', allowBlank: false, editable: false, 
          columnUrl: 'SysSqlColumn.do?sqlid=p_get_basjudge',
          storeUrl: 'BasSearchBasJudge.do',
          hasPage: true, pickerWidth: 600, pickerHeight: 450, searchTools: judgeitems,
          listeners: {
            'griditemclick': { fn: me.OnJudgeSelect, scope: me },
            'gridbeforeload': { fn: me.OnJudgeBeforeLoad, scope: me }
          }
        };
      }
    });
    
    me.plLeft = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '检测方法',
      margin: '0 5 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 350,
      enableColumnMove: false, suspendLayout:true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: leftcols,
      store: leftStore,
      tbar: testitem,
      plugins: [me.cellEditLeft],
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true })
    });
    
    me.plRight = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '判定依据',
      margin: '0 0 0 5',
      padding: '0 0 0 0',
      loadMask: true,
      height: 350,
      columnLines: true,
      multiSelect: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      enableColumnMove: false, suspendLayout:true,
      columns: rightcols,
      store: rightStore,
      tbar: judgeitem,
      plugins: [me.cellEditRight],
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true })
    });
    
    var paritems = [
      ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 100, width: 220, maxLength: 12, name: 'searchparametercn', id: mep + 'searchparametercn', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnParameterSearch, scope: me }
    ];

    var param = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: tools.MustTitle('参数编号'), name: 'bsp.parameterid', id: mep + 'parameterid', winTitle: '检测参数',
      maxLength: 6, maxLengthText: '检测参数编号长度不能超过6个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '检测参数编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 101, editable: false,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basparameter',
      storeUrl: 'BasSearchBasParameter.do',
      hasPage: true, pickerWidth: 600, pickerHeight: 450, searchTools: paritems
    });
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          param,
          tools.FormText('判定大小', 'bsp.testjudge', mep + 'testjudge', 20, '96%', true, 102),
          tools.FormText('标准值单位', 'bsp.paramunit', mep + 'paramunit', 20, '96%', true, 102)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('参数名称', 'bsp.parametername', mep + 'parametername', 20, '100%', false, 102),
          tools.FormText('标准值', 'bsp.standvalue', mep + 'standvalue', 50, '100%', true, 105),
          tools.FormCombo('指标规格', 'bsp.belongtype', mep + 'belongtype', tools.ComboStore('BelongType', gpersist.SELECT_MUST_VALUE), '100%', true, 104)
        ]}
      ]},
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plLeft]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor',items: [me.plRight]}
      ]}
    ];
    
    param.on('griditemclick', me.OnParameterSelect, me);
    param.on('gridbeforeload', me.OnParameterBeforeLoad, me);
    
    me.disNews = ['parametername'];
    me.disEdits = ['parameterid', 'parametername'];
    me.enNews = ['parameterid','testjudge','paramunit','standvalue','belongtype'];
    me.enEdits = ['testjudge','paramunit','standvalue','belongtype'];
  },
  
  OnParameterSearch: function () {
    var me = this, mep = me.mep;
    
    me.OnParameterBeforeLoad();
    
    var param = Ext.getCmp(mep + 'parameterid');
    
    param.store.loadPage(1);
  },
  
  OnParameterBeforeLoad: function () {
    var me = this, mep = me.mep;
    
    var param = Ext.getCmp(mep + 'parameterid');

    if (param.store) {      
      param.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bpara.parametername': tools.GetValueEncode(mep + 'searchparametercn') 
        });
      });
    }
  },
  
  OnParameterSelect: function (render, item) {
    var me = this, mep = me.mep;
    
    if (item && !Ext.isEmpty(item.parameterid)) {
      tools.SetValue(mep + 'parameterid', item.parameterid);
      tools.SetValue(mep + 'parametername', item.parametername);
    }
  },
  
  OnListNewTest: function() {
    var me = this;
    
    var record = me.plLeft.store.model.create({});
    
    me.plLeft.store.insert(me.plLeft.store.getCount(), record);
    
    if (me.cellEditLeft) {
      me.cellEditLeft.startEditByPosition({
        row: me.plLeft.store.getCount() - 1,
        column: 0
      });
    }
  },
  
  OnListDeleteTest : function() {
    var me = this;
    
    var j = me.plLeft.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plLeft.store.remove(me.plLeft.selModel.selected.items[0]);
    }
    
    me.plLeft.getView().refresh();
  },
  
  OnListNewJud: function() {
    var me = this;
    
    var record = me.plRight.store.model.create({});
    
    me.plRight.store.insert(me.plRight.store.getCount(), record);
    
    if (me.cellEditRight) {
      me.cellEditRight.startEditByPosition({
        row: me.plRight.store.getCount() - 1,
        column: 0
      });
    }
  },
  
  OnListDeleteJud : function() {
    var me = this;
    
    var j = me.plRight.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plRight.store.remove(me.plRight.selModel.selected.items[0]);
    }
    
    me.plRight.getView().refresh();
  },
  
  OnTestSearch: function () {
    var me = this, mep = me.mep;
    
    me.OnTestBeforeLoad();
    
    var test = Ext.getCmp(mep + 'selecttest');
    
    test.store.loadPage(1);    
  },
  
  OnTestBeforeLoad: function () {    
    var me = this, mep = me.mep;
    
    var test = Ext.getCmp(mep + 'selecttest');

    if (test.store) {      
      test.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'btest.teststandardname': tools.GetValueEncode(mep + 'searchteststandardname') 
        });
      });
    }
  },
  
  OnTestSelect: function (render, item) {
    var me = this;
    
    if (item && !Ext.isEmpty(item.teststandard) && me.cellEditLeft) {
      var now = me.cellEditLeft.getActiveRecord();

      if (now) {
        me.cellEditLeft.getActiveEditor().setValue(item.teststandard);
        
        now.data.teststandardname = item.teststandardname;
        now.data.teststandardcode = item.teststandardcode;
        
        me.plLeft.getView().refresh();
      }
    }
  },
  
  OnJudgeSearch: function () {
    var me = this, mep = me.mep;
    
    me.OnJudgeBeforeLoad();
    
    var judge = Ext.getCmp(mep + 'selectjudge');
    
    judge.store.loadPage(1);    
  },
  
  OnJudgeBeforeLoad: function () {    
    var me = this, mep = me.mep;
    
    var judge = Ext.getCmp(mep + 'selectjudge');

    if (judge.store) {      
      judge.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bjudge.judgestandardname': tools.GetValueEncode(mep + 'searchjudgestandardname') 
        });
      });
    }
  },
  
  OnJudgeSelect: function (render, item) {
    var me = this;
    
    if (item && !Ext.isEmpty(item.judgestandard) && me.cellEditRight) {
      var now = me.cellEditRight.getActiveRecord();

      if (now) {
        me.cellEditRight.getActiveEditor().setValue(item.judgestandard);
        
        now.data.judgestandardname = item.judgestandardname;
        now.data.judgestandardcode = item.judgestandardcode;
        
        me.plRight.getView().refresh();
      }
    }
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      if (tools.GetValue(mep + 'costunit') == gpersist.SELECT_MUST_VALUE) {
        tools.alert('请选择计费单位！')  
        return;
      }
      
      var lefts = [];
      for (i = 0; i < me.plLeft.store.getCount(); i++) {
        lefts.push({teststandard: me.plLeft.store.getAt(i).data.teststandard, 
          teststandardname: me.plLeft.store.getAt(i).data.teststandardname, teststandardcode: me.plLeft.store.getAt(i).data.teststandardcode
        });
      }
      
      me.tests.removeAtKey(tools.GetValue(mep + 'parameterid'));
      me.tests.add(tools.GetValue(mep + 'parameterid'), lefts);
      
      var rights = [];
      for (i = 0; i < me.plRight.store.getCount(); i++) {
        rights.push({judgestandard: me.plRight.store.getAt(i).data.judgestandard,
          judgestandardname: me.plRight.store.getAt(i).data.judgestandardname, judgestandardcode: me.plRight.store.getAt(i).data.judgestandardcode       
        });
      }
    
      me.judges.removeAtKey(tools.GetValue(mep + 'parameterid'));
      me.judges.add(tools.GetValue(mep + 'parameterid'), rights);
      
      var data = {parameterid: tools.GetValue(mep + 'parameterid'), parametername: tools.GetValue(mep + 'parametername'),
         testjudge: tools.GetValue(mep + 'testjudge'),paramunit: tools.GetValue(mep + 'paramunit'),
         standvalue:tools.GetValue(mep + 'standvalue'),belongtype:tools.GetValue(mep + 'belongtype'),
         belongtypename: Ext.getCmp(mep + 'belongtype').getDisplayValue()};
      
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (tests, judges) {
    var me = this, mep = me.mep;

    me.callParent();
    
    me.plLeft.store.removeAll();
    me.plLeft.getView().refresh();
    
    me.plRight.store.removeAll();
    me.plRight.getView().refresh();
    
    me.tests = tests;
    me.judges = judges;
    
    tools.SetValue(mep + 'belongtype', gpersist.SELECT_MUST_VALUE);
  },
  
  OnSetData: function (record, sampleid, tests, judges) {
    var me = this, mep = me.mep;

    tools.SetValue(mep + 'testjudge', record.data.testjudge);
    tools.SetValue(mep + 'parameterid', record.data.parameterid);
    tools.SetValue(mep + 'parametername', record.data.parametername);
    tools.SetValue(mep + 'paramunit', record.data.paramunit);
    tools.SetValue(mep + 'standvalue', record.data.standvalue);
    tools.SetValue(mep + 'belongtype', record.data.belongtype);

    me.tests = tests;
    me.judges = judges;
    
    me.plLeft.store.removeAll();
    
    if (tests.get(record.data.parameterid) != null) {      
      var listtests = tests.get(record.data.parameterid); 
      
      for (var i = 0; i < listtests.length; i++) {
        var test = listtests[i];
        
        var rtest = me.plLeft.store.model.create({});

        rtest.data.teststandard = test.teststandard;
        rtest.data.teststandardname = test.teststandardname;
        rtest.data.teststandardcode = test.teststandardcode;
            
        me.plLeft.store.insert(me.plLeft.store.getCount(), rtest); 
      }
    }
    
    me.plLeft.getView().refresh();

    me.plRight.store.removeAll();
    
    if (judges.get(record.data.parameterid) != null) {     
      var thisjudges = judges.get(record.data.parameterid); 
      
      for (var i = 0; i < thisjudges.length; i++) {
        var judge = thisjudges[i];
        
        var rjudge = me.plRight.store.model.create({});

        rjudge.data.judgestandard = judge.judgestandard;
        rjudge.data.judgestandardname = judge.judgestandardname;
        rjudge.data.judgestandardcode = judge.judgestandardcode;
            
        me.plRight.store.insert(me.plRight.store.getCount(), rjudge); 
      }
    }
    
    me.plRight.getView().refresh();
  }
});