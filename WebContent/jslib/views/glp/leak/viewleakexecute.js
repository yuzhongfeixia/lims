Ext.define('alms.viewleakexecute', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件泄露信息查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_manbasleak',
      storeUrl: 'GlpSearchGlpFileLeak.do',
      expUrl: 'GlpSearchGlpFileLeak.do',
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
' ', { xtype: 'textfield', fieldLabel: '处置编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: "", iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'basleakhtml', html: '' },
      {xtype:'hiddenfield',name:'gfk.tranid',id: mep + 'tranid'}
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
  
  
//  OnBeforeSearch: function () {
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (me.gridStore) {
//      me.gridStore.on('beforeload', function (store, options) {      
//        Ext.apply(store.proxy.extraParams, {
//          'bgn.flowstatus': '02'
//        });
//      });
//    };
//  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'gfk.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
//          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择文件泄露信息记录！');
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
  
  OnShowBasLeakView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowBasLeakHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'basleakhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowBasLeakView(item);
    }
    return true;
  },
  
  ShowBasLeakHtml: function(record){
    var leak = tools.JsonGet('GlpGetGlpFileLeak.do?gfk.tranid='+record.tranid);
    var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=GlpFileLeak&todo.tranid=' + record.tranid);
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="100%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>GLP文件泄露信息查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">处置编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(leak.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">泄密人编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(leak.leakuser)+'</td>'+
      '<td class="infoat" align="center" width="10%">泄露人姓名</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(leak.leakusername)+'</td>'+
      '<td class="infoat" align="center" width="10%">泄密时间</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(leak.leakdate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">报告时间</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="15%">' +alms.GetItemDateData(leak.reportdate)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">泄密原因</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(leak.leakreason)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">造成后果</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(leak.leakresult)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">泄密简述</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(leak.leakdesc)+'</td></tr>';
    
    html += '</table>';
    
    html += '<br/>';
    
    //html += flowinfo;
    
    return html
    
  }
  
});
