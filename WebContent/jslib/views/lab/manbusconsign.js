Ext.define('alms.manbusconsign', {
  extend: 'gpersist.base.busform',
  getdetail: null,
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '检验委托书',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busconsign',
      storeUrl: 'LabSearchBusGet.do',
      saveUrl: 'LabSaveConsignGet.do',
      expUrl: 'LabSearchBusGet.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_busconsignsample',
      urlDetail: 'LabGetListBusGetDetail.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '委托样品明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'tranid',
      hasGridSelect: true,
      hasDetailCheck:false,
      winWidth:900,
      winHeight:500,
      hasPageDetail: false
    });
    me.callParent(arguments);
    me.getdetail = null;
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnManyConsign', text: '同批次委托', iconCls: 'icon-add', handler: me.OnManyConsign,scope: me},
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
    
    me.OnInitGridToolBar();
    
    var busgetitems = [
      ' ', { xtype: 'textfield', fieldLabel: '委托单位', labelWidth: 70, width: 200, maxLength: 200, name: 'searchtested', id: mep + 'searchtested', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTestedSearch, scope: me }
    ];
   
    var tested = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '委托单位', name: 'bg.testedname', id: mep + 'testedname', winTitle: '选择委托单位',
      maxLength: 200, maxLengthText: '委托单位不能超过200字！', selectOnFocus: false, labelWidth: 80,
      blankText: '委托单位不能为空！', allowBlank: false, anchor: '96%', tabIndex: 1,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_testedunit',
      storeUrl: 'BasSearchBusTestedUnit.do',
      editable:true,
      searchTools:busgetitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
   
    tested.on('griditemclick', me.OnTestedSelect, me);
    
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('委托编号', 'bg.tranid', mep + 'tranid', 20, '96%', true),
          tools.FormText('委托人', 'bg.testeduser', mep + 'testeduser', 20, '96%', true),
          tools.FormCombo('余样处置', 'bg.restdeal', mep + 'restdeal', tools.ComboStore('RestDeal', gpersist.SELECT_MUST_VALUE), '96%', false)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tested,
          tools.FormDate('委托时间', 'bg.testeddate', mep + 'testeddate', 'Y-m-d', '96%', true, 3, nowdate),
          tools.FormCombo('样品类别', 'bg.productcate', mep + 'productcate', tools.ComboStore('ProductCate', gpersist.SELECT_MUST_VALUE), '96%', false, 4)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('联系电话', 'bg.entertele', mep + 'entertele', 20, '96%', true),
          tools.FormCombo('时间要求', 'bg.timereq', mep + 'timereq', tools.ComboStore('TimeReq', gpersist.SELECT_MUST_VALUE), '96%', true, 4)
        ]},
        { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
          tools.FormText('邮政编码', 'bg.enterpost', mep + 'enterpost', 20, '96%', true, 2, null, 90),
          tools.FormCombo('报告提取方式', 'bg.reportget', mep + 'reportget', tools.ComboStore('ReportGet', gpersist.SELECT_MUST_VALUE), '96%', true, null,90)
        ]}
      ]},
      
      tools.FormTextArea('通讯地址', 'bg.enteraddr', mep + 'enteraddr', 200, '100%', true, null, 2),
      {xtype:'hiddenfield',name:'bg.testedid',id: mep + 'testedid'},
      {xtype:'hiddenfield',name:'bg.entertype',id: mep + 'entertype'},
      {xtype:'hiddenfield',name:'bg.enterscale',id: mep + 'enterscale'},
      {xtype:'hiddenfield',name:'bg.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bg.deal.action',id: mep + 'datadeal'}
    ];
      
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['testedname','testedid','testeduser','testeddate','timereq','reportget','restdeal','entertele','enterpost','enteraddr','productcate'];
    me.enEdits = ['testedname','testedid','testeduser','testeddate','timereq','reportget','restdeal','entertele','enterpost','enteraddr','productcate'];
  },
  
  OnTestedSelect:function(render,record){
    var me = this;
    var mep = me.tranPrefix;
    console.log(gpersist.GetUserInfo());
    if(record && record.testedid){
      tools.SetValue(mep+'testedid',record.testedid);
      tools.SetValue(mep + 'testedname', record.testedname);
      tools.SetValue(mep + 'entertele', record.entertele);
      tools.SetValue(mep + 'enterpost', record.enterpost);
      tools.SetValue(mep + 'entertype', record.entertype);
      tools.SetValue(mep + 'enterscale', record.enterscale);
      tools.SetValue(mep + 'enteraddr', record.enteraddr);
      tools.SetValue(mep + 'testeduser', record.enterlegal);
    }
  },
