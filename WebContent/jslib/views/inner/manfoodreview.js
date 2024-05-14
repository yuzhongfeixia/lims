Ext.define('alms.manfoodreview', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '食品检验机构自己认定评审准则',
      winWidth: 750,
      winHeight: 500,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_innerfoodreview',
      storeUrl: 'InnerSearchInnerFoodReview.do',
      saveUrl: 'InnerSaveInnerFoodReview.do',
      hasGridSelect: true,
      expUrl: 'InnerSearchInnerFoodReview.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'ifr.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormCombo('检查结论', 'ifr.checkresult', mep + 'checkresult', tools.ComboStore('CheckResult', gpersist.SELECT_MUST_VALUE), '96%', false, 5),
          tools.FormDate('记录日期', 'ifr.trandate', mep + 'trandate', 'Y-m-d', '96%', false, 6,nowdate)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('序号', 'ifr.serial', mep + 'serial',10, '100%', false, 2),
          tools.FormText('记录人', 'ifr.tranusername', mep + 'tranusername', 10, '100%', false, 4),
          {xtype:'hiddenfield',name:'ifr.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'ifr.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('检查内容', 'ifr.recontent', mep + 'recontent', 200, '100%', true, 8, 7),
      tools.FormTextArea('检查方法及关键点', 'ifr.checkmethod', mep + 'checkmethod', 300, '100%', true, 8, 7)
    ];

    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['serial', 'recontent', 'checkmethod', 'checkresult'];
    me.enEdits = [ 'serial', 'recontent', 'checkmethod', 'checkresult'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername',gpersist.UserInfo.user.username );
    tools.SetValue(mep + 'checkresult',gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var checkresult = Ext.getCmp(mep+'checkresult').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(checkresult == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择检查结论！');
        return;
      }
    }
    return true;
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'ifr.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    if(record && !Ext.isEmpty(record.tranid)){
      var item = tools.JsonGet(tools.GetUrl('InnerGetInnerFoodReview.do?ifr.tranid=') + record.tranid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'serial', item.serial);
      tools.SetValue(mep + 'recontent', item.recontent);
      tools.SetValue(mep + 'checkmethod', item.checkmethod);
      tools.SetValue(mep + 'checkresult', item.checkresult);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
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