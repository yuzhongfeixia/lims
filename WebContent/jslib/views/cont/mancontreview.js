Ext.define('alms.mancontreview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '合同评审',
      winWidth:900,
      winHeight:200,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_contractreview',
      storeUrl: 'ContSearchContractReview.do',
      saveUrl: 'ContSaveContractReview.do',
      expUrl: 'ContSearchContractReview.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '评审明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'tranid',
      hasGridSelect: true,
      hasDetailCheck:false,
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_contractreviewdetail',
      urlDetail: 'ContGetListContractReviewDetail.do',
      hasPageDetail: false,
      hasSubmit: true,
      detailTabs: 2
    });
    me.callParent(arguments);
  },
  
  sampGrid:null,
  sampStore:null,
  sampleDetail:null,
  params:null,
  
  OnInitConfig:function(){
    var me = this;
    me.sampGrid = null;
    me.sampStore = null;
    me.sampleDetail = null;
    me.params = Ext.create('Ext.util.MixedCollection');
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.OnInitGridToolBar();
    var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '客户名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchconsignname', id: mep + 'searchconsignname', allowBlank: true }
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
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
   
    var contractid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '委托书号', name: 'cr.contractid', id: mep + 'contractid', winTitle: '选择委托书',
      maxLength: 20, maxLengthText: '委托书号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '委托书号不能为空！', allowBlank: false, anchor: '96%', tabIndex: 3,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busconsign',
      storeUrl: 'LabSearchBusConsign.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
   
    contractid.on('griditemclick', me.OnGetSelect, me);
    
    var reviewhostname = tools.UserPicker( mep + 'reviewhostname','cr.reviewhostname','评审主持人',80);
    reviewhostname.on('griditemclick', me.OnUserSelect, me);
                   
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth:.33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'cr.tranid', mep + 'tranid', 20, '96%', false,1),
          tools.FormText('联系人', 'cr.consigncontact', mep + 'consigncontact', 10, '96%', false,4),
          reviewhostname
        ]},
        { xtype: 'container', columnWidth:.33, layout: 'anchor', items: [
//          contractid,
          tools.FormText('合同/委托书编号', 'cr.contractid', mep + 'contractid', 20, '96%', false, 2,'',110),
         tools.FormText('联系电话', 'cr.consigntele', mep + 'consigntele', 20, '96%', false, 5, '',110)
        ]},
        { xtype: 'container', columnWidth:.34, layout: 'anchor', items: [
          tools.FormText('客户名称', 'cr.consignname', mep + 'consignname', 20, '100%',3, true),
          tools.FormDate('评审时间', 'cr.reviewhostdate', mep + 'reviewhostdate', 'Y-m-d', '100%', false, 6 , nowdate)
        ]}
      ]},
      tools.FormTextArea('检测特殊要求', 'cr.testrequest', mep + 'testrequest', 200, '100%', true, 10, 2),
      tools.FormTextArea('评审结论', 'cr.reviewresult', mep + 'reviewresult', 200, '100%', true, 11, 3),
      {xtype:'hiddenfield',name:'cr.reviewhost',id: mep + 'reviewhost'},
      {xtype:'hiddenfield',name:'cr.tranuser',id: mep + 'tranuser'},
      {xtype:'hiddenfield',name:'cr.deal.action',id: mep + 'datadeal'}
    ];
      
    me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['contractid', 'consignname', 'consigncontact', 'consigntele', 'testrequest', 'reviewresult', 'reviewhost', 'reviewhostname', 'reviewhostdate'];
    me.enEdits = ['contractid', 'consignname', 'consigncontact', 'consigntele', 'testrequest', 'reviewresult', 'reviewhost', 'reviewhostname', 'reviewhostdate'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'tranuser',gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username);
  },
  
  OnGetSelect:function(render,record){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'consignname', record.consignname);
    tools.SetValue(mep + 'consigncontact', record.consigncontact);
    tools.SetValue(mep + 'consigntele', record.consigntele);
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && !Ext.isEmpty(item.userid)) {
      tools.SetValue(mep + 'reviewhost', item.userid);
      tools.SetValue(mep + 'reviewhostname', item.username);
    }
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'cr.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'cr.consignname':tools.GetEncode(tools.GetValue(mep + 'searchconsignname'))
        });
      });
    }
  },
  
  
  OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     var reviewusername = tools.UserPicker( mep + 'reviewuser','detail.reviewuser','评审人员',80);
     reviewusername.on('griditemclick', me.OnReviewUserSelect, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            reviewusername
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('姓名', 'detail.reviewusername', mep + 'reviewusername',14, '96%', false,21)
        ]}
       ]},      
       tools.FormTextArea('评审意见措施', 'detail.reviewadvice', mep + 'reviewadvice', 200, '98%', false, 24,4)
    ];

     me.disDetailControls = ['reviewusername'];
     me.enDetailControls = [ 'reviewuser', 'reviewadvice'];
     
  },
  
  OnReviewUserSelect: function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && !Ext.isEmpty(item.userid)) {
      tools.SetValue(mep + 'reviewuser', item.userid);
      tools.SetValue(mep + 'reviewusername', item.username);
    }
  },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'reviewusername', item.reviewusername);
    tools.SetValue(mep + 'reviewuser', item.reviewuser);
    tools.SetValue(mep + 'reviewadvice', item.reviewadvice);
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
     record.data.reviewusername =  Ext.getCmp(mep + 'reviewusername').getValue();
     record.data.reviewuser = Ext.getCmp(mep + 'reviewuser').getValue();
     record.data.reviewadvice = Ext.getCmp(mep + 'reviewadvice').getValue();
  },
  
  // 添加检测样品
  OnAfterCreateDetail: function () {
     var me = this, mep = this.tranPrefix;
     
     var sampColumn = [];
     var sampField = [];    

     tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_contractreviewsample'), sampColumn, sampField, mep + '_b_');

     me.sampStore = tools.CreateGridStore(tools.GetUrl('ContGetListContractReviewSample.do'), sampField);
     
     me.sampGrid = Ext.create('Ext.grid.Panel', {
       region : 'center',
       title : '样品',
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
       columns : sampColumn,
       store : me.sampStore,
       selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
       listeners : {
          'itemdblclick' : { fn : me.OnListSelectSamp, scope : me }
       }  
     });
     
     me.sampStore.load();
     //me.plDetail.add(me.sampGrid);
     
     me.sampitems = [
       ' ', { id : mep + 'btnSampAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNewSamp, scope : me },
       ' ', { id : mep + 'btnSampDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDeleteSamp, scope : me }
     ];
     
     me.OnAfterCreateDetailToolBar();
     
     tools.SetToolbar(me.sampitems, mep);
       
     var tbsamp = Ext.create('Ext.toolbar.Toolbar', {
       dock : 'top',
       items : me.sampitems
     });
     me.sampGrid.insertDocked(0, tbsamp);
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
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('ContGetListContractReviewDetail.do?crd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    }
    if (me.sampGrid && me.sampGrid.store) {
      me.sampGrid.store.proxy.url = tools.GetUrl('ContGetListContractReviewSample.do?crs.tranid=') + tools.GetValue(mep + 'tranid');
      me.sampGrid.store.load();
    }
  },
  
  OnListDeleteSamp:function(){
    var me = this;
    var j = me.sampGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.sampGrid.store.remove(me.sampGrid.selModel.selected.items[0]);
    }
    
    me.sampGrid.getView().refresh();
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
    me.OnDetailRefresh();
  },
  
  //新增样品
  OnListNewSamp: function() {
    var me = this;
    
    if (!me.sampleDetail) {
      me.sampleDetail = tools.GetPopupWindow('alms', 'editcontractsamp', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sa', dataDeal: gpersist.DATA_DEAL_NEW})
      
      me.sampleDetail.on('formlast', me.OnParameterSave, me);
      me.sampleDetail.OnFormLoad();
    }
    else
      me.sampleDetail.OnFormShow();
      
    me.sampleDetail.OnInitData(me.params);
    me.sampleDetail.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    
    me.detailRecord = null;
  },
  
  OnListSelectSamp: function(e, record, item, index) {
    var me = this, mep = me.tranPrefix;
    
    if (!me.sampleDetail) {
      me.sampleDetail = tools.GetPopupWindow('alms', 'editcontractsamp', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sa', dataDeal: me.dataDeal})
      
      me.sampleDetail.on('formlast', me.OnParameterSave, me);
      me.sampleDetail.OnFormLoad();
    }
    else
      me.sampleDetail.OnFormShow();
    
    me.sampleDetail.OnSetData(record, tools.GetValue(mep + 'tranid'), me.params);
    me.sampleDetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.detailRecord = record;
  },
  
  OnSetData: function (id, record) {
    var me = this, mep = me.tranPrefix, i = 0;
    var item = tools.JsonGet('ContGetContractReview.do?cr.tranid='+id);
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'contractid', item.contractid);
      tools.SetValue(mep + 'consignname', item.consignname);
      tools.SetValue(mep + 'consigncontact', item.consigncontact);
      tools.SetValue(mep + 'consigntele', item.consigntele);
      tools.SetValue(mep + 'testrequest', item.testrequest);
      tools.SetValue(mep + 'reviewresult', item.reviewresult);
      tools.SetValue(mep + 'reviewhost', item.reviewhost);
      tools.SetValue(mep + 'reviewhostname', item.reviewhostname);
      tools.SetValue(mep + 'reviewhostdate', item.reviewhostdate);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
      
      me.params.clear();
      
      var dparams = tools.JsonGet(tools.GetUrl('ContGetListContractReviewParam.do?crp.tranid=') + id);

      if (dparams && dparams.data) {
        for (i = 0; i < dparams.data.length; i++) {
          var param = dparams.data[i];
          
          if (me.params.get(param.sampleid) != null) {
            me.params.get(param.sampleid).push({parameterid: param.parameterid, 
              parametername: param.parametername });
          }
          else {
            me.params.add(param.sampleid, [{parameterid: param.parameterid, 
              parametername: param.parametername }]);
          }
        }
      }
      me.OnDetailRefresh();
      return true;
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnParameterSave: function (e, data) {
    var me = this;
    var mep = me.tranPrefix;
    var record = me.detailRecord;
    
    if (record == null) record = me.sampGrid.store.model.create({});
    
    for(var j = 0;j < me.sampGrid.store.getCount();j++ ){
      if(data.sampleid == me.sampGrid.store.getAt(j).data.sampleid){
        tools.alert('样品'+data.samplename+'已在列表中！');
        return;
      }
    }
    record.data.sampleid = data.sampleid;
    record.data.samplename = data.samplename;

    if (me.detailRecord){
      me.sampGrid.getView().refresh();
    }else{
      me.sampGrid.store.insert(me.sampGrid.store.getCount(), record); 
    }
  },
  
  OnGetSaveParams : function() {
    var me = this, mep = me.tranPrefix;
    var details = [];
    var dparams = [];
    var sample = [];
    var i = 0, j = 0;
    
    for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
    }
    
    for (i = 0; i < me.sampGrid.store.getCount(); i++) {
      sample.push(me.sampGrid.store.getAt(i).data);
      
      var dparam = me.params.get(me.sampGrid.store.getAt(i).data.sampleid);
      
      if (dparam) {
        for (j = 0; j < dparam.length; j++) {
          dparams.push({sampleid: me.sampGrid.store.getAt(i).data.sampleid, parameterid: dparam[j].parameterid,parametername: dparam[j].parametername })
        }
      }
    } 
    
    me.saveParams = { params: Ext.encode(dparams), details: Ext.encode(details), sample:Ext.encode(sample)};
  },
  
  // 页面空间授权处理
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
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete','btnSampAdd','btnSampDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnSampAdd','btnSampDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete','btnSampAdd','btnSampDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
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
  }

  
});
