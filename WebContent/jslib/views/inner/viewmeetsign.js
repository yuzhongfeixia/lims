Ext.define('alms.viewmeetsign',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'内审首/末次会议记录',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_innermeetsign',
          storeUrl:'InnerSearchInnerMeetSign.do',
          expUrl:'InnerSearchInnerMeetSign.do',
          idPrevNext:'meetid',
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
      ' ', { xtype: 'textfield', fieldLabel: '会议编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchmeetid', id: mep + 'searchmeetid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '会议主题', labelWidth: 60, width: 250, maxLength: 40, name: 'searchmeettopic', id: mep + 'searchmeettopic', allowBlank: true }
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
           'meetsign.meetid':tools.GetValueEncode(mep+'searchmeetid'),
           'meetsign.meettopic':tools.GetValueEncode(mep+'searchmeettopic')
         })
       });
     }
   },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'samphtml', html: '' },
      {xtype:'hiddenfield',name:'meetid',id: mep + 'meetid'}
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
    var height = 20;
    var record = tools.JsonGet('InnerGetInnerMeetSign.do?meetsign.meetid='+item.meetid);
    var parts = tools.JsonGet('InnerGetListInnerMeetPart.do?meetpart.meetid='+item.meetid).data;
    var part = [];
    for(var i=0;i<parts.length;i++){
      part.push(parts[i].partusername);
    }
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>内审首/末次会议记录</b></td></tr>';
    html += '<tr class="infotrheader"><td colspan="8" align="right" width="100%" style="height: 40px;font-size:12px">QCR 4-10-6</td></tr>';
    html += '</table>';
    html += '<table align = "center" cellspacing="0" colspan="8" cellpadding="0" border="1" style="font-size:12px;" width="80%">';
    html += '<tr class="infotr"><td class="infoat" colspan="8" align="left" style="height: 40px;" >会议主题：&nbsp&nbsp&nbsp&nbsp '+alms.GetItemData(record.meettopic)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >时间</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemDateData(record.meetdate)+'</td>' +
    '<td class="infoat" align="center" width="20%" >地点</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.meetplace)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" colspan="8" align="center" style="height: 40px;" >签&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp到 </td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="25%" >姓名</td>' +
    '<td class="infoat" align="center" width="25%">姓名</td>' +
    '<td class="infoat" align="center" width="25%" >姓名</td>' +
    '<td class="infoat" align="center" width="25%">姓名</td></tr>';
   k =0;
    for(i =0;i<10;i++){
      for(j =0;j<4;j ++){
        html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData(part[k++])+'</td>' ;
      }
      html +='</tr>';
    }
    
   
//    //循环体中均为测试数据
//      var pl=parseInt(parts.length/4);
//      if(pl>8){
//      for(var i=0;i<pl;i++){
//        html +='<tr class="infotr">';
//        for(var j=4*i;j<i*4+4;j++){
//          html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData(record.meetplace)+'</td>' ;
//      } 
//        
//        html +='</tr>';
//     }
//    
//    var ply= parts.length %4; 
//    html +='<tr class="infotr">';
//    
//    for(var i=0;i<ply;i++){
//      html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData(record.meetplace)+'</td>' ;
//  } 
//    for(var i=ply;i<4;i++){
//      html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData()+'</td>' ;
//  } 
//      
//    html +='</tr>';
//      }
//      if(pl<8){
//        for(var i=0;i<pl;i++){
//          html +='<tr class="infotr">';
//          for(var j=4*i;j<4*i+4;j++){
//            html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData(record.meetplace)+'</td>' ;
//        } 
//          
//          html +='</tr>';
//       }
//      
//      var ply= parts.length %4; 
//      html +='<tr class="infotr">';
//      for(var i=0;i<ply;i++){
//        html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData(record.meetplace)+'</td>' ;
//    } 
//      for(var i=ply;i<4;i++){
//        html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData()+'</td>' ;
//    }   
//      html +='</tr>';
//      
//      
//      for(var i=pl+1;i<8;i++){
//        html +='<tr class="infotr">';
//        for(var j=4*i;j<4*i+4;j++){
//          html += '<td class="infoat" align="center" width="25%" >'+alms.GetItemData()+'</td>' ;
//      } 
//        
//        html +='</tr>';
//     }
//      
//      
//      
//        } 
      
      
      
      
      
      
    html += '<tr class="infotr"><td class="infoat" colspan="8" align="center" style="height: 200px;" >'+''+'</td></tr>';
   
    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >记录人</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(record.tranuser) + '.jpg" />'+'</td>' +
    '<td class="infoat" align="center" width="20%" >日期</td>' +
    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemDateData(record.trandate)+'</td></tr>';
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="20%" >审核组长</td>' +
//    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemData(record.checkusername)+'</td>' +
//    '<td class="infoat" align="center" width="20%" >日期</td>' +
//    '<td class="infoc infoleft" align="center" width="30%">'+alms.GetItemDateData(record.checkdate)+'</td></tr>';
//    
    //    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
//    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>内审首/末次会议记录</b></td></tr>';
//    
////     html += '<tr class="infotr"><td class="infoc infoleft" colspan=4 align="center" width="10%" style="height:'+height+'px">会议主题：  ' +alms.GetItemData(record.meettopic)+'</td>' +
////    '</tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">会议主题:</td>' +
//    '<td class="infoc infoleft" align="center" width="80%">'+alms.GetItemData(record.meettopic)+'</td>' +
//    '</tr>';
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >会议日期</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemDateData(record.meetdate)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >地点</td>' +
//    '<td class="infoc infoleft" align="center" width="40%">'+alms.GetItemData(record.meetplace)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc" colspan=4 infoleft" align="center" width="10%" >签到</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoc" colspan=4 infoleft" style="height: 60px;" align="left" width="10%" >'+alms.GetItemData(part)+'</td></tr>';
//    
//     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >记录人</td>' +
//    '<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemData(record.tranusername)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >记录日期</td>' +
//    '<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemDateData(record.trandate)+'</td>'+
//    '</tr>';
    
     //html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >审核组长</td>' +
    //'<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemData(record.checkusername)+'</td>' +
//    '<td class="infoat" align="center" width="10%" >日期</td>' +
//    '<td class="infoc infoleft" align="left" width="30%">' +alms.GetItemDateData(record.checkdate)+'</td>'+
//    '</tr>';
    
    
    html += '</table>';
    me.html = html;
    Ext.fly(Ext.getCmp(me.tranPrefix + 'samphtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.meetid)) {
      tools.SetValue(mep + 'meetid',item.meetid);
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