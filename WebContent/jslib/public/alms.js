Ext.namespace('alms');

alms = {
  FMT_DECIMAL2: '0.00',
  FMT_DECIMAL3: '0.000',
  FMT_TIMECNT: '0.000',
  FMT_DECIMAL6: '0.000000',
  FMT_MULSERV: '0.00',
  FMT_DECIMAL10: '0.0000000000',
  
  DEFAULT_TEST_TYPE: '1',
  DEFAULT_GET_TYPE: '1',
  DEFAULT_CONSIGN_DIRECTION: '1',
  
  URL_LAB_TREE_USER: 'BasLabTreeByType.do?lab.labtype=1',
  
  LAB_PROJECT: '项目机构',
  PROJECT_NAME: '工程项目',
  SAMPLE_NAME: '样品',
  VARIETY_NAME: '品种',
  TEST_TYPE: '检测类型',
  
  DEFAULT_PRINT_WIDTH: 27,
  DEFAULT_REPORT_WIDTH: 28,
  DEFAULT_DISPLAY_WIDTH: 36,
  
  winfile: null
};

alms.GetItemData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : data;
};

alms.GetItemDateData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : Ext.Date.format(data, 'Y-m-d');
};

alms.GetFullDateData = function (data) {  
  return Ext.isEmpty(data) ? '&nbsp;' : Ext.Date.format(data, 'Y-m-d H:i');
};

alms.GetTranData = function (data) {  
  return Ext.isEmpty(data) ? '' : data;
};

alms.GetTranDateData = function (data) {  
  return Ext.isEmpty(data) ? null : data;
};

alms.GetHtmlData = function (data) {  
  if (Ext.isEmpty(data))
    return '&nbsp;';
  else
    return data.toString();
};

alms.GetMustTitle = function (text) {  
  return '<font color="Red">' + text + '</font>';
};

alms.PopupFileShow = function (title, fileaction, fileurl, filename) {
  
  if (!alms.winfile) {
    alms.winfile = tools.GetPopupWindow('alms', 'showfile', {title: title});
      
    alms.winfile.OnFormLoad();
  }
  else {
    alms.winfile.OnFormShow();
  }
    
  alms.winfile.OnSetData(title, fileaction, fileurl, filename);
  
};


alms.ShowGetNoticeHtml = function(record){
  var getnotice = tools.JsonGet('LabGetBusGetNotice.do?bgn.tranid='+record.tranid);
  var getnoticedetail = tools.JsonGet('LabGetBusGetNoticeDetail.do?bgnd.tranid=' + record.tranid).data;
  var testedunit = tools.JsonGet('LabGetBusTestedUnit.do?btu.testedid='+getnotice.testedid);
  
  var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
  html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:15px"><b>取样通知单</b></td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">通知单编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(getnotice.tranid)+ '</td>' +
    '<td class="infoat" align="center" width="10%">抽样人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(getnotice.getusername)+'</td>'+
    '<td class="infoat" align="center" width="10%">抽样单类型</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(getnotice.gettypename)+'</td>'+
    '<td class="infoat" align="center" width="10%">受检单位</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(getnotice.testedname)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">抽样说明</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(getnotice.tranremark)+'</td></tr>';
  html += '</table>';
  
  html += '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
  html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:15px"><b>明细信息</b></td></tr>';
  
  if(getnoticedetail.length>0){
    for(var i = 0; i < getnoticedetail.length; i++){
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">抽样依据</td>' +
        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(getnoticedetail[i].getcontent)+ '</td>' +
        '<td class="infoat" align="center" width="10%">抽样地点</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(getnoticedetail[i].getaddress)+'</td>'+
        '<td class="infoat" align="center" width="10%">备注</td>' +
        '<td class="infoc infoleft" colspan=3 align="left" width="40%">'+alms.GetItemData(getnoticedetail[i].remark)+'</td></tr>';
    }
  }
  
  html += '</table>';
  return html
  
};

alms.ShowTradeViewHtml = function(record){
  var tradesurvey = tools.JsonGet('DevGetTradeSurvey.do?trsu.tranid='+record.tranid);
  var creator= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TradeSurvey&htodo.flownode=create');
  var checker= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TradeSurvey&htodo.flownode=check');
  var judger= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=TradeSurvey&htodo.flownode=judge');
  var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>供应商调查评价表 </b></td></tr>';
  html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 4-5-1</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >企业名称</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+alms.GetItemData(tradesurvey.tradename) +'</td>'+
  '<td class="infoat" align="center"  " width="10%" >联系人</td>' +
  '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+alms.GetItemData(tradesurvey.linkman)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >产品名称</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+alms.GetItemData(tradesurvey.prdname) +'</td>'+
  '<td class="infoat" align="center"  " width="10%" >电  话</td>' +
  '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+alms.GetItemData(tradesurvey.linktele)+'</td></tr>';
 
  html += '<tr class="infotr"><td class="infoat" align="left" width="10%" colspan=10>企业现状及合作历史：</td>';
  
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>企业规模：</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.enterscalename)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>知名度：</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.enterpopularname)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>获取资格/证书：</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.entercretname)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>产品质量： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdqualityname)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>试用情况： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdtestname)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>管理： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdmanagename)+'</td></tr>';
  
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>生产/检测设备： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdcheckname)+'</td></tr>';
  
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>服务质量： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdservicename)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>价格： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(tradesurvey.prdpricename)+'</td></tr>';
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%" colspan=3>调查人： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(creator.tranusername)+'</td></tr>';
  

  html += '<tr class="infotr" height = 200px ><td class="infoat" align="center" width="10%"  colspan=3>评价结论： </td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+alms.GetItemData(checker.tododesc)+'</td></tr>';
  (JSON.stringify(checker) == "{}")?html += '<tr class="infotr" height = 200px ><td class="infoat" align="center" width="10%"  colspan=3>评价人员签字：</td>' +
		  '<td class="infoc infoleft" align="left"  width="40%" colspan=5></td></tr>': 
  html += '<tr class="infotr" height = 200px ><td class="infoat" align="center" width="10%"  colspan=3>评价人员签字：</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=5>'+'<image height="40px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />'+'</td></tr>';
  (JSON.stringify(judger) == "{}")?
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >评价日期</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=2></td>'+
  '<td class="infoat" align="center"  " width="10%" >评价负责人</td>' +
  '<td class="infoc infoleft" align="left" width="40%" colspan=3></td></tr>':
  html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%"  >评价日期</td>' +
  '<td class="infoc infoleft" align="left"  width="40%" colspan=2>'+alms.GetItemDateData(checker.trandate) +'</td>'+
  '<td class="infoat" align="center"  " width="10%" >评价负责人</td>' +
  '<td class="infoc infoleft" align="left" width="40%" colspan=3>'+'<image height="40px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" />'+'</td></tr>';
  

// '<td class="infoc infoleft" align="left" width="15%">'
// +alms.GetItemData(tradesurvey.tranid)+ '</td>' +
// '<td class="infoat" align="center" width="10%">供应商编号</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.tradeid)+'</td>'+
// '<td class="infoat" align="center" width="10%">供应商名称</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.tradename)+'</td>'+
// '<td class="infoat" align="center" width="10%">联系人</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.linkman)+'</td></tr>';
//  
// html += '<tr class="infotr"><td class="infoat" align="center"
// width="10%">联系电话</td>' +
// '<td class="infoc infoleft" align="left" width="15%">'
// +alms.GetItemData(tradesurvey.linktele)+ '</td>' +
// '<td class="infoat" align="center" width="10%">邮政编码</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.linkpost)+'</td>'+
// '<td class="infoat" align="center" width="10%">产品名称</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdname)+'</td>'+
// '<td class="infoat" align="center" width="10%">企业规模</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.enterscalename)+'</td></tr>';
//  
// html += '<tr class="infotr"><td class="infoat" align="center"
// width="10%">企业知名度</td>' +
// '<td class="infoc infoleft" align="left" width="15%">'
// +alms.GetItemData(tradesurvey.enterpopularname)+ '</td>' +
// '<td class="infoat" align="center" width="10%">资格证书</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.entercretname)+'</td>'+
// '<td class="infoat" align="center" width="10%">产品质量</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdqualityname)+'</td>'+
// '<td class="infoat" align="center" width="10%">试用情况</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdtestname)+'</td></tr>';
//  
// html += '<tr class="infotr"><td class="infoat" align="center"
// width="10%">管理</td>' +
// '<td class="infoc infoleft" align="left" width="15%">'
// +alms.GetItemData(tradesurvey.prdmanagename)+ '</td>' +
// '<td class="infoat" align="center" width="10%">生产检查设备</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdcheckname)+'</td>'+
// '<td class="infoat" align="center" width="10%">服务质量</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdservicename)+'</td>'+
// '<td class="infoat" align="center" width="10%">价格</td>' +
// '<td class="infoc infoleft" align="left"
// width="15%">'+alms.GetItemData(tradesurvey.prdpricename)+'</td></tr>';
//  
// html += '<tr class="infotr"><td class="infoat" align="center"
// width="10%">单位地址</td>' +
// '<td class="infoc infoleft" colspan=7 align="left"
// width="90%">'+alms.GetItemData(tradesurvey.linkaddress)+'</td></tr>';
  html += '</table>';
  
//  html += '<center>注：列入合格供应商名单第__________号 </center>'
  return html
  
};

alms.ShowBuyApplyHtml = function(record){
  var creator= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=DevApply&htodo.flownode=create');
  var checkor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=DevApply&htodo.flownode=check');
  var auditor = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=DevApply&htodo.flownode=audit');
  var devbuyapply = tools.JsonGet('DevGetBuyApply.do?ba.tranid='+record.tranid);
  var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>仪器设备购置申请单</b></td></tr>';
  html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-5-4</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">仪器名称</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.devname) +'</td></tr>';
  
  html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">生产单位</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.factoryname) +'</td></tr>';

  html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">规格型号</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.devstandard) +'</td></tr>';
  html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">技术指标</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.devrange) +'</td></tr>';
  html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">数    量</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(devbuyapply.devcount) +'</td></tr>';
  html + '</td></tr>';

  
  html += '<tr class="infotr"  ><td  class="infoat"  rowspan="3" align="center"  width="10%">申请理由</td>' +
  '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(devbuyapply.applyreason) +'</td></tr>';
  html + '</td></tr>';
  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >申请部门</td>' +
  '<td class="infoc infoleft" colspan=3  align="center" width="90%">'+alms.GetItemData(creator.trandeptname)+'</td></tr>';
  
  (JSON.stringify(creator) == "{}")?html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
    '<td class="infoc infoleft" width="30%"   align="center" ><td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(creator.trandate)+'</td></tr>':
  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
  '<td class="infoc infoleft" width="30%"   align="center" >'+'<image height="40px" src="images/sign/' +alms.GetItemData(creator.tranuser) + '.jpg" />' +'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(creator.trandate)+'</td></tr>';
  
  html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">评审意 见</td>' +
  '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(checkor.tododesc) +'</td></tr>';
  html + '</td></tr>';
  (JSON.stringify(checkor) == "{}")?  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
  '<td class="infoc infoleft" width="30%"   align="center" >'+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(checkor.trandate)+'</td></tr>':html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
  '<td class="infoc infoleft" width="30%"   align="center" >'+'<image height="40px" src="images/sign/' +alms.GetItemData(checkor.tranuser) + '.jpg" />'+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(checkor.trandate)+'</td></tr>';
  
  html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">批准意见</td>' +
  '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(auditor.tododesc) +'</td></tr>';
  html + '</td></tr>';

  (JSON.stringify(auditor) == "{}")?  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
    '<td class="infoc infoleft" width="30%"   align="center" >'+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(auditor.trandate)+'</td></tr>': html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >负责人</td>' +
    '<td class="infoc infoleft" width="30%"   align="center" >'+'<image height="40px" src="images/sign/' +alms.GetItemData(auditor.tranuser) + '.jpg" />'+'<td class="infoat" align="center" width="10%">日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(auditor.trandate)+'</td></tr>';

  html += '</table>';
  
  
  return html
  
};




alms.SetUserCombo = function(type,deptid,id){
  var typedata = tools.GetTypeList(type);
  var items = tools.JsonGet('BasGetUserByDept.do?deptid=' + deptid);
  if(items && items.data){
    Ext.each(items.data,function(item,index){
        typedata.push({id:item.userid,name:item.username});
    });
    alms.SetStoreToCombo(type, id, typedata);
  }
};

alms.SetCityCombo = function(type,provinceid,id){
  var typedata = tools.GetTypeList(type);
  var items = tools.JsonGet('BasGetBasCityByProv.do?bct.provinceid=' + provinceid);
  if(items && items.data){
    Ext.each(items.data,function(item,index){
        typedata.push({id:item.cityid,name:item.cityname});
    });
    alms.SetStoreToCombo(type, id, typedata);
  }
};

alms.SetAreaCombo = function(type,cityid,id){
  var typedata = tools.GetTypeList(type);
  var items = tools.JsonGet('BasGetBasAreaByCity.do?ba.cityid=' + cityid);
  if(items && items.data){
    Ext.each(items.data,function(item,index){
        typedata.push({id:item.areaid,name:item.areaname});
    });
    alms.SetStoreToCombo(type, id, typedata);
  }
};

