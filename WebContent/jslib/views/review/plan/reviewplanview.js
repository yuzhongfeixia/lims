Ext.define('alms.reviewplanview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '工作计划查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewplan',
      storeUrl: 'RewSearchReviewPlan.do',
      expUrl: 'RewSearchReviewPlan.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '计划编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'reviewplanviewhtml', html: '' },
      {xtype:'hiddenfield',name:'rp.tranid',id: mep + 'tranid'}
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
          'rp.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择评审记录！');
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
  
  OnShowReviewPlanView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowReviewPlanHtml(record);
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'reviewplanviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowReviewPlanView(item);
    }
    return true;
  },
  
  ShowReviewPlanHtml: function(record){
    var reviewplan = tools.JsonGet('RewGetReviewPlan.do?rp.tranid='+record.tranid);
    

    var creater = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=ReviewPlan&htodo.flownode=create');
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=ReviewPlan&htodo.flownode=approve');
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>管理评审工作计划</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 4-11-2</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">评审日期</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemDateData(reviewplan.reviewdate) +'</td>';
    html += '<td class="infoat" align="center"  width="10%">评审地点</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(reviewplan.reviewaddr)+'</td>';
    html += '<td class="infoat" align="center"  width="10%">主持人</td>' +
    '<td class="infoc infoleft" align="left" colspan=4  width="20%">'+alms.GetItemData(reviewplan.reviewusername) +'</td></tr>';
    html + '</td></tr>';
    
    html += '<tr class="infotr" style="height: 120px"><td class="infoat" align="center"  width="10%">评审目的</td>' +
    '<td class="infoc infoleft" align="left" colspan = 14 width="90%">'+alms.GetItemData(reviewplan.reviewgoal) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 120px"><td class="infoat" align="center"  width="10%">评审依据</td>' +
    '<td class="infoc infoleft" align="left" colspan = 14 width="90%">'+ alms.GetItemData(reviewplan.reviewtypename) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 120px"><td class="infoat" align="center"  width="10%">参加人员</td>' +
    '<td class="infoc infoleft" align="left" colspan = 14 width="90%">'+ alms.GetItemData(reviewplan.joinuser) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 80px"><td class="infoat" align="center"  width="1%">评审内容</td>' +
    '<td class="infoc infoleft" align="left" colspan = 14 width="90%">'+ alms.GetItemData(reviewplan.reviewcontentname) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 180px"><td class="infoat" align="center"  width="1%">收集信息的要求</td>' +
    '<td class="infoc infoleft" align="left" colspan = 14 width="90%">'+ alms.GetItemData(reviewplan.inforequire)+'</td></tr>';
    
    
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">编制人</td>' +
    '<td class="infoc infoleft" align="left" colspan = 7 width="20%">'+ alms.GetItemData(creater.tranusername) +'</td>'+
    '<td class="infoat" align="center"  " width="10%">批准人</td>' +
    '<td class="infoc infoleft" align="left"  colspan = 7  width="30%">'+alms.GetItemData(approver.tranusername) +'</td></tr>';
    
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">日期</td>' +
    '<td class="infoc infoleft" align="left" colspan = 7 width="20%">'+alms.GetItemDateData(creater.trandate)+'</td>'+
    '<td class="infoat" align="center"  " width="10%">日期</td>' +
    '<td class="infoc infoleft" align="left"  colspan = 7  width="30%">'+alms.GetItemDateData(approver.trandate) +'</td></tr>';
      
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">计划编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(reviewplan.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">评审日期</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(reviewplan.reviewdate)+'</td>'+
//      '<td class="infoat" align="center" width="10%">主持人</td>' +
//      '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemData(reviewplan.reviewuser)+'</td>'+
//      '<td class="infoat" align="center" width="10%">主持人姓名</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(reviewplan.reviewusername)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">参加人员</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(reviewplan.joinuser)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">评审地点</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(reviewplan.reviewaddr)+'</td>'+
//      '<td class="infoat" align="center" width="10%">评审依据</td>' +
//      '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemData(reviewplan.reviewtypename)+'</td>'+
//      '<td class="infoat" align="center" width="10%">评审内容</td>' +
//      '<td class="infoc infoleft" align="left" width="20%">'+alms.GetItemData(reviewplan.reviewcontentname)+'</td></tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">其他</td>' +
//      '<td class="infoc infoleft" colspan=10  align="left" width="90%">'+alms.GetItemData(reviewplan.othercontent)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">评审目的</td>' +
//      '<td class="infoc infoleft" colspan=10  align="left" width="90%">'+alms.GetItemData(reviewplan.reviewgoal)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">收集信息要求</td>' +
//      '<td class="infoc infoleft" colspan=10  align="left" width="90%">'+alms.GetItemData(reviewplan.inforequire)+'</td></tr>';
//    
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