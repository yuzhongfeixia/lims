Ext.define('alms.viewinnerinvalid',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'不符合纠正措施',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_innerinvalid',
          storeUrl:'InnerSearchInnerInvalid.do',
          expUrl:'InnerSearchInnerInvalid.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '计划编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
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
           'innerinvalid.tranid':tools.GetValueEncode(mep+'searchtranid')
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
//      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },                    
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
    var height = 40;
    var record = tools.JsonGet('InnerGetInnerInvalid.do?innerinvalid.tranid='+item.tranid);
    
    var checker = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=InnerInvalid&htodo.flownode=check');
    var judger = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=InnerInvalid&htodo.flownode=judge');
    var ender = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+record.tranid+'&htodo.busflow=InnerInvalid&htodo.flownode=end');
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>不符合纠正措施报告表</b></td></tr>';
    html += '</table>';
   
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';

    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >发生不符合部门：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.occurdeptname)+'</td>' +
    '<td class="infoat" align="center" width="10%" >编号：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.occurdept)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >不符合来源：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.correctsourcename)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >不符合识别人：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.invalidusername)+'</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.invaliduserdate)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >已发生不符合事实的描述:</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.invaliddesc)+'</td></tr>';
    
    
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >对已发生不符合的评价：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.invalidadvname)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >责任人: </td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.tranusername)+'</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.trandate)+'</td></tr>';
   
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >已发生不符合原因分析:</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.invalidreason)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:100px;"><td class="infoat" align="center" width="10%" >拟采取的纠正措施（包括具体方法、完成期限和实施责任人）：</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="20%">'+alms.GetItemData(record.invalidmeasure)+'</td></tr>';
     
   
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >提出: </td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+'<image height="23px" src="images/sign/' +alms.GetItemData(record.tranuser) + '.jpg" />'+'(责任人)</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.trandate)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >批准: </td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+'<image height="23px" src="images/sign/' +alms.GetItemData(record.tranuser) + '.jpg" />'+'(发生部门负责人)</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.trandate)+'</td></tr>';
    
    html += '<tr class="infotr" style="height:'+ height +'px;"><td class="infoat" align="center" width="10%" >纠正措施已完成 </td>' +
    '<td class="infoc infoleft" align="center" width="40%">审核：'+'<image height="23px" src="images/sign/' +alms.GetItemData(checker.tranuser) + '.jpg" />'+'(发生部门负责人)</td>' +
    '<td class="infoat" align="center" width="10%" >日期：</td>' +
    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(checker.trandate)+'</td></tr>';
    
    
    html += '<tr class="infotr" style="height: 100px;"><td class="infoat"  align="center" width="20%" rowspan=3 >纠正措施已经验证</td>' +
    '<td class="infoc infoleft" colspan=3 align="center" width="60%">验证结果：<br/>&nbsp&nbsp&nbsp&nbsp&nbsp'+alms.GetItemData(judger.tododesc)+'</td></tr>';
    
    	html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >验证：'+'<image height="23px" src="images/sign/' +alms.GetItemData(judger.tranuser) + '.jpg" />'+'(中心内审员)</td>' +
    	'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(judger.trandate)+'</td></tr>';  
    
    	html += '<tr class="infotr" ><td class="infoc infoleft"  align="center" width="40%" >批准：'+'<image height="23px" src="images/sign/' +alms.GetItemData(ender.tranuser) + '.jpg" />'+'(质量负责人)</td>' +
    	'<td class="infoc infoleft" colspan=2 align="center" width="40%">日期：'+alms.GetItemDateData(ender.trandate)+'</td></tr>';  
    
    
    
    
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
//	       LODOP.PRINT();//打印功能
	   }
	 }  
  
});