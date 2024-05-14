Ext.define('alms.manbustodo', {
  extend: 'gpersist.base.listflowform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
        editInfo: '我的工作',
        hasColumnUrl: true,
        columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustodo',
        storeUrl: 'FlowSearchBusTodo.do',
        expUrl: 'FlowSearchBusTodo.do',
        hasPage: true,
        idPrevNext: 'tranid',
        hasDateSearch: true,
        busflow: 'devapply',
      });

    me.callParent(arguments);
  },

  OnInitGridToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;

    me.callParent();

    var nowdate = new Date();
    
    var items = [
      ' ', { xtype:'datefield', fieldLabel:gpersist.STR_BTN_BEGINDATE, labelWidth:60, width:160, name:'searchbegindate', id:mep + 'searchbegindate',
        format:'Y-m-d', value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1), selectOnFocus: false, allowBlank: true},
      ' ', {xtype:'datefield', fieldLabel:gpersist.STR_BTN_ENDDATE, labelWidth:60, width:160, name:'searchenddate', id:mep + 'searchenddate',
        format:'Y-m-d', value:nowdate, selectOnFocus:false, allowBlank:true},
//      ' ', alms.BarComboLabProject('searchlabid', mep + 'searchlabid', 300, lims.LAB_PROJECT, 60, gpersist.SELECT_ALL_VALUE),
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
    ];
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });
    
    me.tbGrid.add(toolbar);
    
//    Ext.getCmp(mep + 'searchlabid').on('select', me.OnSelectLab, me);
  },
  