alms.SetParameterCombo = function (type, sampleid, id,standtype1,standtype2,standtype3,standtype4,standtype5) {
  var typedata = tools.GetTypeList(type);
  
  var items = tools.JsonGet('LabGetBasSampleParamForCombo.do?bmp.sampleid=' + sampleid+'&bmp.standtype1='+standtype1+'&bmp.standtype2='+standtype2+'&bmp.standtype3='+standtype3+'&bmp.standtype4='+standtype4+'&bmp.standtype5='+standtype5);
  if (items && items.data)
    Ext.each(items.data, function (item, index) { 
      typedata.push({ id: item.parameterid, name: item.parametername});
    });
   
  alms.SetStoreToCombo(type, id, typedata);
};


alms.SetDevCombo = function (type, labid, id) {
  var typedata = tools.GetTypeList(type);
  var items = tools.JsonGet('DevGetListBasDevByLab.do?bd.labid=' + labid);
  if (items && items.data)
    Ext.each(items.data, function (item, index) {   
      typedata.push({ id: item.devid, name: item.devname });
    });
  
  alms.SetStoreToCombo(type, id, typedata);
};

alms.SetStoreToCombo = function (type, id, typedata) {
  var store = new Ext.data.JsonStore({
    fields: ['id', 'name'],
    data: typedata
  });
  
  Ext.getCmp(id).bindStore(store); 
  
  if (type)
    tools.SetValue(id, type);
};


alms.ShowGetHtml = function(record){
  var busget = tools.JsonGet('LabGetBusGet.do?bg.tranid='+record.tranid);
  var busgetdetail = tools.JsonGet('LabGetListBusGetDetail.do?bgd.tranid='+record.tranid).data;
  var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
  html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>抽样单</b></td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">抽样单号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busget.tranid)+ '</td>' +
    '<td class="infoat" align="center" width="10%">受检企业</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.testedname)+'</td>'+
    '<td class="infoat" align="center" width="10%">法定代表人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.enterlegal)+'</td>'+
    '<td class="infoat" align="center" width="10%">联系电话</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.testedtele)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">企业性质</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busget.testedtypename)+ '</td>' +
    '<td class="infoat" align="center" width="10%">企业规模</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.testedscalename)+'</td>'+
    '<td class="infoat" align="center" width="10%">邮政编码</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.testedpost)+'</td>'+
    '<td class="infoat" align="center" width="10%">主管单位</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busget.testedmanager)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%">通讯地址</td>' +
    '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(busget.testedaddr)+ '</td></tr>'
  
  html += '</table>';
  
  if(busgetdetail.length > 0){
    for(var i = 0; i < busgetdetail.length; i++){
      html += '<br/>';
      
      html +='<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">样品编号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].sampleid)+ '</td>' +
        '<td class="infoat" align="center" width="10%">样品名称</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplename)+'</td>'+
        '<td class="infoat" align="center" width="10%">商标</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].trademark)+'</td>'+
        '<td class="infoat" align="center" width="10%">任务来源</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].tasksourcename)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">生产厂家</td>' +
        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].factname)+ '</td>' +
        '<td class="infoat" align="center" width="10%">生产批号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].prdcode)+'</td>'+
        '<td class="infoat" align="center" width="10%">厂家电话</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].facttele)+'</td>'+
        '<td class="infoat" align="center" width="10%">厂家邮编</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].factpost)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">规格型号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].samplestand)+ '</td>' +
        '<td class="infoat" align="center" width="10%">登记证号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].regcode)+'</td>'+
        '<td class="infoat" align="center" width="10%">准产证号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].allowcode)+'</td>'+
        '<td class="infoat" align="center" width="10%">标准号</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].prdstandard)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">样本大小</td>' +
        '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(busgetdetail[i].samplesize)+ '</td>' +
        '<td class="infoat" align="center" width="10%">样本基数</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplebase)+'</td>'+
        '<td class="infoat" align="center" width="10%">抽样方式</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].getmethod)+'</td>'+
        '<td class="infoat" align="center" width="10%">批量</td>' +
        '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(busgetdetail[i].samplecount)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">厂家地址</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(busgetdetail[i].factaddr)+ '</td></tr>'
    
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">抽样地点</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +alms.GetItemData(busgetdetail[i].getaddr)+ '</td></tr>'
      
      html += '</table>';
      
    };
  }
  
  return html;
}

alms.ShowBusGet = function(id){
  
  var busget = tools.JsonGet('LabGetBusGet.do?bg.tranid='+id);
  
  var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
  html += '<br/>';
  html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:20px; font-weight:bold; height:60px;"><p>农业部农产品质量安全监督检测测试中心（南京）<br/>江苏省农产品质量检测测试中心<br/>例行监测抽样单</p></td></tr>';
  html += '</table>';
  
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="5%" style="font-size:14px;" width="90%">';
  html += '<tr class="infotr"><td class="infoat" align="center" width="15%" style="height:30px;">受检单位</td>' +
  '<td class="infoc infoleft" align="center" colspan=2  width="30%" style="height:30px;">'+busget.testedname+ '</td>' +
  '<td class="infoat" align="center" width="15%" style="height:30px;">通讯地址</td>' +
  '<td class="infoc infoleft" align="center" colspan=3 width="40%" style="height:30px;">'+busget.enteraddr+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="15%" style="height:30px;">法定代表人<br/>或负责人</td>' +
  '<td class="infoc infoleft" align="center"colspan=2 width="15%" style="height:30px;">' +busget.enterlegal+ '</td>' +
  '<td class="infoat" align="center" width="15%" style="height:30px;">邮政编码</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+busget.enterpost+'</td>'+
  '<td class="infoat" align="center" width="15%" style="height:20px;">联系电话</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:20px;">'+busget.entertele+'</td></tr>';
  
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="15%" style="height:30px;">任务来源</td>' +
  '<td class="infoc infoleft" align="center"colspan=2 width="15%" style="height:30px;">' +busget.tasksource+ '</td>' +
  '<td class="infoat" align="center" width="15%" style="height:30px;">执行标准</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+busget.prdstand+'</td>'+
  '<td class="infoat" align="center" width="15%" style="height:30px;">抽样时间</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(busget.getdate)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="14%" style="height:30px;">样品编号</td>' +
  '<td class="infoat" align="center" width="14%" style="height:30px;">样品名称</td>' +
  '<td class="infoat" align="center" width="14%" style="height:30px;">抽样地点</td>' +
  '<td class="infoat" align="center" width="14%" style="height:30px;">样品来源</td>' +
  '<td class="infoat" align="center" width="14%" style="height:30px;">样品量(n/N)</td>' +
  '<td class="infoat" align="center" width="14%" style="height:30px;">样品状态</td>' +
  '<td class="infoat" align="center" width="16%" style="height:30px;">备注</td>' ;
  
  var busgetdetails = tools.JsonGet('LabGetListBusGetDetail.do?bgd.tranid='+id).data;
  if(busgetdetails.length == 0){
    for(var i = 0; i <10; i++){
      html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="14%" style="height:10px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
      '<td class="infoc infoleft" align="center" width="16%" style="height:30px;">' +''+ '</td>';
    }
  }else{
    if(busgetdetails.length > 10){
      for(var i = 0; i <busgetdetails.length; i++){
      var detail = busgetdetails[i];
      html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="14%" style="height:10px;">' +detail.sampleid+ '</td>'+
        '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplename+ '</td>'+
        '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.getaddr+ '</td>'+
        '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplesource+ '</td>'+
        '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplecount+ '</td>'+
        '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplestatus+ '</td>'+
        '<td class="infoc infoleft" align="center" width="16%" style="height:30px;">' +detail.remark+ '</td>';
      }
    }else {
  for(var i = 0; i <busgetdetails.length; i++){
    var detail = busgetdetails[i];
    html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="14%" style="height:10px;">' +detail.sampleid+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplename+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.getaddr+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplesource+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplecount+ '</td>'+
      '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +detail.samplestatus+ '</td>'+
      '<td class="infoc infoleft" align="center" width="16%" style="height:30px;">' +detail.remark+ '</td>';
                          
  }
  for(var i = 0; i <10-busgetdetails.length; i++){
    html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="14%" style="height:10px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="14%" style="height:30px;">' +''+ '</td>'+
    '<td class="infoc infoleft" align="center" width="16%" style="height:30px;">' +''+ '</td>';
    }  
    }
  }
  html += '<tr class="infotr"><td class="infoc infoleft" align="left" colspan=3 rowspan = 5 height:30px; style="font-size:15px; margin:20 40 20 40;">' +
  "<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;全部抽样过程均在我们的陪同下完成,抽样方法正确,样品真实可靠。<br/><br/><br/><br/><br/><br/><br/><br/>被检单位  (章):<br/> <br/><br/>陪同代表(签字):<br/><br/><br/>日期:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日 " +'</td>'+
  '<td class="infoc infoleft" align="left" colspan = 4 rowspan = 5 width="15%" style="font-size:15px; margin:20 40 20 40;">' +
  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;按有关抽样标准和本次检验实施细则的要求完成了全部抽样工作。严守质检纪律，保证样品具代表性、真实性和公正性，对抽样单填写和样品确认无误。<br/><br/><br/><br/><br/>抽样单位(章):<image width=\"190px\" height=\"190px\" transcolor=\"#FFFFFF\" src=\"images/sign/1.png\"style=\"top:150px;left:100px; position:relative; z-index:1;\" /><br/><br/><br/>抽样人(签字):<br/><br/><br/>日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;日 "+ '</td>';
  html += '</table>';
  html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说明：1. 此单一式三联，第一联质检机构留存，第二联随样品，第三联被检单位保存。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 不详之处请用“-”线填充。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检验机构地址:江苏省南京市草场门大街江苏农业检测大楼<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮政编码：210036&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话:025-86229784'
  return html;
}

