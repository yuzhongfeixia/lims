Ext.define('alms.viewchange', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: 'GLP文件更改申请查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_changeapply',
      storeUrl: 'GlpSearchGlpFileChange.do',
      expUrl: 'GlpSearchGlpFileChange.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfileid', id: mep + 'searchfileid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: '', iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'changeapplyhtml', html: '' },
      {xtype:'hiddenfield',name:'gfc.tranid',id: mep + 'tranid'}
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
          'gfc.changefileid': tools.GetEncode(tools.GetValue(mep + 'searchfileid')),
          'gfc.changefilename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择文件更改申请记录！');
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
  
  OnShowChangeApplyView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowChangeApplyHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'changeapplyhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowChangeApplyView(item);
    }
    return true;
  },
  
  ShowChangeApplyHtml: function(record){
    var changeapply = tools.JsonGet('GlpGetGlpFileChange.do?gfc.tranid='+record.tranid);
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>GLP文件更改申请查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="40%">' +alms.GetItemData(changeapply.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">拟更改文件编号</td>' +
      '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changefileid)+'</td></tr>';
      
    html += '<tr class="infotr"> ' +
    '<td class="infoat" align="center" width="10%">文件名称</td>' +
    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changefilename)+'</td>'+
    '<td class="infoat" align="center" width="10%">替代文件</td>' +
    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.replacefilename)+'</td></tr>';
    
    html += '<tr class="infotr"> ' +
    '<td class="infoat" align="center" width="10%">申请部门</td>' +
    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.changedeptname)+'</td>'+
    '<td class="infoat" align="center" width="10%">会办部门</td>' +
    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(changeapply.deptname)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:80px;">更改理由</td>' +
      '<td class="infoc infoleft" colspan=3  align="left" width="90%">'+alms.GetItemData(changeapply.changereason)+'</td></tr>';
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:80px;">更改后内容</td>' +
      '<td class="infoc infoleft" colspan=3  align="left" width="90%">'+alms.GetItemData(changeapply.changedesc)+'</td></tr>';
    
    html += '</table>';
    
    
    return html
    
  }
  
});
