Ext.define('alms.editenvgetdetail', {
  extend: 'gpersist.base.editwin',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      title: '样品明细',
      winWidth: 900,
      winHeight: 500
    });

    me.callParent(arguments);
  },

  OnBeforeCreateEdit: function () {
    var me = this, mep = me.mep;

    var nowdate = new Date();
    
    var paramcols = [];
    var paramfields = [];
    
    var paramitem = [
      ' ', { id: mep + 'btnAddParam', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNewParam, scope: me },
      ' ', { id: mep + 'btnDeleteParam', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDeleteParam, scope: me },
      ' ', { id: mep + 'btnAddParamBatch', text: '批量新增', iconCls: 'icon-add', handler: me.OnListNewParamBatch, scope: me }
    ];
    
    tools.GetGridColumnsByUrl('SysSqlColumn.do?sqlid=p_get_paramtestjudge', paramcols, paramfields, mep + '_param_');

    var paramStore = tools.CreateGridStore(tools.GetUrl(), paramfields);
    
    me.plParam = Ext.create('Ext.grid.Panel', {
      frame: false,
      border: true,
      title: '检测参数设置',
      margin: '0 0 0 0',
      padding: '0 0 0 0',
      loadMask: true,
      columnLines: true,
      multiSelect: true,
      height: 260,
      enableColumnMove: false, suspendLayout:true,
      viewConfig: {
        autoFill: true,
        stripeRows: true
      },
      columns: paramcols,
      store: paramStore,
      tbar: paramitem,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }),
      listeners : {
        'itemdblclick' : { fn : me.OnListParam, scope : me }
      } 
    });
    
    me.customButtons = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me }
    ];
    
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
      editable:true,
      searchTools:sampleitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    sample.on('griditemclick', me.OnSampleSelect, me);
    
    me.editControls = [
    { xtype: 'container', anchor: '100%', layout: 'column', items: [
      { xtype: 'container', columnWidth: .6, layout: 'anchor', items: [
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            sample,
            tools.FormText('GPS定位', 'gpsnum', 'gpsnum', 100, '96%', true,13)
          ]},
          { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormText('采样量', 'samplecount', mep + 'samplecount', 20, '96%', true,13)
          ]}
        ]},
        tools.FormTextArea('抽样地点', 'getaddr', mep + 'getaddr', 200, '98%', true, 29, 8),
        {xtype:'hiddenfield',name:'sampleid',id: mep + 'sampleid'}
      ]},
      
      { xtype: 'container', columnWidth: .4, layout: 'anchor', items: [
        { xtype: 'fieldset', id:mep+'gtstand', hidden:false, collapsible: true, title: '规格型号', anchor: '100%', height:170, items: [
        ]}
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
  
    me.disNews = [];
    me.disEdits = [];
    me.enNews = ['samplename','sampleid','samplecount','getaddr','gpsnum','map']; 
    me.enEdits = ['samplename','sampleid','samplecount','getaddr','gpsnum','map'];
    
  },
  
  OnSampleSearch:function(){
    var me = this;
    var mep = me.mep;
    
    var sample = Ext.getCmp(mep+'samplename');
    
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
  
  paramdetail: null,
  parambatchdetail: null,
  parameterrecord: null,
  
  OnListNewParam: function () {
    var me = this, mep = me.mep;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'selectbustest', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.paramdetail.on('formlast', me.OnParameterSave, me);
      me.paramdetail.OnFormLoad();
    }
    else
      me.paramdetail.OnFormShow();
    
    me.paramdetail.OnInitData(tools.GetValue(mep + 'sampleid'));    
    me.paramdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    
    me.parameterrecord = null;    
  },
  
  OnParameterBatchSave: function (e, datas) {
    var me = this, mep = me.mep;
    
    if (datas) {
      for (var i = 0; i < datas.length; i++) {
        var data = datas[i];
        var record = me.plParam.store.model.create({});
        record.data.parameterid = data.parameterid;
        record.data.parametername = data.parametername;
        record.data.teststandard = data.teststandard;
        record.data.teststandardname = data.teststandardname;
        record.data.judgestandard = data.judgestandard;
        record.data.judgestandardname = data.judgestandardname;
        me.plParam.store.insert(me.plParam.store.getCount(), record);
      }
    }
  },
  
  OnListNewParamBatch: function () {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.parambatchdetail) {
      me.parambatchdetail = tools.GetPopupWindow('alms', 'selectbatchparam', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbtb', dataDeal: me.dataDeal})
      
      me.parambatchdetail.on('formlast', me.OnParameterBatchSave, me);
      me.parambatchdetail.OnFormLoad();
    }
    else
      me.parambatchdetail.OnFormShow();

    me.parambatchdetail.OnInitData(tools.GetValue(mep + 'sampleid'));    
    me.parambatchdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);   
  },
  
  OnListParam: function (e, record, item, index) {
    var me = this, mep = me.mep;
    
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return;
    
    if (!me.paramdetail) {
      me.paramdetail = tools.GetPopupWindow('alms', 'selectbustest', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 'sbt', dataDeal: me.dataDeal})
      
      me.paramdetail.on('formlast', me.OnParameterSave, me);
      me.paramdetail.OnFormLoad();
    }
    else
      me.paramdetail.OnFormShow();
    
    me.paramdetail.OnSetData(record,tools.GetValue(mep + 'sampleid'));    
    me.paramdetail.OnAuthEditForm(me.dataDeal == gpersist.DATA_DEAL_NEW ? gpersist.DATA_DEAL_EDIT : me.dataDeal);
    me.parameterrecord = record;
  },
  
  OnParameterSave: function (e, data) {
    var me = this, mep = me.mep;
    var record = me.parameterrecord;
    
    if (record == null) record = me.plParam.store.model.create({});
      
    record.data.parameterid = data.parameterid;
    record.data.parametername = data.parametername;
    record.data.teststandard = data.teststandard;
    record.data.teststandardname = data.teststandardname;
    record.data.judgestandard = data.judgestandard;
    record.data.judgestandardname = data.judgestandardname;
    
    if (me.parameterrecord)
      me.plParam.getView().refresh();
    else
      me.plParam.store.insert(me.plParam.store.getCount(), record);
  },
  
  OnListDeleteParam : function() {
    var me = this;
    
    var j = me.plParam.selModel.selected.items.length;
    for ( var i = 0; i < j; i++) {
      me.plParam.store.remove(me.plParam.selModel.selected.items[0]);
    }
    
    me.plParam.getView().refresh();
  },
  
  OnSave: function () {
    var me = this, mep = me.mep;

    if (tools.InvalidForm(me.plEdit.getForm())) { 
      var parameterids = '';
      var parameternames = '';
      var standtype1 = '';
      var standtype2 = '';
      var standtype3 = '';
      var standtype4 = '';
      var standtype5 = '';
      var samplestand = '';
      
      for (i = 0; i < me.plParam.store.getCount(); i++) {
        var record = me.plParam.store.getAt(i).data;
        
        if (!Ext.isEmpty(parameterids)){
          parameterids += ',';
        }
              
        if (!Ext.isEmpty(parameternames)){
          parameternames += ',';
        }
          
        parameterids += record.parameterid + ':' + record.parametername + 
          ':' + record.teststandard + ':' + record.teststandardname + 
          ':' + record.judgestandard + ':' + record.judgestandardname;
        parameternames += record.parametername;
      }
      
      var stand = Ext.getCmp(mep + 'gtstand');
      for(var i = 0; i < stand.items.getCount(); i++){
        if(i < stand.items.getCount()){
          if(i == 0){
            if(stand.items.items[i].getValue()!='-2'){
              standtype1 = stand.items.items[i].getValue();
              samplestand = stand.items.items[i].getDisplayValue();
            }
          }
          if(i == 1){
            if(stand.items.items[i].getValue()!='-2'){
              standtype2 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }
          }
          if(i == 2){
            if(stand.items.items[i].getValue()!='-2'){
              standtype3 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }
          }
          if(i == 3){
            if(stand.items.items[i].getValue()!='-2'){
              standtype4 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }
          }
          if(i == 4){
            if(stand.items.items[i].getValue()!='-2'){
              standtype5 = stand.items.items[i].getValue();
              if(samplestand == ''){
                samplestand = stand.items.items[i].getDisplayValue();
              }else{
                samplestand  = samplestand + ',' + stand.items.items[i].getDisplayValue();
              }
            }
          }
        }
      }
      
      if(tools.GetValue(mep + 'sampleid') == ''){
        tools.alert('请选择需要的样品...');
        Ext.getCmp(mep+'samplename').reset();
      }else{
        var data = { 
          sampleid: tools.GetValue(mep + 'sampleid'),
          samplename: tools.GetValue(mep + 'samplename'),
          samplecount:tools.GetValue(mep + 'samplecount'),
          gpsnum:tools.GetValue('gpsnum'),
          getaddr: tools.GetValue(mep + 'getaddr'),
          samplestand: samplestand,
          standtype1:standtype1,
          standtype2:standtype2,
          standtype3:standtype3,
          standtype4:standtype4,
          standtype5:standtype5,
          parameternames: parameternames,
          parameterids: parameterids
        };
        me.fireEvent('formlast', me, data); 
        me.winEdit.hide();
      }
    }
  },
  
  OnSampleSelect:function(render,sample){
    var me = this, mep = me.mep;
    tools.SetValue(mep + 'sampleid',sample.sampleid);
    tools.SetValue(mep + 'samplename',sample.samplename);
    if(sample.samplename.indexOf("水") != -1){
    	tools.SetValue(mep + 'samplecount','3-5L');
    }else if(sample.samplename.indexOf("土壤") != -1){
    	tools.SetValue(mep + 'samplecount','1KG');
    }
//    me.plParam.store.removeAll();
//    me.plParam.getView().refresh();
    me.OnGetStand();
  },
  
  OnGetStand:function(){
    var me = this, mep = me.mep;
    var sampleid = tools.GetValue(mep + 'sampleid');
    var stand = Ext.getCmp(mep + 'gtstand');
    stand.removeAll();
//    if(Ext.isEmpty(sampleid) || sampleid == gpersist.SELECT.MUST_VALUE)
//      return;
    
    var stands  = tools.JsonGet('BasGetSampleStandCount.do?bss.sampleid=' + sampleid);
    var idx = 130;
    for(var i = 0; i < stands.samplecount; i++){
      var standlabel = '';
      var standid = '';
      var typedata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
      var items = tools.JsonGet('BasGetSampleStandByLevel.do?bss.sampleid=' + sampleid + '&bss.standlevel=' + (i+1));
      if (items && items.data){
        standlabel = items.data[0].standlabel;
        standid = items.data[0].standid;
        Ext.each(items.data, function (item, index) { 
          typedata.push({ id: item.standtype, name: item.standtypename });
        });
      }
      
      var item = tools.FormCombo(standlabel,standid, mep + standid, tools.GetNullStore(), '100%', false, 302);
      item.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: typedata}));
      item.setValue(gpersist.SELECT_MUST_VALUE); 