alms.ShowTaskQueryHtml = function(record){
  var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+record.samplecode);
  var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+record.taskid);
  var bustasktest = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid).data;
  var bustasksample = tools.JsonGet('LabGetListBusTaskForAcc.do?bts.taskid='+record.taskid).data;
  var busgetdetails = tools.JsonGet('LabGetListBusGetDetailByTask.do?bgd.taskid='+record.taskid).data;
  var teststandards = '';
  var taskid = '';
  var temp = new Array();
  
  for(var i = 0; i < bustasktest.length; i++){
    if(temp.indexOf(bustasktest[i].teststandardname) == -1){
      temp.push(bustasktest[i].teststandardname);
      if (!Ext.isEmpty(teststandards)){
        teststandards += ',';
      }
      teststandards += bustasktest[i].teststandardname;
    }
  }
  
  var html = '';
  // 判断是单样品还是多样品
  if(bustask.samplecode == '多样品样品编号-A'||bustask.samplecode == '多样品样品编号-B'){
    var page;
    if(busgetdetails.length%15 == 0){// 一页有15条样品记录
      page = busgetdetails.length/15;
    }else{
      page = parseInt(busgetdetails.length/15)+1;
    }

    // 获取检测项目
    var parameternames = [];
    for(var i = 0; i < bustasktest.length; i++){ 
      if (parameternames.indexOf(bustasktest[i].parametername) == -1){
        parameternames.push(bustasktest[i].parametername); 
      }
    }
    
    var html1='';
    var html2='';
    
    // 检测参数小于15个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html1 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html1 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农业部农产品质量安全监督检测测试中心（南京）<br/>江&nbsp;&nbsp;苏&nbsp;&nbsp;省&nbsp;&nbsp;农&nbsp;&nbsp;产&nbsp;&nbsp;品&nbsp;&nbsp;质&nbsp;&nbsp;量&nbsp;&nbsp;检&nbsp;&nbsp;测&nbsp;&nbsp;测&nbsp;&nbsp;试&nbsp;&nbsp;中&nbsp;&nbsp;心<br/>检&nbsp;&nbsp;验&nbsp;&nbsp;任&nbsp;&nbsp;务&nbsp;&nbsp;单</td></tr>';
      html1 += '</table>';
      
      html1 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      if(parameternames.length>0){
        if(parameternames.length>14){
          for(var i = 0; i < 14; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
          }
        }else{
          for(var i = 0; i < parameternames.length; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
          }
        }
      }
      
      if(parameternames.length <= 14){
        for(var i = parameternames.length; i <14; i++){
          html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
        }
      }
      
      html1 += '</tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录,n为页数
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }

      for(var i = (n*15); i <count; i++){
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+busgetdetails[i].samplecode).data;
        
        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>0){
          if(submitvalues.length>14){
            for(var j=0;j<14;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }else{
            for(var j=0;j<submitvalues.length;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }
        }
        
        if(submitvalues.length <= 14){
          for(var j=submitvalues.length;j<14;j++){
            html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html1 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html1 += '</table>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
    }
  
    // 检测参数大于14个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html2 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html2 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农业部农产品质量安全监督检测测试中心（南京）<br/>江&nbsp;&nbsp;苏&nbsp;&nbsp;省&nbsp;&nbsp;农&nbsp;&nbsp;产&nbsp;&nbsp;品&nbsp;&nbsp;质&nbsp;&nbsp;量&nbsp;&nbsp;检&nbsp;&nbsp;测&nbsp;&nbsp;测&nbsp;&nbsp;试&nbsp;&nbsp;中&nbsp;&nbsp;心<br/>检&nbsp;&nbsp;验&nbsp;&nbsp;任&nbsp;&nbsp;务&nbsp;&nbsp;单</td></tr>';
      html2 += '</table>';
      
      html2 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      if(parameternames.length>14){
        for(var i = 14; i < parameternames.length; i++){
          html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
        }
      }
      
      for(var i = parameternames.length; i <28; i++){
        html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
      }
      
      html2 += '</tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }
      
      for(var i = (n*15); i <count; i++){
        
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+busgetdetails[i].samplecode).data;
        
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>14){
          for(var j=14;j<submitvalues.length;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
          }
          
          for(var j=submitvalues.length-14;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }else{
          for(var j=0;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html2 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html2 += '</table>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
    }
    
    if(parameternames.length>14){
      
      html = html1+html2;
    }else{
      html = html1;
    }
  }else{
    var html1 = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html1 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">试验信息综合查询</td></tr>';
      html1 += '</table>';
      html1 += '<table cellspacing="0" cellpadding="0"  border="0" align="center" height="20%" style="font-size:12px;" width="90%">';
      html1 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="90%" style="font-size:14px;">'+bustask.labname+'检测情况</td></tr>';
      html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务单编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(taskid)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">样品编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplecode)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplename)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">规格型号</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestand)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">检测性质</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">样品状态</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">承检室</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">承检室负责人</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td></tr>';
      
      
      html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务下达人</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(bustask.sendusername)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">下达日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.senddate)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">要求完成日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">检测依据</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.teststandardname)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">领样人</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(bustask.takeusername)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">领样数量</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.takenumber)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">领样时间</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.takedate)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">发样人</td>' +
      '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(bustask.sendsampleuser)+'</td></tr>';
      
      // 签名后显示签名信息
      if(bustask.takesign != ''){
        html1 += '<tr class="infotr"><td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">领样人签字</td>' +
        '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.takesign + '" width="50%" /></td>' +
        '<td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">发样人签字</td>' +
        '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.sendsign + '" width="50%"  /></td></tr>';
      }  
      
      html1 += '</table>';
      
      if(bustasktest.length > 0){
    // html += '<br/>';
        html1 += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="15%" style="font-size:12px;" width="90%">';
        html1 += '<tr class="infotr" ><td class="infoat" colspan=10 align="center" width="100%" style="font-size:14px;">检测项目</td></tr>';
        html1 += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="20%">检测参数</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">单位</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">指标</td>' +
        '<td class="infoat" align="center" colspan="4" width="40%">检测方法</td>' +
        '<td class="infoat" align="center" colspan="2" width="20%">检测结果</td></tr>';
    
        for(var i = 0; i < bustasktest.length; i++){
          html1 += '<tr class="infotr">' +
          '<td class="infoc infoleft" colspan="2" align="center" width="20%">' +alms.GetItemData(bustasktest[i].parametername)+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(bustasktest[i].paramunit)+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(bustasktest[i].testjudge)+alms.GetItemData(bustasktest[i].standvalue)+'</td>'+
          '<td class="infoc infoleft" colspan="4" align="center" width="40%">'+alms.GetItemData(bustasktest[i].teststandardname)+'</td>'+
          '<td class="infoc infoleft" colspan="2" align="center" width="20%">'+alms.GetItemData(bustasktest[i].submitvalue)+'</td></tr>';
        }
        
        html1 += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
        '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;"></td></tr>';
        
        html1 += '</table>';
      }
    
      if(bustasksample.length > 0){
    // html += '<br/>';
        html1 += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
        html1 += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%" style="font-size:14px;">检测项目分配</td></tr>';
        html1 += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="25%">检测员</td>' +
        '<td class="infoat" align="center" colspan="2" width="25%">开始试验</td>' +
        '<td class="infoat" align="center" colspan="2" width="25%">结束试验</td>' +
        '<td class="infoat" align="center" colspan="2" width="25%">是否判定</td></tr>';
        
        
        for(var i = 0; i < bustasksample.length; i++){
          html1 += '<tr class="infotr">' +
          '<td class="infoc infoleft" colspan="2" align="center" width="25%">' +alms.GetItemData(bustasksample[i].testusername)+ '</td>' +
          '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemDateData(bustasksample[i].begintestdate)+'</td>'+
          '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemDateData(bustasksample[i].endtestdate)+'</td>'+
          '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemData(bustasksample[i].isjudge == false?'否':'是')+'</td></tr>';
        }
        html1 += '</table>';
      }
      
      if(bustasksample.length > 0){
        html1 += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
        for(var i = 0; i < bustasksample.length; i++){
          var devuse = tools.JsonGet('DevGetListDevUseBySampletran.do?du.sampletran='+bustasksample[i].sampletran).data;
          if(devuse){
            if(devuse.length>0){
              for(var j = 0; j < devuse.length; j++){
                html1 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">设备使用情况</td></tr>';
                html1 += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">设备名称</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(devuse[j].devname)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">开机时间</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(devuse[j].usebegin)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">关机时间</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(devuse[j].useend)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].samplename)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">检测项目</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(devuse[j].parametername)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">使用前状态</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].beforestatusname)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">使用后状态</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].afterstatusname)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">设备编号</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].devid)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">使用人</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(devuse[j].tranusername)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">使用功能</td>' +
                '<td class="infoc infoleft" align="left" colspan="5" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].usefunction)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
                '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(devuse[j].remark)+'</td></tr>';
              }
            }
          }
        }
        html1 += '</table>';
      }
      
      if(bustasksample.length > 0){
        html1 += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
        for(var i = 0; i < bustasksample.length; i++){
          var stduse = tools.JsonGet('StdGetListStdUseBySampletran.do?su.sampletran='+bustasksample[i].sampletran).data;
          if(stduse){
            if(stduse.length>0){
              for(var j = 0; j < stduse.length; j++){
                html1 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">耗材使用情况</td></tr>';
                html1 += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">物品名称</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(stduse[j].stdname)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">规格</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(stduse[j].stdsize)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">剩余数量</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(stduse[j].stdquanity)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">使用时间</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(stduse[j].indate)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">有效期(月)</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(stduse[j].validmonth)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">检测样品</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(stduse[j].samplename)+'</td>'+
                '<td class="infoat" align="center" width="10%" style="height:30px;">检测项目</td>' +
                '<td class="infoc infoleft" align="left" colspan="3" style="height:30px;">'+alms.GetItemData(stduse[j].parametername)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">使用人</td>' +
                '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(stduse[j].tranusername)+ '</td>' +
                '<td class="infoat" align="center" width="10%" style="height:30px;">使用情况</td>' +
                '<td class="infoc infoleft" align="left" colspan="5" width="15%" style="height:30px;">'+alms.GetItemData(stduse[j].usesituation)+'</td></tr>';
                
                html1 += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
                '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(stduse[j].remark)+'</td></tr>';
              }
            }
          }
        }
        html1 += '</table>';
      }
      
      var taskresults = tools.JsonGet('LabGetListBusTaskResult.do?btr.taskid=' + bustask.taskid).data;

      if(taskresults){
        if(taskresults.length>0){
          html1 += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
          html1 += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">检测过程性数据</td></tr>';
    
          html1 += '<tr class="infotr"><td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">样品名称</td>' +
            '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetTranData(bustask.samplename)+'</td>' +
            '<td class="infoat" align="center" colspan="4" width="20%" style="height:30px;">样品编号</td>'+
            '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetTranData(bustask.samplecode)+'</td></tr>';
          
          html1 += '<tr class="infotr"><td class="infoat" colspan="3" align="center" width="5%" style="height:30px;">序号 </td>' +
            '<td class="infoat" align="center" colspan="5" width="30%" style="height:30px;">检测项目</td>'+
            '<td class="infoat" align="center" colspan="2" width="15%" style="height:30px;">单位</td>'+
            '<td class="infoat" align="center" colspan="4" width="15%" style="height:30px;">指标</td>'+
            '<td class="infoat" align="center" colspan="3" width="15%" style="height:30px;">检测数据</td>'+
            '<td class="infoat" align="center" colspan="3" width="15%" style="height:30px;">单项评价</td></tr>';
    
          if(taskresults && taskresults.length>0){
            for(var i= 0;i<taskresults.length;i++){
              html1 += '<tr class="infotr"><td class="infoat" colspan="3" align="center" width="5%">'+(i+1)+'</td>' +
              '<td class="infoc infoleft" align="center" colspan="5" width="30%">'+alms.GetTranData(taskresults[i].parametername)+'</td>'+
              '<td class="infoc infoleft" align="center" colspan="2" width="15%">'+alms.GetTranData(taskresults[i].paramunit)+'</td>'+
              '<td class="infoc infoleft" align="center" colspan="4" width="15%">'+alms.GetTranData(taskresults[i].standvalue)+'</td>'+
              '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData(taskresults[i].submitvalue)+'</td>'+
              '<td class="infoc infoleft" align="center" colspan="3" width="15%">'+alms.GetTranData(taskresults[i].judgestatusname)+'</td></tr>';
            }
          }
          
          html1 += '</table>';
        }
      }
      
      var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + bustask.samplecode);
      if (records && records.data) {
        var nowdate = new Date();
        var i = 0, j = 0;
        var record;
        var rowspan = 0, colspan = 0;
        var height = 28, width = 36, nowheight = 0, nowwidth = 0;
        var form = '';
        var celltext = '';
        var style = '';
        var align = '';
        
        for (var n = 0; n < records.data.length; n++) {
          var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + tools.GetEncode(tools.GetEncode(records.data[n].reportid)));
    
          if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
            for(var page = 0; page < item.details.length; page++){
              var nowdetails;
              var fl1;
              var fl2;
              if(page == 0){
                fl1 = 2*item.form.formlength/3;
    
                nowdetails = item.details[page].datas;
                if (page == item.details.length - 1){
                  form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
                } else {
                  form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
                }
                form += '<tr>';
                for (i = 0; i < item.form.formwidth; i++) {
                  form += '<td width="' + width + 'px"; height="0px" ></td>';
                }
                form += '</tr>';
                for (i = 1; i <= fl1; i++) {
                  form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
                  for (j = 0; j < nowdetails.length; j++) {
                    record = nowdetails[j];
                    if (record.beginrow == i) {
                      rowspan = record.endrow - record.beginrow + 1;
                      nowheight = height * rowspan;
                      colspan = record.endcolumn - record.begincolumn + 1;
                      nowwidth = width * colspan;
                      celltext = '';
                      switch (record.valuesource) {
                        case '02' :
                          celltext = record.celltext;
                          break;
                        case '03' :
                          celltext = '';
                          break;
                        case '01' :
                          celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                          break;
                        case '04' :
                          celltext = record.celltext;
                          break;
                        default :
                          break;
                      }
                      celltext = record.prefixtext + celltext + record.postfixtext;
                      style = '';
                      align = '';
                      switch (record.aligntype) {
                        case '1' :
                          align = ' align="left" ';
                          style += 'padding-left:3px;';
                          break;
                        case '2' :
                          align = ' align="center" ';
                          break;
                        case '3' :
                          align = ' align="right" ';
                          style += 'padding-right:3px;';
                          break;
                        default :
                          break;
                      }
    
                      if (record.isborder > 0)
                        style += 'border: solid ' + record.isborder
                            + 'px Black;';
    
                      if (record.isline && (record.isborder <= 0))
                        style += 'border-bottom: solid 1px Black;';
    
                      if (record.isbold)
                        style += 'font-weight:bold;';
    
                      style += 'font-size:' + record.fontsize + 'px;';
    
                      cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                          + ' style="' + style + '" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">' + 
                          (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                          
                      if (record.valuesource == '04') {
                        if (record.fieldcode == '{actreportid}') {
                          if (!Ext.isEmpty(celltext)) 
                            form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                              + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                           else
                             form += cellhtml;
                        }
                        else if (Ext.isEmpty(record.classsource)) {
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                        }
                        else if (!Ext.isEmpty(celltext)) {
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                        }
                        else
                          form += cellhtml;
                      }
                      else {
                        form += cellhtml;
                      }
                    }
                  }
                  form += '</tr>';
                }
              }else {
                fl2 = item.form.formlength/3
                nowdetails = item.details[page].datas;
                if (page == item.details.length - 1){
                  form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
                } else {
                  form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; page-break-after: always; border-collapse: collapse;">';
                }
                form += '<tr>';
                for (i = 0; i < item.form.formwidth; i++) {
                  form += '<td width="' + width + 'px"; height="0px" ></td>';
                }
                form += '</tr>';
                for (i = fl1+1; i <= fl1+fl2; i++) {
                  form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
                  for (j = 0; j < nowdetails.length; j++) {
                    record = nowdetails[j];
                    if (record.beginrow == i) {
                      rowspan = record.endrow - record.beginrow + 1;
                      nowheight = height * rowspan;
                      colspan = record.endcolumn - record.begincolumn + 1;
                      nowwidth = width * colspan;
                      celltext = '';
                      switch (record.valuesource) {
                        case '02' :
                          celltext = record.celltext;
                          break;
                        case '03' :
                          celltext = '';
                          break;
                        case '01' :
                          celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                          break;
                        case '04' :
                          celltext = record.celltext;
                          break;
                        default :
                          break;
                      }
                      celltext = record.prefixtext + celltext + record.postfixtext;
                      style = '';
                      align = '';
                      switch (record.aligntype) {
                        case '1' :
                          align = ' align="left" ';
                          style += 'padding-left:3px;';
                          break;
                        case '2' :
                          align = ' align="center" ';
                          break;
                        case '3' :
                          align = ' align="right" ';
                          style += 'padding-right:3px;';
                          break;
                        default :
                          break;
                      }
    
                      if (record.isborder > 0)
                        style += 'border: solid ' + record.isborder
                            + 'px Black;';
    
                      if (record.isline && (record.isborder <= 0))
                        style += 'border-bottom: solid 1px Black;';
    
                      if (record.isbold)
                        style += 'font-weight:bold;';
    
                      style += 'font-size:' + record.fontsize + 'px;';
    
                      cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                          + ' style="' + style + '" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">' + 
                          (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                          
                      if (record.valuesource == '04') {
                        if (record.fieldcode == '{actreportid}') {
                          if (!Ext.isEmpty(celltext)) 
                            form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                              + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                           else
                             form += cellhtml;
                        }
                        else if (Ext.isEmpty(record.classsource)) {
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                        }
                        else if (!Ext.isEmpty(celltext)) {
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                              + ' valign="middle">'
                              + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                        }
                        else
                          form += cellhtml;
                      }
                      else {
                        form += cellhtml;
                      }
                    }
                  }
                  form += '</tr>';
                }
                fl1 = fl1+fl2;
              }
              form += '</table>';
            }
            
          } else {
            tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '试验报告'));
            return '';
          }
        }
      }
      
      var html2 = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%; border-collapse: collapse;">' + 
      '<tr><td align="center">' + form.replace(/\<br \/\>/g, '') + '</td></tr></table>';
      
      html = html1+html2;
  }
  return html;
}

