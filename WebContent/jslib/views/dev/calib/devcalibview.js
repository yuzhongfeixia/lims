Ext.define('alms.devcalibview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备检定校准查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcalib',
      storeUrl: 'DevSearchDevCalib.do',
      expUrl: 'DevSearchDevCalib.do',
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
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
      { xtype: 'label', id: mep + 'devcalibhtml', html: '' },
      {xtype:'hiddenfield',name:'decb.tranid',id: mep + 'tranid'}
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
          'decb.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid'))
//          'decb.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
  
  OnShowDevCalibView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDevCalibHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devcalibhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevCalibView(item);
    }
    return true;
  },
  
  ShowDevCalibHtml: function(record){
    var devcalibplan = tools.JsonGet('DevGetDevCalibPlan.do?decp.tranid='+record.tranid);

    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>设备检定校准查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +record.tranid+ '</td>' +
      '<td class="infoat" align="center" width="10%">设备编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+record.devid+'</td>'+
      '<td class="infoat" align="center" width="10%">设备管理员</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+record.devmanager+'</td>'+
      '<td class="infoat" align="center" width="10%">管理员姓名</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+record.devmanagername+'</td></tr>';

    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检定日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +Ext.Date.format(record.calibuserdate, 'Y-m-d')+ '</td>' +
      '<td class="infoat" align="center" width="10%">最近检定</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+Ext.Date.format(record.lastdate, 'Y-m-d')+'</td>'+
      '<td class="infoat" align="center" width="10%">下次检定</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+Ext.Date.format(record.nextdate, 'Y-m-d')+'</td>'+
      '<td class="infoat" align="center" width="10%">检定周期(月)</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+record.devperiod+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">规格型号</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.specimodel+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出厂编号</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.factorycode+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">设备数量</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.devcount+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">测量范围</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.measurerange+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">准确等级/最大允许误差/不确定度</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.problemdesc+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">校准结果</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.calibresult+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+record.remark+'</td></tr>';
    
    html += '</table>';
    
    
    return html
    
  }
  
});
