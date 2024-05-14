 Ext.define('alms.mansampledeal',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'样品处置单',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_sampledeal',
          storeUrl:'SampSearchSampleDeal.do',
          saveUrl:'SampSaveSampleDeal.do',
          expUrl:'SampSearchSampleDeal.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasSubmit: true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true }
     ];
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeCreateEdit:function(){
     var me = this;
     var mep = this.tranPrefix;
     var nowdate = new Date();
     
     var sampitems = [
     ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecodes', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplenames', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
     ];
     
     var samplecode = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品编号', name: 'sampdeal.samplecode', id: mep + 'samplecode', winTitle: '样品编号',
      maxLength: 20, maxLengthText: '样品编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品编号！', allowBlank: false, anchor: '97%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustaskfordeal',
      storeUrl: 'LabSearchBusTaskForDeal.do',
      editable:false,
      searchTools:sampitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     samplecode.on('griditemclick', me.OnSampleSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'sampdeal.tranid', mep + 'tranid', 20, '97%', false, 1,'',90),
              tools.FormDate('检测结束日期', 'sampdeal.finishdate', mep + 'finishdate', 'Y-m-d', '97%', false, 4,'',90),
              tools.FormDate('处置日期', 'sampdeal.dealdate', mep + 'dealdate', 'Y-m-d', '97%', true, 7,'',90)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//              tools.FormText('样品编号', 'sampdeal.samplecode', mep + 'samplecode', 20, '97%', false, 2),
              samplecode,
              tools.FormDate('收样日期', 'sampdeal.collectdate', mep + 'collectdate', 'Y-m-d', '97%', false, 5,''),
              tools.FormText('处置方式', 'sampdeal.dealway', mep + 'dealway', 20, '97%', false, 8)
              
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('样品名称', 'sampdeal.samplename', mep + 'samplename', 20, '100%', false, 3),
              tools.FormDate('保存期限', 'sampdeal.storeend', mep + 'storeend', 'Y-m-d', '100%', false, 6,''),
              tools.FormCombo('检验状态', 'sampdeal.dealstatus', mep + 'dealstatus', tools.ComboStore('DealStatus', gpersist.SELECT_MUST_VALUE), '100%', false, 9),
              {xtype:'hiddenfield',name:'sampdeal.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'sampdeal.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'sampdeal.dealuser',id: mep + 'dealuser'},
              {xtype:'hiddenfield',name:'sampdeal.taskid',id: mep + 'taskid'},
              {xtype:'hiddenfield',name:'sampdeal.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('处置方法', 'sampdeal.dealmethod', mep + 'dealmethod', 200, '100%', false, 10,10,90)
     ];
     me.disNews = ['tranid', 'dealuser', 'dealusername', 'dealuserdate'];
     me.disEdits = ['tranid', 'dealuser', 'dealusername', 'dealuserdate'];
     me.enNews = [ 'samplecode', 'samplename', 'finishdate', 'collectdate', 'storeend', 'dealdate', 'dealway',  'dealmethod', 'flowaction', 'flowstatus', 'dealstatus', 'approveuser', 'approveusername', 'approvedate'],
     me.enEdits = [  'samplecode', 'samplename', 'finishdate', 'collectdate', 'storeend', 'dealdate', 'dealway',  'dealmethod', 'flowaction', 'flowstatus','dealstatus', 'approveuser', 'approveusername', 'approvedate'];
   },
   
  OnSampleSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnSampleBeforeLoad();
      var samplename = Ext.getCmp(mep + 'samplecode');
      samplename.store.loadPage(1);
   },
   
  OnSampleBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var samplename = Ext.getCmp(mep + 'samplecode');
      if(samplename.store){
        samplename.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'bt.samplecode':tools.GetValueEncode(mep + 'searchsamplecodes'),
              'bt.samplename':tools.GetValueEncode(mep + 'searchsamplenames')
            });
        });
      }
   },
   
  OnSampleSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.samplecode){
        tools.SetValue(mep+"samplename",item.samplename);
        tools.SetValue(mep+"samplecode",item.samplecode);
        tools.SetValue(mep+"collectdate",item.accdate);
        tools.SetValue(mep+"finishdate",item.reqdate);
        tools.SetValue(mep+"taskid",item.taskid);
     }
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
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'sampdeal.samplecode':tools.GetValueEncode(mep+'searchsamplecode'),
           'sampdeal.samplename':tools.GetValueEncode(mep+'searchsamplename')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'taskid', item.taskid);
    tools.SetValue(mep + 'samplecode', item.samplecode);
    tools.SetValue(mep + 'samplename', item.samplename);
    tools.SetValue(mep + 'finishdate', item.finishdate);
    tools.SetValue(mep + 'collectdate', item.collectdate);
    tools.SetValue(mep + 'storeend', item.storeend);
    tools.SetValue(mep + 'dealdate', item.dealdate);
    tools.SetValue(mep + 'dealway', item.dealway);
    tools.SetValue(mep + 'dealmethod', item.dealmethod);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'dealuser', item.dealuser);
    tools.SetValue(mep + 'dealusername', item.dealusername);
    tools.SetValue(mep + 'dealuserdate', item.dealuserdate);
    tools.SetValue(mep + 'dealstatus', item.dealstatus);
    tools.SetValue(mep + 'approveuser', item.approveuser);
    tools.SetValue(mep + 'approveusername', item.approveusername);
    tools.SetValue(mep + 'approvedate', item.approvedate);
    return true;
   },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'dealuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'dealusername', gpersist.UserInfo.user.username );
     Ext.getCmp(mep + 'finishdate').reset();
     Ext.getCmp(mep + 'collectdate').reset();
     Ext.getCmp(mep + 'storeend').reset();
     Ext.getCmp(mep + 'dealdate').reset();
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var dealstatus = Ext.getCmp(mep+'dealstatus').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(dealstatus == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择处置状态！');
        return;
      }
    }
    return true;
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
          tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
          tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
        }
      }
    }
    me.OnDetailRefresh();
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
