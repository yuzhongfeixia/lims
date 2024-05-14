Ext.define('alms.manbasreport', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '检测报告查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busreportaudit',
      storeUrl: 'DatSearchBusReportPrint.do',
      saveUrl: 'DatPrintBusReport.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusReportFile.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '试验报告附件',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: false,
      idPrevNext: 'reportid',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      hasPrevNext:true,
      detailTabs: 1
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '制表人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranuser', id: mep + 'searchtranuser', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '校核人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchapproveuser', id: mep + 'searchapproveuser', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '批准人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchaduituser', id: mep + 'searchaduituser', allowBlank: true },
      '-', tools.GetToolBarCombo('searchreportstatus', mep + 'searchreportstatus', 180, '报告状态', 60, tools.ComboStore('ReportStatus', gpersist.SELECT_MUST_VALUE))
    ];
    
    var items1 = [           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    
    me.tbGrid.add(toolbar);
    me.tbGrid.add(toolbar1);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'busreporthtml', html: '' },
      {xtype:'hiddenfield',name:'breport.reportstatus',id: mep + 'reportstatus'},
      {xtype:'hiddenfield',name:'breport.reportid',id: mep + 'reportid'},
      {xtype:'hiddenfield',name:'breport.samplecode',id: mep + 'samplecode'},
      {xtype:'hiddenfield',name:'breport.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'breport.deal.action',id: mep + 'datadeal'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印试验报告', iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'breport.reportstatus': tools.GetEncode(tools.GetValue(mep + 'searchreportstatus')),
          'breport.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'breport.testedname': tools.GetEncode(tools.GetValue(mep + 'searchtestedname')),
          'breport.tranusername': tools.GetEncode(tools.GetValue(mep + 'searchtranuser')),
          'breport.approveusername': tools.GetEncode(tools.GetValue(mep + 'searchapproveuser')),
          'breport.aduitusername': tools.GetEncode(tools.GetValue(mep + 'searchaduituser'))
        });
      });
    };
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '70%';
  },
  
