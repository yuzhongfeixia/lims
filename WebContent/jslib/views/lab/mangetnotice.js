Ext.define('alms.mangetnotice', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '取样通知单管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_getnotice',
      storeUrl: 'LabSearchBusGetNotice.do',
      saveUrl: 'LabSaveBusGetNotice.do',
      expUrl: 'LabSearchBusGetNotice.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_getnoticedetail',
      urlDetail: 'LabGetBusGetNoticeDetail.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '抽样通知单明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'tranid',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '抽样通知单号', labelWidth: 90, width: 210, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
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
    
//    var getuser = tools.UserPicker( mep + 'getusername','bgn.getusername','抽样人',90);
//    getuser.on('griditemclick', me.OnUserSelect, me);
    
    var testeditems = [
      ' ', { xtype: 'textfield', fieldLabel: '受检单位编号', labelWidth: 100, width: 200, maxLength: 40, name: 'searchtestedid', id: mep + 'searchtestedid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '受检单位姓名', labelWidth: 100, width: 200, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTestedSearch, scope: me }
    ];
   
    var tested = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '受检单位', name: 'bgn.testedname', id: mep + 'testedname', winTitle: '选择受检单位',
      maxLength: 20, maxLengthText: '受检单位不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '受检单位不能为空！', allowBlank: true, anchor: '100%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_testedunit',
      storeUrl: 'LabSearchBusTestedUnit.do',
      editable:true,
      searchTools:testeditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
   
    tested.on('griditemclick', me.OnTestedSelect, me);
                   
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('通知单编号', 'bgn.tranid', mep + 'tranid', 20, '96%', true,null,null,90),
          tools.FormCombo('检测类型', 'bgn.testtype', mep + 'testtype', tools.ComboStore('TestType', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormCheckCombo('抽样人', 'bgn.getuser', mep + 'getuser', tools.ComboStore('User', ''), '96%', false)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormCombo('抽样单类型', 'bgn.gettype', mep + 'gettype', tools.ComboStore('GetType', gpersist.SELECT_MUST_VALUE), '96%', false, 2,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tested
        ]}
      ]},
      tools.FormTextArea('抽样说明', 'bgn.tranremark', mep + 'tranremark', 200, '100%', true, 5, 2,90),
      {xtype:'hiddenfield',name:'bgn.testedid',id: mep + 'testedid'},
      {xtype:'hiddenfield',name:'bgn.getusername',id: mep + 'getusername'},
      {xtype:'hiddenfield',name:'bgn.getdept',id: mep + 'getdept'},
      {xtype:'hiddenfield',name:'bgn.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bgn.deal.action',id: mep + 'datadeal'}
    ];
      
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['getuser','testedname','gettype','tranremark','testtype','testedtele','testedpost','testedtype','testedscale','testedaddr'];
    me.enEdits = ['getuser','testedname','gettype','tranremark','testtype','testedtele','testedpost','testedtype','testedscale','testedaddr'];
    
    Ext.getCmp(mep+'getuser').on('select',function(){
      tools.SetValue(mep+'getusername',Ext.getCmp(mep+'getuser').getDisplayValue());
    })
  },
  
  OnTestedSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.testedid) {
      tools.SetValue(mep+'testedid',item.testedid);
      tools.SetValue(mep+'testedname',item.testedname);
      tools.SetValue(mep+'testedtele',item.testedtele);
      tools.SetValue(mep+'testedpost',item.testedpost);
      tools.SetValue(mep+'testedtype',item.testedtype);
      tools.SetValue(mep+'testedscale',item.testedscale);
      tools.SetValue(mep+'testedaddr',item.testedaddr);
    }
  },
  
  OnTestedSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    var tested = Ext.getCmp(mep+'testedname');
    if(tested.store){
      tested.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'btu.testedid': tools.GetValueEncode(mep + 'searchtestedid'),
          'btu.testedname': tools.GetValueEncode(mep + 'searchtestedname')
        });
      });
    }
    tested.store.loadPage(1);
  },
  
