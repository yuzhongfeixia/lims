Ext.define('alms.mansampleice',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'样品贮存冰柜温度记录',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_sampleice',
          storeUrl:'SampSearchSampleIce.do',
          saveUrl:'SampSaveSampleIce.do',
          expUrl:'SampSearchSampleIce.do',
          idPrevNext:'iceid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: false,
          detailTitle: '温度记录明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_sampleicedetail',
          urlDetail: 'SampGetListSampleIceDetail.do',
          detailTabs: 1,
          hasDateSearch: true,
          hasDetailEdit: true
      });
      me.callParent(arguments);
   },
   
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [ 
       ' ', { xtype: 'textfield', fieldLabel: '冰柜编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchiceid', id: mep + 'searchiceid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '冰柜名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchicename', id: mep + 'searchicename', allowBlank: true }
       ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
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
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('冰柜编号', 'sampice.iceid', mep + 'iceid', 10, '97%', false, 1),
              tools.FormText('容量(L)', 'sampice.icecapa', mep + 'icecapa', 10, '97%', false, 4,'is10p2')
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('冰柜名称', 'sampice.icename', mep + 'icename', 20, '97%', false, 2),
              tools.FormDate('出厂日期', 'sampice.factorydate', mep + 'factorydate', 'Y-m-d', '97%', false,5,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('冰柜型号', 'sampice.icespec', mep + 'icespec', 20, '100%', false, 3),
              tools.FormText('存放地点', 'sampice.icestore', mep + 'icestore', 20, '100%', false, 6),
              {xtype:'hiddenfield',name:'sampice.deal.action',id: mep + 'datadeal'}
           ]}
         ]}
     ];
     me.disNews = [];
     me.disEdits = ['iceid'];
     me.enNews = ['iceid', 'icename', 'icespec', 'icecapa', 'factorydate', 'icestore'];
     me.enEdits = [ 'icename', 'icespec', 'icecapa', 'factorydate', 'icestore'];
   },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'sampice.iceid':tools.GetValueEncode(mep+'searchiceid'),
           'sampice.icename':tools.GetValueEncode(mep+'searchicename')
         })
       });
     }
   },
   
  OnInitData:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'iceid', item.iceid);
    tools.SetValue(mep + 'icename', item.icename);
    tools.SetValue(mep + 'icespec', item.icespec);
    tools.SetValue(mep + 'icecapa', item.icecapa);
    tools.SetValue(mep + 'factorydate', item.factorydate);
    tools.SetValue(mep + 'icestore', item.icestore);
    me.OnDetailRefresh();
    return true;
   },
   
  OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormDate('记录日期', 'sid.icedate', mep + 'icedate', 'Y-m-d', '97%', false,1,null),

            tools.FormText('记录人', 'sid.iceusername', mep + 'iceusername',10, '97%', false, 4),
            tools.FormDate('记录人时间', 'sid.iceuserdate', mep + 'iceuserdate', 'Y-m-d', '97%', false,5,nowdate)

         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('冰柜最低温度(℃)', 'sid.lowtemp', mep + 'lowtemp',4, '97%', false, 2,'',110),
            tools.FormText('冰柜最高温度(℃)', 'sid.hightemp', mep + 'hightemp',4, '97%', false, 3,'',110),
            {xtype:'hiddenfield',name:'sid.iceuser',id: mep + 'iceuser'}
        ]}
       ]},      
       tools.FormTextArea('备注', 'examdetail.iceremark', mep + 'iceremark', 200, '99%', true, 6,5)
    ];

     me.disDetailControls = [ 'iceuser', 'iceusername', 'iceuserdate'];
     me.enDetailControls = [ 'icedate', 'lowtemp', 'hightemp', 'iceremark'];
     
     //最低最高温度比较
     Ext.getCmp(mep + 'lowtemp').on('blur',me.OnCompareTemp,me);
     Ext.getCmp(mep + 'hightemp').on('blur',me.OnCompareTemp,me);
  },
  
  //最低最高温度比较
  OnCompareTemp:function(record){
    var me = this;
    var mep = me.tranPrefix;
    
    var lowtemp = Ext.getCmp(mep + 'lowtemp').getValue();
    var hightemp = Ext.getCmp(mep + 'hightemp').getValue();
    
    if(!isNaN(lowtemp) && !isNaN(hightemp) && !Ext.isEmpty(lowtemp) && !Ext.isEmpty(hightemp)){
      if(parseFloat(lowtemp) > parseFloat(hightemp)){
        tools.alert('最低温度不能大于最高温度');
        Ext.getCmp(record.id).reset();
      }
    }
    
  },
  
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'iceid', item.iceid);
    tools.SetValue(mep + 'icedate', item.icedate);
    tools.SetValue(mep + 'lowtemp', item.lowtemp);
    tools.SetValue(mep + 'hightemp', item.hightemp);
    tools.SetValue(mep + 'iceuser', item.iceuser);
    tools.SetValue(mep + 'iceusername', item.iceusername);
    tools.SetValue(mep + 'iceuserdate', item.iceuserdate);
    tools.SetValue(mep + 'iceremark', item.iceremark);

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
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'iceuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'iceusername', gpersist.UserInfo.user.username);
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        
        var icedate = Ext.getCmp(mep + 'icedate').getValue().getTime();
       
        for(var i=0;i<me.plDetailGrid.store.getCount();i++){
          if(me.plDetailGrid.store.getAt(i).get('icedate').getTime() == icedate){
            tools.alert('该记录日期已存在');
            return;
          }
        }
        
        me.OnBeforeListSave(record);
        
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      }
      else {
        me.OnBeforeListSave(me.detailRecord);
        
        me.plDetailGrid.getView().refresh();
      }
      
      me.winDetail.hide();
    };
  },
   
  OnBeforeListSave: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     record.data.iceid =  Ext.getCmp(mep + 'iceid').getValue();
     record.data.icedate = Ext.getCmp(mep + 'icedate').getValue();
     record.data.lowtemp = Ext.getCmp(mep + 'lowtemp').getValue();
     record.data.hightemp = Ext.getCmp(mep + 'hightemp').getValue();
     record.data.iceuser = Ext.getCmp(mep + 'iceuser').getValue();
     record.data.iceusername = Ext.getCmp(mep + 'iceusername').getValue();
     record.data.iceuserdate = Ext.getCmp(mep + 'iceuserdate').getValue();
     record.data.iceremark = Ext.getCmp(mep + 'iceremark').getValue();
   },
   
  OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'sid.iceid': tools.GetValueEncode(mep + 'iceid') 
        });
      });
      
      me.plDetailGrid.store.load();
     }
     
//     if (me.plDetailGrid && me.plDetailGrid.store) {
//        me.plDetailGrid.store.proxy.url = tools.GetUrl('SampGetListSampleIceDetail.do?sid.iceid=') + tools.GetValueEncode(mep + 'iceid');
//        me.plDetailGrid.store.load();
//     }
   },
   
  OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;

     var details = [];
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     me.saveParams = { details: Ext.encode(details) };
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'iceid').reset();
    Ext.getCmp(mep + 'iceid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.iceid) {
          tools.SetValue(mep + 'iceid', action.result.data.iceid);
        }
      }
    }
    me.OnDetailRefresh();
  }
});
