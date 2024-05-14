Ext.define('alms.devacceptmanager', {
  extend: 'gpersist.base.busform',
  
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备验收',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devacceptmanage',
      storeUrl: 'DevSearchAcceptManage.do',
      saveUrl: 'DevSaveAcceptManage.do',
      hasGridSelect: true,
      expUrl: 'DevSearchAcceptManage.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '设备随机文件',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_devacceptfile',
      urlDetail: 'DevGetListAcceptFileDetail.do',
      detailTabs: 2,
      hasDateSearch: true,
      hasDetailEdit: true
    });
    me.callParent(arguments);
  },
  
  partsGrid:null,
  partsStore:null,
  PartsDetail:null,
  params:null,
  
  OnInitConfig:function(){
    var me = this;
    me.partsGrid = null;
    me.partsStore = null;
    me.PartsDetail = null;
    me.params = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var devitems = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevforacceptname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: this }
    ];
    
    var applyid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '申请编号', name: 'am.applyid', id: mep + 'applyid', winTitle: '选择设备',
      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devbuyapply',
      storeUrl: 'DevSearchBuyApplyForAccept.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    applyid.on('griditemclick', me.OnBuyApplySelect, me);
    
    var partsitems = [
      ' ', { xtype: 'textfield', fieldLabel: '供应商名称', labelWidth: 80, width: 180, maxLength: 40, name: 'searchtradename', id: mep + 'searchtradename', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTradeSearch, scope: this }
    ];
    
    var instaluser = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '安装调试人', name: 'am.instaluser', id: mep + 'instaluser', winTitle: '选择安装调试人',
      maxLength: 40, maxLengthText: '安装调试人不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '安装调试人不能为空！', allowBlank: false, anchor: '96%', tabIndex: 10,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustrade',
      storeUrl: 'DevSearchBusTrade.do',
      editable:true,
      searchTools:partsitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    instaluser.on('griditemclick', me.OnInstalUserSelect, me);
    
    var devmanager = tools.UserPicker(mep + 'devmanager','am.devmanager','设备管理员');
    
    devmanager.on('griditemclick', me.OnManagerSelect, me);
   
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'am.tranid', mep + 'tranid', 20, '96%', false, 1), 
          tools.FormText('设备名称', 'am.devname', mep + 'devname', 10, '96%', false, 4),
          tools.FormText('出厂编号', 'am.factorycode', mep + 'factorycode', 10, '96%', false, 7),
          instaluser,
          devmanager
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('设备编号', 'am.devid', mep + 'devid', 20, '96%', false, 2,null,90),
          tools.FormText('生产厂家', 'am.factoryname', mep + 'factoryname', 40, '96%', false, 5,null,90),  
          tools.FormText('价格(万元)', 'am.devprice', mep + 'devprice', 40, '96%', false, 8,'is15p2',90),
          tools.FormText('安装单位', 'am.instalunit', mep + 'instalunit', 40, '96%', false, 11,null,90),
          tools.FormText('管理员姓名', 'am.devmanagername', mep + 'devmanagername', 40, '96%', false, 11,null,90)
       ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          applyid,
          tools.FormText('设备型号', 'am.devstandard', mep + 'devstandard', 40, '100%', false, 6),
          tools.FormDate('进场日期', 'am.enterdate', mep + 'enterdate', 'Y-m-d', '100%', false, 9,nowdate),
          tools.FormDate('安装时间', 'am.instaldate', mep + 'instaldate', 'Y-m-d', '100%', false, 12,nowdate),
          tools.FormCheckCombo('验收人员', 'am.devoperators', mep + 'devoperators', tools.ComboStore('User'), '100%', true, 21),
          {xtype:'hiddenfield',name:'am.deptuser',id: mep + 'deptuser'},
          {xtype:'hiddenfield',name:'am.deptusername',id: mep + 'deptusername'},
          {xtype:'hiddenfield',name:'am.devoperatorsname',id: mep + 'devoperatorsname'},
          {xtype:'hiddenfield',name:'am.installpath',id: mep + 'installpath'},
          {xtype:'hiddenfield',name:'am.installsign',id: mep + 'installsign'},
          {xtype:'hiddenfield',name:'am.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('技术参数', 'am.devrange', mep + 'devrange', 200, '100%', true, 13, 4),
      tools.FormTextArea('安装情况', 'am.instaldesc', mep + 'instaldesc', 200, '100%', true, 14, 4),
      tools.FormTextArea('验收结论', 'am.acceptremark', mep + 'acceptremark', 200, '100%', true, 15, 4)
      
    ];
    me.disNews = ['devid', 'tranid','devname', 'devstandard','factoryname', 'devrange','devmanagername'];
    me.disEdits = ['devid', 'devname', 'devstandard','tranid', 'factoryname','factoryname', 'devrange','devmanagername'];
    me.enNews = [ 'applyid', 'devprice', 'enterdate', 'instaldate', 'instaldesc', 'instaluser', 'acceptremark', 'factorycode','devmanager','devoperators','instalunit'];
    me.enEdits = [ 'applyid', 'devprice', 'enterdate', 'instaldate', 'instaldesc', 'instaluser', 'acceptremark', 'factorycode','devmanager','devoperators','instalunit'];
    
    
    Ext.getCmp(mep + 'instaldate').on('select',function(){
      var instaldatetime = tools.GetValue(mep + 'instaldate').getTime();
      var enterdatetime = tools.GetValue(mep + 'enterdate').getTime();
      if(enterdatetime > instaldatetime){
        tools.alert('进厂时间不能大于安装时间！');
        Ext.getCmp(mep + 'instaldate').reset();
        return;
      }
    });
    
    Ext.getCmp(mep + 'enterdate').on('select',function(){
      var instaldatetime = tools.GetValue(mep + 'instaldate').getTime();
      var enterdatetime = tools.GetValue(mep + 'enterdate').getTime();
      if(enterdatetime > instaldatetime){
        tools.alert('进厂时间不能大于安装时间！');
        Ext.getCmp(mep + 'enterdate').reset();
        return;
      }
    }); 
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);

  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
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
          'am.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    if(record && !Ext.isEmpty(record.tranid)){
      
      var item = tools.JsonGet(tools.GetUrl('DevGetAcceptManage.do?am.tranid=') + record.tranid);
      
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'applyid', item.applyid);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'factorycode', item.factorycode);
      tools.SetValue(mep + 'devstandard', item.devstandard);
      tools.SetValue(mep + 'devprice', item.devprice);
      tools.SetValue(mep + 'factoryname', item.factoryname);
      tools.SetValue(mep + 'enterdate', item.enterdate);
      tools.SetValue(mep + 'devrange', item.devrange);
      tools.SetValue(mep + 'instalunit', item.instalunit);
      tools.SetValue(mep + 'instaldate', item.instaldate);
      tools.SetValue(mep + 'instaldesc', item.instaldesc);
      tools.SetValue(mep + 'instaluser', item.instaluser);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'acceptremark', item.acceptremark);
      tools.SetValue(mep + 'devmanager', item.devmanager);
      tools.SetValue(mep + 'devmanagername', item.devmanagername);
      tools.SetValue(mep + 'managerdate', item.managerdate);
      tools.SetValue(mep + 'officeuser', item.officeuser);
      tools.SetValue(mep + 'officeusername', item.officeusername);
      tools.SetValue(mep + 'officedate', item.officedate);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'deptuser', item.deptuser);
      tools.SetValue(mep + 'deptusername', item.deptusername);
      tools.SetValue(mep + 'deptdate', item.deptdate);
      tools.SetValue(mep + 'techuser', item.techuser);
      tools.SetValue(mep + 'techusername', item.techusername);
      tools.SetValue(mep + 'techdate', item.techdate);
      tools.SetValue(mep + 'installsign', item.installsign);
      tools.SetValue(mep + 'installpath', item.installpath);
      
      Ext.getCmp(mep + 'devoperators').setRawValue(item.devoperatorsname);
      me.OnDetailRefresh();
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnListUploadAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var upload = Ext.create('Ext.ux.uploadPanel.UploadPanel',{  
      addFileBtnText : '选择文件...',  
      uploadBtnText : '上传',  
      removeBtnText : '移除所有',  
      cancelBtnText : '取消上传',
      file_upload_limit : 1
    });
    
    me.winUpload = Ext.create('Ext.Window', {
      id: mep + 'uploadwin',
      title: '上传',
      width: 700,
      height: 260,
      maximizable: true,
      closeAction: 'destroy',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [upload]
    });
    
    upload.store.removeAll();
    upload.on('showdetail',me.OnShowDetail,me);
    upload.on('closewin',me.OnCloseWin,me);
    
    me.winUpload.show();
  },
  
  OnShowDetail: function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var filename = Ext.getCmp(mep+'filename').getValue();
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    filename = item.name;
    fileurl = item.url;
    tools.SetValue(mep + 'filename',filename);
    tools.SetValue(mep + 'fileurl',fileurl);
  },
  
  OnCloseWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.winUpload.hide();
    if (Ext.getCmp(mep + 'uploadwin')) {
      Ext.getCmp(mep + 'uploadwin').destroy();
    };
  },
  
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me },
      ' ', { id: mep + 'btnDetailUpload', text: '上传', iconCls: 'icon-deal', handler: me.OnListUploadAtt, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winDetail.hide(); } }
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
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
      items: me.editDetailControls    
    });
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
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
      items : [me.plDetailEdit]
    });
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.partsDetailEdit = null;
    me.editPartsDetailControls = [];
    me.partseditsitems = [];
    
    me.editDetailControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .50, layout: 'anchor', items: [
           tools.FormText('文件序号', 'afd.fileserial', mep + 'fileserial', 20, '96%', false, 1,'isnumber'), 
           tools.FormText('文件数量', 'afd.filecount', mep + 'filecount', 6, '96%', false, 3,'')
        ]},
        {xtype: 'container', columnWidth: .50, layout: 'anchor', items: [
           tools.FormText('文件名称', 'afd.filename', mep + 'filename', 40, '100%', false, 2),
           tools.FormCombo('文件类别', 'afd.filecate', mep + 'filecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
           { xtype: 'hiddenfield', name: 'afd.fileurl', id: mep + 'fileurl' }
       ]}
      ]},
      tools.FormTextArea('备注', 'afd.fileremark', mep + 'fileremark', 200, '100%', true, 13, 4)
   ];

    me.disDetailControls = [ 'fileserial'];
    me.enDetailControls = ['filecount', 'fileremark','filecate','filename'];
  },
 
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'fileserial', item.fileserial);
    tools.SetValue(mep + 'filecount', item.filecount);
    tools.SetValue(mep + 'filename', item.filename);
    tools.SetValue(mep + 'fileremark', item.fileremark);
    tools.SetValue(mep + 'filecate', item.filecate);
  },
  
  OnListNew: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnCreateDetailWin();
    
    if(me.winDetail){      
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnInitDetailData();   
      me.OnAuthDetailEditForm(false);
    }
  },     
   
  OnBeforeListSave: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     record.data.fileserial =  Ext.getCmp(mep + 'fileserial').getValue();
     record.data.filename = Ext.getCmp(mep + 'filename').getValue();
     record.data.filecount = Ext.getCmp(mep + 'filecount').getValue();
     record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
     record.data.fileurl = Ext.getCmp(mep + 'fileurl').getValue();
     record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
     record.data.filecatename = Ext.getCmp(mep + 'filecate').getDisplayValue();
  },
   
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        me.OnBeforeListSave(record);
        
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      }
      else {
        me.OnBeforeListSave(me.detailRecord);
        
        me.plDetailGrid.getView().refresh();
      }
      
      me.winDetail.hide();
    }
  },
   
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    var fileserial = me.plDetailGrid.store.getCount() + 1;
    tools.SetValue(mep + 'fileserial', fileserial);
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.plDetailGrid.plugins = [];
    me.deitems = [
      ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
      ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me },
      ' ', { id: mep + 'btnDetailFileScan', text:'文件预览', iconCls: 'icon-deal', handler: me.OnFileScan, scope: me }
    ];
  },

  
  OnFileScan: function () {
    var me = this;
    var record = tools.OnGridLoad(me.plDetailGrid, '请选择需要查看的数据！');
    alms.PopupFileShow('文件附件预览', 'FileDownFile.do', record.fileurl, record.filename);
  },

  
  //添加明细 中 的设备
  OnAfterCreateDetail: function () {
      var me = this, mep = this.tranPrefix;
      
      var partsColumn = [];
      var partsField = [];    

      tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_devacceptparts'), partsColumn, partsField, mep + '_a_');

      me.partsStore = tools.CreateGridStore(tools.GetUrl('DevGetListAcceptPartsDetail.do'), partsField);
      
      me.partsGrid = Ext.create('Ext.grid.Panel', {
        region : 'center',
        title : '设备验收配件',
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
    var partsno = me.partsGrid.store.getCount() + 1;
    tools.SetValue(mep + 'partsno', partsno);
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
       title: '设备验收配件',
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
            tools.FormText('配件编号', 'apd.partsno', mep + 'partsno', 20, '96%', false, 1),
            tools.FormText('配件名称', 'apd.partsname', mep + 'partsname', 40, '96%', false, 3),
            tools.FormText('数量', 'apd.partscount', mep + 'partscount', 6, '96%', false, 5,'')
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('配件序号', 'apd.partsserial', mep + 'partsserial', 20, '100%', false, 2),
            tools.FormText('规格型号', 'apd.partsstandard', mep + 'partsstandard', 40, '100%', false, 4)
          ]}                                                                 
       ]},
       tools.FormTextArea('备注', 'apd.partsremark', mep + 'partsremark', 100, '100%', true, 13, 4)
    ];
    me.disPartsDetailControls = ['partsno'];
    me.enPartsDetailControls = ['partsserial', 'partsname', 'partsstandard', 'partscount', 'partsremark']; 
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
     record.data.partsno =  Ext.getCmp(mep + 'partsno').getValue();
     record.data.partsserial = Ext.getCmp(mep + 'partsserial').getValue();
     record.data.partsname = Ext.getCmp(mep + 'partsname').getValue();
     record.data.partsstandard = Ext.getCmp(mep + 'partsstandard').getValue();
     record.data.partscount = Ext.getCmp(mep + 'partscount').getValue();
     record.data.partsremark = Ext.getCmp(mep + 'partsremark').getValue();
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
    tools.SetValue(mep + 'partsno', record.partsno);
    tools.SetValue(mep + 'partsserial', record.partsserial);
    tools.SetValue(mep + 'partsname', record.partsname);
    tools.SetValue(mep + 'partsstandard', record.partsstandard);
    tools.SetValue(mep + 'partscount', record.partscount);
    tools.SetValue(mep + 'partsremark', record.partsremark);
    return true;
  },
   
  OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('DevGetListAcceptFileDetail.do?afd.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
     
     if (me.partsGrid && me.partsGrid.store) {
       me.partsGrid.store.proxy.url = tools.GetUrl('DevGetListAcceptPartsDetail.do?apd.acceptid=') + tools.GetValue(mep + 'tranid');
       me.partsGrid.store.load();
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
   },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'devid').reset();
    Ext.getCmp(mep + 'applyid').reset();
    Ext.getCmp(mep + 'applyid').focus(true, 500);
  },
  
  OnBuyApplySelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
