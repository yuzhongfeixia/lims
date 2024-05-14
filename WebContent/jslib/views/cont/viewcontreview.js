Ext.define('alms.viewcontreview',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'合同评审',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_contractreview',
          storeUrl:'ContSearchContractReview.do',
          expUrl:'ContSearchContractReview.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '客户名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchconsignname', id: mep + 'searchconsignname', allowBlank: true }
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
           'cr.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
           'cr.consignname':tools.GetEncode(tools.GetValue(mep + 'searchconsignname'))
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
    var record = tools.JsonGet('ContGetContractReview.do?cr.tranid='+item.tranid);
    var reviews = tools.JsonGet('ContGetListContractReviewDetail.do?crd.tranid='+item.tranid).data;
    var samps = tools.JsonGet('ContGetListContractReviewSample.do?crs.tranid='+item.tranid).data;
    var params = tools.JsonGet('ContGetListContractReviewParam.do?crp.tranid='+item.tranid).data;
    var param=[],samp=[];
    
    for(var i=0;i<samps.length;i++){
      sa = samps[i];
      samp.push(sa.samplename);
    }
    for(var j=0;j<params.length;j++){
      pa = params[j];
      param.push(pa.parametername);
    }
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:20px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>合同评审表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR 4-6-1</td></tr>';
    html += '</table>';
    
    html += '<table align="center" cellspacing="0" cellpadding="0" border="1" style=" font-size:12px;" width="80%">';
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">合同/委托书编号</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(record.contractid)+'</td>'+
    '<td  align="center" width="10%">客户名称</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(record.consignname)+'</td></tr>';
    
    
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">样品名称</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(samp)+'</td>'+
    '<td  align="center" width="10%">联系人</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(record.consigncontact)+'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">检测项目</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(param)+'</td>'+
    '<td  align="center" width="10%">电话</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(record.consigntele)+'</td></tr>';
    
    html += '<tr class="infotrheader"><td colspan="8" align="left" width="100%" style="height: 100px;">检测特殊要求（方法、价格、检验期等）：'
      +alms.GetItemData(record.testrequest)+'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >评审人</td>' +
    '<td  colspan=3  align="center" width="90%">评审意见及采取的措施</td></tr>';
    
    if(reviews.length <3){
    for(var i=0;i<reviews.length;i++){
      var review = reviews[i];
      html += '<tr class="infotr" style="height: 60px;"><td  align="center" width="10%" >' +alms.GetItemData(review.reviewusername)+'</td>' +
      '<td  colspan=3  align="center" width="90%">' +alms.GetItemData(review.reviewadvice)+'</td></tr>';
    } 
    for(var i=reviews.length;i<3;i++){
      var review = reviews[i];
      html += '<tr class="infotr" style="height: 60px;"><td  align="center" width="10%" >' +alms.GetItemData()+'</td>' +
      '<td  colspan=3  align="center" width="90%">' +alms.GetItemData()+'</td></tr>';
  }
    }

    if(reviews.length> 3){
    for(var i=0;i<3;i++){
      html += '<tr class="infotr" style="height: 60px;"><td  align="center" width="10%" >' +alms.GetItemData(review.reviewusername)+'</td>' +
      '<td  colspan=3  align="center" width="90%">' +alms.GetItemData(review.reviewadvice)+'</td></tr>';
    }
    }
    html += '<tr class="infotrheader"><td colspan="8" align="left" width="100%" style="height: 100px;"">评审结论：'
      +alms.GetItemData(record.reviewresult)+'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">评审主持人：</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(record.reviewhostname)+'</td>'+
    '<td  align="center" width="10%">日期</td>' +
    '<td  align="left" width="40%">'+alms.GetItemDateData(record.reviewhostdate)+'</td></tr>';
    html += '</table>';
    html +=  '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="left" width="100%" >注：此表用于本中心非常规的、复杂的、大宗客户的委托检测合同的评审。</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >合同/委托书编号</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.contractid)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >客户名称</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">' +alms.GetItemData(record.consignname)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >样品名称</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(samp)+'</td>' +
//    '<td class="infoat" align="center" width="20%" >联系人</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">' +alms.GetItemData(record.consigncontact)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >检测项目</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(param)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >电话</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">' +alms.GetItemData(record.consigntele)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 align="left" width="10%" >检测特殊要求（方法、价格、检验期等）：</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 align="left" width="10%" style="height:100px;" >' +alms.GetItemData(record.testrequest)+'</td></tr>';
//    
//     html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" >评审人</td>' +
//     '<td class="infoc infoleft" colspan=3  align="center" width="20%">评审意见及采取的措施</td></tr>';
//      
//    for(var i=0;i<reviews.length;i++){
//      var review = reviews[i];
//      html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" style="height:60px;">' +alms.GetItemData(review.reviewusername)+'</td>' +
//      '<td class="infoc infoleft" colspan=3  align="center" width="20%">' +alms.GetItemData(review.reviewadvice)+'</td></tr>';
//    }
//    
//    html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" >评审结论</td>' +
//    '<td class="infoc infoleft" colspan=3 align="center" width="30%" style="height:60px;">' +alms.GetItemData(record.reviewresult)+'</td></tr>';
//    
//     html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%" >评审主持人</td>' +
//    '<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemData(record.reviewhostname)+'</td>' +
//    '<td class="infoc infoleft" align="center" width="10%" >日期</td>' +
//    '<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemDateData(record.reviewhostdate)+'</td>'+
//    '</tr>';
    
    
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