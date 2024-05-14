Ext.define('alms.viewcrmsurvey',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'满意度调查',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_crmsurvey',
          storeUrl:'CrmSearchCrmSurvey.do',
          expUrl:'CrmSearchCrmSurvey.do',
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
       ' ', { xtype: 'textfield', fieldLabel: '调查编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '客户名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchcustname', id: mep + 'searchcustname', allowBlank: true }
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
           'crmsurvey.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'crmsurvey.custname':tools.GetValueEncode(mep+'searchcustname')
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
    var record = tools.JsonGet('CrmGetCrmSurvey.do?crmsurvey.tranid='+item.tranid);
    var surveys = tools.JsonGet('CrmGetListCrmSurveyDetail.do?surveydetail.tranid='+item.tranid).data;
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>客户满意度调查表 </b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-7-3</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >客户名称</td>' +
    '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+alms.GetItemData(record.custname) +'</td>'+
    '<td class="infoat" align="center"  " width="10%" >检测项目</td>' +
    '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+alms.GetItemData(record.testitem)+'</td></tr>';
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >地址</td>' +
    '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+alms.GetItemData(record.custaddr) +'</td>'+
    '<td class="infoat" align="center"  " width="10%" >联系电话</td>' +
    '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+alms.GetItemData(record.linktele)+'</td></tr>';

    
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" rowspan=5 >调查内容</td>';

    html += '<td class="infoat" align="center" width="10%"  >检测质量</td><td class="infoat" align="center" width="10%"  >样品维护及处置</td><td class="infoat" align="center" width="10%"  >按期完成</td><td class="infoat" align="center" width="10%"  >业务接洽</td><td class="infoat" align="center" width="10%"  >价格</td><td class="infoat" align="center" width="10%"  >总体评价</td></tr>'
      var jczl = null;
      var ypwh =null;
      var aqwc = null;
      var ywjq = null;
      var jg = null;
      var ztpj = null;
      for( a=0;a<surveys.length;a++){
        switch(surveys[a].surveyitemname)
        {
        case '业务接洽':
         ywjq = surveys[a];
          break;
        case '检测质量':
          jczl = surveys[a];
          break;
        case '样品维护及处置':
          ypwh = surveys[a];
          break;
        case '按期完成':
          aqwc= surveys[a];
          break;  
        
        case '价格':
          jg= surveys[a];
          break;    
          
        case '总体评价':
          ztpj= surveys[a];
          break;     
        default:
         
        }
      }
  
    html += '<tr class="infotr"> <td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(jczl?(jczl.surveyscorename=="较满意"?'checked="checked"':''):'') +'/></form></td>';
    
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(ypwh?(ypwh.surveyscorename=="较满意"?'checked="checked"':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(aqwc?(aqwc.surveyscorename=="较满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(ywjq?(ywjq.surveyscorename=="较满意"?'checked="checked" ':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(jg?(jg.surveyscorename=="较满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>较满意<input   type="checkbox"     onclick="return  false" '+(ztpj?(ztpj.surveyscorename=="较满意"?'checked="checked"':''):'')+'/></form></td></tr>';
    
    html += '<tr class="infotr"> <td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(jczl?(jczl.surveyscorename=="一般满意"?'checked="checked"':''):'') +'/></form></td>';
    
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(ypwh?(ypwh.surveyscorename=="一般满意"?'checked="checked"':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(aqwc?(aqwc.surveyscorename=="一般满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(ywjq?(ywjq.surveyscorename=="一般满意"?'checked="checked" ':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(jg?(jg.surveyscorename=="一般满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>一般满意<input   type="checkbox"     onclick="return  false" '+(ztpj?(ztpj.surveyscorename=="一般满意"?'checked="checked"':''):'')+'/></form></td></tr>';
    
    html += '<tr class="infotr"> <td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(jczl?(jczl.surveyscorename=="不满意"?'checked="checked"':''):'') +'/></form></td>';
    
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(ypwh?(ypwh.surveyscorename=="不满意"?'checked="checked"':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(aqwc?(aqwc.surveyscorename=="不满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(ywjq?(ywjq.surveyscorename=="不满意"?'checked="checked" ':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(jg?(jg.surveyscorename=="不满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>不满意<input   type="checkbox"     onclick="return  false" '+(ztpj?(ztpj.surveyscorename=="不满意"?'checked="checked"':''):'')+'/></form></td></tr>';
    
    html += '<tr class="infotr"> <td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(jczl?(jczl.surveyscorename=="很不满意"?'checked="checked"':''):'') +'/></form></td>';
    
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(ypwh?(ypwh.surveyscorename=="很不满意"?'checked="checked"':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(aqwc?(aqwc.surveyscorename=="很不满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(ywjq?(ywjq.surveyscorename=="很不满意"?'checked="checked" ':''):'') +'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(jg?(jg.surveyscorename=="很不满意"?'checked="checked"':''):'')+'/></form></td>';
    html += '<td class="infoc infoleft" align="center"  width="10%" >'+'<form>很不满意<input   type="checkbox"     onclick="return  false" '+(ztpj?(ztpj.surveyscorename=="很不满意"?'checked="checked"':''):'')+'/></form></td></tr>';
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"   style= "height:380px;" >备注</td>';
    html += '<td class="infoc infoleft" align="left"  width="10%" colspan=6 ><p>&nbsp;&nbsp;&nbsp;&nbsp;若您能对调查内容“较满意、一般满意、不满意、很不满意”等选项加以文字说明</br>，以便我们进行统计分析和采取措施改进工作、更好地为您服务，我们将不胜</br>感激。</p></td>';

    
    
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" style= "height:70px;" >客户签章</td>' +
    '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+'' +'</td>'+
    '<td class="infoat" align="center"  " width="10%" style= "height:70px;">日期</td>' +
    '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+alms.GetItemDateData(record.custdate)+'</td></tr>';
     
//      html += '<tr class="infotr"  ><td  class="infoat"  rowspan="3" align="center"  width="4%">调查内容</td>' +
//      '<td class="infoc infoleft" align="left" style="height:0px;"colspan=4  width="20%">'+'' +'</td></tr>';
//      html + '</td></tr>';
//      html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >申请部门</td>' +
//      '<td class="infoc infoleft" colspan=3  align="center" width="10%">'+''+'</td></tr>';
//      html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
//      '<td class="infoc infoleft" width="30%"   align="center" >'+''+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+''+'</td></tr>';
         
//    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>客户满意度调查表</b></td></tr>';
//    
//    html += '<tr class="infotr" style="height:'+ height +';"><td class="infoat" align="center" width="10%" >客户名称</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.custname)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >检测项目</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.testitem)+'</td></tr>';
//    
//    html += '<tr class="infotr" style="height:'+ height +';"><td class="infoat" align="center" width="10%" >地址</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.custaddr)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >联系电话</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.linktele)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" rowspan="'+(surveys.length+1) +'"align="center" width="10%" >调查内容</td>' +
//    '<td class="infoc infoleft"  align="center" width="10%">调查项目</td>' +
//    '<td class="infoc infoleft"  align="center" width="10%" colspan=2>调查结果</td>' +
//    '</tr>';
//    
//    for(var i=0;i<surveys.length;i++){
//       survey = surveys[i];
//       html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%">'+alms.GetItemData(survey.surveyitemname)+'</td>' +
//      '<td class="infoc infoleft"  align="center" width="30%" colspan=2>'+alms.GetItemData(survey.surveyscorename)+'</td>' +
//      '</tr>';
//    }
//    
//    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%"  >备注</td>' +
//    '<td class="infoc infoleft" colspan=3  align="center" width="90%">'+alms.GetItemData(record.remark)+'</td></tr>';
//   
    html += '</table>';
    html += '<center>请将本调查表寄往：江苏省农产品质量检验测试中心（南京市草场门大街124号 邮</br>编210036）中心办公室收，您也可以通过电话（025-86263556）、传真（025-86229784）、</br>EMAIL（qianzheng@jsagri.gov.cn）告知您的意见。 </center>'
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