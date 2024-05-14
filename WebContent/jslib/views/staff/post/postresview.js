Ext.define('alms.postresview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员上岗考核结果查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userexamresult',
      storeUrl: 'StaffSearchUserExamResult.do',
      expUrl: 'StaffSearchUserExamResult.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'examid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'postresulthtml', html: '' },
      {xtype:'hiddenfield',name:'examresult.examid',id: mep + 'examid'}
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
    var record = tools.OnGridLoad(me.plGrid, '人员上岗考核结果！');
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
  
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.examid)) {
      tools.SetValue(mep + 'examid',item.examid);
      me.tabMain.setActiveTab(1);
      me.ShowUserExamResultHtml(item);
    }
    return true;
  },
  
  ShowUserExamResultHtml: function(item){
	  var me = this;
	  var mep = me.tranPrefix;
	  var height = 80;
    var record = tools.JsonGet('StaffGetUserExamResult.do?examresult.tranid='+item.tranid);
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>人员上岗考核报告</b></td></tr>';
    html += '<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 5-1-2</td></tr>';
    html += '</table>';
    
    
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';

    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >姓名：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.username)+'</td>' +
    '<td class="infoat" align="center" width="10%" >所在科室</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.deptname)+'</td></tr>';
   
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >职称（职务）</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.userdutyname)+'</td>' +
    '<td class="infoat" align="center" width="10%" >所学专业</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.userpro)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:200px;"><td class="infoat" align="center" width="10%" >业务范围</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.busscope)+'</td></tr>';
   
    html += '<tr class="infotr" style="height:250px;"><td class="infoat" align="center" width="10%" >考核合格准许操作的仪器</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.allowdevname)+'</td></tr>';
    html += '<tr class="infotr" style="height:250px;"><td class="infoat" align="center" width="10%" >考核合格可从事的检测项目</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.allowparamname)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >批准人: </td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.examapprovename)+'</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.examapprovedate)+'</td></tr>';
    
     
//   
//    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>人员上岗考核结果表</b></td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(postresult.examid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">所学专业</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(postresult.userpro)+'</td>'+
//      '<td class="infoat" align="center" width="10%">记录日期</td>' +
//      '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemDateData(postresult.crtdate)+'</td>'+
//      '<td class="infoat" align="center" width="10%">批准人</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(postresult.examapprovename)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">批准日期</td>' +
//      '<td class="infoc infoleft" colspan=10  align="left" width="90%">'+alms.GetItemDateData(postresult.examapprovedate)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">专业范围</td>' +
//      '<td class="infoc infoleft" colspan=10  align="left" width="90%">'+alms.GetItemData(postresult.busscope)+'</td></tr>';
    
    html += '</table>';
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'postresulthtml').getEl()).update(html);
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
//	       LODOP.PRINT();//打印功能
	   }
	 }  
  
});
