Ext.define('alms.bassamplereplace',{
  extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'样品检测信息',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_bassamplereplace',
          storeUrl:'BasSearchBasSampleReplace.do',
          saveUrl:'BasSaveBasSampleReplace.do',
          expUrl:'BasSearchBasSampleReplace.do',
          idPrevNext:'parameterorder',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasSubmit: true,
          deleteUrl: 'BasDeleteBasSampleReplace.do'
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
         { xtype: 'textfield', fieldLabel: '样品主类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemainname', id: mep + 'searchsamplemainname', allowBlank: true },               
         { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
         { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchparameter', id: mep + 'searchparameter', allowBlank: true }
     ];
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
        ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
  OnBeforeCreateEdit:function(){
     var me = this;
     var mep = this.tranPrefix;
     var nowdate = new Date();
     
     var sampleitems = [
         ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsample', id: mep + 'searchsample', allowBlank: true },
         ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me 
         }];
     
     var samplename = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'bsr.samplename', id: mep + 'samplename', winTitle: '选择样品',
      maxLength: 40, maxLengthText: '样品名称不能超过30个字符！', selectOnFocus: false, labelWidth: 95,
      blankText: '样品名称不能为空！', allowBlank: false, anchor: '96%', tabIndex: 5,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
     samplename.on('griditemclick', me.OnSampleSelect, me);
    
    var url = '';
    url = 'BasGetListBasSampleParameterDetail.do'
    me.samParStore = new Ext.data.JsonStore({
     proxy:{
       type:'ajax',
       url:url,
       reader:{
         type:'json',
         root:'data'
       }
     },
     fields:[{name:'id',mapping:'parameterid'},{name:'name',mapping:'parametername'}],
     autoLoad:true
   });
    
    var samParCombo =tools.FormCombo('检测参数', 'bsr.parameterid', mep + 'parameterid', me.samParStore,'100%', false, 6,90);
    
    samParCombo.on('select',me.OnSelectParameter,me);
    
    //检测方法
    var testitems = [
       ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 70, width: 220, maxLength: 40, name: 'searchteststandardname', id: mep + 'searchteststandardname', allowBlank: true },
       ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTestSearch, scope: me }
    ];
    
    var teststandard = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '检测方法名称', name: 'bsr.teststandardname', id: mep + 'teststandardname', winTitle: '选择检测方法',
      maxLength: 100, maxLengthText: '检测方法不能超过100个字符！', selectOnFocus: false, labelWidth: 95,
      blankText: '检测方法不能为空！', allowBlank: true, anchor: '96%', tabIndex: 5,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bastest',
      storeUrl: 'BasSearchBasTest.do',
      editable:false,
      searchTools:testitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    teststandard.on('griditemclick', me.OnTestSelect, me);

    //判定依据
    var judgeitems = [
      ' ', { xtype: 'textfield', fieldLabel: '判定方法名称', labelWidth: 100, width: 220, maxLength: 40, name: 'searchjudgestandardname', id: mep + 'searchjudgestandardname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnJudgeSearch, scope: me }
    ];
    
    var judgestandard = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '判定依据名称', name: 'bsr.judgestandardname', id: mep + 'judgestandardname', winTitle: '选择判定依据',
      maxLength: 100, maxLengthText: '判定依据不能超过100个字符！', selectOnFocus: false, labelWidth: 95,
      blankText: '判定依据不能为空！', allowBlank: true, anchor: '96%', tabIndex: 5,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basjudge',
      storeUrl: 'BasSearchBasJudge.do',
      editable:false,
      searchTools:judgeitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    judgestandard.on('griditemclick', me.OnJudgeSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            samplename,
//            tools.FormCombo('判定方法', 'bsr.testjudge', mep + 'testjudge', tools.ComboStore('TestJudge', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90),
            tools.FormCombo('判定方法', 'bsr.testjudge', mep + 'testjudge', tools.ComboStore('JudgeType', gpersist.SELECT_MUST_VALUE), '96%', true, 3,95),
            teststandard,
            tools.FormText('检测限', 'bsr.deteclimit', mep + 'deteclimit', 20, '96%', true,2,null,95)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
            tools.FormText('样品主类名称', 'bsr.samplemainname', mep + 'samplemainname', 20, '96%', false,1,null,95),
            tools.FormText('标准值', 'bsr.standvalue', mep + 'standvalue', 200, '96%', true,2,null,95),
            judgestandard
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
            samParCombo,
            tools.FormText('参数单位', 'bsr.paramunit', mep + 'paramunit', 20, '100%', true,2,null,90),
            tools.FormText('检测参数排序', 'bsr.parameterorder', mep + 'parameterorder', 20, '100%', false,2,'isnumber',90)
           ]}
         ]},
         { xtype: 'fieldset', id:mep+'gtstand', hidden:false, collapsible: true, title: '规格型号', anchor: '100%', height:150, items: [
         ]},
         {xtype:'hiddenfield',name:'bsr.samplemain',id: mep + 'samplemain'},
         {xtype:'hiddenfield',name:'bsr.sampleid',id: mep + 'sampleid'},
         {xtype:'hiddenfield',name:'bsr.parametername',id: mep + 'parametername'},
         {xtype:'hiddenfield',name:'bsr.teststandard',id: mep + 'teststandard'},
         {xtype:'hiddenfield',name:'bsr.teststandardcode',id: mep + 'teststandardcode'},
         {xtype:'hiddenfield',name:'bsr.judgestandard',id: mep + 'judgestandard'},
         {xtype:'hiddenfield',name:'bsr.judgestandardcode',id: mep + 'judgestandardcode'},
         {xtype:'hiddenfield',name:'bsr.deal.action',id: mep + 'datadeal'}
     ];
     me.disNews = [];
     me.disEdits = ['samplemainname','samplename','parameterid'];
     me.enNews = ['samplename','teststandardname','judgestandardname','samplemainname','testjudge','standvalue','paramunit','parameterid','parameterorder','deteclimit'],
     me.enEdits = ['teststandardname','judgestandardname','testjudge','standvalue','paramunit','parameterid','parameterorder','deteclimit'];
   },
   
   OnSampleSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     
     var sample = Ext.getCmp(mep+'samplename');
     
     if(sample.store){
       sample.store.on('beforeload', function (store, options) {   
         Ext.apply(store.proxy.extraParams, {
           'bsample.samplename': tools.GetValueEncode(mep + 'searchsample')
         });
       });
     }
     sample.store.loadPage(1);
   },
   
   OnSampleSelect : function(render, item){
     var me = this;
     var mep = me.tranPrefix;
     var sampleid = item.sampleid;

     if(item && !Ext.isEmpty(item.sampleid)){
       tools.SetValue(mep + 'sampleid', item.sampleid);
       tools.SetValue(mep + 'samplemain', item.samplemain);
       tools.SetValue(mep + 'samplename', item.samplename);
       tools.SetValue(mep + 'samplemainname', item.samplemainname);
       tools.SetValue(mep + 'parameterid', '');
       //规格型号
       me.OnGetStand();
       
       me.samParStore.load({params:{sampleid:sampleid}});
       
       var parameterid = Ext.getCmp(mep + 'parameterid');        
       if (parameterid) {
         parameterid.reset();
       }
     } else{
       tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
     }
   },
   
   OnSelectParameter: function(){
     var me = this;
     var mep = me.tranPrefix;
     
     var parametername = Ext.getCmp(mep + 'parameterid').getDisplayValue();
     tools.SetValue(mep + 'parametername', parametername);
   },
   
   OnGetStand:function(){
     var me = this;
     var mep = me.tranPrefix;
     
     var sampleid = tools.GetValue(mep + 'sampleid');
     var stand = Ext.getCmp(mep + 'gtstand');
     me.formdatas = [];
     stand.removeAll();
     var stands  = tools.JsonGet('BasGetSampleStandCount.do?bss.sampleid=' + sampleid);
     var idx = 130;
     for(var i = 0; i < stands.samplecount; i++){
       var standlabel = '';
       var standid = '';
       var typedata = tools.GetTypeList();
       var items = tools.JsonGet('BasGetSampleStandByLevel.do?bss.sampleid=' + sampleid + '&bss.standlevel=' + (i+1));
       if (items && items.data){
         standlabel = items.data[0].standlabel;
         standid = items.data[0].standid;
         Ext.each(items.data, function (item, index) { 
           typedata.push({ id: item.standtype, name: item.standtypename });
         });
       }
       
       var item = tools.FormCombo(standlabel,'bsr.'+standid, mep + standid, tools.GetNullStore(), '45%', true, null,80);
       item.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: typedata}));
       item.setValue(gpersist.SELECT_MUST_VALUE); 
       stand.add(item);
       me.formdatas.push(standid);
     }
   },
   
   OnTestSelect : function(render, item){
     var me = this;
     var mep = me.tranPrefix;

     if(item && !Ext.isEmpty(item.teststandard)){
       tools.SetValue(mep + 'teststandardname', item.teststandardname);
       tools.SetValue(mep + 'teststandardcode', item.teststandardcode);
       tools.SetValue(mep + 'teststandard', item.teststandard);
     } else{
       tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
     }
   },
   
   OnJudgeSelect : function(render, item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && !Ext.isEmpty(item.judgestandard)){
       tools.SetValue(mep + 'judgestandardname', item.standardnum);
       tools.SetValue(mep + 'judgestandardcode', item.judgestandardcode);
       tools.SetValue(mep + 'judgestandard', item.judgestandard);
     
     } else{
       tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
     }
   },
   
   OnTestSearch: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     var test = Ext.getCmp(mep + 'teststandardname');
     me.OnTestBeforeLoad();
     test.store.loadPage(1);    
   },
   
   OnTestBeforeLoad: function () {    
     var me = this;
     var mep = me.tranPrefix;
     
     var test = Ext.getCmp(mep + 'teststandardname');
     if (test.store) {      
       test.store.on('beforeload', function (store, options) {      
         Ext.apply(store.proxy.extraParams, {
           'btest.teststandardname': tools.GetValueEncode(mep + 'searchteststandardname') 
         });
       });
     }
   },
   
   OnJudgeSearch: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     var judge = Ext.getCmp(mep + 'judgestandardname');
     me.OnJudgeBeforeLoad();
     judge.store.loadPage(1);    
   },
   
   OnJudgeBeforeLoad: function () {    
     var me = this;
     var mep = me.tranPrefix;
     
     var judge = Ext.getCmp(mep + 'judgestandardname');
     if (judge.store) {      
       judge.store.on('beforeload', function (store, options) {      
         Ext.apply(store.proxy.extraParams, {
           'bjudge.judgestandardname': tools.GetValueEncode(mep + 'searchjudgestandardname') 
         });
       });
     }
   },
   
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
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
   
   OnBeforeSearch: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.gridStore) {
       me.gridStore.on('beforeload', function (store, options) {      
         Ext.apply(store.proxy.extraParams, {
           'bsr.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
           'bsr.samplemainname': tools.GetEncode(tools.GetValue(mep + 'searchsamplemainname')),
           'bsr.parametername': tools.GetEncode(tools.GetValue(mep + 'searchparameter'))
         });
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;

    tools.SetValue(mep + 'samplemain', item.samplemain);
    tools.SetValue(mep + 'samplemainname', item.samplemainname);
    tools.SetValue(mep + 'sampleid', item.sampleid);
    tools.SetValue(mep + 'samplename', item.samplename);
    
    me.samParStore.load({url:'BasGetListBasSampleParameterDetail.do',params:{sampleid:item.sampleid}});
    
    tools.SetValue(mep + 'parameterid', item.parameterid);
    
    var parameter = tools.JsonGet('BasGetBasParameter.do?bpara.parameterid='+item.parameterid);
    
    tools.SetValue(mep + 'parametername', parameter.parametername);
    tools.SetValue(mep + 'testjudge', item.testjudge);
    tools.SetValue(mep + 'standvalue', item.standvalue);
    tools.SetValue(mep + 'paramunit', item.paramunit);
    tools.SetValue(mep + 'judgestandard', item.judgestandard);
    tools.SetValue(mep + 'judgestandardcode', item.judgestandardcode);
    tools.SetValue(mep + 'judgestandardname', item.judgestandardname);
    tools.SetValue(mep + 'teststandard', item.teststandard);
    tools.SetValue(mep + 'teststandardcode', item.teststandardcode);
    tools.SetValue(mep + 'teststandardname', item.teststandardname);
    
    me.OnGetStand();
    
    tools.SetValue(mep + 'standtype1',item.standtype1);
    tools.SetValue(mep + 'standtype2',item.standtype2);
    tools.SetValue(mep + 'standtype3',item.standtype3);
    tools.SetValue(mep + 'standtype4',item.standtype4);
    tools.SetValue(mep + 'standtype5',item.standtype5);
    tools.SetValue(mep + 'standtype5',item.standtype5);
    tools.SetValue(mep + 'parameterorder',item.parameterorder);
    tools.SetValue(mep + 'deteclimit',item.deteclimit);
    return true;
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
   
   OnBeforeDelete: function (datas) {
     var me = this;
     
     var json = [];
     for (var i = 0; i < datas.length; i++) {
       json.push(datas[i].data);
     }

     me.deleteParams = {deletes : Ext.encode(json)};
     
     return true;
   },
   
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
         tools.BtnsDisable(['btnDetailAdd','btnDetailDelete'], mep);
         tools.Disabled(me.formdatas, mep);
         break;
       
       case gpersist.DATA_DEAL_NEW:
         tools.FormInit(me.disNews, me.enNews, mep);
         tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
         tools.BtnsEnable(['btnSave'], mep);
         tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
         tools.Enabled(me.formdatas, mep);
         break;
       
       case gpersist.DATA_DEAL_EDIT:
         tools.FormInit(me.disEdits, me.enEdits, mep);
         tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
         tools.BtnsEnable(['btnSave'], mep);
         tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
         tools.Disabled(me.formdatas, mep);
         break;
     }
     
     me.plEdit.updateLayout();
     
     if (islayout) {
       me.plEdit.resumeLayouts(true);
       me.plEdit.doLayout();
     }
   }
   
});