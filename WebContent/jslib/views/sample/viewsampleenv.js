Ext.define('alms.viewsampleenv',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'环境样品采样单',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_sampleenv',
          storeUrl:'SampSearchSampleEnv.do',
          expUrl:'SampSearchSampleEnv.do',
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
       ' ', { xtype: 'textfield', fieldLabel: '受检企业', labelWidth: 70, width: 200, maxLength: 200, name: 'searchentername', id: mep + 'searchentername', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '基地名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchenterbase', id: mep + 'searchenterbase', allowBlank: true }
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
           'sampenv.entername':tools.GetValueEncode(mep+'searchentername'),
           'sampenv.enterbase':tools.GetValueEncode(mep+'searchenterbase')
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
    var record = tools.OnGridLoad(me.plGrid, '请选择采样单！');
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
    var record = tools.JsonGet('SampGetSampleEnv.do?sampenv.tranid='+item.tranid);
    var envdetail = tools.JsonGet('SampGetListSampleEnvDetail.do?sed.tranid='+record.tranid).data;
    
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>环境样品采样单</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">受检企业</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%">' +alms.GetItemData(record.entername)+ '</td>' +
    '<td class="infoat" align="center" width="10%">企业法人/负责人</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%">'+alms.GetItemData(record.enterlegal)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">企业性质</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(record.entertypename)+ '</td>' +
    '<td class="infoat" align="center" width="10%">邮政编码</td>' +
    '<td class="infoc infoleft" colspan=1 align="left" width="15%">'+alms.GetItemData(record.enterpost)+'</td>'+
    '<td class="infoat" align="center" width="10%">联系电话</td>' +
    '<td class="infoc infoleft" colspan=1 align="left" width="15%">'+alms.GetItemData(record.entertele)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%">通讯地址</td>' +
    '<td class="infoc infoleft" colspan=3 align="left" width="15%">' +alms.GetItemData(record.enteraddress)+ '</td>' +
    '<td class="infoat" align="center" width="10%">检验类别</td>' +
    '<td class="infoc infoleft" colspan=1 align="left" width="15%">'+alms.GetItemData(record.testtypename)+'</td></tr>';
    
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%">基地名称</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(record.enterbase)+ '</td>' +
    '<td class="infoat" align="center" width="10%">采样时间</td>' +
    '<td class="infoc infoleft" colspan=1 align="left" width="15%">'+alms.GetItemDateData(record.sampledate)+'</td>'+
    '<td class="infoat" align="center" width="10%">采样深度</td>' +
    '<td class="infoc infoleft" colspan=1 align="left" width="15%">'+alms.GetItemData(record.sampledeepname)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">种植品种</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%">' +alms.GetItemData(record.sampleplant)+ '</td>' +
    '<td class="infoat" align="center" width="10%">基地面积</td>' +
    '<td class="infoc infoleft" colspan=2 align="left" width="15%">'+alms.GetItemData(record.basearea)+'</td></tr>';
    
//    html += '</table>';
    
    if(envdetail.length > 0){
      html += '<br/>';
//      html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
      html += '<tr class="infotr"><td class="infoat" align="center" colspan="1" width="10%">编号</td>' +
      '<td class="infoat" align="center" colspan="2" width="10%">采样地点</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%">采样量</td>' +
      '<td class="infoat" align="center" colspan="1" width="10%">GPS点位</td>'+
      '<td class="infoat" align="center" colspan="1" width="10%">样点示意图</td></tr>';
      
      for(var i = 0; i < envdetail.length; i++){
         var env = envdetail[i];
         html += '<tr class="infotr">' +
        '<td class="infoc infoleft" colspan="1" align="center" width="10%">' +alms.GetItemData(env.envid)+ '</td>' +
        '<td class="infoc infoleft" colspan="2" align="center" width="10%">'+alms.GetItemData(env.sampleaddress)+'</td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(env.samplequantity)+'</td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(env.sampleposi)+'</td>'+
        '<td class="infoc infoleft" colspan="1" align="center" width="10%">'+alms.GetItemData(env.samplepic)+'</td></tr>';
      }
      html += '</table>';
    }
    
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
 }  
  
});