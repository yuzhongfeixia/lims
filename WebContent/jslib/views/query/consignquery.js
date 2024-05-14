Ext.define('alms.consignquery', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '试验信息综合查询',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustaskforquery',
      storeUrl: 'LabSearchBusTaskForQuery.do',
      expUrl: 'LabSearchBusTaskForQuery.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'taskid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var nowdate = new Date();
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'完成开始日期',labelWidth:90,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '', {xtype:'datefield',fieldLabel:'完成结束日期',labelWidth:90,width:200,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, +1),selectOnFocus:false,allowBlank:true},
      '-', tools.GetToolBarCombo('searchlab', mep + 'searchlab', 180, '检测室', 70, tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE))
    ];
    
    var items1 = [
        '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
        '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
          id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
        ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
      ];
     
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'bustaskhtml', html: '' },
      {xtype:'hiddenfield',name:'bt.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'}
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
//          'bt.flowstatus': '84',
          'bt.labid': gpersist.GetUserInfo().dept.deptid,
          'bt.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'bt.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bt.testedname': tools.GetEncode(tools.GetValue(mep + 'searchtestedname')),
          'bt.labid': tools.GetEncode(tools.GetValue(mep + 'searchlab')),
          'bt.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'bt.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
        });
      });
    };
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    me.html = alms.ShowTaskQueryHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.taskid)) {
      tools.SetValue(mep + 'taskid',item.taskid);
    }
    
    me.OnShowTask(item);
    me.OnDetailRefresh();
    return true;
  },
  
  OnListSelect: function (view, record) {
    alms.PopupFileShow('任务单附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskFile.do?btf.taskid=') + tools.GetValue(mep + 'taskid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
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
