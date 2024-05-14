Ext.define('alms.manbasconsign', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '客户委托书查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busgetview',
      storeUrl: 'LabSearchBusGetForView.do',
      expUrl: 'LabSearchBusGetForView.do',
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
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '联系电话', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtele', id: mep + 'searchtele', allowBlank: true },
      '-', tools.GetToolBarCombo('searchprocate', mep + 'searchprocate', 180, '样品类别', 70, tools.ComboStore('ProductCate', gpersist.SELECT_MUST_VALUE)),
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 100, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 100, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true }
    ];
    
    var items1 = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    
    me.tbGrid.add(toolbar);
    me.tbGrid.add(toolbar1);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'getbasconsignhtml', html: '' },
      {xtype:'hiddenfield',name:'bg.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'bg.samplecode',id: mep + 'samplecode'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrint', text: '打印', iconCls: 'icon-print', handler: me.OnPrint, scope: me },
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
          'bg.testedname':tools.GetValueEncode(mep+'searchtestedname'),
          'bg.entertele':tools.GetValueEncode(mep+'searchtele'),
          'bg.productcate':tools.GetValueEncode(mep+'searchprocate'),
          'bg.samplecode':tools.GetValueEncode(mep+'searchsamplecode'),
          'bg.samplename':tools.GetValueEncode(mep+'searchsamplename')
        });
      });
    };
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
    me.OnAfterCreateEdit();
  },
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看样品记录！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
  },
  
  ShowBasConsign:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var gettype = record.gettype;
    
    if(gettype=='01'){
      var formid = '000008';
    }else if(gettype=='02'){
      var formid = '000007';
    }else if(gettype=='03'){
      var formid = '000009';
    }else if(gettype=='04'){
      var formid = '000010';
    }else if(gettype=='05'){
      var formid = '000001';
    }else if(gettype=='06'){
      var formid = '000005';
    }else if(gettype=='07'){
      var formid = '000003';
    }else if(gettype=='08'){
      var formid = '000004';
    }else if(gettype=='09'){
      var formid = '000002';
    }else if(gettype=='10'){
      var formid = '000006';
    }else if(gettype=='11'){
	  var formid = '000011';
    }else if(gettype=='12'){
	var formid = '000012';
}else{
	var formid = '000004';
}
    
    me.html = alms.ShowBasConsign(formid,record.tranid,record.samplecode);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'getbasconsignhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      tools.SetValue(mep + 'samplecode',item.samplecode);
      me.tabMain.setActiveTab(1);
      me.ShowBasConsign(item);
    }
    return true;
  },
  
  OnPrint: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("试验报告打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
    }
    
    return;
    
  }
  
});
