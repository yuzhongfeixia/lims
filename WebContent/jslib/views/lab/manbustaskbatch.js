Ext.define('alms.manbustaskbatch', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '多任务单下达',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busaccsamplebatch',
      storeUrl: 'LabSearchBusAccSampleBatch.do',
      saveUrl: 'LabSaveBusTaskBatch.do',
      expUrl: 'LabSearchBusAccSampleBatch.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bustaskfile',
      urlDetail: 'LabGetListBusTaskFile.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '任务单分配明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'tranid',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false,
      detailTabs: 2
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
    
    var items = [
       ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
       '-', tools.GetToolBarCombo('searchgettype', mep + 'searchgettype', 160, '取样类型', 60, tools.ComboStore('GetType', gpersist.SELECT_MUST_VALUE)),
       '-', tools.GetToolBarCombo('searchissend', mep + 'searchissend', 160, '任务单状态', 70, tools.ComboStore('IsSend', gpersist.SELECT_MUST_VALUE)),
       '-', { xtype:'datefield',fieldLabel:'收样开始时间',labelWidth:90,width:200,name:'searchbegindate',id:mep + 'searchbegindate',
         format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
       '-', {xtype:'datefield',fieldLabel:'收样结束时间',labelWidth:90,width:200,name:'searchenddate',id:mep + 'searchenddate',
         format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true}
    ];

    var items1 = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', { id: mep + 'btnEdit', text: '处理', iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', { id: mep + 'btnCancel', text: '任务单撤销', iconCls: 'icon-deal', handler: me.OnCancel, scope: me },
      '-', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    tools.SetToolbar(items1, mep);
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnDeal:function(){
    var me = this;
    var mep = me.tranPrefix;
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    
    if(datas.length == 0){
      tools.alert('请选择样品！');
      return;
    }
    me.OnEdit();
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
    
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品名称 ', 'bt.samplename', mep + 'samplename', 20, '96%', true,null,null,90),
          tools.FormText('规格型号 ', 'bt.samplestand', mep + 'samplestand', 500, '96%', true,null,null,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormCombo('检验性质', 'bt.testprop', mep + 'testprop', tools.ComboStore('TestProp', ''), '96%', false, 2,90),
          tools.FormDate('要求完成时间', 'bt.reqdate', mep + 'reqdate', 'Y-m-d', '96%', true, 7, nowdate,90)
          
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('检验依据', 'bt.teststandardname', mep + 'teststandardname', 100, '96%', true,null,null,90),
          tools.FormDate('下达日期', 'bt.senddate', mep + 'senddate', 'Y-m-d', '96%', true,null,nowdate,90)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('样品状态 ', 'bt.samplestatus', mep + 'samplestatus', 20, '100%', true,null,null,90),
          tools.FormCombo('下达人', 'bt.senduser', mep + 'senduser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '100%', false, 2,90)
        ]}
      ]},
      {xtype:'hiddenfield',name:'bt.taskid',id: mep + 'taskid'},
      {xtype:'hiddenfield',name:'bt.tranid',id: mep + 'tranid'},
      {xtype:'hiddenfield',name:'bt.sampleid',id: mep + 'sampleid'},
      {xtype:'hiddenfield',name:'bt.accsampleid',id: mep + 'accsampleid'},
      {xtype:'hiddenfield',name:'bt.gettype',id: mep + 'gettype'},
      {xtype:'hiddenfield',name:'bt.samplecode',id: mep + 'samplecode'},
      {xtype:'hiddenfield',name:'bt.issend',id: mep + 'issend'},
      {xtype:'hiddenfield',name:'bt.sampletype',id: mep + 'sampletype'},
      {xtype:'hiddenfield',name:'bt.deal.action',id: mep + 'datadeal'}
    ];
      
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['reqdate','testprop','labid','samplestatus','teststandardname','samplecode','samplename','teststandardname',
                 'samplestand','labuser','senduser','senddate'];
    me.enEdits = ['reqdate','testprop','labid','samplestatus','teststandardname','samplecode','samplename','teststandardname',
                  'samplestand','labuser','senduser','senddate'];
    
  },
  
  OnItemClick: function () {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');

    if(record.issend == false){
      tools.BtnsEnable(['btnSave'], mep);
    }else{
      tools.BtnsDisable(['btnSave'], mep);
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
//          'bas.sampletype': '01',
          'bas.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bas.testedname': tools.GetEncode(tools.GetValue(mep + 'searchtestedname')),
          'bas.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'bas.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d'),
          'bas.gettype': tools.GetEncode(tools.GetValue(mep + 'searchgettype')),
          'bas.issendstatus': tools.GetEncode(tools.GetValue(mep + 'searchissend'))
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : '下达', iconCls : 'icon-save', handler : me.OnSave, scope : me },
      '-', ' ', { id : mep + 'btnFormEdit', text : '处理', iconCls : 'icon-deal', handler : me.OnFormEdit, scope : me },
      '-', ' ', { id : mep + 'btnPrevRecords', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecords', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('检测室', 'labid', mep + 'labid', tools.ComboStore('Lab', gpersist.SELECT_MUST_VALUE), '96%', false, null)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('任务单名称', 'filename', mep + 'filename', 40, '100%', false, 4),
          { xtype: 'hiddenfield', name: 'fileurl', id: mep + 'fileurl' },
          { xtype: 'hiddenfield', name: 'labname', id: mep + 'labname' }
        ]}
      ]},                      
      tools.FormTextArea('备注', 'remark', mep + 'remark', 200, '100%', true, null, 2)
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['fileurl','filename','labid','labname'];
    
    tools.SetValue(mep+'remark','同一个检测室只能上传一个文件，如果有多个文件可以先生成压缩包，再进行上传！');
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
    
    var filename = Ext.getCmp(mep+'filename').getValue();
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    
    if(item){
      if(filename == ""){
        filename = item.name;
      }else{
        filename = filename+','+item.name
      };
      if(fileurl == ""){
        fileurl = item.url;
      }else{
        fileurl = fileurl+','+item.url;
      };
      tools.SetValue(mep + 'filename',filename);
      tools.SetValue(mep + 'fileurl',fileurl);
    };
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    var labid = Ext.getCmp(mep + 'labid').getValue();
    var labname = Ext.getCmp(mep + 'labid').getDisplayValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(fileurl == "" || fileurl == null || fileurl == undefined){
        tools.alert('请上传附件！');
        return;
      }else{
        if (me.detailEditType == 1) {
          //可能有多个附件的情况
          var filenames = filename.split(",");
          var fileurls = fileurl.split(",");
          var store = me.plDetailGrid.store;
          for(i = 0; i <1; i++){
            var record = me.plDetailGrid.store.model.create({});
            record.data.filename = filenames[i];
            record.data.labid = labid;
            record.data.labname = labname;
            record.data.fileurl = fileurls[i];
            
            for(var j=0;j<me.plDetailGrid.store.getCount();j++){
              var oldlabid = store.getAt(j).data.labid;
              
              //修改才会执行
              if(labid == oldlabid){
                tools.alert('该"'+labname+'"已分配任务单,不能重复分配...');
                return false;
              }
            }
            
            me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
          };
        }else {
          me.OnBeforeListSave(me.detailRecord);
          me.plDetailGrid.getView().refresh();
        };
      };
      me.winDetail.hide();
    };
  },
  
  OnListDelete:function(){
    var me = this;
    var j = me.plDetailGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    };
    
    me.plDetailGrid.getView().refresh();
  },
  
  OnListSelect: function (view, record) {
    alms.PopupFileShow('任务单附件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
  },
  
  //检测参数标准文件
  OnAfterCreateDetail: function () {
      var me = this, mep = this.tranPrefix;
      
      var partsColumn = [];
      var partsField = [];    

      tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_bussampleparam'), partsColumn, partsField, mep + '_a_');

      me.partsStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusSampleParamByAcc.do'), partsField);
      
      me.partsGrid = Ext.create('Ext.grid.Panel', {
        region : 'center',
        title : '检测参数标准明细',
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
        columns : partsColumn,
        store : me.partsStore,
        selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
        listeners : {
          'itemdblclick' : { fn : me.OnListSelectParts, scope : me }
        }    
      });
      me.partsStore.load();
      me.plDetail.add(me.partsGrid);
      me.partsitemstar = [
        ' ', { id : mep + 'btnPartsAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewParts, scope : me },
        ' ', { id : mep + 'btnPartsDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteParts, scope : me }
      ];
      
      me.OnAfterCreateDetailToolBar();
      
      tools.SetToolbar(me.partsitemstar, mep);
        
      var tbdev = Ext.create('Ext.toolbar.Toolbar', {
        dock : 'top',
        items : me.partsitemstar
      });
      me.partsGrid.insertDocked(0, tbdev);
  },
  
  OnListNewParts:function(){
    var me = this;
    
    me.OnCreateDetailParts();
    if(me.PartsDetail){      
      me.PartsDetail.show();
      me.detailEditType = 1;
      me.OnAuthDetailEditFormParts(me.dataDeal,true)
      me.OnInitComboParam();
    };
   
  },
  
  OnListDeleteParts : function() {
    var me = this;
   
    var j = me.partsGrid.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.partsGrid.store.remove(me.partsGrid.selModel.selected.items[0]);
    }
   
    me.partsGrid.getView().refresh();
  },
 
  OnCreateDetailParts: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (Ext.getCmp(mep + 'detailparts')) {
       Ext.getCmp(mep + 'detailparts').destroy();
     };
     
     var partseditsitems = [
       ' ', { id: mep + 'btnPartsDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveParts, scope: me },
       '-', ' ', { id: mep + 'btnPartsDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.PartsDetail.hide(); me.detailEditType = 1 } }
     ];
     
     me.OnBeforeCreatePartsDetailEdit();
     
     me.partsDetailEdit = Ext.create('Ext.form.Panel', {
       id:mep + 'partsdetailedit',
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
       tbar : Ext.create('Ext.toolbar.Toolbar', {items: partseditsitems}),
       items: me.editPartsDetailControls    
     });
     
     me.PartsDetail = Ext.create('Ext.Window', {
       id: mep + 'detailparts',
       title: '检测参数标准信息',
       width: 700,
       height: 250,
       maximizable: true,
       closeAction: 'hide',
       modal: true,
       layout: 'fit',
       plain: false,
       closable: true,
       draggable: true,
       constrain: true,
       items : [me.partsDetailEdit]
     });
  },
 
  OnBeforeCreatePartsDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.winWidth=600,
    me.winHeight=260,
    me.OnInitGridToolBar();
    me.editPartsDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('检测参数', 'parameterid', mep + 'parameterid', tools.GetNullStore(), '96%', false, 2),
          tools.FormCombo('判定方法', 'testjudge', mep + 'testjudge', tools.ComboStore('JudgeType', gpersist.SELECT_MUST_VALUE), '96%', true, 3),
          tools.FormCombo('检测方法', 'teststandard', mep + 'teststandard', tools.ComboStore('TestStandard', gpersist.SELECT_MUST_VALUE), '96%', true, 3)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('单位', 'paramunit', mep + 'paramunit', 20, '96%', true),
          tools.FormText('标准值', 'standvalue', mep + 'standvalue', 200, '96%', true),
          tools.FormCombo('检测依据', 'judgetandard', mep + 'judgestandard', tools.ComboStore('JudgeStandard', gpersist.SELECT_MUST_VALUE), '96%', true, 3)
        ]},
        
        { xtype:'hiddenfield',name:'parametername',id: mep + 'parametername'},
        { xtype:'hiddenfield',name:'teststandardcode',id: mep + 'teststandardcode'},
        { xtype:'hiddenfield',name:'judgestandardcode',id: mep + 'judgestandardcode'}
     ]}
    ];
    me.disPartsDetailControls = [];
    me.enPartsDetailControls = ['parameterid','testjudge','teststandard','paramunit','standvalue','judgestandard'];  
 }, 
 
  OnListSaveParts: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (tools.InvalidForm(me.partsDetailEdit.getForm())) {
       
       if (me.detailEditType == 1) {
         var record = me.partsGrid.store.model.create({});
         me.OnBeforeListSaveParts(record);
         
         me.partsGrid.store.insert(me.partsGrid.store.getCount(), record);      
       }
       else {
         me.OnBeforeListSaveParts(me.detailPartsRecord);
         
         me.partsGrid.getView().refresh();
       }
       
       me.PartsDetail.hide();
     }
  },
 
  OnBeforeListSaveParts: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.parameterid = tools.GetValue(mep+'parameterid');
    record.data.testjudge = tools.GetValue(mep+'testjudge');
    record.data.standvalue = tools.GetValue(mep+'standvalue');
    record.data.teststandard = tools.GetValue(mep+'teststandard');
    record.data.judgestandard = tools.GetValue(mep+'judgestandard');
    record.data.paramunit = tools.GetValue(mep+'paramunit');
    record.data.parametername = Ext.getCmp(mep+'parameterid').getDisplayValue();
    record.data.teststandardcode = Ext.getCmp(mep+'teststandard').getDisplayValue();
    record.data.judgestandardcode = Ext.getCmp(mep+'judgestandard').getDisplayValue();
  },
  
  OnListSelectParts: function(e, record, item, index) {
    var me = this;
    var mep = me.tranPrefix;
    me.detailPartsRecord = record;
    me.OnCreateDetailParts();
      
    if(me.PartsDetail && record) {      
      me.PartsDetail.show();
      me.detailEditType = 2;
      me.OnLoadDetailDataParts(record.data);  
      me.OnAuthDetailEditFormParts(me.dataDeal,true);
    };
  },
  
  OnInitComboParam:function(){
    var me = this;
    var mep = me.tranPrefix;
    var paramdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
    var sampleid = tools.GetValue(mep + 'sampleid');
    var params = tools.JsonGet('BasGetListBasSampleReplaceBySampleID.do?bsr.sampleid='+sampleid);
    var param = Ext.getCmp(mep + 'parameterid');
    if (params){
      Ext.each(params.data, function (item, index) {
        paramdata.push({ id: item.parameterid, name: item.parametername });
      });
    }
    param.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: paramdata}))
    tools.SetValue(mep + 'parameterid',gpersist.SELECT_MUST_VALUE);
  },
  
  OnLoadDetailDataParts: function (record) {
    var me = this;
    var mep = me.tranPrefix;

    me.OnInitComboParam();
    tools.SetValue(mep+'parametername',record.parametername);
    tools.SetValue(mep+'parameterid',record.parameterid);
    tools.SetValue(mep+'testjudge',record.testjudge);
    tools.SetValue(mep+'standvalue',record.standvalue);
    tools.SetValue(mep+'teststandardcode',record.teststandardcode);
    tools.SetValue(mep+'judgestandardcode',record.judgestandardcode);
    tools.SetValue(mep+'teststandard',record.teststandard);
    tools.SetValue(mep+'judgestandard',record.judgestandard);
    tools.SetValue(mep+'paramunit',record.paramunit);
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskFile.do?btf.tranid=') + tools.GetValue(mep + 'accsampleid');
      me.plDetailGrid.store.load();
    };
    
    if (me.partsGrid && me.partsGrid.store) {
      me.partsGrid.store.proxy.url = tools.GetUrl('LabGetListBusSampleParamByAcc.do?bsp.samplecode='+ tools.GetValue(mep + 'sampleid')+'&bsp.tranid='+ tools.GetValue(mep + 'tranid')) ;
      me.partsGrid.store.load();
    };
  },
  
  OnGetSaveParams: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var details = [];
    for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      details.push(me.plDetailGrid.store.getAt(i).data);
    }

    var partsdetails = [];
    for (var i = 0; i < me.partsGrid.store.getCount(); i++) {
      partsdetails.push( me.partsGrid.store.getAt(i).data);
    }
    me.saveParams = { details: Ext.encode(details), partsdetails: Ext.encode(partsdetails) };
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusAccSampleByTranID.do?bas.sampletype='+record.sampletype+'&bas.tranid='+record.tranid);
//    var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode='+record.samplecode);
    var userinfo = gpersist.GetUserInfo();
    if(record.flowstatus == '89'){
	      tools.alert('撤销原因:<br/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp'+record.modifydesc);
	      return false;
	    }
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplecode', item.samplecode);
      tools.SetValue(mep + 'samplestatus', item.samplestatus);
      tools.SetValue(mep + 'samplename', item.samplename);
