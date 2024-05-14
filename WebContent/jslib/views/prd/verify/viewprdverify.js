Ext.define('alms.viewprdverify',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'试剂耗材验证',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdverify',
          storeUrl:'PrdSearchPrdVerify.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '验证编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchverifyid', id: mep + 'searchverifyid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '申请编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid1', id: mep + 'searchtranid1', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '试剂耗材', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '供应商', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtradeid', id: mep + 'searchtradeid', allowBlank: true }
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
           'prdverify.tranid':tools.GetValueEncode(mep+'searchtranid1'),
           'prdverify.verifyid':tools.GetValueEncode(mep+'searchverifyid'),
           'prdverify.prdname':tools.GetValueEncode(mep+'searchprdname'),
           'prdverify.factoryname':tools.GetValueEncode(mep+'searchtradeid')
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
    var record = tools.JsonGet('PrdGetPrdVerify.do?prdverify.tranid='+item.tranid);
    var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.verifyid+'&htodo.busflow=PrdAccept&htodo.flownode=judge');
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.verifyid+'&htodo.busflow=PrdAccept&htodo.flownode=approve');
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">';
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>耗材验证记录表</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="10" align="right" width="100%" style="height: 40px;font-size:20px"> QCR 4-5-5</td></tr>';
    html += '<table  cellspacing="0" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%">试剂耗材名称</td>' +
  '<td  colspan=7 align="left" width="15%">'+alms.GetItemData(item.prdname) +'</td></tr>';
   
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%">规格、型号</td>' +
    '<td  colspan=7 align="left" width="15%">'+alms.GetItemData(item.prdstandard) +'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%">生产厂家</td>' +
    '<td  colspan=7 align="left" width="15%">'+alms.GetItemData(item.factoryname) +'</td></tr>';
     
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%">供应商</td>' +
      '<td  colspan=7 align="left" width="15%">'+alms.GetItemData(item.tradename) +'</td></tr>';
   
      html += '<tr class="infotr"  style="height: 40px;"><td class="infotr" align="center" width="10%">进货时间</td>' +
      '<td  colspan=4 align="left" width="15%">'+alms.GetItemDateData(item.buydate) +'</td>'+
    '<td  align="center" width="10%">(拟)进货量</td>' +
    '<td  colspan=2 align="left" width="15%">'+alms.GetItemData(item.buycount) +'</td></tr>' ;
      
      html += '<tr class="infotr" style="height: 100px;" ><td class="infotr" align="center"   width="10%">办公室意见:</td>' +
      '<td  colspan=4 align="left" width="15%">'+alms.GetItemData(item.auditdesc) +'</td>'+
      '<td  align="center"  width="10%">办公室负责人：</td>' +
      '<td  colspan=2 align="left" width="15%">'+alms.GetItemData(item.auditusername) +'</td></tr>' ;
      
      
      
      html += '<tr class="infotr" style="height: 40px;"><td class="infotr" align="center" width="10%">验证人员</td>' +
      '<td  colspan=4 align="left" width="15%">'+alms.GetItemData(item.comfirmusername) +'</td>'+
      '<td class="infotr" align="center" width="10%">验证时间</td>' +
      '<td  colspan=2 align="left" width="15%">'+alms.GetItemDateData(item.comfirmdate) +'</td></tr>' ;
     
      
//      html += '<tr><td class="infotr" colspan="10" align="left" width="100%" style="height: 40px;font-size:20px;border-bottom:0;border-left:0;border-top:0;border-right:0;">办公室意见：</br>'+alms.GetItemData(item.auditdesc)+'</td></tr>';
//      html += '<tr><td  class="infotr" style=" border-top:0;border-right:0; border-bottom:0;border-left:0;" colspan=7 align="right" width="15%">办公室负责人：</td><td class="infotr" style=" border-bottom:0;border-top:0;border-left:0;" align="left" width="10%">'+alms.GetItemData(item.auditusername) +'</td></tr>';
//        
     
      html += '<tr class="infotr" style="height: 100px;" ><td class="infotr" align="center"   width="10%">验 证 情 况：</td>' +
      '<td  colspan=4 align="left" width="15%">'+alms.GetItemData(judger.tododesc) +'</td>'+
      '<td  align="center"  width="10%">检测室负责人：</td>' +
      '<td  colspan=2 align="left" width="15%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" />'+'</td></tr>' ;
    
      html += '<tr class="infotr"><td  style="height: 60px;" align="center" width="10%">主管意见：</td>' +
      '<td  colspan=7 align="left" width="15%">'+alms.GetItemData( approver.tododesc) +'</td></tr>';
      
      html += '<tr class="infotr"><td  style="height: 60px;" align="center" width="10%">备注：</td>' +
      '<td  colspan=7 align="left" width="15%">'+alms.GetItemData(item.remark) +'</td></tr>';
      
