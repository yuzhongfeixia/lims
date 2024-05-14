Ext.define('alms.manbusrecord', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '原始记录表管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busrecord',
      storeUrl: 'DatSearchBusRecordForSampleTran.do',
      expUrl: 'DatSearchBusRecordForSampleTran.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskattach',
      urlDetail: 'LabGetListBusTaskAttach.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '图谱明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'taskid',
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
      { xtype: 'label', id: mep + 'busrecordhtml', html: '' },
      {xtype:'hiddenfield',name:'br.sampletran',id: mep + 'sampletran'},
      {xtype:'hiddenfield',name:'br.samplecode',id: mep + 'samplecode'},
      {xtype:'hiddenfield',name:'br.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'br.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'br.recordid',id: mep + 'recordid'},
      {xtype:'hiddenfield',name:'br.modifydesc',id: mep + 'modifydesc'},
      {xtype:'hiddenfield',name:'groupcount',id: mep + 'groupcount'},
      {xtype:'hiddenfield',name:'groupfinish',id: mep + 'groupfinish'},
      {xtype:'hiddenfield',name:'br.recordstatus',id: mep + 'recordstatus'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnRecordSubmit', text: '制表签名', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      '-', ' ', { id : mep + 'btnBack', text : '查看驳回意见', iconCls : 'icon-outlook', handler : me.OnBack, scope : me },
      ' ', { id: mep + 'btnPrintRecord1', text: '打印原始记录表(纵向)', iconCls: 'icon-print', handler: me.OnPrintRecord1, scope: me },
      ' ', { id: mep + 'btnPrintRecord2', text: '打印原始记录表(横向)', iconCls: 'icon-print', handler: me.OnPrintRecord2, scope: me },
      '-', ' ', { id : mep + 'btnPrevRecords', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecords', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
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
  
  OnCommit: function(){
    var me = this, mep = me.tranPrefix;
    
    var groupcount = tools.GetValue(mep + 'groupcount');
    var groupfinish = tools.GetValue(mep + 'groupfinish');
    
    if (!me.OnBeforeSubmit())
      return;
    
    me.OnGetSaveParams();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl('DatSubmitBusRecord.do'),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
//          me.OnAfterCommit(action);
          me.OnSearch();
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          tools.alert(Ext.String.format("确认签字完成！", me.editInfo));
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
//       ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetail.hide(); me.detailEditType = 1;}}
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
  
  OnLoadData: function (record) {
    var me = this, mep = me.tranPrefix;
    
    me.cancelRecord = record;
    tools.SetValue(mep + 'recordid', record.recordid);
    tools.SetValue(mep + 'taskid', record.taskid);
    tools.SetValue(mep + 'tranid', record.tranid);
    tools.SetValue(mep + 'sampletran', record.sampletran);
    tools.SetValue(mep + 'groupcount', record.groupcount);
    tools.SetValue(mep + 'groupfinish', record.groupfinish);
    tools.SetValue(mep + 'samplecode', record.samplecode);
    tools.SetValue(mep + 'modifydesc', record.modifydesc);

    var records = tools.JsonGet(tools.GetUrl('DatGetBusRecordBySampleTran.do?br.sampletran='+record.sampletran)).data;

    if(records){
      if(records.length>0){
        
        var form = '';
        
        for(var k=0;k<records.length;k++){
//          var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecordForSum.do?br.recordid=') + records[k].recordid);
          var item = tools.JsonGet(tools.GetUrl('DatGetSetBusRecord.do?br.recordid=') + records[k].recordid);
          tools.SetValue(mep + 'recordstatus', records[0].recordstatus);

          var nowdate = new Date();
          if (item && item.record) {
            
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
              
              form += '</table><br/>';
            }
            
          
          }
          else
            tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
        }
      
        me.disphtml = form.replace(/\<br \/\>/g, '');
        
        var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
          '<tr><td align="center">' + form + '</td></tr></table>';
//        alert('html')
        console.log(html)
        Ext.fly(Ext.getCmp(mep + 'busrecordhtml').getEl()).update(html);
        
        me.OnDetailRefresh();
          
        return true;
      }
    }
  },
  
  OnPrintRecord1: function () {
    var me = this, mep = me.tranPrefix;
    alms.PrintRecordHtmlBySampletran(tools.GetValue(mep + 'sampletran'), alms.DEFAULT_REPORT_WIDTH,1);
    return;
  },
  
  OnPrintRecord2: function () {
    var me = this, mep = me.tranPrefix;
    alms.PrintRecordHtmlBySampletran(tools.GetValue(mep + 'sampletran'), alms.DEFAULT_REPORT_WIDTH,2);
    return;
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
//      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
//      height : '18%',
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
            record.data.attachtype = '01';
            record.data.attachtypename = '图谱';
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
        'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
      }    
    });
    me.fileStore.load();
    me.plDetail.add(me.fileGrid);
    me.fileitemstar = [
      ' ', { id : mep + 'btnFileAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewFile, scope : me },
      ' ', { id : mep + 'btnFileDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteFile, scope : me }
    ];
    
    tools.SetToolbar(me.fileitemstar, mep);
      
    var tbfile = Ext.create('Ext.toolbar.Toolbar', {
      dock : 'top',
      items : me.fileitemstar
    });
    me.fileGrid.insertDocked(0, tbfile);
},
  
  OnBeforeCreateDetailEditFile: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.OnInitGridToolBar();
    me.editDetailControlsFile = [
      { xtype: 'hiddenfield', name: 'attachtype', id: mep + 'attachtypefile' },
      { xtype: 'hiddenfield', name: 'attachurl', id: mep + 'attachurlfile' },
      { xtype: 'hiddenfield', name: 'attachname', id: mep + 'attachnamefile' }
    ];
    me.disDetailControlsFile = ['attachnamefile'];
    me.enDetailControlsFile = ['attachtypefile', 'attachurlfile'];  
  },
  
  OnCreateDetailWinFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwinfile')) {
      Ext.getCmp(mep + 'detailwinfile').destroy();
    };
    
    var itemsfile = [
      ' ', { id: mep + 'btnDetailSaveFile', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveFile, scope: me }
//      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
    ];
    
    me.OnBeforeCreateDetailEditFile();
    
    me.plDetailEditFile = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetaileditfile',
      region : 'north',
