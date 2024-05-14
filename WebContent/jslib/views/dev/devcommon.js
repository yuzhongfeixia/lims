Ext.define('alms.devcommon', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '常用设备功能检查',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcommon',
      storeUrl: 'DevSearchDevCommon.do',
      saveUrl: 'DevSaveDevCommon.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevCommon.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var devitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevforcommonid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevforcalibname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    
    
//    var devid = Ext.create('Ext.ux.GridPicker', {
//      fieldLabel: '设备编号', name: 'dc.devid', id: mep + 'devid', winTitle: '选择设备',
//      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
//      blankText: '设备编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
//      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
//      storeUrl: 'DevSearchBasDevForCommon.do',
//      editable:false,
//      searchTools:devitems,
//      hasPage: true, pickerWidth: 600, pickerHeight: 500
//    });
//    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'dc.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('管理员姓名', 'dc.devmanagername', mep + 'devmanagername', 10, '96%', false, 4),
          tools.FormCombo('核查单位', 'dc.commondept', mep + 'commondept', tools.ComboStore('CalibUnit'), '96%', true, 13)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('设备名称及编号', 'dc.devid', mep + 'devid', 10, '100%', false, 4),
          tools.FormDate('检定日期', 'dc.commondate', mep + 'commondate', 'Y-m-d', '100%', true, 7),
          tools.FormText('检查人', 'dc.commonuser', mep + 'commonuser', 10, '100%', false, 6),
          {xtype:'hiddenfield',name:'dc.devmanager',id: mep + 'devmanager'},
          {xtype:'hiddenfield',name:'dc.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('检查内容', 'dc.commoncontent', mep + 'commoncontent', 400, '100%', true, 8, 4),
      tools.FormTextArea('检查结果', 'dc.commonresult', mep + 'commonresult', 200, '100%', true, 9, 4),
      tools.FormTextArea('备注', 'dc.remark', mep + 'remark', 200, '100%', true,10, 4)
    ];
    me.disNews = [ 'tranid','devmanager'];
    me.disEdits = ['tranid','devmanager'];
    me.enNews = ['commonuser', 'commoncontent', 'devmanagername','commonresult', 'remark','devid','commondept','commondate'];
    me.enEdits = ['commonuser', 'commoncontent', 'devmanagername','commonresult', 'remark','devid','commondept','commondate'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments);
    tools.SetValue(mep + 'commonuser', gpersist.UserInfo.user.username);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称或编号', labelWidth: 100, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
//      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
//      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'dc.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'dc.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.alert( gpersist.UserInfo.user);
    console.log( gpersist.UserInfo)
    
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'commondate', item.commondate);
      tools.SetValue(mep + 'commondept', item.commondept);
      tools.SetValue(mep + 'commonuser', item.commonuser);
      tools.SetValue(mep + 'commoncontent', item.commoncontent);
      tools.SetValue(mep + 'commonresult', item.commonresult);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'managerdate', item.managerdate);
      me.html = me.ShowDevCommonHtml(item);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnDevSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.devid) {
      tools.SetValue(mep+'devid',item.devid);
      tools.SetValue(mep+'devmanager',item.devmanager);
      tools.SetValue(mep+'devmanagername',item.devmanagername);
    }
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  },
  
  OnDevSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var dev = Ext.getCmp(mep+'devid');
    me.OnDevBeforeLoad();
    dev.store.loadPage(1);    
  },
    
  OnDevBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var dev = Ext.getCmp(mep+'devid');
    if(dev.store) {      
      dev.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'bd.devname': tools.GetValueEncode(mep + 'searchdevforcalibname')
        });
      });
    };
  },
  OnPrintTask: function () {
    var me = this;
    var mep = me.tranPrefix;
    alert(me.html)
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
  },
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'editwin')) {
      Ext.getCmp(mep + 'editwin').destroy();
    }
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnFormValid', text: '有效', iconCls: 'icon-valid', handler: me.OnFormValid, scope: me },
      '-', ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
//      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
    
    me.OnBeforeCreateEditToolBar();
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnAfterCreateEditToolBar();
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pledit',
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
      tbar : me.tbEdit,
      items: me.editControls    
    });
    
    me.winEdit = Ext.create('Ext.Window', {
      id: mep + 'editwin',
      title: Ext.String.format(gpersist.STR_FMT_DETAIL, me.editInfo),
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plEdit],
      listeners: {'beforehide' : {fn: me.OnBeforeHide, scope:me } }
    });
    
    me.OnAfterCreateEdit();
  },
  ShowDevCommonHtml:function(item){
    var me = this;
    var mep = me.tranPrefix;
    var  html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
    html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>常用设备功能检查表</b></td></tr>';
    html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" > QCR 5-4-8</td></tr>';
    html += '</table>';
    html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
  
    html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">仪器设备名称</td>' +
    '<td class="infoc infoleft" align="left"  width="40%">'+alms.GetItemData(item.devname) +'</td>'+
    '<td class="infoat" align="center"  " width="10%">编  号</td>' +
    '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(item.devid) +'</td></tr>';

  html += '<tr class="infotr"><td class="infoat" align="center"   width="10%">检查日期</td>' +
  '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemDateData(item.commondate) +'</td>'+
  '<td class="infoat" align="center"  width="10%">检查人</td>' +
  '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(item.commonuser) +'</td></tr>';
  
  html += '<tr class="infotr" style="height: 150px"><td class="infoat" align="center"  width="10%">检查内容</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.commoncontent) +'</td></tr>';

  
  html += '<tr class="infotr" style="height: 150px"><td class="infoat" align="center"  width="10%">检查结果</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.commonresult) +'</td></tr>';
  
  
  html += '<tr class="infotr" style="height: 150px"><td class="infoat" align="center"  width="10%">备注</td>' +
  '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(item.remark) +'</td></tr>';
    
    html += '</table>';
    
    return html;
  }
  
});
