Ext.define('alms.editbusflowbtn', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '选择按钮',
      winWidth: 500,
      winHeight: 300
    });

    me.callParent(arguments);
    
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
    var flowbtn = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '按钮编号', name: 'fb.flowbtn', id: mep + 'flowbtn', winTitle: '请选择按钮',
      maxLength: 10, maxLengthText: '按钮编号不能超过2字！', selectOnFocus: false, labelWidth: 80,
      blankText: '按钮编号为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowbutton',
      storeUrl: 'FlowSearchFlowButton.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    flowbtn.on('griditemclick', me.OnBtnSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          flowbtn,
          tools.FormText('按钮标识', 'fb.nodebutton', mep + 'nodebutton', 10, '96%', false, 102),
          tools.FormText('按钮调用动作', 'fb.btnaction', mep + 'btnaction', 30, '96%', false,104),
          tools.FormText('步骤策略', 'fb.nodestep', mep + 'nodestep', 10, '96%', false,106,'isnumber')
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('按钮标题', 'fb.btntitle', mep + 'btntitle', 10, '100%', false, 101),
          tools.FormText('按钮提示', 'fb.btnmsg', mep + 'btnmsg', 40, '100%', false, 103),
          tools.FormText('按钮显示序号', 'fb.btnorder', mep + 'btnorder', 10, '100%', false,105,'isnumber'),
          tools.FormCheck('是否输入确认信息', 'fb.isenter', mep + 'isenter',  false,107),
          {xtype:'hiddenfield',name:'fb.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('处理描述', 'fb.todostatusdesc', mep + 'todostatusdesc', 40, '100%', true,108)
    ];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['flowbtn', 'btntitle', 'nodebutton', 'btnmsg', 'isenter', 'btnaction', 'todostatusdesc', 'nodestep','btnorder'];
    me.enEdits = ['flowbtn','btntitle', 'nodebutton', 'btnmsg', 'isenter', 'btnaction', 'todostatusdesc', 'nodestep','btnorder'];
    
  },
  
  OnBtnSelect:function(render,item){
     var me = this;
     var mep = me.mep;
     if(item && item.flowbtn){
        tools.SetValue(mep + 'flowbtn', item.flowbtn);
        tools.SetValue(mep + 'btntitle', item.flowbtnname);
        tools.SetValue(mep + 'nodebutton', item.nodebutton);
        tools.SetValue(mep + 'btnmsg', item.btnmsg);
        tools.SetValue(mep + 'isenter', item.isenter);
        tools.SetValue(mep + 'btnaction', item.btnaction);
        tools.SetValue(mep + 'todostatusdesc', item.todostatusdesc);
        tools.SetValue(mep + 'nodestep', item.nodestep);
     }
   },
  
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var data = {
          flowbtn: tools.GetValue(mep + 'flowbtn'),
          btntitle: tools.GetValue(mep + 'btntitle'),
          nodebutton : tools.GetValue(mep + 'nodebutton'),
          btnorder : tools.GetValue(mep + 'btnorder'),
          btnmsg : tools.GetValue(mep + 'btnmsg'),
          isenter : tools.GetValue(mep + 'isenter'),
          btnaction : tools.GetValue(mep + 'btnaction'),
          todostatusdesc : tools.GetValue(mep + 'todostatusdesc'),
          nodestep : tools.GetValue(mep + 'nodestep')
      };
        
      me.fireEvent('formlast', me, data); 
      
      me.winEdit.hide();
    }
  },
  
  OnInitData: function (flownode) {
    this.plEdit.getForm().reset(); 
    
  },
  
  OnSetData: function (record,flownode) {
    var me = this, mep = me.mep;
    
    tools.SetValue(mep + 'flowbtn', record.data.flowbtn);
    tools.SetValue(mep + 'btnorder', record.data.btnorder);
    tools.SetValue(mep + 'btntitle', record.data.btntitle);
    tools.SetValue(mep + 'nodebutton', record.data.nodebutton);
    tools.SetValue(mep + 'btnmsg', record.data.btnmsg);
    tools.SetValue(mep + 'isenter', record.data.isenter);
    tools.SetValue(mep + 'btnaction', record.data.btnaction);
    tools.SetValue(mep + 'todostatusdesc', record.data.todostatusdesc);
    tools.SetValue(mep + 'nodestep', record.data.nodestep);
    
  }
});