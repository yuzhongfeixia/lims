Ext.define('alms.samplesbusrecordaudit', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
	    var me = this;
	    Ext.apply(config, {
	      editinfo: '多样品原始记录表审核',
	      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustask',
	      storeUrl: 'LabSearchTaskForMoreAudit.do',
	      saveUrl: 'LabSaveAuditsForSamples.do',
	      expUrl: 'LabSearchBusTask.do',
	      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
	      urlDetail: 'LabGetListBusTaskAttach.do',
	      hasDetail: true,
	      hasDetailEdit: true,
	      detailTitle: '多样品原始记录表明细',
	      hasPage: true,
	      hasExit: true,
	      hasDetailGrid:true,
	      hasDetailEditTool: true,
	      idPrevNext: 'taskid',
	      hasGridSelect: true,
	      hasDetailCheck:false,
	      hasPageDetail: false,
	      detailTabs: 1
	    });
	    me.callParent(arguments);
	  },
  
  fileGrid:null,
  fileStore:null,
  fileDetail:null,
  params:null,
  
  OnInitConfig:function(){
    var me = this;
    me.fileGrid = null;
    me.fileStore = null;
    me.fileDetail = null;
    me.params = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeFormLoad: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    me.OnInitGridToolBar();
	    var items = [
	                 ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },           
	                 ' ', tools.GetToolBarCombo('searchrecordstatus', mep + 'searchrecordstatus', 200, '原始记录状态', 90, tools.ComboStore('RecordStatus', gpersist.SELECT_MUST_VALUE)),             
	                 ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
	                 '-', ' ', { id: mep + 'btnReview', text: '处理', iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
	                 '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
	                   id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
	                 ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
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
          'bt.recordstatus': tools.GetEncode(tools.GetValue(mep + 'searchrecordstatus')),
          'bt.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode'))
        });
      });
    };
  },
  
  OnBeforeCreateEdit: function () {
	    var me = this;
	    var mep = this.tranPrefix;
	                   
	    me.editControls = [
	      { xtype: 'label', id: mep + 'bustaskhtml', html: '' },
	      {xtype:'hiddenfield',name:'bt.flowstatus',id: mep + 'flowstatus'},
	      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'},
	      {xtype:'hiddenfield',name:'bt.modifydesc',id: mep + 'modifydesc'},
	      {xtype:'hiddenfield',name:'samplecode',id: mep + 'samplecode'},
	      {xtype:'hiddenfield',name:'bt.recordstatus',id: mep + 'recordstatus'},
	      {xtype:'hiddenfield',name:'bt.deal.action',id: mep + 'datadeal'}
	    ];
	      
	  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPass', text : '确认签字', iconCls : 'icon-deal', handler : me.OnPass, scope : me },
      ' ', { id : mep + 'btnBack', text : '驳回修改', iconCls : 'icon-deal', handler : me.OnBack, scope : me }, 
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnCreatePrompt: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'winPrompt')) {
      Ext.getCmp(mep + 'winPrompt').destroy();
    }
    
    if (!me.plwinPrompt) {
      me.plwinPrompt = Ext.create('Ext.form.Panel', {
        id: mep + 'plwinPrompt',
        fieldDefaults: {
          width: 370,
          labelSeparator: '：',
          labelWidth: 90,
          labelPad: 0,
          labelStyle: 'font-weight:bold;'
        },
        title: '',
        border: false,
        bodyStyle: 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width: 400,
        defaultType: 'textfield',
        items: [{
          fieldLabel: '密码',
          name: 'signerpassword',
          id: mep + 'signer_passwd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
          allowBlank: false
        }],
        listeners: {
          afterRender: function (form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter: me.OnSavePasswd,
              scope: me
            });
          }
        }
      });
    }
    
    if (!me.winPrompt) {
      me.winPrompt = Ext.create('Ext.window.Window', {
        id: mep + 'winPrompt',
        title: '签名密码',
        width: 400,
        height: 100,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plwinPrompt],
        buttons: [{ text: gpersist.STR_BTN_SUBMIT, id: mep +  'btnSavePassword', handler: me.OnSave, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winPrompt.hide(); } }]
      });
    }
  },
  
  OnPass: function () {
    var me = this, mep = me.tranPrefix;
    
    var recordstatus = tools.GetValue(mep+'recordstatus');
    
    if(recordstatus == '08'){
      tools.alert('该样品原始记录表已审核提交,不能进行审核操作....');
      return false;
    }
    
    if(recordstatus == '06'||recordstatus == '10'){
      tools.alert('该样品原始记录表已被驳回修改，不能进行审核操作.......');
      return false;
    }
    
    tools.SetValue(mep + 'datadeal', '16');
    
    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, "确定审核通过？", function(btn) {
      if (btn == 'yes') {
        
        if (!me.winPrompt)
          me.OnCreatePrompt();
          
        if(me.winPrompt) {       
          me.winPrompt.show();
          Ext.getCmp(mep + 'signer_passwd').reset();
        }
      }
    });
  },
  
  OnBack:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var recordstatus = tools.GetValue(mep+'recordstatus');
    
    if(recordstatus == '08'){
      tools.alert('该样品原始记录表已审核提交,不能进行审核操作....');
      return false;
    }
    
    if(recordstatus == '06'||recordstatus == '10'){
      tools.alert('该样品原始记录表已被驳回修改，不能进行审核操作.......');
      return false;
    }
    
    me.OnCreateCheckWin();
    
    if(me.winCheck){            
      me.winCheck.show();
    };
    
    tools.SetValue(mep + 'datadeal', '9');
    
  },
  
  OnCreateCheckWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.winWidth = 500;
    me.winHeight = 250;
    
    if (Ext.getCmp(mep + 'checkwin')) {
      Ext.getCmp(mep + 'checkwin').destroy();
    };
    
    var checkitems = [
      ' ', { id: mep + 'btnCheckSave', text: '提交', iconCls: 'icon-save', handler: me.OnCommit, scope: me },
      '-', ' ', { id: mep + 'btnCheckClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winCheck.hide(); me.detailEditType = 1;}}
    ];
    
    me.editCheckControls = [
      tools.FormTextArea('', 'checkdesc', mep + 'wincheckdesc', 200, '100%', true, 18,12)
    ];
    
    me.plCheckEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'plcheckedit',
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
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: checkitems}),
      items: me.editCheckControls    
    });
    
    me.winCheck = Ext.create('Ext.Window', {
      id: mep + 'checkwin',
      title: '审核意见',
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
      items : [me.plCheckEdit]
    });
  },
  
  OnCommit: function () {
    var me = this, mep = me.tranPrefix;
    
    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, "确定审核驳回？", function(btn) {
      if (btn == 'yes') {
        
        if (!me.winPrompt)
          me.OnCreatePrompt();
          
        if(me.winPrompt) {       
          me.winPrompt.show();
          Ext.getCmp(mep + 'signer_passwd').reset();
        }
      }
    });
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.saveParams = {signerpassword: Ext.getCmp(mep + 'signer_passwd').getValue() };
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.taskid) {
          tools.SetValue(mep + 'taskid', action.result.data.taskid);
        };
      };
    };
    me.OnDetailRefresh();
    
