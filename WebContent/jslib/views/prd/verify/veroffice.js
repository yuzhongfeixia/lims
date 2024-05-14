Ext.define('alms.veroffice',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材验证',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdverify',
          storeUrl:'PrdSearchPrdVerify.do',
          saveUrl:'PrdSavePrdVerifyOffice.do',
          expUrl:'PrdSearchPrdVerify.do',
          idPrevNext:'verifyid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasSubmit: true
          
      });
      me.callParent(arguments);
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
      {xtype:'hiddenfield',name:'prdverify.verifyid',id: mep + 'verifyid'},
      {xtype:'hiddenfield',name:'prdverify.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'prdverify.flowaction',id: mep + 'flowaction'},
      {xtype:'hiddenfield',name:'prdverify.auditdesc',id: mep + 'auditdesc'},
      { xtype: 'label', id: mep + 'applyhtml', html: '' }
    ];
     
   },
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdverify.flowstatus':'02',
           'prdverify.flowaction':'02'
         })
       });
     }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'verifyid',item.verifyid);
    
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
      me.isPassed = true;
      me.OnCreateApproveWin();
      
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','05');
      tools.SetValue(mep+'flowstatus','05');
   },
   
   //审核未通过
   OnBack:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.isPassed = false;
      me.OnCreateApproveWin();
    
      if(me.winDetail){      
        me.winDetail.show();
      }
      tools.SetValue(mep+'flowaction','02');
      tools.SetValue(mep+'flowstatus','04');
   },
   
   //提交
   OnCommit:function(){
      var me = this;
      var mep = me.tranPrefix;
      tools.SetValue(mep+'auditdesc',tools.GetValue(mep+'appauditdesc'));
      me.OnSubmit();
   },
   
   OnAfterSubmit:function(){
     var me = this;
     var mep = me.tranPrefix;
     tools.BtnsDisable(['btnPass','btnBack','btnFormCommit'], mep);
   },
   
     //双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsEnable(['btnPass'], mep);
    tools.BtnsEnable(['btnBack'], mep);
  },
   
   //创建审批的窗口
   OnCreateApproveWin:function(){
      var me = this;
      var mep = me.tranPrefix;
      var winWidth = 500;
      var winHeight = 200;
    
      if (Ext.getCmp(mep + 'detailwin')) {
        Ext.getCmp(mep + 'detailwin').destroy();
      };
      
      var items = [
        ' ', { id: mep + 'btnFormCommit', text: '提交', iconCls: 'icon-save', handler: me.OnCommit, scope: me },
        '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
      ];
      
      me.editDetailControls = [
       tools.FormTextArea('审核意见', 'auditdesc', mep + 'appauditdesc', 200, '100%', false, 18,6)
      ];
      
      me.disNews = [];
      me.disEdits = [];
      me.enNews = ['appauditdesc'];
      me.enEdits = ['appauditdesc'];
      
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
   
   //预览html内容
   OnReviewPrdApply:function(item){
      var me = this;
      var mep = me.tranPrefix;
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
      html += '<tr><td align="center">'
      
      html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="60%">';
      html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>耗材验证记录表</b></td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="15%">'+alms.GetItemData(item.verifyid) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">试剂耗材名称</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.prdname) +'</td>'+
      '<td class="infoat" align="center" width="10%">规格、型号</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.prdstandard) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">生产厂家</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.factoryname) +'</td>'+
      '<td class="infoat" align="center" width="10%">供应商</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.tradeid) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">出厂批号</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.factorycode) +'</td>'+
      '<td class="infoat" align="center" width="10%">进货时间</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemDateData(item.buydate) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">(拟)进货量</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.buycount) +'</td>'+
      '<td class="infoat" align="center" width="10%">业务员</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td></tr>';
      
      html += '</table>';
      html + '</td></tr>';
    
      html += '</table>';
      return html;
   }
   
});