Ext.define('alms.stdchange', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '标准变更内容识别记录',
      winWidth: 750,
      winHeight: 350,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdchange',
      storeUrl: 'StdSearchStdChange.do',
      saveUrl: 'StdSaveStdChange.do',
      hasGridSelect: true,
      expUrl: 'StdSearchStdChange.do',
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
    
    var changeid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '变更编号', name: 'stdchange.changeid', id: mep + 'changeid', winTitle: '选择标准变更编号',
      maxLength: 20, maxLengthText: '变更编号不能超过20！', selectOnFocus: false, labelWidth: 120,
      blankText: '变更编号为空！', allowBlank: false, anchor: '96%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdreview',
      storeUrl: 'StdSearchStdReviewForChange.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    changeid.on('griditemclick', me.OnReviewSelect, me);
    
    var stdusername = tools.UserPicker(mep + 'stdusername','stdchange.stdusername','跟踪人',120,'96%');
     
    stdusername.on('griditemclick', me.OnUserSelect, me);
    
  //判定依据
    var judgeitems = [
      ' ', { xtype: 'textfield', fieldLabel: '检测标准名称', labelWidth: 100, width: 220, maxLength: 40, name: 'searchstdname', id: mep + 'searchstdname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnJudgeSearch, scope: me }
    ];
    
    var sampleid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '已批准的标准名称', name: 'stdchange.stdname', id: mep + 'stdname', winTitle: '选择标准名称',
      maxLength: 100, maxLengthText: '判定依据不能超过100个字符！', selectOnFocus: false, labelWidth: 120,
      blankText: '标准不能为空！', allowBlank: true, anchor: '96%', tabIndex: 5,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bastest',
      storeUrl: 'BasSearchBasTest.do',
      editable:false,
      searchTools:judgeitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    sampleid.on('griditemclick', me.OnJudgeSelect, me);
    sampleid.on('gridbeforeload', me.OnSampLoad, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'stdchange.tranid', mep + 'tranid', 20, '96%', false, 1,'',120),
          sampleid,
          tools.FormText('变更后的标准名称', 'stdchange.replstdname', mep + 'replstdname', 50, '96%', false, 8,'',120),
//          tools.FormText('限制范围', 'stdchange.judgestandardrange', mep + 'judgestandardrange', 50, '96%', false, 8,'',120),
         
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          changeid,
          tools.FormText('已批准的标准代号', 'stdchange.stdid', mep + 'stdid', 40, '96%', false, 5,'',120),
          tools.FormText('变更后的标准代号', 'stdchange.replstdid', mep + 'replstdid', 40, '96%', false, 7,'',120),
//          tools.FormText('说明', 'stdchange.judgestandardexplain', mep + 'judgestandardexplain', 40, '96%', false, 7,'',120),
        ]},
                                                                       
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
         tools.FormText('类别(产品/项目/参数)', 'stdchange.parametername', mep + 'parametername', 40, '96%', false, 4,'',120),
         tools.FormCombo('能力确认方式', 'stdchange.sureability', mep + 'sureability', tools.ComboStore('SureAbility', gpersist.SELECT_MUST_VALUE), '96%', false, null,120),
         
//          tools.FormText('能力确认方式', 'stdchange.sureability', mep + 'sureability', 50, '96%', false, 9,'',120),
          stdusername,
          {xtype:'hiddenfield',name:'stdchange.stduser',id: mep + 'stduser'},
          {xtype:'hiddenfield',name:'stdchange.serial',id: mep + 'serial'},
          {xtype:'hiddenfield',name:'stdchange.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('变更内容', 'stdchange.changecontent', mep + 'changecontent', 3000, '100%', true, 9, 10,120)
    ];
    me.disNews = [ 'stdid', 'tranid','parametername'];
    me.disEdits = [ 'stdid','tranid','parametername'];
    me.enNews =['sureability','changeid','changecontent','parametername', 'stdname', 'replstdid', 'replstdname','stduser','stdusername'];
    me.enEdits = ['sureability','changeid','changecontent','parametername', 'stdname', 'replstdid', 'replstdname','stduser','stdusername'];
//    me.enNews =['sureability','judgestandardrange','judgestandardexplain','changeid','changecontent','parametername', 'stdname', 'replstdid', 'replstdname','stduser','stdusername'];
//    me.enEdits = ['sureability','judgestandardrange','judgestandardexplain','changeid','changecontent','parametername', 'stdname', 'replstdid', 'replstdname','stduser','stdusername'];
    
  },
  
  OnJudgeSearch: function () {
	     var me = this;
	     var mep = me.tranPrefix;
	     
	     var judge = Ext.getCmp(mep + 'stdname');
	     me.OnJudgeBeforeLoad();
	     judge.store.loadPage(1);    
	   },
	   
	   OnJudgeBeforeLoad: function () {    
	     var me = this;
	     var mep = me.tranPrefix;
	     
	     var judge = Ext.getCmp(mep + 'stdname');
	     console.log(judge)
	     if (judge.store) {      
	       judge.store.on('beforeload', function (store, options) {      
	         Ext.apply(store.proxy.extraParams, {
	           'btest.teststandardname': tools.GetValueEncode(mep + 'searchstdname') 
	         });
	       });
	     }
	   },
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep + 'stduser',item.userid);
      tools.SetValue(mep + 'stdusername',item.username);
    }
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
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
          'stdchange.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid'))
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
     tools.SetValue(mep + 'stdusername',item.stdusername);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'changeid', item.changeid);
      tools.SetValue(mep + 'serial', item.serial);
      tools.SetValue(mep + 'parametername', item.parametername);
      tools.SetValue(mep + 'stdid', item.stdid);
      tools.SetValue(mep + 'stdname', item.stdname);
      tools.SetValue(mep + 'replstdid', item.replstdid);
      tools.SetValue(mep + 'replstdname', item.replstdname);
      tools.SetValue(mep + 'changecontent', item.changecontent);
      tools.SetValue(mep + 'sureability', item.sureability);
      
//      tools.SetValue(mep + 'judgestandardrange', item.judgestandardrange);
//      tools.SetValue(mep + 'judgestandardexplain', item.judgestandardexplain);
      
      tools.SetValue(mep + 'approuser', item.approuser);
      tools.SetValue(mep + 'approusername', item.approusername);
      tools.SetValue(mep + 'approdate', item.approdate);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
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
  
  OnReviewSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.tranid){
       tools.SetValue(mep+"changeid",item.tranid);
    }
  },
  
  
  OnJudgeSelect : function(render, item){
	     var me = this;
	     var mep = me.tranPrefix;
	     if(item && !Ext.isEmpty(item.teststandard)){
	       tools.SetValue(mep + 'stdname', item.teststandardname);
	       tools.SetValue(mep + 'stdid', item.teststandardcode);
	       tools.SetValue(mep + 'parametername', item.standardnum);
	     
	     } else{
	       tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
	     }
	   },
  
  OnSampLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var changeid = Ext.getCmp(mep + 'changeid').getValue();
    if(changeid==null||changeid==''){
      tools.alert('请选择变更编号！');
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
