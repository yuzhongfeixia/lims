Ext.define('alms.buslabready', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '试验准备',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_buslab',
      storeUrl: 'LabSearchBusTaskBegin.do',
      saveUrl: 'LabSaveBusTaskBegin.do',
      expUrl: 'LabSearchBusTaskBegin.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustasktest',
      urlDetail: 'LabGetListBusTaskTesterBySingle.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '检测参数明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'sampletran',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      detailTabs: 1,
      hasDetailGridSelect: false
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '任务单编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtaskid', id: mep + 'searchtaskid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      '-', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnEdit', text: '处理', iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
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
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
    
    var getusername = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '检测人', name: 'btsg.getusername', id: mep + 'getusername', winTitle: '选择检测人',
      maxLength: 20, maxLengthText: '检测人不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '检测人为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_getusername',
      storeUrl: 'FlowSearchFlowRoleUser.do?fruser.deptid='+gpersist.GetUserInfo().dept.deptid,
      editable:false,
//      searchTools:roleitems,
      hasPage: true, pickerWidth: 370, pickerHeight: 400
    });
    
    getusername.on('griditemclick', me.OnGetUsernameSelect, me);
    
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('任务单号', 'btsg.taskid', mep + 'taskid', 20, '96%', true,null,null,90),
          tools.FormText('样品状态', 'btsg.samplestatus', mep + 'samplestatus', 20, '96%', true,null,null,90),
          tools.FormText('承检室负责人', 'btsg.labusername', mep + 'labusername', 20, '96%', true,null,null,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品编号', 'btsg.samplecode', mep + 'samplecode', 500, '96%', true,null,null,90),
          tools.FormCombo('检验性质', 'bbtsgt.testprop', mep + 'testprop', tools.ComboStore('TestProp', gpersist.SELECT_MUST_VALUE), '96%', false, 2,90),
          getusername
//          tools.FormText('样品数量', 'btsg.getcount', mep + 'getcount', 20, '96%', true,null,null,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品名称', 'btsg.samplename', mep + 'samplename', 500, '96%', true,null,null,90),
          tools.FormDate('要求完成日期', 'btsg.reqdate', mep + 'reqdate', 'Y-m-d', '96%', true,1,nowdate,90),
          tools.FormDate('开始检测时间', 'btsg.begintestdate', mep + 'begintestdate', 'Y-m-d', '96%', true,1,nowdate,90)
//          tools.FormText('温度(℃)', 'btsg.testtemp', mep + 'testtemp', 20, '96%', true,null,null,90),
//          tools.FormDate('检测日期', 'btsg.getdate', mep + 'getdate', 'Y-m-d', '96%', true,1,nowdate,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('规格型号', 'btsg.samplestand', mep + 'samplestand', 100, '100%', true,null,null,90),
          tools.FormCombo('承检室', 'btsg.labid', mep + 'labid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '100%', false, 3,90),
//          tools.FormText('湿度(%)', 'btsg.testhum', mep + 'testhum', 20, '100%', true,null,null,90),
//          tools.FormCheckCombo('使用设备', 'btsg.devids', mep + 'devids', tools.GetNullStore(), '100%', true,null,90)
          tools.FormText('环境情况', 'btsg.testenv', mep + 'testenv', 50, '100%', true,null,null,90)
        ]}
      ]},
      tools.FormTextArea('检测依据', 'btsg.mainstandname', mep + 'mainstandname', 500, '100%', true, 8, 2 ,90),
      tools.FormTextArea('备注', 'btsg.taskremark', mep + 'taskremark', 200, '100%', true, 8, 2 ,90),
      {xtype:'hiddenfield',name:'btsg.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'btsg.sampleid',id: mep + 'sampleid'},
      {xtype:'hiddenfield',name:'btsg.sampletran',id: mep + 'sampletran'},
      {xtype:'hiddenfield',name:'btsg.getuser',id: mep + 'getuser'},
      {xtype:'hiddenfield',name:'btsg.libuser',id: mep + 'libuser'},
      {xtype:'hiddenfield',name:'btsg.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'btsg.teststandardname',id: mep + 'teststandardname'},
      {xtype:'hiddenfield',name:'btsg.devnames',id: mep + 'devnames'},
      {xtype:'hiddenfield',name:'auditstatus',id: mep + 'auditstatus'},
      {xtype:'hiddenfield',name:'btsg.deal.action',id: mep + 'datadeal'}
    ];
    
    me.disNews = ['taskid','samplename','samplestand','reqdate','labid','labusername','testprop','samplecode'];
    me.disEdits = ['taskid','samplename','samplestand','reqdate','labid','labusername','testprop','samplecode'];
    me.enNews = ['samplestatus','begintestdate','testtemp','testhum','testenv','devids','getusername','getcount','getdate','taskremark','mainstandname'];
    me.enEdits = ['samplestatus','begintestdate','testtemp','testhum','testenv','devids','getusername','getcount','getdate','taskremark','mainstandname'];
    
//    Ext.getCmp(mep + 'devids').on('select', me.OnDevSelect, me);
    
  },
  
  OnGetUsernameSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.userid){
       tools.SetValue(mep+"getusername",item.username);
       tools.SetValue(mep+"getuser",item.userid);
    }
  },
  
  OnDevSelect : function(){
    var me = this;
    var mep = me.tranPrefix;
    
    //设备名称和设备编号连在一起
    var devnames = Ext.getCmp(mep + 'devids').getDisplayValue().split(",");
    var devids = Ext.getCmp(mep + 'devids').getValue().split(",");
    var devinfos;
    if(devids){
      for(var i=0;i<devids.length;i++){
        if(i==0){
          devinfos = devnames[i]+':'+devids[i]
        }else{
          devinfos = devinfos +','+ devnames[i]+':'+devids[i]
        }
      }
    }

    tools.SetValue(mep + 'devnames',devinfos);
  },
  
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : '处理', iconCls : 'icon-deal', handler : me.OnFormEdit, scope : me },
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
//          'btsg.flowstatus': '81',
          'btsg.labid': gpersist.GetUserInfo().dept.deptid,
          'btsg.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'btsg.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'btsg.taskid': tools.GetEncode(tools.GetValue(mep + 'searchtaskid'))
        });
      });
    };
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskTesterBySingle.do?bttr.taskid=') + tools.GetValue(mep + 'taskid') + 
      '&bttr.tranid=' + tools.GetValue(mep + 'tranid') + '&bttr.testuser=' + gpersist.GetUserInfo().user.userid + '&bttr.sampletran=' + tools.GetValue(mep + 'sampletran');
      me.plDetailGrid.store.load();
    };
    
    if (me.fileGrid && me.fileGrid.store) {
      me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusAccFile.do?baf.sampletran=') + tools.GetValue(mep + 'sampletran');
      me.fileGrid.store.load();
    }
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusTaskSingleBySampleTran.do?btsg.sampletran='+record.sampletran);

    if (item && !Ext.isEmpty(item.taskid)) {
      tools.SetValue(mep + 'taskid', item.taskid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'samplecode', record.samplecode);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'samplestatus', item.samplestatus);
      tools.SetValue(mep + 'testprop', item.testprop);
      tools.SetValue(mep + 'samplestand', item.samplestand);
      tools.SetValue(mep + 'teststandardname', record.teststandardname);
      tools.SetValue(mep + 'mainstandname', record.teststandardname);
      tools.SetValue(mep + 'senduser', item.senduser);
      tools.SetValue(mep + 'sendusername', item.sendusername);
      tools.SetValue(mep + 'senddate', item.senddate);
      tools.SetValue(mep + 'reqdate', item.reqdate);
      tools.SetValue(mep + 'labid', item.labid);
      tools.SetValue(mep + 'labuser', item.labuser);
      tools.SetValue(mep + 'labusername', item.labusername);
      tools.SetValue(mep + 'getuser', item.getuser);
      tools.SetValue(mep + 'taskremark', item.taskremark);
      tools.SetValue(mep + 'backcount', item.backcount);
      tools.SetValue(mep + 'handdate', item.handdate);
      tools.SetValue(mep + 'acceptuser', item.acceptuser);
      tools.SetValue(mep + 'acceptusername', item.acceptusername);
      tools.SetValue(mep + 'acceptdate', item.acceptdate);
      tools.SetValue(mep + 'begintestdate', record.begintestdate == null ? new Date() : record.begintestdate);
      tools.SetValue(mep + 'endtestdate', record.endtestdate);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'devnames', item.devnames);
      tools.SetValue(mep + 'checkuser', item.checkuser);
      tools.SetValue(mep + 'checkusername', item.checkusername);
      tools.SetValue(mep + 'checkdate', item.checkdate);
      tools.SetValue(mep + 'aduituser', item.aduituser);
      tools.SetValue(mep + 'aduitusername', item.aduitusername);
      tools.SetValue(mep + 'aduitdate', item.aduitdate);
      tools.SetValue(mep + 'processuser', item.processuser);
      tools.SetValue(mep + 'processusername', item.processusername);
      tools.SetValue(mep + 'processdate', item.processdate);
      tools.SetValue(mep + 'sampletran', record.sampletran);
      tools.SetValue(mep + 'auditstatus', item.auditstatus);
      tools.SetValue(mep + 'getuser',gpersist.GetUserInfo().user.userid);
      tools.SetValue(mep + 'getusername',gpersist.GetUserInfo().user.username);
      
      me.OnGetDevs(item.labid);
      if(record.begintestdate != null){
        tools.SetValue(mep + 'testtemp', item.testtemp);
        tools.SetValue(mep + 'testhum', item.testhum);
        tools.SetValue(mep + 'testenv', item.testenv);
        tools.SetValue(mep + 'devids', item.devids.split(','));
//        tools.SetValue(mep + 'getusername', item.getusername);
        tools.SetValue(mep + 'getcount', item.getcount == 0 ? null : item.getcount);
        tools.SetValue(mep + 'getdate', item.getdate == null ? new Date() : item.getdate);
      }else{
        tools.SetValue(mep + 'testtemp', null);
        tools.SetValue(mep + 'testhum', null);
        tools.SetValue(mep + 'testenv', item.testenv==''?'符合检测要求': item.testenv);
        tools.SetValue(mep + 'devids', null);
//        tools.SetValue(mep + 'getusername', null);
        tools.SetValue(mep + 'getcount', null);
        tools.SetValue(mep + 'getdate', item.getdate == null ? new Date() : item.getdate);
      }
      me.OnDetailRefresh();
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.deitems = [];
  },
  
  OnGetDevs:function(labid){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(labid) || (labid == gpersist.SELECT_MUST_VALUE))
      return;
    
    var devs = Ext.getCmp(mep + 'devids');
    
    if (devs) {
      if (labid == gpersist.SELECT_MUST_VALUE) {
        devs.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetDevCombo(null, labid, mep + 'devids');
      }
    }
    
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.taskid) {
          tools.SetValue(mep + 'taskid', action.result.data.taskid);
        };
      };
    };
    me.OnDetailRefresh();
  },
  
  OnListSelect :function(){
  },
  
  OnCommit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnSubmit();
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var auditstatus = tools.GetValue(mep+'auditstatus');
    if(auditstatus == '92'||auditstatus == '94'||auditstatus == '98'){
      tools.alert('该样品生成的原始记录表正在审批中,不得重复试验....');
      return false;
    }
    return true;
  }
  
