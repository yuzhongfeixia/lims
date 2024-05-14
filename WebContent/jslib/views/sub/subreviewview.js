Ext.define('alms.subreviewview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '分包评审查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_subreview',
      storeUrl: 'SubSearchSubReview.do',
      expUrl: 'SubSearchSubReview.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '评审编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '分包方名称', labelWidth: 70, width: 180, maxLength: 40, name: 'searchsubname', id: mep + 'searchsubname', allowBlank: true },          
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: '', iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'subreviewhtml', html: '' },
      {xtype:'hiddenfield',name:'sr.tranid',id: mep + 'tranid'}
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
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'sr.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'sr.subname': tools.GetEncode(tools.GetValue(mep + 'searchsubname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择设备购买申请！');
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
  
  OnShowSubReviewView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowSubReviewHtml(record);
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'subreviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowSubReviewView(item);
    }
    return true;
  },
  
  ShowSubReviewHtml: function(record){
    var me = this;
    var mep = me.tranPrefix;
    var subreview = tools.JsonGet('SubGetSubReview.do?sr.tranid='+record.tranid);
     var createor  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=SubReview&htodo.flownode=create');
    var judger  = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=SubReview&htodo.flownode=judge');
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:20px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>分包评审表</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 4-4-1</td></tr>';
    html += '</table>';
    html += '<table align="center" cellspacing="0" cellpadding="0" border="1" style=" font-size:12px;" width="80%">';
    
    
    html += '<tr class="infotr"  style="height: 40px;"><td  align="center" width="10%" >分包方名称</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.subname)+'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">地址</td>' +
    '<td align="left" width="40%">'+alms.GetItemData(subreview.subaddr)+'</td>'+
    '<td  align="center" width="10%">联系人/电话</td>' +
    '<td  align="left" width="40%">'+alms.GetItemData(subreview.linktele)+'</td></tr>';
    
   
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >分包项目</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.subproject)+'</td></tr>';
    
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"" >评&nbsp&nbsp&nbsp&nbsp审&nbsp&nbsp&nbsp&nbsp内&nbsp&nbsp&nbsp&nbsp容</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >检测人员数量、素质</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.testerdesc)+'</td></tr>';
  
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >检测设备</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.testdev)+'</td></tr>';
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >检测设备量值溯源情况</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.testsource)+'</td></tr>';
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >环境条件</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.envdesc)+'</td></tr>';
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >质量体系和运行情况</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.qualsys)+'</td></tr>';
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >服务质量</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.servicequal)+'</td></tr>';
    
    html += '<tr class="infotr" style="height: 40px;"><td  align="center" width="10%" >结论</td>' +
    '<td  colspan=3  align="center" width="90%">'+alms.GetItemData(subreview.auditresult)+'</td></tr>';
    
    (JSON.stringify(createor) == "{}")?html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">评审人员</td>' +'<td align="center" width="40%"></td>'+
    '<td  align="center" width="10%">日期</td>' +'<td  align="center" width="40%"></td></tr>':
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">评审人员</td>' +
    '<td align="center" width="40%">'+'<image height="35px" src="images/sign/'+alms.GetItemData(createor.tranuser)+ '.jpg" />'+'</td>'+
    '<td  align="center" width="10%">日期</td>' +
    '<td  align="center" width="40%">'+alms.GetItemDateData(createor.trandate)+'</td></tr>';
    
    (JSON.stringify(judger) == "{}")?html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">批准</td>' +
    '<td align="center" width="40%"></td>'+'<td  align="center" width="10%">日期</td>' +'<td  align="center" width="40%"></td></tr>' :
    html += '<tr class="infotr" style="height: 40px;" > <td  align="center" width="10%">批准</td>' +
    '<td align="center" width="40%">'+'<image height="35px" src="images/sign/'+alms.GetItemData(judger.tranuser)+ '.jpg" />'+'</td>'+
    '<td  align="center" width="10%">日期</td>' +
    '<td  align="center" width="40%">'+alms.GetItemDateData(judger.trandate)+'</td></tr>';
    
//    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>分包评审查看</b></td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">评审编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(subreview.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">分包方名称</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(subreview.subname)+'</td>'+
//      '<td class="infoat" align="center" width="10%">分包方地址</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(subreview.subaddr)+'</td>'+
//      '<td class="infoat" align="center" width="10%">联系电话</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(subreview.linktele)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">分包项目</td>' +
//      '<td class="infoc infoleft" colspan="7" align="left" width="15%">' +alms.GetItemData(subreview.subproject)+ '</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测人员数量、素质</td>' +
//      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.testerdesc)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测设备</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.testdev)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测设备量值溯源情况</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.testsource)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">环境情况</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.envdesc)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">质量体系和运行情况</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.qualsys)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">服务质量</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.servicequal)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">评审结论</td>' +
//    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(subreview.auditresult)+'</td></tr>';
//    
//    html += '</table>';
    
//    return html
//    Ext.fly(Ext.getCmp(me.tranPrefix + 'subreviewhtml').getEl()).update(html);
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
