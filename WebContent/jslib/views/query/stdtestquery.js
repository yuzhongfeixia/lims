Ext.define('alms.stdtestquery', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '标准检测库综合查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassamplereplace',
      storeUrl: 'BasSearchBasSampleReplace.do',
      expUrl: 'BasSearchBasSampleReplace.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'parameterorder',
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
       { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchparameter', id: mep + 'searchparameter', allowBlank: true },
       '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
       '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
         id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
       ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    me.tbGrid.add(toolbar);
  },
  
//  OnBeforeCreateEdit: function () {
//    var me = this;
//    var mep = this.tranPrefix;
//                   
//    me.editControls = [
//      { xtype: 'label', id: mep + 'stdtestqueryhtml', html: '' },
//      {xtype:'hiddenfield',name:'bsr.prdid',id: mep + 'prdid'}
//    ];
//      
//  },
//  
//  OnAfterCreateEditToolBar:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    me.editToolItems = [
//      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
//      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
//      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
//    ];
//  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bsr.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bsr.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparameter'))
        });
      });
    };
  },
  
//  OnShowTask:function(record){
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    me.html = alms.ShowPrdQueryHtml(record);
//    Ext.fly(Ext.getCmp(me.tranPrefix + 'prdqueryhtml').getEl()).update(me.html);
//  },
  
//  OnLoadData: function (item) {
//    var me = this;
//    var mep = me.tranPrefix;
//    if(!Ext.isEmpty(item.prdid)) {
//      tools.SetValue(mep + 'prdid',item.prdid);
//    }
//    
//    me.OnShowTask(item);
//    return true;
//  },
  
  OnShow:function(){},
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  }
  
});
