Ext.define('alms.auditbusrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '原始记录表审核',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busrecord',
      storeUrl: 'DatSearchBusRecordAudit.do',
      saveUrl: 'DatAuditBusRecord.do',
      expUrl: 'DatSearchBusRecordAudit.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusTaskAttach.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '图谱明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'sampletran',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      detailTabs: 2
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
      '-', { id: mep + 'btnEdit', text: '审核', iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
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
          'br.recordstatus': tools.GetEncode(tools.GetValue(mep + 'searchrecordstatus')),
          'br.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode'))
        });
      });
    };
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      {xtype: 'label', id: mep + 'busrecordhtml', html: '' },
      {xtype:'hiddenfield',name:'br.recordstatus',id: mep + 'recordstatus'},
      {xtype:'hiddenfield',name:'br.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'br.modifydesc',id: mep + 'modifydesc'},
      {xtype:'hiddenfield',name:'br.samplecode',id: mep + 'samplecode'},
      {xtype:'hiddenfield',name:'br.sampletran',id: mep + 'sampletran'},
      {xtype:'hiddenfield',name:'br.deal.action',id: mep + 'datadeal'}
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
  
//  OnAfterCreateEdit:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    me.plEdit.region = 'center';
//  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '75%';
  },
  
  OnLoadData: function (record) {
    var me = this, mep = me.tranPrefix;

    me.cancelRecord = record;
    tools.SetValue(mep + 'samplecode', record.samplecode);
    tools.SetValue(mep + 'sampletran', record.sampletran);
    tools.SetValue(mep + 'taskid', record.taskid);

    var records = tools.JsonGet(tools.GetUrl('DatGetBusRecordBySampleTran.do?br.sampletran='+record.sampletran)).data;

    if(records){
      if(records.length>0){
        
        var form = '';
        
        for(var k=0;k<records.length;k++){
          var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecord.do?br.recordid=') + records[k].recordid);
          tools.SetValue(mep + 'recordstatus', records[0].recordstatus);

          var nowdate = new Date();
          if (item && item.record) {
            tools.log(item.record.recordid, nowdate);
            
            var i = 0, j = 0;
            var record;
            var rowspan = 0, colspan = 0;
            var height = 28, width = 36, nowheight = 0, nowwidth = 0;
            
            var celltext = '';
            var style = '';
            var align = '';
            
            tools.log(item.details.length, nowdate);
            
            for (var page = 0; page < item.details.length; page++) {
              var nowdetails = item.details[page].datas;
              
              tools.log(nowdetails.length, nowdate);
              
              if (i == item.details.length - 1)
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              else
                form += '<table align = "center" width="90%" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
              
              form += '<tr>';
              
              for (i = 0; i < item.form.formwidth; i++) {
                //form += '<td width="' + width + 'px"; height="' + height + 'px" style="border: solid 1px Black;">' + (i + 1) + '</td>';
                form += '<td width="' + width + 'px"; height="1px" ></td>';
              }
              form += '</tr>';
              
              for (i = 1; i <= item.form.formlength; i++) {
                form += '<tr><td width="' + width + 'px"; height="' + height + 'px"></td>';
                
                for (j = 0; j < nowdetails.length; j++) {
                  record = nowdetails[j];
                  
                  nowheight = 0;
                  nowwidth = 0;
                  if (record.beginrow == i) {
                    rowspan = record.endrow - record.beginrow + 1;
                    nowheight = height * rowspan;
                    
                    colspan = record.endcolumn - record.begincolumn + 1;
                    nowwidth = width * colspan;
                    
                    celltext = '';
                    switch (record.valuesource) {
                      case '02':
                        celltext = record.celltext;
                        break;
                        
                      case '03':
                        celltext = '';
                        break;
                        
                      case '01':
                        celltext = record.cellname.replace(new RegExp("br","gm"),"<br/>");
                        break;
                        
                      case '04':
                        celltext = record.celltext;
                        break;
                        
                      default:
                        break;
                    }
                    
                    celltext = record.prefixtext + celltext + record.postfixtext;
                    
                    style = '';
                    align = '';
                    
                    switch (record.aligntype) {
                      case '1':
                        align = ' align="left" ';
                        style += 'padding-left:3px;';
                        break;
                        
                      case '2':
                        align = ' align="center" ';
                        break;
                        
                      case '3':
                        align = ' align="right" ';
                        style += 'padding-right:3px;';
                        break;
                        
                      default:
                        break;
                    }
                    
                    if (record.isborder > 0)
                      style += 'border: solid ' + record.isborder + 'px Black;';
                    
                    if (record.isline && (record.isborder <= 0))
                      style += 'border-bottom: solid 1px Black;';
                    
                    if (record.isbold)
                      style += 'font-weight:bold;';
                    
                    style += 'font-size:' + record.fontsize + 'px;';
                    
                    cellhtml= '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' +
                      ' style="' + style + '" ' + 
                      (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + 
                      (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + 
                      align + ' valign="middle">' + 
                      celltext + '</td>';
                    
                    if (record.valuesource == '04') {
                      
                      if (record.fieldcode == '{actreportid}') {
                        
                        if (!Ext.isEmpty(celltext)) 
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                            + ' valign="middle">'
                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' 
                            + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                         else
                           form += cellhtml;
                      }
                      else if (Ext.isEmpty(record.classsource)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                            + ' valign="middle">'
                            + '<image width="'  + nowwidth + 'px" height="' + nowheight + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      }
                      else if (!Ext.isEmpty(celltext)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" '
                            + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                            + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                            + ' valign="left">'
                            + '<image height="' + nowheight + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                      }
                      
                      //公式变成图片
                      else if (record.classsource == 'formula') {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px"' + ' style="border: solid 1px Black;" ' 
                        + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ')
                        + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align
                        + ' valign="middle">'
                        + '<image height="170px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      }
                      else
                        form += cellhtml;
                    }
                    else {
                      form += cellhtml;
                    }
                  } 
                }
                
                form += '</tr>';
              }
              
              form += '</table><br />';
            }
            
            
          }
          else
            tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
        }
        
        me.disphtml = form.replace(/\<br \/\>/g, '');
        
        var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
          '<tr><td align="center">' + form + '</td></tr></table>';
        
        Ext.fly(Ext.getCmp(mep + 'busrecordhtml').getEl()).update(html);
        
        me.OnDetailRefresh();
          
        return true;
      }
    }
  },
  
  OnPrintRecord: function () {
    var me = this;
    
    if (!Ext.isEmpty(me.disphtml)) {
      Ext.ux.Print.ReportPrint.print(me.disphtml);
    }
  },
  
  OnListSelect: function (view, record) {
    alms.PopupFileShow(record.data.attachtypename+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + tools.GetValue(mep + 'sampletran');
      me.plDetailGrid.store.load();
    };
    
    if (me.fileGrid && me.fileGrid.store) {
      me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusRecordFile.do?brf.tranid=') + tools.GetValue(mep + 'sampletran');
      me.fileGrid.store.load();
   }
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    
    //去除明细中新增、删除按钮
    me.deitems = [];
  },
  
  OnAfterCreateDetail: function () {
    var me = this, mep = this.tranPrefix;
    
    var fileColumn = [];
    var fileField = [];    

    tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_bustaskattach'), fileColumn, fileField, mep + '_recordfile_');

    me.fileStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusRecordFile.do'), fileField);
    
    me.fileGrid = Ext.create('Ext.grid.Panel', {
      region : 'center',
      title : '原始记录表',
      autoScroll : true,
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
      columns : fileColumn,
      store : me.fileStore,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListSelect, scope : me }
      }    
    });
    me.fileStore.load();
    me.plDetail.add(me.fileGrid);
    
    me.OnAfterCreateDetailToolBar();
  },
  
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record, item;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == 0) {
          tools.alert('已经是当前列表第一条数据！');
          return;
        }

        me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
        item = me.plGrid.store.getAt(i - 1).data;
        
        if(item.recordstatus =='04' ){
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
        
        if(item.recordstatus =='04'){
          tools.BtnsEnable(['btnPass','btnBack'],mep);
        }else{
          tools.BtnsDisable(['btnPass','btnBack'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  }
  
});
