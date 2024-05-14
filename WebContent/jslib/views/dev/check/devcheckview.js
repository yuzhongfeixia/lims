Ext.define('alms.devcheckview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '期间核查查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcheck',
      storeUrl: 'DevSearchDevCheck.do',
      expUrl: 'DevSearchDevCheck.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'checkid',
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
      { xtype: 'label', id: mep + 'devcheckhtml', html: '' },
      {xtype:'hiddenfield',name:'deck.checkid',id: mep + 'checkid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },                   
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
          'deck.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'deck.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择设备！');
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
  
  OnShowDevCheckView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDevCheckHtml(record);
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devcheckhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.checkid)) {
      tools.SetValue(mep + 'checkid',item.checkid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevCheckView(item);
    }
    return true;
  },
  
  ShowDevCheckHtml: function(record){
    var devcheck = tools.JsonGet('DevGetDevCheck.do?deck.checkid='+record.checkid);
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>期间核查记录表</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-5-7</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
//    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">仪器名称</td>' +
//    '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.devname) +'</td></tr>';
//    
//    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">生产单位</td>' +
//    '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.factoryname) +'</td></tr>';
//
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">仪器设备名称</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.devname) +'</td>';
    html += '<td class="infoat" align="center"  width="10%">编号</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.devid) +'</td>';
    html += '<td class="infoat" align="center"  width="10%">制造商</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.factoryname) +'</td></tr>';
    html + '</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">上次检定（校核）日期</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.devname) +'</td>';
    html += '<td class="infoat" align="center"  width="10%">测量范围</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.devrange) +'</td>';
    html += '<td class="infoat" align="center"  width="10%">精度</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(devcheck.devprecision) +'</td></tr>';
    html + '</td></tr>';
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center" colspan=14 width="20%">检 查 记 录</td></tr>' 
      
      
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">检查日期</td><td class="infoat" align="center" colspan=5 width="10%">检查情况</td><td class="infoat" align="center" colspan=5 width="10%">结论</td><td class="infoat" align="center" width="20%">审批</td></tr>' ; 
    html += '<tr class="infotr" style="height: 250"><td class="infoc infoleft" align="center"   width="20%">'+alms.GetItemDateData(devcheck.checkdate)+'</td><td class="infoc infoleft" align="left" colspan=5  width="20%">' + alms.GetItemData(devcheck.checkdesc) + '</td><td class="infoc infoleft" align="left" colspan=5  width="20%">'+ alms.GetItemData(devcheck.checkresult)+'</td><td class="infoc infoleft" align="center"  width="20%">'+alms.GetItemData(devcheck.checkapprove)+'</td></tr>' ;  
    
    html += '<tr class="infotr" ><td class="infoc infoleft" style="height:100;" rowspan=2 colspan=14 align="left" width="20%" >备注：'+'</br></br></br><center>' + alms.GetItemData(devcheck.remark)  + '</center>'+'</td></tr>';
    
   
    
    html += '</table>';
    
    
    
    return html
    
  },
  OnPrintTask: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (!Ext.isEmpty(me.html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("样品任务单打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//        LODOP.PRINT();//打印功能
    }
  }
  
});
