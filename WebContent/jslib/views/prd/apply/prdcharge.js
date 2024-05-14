Ext.define('alms.prdcharge',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'耗材申请',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdapply',
          storeUrl:'PrdSearchPrdApplyByPrdSource.do',
          saveUrl:'PrdSavePrdApplyCharge.do',
          expUrl:'PrdSearchPrdApplyByPrdSource.do',
          idPrevNext:'tranid',
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
      {xtype:'hiddenfield',name:'prdapply.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'prdapply.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'prdapply.flowaction',id: mep + 'flowaction'},
      {xtype:'hiddenfield',name:'prdapply.prdsource',id: mep + 'prdsource'},
      {xtype:'hiddenfield',name:'prdapply.checkdesc',id: mep + 'checkdesc'},
      { xtype: 'label', id: mep + 'applyhtml', html: '' }
    ];
     
   },
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'prdapply.flowstatus':'05',
           'prdapply.flowaction':'03'
         })
       });
     }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'tranid',item.tranid);
    tools.SetValue(mep+'prdsource',item.prdsource);
    
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
      var prdsource = tools.GetValue(mep + 'prdsource');
      if(prdsource == '02'){
        tools.SetValue(mep+'flowaction','04');
        tools.SetValue(mep+'flowstatus','07');
      }else{
        tools.SetValue(mep+'flowaction','03');
        tools.SetValue(mep+'flowstatus','88');
      }
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
      tools.SetValue(mep+'flowaction','03');
      tools.SetValue(mep+'flowstatus','06');
   },
   
   //提交
   OnCommit:function(){
      var me = this;
      var mep = me.tranPrefix;
      tools.SetValue(mep+'checkdesc',tools.GetValue(mep+'appcheckdesc'));
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
       tools.FormTextArea('审核意见', 'checkdesc', mep + 'appcheckdesc', 200, '100%', false, 18,6)
      ];
      
      me.disNews = [];
      me.disEdits = [];
      me.enNews = ['appcheckdesc'];
      me.enEdits = ['appcheckdesc'];
      
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
      var prds = tools.JsonGet('PrdGetListPrdApplyDetail.do?prddetail.tranid='+item.tranid).data;
      
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
      html += '<tr><td align="center">'
      
      html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
      html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>试剂耗材采购申请表</b></td></tr>';
      
      if(prds.length > 0){
//        html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%">试剂耗材列表</td></tr>';
        html += '<tr class="infotr"><td class="infoat"  align="center" width="10%">明细序号</td>' +
        '<td class="infoat" align="center" colspan="2" width="10%">名称</td>' +
        '<td class="infoat" align="center" colspan="2" width="10%">规格</td>' +
        '<td class="infoat" align="center" colspan="1" width="10%">数量</td>' +
        '<td class="infoat" align="center" colspan="2" width="10%">备注</td></tr>';
        
        for(var i=0;i<prds.length;i++){
            var prd = prds[i];
            html += '<tr class="infotr"><td class="infoc" colspan="1" align="center" width="10%">'+alms.GetItemData(prd.prdserial) +'</td>' +
            '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(prd.prdname) +'</td>' +
            '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(prd.prdstandard) +'</td>' +
            '<td class="infoc" align="center" colspan="1" width="10%">'+alms.GetItemData(prd.prdcount) +'</td>' +
            '<td class="infoc" align="center" colspan="2" width="10%">'+alms.GetItemData(prd.remark) +'</td></tr>';
        }
      }
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">特殊要求说明</td>' +
      '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.tranremark) +'</td></tr>';
      html += '<tr class="infotr"><td class="infoat" colspan=8 align="center" width="10%"></td></tr>';
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranid) +'</td>'+
      '<td class="infoat" align="center" width="10%">使用项目</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.projectid) +'</td>'+
      '<td class="infoat" align="center" width="10%">申请人姓名</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.tranusername) +'</td>'+
      '<td class="infoat" align="center" width="10%">申请日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.trandate) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">室主任</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.confirmusername) +'</td>'+
      '<td class="infoat" align="center" width="10%">室主任确认日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.confirmdate) +'</td>'+
      '<td class="infoat" align="center" width="10%">室主任意见</td>' +
      '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemData(item.confirmdesc) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">经销渠道</td>' +
      '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(item.fundsource) +'</td></tr>';
      
      html += '<tr class="infotr"><td class="infoat" align="center" width="10%">办公室</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(item.auditusername) +'</td>'+
      '<td class="infoat" align="center" width="10%">办公室确认日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(item.auditdate) +'</td>'+
      '<td class="infoat" align="center" width="10%">办公室意见</td>' +
      '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemData(item.auditdesc) +'</td></tr>';
      
      html += '</table>';
      html + '</td></tr>';
    
      html += '</table>';
      return html;
   }
   
});