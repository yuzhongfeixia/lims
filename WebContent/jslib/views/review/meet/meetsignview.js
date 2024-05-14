Ext.define('alms.meetsignview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '管理评审会议签到查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewmeetsign',
      storeUrl: 'RewSearchMeetSign.do',
      expUrl: 'RewSearchMeetSign.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'meetid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '会议编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchmeetid', id: mep + 'searchmeetid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '会议主题', labelWidth: 70, width: 200, maxLength: 40, name: 'searchmeettopic', id: mep + 'searchmeettopic', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'meetsignviewhtml', html: '' },
      {xtype:'hiddenfield',name:'ms.meetid',id: mep + 'meetid'}
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
    var record = tools.OnGridLoad(me.plGrid, '请选择管理评审会议签到记录！');
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
  
  OnShowMeetSignView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowMeetSignHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'meetsignviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.meetid)) {
      tools.SetValue(mep + 'meetid',item.meetid);
      me.tabMain.setActiveTab(1);
      me.OnShowMeetSignView(item);
    }
    return true;
  },
  
  ShowMeetSignHtml: function(record){
    var meetsign = tools.JsonGet('RewGetMeetSign.do?ms.meetid='+record.meetid);
    var reviewmeetuser = tools.JsonGet('RewGetListReviewMeetUser.do?mu.meetid=' + meetsign.meetid).data;
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>管理评审会议签到查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">会议编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(meetsign.meetid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">会议主题</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(meetsign.meettopic)+'</td>'+
      '<td class="infoat" align="center" width="10%">会议时间</td>' +
      '<td class="infoc infoleft" colspan=3 align="left" width="15%">'+alms.GetItemDateData(meetsign.meetdate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">会议地点</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(meetsign.meetplace)+'</td></tr>';
    
    html += '</table>';
   // html += '<br/>';
    var total = '';
    if(reviewmeetuser.length>0){
      for(var i = 0; i < reviewmeetuser.length; i++){
           if(i==0){
             total +=alms.GetItemData(reviewmeetuser[i].partusername);
             continue;
           }
           total +=(','+alms.GetItemData(reviewmeetuser[i].partusername));
      }
    }
    html += '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;" width="80%">';
    html += '<tr class="infotr"><td class="infoat" align="center" width="13%">参与人员</td>' +
      '<td class="infoc infoleft" colspan=7 align="left" width="90%">' +total+ '</td></tr>';
    html += '</table>';
    return html
    
  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'ms.meetid': tools.GetEncode(tools.GetValue(mep + 'searchmeetid')),
          'ms.meettopic': tools.GetEncode(tools.GetValue(mep + 'searchmeettopic'))
        });
      });
    };
  }
});
