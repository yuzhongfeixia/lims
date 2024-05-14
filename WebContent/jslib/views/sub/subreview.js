Ext.define('alms.subreview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '分包评审',
      winWidth: 750,
      winHeight: 200,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_subreview',
      storeUrl: 'SubSearchSubReview.do',
      saveUrl: 'SubSaveSubReview.do',
      hasGridSelect: true,
      expUrl: 'SubSearchSubReview.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasDetail: true,
      hasSubmit: true,
      detailTitle: '资格证书',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_subfiledetail',
      urlDetail: 'SubGetListSubReviewDetail.do',
      hasDateSearch: true,
      hasDetailEdit: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('评审编号', 'sr.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('联系电话', 'sr.linktele', mep + 'linktele', 30, '96%', false, 4,"isphone")
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('分包方名称', 'sr.subname', mep + 'subname', 20, '96%', false, 2),
          tools.FormText('分包项目', 'sr.subproject', mep + 'subproject', 20, '96%', false, 5)
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('分包方地址', 'sr.subaddr', mep + 'subaddr', 30, '100%', false, 3),
          {xtype:'hiddenfield',name:'sr.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('检测人员数量、素质', 'sr.testerdesc', mep + 'testerdesc', 200, '100%', true, 9, 4),
      tools.FormTextArea('检测设备', 'sr.testdev', mep + 'testdev', 200, '100%', true, 9, 4),
      tools.FormTextArea('检测设备量值溯源情况', 'sr.testsource', mep + 'testsource', 100, '100%', true, 9, 4),
      tools.FormTextArea('环境情况', 'sr.envdesc', mep + 'envdesc', 200, '100%', true, 9, 4),
      tools.FormTextArea('质量体系和运行情况', 'sr.qualsys', mep + 'qualsys', 200, '100%', true, 9, 4),
      tools.FormTextArea('服务质量', 'sr.servicequal', mep + 'servicequal', 200, '100%', true, 9, 4),
      tools.FormTextArea('评审结论', 'sr.auditresult', mep + 'auditresult', 200, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['subaddr','subproject','subname','linktele','testerdesc', 'testdev','testsource','envdesc','qualsys','servicequal','auditresult'];
    me.enEdits = ['subaddr','subproject','subname','linktele','testerdesc', 'testdev','testsource','envdesc','qualsys','servicequal','auditresult'];
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
      ' ', { xtype: 'textfield', fieldLabel: '评审编号', labelWidth: 60, width: 180, maxLength: 40, name: '', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '分包方名称', labelWidth: 70, width: 180, maxLength: 40, name: 'searchsubname', id: mep + 'searchsubname', allowBlank: true },
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
          'sr.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'sr.subname': tools.GetEncode(tools.GetValue(mep + 'searchsubname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'subname', item.subname);
      tools.SetValue(mep + 'subaddr', item.subaddr);
      tools.SetValue(mep + 'linktele', item.linktele);
      tools.SetValue(mep + 'subproject', item.subproject);
      tools.SetValue(mep + 'testerdesc', item.testerdesc);
      tools.SetValue(mep + 'testdev', item.testdev);
      tools.SetValue(mep + 'testsource', item.testsource);
      tools.SetValue(mep + 'envdesc', item.envdesc);
      tools.SetValue(mep + 'qualsys', item.qualsys);
      tools.SetValue(mep + 'servicequal', item.servicequal);
      tools.SetValue(mep + 'auditresult', item.auditresult);
      
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
          tools.FormText('文件名称', 'sfd.attachfilename', mep + 'attachfilename', 20, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('资格证书', 'sfd.entercret', mep + 'entercret', tools.ComboStore('EnterCret', gpersist.SELECT_MUST_VALUE), '100%', false, 2)
          
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'sfd.attachfileurl', id: mep + 'attachfileurl' }
    ];
    me.disDetailControls = ['attachfilename'];
    me.enDetailControls = ['entercret', 'attachfilename', 'attachfileurl'];  
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var attachfilename = Ext.getCmp(mep+ 'attachfilename').getValue();
    var attachfileurl = Ext.getCmp(mep + 'attachfileurl').getValue();
    var entercret = Ext.getCmp(mep + 'entercret').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(entercret == '-2'){
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
              record.data.entercret = Ext.getCmp(mep + 'entercret').getValue();
              record.data.entercretname = Ext.getCmp(mep + 'entercret').rawValue;
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
      me.plDetailGrid.store.proxy.url = tools.GetUrl('SubGetListSubReviewDetail.do?sfd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;        
    tools.SetValue(mep + 'entercret', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;
//    record.data.tranid = Ext.getCmp(mep + 'tranid').getValue();
    record.data.entercretname = Ext.getCmp(mep + 'entercret').getDisplayValue();
    record.data.entercret = Ext.getCmp(mep + 'entercret').getValue();
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