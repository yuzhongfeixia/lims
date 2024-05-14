Ext.define('alms.reviewimproveview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '改进措施查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewimprove',
      storeUrl: 'RewSearchReviewImprove.do',
      expUrl: 'RewSearchReviewImprove.do',
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
      { xtype: 'label', id: mep + 'reviewimprovehtml', html: '' },
      {xtype:'hiddenfield',name:'ri.tranid',id: mep + 'tranid'}
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
          'ri.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择改进措施！');
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
  
  OnShowReviewImproveView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowReviewImproveHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'reviewimprovehtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowReviewImproveView(item);
    }
    return true;
  },
  
  ShowReviewImproveHtml: function(record){
    var me = this;
    var reviewimprove = tools.JsonGet('RewGetReviewImprove.do?ri.tranid='+record.tranid);
    var creator= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=ReviewImprove&htodo.flownode=create');
  var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=ReviewImprove&htodo.flownode=approve');
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>质量改进措施表</b></td></tr>';
       html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-11-5</td></tr>';
     html += '</table>';
      html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">责任部门</td>' +
      '<td class="infoc infoleft" colspan=6  align="left" width="15%">' +alms.GetItemData(reviewimprove.respdeptname)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">质量改进内容</td>' +
      '<td class="infoc infoleft" colspan=6  align="left" width="90%">'+alms.GetItemData(reviewimprove.improvecontent)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">期限要求</td>' +
      '<td class="infoc infoleft" colspan=6  align="left" width="90%">'+alms.GetItemData(reviewimprove.timerequire)+'</td></tr>';
      
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">签发人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(creator.tranusername)+ '</td>'+
    '<td class="infoat" align="center" width="10%">签发日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(creator.trandate)+'</td></tr>';
    
      html += '<tr class="infotr" height= 150px><td class="infoat" align="center" width="10%">改进措施实施情况</td>' +
      '<td class="infoc infoleft" colspan=6  align="left" width="15%">' +alms.GetItemData(reviewimprove.improvedesc)+ '</td></tr>';
    
      
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">责任部门负责人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(reviewimprove.tranusername)+ '</td>'+
    '<td class="infoat" align="center" width="10%">签发日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(reviewimprove.trandate)+'</td></tr>';
      
      
      html += '<tr class="infotr" height= 150px><td class="infoat" align="center" width="10%">改进措施实施结果验证</td>' +
      '<td class="infoc infoleft" colspan=6  align="left" width="15%">' +alms.GetItemData(approver.tododesc)+ '</td></tr>';
      
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">验证人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +'<image height="40px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" />'+ '</td>'+
    '<td class="infoat" align="center" width="10%">签发日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(approver.trandate)+'</td></tr>';
      
    html += '</table>';
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
