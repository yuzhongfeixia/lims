Ext.namespace('almsprint');


almsprint.GetItemData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : data;
};

almsprint.GetItemDateData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : Ext.Date.format(data, 'Y-m-d');
};

almsprint.GetFullDateData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : Ext.Date.format(data, 'Y-m-d H:i');
};

almsprint.GetTranData = function (data) {  
  return Ext.isEmpty(data) ? '' : data;
};

almsprint.GetTranDateData = function (data) {  
  return Ext.isEmpty(data) ? null : data;
};

almsprint.GetHtmlData = function (data) {  
  if (Ext.isEmpty(data))
    return '&nbsp;';
  else
    return data.toString();
};


// region GetBusNotice Html

almsprint.GetBusNotice = function (item) {
  if (item) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotrheader"><td colspan="6" align="center" width="100%" class="infohead"><b>取样通知单</b></td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">通知单号</td>' +
      '<td class="infoc infoleft" align="left" width="23%">' + lims.GetTranData(item.tranid) + '</td>' +
      '<td class="infoat" align="center" width="10%">工程名称</td>' +
      '<td class="infoc infoleft" align="left" width="23%">'+  lims.GetTranData(item.projectname) + '</td>' +
      '<td class="infoat" align="center" width="10%">标段号</td>' +
      '<td class="infoc infoleft" align="left" width="24%">'+ lims.GetTranData(item.sectionname) +'</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">取样人</td>' +
      '<td class="infoc infoleft" align="left">' + lims.GetTranData(item.getusername) + '</td>' +
      '<td class="infoat" align="center">下达时间</td>' +
      '<td class="infoc infoleft" align="left">'+  Ext.Date.format(item.trandate, 'Y-m-d H:i') + '</td>' +
      '<td class="infoat" align="center">完成时间</td>' +
      '<td class="infoc infoleft" align="left">'+ Ext.Date.format(item.finishdate, 'Y-m-d H:i') +'</td></tr>';
      
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

almsprint.GetBusNoticeDetail = function (items) {
  if (items) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="14%">取样内容</td>' +
      '<td class="infoat" align="center" width="6%">取样地点</td>' +
      '<td class="infoat" align="center" width="40%">见证人</td>' +
      '<td class="infoat" align="center" width="40%">备注</td></tr>';
    
    var itemclass ='infoc';
    
    for (var i = 0; i < items.length; i++) {
      var notice = items[i];
      
      itemclass = i % 2 == 0 ? 'infoc' : 'infoac';
      
      rtn += '<tr class="infotr"><td class="' + itemclass + ' infoleft" align="left" width="30%">' + lims.GetTranData(notice.getcontent) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">'+  lims.GetTranData(notice.getaddress) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="14%">' + lims.GetTranData(notice.witnessname) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="50%">' + lims.GetTranData(notice.remark) + '</td></tr>';
      
    }
    
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

// endregion GetBusNotice Html

// region GetBusConsign Html

//almsprint.ConsignHtml = function(record){
//
//  var busgetdetail = tools.JsonGet('LabGetListBusGetDetail.do?bgd.tranid='+record.tranid).data;
//
//  var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//  html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>检验委托书</b></td></tr>';
//  
//  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">委托编号</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(record.tranid)+ '</td>' +
//    '<td class="infoat" align="center" width="10%">委托单位</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(record.testedname)+'</td>'+
//    '<td class="infoat" align="center" width="10%">联系电话</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(record.entertele)+'</td>'+
//    '<td class="infoat" align="center" width="10%">邮政编码</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(record.enterpost)+'</td></tr>';
//  
//  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">委托人</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(record.testeduser)+ '</td>' +
//    '<td class="infoat" align="center" width="10%">委托时间</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(record.testeddate)+'</td>'+
//    '<td class="infoat" align="center" width="10%">时间要求</td>' +
//    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(record.timereqname)+'</td>'+
//    '<td class="infoat" align="center" width="10%"></td>' +
//    '<td class="infoc infoleft" align="left" width="15%"></td></tr>';
//    
//  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">报告提取方式</td>' +
//    '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(record.enteraddr)+ '</td></tr>'
//  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">报告提取方式</td>' +
//    '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(record.reportget)+ '</td></tr>'
//  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">余样处理方式</td>' +
//    '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(record.restdeal)+ '</td></tr>'
//  
//  html += '</table>';
//  
//  if(busgetdetail.length > 0){
//    html +='<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%">检验样品信息</td></tr>';
//    
//    for(var i = 0; i < busgetdetail.length; i++){
//      
//      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">样品名称</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].samplename)+ '</td>' +
//        '<td class="infoat" align="center" width="10%">商标</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].trademark)+'</td>'+
//        '<td class="infoat" align="center" width="10%">规格型号</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplestand)+'</td>'+
//        '<td class="infoat" align="center" width="10%">样品等级</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplelevel)+'</td></tr>';
//      
//      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">生产单位</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].factname)+ '</td>' +
//        '<td class="infoat" align="center" width="10%">生产批号</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].prdcode)+'</td>'+
//        '<td class="infoat" align="center" width="10%">样品数量</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplecount)+'</td>'+
//        '<td class="infoat" align="center" width="10%">样品状态</td>' +
//        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplestatus)+'</td></tr>'
//        
//      var param = tools.JsonGet('LabGetListBusSampleParam.do?bsp.samplecode='+busgetdetail[i].samplecode ).data;
//      
//      if(param.length > 0){
//        html += '<br/>';
//        html +='<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//        html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%">检测参数</td></tr>';
//        
//        html += '<tr class="infotr"><td class="infoat" align="center" width="20%">检测参数</td>' +
//        '<td class="infoat" align="center" width="20%">检测依据</td>' +
//        '<td class="infoat" align="center" width="40%">判定依据</td>' +
//        '<td class="infoat" align="center" width="10%">判定方法</td>' +
//        '<td class="infoat" align="center" width="10%">标准值</td></tr>';
//        
//        for(var i = 0; i < param.length; i++){
//          html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="20%">' +alms.GetItemData(param[i].parametername)+ '</td>' +
//            '<td class="infoc infoleft" align="center" width="20%">'+alms.GetItemData(param[i].judgestandardname)+'</td>'+
//            '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(param[i].teststandardname)+'</td>'+
//            '<td class="infoc infoleft" align="center" width="10%">'+alms.GetItemData(param[i].testjudge)+'</td>'+
//            '<td class="infoc infoleft" align="center" width="10%">'+alms.GetItemData(param[i].standvalue)+'</td></tr>'
//        };
//        
//        html += '</table>';
//      }
//      
//    };
//    
//    html += '</table>';
//  }
//  
//  return html;
//}

almsprint.ConsignHtml = function(record){

  var busgetdetail = tools.JsonGet('LabGetListBusGetDetail.do?bgd.tranid='+record.tranid).data;
  
  var busaccsample = tools.JsonGet('LabGetBusAccSampleByGetID.do?bas.getid='+record.tranid);

  var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="70%" height=120%>';
  html += '<tr class="infotrheader"><td colspan="6" align="center" width="100%" style="height: 40px;font-size:20px"><b>检验委托书</b></td></tr>';
  if(busgetdetail){
    if(busgetdetail.length > 0){
      for(var i = 0; i < busgetdetail.length; i++){
        
        html += '<tr class="infotrheader"><td colspan="3" align="left" width="50%" style="height: 20px;font-size:12px">编号</td>' +
        '<td colspan="3" align="right" width="50%" style="height: 20px;font-size:12px">QCR 5-6-3</td></tr>';
    
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">样品名称</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(busgetdetail[i].samplename)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">委托单位</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(record.testedname)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">生产单位</td>' +
        '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(busgetdetail[i].factname)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">样品基数</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemData(busgetdetail[i].samplebase)+ '</td>' +
          '<td class="infoat" align="center" width="16%">商标</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busgetdetail[i].trademark)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">规格型号</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemData(busgetdetail[i].samplestand)+ '</td>' +
          '<td class="infoat" align="center" width="16%">生产批号</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busgetdetail[i].prdcode)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">样品状态</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(busgetdetail[i].samplestatus)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">样品数量</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemData(busgetdetail[i].samplecount)+ '</td>' +
          '<td class="infoat" align="center" width="16%">样品等级</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busgetdetail[i].samplelevel)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%" style="height: 60px">检测项目</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%" style="height: 60px">'+alms.GetItemData(busgetdetail[i].testitems)+'</td>' +
          '<td class="infoat" align="center" width="16%" style="height: 60px">检测依据</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%" style="height: 60px">'+alms.GetItemData(busgetdetail[i].mainstandname)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">委托人</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemData(record.testeduser)+ '</td>' +
          '<td class="infoat" align="center" width="16%">联系电话</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(record.entertele)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">通讯地址</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemData(record.enteraddr)+ '</td>' +
          '<td class="infoat" align="center" width="16%">邮政编码</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(record.enterpost)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">委托日期</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemDateData(record.testeddate)+ '</td>' +
          '<td class="infoat" align="center" width="16%">时间要求</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(record.timereqname)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">报告提取方式</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(record.reportget)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">余样处置方式</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%">'+alms.GetItemData(record.restdeal)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="100%" style="height: 40px;font-size:15px"><b>以 下 由 业 务 管 理 员 填 写</b></td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">收样人</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busaccsample.accusername)+'</td>' +
          '<td class="infoat" align="center" width="16%">收样日期</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">' +alms.GetItemDateData(busaccsample.accdate)+ '</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%">检验费用</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busaccsample.feestatus)+'</td>' +
          '<td class="infoat" align="center" width="16%">咨询电话</td>' +
          '<td class="infoc infoleft" colspan="2" align="left" width="34%">'+alms.GetItemData(busaccsample.entertele)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="16%" style="height: 80px">备注(合同评审)</td>' +
          '<td class="infoc infoleft" colspan="5" align="left" width="84%" style="height: 80px">'+alms.GetItemData(busaccsample.remark)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoc infoleft" colspan="6" align="left" width="100%" style="height: 50px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：1、委托书一经完成,即具有法律效力,不得随意修改;  2、此委托书一式两联,第二联作为委托方提取检验报告的依据; 3、委托书中不详之处请用“——”线填充。</td></tr>';
        
        html += '<tr class="infotrheader"><td colspan="6" align="left" width="100%">检验机构地址：江苏省南京市草场门大街124号江苏农业检测大楼 &nbsp;&nbsp;&nbsp;邮政编码：210036    联系电话：025—86229784</td></tr>';
      }
    }
  }
  
  html += '</table>';
  
  return html;
}
// endregion GetBusConsign Html


// region GetBusGet Html

almsprint.GetBusYGet = function (item) {
  if (item) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotrheader"><td colspan="6" align="center" width="100%" class="infohead"><b>原材取样单</b></td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">取样单号</td>' +
      '<td class="infoc infoleft" align="left" width="23%">' + lims.GetTranData(item.tranid) + '</td>' +
      '<td class="infoat" align="center" width="10%">工程名称</td>' +
      '<td class="infoc infoleft" align="left" width="23%">'+  lims.GetTranData(item.projectname) + '</td>' +
      '<td class="infoat" align="center" width="10%">标段号</td>' +
      '<td class="infoc infoleft" align="left" width="24%">'+ lims.GetTranData(item.sectionname) +'</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">取样人</td>' +
      '<td class="infoc infoleft" align="left">' + lims.GetTranData(item.getusername) + '</td>' +
      '<td class="infoat" align="center">监理单位</td>' +
      '<td class="infoc infoleft" align="left">'+  lims.GetTranData(item.supervisorname) + '</td>' +
      '<td class="infoat" align="center">施工单位</td>' +
      '<td class="infoc infoleft" align="left">'+ lims.GetTranData(item.constructname) +'</td></tr>';
      
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

almsprint.GetBusGetYDetail = function (items) {
  if (items) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="14%">样品名称</td>' +
      '<td class="infoat" align="center" width="6%">规格型号</td>' +
      '<td class="infoat" align="center" width="40%">生产厂家/产地</td>' +
      '<td class="infoat" align="center" width="40%">工程部位</td>' + 
      '<td class="infoat" align="center" width="40%">取样地点</td>' + 
      '<td class="infoat" align="center" width="40%">取样数量</td>' + 
      '<td class="infoat" align="center" width="40%">取样依据</td>' + 
      '<td class="infoat" align="center" width="40%">进场日期</td>' + 
      '<td class="infoat" align="center" width="40%">代表数量</td>' + 
      '<td class="infoat" align="center" width="40%">见证人/见证号</td>' + 
      '<td class="infoat" align="center" width="40%">备注</td></tr>';
    
    var itemclass ='infoc';
    
    for (var i = 0; i < items.length; i++) {
      var get = items[i];
      
      itemclass = i % 2 == 0 ? 'infoc' : 'infoac';
      
      rtn += '<tr class="infotr"><td class="' + itemclass + ' infoleft" align="left" width="6%">' + lims.GetTranData(get.samplename) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="7%">'+  lims.GetTranData(get.samplestandard) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">' + lims.GetTranData(get.factname) + '/' + lims.GetTranData(get.areaname) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="5%">' + lims.GetTranData(get.projectunitname) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">'+  lims.GetTranData(get.getgroup) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="25%">'+  lims.GetTranData(get.getbasis) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">'+  Ext.Date.format(item.divedate, 'Y-m-d H:i') + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="8%"></td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">'+  lims.GetTranData(get.witnessname) + '/' + lims.GetTranData(get.witnesscode) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="13%">'+  lims.GetTranData(get.remark) + '</td></tr>';
      
    }
    
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};



almsprint.GetBusZGet = function (item) {
  if (item) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotrheader"><td colspan="6" align="center" width="100%" class="infohead"><b>制件取样单</b></td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">取样单号</td>' +
      '<td class="infoc infoleft" align="left" width="23%">' + lims.GetTranData(item.tranid) + '</td>' +
      '<td class="infoat" align="center" width="10%">工程名称</td>' +
      '<td class="infoc infoleft" align="left" width="23%">'+  lims.GetTranData(item.projectname) + '</td>' +
      '<td class="infoat" align="center" width="10%">标段号</td>' +
      '<td class="infoc infoleft" align="left" width="24%">'+ lims.GetTranData(item.sectionname) +'</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">取样人</td>' +
      '<td class="infoc infoleft" align="left">' + lims.GetTranData(item.getusername) + '</td>' +
      '<td class="infoat" align="center">监理单位</td>' +
      '<td class="infoc infoleft" align="left">'+  lims.GetTranData(item.supervisorname) + '</td>' +
      '<td class="infoat" align="center">施工单位</td>' +
      '<td class="infoc infoleft" align="left">'+ lims.GetTranData(item.constructname) +'</td></tr>';
      
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

almsprint.GetBusGetZDetail = function (items) {
  if (items) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="8%">制件名称</td>' +
      '<td class="infoat" align="center" width="6%">规格型号</td>' +
      '<td class="infoat" align="center" width="6%">生产厂家</td>' +
      '<td class="infoat" align="center" width="8%">制件地点</td>' + 
      '<td class="infoat" align="center" width="6%">工程部位</td>' + 
      '<td class="infoat" align="center" width="8%">制件日期</td>' + 
      '<td class="infoat" align="center" width="4%">制件数量</td>' + 
      '<td class="infoat" align="center" width="14%">制件依据</td>' + 
      '<td class="infoat" align="center" width="8%">取回时间</td>' + 
      '<td class="infoat" align="center" width="6%">制件环境</td>' + 
      '<td class="infoat" align="center" width="6%">试件标识</td>' + 
      '<td class="infoat" align="center" width="10%">见证人/见证号</td>' + 
      '<td class="infoat" align="center" width="10%">备注</td></tr>';
    
    var itemclass ='infoc';
    
    for (var i = 0; i < items.length; i++) {
      var get = items[i];
      
      itemclass = i % 2 == 0 ? 'infoc' : 'infoac';
      
      rtn += '<tr class="infotr"><td class="' + itemclass + ' infoleft" align="left" width="8%">' + lims.GetTranData(get.samplename) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">'+  lims.GetTranData(get.samplestandard) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">' + lims.GetTranData(get.factname) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="8%">' + lims.GetTranData(get.getaddress) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">'+  lims.GetTranData(get.projectunitname) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="8%">'+  Ext.Date.format(get.makedate, 'Y-m-d H:i') + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="4%">'+  lims.GetTranData(get.getgroup) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="14%">'+ lims.GetTranData(get.getbasis) +'</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="8%">'+ Ext.Date.format(get.divedate, 'Y-m-d H:i') +'</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%"></td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%"></td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">'+  lims.GetTranData(get.witnessname) + '/' + lims.GetTranData(get.witnesscode) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">'+  lims.GetTranData(get.remark) + '</td></tr>';
    }
    
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};



// endregion GetBusGet Html

// region BusTask Html

almsprint.GetBusTaskHtml = function (item) {
  if (item) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotrheader"><td colspan="6" align="center" width="100%" class="infohead"><b>任务单</b></td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">任务单单号</td>' +
      '<td class="infoc infoleft" align="left" width="23%">' + lims.GetTranData(item.tranid) + '</td>' +
      '<td class="infoat" align="center" width="10%">来样方式</td>' +
      '<td class="infoc infoleft" align="left" width="23%">'+  lims.GetTranData(item.samplesourcename) + '</td>' +
      '<td class="infoat" align="center" width="10%">检验要求</td>' +
      '<td class="infoc infoleft" align="left" width="24%">'+ lims.GetTranData(item.testrequestname) +'</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">保密要求</td>' +
      '<td class="infoc infoleft" align="left">' + lims.GetTranData(item.secretrequestname) + '</td>' +
      '<td class="infoat" align="center">其他附件</td>' +
      '<td class="infoc infoleft" align="left" colspan="3">'+  lims.GetTranData(item.tranremark) + '</td></tr>';
      
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

almsprint.GetBusTaskSampleHtml = function (items) {
  if (items) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">样品编号</td>' +
      '<td class="infoat" align="center" width="8%">样品名称</td>' +
      '<td class="infoat" align="center" width="10%">规格型号</td>' +
      '<td class="infoat" align="center" width="6%">取样地点</td>' +
      '<td class="infoat" align="center" width="6%">数量</td>' +
      '<td class="infoat" align="center" width="15%">检测参数</td>' +
      '<td class="infoat" align="center" width="15%">检测方法</td>' + 
      '<td class="infoat" align="center" width="15%">判定依据</td></tr>';
    
    var itemclass ='infoc';
    
    for (var i = 0; i < items.length; i++) {
      var sample = items[i];
      
      itemclass = i % 2 == 0 ? 'infoc' : 'infoac';
      
      rtn += '<tr class="infotr"><td class="' + itemclass + ' infoleft" align="left" width="10%">' + lims.GetTranData(sample.sampleid) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="8%">'+  lims.GetTranData(sample.samplename) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="10%">' + lims.GetTranData(sample.samplestandard) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">' + lims.GetTranData(notice.getaddress) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="6%">'+  lims.GetTranData(sample.getgroup) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="15%">' + lims.GetTranData(sample.parameternames) + '</td>' +
        '<td class="' + itemclass + ' infoleft" align="left" width="15%">' + lims.GetTranData(sample.teststandardname) + '</td>' + 
        '<td class="' + itemclass + ' infoleft" align="left" width="15%">' + lims.GetTranData(sample.judgestandardname) + '</td></tr>';
      
    }
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">备注</td>' +
    '<td class="infoc infoleft" align="left" colspan="5">' + lims.GetTranData(item.remark) + '</td></tr>';
    
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};

almsprint.GetBusTaskConsignHtml = function (item) {
  if (item) {
    var rtn = '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="88%">';

    rtn += '<tr class="infotr"><td class="infoat" align="center" width="10%">样品接收情况</td>' +
      '<td class="infoc infoleft" align="left" width="23%">' + lims.GetTranData(item.sampleaccept) + '</td>' +
      '<td class="infoat" align="center" width="10%">样品描述</td>' +
      '<td class="infoc infoleft" align="left" width="23%">'+  lims.GetTranData(item.sampledesc) + '</td>' +
      '<td class="infoat" align="center" width="10%">分包参数</td>' +
      '<td class="infoc infoleft" align="left" width="24%">'+ lims.GetTranData(item.splitparameter) +'</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">样品处置</td>' +
      '<td class="infoc infoleft" align="left">' + lims.GetTranData(item.sampledisposalname) + '</td>' +
      '<td class="infoat" align="center">签发人</td>' +
      '<td class="infoc infoleft" align="left">'+  lims.GetTranData(item.tranremark) + '</td>' + 
      '<td class="infoat" align="center">签发日期</td>' +
      '<td class="infoc infoleft" align="left">'+  lims.GetTranData(item.tranremark) + '</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">样品管理员</td>' +
    '<td class="infoc infoleft" align="left" colspan="2">' + lims.GetTranData(item.sampledisposalname) + '</td>' +
    '<td class="infoat" align="center">收样日期</td>' +
    '<td class="infoc infoleft" align="left" colspan="2">'+  Ext.Date.format(get.divedate, 'Y-m-d H:i') + '</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" align="center">试验检测组</td>' +
    '<td class="infoc infoleft" align="left" colspan="2">' + lims.GetTranData(item.sampledisposalname) + '</td>' +
    '<td class="infoat" align="center">任务单接收日期</td>' +
    '<td class="infoc infoleft" align="left" colspan="2">'+  Ext.Date.format(get.divedate, 'Y-m-d H:i') + '</td></tr>';
    rtn += '</table>';
    
   return rtn;  
  }
  else
    return '';
};
// endregion BusTask Html

//region JudgeResult Html

almsprint.JudgeResult = function (taskid,sampletran,samplename,samplecode) {
  var taskresults = tools.JsonGet('LabGetListBusTaskResult.do?btr.taskid=' + taskid+ '&btr.sampletran=' + sampletran).data;

  var rtn = '<table cellspacing="0" cellpadding="0"  border="1" align="center" style="font-size:13px; margin:40 40 20 40;" width="80%">';
  
  rtn += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" class="infohead" style="font-size:18px; font-weight:bold; height:60px;">农业部农产品质量安全监督检验测试中心(南京)检测结果</td></tr>';

  rtn += '<tr class="infotr"><td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">样品名称</td>' +
    '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetTranData(samplename)+'</td>' +
    '<td class="infoat" align="center" colspan="4" width="20%" style="height:30px;">样品编号</td>'+
    '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetTranData(samplecode)+'</td></tr>';
  
  rtn += '<tr class="infotr"><td class="infoat" colspan="3" align="center" width="5%" style="height:30px;">序号 </td>' +
    '<td class="infoat" align="center" colspan="5" width="30%" style="height:30px;">检测项目</td>'+
    '<td class="infoat" align="center" colspan="2" width="15%" style="height:30px;">单位</td>'+
    '<td class="infoat" align="center" colspan="4" width="15%" style="height:30px;">指标</td>'+
    '<td class="infoat" align="center" colspan="3" width="15%" style="height:30px;">检测数据</td>'+
    '<td class="infoat" align="center" colspan="3" width="15%" style="height:30px;">单项评价</td></tr>';

  if(taskresults && taskresults.length>0){
    
    for(var i= 0;i<taskresults.length;i++){
     
      convert(taskresults[i].standvalue,taskresults[i].submitvalue);
      rtn += '<tr class="infotr"><td class="infoat" colspan="3" align="center" width="5%">'+(i+1)+'</td>' +
      '<td class="infoc infoleft" align="center" colspan="5" width="30%">'+alms.GetTranData(taskresults[i].parametername)+'</td>'+
      '<td class="infoc infoleft" align="center" colspan="2" width="15%">'+alms.GetTranData(taskresults[i].paramunit)+'</td>'+
      '<td class="infoc infoleft" align="center" colspan="4" width="15%">'+alms.GetTranData(taskresults[i].standvalue)+'</td>'+//
      '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData(taskresults[i].submitvalue)+'</td>'+
      '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData(taskresults[i].judgestatusname)+'</td></tr>';
    }
  }
  
  for(var i= taskresults.length;i<40;i++){
    rtn += '<tr class="infotr"><td class="infoat" colspan="3" align="center" width="5%">'+(i+1)+'</td>' +
    '<td class="infoc infoleft" align="center" colspan="5" width="30%">'+alms.GetTranData()+'</td>'+
    '<td class="infoc infoleft" align="center" colspan="2" width="15%">'+alms.GetTranData()+'</td>'+
    '<td class="infoc infoleft" align="center" colspan="4" width="15%">'+alms.GetTranData()+'</td>'+
    '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData()+'</td>'+
    '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData()+'</td></tr>';
  }

  rtn += '</table>';
  
  return rtn;  
  
};

//endregion JudgeResult Html

//region RecordJudgeResult Html

almsprint.RecordJudgeResult = function (taskid,samplecode) {
  
  var task = tools.JsonGet('LabGetBusTask.do?bt.taskid=' + taskid);
  var judgeparam = tools.JsonGet('LabGetListBusSampleParamByTask.do?bsp.samplecode=' + samplecode+'&bsp.taskid='+taskid).data;

  var rtn = '<table cellspacing="0" cellpadding="0"  border="0" style="font-size:12px;margin: 10 50 50 50;" width="93%">';
  
  rtn += '<tr class="infotrheader"><td colspan="20" align="center" width="100%"  class="infohead"><h1>样品检测数据结果记录</h1></td></tr>';
  
  rtn += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="10%">检验性质</td>' +
    '<td class="infoc infoleft" align="center" colspan="2" width="10%">'+alms.GetTranData(task.testpropname)+'</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">样品状态</td>' +
    '<td class="infoc infoleft" align="center" colspan="2" width="10%">'+alms.GetTranData(task.samplestatus)+'</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">承检室</td>' +
    '<td class="infoc infoleft" align="center" colspan="2" width="10%">'+alms.GetTranData(task.labname)+'</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">检测室负责人</td>' +
    '<td class="infoc infoleft" align="center" colspan="2" width="10%">'+alms.GetTranData(task.labusername)+'</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">任务单下达人</td>' +
    '<td class="infoc infoleft" align="center" colspan="2" width="10%">'+alms.GetTranData(task.sendusername)+'</td></tr>'; 
  
  rtn += '<tr class="infotr"><td class="infoat" colspan="4" align="center" width="20%">样品下达时间</td>' +
    '<td class="infoc infoleft" colspan="6" align="center" width="30%">'+alms.GetItemDateData(task.senddate)+'</td>' +
    '<td class="infoat" align="center" colspan="4" width="20%">要求完成时间</td>' +
    '<td class="infoc infoleft" align="center" colspan="6" width="30%">'+alms.GetItemDateData(task.reqdate)+'</td></tr>';
  var judgelength = 0;
  
  if(judgeparam.length>13){
    judgelength = 13;
  }else{
    judgelength = judgeparam.length;
  }
  
  rtn += '<tr class="infotr" style="height:60px;"><td class="infoat" colspan="2" align="center" width="10%">检验依据</td>' +
  '<td class="infoc infoleft" colspan="2" align="center" width="10%">'+alms.GetTranData(task.teststandardname)+'</td>' +
  '<td class="infoat" align="center" colspan="2" width="10%">检验项目</td>';

  //检测项目
  if(judgeparam){
    for(var g=0;g<judgelength;g++){
      
      if(g==0){
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].parametername)+'</td>';
      }else{
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].parametername)+'</td>';
      }
    }
    
    for(var i = 0;i<13-judgelength;i++){
      rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
    }
  }
  
  rtn +='<td class="infoat" align="center" rowspan = "3" colspan="1" width="5%">判定结果</td></tr>';
  
  rtn += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%">指   标 （mg/kg）</td>';
  
  //指标结果
  if(judgeparam){
    for(var g=0;g<judgelength;g++){
      
      if(g==0){
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].testjudge)+alms.GetTranData(judgeparam[g].standvalue)+'</td>';
      }else{
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].testjudge)+alms.GetTranData(judgeparam[g].standvalue)+'</td>';
      }
    }
    
    for(var i = 0;i<13-judgelength;i++){
      rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
    }
  }
  
  rtn += '<tr class="infotr" style="height:40px;"><td class="infoat" colspan="2" align="center" width="10%">样品编号</td>' +
    '<td class="infoat" colspan="3" align="center" width="15%">受检单位/产地</td>' +
    '<td class="infoat" align="center" colspan="1" width="5%">样品名称</td>' +
    '<td class="infoat" align="center" colspan="13" width="65%">检      测     数     据（mg/kg）</td></tr>';
  
  //获取判定数据t_bus_task_judge
  var samplecodes = task.samplecode.split(",");
  
  for(var j = 0;j<samplecodes.length;j++){
    var taskjudge = tools.JsonGet('LabGetListBusTaskJudgeBySampleCode.do?btj.samplecode=' + samplecodes[j]+'&btj.sampletype='+task.sampletype).data;
    var judgevalue = '';
    var judgecountno = 0;
    var judgecountyes = 0;
    var tasklength = 0;
    
    if(taskjudge.length>13){
      tasklength = 13;
    }else{
      tasklength = taskjudge.length;
    }
    
    if(taskjudge){
      for(var k=0;k<tasklength;k++){

        if(k==0){
          rtn += '<tr class="infotr" style="height:60px;"><td class="infoc infoleft" colspan="2" align="center" width="10%">'+alms.GetTranData(taskjudge[k].samplecode)+'</td>' +
          '<td class="infoc infoleft" colspan="3" align="center" width="15%">'+alms.GetTranData(taskjudge[k].testedname)+'/'+alms.GetTranData(taskjudge[k].getaddr)+'</td>' +
          '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].samplename)+'</td>'+
          '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].submitvalue)+'</td>';
        }else{
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].submitvalue)+'</td>';
        }
        
        if(taskjudge[k].actvalue == '不合格'){
          judgecountno++;
        }else if(taskjudge[k].actvalue == '合格'){
          judgecountyes++
        }
      }

      if(judgecountno>0){
        judgevalue = '不合格';
      }else if(judgecountyes>0){
        judgevalue = '合格';
      }else{
        judgevalue='';
      }
      
      for(var i = 0;i<13-tasklength;i++){
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
      }
    }
    
    rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgevalue)+'</td>';
    
    rtn += '</tr>';
  }
  
  rtn += '</table>';
  
  rtn += '<table cellspacing="0" cellpadding="0"  border="0" style="font-size:12px;margin: 10 10 10 50;" width="93%">';
  
  //检测的项目大于13个的时候
  if(judgeparam.length>13){
    rtn += '<p style="font-size:12px;margin: 10 0 10 50;">检测结果第二页<p/>';
    rtn += '<tr class="infotr" style="height:60px;"><td class="infoat" colspan="2" align="center" width="10%">检验依据</td>' +
    '<td class="infoc infoleft" colspan="2" align="center" width="10%">'+alms.GetTranData(task.teststandardname)+'</td>' +
    '<td class="infoat" align="center" colspan="2" width="10%">检验项目</td>';

    //检测项目
    if(judgeparam){
      for(var g=13;g<judgeparam.length;g++){
        
        if(g==0){
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].parametername)+'</td>';
        }else{
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].parametername)+'</td>';
        }
      }
      
      for(var i = 0;i<26-judgeparam.length;i++){
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
      }
    }
    
    rtn +='<td class="infoat" align="center" rowspan = "3" colspan="1" width="5%">判定结果</td></tr>';
    
    rtn += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%">指   标 （mg/kg）</td>';
    
    //指标结果
    if(judgeparam){
      for(var g=13;g<judgeparam.length;g++){
        
        if(g==13){
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].testjudge)+alms.GetTranData(judgeparam[g].standvalue)+'</td>';
        }else{
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgeparam[g].testjudge)+alms.GetTranData(judgeparam[g].standvalue)+'</td>';
        }
      }
      
      for(var i = 0;i<26-judgeparam.length;i++){
        rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
      }
    }
    
    rtn += '<tr class="infotr" style="height:40px;"><td class="infoat" colspan="2" align="center" width="10%">样品编号</td>' +
      '<td class="infoat" colspan="3" align="center" width="15%">受检单位/产地</td>' +
      '<td class="infoat" align="center" colspan="1" width="5%">样品名称</td>' +
      '<td class="infoat" align="center" colspan="13" width="65%">检      测     数     据（mg/kg）</td></tr>';
    
    //获取判定数据t_bus_task_judge
    var samplecodes = task.samplecode.split(",");

    for(var j = 0;j<samplecodes.length;j++){
      var taskjudge = tools.JsonGet('LabGetListBusTaskJudgeBySampleCode.do?btj.samplecode=' + samplecodes[j]+'&btj.sampletype='+task.sampletype).data;
      var judgevalue = '';
      var judgecountno = 0;
      var judgecountyes = 0;
      if(taskjudge){
        for(var k=13;k<taskjudge.length;k++){
          if(k==13){
            rtn += '<tr class="infotr" style="height:60px;"><td class="infoc infoleft" colspan="2" align="center" width="10%">'+alms.GetTranData(taskjudge[k].samplecode)+'</td>' +
            '<td class="infoc infoleft" colspan="3" align="center" width="15%">'+alms.GetTranData(taskjudge[k].testedname)+'/'+alms.GetTranData(taskjudge[k].getaddr)+'</td>' +
            '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].samplename)+'</td>'+
            '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].submitvalue)+'</td>';
          }else{
            rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(taskjudge[k].submitvalue)+'</td>';
          }
          
          if(taskjudge[k].actvalue == '不合格'){
            judgecountno++;
          }else if(taskjudge[k].actvalue == '合格'){
            judgecountyes++
          }
        }
        
        if(judgecountno>0){
          judgevalue = '不合格';
        }else if(judgecountyes>0){
          judgevalue = '合格';
        }else{
          judgevalue='';
        }
        
        for(var i = 0;i<26-taskjudge.length;i++){
          rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%"></td>';
        }
      }
      
      rtn += '<td class="infoc infoleft" align="center" colspan="1" width="5%">'+alms.GetTranData(judgevalue)+'</td>';
      
      rtn += '</tr>';
    
    }
  }
  
  
  
  rtn += '</table>';
  