alms.ShowTaskHtml = function(record){
  
  var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+record.samplecode);
  var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+record.taskid);
  var bustasktest = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid).data;
  var bustasksample = tools.JsonGet('LabGetListBusTaskForAcc.do?bts.taskid='+record.taskid).data;
  var busgetdetails = tools.JsonGet('LabGetListBusGetDetailByTask.do?bgd.taskid='+record.taskid).data;
  var teststandards = '';
  var taskid = '';
  var temp = new Array();
  
  for(var i = 0; i < bustasktest.length; i++){
    if(temp.indexOf(bustasktest[i].teststandardname) == -1){
      temp.push(bustasktest[i].teststandardname);
      if (!Ext.isEmpty(teststandards)){
        teststandards += ',';
      }
      teststandards += bustasktest[i].teststandardname;
    }
  }
  
  // 判断是单样品还是多样品
  if(bustask.samplecode == '多样品样品编号-A'||bustask.samplecode == '多样品样品编号-B'){
    var html = '';
    var page;
    if(busgetdetails.length%15 == 0){// 一页有15条样品记录
      page = busgetdetails.length/15;
    }else{
      page = parseInt(busgetdetails.length/15)+1;
    }

    // 获取检测项目
    var parameternames = [];
    for(var i = 0; i < bustasktest.length; i++){ 
      if (parameternames.indexOf(bustasktest[i].parametername) == -1){
        parameternames.push(bustasktest[i].parametername); 
      }
    }
    
    var html1='';
    var html2='';
    
    // 检测参数小于15个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html1 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html1 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农业部农产品质量安全监督检测测试中心（南京）<br/>江&nbsp;&nbsp;苏&nbsp;&nbsp;省&nbsp;&nbsp;农&nbsp;&nbsp;产&nbsp;&nbsp;品&nbsp;&nbsp;质&nbsp;&nbsp;量&nbsp;&nbsp;检&nbsp;&nbsp;测&nbsp;&nbsp;测&nbsp;&nbsp;试&nbsp;&nbsp;中&nbsp;&nbsp;心<br/>检&nbsp;&nbsp;验&nbsp;&nbsp;任&nbsp;&nbsp;务&nbsp;&nbsp;单</td></tr>';
      html1 += '</table>';
      
      html1 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      if(parameternames.length>0){
        if(parameternames.length>14){
          for(var i = 0; i < 14; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
          }
        }else{
          for(var i = 0; i < parameternames.length; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
          }
        }
      }
      
      if(parameternames.length <= 14){
        for(var i = parameternames.length; i <14; i++){
          html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
        }
      }
      
      html1 += '</tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录,n为页数
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }

      for(var i = (n*15); i <count; i++){
        var samplecode = '';
        if(bustask.samplecode.indexOf('-A')>-1){
          samplecode = busgetdetails[i].samplecode+'-A';
        }else{
          samplecode = busgetdetails[i].samplecode+'-B';
        }
        
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+samplecode).data;

        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>0){
          if(submitvalues.length>14){
            for(var j=0;j<14;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }else{
            for(var j=0;j<submitvalues.length;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }
        }
        
        if(submitvalues.length <= 14){
          for(var j=submitvalues.length;j<14;j++){
            html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html1 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html1 += '</table>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
      html1 += '<br/>';
    }
  
    // 检测参数大于14个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html2 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html2 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农业部农产品质量安全监督检测测试中心（南京）<br/>江&nbsp;&nbsp;苏&nbsp;&nbsp;省&nbsp;&nbsp;农&nbsp;&nbsp;产&nbsp;&nbsp;品&nbsp;&nbsp;质&nbsp;&nbsp;量&nbsp;&nbsp;检&nbsp;&nbsp;测&nbsp;&nbsp;测&nbsp;&nbsp;试&nbsp;&nbsp;中&nbsp;&nbsp;心<br/>检&nbsp;&nbsp;验&nbsp;&nbsp;任&nbsp;&nbsp;务&nbsp;&nbsp;单</td></tr>';
      html2 += '</table>';
      
      html2 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      if(parameternames.length>14){
        for(var i = 14; i < parameternames.length; i++){
          html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
        }
      }
      
      for(var i = parameternames.length; i <28; i++){
        html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
      }
      
      html2 += '</tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }
      
      for(var i = (n*15); i <count; i++){
        
        var samplecode = '';
        if(bustask.samplecode.indexOf('-A')>-1){
          samplecode = busgetdetails[i].samplecode+'-A';
        }else{
          samplecode = busgetdetails[i].samplecode+'-B';
        }
        
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+ samplecode).data;
        
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>14){
          for(var j=14;j<submitvalues.length;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
          }
          
          for(var j=submitvalues.length-14;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }else{
          for(var j=0;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html2 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html2 += '</table>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
      html2 += '<br/>';
    }
    
    if(parameternames.length>14){
      
      html = html1+html2;
    }else{
      html = html1;
    }
    
  }else{
    var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">样品任务单</td></tr>';
    html += '</table>';
    
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务单编号</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.taskid)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">样品编号</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplecode)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplename)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">规格型号</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestand)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">检测性质</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">样品状态</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">承检室</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">承检室负责人</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td></tr>';
    
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务下达人</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.sendusername)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">下达日期</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.senddate)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">要求完成日期</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">检测依据</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.teststandardname)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">领样人</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.takeusername)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">领样数量</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.takenumber)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">领样时间</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.takedate)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">发样人</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.sendsampleuser)+'</td></tr>';
    
    // 签名后显示签名信息
    if(bustask.takesign != ''){
      html += '<tr class="infotr"><td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">领样人签字</td>' +
      '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.takesign + '" width="50%" /></td>' +
      '<td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">发样人签字</td>' +
      '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.sendsign + '" width="50%"  /></td></tr>';
    }  
    
    html += '</table>';
    
    if(bustasktest.length > 0){
// html += '<br/>';
      html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="15%" style="font-size:12px;" width="90%">';
      html += '<tr class="infotr" ><td class="infoat" colspan=10 align="center" width="100%" style="font-size:14px; height:30px;">检测项目</td></tr>';
      html += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="20%" style="height:30px;">检测参数</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:30px;">单位</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:30px;">指标</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%" style="height:30px;">检测限</td>' +
      '<td class="infoat" align="center" colspan="3" width="30%" style="height:30px;">检测方法</td>' +
      '<td class="infoat" align="center" colspan="2" width="20%" style="height:30px;">检测结果</td></tr>';

      for(var i = 0; i < bustasktest.length; i++){
        html += '<tr class="infotr">' +
        '<td class="infoc infoleft" colspan="2" align="center" width="20%" style="height:30px;">' +alms.GetItemData(bustasktest[i].parametername)+ '</td>' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustasktest[i].paramunit)+'</td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustasktest[i].testjudge)+alms.GetItemData(bustasktest[i].standvalue)+'</td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustasktest[i].deteclimit)+'</td>'+
        '<td class="infoc infoleft" colspan="3" align="center" width="30%" style="height:30px;">'+alms.GetItemData(bustasktest[i].teststandardname)+'</td>'+
        '<td class="infoc infoleft" colspan="2" align="center" width="20%" style="height:30px;">'+alms.GetItemData(bustasktest[i].submitvalue)+'</td></tr>';
      }
      
      html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
      '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;"></td></tr>';
      
      html += '</table>';
    }
    
    if(bustasksample.length > 0){
// html += '<br/>';
      html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
      html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%" style="font-size:14px; height:30px;">检测项目分配</td></tr>';
      html += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="25%" style="height:30px;">检测员</td>' +
      '<td class="infoat" align="center" colspan="2" width="25%" style="height:30px;">开始试验</td>' +
      '<td class="infoat" align="center" colspan="2" width="25%" style="height:30px;">结束试验</td>' +
      '<td class="infoat" align="center" colspan="2" width="25%" style="height:30px;">是否判定</td></tr>';
      
      
      for(var i = 0; i < bustasksample.length; i++){
        html += '<tr class="infotr">' +
        '<td class="infoc infoleft" colspan="2" align="center" width="25%" style="height:30px;">' +alms.GetItemData(bustasksample[i].testusername)+ '</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="25%" style="height:30px;">'+alms.GetItemDateData(bustasksample[i].begintestdate)+'</td>'+
        '<td class="infoc infoleft" colspan="2" align="center" width="25%" style="height:30px;">'+alms.GetItemDateData(bustasksample[i].endtestdate)+'</td>'+
        '<td class="infoc infoleft" colspan="2" align="center" width="25%" style="height:30px;">'+alms.GetItemData(bustasksample[i].isjudge == false?'否':'是')+'</td></tr>';
      }
      html += '</table>';
    }
  }
  
  return html;
}

alms.ShowSamUseInfo = function(record){
  var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+record.samplecode);
  var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+record.taskid);
  var bustasktest = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid).data;
  var bustasksample = tools.JsonGet('LabGetListBusTaskForAcc.do?bts.taskid='+record.taskid).data;
  var teststandards = '';
  var taskid = '';
  var temp = new Array();
  for(var i = 0; i < bustasktest.length; i++){
    if(temp.indexOf(bustasktest[i].teststandardname) == -1){
      temp.push(bustasktest[i].teststandardname);
      if (!Ext.isEmpty(teststandards)){
        teststandards += ',';
      }
      teststandards += bustasktest[i].teststandardname;
    }
  }
  
  // 判断是单样品还是多样品
  if(bustask.samplecode == '多样品样品编号-A'||bustask.samplecode == '多样品样品编号-B'){
    taskid = bustask.taskid;
  }else{
    taskid = busgetdetail.tranidcn;
  }
  
  var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">样品任务单</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务单编号</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(taskid)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">样品编号</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplecode)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplename)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">规格型号</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestand)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">检测性质</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">样品状态</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">承检室</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">承检室负责人</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td></tr>';
  
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">任务下达人</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.sendusername)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">下达日期</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.senddate)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">要求完成日期</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">检测依据</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.teststandardname)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">领样人</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(bustask.takeusername)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">领样数量</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.takenumber)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">领样时间</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(bustask.takedate)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">发样人</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(bustask.sendsampleuser)+'</td></tr>';
  
  // 签名后显示签名信息
  if(bustask.takesign != ''){
    html += '<tr class="infotr"><td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">领样人签字</td>' +
    '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.takesign + '" width="50%" /></td>' +
    '<td colspan="2" class="infoat" align="center" width="25%" style="height:20px;">发样人签字</td>' +
    '<td colspan="2" class="infocs" align="center"><img src="FileDownImage.do?file=' + bustask.sendsign + '" width="50%"  /></td></tr>';
  }  
  
  html += '</table>';
  
  if(bustasktest.length > 0){
// html += '<br/>';
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr" ><td class="infoat" colspan=10 align="center" width="100%" style="font-size:14px;">检测项目</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="20%">检测参数</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">单位</td>' +
    '<td class="infoat" align="center" colspan="1" width="10%">指标</td>' +
    '<td class="infoat" align="center" colspan="4" width="40%">检测方法</td>' +
    '<td class="infoat" align="center" colspan="2" width="20%">检测结果</td></tr>';

    for(var i = 0; i < bustasktest.length; i++){
      html += '<tr class="infotr">' +
      '<td class="infoc infoleft" colspan="2" align="center" width="20%">' +alms.GetItemData(bustasktest[i].parametername)+ '</td>' +
      '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(bustasktest[i].paramunit)+'</td>'+
      '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(bustasktest[i].testjudge)+alms.GetItemData(bustasktest[i].standvalue)+'</td>'+
      '<td class="infoc infoleft" colspan="4" align="center" width="40%">'+alms.GetItemData(bustasktest[i].teststandardname)+'</td>'+
      '<td class="infoc infoleft" colspan="2" align="center" width="20%">'+alms.GetItemData(bustasktest[i].submitvalue)+'</td></tr>';
    }
    
    html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
    '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;"></td></tr>';
    
    html += '</table>';
  }
  
  if(bustasksample.length > 0){
// html += '<br/>';
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%" style="font-size:14px;">检测项目分配</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" colspan="2" width="25%">检测员</td>' +
    '<td class="infoat" align="center" colspan="2" width="25%">开始试验</td>' +
    '<td class="infoat" align="center" colspan="2" width="25%">结束试验</td>' +
    '<td class="infoat" align="center" colspan="2" width="25%">是否判定</td></tr>';
    
    
    for(var i = 0; i < bustasksample.length; i++){
      html += '<tr class="infotr">' +
      '<td class="infoc infoleft" colspan="2" align="center" width="25%">' +alms.GetItemData(bustasksample[i].testusername)+ '</td>' +
      '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemDateData(bustasksample[i].begintestdate)+'</td>'+
      '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemDateData(bustasksample[i].endtestdate)+'</td>'+
      '<td class="infoc infoleft" colspan="2" align="center" width="25%">'+alms.GetItemData(bustasksample[i].isjudge == false?'否':'是')+'</td></tr>';
    }
    html += '</table>';
  }
  
  var sampdeal = tools.JsonGet('SampGetSampleDealByTaskID.do?sampdeal.taskid='+record.taskid);
  if(sampdeal && sampdeal.samplecode){
    html += '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">样品处置单</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">样品编号</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(sampdeal.samplecode)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(sampdeal.samplename)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">收样日期</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(sampdeal.collectdate)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">保存期限</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(sampdeal.storeend)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">处置日期</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemDateData(sampdeal.dealdate)+ '</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;">处置方式</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(sampdeal.dealway)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">检验状态</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(sampdeal.dealstatusname)+'</td>'+
    '<td class="infoat" align="center" width="10%" style="height:30px;">处置人</td>' +
    '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(sampdeal.dealusername)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">处置方法</td>' +
    '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(sampdeal.dealmethod)+'</td></tr>';
    
    html += '</table>';
  }
  
  return html;
}