//  OnSelectLab: function () {
//    var me = this, mep = me.tranPrefix;
//    
//    var lab = tools.GetValue(mep + 'searchlabid');
//    
//    if (!Ext.isEmpty(lab) && lab != gpersist.SELECT_NULL_VALUE){
//      me.OnSearch();
//    }
//  },
  
  OnGetSearchParam: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    return {
      'todo.search.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '',
      'todo.search.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : '',
      'todo.tranid': tools.GetValue(mep + 'searchtranid'),
      'todo.labid': tools.GetValue(mep + 'searchlabid'),
      'todo.todotype': '1'
    };
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    if (me.hasDateSearch)
      Ext.Array.insert(items, 2, [
        ' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
        ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }
      ]);
        
    if (me.hasPage && me.hasPageSize)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    var date = new Date();
    var mainpanel = Ext.getCmp('tpanel' + me.mid);
    
    if (!Ext.isDefined(mainpanel))
      return;
    
    mainpanel.removeAll();
    
    // 生成列表的字段属性
    me.columns = [];
    me.fields = []; 
    tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_'); 
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields); 
    
    // 加载主页面前
    me.OnBeforeFormLoad();
    
    // 生成列表
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id : mep + 'grid',
      region : 'center',
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : me.columns,
      store : me.gridStore,
      tbar : me.tbGrid,
      enableColumnMove: false, 
      enableColumnHide: false,
      suspendLayout: false,
      listeners : {
        'itemdblclick' : { fn : me.OnDeal, scope : me }
      }
    });
    
    // 分页处理
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store : me.gridStore,
        displayInfo : true,
        displayMsg : gpersist.STR_PAGE_FMT,
        emptyMsg : gpersist.STR_NO_DATA, suspendLayout: true,
        dock : 'bottom'
      }));
    }

    // 主界面生成后处理
    me.OnAfterFormLoad();

    // 建立编辑界面
    me.OnCreateEditWin();

    var pleditview = Ext.create('Ext.Panel', {
      frame : false,
      autoScroll : false,
      region : 'center',
      border : false,
      layout : 'border',
      margins : '0 0 0 0',
      padding : '0 0 0 0'
    });
    
    if (me.hasEdit)
      pleditview.add(me.plEdit);
      
    if (me.hasDetail) {
      if (me.plDetail && !me.hasEdit)
        me.plDetail.region = 'center';
        
      pleditview.add(me.plDetail);
    }
    
    me.tabMain = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : me.deferredRender,
      items :
        [me.plGrid, pleditview]
    });    
        
    me.tabMain.getTabBar().setVisible(false);

    mainpanel.add(me.tabMain);
    
    if (me.hasAutoLoad)
      me.OnSearch();
  },  
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
  
    me.editControls = [
      {xtype: 'label', id: mep + 'flowinfo', html: ''},
      {xtype: 'label', id: mep + 'businfo', html: ''},
      {xtype: 'label', id: mep + 'busfile', html: ''},
      {xtype: 'hiddenfield', name: 'todo.tranid', id: mep + 'tranid'},
      {xtype: 'hiddenfield', name: 'todo.busflow', id: mep + 'busflow'},
      {xtype: 'hiddenfield', name: 'todo.flownode', id: mep + 'flownode'},
      {xtype: 'hiddenfield', name: 'todo.todoserial', id: mep + 'todoserial'}
    ];
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      autoScroll: true,
      region : 'center',
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
    
    me.OnAfterCreateEdit();
    
  },

  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix;
    
    me.busflow = record.busflow;
    me.busflownode = record.flownode;
    
    me.flownode = tools.JsonGet('FlowGetFlowNode.do?fn.busflow=' + me.busflow + '&fn.flownode=' + me.busflownode);
    
    var flow = tools.JsonGet('FlowGetBusFlow.do?flow.busflow=' + me.busflow);
    
    var buttons = tools.JsonGet('FlowGetListFlowNodeButton.do?nodebutton.busflow=' + me.busflow + '&nodebutton.flownode=' + me.busflownode).data;
    
    if ((me.flownode == null) || Ext.isEmpty(me.flownode.busflow)) {
      tools.alert('错误的流程配置');
      return false;
    }
    
    me.editToolItems = [];
    
    var btn = {};
    
    for(var i=0;i<buttons.length;i++){
      var button = buttons[i];
      btn = Ext.create('Ext.Button', {
           id : mep + button.nodebutton, text : button.btntitle, iconCls : 'icon-deal', handler : me.OnSubmit, scope : me,button:button });
         
      me.editToolItems.push(btn);
    }
    
    me.editToolItems.push('-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me });
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
    
    if(Ext.getCmp('toolbar')){
      Ext.getCmp('toolbar').removeAll();
    }
    
//    if(flow.htmldatafunction=='StdHtmlStdReview'||flow.htmldatafunction=='StdHtmlStdReplRecord'){
//    	me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFileTWO, scope : me });
//    	
//    }
    if(flow.htmldatafunction=='StdHtmlStdReplRecord'){
    	me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFileTWO, scope : me });
    	
    }
    if(flow.htmldatafunction=='StdHtmlTestSure'){
    	me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewTestSure, scope : me });
    	
    }
    
    if (flow.htmldatafunction=='PrdHtmlPrdVerify') {
    	if (gpersist.UserInfo.user.deptname.indexOf("检验") != -1) {
    		me.editToolItems.push('-',' ', { id: mep + 'btnDetailUpload', text: '上传附件', iconCls: 'icon-deal', handler: me.OnListNew, scope: me });
        
		} else {
			
			me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '下载附件', iconCls : 'icon-outlook', handler : me.OnDownZip, scope : me });
