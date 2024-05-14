Ext.define('alms.devuseallot', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '设备调拨',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devuseallot',
      storeUrl: 'DevSearchDevUseAllot.do',
      saveUrl: 'DevSaveDevUseAllot.do',
      expUrl: 'DevSearchDevUseAllot.do',
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

    var devid = Ext.create('Ext.ux.GridPicker', {
        fieldLabel: '设备编号', name: 'dual.devid', id: mep + 'devid', winTitle: '选择设备',
        maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 90,
        blankText: '设备编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
        columnUrl: 'SysSqlColumn.do?sqlid=p_get_basdev',
        storeUrl: 'DevSearchBasDev.do',
        editable:false,
        searchTools:devitems,
        hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    devid.on('griditemclick', me.OnDevSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           devid,
           tools.FormCombo('调拨人', 'dual.allotuser', mep + 'allotuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),                                                                  	
           tools.FormCombo('设备状态', 'dual.devstatus', mep + 'devstatus', tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),
           tools.FormCombo('调出实验室', 'dual.startlabid', mep + 'startlabid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90) ,
           tools.FormCombo('调出人', 'dual.allotoutuser', mep + 'allotoutuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)    
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText('设备名称', 'dual.devname', mep + 'devname', 20, '100%', false, 4,'',90),
           tools.FormDate('调拨日期', 'dual.allotdate', mep + 'allotdate', 'Y-m-d', '100%', false, 17,null,90),
           tools.FormCombo('调入实验室', 'dual.endlabid', mep + 'endlabid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '100%', false, 4,90),
           tools.FormCombo('调入人', 'dual.allotinuser', mep + 'allotinuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),     
          {xtype:'hiddenfield',name:'dual.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'dual.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('调出项目', 'dual.startproject', mep + 'startproject', 200, '100%', true,12,5,90),
      tools.FormTextArea('调入项目', 'dual.endproject', mep + 'endproject', 200, '100%', true,12,5,90)
    ];
    
    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['allotuser','devstatus','devid','devname', 'allotdate','startlabid', 'endlabid',
                  'endproject','startproject','allotoutuser','allotinuser' ];
    me.enEdits =['allotuser','devstatus','devid','devname', 'allotdate','startlabid', 'endlabid',
                 'endproject','startproject','allotoutuser' ,'allotinuser'];
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
    
     var items = [
        ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
        ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '', { id: mep + 'btnEdit', text: '处理', iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
     
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
        	 'dual.devid':tools.GetValueEncode(mep+'searchdevid'),
             'dual.devname':tools.GetValueEncode(mep+'searchdevname')
    
  
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
    tools.SetValue(mep + 'devid',item.devid);
    tools.SetValue(mep + 'allotuser', item.allotuser);
    tools.SetValue(mep + 'devstatus', item.devstatus);
    tools.SetValue(mep + 'devname', item.devname);
    tools.SetValue(mep + 'allotdate', item.allotdate);
    tools.SetValue(mep + 'startlabid', item.startlabid);
    tools.SetValue(mep + 'startproject',item.startproject);
    tools.SetValue(mep + 'endlabid', item.endlabid);
    tools.SetValue(mep + 'endproject',item.endproject);
    tools.SetValue(mep + 'allotoutuser',item.allotoutuser);
    tools.SetValue(mep + 'allotinuser',item.allotinuser);
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
     '-',  { id: mep + 'btnSubmits', text: '调拨确认', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
     '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
     ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
     '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
   ];
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
      tools.BtnsDisable(['btnSubmits'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmits'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmits'], mep);
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
            tools.BtnsDisable(['btnSubmits'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmits'], mep);
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
            tools.BtnsDisable(['btnSubmits'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmits'], mep);
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
	          'dual.devname': tools.GetValueEncode(mep + 'searchdevnamefortotal')
	        });
	      });
	    };
  }
  
});