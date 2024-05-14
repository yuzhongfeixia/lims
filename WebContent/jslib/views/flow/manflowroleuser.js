Ext.define('alms.manflowroleuser', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: ['NodeRole'],
      editInfo: '流程人员设置',
      winWidth: 750,
      winHeight: 120,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowroleuser',
      storeUrl: 'FlowSearchFlowRoleUser.do',
      saveUrl: 'FlowSaveFlowRoleUser.do',
      expUrl: 'FlowSearchFlowRoleUser.do',
      deleteUrl: 'FlowDeleteFlowRoleUser.do',
      hasPage: true,
      hasPrevNext: false,
      idPrevNext: 'noderole',
      hasGridSelect: true
    });    

    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', tools.GetToolBarCombo('searchnoderole', mep + 'searchnoderole', 200, '业务角色', 60, tools.ComboStore('NodeRole', gpersist.SELECT_ALL_VALUE), gpersist.SELECT_ALL_VALUE),
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];  
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'fruser.noderole': tools.GetValue(mep + 'searchnoderole')
    };
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(tools.MustTitle('人员编号'), 'fruser.userid', mep + 'userid', 14, '96%', false, 1),
           tools.FormCombo(tools.MustTitle('业务角色'), 'fruser.noderole', mep + 'noderole', tools.ComboStore('NodeRole', gpersist.SELECT_MUST_VALUE), '96%', false, 3)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(tools.MustTitle('人员姓名'), 'fruser.username', mep + 'username', 20, '100%', false, 2),           
           {xtype: 'hiddenfield', name: 'fruser.deal.action', id: mep + 'datadeal'}
         ]}
      ]}
    ];
    
    me.disNews = ['username'];
    me.disEdits = ['noderole','username','userid'];
    me.enNews = ['noderole', 'userid'];
    me.enEdits = [];
    
    Ext.getCmp(mep + 'userid').on('blur', me.GetUserName, me);
  },
  
  GetUserName: function () {
    var me = this, mep = me.tranPrefix;
    
    if (Ext.isEmpty(tools.GetValue(mep + 'userid'))) 
      return;
    
    var item = tools.JsonGetPost(tools.GetUrl('BasGetUserByUserid.do?userid=') + 
      tools.GetEncode(tools.GetValueEncode(mep + 'userid')));
    
    if (item && !Ext.isEmpty(item.userid)) {
      tools.SetValue(mep + 'username', item.username);
      tools.SetValue(mep + 'userid', item.userid);
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '人员'));
    }
  },
  
  OnInitData: function () {
    var me = this, mep = me.tranPrefix;  
    
    me.callParent();
    
    tools.SetValue(mep + 'noderole', gpersist.SELECT_MUST_VALUE);
  },
  
  OnSetData: function(id, record) {
    var me = this, mep = me.tranPrefix;  
    
    tools.SetValue(mep + 'noderole', record.noderole);
    tools.SetValue(mep + 'noderolename', record.noderolename);
    
    return true;
  },
  
  OnShow: function () { },
  
  OnEdit: function () { },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'noderole').reset();
    Ext.getCmp(mep + 'noderole').focus(true, 500);
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({noderole: datas[i].data.noderole, userid: datas[i].data.userid });
    }

    me.deleteParams = {deletes : Ext.encode(json)};
    
    return true;
  }
});