Ext.define('alms.devuseapply', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '设备申请',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devuseapply',
      storeUrl: 'DevSearchDevUseApply.do',
      saveUrl: 'DevSaveDevUseApply.do',
      expUrl: 'DevSearchDevUseApply.do',
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
    

    var devitems = [
       ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevnamefortotal', id: mep + 'searchdevnamefortotal', allowBlank: true },
       ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var nowdate = new Date();

    var devid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备编号', name: 'dua.devid', id: mep + 'devid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
      storeUrl: 'DevSearchBasDev4Use.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           devid,
           tools.FormCombo('申请人', 'dua.applyuser', mep + 'applyuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),                                                                  	
           tools.FormCombo('设备状态', 'dua.devstatus', mep + 'devstatus', tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),
           tools.FormCombo('设备借出状态', 'dua.borrowstatu', mep + 'borrowstatu', tools.ComboStore('BorrowStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText('设备名称', 'dua.devname', mep + 'devname', 20, '100%', false, 4,'',90),
           tools.FormDate('申请日期', 'dua.applydate', mep + 'applydate', 'Y-m-d', '100%', false, 17,nowdate,90),
           tools.FormCombo('设备接收人', 'dua.acceptuser', mep + 'acceptuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '100%', false, 4,90), 
           tools.FormCombo('实验室', 'dua.labid', mep + 'labid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '100%', false, 4,90), 
          {xtype:'hiddenfield',name:'dua.flowaction',id: mep + 'flowaction'},
          {xtype:'hiddenfield',name:'dua.flowstatus',id: mep + 'flowstatus'},
          {xtype:'hiddenfield',name:'dua.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'dua.eventuser',id: mep + 'eventuser'},
          {xtype:'hiddenfield',name:'dua.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'dua.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('使用项目', 'dua.useproject', mep + 'useproject', 200, '100%', true,12,5,90),
      tools.FormTextArea('备注', 'dua.remark', mep + 'remark', 200, '100%', true,13,5,90)
    ];
    
    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['applyuser','devstatus', 'borrowstatu', 'devid','devname', 'applydate', 'acceptuser', 'labid', 'flowaction',
    'flowstatus', 'officeuser', 'officeusername', 'officedate', 'officedesc', 'allowuser', 'allowusername', 'allowdate', 'allowdesc', 'remark','useproject' ];
    me.enEdits = ['applyuser','devstatus', 'borrowstatu','devid', 'devname', 'applydate', 'acceptuser', 'labid', 'flowaction',
    'flowstatus', 'officeuser', 'officeusername', 'officedate', 'officedesc', 'allowuser', 'allowusername',  'allowdate', 'allowdesc', 'remark','useproject' ];
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

  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
     var searchitems = [
                        ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
                        ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true }

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
        	 'dua.devid':tools.GetValueEncode(mep+'searchdevid'),
             'dua.devname':tools.GetValueEncode(mep+'searchdevname')
    
  
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
    tools.SetValue(mep + 'applyuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'acceptuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'labid', gpersist.UserInfo.user.deptid);
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
    tools.SetValue(mep + 'devid',item.devid);
    tools.SetValue(mep + 'applyuser', item.applyuser);
    tools.SetValue(mep + 'devstatus', item.devstatus);
    tools.SetValue(mep + 'borrowstatu', item.borrowstatu);
    tools.SetValue(mep + 'devname', item.devname);
    tools.SetValue(mep + 'applydate', item.applydate);
    tools.SetValue(mep + 'acceptuser', item.acceptuser);
    tools.SetValue(mep + 'labid', item.labid);
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
    tools.SetValue(mep + 'useproject',item.useproject);
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
  },
  
  OnDevSelect:function(render,item){
	    var me = this;
	    var mep = me.tranPrefix;
	      
	    if (item && item.devid) {
	      tools.SetValue(mep+'devid',item.devid);
	      tools.SetValue(mep+'devname',item.devname);
	  
	      tools.SetValue(mep+'devstatus',item.devstatus);
	      tools.SetValue(mep+'borrowstatu',item.borrowStatu);
	      if(item.borrowStatu=='03'){
	    	  tools.SetValue(mep+'borrowstatu','05');
	      }
	    

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
	          'bd.devname': tools.GetValueEncode(mep + 'searchdevnamefortotal')
	        });
	      });
	    };
}
  
});
