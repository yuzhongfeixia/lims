Ext.define('alms.stdreplrecordview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '标准查新记录查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdreplrecord',
      storeUrl: 'StdSearchStdReplRecord.do',
      expUrl: 'StdSearchStdReplRecord.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '标准名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchstdname', id: mep + 'searchstdname', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
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
      { xtype: 'label', id: mep + 'stdreplrecordviewhtml', html: '' },
      {xtype:'hiddenfield',name:'replrecord.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me },
      '-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFile, scope : me }
 
    ];
  },
  
  OnViewFile: function () {
	    var me = this, mep = me.tranPrefix;
	    var busfilesss = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data[0];
	    if(!Ext.isEmpty(busfilesss)){
	     alms.PopupFileShow('文件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname);
	    }else{
	    	tools.alert('该记录没有上传附件');
	        return false;
	    }
	    
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
          'replrecord.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'replrecord.stdname': tools.GetEncode(tools.GetValue(mep + 'searchstdname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择标准查新记录！');
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
  
  OnShowStdReplRecordView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowStdReplRecordHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'stdreplrecordviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowStdReplRecordView(item);
    }
    return true;
  },
  
  ShowStdReplRecordHtml: function(record){
    var stdreplrecord = tools.JsonGet('StdGetStdReplRecord.do?replrecord.tranid='+record.tranid);
    var me = this;
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>标准查新记录查看</b></td></tr>';
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdreplrecord.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">标准号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdreplrecord.stdid)+'</td>'+
//      '<td class="infoat" align="center" width="10%">标准名称</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdreplrecord.stdname)+'</td>'+
//      '<td class="infoat" align="center" width="10%">代替标准号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdreplrecord.replstdid)+'</td></tr>';
//    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdreplrecord.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">创建日期</td>' +
      '<td class="infoc infoleft" colspan=5 align="left" width="15%">' +alms.GetItemDateData(stdreplrecord.trandate)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">变更描述</td>' +
      '<td class="infoc infoleft" colspan=7 rowspan=3  align="left" width="90%">'+alms.GetItemData(stdreplrecord.remark)+'</td></tr>';
    
    
    html += '</table>';
    
    return html
    
  },
  
  OnPrintTask: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.html = me.OnMakeRecordsPrintHtml();
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
  },
  
  OnMakeRecordsPrintHtml: function(){
    var me = this;

    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>标 准 查 新 记 录</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 5-3-4</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">序号</td>' +
    '<td class="infoat" align="center"  width="10%">标准号</td><td class="infoat" align="center"  width="10%">标准名称</td><td class="infoat" align="center"  width="10%">代替标准号</td><td class="infoat" align="center"  width="10%">作废日期</td><td class="infoat" align="center"  width="10%">实施日期</td><td class="infoat" align="center"  width="10%">备注</td></tr>';
   var records = me.plGrid.store.data.items;

    for(a = 0;a <records.length;a++ ){
    html += '<tr class="infotr" style="height: 40px">' +
    '<td class="infoc infoleft" align="center"   width="10%">'+(a + 1) +'</td>';
    html += 
    '<td class="infoc infoleft" align="center"   width="10%">'+records[a].data.stdid +'</td>';
    html +=
    '<td class="infoc infoleft" align="center"   width="10%">'+records[a].data.stdname +'</td>';
    
    html += 
    '<td class="infoc infoleft" align="center"   width="10%">'+ records[a].data.replstdid +'</td>';
    
    html += 
    '<td class="infoc infoleft" align="center"   width="10%">'+alms.GetItemDateData(records[a].data.canceldate) +'</td>';
    
    html += 
    '<td class="infoc infoleft" align="center"   width="10%">'+alms.GetItemDateData(records[a].data.impldate) +'</td>';
    
    html += 
    '<td class="infoc infoleft" align="center"   width="10%">'+records[a].data.remark +'</td></tr>';
    }
    
//    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">批准人</td>' +
//    '<td class="infoc infoleft" align="left"  width="10%">'+'' +'</td>'+
//    '<td class="infoat" align="center"  " width="10%">校核人</td>' +
//    '<td class="infoc infoleft" align="left" width="10%">'+'' +'</td>'
//    + '<td class="infoat" align="center"  " width="10%">查新人</td>' +
//    '<td class="infoc infoleft" align="left" width="10%" colspan =2>'+''+'</td></tr>';
//
//    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">日期</td>' +
//    '<td class="infoc infoleft" align="left"  width="10%">'+'' +'</td>'+
//    '<td class="infoat" align="center"  " width="10%">日期</td>' +
//    '<td class="infoc infoleft" align="left" width="10%">'+'' +'</td>'
//    + '<td class="infoat" align="center"  " width="10%"></td>' +
//    '<td class="infoc infoleft" align="left" width="10%" colspan =2>'+''+'</td></tr>';
    
    html += '</table>'
      return html;
  }
  
});
