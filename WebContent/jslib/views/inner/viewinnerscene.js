Ext.define('alms.viewinnerscene',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'现场审核检查',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_innerscene',
          storeUrl:'InnerSearchInnerScene.do',
          expUrl:'InnerSearchInnerScene.do',
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
           'innerscene.tranid':tools.GetValueEncode(mep+'searchtranid')
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
    var height = 40;
    var record = tools.JsonGet('InnerGetInnerScene.do?innerscene.tranid='+item.tranid);
    var works = tools.JsonGet('InnerGetListInnerSceneWork.do?scenework.tranid='+item.tranid).data;
    
    var member = [];
    var members = tools.JsonGet('InnerGetListInnerGroupMember.do?groupmember.groupid='+item.groupid).data;
    for(var j = 0;j<members.length;j++){
      member.push(members[j].memberusername);
    }
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>现场审核工作计划</b></td></tr>';
     html += '</table>';  
      html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:60px">审核目的</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(record.auditgoal)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:60px">审核范围</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(record.auditscope)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:60px">审核依据</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(Ext.util.Format.htmlDecode(record.auditby))+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:100px">审核方法</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(Ext.util.Format.htmlDecode(record.auditmethod))+'</td></tr>';
   
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%">审核组长</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(record.auditleadname)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%" style="height:60px">组员</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemData(member)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="5%">审核日期</td>' +
    '<td class="infoc infoleft" colspan=4 align="left" width="30%">' +alms.GetItemDateData(record.auditdate)+'</td></tr>';
    
    html += '</table>';
    
    html += '<br/>';
    
    html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>内审现场审核安排</b></td></tr>';
       html += '</table>';  
      html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%" >时间</td>' +
    '<td class="infoc infoleft"  align="center" width="40%">内容</td>' +
    '<td class="infoc infoleft"  align="center" width="40%">参加人员</td>' +
    '<td class="infoc infoleft"  align="center" width="10%">实施人员</td>' +
    '</tr>';
    
    for(var i=0;i<works.length;i++){

       work = works[i];
       html += '<tr class="infotr"><td class="infoc infoleft" align="center" width="10%">'+alms.GetItemDateData(work.workdate)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="10%">'+alms.GetItemData(work.workcontentname)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="30%">'+alms.GetItemData(work.joinuser)+'</td>' +
      '<td class="infoc infoleft"  align="center" width="30%">'+alms.GetItemData(work.operuserame)+'</td>'+
      '</tr>';
    }
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
//         LODOP.PRINT();//打印功能
     }
   }
  
});