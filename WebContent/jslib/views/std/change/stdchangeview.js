Ext.define('alms.stdchangeview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '标准变更内容识别记录查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdchanhe',
      storeUrl: 'StdSearchStdChange.do',
      expUrl: 'StdSearchStdChange.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
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
      { xtype: 'label', id: mep + 'stdchangeviewhtml', html: '' },
      {xtype:'hiddenfield',name:'testsure.tranid',id: mep + 'tranid'}
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
          'stdchange.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
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
  
  OnShowStdChangeView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowStdChangeHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'stdchangeviewhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowStdChangeView(item);
    }
    return true;
  },
  
  ShowStdChangeHtml: function(record){
    var stdchange = tools.JsonGet('StdGetStdChange.do?stdchange.tranid='+record.tranid);
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>标准变更内容识别记录查看</b></td></tr>';
   
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
  '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.tranid)+ '</td>' +
  '<td class="infoat" align="center" width="10%">类别(产品/项目/参数)</td>' +
  '<td class="infoc infoleft"  align="left" width="15%">' +alms.GetItemData(stdchange.parametername)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">已批准的标准代号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.stdid)+ '</td>' +
    '<td class="infoat" align="center" width="10%">已批准的标准名称</td>' +
    '<td class="infoc infoleft"  align="left" width="15%">' +alms.GetItemData(stdchange.stdname)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">变更后的标准代号</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.replstdid)+ '</td>' +
    '<td class="infoat" align="center" width="10%">变更后的标准名称</td>' +
    '<td class="infoc infoleft"  align="left" width="15%">' +alms.GetItemData(stdchange.replstdname)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">确认方式</td>' +
    '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.sureabilityname)+ '</td>' +
    '<td class="infoat" align="center" width="10%">标准跟踪人</td>' +
    '<td class="infoc infoleft"  align="left" width="15%">' +alms.GetItemData(stdchange.stdusername)+ '</td></tr>';
    
    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.tranid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">确认方式</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdchange.sureability)+'</td>'+
//      '<td class="infoat" align="center" width="10%">标准跟踪人</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(stdchange.stdusername)+'</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">类别(产品/项目/参数)</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.parametername)+ '</td>'+
//      '<td class="infoat" align="center" width="10%">已批准的标准代号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.stdid)+ '</td>'+
//      '<td class="infoat" align="center" width="10%">已批准的标准名称</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.stdname)+ '</td></tr>';
//    
//    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">变更后的标准代号</td>' +
//      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(stdchange.replstdid)+ '</td>' +
//      '<td class="infoat" align="center" width="10%">变更后的标准名称</td>' +
//      '<td class="infoc infoleft" colspan=5 align="left" width="15%">' +alms.GetItemData(stdchange.replstdname)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">变更内容</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(stdchange.changecontent)+'</td></tr>';
    
    html += '</table>';
    
    return html
    
  },
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnPrevRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
    
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) {
            tools.alert('已经是当前列表第一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i - 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnNextRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
      
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) {
            tools.alert('已经是当前列表最后一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i + 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  }
  
  
});
