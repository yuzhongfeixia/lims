Ext.define('alms.labreportprint', {
  extend : 'gpersist.base.busform',
  
  constructor : function(config) {
    var me = this;
    Ext.apply(config, {
      editinfo : '试验报告打印',
      columnUrl : 'SysSqlColumn.do?sqlid=p_get_busreportaudit',
      storeUrl : 'DatSearchBusReportPrint.do',
      saveUrl : 'DatPrintBusReport.do',
      urlDetailColumn : 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail : 'LabGetListBusReportFile.do',
      hasDetail : true,
      hasDetailEdit : true,
      detailTitle : '试验报告附件',
      hasPage : true,
      hasExit : true,
      hasDetailGrid : true,
      hasDetailEditTool : false,
      idPrevNext : 'reportid',
      hasGridSelect : true,
      hasDetailCheck : false,
      hasPageDetail : false,
      hasPrevNext : true,
      detailTabs : 1
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items =
      [' ',{
        xtype : 'textfield',
        fieldLabel : '样品编号',
        labelWidth : 60,
        width : 200,
        maxLength : 40,
        name : 'searchsamplecode',
        id : mep + 'searchsamplecode',
        allowBlank : true
      },' ',{
        xtype : 'textfield',
        fieldLabel : '受检单位',
        labelWidth : 60,
        width : 200,
        maxLength : 40,
        name : 'searchtestedname',
        id : mep + 'searchtestedname',
        allowBlank : true
      },'-',tools.GetToolBarCombo('searchreportstatus', mep + 'searchreportstatus', 180, '报告状态', 60, tools.ComboStore('ReportStatus', gpersist.SELECT_MUST_VALUE)),' ',tools.GetToolBarCombo('searchispass', mep + 'searchispass', 180, '是否合格', 60, tools.ComboStore('SendType', gpersist.SELECT_MUST_VALUE)),' ',{
        id : mep + 'btnSearch',
        text : gpersist.STR_BTN_SEARCH,
        iconCls : 'icon-find',
        handler : me.OnSearch,
        scope : me
      },' ',{
        id : mep + 'btnEdit',
        text : '打印',
        iconCls : 'icon-deal',
        handler : me.OnShow,
        scope : me
      },'-',' ',{
        xtype : 'numberfield',
        fieldLabel : gpersist.STR_BTN_PAGESIZE,
        editable : false,
        labelWidth : gpersist.CON_PAGESIZE_LABLE,
        width : gpersist.CON_PAGESIZE_WIDTH,
        id : mep + 'btnPageSize',
        name : 'btnPageSize',
        step : gpersist.CON_PAGESIZE_STEP,
        value : gpersist.DEFAULT_PAGESIZE,
        minValue : gpersist.CON_PAGESIZE_MIN,
        maxValue : gpersist.CON_PAGESIZE_MAX
      },' ',{
        text : gpersist.STR_BTN_REFRESH_NOW,
        iconCls : 'icon-pagerefresh',
        handler : me.OnResetForm,
        scope : me
      }];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
      items : items,
      border : false
    });
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit : function() {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls =
      [{
        xtype : 'label',
        id : mep + 'busreporthtml',
        html : ''
      },{
        xtype : 'hiddenfield',
        name : 'breport.reportstatus',
        id : mep + 'reportstatus'
      },{
        xtype : 'hiddenfield',
        name : 'breport.reportid',
        id : mep + 'reportid'
      },{
        xtype : 'hiddenfield',
        name : 'breport.samplecode',
        id : mep + 'samplecode'
      },{
        xtype : 'hiddenfield',
        name : 'breport.taskid',
        id : mep + 'taskid'
      },{
        xtype : 'hiddenfield',
        name : 'breport.deal.action',
        id : mep + 'datadeal'
      }];
    
  },
  
  OnAfterCreateEditToolBar : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems =
      [' ',{
        id : mep + 'btnPrintRecord',
        text : '打印试验报告',
        iconCls : 'icon-print',
        handler : me.OnPrintReport,
        scope : me
      },
//      ' ',{
//        id : mep + 'btnTurnReport',
//        text : '调整试验报告',
//        iconCls : 'icon-print',
//        handler : me.OnTurnRecord,
//        scope : me
//      },
      '-',' ',{
        id : mep + 'btnPrevRecord',
        text : gpersist.STR_BTN_PREVRECORD,
        iconCls : 'icon-prev',
        handler : me.OnPrevRecord,
        scope : me
      },' ',{
        id : mep + 'btnNextRecord',
        text : gpersist.STR_BTN_NEXTRECORD,
        iconCls : 'icon-next',
        handler : me.OnNextRecord,
        scope : me
      },'-',' ',{
        id : mep + 'btnClose',
        text : gpersist.STR_BTN_RETURNLIST,
        iconCls : 'icon-close',
        handler : me.OnFormClose,
        scope : me
      }];
  },
  
  OnBeforeSearch : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function(store, options) {
        Ext.apply(store.proxy.extraParams, {
          'breport.reportstatus' : tools.GetEncode(tools.GetValue(mep + 'searchreportstatus')),
          'breport.ispass' : tools.GetEncode(tools.GetValue(mep + 'searchispass') == '-2' ? '' : (tools.GetValue(mep + 'searchispass') == '1' ? '合格' : '不合格')),
          'breport.samplecode' : tools.GetValue(mep + 'searchsamplecode'),
          'breport.testedname' : tools.GetValue(mep + 'searchtestedname')
        });
      });
    };
  },
  
  OnAfterCreateEdit : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '70%';
  },
  
  OnLoadData : function(record) {
    var me = this, mep = me.tranPrefix;
    me.cancelRecord = record;
    tools.SetValue(mep + 'reportid', record.reportid);
    tools.SetValue(mep + 'taskid', record.taskid);
    tools.SetValue(mep + 'samplecode', record.samplecode);
    tools.SetValue(mep + 'modifydesc', record.modifydesc);
    var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + record.samplecode);
    if (records && records.data) {
      var nowdate = new Date();
      var i = 0, j = 0;
      var record;
      var rowspan = 0, colspan = 0;
      var height = 25, width = 36, nowheight = 0, nowwidth = 0;
      var form = '';
      var celltext = '';
      var style = '';
      var align = '';
      var zhuanhuan = '';
      var zhuanhuan1 = '';
      var zhuanhuan2 = '';
      var doublehtml = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
            doublehtml += '<tr><td width="36px" ; height="27px"></td></tr>';
            doublehtml += '<tr><td width="36px" ; height="27px"></td></tr>';
            doublehtml += '<tr><td width="36px" ; height="27px"></td></tr>';
            doublehtml += '<tr><td width="36px" ; height="27px"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:STZhongsong;font-size:24px;"  colspan="26" align="center" valign="middle"><h2>注 意 事 项</h2></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">l、报告无 ”检验检测专用章” 或检验检测单位公章无效。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">2、复制报告未重新加盖”检验检测专用章” 或检验检测单位</td></tr>';
            doublehtml += '<tr><td width="936px" height="10px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">&emsp;公章无效。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';

            
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">3、报告无制表、审核、批准人签单无效。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">4、报告涂改无效。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">5、对检验检测报告若有异议，应于收到报告之日起十五日内向</td></tr>';
            doublehtml += '<tr><td width="936px" height="10px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">&emsp;检验检测单位提出。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">6、客户送样委托检验， 仅对来样负责。</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle"></td></tr>';
            
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">7、未经本中心同意， 该检验检测报告不得用于商业宣传。</td></tr>';
            
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">地 址： 南京市草场门大街124号江苏农业检测大楼</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">电 话 ( Tel ):(025) 86263563   传 真(Fax):(025) 86229784</td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;" colspan="26" align="left" valign="middle"></td></tr>';
            doublehtml += '<tr><td width="936px" height="27px" style="font-weight:bold; font-family:FangSong;font-size:20px;padding-left:10px;padding-right:50px;" colspan="26" align="left" valign="middle">邮政编码 ( Postcode) : 210036</td></tr>';
            
      doublehtml += '</table>';
      for (var n = 0; n < records.data.length; n++) {
        var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + tools.GetEncode(tools.GetEncode(records.data[n].reportid)));
        
        var busget = tools.JsonGet(tools.GetUrl('BasSearchBasSample.do?bsample.sampleid=')+ item.record.sampleid +'&page=1&start=0&limit=50').data[0];
        var bggz;
        if(busget.samplecatalog=='0301'){
          bggz='ny';
        }else if(busget.samplecatalog=='0201'){
          bggz='hf';
        }else{
          bggz='jy';
        }
        console.log(bggz)
        if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
          for (var page = 0; page < item.details.length; page++) {
            var nowdetails;
            var fl1;
            var fl2;
            if (page == 0) {
              fl1 = 2 * item.form.formlength / 3;
//              if (item.form.formid = '0000000110') {
//                //                item.details.length=1;
//                fl1 = item.details[page].datas[(item.details[page].datas.length) - 1].endrow + 1;
//              }
              
              nowdetails = item.details[page].datas;
              if (page == item.details.length - 1) {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
              }
              form += '<tr>';
              for (i = 0; i < item.form.formwidth; i++) {
                form += '<td width="' + width + 'px"; height="' + height + 'px" ></td>'; 
              }
              form += '</tr>';
             
              for (i = 1; i <= fl1; i++) {
                 
                if(i==40){
                //调整内容页距离上边距
                  form += '<tr><td width="36px"; height="' + item.record.onespacing + 'px" ></td></tr>';
//                  form += '<tr><td width="36px"; height="75px" ></td></tr>';
                }
                
                form += '<tr><td ></td>';
                
                for (j = 0; j < nowdetails.length; j++) {
                  record = nowdetails[j];
                  if (record.beginrow == i) {
                    rowspan = record.endrow - record.beginrow + 1;
                    nowheight = height * rowspan;
                    colspan = record.endcolumn - record.begincolumn + 1;
                    nowwidth = width * colspan;
                    celltext = '';
                    style = '';
                    switch (record.valuesource) {
                      case '02':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'reportresult') {
                          var date = new Date();
                          celltext ='<br/>&emsp;&emsp;'+ record.celltext + '<br/><image width=\"170px\" height=\"170px\"  src=\"images/sign/' + bggz + '.png\"style=\"top:50px;left:300px; position:relative; z-index:1;\" /><br/>' + '&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;' + '&emsp;&emsp;&emsp;' + '（检验报告专用章）<br/>' + '&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;' + '&emsp;&emsp;&emsp;&emsp;&emsp;' + '签 发 日 期：' + date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日<br/><br/>';
                        }
                        
                        if((record.fieldcode == 'tranidcn' )&&(record.cellserial == '7' )) {
                          celltext = '<b><u>' + record.celltext + '</u></b>';
                        }
                        
                        break;
                      case '03':
                        celltext = '';
                        break;
                      case '01':
                        celltext = record.cellname.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'reportrequest') {
                          celltext ='&emsp;&emsp;'+ record.cellname;
                          }
                        break;
                      case '04':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      case '05':
                        zhuanhuan = record.cellname.replace(new RegExp("tips1"), "<image width=\"110px\" height=\"100px\" src=\"images/sign/tip1.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan1 = zhuanhuan.replace(new RegExp("tips2"), "<image width=\"210px\" height=\"100px\" src=\"images/sign/tip2.png\"  style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan2 = zhuanhuan1.replace(new RegExp("jczxbz"), "<image width=\"200px\" height=\"180px\" src=\"images/sign/reportlogo.png\"style=\"top:40px;left:0px; position:relative; z-index:1;\" />");
                        celltext = zhuanhuan2.replace(new RegExp("footjpg"), "<image width=\"170px\" height=\"170px\" src=\"images/sign/1.png\"style=\"top:110px;left:0px; position:relative; z-index:1;\" />");
                        celltext = celltext.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      default:
                        break;
                    }
                    celltext = record.prefixtext + celltext + record.postfixtext;
                    
                    align = '';
                    switch (record.aligntype) {
                      case '1':
                        align = ' align="left" ';
                        if ((record.fieldcode == 'reportrequest')||(record.fieldcode == 'reportresult')) {
                          style += 'padding-left:20px;';
                          style += 'padding-right:20px;';
                        }else{
                          style += 'padding-left:3px;';
                        }
                       
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
                    
                    if (record.isborder)
                      style += 'border: solid 1px Black;';
                      
                    
                    if (record.isline && (record.isborder <= 0))
                      style += 'border-bottom: solid 1px Black;';
                    
                    if (record.isbold)
                      style += 'font-weight:bold;';
                    
                    style += 'font-size:' + record.fontsize + 'px;';
                    //字体
                    style += 'font-family:' + record.fonttypesign + ';';
                    
                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' + ' style="' + style + '" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                    
                    if (record.valuesource == '04') {
                      if (record.fieldcode == '{actreportid}') {
                        if (!Ext.isEmpty(celltext))
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                        else
                          form += cellhtml;
                      } else if (Ext.isEmpty(record.classsource)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + 40 + 'px" height="' + 25 + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      } else if (!Ext.isEmpty(celltext)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle" style="padding-top:3px;">' + '<image width="' + 80 + 'px" height="' + 50 + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                      } else
                        form += cellhtml;
                    } else {
                      form += cellhtml;
                    }
                    
                  }
                }
                form += '</tr>';
                
              }
              
              
            } else {
              fl2 = item.form.formlength / 3;
              nowdetails = item.details[page].datas;
              if (page == item.details.length - 1) {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; page-break-after: always; border-collapse: collapse;">';
              }
              form += '<tr>';
              for (i = 0; i < item.form.formwidth; i++) {
                form += '<td width="' + width + 'px"; height="0px" ></td>';
              }
              form += '</tr>';
              //调整内容页距离上边距
              form += '<tr><td width="36px"; height="' + item.record.twospacing + 'px" ></td></tr>';
             
//              form += '<tr><td width="36px" ; height="54px"></td></tr>';
              for (i = fl1 + 1; i <= fl1 + fl2; i++) {
                form += '<tr><td></td>';
                
                for (j = 0; j < nowdetails.length; j++) {
                  record = nowdetails[j];
                  if (record.beginrow == i) {
                    rowspan = record.endrow - record.beginrow + 1;
                    nowheight = height * rowspan;
                    colspan = record.endcolumn - record.begincolumn + 1;
                    nowwidth = width * colspan;
                    celltext = '';
                    switch (record.valuesource) {
                      case '02':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'tranidcn') {
                          celltext = '<b>' + record.celltext + '</b>';
                        }
                        break;
                      case '03':
                        celltext = '';
                        break;
                      case '01':
                        celltext = record.cellname.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      case '04':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");

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
                    
                    if (record.isborder)
                      style += 'border: solid 1px Black;';
                    
                    if (record.isline && (record.isborder <= 0))
                      style += 'border-bottom: solid 1px Black;';
                    
                    if (record.isbold)
                      style += 'font-weight:bold;';
                    
                    style += 'font-size:' + record.fontsize + 'px;';
                    style += 'font-family:' + record.fonttypesign + ';';
                    
                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' + ' style="' + style + '" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                    
                    if (record.valuesource == '04') {
                      if (  record.fieldcode == '{actreportid}') {
                        if (!Ext.isEmpty(celltext))
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                        else
                          form += cellhtml;
                      } else if (Ext.isEmpty(record.classsource)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + 20 + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      } else if (!Ext.isEmpty(celltext)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image height="' + 20 + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                      } else
                        form += cellhtml;
                    } else {
                      form += cellhtml;
                    }
                  }
                }
                form += '</tr>';
              }
              
              fl1 = fl1 + fl2;
            }
            
            form += '</table>';
          }
          
        } else {
          tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '试验报告'));
          return '';
        }
      }
      
      me.disphtml += form.replace(/\<br \/\>/g, '');
      
      me.disphtml = form + doublehtml;
      
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%; border-collapse: collapse;">' + '<tr><td align="center">' + form + '</td></tr></table>';
      
      Ext.fly(Ext.getCmp(mep + 'busreporthtml').getEl()).update(html);
      
      me.OnDetailRefresh();
      
      return true;
    }
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusReportFile.do?bref.tranid=' + tools.GetValue(mep + 'reportid'));
      me.plDetailGrid.store.load();
    }
    ;
  },
  
  OnAfterCreateDetailToolBar : function() {
    var me = this;
    
    //去除明细中新增、删除按钮
    me.deitems = [];
  },
  
  OnCreatePrompt : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'winPrompt')) {
      Ext.getCmp(mep + 'winPrompt').destroy();
    }
    ;
    
    if (!me.plwinPrompt) {
      me.plwinPrompt = Ext.create('Ext.form.Panel', {
        id : mep + 'plwinPrompt',
        fieldDefaults : {
          width : 370,
          labelSeparator : '：',
          labelWidth : 90,
          labelPad : 0,
          labelStyle : 'font-weight:bold;'
        },
        title : '',
        border : false,
        bodyStyle : 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width : 400,
        defaultType : 'textfield',
        items :
          [{
            fieldLabel : '密码',
            name : 'signerpassword',
            id : mep + 'signer_passwd',
            type : 'password',
            inputType : 'password',
            selectOnFocus : true,
            labelStyle : 'font-weight:bold;',
            blankText : Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
            allowBlank : false
          },{
            xtype : 'hiddenfield',
            name : 'gfo.tranid',
            id : mep + 'tranid'
          }],
        listeners : {
          afterRender : function(form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter : me.OnSavePasswd,
              scope : me
            });
          }
        }
      });
    }
    
    if (!me.winPrompt) {
      me.winPrompt = Ext.create('Ext.window.Window', {
        id : mep + 'winPrompt',
        title : '权限密码',
        width : 400,
        height : 100,
        maximizable : true,
        modal : true,
        layout : 'fit',
        plain : false,
        buttonAlign : 'center',
        closable : true,
        closeAction : 'hide',
        border : false,
        items :
          [me.plwinPrompt],
        buttons :
          [{
            text : gpersist.STR_BTN_SUBMIT,
            id : mep + 'btnSave',
            handler : me.OnSave,
            scope : me
          },{
            text : gpersist.STR_BTN_CLOSE,
            handler : function() {
              me.winPrompt.hide();
            }
          }]
      });
    }
  },
  
  OnPrintReport : function() {
    var me = this, mep = me.tranPrefix;
    
    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, "确定打印试验报告？", function(btn) {
      
      if (btn == 'yes') {
        if (!me.winPrompt)
          me.OnCreatePrompt();
        
        if (me.winPrompt) {
          me.winPrompt.show();
          Ext.getCmp(mep + 'signer_passwd').reset();
        }
      }
      tools.BtnsEnable(
        ['btnSave'], mep);
      
    });
  },
  OnTurnRecord : function() {
    var me = this, mep = me.tranPrefix;
//    
     tools.GetPopupWindow('alms', 'editreportdetail', 
      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'tr',dataDeal: gpersist.DATA_DEAL_NEW})
  
      Ext.Ajax.request({
        url: 'jslib/views/popup/editreportdetail.js',
        params:{reportid: tools.GetValue(mep + 'reportid')},
        async: false,
        method : 'GET',
        success: function (response, opts) {
          eval(response.responseText);          
        },
        failure: function (response) { }
      });
      
      win = Ext.create('alms.editreportdetail');
  
  },
  
  //  OnPrint: function () {
  //    var me = this, mep = me.tranPrefix;
  //    console.log(me.disphtml)
  //    if (!Ext.isEmpty(me.disphtml)) {
  //      var LODOP = getLodop();
  //      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
  //      LODOP.PRINT_INIT("试验报告打印");
  //      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
  //      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.disphtml);
  //      LODOP.SET_PRINTER_INDEXA(-1);
  //      LODOP.PREVIEW();//预览功能
  ////      LODOP.PRINT();//打印功能
  //    }
  //    return;
  //  },
  OnPrint : function() {
    var me = this, mep = me.tranPrefix;
    
    console.log(me.disphtml)
    Ext.ux.Print.ReportPrint.print(me.disphtml);
  },
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.saveParams = {
      signerpassword : Ext.getCmp(mep + 'signer_passwd').getValue()
    };
  },
  
  OnListSelect : function(view, record) {
    alms.PopupFileShow(record.data.attachtypename + '附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
  },
  
  OnSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.isNowCopy = false;
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          //打印
          me.OnPrint();
          tools.alert(Ext.String.format("试验报告打印完成！", me.editInfo));
          
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnBeforeSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep + 'reportstatus', '12');
    
    return true;
  }

});
