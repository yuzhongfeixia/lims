Ext.define('alms.stdtestsureview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '新增检验能力查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdtestsure',
      storeUrl: 'StdSearchStdTestSure.do',
      expUrl: 'StdSearchStdTestSure.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '新增项目名称', labelWidth: 90, width: 180, maxLength: 40, name: 'searchprojname', id: mep + 'searchprojname', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'stdtestsureviewhtml', html: '' },
      {xtype:'hiddenfield',name:'testsure.tranid',id: mep + 'tranid'}
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
          'testsure.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'testsure.projname': tools.GetEncode(tools.GetValue(mep + 'searchprojname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择新增检验能力记录！');
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
  
  OnShowStdTestSureView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowStdTestSureHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'stdtestsureviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowStdTestSureView(item);
    }
    return true;
  },
  
  ShowStdTestSureHtml: function(record){
    var me = this;
    var mep = me.tranPrefix;
    var stdtestsure = tools.JsonGet('StdGetStdTestSure.do?testsure.tranid='+record.tranid);
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>新增检验项目能力确认表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR 5-3-3</td></tr>';
    html += '</table>';
    
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr" style="height:40px;"><td class="infoat" align="center" width="10%" rowspan=2 >新增项目名称</td>' +
    '<td class="infoc infoleft" align="center" width="40%" rowspan=2>'+alms.GetItemData(stdtestsure.projname)+'</td>' +
    '<td class="infoat" align="center" width="10%" >检测人</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(stdtestsure.testuser)+'&nbsp&nbsp&nbsp&nbsp'+alms.GetItemData(stdtestsure.testusername)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:40px;"><td class="infoat" align="center" width="10%" >负责人</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(stdtestsure.respusername)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >人员要求情况检查</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(stdtestsure.qualfile)+'</td></tr>';
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >环境设施条件检查</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(stdtestsure.envfacility)+'</td></tr>';
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >仪器设备满足情况检查</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(stdtestsure.devsatisfy)+'</td></tr>';
    
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TestSure&htodo.flownode=check');
    var reviewer = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TestSure&htodo.flownode=review');
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TestSure&htodo.flownode=approve');
    var auditor = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TestSure&htodo.flownode=audit');
    var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TestSure&htodo.flownode=judge');
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >过程监督意见</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(checker.tododesc)+'</td></tr>';
    
    (JSON.stringify(checker) == "{}")?html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >质量监督员：</td>'+'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：</td></tr>':
      html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >质量监督员：'+'<image height="23px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />'+'</td>' +
      '<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(checker.trandate)+'</td></tr>';  
   
    
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >科室意见</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(reviewer.tododesc)+'</td></tr>';
    
    (JSON.stringify(checker) == "{}")?html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >室主任签字：</td>'+'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：</td></tr>':
    	html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >室主任签字：'+'<image height="23px" src="images/sign/' +alms.GetItemData(reviewer.tranuser) + '.jpg" />'+'</td>' +
    	'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(reviewer.trandate)+'</td></tr>';  
   
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >办公室意见：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(approver.tododesc)+'</td></tr>';
    
    (JSON.stringify(checker) == "{}")?html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >办公室主任签字：</td>'+'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：</td></tr>':
    	html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >办公室主任签字：'+'<image height="23px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" />'+'</td>' +
    	'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(approver.trandate)+'</td></tr>';  
     
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >技术负责人意见</td>' +
      '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(auditor.tododesc)+'</td></tr>';
    
    (JSON.stringify(auditor) == "{}")?html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >技术负责人签  字</td>'+'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：</td></tr>':
        html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >技术负责人签  字：'+'<image height="35px" src="images/sign/' +alms.GetItemData(auditor.tranuser) + '.jpg" />'+'</td>' +
        '<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(auditor.trandate)+'</td></tr>';  
    
     html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=2 >中心负责人意见</td>' +
        '<td class="infoc infoleft" colspan=3 align="center" width="60%">'+alms.GetItemData(judger.tododesc)+'</td></tr>';
     
     (JSON.stringify(judger) == "{}")?html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >签  字:</td>'+'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：</td></tr>':
          html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >签字：'+'<image height="35px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" />'+'</td>' +
          '<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(judger.trandate)+'</td></tr>';  
    
    html += '</table>';
//    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>检测方法偏离确认查看</b></td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdtestsure.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">新项目名</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdtestsure.projname)+'</td>'+
//      '<td class="infoat" align="center" width="10%">检测人</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdtestsure.testuser)+'</td>'+
//      '<td class="infoat" align="center" width="10%">检测人姓名</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdtestsure.testusername)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">负责人</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdtestsure.respuser)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">负责人姓名</td>' +
//      '<td class="infoc infoleft" colspan=5 align="left" width="15%">' +alms.GetItemData(stdtestsure.respusername)+ '</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">质量文件运行情况检查</td>' +
//      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdtestsure.qualfile)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">环境设施条件检查</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdtestsure.envfacility)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">仪器设备满足情况检查</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdtestsure.devsatisfy)+'</td></tr>';
//    
//    html += '</table>';
    me.html = html;
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
