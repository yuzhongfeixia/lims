Ext.define('alms.manbasdevtest', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备检测参数设置',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDev.do',
      saveUrl: 'DevSaveDevTest.do',
      hasGridSelect: true,
      expUrl: 'DevSearchBasDev.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'devid'
    });
    me.callParent(arguments);
  },
  
  
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'devid', item.devid);
    tools.SetValue(mep + 'devname', item.devname);
    return true;
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
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '分配', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
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
          'bd.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
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
  
  
  OnShow: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    
    me.OnCreateDetailWin();
    me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
    if(me.winDetail){      
      me.winDetail.show();
    }
    
    me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, false);
    
    if (!me.OnLoadData(record)) 
      return;
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
  },
  
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: '分配保存', iconCls: 'icon-save', handler: me.OnSave, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
      region : 'north',
      height : '15%',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editControls    
    });
    
    me.OnBeforeCreateEditss();
    
    me.plAttDetailGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'attdetailgrid',
      region : 'center',
      columnWidth:1,
      scope: me,
      items: [me.partsGrid]    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
      width: 600,
      height: 500,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: false,
      draggable: true,
      constrain: true,
      items : [me.plEdit,me.plAttDetailGrid]
    });
    
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('设备编号', 'devtest.devid', mep + 'devid', 40, '96%', false, 3)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('设备名称', 'devtest.devname', mep + 'devname', 40, '100%', false, 2),
          {xtype:'hiddenfield',name:'bd.deal.action',id: mep + 'datadeal'}
        ]}
      ]}
    ];
    me.disNews = ['devid','devname'];
    me.disEdits = ['devid','devname'];
    me.enNews = [];
    me.enEdits = [];
  },
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.winDetail.hide();
  },
  
  OnBeforeCreateEditss: function () {
    var me = this, mep = this.tranPrefix;
    
    var partsColumn = [];
    var partsField = [];    

    tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_basdevtest'), partsColumn, partsField, mep + '_a_');
    var record = tools.OnGridLoad(me.plGrid, '请选择需要修改的数据！');
    me.partsStore = tools.CreateGridStore(tools.GetUrl("DevGetListDevTest.do?devtest.devid=") + record.devid, partsField);
    me.partsGrid = Ext.create('Ext.grid.Panel', {
      region : 'center',
      title : '检测参数配置',
      autoScroll : true,
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : partsColumn,
      store : me.partsStore,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListSelectParts, scope : me }
      }    
    });
    me.partsStore.load();
//    me.plDetail.add(me.partsGrid);
    me.partsitems = [
      ' ', { id : mep + 'btnPartsAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewParts, scope : me },
      ' ', { id : mep + 'btnPartsDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteParts, scope : me }
    ];
    
//    me.OnAfterCreateDetailToolBar();
    
    tools.SetToolbar(me.partsitems, mep);
      
    var tbdev = Ext.create('Ext.toolbar.Toolbar', {
      dock : 'top',
      items : me.partsitems
    });
    me.partsGrid.insertDocked(0, tbdev);
    
  },
  
  OnListNewParts: function () {
    var me = this, mep = me.mep;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'selectdevtest', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.paramdetail.on('formlast', me.OnParameterSave, me);
      me.paramdetail.OnFormLoad();
    }
    else
      me.paramdetail.OnFormShow();
    
    me.paramdetail.OnInitData(tools.GetValue(mep + 'sampleid'));    
    me.paramdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.parameterrecord = null;    
  },
  
  OnListDeleteParts : function() {
    var me = this;
   
    var j = me.partsGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.partsGrid.store.remove(me.partsGrid.selModel.selected.items[0]);
    }
   
    me.partsGrid.getView().refresh();
  },
  
  OnParameterSave: function (e, data) {
    var me = this, mep = me.mep;
    var record = me.parameterrecord;
    devid = tools.GetValue(mep+'devid');
    if (record == null) record = me.partsGrid.store.model.create({});
    record.data.devid = devid; 
    record.data.sampleid = data.sampleid;
    record.data.samplename = data.samplename;
    record.data.parameterid = data.parameterid;
    record.data.parametername = data.parametername;
    if (me.parameterrecord)
      me.partsGrid.getView().refresh();
    else
      me.partsGrid.store.insert(me.partsGrid.store.getCount(), record);
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
      var details = [];
      var devid = [];
      for (i = 0; i < me.partsGrid.store.getCount(); i++) {
        details.push(me.partsGrid.store.getAt(i).data);
      }
      me.saveParams = { details: Ext.encode(details) };
  },
  
  OnFormPrint:function(){
    var me = this, mep = this.tranPrefix;
    
    var devid = tools.GetValue(mep + 'devid');
    
    if (Ext.isEmpty(devid)) return;
    
    html = '<table style="page-break-after: always;">';
    html += '<tr width="150"><td width="60" style="padding-left:10px;"><image width="60px" src="qrcode.do?width=100&code=' + devid + '"></td>' + 
      '<td width="90" style="font-size:12px;font-weight:bold;padding-left:4px;word-wrap:break-word;" valign="top" align="left">编号:<br/>' + devid + 
      '<br />名称:<br/>' + tools.GetValue(mep + 'devname') + '</td></tr>';
    html += '</table>';
    
    if (!Ext.isEmpty(html))
      Ext.ux.Print.QRCodePrint.print(html);
  }
   
});