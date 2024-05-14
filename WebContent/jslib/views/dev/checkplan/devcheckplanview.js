Ext.define('alms.devcheckplanview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '期间核查计划查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcheckplan',
      storeUrl: 'DevSearchDevCheckPlan.do',
      expUrl: 'DevSearchDevCheckPlan.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'核查开始时间',labelWidth:80,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:nowdate,selectOnFocus: false, allowBlank: true},
      '-', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, 1),selectOnFocus:false,allowBlank:true},
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
      { xtype: 'label', id: mep + 'devcheckplanhtml', html: '' },
      {xtype:'hiddenfield',name:'dcp.tranid',id: mep + 'tranid'}
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
          'dcp.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'dcp.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'dcp.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
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
    var record = tools.OnGridLoad(me.plGrid, '请选择数据！');
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
  
  OnShowDevCheckPlanView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDevCheckPlanHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devcheckplanhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevCheckPlanView(item);
    }
    return true;
  },
  
  ShowDevCheckPlanHtml: function(record){
    var devcheckplan = tools.JsonGet('DevGetDevCheckPlan.do?dcp.tranid='+record.tranid);
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>设备期间核查查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(devcheckplan.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">设备编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.devid)+'</td>'+
      '<td class="infoat" align="center" width="10%">仪器名称</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.devname)+'</td>'+
      '<td class="infoat" align="center" width="10%">设备型号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.devstandard)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出厂编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(devcheckplan.factorycode)+ '</td>' +
      '<td class="infoat" align="center" width="10%">生产厂商</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.factoryname)+'</td>'+
      '<td class="infoat" align="center" width="10%">上次检定时间</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(devcheckplan.lastcheckdate)+'</td>'+
      '<td class="infoat" align="center" width="10%">核查计划时间</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(devcheckplan.devcheckdate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">下次检定时间</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemDateData(devcheckplan.nextdate)+ '</td>' +
      '<td class="infoat" align="center" width="10%">检定周期(月)</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.devperiod)+'</td>'+
      '<td class="infoat" align="center" width="10%">使用温度</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.usetemp)+'</td>'+
      '<td class="infoat" align="center" width="10%">使用湿度</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devcheckplan.usehumid)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">设备用途</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(devcheckplan.devusage)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">技术指标</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(devcheckplan.devrange)+'</td></tr>';
    html += '</table>';
    
    
    return html
    
  }
  
});