//  OnInitData : function() {
//	    var me = this;
//	    var mep = me.tranPrefix;
//	    console.log(gpersist.UserInfo.user)
//	    me.callParent(arguments);
//	    if(gpersist.UserInfo.user.deptid='8003'){
//	    	 tools.SetValue(mep + 'productcate','06');
//	    }
//	   
//	    
//	  },
  OnTestedSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var tested = Ext.getCmp(mep+'testedname');
    
    if(tested.store){
      tested.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'btu.testedname': tools.GetValueEncode(mep + 'searchtested')
        });
      });
    }
    tested.store.loadPage(1);
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && !Ext.isEmpty(item.userid)) {
      tools.SetValue(mep + 'sampleuser', item.userid);
      tools.SetValue(mep + 'sampleusername', item.username);
    };
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      '-',  { id: mep + 'btnCommit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me },
      '-', ' ', { id : mep + 'btnView', text : '委托书查看', iconCls : 'icon-outlook', handler : me.OnView, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnListNew : function(){
    var me = this;
    var mep = this.tranPrefix;
    
    me.getdetail = tools.GetPopupWindow('alms', 'editconsigndetail', 
      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'gd', dataDeal: gpersist.DATA_DEAL_NEW})
    me.getdetail.on('formlast', me.OnDetailSave, me);
    me.getdetail.OnFormLoad();
//    me.getdetail.OnInitData();
    me.getdetail.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    me.detailRecord = null;
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this, mep = me.tranPrefix;
    var tranid = tools.GetValue(mep + 'tranid');
    me.getdetail = tools.GetPopupWindow('alms', 'editconsigndetail', 
      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'gd', dataDeal: me.dataDeal})
    me.getdetail.on('formlast', me.OnDetailSave, me);
    me.getdetail.OnFormLoad();
    me.getdetail.OnSetData(record,tranid);
    me.getdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.detailRecord = record;
  },
  
  OnDetailSave: function (e, data) {
    var me = this;
    var mep = me.tranPrefix;
    var record = me.detailRecord;
    if (record == null){
      record = me.plDetailGrid.store.model.create({});
    }
    record.data.sampleid = data.sampleid;
    record.data.samplename = data.samplename;
    record.data.samplestand = data.samplestand;
    record.data.factname = data.factname;
    record.data.samplecount = data.samplecount;
    record.data.getaddr = data.getaddr;
    record.data.samplebase = data.samplebase;
    record.data.trademark = data.trademark;
    record.data.samplelevel = data.samplelevel;
    record.data.prdcode = data.prdcode;
    record.data.samplestatus = data.samplestatus;
    record.data.parameterids = data.parameterids;
    record.data.parameternames = data.parameternames;
    record.data.standtype1 = data.standtype1;
    record.data.standtype2 = data.standtype2;
    record.data.standtype3 = data.standtype3;
    record.data.standtype4 = data.standtype4;
    record.data.standtype5 = data.standtype5;
    
    if (me.detailRecord)
      me.plDetailGrid.getView().refresh();
    else
      me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record); 
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        me.OnBeforeListSave(record);
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      } else {
        me.OnBeforeListSave(me.detailRecord);
        me.plDetailGrid.getView().refresh();
      };
      me.winDetail.hide();
    };
  },
  
  OnBeforeListSave:function(record){
    var me = this;
    var mep = me.tranPrefix;
    record.data.sampleid = tools.GetValue(mep+'sampleid');
    record.data.samplename = tools.GetValue(mep+'samplename');
    record.data.samplestand = tools.GetValue(mep+'samplestand');
    record.data.factname = tools.GetValue(mep+'factname');
    record.data.samplecount = tools.GetValue(mep+'samplecount');
    record.data.getaddr = tools.GetValue(mep+'getaddr');
    record.data.samplebase = tools.GetValue(mep+'samplebase');
    record.data.trademark = tools.GetValue(mep+'trademark');
    record.data.samplelevel = tools.GetValue(mep+'samplelevel');
    record.data.prdcode = tools.GetValue(mep+'prdcode');
    record.data.samplestatus = tools.GetValue(mep+'samplestatus');
  },
  
  OnListDelete:function(){
    var me = this;
    var j = me.plDetailGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    };
    
    me.plDetailGrid.getView().refresh();
  },

  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bg.gettype': '10',
          'bg.testedname':tools.GetValueEncode(mep+'searchtestedname')
        });
      });
    };
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var count = me.plDetailGrid.store.getCount();
    if(count==0){
      tools.alert('委托样品明细不能为空，需添加！');
      return false;
    }