alms.ShowBasConsign = function(formid,tranid,samplecode){

  var me = this, mep = me.mep;
  
  var form = '';
  
  var i = 0, j = 0;
  var record;
  var rowspan = 0, colspan = 0;
  var height = 28, width = 36, nowheight = 0, nowwidth = 0;   
  var celltext = '';
  var style = '';
  var align = '';
  var tablewidth = 0, tableheight = 0;
  
  var item = tools.JsonGet(tools.GetUrl('FormGetSetFrmGet.do?fg.formid='+ formid + '&fg.tranid=' + tranid+ '&fg.samplecode=' + samplecode));
  if (item && item.form && !Ext.isEmpty(item.form.formid)) {
    for (var page = 0; page < item.details.length; page++) {
      var nowdetails = item.details[page].datas;
      
      tablewidth = width * item.form.formwidth;
      tableheight = height * item.form.formlength + 1;
      
      if (page == item.details.length - 1)
        form += '<table align = "center" width="85%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
      else
        form += '<table align = "center" width="85%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';

      form += '<tr>';

      for (i = 0; i < item.form.formwidth; i++) {
        form += '<td width="' + width + 'px"; height="1px" ></td>';
      }
      
      form += '</tr>';

      for (i = 1; i <= item.form.formlength; i++) {
        form += '<tr><td width="' + width + 'px"; height="'
            + height + 'px"></td>';

        for (j = 0; j < nowdetails.length; j++) {
          record = nowdetails[j];
          nowheight = 0;
          nowwidth = 0;
          if (record.beginrow == i) {
            rowspan = record.endrow - record.beginrow + 1;
            nowheight = height * rowspan;

            colspan = record.endcolumn - record.begincolumn + 1;
            nowwidth = width * colspan;

            celltext = '';

            switch (record.valuesource) {
              case '01' :
                celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                break;

              default :
                celltext = record.celltext;
                break;
            }

            celltext = record.prefixtext + celltext + record.postfixtext;

            style = '';
            align = '';

            switch (record.aligntype) {
              case '1' :
                align = ' align="left" ';
                style += 'padding-left:3px;';
                break;

              case '2' :
                align = ' align="center" ';
                break;

              case '3' :
                align = ' align="right" ';
                style += 'padding-right:3px;';
                break;

              default :
                break;
            }

            if (record.isborder > 0)
              style += 'border: solid ' + record.isborder + 'px Black;';

            if (record.isline && (record.isborder <= 0))
              style += 'border-bottom: solid 1px Black;';

            if (record.isbold)
              style += 'font-weight:bold;';

            style += 'font-size:' + record.fontsize + 'px;';
            
            if (record.valuesource != '04') {
              form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' + ' style="' + style
              + '" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
              + ' valign="middle">' + celltext + '</td>';
            }else{
              form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
              + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
              + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
              + ' valign="middle">'
              + '<image width="'  + nowheight + 'px" height="' + nowheight + 'px" src="images/sign/1.jpg" /></td>';
            }
          }
        }

        form += '</tr>';
      }

      form += '</table>';
    }
  } else {
    tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '委托书'));
    return '';
  }
  
  me.disphtml = form;
  return form;
}

alms.ShowBusRecord = function(record){

   var item = tools.JsonGet('LabGetBusTaskSingleByTranID.do?btsg.tranid='+record.tranid).data;
   
   var attach = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid='+record.tranid).data;

   if(item && item.length>0){
     var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
     html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>原始记录表信息</b></td></tr>';
     for(var i=0;i<item.length;i++){
       html += '<br/>';
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">任务单号</td>' +
       '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(item[i].taskid)+ '</td>' +
       '<td class="infoat" align="center" width="10%">样品编号</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].samplecode)+'</td>'+
       '<td class="infoat" align="center" width="10%">样品名称</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].samplename)+'</td>'+
       '<td class="infoat" align="center" width="10%">规格型号</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].samplestand)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">样品状态</td>' +
       '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(item[i].samplestatus)+ '</td>' +
       '<td class="infoat" align="center" width="10%">检验性质</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].testpropname)+'</td>'+
       '<td class="infoat" align="center" width="10%">要求完成日期</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item[i].reqdate)+'</td>'+
       '<td class="infoat" align="center" width="10%">承检室</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].labname)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">承检室负责人</td>' +
       '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(item[i].labusername)+ '</td>' +
       '<td class="infoat" align="center" width="10%">开始检测时间</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item[i].begintestdate)+'</td>'+
       '<td class="infoat" align="center" width="10%">温度</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].testtemp)+'</td>'+
       '<td class="infoat" align="center" width="10%">湿度</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].testhum)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">领样人</td>' +
       '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(item[i].getusername)+ '</td>' +
       '<td class="infoat" align="center" width="10%">领样数量</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].getcount)+'</td>'+
       '<td class="infoat" align="center" width="10%">领样日期</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item[i].getdate)+'</td>'+
       '<td class="infoat" align="center" width="10%">使用设备</td>' +
       '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item[i].devnames)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">环境情况</td>' +
       '<td class="infoc infoleft" colspan="7" align="left" width="90%">'+alms.GetItemData(item[i].testenv)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
       '<td class="infoc infoleft" colspan="7" align="left" width="90%">'+alms.GetItemData(item[i].taskremark)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%"></td>' +
       '<td class="infoc infoleft" align="left" width="15%"></td>' +
       '<td class="infoat" align="center" width="10%"></td>' +
       '<td class="infoc infoleft" align="left" width="15%"></td>'+
       '<td class="infoat" align="center" width="10%"></td>' +
       '<td class="infoc infoleft" align="left" width="15%"></td>'+
       '<td class="infoat" align="center" width="10%"></td>' +
       '<td class="infoc infoleft" align="left" width="15%"></td></tr>';
     }
     
     html += '</table>';
     
     html += '<br/>';
     html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
     html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>原始记录表附件</b></td></tr>';
     
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%">附件序号</td>' +
       ' <td class="infoat" align="center" width="30%">附件名称</td>' +
       ' <td class="infoat" align="center" width="30%">附件类型名称</td>' +
       '<td class="infoc infoleft"  align="center" width="30%">操作</td></tr>';
   
     for(var i=0;i<attach.length;i++){
       html += '<tr class="infotr">' +
       '<td class="infoc infoleft"  align="center" width="10%">' +parseInt(i+1)+ '</td>' +
       '<td class="infoc infoleft"  align="center" width="30%">' +alms.GetItemData(attach[i].attachname)+ '</td>' +
       '<td class="infoc infoleft"  align="center" width="30%">' +alms.GetItemData(attach[i].attachtypename)+ '</td>' +
       '<td class="infoc infoleft"  align="center" width="30%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attach[i].attachurl+"','"+attach[i].attachname+"'"+')">'+attach[i].attachtypename+'预览</a></td></tr>';
     }
     
     html += '</table>';
     
     html += '<br/>';
     html += '<br/>';
     html += '<br/>';
     
     html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
     html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>样品检测数据结果</b></td></tr>';
     html += '<tr class="infotr"><td class="infoc infoleft"  align="center" width="30%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="OnResult('+"'"+record.taskid+"','"+record.samplecode+"'"+')">样品检测数据结果预览</a></td></tr>';
   
   }
   
   return html;
}

alms.FormComboByUrl = function(url,type,id){
  var lists = tools.GetTypeList(type);
  var rtn;
  var rtn = tools.JsonGet(url);
  
  if (rtn && rtn.data)
    Ext.each(rtn.data, function (item, index) { 
      lists.push({ id: item.id, name: item.name});
    });
  
  var store = new Ext.data.JsonStore({
    fields: ['id', 'name'],
    data: lists
  });
  
  Ext.getCmp(id).bindStore(store); 
  
  if (type)
    tools.SetValue(id, type);
};

alms.PrintReportHtmlBySamplecode = function (samplecode, defaultwidth) {
  var form = '';
  var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + samplecode);
  if (records && records.data) {
    var nowdate = new Date();
    var i = 0, j = 0;
    var record;
    var rowspan = 0, colspan = 0;
    var height = 28, width = 36, nowheight = 0, nowwidth = 0;
    var form = '';
    var celltext = '';
    var style = '';
    var align = '';
    
    for (var n = 0; n < records.data.length; n++) {
      var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + tools.GetEncode(tools.GetEncode(records.data[n].reportid)));

      if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
        for(var page = 0; page < item.details.length; page++){
          var nowdetails;
          var fl1;
          var fl2;
          if(page == 0){
            fl1 = 3*item.form.formlength/4;

            nowdetails = item.details[page].datas;
            if (page == item.details.length - 1){
              form += '<table align = "center" cellspacing="0" cellpadding="0" border="0" width="90%" style="font-size:12px; border-collapse: collapse;">';
            } else {
              form += '<table align = "center" cellspacing="0" cellpadding="0" border="0" width="90%" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
            }
            form += '<tr>';
            for (i = 0; i < item.form.formwidth; i++) {
              form += '<td width="' + width + 'px"; height="0px" ></td>';
            }
            form += '</tr>';
            for (i = 1; i <= fl1; i++) {
              form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
              for (j = 0; j < nowdetails.length; j++) {
                record = nowdetails[j];
                if (record.beginrow == i) {
                  rowspan = record.endrow - record.beginrow + 1;
                  nowheight = height * rowspan;
                  colspan = record.endcolumn - record.begincolumn + 1;
                  nowwidth = width * colspan;
                  celltext = '';
                  switch (record.valuesource) {
                    case '02' :
                      celltext = record.celltext;
                      break;
                    case '03' :
                      celltext = '';
                      break;
                    case '01' :
                      celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                      break;
                    case '04' :
                      celltext = record.celltext;
                      break;
                    default :
                      break;
                  }
                  celltext = record.prefixtext + celltext + record.postfixtext;
                  style = '';
                  align = '';
                  switch (record.aligntype) {
                    case '1' :
                      align = ' align="left" ';
                      style += 'padding-left:3px;';
                      break;
                    case '2' :
                      align = ' align="center" ';
                      break;
                    case '3' :
                      align = ' align="right" ';
                      style += 'padding-right:3px;';
                      break;
                    default :
                      break;
                  }

                  if (record.isborder > 0)
                    style += 'border: solid ' + record.isborder
                        + 'px Black;';

                  if (record.isline && (record.isborder <= 0))
                    style += 'border-bottom: solid 1px Black;';

                  if (record.isbold)
                    style += 'font-weight:bold;';

                  style += 'font-size:' + record.fontsize + 'px;';

                  cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                      + ' style="' + style + '" '
                      + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                      + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                      + ' valign="middle">' + 
                      (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                      
                  if (record.valuesource == '04') {
                    if (record.fieldcode == '{actreportid}') {
                      if (!Ext.isEmpty(celltext)) 
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                          + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                       else
                         form += cellhtml;
                    }
                    else if (Ext.isEmpty(record.classsource)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else if (!Ext.isEmpty(celltext)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                    }
                    else
                      form += cellhtml;
                  }
                  else {
                    form += cellhtml;
                  }
                }
              }
              form += '</tr>';
            }
          } else {
            fl2 = item.form.formlength/4
            nowdetails = item.details[page].datas;
            if (page == item.details.length - 1){
              form += '<table align = "center" cellspacing="0" cellpadding="0" border="0" width="90%" style="font-size:12px; border-collapse: collapse;">';
            } else {
              form += '<table align = "center" cellspacing="0" cellpadding="0" border="0" width="90%" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
            }
            form += '<tr>';
            for (i = 0; i < item.form.formwidth; i++) {
              form += '<td width="' + width + 'px"; height="0px" ></td>';
            }
            form += '</tr>';
            for (i = fl1+1; i <= fl1+fl2; i++) {
              form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
              for (j = 0; j < nowdetails.length; j++) {
                record = nowdetails[j];
                if (record.beginrow == i) {
                  rowspan = record.endrow - record.beginrow + 1;
                  nowheight = height * rowspan;
                  colspan = record.endcolumn - record.begincolumn + 1;
                  nowwidth = width * colspan;
                  celltext = '';
                  switch (record.valuesource) {
                    case '02' :
                      celltext = record.celltext;
                      break;
                    case '03' :
                      celltext = '';
                      break;
                    case '01' :
                      celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                      break;
                    case '04' :
                      celltext = record.celltext;
                      break;
                    default :
                      break;
                  }
                  celltext = record.prefixtext + celltext + record.postfixtext;
                  style = '';
                  align = '';
                  switch (record.aligntype) {
                    case '1' :
                      align = ' align="left" ';
                      style += 'padding-left:3px;';
                      break;
                    case '2' :
                      align = ' align="center" ';
                      break;
                    case '3' :
                      align = ' align="right" ';
                      style += 'padding-right:3px;';
                      break;
                    default :
                      break;
                  }

                  if (record.isborder > 0)
                    style += 'border: solid ' + record.isborder
                        + 'px Black;';

                  if (record.isline && (record.isborder <= 0))
                    style += 'border-bottom: solid 1px Black;';

                  if (record.isbold)
                    style += 'font-weight:bold;';

                  style += 'font-size:' + record.fontsize + 'px;';

                  cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                      + ' style="' + style + '" '
                      + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                      + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                      + ' valign="middle">' + 
                      (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                      
                  if (record.valuesource == '04') {
                    if (record.fieldcode == '{actreportid}') {
                      if (!Ext.isEmpty(celltext)) 
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                          + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                       else
                         form += cellhtml;
                    }
                    else if (Ext.isEmpty(record.classsource)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else if (!Ext.isEmpty(celltext)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                    }
                    else
                      form += cellhtml;
                  }
                  else {
                    form += cellhtml;
                  }
                }
              }
              form += '</tr>';
            }
            fl1 = fl1+fl2;
          }
          form += '</table>';     
        }
        
        if (!Ext.isEmpty(form)) {
          var LODOP = getLodop();
          LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
          LODOP.PRINT_INIT("试验报告打印");
          if (item.form.formdirect == 1)
            LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
          else
            LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");
          LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', form);
          LODOP.SET_PRINTER_INDEXA(-1);
          LODOP.PREVIEW();// 预览功能
// LODOP.PRINT();//打印功能
        }
        
      } else {
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '原始记录'));
        return '';
      }
    }
    
    return form;
  }
}

