Ext.define('alms.bassample', {
  extend: 'gpersist.base.busform',
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editInfo: '样品',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      saveUrl: 'BasSaveBasSample.do',
      expUrl: 'BasSearchBasSample.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'sampleid',
      hasGridSelect: true,
      hasEdit: true,
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '检测参数',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bassampleparameter',
      urlDetail: 'BasGetListBasSampleDetail.do',
      detailTabs: 2,
      hasDateSearch: true,
      hasDetailEdit: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  selectparameter: null,
  tests: null,
  judges: null,
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.selectparameter = null;
    me.tests = Ext.create('Ext.util.MixedCollection');
    me.judges = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var sampitems = [
       ' ', { xtype: 'textfield', fieldLabel: '样品主类编号', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemain', id: mep + 'searchsamplemain', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '样品主类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemainname', id: mep + 'searchsamplemainname', allowBlank: true },
       ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSamSearch, scope: me }
     ];
     
     var samplemainname = Ext.create('Ext.ux.GridPicker', {
       fieldLabel: '所属样品主类', name: 'bsample.samplemainname', id: mep + 'samplemainname', winTitle: '选择样品主类',
       maxLength: 20, maxLengthText: '样品主类不能超过20！', selectOnFocus: false, labelWidth: 90,
       blankText: '样品主类为空！', allowBlank: false, anchor: '96%', tabIndex: 3,
       columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassamplemain',
       storeUrl: 'BasSearchBasSampleMain.do',
       editable:false,
       searchTools:sampitems,
       hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
     
     samplemainname.on('griditemclick', me.OnCatalogSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品编号', 'bsample.sampleid', mep + 'sampleid', 10, '96%', true, 1,'',90),
          samplemainname,
          tools.FormCombo('样品简称', 'bsample.code', mep + 'code', tools.ComboStore('Code', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('样品名称', 'bsample.samplename', mep + 'samplename', 30, '100%', true, 2,'',90),
          tools.FormText('所属样品大类', 'bsample.samplecatalogname', mep + 'samplecatalogname', 20, '100%', false, 4,'',90),
          {xtype:'hiddenfield',name:'bsample.samplecatalog',id: mep + 'samplecatalog'},
          {xtype:'hiddenfield',name:'bsample.samplemain',id: mep + 'samplemain'},
          {xtype:'hiddenfield',name:'bsample.deal.action',id: mep + 'datadeal'}
        ]}     
      ]}
    ];
    
    me.disNews = ['samplecatalogname','sampleid'];
    me.disEdits = ['samplecatalogname','sampleid'];
    me.enNews = [ 'samplemainname', 'samplemain', 'samplename','code'];
    me.enEdits = ['samplemainname', 'samplemain', 'samplename','code'];
  },
  
  OnCatalogSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.samplemain){
      tools.SetValue(mep+"samplecatalog",item.samplecatalog);
      tools.SetValue(mep+"samplecatalogname",item.samplecatalogname);
      tools.SetValue(mep+"samplemain",item.samplemain);
      tools.SetValue(mep+"samplemainname",item.samplemainname);
    }
    
  },
  
  OnSamSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnSamBeforeLoad();
    var samplemainname = Ext.getCmp(mep + 'samplemainname');
    samplemainname.store.loadPage(1);
    
  },
 
  OnSamBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var samplemainname = Ext.getCmp(mep + 'samplemainname');
    
    if(samplemainname.store){
      samplemainname.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'bsmain.samplemain':tools.GetValueEncode(mep + 'searchsamplemain'),
            'bsmain.samplemainname':tools.GetValueEncode(mep + 'searchsamplemainname')
          });
      });
    }
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品主类名称', labelWidth: 90, width: 200, maxLength: 40, name: 'searchsamplemains', id: mep + 'searchsamplemains', allowBlank: true },    
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bsample.sampleid': tools.GetEncode(tools.GetValue(mep + 'searchsampleid')),
          'bsample.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bsample.samplemainname':tools.GetValueEncode(mep + 'searchsamplemains')
        });
      });
    }
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'sampleid').reset();
    Ext.getCmp(mep + 'sampleid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.sampleid) {
          tools.SetValue(mep + 'sampleid', action.result.data.sampleid);
        }
      }
    }
  },
  
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet(tools.GetUrl('BasGetBasSample.do?bsample.sampleid=') + record.sampleid);
    
    if(item){
      if (item && !Ext.isEmpty(item.sampleid)) {
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplecatalog', item.samplecatalog);
      tools.SetValue(mep + 'samplemain', item.samplemain);
      tools.SetValue(mep + 'samplecatalogname', item.samplecatalogname);
      tools.SetValue(mep + 'samplemainname', item.samplemainname);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'code', item.code);
      }
      me.OnDetailRefresh();
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    me.winWidth=600,
    me.winHeight=260,
    me.OnInitGridToolBar();
    
    var paritems = [
      ' ', { xtype: 'textfield', fieldLabel: '检测参数名称', labelWidth: 100, width: 220, maxLength: 12, name: 'searchparametercn', id: mep + 'searchparametercn', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnParameterSearch, scope: me }
    ];
    
    var param = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: tools.MustTitle('参数编号'), name: 'parameterid', id: mep + 'parameterid', winTitle: '检测参数',
      maxLength: 6, maxLengthText: '检测参数编号长度不能超过6个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '检测参数编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 101, editable: false,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basparameter',
      storeUrl: 'BasSearchBasParameter.do',
      hasPage: true, pickerWidth: 600, pickerHeight: 450, searchTools: paritems
    });
    
    me.editDetailControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          param
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('参数名称', 'parametername', mep + 'parametername', 50, '100%', false, 102)
        ]}
      ]}
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['parameterid', 'parametername'];
    
    param.on('griditemclick', me.OnParameterSelect, me);
    param.on('gridbeforeload', me.OnParameterBeforeLoad, me);
  },
  
  OnParameterSelect: function (render, item) {
    var me = this;
    var mep = this.tranPrefix;
    
    if (item && !Ext.isEmpty(item.parameterid)) {
      tools.SetValue(mep + 'parameterid', item.parameterid);
      tools.SetValue(mep + 'parametername', item.parametername);
    }
  },
  
  OnParameterSearch: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnParameterBeforeLoad();
    
    var param = Ext.getCmp(mep + 'parameterid');
    
    param.store.loadPage(1);
  },
  
  OnParameterBeforeLoad: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var param = Ext.getCmp(mep + 'parameterid');

    if (param.store) {      
      param.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bpara.parametername': tools.GetValueEncode(mep + 'searchparametercn') 
        });
      });
    }
  },
  
  OnListNew: function(){
    var me = this;
    me.OnCreateDetailWin();
    if(me.winDetail){
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnAuthDetailEditForm(false);
    };
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        record.data.parameterid = tools.GetValue(mep+'parameterid');
        record.data.parametername = tools.GetValue(mep+'parametername');
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      } else {
        me.OnBeforeListSave(me.detailRecord);
        me.plDetailGrid.getView().refresh();
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
  
  //添加样品规格型号
  OnAfterCreateDetail: function () {
      var me = this, mep = this.tranPrefix;
      
      var partsColumn = [];
      var partsField = [];    

      tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_bassamplestand'), partsColumn, partsField, mep + '_a_');

      me.partsStore = tools.CreateGridStore(tools.GetUrl('BasGetListBasSampleStand.do'), partsField);
      
      me.partsGrid = Ext.create('Ext.grid.Panel', {
        region : 'center',
        title : '样品规格型号',
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
      me.OnInitPartDetailData();
      me.OnAuthDetailEditFormParts(me.dataDeal,true)
    };
   
  },
  
  OnInitPartDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
//    var number = me.partsGrid.store.getCount() + 1;
//    tools.SetValue(mep + 'standlevel', number);
//    tools.SetValue(mep + 'standlabel', '规格型号'+number);
//    tools.SetValue(mep + 'standid', 'standtype'+number);
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
       title: '设备建档配件',
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
    var nowdate = new Date(); 
   
    me.OnInitGridToolBar();
    me.editPartsDetailControls = [
       { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormCombo('规格种类编号', 'standlevel', mep + 'standlevel', tools.ComboStore('StandKind', gpersist.SELECT_MUST_VALUE), '96%', false, 4,100),
            tools.FormText('规格标签名称', 'standlabel', mep + 'standlabel', 40, '96%', false, 3,null,100)
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormCombo('样品规格', 'standtype', mep + 'standtype', tools.ComboStore('StandType', gpersist.SELECT_MUST_VALUE), '100%', false, 4,100),
            tools.FormText('样品规格编码', 'standid', mep + 'standid', 20, '100%', false, 2,null,100),
            { xtype: 'hiddenfield', name: 'standtype', id: mep + 'standtypename' }
          ]}                                                                 
       ]}
    ];
    me.disPartsDetailControls = ['standlabel','standid'];
    me.enPartsDetailControls = ['standlevel','standtype'];
    
    Ext.getCmp(mep+'standlevel').on('select',me.OnStandLevel,me)
 }, 
 
 OnStandLevel: function(){
   var me = this;
   var mep = me.tranPrefix;
   
   var standlevel = tools.GetValue(mep+'standlevel');
   tools.SetValue(mep+'standlabel','规格型号'+standlevel);
   tools.SetValue(mep+'standid','standtype'+standlevel);
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
     record.data.standlevel =  Ext.getCmp(mep + 'standlevel').getValue();
     record.data.standlabel = Ext.getCmp(mep + 'standlabel').getValue();
     record.data.standtype = Ext.getCmp(mep + 'standtype').getValue();
     record.data.standid = Ext.getCmp(mep + 'standid').getValue();
     record.data.standtypename = Ext.getCmp(mep + 'standtype').getDisplayValue();;
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
  
  OnLoadDetailDataParts: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'standlevel', record.standlevel);
    tools.SetValue(mep + 'standlabel', record.standlabel);
    tools.SetValue(mep + 'standtype', record.standtype);
    tools.SetValue(mep + 'standid', record.standid);
    tools.SetValue(mep + 'standtype', record.standtype);
    return true;
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;

    if (me.plDetailGrid && me.plDetailGrid.store) {

      me.plDetailGrid.store.proxy.url = tools.GetUrl('BasGetListBasSampleDetail.do?bsampara.sampleid=') + tools.GetValue(mep + 'sampleid');
      me.plDetailGrid.store.load();
    };
    
    if (me.partsGrid && me.partsGrid.store) {
      me.partsGrid.store.proxy.url = tools.GetUrl('BasGetListBasSampleStand.do?bss.sampleid=') + tools.GetValue(mep + 'sampleid');
      me.partsGrid.store.load();
   }
  },
  
  
  
  OnLoadDetailData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'parametername',record.parametername);
    tools.SetValue(mep+'parameterid',record.parameterid);
  },
  
  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.parametername = tools.GetValue(mep+'parametername');
    record.data.parameterid = tools.GetValue(mep+'parameterid');
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
  }
  
  //新增检测参数
