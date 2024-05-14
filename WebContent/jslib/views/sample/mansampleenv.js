Ext.define('alms.mansampleenv',{
  extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'环境样品采样单',
          winWidth:750,
          winHeight:450,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_sampleenv',
          storeUrl:'SampSearchSampleEnv.do',
          saveUrl:'SampSaveSampleEnv.do',
          expUrl:'SampSearchSampleEnv.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: false,
          detailTitle: '采样单明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_sampleenvdetail',
          urlDetail: 'SampGetListSampleEnvDetail.do',
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
       ' ', { xtype: 'textfield', fieldLabel: '受检企业', labelWidth: 70, width: 200, maxLength: 40, name: 'searchentername', id: mep + 'searchentername', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '基地名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchenterbase', id: mep + 'searchenterbase', allowBlank: true }
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
     
     var entername = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '受检企业', name: 'sampenv.entername', id: mep + 'entername', winTitle: '选择受检企业',
      maxLength: 200, maxLengthText: '受检企业不能超过200字！', selectOnFocus: false, labelWidth: 80,
      blankText: '受检企业为空！', allowBlank: false, anchor: '97%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_testedunit',
      storeUrl: 'BasSearchBusTestedUnit.do',
      editable:false,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     entername.on('griditemclick', me.OnItemSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'sampenv.tranid', mep + 'tranid', 20, '97%', false, 1),
              tools.FormCombo('企业性质', 'sampenv.entertype', mep + 'entertype', tools.ComboStore('EnterType', gpersist.SELECT_MUST_VALUE), '97%', false, 4),
              tools.FormText('企业地址', 'sampenv.enteraddress', mep + 'enteraddress', 30, '97%', false, 7)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              entername,
              tools.FormText('邮编', 'sampenv.enterpost', mep + 'enterpost', 6, '97%', true,5),
              tools.FormCombo('检验类别', 'sampenv.testtype', mep + 'testtype', tools.ComboStore('TestType', gpersist.SELECT_MUST_VALUE), '97%', false, 8)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('企业法人', 'sampenv.enterlegal', mep + 'enterlegal', 10, '100%', false, 3),
              tools.FormText('联系电话', 'sampenv.entertele', mep + 'entertele', 20, '100%', false, 6),
              {xtype:'hiddenfield',name:'sampenv.deal.action',id: mep + 'datadeal'},
              {xtype:'hiddenfield',name:'sampenv.sampleuser',id: mep + 'sampleuser'}
           ]}
         ]},
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('基地名称', 'sampenv.enterbase', mep + 'enterbase', 20, '97%', false, 9),
              tools.FormCombo('采样深度', 'sampenv.sampledeep', mep + 'sampledeep', tools.ComboStore('SampleDeep', gpersist.SELECT_MUST_VALUE), '97%', false, 12),
              tools.FormDate('记录日期', 'sampenv.sampleuserdate', mep + 'sampleuserdate', 'Y-m-d', '97%', false,15,nowdate)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormDate('采样时间', 'sampenv.sampledate', mep + 'sampledate', 'Y-m-d', '97%', false,10,nowdate),
              tools.FormText('基地面积', 'sampenv.basearea', mep + 'basearea', 20, '97%', true, 13)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('种植品种', 'sampenv.sampleplant', mep + 'sampleplant', 20, '100%', true, 11),
              tools.FormText('采样员', 'sampenv.sampleusername', mep + 'sampleusername', 14, '100%', false, 14)
           ]}
         ]},
        tools.FormTextArea('备注', 'sampenv.remark', mep + 'remark', 200, '100%', true, 16,4)
     ];
     me.disNews = ['tranid', 'enterlegal', 'entertype', 'enterpost', 'entertele', 'enteraddress', 'sampleuser', 'sampleusername', 'sampleuserdate'];
     me.disEdits = ['tranid', 'enterlegal', 'entertype', 'enterpost', 'entertele', 'enteraddress', 'sampleuser', 'sampleusername', 'sampleuserdate'];
     me.enNews = [ 'entername', 'testtype', 'enterbase', 'sampledate', 'sampledeep', 'sampleplant', 'basearea', 'remark'];
     me.enEdits = [  'entername', 'testtype', 'enterbase', 'sampledate', 'sampledeep', 'sampleplant', 'basearea', 'remark'];
   },
   
  OnItemSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item){
        tools.SetValue(mep+"entername",item.testedname);
        tools.SetValue(mep+"enterlegal",item.enterlegal);
        tools.SetValue(mep+"entertype",item.entertype);
        tools.SetValue(mep+"enterpost",item.enterpost);
        tools.SetValue(mep+"entertele",item.entertele);
        tools.SetValue(mep+"enteraddress",item.enteraddr);
     }
  },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'sampenv.entername':tools.GetValueEncode(mep+'searchentername'),
           'sampenv.enterbase':tools.GetValueEncode(mep+'searchenterbase')
         })
       });
     }
   },
   
  OnInitData:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'sampleuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'sampleusername', gpersist.UserInfo.user.username );
     tools.SetValue(mep + 'testtype', gpersist.SELECT_MUST_VALUE );
     tools.SetValue(mep + 'sampledeep', gpersist.SELECT_MUST_VALUE );
   },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var testtype = Ext.getCmp(mep+'testtype').getValue();
    var sampledeep = Ext.getCmp(mep+'sampledeep').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(testtype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择检验类别！');
        return;
      }
      if(sampledeep == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择采样深度！');
        return;
      }
    }
    return true;
  },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'entername', item.entername);
    tools.SetValue(mep + 'enterlegal', item.enterlegal);
    tools.SetValue(mep + 'entertype', item.entertype);
    tools.SetValue(mep + 'enterpost', item.enterpost);
    tools.SetValue(mep + 'entertele', item.entertele);
    tools.SetValue(mep + 'enteraddress', item.enteraddress);
    tools.SetValue(mep + 'testtype', item.testtype);
    tools.SetValue(mep + 'enterbase', item.enterbase);
    tools.SetValue(mep + 'sampledate', item.sampledate);
    tools.SetValue(mep + 'sampledeep', item.sampledeep);
    tools.SetValue(mep + 'sampleplant', item.sampleplant);
    tools.SetValue(mep + 'basearea', item.basearea);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'sampleuser', item.sampleuser);
    tools.SetValue(mep + 'sampleusername', item.sampleusername);
    tools.SetValue(mep + 'sampleuserdate', item.sampleuserdate);
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
            tools.FormText('采样地点', 'sed.sampleaddress', mep + 'sampleaddress',20, '97%', false, 21),
            tools.FormText('采样量', 'sed.samplequantity', mep + 'samplequantity',10, '97%', false, 22)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('GPS定位', 'sed.sampleposi', 'sampleposi',20, '100%', true, 23),
            tools.FormText('样点示意图', 'sed.samplepic', mep + 'samplepic',100, '100%', true, 24)
        ]}
       ]},
       {  
          xtype : 'panel',
          width:'100%',
          height:250,
          id:mep +'map',
          html: ''
       }
    ];

     me.disDetailControls = ['sampleposi'];
     me.enDetailControls = ['sampleaddress', 'samplequantity',  'samplepic','map'];
     Ext.getCmp('sampleposi').setReadOnly(true);
  },
  
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'sampleaddress', item.sampleaddress);
    tools.SetValue(mep + 'samplequantity', item.samplequantity);
    tools.SetValue('sampleposi', item.sampleposi);
    tools.SetValue(mep + 'samplepic', item.samplepic);

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
      me.OnLoadMap();
    }
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'iceuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'iceusername', gpersist.UserInfo.user.username);
  },
   
  OnBeforeListSave: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     record.data.sampleaddress = Ext.getCmp(mep + 'sampleaddress').getValue();
     record.data.samplequantity = Ext.getCmp(mep + 'samplequantity').getValue();
     record.data.sampleposi = Ext.getCmp( 'sampleposi').getValue();
     record.data.samplepic = Ext.getCmp(mep + 'samplepic').getValue();
   },
   
  OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('SampGetListSampleEnvDetail.do?sed.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
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
    me.OnDetailRefresh();
  },
  
   OnListSelect: function(e, record, item, index) {
    var me = this;
    
    me.detailRecord = record;
    
    me.OnCreateDetailWin();
   
    if(me.winDetail && record) {      
      me.winDetail.show();
      
      me.detailEditType = 2;
      me.OnLoadDetailData(record.data);
      me.OnLoadMap();
      me.OnListSelectMap(record);
      me.OnAuthDetailEditForm(true);
    }
  },
  
  OnListSelectMap:function(record){
    var me = this;
    me.addMapLb(record);
  },
  
   OnLoadMap: function() {
    var me = this;
    var mep = me.tranPrefix;
    var  htmldiv = '<div>' +
        '<input type="text" id="input"> ' +
        '<input type="button" id="ina" value="搜索地图"> '+
        '</div>';
    htmldiv += '<div style="width: 100%; height: 100%; ; border: #ccc solid 1px;" id="dituContent"></div>';
    
    htmldiv +='';
    
    Ext.fly(Ext.getCmp(mep+'map').getEl()).update(htmldiv);
     
    me.initMap();
    
     Ext.get('ina').on("click",function(){  
         me.searchMap();
     })  
   
    return true;
   },
  
   //搜索
   searchMap:function(){
    var me = this;
      var area =Ext.get('input').getValue();
      var local = new BMap.LocalSearch(map, {renderOptions:{map: map,autoViewport: true},pageCapacity: 1});
      map.setZoom(18); 
      local.search(area);
    },
  
    showInfo:function(e){
      var me = this;
      map.clearOverlays();
      var point = new BMap.Point(e.point.lng,e.point.lat);
      
      var lng = e.point.lng;
      var lat = e.point.lat;
      marker = new BMap.Marker(point);  // 创建标注
      marker.enableDragging();  
      map.addOverlay(marker);
      //展示提示框
      var sContent =
        "<div class=''><p style='margin:0;line-height:1.5;font-size:13px;text-indent:2em'>是否使用此位置的定位？</p>" + 
        "<a class='' href='javascript:void(0)' style='margin-left:25px;margin-top:10px;margin-bottom:5px;' onclick='ensureLocation("+lng+","+lat+")'>确定使用</a><div>";
      infoWindow = new BMap.InfoWindow(sContent); 
      marker.openInfoWindow(infoWindow);
              
      ensureLocation = function (lng,lat){
          stopMaker();
          tools.SetValue('sampleposi',lng+','+lat);
          tools.alert("添加成功");
      }
      stopMaker = function (){
        marker.disableDragging(); 
        infoWindow.hide();
      }
    },
    
   initMap:function (){
        var me = this;
        me.createMap();//创建地图
        me.setMapEvent();//设置地图事件
        me.addMapControl();//向地图添加控件
        me.addMapLb();
    },
    
    //创建地图函数：
    createMap:function (){
      var me = this;
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(116.404,39.915);//定义一个中心点坐标
        map.centerAndZoom(point,12);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
        map.addEventListener("click", me.showInfo);
    },
    
    addMapLb:function (record){
      if(record){
        var samp = record.data.sampleposi.split(",");
        var lng = samp[0];
        var lat = samp[1];
        var point = new BMap.Point(lng,lat);
        var marker = new BMap.Marker(point);  // 创建标注
        map.addOverlay(marker);
        map.centerAndZoom(point, 18);
      }
    },
    
    //地图事件设置函数：
    setMapEvent:function (){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    },
    
    //地图控件添加函数：
    addMapControl:function (){
       //向地图中添加缩放控件
       var ctrl_nav = new BMap.NavigationControl({
         anchor : BMAP_ANCHOR_TOP_LEFT,
         type : BMAP_NAVIGATION_CONTROL_LARGE
       });
       map.addControl(ctrl_nav);
       //向地图中添加缩略图控件
       var ctrl_ove = new BMap.OverviewMapControl({
         anchor : BMAP_ANCHOR_BOTTOM_RIGHT,
         isOpen : 1
       });
       map.addControl(ctrl_ove);
       //向地图中添加比例尺控件
       var ctrl_sca = new BMap.ScaleControl({
         anchor : BMAP_ANCHOR_BOTTOM_LEFT
       });
       map.addControl(ctrl_sca);                  
    }
  
});