//      item.on('select', me.OnStandSelect, me); 
      stand.add(item);  
    }
  },
  
  
  OnSetData: function (record,tranid) {
    var me = this, mep = me.mep;
    var item = record.data;
    if (me.dataDeal == gpersist.DATA_DEAL_SELECT){
      tools.BtnsDisable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }else{
      tools.BtnsEnable(['btnAddParam','btnDeleteParam','btnAddParamBatch'], mep)
    }
    tools.SetValue(mep + 'sampleid',item.sampleid);
    tools.SetValue(mep + 'samplename',item.samplename);
    tools.SetValue(mep + 'samplecount',item.samplecount);
    tools.SetValue('gpsnum',item.gpsnum);
    tools.SetValue(mep + 'getaddr',item.getaddr);
    
    me.OnGetStand();
    
    tools.SetValue(mep + 'standtype1',item.standtype1);
    tools.SetValue(mep + 'standtype2',item.standtype2);
    tools.SetValue(mep + 'standtype3',item.standtype3);
    tools.SetValue(mep + 'standtype4',item.standtype4);
    tools.SetValue(mep + 'standtype5',item.standtype5);
    
    me.plParam.store.removeAll();
    
    if(!Ext.isEmpty(item.parameterids)){
      var items = item.parameterids.split(',');
      for (var i = 0; i < items.length; i++) {
        var params = items[i].split(':');
        var precord = me.plParam.store.model.create({});
        precord.data.parameterid = params[0];
        precord.data.parametername = params[1];
        precord.data.teststandard = params[2];
        precord.data.teststandardname = params[3];
        precord.data.judgestandard = params[4];
        precord.data.judgestandardname = params[5];
        me.plParam.store.insert(me.plParam.store.getCount(), precord);
      }
    } else {
      var param = tools.JsonGet('LabGetListBusSampleParam.do?bsp.tranid='+tranid + '&bsp.sampleid='+item.sampleid).data;
      if (param.length > 0) {
        for (var i = 0; i < param.length; i++) {
          var precord = me.plParam.store.model.create({});
          precord.data.parameterid = param[i].parameterid;
          precord.data.parametername = param[i].parametername;
          precord.data.judgestandard = param[i].judgestandard;
          precord.data.judgestandardname = param[i].judgestandardname;
          precord.data.teststandard = param[i].teststandard;
          precord.data.teststandardname = param[i].teststandardname;
          me.plParam.store.insert(me.plParam.store.getCount(), precord);
        }     
      }
    }
    me.plParam.getView().refresh();
  },
  
  OnListSelectMap:function(record){
    var me = this;
    me.addMapLb(record);
  },
  
   OnLoadMap: function() {
    var me = this;
    var mep = me.mep;
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
      var mep = me.mep;
      
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
          tools.SetValue('gpsnum',lng+','+lat);
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
        var samp = record.data.gpsnum.split(",");
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