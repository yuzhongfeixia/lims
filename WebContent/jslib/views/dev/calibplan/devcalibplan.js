Ext.define('alms.devcalibplan', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备检定校准',
      winWidth: 750,
      winHeight: 350,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcalibplan',
      storeUrl: 'DevSearchDevCalibPlan.do',
      saveUrl: 'DevSaveDevCalibPlan.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevCalibPlan.do',
      hasPage: true,
      hasPrevNext: true,
      hasSubmit: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var devitems = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevforcalibplan', id: mep + 'searchdevforcalibplan', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'下次检定时间',labelWidth:100,width:200,name:'searchbegindateforcalibplan',id:mep + 'searchbegindateforcalibplan',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '-', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'searchenddateforcalibplan',id:mep + 'searchenddateforcalibplan',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.DAY, 5),selectOnFocus:false,allowBlank:true},
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'decp.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '设备编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDevForCalibplan.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 650, pickerHeight: 500
    });
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'decp.tranid', mep + 'tranid', 20, '96%', false, 1,'',90),
          tools.FormText('设备型号', 'decp.devstandard', mep + 'devstandard', 40, '96%', true, 4,'',90),
          tools.FormText('使用温度', 'decp.usetemp', mep + 'usetemp', 10, '96%', true, 7,'',90),
          tools.FormText('价格(万元)', 'decp.devprice', mep + 'devprice', 20, '96%', false, 10,'is15p2',90),
          tools.FormDate('上次检定时间', 'decp.lastdate', mep + 'lastdate', 'Y-m-d', '96%', true, 13,Ext.Date.add(nowdate, Ext.Date.MONTH, -1),90)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          devid,
          tools.FormText('出厂编号', 'decp.factorycode', mep + 'factorycode', 40, '96%', false, 5,'',90),
          tools.FormText('使用湿度', 'decp.usehumid', mep + 'usehumid', 10, '96%', true, 8,'',90),
//          tools.FormText('检定单位', 'decp.devmanager', mep + 'devmanager', 40, '96%', true, 11,'',90),
          tools.FormCombo('检定单位', 'decp.calibdept', mep + 'calibdept', tools.ComboStore('CalibUnit'), '96%', false, 11,90),
          tools.FormDate('下次检定时间', 'decp.nextdate', mep + 'nextdate', 'Y-m-d', '96%', false, 14,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('设备名称', 'decp.devname', mep + 'devname', 20, '100%', false, 3,'',90),
          tools.FormText('生产厂商', 'decp.factoryname', mep + 'factoryname', 20, '100%', false, 6,'',90),
          tools.FormDate('购买日期', 'decp.buydate', mep + 'buydate', 'Y-m-d', '100%', true, 9,nowdate,90),
          tools.FormText('检定人', 'decp.devmanagername', mep + 'devmanagername', 40, '100%', true, 12,'',90),
          tools.FormText('检定周期(月)', 'decp.devperiod', mep + 'devperiod', 40, '100%', true, 12,'',90),
          {xtype:'hiddenfield',name:'decp.devmanager',id: mep + 'devmanager'},
          {xtype:'hiddenfield',name:'decp.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('设备用途', 'decp.devusage', mep + 'devusage', 100, '99%', false, 16, 4,90),
      tools.FormTextArea('技术指标', 'decp.devrange', mep + 'devrange', 100, '99%', false, 17, 4,90)
    ];
    me.disNews = ['tranid'];  
    me.disEdits = ['tranid'];
    me.enNews = [ 'devid','devperiod',  'lastdate',  'nextdate', 'devperiod', 'devmanager', 'devmanagername','calibdept', 'devname', 
      'devstandard', 'factorycode', 'factoryname', 'devrange', 'devusage', 'buydate','usetemp', 'usehumid', 'devprice'];
    me.enEdits = ['devid','devperiod',  'lastdate', 'nextdate','devperiod', 'devmanager', 'devmanagername','calibdept', 'devname', 
      'devstandard', 'factorycode', 'factoryname', 'devrange', 'devusage', 'buydate','usetemp', 'usehumid', 'devprice'];
    
    Ext.getCmp(mep+'lastdate').on('change',function(){
      var lastmouth = tools.GetValue(mep+'lastdate').getMonth();
      var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
      var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastdate').getFullYear()*12+lastmouth);
      tools.SetValue(mep+'devperiod',intervalMonth);
    })
    
    Ext.getCmp(mep+'nextdate').on('change',function(){
      var lastmouth = tools.GetValue(mep+'lastdate').getMonth();
      var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
      var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastdate').getFullYear()*12+lastmouth);
      tools.SetValue(mep+'devperiod',intervalMonth);
    })
    
    Ext.getCmp( mep + 'lastdate').on('change', function(){
      var lastdatetime = tools.GetValue(mep + 'lastdate').getTime();
      var nextdatetime = tools.GetValue(mep + 'nextdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次检定时间不能大于下次检定日期时间');
        Ext.getCmp( mep + 'lastdate').reset();
      }
    });
    
    Ext.getCmp( mep + 'nextdate').on('change', function(){
      var nextdatetime = tools.GetValue(mep + 'nextdate').getTime();
      var lastdatetime = tools.GetValue(mep + 'lastdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次检定时间不能大于下次检定日期时间');
        Ext.getCmp( mep + 'nextdate').reset();
      }
    });
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    
    //计算检定周期
    var lastmouth = tools.GetValue(mep+'lastdate').getMonth();
    var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
    var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastdate').getFullYear()*12+lastmouth);
    tools.SetValue(mep+'devperiod',intervalMonth);
  },

  OnBeforeSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    return true;
  },

  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    var nowdate =new Date();
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'下次检定时间',labelWidth:100,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:nowdate,selectOnFocus: false, allowBlank: true},
      '-', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, 12),selectOnFocus:false,allowBlank:true},
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
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'decp.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'decp.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'decp.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
        });
      });
    };
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      height:'100%',
      autoScroll: true,
      region : 'north',
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
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.JsonGet(tools.GetUrl('DevGetDevCalibPlan.do?decp.tranid=') +item.tranid);
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'devperiod', item.devperiod);
      tools.SetValue(mep + 'lastdate', item.lastdate);
      tools.SetValue(mep + 'nextdate', item.nextdate);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'buydate', item.buydate);
      tools.SetValue(mep + 'devprice', item.devprice);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'usehumid', item.usehumid);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'calibdept', record.calibdept);
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
  
  OnDevSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.devid) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'buydate', item.buydate);
      tools.SetValue(mep + 'devprice', item.devprice);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'usehumid', item.usehumid);
      tools.SetValue(mep + 'lastdate',item.nexttime);
      tools.SetValue(mep + 'calibdept','01');
      
      tools.SetValue(mep + 'nextdate',Ext.Date.add(item.nexttime, Ext.Date.MONTH, 12));
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      
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
          'bd.devname': tools.GetValueEncode(mep + 'searchdevforcalibplan'),
          'bd.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindateforcalibplan'), 'Y-m-d'),
          'bd.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddateforcalibplan'), 'Y-m-d')
        });
      });
    };
  },
  
  OnCalibSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+"devmanager",item.deptid);
     tools.SetValue(mep+"devmanagername",item.username);
   }
  },
  
  //提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);
    if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnEdit'], mep);
    } else{
      tools.BtnsEnable(['btnEdit'], mep);
    }
  },
  
  //双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnSubmit'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmit'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  },
  
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
     '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
     '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
   ];
   
   if (me.hasSubmit) {
     Ext.Array.insert(me.editToolItems, 2, [
       ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
     ]);
   }
   
   if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
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