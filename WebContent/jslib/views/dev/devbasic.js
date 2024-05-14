Ext.define('alms.devbasic', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '仪器设备基本信息',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devbasic',
      storeUrl: 'DevSearchDevBasic.do',
      saveUrl: 'DevSaveDevBasic.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevBasic.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'devid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var devitems = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevfordevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevfordevname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'db.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 1,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcalib',
      storeUrl: 'DevSearchDevCalib.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
//          tools.FormText('设备编号', 'db.devid', mep + 'devid', 30, '96%', false, 1),
          devid,
          tools.FormCombo('设备类型', 'db.devtype', mep + 'devtype', tools.ComboStore('DevType', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
          tools.FormText('出厂编号', 'db.factorycode', mep + 'factorycode', 20, '96%', false, 5),
          tools.FormDate('购买日期', 'db.buydate', mep + 'buydate', 'Y-m-d H:i', '96%', true, 7),
          tools.FormText('设备用途', 'db.devusage', mep + 'devusage', 40, '96%', false, 9),
          tools.FormText('设备管理员', 'db.devmanager', mep + 'devmanager', 10, '96%', false, 11)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('设备名称', 'db.devname', mep + 'devname', 40, '100%', false, 2),
          tools.FormText('生产厂家', 'db.factoryname', mep + 'factoryname', 40, '100%', false, 4),
          tools.FormText('设备型号', 'db.devstandard', mep + 'devstandard', 40, '100%', false, 6),
          tools.FormText('价格', 'db.devprice', mep + 'devprice', 20, '100%', false, 8,'is15p2'),
          tools.FormText('放置地点', 'db.devplace', mep + 'devplace', 10, '100%', false, 10),
          tools.FormText('管理员姓名', 'db.devmanagername', mep + 'devmanagername', 10, '100%', false, 12),
          {xtype:'hiddenfield',name:'db.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('接收(启用)状态', 'db.devstatus', mep + 'devstatus', 100, '100%', true, 9, 4),
      tools.FormTextArea('检定(校准)情况', 'db.devcheck', mep + 'devcheck', 100, '100%', true, 9, 4)
    ];
    me.disNews = ['devname', 'devtype', 'factoryname', 'factorycode', 'devstandard', 'buydate', 'devprice', 'devusage', 'devmanager', 'devmanagername',  'devstatus', 'devcheck', 'devremark','devplace'];
    me.disEdits = ['devname', 'devtype', 'factoryname', 'factorycode', 'devstandard', 'buydate', 'devprice', 'devusage', 'devmanager', 'devmanagername',  'devstatus', 'devcheck', 'devremark','devplace'];
    me.enNews = ['devid'];
    me.enEdits = ['devid'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
//    tools.SetValue(mep + 'devtype', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
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
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'db.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'db.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.devid)){
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devtype', item.devtype);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'buydate', item.buydate);
      tools.SetValue(mep + 'devprice', item.devprice);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'devplace', item.devplace);
      tools.SetValue(mep + 'devstatus', item.devstatus);
      tools.SetValue(mep + 'devcheck', item.devcheck);
      tools.SetValue(mep + 'devremark', item.devremark);

      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'devid').reset();
    Ext.getCmp(mep + 'devid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.devid) {
          tools.SetValue(mep + 'devid', action.result.data.devid);
        }
      }
    }
  },
  
  OnDevSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var records = tools.JsonGet(tools.GetUrl('DevGetDevPlan.do?devplan.devid=') +item.devid);
    var items = tools.JsonGet(tools.GetUrl('DevGetAcceptManage.do?am.devid=') +item.devid);
    var record = tools.JsonGet(tools.GetUrl('DevGetBuyApply.do?ba.tranid=') +items.applyid);
    if(item){
      tools.SetValue(mep + 'devid', items.devid);
      tools.SetValue(mep + 'devname', items.devname);
      tools.SetValue(mep + 'devtype', record.devtype);
      tools.SetValue(mep + 'factoryname', items.factoryname);
      tools.SetValue(mep + 'devstandard', items.devstandard);
      tools.SetValue(mep + 'factorycode', items.factorycode);
      tools.SetValue(mep + 'buydate', items.instaldate);
      tools.SetValue(mep + 'devmanager', items.devmanager);
      tools.SetValue(mep + 'devmanagername', items.devmanagername);
      tools.SetValue(mep + 'devprice', items.devprice);
      tools.SetValue(mep + 'devstatus', items.instaldesc);
      tools.SetValue(mep + 'devcheck', item.calibrequire);
      tools.SetValue(mep + 'devusage', records.devusage);
      tools.SetValue(mep + 'devplace', record.applydeptname);
    }
  },
  
  OnDevSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var devid = Ext.getCmp(mep+'devid');
    me.OnDevBeforeLoad();
    devid.store.loadPage(1);    
  },
    
  OnDevBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var devid = Ext.getCmp(mep+'devid');
    if(devid.store) {      
      devid.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'ds.devid': tools.GetValueEncode(mep + 'searchdevfordevid'),
          'ds.devname': tools.GetValueEncode(mep + 'searchdevfordevname')
        });
      });
    };
  }
   
});
