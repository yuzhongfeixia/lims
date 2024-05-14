Ext.define("Ext.ux.grid.Printer", {
  requires: 'Ext.XTemplate',

  statics: {
    print: function (grid) {
      var me = this;
      me.maxrow = 0;
      
      me.columns = [];
      me.initColumns(grid.columns);
      var columns = me.columns;
      me.headings = me.initHeader(grid.columns);
      var data = [];
      var num = 0;
      grid.store.data.each(function (item) {
        var convertedData = [];
        num++;

        for (var key in item.data) {          
          var value = item.data[key];

          Ext.each(columns, function (column) {
            if (column.dataIndex == 'IndexNum') {
              convertedData['IndexNum'] = num;
            };
            
            if (column.dataIndex == key) {
              convertedData[key] = column.renderer ? column.renderer(value) : value;
            }
          }, this);
        }

        data.push(convertedData);
      });

      var body = Ext.create('Ext.XTemplate', this.bodyTpl).apply(columns);

      var nowdate = new Date();
      var sdate = '<table width="' + totalwidth + '"><tr><td align="left" style="border-width:0px"><b>' + nowdate.toLocaleString() + '</b></td></tr></table>';

      if (!this.isdate) sdate = '';

      var footer = '&b第&p页/共&P页&b';

      if (!this.ispage) footer = '';

      var headerjs = '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">' + 
      '<html><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />' + 
      '<link href="' + this.stylesheetPath + '" rel="stylesheet" type="text/css" media="screen,print" />' + 
      '<title></title></head><body onload="printWindow();">' + 
      '<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="jslib/cab/smsx.cab#Version=6,6,440,26"></object>'; 
      
      
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

      var htmlMarkup = [	
        '<div style="page-break-after: always;"><table width="' + totalwidth + '"><tr><td align="center" style="font-size:16px;border-width:0px"><b>' + this.printTitle + '</b></td></tr></table>' + sdate +			
				'<table width="' + totalwidth + '">' + this.headings +
				'<tpl for=".">',
				body,
				'</tpl>',
				'</table></div>'
			];

      var j = data.length;
      var printdata = [];
      var html = '';
      for (var i = 1; i <= j; i++) {
        if (i%30 == 0) {
          if (printdata.length > 0) {
            html += Ext.create('Ext.XTemplate', htmlMarkup).apply(printdata);
            printdata = [];
          }          
        }
        else
          printdata.push(data[i -1]);
      }
      
      if ((printdata.length > 0) || Ext.isEmpty(html))
        html += Ext.create('Ext.XTemplate', htmlMarkup).apply(printdata);

      //letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0
      var win = window.open('', 'print', 'letf=0,top=0,width=1,height=1,toolbar=0,scrollbars=0,status=0', 'replace');

      win.document.write(headerjs + html + bottomjs);
      win.document.close();
      this.headerCtl = [];
    },

    initColumns: function (columns) {
      var me = this;
      totalwidth = 0;
      Ext.each(columns, function (column) {
        if (column.dataIndex != null) {
          totalwidth += column.width;
          me.columns.push(column);
        }
        else {
          me.initColumns(column.items.items);
        }
      });
      
    },

    initHeaderCtl: function (columns, index, maxrow) {
      var me = this;
      var html = ''; // '<tr align=center>';
      var colspan = 0;
      Ext.each(columns, function (column) {
        if (column.dataIndex != null) {
          if (maxrow - index > 1) 
            html += '<th rowspan=' + (maxrow - index) + ' width="' + column.width + '">' + column.text + '</th>';
          else 
            html += '<th width="' + column.width + '">' + column.text + '</th>';
          colspan++;
        }
        else {
          var temp = me.initHeaderCtl(column.items.items, index + 1, maxrow);
          html += '<th colspan=' + temp + '>' + column.text + '</th>';
          colspan += temp;
        }
      });
            
      //html += '</tr>';
      
      var temp = this.headerCtl[index];
      if (!Ext.isEmpty(temp)) {
        html = temp + html;
      }
      
      this.headerCtl[index] = html;
      return colspan;
    },

    initHeader: function (columns) {
      var me = this;
      me.getMaxRows(columns, 1); 
      me.initHeaderCtl(columns, 0, me.maxrow);
      var headerCtls = me.headerCtl;
      var headings = '';
      Ext.each(headerCtls, function (headerCtl) {
        headings += '<tr align=center>' + headerCtl + '</tr>';
      });      
      
      return headings;
    },

    getMaxRows: function (columns, rowIndex) {
      var me = this;
      Ext.each(columns, function (column) {
        if (column.dataIndex == null) {
          var temp = me.getMaxRows(column.items.items, rowIndex + 1);
        }
      });
      if (rowIndex > me.maxrow) {
        me.maxrow = rowIndex;
      }
      return me.maxrow;
    },

    columns: [],

    headerCtl: [],

    maxrow: 0,

    headings: '',

    printTitle: '',

    isdate: true,

    ispage: true,

    totalwidth: 0,

    portrait: true,

    leftmargin: 2.0,

    topmargin: 6.0,

    rightmargin: 2.0,

    bottommargin: 6.0,

    stylesheetPath: 'jslib/ext/resources/css/print.css',

    printAutomatically: true,

    bodyTpl: [
			'<tr>',
				'<tpl for=".">',
					'<td>\{{dataIndex}\}</td>',
				'</tpl>',
			'</tr>'
		]
  }
});