//    var record = tools.JsonGet(tools.GetUrl('GetBuyApply.do?bd.devid=') + item.devid);  
    if (item && item.tranid) {
      tools.SetValue(mep+'applyid',item.tranid);
      tools.SetValue(mep+'devname',item.devname);
      tools.SetValue(mep+'devstandard',item.devstandard);
      tools.SetValue(mep+'factoryname',item.factoryname);
      tools.SetValue(mep+'devrange',item.devrange);
      tools.SetValue(mep+'deptuser',item.applyuser);
      tools.SetValue(mep+'deptusername',item.applyusername);
    }
  },
  
  OnInstalUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tradeid) {
      tools.SetValue(mep+'instaluser',item.linkman);
      tools.SetValue(mep+'instalunit',item.tradename);
    }
  },
  
  OnManagerSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.userid) {
      tools.SetValue(mep+'devmanager',item.userid);
      tools.SetValue(mep+'devmanagername',item.username);
    }
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid || action.result.data.devid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
          tools.SetValue(mep + 'devid', action.result.data.devid);
        }
      }
    }
    me.OnDetailRefresh();
  },
  
  OnDevSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var apply = Ext.getCmp(mep+'applyid');
    me.OnDevBeforeLoad();
    apply.store.loadPage(1);    
  },
    
  OnDevBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var apply = Ext.getCmp(mep+'applyid');
    if(apply.store) {      
      apply.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'ba.devname': tools.GetValueEncode(mep + 'searchdevforacceptname')
        });
      });
    };
  },
  
  OnTradeSearch:function(){
    var me = this, mep = me.tranPrefix;
    var instaluser = Ext.getCmp(mep+'instaluser');
    me.OnInstalUserBeforeLoad();
    instaluser.store.loadPage(1);    
  },
    
  OnInstalUserBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var instaluser = Ext.getCmp(mep+'instaluser');
    if(instaluser.store) {      
      instaluser.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'bustrade.tradename': tools.GetValueEncode(mep + 'searchtradename')
        });
      });
    };
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
  
  //提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);
    if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnEdit'], mep);
    } else{
      tools.BtnsEnable(['btnEdit'], mep);
    }
  },
  
  //双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnSubmit'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmit'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  },
  
  //修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
   var me = this;
   var mep = me.tranPrefix;
   me.editToolItems = [
     ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
     '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
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
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnPrevRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
    
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) {
            tools.alert('已经是当前列表第一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i - 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnNextRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
      
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) {
            tools.alert('已经是当前列表最后一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i + 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
   },
    
   OnBeforeSave : function() {
     var me = this;
     var mep = me.tranPrefix;
     tools.SetValue(mep+'devoperatorsname', Ext.getCmp(mep + 'devoperators').getRawValue());
     return true;
   } 
  

  
});
