Ext.define('alms.repairrecordview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备维修记录查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_repairrecord',
      storeUrl: 'DevSearchRepairRecord.do',
      expUrl: 'DevSearchRepairRecord.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevnameforrepairrecord', id: mep + 'searchdevnameforrepairrecord', allowBlank: true },
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
      { xtype: 'label', id: mep + 'repairrecordhtml', html: '' },
      {xtype:'hiddenfield',name:'rere.tranid',id: mep + 'tranid'}
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
          //'rere.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'rere.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevnameforrepairrecord'))
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
  
  OnShowRepairRecordView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowRepairRecordHtml(record);
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'repairrecordhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowRepairRecordView(item);
    }
    return true;
  },
  
  //预览html内容
  ShowRepairRecordHtml:function(item){
    var me = this;
    var mep = me.tranPrefix;
    var repairrecord = tools.JsonGet('DevGetRepairRecord.do?rere.tranid='+item.tranid);
     var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>仪器维修记录卡</b></td></tr>';
     html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR5-4-12</td></tr>';
     html += '</table>';
     html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotr"  ><td class="infoat" align="center" width="20%">仪器型号及名称</td>' +
     '<td class="infoc infoleft" align="left"  width="30%">'+alms.GetItemData(repairrecord.devname) +'(' +alms.GetItemData(repairrecord.devid)+')</td>'+
     '<td class="infoat" align="center"  " width="20%">所属室</td>' +
     '<td class="infoc infoleft" align="left" width="30%">'+alms.GetItemData(repairrecord.deptname) +'</td></tr>';
     html += '<tr class="infotr"  ><td class="infoat" align="center" width="20%">主机号码</td>' +
     '<td class="infoc infoleft" align="left"  width="30%">'+alms.GetItemData(repairrecord.hostnumber) + '</td>'+
     '<td class="infoat" align="center"  " width="20%">管理人</td>' +
     '<td class="infoc infoleft" align="left" width="30%">'+alms.GetItemData(repairrecord.managerusername) +'</td></tr>';
    
     html += '<tr class="infotr" style="height: 140px"><td class="infoat" align="center"  width="10%">仪器故障</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(repairrecord.devdesc) +'</td></tr>';
     
     html += '<tr class="infotr" style="height: 140px"><td class="infoat" align="center"  width="10%">故障原因</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(repairrecord.devreason) +'</td></tr>';
     
     
     html += '<tr class="infotr" style="height: 140px"><td class="infoat" align="center"  width="10%">采取措施</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(repairrecord.devrepair) +'</td></tr>';
     var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=RepairRecord&htodo.flownode=check');
     var date = alms.GetItemDateData(checker.trandate).split('-');
     html += '<tr class="infotr" style="height: 120px"><td class="infoat" align="center"  width="10%">维修结果：</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(repairrecord.repairresult) +'</td></tr>';
     
     (JSON.stringify(checker) == "{}")?  
     html += '<tr class="infotr"  ><td class="infoat" align="center" width="20%" >维修人： </td>' +
     '<td class="infoc infoleft" align="center"  width="20%">'+alms.GetItemData(repairrecord.repairman) +'</td>'+
     '<td class="infoat" align="center"  " width="20%">验收人：</td>' +
     '<td class="infoc infoleft" align="center" width="20%" ><p align="right">&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日 </p></td></tr>'  
    : html += '<tr class="infotr"  ><td class="infoat" align="center" width="20%" >维修人： </td>' +
     '<td class="infoc infoleft" align="center"  width="20%">'+alms.GetItemData(repairrecord.repairman) +'</td>'+
     '<td class="infoat" align="center"  " width="20%">验收人：</td>' +
     '<td class="infoc infoleft" align="center" width="20%" >'+'<image height="35px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />' +'<p align="right">' +alms.GetItemData(date[0])+'年'+alms.GetItemData(date[1])+'月'+alms.GetItemData(date[2]) +'日 </p>' +'</td></tr>';
     
     html += '</table>';
     
     return html;
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
//         LODOP.PRINT();//打印功能
     }
   }
  
});
