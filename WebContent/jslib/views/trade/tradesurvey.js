Ext.define('alms.tradesurvey',{
  extend:'gpersist.base.busform',
   
  constructor:function(config){
    var me = this;
    Ext.apply(config,{
    editInfo:'供应商调查',
    winWidth:900,
    winHeight:300,
    hasColumnUrl:true,
    columnUrl:'SysSqlColumn.do?sqlid=p_get_tradesurvey',
    storeUrl:'DevSearchTradeSurvey.do',
    saveUrl: 'DevSaveTradeSurvey.do',
    expUrl: 'DevSearchTradeSurvey.do',
    hasGridSelect:true,
    hasPage: true,
    hasPrevNext: true,
    hasSubmit:true,
    idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
   
  OnBeforeFormLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnInitGridToolBar();
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '供应商编号', labelWidth: 80, width: 180, maxLength: 40, name: 'searchtradeid', id: mep + 'searchtradeid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '供应商名称', labelWidth: 80, width: 180, maxLength: 40, name: 'searchtradename', id: mep + 'searchtradename', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    tools.SetToolbar(items, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    me.tbGrid.add(toolbar);	   
  },
   
  OnBeforeCreateEdit:function(){
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var tradeitems = [
      ' ', { xtype: 'textfield', fieldLabel: '供应商名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchtradsenames', id: mep + 'searchtradenames', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTradeSearch, scope: this }
    ];
    
    var trade = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '供应商名称', name: 'trsu.tradename', id: mep + 'tradename', winTitle: '选择供应商',
      maxLength: 20, maxLengthText: '供应商名称不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '供应商名称不能为空！', allowBlank: false, anchor: '96%', tabIndex: 1,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bustrade',
      storeUrl: 'DevSearchBusTrade.do',
      editable:true,
      searchTools:tradeitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    trade.on('griditemclick', me.OnTradeSelect, me);
	   
    me.editControls=[
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'trsu.tranid', mep + 'tranid', 20, '96%', false),
          tools.FormText('联系人', 'trsu.linkman', mep + 'linkman', 20, '96%', true),
          tools.FormText('产品名称', 'trsu.prdname', mep + 'prdname', 20, '96%', true, 2),
          tools.FormCheckCombo('资格证书', 'trsu.entercret', mep + 'entercret',tools.ComboStore('EnterCret'), '96%', true, 5),
          tools.FormCombo('管理', 'trsu.prdmanage', mep + 'prdmanage', tools.ComboStore('PrdManage', gpersist.SELECT_MUST_VALUE), '96%', false, 8)
          
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          trade,
          tools.FormText('联系电话', 'trsu.linktele', mep + 'linktele', 32, '96%', true, 3,'isphone',90),
          tools.FormCombo('企业规模', 'trsu.enterscale', mep + 'enterscale', tools.ComboStore('EnterScale', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90),
          tools.FormCombo('产品质量', 'trsu.prdquality', mep + 'prdquality', tools.ComboStore('PrdQuality', gpersist.SELECT_MUST_VALUE), '96%', false, 6,90),
          tools.FormCombo('生产检查设备', 'trsu.prdcheck', mep + 'prdcheck', tools.ComboStore('PrdCheck', gpersist.SELECT_MUST_VALUE), '96%', false, 9,90)
        ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText('邮政编码', 'trsu.linkpost', mep + 'linkpost', 6, '100%', true),
          tools.FormCombo('价格', 'trsu.prdprice', mep + 'prdprice', tools.ComboStore('PrdPrice', gpersist.SELECT_MUST_VALUE), '100%', true, 11),
          tools.FormCombo('企业知名度', 'trsu.enterpopular', mep + 'enterpopular', tools.ComboStore('EnterPopular', gpersist.SELECT_MUST_VALUE), '100%', false, 4),
          tools.FormCombo('试用情况', 'trsu.prdtest', mep + 'prdtest', tools.ComboStore('PrdTest', gpersist.SELECT_MUST_VALUE), '100%', false, 7),
          tools.FormCombo('服务质量', 'trsu.prdservice', mep + 'prdservice', tools.ComboStore('PrdService', gpersist.SELECT_MUST_VALUE), '100%', false, 10),
          {xtype:'hiddenfield',name:'trsu.entercretname',id: mep + 'entercretname'},
          {xtype:'hiddenfield',name:'trsu.tradeid',id: mep + 'tradeid'},
          {xtype:'hiddenfield',name:'trsu.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('单位地址', 'trsu.linkaddress', mep + 'linkaddress', 200, '100%', true, null, 3)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['prdname','entercret','prdmanage','tradeid','prdprice','enterscale',
                 'prdquality','prdcheck','enterpopular','prdtest','prdservice','linkman','linktele','linkpost','linkaddress','tradename'];
    me.enEdits = ['prdname','entercret','prdmanage','tradeid','prdprice','enterscale',
                  'prdquality','prdcheck','enterpopular','prdtest','prdservice','linkman','linktele','linkpost','linkaddress','tradename'];
    Ext.getCmp(mep + 'entercret').on('change', me.SetEnterCretName, me);
  },
  
  OnTradeSearch:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.OnTradeBeforeLoad();
    var trade = Ext.getCmp(mep+'tradeid');
    trade.store.loadPage(1);
  },
  
  OnTradeBeforeLoad: function(){
    var me = this;
    var mep = this.tranPrefix;
    
    var trade = Ext.getCmp(mep+'tradeid');
    if(trade.store) {      
      trade.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'bustrade.tradename': tools.GetValueEncode(mep + 'searchtradenames')
        });
      });
    };
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'trsu.tradeid': tools.GetEncode(tools.GetValue(mep + 'searchtradeid')),
          'trsu.tradename': tools.GetEncode(tools.GetValue(mep + 'searchtradename'))
        });
      });
    };
  },
   
  SetEnterCretName: function(){
    var me = this;
    var mep = me.tranPrefix;
     
    tools.SetValue(mep + 'entercretname', Ext.getCmp(mep + 'entercret').getDisplayValue());
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
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'enterscale', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'enterpopular', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'entercret', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdquality', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdtest', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdmanage', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdcheck', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdservice', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdprice', gpersist.SELECT_MUST_VALUE);
  },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'tradeid', item.tradeid);
    tools.SetValue(mep + 'linkman', item.linkman);
    tools.SetValue(mep + 'linktele', item.linktele);
    tools.SetValue(mep + 'linkpost', item.linkpost);
    tools.SetValue(mep + 'linkaddress', item.linkaddress);
    tools.SetValue(mep + 'tradename', item.tradename);
    tools.SetValue(mep + 'prdname', item.prdname);
    tools.SetValue(mep + 'entercret', item.entercret.split(','));
    tools.SetValue(mep + 'flowaction', item.flowaction);
    tools.SetValue(mep + 'flowstatus', item.flowstatus);
    tools.SetValue(mep + 'prdmanage', item.prdmanage);
    tools.SetValue(mep + 'enterscale', item.enterscale);
    tools.SetValue(mep + 'prdquality', item.prdquality);
    tools.SetValue(mep + 'prdcheck', item.prdcheck);
    tools.SetValue(mep + 'enterpopular', item.enterpopular);
    tools.SetValue(mep + 'prdtest', item.prdtest);
    tools.SetValue(mep + 'prdservice', item.prdservice);
    tools.SetValue(mep + 'prdprice', item.prdprice);
    tools.BtnsEnable(['btnFormCommit'], mep);
    return true;
  },
   
  OnTradeSelect:function(render,item){
	  var me = this;
	  var mep = me.tranPrefix;
	    
	  if (item && item.tradeid) {
	    tools.SetValue(mep+'tradeid',item.tradeid);
	    tools.SetValue(mep+'tradename',item.tradename);
	    tools.SetValue(mep+'linkman',item.linkman);
	    tools.SetValue(mep+'linktele',item.linktele);
	    tools.SetValue(mep+'linkpost',item.linkpost);
	    tools.SetValue(mep+'linkaddress',item.linkaddress);
	  };
  },
    
  OnUserSelect : function (render, item) {
    var me = this;
    var mep = me.tranPrefix;
    if (item && !Ext.isEmpty(item[0].userid)) {
      tools.SetValue(mep + 'tranuser', item[0].userid);
      tools.SetValue(mep + 'tranusername', item[0].username);
    };
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var enterscale = tools.GetValue(mep+'enterscale');
    var enterpopular = tools.GetValue(mep+'enterpopular');
    var entercret = tools.GetValue(mep+'entercret');
    var prdquality = tools.GetValue(mep+'prdquality');
    var prdtest = tools.GetValue(mep+'prdtest');
    var prdmanage = tools.GetValue(mep+'prdmanage');
    var prdcheck = tools.GetValue(mep+'prdcheck');
    var prdservice = tools.GetValue(mep+'prdservice');
    var prdprice = tools.GetValue(mep+'prdprice');
   
    if(enterscale ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择企业规模！');
       return;
    }
   
    if(enterpopular ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择企业知名度！');
      return;
    }
    if(entercret ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择资格证书！');
      return;
    }
    if(prdquality ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择产品质量！');
      return;
    }
    if(prdtest ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择使用情况！');
      return;
    }
    if(prdmanage ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择管理！');
      return;
    }
    if(prdcheck ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择生产设配检查！');
      return;
    }
    if(prdservice ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择服务质量！');
      return;
    }
    if(prdprice ==gpersist.SELECT_MUST_VALUE ){
      tools.alert('请选择价格！');
      return;
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
           tools.SetValue(mep + 'tradeid', action.result.data.tradeid);
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