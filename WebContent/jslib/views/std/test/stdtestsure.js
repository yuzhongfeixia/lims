Ext.define('alms.stdtestsure', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '新增检验能力确认',
      winWidth: 750,
      winHeight: 400,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdtestsure',
      storeUrl: 'StdSearchStdTestSure.do',
      saveUrl: 'StdSaveStdTestSure.do',
      hasGridSelect: true,
      expUrl: 'StdSearchStdTestSure.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '附件上传',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_stdsuredetail',
      urlDetail: 'StdGetListStdSureDetail.do',
      hasDateSearch: true,
      hasDetailEdit: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var projitems = [
      ' ', { xtype: 'textfield', fieldLabel: '新项目名称', labelWidth: 80, width: 180, maxLength: 40, name: 'searchproapplyname', id: mep + 'searchproapplyname', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnProjSearch, scope: me }
    ];
    
    var projname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '新项目名', name: 'testsure.projname', id: mep + 'projname', winTitle: '选择新增项目',
      maxLength: 20, maxLengthText: '新增项目名称不能超过30字！', selectOnFocus: false, labelWidth: 80,
      blankText: '新增项目名称不能为空！', allowBlank: false, anchor: '96%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdproapply',
      storeUrl: 'StdSearchStdProApply.do?proapply.flowstatus=02',
      editable:false,
      searchTools:projitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    projname.on('griditemclick', me.OnProSelect, me);
    
    var testuser = tools.UserPicker(mep + 'testuser','testsure.testuser','检测人');
    
    testuser.on('griditemclick', me.OnTestUserSelect, me);

    var respuser = tools.UserPicker(mep + 'respuser','testsure.respuser','负责人');
    
    respuser.on('griditemclick', me.OnRespUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//          tools.FormText('业务编号', 'testsure.tranid', mep + 'tranid', 20, '96%', false, 1),
          projname,
          testuser
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          respuser,
          tools.FormText('检测人姓名', 'testsure.testusername', mep + 'testusername', 20, '96%', false, 4)
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('负责人姓名', 'testsure.respusername', mep + 'respusername', 20, '100%', false, 6),
          tools.FormText('检测室', 'deptname', mep + 'deptname', 20, '100%', false, 7),
          {xtype:'hiddenfield',name:'testsure.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'testsure.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('人员要求情况检查', 'testsure.qualfile', mep + 'qualfile', 100, '99%', false, 9, 4),
      tools.FormTextArea('环境设施条件检查', 'testsure.envfacility', mep + 'envfacility', 100, '99%', false, 9, 4),
      tools.FormTextArea('仪器设备满足情况检查', 'testsure.devsatisfy', mep + 'devsatisfy', 100, '99%', false, 9, 4)
    ];
    me.disNews = ['testusername','tranid','respusername','deptname'];
    me.disEdits = ['testusername','tranid','respusername','deptname'];
    me.enNews = ['projname', 'testuser',  'respuser', 'qualfile', 'envfacility', 'devsatisfy'];
    me.enEdits = ['projname', 'testuser',  'respuser', 'qualfile', 'envfacility', 'devsatisfy'];
    
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
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '新增项目名称', labelWidth: 90, width: 180, maxLength: 40, name: 'searchprojname', id: mep + 'searchprojname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
//      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
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
          'testsure.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'testsure.projname': tools.GetEncode(tools.GetValue(mep + 'searchprojname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.JsonGet(tools.GetUrl('BasGetUserByUserid.do?userid=') +item.testuser);
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'projname', item.projname);
      tools.SetValue(mep + 'testuser', item.testuser);
      tools.SetValue(mep + 'testusername', item.testusername);
      tools.SetValue(mep + 'deptname', record.deptname);
      tools.SetValue(mep + 'respuser', item.respuser);
      tools.SetValue(mep + 'respusername', item.respusername);
      tools.SetValue(mep + 'qualfile', item.qualfile);
      tools.SetValue(mep + 'envfacility', item.envfacility);
      tools.SetValue(mep + 'devsatisfy', item.devsatisfy);
      me.OnDetailRefresh();
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnBeforeSave: function(){
    var me = this;
    var mep = me.tranPrefix;
    var testuser = tools.GetValue(mep + 'testuser' );
    if(testuser==''||testuser==null){
      tools.alert('请选择检测人！');
    }
    
    var respuser = tools.GetValue(mep + 'respuser' );
    if(respuser==''||respuser==null){
      tools.alert('请选择负责人！');
    }
    
    return true;
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  },
  
  OnTestUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    var record = gpersist.UserInfo;
    if (item) {
      tools.SetValue(mep+'testuser',item.userid);
      tools.SetValue(mep+'testusername',item.username);
      tools.SetValue(mep+'deptname',item.deptname);
   }
  },
  
  OnRespUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item) {
      tools.SetValue(mep+'respuser',item.userid);
      tools.SetValue(mep+'respusername',item.username);
   }
  },
  
  OnProSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tranid) {
      tools.SetValue(mep+'projname',item.projname);
    }
  },
  
  OnProjSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var projname = Ext.getCmp(mep+'projname');
    me.OnProjBeforeLoad();
    projname.store.loadPage(1);    
  },
    
  OnProjBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var projname = Ext.getCmp(mep+'projname');
    if(projname.store) {      
      projname.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
          'proapply.projname': tools.GetValueEncode(mep + 'searchproapplyname')
        });
      });
    };
  },
  
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    };
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
      height : '20%',
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
      title:'上传文件',
      columnWidth:1,
      scope: me,
      items: [upload]    
    });
    
    upload.on('showdetail',me.OnShowDetail,me);
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
      width: 600,
      height: 400,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      region : 'center',
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
    me.OnCreateDetailWin();
    if(me.winDetail){      
      me.winDetail.show();
      me.OnInitDetailData();   
      me.OnAuthDetailEditForm(false);
    }
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.plDetailGrid.plugins = [];
    me.deitems = [
      ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
      ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me },
      '-',' ', { id: mep + 'btnDownAll', text: '下载所有文件', iconCls: 'icon-down', handler: me.OnDownZip, scope: me }
    ];
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('文件名称', 'ssd.attachfilename', mep + 'attachfilename', 20, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('文件类别', 'ssd.filecate', mep + 'filecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '100%', false, 2)
          
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'ssd.attachfileurl', id: mep + 'attachfileurl' }
    ];
    me.disDetailControls = ['attachfilename'];
    me.enDetailControls = ['filecate', 'attachfilename', 'attachfileurl'];  
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var attachfilename = Ext.getCmp(mep+ 'attachfilename').getValue();
    var attachfileurl = Ext.getCmp(mep + 'attachfileurl').getValue();
    var filecate = Ext.getCmp(mep + 'filecate').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(filecate == '-2'){
        tools.alert('请选择资格证书');
      }else{
        if(attachfilename == "" || attachfileurl == null || attachfilename == undefined){
          tools.alert('请上传附件！');
          return;
        }else{
          if (me.detailEditType == 1) {
            //可能有多个附件的情况
            var attachfilenames = attachfilename.split(",");
            var attachfileurls = attachfileurl.split(",");
            for(i = 0; i <attachfilenames.length; i++){
              var record = me.plDetailGrid.store.model.create({});
              record.data.attachfilename = attachfilenames[i];
              record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
              record.data.filecatename = Ext.getCmp(mep + 'filecate').rawValue;
              record.data.attachfileurl = attachfileurls[i];
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
    };
  },

  OnShowDetail:function(render, item){
    var me = this;
    var mep = this.tranPrefix;
    
    var attachfilename = Ext.getCmp(mep+'attachfilename').getValue();
    var attachfileurl = Ext.getCmp(mep+'attachfileurl').getValue();
    
    if(item){
      if(attachfilename == ""){
        attachfilename = item.name;
      }else{
        attachfilename = attachfilename+','+item.name
      };
      if(attachfileurl == ""){
        attachfileurl = item.url;
      }else{
        attachfileurl = attachfileurl+','+item.url;
      };
      
      tools.SetValue(mep + 'attachfilename',attachfilename);
      tools.SetValue(mep + 'attachfileurl',attachfileurl);
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
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var attachfileurl = Ext.getCmp(mep + 'attachfileurl').getValue();
    attachfileurl = tools.GetEncode(tools.GetEncode(attachfileurl));
//    tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + attachurl);
    me.winDetail.hide();
    me.detailEditType = 1;
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('StdGetListStdSureDetail.do?ssd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;        
    tools.SetValue(mep + 'filecate', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;
//    record.data.tranid = Ext.getCmp(mep + 'tranid').getValue();
    record.data.filecatename = Ext.getCmp(mep + 'filecate').getDisplayValue();
    record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
    record.data.attachfilename = Ext.getCmp(mep + 'attachfilename').getValue();
    record.data.attachfileurl = Ext.getCmp(mep + 'attachfileurl').getValue();
  },
  
  OnListSelect: function (view, record) {
    alms.PopupFileShow('预约附件预览', 'FileDownFile.do', record.data.attachfileurl, record.data.attachfilename);
  },
  
  OnDownZip: function () {
    var me = this, mep = me.tranPrefix;
    var fileurls = [];
    
    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      fileurls.push(me.plDetailGrid.store.getAt(i).data.attachfilename + ':' +me.plDetailGrid.store.getAt(i).data.attachfileurl);
    };
    
    if (fileurls.length <= 0) {
      tools.alert('没有需要下载的文件！');
      return;      
    };
    
    var filename = tools.GetValue(mep + 'subname') + "资格证书附件";
    var iframe = document.getElementById('export');
    var plExport = Ext.getCmp('plexport');
    
    plExport.form.submit({
      url: 'FileDownZipFile.do',
      params: { filename: filename, fileurl: fileurls.join() },
      target: 'export'
    });
  },
  
//  OnAfterCreateEdit:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    me.plEdit = Ext.create('Ext.form.Panel', {
//      id : mep + 'pledit',
//      header : false,
//      height:'100%',
//      autoScroll: true,
//      region : 'north',
//      fieldDefaults : {
//        labelSeparator : '：',
//        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
//        labelPad : 0,
//        labelStyle : 'font-weight:bold;'
//      },
//      frame : true,
//      title : '',
//      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
//      defaultType : 'textfield',
//      closable : false,
//      header : false,
//      unstyled : true,
//      scope : me,
//      tbar : me.tbEdit,
//      items : me.editControls
//    });
//  },
  
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
//     ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
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
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  }
   
});