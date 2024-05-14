Ext.define('alms.tradecheck',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
        editInfo:'供应商评价',
        winWidth:750,
        winHeight:250,
        hasColumnUrl:true,
        columnUrl:'SysSqlColumn.do?sqlid=p_get_tradeaudit',
        storeUrl:'DevSearchTradeCheck.do',
        saveUrl:'DevSaveTradeCheck.do',
        expUrl:'DevSearchTradeCheck.do',
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
      {xtype:'hiddenfield',name:'trsu.evaldesc',id: mep + 'evaldesc'},
      { xtype: 'label', id: mep + 'applyhtml', html: '' }
    ];
     
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'tranid',item.tranid);
    
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
      
      me.OnCreateApproveWin();
      
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','02');
      tools.SetValue(mep+'flowstatus','05');
      //me.OnSubmit();
   },
   
   //审核未通过
   OnBack:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnCreateApproveWin();
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','02');
      tools.SetValue(mep+'flowstatus','04');
      //me.OnSubmit();
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
       
    if(record.flowstatus != '02'){
      tools.BtnsDisable(['btnDo'], mep);
    } else{
      tools.BtnsEnable(['btnDo'], mep);
    };
  }, 
  
  //提交
  OnCommit:function(){
     var me = this;
     var mep = me.tranPrefix;  
     tools.SetValue(mep+'evaldesc',tools.GetValue(mep+'addevaldesc'));
     me.OnSubmit();
  },
  
  //创建审批的窗口
  OnCreateApproveWin:function(){
     var me = this;
     var mep = me.tranPrefix;
     var winWidth = 500;
     var winHeight = 200;
     var nowdate = new Date();
   
     if (Ext.getCmp(mep + 'detailwin')) {
       Ext.getCmp(mep + 'detailwin').destroy();
     };
     
     var items = [
       ' ', { id: mep + 'btnFormCommit', text: '提交', iconCls: 'icon-save', handler: me.OnCommit, scope: me },
       '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
     ];

     me.editDetailControls = [
       tools.FormTextArea('评价结论', 'evaldesc', mep + 'addevaldesc', 200, '98%', true, 18, 6)   
     ];

     me.disNews = [];
     me.disEdits = [];
     me.enNews = ['addevaldesc'];
     me.enEdits = ['addevaldesc'];
     
     me.plDetailEdit = Ext.create('Ext.form.Panel', {
       id:mep + 'pldetailedit',
       columnWidth:1,
       fieldDefaults: {
         labelSeparator: '：',
         labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
         labelPad: 0,
         labelStyle: 'font-weight:bold;'
       },
       frame: true,
       title: '',
       bodyStyle: 'padding:5px;background:#FFFFFF',
       defaultType: 'textfield',
       closable: false,
       header: false,
       unstyled: true,
       scope: me,
       tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
       items: me.editDetailControls    
     });
     
     me.winDetail = Ext.create('Ext.Window', {
       id: mep + 'detailwin',
       title: '审核',
       width: winWidth,
       height:winHeight,
       maximizable: true,
       closeAction: 'hide',
       modal: true,
       layout: 'fit',
       plain: false,
       closable: true,
       draggable: true,
       constrain: true,
       items : [me.plDetailEdit]
     });
  },
  
  OnUserSelect : function (render, item) {
    var me = this;
    var mep = me.tranPrefix;
    if (item && !Ext.isEmpty(item[0].userid)) {
      tools.SetValue(mep + 'evaluser', item[0].userid);
      tools.SetValue(mep + 'evalusername', item[0].username);
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

    html += '</table>';
    html + '</td></tr>';
  
    html += '</table>';
    return html;
  }
   
});