//    if(count>1){
//        tools.alert('一次只能委托一个样品');
//        return false;
//      }
    
    return true;
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusGetDetail.do?bgd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnLoadData : function(record) {
	 
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusGet.do?bg.tranid='+record.tranid);
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'entertele', item.entertele);
      tools.SetValue(mep + 'enterpost', item.enterpost);
      tools.SetValue(mep + 'testeddate', item.testeddate);
      tools.SetValue(mep + 'testeduser', item.testeduser);
      tools.SetValue(mep + 'timereq', item.timereq);
      tools.SetValue(mep + 'reportget', item.reportget);
      tools.SetValue(mep + 'restdeal', item.restdeal);
      tools.SetValue(mep + 'enteraddr', item.enteraddr);
      tools.SetValue(mep + 'entertype', item.entertype);
      tools.SetValue(mep + 'enterscale', item.enterscale);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'productcate', item.productcate);
      me.OnDetailRefresh();
      return true;
    }else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    };
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;

    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
          tools.SetValue(mep + 'testedid', action.result.data.testedid);
        };
      };
    };
    me.OnDetailRefresh();
  },
  
  OnPrintSample: function () {
    var me = this, mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetConsignCode.do?bc.tranid=' + tools.GetValue(mep + 'tranid'));
    
    if (item && item.data) {
      var codes = item.data;
      var html = '';
      
      for (var i = 0; i < codes.length; i++) {
        if (i == codes.length - 1){
          html += '<table>';
        }
        else{
          html += '<table style="page-break-after: always;">';
        }
        html += '<tr><td><image width="120px" height="40px" src="barcode?msg=' + codes[i].sampletran + '&type=code128&fmt=jpeg&height=12&hrsize=3mm"></td></tr>'
          + '<tr><td style="font-size:10px;font-weight:bold;">' + codes[i].collectstatusname + '</td></tr>';
        html += '</table>';
      }
      
      if (!Ext.isEmpty(html)) {
        Ext.ux.Print.QRCodePrint.print(html);
      }
    }
  },
  
  OnView: function () {
    var me = this, mep = me.tranPrefix;
    var tranid = tools.GetValue(mep+'tranid');
    if (!me.winpreview) {
      me.winpreview = tools.GetPopupWindow('alms', 'previewget', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'pr', dataDeal: gpersist.DATA_DEAL_NEW});
      
      me.winpreview.OnFormLoad();
    }
    else
      me.winpreview.OnFormShow();
      
    me.winpreview.OnSetData('000006',tranid,'10');
  },
  
//提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);

    if(record.flowstatus == '01'){
      tools.BtnsEnable(['btnEdit'], mep);
    } else{
      tools.BtnsDisable(['btnEdit'], mep);
    }
  },
  
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus == '01'){
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnCommit'], mep);
    } else{
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnCommit'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsEnable(['btnCommit'], mep);
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  OnManyConsign:function () {
      var me = this;
      var mep = me.tranPrefix;
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      if (datas.length < 1) {
          tools.alert('请选择一个委托书........');
          return false;

      } else if (datas.length > 1) {
          tools.alert('只能选择单项委托书........');
          return false;
      } else {
          
          me.OnCreateCheckWin();
          if (me.winCheck) {
              me.winCheck.show();
          };

          tools.SetValue(mep + 'datadeal', '9');
      }


  },

  OnCreateCheckWin: function () {
      var me = this;
      var mep = me.tranPrefix;

      me.winWidth = 250;
      me.winHeight = 100;

      if (Ext.getCmp(mep + 'checkwin')) {
          Ext.getCmp(mep + 'checkwin').destroy();
      };

      var checkitems = [
			      ' ', {
              id: mep + 'btnCheckSave',
              text: '提交',
              iconCls: 'icon-save',
              handler: me.OnGetConsign,
              scope: me
          },
			      '-', ' ', {
              id: mep + 'btnCheckClose',
              text: gpersist.STR_BTN_CLOSE,
              iconCls: 'icon-close',
              handler: function () {
                  me.winCheck.hide();
                  me.detailEditType = 1;
              }
          }
			    ];

      me.editCheckControls = [
			      tools.FormText('', 'checkdesc', mep + 'wincheckdesc', 3, '100%', false,3,'isinteger')
			    ];

      me.disNews = [];
      me.disEdits = [];
      me.enNews = ['wincheckdesc'];
      me.enEdits = ['wincheckdesc'];

      me.plCheckEdit = Ext.create('Ext.form.Panel', {
          id: mep + 'plcheckedit',
          columnWidth: 1,
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
          tbar: Ext.create('Ext.toolbar.Toolbar', {
              items: checkitems
          }),
          items: me.editCheckControls
      });

      me.winCheck = Ext.create('Ext.Window', {
          id: mep + 'checkwin',
          title: '请填入批次数量（只能填整数）',
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
          items: [me.plCheckEdit]
      });
  },
  
  OnGetConsign: function (action) {
      var me = this;
      var mep = me.tranPrefix;
      var datas = me.plGrid.getView().getSelectionModel().getSelection();

      var tranid = me.plGrid.selModel.selected.items[0].data.tranid;
      var modifydesc = tools.GetValue(mep + 'wincheckdesc')
    var param = tools.JsonGet('LabGetManyConsign.do?bt.tranid=' + tranid + '&bt.modifydesc=' +
    	          modifydesc);

    	      tools.alert(param.msg);
    	      me.winCheck.hide();
      
      
  },

  
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record, item;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == 0) {
          tools.alert('已经是当前列表第一条数据！');
          return;
        }

        me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
        item = me.plGrid.store.getAt(i - 1).data;
        
        if(item.flowstatus =='01' ){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnCommit','btnFormEdit'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == j - 1) {
          tools.alert('已经是当前列表最后一条数据！');
          return;
        }
        
        me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
        
        item = me.plGrid.store.getAt(i + 1).data;
        
        if(item.flowstatus =='01'){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnCommit','btnFormEdit'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
        me.OnFormValidShow();
        return;
      }
    }
  }
  
});
