Ext.define('alms.mantaskallot', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '任务单二次分配',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustask',
      storeUrl: 'LabSearchBusTaskAllot.do',
      saveUrl: 'LabSaveBusTaskAllot.do',
      expUrl: 'LabSearchBusTaskAllot.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskallot',
      urlDetail: 'LabGetListBusTaskTester.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '单样品检测参数分配',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'taskid',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      detailTabs: 2
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
      '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnCommit', text: '分配', iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
      ' ', { id: mep + 'btnManyConsign', text: '同批次分配', iconCls: 'icon-add', handler: me.OnManyAllot,scope: me},
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
      { xtype: 'label', id: mep + 'bustaskhtml', html: '' },
      {xtype:'hiddenfield',name:'bt.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'bt.samplecode',id: mep + 'samplecode'},
      {xtype:'hiddenfield',name:'bt.samplename',id: mep + 'samplename'}
    ];
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = alms.ShowTaskHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(html);
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : '分配', iconCls : 'icon-save', handler : me.OnSave, scope : me },
      '-', ' ', { id : mep + 'btnFormEdit', text : '处理', iconCls : 'icon-deal', handler : me.OnFormEdit, scope : me },
//      '-', { id : mep + 'btnFormPrint', text : '样品二维码', iconCls : 'icon-print', handler : me.OnFormPrint, scope : me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = this.tranPrefix;
    var parameternames =[] ;
    var gridsize = me.plDetailGrid.store.getCount();
    var filesize = me.fileGrid.store.getCount();
    
    //多样品明细为空时，单样品要进行分配
    if(filesize == 0){
      if(gridsize == 0){
        tools.alert('请先进行分配！');
        return;
      }else{
        //判定是否所有的检测参数都已分配
        var count = me.plDetailGrid.store.getCount();
        for(var i=0;i<count;i++){
          parameternames =parameternames.concat( me.plDetailGrid.store.getAt(i).data.parameternames.split(','));
        }
        
        var task = tools.JsonGet('LabGetListBusTaskTest.do?btt.taskid='+tools.GetValue(mep+'taskid')).data;
        
        if(task.length > parameternames.length){
          tools.alert('还有检测参数未分配检测员，请继续分配！');
          return;
        }
      }
    }
    
    //单样品明细为空时，多样品要进行分配
    if(gridsize == 0){
      if(filesize == 0){
        tools.alert('请先进行分配！');
        return;
      }else{
        //判定是否所有的检测参数都已分配
        var filecount = me.fileGrid.store.getCount();
        for(var i=0;i<filecount;i++){
          parameternames = parameternames.concat( me.fileGrid.store.getAt(i).data.parametername.split(','));
        }
        
        var task = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid='+tools.GetValue(mep+'taskid')).data;
        
        if(task.length > parameternames.length){
          tools.alert('还有检测参数未分配检测员，请继续分配！');
          return;
        }
      }
    }
    
    //判断不能重复分配
    var flowstatus = tools.GetValue(mep+'flowstatus');

    if(flowstatus == '84'){
      tools.alert('该样品处于待接单状态，需接收任务单！');
      return false;
    }else if(flowstatus <'81' || flowstatus >'90'){
      tools.alert('该样品实验员已开始试验，不能修改分配任务！');
      return false;
    }
    
    return true;
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.winWidth=600,
    me.winHeight=120,
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('检测员', 'testuser', mep + 'testuser', tools.GetNullStore(), '96%', false)
          
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCheckCombo('检测参数', 'parameterids', mep + 'parameterids', tools.GetNullStore(), '100%', true)
        ]},
        {xtype:'hiddenfield',name:'testusername',id: mep + 'testusername'},
        { xtype: 'hiddenfield', name: 'attachurl', id: mep + 'attachurl' },
        { xtype: 'hiddenfield', name: 'attachname', id: mep + 'attachname' }
      ]}
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['testuser','parameterids', 'testusername'];
    Ext.getCmp(mep + 'testuser').on('select',function(){
      tools.SetValue(mep + 'testusername',Ext.getCmp(mep + 'testuser').getDisplayValue());
    });
  },
  
  OnListNew : function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnCreateDetailWin();
    if(me.winDetail){
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnAuthDetailEditForm(false);
    };
    
    me.OnInitComboData();
  },
  
  OnInitComboData:function(index){
    var me = this;
    var mep = me.tranPrefix;
    
    //过滤检测参数，分配完的不在显示
    var oldparameterids = '';
    var oldparamidarr = [];
    
    for(var j=0;j<me.plDetailGrid.store.getCount();j++){
      if(j != index){
        if(j == 0){
          oldparameterids = me.plDetailGrid.store.getAt(j).data.parameterids;
        }else{
          oldparameterids = oldparameterids +','+ me.plDetailGrid.store.getAt(j).data.parameterids;
        }
      }
    }
    
    oldparamidarr = oldparameterids.split(',');
    
    var testuserdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var parameteridsdata = tools.GetTypeList(null);
    var testuser = Ext.getCmp(mep + 'testuser');
    var param = Ext.getCmp(mep + 'parameterids');
    
    testuser.bindStore(tools.GetNullStore());
    
    var deptid = gpersist.UserInfo.dept.deptid;
    var testusers = tools.JsonGet('BasGetUserByDept.do?deptid=' + deptid);
    if (testusers && testusers.data){
      Ext.each(testusers.data, function (item, index) { 
        testuserdata.push({ id: item.userid, name: item.username });
      });
    }
    
    testuser.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: testuserdata}))
    tools.SetValue(mep + 'testuser',gpersist.SELECT_MUST_VALUE);
    
    if (param) {
      param.bindStore(tools.GetNullStore());
      var items = tools.JsonGet('LabGetListBusTaskTest.do?btt.taskid=' + tools.GetValue(mep + 'taskid'));
      if (items && items.data){
        Ext.each(items.data, function (item, index) {
          
          //me.detailEditType=1新增的情况
          if(me.detailEditType == 1){
            for(var k=0;k<oldparamidarr.length;k++){
              if(item.parameterid == oldparamidarr[k]){
                return true;
              }
            }
          }
          parameteridsdata.push({ id: item.parameterid, name: item.parametername });
        });
      }
      
      param.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: parameteridsdata}))
      tools.SetValue(mep + 'parameterids',gpersist.SELECT_MUST_VALUE);
    }
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '70%';
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        if(me.OnBeforeListSave(record)){
          me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
          me.winDetail.hide();
        }
          
      } else {
        if(me.OnBeforeListSave(me.detailRecord)){
          me.plDetailGrid.getView().refresh();
          me.winDetail.hide();
        }
      };
    };
  },

  OnListDelete:function(){
    var me = this;
    var j = me.plDetailGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    };
    
    me.plDetailGrid.getView().refresh();
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
//          'bt.flowstatus': '82',
          'bt.labid': gpersist.GetUserInfo().dept.deptid,
          'bt.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'bt.taskid': tools.GetEncode(tools.GetValue(mep + 'searchtaskid'))
        });
      });
    };
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskTester.do?bttr.taskid=') + tools.GetValue(mep + 'taskid');
      me.plDetailGrid.store.load();
    };
    
    if (me.fileGrid && me.fileGrid.store) {
      me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusAccFile.do?baf.tranid=') + tools.GetValue(mep + 'taskid');
      me.fileGrid.store.load();
    }
  },
  
  OnLoadDetailData: function (record,index) {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitComboData(index);
    tools.SetValue(mep+'testuser',record.testuser);
    tools.SetValue(mep+'testusername',record.testusername);
    tools.SetValue(mep+'parameterids',record.parameterids.split(','));
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep + 'samplename', record.samplename);
    
    if (record && !Ext.isEmpty(record.taskid)) {
      var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+record.taskid);
      tools.SetValue(mep + 'taskid', record.taskid);
      tools.SetValue(mep + 'samplecode', bustask.samplecode);
      tools.SetValue(mep + 'flowstatus', bustask.flowstatus);

      me.OnShowTask(record);
      me.OnDetailRefresh();
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this;
    
    me.detailRecord = record;

    me.index = index;
    
    if (!me.winDetail)
      me.OnCreateDetailWin();
      
    if(me.winDetail && record) {      
      me.winDetail.show();
      
      me.detailEditType = 2;
      me.OnLoadDetailData(record.data,index);  
      
      me.OnAuthDetailEditForm(true);
    }
  },

  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;

    record.data.testuser = tools.GetValue(mep+'testuser');
    record.data.testusername = tools.GetValue(mep+'testusername');
    record.data.parameterids = tools.GetValue(mep+'parameterids');
    record.data.parameternames = Ext.getCmp(mep+'parameterids').getDisplayValue();
    
    if(record.data.testuser=='-2'||record.data.parameterids==''){
      tools.alert("请选择需要分配检测员或者检测参数！");
      return false;
    }
    
    var newtestuser = record.data.testuser;
    var parameternames = record.data.parameternames.split(',');
    for(var i=0;i<parameternames.length;i++){
      var newparametername = parameternames[i];
      var store = me.plDetailGrid.store;

      for(var j=0;j<me.plDetailGrid.store.getCount();j++){
        var oldparameternames = store.getAt(j).data.parameternames.split(',');
        var testusername = store.getAt(j).data.testusername;
        var oldtestuser = store.getAt(j).data.testuser;
        
        if(oldtestuser != newtestuser){
          for(var k=0;k<oldparameternames.length;k++){
            //.replace(/^\s+|\s+$/g,"")去字符串中空格
            if(newparametername.replace(/^\s+|\s+$/g,"") == oldparameternames[k].replace(/^\s+|\s+$/g,"") ){
              tools.alert('"'+newparametername+'"检测参数已分配给'+testusername+'，请修改！');
              return false;
            }
          }
        }
        
        if (me.detailEditType == 1) {
          if(oldtestuser == newtestuser){
            tools.alert('"'+testusername+'"已分配检测参数,不能重复分配,请修改...');
            return false;
          }
        }
        
        //分配检测人员修改
        if (me.detailEditType == 2) {
          //me.index=需要修改明细的序号
          if(j != me.index){
            if(oldtestuser == newtestuser){
              tools.alert('"'+testusername+'"已分配检测参数,不能重复分配,请修改...');
              return false;
            }
          }
        }
      }
    }
    
    return true;
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
       tools.SetValue(mep + 'tranid', action.result.data.tranid);
       tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
    };
    me.OnDetailRefresh();
    
    tools.BtnsDisable(['btnSaveAllot'],mep);
  },
  
  OnAfterCreateDetail: function () {
    var me = this, mep = this.tranPrefix;
    
    var fileColumn = [];
    var fileField = [];    

    tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_busaccfile'), fileColumn, fileField, mep + '_accfile_');

    me.fileStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusAccFile.do'), fileField);
    
    me.fileGrid = Ext.create('Ext.grid.Panel', {
      region : 'center',
      title : '多样品检测参数分配',
      autoScroll : true,
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : fileColumn,
      store : me.fileStore,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
      }    
    });
    me.fileStore.load();
    me.plDetail.add(me.fileGrid);
    me.fileitemstar = [
      ' ', { id : mep + 'btnFileAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewFile, scope : me },
      ' ', { id : mep + 'btnFileDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteFile, scope : me }
    ];
    
    me.OnAfterCreateDetailToolBar();
    
    tools.SetToolbar(me.fileitemstar, mep);
      
    var tbfile = Ext.create('Ext.toolbar.Toolbar', {
      dock : 'top',
      items : me.fileitemstar
    });
    me.fileGrid.insertDocked(0, tbfile);
  },
  
  OnBeforeCreateDetailEditFile: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControlsFile = [
     { xtype: 'container', anchor: '100%', layout: 'column', items: [
      { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
        tools.FormCombo('检测员', 'testuserfile', mep + 'testuserfile', tools.GetNullStore(), '96%', false, null)
      ]},
      { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
        tools.FormCheckCombo('检测参数', 'parameterid', mep + 'parameterid', tools.GetNullStore(), '100%', true),
        { xtype: 'hiddenfield', name: 'fileurl', id: mep + 'fileurl' },
        { xtype: 'hiddenfield', name: 'filename', id: mep + 'filename' },
        { xtype: 'hiddenfield', name: 'testuserfilename', id: mep + 'testuserfilename' }
      ]}
    ]},
    tools.FormTextArea('备注', 'remark', mep + 'remark', 200, '100%', true, null, 2)
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['fileurl','filename','testuserfile','testuserfilename'];
    
    tools.SetValue(mep+'remark','同一个检测室只能上传一个文件，如果有多个文件可以先生成压缩包，再进行上传！');
  },
  
  OnCreateDetailWinFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwinfile')) {
      Ext.getCmp(mep + 'detailwinfile').destroy();
    };
    
    var itemsfile = [
      ' ', { id: mep + 'btnDetailSaveFile', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveFile, scope: me }
//      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
    ];
    
    me.OnBeforeCreateDetailEditFile();
    
    me.plDetailEditFile = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetaileditfile',
      region : 'north',
