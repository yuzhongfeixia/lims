Ext.define('alms.tradeapprove',{
    extend:'gpersist.base.busform',
   
    constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'供应商评价',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_tradeapprove',
          storeUrl:'DevSearchTradeApprove.do',
          saveUrl:'DevSaveTradeApprove.do',
          expUrl:'DevSearchTradeApprove.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasSubmit: true
          
      });
      me.callParent(arguments);
   },
   
   isPassed:true,
   
   OnInitConfig:function(){
     var me = this;
     me.callParent(arguments);
     me.isPassed = true;
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnDo', text:'处理', iconCls: 'icon-deal', handler: me.OnShow,scope: me}, 
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
     tools.SetToolbar(items, mep);
     
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(toolbar);
   },
   
   OnBeforeCreateEdit:function(){
     var me = this;
     var mep = this.tranPrefix;
     me.OnInitGridToolBar();
    
     me.editControls = [
      {xtype:'hiddenfield',name:'trsu.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'trsu.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'trsu.flowaction',id: mep + 'flowaction'},
      { xtype: 'label', id: mep + 'applyhtml', html: '' }
    ];
     
   },
   
   /*OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdapply.flowstatus':'07',
           'prdapply.flowaction':'04'
         })
       });
     }
   },*/
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'tranid',item.tranid);
    //tools.SetValue(mep+'prdsource',item.prdsource);
    
    var html = me.OnReviewPrdApply(item);
    
    Ext.getCmp(mep + 'applyhtml').update(html);
    
    return true;
   },
   
   OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnBeforeCreateEditToolBar();
    
    me.OnAfterCreateEditToolBar();
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
      items : me.editToolItems
    });
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      autoScroll:true,
      height : '100%',
      region : 'north',
      frame : true,
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
  },
  
   //修改编辑面的按钮菜单
   OnAfterCreateEditToolBar:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.editToolItems = [
        '-', ' ', { id: mep + 'btnPass', text:'同意', iconCls: 'icon-deal', handler: me.OnPass, scope: me },
         '-', ' ', { id: mep + 'btnBack', text:'退回', iconCls: 'icon-deal', handler: me.OnBack, scope: me },
        '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
      ];
   },
   
   //审核通过
   OnPass:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','03');
      tools.SetValue(mep+'flowstatus','07');
      me.OnSubmit();
   },
   
   //审核未通过
   OnBack:function(){
      var me = this;
      var mep = me.tranPrefix;
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','03');
      tools.SetValue(mep+'flowstatus','06');
      me.OnSubmit();
   },
   
   OnAfterSubmit:function(){
     var me = this;
     var mep = me.tranPrefix;
     tools.BtnsDisable(['btnPass','btnBack'], mep);
   },
   
  //双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsEnable(['btnPass'], mep);
    tools.BtnsEnable(['btnBack'], mep);
  },
  
  //单击grid
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);
       
    if(record.flowstatus != '05'){
      tools.BtnsDisable(['btnDo'], mep);
    } else{
      tools.BtnsEnable(['btnDo'], mep);
    };
  }, 
  


 //预览html内容
 OnReviewPrdApply:function(item){
    var me = this;
    var mep = me.tranPrefix;
    var items = tools.JsonGet('DevGetTradeSurvey.do?trsu.tranid='+item.tranid);
    
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">'
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>供应商调查评论表</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.tranid) +'</td>'+
    '<td class="infoat" align="center" width="10%">供应商编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.tradeid) +'</td>'+
    '<td class="infoat" align="center" width="10%">供应商名称</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.tradename) +'</td>'+
    '<td class="infoat" align="center" width="10%">产品名称</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdname) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">企业规模</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.enterscalename) +'</td>'+
    '<td class="infoat" align="center" width="10%">企业知名度</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.enterpopularname) +'</td>'+
    '<td class="infoat" align="center" width="10%">资格证书</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.entercretname) +'</td>'+
    '<td class="infoat" align="center" width="10%">管理</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdmanagename) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">价格</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdpricename) +'</td>'+
    '<td class="infoat" align="center" width="10%">产品质量</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdqualityname) +'</td>'+
    '<td class="infoat" align="center" width="10%">生产检查设备</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdcheckname) +'</td>'+
    '<td class="infoat" align="center" width="10%">试用情况</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdtestname) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">服务质量</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.prdservicename) +'</td>'+
    '<td class="infoat" align="center" width="10%">调查员</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.tranusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">调查员确认日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(items.trandate) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">评价员</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(items.evalusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">调查员确认日期</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(items.evaldate) +'</td></tr>';

    html += '</table>';
    html + '</td></tr>';
  
    html += '</table>';
    return html;
  }
   
});