//  me.disphtml = rtn;
  
  return rtn;  
  
};

//endregion JudgeResult Html

// region Print Html

Ext.define("Ext.ux.Print.HtmlPrint", {

  statics : {
    print : function(printhtml) {

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<link href="jslib/public/almsprint.css" rel="stylesheet" type="text/css" media="screen,print" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += '<div><table border="2" cellpadding="0" cellspacing="0" style="width:100%;height:100%"><tr><td valign="top" align="center">';
      html += printhtml;
      html += '</td></tr></table></div>';
      html += '<script lageuage="text/javascript"> '
        + 'function printWindow() { if (!(factory && factory.printing)) return false; '
        + 'factory.printing.header = ""; ' + 'factory.printing.footer = "";' 
        + 'factory.printing.portrait = ' + this.portrait + ';' 
        + 'factory.printing.leftMargin = ' + this.leftmargin + ';' 
        + 'factory.printing.topMargin = ' + this.topmargin + ';' 
        + 'factory.printing.rightMargin = ' + this.rightmargin + ';' 
        + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' 
        + 'factory.printing.Print(false); window.close();}</script>';
      html += '</body></html>';
      
      //letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0
      var win = window.open('', 'print', '', 'replace');
      win.document.write(html);
      win.document.close();
    },

    maxrow : 0,
    headings : '',
    printTitle : '',
    totalwidth : 0,
    portrait : true,
    leftmargin : 2.0,
    topmargin : 6.0,
    rightmargin : 2.0,
    bottommargin : 6.0,
    stylesheetPath : '',
    printAutomatically : true
  }
});

