Ext.define('alms.postview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员上岗资格查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userexamreport',
      storeUrl: 'StaffSearchUserExamReport.do',
      expUrl: 'StaffSearchUserExamReport.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '考核编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchexamid', id: mep + 'searchexamid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '被考核人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchusername', id: mep + 'searchusername', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'postexamhtml', html: '' },
      {xtype:'hiddenfield',name:'examreport.examid',id: mep + 'examid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
//      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },   
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
          //'bgn.flowstatus': '02'
          'examreport.username':tools.GetValueEncode(mep+'searchusername'),
          'examreport.examid':tools.GetValueEncode(mep+'searchexamid')
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
    var record = tools.OnGridLoad(me.plGrid, '请选择人员岗位考核！');
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
  
//  OnShowUserExamReportView:function(record){
//    var me = this;
//    var mep = me.tranPrefix;
//    var html = me.ShowUserExamReportHtml(record);
//    Ext.fly(Ext.getCmp(me.tranPrefix + 'postexamhtml').getEl()).update(html);
//  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.examid)) {
      tools.SetValue(mep + 'examid',item.examid);
      me.tabMain.setActiveTab(1);
      me.ShowUserExamReportHtml(item);
      viewFile = function (fileurl,filename) {
          alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
      };
    }
    return true;
  },
  
  ShowUserExamReportHtml: function(record){
	  var me = this;
	    var mep = me.tranPrefix;
    var workexam = tools.JsonGet('StaffGetUserExamReport.do?examreport.examid='+record.examid);
    var examgroupdetail = tools.JsonGet('StaffGetListUserExamGroup.do?examgroup.examid=' + record.examid).data;
    var examdevdetail = tools.JsonGet('StaffGetListUserExamDev.do?examdev.examid=' + record.examid).data;
    var examfiledetail = tools.JsonGet('StaffGetListUserExamFile.do?uxf.examid='+ record.examid).data;
    
//    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>上岗资格考核报告</b></td></tr>';
//   
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>上岗资格考核报告</b></td></tr>';
    html += '<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR5-1-2</td></tr>';
    html += '</table>';
    	
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    
    
    html += '<tr class="infotr"><td class="infoat inforight" align="center" width="12%">被考核人</td>' + 
	'<td class="infoc infoleft" align="left"  width="22%">' + alms.GetItemData(workexam.username) + '</td>' + 
	'<td class="infoat inforight" align="center"  width="12%">岗位</td>' + 
	'<td class="infoc infoleft" align="left"  width="21%">' +alms.GetItemData(workexam.userpostname) + '</td>' + 
	'<td class="infoat inforight" align="center" width="12%">考核形式</td>' + 
	'<td class="infoc infoleft" align="left" width="21%">' + alms.GetItemData(workexam.examtypename) + '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat inforight" align="center" width="12%">部门</td>' + 
    '<td class="infoc infoleft" align="left"  width="22%">' + alms.GetItemData(workexam.deptname) + '</td>' + 
    '<td class="infoat inforight" align="center"  width="12%">职务</td>' + 
    '<td class="infoc infoleft" align="left"  width="21%">' +alms.GetItemData(workexam.userdutyname) + '</td>' + 
    '<td class="infoat inforight" align="center" width="12%">考核日期</td>' + 
    '<td class="infoc infoleft" align="left" width="21%">' + alms.GetItemDateData(workexam.examdate) + '</td></tr>';
    console.log(examgroupdetail.length)
    if(examgroupdetail.length > 0){
    html +='<tr class="infotr"><td rowspan='+examgroupdetail.length+1+' class="infoat"  align="center" width="12%">考核小组成员</td>' +
	   '<td class="infoat" align="center"  width="22%">姓名</td>' +
	   '<td class="infoat" align="center" colspan="2"  width="33%">技术职务</td>' +
	   '<td class="infoat" align="center"  width="12%">部门</td>' +
	   '<td class="infoat" align="center"  width="21%">签名</td></tr>';
   for (var i = 0; i < examgroupdetail.length; i++) {
	   html +='<tr class="infotr"><td class="infoc" align="center"  width="12%">'+alms.GetItemData(examgroupdetail[i].username)+'</td>' +
	       '<td class="infoc" align="center" colspan="2"  width="33%">'+alms.GetItemData(examgroupdetail[i].userdutyname)+'</td>' +
	       '<td class="infoc" align="center"  width="12%">'+alms.GetItemData(examgroupdetail[i].deptname)+'</td>' +
	       '<td class="infoc" align="center"  width="21%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(examgroupdetail[i].userid) + '.jpg" />'+'</td></tr>';
	       
        }
    
    }
    html += '</table>';
    html += '<table align = "center" cellspacing="0"  colspan=8 cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td class="infoat inforight" colspan=6 align="left" width="79%">考核内容</td>' + 
    '<td class="infoat inforight" align="center" colspan=2  width="21%">综合评定</td></tr>';
   
    html += '<tr class="infotr" ><td class="infoc" style="height: 60px;" colspan=6 align="left"  width="79%" >'+alms.GetItemData(workexam.examcontent)+'</td>' +
    '<td class="infoc infoleft" rowspan=2 colspan=2 align="left" width="21%">'+alms.GetItemData(workexam.examdesc)+'</td></tr>';
    
    
    if(examdevdetail.length>0){
    	var devname='';
      for(var i = 0; i < examdevdetail.length; i++){
    	  devname = alms.GetItemData(examdevdetail[i].devname)+' '+devname;
      }
      html += '<tr class="infotr" hight="20px"><td class="infoat" colspan=1  align="left" width="12%" >仪器设备</td>' +
      '<td class="infoc infoleft" colspan=5 align="left" width="67%">'+devname+'</td></tr>';
      }else{
    	  html += '<tr class="infotr" hight="20px"><td class="infoat"  align="left" width="12%" >仪器设备</td>' +
          '<td class="infoc infoleft"  align="left" width="67%"></td></tr>';
      }
   
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="12%" rowspan=2 >考核结论</td>' +
    '<td class="infoc infoleft" colspan=6  align="center" width="88%">'+alms.GetItemData(workexam.examresult)+'</td></tr>';
    
    html += '<tr class="infotr" hight="20px"><td class="infoat" colspan=3 align="left" width="39%" >考核组长'+'<image height="35px" src="images/sign/' +alms.GetItemData(workexam.examleader) + '.jpg" />'+'</td>' +
    '<td class="infoc infoleft" colspan=3  align="left" width="40%">日期：'+alms.GetItemDateData(workexam.examdate)+'</td></tr>';
    
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+workexam.examid+'&htodo.busflow=UserExamReport&htodo.flownode=check');

    
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="12%" rowspan=2 >技术负责人意见</td>' +
    '<td class="infoc infoleft" colspan=6  align="center" width="88%">'+alms.GetItemData(checker.tododesc)+'</td></tr>';
    
    html += '<tr class="infotr" hight="20px"><td class="infoat" colspan=3 align="left" width="39%" >签字'+'<image height="35px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />'+'</td>' +
    '<td class="infoc infoleft" colspan=3  align="left" width="40%">日期：'+alms.GetItemDateData(checker.trandate)+'</td></tr>';
    
    html += '</table>';
    
    if(examfiledetail.length>0){

       var  html2 = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
	   html2 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">上岗考核明细附件信息</td></tr>';
	   
	   html2 += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
	     '<td class="infoat" align="center" width="10%">附件名称</td>' +
	     '<td class="infoat" align="center" width="10%">附件类型</td>' +
	     '<td class="infoat" align="center" width="10%">预览</td></tr>';

   for(var i=0;i<examfiledetail.length;i++){
   var str = examfiledetail[i].fileurl.split(".");
     var attachname = examfiledetail[i].filename+"."+str[str.length-1];
     var attachtype = examfiledetail[i].filecatename;
     var attachurl = examfiledetail[i].fileurl;
     html2 += '<tr class="infotr">' +
     '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
     '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
     '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
     '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
   }
   me.html = html;
   Ext.fly(Ext.getCmp(me.tranPrefix + 'postexamhtml').getEl()).update(html+html2);
 }else{
	 me.html = html;
	    Ext.fly(Ext.getCmp(me.tranPrefix + 'postexamhtml').getEl()).update(html);
 }
   
//    return tools.HtmlGet('StaffHtmlUserExamReport.do?tranid=' + record.examid);
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
