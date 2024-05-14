Ext.define('gpersist.mandept', {
  extend: 'gpersist.base.baseform',
  
  disnews: ['coid'],
  ennews: ['deptid', 'deptname', 'deptshort', 'deptpid', 'deptlevel', 'depttype', 'deptstatus', 'sortorder'],
  disedits: ['coid','deptid'],
  enedits: [ 'deptname', 'deptshort', 'deptpid', 'deptlevel', 'depttype', 'deptstatus', 'sortorder'],
  
  plTree: null,
  plMain: null,
  plEdit: null,
  cbCompany: null,
  
  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: ['Company','DeptLevel', 'DeptStatus', 'DeptType']
    });

    me.callParent(arguments);
  },
  
  OnInitConfig: function () {
    var me = this;

    me.callParent(arguments);

    me.plTree = null;
    me.plMain = null;
    me.plEdit = null;
    me.cbCompany = null;
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    var items = [
      ' ', { id: mep + 'btnRefresh', text: gpersist.STR_BTN_REFRESH, iconCls: 'icon-refresh', handler: me.OnRefresh, scope: me }, 
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_ADD_SAME, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnAddSub', text: gpersist.STR_BTN_ADD_SUB, iconCls: 'icon-add', handler: me.OnNewSub, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
      '-', ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },
      ' ', { id: mep + 'btnCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-reset', handler: me.OnCancel, scope: me },     
      '-', ' ', { text: '', tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    var sauth = tools.GetAuth(mep);
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnAdd', 2, tools.ValidSeparator(sauth, 2));
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnAddSub', 2);
    tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnFormEdit', 3, tools.ValidSeparator(sauth, 4));
    
    if (!tools.ValidSeparator(sauth, 6)) {
      tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnSave', 2, tools.ValidSeparator(sauth, 2));
      tools.DeleteToolbarItemByAuth(sauth, items, mep + 'btnCancel', 2);
    }
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
      items: items
    });
    
    me.cbCompany = Ext.create('Ext.form.field.ComboBox', {
      id: mep + 'cbCompany',
      name: 'cbCompany',
      hideLabel: true,
      store: tools.GetParam('Company'),
      displayField: 'name',
      valueField: 'id',
      typeAhead: true,
      queryMode: 'local',
      triggerAction: 'all',
      selectOnFocus: true,
      editable: false,
      width: 240,
      listeners: {
        'select': { fn: function (record, index) {
            me.plTree.getRootNode().removeAll(false);
            var proxy = me.plTree.store.getProxy();
            proxy.url = tools.GetUrl('OrgDeptTreeByCoDept.do?coid=') + me.cbCompany.getValue();
            me.plTree.store.setProxy(proxy);
            me.plTree.store.load();
          }, scope: me
        }
      }
    });

    if (me.cbCompany.store.data.length > 0)
      me.cbCompany.setValue(me.cbCompany.store.getAt(0).data.id);
      
    me.plTree = Ext.create('Ext.tree.Panel', {
      id:mep + 'deptlist',
      rootVisible: false,
      region: 'west',
      title: Ext.String.format(gpersist.STR_FMT_LIST, gpersist.STR_COL_DEPT),
      width: 250,
      autoScroll: true,
      border: true,
      margins: '2 0 2 2',
      layout: 'fit',
      animate: false,
      tbar: Ext.create('Ext.toolbar.Toolbar', { items: [me.cbCompany] }),
      store: Ext.create('Ext.data.TreeStore', {
        model:'gpersist.gfdeptmodel',
        proxy: {
          type: 'ajax',
          url: tools.GetUrl('OrgDeptTreeByCoDept.do?coid=') + me.cbCompany.getValue()
        }
      }),
      listeners: {
        'itemclick': {fn: function (view, record) {
          me.OnLoadData(record);
        }, scope:me}
      } 
    });

    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: 80,
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
      items: [
        {xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor',
            items: [
              tools.FormText('机构编号', 'dept.deptid', mep + 'deptid', 6, '96%', false, 1),
              tools.FormCombo('所属公司', 'dept.coid', mep + 'coid', tools.ComboStore('Company', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
              tools.FormCombo('上级机构', 'dept.deptpid', mep + 'deptpid', tools.GetNullStore(gpersist.SELECT_NULL_VALUE), '96%', false, 5),
              tools.FormCombo('机构类型', 'dept.depttype', mep + 'depttype', tools.ComboStore('DeptType', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
              tools.FormText('排序序号', 'dept.sortorder', mep + 'sortorder', 3, '96%', false, 9, 'isnumber')
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText('机构名称', 'dept.deptname', mep + 'deptname', 40, '100%', false, 2), 
              tools.FormText('机构简称', 'dept.deptshort', mep + 'deptshort', 6, '100%', false, 4),
              tools.FormCombo('机构级别', 'dept.deptlevel', mep + 'deptlevel', tools.ComboStore('DeptLevel', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
              tools.FormCombo('机构状态', 'dept.deptstatus', mep + 'deptstatus', tools.ComboStore('DeptStatus', gpersist.SELECT_MUST_VALUE), '100%', false, 8),
              {xtype:'hiddenfield', name:'dept.deptdepth', id: mep + 'deptdepth'},
              {xtype:'hiddenfield', name:'dept.deal.action', id: mep + 'datadeal'}
          ]}
        ]}
      ],
      listeners: {
        'afterrender': { fn: me.OnInit, scope: me }
      }
    });
    
    me.plMain = Ext.create('Ext.Panel', {
      frame:false,
      autoScroll : false,
      region: 'center',
      border:true,
      items:[me.plEdit],
      layout:'column',
      title:Ext.String.format(gpersist.STR_FMT_DETAIL, gpersist.STR_COL_DEPT),
      tbar: toolbar,
      margins: '2 2 2 5',
      padding: '0 0 0 0'
    });
    
    
    Ext.getCmp('tpanel' + me.mid).add(me.plTree);
    Ext.getCmp('tpanel' + me.mid).add(me.plMain); 

  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    if (record && record.data && record.data.id) {
      if (record.data.id == '0000')
        return;
      
      var item = tools.JsonGet(tools.GetUrl(gpersist.ACTION_DEPT_GET + '?deptid=') + record.data.id);

      if (item && !Ext.isEmpty(item.deptid)) {
        tools.SetValue(mep + 'coid', item.coid);
        me.OnRefreshPID();
        tools.SetValue(mep + 'deptid', item.deptid);
        tools.SetValue(mep + 'deptname', item.deptname);
        tools.SetValue(mep + 'deptshort', item.deptshort);
        tools.SetValue(mep + 'deptpid', item.deptpid);
        tools.SetValue(mep + 'deptlevel', item.deptlevel);
        tools.SetValue(mep + 'depttype', item.depttype);
        tools.SetValue(mep + 'deptstatus', item.deptstatus);
        tools.SetValue(mep + 'sortorder', item.sortorder);
        tools.SetValue(mep + 'deptdepth', item.deptdepth);
        tools.FormInit(me.disedits.concat(me.enedits), [], mep);
        tools.BtnsEnable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
        tools.BtnsDisable(['btnSave','btnCancel'], mep); 
        tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SELECT);
      }
      else
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_DEPT));
    }
    else
        tools.alert(Ext.String.format(gpersist.STR_FMT_NOLOAD, gpersist.STR_COL_DEPT));
  },
  
  OnInit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.FormInit(this.disedits.concat(this.enedits), [], this.tranPrefix);
    tools.SetValue(mep + 'coid', tools.GetValue(mep + 'cbCompany'));
    me.OnRefreshPID();
    tools.SetValue(mep + 'deptlevel', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'depttype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'deptstatus', gpersist.SELECT_MUST_VALUE);
  },
  
  OnCancel: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.ResetForm(me.plEdit.getForm());
    tools.FormInit(me.disedits.concat(me.enedits), [], mep);
    tools.BtnsEnable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
    tools.BtnsDisable(['btnSave','btnCancel'], mep);       
    tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SELECT);
  },
  
  OnRefresh: function () {
    var me = this;
    
    if (me.plTree) {
      me.plTree.getRootNode().removeAll(false);
      var proxy = me.plTree.store.getProxy();
      proxy.url = tools.GetUrl('OrgDeptTreeByCoDept.do?coid=') + me.cbCompany.getValue();
      me.plTree.store.setProxy(proxy);
      me.plTree.store.load();
    }
  },
  
  OnNew: function () {
    var me = this;
    var mep = me.tranPrefix;
    var deptid = tools.GetValue(mep + 'deptid');

    if (Ext.isEmpty(deptid))
      tools.alert(Ext.String.format(gpersist.STR_FMT_ADDSAME, gpersist.STR_COL_DEPT));
    else {
      tools.FormInit(me.disnews, me.ennews, mep);
      tools.BtnsDisable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
      tools.BtnsEnable(['btnSave','btnCancel'], mep);
      tools.Resets(['deptid', 'deptname', 'deptshort'], mep);
      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_NEW);   
      
      if (deptid == tools.GetValue(mep + 'deptpid'))
        tools.SetValue(mep + 'deptpid', gpersist.SELECT_NULL_VALUE);
    }
  },
  
  OnNewSub: function () {
    var me = this;
    var mep = me.tranPrefix;
    var deptid = tools.GetValue(mep + 'deptid');
    
    if (Ext.isEmpty(deptid))
      tools.alert(Ext.String.format(gpersist.STR_FMT_ADDSUB, gpersist.STR_COL_DEPT));
    else {      
      tools.FormInit(me.disnews, me.ennews, mep);
      tools.BtnsDisable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
      tools.BtnsEnable(['btnSave','btnCancel'], mep);
      tools.SetValue(mep + 'deptpid', deptid);
      tools.Resets(['deptid', 'deptname', 'deptshort'], mep); 
      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_NEW);     
    }
  },
  
  OnEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(tools.GetValue(mep + 'deptid')))
      tools.alert(Ext.String.format(gpersist.STR_FMT_EDIT, gpersist.STR_COL_DEPT));
    else {
      tools.FormInit(me.disedits, me.enedits, mep);
      tools.BtnsDisable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
      tools.BtnsEnable(['btnSave','btnCancel'], mep);
      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_EDIT);
    }
  },
  
  OnRefreshPID: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var deptpid = tools.GetValue(mep + 'deptpid');
    var cbdeptpid = Ext.getCmp(mep + 'deptpid');
    
    if (cbdeptpid) {
      var store = tools.ComboStoreByUrl('OrgGetSelectDeptByCo.do', {coid: tools.GetValue(mep + 'coid')}, gpersist.SELECT_NULL_VALUE);
      cbdeptpid.bindStore(store);

      if (Ext.isEmpty(deptpid))
        tools.SetValue(mep + 'deptpid', gpersist.SELECT_NULL_VALUE);
      else
        tools.SetValue(mep + 'deptpid', deptpid);
    }
  },
  
  OnSave: function () {
    var me = this;
    var mep = me.tranPrefix;
        
    if (tools.InvalidForm(me.plEdit.getForm())) {
      me.plEdit.form.submit({
        clientValidation: false,
        url: tools.GetUrl(gpersist.ACTION_DEPT_SAVE),
        waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle: gpersist.STR_DATA_WAIT_TITLE,
        timeout: 3000,
        success: function (form, action) {
          tools.FormInit(me.disedits.concat(me.enedits), [], mep);
          tools.BtnsEnable(['btnAdd','btnAddSub','btnFormEdit','btnRefresh'], mep);
          tools.BtnsDisable(['btnSave','btnCancel'], mep);       
          tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SELECT);
          me.OnRefresh();
          me.OnRefreshPID();
        },
        failure: function (form, action) {
          tools.ErrorAlert(action);
        }
      });        
    }
  }
});