Ext.define('alms.userquery', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员信息综合查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basuser',
      storeUrl: 'StaffSearchBasUser.do',
      expUrl: 'StaffSearchBasUser.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var nowdate = new Date();
    
    me.OnInitGridToolBar();

    var items = [
       ' ', { xtype: 'textfield', fieldLabel: '人员名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchusername', id: mep + 'searchusername', allowBlank: true },
       '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
       '', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
       '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
         id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
       ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'userqueryhtml', html: '' },
      {xtype:'hiddenfield',name:'bu.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bu.username': tools.GetEncode(tools.GetValue(mep + 'searchusername'))
        });
      });
    };
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    me.html = alms.ShowUserQueryHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'userqueryhtml').getEl()).update(me.html);
    
    viewFile = function (fileurl,filename) {
      alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
    };
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
    }
    
    me.OnShowTask(item);
    return true;
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  }
  
});
