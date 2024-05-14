Ext.define('alms.viewcontrolplan',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'能力验证（质量控制）计划表',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quancontrolplan',
          storeUrl:'QuanSearchQuanControlPlan.do',
          expUrl:'QuanSearchQuanControlPlan.do',
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
           'qcp.tranid':tools.GetValueEncode(mep+'searchtranid')
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
    var height = 40;
    var record = tools.JsonGet('QuanGetQuanControlPlan.do?qcp.tranid='+item.tranid);
    var plans = tools.JsonGet('QuanGetListQuanControlPlanDetail.do?qcpd.tranid='+item.tranid).data;
    var plan;
    var createor  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanControlPlan&htodo.flownode=create');
    var approver  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanControlPlan&htodo.flownode=approve');
    var checker  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=QuanControlPlan&htodo.flownode=check');
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>农业部农产品质量安全监督检验测试中心（南京）</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>江苏省农产品质量检验测试中心</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>能力验证（质量控制）计划表</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 5-7-5</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:40px">年度</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.contyear)+'年能力验证（质量控制）计划</td></tr>';
   
    html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" style="height:'+height+'px">时间</td>' +
    '<td class="infoc infoleft"  align="center" width="30%">内容</td>' +
    '<td class="infoc infoleft"  align="center" width="30%">组织机构(人员)</td>' +
    '<td class="infoc infoleft"  align="center" width="30%">备注</td>' +
    '</tr>';
    
    for(var i=0;i<plans.length;i++){
       plan = plans[i];
       
       html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" style="height:'+height+'px">'+alms.GetItemData(plan.plantime)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="30%">'+alms.GetItemData(plan.plancontent)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="30%">'+alms.GetItemData(plan.orgdept)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="30%">'+alms.GetItemData(plan.planremark)+'</td>' +
      '</tr>';
    }
    
    html += '</table>';
    
    html += '<br/>'
    
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="left" width="100%" style="height: 40px;font-size:12px">注：在年度管理评审中，由技术负责人会质量负责人对年度质量控制计划组织评价</td></tr>';
    (JSON.stringify(approver) == "{}")? html += '<tr class="infotr"><td  align="center" tyle="font-size:20px;"><b>审核未通过，请查找原因</b></td></tr>' : 
    html += '<tr class="infotr"><td  align="center" width="10%" >批准:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" /></td>' +
    '<td  align="center" width="10%">审核:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" /></td>' +
    '<td  align="center" width="10%">编制:</td>' +
    '<td  align="left" width="25%"><image height="40px" src="images/sign/' +alms.GetItemData(createor.tranuser) + '.jpg" /></tr>';
    html += '</table>'
    me.html = html;
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