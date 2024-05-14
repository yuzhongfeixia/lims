Ext.define('alms.viewmonitplan',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'质量监督计划',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quanmonitplan',
          storeUrl:'QuanSearchQuanMonitPlanApproved.do',
          expUrl:'QuanSearchQuanMonitPlanApproved.do',
          idPrevNext:'tranid',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
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
           'qmp.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'tranid',id: mep + 'tranid'}
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
    var height = 100;
    var record = tools.JsonGet('QuanGetQuanMonitPlan.do?qmp.tranid='+item.tranid);
    var createor  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanMonitPlan&htodo.flownode=create');
    var approver  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanMonitPlan&htodo.flownode=approve');
    var checker  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanMonitPlan&htodo.flownode=check');
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px; margin:50 100 30 100;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>农业部农产品质量安全监督检验测试中心（南京）</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>江苏省农产品质量检验测试中心</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>质量监督计划表</b></td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:40px">年度</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.monityear)+'质量监督计划</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:'+height+'px">目的</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.monitpurp.replace(/n/g,'<br/>'))+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:'+height+'px">范围</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.monitscope.replace(/n/g,'<br/>'))+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:'+height+'px">监督频次</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.monitfreq.replace(/n/g,'<br/>'))+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" rowspan=3 align="center" width="5%" >人员分工</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">监督考核小组:</td></tr>'+
    '<tr class="infotr"><td class="infoat"  align="left" width="2%" >组长:</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="30%" >'+alms.GetItemData(record.monitleadername) +'</td></tr>'+
    '<tr class="infotr" style="height:50px"><td class="infoat"  align="left" width="2%" >成员:</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="30%" >'+alms.GetItemData(record.monitmembername) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:'+height+'px">备注</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.remark)+'</td></tr>';
    
    html += '</table>';
    
    html += '<br/>'
    
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td  align="center" width="10%" >批准:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" /></td>' +
    '<td  align="center" width="10%">审核:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" /></td>' +
    '<td  align="center" width="10%">编制:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(createor.tranuser) + '.jpg" /></td></tr>';
    html += '</table>'
    me.html =html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
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
//       LODOP.PRINT();//打印功能
   }
 }
  
});