//      html += '<tr class="infotr"><td colspan="10" align="left" width="100%" style="height: 60px;font-size:20px">主管意见：</br>'+alms.GetItemData(item.checkdesc)+'</td></tr>';
//      html += '<tr class="infotr"><td colspan="10" align="left" width="100%" style="height: 60px;font-size:20px">备注：</br>'+alms.GetItemData(item.remark)+'</td></tr>';
//   
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//    '<td class="infoc infoleft" colspan=7 align="left" width="15%">'+alms.GetItemData(item.verifyid) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">试剂耗材名称</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.prdname) +'</td>'+
//    '<td class="infoat" align="center" width="10%">规格、型号</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.prdstandard) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">生产厂家</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.factoryname) +'</td>'+
//    '<td class="infoat" align="center" width="10%">供应商</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.tradename) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出厂批号</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.factorycode) +'</td>'+
//    '<td class="infoat" align="center" width="10%">进货时间</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemDateData(item.buydate) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">(拟)进货量</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.buycount) +'</td>'+
//    '<td class="infoat" align="center" width="10%">业务员</td>' +
//    '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td></tr>';
    
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">办公室</td>' +
//    '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemData(item.auditusername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">办公室确认日期</td>' +
//    '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemDateData(item.auditdate) +'</td></tr>';
//    
//    html += '<tr class="infotr"> '+
//    '<td class="infoat" align="center" width="10%">办公室意见</td>' +
//    '<td class="infoc infoleft" colspan="7"  align="left" width="10%">'+alms.GetItemData(item.auditdesc) +'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">验证人员</td>' +
//    '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemData(item.comfirmusername) +'</td>'+
//    '<td class="infoat" align="center" width="10%">验证时间</td>' +
//    '<td class="infoc infoleft" colspan="3" align="left" width="15%">'+alms.GetItemDateData(item.comfirmdate) +'</td></tr>';
//    
//    html += '<tr class="infotr"> '+
//    '<td class="infoat" align="center" width="10%">验证情况</td>' +
//    '<td class="infoc infoleft" colspan="7"  align="left" width="10%">'+alms.GetItemData(item.comfirmdesc) +'</td></tr>';
//    
//    html += '<tr class="infotr"> '+
//    '<td class="infoat" align="center" width="10%">主管意见</td>' +
//    '<td class="infoc infoleft" colspan="7"  align="left" width="10%">'+alms.GetItemData(item.checkdesc) +'</td></tr>';
    
//    html += '<tr class="infotr"> '+
//    '<td class="infoat" align="center" width="10%">备注</td>' +
//    '<td class="infoc infoleft" colspan="7"  align="left" width="10%">'+alms.GetItemData(item.remark) +'</td></tr>';
    
    html += '</table>';
    html + '</td></tr>';
    html += '</table>';
     var attaches = tools.JsonGet(tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + item.verifyid).data;
    if(attaches){
      if(attaches.length>0){

    	     var  html2 = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
        html2 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">图谱明细附件信息</td></tr>';
        
        html2 += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
          '<td class="infoat" align="center" width="10%">附件名称</td>' +
          '<td class="infoat" align="center" width="10%">附件类型</td>' +
          '<td class="infoat" align="center" width="10%">预览</td></tr>';

        for(var i=0;i<attaches.length;i++){
        var str = attaches[i].attachurl.split(".");
          var attachname = attaches[i].attachname+"."+str[str.length-1];
          var attachtype = attaches[i].attachtypename;
          var attachurl = attaches[i].attachurl;
          html2 += '<tr class="infotr">' +
          '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
        }
      }
    }
    html2 += '</table>';
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html+html2);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowGetHtml(item);
      viewFile = function (fileurl,filename) {
          alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
      };
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