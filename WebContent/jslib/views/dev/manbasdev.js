Ext.define('alms.manbasdev', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '仪器设备一览表',
      winWidth: 750,
      winHeight: 500,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDev.do',
      saveUrl: 'DevSaveBasDev.do',
      hasGridSelect: true,
      expUrl: 'DevSearchBasDev.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'devid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var devmanager = tools.UserPicker(mep + 'devmanagername','bd.devmanagername','设备负责人');
    
    devmanager.on('griditemclick', me.OnUserSelect, me);
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText('设备编号', 'bd.devid', mep + 'devid', 40, '96%', true, 1),
          tools.FormText('生产厂家', 'bd.factoryname', mep + 'factoryname', 40, '96%', false, 3),
          tools.FormText('设备型号', 'bd.devstandard', mep + 'devstandard', 40, '96%', false, 5),
          tools.FormText('使用温度', 'bd.usetemp', mep + 'usetemp', 10, '96%', true, 9),
          devmanager,
          tools.FormText('价格(万元)', 'bd.devprice', mep + 'devprice', 20, '96%', true, 8,'is15p2'),
          tools.FormDate('上次检定', 'bd.prevtime', mep + 'prevtime', 'Y-m-d', '96%', true, 7),
          tools.FormCheckCombo('操作人员', 'bd.devoperators', mep + 'devoperators', tools.ComboStore('User'), '96%', true, 21)
            
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('设备名称', 'bd.devname', mep + 'devname', 40, '100%', false, 2),
          tools.FormText('出厂编号', 'bd.factorycode', mep + 'factorycode', 40, '100%', true, 4),
          tools.FormCombo('设备类型', 'bd.devtype', mep + 'devtype', tools.ComboStore('DevType', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
          tools.FormText('使用湿度', 'bd.usehumid', mep + 'usehumid', 10, '100%', true, 10),
          tools.FormCombo('所属实验室', 'bd.labid', mep + 'labid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '100%', false, 11),
          tools.FormDate('购买日期', 'bd.buydate', mep + 'buydate', 'Y-m-d', '100%', true, 7),
          tools.FormDate('下次检定', 'bd.nexttime', mep + 'nexttime', 'Y-m-d', '100%', true, 7),
          {xtype:'hiddenfield',name:'bd.devmanager',id: mep + 'devmanager'},
          {xtype:'hiddenfield',name:'bd.nowdevid',id: mep + 'nowdevid'},
          {xtype:'hiddenfield',name:'am.devoperatorsname',id: mep + 'devoperatorsname'},
          {xtype:'hiddenfield',name:'bd.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('存放位置', 'bd.storeplace', mep + 'storeplace', 100, '100%', true, 9, 4),
      tools.FormTextArea('设备用途', 'bd.devusage', mep + 'devusage', 100, '100%', true, 9, 4),
      tools.FormTextArea('技术指标', 'bd.devrange', mep + 'devrange', 100, '100%', true, 9, 4)
    ];
    me.disNews = ['devid'];
    me.disEdits = ['devid'];
    me.enNews = ['usetemp','usehumid','devperiod','factoryname','devstandard','devusage','devname', 'factorycode',
      'devtype','maintainid','devrange','devprice','buydate','devmanager','prevtime','nexttime','storeplace','labid','devmanagername','devoperators'];
    me.enEdits = ['usetemp','usehumid','devperiod','factoryname','devstandard','devusage','devname', 
      'factorycode','devtype','maintainid','devrange','devprice','buydate','devmanager','prevtime','nexttime','storeplace','labid','devmanagername','devoperators'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'devtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'labid', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      '', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      '', tools.GetToolBarCombo('searchdevstatus', mep + 'searchdevstatus', 160, '设备状态', 60, tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE)),
      '', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }
    ]; 
    
    var items1 = [
       '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
       '', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
       '', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
       '-', { id : mep + 'btnFormPrint', text : '设备二维码打印', iconCls : 'icon-print', handler : me.OnFormPrint, scope : me },
       '-', ' ', { id: mep + 'btnStart', text: '设备启动', iconCls: 'icon-deal', handler: me.OnStart,scope: me },
       '', { id: mep + 'btnStop', text: '设备停用', iconCls: 'icon-deal', handler: me.OnStop,scope: me},
       '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
       '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bd.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'bd.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'bd.devstatus': tools.GetEncode(tools.GetValue(mep + 'searchdevstatus'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item){
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'devtype', item.devtype);
      tools.SetValue(mep + 'maintainid', item.maintainid);
      tools.SetValue(mep + 'usehumid', item.usehumid);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'labid', item.labid);
      tools.SetValue(mep + 'devprice', item.devprice);
      tools.SetValue(mep + 'prevtime', item.prevtime);
      tools.SetValue(mep + 'nexttime', item.nexttime);
      tools.SetValue(mep + 'storeplace', item.storeplace);
      tools.SetValue(mep + 'buydate', item.buydate);
      tools.SetValue(mep + 'devperiod', item.devperiod);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'usehumid', item.usehumid);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'nowdevid', item.devid);
      Ext.getCmp(mep + 'devoperators').setRawValue(item.devoperatorsname);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnCommit: function() {
    var me = this, mep = me.tranPrefix;

    if (me.plGrid) {
      var datas = me.plGrid.getView().getSelectionModel().getSelection();
      
      if (datas.length > 0) {
        tools.SetValue(mep + 'datadeal', me.dataDeal);
        
        if (!me.OnBeforeDev(datas)) 
          return;
        
        Ext.Ajax.request({
          url: 'DevOperateBasDev.do',
          params: me.devParams,
          async: false,
          method: 'POST',
          success: function (response, opts) {
            tools.ResponseAlert(response, function () {
              Ext.getCmp(mep + 'winPrompt').hide();
              me.OnSearch();
            });
          },
          failure: function (response) {
            tools.ResponseAlert(response);
          }
        });
      }
      else 
        tools.alert('请选择需要处理的设备记录！');
    }
  },
  
  OnBeforeDev: function (datas) {
    var me = this, mep = me.tranPrefix;
    
    var json = [];
    var issure = false;
    for (var i = 0; i < datas.length; i++) {
      json.push({devid: datas[i].data.devid});
    }
    
    var operatereason = tools.GetValue(mep+'operatereason');
    
    if(datas.length == 0){
      tools.alert('请选择需要处理的设备记录！');
      return false;
    } else {
      me.devParams = {devs : Ext.encode(json), operatereasons:operatereason, devstatus:me.devstatus};
      return true;
    }
  },
  
  OnStart:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    
    if(datas.length>0){
      
      me.devstatus = '00'
        
      if (!me.winPrompt)
        me.OnCreatePrompt();
        
      if(me.winPrompt) {
        me.winPrompt.show();
        Ext.getCmp(mep + 'operatereason').reset();
      }
    }else{
      tools.alert('请选择需要处理的设备记录！');
    }
  },
  
  OnStop:function(){
    var me = this;
    var mep = me.tranPrefix;
    var isstop = true;
    
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    if(datas.length>0){
      for (var i = 0; i < datas.length; i++) {
        if(datas[i].data.devstatus == '03'){
          tools.alert('该设备已处于报废状态，不能进行停用操作！');
          isstop = false;
          break;
        }if(datas[i].data.devstatus == '04'){
          tools.alert('该设备已处于停用状态，不能进行停用操作！');
          isstop = false;
          break;
        }if(datas[i].data.devstatus == '05'){
          tools.alert('该设备已发出停用申请！');
          isstop = false;
          break;
        }
      }
      
      if(isstop){
        me.devstatus = '05'
          
        if (!me.winPrompt)
          me.OnCreatePrompt();
          
        if(me.winPrompt) {       
          me.winPrompt.show();
          Ext.getCmp(mep + 'operatereason').reset();
        }
      }
    }else{
      tools.alert('请选择需要处理的设备记录！');
    }
  },
  
  OnCreatePrompt: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'winPrompt')) {
      Ext.getCmp(mep + 'winPrompt').destroy();
    }
    
    if (!me.plwinPrompt) {
      me.plwinPrompt = Ext.create('Ext.form.Panel', {
        id: mep + 'plwinPrompt',
        fieldDefaults: {
          width: 370,
          labelSeparator: '：',
          labelWidth: 90,
          labelPad: 0,
          labelStyle: 'font-weight:bold;'
        },
        title: '',
        border: false,
        bodyStyle: 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width: 400,
        defaultType: 'textfield',
        items: [tools.FormTextArea('', 'operatereason', mep + 'operatereason', 200, '100%', true, 18,20)],
        listeners: {
          afterRender: function (form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter: me.OnSavePasswd,
              scope: me
            });
          }
        }
      });
    }
    
    if (!me.winPrompt) {
      me.winPrompt = Ext.create('Ext.window.Window', {
        id: mep + 'winPrompt',
        title: '启用/停用设备原因',
        width: 400,
        height: 150,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plwinPrompt],
        buttons: [{ text: gpersist.STR_BTN_DEAL, id: mep +  'btnOnCommit', handler: me.OnCommit, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winPrompt.hide(); } }]
      });
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'devid').reset();
    Ext.getCmp(mep + 'devid').focus(true, 500);
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.devid) {
          tools.SetValue(mep + 'devid', action.result.data.devid);
        }
      }
    }
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+"devmanager",item.userid);
     tools.SetValue(mep+"devmanagername",item.username);