alms.PrintRecordHtmlBySampletran = function (sampletran, defaultwidth,type) {
  var form = '';
  var records = tools.JsonGet(tools.GetUrl('DatGetBusRecordBySampleTran.do?br.sampletran='+sampletran)).data;

  if(records){
    if(records.length>0){
      for(var k=0;k<records.length;k++){
        var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecord.do?br.recordid=') + records[k].recordid);
        if(item.form.formdirect == type){
          var nowdate = new Date();
        if (item && item.record && !Ext.isEmpty(item.record.recordid)) {
          tools.log(item.record.recordid, nowdate);
          
          var i = 0, j = 0;
          var record;
          var rowspan = 0, colspan = 0;
          var height = 28, width = 36, nowheight = 0, nowwidth = 0;
          
          var celltext = '';
          var style = '';
          var align = '';
          
          tools.log(item.details.length, nowdate);
          
          for (var page = 0; page < item.details.length; page++) {
            var nowdetails = item.details[page].datas;
            
            tools.log(nowdetails.length, nowdate);
            
            if (i == item.details.length - 1)
              form += '<br/><table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
            else
              form += '<br/><table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
            
            form += '<tr>';
            
            for (i = 0; i < item.form.formwidth; i++) {
              // form += '<td width="' + width + 'px"; height="' + height +
				// 'px" style="border: solid 1px Black;">' + (i + 1) + '</td>';
              form += '<td width="' + width + 'px"; height="0px" ></td>';
            }
            form += '</tr>';
            
            for (i = 1; i <= item.form.formlength; i++) {
              form += '<tr><td width="' + width + 'px"; height="18px"></td>';
              
              for (j = 0; j < nowdetails.length; j++) {
                record = nowdetails[j];
                
                nowheight = 0;
                nowwidth = 0;
                if (record.beginrow == i) {
                  rowspan = record.endrow - record.beginrow + 1;
                  nowheight = height * rowspan;
                  
                  colspan = record.endcolumn - record.begincolumn + 1;
                  nowwidth = width * colspan;
                  
                  celltext = '';
                  switch (record.valuesource) {
                    case '02':
                      celltext = record.celltext;
                      break;
                      
                    case '03':
                      celltext = '';
                      break;
                      
                    case '01':
                      celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                      break;
                      
                    case '04':
                      celltext = record.celltext;
                      break;
                      
                    default:
                      break;
                  }
                  
                  celltext = record.prefixtext + celltext + record.postfixtext;
                  
                  style = '';
                  align = '';
                  
                  switch (record.aligntype) {
                    case '1':
                      align = ' align="left" ';
                      style += 'padding-left:3px;';
                      break;
                      
                    case '2':
                      align = ' align="center" ';
                      break;
                      
                    case '3':
                      align = ' align="right" ';
                      style += 'padding-right:3px;';
                      break;
                      
                    default:
                      break;
                  }
                  
                  if (record.isborder > 0)
                    style += 'border: solid ' + record.isborder + 'px Black;';
                  
                  if (record.isline && (record.isborder <= 0))
                    style += 'border-bottom: solid 1px Black;';
                  
                  if (record.isbold)
                    style += 'font-weight:bold;';
                  
                  style += 'font-size:' + record.fontsize + 'px;';
                  
                  cellhtml= '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' +
                    ' style="' + style + '" ' + 
                    (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + 
                    (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + 
                    align + ' valign="middle">' + 
                    celltext + '</td>';
                  
                  if (record.valuesource == '04') {
                    
                    if (record.fieldcode == '{actreportid}') {
                      
                      if (!Ext.isEmpty(celltext)) 
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                          + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                       else
                         form += cellhtml;
                    }
                    else if (Ext.isEmpty(record.classsource)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else if (!Ext.isEmpty(celltext)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="left">'
                          + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                    }
                    
                    // 公式变成图片
                    else if (record.classsource == 'formula') {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px"' + ' style="border: solid 1px Black;" ' 
                      + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                      + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                      + ' valign="middle">'
                      + '<image height="170px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else
                      form += cellhtml;
                  }
                  else {
                    form += cellhtml;
                  }
                } 
              }
              
              form += '</tr>';
            }
            
            form += '</table><br/><br/>';
          }
        }
        else
          tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
        }
      }
      
      if (!Ext.isEmpty(form)) {
          var LODOP = getLodop();
          LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
          LODOP.PRINT_INIT("试验报告打印");
          if (type == 1)
            LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
          else
           LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");
          LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', form);
          LODOP.SET_PRINTER_INDEXA(-1);
          LODOP.PREVIEW();// 预览功能
// LODOP.PRINT();//打印功能
       }
    }
  }
}

alms.GetReportHtmlByTask = function (samplecode, split, ispage, defaultwidth) {
  var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + samplecode);
  if (records && records.data) {
    var nowdate = new Date();
    var i = 0, j = 0;
    var record;
    var rowspan = 0, colspan = 0;
    var height = 28, width = 36, nowheight = 0, nowwidth = 0;
    var form = '';
    var celltext = '';
    var style = '';
    var align = '';
    
    for (var n = 0; n < records.data.length; n++) {
      var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + records.data[n].reportid);

      if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
        for(var page = 0; page < item.details.length; page++){
          var nowdetails;
          var fl1;
          var fl2;
          if(page == 0){
            fl1 = 3*item.form.formlength/4;

            nowdetails = item.details[page].datas;
            if (page == item.details.length - 1){
              form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
            } else {
              form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
            }
            form += '<tr>';
            for (i = 0; i < item.form.formwidth; i++) {
              form += '<td width="' + width + 'px"; height="0px" ></td>';
            }
            form += '</tr>';
            for (i = 1; i <= fl1; i++) {
              form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
              for (j = 0; j < nowdetails.length; j++) {
                record = nowdetails[j];
                if (record.beginrow == i) {
                  rowspan = record.endrow - record.beginrow + 1;
                  nowheight = height * rowspan;
                  colspan = record.endcolumn - record.begincolumn + 1;
                  nowwidth = width * colspan;
                  celltext = '';
                  switch (record.valuesource) {
                    case '02' :
                      celltext = record.celltext;
                      break;
                    case '03' :
                      celltext = '';
                      break;
                    case '01' :
                      celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                      break;
                    case '04' :
                      celltext = record.celltext;
                      break;
                    default :
                      break;
                  }
                  celltext = record.prefixtext + celltext + record.postfixtext;
                  style = '';
                  align = '';
                  switch (record.aligntype) {
                    case '1' :
                      align = ' align="left" ';
                      style += 'padding-left:3px;';
                      break;
                    case '2' :
                      align = ' align="center" ';
                      break;
                    case '3' :
                      align = ' align="right" ';
                      style += 'padding-right:3px;';
                      break;
                    default :
                      break;
                  }

                  if (record.isborder > 0)
                    style += 'border: solid ' + record.isborder
                        + 'px Black;';

                  if (record.isline && (record.isborder <= 0))
                    style += 'border-bottom: solid 1px Black;';

                  if (record.isbold)
                    style += 'font-weight:bold;';

                  style += 'font-size:' + record.fontsize + 'px;';

                  cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                      + ' style="' + style + '" '
                      + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                      + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                      + ' valign="middle">' + 
                      (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                      
                  if (record.valuesource == '04') {
                    if (record.fieldcode == '{actreportid}') {
                      if (!Ext.isEmpty(celltext)) 
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                          + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                       else
                         form += cellhtml;
                    }
                    else if (Ext.isEmpty(record.classsource)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else if (!Ext.isEmpty(celltext)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                    }
                    else
                      form += cellhtml;
                  }
                  else {
                    form += cellhtml;
                  }
                }
              }
              form += '</tr>';
            }
          } else {
            fl2 = item.form.formlength/4
            nowdetails = item.details[page].datas;
            if (page == item.details.length - 1){
              form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
            } else {
              form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
            }
            form += '<tr>';
            for (i = 0; i < item.form.formwidth; i++) {
              form += '<td width="' + width + 'px"; height="0px" ></td>';
            }
            form += '</tr>';
            for (i = fl1+1; i <= fl1+fl2; i++) {
              form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
              for (j = 0; j < nowdetails.length; j++) {
                record = nowdetails[j];
                if (record.beginrow == i) {
                  rowspan = record.endrow - record.beginrow + 1;
                  nowheight = height * rowspan;
                  colspan = record.endcolumn - record.begincolumn + 1;
                  nowwidth = width * colspan;
                  celltext = '';
                  switch (record.valuesource) {
                    case '02' :
                      celltext = record.celltext;
                      break;
                    case '03' :
                      celltext = '';
                      break;
                    case '01' :
                      ccelltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                      break;
                    case '04' :
                      celltext = record.celltext;
                      break;
                    default :
                      break;
                  }
                  celltext = record.prefixtext + celltext + record.postfixtext;
                  style = '';
                  align = '';
                  switch (record.aligntype) {
                    case '1' :
                      align = ' align="left" ';
                      style += 'padding-left:3px;';
                      break;
                    case '2' :
                      align = ' align="center" ';
                      break;
                    case '3' :
                      align = ' align="right" ';
                      style += 'padding-right:3px;';
                      break;
                    default :
                      break;
                  }

                  if (record.isborder > 0)
                    style += 'border: solid ' + record.isborder
                        + 'px Black;';

                  if (record.isline && (record.isborder <= 0))
                    style += 'border-bottom: solid 1px Black;';

                  if (record.isbold)
                    style += 'font-weight:bold;';

                  style += 'font-size:' + record.fontsize + 'px;';

                  cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
                      + ' style="' + style + '" '
                      + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                      + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                      + ' valign="middle">' + 
                      (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                      
                  if (record.valuesource == '04') {
                    if (record.fieldcode == '{actreportid}') {
                      if (!Ext.isEmpty(celltext)) 
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                          + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                       else
                         form += cellhtml;
                    }
                    else if (Ext.isEmpty(record.classsource)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                    }
                    else if (!Ext.isEmpty(celltext)) {
                      form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                          + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                          + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                          + ' valign="middle">'
                          + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                    }
                    else
                      form += cellhtml;
                  }
                  else {
                    form += cellhtml;
                  }
                }
              }
              form += '</tr>';
            }
            fl1 = fl1+fl2;
          }
          form += '</table>';
          
          if (split) 
            form += '<br />';
        }
      } else {
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '试验报告'));
        return '';
      }
      
    }

    return form;
  }
};

alms.ShowDevQueryHtml = function(record){
  var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">设备信息综合查询</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">设备编号</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(record.devid)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">设备名称</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.devname)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">生产厂家</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.factoryname)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">出厂编号</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.factorycode)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">设备类型</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(record.devtypename)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">使用温度</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.usetemp)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">使用湿度</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.usehumid)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">设备负责人</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.devmanagername)+'</td></tr>';
  
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">所属实验室</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(record.deptname)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">价格(万元)</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(record.devprice)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">购买日期</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(record.buydate)+'</td>'+
  '<td class="infoat" align="center" width="10%" style="height:30px;">上次检定</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(record.prevtime)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">下次检定</td>' +
  '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemDateData(record.nexttime)+ '</td>' +
  '<td class="infoat" align="center" width="10%" style="height:30px;">存放位置</td>' +
  '<td class="infoc infoleft" align="left" colspan="5" width="15%" style="height:30px;">'+alms.GetItemData(record.storeplace)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">设备用途</td>' +
  '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(record.devusage)+'</td></tr>';
  
  html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">技术指标</td>' +
  '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(record.devrange)+'</td></tr>';
  
  var devuse = tools.JsonGet('DevGetListDevUseByDevID.do?du.devid='+tools.GetEncode(tools.GetEncode(record.devid))).data;

  if(devuse){
    if(devuse.length>0){
      for(var j = 0; j < devuse.length; j++){
        html += '<tr class="infotr" ><td class="infoat" colspan=10 align="center" width="100%" style="font-size:14px;">设备使用情况</td></tr>';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">设备名称</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(devuse[j].devname)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">开机时间</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(devuse[j].usebegin)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">关机时间</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(devuse[j].useend)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].samplename)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">使用前状态</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(devuse[j].beforestatusname)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">使用后状态</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(devuse[j].afterstatusname)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">检测项目</td>' +
        '<td class="infoc infoleft" align="center" colspan="9" width="40%" style="height:30px;">'+alms.GetItemData(devuse[j].parametername)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">使用功能</td>' +
        '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(devuse[j].usefunction)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
        '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(devuse[j].remark)+'</td></tr>';
      }
    }
  }
  
  html += '</table>';
  
  return html;
}

