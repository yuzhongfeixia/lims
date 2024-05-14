Ext.define('alms.bustaskacc', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '任务单接单',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustask',
      storeUrl: 'LabSearchBusTask.do',
      saveUrl: 'LabSaveBusTaskAcc.do',
      saveListUrl: 'LabSaveListBusTaskAcc.do',
      expUrl: 'LabSearchBusTask.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'taskid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var nowdate = new Date();
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '任务单编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtaskid', id: mep + 'searchtaskid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      '-', { xtype:'datefield',fieldLabel:'完成开始日期',labelWidth:90,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '', {xtype:'datefield',fieldLabel:'完成结束日期',labelWidth:90,width:200,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, +1),selectOnFocus:false,allowBlank:true}
    ];
    
    var items1 = [
        '-', tools.GetToolBarCombo('searchlab', mep + 'searchlab', 180, '检测室', 70, tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE)), 
        '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        ' ', { id: mep + 'btnAudit', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnShow, scope: me },
        '-', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
          id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
          ' ', { id : mep + 'btnListSave', text : '批量接单', iconCls : 'icon-save', handler :me.OnListSubmit, scope : me },
          ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
      ];
     
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'bustaskhtml', html: '' },
      {xtype:'hiddenfield',name:'bt.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'samplecode',id: mep + 'samplecode'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnPass', text :'接单', iconCls : 'icon-deal', handler : me.OnSubmit, scope : me },
      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var record = tools.OnGridLoad(me.plGrid);
    if(record.flowstatus == '84'){
      tools.BtnsEnable(['btnPass'],mep);
    }else{
      tools.BtnsDisable(['btnPass'],mep);
    };
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
//          'bt.flowstatus': '84',
          'bt.labid': gpersist.GetUserInfo().dept.deptid,
          'bt.taskid': tools.GetEncode(tools.GetValue(mep + 'searchtaskid')),
          'bt.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'bt.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bt.labid': tools.GetEncode(tools.GetValue(mep + 'searchlab')),
          'bt.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'bt.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d')
        });
      });
    };
  },
  
  OnShowTask:function(record){
    var me = this;
    var mep = me.tranPrefix;  
    
    me.html = alms.ShowTaskHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'bustaskhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var accsamplestats = tools.JsonGet('LabGetBusAccSample.do?bas.tranid='+item.accsampleid).flowstatus;
    if(!Ext.isEmpty(item.taskid)) {
      tools.SetValue(mep + 'taskid',item.taskid);
      tools.SetValue(mep + 'samplecode',item.samplecode);
    }
    
    if(accsamplestats == '89'||item.flowstatus=='89'){
	      tools.alert('撤销原因:<br/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp'+item.modifydesc);
	      var deletealert = tools.JsonGet('LabDeleteTaskAlert.do?bt.taskid='+item.taskid);
	      return false;
	    }
    me.OnShowTask(item);
    me.OnDetailRefresh();
    return true;
  },
  
//  OnListSelect: function (view, record) {
//    alms.PopupFileShow('任务单附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
//  },
  
//  OnDetailRefresh : function() {
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (me.plDetailGrid && me.plDetailGrid.store) {
//      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskFile.do?btf.taskid=') + tools.GetValue(mep + 'taskid');
//      me.plDetailGrid.store.load();
//    };
//  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '100%';
  },
  
  OnSubmit : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
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
          me.OnAfterSave(action);
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnSearch();
          me.OnFormValidShow();
          me.OnAfterSubmit();
          tools.alert(Ext.String.format('接单成功！', me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
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
      //多样品
      if(tools.GetValue(mep+'samplecode') == '多样品样品编号-A' || tools.GetValue(mep+'samplecode') == '多样品样品编号-B'){
        var LODOP = getLodop();
        LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
        LODOP.PRINT_INIT("样品任务单打印");
        LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");//2横向打印
        LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
        LODOP.SET_PRINTER_INDEXA(-1);
        LODOP.PREVIEW();//预览功能
//        LODOP.PRINT();//打印功能
      }
      
      //单样品
      else{
        Ext.ux.Print.LimsPrint.print(me.html);
      }
    }
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.deitems = [];
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
        
        if(me.plGrid.store.getAt(i - 1).data.flowstatus == '84'){
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
        
        if(me.plGrid.store.getAt(i + 1).data.flowstatus == '84'){
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
  OnListSubmit : function() {
	    var me = this;
	    var mep = me.tranPrefix;
	    var  tasklist=[];
	    var datas = me.plGrid.getView().getSelectionModel().getSelection();
	    for (var i = 0; i < datas.length; i++) {
	    	var flowstatus = me.plGrid.selModel.selected.items[i].data.flowstatus;
	    	console.log(me.plGrid.selModel.selected.items[i].data)
	    	if(!(flowstatus == "84")){
	    		tools.alert("您选择的任务单含有已接收任务单.....");
	  	      return ;
	  	    }
	    	
	    	 var param={taskid: me.plGrid.selModel.selected.items[i].data.taskid,
	    			 samplecode:me.plGrid.selModel.selected.items[i].data.samplecode};
	    	 tasklist.push(param);
	    }
	    	 me.plEdit.form.submit({
	 	        clientValidation : false,
	 	        url : tools.GetUrl(me.saveListUrl),
	 	        params : {bstlist:Ext.encode(tasklist)},
	 	        async : false,
	 	        method : 'POST',
	 	        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
	 	        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
	 	        timeout : 3000,
	 	        success : function(form, action) {
	 	        	me.OnResetForm();
	 	          tools.alert(Ext.String.format('接单成功！', me.editInfo));
	 	        },
	 	        failure : function(form, action) {
	 	          tools.ErrorAlert(action);
	 	        }
	 	      });
	  }
  
});