//     tools.SetValue(mep+"labid",item.deptid);
   }
  },
  
  OnFormPrint:function(){
    var me = this, mep = this.tranPrefix;
    
    var html = '';
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    
    for(var i=0;i<datas.length;i++){
      var record = datas[i].data;
      if (Ext.isEmpty(record.devid)) return;
    
      html += '<table style="page-break-after: always;">';
      html += '<tr><td style="padding-left:20px; padding-top:180px;"><image width="120px" src="qrcode.do?width=95&code=' + tools.GetEncode(tools.GetEncode(record.devid)) + '"></td>' + 
        '<td width="150" style="font-size:15px;font-weight:bold;padding-left:4px;word-wrap:break-word;padding-top:180px;" valign="top" align="left">' + record.devid + 
        '<br />' + record.devname + '</td></tr>';
      html += '</table>';
    }
    
   if (!Ext.isEmpty(html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("试验报告打印");
      LODOP.GET_PRINTER_NAME(0);
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(20, 40, '100%', '100%', html);
      LODOP.SET_PRINTER_INDEXA("Godex ZA12X");
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
   }
  },
  
   OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var date = new Date();
    
    if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
      return;
    
    Ext.getCmp('tpanel' + me.mid).removeAll();

    me.fields = [];
    me.columns = [];
    
    if (me.hasColumnUrl) 
      tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_');
    else {
      if (!me.hasRptGrid)
        tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
      else
        tools.GetRptColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
    }
    
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', me.OnStoreBeforeLoad, me);
    }
      
    me.OnBeforeFormLoad();

    tools.log('OnFormLoad1', date);
    
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id: mep + 'grid',
      region: 'center', 
      frame: false,
      border: false,
      margins: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      viewConfig: {
        autoFill: true,
        stripeRows: true,
        getRowClass: function (record) {
          if (record.data.devstatus == '04') {
            return  'yellow-row';
          }else if (record.data.devstatus == '03'){
            return  'red-row';
          }else{
            return '';
          }
        }
      },
      columns: me.columns,
      store: me.gridStore,
      tbar: me.tbGrid,
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners: {'itemdblclick' : {fn: me.OnShow, scope:me } }
    });
        
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store: me.gridStore,
        displayInfo: true,
        displayMsg: gpersist.STR_PAGE_FMT,
        emptyMsg: gpersist.STR_NO_DATA,
        dock: 'bottom'
      }));
    }
    
    tools.log('OnFormLoad2', date);
    
    me.OnAfterFormLoad();
    
    tools.log('OnFormLoad3', date);
    
    Ext.getCmp('tpanel' + me.mid).add(me.plGrid);
    
    tools.log('OnFormLoad4', date);
    
    if (me.hasAutoLoad)
      me.OnSearch();    
    
    tools.log('OnFormLoad5', date);
  },
  OnBeforeSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'devoperatorsname', Ext.getCmp(mep + 'devoperators').getRawValue());
    return true;
  }


   
});
