Ext.define('alms.getnoticerecv', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '抽样通知单接单',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_getnotice',
      storeUrl: 'LabSearchBusGetNoticeForRecv.do',
      saveUrl: 'LabSaveBusGetNoticeRecv.do',
      expUrl: 'LabSearchBusGetNoticeForRecv.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnRev', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'getnoticehtml', html: '' },
      {xtype:'hiddenfield',name:'bgn.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'bgn.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bgn.backdesc',id: mep + 'backdesc'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-',  { id: mep + 'btnFormRecv', text: '接单', iconCls: 'icon-deal', handler: me.OnRecv, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
//      '-',  { id: mep + 'btnFormBack', text: '退单', iconCls: 'icon-deal', handler: me.OnBack, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bgn.flowstatus': '02',
          'bgn.testedname':tools.GetValueEncode(mep + 'searchtestedname'),
          'bgn.getuser':gpersist.UserInfo.user.userid
        });
      });
    };
  },
  
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
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
    me.OnAfterCreateEdit();
  },
  
  OnShowGetNotice:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = alms.ShowGetNoticeHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'getnoticehtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
    }
    
    me.OnShowGetNotice(item);
    me.OnAuthEditForm();
    return true;
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        };
      };
    };
    
    tools.BtnsDisable(['btnFormRecv','btnFormBack'],mep);
    
  },
  
  OnRecv:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+ 'flowstatus','03');
    me.OnSave();
  },
  
  OnBack:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnCreateDetailWinBack();
    if(me.winDetail){      
      me.winDetail.show();
    };
    tools.SetValue(mep+ 'flowstatus','86');
  },
  
  OnCreateDetailWinBack:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.winWidth = 500;
    me.winHeight = 180;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    };
    
    var items = [
      ' ', { id: mep + 'btnFormCommit', text: '提交', iconCls: 'icon-save', handler: me.OnCommit, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
    ];
    
    me.editDetailControls = [
      tools.FormTextArea('退单说明', 'recvbackdesc', mep + 'recvbackdesc', 200, '100%', false, 18,6)
    ];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['recvbackdesc'];
    me.enEdits = ['recvbackdesc'];
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: '退单',
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit]
    });
    },
  
  OnCommit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'backdesc',tools.GetValue(mep + 'recvbackdesc'));
    me.OnSave();
  },
  
  //提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);

    if(record.flowstatus == '02'){
      tools.BtnsEnable(['btnRev','btnFormRecv'], mep);
    } else{
      tools.BtnsDisable(['btnRev','btnFormRecv'], mep);
    }
  },
  
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
    if(record.flowstatus == '02'){
      tools.BtnsEnable(['btnRev','btnFormRecv'], mep);
    } else{
      tools.BtnsDisable(['btnRev','btnFormRecv'], mep);
    }
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
        
        if(me.plGrid.store.getAt(i - 1).data.flowstatus == '02'){
          tools.BtnsEnable(['btnFormRecv'],mep);
        }else{
          tools.BtnsDisable(['btnFormRecv'],mep);
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
        
        if(me.plGrid.store.getAt(i + 1).data.flowstatus == '02'){
          tools.BtnsEnable(['btnFormRecv'],mep);
        }else{
          tools.BtnsDisable(['btnFormRecv'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  }
  
});
