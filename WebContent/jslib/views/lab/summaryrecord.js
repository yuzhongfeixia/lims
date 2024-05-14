Ext.define('alms.summaryrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '原始记录表台账信息管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busrecordforsum',
      storeUrl: 'DatSearchBusRecordForSummary.do',
      expUrl: 'DatSearchBusRecordForSummary.do',
      hasDetail: false,
      hasPage: true,
      hasExit: true,
      idPrevNext: 'taskid',
      hasGridSelect: true,
      detailTabs: 1
    });
    me.callParent(arguments);
  },
  
  OnInitConfig:function(){
    var me = this;
    me.params = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },           
      ' ', tools.GetToolBarCombo('searchparameter', mep + 'searchparameter', 250, '检测参数', 70, tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE)),
      '-', { xtype:'datefield',fieldLabel:'制表日期',labelWidth:60,width:160, name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -2),selectOnFocus: false, allowBlank: true},
      '', {xtype:'datefield',fieldLabel:'至',labelWidth:15, width:130, name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true},
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
  
  OnShow: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if(tools.GetValue(mep + 'searchparameter')=='-2'){
      tools.alert('请选择需要查看的检测参数！');
      return false;
    }
    
    if (me.tabMain ) {      
      
      me.tabMain.setActiveTab(1);
      
      
      if (!me.OnLoadData()) {
        me.tabMain.setActiveTab(0);
        return;
      }
      
      me.OnFormValidShow();
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'br.parameterid': tools.GetEncode(tools.GetValue(mep + 'searchparameter')),
          'br.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'br.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'br.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
        });
      });
    };
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'busrecordhtml', html: '' }
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印原始记录表', iconCls: 'icon-print', handler: me.OnPrintRecord, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecords', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecords', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  },
  
  OnLoadData: function () {
    var me = this, mep = me.tranPrefix;
    
    var parameterid = tools.GetEncode(tools.GetValue(mep + 'searchparameter'));
    var startdate = Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d');
    var enddate = Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d');
        
    var form = '';
    
    var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecordForSum.do?br.parameterid='+parameterid+'&br.startdate='+startdate+'&br.enddate='+enddate + '&br.tranuser='+gpersist.UserInfo.user.userid) );

    if (item && item.record && item.details.length>0) {

      var i = 0, j = 0;
      var record;
      var rowspan = 0, colspan = 0;
      var height = 28, width = 36, nowheight = 0, nowwidth = 0;
      
      var celltext = '';
      var style = '';
      var align = '';
      
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
    else{
      tools.alert('该检测参数对应的原始记录表台账不存在，请修改！');
      return false;
    }
    
    me.disphtml = form.replace(/\<br \/\>/g, '');
    
    me.disphtml = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
      '<tr><td align="center">' + form + '</td></tr></table>';

    Ext.fly(Ext.getCmp(mep + 'busrecordhtml').getEl()).update(me.disphtml);
    
    return true;

  },
  
  OnPrintRecord: function () {
    var me = this, mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.disphtml)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("原始记录打印打印");
       LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.disphtml);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
   }
    
    return;
  },
  
 OnPrevRecord : function() {
   var me = this;
   var mep = me.tranPrefix;
   
   var j = me.plGrid.store.getCount(), record, item;
   for ( var i = 0; i < j; i++) {
     record = me.plGrid.store.getAt(i).data;
     
     if (me.OnCheckPrevNext(record)) {
       if (i == 0) {
         tools.alert('已经是当前列表第一条数据！');
         return;
       }

       me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
       item = me.plGrid.store.getAt(i - 1).data;
       
       if(item.recordstatus == '01'|| item.recordstatus =='06'|| item.recordstatus =='10' ){
         tools.BtnsEnable(['btnRecordSubmit'],mep);
       }else{
         tools.BtnsDisable(['btnRecordSubmit'],mep);
       };
       
       me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
       me.OnFormValidShow();
       return;
     }
   }
 },
 
 OnNextRecord : function() {
   var me = this;
   var mep = me.tranPrefix;
   
   var j = me.plGrid.store.getCount(), record;
   for ( var i = 0; i < j; i++) {
     record = me.plGrid.store.getAt(i).data;
     
     if (me.OnCheckPrevNext(record)) {
       if (i == j - 1) {
         tools.alert('已经是当前列表最后一条数据！');
         return;
       }
       
       me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
       
       item = me.plGrid.store.getAt(i + 1).data;
       
       if(item.recordstatus == '01'||item.recordstatus =='06'|| item.recordstatus =='10'){
         tools.BtnsEnable(['btnRecordSubmit'],mep);
       }else{
         tools.BtnsDisable(['btnRecordSubmit'],mep);
       };
       
       me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
       me.OnFormValidShow();
       return;
     }
   }
 },
 
 OnFormClose : function() {
   var me = this;
   
   if (me.tabMain) {
     if (me.hasExitPrompt) {
         me.tabMain.setActiveTab(0);
     }
   }
 }
});
