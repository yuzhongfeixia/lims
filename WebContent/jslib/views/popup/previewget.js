Ext.define('alms.previewget', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, { 
      title: '模板预览',
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
      ' ', { id: mep + 'btnPrint', text: '打印', iconCls: 'icon-print', handler: me.OnPrint, scope: me },    
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
  
  OnSetData: function (formid,tranid,gettype) {
    var me = this, mep = me.mep;
   
    var html = me.GetRecordHtml(formid,tranid,gettype);
    
    if (Ext.isEmpty(html)) return false;
    
    html = '<table cellspacing="0" align="center" cellpadding="0" border="0" style="width:50%; border-collapse: collapse;">'
              + '<tr><td align="center">'
              + html
              + '</td></tr></table>';
              
    Ext.fly(Ext.getCmp(mep + 'recordinfo').getEl()).update(html);
    
    me.winEdit.maximize();
  },
  
  GetRecordHtml: function (formid,tranid,gettype) {
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
  
  OnPrint: function () {
    var me = this, mep = me.tranPrefix;
       
    
    if (!Ext.isEmpty(me.disphtml)) {
    	 Ext.ux.Print.LimsPrint.print(me.disphtml);
//      var LODOP = getLodop();
//      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
//      LODOP.PRINT_INIT("试验报告打印");
//      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
//      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.disphtml);
//      LODOP.SET_PRINTER_INDEXA(-1);
//      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
    }
    
    return;
    
  }
});