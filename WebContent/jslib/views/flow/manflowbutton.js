Ext.define('alms.manflowbutton', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      editInfo: '按钮类型',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowbutton',
      storeUrl: 'FlowSearchFlowButton.do',
      saveUrl: 'FlowSaveFlowButton.do',
      expUrl: 'FlowSearchFlowButton.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'flowbtn',
      hasGridSelect: true
    });    

    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('按钮类型编号', 'fb.flowbtn', mep + 'flowbtn', 2, '96%', false, 1,'',90),
          tools.FormText('按钮缺省标识', 'fb.nodebutton', mep + 'nodebutton', 10, '96%', false, 3,'',90),
          tools.FormText('步骤策略', 'fb.nodestep', mep + 'nodestep', 20, '96%', false,5,'isnumber',90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('按钮类型名称', 'fb.flowbtnname', mep + 'flowbtnname', 10, '100%', false, 2,'',90),
          tools.FormText('按钮缺省提示', 'fb.btnmsg', mep + 'btnmsg', 40, '100%', false, 4,'',90),
          tools.FormCheck('是否输入确认信息', 'fb.isenter', mep + 'isenter',  false,6),
          {xtype:'hiddenfield',name:'fb.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('按钮缺省调用动作', 'fb.btnaction', mep + 'btnaction', 30, '100%', false,7,2,90),
      tools.FormTextArea('处理描述', 'fb.todostatusdesc', mep + 'todostatusdesc', 40, '100%', true,8,2,90)
    ];
    
    me.disNews = [];
    me.disEdits = ['flowbtn'];
    me.enNews = ['flowbtn', 'flowbtnname', 'nodebutton', 'btnmsg', 'isenter', 'btnaction', 'todostatusdesc', 'nodestep'];
    me.enEdits = [ 'flowbtnname', 'nodebutton', 'btnmsg', 'isenter', 'btnaction', 'todostatusdesc', 'nodestep'];
  },

  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '按钮类型编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchflowbtn', id: mep + 'searchflowbtn', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '按钮类型名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchflowbtnname', id: mep + 'searchflowbtnname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
  
   
  OnLoadData: function(item) {
    var me = this, mep = me.tranPrefix;  
    
    tools.SetValue(mep + 'flowbtn', item.flowbtn);
    tools.SetValue(mep + 'flowbtnname', item.flowbtnname);
    tools.SetValue(mep + 'nodebutton', item.nodebutton);
    tools.SetValue(mep + 'btnmsg', item.btnmsg);
    tools.SetValue(mep + 'isenter', item.isenter);
    tools.SetValue(mep + 'btnaction', item.btnaction);
    tools.SetValue(mep + 'todostatusdesc', item.todostatusdesc);
    tools.SetValue(mep + 'nodestep', item.nodestep);

    return true;
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'fb.flowbtn': tools.GetEncode(tools.GetValue(mep + 'searchflowbtn')),
          'fb.flowbtnname': tools.GetEncode(tools.GetValue(mep + 'searchflowbtnname'))
        });
      });
    };
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'flowbtn').reset();
    Ext.getCmp(mep + 'flowbtn').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.flowbtn) {
          tools.SetValue(mep + 'flowbtn', action.result.data.flowbtn);
        }
      }
    }
  }
});