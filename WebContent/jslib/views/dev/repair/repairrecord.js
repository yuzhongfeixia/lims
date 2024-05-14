Ext.define('alms.repairrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备维修记录',
      winWidth: 750,
      winHeight: 500,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_repairrecord',
      storeUrl: 'DevSearchRepairRecord.do',
      saveUrl: 'DevSaveRepairRecord.do',
      hasGridSelect: true,
      expUrl: 'DevSearchRepairRecord.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevnameforrepairrecord', id: mep + 'searchdevnameforrepairrecord', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var repairid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '报修单编号', name: 'rere.repairid', id: mep + 'repairid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '报修单编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '报修单编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devrepairapply',
      storeUrl: 'DevSearchRepairApplyForRecord.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
   });
    repairid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'rere.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('设备名称', 'rere.devname', mep + 'devname', 40, '96%', false, 4),
          tools.FormCombo('所属室', 'rere.deptid', mep + 'deptid', tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE), '96%', false, 7)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('设备编号', 'rere.devid', mep + 'devid', 30, '96%', false, 3),
          tools.FormText('设备管理员', 'rere.manageruser', mep + 'manageruser', 20, '96%', false, 5),
          tools.FormText('主机号码', 'rere.hostnumber', mep + 'hostnumber', 20, '96%', false, 8,'isphone')
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//          tools.FormText('报修单编号', 'rere.repairid', mep + 'repairid', 20, '100%', false,4),
          repairid,
          tools.FormText('管理员姓名', 'rere.managerusername', mep + 'managerusername', 14, '100%', false, 6),
          tools.FormText('维修人', 'rere.repairman', mep + 'repairman', 20, '100%', false, 9),
          {xtype:'hiddenfield',name:'rere.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('仪器故障', 'rere.devdesc', mep + 'devdesc', 200, '100%', true, 10, 4),
      tools.FormTextArea('故障原因', 'rere.devreason', mep + 'devreason', 200, '100%', true, 11, 4),
      tools.FormTextArea('采取措施', 'rere.devrepair', mep + 'devrepair', 200, '100%', true, 12, 4),
      tools.FormTextArea('维修结果', 'rere.repairresult', mep + 'repairresult', 200, '100%', true, 13, 4)
    ];
    me.disNews = ['tranid','devname', 'devid', 'manageruser','managerusername','deptid'];
    me.disEdits = ['tranid','devname', 'devid', 'manageruser','managerusername','deptid'];
    me.enNews = ['repairid', 'hostnumber', 'devdesc', 'devreason', 'devrepair','repairman','repairresult'];
    me.enEdits = ['repairid','hostnumber', 'devdesc', 'devreason', 'devrepair','repairman','repairresult'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
          'rere.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'rere.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'manageruser', item.manageruser);
      tools.SetValue(mep + 'managerusername', item.managerusername);
      tools.SetValue(mep + 'deptid', item.deptid);
      tools.SetValue(mep + 'repairid', item.repairid);
      tools.SetValue(mep + 'hostnumber', item.hostnumber);
      tools.SetValue(mep + 'devdesc', item.devdesc);
      tools.SetValue(mep + 'devreason', item.devreason);
      tools.SetValue(mep + 'devrepair', item.devrepair);
      tools.SetValue(mep + 'repairman', item.repairman);
      tools.SetValue(mep + 'repairresult', item.repairresult);
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


    var items = tools.JsonGet(tools.GetUrl('DevGetBasDev.do?bd.devid=') +tools.GetEncode(tools.GetEncode(item.devid)));

    if (item && item.tranid) {
      tools.SetValue(mep+'repairid',item.tranid);
      tools.SetValue(mep+'devid',item.devid);
      tools.SetValue(mep+'devname',item.devname);
      tools.SetValue(mep+'deptid',items.labid);
      tools.SetValue(mep+'manageruser',items.devmanager);
      tools.SetValue(mep+'managerusername',items.devmanagername);
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
  
  OnRepairSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'repairman',item.username);
    }
  },
  
  OnDevSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var repair = Ext.getCmp(mep+'repairid');
    me.OnDevBeforeLoad();
    repair.store.loadPage(1);    
  },
    
  OnDevBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var repair = Ext.getCmp(mep+'repairid');
    if(repair.store) {      
      repair.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
//          'devplan.devid': tools.GetValueEncode(mep + 'searchdevforcalibid'),
          'ra.devname': tools.GetValueEncode(mep + 'searchdevnameforrepairrecord')
        });
      });
    };
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
