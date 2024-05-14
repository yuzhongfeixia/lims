Ext.define('alms.manbuslab', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '试验',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_buslab',
      storeUrl: 'LabSearchBusTaskEnd.do',
      saveUrl: 'LabSaveBusTaskEnd.do',
      expUrl: 'LabSearchBusTaskEnd.do',
      idPrevNext: 'sampletran',
      hasEdit: true,
      hasDetail: false,
      detailTabs: 0,
      hasDateSearch: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '任务单编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtaskid', id: mep + 'searchtaskid', allowBlank: true }, 
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplecode', id: mep + 'searchsamplecode', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      '-', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      ' ', { id: mep + 'btnEdit', text: '处理', iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    var url = '';
    url = 'DevGetListBasDevByLab.do'
    me.samParStore = new Ext.data.JsonStore({
     proxy:{
       type:'ajax',
       url:url,
       reader:{
         type:'json',
         root:'data'
       }
     },
     fields:[{name:'id',mapping:'devid'},{name:'name',mapping:'devname'}],
     autoLoad:true
   });
    
    me.OnInitGridToolBar();
    
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('任务单号', 'btsg.taskid', mep + 'taskid', 20, '96%', true,null,null,90),
          tools.FormText('样品状态', 'btsg.samplestatus', mep + 'samplestatus', 20, '96%', true,null,null,90),
          tools.FormText('承检室负责人', 'btsg.labusername', mep + 'labusername', 20, '96%', true,null,null,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品编号', 'btsg.samplecode', mep + 'samplecode', 500, '96%', true,null,null,90),
          tools.FormCombo('检验性质', 'btsg.testprop', mep + 'testprop', tools.ComboStore('TestProp', gpersist.SELECT_MUST_VALUE), '96%', false, 2,90),
          tools.FormDate('结束检测时间', 'btsg.endtestdate', mep + 'endtestdate', 'Y-m-d', '96%', false,1,nowdate,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品名称', 'btsg.samplename', mep + 'samplename', 500, '96%', true,null,null,90),
          tools.FormDate('要求完成日期', 'btsg.reqdate', mep + 'reqdate', 'Y-m-d', '96%', true,1,nowdate,90),
//          tools.FormCheckCombo('使用设备', 'btsg.devids', mep + 'devids', tools.GetNullStore(), '96%', true,null,90)
          tools.FormText('环境情况', 'btsg.testenv', mep + 'testenv', 50, '96%', true,null,null,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('规格型号', 'btsg.samplestand', mep + 'samplestand', 100, '100%', true,null,null,90),
          tools.FormCombo('承检室', 'btsg.labid', mep + 'labid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '100%', false, 3,90)
        ]}
      ]},
      tools.FormTextArea('检测依据', 'btsg.mainstandname', mep + 'mainstandname', 500, '100%', true, 8, 3 ,90),
      tools.FormTextArea('备注', 'btsg.taskremark', mep + 'taskremark', 200, '100%', true, 8, 3 ,90),
      { xtype: 'fieldset', collapsible: true, title: '使用设备批量选择', anchor: '100%', items: [
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .50, layout: 'anchor', items: [
            tools.FormCheckCombo('使用设备', 'devusers', mep + 'devusers', me.samParStore, '100%', true,null,80)
          ]}
        ]}                                                                             
      ]},
      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: 1, layout: 'anchor', id: mep + 'testdata', items: [
        
        ]}
      ]},
      {xtype:'hiddenfield',name:'btsg.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'btsg.sampletran',id: mep + 'sampletran'},
      {xtype:'hiddenfield',name:'btsg.sampleid',id: mep + 'sampleid'},
      {xtype:'hiddenfield',name:'btsg.libuser',id: mep + 'libuser'},
      {xtype:'hiddenfield',name:'btsg.libname',id: mep + 'libname'},
      {xtype:'hiddenfield',name:'btsg.devnames',id: mep + 'devnames'},
      {xtype:'hiddenfield',name:'btsg.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'isjudge',id: mep + 'isjudge'},
      {xtype:'hiddenfield',name:'auditstatus',id: mep + 'auditstatus'},
      {xtype:'hiddenfield',name:'btsg.deal.action',id: mep + 'datadeal'}
    ];
    
    me.disNews = ['taskid','samplename','samplestand','reqdate','labid','labusername','testprop','samplecode'];
    me.disEdits = ['taskid','samplename','samplestand','reqdate','labid','labusername','testprop','samplecode'];
    me.enNews = ['samplestatus','endtestdate','testtemp','testhum','testenv','devids','taskremark','mainstandname','devusers'];
    me.enEdits = ['samplestatus','endtestdate','testtemp','testhum','testenv','devids','taskremark','mainstandname','devusers'];
    
    Ext.getCmp(mep+'devusers').on('select',function(){
      if (me.formdatas) {
        for (var i = 0; i < me.formdatas.length; i++) {
          var formdata = me.formdatas[i];
          if (formdata.fieldtype == '07') {
            tools.SetValue(mep + 'd' + formdata.fieldcode + '-' + formdata.specserial,tools.GetValue(mep + 'devusers').split(','));
          }
        }
      }   
    })
  },
  
  OnDevSelect : function(){
    var me = this;
    var mep = me.tranPrefix;
    
    //设备名称和设备编号连在一起
    var devnames = Ext.getCmp(mep + 'devids').getDisplayValue().split(",");
    var devids = Ext.getCmp(mep + 'devids').getValue().split(",");
    var devinfos;
    if(devids){
      for(var i=0;i<devids.length;i++){
        if(i==0){
          devinfos = devnames[i]+':'+devids[i]
        }else{
          devinfos = devinfos +','+ devnames[i]+':'+devids[i]
        }
      }
    }

    tools.SetValue(mep + 'devnames',devinfos);
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : '处理', iconCls : 'icon-deal', handler : me.OnWrite, scope : me },
      '-', ' ', { id : mep + 'btnFormula', text : '判定', iconCls : 'icon-deal', handler : me.OnFormula, scope : me },
      '-', ' ', { id : mep + 'btnResult', text : '查看判定结果', iconCls : 'icon-deal', handler : me.OnResult, scope : me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnAfterCreateEdit: function () {
    var me = this;
    me.plEdit.region = 'center';
  },
  
  OnResult:function(){
    var me = this;
    var mep = me.tranPrefix;

    var taskid = tools.GetValue(mep+'taskid');
    var sampletran = tools.GetValue(mep+'sampletran');
    var samplename = tools.GetValue(mep+'samplename');
    var samplecode = tools.GetValue(mep+'samplecode');

    me.OnCreateDetailResult();
    
    if(me.winDetailResult){      
      me.winDetailResult.show();
      me.winDetailResult.maximize();
      me.onScanResult(taskid,sampletran,samplename,samplecode);
    };
  },
  
  OnCreateDetailResult: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwinresult')) {
      Ext.getCmp(mep + 'detailwinresult').destroy();
    };
    
    var items = [
       ' ', { id: mep + 'btnPrintRecord', text: '打印检测结果', iconCls: 'icon-print', handler: me.OnPrintResult, scope: me },          
       ' ', { id: mep + 'btnDetailResultClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winDetailResult.hide();}}
    ];
    
    me.editDetailControls = [
      { xtype: 'label', id: mep + 'labresulthtml', html: '' }
    ];
    
    me.plDetailEditResult = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetaileditresult',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      title: '',
      autoScroll : true,
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    me.winDetailResult = Ext.create('Ext.Window', {
      id: mep + 'detailwinresult',
      title: '检测判定结果',
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [me.plDetailEditResult]
    });
  },
  
  onScanResult:function(taskid,sampletran,samplename,samplecode){
    var me = this;
    var mep = me.tranPrefix;
    me.html = almsprint.JudgeResult(taskid,sampletran,samplename,samplecode);
    Ext.fly(Ext.getCmp(mep + 'labresulthtml').getEl()).update(me.html);
    return true;
  },
  
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    var fields = [];

    if (me.formdatas) {
      for (var i = 0; i < me.formdatas.length; i++) {
        var formdata = me.formdatas[i];
        fields.push('d' + formdata.fieldcode + '-' + formdata.specserial);
      }
    }
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormJudge','btnFormEdit'], mep);        
        tools.BtnsDisable(['btnSave','btnFormula'], mep);
        tools.Disabled(fields, mep); 
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormJudge','btnFormEdit'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.Enabled(fields, mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormJudge','btnFormEdit','btnFormula'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.Enabled(fields, mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var labid = gpersist.UserInfo.dept.deptid;

    var testdata = Ext.getCmp(mep + 'testdata');
    var item = tools.JsonGet('LabGetBusTaskSingleBySampleTran.do?btsg.sampletran='+record.sampletran);
    if (item && !Ext.isEmpty(item.taskid)) {
      tools.SetValue(mep + 'taskid', item.taskid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'samplecode', item.samplecode);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'samplestatus', item.samplestatus);
      tools.SetValue(mep + 'testprop', item.testprop);
      tools.SetValue(mep + 'samplestand', item.samplestand);
      tools.SetValue(mep + 'senduser', item.senduser);
      tools.SetValue(mep + 'sendusername', item.sendusername);
      tools.SetValue(mep + 'mainstandname', record.teststandardname);
      tools.SetValue(mep + 'senddate', item.senddate);
      tools.SetValue(mep + 'reqdate', item.reqdate);
      tools.SetValue(mep + 'labid', item.labid);
      tools.SetValue(mep + 'labuser', item.labuser);
      tools.SetValue(mep + 'labusername', item.labusername);
      tools.SetValue(mep + 'getuser', item.getuser);
      tools.SetValue(mep + 'getusername', item.getusername);
      tools.SetValue(mep + 'getcount', item.getcount);
      tools.SetValue(mep + 'getdate', item.getdate);
      tools.SetValue(mep + 'taskremark', item.taskremark);
      tools.SetValue(mep + 'backcount', item.backcount);
      tools.SetValue(mep + 'handdate', item.handdate);
      tools.SetValue(mep + 'acceptuser', item.acceptuser);
      tools.SetValue(mep + 'acceptusername', item.acceptusername);
      tools.SetValue(mep + 'acceptdate', item.acceptdate);
//      tools.SetValue(mep + 'endtestdate', item.endtestdate);
      tools.SetValue(mep + 'endtestdate', item.endtestdate == null ? new Date() : item.endtestdate);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'testtemp', item.testtemp);
      tools.SetValue(mep + 'testhum', item.testhum);
      tools.SetValue(mep + 'testenv', item.testenv==''?'符合检测要求': item.testenv);
      tools.SetValue(mep + 'devnames', item.devnames);
      tools.SetValue(mep + 'checkuser', item.checkuser);
      tools.SetValue(mep + 'checkusername', item.checkusername);
      tools.SetValue(mep + 'checkdate', item.checkdate);
      tools.SetValue(mep + 'aduituser', item.aduituser);
      tools.SetValue(mep + 'aduitusername', item.aduitusername);
      tools.SetValue(mep + 'aduitdate', item.aduitdate);
      tools.SetValue(mep + 'processuser', item.processuser);
      tools.SetValue(mep + 'processusername', item.processusername);
      tools.SetValue(mep + 'processdate', item.processdate);
      tools.SetValue(mep + 'sampletran', record.sampletran);
      tools.SetValue(mep + 'auditstatus', item.auditstatus);
      tools.SetValue(mep + 'devusers', '');

      me.OnGetDevs(item.labid);
      tools.SetValue(mep + 'devids', item.devids.split(','));
      me.samParStore.load({url:'DevGetListBasDevByLab.do?bd.labid=' + item.labid});
      
      testdata.removeAll();

      var ints = tools.JsonGet(tools.GetUrl('FormGetIntInterfaceByTask.do?btsg.sampletran=' + record.sampletran + '&btsg.tranid=' + record.tranid + '&btsg.labid=' + labid));

      me.formdatas = [];
      var maxline = 0, i = 0, j = 0, m = 0, k = 0, idx = 0;
      for(i = 0; i < ints.data.length; i++){
        var intitem = ints.data[i];
        var fields = tools.JsonGet(tools.GetUrl('FormGetListIntField.do?ifd.intid=' + intitem.intid));
        var intgroup = Ext.create('Ext.form.FieldSet', { id: mep + 'g' + intitem.intid, hidden: false, collapsible: true, 
          title: intitem.intname, anchor: '100%'});
        
        for (j = 0; j < fields.data.length; j++) {
          if (fields.data[j].fieldrow > maxline) 
            maxline = fields.data[j].fieldrow;
        }
        idx = 1;
        //m指的是行数
        for (m = 1; m <= maxline; m++) {
          var cbox = Ext.create('Ext.container.Container', { id: mep + 'con' + intitem.intid + '_' + m, margin: 2, anchor: '100%', layout: 'hbox' }); 
          for (j = 0; j < fields.data.length; j++) {
            if (fields.data[j].fieldrow == m) {
              if (fields.data[j].fieldtype == '05') {
                var ccombo = tools.AutoFormComboFlex(tools.MustTitle(fields.data[j].fieldlabel), 'data.' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  mep + 'd' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  Ext.isEmpty(fields.data[j].fieldcode) ? tools.GetNullStore() : 
                  tools.ComboStoreByUrl('LabGetBusSelectByCode.do', {'bselect.selectcode': ''}, fields.data[j].isnull ? gpersist.SELECT_NULL_VALUE : gpersist.SELECT_MUST_VALUE), 
                  true, (i +1) * 100 + idx, fields.data[j].labelwidth, true, fields.data[j].fieldwidth);
                  
                if (Ext.isEmpty(fields.data[j].defaultvalue))
                  ccombo.setValue(fields.data[j].isnull ? gpersist.SELECT_NULL_VALUE : gpersist.SELECT_MUST_VALUE);
                else
                  ccombo.setValue(fields.data[j].defaultvalue);
                    
                cbox.add(ccombo);
              }else if (fields.data[j].fieldtype == '07') {
                var ccheckombo = tools.AutoFormCheckComboFlex(tools.MustTitle(fields.data[j].fieldlabel), 'data.' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  mep + 'd' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, me.samParStore,  
                  true, (i +1) * 100 + idx, fields.data[j].labelwidth, true, fields.data[j].fieldwidth);
                  
                if (Ext.isEmpty(fields.data[j].defaultvalue))
                  ccheckombo.setValue(fields.data[j].isnull ? gpersist.SELECT_NULL_VALUE : gpersist.SELECT_MUST_VALUE);
                else
                  ccheckombo.setValue(fields.data[j].defaultvalue);
                  
                cbox.add(ccheckombo);
              }else if(fields.data[j].fieldtype == '01'){
                var ctext = tools.AutoFormTextFlex(fields.data[j].fieldlabel, 'data.' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  mep + 'd' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  fields.data[j].fieldmax, true, (i +1) * 100 + idx, 'isdecimal6', fields.data[j].labelwidth, fields.data[j].fieldwidth);
                  
                if (!Ext.isEmpty(fields.data[j].defaultvalue))
                  ctext.setValue(fields.data[j].defaultvalue);
                    
                cbox.add(ctext);
                
              }else {
                var ctext = tools.AutoFormTextFlex(fields.data[j].fieldlabel, 'data.' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  mep + 'd' + fields.data[j].fieldcode + '-' + fields.data[j].fieldserial, 
                  fields.data[j].fieldmax, true, (i +1) * 100 + idx, null, fields.data[j].labelwidth, fields.data[j].fieldwidth);
                
                if (!Ext.isEmpty(fields.data[j].defaultvalue))
                  ctext.setValue(fields.data[j].defaultvalue);
                
                //默认填写检测方法
                var fieldcodes = fields.data[j].fieldcode;
                if(fieldcodes.substring(fieldcodes.length-5,fieldcodes.length).indexOf('jcff')>0){
                  ctext.setValue(intitem.teststandardname);
                }
                    
                cbox.add(ctext);
              }
              
              me.formdatas.push({fieldcode: fields.data[j].fieldcode, specserial: fields.data[j].fieldserial, 
                displayvalue: '', submitvalue: '', fieldtype: fields.data[j].fieldtype, intid: fields.data[j].intid});

              idx++;
            }          
          }
          intgroup.add(cbox);
        }
        
        testdata.add(intgroup); 
      }
      
      var datas = tools.JsonGet(tools.GetUrl('LabGetListBusTaskData.do?btdt.taskid=') + record.taskid + '&btdt.sampletran=' + record.sampletran);
      if (datas && datas.data) {
        for (var i = 0; i < datas.data.length; i++) {
          var data = datas.data[i];
          if (data.fieldtype == '07') {
            tools.SetValue(mep + 'd' + data.fieldcode + '-' + data.specserial, data.submitvalue.split(','));
          }else
            tools.SetValue(mep + 'd' + data.fieldcode + '-' + data.specserial, data.submitvalue);
        }  
      }
      
      return true;
      
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  
  OnGetDevs:function(labid){
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(labid) || (labid == gpersist.SELECT_MUST_VALUE))
      return;
    
    var devs = Ext.getCmp(mep + 'devids');
    
    if (devs) {
      if (labid == gpersist.SELECT_MUST_VALUE) {
        devs.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetDevCombo(null, labid, mep + 'devids');
      }
    }
    
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
    
    tools.BtnsEnable(['btnFormula'], mep);
  },
  
  OnGetSaveParams: function () {
    var me = this, mep = me.tranPrefix;
    var tmpdatas = [];
              
    if (me.formdatas) {
//      for (var i = 0; i < me.formdatas.length; i++) {
//        if(i % 3 == 1){
//          tmpdatas.push(me.formdatas[i]);
//        }
//      }
      for (var i = 0; i < me.formdatas.length; i++) {
        var formdata = me.formdatas[i];
        formdata.submitvalue = tools.GetValue(mep + 'd' + formdata.fieldcode + '-' + formdata.specserial);
        if (formdata.fieldtype == '07') {
          formdata.displayvalue = tools.GetDisplayValue(mep + 'd' + formdata.fieldcode + '-' + formdata.specserial);
        }else if (formdata.fieldtype == '05'){
          formdata.displayvalue = tools.GetDisplayValue(mep + 'd' + formdata.fieldcode + '-' + formdata.specserial);
        }else
          formdata.displayvalue = formdata.submitvalue;
      }
      me.saveParams = { datas: Ext.encode(me.formdatas) };

    }   
  },
  
  OnAfterFormula : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.BtnsDisable(['btnFormula'], mep);
  },
  
  OnWrite:function(){
    var me = this;
    var mep = me.tranPrefix;
    var flowstatus = tools.GetValue(mep + 'flowstatus');
    me.OnFormEdit();
  },
  
  OnFormula:function(){
    var me = this;
    var mep = me.tranPrefix;
    
//    tools.BtnsEnable(['btnResult'], mep);
//    tools.BtnsDisable(['btnFormula'], mep);
    
//    if (!me.OnFormulaSave())
//      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      //me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl('LabComputeBusTask.do'),
//        url : tools.GetUrl('LabJudgeBusTask.do'),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.OnAfterFormula(action);
//          me.OnSearch();
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          tools.alert(Ext.String.format(action.result.msg, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
//  OnItemClick: function () {
//    var me = this;
//    var mep = me.tranPrefix;
//    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
//
//    if(record.isjudge == true){
//      tools.BtnsDisable(['btnFormula'], mep);
//      tools.BtnsEnable(['btnResult'], mep);
//    }else {
//      tools.BtnsEnable(['btnFormula'], mep);
//      tools.BtnsDisable(['btnResult'], mep);
//    }
//  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'btsg.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'btsg.samplecode': tools.GetEncode(tools.GetValue(mep + 'searchsamplecode')),
          'btsg.taskid': tools.GetEncode(tools.GetValue(mep + 'searchtaskid'))
        });
      });
    };
  },
  
//  OnFormClose : function() {
//    var me = this;
//    
//    if (me.tabMain) {
//      if (me.hasExitPrompt) {
//          Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_EXITLIST, function(btn) {
//            if (btn == 'yes') {
//              me.dataDeal = gpersist.DATA_DEAL_SELECT;
//              me.tabMain.setActiveTab(0);
//              me.OnResetForm();
//            }
//          });
//      }
//    }
//  },
  
  OnPrintResult: function () {
    var me = this;
    
    if (!Ext.isEmpty(me.html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("检测结果打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', me.html);
      LODOP.SET_PRINTER_INDEXA(-1);
      LODOP.PREVIEW();//预览功能
//        LODOP.PRINT();//打印功能
    }
  },
  
  OnSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
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
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnAfterSave(action);
//          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
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
    
    var auditstatus = tools.GetValue(mep+'auditstatus');

    if(auditstatus == '92'||auditstatus == '94'||auditstatus == '98'){
      tools.alert('该样品生成的原始记录表正在审批中,不得重复试验....');
      return false;
    }
    return true;
  },
  
  OnInitData: function(){
    var me = this, mep = this.tranPrefix;
    tools.SetValue(mep+'testenv','符合检测要求');
  }
  
});
