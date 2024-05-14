Ext.define('alms.viewprdwaste',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'废弃物申请',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdwaste',
          storeUrl:'PrdSearchPrdWaste.do',
          expUrl:'PrdSearchPrdWaste.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnView', text: gpersist.STR_BTN_VIEW, iconCls: 'icon-outlook', handler: me.OnShow,scope: me},
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
     
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdwaste.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'tranid',id: mep + 'tranid'}
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
    var record = tools.OnGridLoad(me.plGrid, '请选择数据！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      }
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    }
  },
  
  OnShowGetHtml:function(item){
    var me = this;
    var mep = me.tranPrefix;
   
    var record = tools.JsonGet('PrdGetPrdWaste.do?prdwaste.tranid='+item.tranid);
    var prds = tools.JsonGet('PrdGetListPrdWasteDetail.do?wastedetail.tranid='+item.tranid).data;
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=WasteApply&htodo.flownode=check');
    var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=WasteApply&htodo.flownode=judge');
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;  margin:50 0 20 0;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>废弃物处理情况一览表</b></td></tr>';
    html += '</table>';
    
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:30px;" > 待处理废弃物<br>名         称</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">规格型号</td>' +
    '<td class="infoat" align="center" width="20%" style="height:30px;" >数  量</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">备注</td></tr>';
    
    if(prds.length <6){
    for(var i=0;i<prds.length;i++){
      var prd = prds[i];
      html +='<tr class="infotr"><td class="infoc infoleft" align="center" width="20%" style="height:30px;" >'+alms.GetItemData(prd.prdname) +'</td>' +
      '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(prd.prdstd) +'</td>' +
      '<td class="infoc infoleft" align="center" width="20%" style="height:30px;">'+alms.GetItemData(prd.prdcount) +'</td>' +
      '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(prd.tranremark) +'</td></tr>';
  }
    for(var i=prds.length;i<6;i++){
      var prd = prds[i];
      html +='<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:30px;" >'+alms.GetItemData() +'</td>' +
      '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData() +'</td>' +
      '<td class="infoat" align="center" width="20%" style="height:30px;">'+alms.GetItemData() +'</td>' +
      '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData() +'</td></tr>';
  }
    }
    if(prds.length >6){
      for(var i=0;i<6;i++){
        var prd = prds[i];
        html +='<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:30px;" >'+alms.GetItemData(prd.prdname) +'</td>' +
        '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(prd.prdstd) +'</td>' +
        '<td class="infoat" align="center" width="20%" style="height:30px;">'+alms.GetItemData(prd.prdcount) +'</td>' +
        '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(prd.tranremark) +'</td></tr>';
    }
      }

    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:30px;" > 申请人</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(item.tranusername)+'</td>' +
    '<td class="infoat" align="center" width="20%" style="height:30px;" >申请日期</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemDateData(item.trandate)+'</td></tr>';
    
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:30px;" > 科室名称</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(item.deptname)+'</td>' +
    '<td class="infoat" align="center" width="20%" style="height:30px;" >室主任</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:30px;">'+alms.GetItemData(item.comfirmusername)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat"  colspan="8" align="left" width="100%" style="height: 50px;">办公室意见：'
      +alms.GetItemData(checker.tododesc)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" style="height:50px;"> 主任（签字）：</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:50px;"><image height="35px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" /></td>' +
    '<td class="infoat" align="center" width="20%" style="height:50px;" >日期</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:50px;">'+alms.GetItemDateData(judger.trandate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat"  colspan="8" align="left" width="100%" style="height: 50px;">废弃物处理单位：'
      +''+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="left" width="20%" style="height:50px;" >废弃物处理方签字：</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:50px;">'+''+'</td>' +
    '<td class="infoat" align="center" width="20%" style="height:50px;">日期</td>' +
    '<td class="infoc infoleft" align="center" width="30%" style="height:50px;">'+''+'</td></tr>';
    
    html += '</table>';
    
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowGetHtml(item);
    }
    return true;
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
//       LODOP.PRINT();//打印功能
   }
 }   
  
});