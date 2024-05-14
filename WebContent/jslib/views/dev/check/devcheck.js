Ext.define('alms.devcheck', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备期间核查',
      winWidth: 750,
      winHeight: 420,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcheck',
      storeUrl: 'DevSearchDevCheck.do',
      saveUrl: 'DevSaveDevCheck.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevCheck.do',
      hasPage: true,
      hasPrevNext: true,
      hasSubmit: true,
      idPrevNext: 'checkid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var devitems = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevnameforcalib', id: mep + 'searchdevnameforcalib', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'deck.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '设备编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devcheckplan',
      storeUrl: 'DevSearchDevCheckPlanForCheck.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [         
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('核查编号', 'deck.checkid', mep + 'checkid', 20, '96%', true, 1,'',90),
          tools.FormText('生产厂家', 'deck.factoryname', mep + 'factoryname', 20, '96%', false, 4,'',90),
          tools.FormDate('核查日期', 'deck.checkdate', mep + 'checkdate', 'Y-m-d', '96%', false, 8,nowdate,90)
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          devid,
          tools.FormText('核查精度', 'deck.devprecision', mep + 'devprecision', 20, '96%', false, 5,'',90),
          tools.FormDate('上次核查日期', 'deck.lastcheckdate', mep + 'lastcheckdate', 'Y-m-d', '96%', false, 7,Ext.Date.add(nowdate, Ext.Date.MONTH, -1),90),
          
          {xtype:'hiddenfield',name:'deck.deal.action',id: mep + 'datadeal'}
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('仪器名称', 'deck.devname', mep + 'devname', 40, '100%', false, 3,'',90),
          tools.FormText('测量范围', 'deck.devrange', mep + 'devrange', 40, '100%', false, 6,'',90),
          tools.FormDate('下次核查日期', 'deck.nextcheckdate', mep + 'nextcheckdate', 'Y-m-d', '100%', false, 7,Ext.Date.add(nowdate, Ext.Date.MONTH, 1),90),
          {xtype:'hiddenfield',name:'deck.deal.action',id: mep + 'datadeal'},
          {xtype:'hiddenfield',name:'deck.planid',id: mep + 'planid'}
        ]}
      ]},
      tools.FormTextArea('检查情况', 'deck.checkdesc', mep + 'checkdesc', 200, '100%', true, 9, 8,90),
      tools.FormTextArea('检查结论', 'deck.checkresult', mep + 'checkresult', 200, '100%', true, 9,8,90)
     
    ];
    me.disNews = ['checkid'];
    me.disEdits = ['checkid'];
    me.enNews = ['devid','lastcheckdate', 'devrange', 'devprecision', 'checkdate', 'checkdesc', 'checkresult', 'checkapprove',
                 'devname','factoryname','nextcheckdate' ];
    me.enEdits = ['devid','lastcheckdate', 'devrange', 'devprecision', 'checkdate', 'checkdesc', 'checkresult', 'checkapprove' ,
                  'devname','factoryname','nextcheckdate'];
                  
    Ext.getCmp( mep + 'lastcheckdate').on('change', function(){
      var lastdatetime = tools.GetValue(mep + 'lastcheckdate').getTime();
      var nextdatetime = tools.GetValue(mep + 'nextcheckdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次核查时间不能大于下次核查日期时间');
        Ext.getCmp( mep + 'lastcheckdate').reset();
      }
    });
    
    Ext.getCmp( mep + 'nextcheckdate').on('change', function(){
      var nextdatetime = tools.GetValue(mep + 'nextcheckdate').getTime();
      var lastdatetime = tools.GetValue(mep + 'lastcheckdate').getTime();
      if(lastdatetime > nextdatetime ){
        tools.alert('上次核查时间不能大于下次核查日期时间');
        Ext.getCmp( mep + 'nextcheckdate').reset();
      }
    });              
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
//    tools.SetValue(mep + 'devtype', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    var nowdate = new Date();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'下次核查日期',labelWidth:90,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '', {xtype:'datefield',fieldLabel:'结束日期',labelWidth:90,width:200,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, +1),selectOnFocus:false,allowBlank:true},
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
//      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
          'deck.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'deck.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname')),
          'deck.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'deck.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
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
    if(item && !Ext.isEmpty(item.checkid)){
      tools.SetValue(mep + 'checkid', item.checkid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'lastcheckdate', item.lastcheckdate);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'devprecision', item.devprecision);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'checkdate', item.checkdate);
      tools.SetValue(mep + 'checkdesc', item.checkdesc);
      tools.SetValue(mep + 'checkresult', item.checkresult);
      tools.SetValue(mep + 'checkapprove', item.checkapprove);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'managerdate', item.managerdate);
      tools.SetValue(mep + 'techuser', item.techuser);
      tools.SetValue(mep + 'techusername', item.techusername);
      tools.SetValue(mep + 'techdate', item.techdate);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'planid', item.planid);
      tools.SetValue(mep + 'nextcheckdate', item.nextcheckdate);

      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'checkid').reset();
    Ext.getCmp(mep + 'checkid').focus(true, 500);
  },
  
  OnDevSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    
//    var items = tools.JsonGet(tools.GetUrl('DevGetAcceptManage.do?am.devid=') + item.devid);
    if (item && item.devid) {
      tools.SetValue(mep+'devid',item.devid);
      tools.SetValue(mep+'devname',item.devname);
      tools.SetValue(mep+'factoryname',item.factoryname);
      tools.SetValue(mep+'planid',item.tranid);
      tools.SetValue(mep+'lastcheckdate',item.lastcheckdate);
    }
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.checkid) {
          tools.SetValue(mep + 'checkid', action.result.data.checkid);
        }
      }
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
     '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
     '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
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
          'dcp.devname': tools.GetValueEncode(mep + 'searchdevnameforcalib')
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