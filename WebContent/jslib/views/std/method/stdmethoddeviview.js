Ext.define('alms.stdmethoddeviview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '检测方法偏离确认查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stddevi',
      storeUrl: 'StdSearchStdMethodDevi.do',
      expUrl: 'StdSearchStdMethodDevi.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 90, width: 180, maxLength: 40, name: 'searchmethodname', id: mep + 'searchmethodname', allowBlank: true },          
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'stdmethoddeviviewhtml', html: '' },
      {xtype:'hiddenfield',name:'stddevi.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  
//  OnBeforeSearch: function () {
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (me.gridStore) {
//      me.gridStore.on('beforeload', function (store, options) {      
//        Ext.apply(store.proxy.extraParams, {
//          'bgn.flowstatus': '02'
//        });
//      });
//    };
//  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'stddevi.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'stddevi.methodname': tools.GetEncode(tools.GetValue(mep + 'searchmethodname'))
        });
      });
    };
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
    me.OnAfterCreateEdit();
  },
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择检测方法偏离确认记录！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
  },
  
  OnShowStdMethodDeviView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowStdMethodDeviHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'stdmethoddeviviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowStdMethodDeviView(item);
      viewFile = function (fileurl,filename) {
          alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
      };
    }
    return true;
  },
  
  ShowStdMethodDeviHtml: function(record){
    var me = this;
    var stdmethoddevi = tools.JsonGet('StdGetStdMethodDevi.do?stddevi.tranid='+record.tranid);
     var creator= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=MethodDevi&htodo.flownode=create');
  var checkor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=MethodDevi&htodo.flownode=check');
  var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=MethodDevi&htodo.flownode=judge');
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>检测方法偏离确认查看</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-5-4</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdmethoddevi.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">检测方法名称</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdmethoddevi.methodname)+'</td>'+
//      '<td class="infoat" align="center" width="10%">提出人</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdmethoddevi.propuser)+'</td>'+
//      '<td class="infoat" align="center" width="10%">提出人姓名</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdmethoddevi.propusername)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测方法名称</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="15%">' +alms.GetItemData(stdmethoddevi.methodname)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">方法偏离情况</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdmethoddevi.methodsource)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">理由</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdmethoddevi.reason)+'</td></tr>';

     html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">试验记录</td>' +
  '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(stdmethoddevi.trialrecord) +'</td></tr>';
  html + '</td></tr>';
  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >提出人：</td>' +
  '<td class="infoc infoleft" width="30%"   align="center" >'+'<image height="40px" src="images/sign/' +alms.GetItemData(stdmethoddevi.propuser)+ '.jpg" />'+'<td class="infoat" align="center" width="10%"> 审查人：</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+'<image height="40px" src="images/sign/' +alms.GetItemData(checkor.tranuser)+ '.jpg" />'+'</td></tr>';
  
  
  
     html += '<tr class="infotr"  ><td  class="infoat"  rowspan="2" align="center"  width="10%">确认结果</td>' +
  '<td class="infoc infoleft" align="left" style="height:100px;"colspan=4  width="40%">'+alms.GetItemData(judger.tododesc) +'</td></tr>';
  html + '</td></tr>';
  html += '<tr class="infotr" ><td class="infoat" align="center" width="10%"  >技术负责人签字：</td>' +
  '<td class="infoc infoleft" width="30%"   align="center" >'+'<image height="40px" src="images/sign/' +alms.GetItemData(judger.tranuser)+ '.jpg" />'+'<td class="infoat" align="center" width="10%"> 日期</td>'+'<td class="infoc infoleft"  align="center" width="40%">'+alms.GetItemDateData(judger.trandate)+'</td></tr>';
  
  
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdmethoddevi.remark)+'</td></tr>';
    
    html += '</table>';
    var attaches = tools.JsonGet(tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + record.tranid).data;
    if(attaches){
      if(attaches.length>0){
        html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">图谱明细附件信息</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
          '<td class="infoat" align="center" width="10%">附件名称</td>' +
          '<td class="infoat" align="center" width="10%">附件类型</td>' +
          '<td class="infoat" align="center" width="10%">预览</td></tr>';

        for(var i=0;i<attaches.length;i++){
        	var attachname = attaches[i].attachname;
        	var attachtype = attaches[i].attachtypename;
        	var attachurl = attaches[i].attachurl;
          html += '<tr class="infotr">' +
          '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
        }
      }
    }
    html += '</table>';
    
    
    var attaches = tools.JsonGet(tools.GetUrl('LabGetListBusRecordFile.do?brf.tranid=') + record.tranid).data;
    if(attaches){
      if(attaches.length>0){
        html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">原始记录表</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
          '<td class="infoat" align="center" width="10%">附件名称</td>' +
          '<td class="infoat" align="center" width="10%">附件类型</td>' +
          '<td class="infoat" align="center" width="10%">预览</td></tr>';

        for(var i=0;i<attaches.length;i++){
        	var attachname = attaches[i].attachname;
        	var attachtype = attaches[i].attachtypename;
        	var attachurl = attaches[i].attachurl;
          html += '<tr class="infotr">' +
          '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
        }
      }
    }
    html += '</table>';
    me.html = html;
    return html
    
  },
    OnPrintTask: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (!Ext.isEmpty(me.html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("样品任务单打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//        LODOP.PRINT();//打印功能
    }
  }
  
});
