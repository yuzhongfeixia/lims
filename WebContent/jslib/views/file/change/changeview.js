Ext.define('alms.changeview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件更改申请查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_changeapply',
      storeUrl: 'IncFileSearchChangeApply.do',
      expUrl: 'IncFileSearchChangeApply.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfileid', id: mep + 'searchfileid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },           
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
      { xtype: 'label', id: mep + 'changeapplyhtml', html: '' },
      {xtype:'hiddenfield',name:'ca.tranid',id: mep + 'tranid'}
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
          'ca.changefileid': tools.GetEncode(tools.GetValue(mep + 'searchfileid')),
          'ca.changefilename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择文件更改申请记录！');
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
  
  OnShowChangeApplyView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowChangeApplyHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'changeapplyhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
     
     var html =   me.ShowChangeApplyHtml(item);
     me.html = html;
     Ext.fly(Ext.getCmp(me.tranPrefix + 'changeapplyhtml').getEl()).update(html);
    }
    return true;
  },
  
  ShowChangeApplyHtml: function(record){
    var me = this;
    var mep = me.tranPrefix;
    var changeapply = tools.JsonGet('IncFileGetChangeApply.do?ca.tranid='+record.tranid);
    var judger= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileChange&htodo.flownode=judge');
    var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=IncFileChange&htodo.flownode=approve');
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:20px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>文件更改申请书</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:20px">QCR 4-3-2</td></tr>';
    html += '</table>';
    html += '<table  cellspacing="0" cellpadding="0" border="1"  align="center" style="font-size:12px;" width="80%">';
    
    html += '<tr class="infotr" style="height:40px;"> <td  align="center" width="10%">申请部门（人）</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(changeapply.changedeptname)+'</td>'+
    '<td " align="center" width="10%">拟更改文件名称</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(changeapply.changefilename)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:40px;"> <td  align="center" width="10%">会办部门</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(changeapply.changedeptname)+'</td>'+
    '<td  align="center" width="10%">拟更改文件编号</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(changeapply.changefileid)+'</td></tr>';   

  html += '<tr class="infotr"><td  align="center" width="10%" style="height:100px;">更改理由</td>' +
    '<td " colspan=3  align="center" width="90%">'+alms.GetItemData(changeapply.changereason)+'</td></tr>';
  html += '<tr class="infotr"><td  align="center" width="10%" style="height:100px;">更改后内容</td>' +
  '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(changeapply.changedesc)+'</td></tr>';
  

  html += '<tr class="infotr" style="height:60px;"> <td  align="center" width="10%">审核人意见：</td>' +
  '<td align="left" width="40%">'+ alms.GetItemData(judger.tododesc)+'</td>'+
  '<td " align="center" width="10%">批准人意见</td>' +
  '<td  align="left" width="40%">'+ alms.GetItemData(approver.tododesc)+'</td></tr>';
  html += '<tr class="infotr" style="height:40px;"> <td  align="center" width="10%">日期：</td>' +
  '<td align="left" width="40%">'+ alms.GetItemDateData(judger.trandate)+'</td>'+
  '<td " align="center" width="10%">日期：</td>' +
  '<td  align="left" width="40%">'+ alms.GetItemDateData(judger.trandate)+'</td></tr>';
//  html += '<table  cellspacing="0" cellpadding="0" border="0"   align="center" style="font-size:12px; border-top:black solid 1px;border-left:black solid 1px;border-right:black solid 1px;" width="80%">';
//  html += '<tr class="infotr" ><td  align="center" width="50%" style="height:80px;border-right:black solid 1px;border-left:black solid 1px;border-bottom:black solid 1px; ">审核人意见：<br>' +'暂无数据'+
//  '<br/>日期：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp日</td>' +
//    '<td  colspan=2  align="center" width="50%" style="border-bottom:black solid 1px;border-right:black solid 1px;">批准人意见<br>'+'暂无数据'+
//    '<br/>日期：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp日</td></tr>';
  
//  html += '<tr class="infotr"><td  class="infoat" align="left" width="50%" style="height:60px;">审核人意见：<br>' +'暂无数据'+
//  '<br/>日期：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp日</td>'
//  '<td class="infoat " colspan=2  align="left" width="50%" style="height:60px;" >批准人意见'+'暂无数据'+
//  '<br/>日期：&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp日</td></tr>'; 
//  
    
    
//  html += '<tr class="infotr"><td class="infoat" align="left"  colspan="8" style="height:80px;">更改理由<br/>' +alms.GetItemData(changeapply.changereason)+'</td></tr>';
//  html += '<tr class="infotr"><td class="infoat" align="left"  colspan="8" style="height:80px;">更改后内容<br/>' +alms.GetItemData(changeapply.changedesc)+'</td></tr>'; 
  
  
  
  
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>文件更改申请查看</b></td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//      '<td class="infoc infoleft" align="left" width="40%">' +alms.GetItemData(changeapply.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">拟更改文件编号</td>' +
//      '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changefileid)+'</td></tr>';
//      
//    html += '<tr class="infotr"> ' +
//    '<td class="infoat" align="center" width="10%">文件名称</td>' +
//    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changefilename)+'</td>'+
//    '<td class="infoat" align="center" width="10%">替代文件</td>' +
//    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.replacefilename)+'</td></tr>';
//    
//    html += '<tr class="infotr"> ' +
//    '<td class="infoat" align="center" width="10%">申请部门</td>' +
//    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changedeptname)+'</td>'+
//    '<td class="infoat" align="center" width="10%">会办部门</td>' +
//    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.deptname)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:80px; border-right:0;">更改理由</td>' +
//      '<td class="infoc infoleft" colspan=3  align="center" width="90%">'+alms.GetItemData(changeapply.changereason)+'</td></tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:80px;">更改后内容</td>' +
//      '<td class="infoc infoleft" colspan=3  align="center" width="90%">'+alms.GetItemData(changeapply.changedesc)+'</td></tr>';
//  
  html += '</table>';
    html += '</table>';
  
    return html;
    
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
