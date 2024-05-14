Ext.define('alms.samplesrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '多样品任务单原始记录表',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustask',
      storeUrl: 'LabSearchBusTaskForMore.do',
      saveUrl: 'LabSaveBusTaskForSamples.do',
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
      {xtype:'hiddenfield',name:'bt.recordstatus',id: mep + 'recordstatus'}

    ];
      
  },
  OnAfterCreateEditToolBar:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.editToolItems = [
	      ' ', { id : mep + 'btnPass', text :'制表签名', iconCls : 'icon-deal', handler : me.OnSubmit, scope : me },
	      '-', ' ', { id : mep + 'btnBack', text : '查看驳回意见', iconCls : 'icon-outlook', handler : me.OnBack, scope : me },
	      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
	      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
	      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
	      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
	    ];
	  },
	  
  
  OnCreatePrompt: function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    var url = '';
	    url = 'UserGetListUserByDept.do'
	    me.userStore = new Ext.data.JsonStore({
	     proxy:{
	       type:'ajax',
	       url:url,
	       reader:{
	         type:'json',
	         root:'data'
	       }
	     },
	     fields:[{name:'id',mapping:'userid'},{name:'name',mapping:'username'}],
	     autoLoad:true
	    });
	    
	    if (me.userStore) {
	      me.userStore.on('beforeload', function (store, options) {      
	        Ext.apply(store.proxy.extraParams, {
	          'user.deptid': gpersist.UserInfo.dept.deptid
	        });
	      });
	    };
	    
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
	        items: [tools.FormCheckCombo('复核人指定', 'approveuser', mep + 'approveuser', me.userStore, '100%', true, 2,90),
	          { fieldLabel: '密码',
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
	        height: 130,
	        maximizable: true,
	        modal: true,
	        layout: 'fit',
	        plain: false,
	        buttonAlign: 'center',
	        closable: true,
	        closeAction: 'hide', border: false,
	        items: [me.plwinPrompt],
	        buttons: [{ text: gpersist.STR_BTN_SUBMIT, id: mep +  'btnSavePassword', handler: me.OnCommit, scope: me },
	                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winPrompt.hide(); } }]
	      });
	    }
	  },
	  
	  OnSubmit: function () {
		    var me = this, mep = me.tranPrefix;
		    var recordstatus = tools.GetValue(mep+'recordstatus');
		    var taskid = tools.GetValue(mep+'taskid');
		    var bustaskSamples = tools.JsonGet(tools.GetUrl('LabGetBusTaskSampleByTask.do?Bts.taskid='+taskid)).data;
//		    console.log(bustaskSamples.length)
		    if(bustaskSamples.length<1){
		    	tools.alert('该多样品试验尚未结束，不能进行签名...');
			      return false;
		    }else{
//		    	console.log(bustaskSamples)
				   
		    	 for(var i=0;i<bustaskSamples.length;i++){
//				    	alert(bustaskSamples[i].isjudge)
//					
						if (bustaskSamples[i].isjudge==false){
							tools.alert('该多样品试验尚未结束，不能进行签名...');
						      return false;
						}
					}	
		    }
		    
		    if(recordstatus=='02'||recordstatus=='04'||recordstatus=='08'){
		      tools.alert('该原始记录表复核中，不能进行修改...');
		      return false;
		    }
		    
		    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, "确定制表完成？", function(btn) {
		      if (btn == 'yes') {
		        
		        if (!me.winPrompt){
		          me.OnCreatePrompt();
		        }
		          
		        if(me.winPrompt) {       
		          me.winPrompt.show();
		          Ext.getCmp(mep + 'signer_passwd').reset();
		        }
		      }
		    });
		  },
		  OnCommit : function() {
			    var me = this;
			    var mep = me.tranPrefix;
			    
			    
			    if (!me.OnBeforeSubmit())
			        return;
			    
			    if (tools.InvalidForm(me.plEdit.getForm())) {
			      tools.SetValue(mep + 'datadeal', gpersist.DATA_DEAL_SUBMIT);
			      
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
//			          me.OnAfterSave(action);
			          me.OnSearch();
			          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
//			          me.OnFormValidShow();
			          me.OnAfterSubmit();
			          tools.alert(Ext.String.format('确认签字完成！', me.editInfo));
			          Ext.getCmp(mep + 'winPrompt').hide();
			        },
			        failure : function(form, action) {
			          tools.ErrorAlert(action);
			        }
			      });
			    }
			  },
			  
  OnBeforeSubmit:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    tools.SetValue(mep + 'recordstatus','02');
	    tools.SetValue(mep+'modifydesc','');
	    return true;
	  },
		  
  OnBack:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.OnCreateDetailWinCheck();
	    
	    var modifydesc = tools.GetValue(mep+'modifydesc').replace(/\n/g,"\n");
	    tools.SetValue(mep+'wincheckdesc',modifydesc);
	    
	    if(me.winDetail){      
	      me.winDetail.show();
	    };
	    
	  },
  OnCreateDetailWinCheck: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.winWidth = 500;
	    me.winHeight = 250;
	    
	    if (Ext.getCmp(mep + 'detailwin')) {
	      Ext.getCmp(mep + 'detailwin').destroy();
	    };
	    
	    var items = [
//		       ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
	    ];
	    
	    me.editCheckControls = [
	      tools.FormTextArea('', 'wincheckdesc', mep + 'wincheckdesc', 200, '100%', false, 18,14)
	    ];
	    
	    me.disNews = [];
	    me.disEdits = [];
	    me.enNews = ['wincheckdesc'];
	    me.enEdits = ['wincheckdesc'];
	    
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
	      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
	      items: me.editCheckControls    
	    });
	    
	    me.winDetail = Ext.create('Ext.Window', {
	      id: mep + 'detailwin',
	      title: '驳回意见',
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
		      return false;
		    }
	    if(!Ext.isEmpty(item.taskid)) {
	    var bustask = tools.JsonGet(tools.GetUrl('LabSearchRecordSamples.do?bt.taskid='+item.taskid));
	      tools.SetValue(mep + 'taskid',item.taskid);
	      tools.SetValue(mep + 'recordstatus', bustask.recordstatus);
	      tools.SetValue(mep + 'samplecode',item.samplecode);
	      tools.SetValue(mep + 'modifydesc', bustask.modifydesc);
	    }
	    
	    me.OnShowTask(item);
	    
	    me.OnDetailRefresh();
	    return true;
	  },
		  
	  
  OnBeforeCreateDetailEdit: function () {
	    var me = this;
	    var mep = this.tranPrefix;
	    
	    me.OnInitGridToolBar();
	    me.editDetailControls = [
	      { xtype: 'hiddenfield', name: 'attachtype', id: mep + 'attachtype' },
	      { xtype: 'hiddenfield', name: 'attachurl', id: mep + 'attachurl' },
	      { xtype: 'hiddenfield', name: 'attachname', id: mep + 'attachname' }
	    ];
	    me.disDetailControls = ['attachname'];
	    me.enDetailControls = ['attachtype', 'attachurl'];  
	  },
	  
  OnCreateDetailWin: function () {  
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    if (Ext.getCmp(mep + 'detailwin')) {
	      Ext.getCmp(mep + 'detailwin').destroy();
	    };
	    
	    var items = [
	      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me }
//		      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
	    ];
	    
	    me.OnBeforeCreateDetailEdit();
	    
	    me.plDetailEdit = Ext.create('Ext.form.Panel', {
	      id:mep + 'pldetailedit',
	      region : 'north',
//		      height : '18%',
	      columnWidth:1,
	      fieldDefaults: {
	        labelSeparator: '：',
	        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
	        labelPad: 0,
	        labelStyle: 'font-weight:bold;'
	      },
	      frame: true,
	      bodyStyle: 'padding:5px;background:#FFFFFF',
	      defaultType: 'textfield',
	      closable: false,
	      header: false,
	      unstyled: true,
	      scope: me,
	      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
	      items: me.editDetailControls    
	    });
	    
	    var upload = tools.SwfUpload();
	    me.plAttDetailGrid = Ext.create('Ext.form.Panel', {
	      id:mep + 'attdetailgrid',
	      region : 'center',
	      columnWidth:1,
	      scope: me,
	      items: [upload]    
	    });
	    
	    upload.on('showdetail',me.OnShowDetail,me);
	    upload.on('closewin',me.OnCloseWin,me);
	    
	    me.winDetail = Ext.create('Ext.Window', {
	      id: mep + 'detailwin',
	      title: me.detailTitle,
	      width: 600,
	      height: 370,
	      maximizable: false,
	      closeAction: 'hide',
	      modal: true,
	      layout: 'border',
	      plain: false,
	      closable: true,
	      draggable: true,
	      constrain: true,
	      items : [me.plDetailEdit,me.plAttDetailGrid]
	    });
	  },
	  
	  

  OnListNew : function(){
    var me = this;
    var mep = this.tranPrefix;
    var recordstatus = tools.GetValue(mep+'recordstatus');
    
    if(recordstatus=='02'||recordstatus=='04'||recordstatus=='08'){
      tools.alert('该原始记录表复核中，不能进行修改...');
      return false;
    }
    me.OnCreateDetailWin();
    if(me.winDetail){      
      me.winDetail.show();
    };
  },
	  
  
  OnCloseWin:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.winDetail.hide();
  },
    
  
  OnShowDetail:function(render, item){
	    var me = this;
	    var mep = this.tranPrefix;
	    
	    var attachname = Ext.getCmp(mep+'attachname').getValue();
	    var attachurl = Ext.getCmp(mep+'attachurl').getValue();
	    
	    if(item){
	      if(attachname == ""){
	        attachname = item.name;
	      }else{
	        attachname = attachname+','+item.name
	      };
	      if(attachurl == ""){
	        attachurl = item.url;
	      }else{
	        attachurl = attachurl+','+item.url;
	      };
	      tools.SetValue(mep + 'attachname',attachname);
	      tools.SetValue(mep + 'attachurl',attachurl);
	    };
	  },
	  
	  
  OnListSave: function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    var attachname = Ext.getCmp(mep+ 'attachname').getValue();
    var attachurl = Ext.getCmp(mep + 'attachurl').getValue();
    var attachtype = Ext.getCmp(mep + 'attachtype').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(attachname == "" || attachname == null || attachname == undefined){
        tools.alert('请上传附件！');
        return;
      }else{
        if (me.detailEditType == 1) {
          //可能有多个附件的情况
          var attachnames = attachname.split(",");
          var attachurls = attachurl.split(",");
          for(i = 0; i <attachnames.length; i++){
            var record = me.plDetailGrid.store.model.create({});
            record.data.attachname = attachnames[i];
            record.data.attachtype = '02';
            record.data.attachtypename = '多样品原始记录表';
            record.data.attachurl = attachurls[i];
            me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
          };
        }
        else {
          me.OnBeforeListSave(me.detailRecord);
          me.plDetailGrid.getView().refresh();
        };
      };
      me.winDetail.hide();
    };
  },
  
  
  OnListDelete:function(){
	    var me = this;
	    var mep = this.tranPrefix;
	    var recordstatus = tools.GetValue(mep+'recordstatus');
	    
	    if(recordstatus=='02'||recordstatus=='04'||recordstatus=='08'){
	      tools.alert('该原始记录表复核中，不能进行修改...');
	      return false;
	    }
	    var j = me.plDetailGrid.selModel.selected.items.length;
	    
	    for ( var i = 0; i < j; i++) {
	      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
	    };
	    
	    me.plDetailGrid.getView().refresh();
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
	
  OnGetSaveParams : function() {
	   var me = this;
	   var mep = me.tranPrefix;
	   
	   if (me.hasDetail) {
	     var details = [];
	     for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
	       details.push(me.plDetailGrid.store.getAt(i).data);
	     }
	     
	     me.saveParams = { details: Ext.encode(details), 
	       signerpassword: Ext.getCmp(mep + 'signer_passwd').getValue(), approveusers: Ext.getCmp(mep + 'approveuser').getValue() };
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
	        item = me.plGrid.store.getAt(i - 1).data;
	        
	        if(item.recordstatus == '01'|| item.recordstatus =='06'|| item.recordstatus =='10' ){
	          tools.BtnsEnable(['btnPass'],mep);
	        }else{
	          tools.BtnsDisable(['btnPass'],mep);
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
	        
	        if(item.recordstatus == '01'||item.recordstatus =='06'|| item.recordstatus =='10'){
//	        if(me.plGrid.store.getAt(i + 1).data.flowstatus == '84'){
	          tools.BtnsEnable(['btnPass'],mep);
	        }else{
	          tools.BtnsDisable(['btnPass'],mep);
	        };
	        
	        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
	        me.OnFormValidShow();
	        return;
	      }
	    }
	  },
	  
  OnAfterCreateDetailToolBar:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    me.deitems = [
	      ' ', { id : mep + 'btnDetailAdds', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
      ' ', { id : mep + 'btnDetailDeletes', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me }
    ];
  
  },
 

  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    me.html = alms.ShowMostRecordHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(me.html);
  },
  

  OnPrintRecord: function () {
    var me = this, mep = me.tranPrefix;
    alms.PrintRecordHtmlBySampletran(tools.GetValue(mep + 'sampletran'), alms.DEFAULT_REPORT_WIDTH);
    return;
  },
  OnAfterSave:function(action){
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_SELECT) {
        if (action.result.data.taskid) {
          var taskid = action.result.data.taskid;
          tools.SetValue(mep + 'taskid', taskid);
          var bustask = tools.JsonGet('LabGetBusTask.do?bt.taskid='+taskid);
          me.OnShowTask(bustask);
        };
      };
    };
    tools.BtnsDisable(['btnPass'],mep)
  },
  
  OnPrintTask: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!Ext.isEmpty(me.html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("样品任务单打印");
      
      if(tools.GetValue(mep+'samplecode') == '多样品样品编号-A' || tools.GetValue(mep+'samplecode') == '多样品样品编号-B'){
        LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");//2横向打印
      }else{
        LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");//1为纵向打印
      }
      
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//        LODOP.PRINT();//打印功能
    }
  }
  
  
  

  
 
  
  
});