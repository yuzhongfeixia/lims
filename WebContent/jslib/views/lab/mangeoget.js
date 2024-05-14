Ext.define('alms.mangeoget', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '地理标识抽样单管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busget',
      storeUrl: 'LabSearchBusGet.do',
      saveUrl: 'LabSaveBusGetGeo.do',
      expUrl: 'LabSearchBusGet.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_geogetdetail',
      urlDetail: 'LabGetListBusGetDetail.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '抽样单明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'tranid',
      hasGridSelect: true,
      hasDetailCheck:false,
      hasPageDetail: false
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 60, width: 200, maxLength: 100, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
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
    
    var getnoticeitems = [
      ' ', { xtype: 'textfield', fieldLabel: '通知单号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnNoticeSearch, scope: me }
    ];
   
    var getnotice = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '取样通知单号', name: 'bg.noticeid', id: mep + 'noticeid', winTitle: '选择取样通知单号',
      maxLength: 20, maxLengthText: '受检单位不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '取样通知单号不能为空！', allowBlank: true, anchor: '96%', tabIndex: 1,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_getnotice',
      storeUrl: 'LabSearchBusGetNoticeForGet.do?bgn.gettype=07',
      editable:false,
      searchTools:getnoticeitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
   
    getnotice.on('griditemclick', me.OnNoticeSelect, me);
                   
    me.editControls = [ 
      { xtype: 'fieldset', collapsible: true, title: '受检单位', anchor: '100%', items: [  
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('抽样单号', 'bg.tranid', mep + 'tranid', 20, '96%', true,null,null,90),
            tools.FormText('联系电话', 'bg.entertele', mep + 'entertele', 20, '96%', true,null,null,90),
            tools.FormDate('采样日期', 'bg.getdate', mep + 'getdate', 'Y-m-d', '96%', true, 5,nowdate,90)
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            getnotice,
            tools.FormText('邮政编码', 'bg.enterpost', mep + 'enterpost', 20, '96%', true,null,null,90),
            tools.FormText('受检单位代表', 'bg.testeduser', mep + 'testeduser', 10, '96%', true,3,null,90)
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('受检企业', 'bg.testedname', mep + 'testedname', 200, '96%', true,null,null,90),
            tools.FormText('传真', 'bg.enterfax', mep + 'enterfax', 20, '96%', true,null,null,90)
            
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('法定代表人', 'bg.enterlegal', mep + 'enterlegal', 20, '100%', true,null,null,90),
            tools.FormCombo('检验类别', 'bg.testtype', mep + 'testtype', tools.ComboStore('TestType', gpersist.SELECT_MUST_VALUE), '100%', false, null,90)
            
          ]}
        ]},
        tools.FormTextArea('通讯地址', 'bg.enteraddr', mep + 'enteraddr', 30, '100%', true, null, 2,90)
      ]},
      
      { xtype: 'fieldset', collapsible: true, title: '抽样单位', anchor: '100%', items: [  
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('检验机构全称', 'bg.testdept', mep + 'testdept', 30, '96%', true,null,null,90)
           
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('联系电话', 'bg.testtele', mep + 'testtele', 20, '96%', true,null,null,90)
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('传真', 'bg.testfax', mep + 'testfax', 20, '96%', true,null,null,90)
          ]},
          { xtype: 'container', columnWidth: .25, layout: 'anchor', items: [
            tools.FormText('邮编', 'bg.testpost', mep + 'testpost', 20, '100%', true,null,null,90)
          ]}
        ]},
        tools.FormTextArea('通讯地址', 'bg.testaddr', mep + 'testaddr', 30, '100%', true, null, 2,90)
      ]},
      {xtype:'hiddenfield',name:'bg.gettype',id: mep + 'gettype'},
      {xtype:'hiddenfield',name:'bg.entertype',id: mep + 'entertype'},
      {xtype:'hiddenfield',name:'bg.enterscale',id: mep + 'enterscale'},
      {xtype:'hiddenfield',name:'bg.sampleusername',id: mep + 'sampleusername'},
      {xtype:'hiddenfield',name:'bg.testedid',id: mep + 'testedid'},
      {xtype:'hiddenfield',name:'bg.sampleuser',id: mep + 'sampleuser'},
      {xtype:'hiddenfield',name:'bg.flowstatus',id: mep + 'flowstatus'},
      {xtype:'hiddenfield',name:'bg.deal.action',id: mep + 'datadeal'}
    ];
      
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['noticeid','entermanager','tranremark','testeduser','trustdept','prdstand',
                 'getdate','sendtype','senddate','sendaddr','basename','basearea','sampledeep',
                 'tasksource','sendtype','testedtele','testedfax','testdept','testfax','testlink',
                 'testemail','testtele','testpost','testaddr','testedname','enterlegal','entertele','enterpost','testtype',
                  'entertype','enterscale','enteraddr','gettype','sampleusername','enterfax'];
    me.enEdits = ['noticeid','entermanager','tranremark','testeduser','trustdept','prdstand',
                  'getdate','senddate','sendaddr','basename','basearea','sampledeep',
                  'tasksource','sendtype','testedtele','testedfax','testdept','testfax','testlink',
                  'testemail','testtele','testpost','testaddr','testedname','enterlegal','entertele','enterpost','testtype',
                  'entertype','enterscale','enteraddr','gettype','sampleusername','enterfax'];  
  },
  
  OnNoticeSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+'gettype',item.gettype);
    tools.SetValue(mep+'testtype',item.testtype);
    tools.SetValue(mep+'noticeid',item.tranid);
    tools.SetValue(mep+'sampleuser',item.getuser);
    tools.SetValue(mep+'sampleusername',item.getusername);
    tools.SetValue(mep+'testedname',item.testedname);
    if (item && item.testedid) {
      var testedunit = tools.JsonGet('LabGetBusTestedUnit.do?btu.testedid='+item.testedid);
      tools.SetValue(mep+'testedid',testedunit.testedid);
      tools.SetValue(mep+'testedname',testedunit.testedname);
      tools.SetValue(mep+'entertele',testedunit.entertele);
      tools.SetValue(mep+'enterpost',testedunit.enterpost);
      tools.SetValue(mep+'entertype',testedunit.entertype);
      tools.SetValue(mep+'enterscale',testedunit.enterscale);
      tools.SetValue(mep+'enteraddr',testedunit.enteraddr);
      tools.SetValue(mep+'enterlegal',testedunit.enterlegal);
    }else{
    //可编辑处理
      tools.Enabled(['testedname','enterlegal','entertele','enterpost','testtype',
                     'entertype','enterscale','enteraddr','gettype','sampleusername'], mep);
    }
  },
  
  OnNoticeSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var notice = Ext.getCmp(mep+'noticeid');
    
    if(notice.store){
      notice.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'bgn.tranid': tools.GetValueEncode(mep + 'searchtranid')
        });
      });
    }
    notice.store.loadPage(1);
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && !Ext.isEmpty(item.userid)) {
      tools.SetValue(mep + 'sampleuser', item.userid);
      tools.SetValue(mep + 'sampleusername', item.username);
    };
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'sendtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'testdept', '农业部农产品质量安全监督检验测试中心（南京）');
    tools.SetValue(mep + 'testfax', '025-86229784');
    tools.SetValue(mep + 'testlink', '王磊');
    tools.SetValue(mep + 'testemail', 'wanglei@jsagri.gov.cn');
    tools.SetValue(mep + 'testtele', '025-86263553');
    tools.SetValue(mep + 'testpost', '210036');
    tools.SetValue(mep + 'testaddr', '南京市草场门大街124号');
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      '-',  { id: mep + 'btnCommit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me },
      '-', ' ', { id : mep + 'btnView', text : '抽样单查看', iconCls : 'icon-outlook', handler : me.OnView, scope : me },
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var sampleitems = [
      ' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
    ];
    
    var sample = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'samplename', id: mep + 'samplename', winTitle: '选择样品',
      maxLength: 20, maxLengthText: '样品编号不能超过10字！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品编号不能为空！', allowBlank: false, anchor: '96%', tabIndex: null,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    sample.on('griditemclick', me.OnSampleSelect, me);
    
    me.winWidth=900,
    me.winHeight=210,
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          sample,
          tools.FormText('采样部位', 'getbody', mep + 'getbody', 20, '96%', false,23)
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormCombo('保存条件', 'storetype', mep + 'storetype', tools.ComboStore('StoreType', gpersist.SELECT_MUST_VALUE), '96%', false, null),
          tools.FormText('样品数量', 'samplecount', mep + 'samplecount', 20, '96%', false,19)
        ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText('抽样方法', 'getmethod', mep + 'getmethod', 20, '100%', false,13),
          tools.FormText('样本基数', 'samplebase', mep + 'samplebase', 20, '100%', false,24)
        ]}
      ]},
      tools.FormTextArea('抽样地点', 'getaddr', mep + 'getaddr', 200, '100%', false, 29,2),
      tools.FormTextArea('样品状态', 'samplestatus', mep + 'samplestatus', 200, '100%', false, 29, 2),
      {xtype:'hiddenfield',name:'sampleid',id: mep + 'sampleid'}
    ];
    me.disDetailControls = [];
    me.enDetailControls = ['sampleid','samplename','getbody','storetype',
                           'samplecount','getmethod','samplebase','getaddr','samplestatus']; 
      
  },
  
  OnSampleSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && !Ext.isEmpty(item.sampleid)) {
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
    };
  },
  
  OnSampleSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var sample = Ext.getCmp(mep+'sampleid');
    
    if(sample.store){
      sample.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'bsample.sampleid': tools.GetValueEncode(mep + 'searchsampleid'),
          'bsample.samplename': tools.GetValueEncode(mep + 'searchsamplename')
        });
      });
    }
    sample.store.loadPage(1);
  },
  
  OnListNew : function(){
    var me = this;
    var mep = this.tranPrefix;
    
    me.getdetail = tools.GetPopupWindow('alms', 'editgeogetdetail', 
      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'ggd', dataDeal: gpersist.DATA_DEAL_NEW})
    me.getdetail.on('formlast', me.OnDetailSave, me);
    me.getdetail.OnFormLoad();
    me.getdetail.OnAuthEditForm(gpersist.DATA_DEAL_NEW);
    me.detailRecord = null;
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
    record.data.getbody = data.getbody;
    record.data.storetype = data.storetype;
    record.data.samplecount = data.samplecount;
    record.data.getmethod = data.getmethod;
    record.data.samplebase = data.samplebase;
    record.data.getaddr = data.getaddr;
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
          'bg.gettype': '07',
          'bg.testedname':tools.GetValueEncode(mep+'searchtestedname')
        });
      });
    };
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusGetDetail.do?bgd.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnListSelect: function(e, record, item, index) {
    var me = this, mep = me.tranPrefix;
    var tranid = tools.GetValue(mep + 'tranid');
    
    me.getdetail = tools.GetPopupWindow('alms', 'editgeogetdetail', 
      {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'ggd', dataDeal: me.dataDeal})
    me.getdetail.on('formlast', me.OnDetailSave, me);
    me.getdetail.OnFormLoad();
    me.getdetail.OnSetData(record,tranid);
    me.getdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    me.detailRecord = record;
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var item = tools.JsonGet('LabGetBusGet.do?bg.tranid='+record.tranid);
    if (item && !Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'enterlegal', item.enterlegal);
      tools.SetValue(mep + 'enteraddr', item.enteraddr);
      tools.SetValue(mep + 'enterpost', item.enterpost);
      tools.SetValue(mep + 'enterfax', item.enterfax);
      tools.SetValue(mep + 'enterlink', item.enterlink);
      tools.SetValue(mep + 'entertele', item.entertele);
      tools.SetValue(mep + 'entertype', item.entertype);
      tools.SetValue(mep + 'enterscale', item.enterscale);
      tools.SetValue(mep + 'entermanager', item.entermanager);
      tools.SetValue(mep + 'testeduser', item.testeduser);
      tools.SetValue(mep + 'testedtele', item.testedtele);
      tools.SetValue(mep + 'testedfax', item.testedfax);
      tools.SetValue(mep + 'testeddate', item.testeddate);
      tools.SetValue(mep + 'testdept', item.testdept);
      tools.SetValue(mep + 'testaddr', item.testaddr);
      tools.SetValue(mep + 'testlink', item.testlink);
      tools.SetValue(mep + 'testpost', item.testpost);
      tools.SetValue(mep + 'testtele', item.testtele);
      tools.SetValue(mep + 'testfax', item.testfax);
      tools.SetValue(mep + 'testemail', item.testemail);
      tools.SetValue(mep + 'sampleuser', item.sampleuser);
      tools.SetValue(mep + 'sampleusername', item.sampleusername);
      tools.SetValue(mep + 'userdate', item.userdate);
      tools.SetValue(mep + 'trustdept', item.trustdept);
      tools.SetValue(mep + 'samplerespon', item.samplerespon);
      tools.SetValue(mep + 'sampleresponname', item.sampleresponname);
      tools.SetValue(mep + 'respondate', item.respondate);
      tools.SetValue(mep + 'flowstatus', item.flowstatus);
      tools.SetValue(mep + 'flowaction', item.flowaction);
      tools.SetValue(mep + 'noticeid', item.noticeid);
      tools.SetValue(mep + 'tranuser', item.tranuser);
      tools.SetValue(mep + 'tranusername', item.tranusername);
      tools.SetValue(mep + 'trandate', item.trandate);
      tools.SetValue(mep + 'gettype', item.gettype);
      tools.SetValue(mep + 'testtype', item.testtype);
      tools.SetValue(mep + 'getdate', item.getdate);
      tools.SetValue(mep + 'sendtype', item.sendtype);
      tools.SetValue(mep + 'senddate', item.senddate);
      tools.SetValue(mep + 'sendaddr', item.sendaddr);
      tools.SetValue(mep + 'sampledeep', item.sampledeep);
      tools.SetValue(mep + 'basename', item.basename);
      tools.SetValue(mep + 'basearea', item.basearea);
      tools.SetValue(mep + 'planvar', item.planvar);
      tools.SetValue(mep + 'tasksource', item.tasksource);
      tools.SetValue(mep + 'prdstand', item.prdstand);
      tools.SetValue(mep + 'fileid', item.fileid);
      tools.SetValue(mep + 'teststand', item.teststand);
      tools.SetValue(mep + 'tranremark', item.tranremark);
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
        };
      };
    };
    me.OnDetailRefresh();
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
      
    me.winpreview.OnSetData('000003',tranid,'07');
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var count = me.plDetailGrid.store.getCount();
    if(count==0){
      tools.alert('质量抽样单明细不能为空，需添加！');
      return false;
    }
    
    return true;
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
  
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var j = me.plGrid.store.getCount(), record;
    for ( var i = 0; i < j; i++) {
      record = me.plGrid.store.getAt(i).data;
      
      if (me.OnCheckPrevNext(record)) {
        if (i == 0) {
          tools.alert('已经是当前列表第一条数据！');
          return;
        }
       
        me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
        
        if(me.plGrid.store.getAt(i - 1).data.flowstatus == '01'){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnFormEdit','btnCommit'],mep);
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
        
        if(me.plGrid.store.getAt(i + 1).data.flowstatus == '01'){
          tools.BtnsEnable(['btnFormEdit','btnCommit'],mep);
        }else{
          tools.BtnsDisable(['btnSave','btnFormEdit','btnCommit'],mep);
        };
        
        me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
        me.OnFormValidShow();
        return;
      }
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
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.BtnsDisable(['btnDetailAdd','btnDetailDelete'], mep);         
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord','btnCommit'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.BtnsEnable(['btnDetailAdd','btnDetailDelete'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  }
  
});
