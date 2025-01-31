Ext.define('alms.recordview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员培训记录查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_usertrain',
      storeUrl: 'StaffSearchUserTrain.do',
      expUrl: 'StaffSearchUserTrain.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '培训项目', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtraincontent', id: mep + 'searchtraincontent', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'recordapplyhtml', html: '' },
      {xtype:'hiddenfield',name:'train.tranid',id: mep + 'tranid'}
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
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'train.tranid':tools.GetValueEncode(mep+'searchtranid'),
          'train.traincontent':tools.GetValueEncode(mep+'searchtraincontent')
        });
      });
    };
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
    var record = tools.OnGridLoad(me.plGrid, '请选择人员培训记录！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
  },
  
  OnShowUserTrainView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowUserTrainHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'recordapplyhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowUserTrainView(item);
    }
    return true;
  },
  
  ShowUserTrainHtml: function(record){
    var me = this;
    var recordapply = tools.JsonGet('StaffGetUserTrain.do?train.tranid='+record.tranid);
    var users = tools.JsonGet(tools.GetUrl('StaffGetListUserTrainDetail.do?traindetail.tranid=')+recordapply.tranid).data;
    
    var user = [];
    for(var i = 0; i < users.length; i++){
      user.push(users[i].username);
    }
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>培训实施记录表</b></td></tr>';
     html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 5-1-1</td></tr>';
  html += '</table>';
  html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(recordapply.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">培训编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(recordapply.relaid)+'</td>'+
      '<td class="infoat" align="center" width="10%">业务员</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(recordapply.tranusername)+'</td>'+
      '<td class="infoat" align="center" width="10%">培训日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(recordapply.begindate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">培训形式</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(recordapply.traintypename)+ '</td>' +
      '<td class="infoat" align="center" width="10%">记录日期</td>' +
      '<td class="infoc infoleft" colspan=5 align="left" width="15%">'+alms.GetItemDateData(recordapply.trandate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">培训目标</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.traintarget)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">培训对象</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.trainobject)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">培训项目</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.traincontent)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">实施情况</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.trainresult)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">教员</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.trainteacher)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.trainexam)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">主持人</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(recordapply.traintaker)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">参与培训员工</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="90%">'+alms.GetItemData(user)+'</td></tr>';
     html += '<tr class="infotr"><td class="infoat" align="center" width="10%">记录人</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="90%">'+alms.GetItemData(recordapply.tranusername)+'</td>';
      html += '<td class="infoat" align="center" width="10%">日期</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="90%">'+alms.GetItemDateData(recordapply.trandate)+'</td></tr>';
    html += '</table>';
    me.html = html;
    return html
    
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
//        LODOP.PRINT();//打印功能
    }
  }
  
});