//			var sample = Ext.create('Ext.ux.GridPicker', {
//			      fieldLabel: '附件查看', winTitle: '选择附件',
//			      maxLength: 20, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
//			      blankText: '样品编号不能为空！', allowBlank: true, anchor: '100%', tabIndex: null,
//			      columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowfileattach',
//			      storeUrl: 'LabGetListBusTaskAttach.do?bta.attachtype=05&bta.tranid='+tools.GetEncode(tools.GetEncode(id)),
//			      editable:false,
//			      hasPage: true, pickerWidth: 600, pickerHeight: 500
//			    });uo
//			
//			 me.editToolItems.push(sample);
//			    sample.on('griditemclick', me.OnViewFile, me);
//	    	    
		}
	} 
    if (flow.htmldatafunction=='StdHtmlBusCountry') {
    	if (gpersist.UserInfo.user.deptname.indexOf("检验") != -1) {
    		me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看任务单', iconCls : 'icon-outlook', handler : me.OnViewFileTWO, scope : me });
        	
    		me.editToolItems.push('-',' ', { id: mep + 'btnDetailUpload', text: '上传检测结果', iconCls: 'icon-deal', handler: me.OnListNew, scope: me });
    		
    	} else {
//			me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFile, scope : me });
    		var sample = Ext.create('Ext.ux.GridPicker', {
    			fieldLabel: '  ', winTitle: '选择附件',
    			maxLength: 20, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
    			blankText: '样品编号不能为空！', allowBlank: true, anchor: '100%', tabIndex: null,
    			columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowfileattach',
    			storeUrl: 'LabGetListBusTaskAttach.do?bta.attachtype=05&bta.tranid='+tools.GetEncode(tools.GetEncode(id)),
    			editable:false,
    			hasPage: true, pickerWidth: 600, pickerHeight: 500
    		});
    		
    		me.editToolItems.push(sample);
    		sample.on('griditemclick', me.OnViewFile, me);
    		
    	}
    }
    
    if (flow.htmldatafunction=='StdHtmlStdReview') {
//    	var buttons = tools.JsonGet('FlowGetListFlowNodeButton.do?nodebutton.busflow=' + me.busflow + '&nodebutton.flownode=' + me.busflownode).data;
    	if ((gpersist.UserInfo.user.deptname.indexOf("检验") != -1)&&(gpersist.UserInfo.user.userid!= "812")) {
    		me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看标准变更表', iconCls : 'icon-outlook', handler : me.OnViewFileTWO, scope : me });
    		
    		me.editToolItems.push('-',' ', { id: mep + 'btnDetailUpload', text: '上传标准变更确认结果', iconCls: 'icon-deal', handler: me.OnListNew, scope: me });
    		
    	} else {
//			me.editToolItems.push('-', ' ', { id : mep + 'btnview', text : '查看附件', iconCls : 'icon-outlook', handler : me.OnViewFile, scope : me });
    		var sample = Ext.create('Ext.ux.GridPicker', {
    			fieldLabel: '附件查看', winTitle: '选择附件',
    			maxLength: 20, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
    			blankText: '样品编号不能为空！', allowBlank: true, anchor: '100%', tabIndex: null,
    			columnUrl: 'SysSqlColumn.do?sqlid=p_get_flowfileattach',
    			storeUrl: 'LabGetListBusTaskAttach.do?bta.attachtype=05&bta.tranid='+tools.GetEncode(tools.GetEncode(id)),
    			editable:false,
    			hasPage: true, pickerWidth: 600, pickerHeight: 500
    		});
    		me.editToolItems.push(sample);
    		sample.on('griditemclick', me.OnViewFile, me);
    		
    	}
    } 
    
    var tbedit = Ext.create('Ext.toolbar.Toolbar', {
        id : 'toolbar',
        dock : 'top',
        items : me.editToolItems
    });
    
    me.plEdit.insertDocked(0,tbedit);
    
    tools.SetValue(mep + 'tranid', id);
    tools.SetValue(mep + 'busflow', me.busflow);
    tools.SetValue(mep + 'flownode', me.busflownode);
    tools.SetValue(mep + 'todoserial', record.todoserial);

    var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=' + me.busflow + '&todo.tranid=' + tools.GetEncode(tools.GetEncode(id)));

    flowinfo = flowinfo || '';    
    Ext.fly(Ext.getCmp(mep + 'flowinfo').getEl()).update(flowinfo + '<br />');
    //alert(flow.htmldatafunction )
    var businfo = tools.HtmlGet(flow.htmldatafunction + '.do?tranid=' + tools.GetEncode(tools.GetEncode(id)));
    businfo = businfo || '';
    Ext.fly(Ext.getCmp(mep + 'businfo').getEl()).update(businfo);
    
    viewFile = function (fileurl,filename) {
       alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
    }
    
    return true;  
  },
  OnBeforeListSave : function() {
	    
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
//	      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
	    ];
	    
	    me.OnBeforeCreateDetailEdit();
	    
	    me.plDetailEdit = Ext.create('Ext.form.Panel', {
	      id:mep + 'pldetailedit',
	      region : 'north',
//	      height : '18%',
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
		    console.log(attachname+"attachnameattachnameattachnameattachname")
		     
		    console.log(tools.GetEncode(attachname)+"tools.GetEncode(attachname)tools.GetEncode(attachname)tools.GetEncode(attachname)")
		    var attachurl = Ext.getCmp(mep + 'attachurl').getValue();
		    var attachtype = Ext.getCmp(mep + 'attachtype').getValue();
		    var tranid = Ext.getCmp(mep + 'tranid').getValue();
		    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
		      if(attachname == "" || attachname == null || attachname == undefined){
		        tools.alert('请上传附件！');
		        return;
		      }else{
		        
		          //可能有多个附件的情况
		          var attachnames = attachname.split(",");
		          var attachurls = attachurl.split(",");
		          for(i = 0; i <attachnames.length; i++){
		            
		        	attachname = attachnames[i];
		            attachurl = attachurls[i];
		            var item = tools.JsonGet(tools.GetUrl('FlowSaveFileFlow.do?attachname=') + attachname +
		    	    		'&attachurl=' +attachurl+ '&attachtype=' +attachtype+ '&tranid=' +tranid);
		          };
		        
		      };
		      tools.alert('上传成功！');
		      me.winDetail.hide();
		    };
		  },
  
  
  OnSubmit: function (value) {
    var me = this, mep = me.tranPrefix;
   
    me.button  = value.button;
    
    if (!Ext.isEmpty(me.button.btnmsg)) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, me.button.btnmsg, function(btn) {
        	
    	  if (btn == 'yes') {
//          	if(value.scope.cancelRecord.busflow=="StdReview"){
//          		me.OnListNew();
//          	}
//          	console.log(value.scope.cancelRecord)
        	
          if (me.button.isenter) {
            if (!me.winPrompt)
              me.OnCreatePrompt();
              
            if(me.winPrompt) {       
              
              me.winPrompt.setTitle(me.button.btntitle + '信息');
              var tododesc = Ext.getCmp(mep + 'tododesc');
              if (tododesc) {
                tododesc.setFieldLabel(me.button.btntitle + '说明');
                tododesc.reset();
              }
              
              me.winPrompt.show();
            }
          }else    
            me.OnSave(false,me.button);
        }
      });
    }else    
     me.OnSave(false,me.button);
  },
  
  OnDownZip:function(render,sample){
		    var me = this, mep = me.tranPrefix;
		    var busfilesss = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data;
			   
		    var fileurls = [];
		    
		    for (i = 0; i < busfilesss.length; i++) {
		    	var str = busfilesss[i].attachurl.split(".");
		      fileurls.push(busfilesss[i].attachname +"."+str[str.length-1] +':' +busfilesss[i].attachurl);
		    }
		    
		    if (fileurls.length <= 0) {
		      tools.alert('没有需要下载的文件！');
		      return;      
		    }
		    var filename = me.cancelRecord.busflowname+ "附件";
		    var iframe = document.getElementById('export');
		    var plExport = Ext.getCmp('plexport');
		    
		    plExport.form.submit({
		      url: 'FileDownZipFile.do',
		      params: { filename: filename, fileurl: fileurls.join() },
		      target: 'export'
		    });
  },
		    OnViewFile:function(render,sample){
	    var me = this, mep = me.mep;
	    if(!Ext.isEmpty(sample)){
	    	var str = sample.attachurl.split(".");
	        alms.PopupFileShow(sample.attachtypename+'附件预览', 'FileDownFile.do', sample.attachurl,sample.attachname+"."+str[str.length-1]);
	      	
	    }else{
	    	tools.alert('没有附件');
	        return false;
	    }
	  },
	  OnViewFileTWO:function(){
		  var me = this, mep = me.tranPrefix;
		  
		  var busfilesss = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data[0];
		    	if(!Ext.isEmpty(busfilesss)){
			    	var str = busfilesss.attachurl.split(".");
			        alms.PopupFileShow(busfilesss.attachtypename+'附件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname+"."+str[str.length-1]);
			      	
			    }else{
			    	tools.alert('没有附件');
			        return false;
			    }
	  },
	  OnViewTestSure:function(){
		  var me = this, mep = me.tranPrefix;
		  
		  var busfilesss = tools.JsonGet('StdGetListStdSureDetail.do?ssd.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data[0];
		  if(!Ext.isEmpty(busfilesss)){
//			  var str = busfilesss.attachurl.split(".");
			  alms.PopupFileShow(busfilesss.filecatename+'附件预览', 'FileDownFile.do', busfilesss.attachfileurl,busfilesss.attachfilename);
			  
		  }else{
			  tools.alert('没有附件');
			  return false;
		  }
	  },
//  OnViewFile: function () {
//	    var me = this, mep = me.tranPrefix;
//	    var me = this;
//	    me.getdetail= tools.GetPopupWindow('alms', 'editshowflowfile', 
//	      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'gd', dataDeal: me.dataDeal})
//	    me.getdetail.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
	  
	  
//	     var busfiles = tools.JsonGet('LabGetListBusTaskAttach.do?bta.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue()))).data;
//	    console.log(busfiles)
//	    for ( var i = 0; i < busfiles.length; i++) {
//	    	tools.alert('共有'+busfiles.length+'个附件<br/>您正在查看第'+i+'个文件');
//	    	
//	    	var busfilesss=busfiles[i]
//	    	if(!Ext.isEmpty(busfilesss)){
//		    	var str = busfilesss.attachurl.split(".");
//		        alms.PopupFileShow(busfilesss.attachtypename+'附件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname+"."+str[str.length-1]);
//		      	
////		     alms.PopupFileShow('文件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname);
//		    }else{
//		    	tools.alert('没有附件');
//		        return false;
//		    }
//	      }
//	  },
  OnSaveInfo: function () {
	  var me = this, mep = me.tranPrefix;
	  
	  var plPrompt = Ext.getCmp(mep + 'plprompt'); 
	  
	  if (plPrompt && tools.InvalidForm(plPrompt.getForm())) {
		  Ext.apply(me.saveParams, { 'todo.tododesc': tools.GetValue(mep + 'tododesc') });
		  me.OnSave(true,me.button);
	  }
  },
  
  OnSave : function(haswin,button) {
    var me = this;
    var mep = me.tranPrefix;
    var tododesc = '';
    if(Ext.getCmp(mep + 'tododesc')){
      tododesc = Ext.getCmp(mep + 'tododesc').getValue();
    }

    Ext.apply(me.saveParams, { 
      'todo.busflow': me.busflow,
      'todo.flownode': me.busflownode,
      'todo.tranid': Ext.getCmp(mep + 'tranid').getValue(),
      'todo.todoserial': Ext.getCmp(mep + 'todoserial').getValue(),
      'todo.tododesc': tododesc,
      'todo.todostatusdesc':button.todostatusdesc
    });
    alert(button.btnaction);
    Ext.Ajax.request({
      url : tools.GetUrl(button.btnaction + '.do'),
      params : me.saveParams,
      async : false,
      method : 'POST',
      success : function(response, opts) {
        tools.ResponseAlert(response, function() {
          me.OnSearch();
         // alert(me.busflow)
          var flowinfo = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=' + me.busflow + '&todo.tranid=' + tools.GetEncode(tools.GetEncode(Ext.getCmp(mep + 'tranid').getValue())));
          flowinfo = flowinfo || '';    
          Ext.fly(Ext.getCmp(mep + 'flowinfo').getEl()).update(flowinfo + '<br />');
         
          if (haswin)
            me.winPrompt.hide();
            
        });
      },
      failure : function(response) {
        tools.ResponseAlert(response);
      }
    });
  },
  
  OnCreatePrompt: function () {
    var me = this, mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'promptwin')) {
      Ext.getCmp(mep + 'promptwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnSave', text: '提交', iconCls: 'icon-save', handler: me.OnSaveInfo, scope: me },
      '-', ' ', { text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winPrompt.hide(); } }
    ];
    
    var plPrompt = Ext.create('Ext.form.Panel', {
      id:mep + 'plprompt',
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
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items, border: false}),
      items: [tools.FormTextArea( '说明', 
        'todo.tododesc', mep + 'tododesc', 200, '100%', true, 101, 10)]    
    });
    
    me.winPrompt = Ext.create('Ext.Window', {
      id: mep + 'promptwin',
      title: '信息',
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
      items : [plPrompt]
    });
  }

});