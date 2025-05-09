Ext.define('alms.viewprdapply',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材申请',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdapply',
          storeUrl:'PrdSearchPrdApply.do',
          expUrl:'PrdSearchPrdApply.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '使用项目', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprojectid', id: mep + 'searchprojectid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchprdtype', mep + 'searchprdtype', 210, '耗材分类', 70, tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE))
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
           'prdapply.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'prdapply.projectid':tools.GetValueEncode(mep+'searchprojectid'),
           'prdapply.prdtype':tools.GetValueEncode(mep+'searchprdtype')
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
    var record = tools.JsonGet('PrdGetPrdApply.do?prdapply.tranid='+item.tranid);
    var prds = tools.JsonGet('PrdGetListPrdApplyDetail.do?prddetail.tranid='+item.tranid).data;
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=PrdApply&htodo.flownode=check');  
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=PrdApply&htodo.flownode=approve');  
    var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=PrdApply&htodo.flownode=judge'); 
    var auditor = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=PrdApply&htodo.flownode=audit'); 
    var html = '<table cellspacing="0" cellpadding="0" border="0" align="center" style="width:90%;">' ;
    html += '<tr><td align="center">'
      
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>普通试剂耗材采购申请表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="right" width="100%" style="height: 40px;font-size:20px">QCR 4-5-3</td></tr>';    
    
    html += '<table  cellspacing="0" cellpadding="0"  align="center" border="1" style="font-size:12px;" width="90%">';
      html += '<tr style="height: 40px;"><td class="infotr"  align="center" colspan="3" width="30%" style="height: 40px;">名称</td>' +
    
      '<td class="infotr" align="center" colspan="2" width="20%">规格型号</td>' +
      '<td class="infotr" align="center" colspan="2" width="20%">数量</td>' +
      '<td class="infotr" align="center" colspan="3" width="30%">备注</td></tr>';
      if(prds.length <6){
        
        for(var i=0;i<prds.length;i++){
          var prd = prds[i];
          html += '<tr class="infotr" style="height: 40px;"><td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(prd.prdname) +'</td>' +
          '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData(prd.prdstandard) +'</td>' +
          '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData(prd.prdcount) +'</td>' +
          '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(prd.remark) +'</td></tr>';
      }
        
      for(var i=prds.length;i<6;i++){
          var prd = prds[i];
          html += '<tr class="infotr" style="height: 40px;"><td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData() +'</td>' +
          '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData() +'</td>' +
          '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData() +'</td>' +
          '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData() +'</td></tr>';
      } 
    }
      
      
      if(prds.length > 6){
      for(var i=0;i<6;i++){
        var prd = prds[i];
        html += '<tr class="infotr" style="height: 40px;"><td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(prd.prdname) +'</td>' +
        '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData(prd.prdstandard) +'</td>' +
        '<td class="infotr" align="center" colspan="2" width="20%">'+alms.GetItemData(prd.prdcount) +'</td>' +
        '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(prd.remark) +'</td></tr>';
    }
  }
      
    html += '<tr class="infotr"><td class="infotr" colspan="10" align="left" style="height: 100px;" width="10%">特殊要求说明:<br/>&nbsp&nbsp&nbsp&nbsp '+alms.GetItemData(item.tranremark) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%">申请人</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(item.tranusername) +'</td>'+
    '<td class="infotr" align="center" colspan="2" width="20%">申请日期</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemDateData(item.trandate) +'</td></tr>';
   
    (JSON.stringify(checker) == "{}") ? html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%">使用项目</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(item.projectid) +'</td>'+
    '<td class="infotr" align="center" colspan="2" width="20%">室主任</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%"></td></tr>':
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%">使用项目</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(item.projectid) +'</td>'+
    '<td class="infotr" align="center" colspan="2" width="20%">室主任</td>' +
    '<td class="infotr" align="center" colspan="3" width="30%">'+alms.GetItemData(checker.tranusername) +'<image height="35px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />' +'</td></tr>';
   
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%">经费渠道</td>' +
    '<td class="infotr" align="center" colspan="8" width="80%">'+alms.GetItemData(item.fundsource) +'</td></tr>';
    
    (JSON.stringify(approver) == "{}") ? html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="10" align="left" width="20%">办公室意见：' + '</td></tr>':
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="10" align="left" width="20%">办公室意见：' +
    alms.GetItemData(approver.tododesc) +'<image height="35px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" />' +'</td></tr>';
    
    (JSON.stringify(judger) == "{}") ?html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="10" align="left" width="20%">分管主任意见：' + '</td></tr>':
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="10" align="left" width="20%">分管主任意见：' +
    alms.GetItemData(judger.tododesc) +'<image height="35px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" />'+'</td></tr>';
    
    html += '</table>';
    html += '<table  cellspacing="0" cellpadding="0" border="1"  align="center" style="font-size:12px;" width="90%">';
    
    (JSON.stringify(auditor) == "{}") ?html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%" style="border-right:0;border-left:0;border-bottom:0;border-top:0;">主任签字：</td>' +
    '<td class="infotr" align="left" colspan="3" width="30%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;"></td>'+  '<td class="infotr" align="center" colspan="2" width="20%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;">签字日期</td>' +
    '<td class="infotr" align="left" colspan="3" width="30%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;">'+ alms.GetItemDateData(auditor.trandate) + '</td></tr>':
    html += '<tr class="infotr" style="height: 40px;"><td class="infotr" colspan="2" align="center" width="20%" style="border-right:0;border-left:0;border-bottom:0;border-top:0;">主任签字：</td>' +
    '<td class="infotr" align="left" colspan="3" width="30%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;">'+'<image height="35px" src="images/sign/' +alms.GetItemData(auditor.tranuser) + '.jpg" />'+'</td>'+
    '<td class="infotr" align="center" colspan="2" width="20%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;">签字日期</td>' +
    '<td class="infotr" align="left" colspan="3" width="30%" style="border-right:0;border-bottom:0;border-left:0;border-top:0;">'+ alms.GetItemDateData(auditor.trandate) + '</td></tr>';
    
      
//      html += '<tr class="infotr"><td class="infoat"  align="center" colspan="1" width="10%">明细序号</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">名称</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">规格型号</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">购买数量</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">单位</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">级别</td>' +
//      '<td class="infoat" align="center" colspan="1" width="10%">是否验收</td>' +
//      '<td class="infoat" align="center" colspan="2" width="10%">备注</td></tr>';
      
//      for(var i=0;i<prds.length;i++){
//          var prd = prds[i];
//          html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(prd.prdserial) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.prdname) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.prdstandard) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.prdcount) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.prdunitname) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.levelname) +'</td>' +
//          '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.isverifyname) +'</td>' +
//          '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(prd.remark) +'</td></tr>';
//      }
//    }
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">特殊要求说明</td>' +
//    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.tranremark) +'</td></tr>';
//    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranid) +'</td>'+
//    '<td class="infoat" align="center" width="10%">使用项目</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.projectid) +'</td>'+
//    '<td class="infoat" align="center" width="10%">申请人姓名</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">申请日期</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.trandate) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">室主任</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.confirmusername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">室主任确认日期</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.confirmdate) +'</td>'+
//    '<td class="infoat" align="center" width="10%">室主任意见</td>' +
//    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemData(item.confirmdesc) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">经费渠道</td>' +
//    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.fundsource) +'</td></tr>';
    html += '</table>';
    html += '</table>';
    html + '</td></tr>';
    html += '</table>';
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