//      height : '18%',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: itemsfile}),
      items: me.editDetailControlsFile    
    });
    
    var upload = tools.SwfUpload();
    me.plFileDetailGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'filedetailgrid',
      region : 'center',
      columnWidth:1,
      scope: me,
      items: [upload]    
    });
    
    upload.on('showdetail',me.OnShowDetailFile,me);
    upload.on('closewin',me.OnCloseWinFile,me);
    
    me.winDetailFile = Ext.create('Ext.Window', {
      id: mep + 'detailwinfile',
      title: '检测参数分配',
      width: 600,
      height: 370,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDetailEditFile,me.plFileDetailGrid]
    });
  },
  
  OnListNewFile : function(){
    var me = this;
    me.OnCreateDetailWinFile();
    if(me.winDetailFile){
      me.detailEditType = 1;
      me.winDetailFile.show();
    };
    
    me.OnInitComboDataFile();
  },
  
  OnCloseWinFile:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.winDetailFile.hide();
  },
  
  OnShowDetailFile:function(render, item){
    var me = this;
    var mep = this.tranPrefix;
    
    var filename = Ext.getCmp(mep+'filename').getValue();
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    
    if(item){
      if(filename == ""){
        filename = item.name;
      }else{
        filename = filename+','+item.name
      };
      if(fileurl == ""){
        fileurl = item.url;
      }else{
        fileurl = fileurl+','+item.url;
      };
      tools.SetValue(mep + 'filename',filename);
      tools.SetValue(mep + 'fileurl',fileurl);
    };
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        if(me.OnBeforeListSave(record)){
          me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
          me.winDetail.hide();
        }
          
      } else {
        if(me.OnBeforeListSave(me.detailRecord)){
          me.plDetailGrid.getView().refresh();
          me.winDetail.hide();
        }
      };
    };
  },
  
  OnListSaveFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    var testuserfile = Ext.getCmp(mep + 'testuserfile').getValue();
    var testuserfilename = Ext.getCmp(mep + 'testuserfile').getDisplayValue();
    var record = me.fileGrid.store.model.create({});
    if (tools.InvalidForm(me.plDetailEditFile.getForm())) {
      if (me.detailEditType == 1) {
//        var record = me.fileGrid.store.model.create({});
        if(me.OnBeforeListSaveFile(record)){
          me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);
          me.winDetailFile.hide();
        }
      }
      else {
        me.OnBeforeListSaveFile(me.detailRecordFile);
        me.fileGrid.getView().refresh();
        me.winDetailFile.hide();
      };
      
    };
  },
  
  OnBeforeListSaveFile : function(record) {
    var me = this;
    var mep = me.tranPrefix;

    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    var testuserfile = Ext.getCmp(mep + 'testuserfile').getValue();
    var testuserfilename = Ext.getCmp(mep + 'testuserfile').getDisplayValue();
    
  //可能有多个附件的情况
    var filenames = filename.split(",");
    var fileurls = fileurl.split(",");
    for(i = 0; i <1; i++){
      record.data.filename = filenames[i];
      record.data.testuserfile = testuserfile;
      record.data.testuserfilename = testuserfilename;
      record.data.fileurl = fileurls[i];
      record.data.parameterid = tools.GetValue(mep+'parameterid');
      record.data.parametername = Ext.getCmp(mep+'parameterid').getDisplayValue();
    };
    
    if(record.data.testuserfile=='-2'||record.data.parameterid==''){
      tools.alert("请选择需要分配检测员或者检测参数！");
      return false;
    }
    
    var newtestuser = record.data.testuserfile;
    var parameternames = record.data.parametername.split(',');
    for(var i=0;i<parameternames.length;i++){
      var newparametername = parameternames[i];
      var store = me.fileGrid.store;

      for(var j=0;j<me.fileGrid.store.getCount();j++){
        var oldparameternames = store.getAt(j).data.parametername.split(',');
        var testuserfilename = store.getAt(j).data.testuserfilename;
        var oldtestuser = store.getAt(j).data.testuserfile;
        
        if(oldtestuser != newtestuser){
          for(var k=0;k<oldparameternames.length;k++){
            //.replace(/^\s+|\s+$/g,"")去字符串中空格
            if(newparametername.replace(/^\s+|\s+$/g,"") == oldparameternames[k].replace(/^\s+|\s+$/g,"") ){
              tools.alert('"'+newparametername+'"检测参数已分配给'+testuserfilename+'，请修改！');
              return false;
            }
          }
        }
        
        if (me.detailEditType == 1) {
          if(oldtestuser == newtestuser){
            tools.alert('"'+testuserfilename+'"已分配检测参数,不能重复分配,请修改...');
            return false;
          }
        }
        
        //分配检测人员修改
        if (me.detailEditType == 2) {
          //me.index=需要修改明细的序号
          if(j != me.index){
            if(oldtestuser == newtestuser){
              tools.alert('"'+testuserfilename+'"已分配检测参数,不能重复分配,请修改...');
              return false;
            }
          }
        }
      }
    }
    
    return true;
  },
  
  OnListDeleteFile:function(){
    var me = this;
    var j = me.fileGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
    };
    
    me.fileGrid.getView().refresh();
  },
  
  OnListSelectFile: function (e, record, item, index) {
    var me = this;
    
    if(record.data.fileurl == ''||record.data.fileurl == null){
      me.detailRecordFile = record;

      me.index = index;
      
      if (!me.winDetailFile)
        me.OnCreateDetailWinFile();
        
      if(me.winDetailFile && record) {      
        me.winDetailFile.show();
        
        me.detailEditType = 2;
        me.OnLoadDetailDataFile(record.data,index);  
        
//        me.OnAuthDetailEditForm(true);
      }
    }else{
      alms.PopupFileShow('任务单附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
    }
  },
  
  OnLoadDetailDataFile: function (record,index) {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitComboDataFile(index);
    tools.SetValue(mep+'testuserfile',record.testuserfile);
    tools.SetValue(mep+'testuserfilename',record.testuserfilename);
    tools.SetValue(mep+'parameterid',record.parameterid.split(','));
  },
  
  OnInitComboDataFile:function(index){
    var me = this;
    var mep = me.tranPrefix;
    
    //过滤检测参数，分配完的不在显示
    var oldparameterids = '';
    var oldparamidarr = [];
    
    for(var j=0;j<me.fileGrid.store.getCount();j++){
      if(j != index){
        if(j == 0){
          oldparameterids = me.fileGrid.store.getAt(j).data.parameterid;
        }else{
          oldparameterids = oldparameterids +','+ me.fileGrid.store.getAt(j).data.parameterid;
        }
      }
      
    }
    
    oldparamidarr = oldparameterids.split(',');
    
    var testuserdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var parameteridsdata = tools.GetTypeList(null);
    var testuserfile = Ext.getCmp(mep + 'testuserfile');
    var param = Ext.getCmp(mep + 'parameterid');
    
    
    testuserfile.bindStore(tools.GetNullStore());
    var deptid = gpersist.UserInfo.dept.deptid;
    var testusers = tools.JsonGet('BasGetUserByDept.do?deptid=' + deptid);
    if (testusers && testusers.data){
      Ext.each(testusers.data, function (item, index) { 
        testuserdata.push({ id: item.userid, name: item.username });
      });
    }
    testuserfile.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: testuserdata}))
    tools.SetValue(mep + 'testuserfile',gpersist.SELECT_MUST_VALUE);
    
    if (param) {
      param.bindStore(tools.GetNullStore());
      var items = tools.JsonGet('LabGetListBusTaskTestForMore.do?btt.taskid=' + tools.GetValue(mep + 'taskid'));
      
      if (items && items.data){
        Ext.each(items.data, function (item, index) {
        //me.detailEditType=1新增的情况
//          if(me.detailEditType == 1){
            for(var k=0;k<oldparamidarr.length;k++){
              if(item.parameterid == oldparamidarr[k]){
                return true;
              }
            }
//          }
          
          parameteridsdata.push({ id: item.parameterid, name: item.parametername });
        });
      }
      param.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: parameteridsdata}))
      tools.SetValue(mep + 'parameterid',gpersist.SELECT_MUST_VALUE);
    }
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.hasDetail) {
      var details = [];
      for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
        details.push(me.plDetailGrid.store.getAt(i).data);
      }
      
      var filedetails = [];
      for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
        filedetails.push( me.fileGrid.store.getAt(i).data);
      }
      
      me.saveParams = { details: Ext.encode(details),filedetails: Ext.encode(filedetails)};
    }
  },
  
  
  OnManyAllot:function(){
	  var me = this;
	  var mep = me.tranPrefix;
	  
	  var datas = me.plGrid.getView().getSelectionModel().getSelection();
	  
	  if (datas.length < 1) {
          tools.alert('请选择一个任务批次........');
          return false;

      } else if (datas.length > 1) {
          tools.alert('只能选择一个任务批次........');
          return false;
      } else {
    	  console.log(me.plGrid.selModel.selected.items[0].data)
    	  
    	  var accsampleid = me.plGrid.selModel.selected.items[0].data.accsampleid;
    	  var flowstatus = me.plGrid.selModel.selected.items[0].data.flowstatus;
    	  var taskid = me.plGrid.selModel.selected.items[0].data.taskid;
    	  var samplecode = me.plGrid.selModel.selected.items[0].data.samplecode;
    	  var samplename = me.plGrid.selModel.selected.items[0].data.samplename;
    	  
    	  if(flowstatus=='82'){
    		  tools.alert('请选择一个分配过的任务批次........');
              return false;
    	  }else{
          var param = tools.JsonGet('LabManAllot.do?bt.accsampleid=' + accsampleid + '&bt.flowstatus=' +
         flowstatus+ '&bt.taskid=' +taskid+ '&bt.samplecode=' +samplecode+ '&bt.samplename=' + samplename);

         tools.alert(param.msg);
    	  }
      }
	  
	 
  },
  
  
  
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete','btnFileAdd','btnFileDelete','btnFileAdd','btnFileDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnFileAdd','btnFileDelete','btnFileAdd','btnFileDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnAuthDetailEditForm : function(islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (islayout)
      me.plDetailEdit.suspendLayouts();
    
    switch (me.dataDeal) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disDetailControls, me.enDetailControls, mep);        
        tools.BtnsDisable(['btnDetailSave','btnDetailSaveFile'], mep);     
        break;
      
      default:
        tools.FormInit(me.disDetailControls, me.enDetailControls, mep);
        tools.BtnsEnable(['btnDetailSave','btnDetailSaveFile'], mep);
        break;
    }
    
    if (islayout) {
      me.plDetailEdit.resumeLayouts(true);
      me.plDetailEdit.doLayout();
    }
  }
  
});
