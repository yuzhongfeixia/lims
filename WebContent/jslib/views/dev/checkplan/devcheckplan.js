Ext.define('alms.devcheckplan', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备期间核查计划',
      winWidth: 750,
      winHeight: 350,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcheckplan',
      storeUrl: 'DevSearchDevCheckPlan.do',
      saveUrl: 'DevSaveDevCheckPlan.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevCheckPlan.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevforcheckplan', id: mep + 'searchdevforcheckplan', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'dcp.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '设备编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDevForCheckPlan.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    devid.on('griditemclick', me.OnDevSelect, me);
    
    //var deptname = tools.UserPicker(mep + 'deptname','dcp.deptname','核查单位',90);
    
    //deptname.on('griditemclick', me.OnCheckSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'dcp.tranid', mep + 'tranid', 20, '96%', false, 1,'',90),
          tools.FormText('设备型号', 'dcp.devstandard', mep + 'devstandard', 40, '96%', true, 4,'',90),
          tools.FormText('使用温度', 'dcp.usetemp', mep + 'usetemp', 10, '96%', true, 11,'',90),
          tools.FormDate('上次核查时间', 'dcp.lastcheckdate', mep + 'lastcheckdate', 'Y-m-d', '96%', false, 7,Ext.Date.add(nowdate, Ext.Date.MONTH, -1),90),
//          tools.FormText('检定单位', 'dcp.devmanager', mep + 'devmanager', 40, '96%', true, 4,'',90)
          tools.FormCombo('核查单位', 'dcp.deptname', mep + 'deptname', tools.ComboStore('CalibUnit'), '96%', false, 13,90)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          devid,
          tools.FormText('出厂编号', 'dcp.factorycode', mep + 'factorycode', 40, '96%', true, 5,'',90),
          tools.FormText('使用湿度', 'dcp.usehumid', mep + 'usehumid', 10, '96%', true, 12,'',90),
          tools.FormDate('下次核查时间', 'dcp.nextdate', mep + 'nextdate', 'Y-m-d', '96%', false, 9,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90),
          tools.FormText('核查人', 'dcp.devmanagername', mep + 'devmanagername', 40, '96%', true, 14,'',90)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('设备名称', 'dcp.devname', mep + 'devname', 20, '100%', false, 3,'',90),
          tools.FormText('生产厂商', 'dcp.factoryname', mep + 'factoryname', 20, '100%', true, 6,'',90),
          tools.FormDate('填写时间', 'dcp.devcheckdate', mep + 'devcheckdate', 'Y-m-d', '100%', false, 8,nowdate,90),
          tools.FormText('核查周期(月)', 'dcp.devperiod', mep + 'devperiod', 20, '100%', true, 6,'',90),
          {xtype:'hiddenfield',name:'dcp.devmanager',id: mep + 'devmanager'},
          {xtype:'hiddenfield',name:'dcp.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('设备用途', 'dcp.devusage', mep + 'devusage', 100, '99%', false, 13, 4,90),
      tools.FormTextArea('技术指标', 'dcp.devrange', mep + 'devrange', 100, '99%', false, 14, 4,90)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = [ 'devid','devperiod', 'lastcheckdate', 'devcheckdate', 'nextdate','devperiod', 'devmanager', 'devmanagername','deptname','devname', 'devstandard', 'factorycode', 'factoryname', 'devrange', 'devusage', 'usetemp', 'usehumid'];
    me.enEdits = ['devid','devperiod', 'lastcheckdate', 'devcheckdate', 'nextdate','devperiod', 'devmanager', 'devmanagername','deptname','devname', 'devstandard', 'factorycode', 'factoryname', 'devrange', 'devusage', 'usetemp', 'usehumid'];
    
    Ext.getCmp( mep + 'lastcheckdate').on('change', function(){
      var lastdatetime = tools.GetValue(mep + 'lastcheckdate').getTime();
      var nextdatetime = tools.GetValue(mep + 'nextdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次核查时间不能大于下次核查日期时间');
        Ext.getCmp( mep + 'lastcheckdate').reset();
      }
    });
    
    Ext.getCmp( mep + 'nextdate').on('change', function(){
      var nextdatetime = tools.GetValue(mep + 'nextdate').getTime();
      var lastdatetime = tools.GetValue(mep + 'lastcheckdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次核查时间不能大于下次核查日期时间');
        Ext.getCmp( mep + 'nextdate').reset();
      }
    });
    
    Ext.getCmp(mep+'lastcheckdate').on('change',function(){
      var lastmouth = tools.GetValue(mep+'lastcheckdate').getMonth();
      var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
      var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastcheckdate').getFullYear()*12+lastmouth);
      tools.SetValue(mep+'devperiod',intervalMonth);
    })
    
    Ext.getCmp(mep+'nextdate').on('change',function(){
      var lastmouth = tools.GetValue(mep+'lastcheckdate').getMonth();
      var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
      var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastcheckdate').getFullYear()*12+lastmouth);
      tools.SetValue(mep+'devperiod',intervalMonth);
    })
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    
    tools.SetValue(mep + 'devtype', gpersist.SELECT_MUST_VALUE);
    
    //计算检定周期
    var lastmouth = tools.GetValue(mep+'lastcheckdate').getMonth();
    var nextmouth = tools.GetValue(mep+'nextdate').getMonth();
    var intervalMonth = (tools.GetValue(mep+'nextdate').getFullYear()*12+nextmouth) - (tools.GetValue(mep+'lastcheckdate').getFullYear()*12+lastmouth);
    tools.SetValue(mep+'devperiod',intervalMonth);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    var nowdate =new Date();
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'核查开始时间',labelWidth:100,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:nowdate,selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield',fieldLabel:gpersist.STR_BTN_ENDDATE,labelWidth:60,width:160,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, 1),selectOnFocus:false,allowBlank:true},
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
          'dcp.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'dcp.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'dcp.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
        });
      });
    };
  },
  
  OnBeforeSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    return true;
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
    var record = tools.JsonGet(tools.GetUrl('DevGetDevCheckPlan.do?dcp.tranid=') +item.tranid);
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'devperiod', item.devperiod);
      tools.SetValue(mep + 'devusage', item.devusage);
      tools.SetValue(mep + 'lastcheckdate', item.lastcheckdate);
      tools.SetValue(mep + 'devcheckdate', item.devcheckdate);
      tools.SetValue(mep + 'nextdate', item.nextdate);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'usehumid', item.usehumid);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'deptname', record.deptname);
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
//    var items = tools.JsonGet(tools.GetUrl('DevGetAcceptManage.do?am.devid=') +item.devid);
    if (item && item.devid) {
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'devusage', item.devusage);
//      tools.SetValue(mep + 'devmanager', item.devmanager);
//      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'lastcheckdate', item.prevtime);
      tools.SetValue(mep + 'nextdate', item.nexttime);
      tools.SetValue(mep + 'usetemp', item.usetemp);
      tools.SetValue(mep + 'usehumid', item.usehumid);
    }
  },
  
  OnCheckSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+"devmanager",item.deptid);
     tools.SetValue(mep+"devmanagername",item.username);
     tools.SetValue(mep+"deptname",item.deptname);
//     tools.SetValue(mep+"calibuser",item.userid);
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
          'bd.devname': tools.GetValueEncode(mep + 'searchdevforcheckplan')
        });
      });
    };
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