//      height : '18%',
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
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: itemsfile}),
      items: me.editDetailControlsFile    
    });
    
    var uploadFile = tools.SwfUpload();
    me.plFileDetailGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'filedetailgrid',
      region : 'center',
      columnWidth:1,
      scope: me,
      items: [uploadFile]    
    });
    
    uploadFile.on('showdetail',me.OnShowDetailFile,me);
    uploadFile.on('closewin',me.OnCloseWinFile,me);
    
    me.winDetailFile = Ext.create('Ext.Window', {
      id: mep + 'detailwinfile',
      title: '原始记录表',
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
      items : [me.plDetailEditFile,me.plFileDetailGrid]
    });
  },
  
  OnListNewFile : function(){
    var me = this;
    var mep = this.tranPrefix;
    var recordstatus = tools.GetValue(mep+'recordstatus');
    
    if(recordstatus=='02'||recordstatus=='04'||recordstatus=='08'){
      tools.alert('该原始记录表复核中，不能进行修改...');
      return false;
    }
    me.OnCreateDetailWinFile();
    if(me.winDetailFile){      
      me.winDetailFile.show();
    };
  },
  
  OnCloseWinFile:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.winDetailFile.hide();
  },
  
  OnListSelectFile: function (view, record) {
    alms.PopupFileShow(record.data.attachname+'附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
  },
  
  OnShowDetailFile:function(render, item){
    var me = this;
    var mep = this.tranPrefix;
    
    var attachname = Ext.getCmp(mep+'attachnamefile').getValue();
    var attachurl = Ext.getCmp(mep+'attachurlfile').getValue();
    
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
      tools.SetValue(mep + 'attachnamefile',attachname);
      tools.SetValue(mep + 'attachurlfile',attachurl);
    };
  },
  
  OnListSaveFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var attachname = Ext.getCmp(mep+ 'attachnamefile').getValue();
    var attachurl = Ext.getCmp(mep + 'attachurlfile').getValue();
    var attachtype = Ext.getCmp(mep + 'attachtypefile').getValue();
    
    if (tools.InvalidForm(me.plDetailEditFile.getForm())) {
      if(attachname == "" || attachname == null || attachname == undefined){
        tools.alert('请上传附件！');
        return;
      }else{
        if (me.detailEditType == 1) {
          //可能有多个附件的情况
          var attachnames = attachname.split(",");
          var attachurls = attachurl.split(",");
          for(i = 0; i <attachnames.length; i++){
            var record = me.fileGrid.store.model.create({});
            record.data.attachname = attachnames[i];
            record.data.attachtype = '02';
            record.data.attachtypename = '原始记录表';
            record.data.attachurl = attachurls[i];
            me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);      
          };
        }
        else {
          me.OnBeforeListSaveFile(me.detailRecord);
          me.fileGrid.getView().refresh();
        };
      };
      me.winDetailFile.hide();
    };
  },
  
  OnListDeleteFile:function(){
    var me = this;
    var mep = this.tranPrefix;
    var recordstatus = tools.GetValue(mep+'recordstatus');
    
    if(recordstatus=='02'||recordstatus=='04'||recordstatus=='08'){
      tools.alert('该原始记录表复核中，不能进行修改...');
      return false;
    }
    var j = me.fileGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
    };
    
    me.fileGrid.getView().refresh();
  },
 
OnDetailRefresh: function () {
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
 
 OnGetSaveParams : function() {
   var me = this;
   var mep = me.tranPrefix;
   
   if (me.hasDetail) {
     var details = [];
     for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     
     var filedetails = [];
     for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
       filedetails.push( me.fileGrid.store.getAt(i).data);
     }
     
     me.saveParams = { details: Ext.encode(details), filedetails: Ext.encode(filedetails), 
       signerpassword: Ext.getCmp(mep + 'signer_passwd').getValue(), approveusers: Ext.getCmp(mep + 'approveuser').getValue() };
   }
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
       
       if(item.recordstatus == '01'|| item.recordstatus =='06'|| item.recordstatus =='10' ){
         tools.BtnsEnable(['btnRecordSubmit'],mep);
       }else{
         tools.BtnsDisable(['btnRecordSubmit'],mep);
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
         tools.BtnsEnable(['btnRecordSubmit'],mep);
       }else{
         tools.BtnsDisable(['btnRecordSubmit'],mep);
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
 
 }

 
});
