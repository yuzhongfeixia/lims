Ext.define('alms.prdpoison',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'剧毒易制毒化学品使用',
          winWidth:750,
          winHeight:300,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_prdpoison',
          storeUrl:'PrdSearchPrdPoison.do',
          saveUrl:'PrdSavePrdPoison.do',
          expUrl:'PrdSearchPrdPoison.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '耗材明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_prdpoisondetail',
          urlDetail: 'PrdGetListPrdPoisonDetail.do',
          detailTabs: 1,
          hasDateSearch: true,
          hasDetailEdit: true,
          detailTabs : 2
      });
      me.callParent(arguments);
   },
   
   fileGrid: null,
   fileStore: null,
   plAttDetailGrid: null,
   
   OnInitConfig:function(){
    var  me = this;
    me.fileGrid = null;
    me.fileStore = null;
    me.plAttDetailGrid = null;
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '申请编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true }
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
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
    
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'pp.tranid', mep + 'tranid', 20, '96%', false, 1),
              tools.FormCombo('申请人', 'pp.tranuser', mep + 'tranuser',  tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false, 4),
              //tools.FormDate('确认时间', 'pp.confirmdate', mep + 'confirmdate', 'Y-m-d H:i', '96%', false, 5,nowdate)
              tools.FormDate('确认时间', 'pp.confirmdate', mep + 'confirmdate', 'Y-m-d', '96%', false, 5,nowdate)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormCombo('耗材类别', 'pp.prdtype', mep + 'prdtype', tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE), '96%', false, 2),
              //tools.FormDate('申请时间', 'pp.trandate', mep + 'trandate', 'Y-m-d H:i', '96%', false, 5,nowdate)
              tools.FormDate('申请时间', 'pp.trandate', mep + 'trandate', 'Y-m-d', '96%', false, 5,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('使用项目', 'pp.projectid', mep + 'projectid', 20, '100%', false, 3),
              tools.FormText('室主任', 'pp.confirmusername', mep + 'confirmusername', 20, '100%', false, 3),
              {xtype:'hiddenfield',name:'pp.flowstatus',id: mep + 'flowstatus'},
              {xtype:'hiddenfield',name:'pp.flowaction',id: mep + 'flowaction'},
              {xtype:'hiddenfield',name:'pp.confirmuser',id: mep + 'confirmuser'},
              {xtype:'hiddenfield',name:'pp.tranusername',id: mep + 'tranusername'},
              {xtype:'hiddenfield',name:'pp.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
         tools.FormTextArea('室主任意见', 'pp.confirmdesc', mep + 'confirmdesc', 200, '100%', true, 6,2),
         tools.FormTextArea('特殊要求说明', 'pp.remark', mep + 'remark',  200, '100%',true, 8,3)
     ];
     me.disNews = ['tranid', 'confirmuser', 'confirmusername', 'confirmdate', 'tranusername'];
     me.disEdits = ['tranid','recouser', 'confirmuser', 'confirmusername', 'confirmdate', 'tranusername'];
     me.enNews = ['projectid', 'prdtype', 'flowaction', 'flowstatus', 'tranuser', 'trandate', 'confirmdesc', 'remark'];
     me.enEdits = [ 'projectid', 'prdtype', 'flowaction', 'flowstatus', 'tranuser', 'trandate', 'confirmdesc', 'remark'];
     
      Ext.getCmp(mep + 'tranuser').on('change',me.OnTranSelect,me);
   },
   
   OnTranSelect:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranusername',item.rawValue);
   },
   
   
   OnGetUser: function (deptid) {
    var me = this, mep = me.tranPrefix;
    
    var tranuser = Ext.getCmp(mep + 'tranuser');
    
    if (Ext.isEmpty(deptid) || (deptid == gpersist.SELECT_MUST_VALUE))
      return;
    
    if (tranuser) {
      if (deptid == gpersist.SELECT_MUST_VALUE) {
        tranuser.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetUserCombo(gpersist.SELECT_MUST_VALUE, deptid, mep + 'tranuser');
      }
    }
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
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'pp.tranid':tools.GetValueEncode(mep+'searchtranid')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'projectid', item.projectid);
    tools.SetValue(mep + 'prdtype', item.prdtype);
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    tools.SetValue(mep + 'confirmuser', item.confirmuser);
    tools.SetValue(mep + 'confirmusername', item.confirmusername);
    tools.SetValue(mep + 'confirmdate', item.confirmdate);
    tools.SetValue(mep + 'confirmdesc', item.confirmdesc);
    tools.SetValue(mep + 'remark', item.remark);
    me.OnDetailRefresh();
    return true;
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var prditems = [
     ' ', { xtype: 'textfield', fieldLabel: '耗材编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdid', id: mep + 'searchprdid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnPrdSearch, scope: me }
    ];
     
     var prdname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '物品', name: 'detail.prdname', id: mep + 'prdname', winTitle: '选择物品',
      maxLength: 50, maxLengthText: '物品名称不能超过50字！', selectOnFocus: false, labelWidth: 80,
      blankText: '物品为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basprd',
      storeUrl: 'PrdSearchBasPrd.do?basprd.prdtype='+tools.GetValue(mep+'prdtype'),
      editable:false,
      searchTools:prditems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
     prdname.on('griditemclick', me.OnPrdSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('序号', 'detail.prdserial', mep + 'prdserial',20, '96%', false, 1,'isnumber'),
            tools.FormText('领用量', 'detail.prdcount', mep + 'prdcount',12, '96%', false, 22,'isinteger')
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            prdname,
            tools.FormCombo('数量单位', 'detail.prdunit', mep + 'prdunit', tools.ComboStore('PrdUnit', gpersist.SELECT_MUST_VALUE), '96%', false, 2),
            {xtype:'hiddenfield',name:'detail.prdid',id: mep + 'prdid'}
        ]}
       ]},      
       tools.FormTextArea('备注', 'detail.poiremark', mep + 'poiremark', 200, '98%', true, 25,4)
    ];

     me.disDetailControls = ['prdserial'];
     me.enDetailControls = [  'prdid', 'prdcount', 'prdunit', 'poiremark','prdname'];
     
   },
   
   OnPrdSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.prdid){
        tools.SetValue(mep+"prdid",item.prdid);
        tools.SetValue(mep+"prdname",item.prdname);
        tools.SetValue(mep+"prdunit",item.prdunit);
     }
   },
   
   OnPrdSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnPrdBeforeLoad();
      var prd = Ext.getCmp(mep + 'prdname');
      prd.store.loadPage(1);
   },
   
   OnPrdBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var prd = Ext.getCmp(mep + 'prdname');
      if(prd.store){
        prd.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'basprd.prdid':tools.GetValueEncode(mep + 'searchprdid'),
              'basprd.prdname':tools.GetValueEncode(mep + 'searchprdname')
            });
        });
      }
   },
   
   OnLoadDetailData: function (item) {
      var me = this;
      var mep = me.tranPrefix;
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'prdserial', item.prdserial);
      tools.SetValue(mep + 'prdid', item.prdid);
      tools.SetValue(mep + 'prdcount', item.prdcount);
      tools.SetValue(mep + 'prdunit', item.prdunit);
      tools.SetValue(mep + 'poiremark', item.poiremark);
      tools.SetValue(mep+'prdname',item.prdname);
   },
   
   OnListNew: function() {
    var me = this;
    
    var date = new Date();
    
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
     record.data.prdserial = Ext.getCmp(mep + 'prdserial').getValue();
     record.data.prdid = Ext.getCmp(mep + 'prdid').getValue();
     record.data.prdname = Ext.getCmp(mep + 'prdname').getValue();
     record.data.prdcount = Ext.getCmp(mep + 'prdcount').getValue();
     record.data.prdunit = Ext.getCmp(mep + 'prdunit').getValue();
     record.data.prdunitname = Ext.getCmp(mep + 'prdunit').getDisplayValue();
     record.data.poiremark = Ext.getCmp(mep + 'poiremark').getValue();
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'prdtype', gpersist.SELECT_MUST_VALUE);
     tools.SetValue(mep + 'confirmuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'confirmusername', gpersist.UserInfo.user.username );
     me.OnGetUser(gpersist.UserInfo.user.deptid);
   },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    var serial = me.plDetailGrid.store.getCount() + 1;
    tools.SetValue(mep + 'prdunit', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdserial', serial);
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('PrdGetListPrdPoisonDetail.do?ppd.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
     
     if (me.fileGrid && me.fileGrid.store) {
        me.fileGrid.store.proxy.url = tools.GetUrl('PrdGetListPrdPoisonFile.do?ppf.tranid=') + tools.GetValue(mep + 'tranid');
        me.fileGrid.store.load();
     }
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;

     var details = [];
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     
     var filedetails = [];
     for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
       filedetails.push(me.fileGrid.store.getAt(i).data);
     }
     me.saveParams = { details: Ext.encode(details),filedetails: Ext.encode(filedetails) };
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var prdtype = Ext.getCmp(mep+'prdtype').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(prdtype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择耗材类别！');
        return;
      }
    }
    return true;
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
          tools.SetValue(mep + 'flowstatus', action.result.data.flowstatus);
          tools.SetValue(mep + 'flowaction', action.result.data.flowaction);
        }
      }
    }
    me.OnDetailRefresh();
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
      tools.BtnsDisable(['btnFileAdd'], mep);
      tools.BtnsDisable(['btnFileDelete'], mep);
      tools.BtnsDisable(['btnDownAll'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmit'], mep);
      tools.BtnsEnable(['btnFileAdd'], mep);
      tools.BtnsEnable(['btnFileDelete'], mep);
      tools.BtnsEnable(['btnDownAll'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  },
  
   // 添加明细 中 的设备 及 附件
  OnAfterCreateDetail: function () {
     var me = this, mep = this.tranPrefix;
     // 附件
     var fileColumn = [];
     var fileField = [];    

     tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_prdpoisonfile'), fileColumn, fileField, mep + '_b_');

     me.fileStore = tools.CreateGridStore(tools.GetUrl(''), fileField);
     
     me.fileGrid = Ext.create('Ext.grid.Panel', {
       region : 'center',
       title : '文件',
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
       selModel: me.hasDetailGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }) : {},
       listeners : {
           'itemdblclick' : { fn : me.OnListSelectFile, scope : me }
       }   
     });
     me.fileStore.load();
     me.plDetail.add(me.fileGrid);
     me.fileitems = [
       ' ', { id : mep + 'btnFileAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewFile, scope : me },
       ' ', { id : mep + 'btnFileDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteFile, scope : me },
       '-',' ', { id: mep + 'btnDownAll', text: '下载所有文件', iconCls: 'icon-down', handler: me.OnDownZip, scope: me }
     ];
     
     me.OnAfterCreateDetailToolBar();
     
     tools.SetToolbar(me.fileitems, mep);
       
     var tbfile = Ext.create('Ext.toolbar.Toolbar', {
       dock : 'top',
       items : me.fileitems
     });
       
     me.fileGrid.insertDocked(0, tbfile);
   },
   
  OnCreateDetailWinFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSaveFile, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEditFile();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
      height : '40%',
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
      title: '文件',
      width: 600,
      height: 500,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: false,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit,me.plAttDetailGrid]
    });
    
  },
  
  OnListNewFile : function(){
    var me = this;
    me.OnCreateDetailWinFile();
    me.OnInitDetailDataFile();
    if(me.winDetail){      
      me.winDetail.show();
    }
  },
  
  OnListSelectFile: function (view, record) {
    alms.PopupFileShow('文件预览', 'FileDownFile.do', record.data.fileurl, record.data.filename);
  },
  
  OnDownZip: function () {
    var me = this, mep = me.tranPrefix;
    
    var fileurls = [];
    
    for (i = 0; i < me.fileGrid.store.getCount(); i++) {
      fileurls.push(me.fileGrid.store.getAt(i).data.filename + ':' +me.fileGrid.store.getAt(i).data.fileurl);
    }
    
    if (fileurls.length <= 0) {
      tools.alert('没有需要下载的文件！');
      return;      
    }
    
    var filename = tools.GetValue(mep + 'tranusername') + "_易制毒申请文件";
    var iframe = document.getElementById('export');
    var plExport = Ext.getCmp('plexport');
    
    plExport.form.submit({
      url: 'FileDownZipFile.do',
      params: { filename: filename, fileurl: fileurls.join() },
      target: 'export'
    });
  },
  
  OnBeforeCreateDetailEditFile: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('附件类别', 'bbd.filecate', mep + 'filecate', tools.ComboStore('FileCate', gpersist.SELECT_MUST_VALUE), '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('附件类型', 'bbd.filetype', mep + 'filetype', tools.ComboStore('FileType', gpersist.SELECT_MUST_VALUE), '100%', false, 2)
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'bbd.filename', id: mep + 'filename' },
      { xtype: 'hiddenfield', name: 'bbd.fileurl', id: mep + 'fileurl' },
      { xtype: 'hiddenfield', name: 'bbd.fileno', id: mep + 'fileno' },
      tools.FormTextArea('备注', 'bbd.fileremark', mep + 'fileremark', 200, '100%', true, 3, 7)
      
    ]
    me.disDetailControls = [];
    me.enDetailControls = ['filechtype', 'filename', 'fileurl', 'fileno', 'filecate', 'fileremark'];  
  },
  
  OnListSaveFile: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    var filetype = Ext.getCmp(mep + 'filetype').getValue();
    var filecate = Ext.getCmp(mep + 'filecate').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(filetype == gpersist.SELECT_MUST_VALUE || filecate == gpersist.SELECT_MUST_VALUE){
        tools.alert('请选择附件类型或附件类别');
      }else{
        if(filename == "" || filename == null || filename == undefined){
          tools.alert('请上传附件！');
          return;
        }else{
          if (me.detailEditType == 1) {
            //可能有多个附件的情况
            var filenames = filename.split(",");
            var fileurls = fileurl.split(",");
            for(i = 0; i <filenames.length; i++){
              var record = me.fileGrid.store.model.create({});
              record.data.filename = filenames[i];
              record.data.filetype = Ext.getCmp(mep + 'filetype').getValue();
              record.data.filetypename = Ext.getCmp(mep + 'filetype').rawValue;
              record.data.fileurl = fileurls[i];
              record.data.filecatename = Ext.getCmp(mep + 'filecate').rawValue;
              record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
              record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
              record.data.fileno = i + parseInt(Ext.getCmp(mep + 'fileno').getValue());
              me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);      
            }
          }
          else {
            me.OnBeforeListSaveFile(me.detailRecord);
            me.fileGrid.getView().refresh();
          }
        }
        me.winDetail.hide();
      }
    }
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
      }
      if(fileurl == ""){
        fileurl = item.url;
      }else{
        fileurl = fileurl+','+item.url;
      }
      
      tools.SetValue(mep + 'filename',filename);
      tools.SetValue(mep + 'fileurl',fileurl);
    };
  },
  
  OnListDeleteFile:function(){
    var me = this;
    var j = me.fileGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
//      var fileurl = me.fileGrid.selModel.selected.items[0].data.fileurl;
//      fileurl = tools.GetEncode(tools.GetEncode(fileurl));
//      tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
    }
    
    me.fileGrid.getView().refresh();
  },
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    fileurl = tools.GetEncode(tools.GetEncode(fileurl));
    tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
    me.winDetail.hide();
    me.detailEditType = 1;
  },
  
  OnInitDetailDataFile: function () {
    var me = this;
    var mep = me.tranPrefix;   
    var serial = me.fileGrid.store.getCount() + 1;
    tools.SetValue(mep + 'fileno', serial);
    tools.SetValue(mep + 'filetype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'filecate', gpersist.SELECT_MUST_VALUE);
  },

  OnBeforeListSaveFile : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.filetypename = Ext.getCmp(mep + 'filetype').getDisplayValue();
    record.data.filetype = Ext.getCmp(mep + 'filetype').getValue();
    record.data.filename = Ext.getCmp(mep + 'filename').getValue();
    record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
    record.data.filechurl = Ext.getCmp(mep + 'fileurl').getValue();
    record.data.filechno = Ext.getCmp(mep + 'fileno').getValue();
    record.data.filecatename = Ext.getCmp(mep + 'filecate').getDisplayValue();
    record.data.filecate = Ext.getCmp(mep + 'filecate').getValue();
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
            tools.BtnsDisable(['btnFileAdd'], mep);
            tools.BtnsDisable(['btnFileDelete'], mep);
            tools.BtnsDisable(['btnDownAll'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
            tools.BtnsEnable(['btnFileAdd'], mep);
            tools.BtnsEnable(['btnFileDelete'], mep);
            tools.BtnsEnable(['btnDownAll'], mep);
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
            tools.BtnsDisable(['btnFileAdd'], mep);
            tools.BtnsDisable(['btnFileDelete'], mep);
            tools.BtnsDisable(['btnDownAll'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
            tools.BtnsEnable(['btnFileAdd'], mep);
            tools.BtnsEnable(['btnFileDelete'], mep);
            tools.BtnsEnable(['btnDownAll'], mep);
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