//  OnListNew: function() {
//    var me = this;
//    
//    if (!me.selectparameter) {
//      me.selectparameter = tools.GetPopupWindow('alms', 'selectparameter', 
//        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'para', dataDeal: gpersist.DATA_DEAL_NEW})
//      
//      me.selectparameter.on('formlast', me.OnParameterSave, me);
//      me.selectparameter.OnFormLoad();
//    }
//    else
//      me.selectparameter.OnFormShow();
//      
//    me.selectparameter.OnInitData(me.tests, me.judges);
//    me.selectparameter.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
//    
//    me.detailRecord = null;
//  },
//  
//  OnListSelect: function(e, record, item, index) {
//    var me = this, mep = me.tranPrefix;
//    
//    if (!me.selectparameter) {
//      me.selectparameter = tools.GetPopupWindow('alms', 'selectparameter', 
//        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'para', dataDeal: me.dataDeal})
//      
//      me.selectparameter.on('formlast', me.OnParameterSave, me);
//      me.selectparameter.OnFormLoad();
//    }
//    else
//      me.selectparameter.OnFormShow();
//    
//    me.selectparameter.OnSetData(record, tools.GetValue(mep + 'sampleid'), me.tests, me.judges);
//    me.selectparameter.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
//    
//    me.detailRecord = record;
//  },
//  
//  //ondetailrefresh
//  OnDetailRefresh : function() {
//    var me = this, mep = me.tranPrefix;
//    
//    if (me.plDetailGrid && me.plDetailGrid.store) {
//      me.plDetailGrid.store.on('beforeload', function (store, options) {      
//        Ext.apply(store.proxy.extraParams, {
//          'bsampara.sampleid': tools.GetValue(mep + 'sampleid') 
//        });
//      });
//      
//      me.plDetailGrid.store.load();
//    }
//  },
//  
//  OnSetData: function (id, record) {
//    var me = this, mep = me.tranPrefix, i = 0;
//
//    var item = tools.JsonGet(tools.GetUrl('BasGetBasSample.do?bsample.sampleid=') + id);
//    
//    if (item && !Ext.isEmpty(item.sampleid)) {
//      tools.SetValue(mep + 'sampleid', item.sampleid);
//      tools.SetValue(mep + 'samplecatalog', item.samplecatalog);
//      tools.SetValue(mep + 'samplemain', item.samplemain);
//      tools.SetValue(mep + 'samplecatalogname', item.samplecatalogname);
//      tools.SetValue(mep + 'samplemainname', item.samplemainname);
//      tools.SetValue(mep + 'samplename', item.samplename);
//      tools.SetValue(mep + 'code', item.code);
//      
//      me.tests.clear();
//      
//      var dtests = tools.JsonGet(tools.GetUrl('BasGetListBasSampleTest.do?bst.sampleid=') + id);
//      if (dtests && dtests.data) {
//        for (i = 0; i < dtests.data.length; i++) {
//          var test = dtests.data[i];
//          
//          if (me.tests.get(test.parameterid) != null) {
//            me.tests.get(test.parameterid).push({teststandard: test.teststandard, 
//              teststandardname: test.teststandardname, teststandardcode: test.teststandardcode
//            });
//          }
//          else {
//            me.tests.add(test.parameterid, [{teststandard: test.teststandard, 
//              teststandardname: test.teststandardname, teststandardcode: test.teststandardcode
//            }]);
//          }
//        }
//      }
//      
//      me.judges.clear();
//      
//      var djudges = tools.JsonGet(tools.GetUrl('BasGetListBasSampleJudge.do?bsj.sampleid=') + id);
//
//      if (djudges && djudges.data) {
//        for (i = 0; i < djudges.data.length; i++) {
//          var judge = djudges.data[i];
//          
//          if (me.judges.get(judge.parameterid) != null) {
//            me.judges.get(judge.parameterid).push({judgestandard: judge.judgestandard, 
//              judgestandardname: judge.judgestandardname, judgestandardcode: judge.judgestandardcode
//            });
//          }
//          else {
//            me.judges.add(judge.parameterid, [{judgestandard: judge.judgestandard, 
//              judgestandardname: judge.judgestandardname, judgestandardcode: judge.judgestandardcode
//            }]);
//          }
//        }
//      }
//      
//      me.OnDetailRefresh();
//      
//      return true;
//    }
//    else {
//      me.dataDeal == gpersist.DATA_DEAL_SELECT;
//      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
//    }
//  },
//  
//  OnParameterSave: function (e, data) {
//    var me = this;
//    var mep = me.tranPrefix;
//    var record = me.detailRecord;
//    
//    if (record == null) record = me.plDetailGrid.store.model.create({});
//
//    record.data.parameterid = data.parameterid;
//    record.data.parametername = data.parametername;
//    record.data.testjudge = data.testjudge;
//    record.data.paramunit = data.paramunit;
//    record.data.belongtype = data.belongtype;
//    record.data.belongtypename = data.belongtypename;
//    record.data.standvalue = data.standvalue;
//    if (me.detailRecord)
//      me.plDetailGrid.getView().refresh();
//    else
//      me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record); 
//  },
//  
//  OnGetSaveParams : function() {
//    var me = this, mep = me.tranPrefix;
//    
//    var params = [];
//    var stests = [];
//    var sjudges = [];
//    var i = 0, j = 0;
//    
//    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
//      params.push(me.plDetailGrid.store.getAt(i).data);
//     
//      var dtests = me.tests.get(me.plDetailGrid.store.getAt(i).data.parameterid);
//      if (dtests) {
//        for (j = 0; j < dtests.length; j++) {
//
//          stests.push({parameterid: me.plDetailGrid.store.getAt(i).data.parameterid, teststandard: dtests[j].teststandard })
//        }
//      }
//      
//      var djudges = me.judges.get(me.plDetailGrid.store.getAt(i).data.parameterid);
//      
//      if (djudges) {
//        for (j = 0; j < djudges.length; j++) {
//          sjudges.push({parameterid: me.plDetailGrid.store.getAt(i).data.parameterid, judgestandard: djudges[j].judgestandard })
//        }
//      }
//    }
//    
//    me.saveParams = { params: Ext.encode(params), tests: Ext.encode(stests), judges: Ext.encode(sjudges)};
//  }
  
});