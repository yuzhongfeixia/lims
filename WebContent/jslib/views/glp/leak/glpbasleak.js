Ext.define('alms.glpbasleak', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件泄露信息',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_manbasleak',
      storeUrl: 'GlpSearchGlpFileLeak.do',
      saveUrl: 'GlpSaveGlpFileLeak.do',
      hasGridSelect: true,
      expUrl: 'GlpSearchGlpFileLeak.do',
      hasPage: true,
      hasSubmit: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var leakuser = tools.UserPicker( mep + 'leakuser','gfk.leakuser','泄密人编号',80,'100%');
    leakuser.on('griditemclick', me.OnLeakUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('处置编号', 'gfk.tranid', mep + 'tranid', 30, '96%', false, 1),
          tools.FormText('泄露人姓名', 'gfk.leakusername', mep + 'leakusername', 30, '96%', false, 3),
         // tools.FormDate('报告时间', 'gfk.reportdate', mep + 'reportdate', 'Y-m-d H:i', '96%', false, 5,nowdate)
          tools.FormDate('报告时间', 'gfk.reportdate', mep + 'reportdate', 'Y-m-d', '96%', false, 5,nowdate)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          leakuser,
          tools.FormDate('泄密时间', 'gfk.leakdate', mep + 'leakdate', 'Y-m-d ', '100%', true, 4,nowdate),
          {xtype:'hiddenfield',name:'gfk.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('泄密原因', 'gfk.leakreason', mep + 'leakreason', 100, '100%', true, 9, 5),
      tools.FormTextArea('造成后果', 'gfk.leakresult', mep + 'leakresult', 100, '100%', true, 9, 5),
      tools.FormTextArea('泄密简述', 'gfk.leakdesc', mep + 'leakdesc', 200, '100%', true, 9, 5)
    ];
    me.disNews = ['tranid','leakusername'];
    me.disEdits = ['tranid','leakusername'];
    me.enNews = ['reportdate','leakuser','leakreason','leakresult','leakdesc','leakdate'];
    me.enEdits = ['reportdate','leakuser','leakreason','leakresult','leakdesc','leakdate'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '处置编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
     // ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
          'gfk.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
//          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'leakuser', item.leakuser);
      tools.SetValue(mep + 'leakusername', item.leakusername);
      tools.SetValue(mep + 'leakdate', item.leakdate);
      tools.SetValue(mep + 'leakreason', item.leakreason);
      tools.SetValue(mep + 'leakresult', item.leakresult);
      tools.SetValue(mep + 'reportdate', item.reportdate);
      tools.SetValue(mep + 'leakdesc', item.leakdesc);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnLeakUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'leakuser',item.userid);
      tools.SetValue(mep+'leakusername',item.username);
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
  
  OnReadSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var leakuser = Ext.getCmp(mep+'leakuser');
    me.OnReadBeforeLoad();
    leakuser.store.loadPage(1);    
  },
    
  OnReadBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var leakuser = Ext.getCmp(mep+'leakuser');
    if(leakuser.store) {      
      leakuser.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'sa.tranid': tools.GetValueEncode(mep + 'searchreadid')
//          'decb.devname': tools.GetValueEncode(mep + 'searchdevforcalibname')
        });
      });
    };
  },
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : '提交', iconCls : 'icon-deal', handler : me.OnSave, scope : me },  
     //'-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSave, scope: me },
     //'-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler: function () { me.winEdit.hide(); }, scope : me }
   ];
   
//   if (me.hasSubmit) {
//     Ext.Array.insert(me.editToolItems, 2, [
//       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
//     ]);
//   }
   
   if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
  }

   
});