//    tools.BtnsDisable(['btnPass','btnBack'], mep);
  },
  
  OnSave : function() {
    var me = this;
    var mep = me.tranPrefix;

    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.isNowCopy = false;
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format('确认签字完成！！', me.editInfo));
          Ext.getCmp(mep + 'winPrompt').hide();
//          Ext.getCmp(mep + 'checkwin').hide();
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var datadeal = tools.GetValue(mep+'datadeal');
    if(datadeal == '16'){
      tools.SetValue(mep+'recordstatus','08');
    }else if(datadeal == '9'){
      tools.SetValue(mep+'recordstatus','10');
    }
    
    tools.SetValue(mep+'modifydesc',tools.GetValue(mep+'wincheckdesc'));
    
    return true;
  },
  
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '75%';
  },
  
  OnLoadData: function (item) {
	    var me = this;
	    var mep = me.tranPrefix;
	    if(item.flowstatus == '89'){
		      tools.alert('撤销原因:<br/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp'+item.modifydesc);
		      me.OnDetailRefresh();
		      return false;
		    }
	    if(!Ext.isEmpty(item.taskid)) {
	     var bustask = tools.JsonGet(tools.GetUrl('LabSearchRecordSamples.do?bt.taskid='+item.taskid));
	      tools.SetValue(mep + 'taskid',item.taskid);
	      tools.SetValue(mep + 'taskid',item.taskid);
	      tools.SetValue(mep + 'recordstatus', bustask.recordstatus);
	      tools.SetValue(mep + 'samplecode',item.samplecode);
	      tools.SetValue(mep + 'modifydesc', bustask.modifydesc);
	    }
	    
	    me.OnShowTask(item);
	    
	    me.OnDetailRefresh();
	    return true;
	  },
	  OnListSelect: function (view, record) {
		    alms.PopupFileShow(record.data.attachtypename+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
		  },
		  
	  OnDetailRefresh : function() {
		    var me = this;
		    var mep = me.tranPrefix;
		    
		    if (me.plDetailGrid && me.plDetailGrid.store) {
		      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'taskid');
	      me.plDetailGrid.store.load();
	    };
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
		        item = me.plGrid.store.getAt(i - 1).data;
		        
		        if(item.recordstatus =='02' ){
		            tools.BtnsEnable(['btnPass','btnBack'],mep);
		          }else{
		            tools.BtnsDisable(['btnPass','btnBack'],mep);
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
		        item = me.plGrid.store.getAt(i + 1).data;
		        
		        if(item.recordstatus =='02'){
		            tools.BtnsEnable(['btnPass','btnBack'],mep);
		          }else{
		            tools.BtnsDisable(['btnPass','btnBack'],mep);
		          };
		        
		        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
		        me.OnFormValidShow();
		        return;
		      }
		    }
		  },
		  
		  OnAfterCreateDetailToolBar:function(){
			    var me = this;
			    
			    //去除明细中新增、删除按钮
			    me.deitems = [];
			  },
	 
	  

	  OnShowTask:function(record){
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.html = alms.ShowMostRecordHtml(record);
	    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(me.html);
	  }
  
});
