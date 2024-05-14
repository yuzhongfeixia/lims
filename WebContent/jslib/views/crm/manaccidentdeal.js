Ext.define('alms.manaccidentdeal', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '检验事故处理登记',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_crmaccidentdeal',
      storeUrl: 'CrmSearchCrmAccidentDeal.do',
      saveUrl: 'CrmSaveCrmAccidentDeal.do',
      expUrl: 'CrmSearchCrmAccidentDeal.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasSubmit: true,
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var sampleitems = [
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsample', id: mep + 'searchsample', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
     ];
     
    var samplename = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'accident.samplename', id: mep + 'samplename', winTitle: '选择样品名称',
      maxLength: 20, maxLengthText: '样品名称不能超过20！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品名称为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    samplename.on('griditemclick', me.OnSampleSelect, me);
     
    var eventusername = tools.UserPicker(mep + 'eventusername','accident.eventusername','事故负责人');
     
    eventusername.on('griditemclick', me.OnUserSelect, me);
    
    var tested = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '送检单位', name: 'accident.testedname', id: mep + 'testedname', winTitle: '选择送检单位',
      maxLength: 30, maxLengthText: '送检单位不能超过30字！', selectOnFocus: false, labelWidth: 100,
      blankText: '送检单位不能为空！', allowBlank: false, anchor: '100%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_testedunit',
      storeUrl: 'LabSearchBusTestedUnit.do',
      editable:false,
//      searchTools:bookitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
   
    tested.on('griditemclick', me.OnTestedSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('验证编号', 'accident.tranid', mep + 'tranid',20, '96%', false, 1),
          samplename,
          eventusername
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tested,
          tools.FormText('事故来源', 'accident.oldreport', mep + 'oldreport', 20, '100%', false, 4,'',100),
          tools.FormDate('事故日期', 'accident.eventdate', mep + 'eventdate', 'Y-m-d', '100%', false, 6,nowdate,100),
          {xtype:'hiddenfield',name:'accident.testedunit',id: mep + 'testedunit'},
          {xtype:'hiddenfield',name:'accident.sampleid',id: mep + 'sampleid'},
          {xtype:'hiddenfield',name:'accident.flowaction',id: mep + 'flowaction'},
          {xtype:'hiddenfield',name:'accident.flowstatus',id: mep + 'flowstatus'},
          {xtype:'hiddenfield',name:'accident.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'accident.eventuser',id: mep + 'eventuser'},
          {xtype:'hiddenfield',name:'accident.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('事故原因', 'accident.eventreason', mep + 'eventreason', 200, '100%', true,11,5),
      tools.FormTextArea('处理措施', 'accident.dealdesc', mep + 'dealdesc', 200, '100%', true,12,5),
      tools.FormTextArea('备注', 'accident.remark', mep + 'remark', 200, '100%', true,13,5)
    ];
    
    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['testedname','testedunit', 'sampleid', 'samplename', 'oldreport', 'eventdate', 'eventuser', 'eventusername', 'eventreason', 'dealdesc', 'flowaction',
    'flowstatus', 'officeuser', 'officeusername', 'officedate', 'officedesc', 'allowuser', 'allowusername', 'allowdate', 'allowdesc', 'remark' ];
    me.enEdits = ['testedname','testedunit', 'sampleid', 'samplename', 'oldreport', 'eventdate', 'eventuser', 'eventusername', 'eventreason', 'dealdesc', 'flowaction',
    'flowstatus', 'officeuser', 'officeusername', 'officedate', 'officedesc', 'allowuser', 'allowusername',  'allowdate', 'allowdesc', 'remark' ];
  },
  
  OnTestedSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.testedid) {
      tools.SetValue(mep+'testedname',item.testedname);
      tools.SetValue(mep+'testedunit',item.testedid);
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
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"eventuser",item.userid);
      tools.SetValue(mep+"eventusername",item.username);
    }
   },
  
  OnSampleSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnTranBeforeLoad();
    var samplename = Ext.getCmp(mep + 'samplename');
    samplename.store.loadPage(1);
    
  },
 
  OnTranBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var samplename = Ext.getCmp(mep + 'samplename');
    
    if(samplename.store){
      samplename.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'bsample.samplename':tools.GetValueEncode(mep + 'searchsample')
          });
      });
    }
  },
  
  OnSampleSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.sampleid){
       tools.SetValue(mep+"sampleid",item.sampleid);
       tools.SetValue(mep+"samplename",item.samplename);
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true }
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(searchitems,mep);
    tools.SetToolbar(items, mep);
     
    var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
    me.tbGrid.add(searchtoolbar);
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'accident.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'accident.samplename':tools.GetValueEncode(mep+'searchsamplename'),
           'accident.testedname':tools.GetValueEncode(mep+'searchtestedname')
         })
       });
     }
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'testedunit', item.testedunit);
    tools.SetValue(mep + 'testedname', item.testedname);
    tools.SetValue(mep + 'sampleid', item.sampleid);
    tools.SetValue(mep + 'samplename', item.samplename);
    tools.SetValue(mep + 'oldreport', item.oldreport);
    tools.SetValue(mep + 'eventdate', item.eventdate);
    tools.SetValue(mep + 'eventuser', item.eventuser);
    tools.SetValue(mep + 'eventusername', item.eventusername);
    tools.SetValue(mep + 'eventreason', item.eventreason);
    tools.SetValue(mep + 'dealdesc', item.dealdesc);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'officeuser', item.officeuser);
    tools.SetValue(mep + 'officeusername', item.officeusername);
    tools.SetValue(mep + 'officedate', item.officedate);
    tools.SetValue(mep + 'officedesc', item.officedesc);
    tools.SetValue(mep + 'allowuser', item.allowuser);
    tools.SetValue(mep + 'allowusername', item.allowusername);
    tools.SetValue(mep + 'allowdate', item.allowdate);
    tools.SetValue(mep + 'allowdesc', item.allowdesc);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'remark', item.remark);
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