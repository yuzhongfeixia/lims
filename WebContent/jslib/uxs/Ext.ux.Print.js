// add by cloudy 20141101
Ext.define("Ext.ux.Print.HtmlPrint", {

  statics : {
    print : function(printhtml) {

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<link href="jslib/public/lms.css" rel="stylesheet" type="text/css" media="screen,print" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += '<div><table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:100%"><tr><td valign="top" align="center">';
      html += printhtml;
      html += '</td></tr></table></div>';
      html += '<script lageuage="text/javascript"> function printWindow() { if (!(factory && factory.printing)) return false;factory.printing.header = ""; ' + 'factory.printing.footer = "";' + 'factory.printing.portrait = ' + this.portrait + ';' + 'factory.printing.leftMargin = ' + this.leftmargin + ';' + 'factory.printing.topMargin = ' + this.topmargin + ';' + 'factory.printing.rightMargin = ' + this.rightmargin + ';' + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' + 'factory.printing.Print(false);window.close();}</script>';
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

Ext.define("Ext.ux.Print.LimsPrint", {

  statics : {
    print : function(printhtml) {

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<link href="jslib/public/lms.css" rel="stylesheet" type="text/css" media="screen,print" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += printhtml;
      html += '<script lageuage="text/javascript"> function printWindow() { if (!(factory && factory.printing)) return false;factory.printing.header = ""; ' + 'factory.printing.footer = "";' + 'factory.printing.portrait = ' + this.portrait + ';' + 'factory.printing.leftMargin = ' + this.leftmargin + ';' + 'factory.printing.topMargin = ' + this.topmargin + ';' + 'factory.printing.rightMargin = ' + this.rightmargin + ';' + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' + 'factory.printing.Print(false);window.close();}</script>';
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

Ext.define("Ext.ux.Print.PrintCarIn", {

  statics : {
    print : function(tele, carout) {
      var body = '';

      body += '<span Style="position:absolute;left:33mm;top:17.5mm;font-size:14px;">' + tele.teleid + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:25mm;font-size:14px;">' + tele.custname + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:25mm;font-size:14px;">' + tele.linktele + '</span>';
      body += '<span Style="position:absolute;left:172mm;top:25mm;font-size:14px;">' + tele.relaname + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:34mm;font-size:12px;">' + Ext.Date.format(tele.gagego, 'Y年m月d日 h:i') + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:34mm;font-size:14px;">' + tele.cartypename + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:44.5mm;font-size:14px;">' + tele.teleaddress + '</span>';
      body += '<span Style="position:absolute;left:31mm;top:53.5mm;font-size:14px;">' + tele.cardesc + '</span>';      
      body += '<span Style="position:absolute;left:50mm;top:62mm;font-size:14px;">' + carout.drivername + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:62mm;font-size:14px;">' + carout.aceeptname + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:71mm;font-size:14px;">' + carout.carcard + '</span>';
      body += '<span Style="position:absolute;left:55mm;top:66mm;font-size:14px;">' + tele.deadname + '</span>';
      body += '<span Style="position:absolute;left:113mm;top:66mm;font-size:14px;">' + tele.sexname + '</span>';
      body += '<span Style="position:absolute;left:161mm;top:66mm;font-size:14px;">' + tele.deadage + '</span>';
      body += '<span Style="position:absolute;left:55mm;top:75mm;font-size:14px;">' + tele.deaddept + '</span>';
      body += '<span Style="position:absolute;left:161mm;top:75mm;font-size:14px;">' + tele.deadreasonname + '</span>';

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += body;
      var bottomjs = '<script lageuage="text/javascript"> function printWindow () ' +
      '{ if (!(factory && factory.printing && factory.printing.header)) return false; factory.printing.header = ""; ' +
      'factory.printing.footer = "' + footer + '";' +
      'factory.printing.portrait = ' + this.portrait + ';' +
      'factory.printing.leftMargin = ' + this.leftmargin + ';' +
      'factory.printing.topMargin = ' + this.topmargin + ';' +
      'factory.printing.rightMargin = ' + this.rightmargin + ';' +
      'factory.printing.bottomMargin = ' + this.bottommargin + ';' +
      'factory.printing.Print(true); ' +
      'window.close(); }</script></body></html>';

      var win = window.open('', 'print', 'letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0', 'replace');
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

Ext.define("Ext.ux.Print.PrintConsign", {

  statics : {
    print : function(tran) {
      var body = '';

      body += '<span Style="position:absolute;left:51mm;top:21mm;font-size:12px;">' + tran.custom.custname + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:25mm;font-size:14px;">' + tele.custname + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:25mm;font-size:14px;">' + tele.linktele + '</span>';
      body += '<span Style="position:absolute;left:172mm;top:25mm;font-size:14px;">' + tele.relaname + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:34mm;font-size:12px;">' + Ext.Date.format(tele.gagego, 'Y年m月d日 h:i') + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:34mm;font-size:14px;">' + tele.cartypename + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:44.5mm;font-size:14px;">' + tele.teleaddress + '</span>';
      body += '<span Style="position:absolute;left:31mm;top:53.5mm;font-size:14px;">' + tele.cardesc + '</span>';      
      body += '<span Style="position:absolute;left:50mm;top:62mm;font-size:14px;">' + carout.drivername + '</span>';
      body += '<span Style="position:absolute;left:125mm;top:62mm;font-size:14px;">' + carout.aceeptname + '</span>';
      body += '<span Style="position:absolute;left:50mm;top:71mm;font-size:14px;">' + carout.carcard + '</span>';
      body += '<span Style="position:absolute;left:55mm;top:66mm;font-size:14px;">' + tele.deadname + '</span>';
      body += '<span Style="position:absolute;left:113mm;top:66mm;font-size:14px;">' + tele.sexname + '</span>';
      body += '<span Style="position:absolute;left:161mm;top:66mm;font-size:14px;">' + tele.deadage + '</span>';
      body += '<span Style="position:absolute;left:55mm;top:75mm;font-size:14px;">' + tele.deaddept + '</span>';
      body += '<span Style="position:absolute;left:161mm;top:75mm;font-size:14px;">' + tele.deadreasonname + '</span>';

      var html = '';
      html += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
      html += '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />';
      html += '<style type="text/css">table { font-size: 16px;}</style><title></title></head>';
      html += '<body onload="printWindow();">';
      html += '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>';
      html += body;
      html += '<script lageuage="text/javascript"> function printWindow() { if (!(factory && factory.printing)) return false;factory.printing.header = ""; ' + 'factory.printing.footer = "";' + 'factory.printing.portrait = ' + this.portrait + ';' + 'factory.printing.leftMargin = ' + this.leftmargin + ';' + 'factory.printing.topMargin = ' + this.topmargin + ';' + 'factory.printing.rightMargin = ' + this.rightmargin + ';' + 'factory.printing.bottomMargin = ' + this.bottommargin + ';' + 'factory.printing.Print(false);window.close();}</script>';
      html += '</body></html>';

      var win = window.open('', 'print', 'letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0', 'replace');
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