//  OnUserSelect:function(render,item){
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (item && !Ext.isEmpty(item.userid)) {
//      tools.SetValue(mep + 'getuser', item.userid);
//      tools.SetValue(mep + 'getusername', item.username);
//      tools.SetValue(mep + 'getdept', item.deptid);
//    };
//  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      '-',  { id: mep + 'btnCommit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.winWidth=600,
    me.winHeight=260,
    me.OnInitGridToolBar();
    me.editDetailControls = [
      tools.FormTextArea('抽样依据', 'getcontent', mep + 'getcontent', 50, '100%', true, 6, 3),
      tools.FormTextArea('抽样地点', 'getaddress', mep + 'getaddress', 50, '100%', true, 7, 2),
      tools.FormTextArea('备注', 'remark', mep + 'remark', 100, '100%', true, 8, 4)
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['getcontent', 'getaddress', 'remark'];  
  },
  
  OnListNew: function(){
    var me = this;
    me.OnCreateDetailWin();
    if(me.winDetail){
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnAuthDetailEditForm(false);
    };
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        record.data.getcontent = tools.GetValue(mep+'getcontent');
        record.data.getaddress = tools.GetValue(mep+'getaddress');
        record.data.remark = tools.GetValue(mep+'remark');
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      } else {
        me.OnBeforeListSave(me.detailRecord);
        me.plDetailGrid.getView().refresh();
      };
      me.winDetail.hide();
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
          'bgn.tranid': tools.GetValueEncode(mep + 'searchtranid'),
          'bgn.tranuser':gpersist.UserInfo.user.userid
        });
      });
    };
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if(tools.GetValue(mep + 'gettype') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择抽样单类型！');
      return false
    }
    
    if(tools.GetValue(mep + 'testtype') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择检测类型！');
      return false
    }
    
    if(me.plDetailGrid.store.getCount() == 0){
      tools.alert('请填写抽样通知单明细信息');
      return false;
    }
    return true;
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetBusGetNoticeDetail.do?bgnd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
      
    me.callParent(arguments);
    tools.SetValue(mep + 'gettype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'testtype', gpersist.SELECT_MUST_VALUE);
  },
  
  OnLoadDetailData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'getcontent',record.getcontent);
    tools.SetValue(mep+'getaddress',record.getaddress);
    tools.SetValue(mep+'remark',record.remark);
  },
   
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusGetNotice.do?bgn.tranid='+record.tranid);
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'gettype', item.gettype);
      tools.SetValue(mep + 'testtype', item.testtype);
      tools.SetValue(mep + 'trandate', item.trandate);
      tools.SetValue(mep + 'trandatecn', item.trandatecn);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'getuser', item.getuser.split(','));
      tools.SetValue(mep + 'getusername', item.getusername);
      tools.SetValue(mep + 'backdesc', item.backdesc);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'finishdate', item.finishdate);
      tools.SetValue(mep + 'finishdatecn', item.finishdatecn);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'tranremark', item.tranremark);
      tools.SetValue(mep + 'modifydate', item.modifydate);
      tools.SetValue(mep + 'modifyserial', item.modifyserial);
      me.OnDetailRefresh();
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.getcontent = tools.GetValue(mep+'getcontent');
    record.data.getaddress = tools.GetValue(mep+'getaddress');
    record.data.remark = tools.GetValue(mep+'remark');
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
          tools.SetValue(mep + 'testedid', action.result.data.testedid);
        };
      };
    };
    me.OnDetailRefresh();
  },
  
//提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);

    if(record.flowstatus == '01'){
      tools.BtnsEnable(['btnEdit'], mep);
    } else{
      tools.BtnsDisable(['btnEdit'], mep);
    }
  },
  
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus == '01'){
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnCommit'], mep);
    } else{
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnCommit'], mep);
    }
  },
  
//提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
  },
  
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == 0) {
          tools.alert('已经是当前列表第一条数据！');
          return;
        }
       
        me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
        
        if(me.plGrid.store.getAt(i - 1).data.flowstatus == '01'){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnFormEdit','btnCommit'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == j - 1) {
          tools.alert('已经是当前列表最后一条数据！');
          return;
        }
        
        me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
        
        if(me.plGrid.store.getAt(i + 1).data.flowstatus == '01'){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnFormEdit','btnCommit'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  },
  
//页面空间授权处理
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  }
  
});