//  OnAfterCreateDetail: function () {
//    var me = this, mep = this.tranPrefix;
//    
//    var fileColumn = [];
//    var fileField = [];    
//
//    tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_busaccfile'), fileColumn, fileField, mep + '_accfile_');
//
//    me.fileStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusAccFile.do'), fileField);
//    
//    me.fileGrid = Ext.create('Ext.grid.Panel', {
//      region : 'center',
//      title : '多样品任务单附件',
//      autoScroll : true,
//      frame : false,
//      border : false,
//      margins : '0 0 0 0', 
//      padding : '0 0 0 0',
//      loadMask : true,
//      columnLines : true,
//      viewConfig : {
//        autoFill : true,
//        stripeRows : true
//      },
//      columns : fileColumn,
//      store : me.fileStore,
//      selModel: me.hasDetailGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }) : {},
//      listeners : {
//        'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
//      }    
//    });
//    me.fileStore.load();
//    me.plDetail.add(me.fileGrid);
//    me.fileitemstar = [];
//    
//    me.OnAfterCreateDetailToolBar();
//    
//    tools.SetToolbar(me.fileitemstar, mep);
//      
//    var tbfile = Ext.create('Ext.toolbar.Toolbar', {
//      dock : 'top',
//      items : me.fileitemstar
//    });
//    me.fileGrid.insertDocked(0, tbfile);
//  },
//  
//  OnListSelectFile: function (view, record) {
//    if(record.data.fileurl==''||record.data.fileurl == null){
//      tools.alert('该附件不存在...');
//    }else{
//      alms.PopupFileShow('任务单附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
//    }
//  }
  
});
