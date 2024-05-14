Ext.define('alms.destoryview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件销毁记录查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_glpfiledestroyrecord',
      storeUrl: 'IncFileSearchIncFileDestroyRecord.do',
      expUrl: 'IncFileSearchIncFileDestroyRecord.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
      { xtype: 'label', id: mep + 'destoryapplyhtml', html: '' },
      {xtype:'hiddenfield',name:'da.tranid',id: mep + 'tranid'}
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
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          //'bgn.flowstatus': '02'
          'ifdr.filename':tools.GetValueEncode(mep+'searchfilename')
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
    var record = tools.OnGridLoad(me.plGrid, '请选择设备购买申请！');
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
  
  OnShowDestoryApplyView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowDestoryApplyHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'destoryapplyhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDestoryApplyView(item);
    }
    return true;
  },
  
  ShowDestoryApplyHtml: function(record){
    var destoryapply = tools.JsonGet('IncFileGetIncFileDestroyRecord.do?ifdr.tranid='+record.tranid);
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>文件销毁记录查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">销毁编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(destoryapply.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">文件编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(destoryapply.fileid)+'</td>'+
      '<td class="infoat" align="center" width="10%">文件名称</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(destoryapply.filename)+'</td>'+
      '<td class="infoat" align="center" width="10%">销毁日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(destoryapply.destroydate)+'</td></tr>';
      
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">批准人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(destoryapply.allowusername)+ '</td>' +
      '<td class="infoat" align="center" width="10%">批准日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(destoryapply.allowdate)+'</td>'+
      '<td class="infoat" align="center" width="10%">监销人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(destoryapply.monitusername)+'</td>'+
      '<td class="infoat" align="center" width="10%">监销日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(destoryapply.monitdate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%" style="height:60px;">销毁原因</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(destoryapply.destroyreason)+'</td></tr>';
      
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(destoryapply.remark)+'</td></tr>';
    
    html += '</table>';
    
    return html
    
  }
  
});
