Ext.define('alms.mancrmrecept', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: ['ReceptType'],
      editInfo: '接待记录',
      winWidth: 750,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_crmrecept',
      storeUrl: 'CrmSearchCrmRecept.do',
      saveUrl: 'CrmSaveCrmRecept.do',
      expUrl: 'CrmSearchCrmRecept.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var tranuser = tools.UserPicker(mep + 'tranusername','crmrecept.tranusername','接待人');
     
    tranuser.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('接待编号', 'crmrecept.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormCombo('处理类型', 'crmrecept.recepttype', mep + 'recepttype', tools.ComboStore('ReceptType', gpersist.SELECT_MUST_VALUE), '96%', false, 3)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormDate('接待时间', 'crmrecept.trandate', mep + 'trandate', 'Y-m-d', '96%', false,2,nowdate),
          tranuser,
          {xtype:'hiddenfield',name:'crmrecept.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'crmrecept.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('接待地点', 'crmrecept.receptaddr', mep + 'receptaddr', 30, '98%', false, 5,2),
      tools.FormTextArea('接待对象', 'crmrecept.receptobject', mep + 'receptobject', 50, '98%', false,6,2),
      tools.FormTextArea('事由', 'crmrecept.receptreason', mep + 'receptreason', 50, '98%', false, 7,3),
      tools.FormTextArea('处理描述', 'crmrecept.receptdesc', mep + 'receptdesc', 300, '98%', true,8,7),
      tools.FormTextArea('备注', 'crmrecept.remark', mep + 'remark', 200, '98%', true,8,3)
    ];
    
    me.disNews = ['tranid', 'tranuser'];
    me.disEdits = ['tranid', 'tranuser'];
    me.enNews = ['trandate', 'receptaddr', 'receptobject', 'receptreason', 'receptdesc', 'recepttype', 'tranusername', 'remark' ];
    me.enEdits = [ 'trandate', 'receptaddr', 'receptobject', 'receptreason', 'receptdesc', 'recepttype', 'tranusername', 'remark'];
  },
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"tranuser",item.userid);
      tools.SetValue(mep+"tranusername",item.username);
    }
   },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '接待编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
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
    
    tools.SetValue(mep + 'recepttype',gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'crmrecept.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'receptaddr', item.receptaddr);
    tools.SetValue(mep + 'receptobject', item.receptobject);
    tools.SetValue(mep + 'receptreason', item.receptreason);
    tools.SetValue(mep + 'receptdesc', item.receptdesc);
    tools.SetValue(mep + 'recepttype', item.recepttype);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'remark', item.remark);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  }
  
});