Ext.define('alms.devscrapview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备降级报废查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devscrap',
      storeUrl: 'DevSearchDevScrap.do',
      expUrl: 'DevSearchDevScrap.do',
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
                 ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
                 ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'devscraphtml', html: '' },
      {xtype:'hiddenfield',name:'ds.tranid',id: mep + 'tranid'}
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
          'ds.devid': tools.GetValueEncode(mep + 'searchdevid'),
          'ds.devname': tools.GetValueEncode(mep + 'searchdevname')
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
  
  OnShowDevScrapView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDevScrapHtml(record);
    
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devscraphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevScrapView(item);
    }
    return true;
  },
  
  //预览html内容
  ShowDevScrapHtml:function(item){
     var me = this;
     var mep = me.tranPrefix;
     var devscrap = tools.JsonGet('DevGetDevScrap.do?ds.tranid='+item.tranid);
     var appllyor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevScrap&htodo.flownode=create');
     var mannert= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevScrap&htodo.flownode=manner');
     var checkor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevScrap&htodo.flownode=check');
     var approver= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevScrap&htodo.flownode=approve');
     var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';

     html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>仪器设备降级/报废申请书</b></td></tr>';
     html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 5-4-1</td></tr>';
     html += '</table>';
     html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">仪器名称</td>' +
     '<td class="infoc infoleft" align="left" colspan = 6 width="90%">'+alms.GetItemData(devscrap.devname) +'</td></tr>';
     
     html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">生产单位</td>' +
     '<td class="infoc infoleft" align="left" colspan=6 width="90%">'+alms.GetItemData(devscrap.factoryname) +'</td></tr>';

     html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">规格型号</td>' +
     '<td class="infoc infoleft" align="left"   width="10%">'+alms.GetItemData(devscrap.devstandard) +'</td><td class="infoat" align="center"  width="10%">出厂编号</td> '+'<td class="infoc infoleft" align="left" colspan=2 width="30%">'+alms.GetItemData(devscrap.factorycode)+'</td></tr>';
     html + '</td></tr>';

     
     html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">申请事由</td>' +
     '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(devscrap.applyreason) +'</td></tr>';
     html + '</td></tr>';
     (JSON.stringify(appllyor) == "{}")?html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >申请部门</td>' +
    '<td class="infoc infoleft"   align="center" width="10%"></td><td class="infoat" align="center" width="5%"  >负责人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%"> </td></tr>':  
     html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >申请部门</td>' +
     '<td class="infoc infoleft"   align="center" width="10%">'+alms.GetItemData(appllyor.trandeptname)+'</td><td class="infoat" align="center" width="5%"  >负责人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(appllyor.tranuser) + '.jpg" />' +'/'+ alms.GetItemDateData(appllyor.trandate)+'</tr>';
     
     html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">鉴定意见</td>' +
     '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(checkor.tododesc) +'</td></tr>';
     html + '</td></tr>';
     
     (JSON.stringify(checkor) == "{}")? html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >鉴定部门</td>' +
    	     '<td class="infoc infoleft"   align="center" width="10%"></td><td class="infoat" align="center" width="5%"  >负责人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%"></td></tr>':
     html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >鉴定部门</td>' +
     '<td class="infoc infoleft"   align="center" width="10%">'+alms.GetItemData(checkor.trandeptname)+'</td><td class="infoat" align="center" width="5%"  >负责人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(checkor.tranuser) + '.jpg" />' +'/'+alms.GetItemDateData(checkor.trandate)+'</tr>';
    
     html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">单位意见</td>' +
     '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(approver.tododesc) +'</td></tr>';
     html + '</td></tr>';
     
     (JSON.stringify(approver) == "{}")?html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >设备管理员/日期</td>' +
     '<td class="infoc infoleft"   align="center" width="10%"></td><td class="infoat" align="center" width="5%"  >批准人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%"></td></tr>':
     html += '<tr class="infotr" ><td class="infoat" align="center" width="5%"  >设备管理员/日期</td>' +
     '<td class="infoc infoleft"   align="center" width="10%">'+'<image height="35px" src="images/sign/'+alms.GetItemData(mannert.tranuser)+ '.jpg"/>' +'/'+alms.GetItemDateData(mannert.trandate)+'</td><td class="infoat" align="center" width="5%"  >批准人/日期</td>'+'<td class="infoc infoleft"   align="center" width="10%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg"/>' + '/'+ alms.GetItemDateData(approver.trandate)+'</tr>';
     

     
     me.html = html;
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