Ext.define("Ext.ux.Print.ReportPrint", {

  statics : {
    print : function(printhtml) {

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
//      html += '<link href="jslib/public/almsprint.css" rel="stylesheet" type="text/css" media="screen,print" />';
      html += '<link href="jslib/public/almsprint.css" rel="stylesheet" type="text/css" media="print" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += printhtml;
      html += '<script lageuage="text/javascript"> '
        + 'function printWindow() { if (!(factory && factory.printing)) return false; '
        + 'factory.printing.header = ""; ' + 'factory.printing.footer = "";' 
        + 'factory.printing.portrait = ' + this.portrait + ';' 
        + 'factory.printing.leftMargin = ' + this.leftmargin + ';' 
        + 'factory.printing.topMargin = ' + this.topmargin + ';' 
        + 'factory.printing.rightMargin = ' + this.rightmargin + ';' 
        + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' 
        + 'factory.printing.Print(false);}</script>';
      html += '</body></html>';
      
      //letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0
      var win = window.open('', 'print', '', 'replace');
      win.document.write(html);
      win.document.close();
    },

    maxrow : 0,
    headings : '',
    printTitle : '',
    totalwidth : 0,
    portrait : true,
    leftmargin : 0.0,
    topmargin : 0.0,
    rightmargin : 1.5,
    bottommargin : 0.0,
    stylesheetPath : '',
    printAutomatically : true
  }
});

