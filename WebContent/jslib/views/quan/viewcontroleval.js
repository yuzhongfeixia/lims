Ext.define('alms.viewcontroleval',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'质量控制评价',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quancontroleval',
          storeUrl:'QuanSearchQuanControlEval.do',
          expUrl:'QuanSearchQuanControlEval.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '项目名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchprojname', id: mep + 'searchprojname', allowBlank: true }
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
           'qce.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'qce.projname':tools.GetValueEncode(mep+'searchprojname')
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
    var height = 100;
    var record = tools.JsonGet('QuanGetQuanControlEval.do?qce.tranid='+item.tranid);
    var samps = tools.JsonGet('QuanGetListQuanControlSamp.do?qcs.tranid='+item.tranid).data;
    var users = tools.JsonGet('QuanGetListQuanControlUser.do?qcu.tranid='+item.tranid).data;
    var user = [], samp = [], samst = [], samso = [], stmeth = [], result = [];
    for(var i =0; i<users.length;i++){
      user.push(users[i].username);
    }
    for(var j =0;j<samps.length;j++){
      samp.push(samps[j].samplename);    //样品名称
      samst.push(samps[j].samplestatus); //样品状态
      samso.push(samps[j].samplesource);  //样品来源
      stmeth.push(samps[j].usestdmethod);//使用标准或方法
      result.push(samps[j].testresult);  //测试结果
    }
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>质量控制评价表</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR 5-7-3</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;" >项目名称</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +alms.GetItemData(record.projname)+'</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;" >项目类别</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +alms.GetItemData(record.projtype)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;" >参加人员</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +alms.GetItemData(user)+'</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;" >完成时间</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +alms.GetItemDateData(record.finishdate)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:30px;" >样品名称及状态</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +samp+"("+samst+')</td>' +
    '<td class="infoat" align="center" width="10%" style="height:30px;" >样品来源</td>' +
    '<td class="infoc infoleft" align="left" width="40%" style="height:30px;">' +alms.GetItemData(samso)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">使用标准或方法</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(stmeth)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">测试验证结果</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(result)+'</td></tr>';
    
     var checkor= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=QuanControlEval&htodo.flownode=check');
     var date = alms.GetItemDateData(checkor.trandate).split('-');
     (JSON.stringify(checkor) == "{}")?  
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">评价</td>' +
     '<td class="infoc infoleft" colspan=3 align= "right" width="30%"><p ><center>'+ alms.GetItemData(checkor.tododesc)+'</center>  签名：' +' &nbsp&nbsp&nbsp'+'年'+'&nbsp&nbsp&nbsp'+'月'+'&nbsp&nbsp&nbsp' +'日 '  + ' </p></td></tr>'
     :html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">评价</td>' +
     '<td class="infoc infoleft" colspan=3 align= "right" width="30%"><p ><center>'+ alms.GetItemData(checkor.tododesc)+'</center>  签名：<image height="40px" src="images/sign/' +alms.GetItemData(checkor.tranuser) + '.jpg" />' +alms.GetItemData(date[0])+'年'+alms.GetItemData(date[1])+'月'+alms.GetItemData(date[2]) +'日 '  + ' </p></td></tr>';
     
     var approver= tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=QuanControlEval&htodo.flownode=approve');
     
     var date1 = alms.GetItemDateData(approver.trandate).split('-');
     (JSON.stringify(approver) == "{}")?
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">主任意见</td>' +
     '<td class="infoc infoleft" colspan=3 align= "right" width="30%"><p ><center>' + alms.GetItemData(approver.tododesc)+'</center>  签名：'+ '&nbsp&nbsp&nbsp'+'年'+'&nbsp&nbsp&nbsp'+'月'+'&nbsp&nbsp&nbsp' +'日 '  + ' </p></td></tr>'
     :html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">主任意见</td>' +
     '<td class="infoc infoleft" colspan=3 align= "right" width="30%"><p ><center>' + alms.GetItemData(approver.tododesc)+'</center>  签名：<image height="40px" src="images/sign/' +alms.GetItemData(approver.tranuser) + '.jpg" />'+  alms.GetItemData(date1[0])+'年'+alms.GetItemData(date1[1])+'月'+alms.GetItemData(date1[2]) +'日 '  + ' </p></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:'+height+'px">备注</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="30%">' +alms.GetItemData(record.remark)+'</td></tr>';
    
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
 } ,
 
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