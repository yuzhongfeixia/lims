Ext.define('alms.reportotherpreview', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, { 
      title: gpersist.STR_BTN_REPORTOTHER,
      winWidth: 800,
      winHeight: 500,
      autoscroll: true
    });

    me.callParent(arguments);
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix + me.childPrefix;
   
    if (Ext.getCmp(mep + 'winEdit')) {
      Ext.getCmp(mep + 'winEdit').destroy();
    }
    
    var items = [
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    me.OnBeforeCreateEdit();
    
    Ext.Array.insert(items, 0, me.customButtons);
    
    tools.SetToolbar(items, me.tranPrefix);
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true, 
      autoScroll: me.autoscroll,  
      tbar:Ext.create('Ext.toolbar.Toolbar', { items: items }),   
      items: me.editControls
    });
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'winEdit',
      title: me.title,
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      resizable: false, 
      items: [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });

    me.winEdit.show();
    me.OnAfterCreateEdit();
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    me.editControls = [
      {xtype:'label', id:mep + 'recordinfo', html:''}
    ];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews = [];
    me.enEdits = [];
  },
  
  OnSetData: function (reportid,samplecode) {
    var me = this, mep = me.mep;
    var busgetinfo = tools.JsonGet('LabGetBusGetBySampleCode.do?bg.samplecode=' + samplecode);
    var samplinginfo = tools.JsonGet('LabGetBusSamplingGetByNoticeid.do?bg.noticeid=' +busgetinfo.tranid);
    var sampletraninfo = tools.JsonGet('DatGetSampleTranBySampleCode.do?br.samplecode=' + samplecode).data;
    var consignhtml = me.GetConsignRecordHtml('000006',busgetinfo.tranid,busgetinfo.gettype);
    var taskhtml='';
    var samplinghtml='';
    var recordhtml='';
     for (i = 0; i < sampletraninfo.length; i++) {
       recordhtml  =recordhtml+ me.GetRecordHtml(sampletraninfo[i].sampletran);
       taskhtml  =taskhtml+ me.GetTaskHtml(samplecode,sampletraninfo[i].taskid);
      }
    
  
      if(samplinginfo.gettype=='11'){
        samplinghtml = me.GetConsignRecordHtml('000011',samplinginfo.tranid,'11');
      }else if(samplinginfo.gettype=='12'){
        samplinghtml = me.GetConsignRecordHtml('000012',samplinginfo.tranid,'12')
      }

//    if (Ext.isEmpty(consignhtml)) return false;
//    if (Ext.isEmpty(samplinghtml)) return false;
//    if (Ext.isEmpty(taskhtml)) return false;
//    if (Ext.isEmpty(recordhtml)) return false;
    
    var allhtml = '<table cellspacing="0" align="center" cellpadding="0" border="0" style="width:50%; border-collapse: collapse;">'
              + '<tr><td align="center">'
              +samplinghtml+consignhtml+taskhtml+recordhtml
              + '</td></tr></table>';
              
    Ext.fly(Ext.getCmp(mep + 'recordinfo').getEl()).update(allhtml);
    
    me.winEdit.maximize();
  },
  
  GetConsignRecordHtml: function (formid,tranid,gettype) {
    var me = this, mep = me.mep;
    var form = '';
    
    var i = 0, j = 0;
    var record;
    var rowspan = 0, colspan = 0;
    var width = 36, nowheight = 0, nowwidth = 0; 
    var height;
    if(gettype=='01'){
    	height =23;
	    }else if(gettype=='02'){
	    height =23;
	    }else if(gettype=='03'){
	    height =23;
	    }else if(gettype=='04'){
	    height =23;
	    }else if(gettype=='05'){
	    height =23;
	    }else if(gettype=='07'){
	    height =23;
	    }else if(gettype=='08'){
	    height =22;
	    }else if(gettype=='11'){
	    height =22;
	    }else if(gettype=='12'){
	    height =23;
	    }else{
	    height = 28;
		}
    var celltext = '';
    var zhuanhuan='';
    var zhuanhuan1='';
    var zhuanhuan2='';
    var zhuanhuan3='';
    var zhuanhuan4='';
    var zhuanhuan5='';
    var zhuanhuan6='';
    var zhuanhuan7='';
    var zhuanhuan8='';
    var zhuanhuan9='';
    var zhuanhuan10='';
    var zhuanhuan11='';
    var style = '';
    var align = '';
    var tablewidth = 0, tableheight = 0;

    var item = tools.JsonGet(tools.GetUrl('FormGetSetFrmGet.do?fg.formid='+ formid + '&fg.tranid=' + tranid));

    if (item && item.form && !Ext.isEmpty(item.form.formid)) {
      for (var page = 0; page < item.details.length; page++) {
        var nowdetails = item.details[page].datas;
        
        tablewidth = width * item.form.formwidth;
        tableheight = height * item.form.formlength + 1;
        
        if (page == item.details.length - 1)
          form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
        else
          form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';

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
                	 if(record.fieldcode.search("unitcharacte")!= -1){
                		 zhuanhuan=record.celltext+"br单位名称";
                		 celltext = zhuanhuan.replace(new RegExp("br","gm"),"<br/>"); 
                   	
                     }else{
                    	 celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>"); 
                     }
                  break;
                case '05' :
                 zhuanhuan1=record.cellname.replace(new RegExp("br","gm"),"<br/>");
                 zhuanhuan2=zhuanhuan1.replace(new RegExp("lsgz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/1.png\"style=\"top:50px;left:20px; position:relative; z-index:1;\" />");
                 zhuanhuan3=zhuanhuan2.replace(new RegExp("hjgz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/1.png\"style=\"top:-260px;left:480px; position:relative; z-index:1;\" />");
                 zhuanhuan4=zhuanhuan3.replace(new RegExp("jdccgz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/1.png\"style=\"top:150px;left:100px; position:relative; z-index:1;\" />");
                 zhuanhuan5=zhuanhuan4.replace(new RegExp("slxjcgz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/1.png\"style=\"top:150px;left:100px; position:relative; z-index:1;\" />");
                 celltext=zhuanhuan5.replace(new RegExp("zlcydgz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/1.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                  
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
                form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="1" '
                + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                + ' valign="middle">'
                + '<image width="250px" height="250px" src="images/sign/1.png" style=" position:relative; z-index:1;" /></td>';
              }
            }
          }

          form += '</tr>';
        }
        
        form += '</table>';
      }
    } else {
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '原始记录'));
      return '';
    }
    me.disphtml = form;
    return form;
  },
  
  GetTaskHtml: function (samplecode,taskid) {
    var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+samplecode);
    var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+taskid);
    var bustasktest = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+taskid).data;
    var bustasksample = tools.JsonGet('LabGetListBusTaskForAcc.do?bts.taskid='+taskid).data;
    var busgetdetails = tools.JsonGet('LabGetListBusGetDetailByTask.do?bgd.taskid='+taskid).data;
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
  },
  
  GetRecordHtml: function (sampletran) {
    var me = this, mep = me.tranPrefix;
    

    var records = tools.JsonGet(tools.GetUrl('DatGetBusRecordBySampleTran.do?br.sampletran='+sampletran)).data;

    if(records){
      if(records.length>0){
        
        var form = '';
        
        for(var k=0;k<records.length;k++){
          var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecord.do?br.recordid=') + records[k].recordid);
          var nowdate = new Date();
          if (item && item.record) {
            
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
              
              if (i == item.details.length - 1)
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              else
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
              
              form += '<tr>';
              
              for (i = 0; i < item.form.formwidth; i++) {
                //form += '<td width="' + width + 'px"; height="' + height + 'px" style="border: solid 1px Black;">' + (i + 1) + '</td>';
                form += '<td width="' + width + 'px"; height="1px" ></td>';
              }
              form += '</tr>';
              
              for (i = 1; i <= item.form.formlength; i++) {
                form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
                
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
                      
                      //公式变成图片
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
              
              form += '</table><br/>';
            }
            
          
          }
          else {
            tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
            return '';
          }
        }
      
        me.disphtml = form.replace(/\<br \/\>/g, '');
        
        var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
          '<tr><td align="center">' + form + '</td></tr></table>';
           me.disphtml = form;
             return form;
      }
    }
  }
});