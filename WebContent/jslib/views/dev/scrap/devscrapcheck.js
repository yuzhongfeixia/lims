Ext.define('alms.devscrapcheck',{
  extend:'gpersist.base.busform',
   
  constructor:function(config){
    var me = this;
    Ext.apply(config,{
      editInfo:'设备降级/报废管理',
      winWidth:750,
      winHeight:250,
      hasColumnUrl:true,
      columnUrl:'SysSqlColumn.do?sqlid=p_get_devscrap',
      storeUrl:'DevSearchDevScrap.do',
      saveUrl:'DevSaveDevScrapCheck.do',
      expUrl:'DevSearchDevScrap.do',
      idPrevNext:'tranid',
      hasGridSelect:true,
      hasPage:true,
      hasPrevNext : true,
      hasSubmit: true
    });
    me.callParent(arguments);
  },
   
  OnInitConfig:function(){
    var me = this;
    me.callParent(arguments);
  },
   
  OnBeforeFormLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnInitGridToolBar();
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
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
      {xtype:'hiddenfield',name:'ds.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'ds.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'ds.flowaction',id: mep + 'flowaction'},
      {xtype:'hiddenfield',name:'ds.checkdesc',id: mep + 'checkdesc'},
      { xtype: 'label', id: mep + 'dschtml', html: '' }
    ];
  },
   
  OnBeforeSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    if(me.gridStore){
      me.gridStore.on('beforeload',function(store,options){
        Ext.apply(store.proxy.extraParams,{
          'ds.devid':tools.GetValueEncode(mep + 'searchdevid'),
          'ds.devname':tools.GetValueEncode(mep + 'searchdevname')
        })
      });
    }
  },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep+'tranid',item.tranid);
    
    var html = me.OnShowDevScrap(item);
    
    Ext.getCmp(mep + 'dschtml').update(html);
    
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
    
    me.OnCreateAllowWin();
    
    if(me.winDetail){      
      me.winDetail.show();
    }
    tools.SetValue(mep+'flowaction','11');
    tools.SetValue(mep+'flowstatus','13');
  },
   
   //审核未通过
  OnBack:function(){
    var me = this;
    var mep = me.tranPrefix;

    me.OnCreateAllowWin();
    
    if(me.winDetail){      
      me.winDetail.show();
    }
    tools.SetValue(mep+'flowaction','11');
    tools.SetValue(mep+'flowstatus','14');
  },
   
  //提交
  OnCommit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'checkdesc',tools.GetValue(mep+'allowcheckdesc'));
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
  OnCreateAllowWin:function(){
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
      
      //是否审核通过 
//    if(me.isPassed){
    me.editDetailControls = [
      tools.FormTextArea('审核意见', 'allowcheckdesc', mep + 'allowcheckdesc', 200, '100%', false, 18,6)
    ];
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['allowcheckdesc'];
    me.enEdits = ['allowcheckdesc'];
    
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
  OnShowDevScrap:function(item){
    var me = this;
    var mep = me.tranPrefix;
    
    var devscrap = tools.JsonGet('DevGetDevScrap.do?ds.tranid='+item.tranid);
    
    var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:100%;">' ;
    html += '<tr><td align="center">'
    
    html += '<table  cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>设备降级报废申请</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
    '<td class="infoc infoleft" align="left" width="22%">'+alms.GetItemData(devscrap.tranid) +'</td>'+
    '<td class="infoat" align="center" width="10%">设备编号</td>' +
    '<td class="infoc infoleft" align="left" width="22%">'+alms.GetItemData(devscrap.devid) +'</td>'+
    '<td class="infoat" align="center" width="10%">设备名称</td>' +
    '<td class="infoc infoleft" align="left" width="23%">'+alms.GetItemData(devscrap.devname) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">生产厂家</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devscrap.factoryname) +'</td>'+
    '<td class="infoat" align="center" width="10%">设备型号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devscrap.devstandard) +'</td>'+
    '<td class="infoat" align="center" width="10%">出厂编号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devscrap.factorycode) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">申请部门</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devscrap.trandeptname) +'</td>'+
    '<td class="infoat" align="center" width="10%">申请负责人</td>' +
    '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(devscrap.tranusername) +'</td>'+
    '<td class="infoat" align="center" width="10%">申请时间</td>' +
    '<td class="infoc infoleft" colspan="3"  align="left" width="10%">'+alms.GetItemDateData(devscrap.trandate) +'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">申请事由</td>' +
    '<td class="infoc  infoleft" colspan="7" align="left" width="10%">'+alms.GetItemData(devscrap.applyreason) +'</td></tr>';

    html += '</table>';
    html + '</td></tr>';
  
    html += '</table>';
    return html;
  }
   
});