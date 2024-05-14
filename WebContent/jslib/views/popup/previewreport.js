Ext.define('alms.previewreport', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, { 
      title: '试验报告',
      winWidth: 800,
      winHeight: 500,
      autoscroll: true
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;
    
    me.editControls = [
      {xtype:'label', id:mep + 'reportinfo', html:''}
    ];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews = [];
    me.enEdits = [];
  },
  
  OnSetData: function (formid) {
    var me = this, mep = me.mep;

    var html = me.GetReportHtml(formid);
    
    if (Ext.isEmpty(html)) return false;
    
    html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">'
              + '<tr><td align="center">'
              + html
              + '</td></tr></table>';
              
    Ext.fly(Ext.getCmp(mep + 'reportinfo').getEl()).update(html);
    
    me.winEdit.maximize();
  },
  
  GetReportHtml: function (formid) {
    var form = '';
    
    var i = 0, j = 0;
    var record;
    var rowspan = 0, colspan = 0;
    var height = 28, width = 36, nowheight = 0, nowwidth = 0;   
    var celltext = '';
    var style = '';
    var align = '';
    var tablewidth = 0, tableheight = 0;
    var zhuanhuan='';
    var zhuanhuan1='';
    var zhuanhuan2=''; 
    
    var item = tools.JsonGet(tools.GetUrl('FormGetSetFrmReport.do?freport.formid=') + formid);
      
    if (item && item.form && !Ext.isEmpty(item.form.formid)) {
      for (var page = 0; page < item.details.length; page++) {
        var nowdetails = item.details[page].datas;
        
        tablewidth = width * item.form.formwidth;
        tableheight = height * item.form.formlength + 1;
        
        if (page == item.details.length - 1)
          form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="' 
            + tablewidth + 'px" height="' + tableheight + '">';
        else
          form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always;" width="' 
            + tablewidth + 'px" height="' + tableheight + '">';

        form += '<tr>';

        for (i = 0; i < item.form.formwidth; i++) {
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
              if (record.fieldcode == 'reportrequest'){
                record.cellname='&emsp;&emsp;'+record.cellname;
              }

              switch (record.valuesource) {
                case '01' :
                  celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                  break;
                  
                case '05' :
                  zhuanhuan = record.cellname.replace(new RegExp("tips1"), "<image width=\"100px\" height=\"100px\" src=\"images/sign/tip1.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                  zhuanhuan1 = zhuanhuan.replace(new RegExp("tips2"), "<image width=\"210px\" height=\"100px\" src=\"images/sign/tip2.png\"  style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                  zhuanhuan2 = zhuanhuan1.replace(new RegExp("jczxbz"), "<image width=\"200px\" height=\"180px\" src=\"images/sign/reportlogo.png\"style=\"top:40px;left:0px; position:relative; z-index:1;\" />");
                  celltext = zhuanhuan2.replace(new RegExp("footjpg"), "<image width=\"160px\" height=\"160px\" src=\"images/sign/1.png\"style=\"top:-135px;left:0px; position:relative; z-index:1;\" />");
                  celltext = celltext.replace(new RegExp("br", "gm"), "<br/>");
                    break;
                  
                default :
                  celltext = '';
                  break;
              }

              celltext = record.prefixtext + celltext + record.postfixtext;

              style = '';
              align = '';

              switch (record.aligntype) {
                case '1' :
                  align = ' align="left" ';
                  if ((record.fieldcode == 'reportrequest')||(record.fieldcode == 'reportresult')) {
                    style += 'padding-left:20px;';
                    style += 'padding-right:20px;';
                  }else{
                    style += 'padding-left:3px;';
                  }
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
          
                  
              form += cellhtml;
            }
          }

          form += '</tr>';
        }

        form += '</table>';
      }
    } else {
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '报告模板'));
      return '';
    }
    
    return form;
  }
});