//  OnLoadData: function (record) {
//    var me = this, mep = me.tranPrefix;
//    me.cancelRecord = record;
//    tools.SetValue(mep + 'reportid', record.reportid);
//    tools.SetValue(mep + 'taskid', record.taskid);
//    tools.SetValue(mep + 'samplecode', record.samplecode);
//    tools.SetValue(mep + 'modifydesc', record.modifydesc);
//    var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + record.samplecode);
//    if (records && records.data) {
//      var nowdate = new Date();
//      var i = 0, j = 0;
//      var record;
//      var rowspan = 0, colspan = 0;
//      var height = 28, width = 36, nowheight = 0, nowwidth = 0;
//      var form = '';
//      var celltext = '';
//      var style = '';
//      var align = '';
//      
//      for (var n = 0; n < records.data.length; n++) {
//        var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + tools.GetEncode(tools.GetEncode(records.data[n].reportid)));
//
//        if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
//          for(var page = 0; page < item.details.length; page++){
//            var nowdetails;
//            var fl1;
//            var fl2;
//            if(page == 0){
//              fl1 = 2*item.form.formlength/3;
//
//              nowdetails = item.details[page].datas;
//              if (page == item.details.length - 1){
//                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
//              } else {
//                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
//              }
//              form += '<tr>';
//              for (i = 0; i < item.form.formwidth; i++) {
//                form += '<td width="' + width + 'px"; height="0px" ></td>';
//              }
//              form += '</tr>';
//              for (i = 1; i <= fl1; i++) {
//                form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
//                for (j = 0; j < nowdetails.length; j++) {
//                  record = nowdetails[j];
//                  if (record.beginrow == i) {
//                    rowspan = record.endrow - record.beginrow + 1;
//                    nowheight = height * rowspan;
//                    colspan = record.endcolumn - record.begincolumn + 1;
//                    nowwidth = width * colspan;
//                    celltext = '';
//                    switch (record.valuesource) {
//                      case '02' :
//                        celltext = record.celltext;
//                        break;
//                      case '03' :
//                        celltext = '';
//                        break;
//                      case '01' :
//                        celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
//                        break;
//                      case '04' :
//                        celltext = record.celltext;
//                        break;
//                      default :
//                        break;
//                    }
//                    celltext = record.prefixtext + celltext + record.postfixtext;
//                    style = '';
//                    align = '';
//                    switch (record.aligntype) {
//                      case '1' :
//                        align = ' align="left" ';
//                        style += 'padding-left:3px;';
//                        break;
//                      case '2' :
//                        align = ' align="center" ';
//                        break;
//                      case '3' :
//                        align = ' align="right" ';
//                        style += 'padding-right:3px;';
//                        break;
//                      default :
//                        break;
//                    }
//  
//                    if (record.isborder > 0)
//                      style += 'border: solid ' + record.isborder
//                          + 'px Black;';
//  
//                    if (record.isline && (record.isborder <= 0))
//                      style += 'border-bottom: solid 1px Black;';
//  
//                    if (record.isbold)
//                      style += 'font-weight:bold;';
//  
//                    style += 'font-size:' + record.fontsize + 'px;';
//  
//                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
//                        + ' style="' + style + '" '
//                        + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                        + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                        + ' valign="middle">' + 
//                        (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
//                        
//                    if (record.valuesource == '04') {
//                      if (record.fieldcode == '{actreportid}') {
//                        if (!Ext.isEmpty(celltext)) 
//                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
//                            + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
//                         else
//                           form += cellhtml;
//                      }
//                      else if (Ext.isEmpty(record.classsource)) {
//                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
//                      }
//                      else if (!Ext.isEmpty(celltext)) {
//                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
//                      }
//                      else
//                        form += cellhtml;
//                    }
//                    else {
//                      form += cellhtml;
//                    }
//                  }
//                }
//                form += '</tr>';
//              }
//            } else {
//              fl2 = item.form.formlength/3
//              nowdetails = item.details[page].datas;
//              if (page == item.details.length - 1){
//                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
//              } else {
//                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; page-break-after: always; border-collapse: collapse;">';
//              }
//              form += '<tr>';
//              for (i = 0; i < item.form.formwidth; i++) {
//                form += '<td width="' + width + 'px"; height="0px" ></td>';
//              }
//              form += '</tr>';
//              for (i = fl1+1; i <= fl1+fl2; i++) {
//                form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
//                for (j = 0; j < nowdetails.length; j++) {
//                  record = nowdetails[j];
//                  if (record.beginrow == i) {
//                    rowspan = record.endrow - record.beginrow + 1;
//                    nowheight = height * rowspan;
//                    colspan = record.endcolumn - record.begincolumn + 1;
//                    nowwidth = width * colspan;
//                    celltext = '';
//                    switch (record.valuesource) {
//                      case '02' :
//                        celltext = record.celltext;
//                        break;
//                      case '03' :
//                        celltext = '';
//                        break;
//                      case '01' :
//                        celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
//                        break;
//                      case '04' :
//                        celltext = record.celltext;
//                        break;
//                      default :
//                        break;
//                    }
//                    celltext = record.prefixtext + celltext + record.postfixtext;
//                    style = '';
//                    align = '';
//                    switch (record.aligntype) {
//                      case '1' :
//                        align = ' align="left" ';
//                        style += 'padding-left:3px;';
//                        break;
//                      case '2' :
//                        align = ' align="center" ';
//                        break;
//                      case '3' :
//                        align = ' align="right" ';
//                        style += 'padding-right:3px;';
//                        break;
//                      default :
//                        break;
//                    }
//  
//                    if (record.isborder > 0)
//                      style += 'border: solid ' + record.isborder
//                          + 'px Black;';
//  
//                    if (record.isline && (record.isborder <= 0))
//                      style += 'border-bottom: solid 1px Black;';
//  
//                    if (record.isbold)
//                      style += 'font-weight:bold;';
//  
//                    style += 'font-size:' + record.fontsize + 'px;';
//  
//                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" '
//                        + ' style="' + style + '" '
//                        + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                        + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                        + ' valign="middle">' + 
//                        (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
//                        
//                    if (record.valuesource == '04') {
//                      if (record.fieldcode == '{actreportid}') {
//                        if (!Ext.isEmpty(celltext)) 
//                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
//                            + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
//                         else
//                           form += cellhtml;
//                      }
//                      else if (Ext.isEmpty(record.classsource)) {
//                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
//                      }
//                      else if (!Ext.isEmpty(celltext)) {
//                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
//                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
//                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
//                            + ' valign="middle">'
//                            + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
//                      }
//                      else
//                        form += cellhtml;
//                    }
//                    else {
//                      form += cellhtml;
//                    }
//                  }
//                }
//                form += '</tr>';
//              }
//              fl1 = fl1+fl2;
//            }
//            form += '</table>';
//          }
//          
//        } else {
//          tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '试验报告'));
//          return '';
//        }
//      }
//      
//      me.disphtml += form.replace(/\<br \/\>/g, '');
//      
//      me.disphtml = form;
//      
//      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%; border-collapse: collapse;">' + 
//        '<tr><td align="center">' + form + '</td></tr></table>';
//        
//      Ext.fly(Ext.getCmp(mep + 'busreporthtml').getEl()).update(html);
//      
//      me.OnDetailRefresh();
//        
//      return true;
//    }
//  },
  OnLoadData: function (record) {

    var me = this, mep = me.tranPrefix;
    me.cancelRecord = record;
    
    tools.SetValue(mep + 'reportid', record.reportid);
    tools.SetValue(mep + 'reportstatus', record.reportstatus);
    tools.SetValue(mep + 'taskid', record.taskid);
    tools.SetValue(mep + 'samplecode', record.samplecode);
    
    var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + record.samplecode);
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
      var zhuanhuan='';
      var zhuanhuan1='';
      var zhuanhuan2=''; 
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
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
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
                      case '05' :
                        zhuanhuan=record.cellname.replace(new RegExp("tips1"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/tip1.jpg\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan1=zhuanhuan.replace(new RegExp("tips2"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/tip2.jpg\"  style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan2=zhuanhuan1.replace(new RegExp("jczxbz"),"<image width=\"190px\" height=\"190px\" src=\"images/sign/2.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        celltext=zhuanhuan2.replace(new RegExp("footjpg"),"<image width=\"600px\" height=\"190px\" src=\"images/sign/foot.jpg\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                          
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
              fl2 = item.form.formlength/3;
              nowdetails = item.details[page].datas;
              if (page == item.details.length - 1){
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
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
      
      me.disphtml += form.replace(/\<br \/\>/g, '');
      
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
        '<tr><td align="center">' + form + '</td></tr></table>';
      Ext.fly(Ext.getCmp(mep + 'busreporthtml').getEl()).update(html);
      
      me.OnDetailRefresh();
      
      return true;
    }
  
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusReportFile.do?bref.tranid='+ tools.GetValue(mep + 'reportid')) ;
      me.plDetailGrid.store.load();
    };
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    
    //去除明细中新增、删除按钮
    me.deitems = [];
  },
  
  OnCreatePrompt: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'winPrompt')) {
      Ext.getCmp(mep + 'winPrompt').destroy();
    };
    
    if (!me.plwinPrompt) {
      me.plwinPrompt = Ext.create('Ext.form.Panel', {
        id: mep + 'plwinPrompt',
        fieldDefaults: {
          width: 370,
          labelSeparator: '：',
          labelWidth: 90,
          labelPad: 0,
          labelStyle: 'font-weight:bold;'
        },
        title: '',
        border: false,
        bodyStyle: 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width: 400,
        defaultType: 'textfield',
        items: [{
          fieldLabel: '密码',
          name: 'signerpassword',
          id: mep + 'signer_passwd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
          allowBlank: false
        },
        {
          xtype:'hiddenfield',name:'gfo.tranid',id: mep + 'tranid'
        }],
        listeners: {
          afterRender: function (form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter: me.OnSavePasswd,
              scope: me
            });
          }
        }
      });
    }
    
    if (!me.winPrompt) {
      me.winPrompt = Ext.create('Ext.window.Window', {
        id: mep + 'winPrompt',
        title: '权限密码',
        width: 400,
        height: 100,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plwinPrompt],
        buttons: [{ text: gpersist.STR_BTN_SUBMIT, id: mep +  'btnSave', handler: me.OnSave, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winPrompt.hide(); } }]
      });
    }
  },
  
  OnPrint: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.disphtml)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("试验报告打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.disphtml);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
    }
    return;
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.saveParams = {signerpassword: Ext.getCmp(mep + 'signer_passwd').getValue() };
  },
  
  OnListSelect: function (view, record) {
    alms.PopupFileShow(record.data.attachtypename+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
  },
  
  OnSave: function () {
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
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep+'reportstatus','12');
    
    return true;
  }
  
});
