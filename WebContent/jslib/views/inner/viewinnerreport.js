Ext.define('alms.viewinnerreport',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'内审报告',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_innerreport',
          storeUrl:'InnerSearchInnerReport.do',
          expUrl:'InnerSearchInnerReport.do',
          idPrevNext:'reportid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '报告编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchreportid', id: mep + 'searchreportid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '部门', 70, tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE))
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnView', text: gpersist.STR_BTN_VIEW, iconCls: 'icon-outlook', handler: me.OnShow,scope: me},
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
     
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'innerreport.reportid':tools.GetValueEncode(mep+'searchreportid'),
           'innerreport.auditeddept':tools.GetValueEncode(mep+'searchdeptid')
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'reportid',id: mep + 'reportid'}
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
      }
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    }
  },
  
  OnShowGetHtml:function(item){
    var me = this;
    var mep = me.tranPrefix;
    var height = 40;
    
    var record = tools.JsonGet('InnerGetInnerReport.do?innerreport.reportid='+item.reportid);
    var member = [];
    var members = tools.JsonGet('InnerGetListInnerGroupMember.do?groupmember.groupid='+item.groupid).data;
    for(var j = 0;j<members.length;j++){
      member.push(members[j].memberusername);
    }
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>审 核 报 告</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR4-10-5</td></tr>';
    html += '</table>';
    
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >审核目的：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.auditgoal)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >审核要素：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.auditelem)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >受审核部门：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.auditeddeptname)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;">审核依据：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(Ext.util.Format.htmlDecode(record.auditby))+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat"  style=" font-size:14px;font-weight:bold;" align="center" width="20%" rowspan=2 >审核组成员：</td>' +
    '<td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;">组长：</td>' +
    '<td class="infoc infoleft" colspan=5 align="center" width="80%" >'+alms.GetItemData(record.auditleadname)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:50px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;">小组成员：</td>' +
    '<td class="infoc infoleft" colspan=5 align="center" width="80%">'+alms.GetItemData(member)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >内审情况概述：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.auditdesc)+'</td></tr>';

    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >不合格项（包括汇总和分布情况）：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.belowstandard)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >前次审核后的纠正措施及执行情况：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.beforedesc)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >质量体系运行评价及改进情况：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.inneradv)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" style=" font-size:14px;font-weight:bold;" >审核组长</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.auditleadname)+'</td>' +
    '<td class="infoat" align="center" width="10%"style=" font-size:14px;font-weight:bold;" >日期:</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.auditdate)+'</td></tr>';
    
    
//    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>审 核 报 告</b></td></tr>';
//         html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR4-10-5</td></tr>';
//     html += '</table>';
//     
//     html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >审核目的：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:40px;" align="left" width="10%" >' +alms.GetItemData(record.auditgoal)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >审核要素：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:40px;" align="left" width="10%" >' +alms.GetItemData(record.auditelem)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >受审核部门：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:40px;" align="left" width="10%" >' +alms.GetItemData(record.auditeddeptname)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >审核依据：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:60px;" align="left" width="10%" >' +alms.GetItemData(Ext.util.Format.htmlDecode(record.auditby))+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >审核组成员：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;border-bottom:0;" align="left" width="10%" >组长：      ' +alms.GetItemData(record.auditleadname)+'</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:20px;" align="left" width="10%" >组员：      ' +alms.GetItemData(member)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >内审情况概述：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:100px;" align="left" width="10%" >' +alms.GetItemData(record.auditdesc)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >不合格项（包括汇总和分布情况）：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:100px;" align="left" width="10%" >' +alms.GetItemData(record.belowstandard)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >前次审核后的纠正措施及执行情况：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:100px;" align="left" width="10%" >' +alms.GetItemData(record.beforedesc)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-bottom:0;font-size:14px;font-weight:bold;" align="left" width="10%" >质量体系运行评价及改进情况：</td></tr>';
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 style="border-top:0;height:100px;" align="left" width="10%" >' +alms.GetItemData(record.inneradv)+'</td></tr>';
//    
//    
//    html += '<tr class="infotr" ><td class="infoc infoleft" align="center" width="5%" >审核组长</td>' +
//    '<td class="infoc infoleft"  align="left" width="30%"> ' +alms.GetItemData(record.auditleadname)+'</td>' +
//    '<td class="infoc infoleft"  align="right" width="5%" >日期:</td>' +
//    '<td class="infoc infoleft"  align="left" width="30%">' +alms.GetItemDateData(record.auditdate)+'</td></tr>';
//    
    html += '</table>';
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.reportid)) {
      tools.SetValue(mep + 'reportid',item.reportid);
      me.tabMain.setActiveTab(1);
      me.OnShowGetHtml(item);
    }
    return true;
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
