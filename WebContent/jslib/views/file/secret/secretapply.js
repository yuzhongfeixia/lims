Ext.define('alms.secretapply', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件机密查阅',
      winWidth: 750,
      winHeight: 250,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_secretapply',
      storeUrl: 'IncFileSearchSecretApply.do',
      saveUrl: 'IncFileSaveSecretApply.do',
      hasGridSelect: true,
      expUrl: 'IncFileSearchSecretApply.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('查阅编号', 'sa.tranid', mep + 'tranid', 20, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('查阅人', 'sa.readusername', mep + 'readusername', 30, '100%', false, 2),
          {xtype:'hiddenfield',name:'sa.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('查阅内容', 'sa.readcontent', mep + 'readcontent', 200, '100%', false, 4,6),
      tools.FormTextArea('备注', 'sa.remark', mep + 'remark', 200, '100%', true, 5, 4)
    ];
    me.disNews = ['tranid','readusername'];
    me.disEdits = ['tranid','readusername'];
    me.enNews = ['readcontent','remark'];
    me.enEdits = ['readcontent','remark'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'readusername', gpersist.UserInfo.user.username );
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '查阅编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
      ' ', { id: mep + 'btnCommit', text: '提交', iconCls: 'icon-deal', handler: me.OnCommit,scope: me},
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'sa.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      var items = tools.JsonGet(tools.GetUrl('IncFileGetSecretApply.do?sa.tranid=') + item.tranid);
      tools.SetValue(mep + 'tranid', items.tranid);
      tools.SetValue(mep + 'readcontent', items.readcontent);
      tools.SetValue(mep + 'remark', items.remark);
      tools.SetValue(mep + 'readusername', items.readusername);
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
  },
  
   OnBeforeDelete: function (datas) {
    var me = this;
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({attachid: datas[i].data.attachid});
    }
    
    me.deleteParams = {deletes : Ext.encode(json)};
  },
  
  OnCommit:function(){
    var me = this;
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    var commitUrl = 'IncFileCommitSecretApply.do'
    
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({tranid: datas[i].data.tranid});
    }
    
    me.commitParams = {commits : Ext.encode(json)};
        
    Ext.Ajax.request({
      url: tools.GetUrl(commitUrl),
      params: me.commitParams,
      async: false,
      method: 'POST',
      success: function (response, opts) {
        tools.ResponseAlert(response, function () {
          me.OnSearch();
          tools.alert('提交成功');
        });
      },
      failure: function (response) {
        tools.ResponseAlert(response);
      }
    });
  }
   
});