Ext.define('alms.leakexecuteview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件泄露信息查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_manbasleak',
      storeUrl: 'IncFileSearchBasLeak.do',
      expUrl: 'IncFileSearchBasLeak.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '处置编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'basleakhtml', html: '' },
      {xtype:'hiddenfield',name:'bl.tranid',id: mep + 'tranid'}
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
  
  
//  OnBeforeSearch: function () {
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (me.gridStore) {
//      me.gridStore.on('beforeload', function (store, options) {      
//        Ext.apply(store.proxy.extraParams, {
//          'bgn.flowstatus': '02'
//        });
//      });
//    };
//  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bl.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
//          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择文件泄露信息记录！');
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
  
  OnShowBasLeakView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowBasLeakHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'basleakhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowBasLeakHtml(item);
    }
    return true;
  },
  

  OnShowBasLeakHtml: function(record){
    var me = this;
    var mep = me.tranPrefix;
    var basleak = tools.JsonGet('IncFileGetBasLeak.do?bl.tranid='+record.tranid);
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileLeak&htodo.flownode=check');
    var dealer = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileLeak&htodo.flownode=deal');
    var advicer = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileLeak&htodo.flownode=advice');
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileLeak&htodo.flownode=approve');
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:20px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>泄密情况处置表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR 4-3-6</td></tr>';
    html += '</table>';
    html += '<table align="center" cellspacing="0" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"  style="height:40px;"> <td  align="center" width="10%">泄密人</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(basleak.leakusername)+'</td>'+
    '<td  align="center" width="10%">泄密时间</td>' +
    '<td  align="left" width="40%">'+alms.GetItemDateData(basleak.leakdate)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:40px;"><td  align="center" width="10%" >泄密原因</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(basleak.leakreason)+'</td></tr>';
    html += '<tr class="infotr"><td  align="center" width="10%" style="height:100px;">造成后果</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(basleak.leakreason)+'</td></tr>';
    html += '<tr class="infotr"><td  align="center" width="10%" style="height:100px;">泄密情况报告时间</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemDateData(basleak.reportdate)+'</td></tr>';
    
    
    
    
    
    html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">泄密过程简述:'+alms.GetItemData(basleak.leakdesc)+'</td>' +
    '<td  align="center" width="10%">泄密人：'+alms.GetItemData(basleak.leakusername)+'</td></tr>';
    
    (JSON.stringify(checker) == "{}")?html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">补救措施:'+alms.GetItemData(checker.tododesc)+'</td>' +
    '<td  align="center" width="10%">措施实施人:</td></tr>':
    html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">补救措施:'+alms.GetItemData(checker.tododesc)+'</td>' +
    '<td  align="center" width="10%">措施实施人:'+'<image height="35px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />'+'</td></tr>';
    
    (JSON.stringify(dealer) == "{}")? html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">补救措施实施效果:'+alms.GetItemData(dealer.tododesc)+'</td>' +
    '<td  align="center" width="10%">技术负责人：</td></tr>':
    html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">补救措施实施效果:'+alms.GetItemData(dealer.tododesc)+'</td>' +
    '<td  align="center" width="10%">技术负责人：'+'<image height="35px" src="images/sign/' +alms.GetItemData(dealer.tranuser) + '.jpg" />'+'</td></tr>';
    
    (JSON.stringify(advicer) == "{}")?html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">质量负责人意见:'+alms.GetItemData(advicer.tododesc)+'</td>' +
    '<td  align="center" width="10%">签名:</td></tr>': 
    html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">质量负责人意见:'+alms.GetItemData(advicer.tododesc)+'</td>' +
    '<td  align="center" width="10%">签名:'+'<image height="40px" src="images/sign/' +alms.GetItemData(advicer.tranuser) + '.jpg" />'+'</td></tr>';
    (JSON.stringify(approver) == "{}")? html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">主任意见:'+alms.GetItemData(approver.tododesc)+'</td>' +
    '<td  align="center" width="10%">签名:<br/>日期：</td></tr>' :
    html += '<tr class="infotr"><td  colspan=3  align="left" width="90%"  style="height:100px;">主任意见:'+alms.GetItemData(approver.tododesc)+'</td>' +
    '<td  align="center" width="10%">签名:'+'<image height="35px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" />'+'<br/>日期：'+alms.GetItemDateData(approver.trandate)+'</td></tr>';
    html += '</table>';
    
    
    
//    html += '<table align="center" cellspacing="0" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotr"><td  colspan="8" align="left"  style="height:100px;border-bottom:0;">:XXXXX'+alms.GetItemData(basleak.leakdesc)+'</td></tr>';
//    html += '<tr class="infotr"><td  colspan=3 align="left" width="90%"  style="height:10px;border-top:0;border-right:0;">质量负责人意见:'+alms.GetItemData(basleak.leakdesc)+'</td>' +
//    '<td  align="center" colspan=2 width="10%" style="height:10px;border-top:0;border-left:0;">签名:'+alms.GetItemData(basleak.leakusername)+'</td></tr>';
//    html += '</table>';
//    
    
    //    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">处置编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(basleak.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">泄密人编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(basleak.leakuser)+'</td>'+
//      '<td class="infoat" align="center" width="10%">泄露人姓名</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(basleak.leakusername)+'</td>'+
//      '<td class="infoat" align="center" width="10%">泄密时间</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(basleak.leakdate)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">报告时间</td>' +
//      '<td class="infoc infoleft" colspan=7  align="left" width="15%">' +alms.GetItemDateData(basleak.reportdate)+ '</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">泄密原因</td>' +
//      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(basleak.leakreason)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">造成后果</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(basleak.leakresult)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">泄密简述</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(basleak.leakdesc)+'</td></tr>';
//    
    
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'basleakhtml').getEl()).update(html);
//    return html
    
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