//      tools.SetValue(mep + 'samplestand', busgetdetail.samplestand);
      tools.SetValue(mep + 'samplestand', item.samplestand);
      tools.SetValue(mep + 'labid', gpersist.SELECT_MUST_VALUE);
      tools.SetValue(mep + 'teststandardname', item.teststandardname);
      tools.SetValue(mep + 'testprop', item.testprop);
      tools.SetValue(mep + 'senduser', userinfo.user.userid);
      tools.SetValue(mep + 'senddate', item.senddate == null ? new Date() : item.senddate);
      tools.SetValue(mep + 'reqdate', item.reqdate == null ? new Date() : item.reqdate);
      tools.SetValue(mep + 'accsampleid', record.tranid);
      tools.SetValue(mep + 'tranid', record.tranid);
      tools.SetValue(mep + 'issend', record.issend);
      tools.SetValue(mep + 'sampletype', record.sampletype);
      
      if(record.issend){
        me.OnDetailRefresh();
      } else{
        me.OnInitParam(record.gettype);
        
        if (me.plDetailGrid && me.plDetailGrid.store) {
          me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskFile.do?btf.tranid=') + tools.GetValue(mep + 'accsampleid');
          me.plDetailGrid.store.load();
        };
      }
      
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  OnCancel:function(){
	    var me = this;
	    var mep = me.tranPrefix;
	    var datas = me.plGrid.getView().getSelectionModel().getSelection();
	  
	    if(datas.length<1){
	    	tools.alert('请选择任务单........');
		      return false;
	    	
	    }else if (datas.length>1){
	    	tools.alert('请选择单项任务单........');
		      return false;
	    }else{
         var flowstatus = me.plGrid.selModel.selected.items[0].data.flowstatus;
	    	
	    	if(flowstatus == '89'){
			      tools.alert('该任务单已撤销........');
			      return false;
			    }
	    	if(flowstatus == '85'){
			      tools.alert('该任务单还未下达，无需撤销........');
			      return false;
			    }
	    	 me.OnCreateCheckWin();
			    if(me.winCheck){            
			      me.winCheck.show();
			    };
			    
			    tools.SetValue(mep + 'datadeal', '9');
	    }
	    
	   
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
		      ' ', { id: mep + 'btnCheckSave', text: '提交', iconCls: 'icon-save', handler: me.OnSingleCancel, scope: me },
		      '-', ' ', { id: mep + 'btnCheckClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler:function () { me.winCheck.hide(); me.detailEditType = 1;}}
		    ];
		    
		    me.editCheckControls = [
		      tools.FormTextArea('', 'checkdesc', mep + 'wincheckdesc', 200, '100%', true, 18,12)
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
		      tbar : Ext.create('Ext.toolbar.Toolbar', {items: checkitems}),
		      items: me.editCheckControls    
		    });
		    
		    me.winCheck = Ext.create('Ext.Window', {
		      id: mep + 'checkwin',
		      title: '撤销理由',
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
		  OnSingleCancel : function(action) {
			    var me = this;
			    var mep = me.tranPrefix;
			    var datas = me.plGrid.getView().getSelectionModel().getSelection();
			    
			    
//			    for (var i = 0; i < datas.length; i++) {
			    	var tranid = me.plGrid.selModel.selected.items[0].data.tranid;
			    	var datadeal = tools.GetValue(mep+'datadeal');
			    	var tranid = me.plGrid.selModel.selected.items[0].data.tranid;
			    	var modifydesc = tools.GetValue(mep+'wincheckdesc')
//			        if(datadeal == '9'){
//			       
//			          tools.SetValue(mep+'flowstatus','89');
//			        }
			    	
			        
			    	var param = tools.JsonGet('LabCancelBusTaskSingle.do?bt.tranid='+tranid+'&bt.modifydesc='+modifydesc);
			    	console.log(param+'param')
			    	
			    		tools.alert(param.msg);
//			  	    }
			    
			    me.winCheck.hide();
			  },
	  

  OnBeforeReset: function () {
	    return true;
	  },

OnResetForm: function () {
  var me = this;
  
  if (!me.OnBeforeReset())
    return;
  
  me.OnFormLoad(me.mid);
},
  
  OnInitParam:function(gettype){
    var me = this;
    var mep = me.tranPrefix;
    var sampleid = tools.GetValue(mep + 'sampleid');
    
    var param = tools.JsonGet('BasGetListBasSampleReplaceBySampleID.do?bsr.sampleid='+sampleid);
    
    me.partsGrid.store.removeAll();
    for(var i = 0; i < param.data.length; i++){
      var record = me.partsGrid.store.model.create({});
      record.data.parametername = param.data[i].parametername;
      record.data.testjudge = param.data[i].testjudge
      record.data.standvalue = param.data[i].standvalue;
      record.data.teststandardcode = param.data[i].teststandardcode;
      record.data.judgestandardcode = param.data[i].judgestandardcode;
      record.data.samplecode = param.data[i].samplecode;
      record.data.sampleid = param.data[i].sampleid;
      record.data.parameterid = param.data[i].parameterid;
      record.data.teststandard = param.data[i].teststandard;
      record.data.judgestandard = param.data[i].judgestandard;
      record.data.paramunit = param.data[i].paramunit;
      me.partsGrid.store.insert(me.partsGrid.store.getCount(), record);
    }
  },
  
  OnBeforeSave: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var issend = tools.GetValue(mep+'issend');
    if(issend == 'true'){
      tools.alert("该任务单已下达！");
      return false;
    }
//    else if(me.plDetailGrid.store.getCount() == 0){
//      tools.alert("多样品任务单分配明细不能为空......");
//      return false;
//    }
    else{
      return true;
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
//    me.OnDetailRefresh();
  },
  
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit.height = '15%';
  },
  
  OnAuthDetailEditFormParts : function(type,islayout) {
    var me = this;
    var mep = this.tranPrefix;
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disPartsDetailControls, me.enPartsDetailControls, mep);        
        tools.BtnsDisable(['btnPartsDetailSave'], mep);     
        break;
      
      default:
        tools.FormInit(me.disPartsDetailControls, me.enPartsDetailControls, mep);
        tools.BtnsEnable(['btnPartsDetailSave'], mep);
        break;
    }
  },
  
//页面空间授权处理
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnPartsAdd','btnPartsDelete','btnDetailDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnPartsAdd','btnPartsDelete','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnPartsAdd','btnPartsDelete','btnDetailDelete'], mep);
        break;
        
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  }
  
});
