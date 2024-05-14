Ext.define('alms.manreceptdeal', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '投诉处理',
      winWidth: 750,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_crmreceptdeal',
      storeUrl: 'CrmSearchCrmReceptDeal.do',
      saveUrl: 'CrmSaveCrmReceptDeal.do',
      expUrl: 'CrmSearchCrmReceptDeal.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasSubmit:true,
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var tranuser = tools.UserPicker(mep + 'acceptusername','receptdeal.acceptusername','接收人');
     
    tranuser.on('griditemclick', me.OnUserSelect, me);
    
    var receptid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '接待编号', name: 'receptdeal.receptid', id: mep + 'receptid', winTitle: '选择接待编号',
      maxLength: 20, maxLengthText: '接待编号不能超过20！', selectOnFocus: true, labelWidth: 80,
      blankText: '接待编号为空！', allowBlank: true, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_crmrecept',
      storeUrl: 'CrmSearchCrmReceptForDeal.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    receptid.on('griditemclick', me.OnReceptSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('处理编号', 'receptdeal.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormDate('投诉时间', 'receptdeal.complaindate', mep + 'complaindate', 'Y-m-d', '96%', false,3,nowdate),
          tools.FormText('联系人', 'receptdeal.linkman', mep + 'linkman', 20, '96%', false, 5),
          tools.FormText('联系电话', 'receptdeal.linktele', mep + 'linktele', 30, '96%', false, 7),
          tranuser
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          receptid,
          tools.FormText('投诉时效', 'receptdeal.prescription', mep + 'prescription', 10, '96%', true, 2),
          tools.FormDate('受理时间', 'receptdeal.acceptdate', mep + 'acceptdate', 'Y-m-d', '96%', false,4,nowdate),
          tools.FormText('邮编', 'receptdeal.linkpost', mep + 'linkpost', 10, '96%', true, 8),
          {xtype:'hiddenfield',name:'receptdeal.acceptuser',id: mep + 'acceptuser'},
          {xtype:'hiddenfield',name:'receptdeal.flowstatus',id: mep + 'flowstatus'},
          {xtype:'hiddenfield',name:'receptdeal.flowaction',id: mep + 'flowaction'},
          {xtype:'hiddenfield',name:'receptdeal.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('申(投)诉人(单位)', 'receptdeal.complainobject', mep + 'complainobject', 200, '98%', true, 9,3),
      tools.FormTextArea('联系人地址', 'receptdeal.linkaddr', mep + 'linkaddr', 30, '98%', true, 10,2),
      tools.FormTextArea('投诉申诉内容', 'receptdeal.complaindesc', mep + 'complaindesc', 200, '98%', true,11,6),
      tools.FormTextArea('备注', 'receptdeal.remark', mep + 'remark', 200, '98%', true,12,3)
    ];
    
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['prescription','receptid', 'complainobject', 'complaindate', 'acceptdate', 'linkman', 'linktele', 'linkpost', 'linkaddr', 'complaindesc', 'flowaction', 
    'flowstatus', 'acceptuser', 'acceptusername', 'acceptdesc', 'audituser', 'auditusername', 'auditdate', 'dealresult',
    'allowuser', 'allowusername', 'allowdate', 'remark'];
    me.enEdits = ['prescription','receptid', 'complainobject', 'complaindate', 'acceptdate', 'linkman', 'linktele', 'linkpost', 'linkaddr', 'complaindesc', 'flowaction',
    'flowstatus', 'acceptuser', 'acceptusername', 'acceptdesc', 'audituser', 'auditusername', 'auditdate', 'dealresult', 'allowuser', 'allowusername',
    'allowdate', 'remark'];
  },
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"acceptuser",item.userid);
      tools.SetValue(mep+"acceptusername",item.username);
    }
   },
   
  OnReceptSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"receptid",item.tranid);
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '处理编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '接待编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchreceptid', id: mep + 'searchreceptid', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments); 
    
    tools.SetValue(mep + 'recepttype',gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'receptdeal.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'receptdeal.receptid': tools.GetEncode(tools.GetValue(mep + 'searchreceptid'))
        });
      });
    };
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
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'receptid', item.receptid);
    tools.SetValue(mep + 'complainobject', item.complainobject);
    tools.SetValue(mep + 'complaindate', item.complaindate);
    tools.SetValue(mep + 'acceptdate', item.acceptdate);
    tools.SetValue(mep + 'linkman', item.linkman);
    tools.SetValue(mep + 'linktele', item.linktele);
    tools.SetValue(mep + 'linkpost', item.linkpost);
    tools.SetValue(mep + 'linkaddr', item.linkaddr);
    tools.SetValue(mep + 'complaindesc', item.complaindesc);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'acceptuser', item.acceptuser);
    tools.SetValue(mep + 'acceptusername', item.acceptusername);
    tools.SetValue(mep + 'acceptdesc', item.acceptdesc);
    tools.SetValue(mep + 'audituser', item.audituser);
    tools.SetValue(mep + 'auditusername', item.auditusername);
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'dealresult', item.dealresult);
    tools.SetValue(mep + 'allowuser', item.allowuser);
    tools.SetValue(mep + 'allowusername', item.allowusername);
    tools.SetValue(mep + 'allowdate', item.allowdate);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep+'prescription',item.prescription);

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
//  OnItemClick:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    var record = tools.OnGridLoad(me.plGrid);
//    
//    if(record.flowstatus != '01'){
//      tools.BtnsDisable(['btnEdit'], mep);
//    } else{
//      tools.BtnsEnable(['btnEdit'], mep);
//    }
//  },
  
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