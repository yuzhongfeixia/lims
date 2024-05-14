Ext.define('alms.devcalib', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备检定与校准',
      winWidth: 750,
      winHeight: 550,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcalib',
      storeUrl: 'DevSearchDevCalib.do',
      saveUrl: 'DevSaveDevCalib.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevCalib.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasSubmit: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var devitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevforcalibid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevforcalibname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'decb.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '设备编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcalibplan',
      storeUrl: 'DevSearchDevCalibPlanForCalib.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'decb.tranid', mep + 'tranid', 20, '96%', false, 1,'',90),
          tools.FormText('管理员姓名', 'decb.devmanagername', mep + 'devmanagername', 10, '96%', false, 4,'',90),
          tools.FormDate('检定时间', 'decb.calibuserdate', mep + 'calibuserdate', 'Y-m-d', '96%', false, 7,nowdate,90),
          tools.FormText('检定周期(月)', 'decb.devperiod', mep + 'devperiod', 50, '96%', false, 4,'',90),
          tools.FormText('设备数量', 'decb.devcount', mep + 'devcount', 10, '96%', false, 5,'',90)
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//          tools.FormText('设备编号', 'decb.devid', mep + 'devid', 30, '100%', false, 1),
          devid,
          tools.FormCombo('检定单位', 'decb.calibunit', mep + 'calibunit', tools.ComboStore('CalibUnit'), '96%', false, 11,90),
          tools.FormDate('上次检定', 'decb.lastdate', mep + 'lastdate', 'Y-m-d', '96%', false, 9,Ext.Date.add(nowdate, Ext.Date.MONTH, -1),90),
          tools.FormText('规格型号', 'decb.specimodel', mep + 'specimodel', 100, '96%', false, 4,'',90),
          tools.FormText('测量范围', 'decb.measurerange', mep + 'measurerange', 100, '96%', false, 4,'',90)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('设备管理员', 'decb.devmanager', mep + 'devmanager', 20, '100%', false, 3),
          tools.FormText('检定人', 'decb.calibusername', mep + 'calibusername', 10, '100%', false, 6),
          tools.FormDate('下次检定', 'decb.nextdate', mep + 'nextdate', 'Y-m-d', '100%', false, 10,Ext.Date.add(nowdate, Ext.Date.MONTH, 1)),
          tools.FormText('出厂编号', 'decb.factorycode', mep + 'factorycode', 40, '100%', true, 4),
          {xtype:'hiddenfield',name:'decb.calibuser',id: mep + 'calibuser'},
          {xtype:'hiddenfield',name:'decb.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      {xtype:'hiddenfield',name:'calibplanid',id: mep + 'calibplanid'},
      tools.FormTextArea('准确等级/最大允许误差/不确定度', 'decb.problemdesc', mep + 'problemdesc', 200, '100%', true, 12, 4,90),
      tools.FormTextArea('校准结果', 'decb.calibresult', mep + 'calibresult', 400, '100%', true, 13, 4,90),
//      tools.FormTextArea('检查结果', 'decb.calibresult', mep + 'calibresult', 200, '100%', true, 14, 4,90),
//      tools.FormTextArea('校准要求', 'decb.calibrequire', mep + 'calibrequire', 100, '100%', true,15, 4,90),
      tools.FormTextArea('备注', 'decb.remark', mep + 'remark', 200, '100%', true, 16, 4,90)
      
    ];
    me.disNews = ['tranid', 'devmanager', 'devmanagername','calibresult'];
    me.disEdits = ['tranid', 'devmanager', 'devmanagername','calibresult'];
    me.enNews = [ 'devid', 'calibdate', 'calibunit', 'calibuserdate', 'problemdesc', 'calibcontent',  'calibrequire', 'lastdate', 
                  'nextdate', 'devperiod', 'remark','calibusername','devcount','measurerange','factorycode','specimodel'];
    me.enEdits = ['devid', 'calibdate', 'calibunit', 'calibuserdate', 'problemdesc', 'calibcontent',  'calibrequire', 'lastdate', 
                  'nextdate', 'devperiod', 'remark','calibusername','devcount','measurerange','factorycode','specimodel'];
    
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
    tools.SetValue(mep+'calibresult','校准结果由检测室填写');
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
//      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
//      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
          'decb.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid'))
//          'decb.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'calibdate', item.calibdate);
      tools.SetValue(mep + 'calibunit', item.calibunit);
      tools.SetValue(mep + 'calibusername', item.calibusername);
      tools.SetValue(mep + 'calibuserdate', item.calibuserdate);
      tools.SetValue(mep + 'problemdesc', item.problemdesc);
      tools.SetValue(mep + 'calibcontent', item.calibcontent);
      tools.SetValue(mep + 'calibresult', item.calibresult);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'calibrequire', item.calibrequire);
      tools.SetValue(mep + 'lastdate', item.lastdate);
      tools.SetValue(mep + 'nextdate', item.nextdate);
      tools.SetValue(mep + 'devperiod', item.devperiod);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
      //alert(item.devmanager);
      tools.SetValue(mep + 'measurerange', item.measurerange);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'devcount', item.devcount);
      tools.SetValue(mep + 'specimodel', item.specimodel);
      
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
//    var items = tools.JsonGet(tools.GetUrl('DevGetAcceptManage.do?am.devid=') +item.devid);
    if (item && item.devid) {
      tools.SetValue(mep+'devid',item.devid);
      tools.SetValue(mep+'devmanager',item.devmanager);
      tools.SetValue(mep+'devmanagername',item.devmanagername);
      tools.SetValue(mep+'calibplanid',item.tranid);
      console.log(item)
      tools.SetValue(mep+'lastdate',item.lastdate);
//      tools.SetValue(mep+'nextdate',item.nextdate);
      tools.SetValue(mep + 'nextdate',Ext.Date.add(item.lastdate, Ext.Date.MONTH, 12));
      tools.SetValue(mep+'factorycode',item.factorycode);
    }
  },
  
  OnCalibSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
     tools.SetValue(mep+"calibunit",item.deptname);
     tools.SetValue(mep+"calibusername",item.username);
     tools.SetValue(mep+"calibuser",item.userid);
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
          'decp.devname': tools.GetValueEncode(mep + 'searchdevforcalibname')
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
  
  //获取期间核查计划编号
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    Ext.apply(me.saveParams, { 
      'calibplanid': Ext.getCmp(mep + 'calibplanid').getValue()
    });
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