alms.ShowPrdQueryHtml = function(record){
  
  var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">耗材信息综合查询</td></tr>';
  html += '</table>';
  html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
  
  var prdindetail = tools.JsonGet('PrdGetListStkInDetailByPrdID.do?indetail.prdid='+record.prdid).data;
  if(prdindetail){
    if(prdindetail.length>0){
      for(var j = 0; j < prdindetail.length; j++){
        var prdin = tools.JsonGet('PrdGetStkIn.do?stkin.tranid='+prdindetail[j].tranid);
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">耗材入库情况</td></tr>';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">物品</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdindetail[j].prdname)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">条件前缀</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].prdcodeprefix)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">入库总数</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].factnumber+prdindetail[j].prdunitname)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">入库单价(元)</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].prdprice)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">入库金额(元)</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdindetail[j].prdamount)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">生产厂商</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].factoryname)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">供应商</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].tradename)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">有效期(月)</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].validdate)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">生产日期</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemDateData(prdindetail[j].factorydate)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">购买人</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].buyusername)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">购买日期</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(prdindetail[j].buydate)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">耗材类型</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].prdtypename)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">规格型号</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdindetail[j].prdstandard)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">入库仓库</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdin.storename)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">入库人员</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="  height:30px;">'+alms.GetItemData(prdin.inusername)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">入库日期</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(prdin.indate)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">记账员</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdin.recousername)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">备注</td>' +
        '<td class="infoc infoleft" align="left" colspan="5" width="15%" style="height:30px;">'+alms.GetItemData(prdindetail[j].remark)+'</td></tr>';
      }
    }
  }
  
  var prdoutdetail = tools.JsonGet('PrdGetListStkOutDetailByPrdID.do?outdetail.prdid='+record.prdid).data;
  if(prdoutdetail){
    if(prdoutdetail.length>0){
      for(var j = 0; j < prdoutdetail.length; j++){
        var prdout = tools.JsonGet('PrdGetStkOut.do?stkout.tranid='+prdoutdetail[j].tranid);

        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">耗材出库情况</td></tr>';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">物品</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdoutdetail[j].prdname)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">耗材条码</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdoutdetail[j].prdcode)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">出库总数</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdoutdetail[j].factnumber+prdoutdetail[j].prdunitname)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">规格型号</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdoutdetail[j].prdstandard)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">出库仓库</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(prdout.storename)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">出库日期</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(prdout.outdate)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">领出人</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdout.outusername)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">记账员</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(prdout.recousername)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">备注</td>' +
        '<td class="infoc infoleft" align="left" colspan="7" width="15%" style="height:30px;">' +alms.GetItemData(prdoutdetail[j].remark)+ '</td></tr>';
      }
    }
  }
  
  html += '</table>';
  
  return html;
}

alms.ShowUserQueryHtml = function(record){
  
  var html = '<table cellspacing="0" cellpadding="0"  border="0" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
  html += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">人员信息综合查询</td></tr>';
  html += '</table>';
  html += '<table align = "center" cellspacing="0" cellpadding="0" border="1" height="8%" style="font-size:12px;" width="90%">';
  
  var item = tools.JsonGet(tools.GetUrl('StaffGetBasUser.do?bu.tranid=') + record.tranid);

  if(item){
    html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">人员基本信息</td></tr>';
    html += '<tr class="infotr"><td class="infoat" colspan=3 align="center" width="10%" style="height:30px;">姓名</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.username)+ '</td>' +
    '<td class="infoat" align="center" colspan=2 width="10%" style="height:30px;">性别</td>' +
    '<td class="infoc infoleft"  colspan=2 align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usersexname)+'</td>'+
    '<td class="infoat" align="center" colspan=3 width="10%" style="height:30px;">出生年月</td>' +
    '<td class="infoc infoleft" align="left" colspan=4 width="15%" style="height:30px;">'+alms.GetItemDateData(item.borndate)+'</td>'+
    '<td class="infoat" align="center" colspan=2 width="10%" style="height:30px;">民族</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usernative)+'</td></tr>';
    
    if(alms.GetItemData(item.userpolity)=='13'){
    	var userpolityname='';
    }else{
    	var userpolityname=alms.GetItemData(item.userpolityname);
    }
    
    html += '<tr class="infotr"><td class="infoat" colspan=3 align="center" width="10%" style="height:30px;">籍贯</td>' +
    '<td class="infoc infoleft" align="left" colspan=2 width="15%" style="height:30px;">' +alms.GetItemData(item.bornaddress)+ '</td>' +
    '<td class="infoat" align="center" colspan=4 width="10%" style="height:30px;">党团时间</td>' +
    '<td class="infoc infoleft" align="left" colspan=3 width="15%" style="height:30px;">'+alms.GetItemData(item.useridentity)+'  '+userpolityname+'</td>'+
    '<td class="infoat" align="center" colspan=4 width="10%" style="height:30px;">健康状况</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.altername)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan=3 rowspan=2  align="center" width="10%" style="height:30px;">职务</td>' +
    '<td class="infoc infoleft" align="left" colspan=2 rowspan=2  width="15%" style="height:30px;">' +alms.GetItemData(item.opertypename)+ '</td>' +
    '<td class="infoat" align="center" colspan=4  rowspan=2 width="10%" style="height:30px;">参见工作时间</td>' +
    '<td class="infoc infoleft" align="left" colspan=3 rowspan=2  width="15%" style="height:30px;">'+alms.GetItemDateData(item.begintest)+'</td>'+
    '<td class="infoat" align="center" colspan=4  rowspan=2 width="10%" style="height:30px;">从事本技术领域年限</td>' +
    '<td class="infoc infoleft" colspan=4 rowspan=2  align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usertele)+'</td></tr>';
    
    html += '<tr class="infotr"></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan=3 align="center" width="10%" style="height:30px;">学历</td>' +
    '<td class="infoc infoleft" align="left" colspan=2 width="15%" style="height:30px;">' +alms.GetItemData(item.usereduname)+ '</td>' +
    '<td class="infoat" align="center" colspan=4 rowspan=2  width="10%" style="height:30px;">毕业院校及专业</td>' +
    '<td class="infoc infoleft" align="left" colspan=3 rowspan=2  width="15%" style="height:30px;">'+alms.GetItemData(item.hometele)+'  '+alms.GetItemData(item.usermajor)+'</td>'+
    '<td class="infoat" align="center" colspan=4 width="10%" style="height:30px;">现在部门岗位</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.userdegreename)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan=3 align="center" width="10%" style="height:30px;">学位或专业技术职务</td>' +
    '<td class="infoc infoleft" align="left" colspan=2 width="15%" style="height:30px;">' +alms.GetItemData(item.userdutyname)+ '</td>' +
    '<td class="infoat" align="center" colspan=4 width="10%" style="height:30px;">本岗位年限</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.useremail)+'</td></tr>';
    
    html += '<tr class="infotr" ><td class="infoat" colspan=3 rowspan=10 align="center" width="10%" style="height:30px;">简历</td>' +
    
    '<td class="infoc infoleft" colspan=17 rowspan=5 align="left" width="15%" style="height:30px;">技术工作简历：<br/> &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;'+alms.GetItemData(item.homeaddress)+'</td></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"><td class="infoc infoleft" colspan=17 rowspan=5 align="left" width="15%" style="height:30px;">主要业绩：<br/>&emsp;&emsp;&emsp;&emsp;&emsp;'+alms.GetItemData(item.userremark)+'</td></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';
    html += '<tr class="infotr"></tr>';

    
    //    html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">人员基本信息</td></tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">人员姓名</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.username)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">所属部门</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.deptname)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">所学专业</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usermajor)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">联系电话</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usertele)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">电子邮箱</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.useremail)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">家庭电话</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.hometele)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">性别</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usersexname)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">民族</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usernative)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">曾用名</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.altername)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">出生日期</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(item.borndate)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">出生地</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.bornaddress)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">身份证号</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.useridentity)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">最高学历</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.usereduname)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">最高学位</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.userdegreename)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">政治面貌</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="  height:30px;">'+alms.GetItemData(item.userpolityname)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">实验员类型</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.opertypename)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">技术职务</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(item.userdutyname)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">人员职称</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemData(item.usertitlename)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">人员状态</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="  height:30px;">'+alms.GetItemData(item.userstatusname)+'</td>'+
//    '<td class="infoat" align="center" width="10%" style="height:30px;">开始从事检测时间</td>' +
//    '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">'+alms.GetItemDateData(item.begintest)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">家庭住址</td>' +
//    '<td class="infoc infoleft" align="left" colspan="3" width="15%" style="height:30px;">' +alms.GetItemData(item.homeaddress)+ '</td>' +
//    '<td class="infoat" align="center" width="10%" style="height:30px;">备注</td>' +
//    '<td class="infoc infoleft" align="left" colspan="3" width="15%" style="height:30px;">'+alms.GetItemData(item.userremark)+'</td></tr>';
  }
  
  html += '</table>';
  
  var files = tools.JsonGet(tools.GetUrl('StaffGetListBasUserFile.do?buf.tranid=') + record.tranid).data;
  if(files){
    if(files.length>0){
      html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
      html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">人员档案附件信息</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">文件序号</td>' +
        '<td class="infoat" align="center" width="10%">证书名称</td>' +
        '<td class="infoat" align="center" width="10%">所学专业</td>' +
        '<td class="infoat" align="center" width="10%">证书编号</td>' +
        '<td class="infoat" align="center" width="10%">证书类型</td>' +
        '<td class="infoat" align="center" width="10%">获取时间</td>' +	
        '<td class="infoat" align="center" width="10%">有效时间</td>' +
        '<td class="infoat" align="center" width="20%">备注</td>' +
        '<td class="infoat" align="center" width="10%">预览</td></tr>';

      for(var i=0;i<files.length;i++){
        html += '<tr class="infotr">' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(files[i].fileno)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(files[i].filename)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(files[i].major)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(files[i].filenumber)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(files[i].filetypename)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemDateData(files[i].gettime)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemDateData(files[i].validtime)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="20%">' +alms.GetItemData(files[i].fileremark)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+files[i].fileurl+"','"+files[i].filename+"'"+')">'+'预览</a></td></tr>';
      }
    }
  }
  html += '</table>';
  
  var bustasks = tools.JsonGet(tools.GetUrl('LabGetListBusTaskTesterByUser.do?bttr.testuser=') + item.userid).data;
  
  if(bustasks){
    if(bustasks.length>0){
      html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
      html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">检测任务执行情况</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="20%">样品编号</td>' +
        '<td class="infoat" align="center" width="20%">样品名称</td>' +
        '<td class="infoat" align="center" width="20%">检测项目</td>' +
        '<td class="infoat" align="center" width="20%">开始检测时间</td>' +
        '<td class="infoat" align="center" width="20%">结束检测时间</td></tr>';

      for(var i=0;i<bustasks.length;i++){
        html += '<tr class="infotr">' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(bustasks[i].samplecode)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(bustasks[i].samplename)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemData(bustasks[i].parametername)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemDateData(bustasks[i].begintestdate)+ '</td>' +
        '<td class="infoc infoleft"  align="center" width="10%">' +alms.GetItemDateData(bustasks[i].endtestdate)+ '</td></tr>';
      }
    }
  }
  html += '</table>';
  
  
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  
  var devuses = tools.JsonGet('DevGetListDevUseByUser.do?du.tranuser='+item.userid).data;
  if(devuses){
    if(devuses.length>0){
      for(var j = 0; j < devuses.length; j++){
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">设备使用情况</td></tr>';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">设备名称</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(devuses[j].devname)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">开机时间</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(devuses[j].usebegin)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">关机时间</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(devuses[j].useend)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">样品名称</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(devuses[j].samplename)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">检测项目</td>' +
        '<td class="infoc infoleft" align="left" width="15%" style="height:30px;">' +alms.GetItemData(devuses[j].parametername)+ '</td>' +
        '<td class="infoat" align="center" width="10%" style="height:30px;">使用前状态</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(devuses[j].beforestatusname)+'</td>'+
        '<td class="infoat" align="center" width="10%" style="height:30px;">使用后状态</td>' +
        '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(devuses[j].afterstatusname)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">使用功能</td>' +
        '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(devuses[j].usefunction)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">备注</td>' +
        '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(devuses[j].remark)+'</td></tr>';
      }
    }
  }
  html += '</table>';
  
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  
  var usertrains = tools.JsonGet('StaffGetListUserTrainByUser.do?train.userid='+item.userid).data;
  if(usertrains){
    if(usertrains.length>0){
      for(var j = 0; j < usertrains.length; j++){
        
        html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%" style="font-size:14px;">接收培训经历情况</td></tr>';
        html += '<tr class="infotr"><td class="infoat" colspan=1 align="center" width="10%" style="height:30px;">培训形式</td>' +
        '<td class="infoc infoleft" align="center" colspan=1 width="15%" style="height:30px;">' +alms.GetItemData(usertrains[j].traintypename)+ '</td>' +
        '<td class="infoat" align="center" colspan=1 width="10%" style="height:30px;">培训日期</td>' +
        '<td class="infoc infoleft" align="center" colspan=1 width="15%" style="height:30px;">'+alms.GetItemDateData(usertrains[j].begindate)+'</td>'+
        '<td class="infoat" align="center" colspan=1 width="10%" style="height:30px;">培训目标</td>' +
        '<td class="infoc infoleft" align="center" colspan=1 width="15%" style="height:30px;">'+alms.GetItemData(usertrains[j].traintarget)+'</td>'+
        '<td class="infoat" align="center" colspan=1 width="10%" style="height:30px;">培训对象</td>' +
        '<td class="infoc infoleft" align="center" colspan=1 width="15%" style="height:30px;">'+alms.GetItemData(usertrains[j].trainobject)+'</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" colspan=1 align="center" width="10%" style="height:30px;">培训项目</td>' +
        '<td class="infoc infoleft" colspan=1 align="center" width="15%" style="height:30px;">' +alms.GetItemData(usertrains[j].traincontent)+ '</td>' +
        '<td class="infoat" colspan=1 align="center" width="10%" style="height:30px;">实施情况</td>' +
        '<td class="infoc infoleft" colspan=1 align="center" width="15%" style="height:30px;">'+alms.GetItemData(usertrains[j].trainresult)+'</td>'+
        '<td class="infoat" colspan=1 align="center" width="10%" style="height:30px;">教员</td>' +
        '<td class="infoc infoleft" colspan=1 align="center" width="15%" style="height:30px;">'+alms.GetItemData(usertrains[j].trainteacher)+'</td>'+
        '<td class="infoat" colspan=1 align="center" width="10%" style="height:30px;">考核</td>' +
        '<td class="infoc infoleft" colspan=1 align="center" width="15%" style="height:30px;">'+alms.GetItemData(usertrains[j].trainexam)+'</td></tr>';
      }
    }
  }
  
  html += '</table>';
  
  // 考核结果
  var userexamresult = tools.JsonGet('StaffGetUserExamResultByUser.do?examresult.userid='+item.userid);
  if(userexamresult.userid){
      html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
      html += '<tr class="infotr" ><td class="infoat" colspan=8 align="center" width="100%" style="font-size:14px;">人员上岗考核结果</td></tr>';
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">被考核人</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(userexamresult.username)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">部门机构</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(userexamresult.deptname)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">技术职务</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(userexamresult.userdutyname)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">所学专业</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(userexamresult.userpro)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;">岗位</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">' +alms.GetItemData(userexamresult.userpostname)+ '</td>' +
      '<td class="infoat" align="center" width="10%" style="height:30px;">批准人</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemData(userexamresult.examapprovename)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">批准日期</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(userexamresult.examapprovedate)+'</td>'+
      '<td class="infoat" align="center" width="10%" style="height:30px;">记录日期</td>' +
      '<td class="infoc infoleft" align="center" width="15%" style="height:30px;">'+alms.GetItemDateData(userexamresult.crtdate)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" colspan=1 width="10%" style="height:30px;">记录人</td>' +
      '<td class="infoc infoleft" align="center" colspan=1 width="15%" style="height:30px;">' +alms.GetItemData(userexamresult.tranusername)+ '</td>' +
      '<td class="infoat" align="center" colspan=1 width="10%" style="height:30px;">业务范围</td>' +
      '<td class="infoc infoleft" align="center" colspan=5 width="65%" style="height:30px;">'+alms.GetItemData(userexamresult.busscope)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">可使用设备</td>' +
      '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(userexamresult.allowdevname)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">可检测项目</td>' +
      '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(userexamresult.allowparamname)+'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" colspan="1" align="center" width="10%" style="height:30px;">可检测样品类型</td>' +
      '<td class="infoc infoleft" colspan="9" align="left" width="90%" style="height:30px;">'+alms.GetItemData(userexamresult.allowsamplename)+'</td></tr>';
  }
  
  html += '</table>';
  
  return html;
},