Ext.define("Ext.ux.Print.QRCodePrint", {

  statics : {
    print : function(printhtml) {

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<style type="text/css">body { margin: 0px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += printhtml;
      html += '<script lageuage="text/javascript"> '
        + 'function printWindow() { if (!(factory && factory.printing)) return false; '
        + 'factory.printing.header = ""; ' + 'factory.printing.footer = "";' 
        + 'factory.printing.portrait = ' + this.portrait + ';' 
        + 'factory.printing.leftMargin = ' + this.leftmargin + ';' 
        + 'factory.printing.topMargin = ' + this.topmargin + ';' 
        + 'factory.printing.rightMargin = ' + this.rightmargin + ';' 
        + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' 
        + 'factory.printing.Print(false); window.close();}</script>';
      html += '</body></html>';
      
      //letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0
      var win = window.open('', 'print', '', 'replace');
      win.document.write(html);
      win.document.close();
    },

    maxrow: 0,
    headings: '',
    printTitle: '',
    totalwidth: 0,
    portrait: true,
    leftmargin: 0.0,
    topmargin: 2.0,
    rightmargin: 0.0,
    bottommargin: 0.0,
    stylesheetPath: '',
    printAutomatically: true
  }
});
//使值与指标格式一致   主要考虑两者都为小数时的位数不一致情况
function convert(stand,submit){

    var standvalue = stand.substring(1,stand.length);
    var isstandfloat = (/\d+\.\d+$/.test(stand));
    var issubmitfloat = (/\d+\.\d+$/.test(submit));
    //值和指标都为小数时
    if(isstandfloat && issubmitfloat){
      var stdr = standvalue.split(".")[1];
      var subr = submit.split(".")[1]
     
      if(subr.length > stdr.length){
        var s = parseFloat(submit);
        var num = new Number(s);
        
        return num.toFixed(stdr.length);
      }
      if(subr.length < stdr.length){
            
            for(j =0 ;j < stdr.length-subr.length;j++){
              submit += '0'
            }
        return submit;
      }
      
    }
    
    //值为整数，指标为小数时
    if(isstandfloat && !issubmitfloat && (/\d+$/.test(submit))){
      var stdr = standvalue.split(".")[1];
      submit = submit +'.'
      for(i = 0;i<stdr.length;i++){
        submit += '0';
      }
      return submit;
    }
    
    
    return  submit;
}
// endregio Print Html

