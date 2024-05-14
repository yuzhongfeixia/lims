Ext.define('alms.devrepairview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备报修查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devrepairapply',
      storeUrl: 'DevSearchRepairApply.do',
      expUrl: 'DevSearchRepairApply.do',
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
       ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
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
      { xtype: 'label', id: mep + 'devrepairhtml', html: '' },
      {xtype:'hiddenfield',name:'ra.tranid',id: mep + 'tranid'}
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
          'ra.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'ra.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择设备购买申请！');
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
  
  OnShowDevRepairView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDevRepairHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devrepairhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevRepairView(item);
    }
    return true;
  },
  
  ShowDevRepairHtml: function(record){
   var devrepair = tools.JsonGet('DevGetRepairApply.do?ra.tranid='+record.tranid);
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>设备报修查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(devrepair.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">设备编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devrepair.devid)+'</td>'+
      '<td class="infoat" align="center" width="10%">设备名称</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devrepair.devname)+'</td>'+
      '<td class="infoat" align="center" width="10%">设备型号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devrepair.devstandard)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">设备类别</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(devrepair.devtypename)+ '</td>' +
      '<td class="infoat" align="center" width="10%">维修预算(元)</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devrepair.repaircost)+'</td>'+
      '<td class="infoat" align="center" width="10%">故障发生时间</td>' +
      '<td class="infoc  infoleft" colspan=3 align="left" width="15%">'+alms.GetItemDateData(devrepair.faultstart)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">故障情况</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(devrepair.faultdesc)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">维修地点</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(devrepair.repairaddress)+'</td></tr>';
    
    html += '</table>';
    
    
    return html
    
  }
  
});