alms.ShowMostRecordHtml = function(record){
  
  var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+record.samplecode);
  var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+record.taskid);
  var bustasktest = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid).data;
  var bustasksample = tools.JsonGet('LabGetListBusTaskForAcc.do?bts.taskid='+record.taskid).data;
  var busgetdetails = tools.JsonGet('LabGetListBusGetDetailByTask.do?bgd.taskid='+record.taskid).data;
  var bustasksamples = tools.JsonGet(tools.GetUrl('LabSearchRecordSamples.do?bt.taskid='+record.taskid));
  var teststandards = '';
  var taskid = '';
  var temp = new Array();

  for(var i = 0; i < bustasktest.length; i++){
    if(temp.indexOf(bustasktest[i].teststandardname) == -1){
      temp.push(bustasktest[i].teststandardname);
      if (!Ext.isEmpty(teststandards)){
        teststandards += ',';
      }
      teststandards += bustasktest[i].teststandardname;
    }
  }
  
  // 判断是单样品还是多样品
  if(bustask.samplecode == '多样品样品编号-A'||bustask.samplecode == '多样品样品编号-B'){
    var html = '';
    var page;
    if(busgetdetails.length%15 == 0){// 一页有15条样品记录
      page = busgetdetails.length/15;
    }else{
      page = parseInt(busgetdetails.length/15)+1;
    }

    // 获取检测项目
    var parameternames = [];
    for(var i = 0; i < bustasktest.length; i++){ 
      if (parameternames.indexOf(bustasktest[i].parametername) == -1){
        parameternames.push(bustasktest[i].parametername); 
      }
    }
    
    var html1='';
    var html2='';
    var html3='';
    // 检测参数小于15个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html1 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html1 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农产检测多样品原始记录表</td></tr>';
      html1 += '</table>';
      
      html1 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      var newparameternames1 = [];
      
      if(parameternames.length>0){
        if(parameternames.length>14){
          for(var i = 0; i < 14; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
            newparameternames1.push(parameternames[i])
          }
        }else{
          for(var i = 0; i < parameternames.length; i++){
            html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
            newparameternames1.push(parameternames[i])
          }
        }
      }
      
      if(parameternames.length <= 14){
        for(var i = parameternames.length; i <14; i++){
          html1 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
        }
      }
      
      html1 += '</tr>';
      
      html1 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录,n为页数
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }

      for(var i = (n*15); i <count; i++){
        
        var samplecode = '';
        if(bustask.samplecode.indexOf('-A')>-1){
          samplecode = busgetdetails[i].samplecode+'-A';
        }else{
          samplecode = busgetdetails[i].samplecode+'-B';
        }
        
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+samplecode).data;

        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>0){
          if(submitvalues.length>14){
            for(var j=0;j<14;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }else{
            for(var j=0;j<submitvalues.length;j++){
              html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
            }
          }
        }
        
        if(submitvalues.length <= 14){
          for(var j=submitvalues.length;j<14;j++){
            html1+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html1 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html1 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html1 += '</table>';
      html1 += '<br/>';
    }
  
    // 检测参数大于14个，一页放14个检测参数
    for(var n=0; n<page; n++){
      html2 += '<table cellspacing="0" cellpadding="0"  border="0" td colspan="20" align="center" style="font-size:13px; margin:20 40 20 40;" width="90%">';
      html2 += '<tr class="infotrheader"><td colspan="20" align="center" width="100%" style="font-size:18px; font-weight:bold; height:60px;">农产检测多样品原始记录表</td></tr>';
      html2 += '</table>';
      
      html2 += '<table cellspacing="0" cellpadding="0"  border="1" td colspan="20" align="center" style="font-size:13px;" width="90%">';
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验性质</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.testpropname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品状态</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.samplestatus)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">承检室</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labname)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测室负责人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.labusername)+'</td>'+
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">任务下达人</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">'+alms.GetItemData(bustask.sendusername)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="6" align="center" width="30%" style="height:30px;">下达日期</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">' +alms.GetItemDateData(bustask.senddate)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">要求完成时间</td>' +
        '<td class="infoc infoleft" colspan="6" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(bustask.reqdate)+'</td></tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检验依据</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(bustask.teststandardname)+ '</td>' +
        '<td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">检测项目</td>';
      
      if(parameternames.length>14){
        for(var i = 14; i < parameternames.length; i++){
          html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(parameternames[i])+'</td>';
        }
      }
      
      for(var i = parameternames.length; i <28; i++){
        html2 += '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
      }
      
      html2 += '</tr>';
      
      html2 += '<tr class="infotr"><td class="infoat" colspan="2" align="center" width="10%" style="height:30px;">样品编号</td>' +
        '<td class="infoat" colspan="4" align="center" width="20%" style="height:30px;">产品名称</td>' +
        '<td class="infoat" colspan="14" align="center" width="70%" style="height:30px;">检&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;据</td></tr>';
      
      var count;
      if(15*(n+1)<=busgetdetails.length){// 一页有15条样品记录
        count = 15*(n+1);
      }
      
      if(15*(n+1)>busgetdetails.length){
        count = busgetdetails.length;
      }
      
      for(var i = (n*15); i <count; i++){
        
        var samplecode = '';
        if(bustask.samplecode.indexOf('-A')>-1){
          samplecode = busgetdetails[i].samplecode+'-A';
        }else{
          samplecode = busgetdetails[i].samplecode+'-B';
        }
        
        var submitvalues = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+record.taskid+'&btt.samplecode='+samplecode).data;
        
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData(samplecode)+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData(busgetdetails[i].samplename)+ '</td>';

        // 获取检测参数的检测结果
        if(submitvalues.length>14){
          for(var j=14;j<submitvalues.length;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData(submitvalues[j].submitvalue)+'</td>';
          }
          
          for(var j=submitvalues.length-14;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }else{
          for(var j=0;j<14;j++){
            html2+= '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>';
          }
        }
        
        html2 += '</tr>'
      }
      
      for(var i = busgetdetails.length; i < (15+n*15); i++){
        html2 += '<tr class="infotr"><td class="infoc infoleft" colspan="2" align="center" width="10%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="4" align="center" width="20%" style="height:30px;">' +alms.GetItemData()+ '</td>' +
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td>'+
          '<td class="infoc infoleft" colspan="1" align="center" width="5%" style="height:30px;">'+alms.GetItemData()+'</td></tr>';
      }
      html2 += '</table>';
      html2 += '<br/>';
    }
    
    html3 += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    if(bustasksamples.recordstatus == 02){
	  html3 += '<tr class="infotr"><td  align="center" width="10%" >检测人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.tranuser) +'.jpg" /></td>' +
	    '<td  align="center" width="10%">复核人：</td>' +
	    '<td  align="left" width="25%"></td>' +
	    '<td  align="center" width="10%">审核人：</td>' +
	    '<td  align="left" width="25%"></td></tr>';
    }else if(bustasksamples.recordstatus == 04){
	 html3 += '<tr class="infotr"><td  align="center" width="10%" >检测人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.tranuser) +'.jpg" /></td>' +
	    '<td  align="center" width="10%">复核人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.approveuser) + '.jpg" /></td>' +
	    '<td  align="center" width="10%">审核人：</td>' +
	    '<td  align="left" width="25%"></td></tr>';
    }else if(bustasksamples.recordstatus == 08){
     html3 += '<tr class="infotr"><td  align="center" width="10%" >检测人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.tranuser) +'.jpg" /></td>' +
	    '<td  align="center" width="10%">复核人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.approveuser) + '.jpg" /></td>' +
	    '<td  align="center" width="10%">审核人：</td>' +
	    '<td  align="left" width="25%"><image height="30px" src="images/sign/' +alms.GetItemData(bustasksamples.aduituser) + '.jpg" /></td></tr>';
    }else{
    	 html3 += '<tr class="infotr"><td  align="center" width="10%" >检测人：</td>' +
 	    '<td  align="left" width="25%"></td>' +
 	    '<td  align="center" width="10%">复核人：</td>' +
 	    '<td  align="left" width="25%"></td>' +
 	    '<td  align="center" width="10%">审核人：</td>' +
 	    '<td  align="left" width="25%"></td></tr>';
    }
    html3 += '</table>'
    html3 += '<br/>';
    html3 += '<br/>';
    html3 += '<br/>';
    
    if(parameternames.length>14){
      
      html = html1+html3+html2+html3;
    }else{
      html = html1+html3;
    }
  }  
  
  return html;
}


