Ext.define('alms.busrecordview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '原始记录表管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busrecord',
      storeUrl: 'DatSearchBusRecord.do',
      expUrl: 'DatSearchBusRecord.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'recordid',
      detailTabs: 0,
      hasDetail: false,
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnReview', text: '查看', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'busrecordhtml', html: '' },
      {xtype:'hiddenfield',name:'br.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'br.recordid',id: mep + 'recordid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
//      ' ', { id : mep + 'btnPass', text :'接单', iconCls : 'icon-deal', handler : me.OnSubmit, scope : me },
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
          'bt.flowstatus': '84',
          'bt.labid': gpersist.GetUserInfo().dept.deptid
        });
      });
    };
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.region = 'center';
  },
  
  OnLoadData: function (record) {
    var me = this, mep = me.tranPrefix;

    me.cancelRecord = record;

    var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecord.do?br.recordid=') + record.recordid);

    tools.SetValue(mep + 'recordid', record.recordid);
    tools.SetValue(mep + 'taskid', record.taskid);
    
    var nowdate = new Date();
    if (item && item.record && !Ext.isEmpty(item.record.recordid)) {
      tools.log(item.record.recordid, nowdate);
      
      var i = 0, j = 0;
      var record;
      var rowspan = 0, colspan = 0;
      var height = 28, width = 36, nowheight = 0, nowwidth = 0;
      var form = '';
      var celltext = '';
      var style = '';
      var align = '';
      
      tools.log(item.details.length, nowdate);
      
      for (var page = 0; page < item.details.length; page++) {
        var nowdetails = item.details[page].datas;
        
        tools.log(nowdetails.length, nowdate);
        
        if (i == item.details.length - 1)
          form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;">';
        else
          form += '<table cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always;">';
        
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
              //tools.log(record.cellname + '-' + record.celltext);
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
              
              //tools.log(style, nowdate);
              //tools.log(record.cellname, nowdate);
              
              form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' +
                ' style="' + style + '" ' + 
                (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + 
                (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + 
                align + ' valign="middle">' + 
                celltext + '</td>';
            } 
          }
          
          form += '</tr>';
        }
        
        form += '</table><br />';
      }
      
      me.disphtml += form.replace(/\<br \/\>/g, '');
      
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
        '<tr><td align="center">' + form + '</td></tr></table>';
      
      Ext.fly(Ext.getCmp(mep + 'busrecordhtml').getEl()).update(html);
        
      return true;
    }
    else
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
  }
  
});
