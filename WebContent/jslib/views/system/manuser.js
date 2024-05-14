Ext.define('gpersist.manuser', {
  extend: 'gpersist.base.lteditform',

  cbCompany: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.cbCompany = null;
  },
  
  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: ['Company', 'UserPost','UserLevel','UserTitle','UserStatus','Dept'],
      treeTitle: '机构列表',
      mainTitle: '操作员列表',
      editInfo: '操作员',
      modelName: 'gpersist.gfdeptmodel',
      treeUrl: 'OrgDeptTree.do',
      winWidth: 750,
      winHeight: 320,
      columnUrl: 'p_get_user',
      storeUrl: 'UserSearchUser.do',
      saveUrl: 'UserSaveUser.do',
      expUrl: 'UserSearchUser.do',
      hasAutoLoad: false,
      hasPage: true,
      hasPrevNext: true
    });

    me.callParent(arguments);
  },

  OnBeforeCreateTree: function () {
    var me = this;
    var mep = me.tranPrefix;
    
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
            proxy.url = tools.GetUrl(gpersist.ACTION_DEPT_TREE) + '?coid=' + me.cbCompany.getValue();
            me.plTree.store.setProxy(proxy);
            me.plTree.store.load();
          }, scope: me
        }
      }
    });

    if (me.cbCompany.store.data.length > 0)
      me.cbCompany.setValue(me.cbCompany.store.getAt(0).data.id);
      
    me.treeTools = [me.cbCompany];
    
    me.treeUrl = tools.GetUrl(gpersist.ACTION_DEPT_TREE) + '?coid=' + me.cbCompany.getValue();
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}
      ]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    var otheritems = [
      ' ', {xtype: 'textfield', fieldLabel:'操作员姓名',labelWidth:75,width:200,maxLength:22,name:'listusername',id:mep + 'listusername',allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }           
    ];
    
    var othertoolbar = Ext.create('Ext.toolbar.Toolbar', {items: otheritems, border: false});
    
    me.tbGrid.add(othertoolbar);
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var nowdate = new Date();

    me.editControls = [{xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor',
            items: [
              tools.FormText('操作员编号', 'user.userid', mep + 'userid', 14, '96%', false, 1),
              tools.FormDate('出生日期', 'user.birthdate', mep + 'birthdate', 'Y-m-d', '96%', true, 4,nowdate),
              tools.FormText('从事年限', 'user.workyear', mep + 'workyear', 32, '96%', true, 7),
              tools.FormText('本岗年限', 'user.postyear', mep + 'postyear', 32, '96%', true, 7),
              tools.FormText('电子邮件', 'user.useremail', mep + 'useremail', 32, '96%', true, 8),
              tools.FormCheck('是否管理员', 'user.isadmin', mep + 'isadmin', false, true, 10)]
          },
          
          { xtype: 'container', columnWidth: .33, layout: 'anchor',
            items: [
              tools.FormText('操作员姓名', 'user.username', mep + 'username', 32, '96%', true, 7),   
              tools.FormCombo('文化程度', 'user.useredu', mep + 'useredu', tools.ComboStore('UserEdu', gpersist.SELECT_MUST_VALUE), '96%', false, 4),
              tools.FormCombo('所属部门', 'user.deptid', mep + 'deptid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
              tools.FormCombo('级   别', 'user.userlevel', mep + 'userlevel', tools.ComboStore('UserLevel', gpersist.SELECT_MUST_VALUE), '96%', false, 5),
              tools.FormCombo('状   态', 'user.userstatus', mep + 'userstatus', tools.ComboStore('UserStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 9)]
          },
          
          { xtype: 'container', columnWidth: .34, layout: 'anchor',
            items: [
              tools.FormCombo('性   别', 'user.usersex', mep + 'usersex', tools.ComboStore('UserSex', gpersist.SELECT_MUST_VALUE), '100%', false, 3),
              tools.FormCombo('职   称', 'user.worktitle', mep + 'worktitle', tools.ComboStore('WorkTitle', gpersist.SELECT_MUST_VALUE), '100%', false, 3),
              tools.FormCombo('部门岗位', 'user.userpost', mep + 'userpost', tools.ComboStore('UserPost', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
              tools.FormText('联系电话', 'user.usertele', mep + 'usertele', 32, '100%', true, 7),
              tools.FormCombo('是否正式', 'user.usertitle', mep + 'usertitle', tools.ComboStore('UserTitle', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
              {xtype:'hiddenfield',name:'user.deal.action',id: mep + 'datadeal'}]
          }]
        },
    tools.FormTextArea('所学专业', 'user.usermajor', mep + 'usermajor', 30, '100%', true, null, 2),
    tools.FormTextArea('备注', 'user.remark', mep + 'remark', 30, '100%', true, null, 2)   
    ];

    me.disNews = [];
    me.disEdits = ['userid'];
    me.enNews = ['userid','username','userpost','usertitle','deptid','userstatus','usertele','isadmin','userlevel','useremail',
                 'usersex','birthdate','worktitle','useredu','workyear','postyear','usermajor','remark'];
    me.enEdits = ['username','userpost','usertitle','deptid','userstatus','usertele','isadmin','userlevel','useremail',
                  'usersex','birthdate','worktitle','useredu','workyear','postyear','usermajor','remark'];
  },

  OnAfterCreateEdit: function () {
    tools.SetCheckBoxValue(['isadmin'], this.tranPrefix);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'user.deptid': me.nowtid,
          'user.username': tools.GetValueEncode(mep + 'listusername')   
        });
      });
    }
  },
  
  OnCheckTreeSelect: function (isedit) {
    var me = this;
    
    if (isedit)
      return false;
    
    if (Ext.isEmpty(me.nowtid)) {
      tools.alert('请先在树形列表中选择机构再进行查询！');
      return true;
    }
    
    return false;
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.ResetForm(me.plEdit.getForm());
    
    tools.DeleteParam('DeptByCo');
    tools.GetParamByUrl('DeptByCo', tools.GetUrl('OrgGetSelectDeptByCo.do?coid=') + tools.GetValue(mep + 'cbCompany'));
    Ext.getCmp(mep + 'deptid').bindStore(tools.ComboStore('DeptByCo', gpersist.SELECT_MUST_VALUE));
    
    tools.SetValue(mep + 'deptid', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userpost', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userlevel', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'usertitle', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userstatus', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'isadmin', false);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;

    if (item && item.userid && !Ext.isEmpty(item.userid)) {
      
      var user = tools.JsonGet('UserGetUser.do?userid='+item.userid);
      
      tools.DeleteParam('DeptByCo');
      tools.GetParamByUrl('DeptByCo', tools.GetUrl('OrgGetSelectDeptByCo.do?coid=') + tools.GetValue(mep + 'cbCompany'));
      Ext.getCmp(mep + 'deptid').bindStore(tools.ComboStore('DeptByCo', gpersist.SELECT_MUST_VALUE));
      
      tools.SetValue(mep + 'userid', item.userid);
      tools.SetValue(mep + 'username', item.username);
      tools.SetValue(mep + 'deptid', item.deptid);
      tools.SetValue(mep + 'userpost', item.userpost);
      tools.SetValue(mep + 'userlevel', item.userlevel);
      tools.SetValue(mep + 'usertitle', item.usertitle);
      tools.SetValue(mep + 'userstatus', item.userstatus);
      tools.SetValue(mep + 'usertele', item.usertele);
      tools.SetValue(mep + 'useremail', item.useremail);
      tools.SetValue(mep + 'isadmin', item.isadmin);
      tools.SetValue(mep + 'usermajor', user.usermajor); 
      tools.SetValue(mep + 'workyear', user.workyear); 
      tools.SetValue(mep + 'postyear', user.postyear); 
      tools.SetValue(mep + 'usersex', user.usersex); 
      tools.SetValue(mep + 'useredu', user.useredu);
      tools.SetValue(mep + 'worktitle', user.worktitle); 
      tools.SetValue(mep + 'birthdate', user.birthdate); 
      tools.SetValue(mep + 'remark', user.remark); 
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, gpersist.STR_COL_USER));
  },

  OnBeforePrint: function () {
    Ext.ux.grid.Printer.printTitle = '操作员';
  }
});