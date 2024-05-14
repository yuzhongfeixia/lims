Ext.define('alms.maninnerinvalid', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '不符合纠正措施',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_innerinvalid',
      storeUrl: 'InnerSearchInnerInvalid.do',
      saveUrl: 'InnerSaveInnerInvalid.do',
      expUrl: 'InnerSearchInnerInvalid.do',
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
     
    var invalidusername = tools.UserPicker(mep + 'invalidusername','innerinvalid.invalidusername','不符合识别人',90);
     
    invalidusername.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'innerinvalid.tranid', mep + 'tranid',20, '96%', false, 1),
          tools.FormCombo('不符合来源', 'innerinvalid.correctsource', mep + 'correctsource', tools.ComboStore('InnerCorrect', gpersist.SELECT_MUST_VALUE),'96%', false, 3),
          invalidusername,
          tools.FormText('责任人', 'innerinvalid.tranusername', mep + 'tranusername', 40, '96%', false, 8)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('部门', 'innerinvalid.occurdept', mep + 'occurdept', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '100%', false, 2),
          tools.FormCombo('对已发生不符合的评价', 'innerinvalid.invalidadv', mep + 'invalidadv', tools.ComboStore('InnerAdv', gpersist.SELECT_MUST_VALUE),'100%', false,5),
          tools.FormDate('不符合日期', 'innerinvalid.invaliduserdate', mep + 'invaliduserdate', 'Y-m-d', '100%', false, 7,nowdate),
          tools.FormDate('记录日期', 'innerinvalid.trandate', mep + 'trandate', 'Y-m-d', '100%', false, 7,nowdate),
          {xtype:'hiddenfield',name:'innerinvalid.flowaction',id: mep + 'flowaction'},
          {xtype:'hiddenfield',name:'innerinvalid.flowstatus',id: mep + 'flowstatus'},
          {xtype:'hiddenfield',name:'innerinvalid.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'innerinvalid.invaliduser',id: mep + 'invaliduser'},
          {xtype:'hiddenfield',name:'innerinvalid.occurdeptname',id: mep + 'occurdeptname'},
          {xtype:'hiddenfield',name:'innerinvalid.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('已发生不符合事实的描述', 'innerinvalid.invaliddesc', mep + 'invaliddesc', 200, '100%', true,9,5),
      tools.FormTextArea('已发生不符合原因分析', 'innerinvalid.invalidreason', mep + 'invalidreason', 200, '100%', true,10,5),
      tools.FormTextArea('拟采取的纠正措施（包括具体方法、完成期限和实施责任人）', 'innerinvalid.invalidmeasure', mep + 'invalidmeasure', 200, '100%', true,11,5)
    ];
    
    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['occurdept', 'occurdeptname', 'correctsource', 'invaliduser', 'invalidusername','invaliduserdate', 'invaliddesc', 'invalidadv', 'invalidreason', 'invalidmeasure', 'deptuser', 
                 'deptusername', 'deptdate', 'deptdesc', 'audituser', 'auditusername', 'auditdate'];
    me.enEdits = ['occurdept', 'occurdeptname', 'correctsource', 'invaliduser', 'invalidusername','invaliduserdate', 'invaliddesc', 'invalidadv', 'invalidreason', 'invalidmeasure',
                 'deptuser', 'deptusername', 'deptdate', 'deptdesc', 'audituser', 'auditusername', 'auditdate'];
    //给部门名称赋值
    Ext.getCmp(mep + 'occurdept').on('select',me.OnDeptSelect,me);
  },
  
  OnDeptSelect:function(combo, records, eOpts){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+"occurdeptname",records[0].data.name);
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
      tools.SetValue(mep+"invaliduser",item.userid);
      tools.SetValue(mep+"invalidusername",item.username);
    }
   },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '部门', 60, tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE))
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
           'innerinvalid.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'innerinvalid.occurdept':tools.GetValueEncode(mep+'searchdeptid')
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
    tools.SetValue(mep + 'correctsource', gpersist.SELECT_MUST_VALUE );
    tools.SetValue(mep + 'invalidadv', gpersist.SELECT_MUST_VALUE );
    tools.SetValue(mep + 'occurdept', gpersist.SELECT_MUST_VALUE );
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var correctsource = Ext.getCmp(mep+'correctsource').getValue();
    var invalidadv = Ext.getCmp(mep+'invalidadv').getValue();
    var occurdept = Ext.getCmp(mep+'occurdept').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(occurdept == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择部门！');
        return;
      }
      if(correctsource == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择不符合来源！');
        return;
      }
      if(invalidadv == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择不符合评价！');
        return;
      }
    }
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'occurdept', item.occurdept);
    tools.SetValue(mep + 'occurdeptname', item.occurdeptname);
    tools.SetValue(mep + 'correctsource', item.correctsource);
    tools.SetValue(mep + 'invaliduser', item.invaliduser);
    tools.SetValue(mep + 'invaliduserdate', item.invaliduserdate);
    tools.SetValue(mep + 'invalidusername', item.invalidusername);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'invaliddesc', item.invaliddesc);
    tools.SetValue(mep + 'invalidadv', item.invalidadv);
    tools.SetValue(mep + 'invalidreason', item.invalidreason);
    tools.SetValue(mep + 'invalidmeasure', item.invalidmeasure);
    tools.SetValue(mep + 'deptuser', item.deptuser);
    tools.SetValue(mep + 'deptusername', item.deptusername);
    tools.SetValue(mep + 'deptdate', item.deptdate);
    tools.SetValue(mep + 'deptdesc', item.deptdesc);
    tools.SetValue(mep + 'audituser', item.audituser);
    tools.SetValue(mep + 'auditusername', item.auditusername);
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'isfinish', item.isfinish);
    tools.SetValue(mep + 'validuser', item.validuser);
    tools.SetValue(mep + 'validusername', item.validusername);
    tools.SetValue(mep + 'validdate', item.validdate);
    tools.SetValue(mep + 'isvalid', item.isvalid);
    tools.SetValue(mep + 'validresult', item.validresult);
    tools.SetValue(mep + 'allowuser', item.allowuser);
    tools.SetValue(mep + 'allowusername', item.allowusername);
    tools.SetValue(mep + 'allowdate', item.allowdate);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
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