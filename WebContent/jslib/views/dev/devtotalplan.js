Ext.define('alms.devtotalplan', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备检定、核查、维护总体计划',
      winWidth: 1000,
      winHeight: 360,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devtotalplan',
      storeUrl: 'DevSearchDevPlan.do',
      saveUrl: 'DevSaveDevPlan.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevPlan.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'planid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var devitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + '', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevnamefortotal', id: mep + 'searchdevnamefortotal', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    var nowdate = new Date();
    
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'devplan.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDevForTotal.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
   });
    devid.on('griditemclick', me.OnDevSelect, me);
   
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('计划编号', 'devplan.planid', mep + 'planid', 20, '96%', false, 1,'',90),
          tools.FormCombo('溯源方式', 'devplan.devsource', mep + 'devsource', tools.ComboStore('DevSource', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),     
          tools.FormCombo('溯源机构', 'devplan.deptid', mep + 'deptid', tools.ComboStore('SourceDept', gpersist.SELECT_MUST_VALUE), '96%', false, 7,90),
          //tools.FormDate('核查开始时间', 'devplan.checkstart', mep + 'checkstart', 'Y-m-d H:i', '96%', false, 10,nowdate,90),
          tools.FormText('使用湿度', 'devplan.usagehumid', mep + 'usagehumid', 10, '96%', true, 13,'',90),
          //tools.FormDate('维护结束时间', 'devplan.maintenend', mep + 'maintenend', 'Y-m-d H:i', '96%', false, 16,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90),
          tools.FormCombo('检校周期', 'devplan.calibrationcycleid', mep + 'calibrationcycleid', tools.ComboStore('CalibrationCycle', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)  
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          devid,                                                               
         // tools.FormDate('溯源开始时间', 'devplan.sourcestart', mep + 'sourcestart', 'Y-m-d H:i', '96%', false, 5,nowdate,90),
          //原为计划确认
          tools.FormCombo('溯源确认', 'devplan.devconfirm', mep + 'devconfirm', tools.ComboStore('DevConfirm', gpersist.SELECT_MUST_VALUE), '96%', false, 8,90),
         // tools.FormDate('核查结束时间', 'devplan.checkend', mep + 'checkend', 'Y-m-d H:i', '96%', false, 11,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90),
//          tools.FormText('维护项目', 'devplan.maintenproject', mep + 'maintenproject', 20, '96%', false, 14,'',90),
          tools.FormDate('检定校准日期', 'devplan.devcheckdate', mep + 'devcheckdate', 'Y-m-d', '96%', false, 17,nowdate,90),
          tools.FormText('核查周期', 'devplan.checkperiod', mep + 'checkperiod', 10, '96%', true, 12,'',90)
       ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText('设备名称', 'devplan.devname', mep + 'devname', 40, '100%', false, 3,'',90),
          //tools.FormDate('溯源结束时间', 'devplan.sourceend', mep + 'sourceend', 'Y-m-d H:i', '100%', false, 6,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90),
          tools.FormCombo('期间核查方法', 'devplan.devmethod', mep + 'devmethod', tools.ComboStore('DevMethod', gpersist.SELECT_MUST_VALUE), '100%', false, 9,90),
          tools.FormText('使用温度', 'devplan.usagetemp', mep + 'usagetemp', 10, '100%', true, 12,'',90),
          //tools.FormDate('维护开始时间', 'devplan.maintenstart', mep + 'maintenstart', 'Y-m-d H:i', '100%', false, 15,nowdate,90),
          tools.FormText('维护周期', 'devplan.maintenperiod', mep + 'maintenperiod', 30, '100%', true, 12,'',90),
          {xtype:'hiddenfield',name:'devplan.devmanager',id: mep + 'devmanager'},
          {xtype:'hiddenfield',name:'devplan.devmanagername',id: mep + 'devmanagername'},
          {xtype:'hiddenfield',name:'devplan.maintenproject',id: mep + 'maintenproject'},
          {xtype:'hiddenfield',name:'devplan.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('设备用途', 'devplan.devusage', mep + 'devusage', 200, '100%', true, 16, 4,90),
      tools.FormTextArea('技术指标', 'devplan.devrange', mep + 'devrange', 200, '100%', true, 16, 4,90)
    ];
    me.disNews = ['planid','devname'];
    me.disEdits = ['planid','devname'];
    me.enNews = ['sourcestart','deptid','devmethod','checkend',
                 'maintenstart','devcheckdate','sourceend','devconfirm','checkstart','maintenproject','devid','devsource','maintenend','usagetemp','devusage','usagehumid','checkperiod','maintenperiod','calibrationcycleid','devrange'];
    me.enEdits = ['sourcestart','deptid','devmethod','checkend',
                  'maintenstart','devcheckdate','sourceend','devconfirm','checkstart','maintenproject','devid','devsource','maintenend','usagetemp','devusage','usagehumid','checkperiod','maintenperiod','calibrationcycleid','devrange'];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', {xtype:'textfield',fieldLabel:'设备名称',labelWidth:70,width:180,maxLength:20,name:'searchdevname',id:mep + 'searchdevname',allowBlank:true},           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
     tools.SetToolbar(items, mep);
    
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
     me.tbGrid.add(toolbar);
  },
  
  OnDevSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
      
    if (item && item.devid) {
      tools.SetValue(mep+'devid',item.devid);
      tools.SetValue(mep+'devname',item.devname);
      tools.SetValue(mep+'usagetemp',item.usetemp);
      tools.SetValue(mep+'usagehumid',item.usehumid);
      tools.SetValue(mep+'devusage',item.devusage);
      tools.SetValue(mep+'devrange',item.devrange);
      tools.SetValue(mep+'devmanager',item.devmanager);
      tools.SetValue(mep+'devmanagername',item.devmanagername);
    }
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
       
    me.callParent(arguments);
    tools.SetValue(mep + 'devconfirm', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'devmethod', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'devsource', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'deptid', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'maintenproject', "中心自我设备检测");
  },
  
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'planid', item.planid);
    tools.SetValue(mep + 'devid', item.devid);
    tools.SetValue(mep + 'devname', item.devname);
    tools.SetValue(mep + 'devusage', item.devusage);
    tools.SetValue(mep + 'devsource', item.devsource);
    tools.SetValue(mep + 'sourcestart', item.sourcestart);
    tools.SetValue(mep + 'sourceend', item.sourceend);
    tools.SetValue(mep + 'deptid', item.deptid);
    tools.SetValue(mep + 'devconfirm', item.devconfirm);
    tools.SetValue(mep + 'devmethod', item.devmethod);
    tools.SetValue(mep + 'checkstart', item.checkstart);
    tools.SetValue(mep + 'checkend', item.checkend);
    tools.SetValue(mep + 'usagetemp', item.usagetemp);
    tools.SetValue(mep + 'usagehumid', item.usagehumid);
    tools.SetValue(mep + 'maintenproject', item.maintenproject);
    tools.SetValue(mep + 'maintenstart', item.maintenstart);
    tools.SetValue(mep + 'maintenend', item.maintenend);
    tools.SetValue(mep + 'devcheckdate', item.devcheckdate);
    tools.SetValue(mep + 'calibrationcycleid', item.calibrationcycleid);
    tools.SetValue(mep + 'checkperiod', item.checkperiod);
    tools.SetValue(mep + 'maintenperiod', item.maintenperiod);
    tools.SetValue(mep + 'devrange', item.devrange);
    return true;

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
//          'bu.userid': tools.GetEncode(tools.GetValue(mep + 'searchuserid')),
          'devplan.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.planid) {
          tools.SetValue(mep + 'planid', action.result.data.planid);
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
          'bd.devname': tools.GetValueEncode(mep + 'searchdevnamefortotal')
        });
      });
    };
  }
  
});
  
  
  