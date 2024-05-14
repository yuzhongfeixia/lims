Ext.define('alms.mantestedunit', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '受检单位管理',
      winWidth: 750,
      winHeight: 350,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_testedunit',
      storeUrl: 'BasSearchBusTestedUnit.do',
      saveUrl: 'BasSaveBusTestedUnit.do',
      hasGridSelect: true,
      expUrl: 'BasSearchBusTestedUnit.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'testedid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('企业编号', 'btu.testedid', mep + 'testedid', 10, '96%', false, 1),
          tools.FormText('企业法人', 'btu.enterlegal', mep + 'enterlegal', 10, '96%', false, 3),
          tools.FormCombo('企业规模', 'btu.enterscale', mep + 'enterscale', tools.ComboStore('EnterScale', gpersist.SELECT_MUST_VALUE), '96%', false, 5),
          tools.FormCombo('检测类型', 'btu.comtype', mep + 'comtype', tools.ComboStore('TestType', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
          tools.FormText('种植产品', 'btu.plantcorps', mep + 'plantcorps', 100, '96%', true, 9),
          tools.FormCombo('所在省', 'btu.provinceid', mep + 'provinceid', tools.ComboStore('Province', gpersist.SELECT_MUST_VALUE), '96%', false, 11),
          tools.FormCombo('所在市', 'btu.cityid', mep + 'cityid', tools.ComboStore('', gpersist.SELECT_MUST_VALUE), '96%', false, 14)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('企业名称', 'btu.testedname', mep + 'testedname', 20, '100%', false, 2),
          tools.FormText('联系电话', 'btu.entertele', mep + 'entertele', 20, '100%', false, 4,'isphone'),
          tools.FormText('传真', 'btu.enterfax', mep + 'enterfax', 20, '100%', true, 6),
          tools.FormCombo('企业性质', 'btu.entertype', mep + 'entertype', tools.ComboStore('EnterType', gpersist.SELECT_MUST_VALUE), '100%', false, 8),
          tools.FormText('种植面积', 'btu.plantarea', mep + 'plantarea', 20, '100%', true, 10),
          tools.FormText('邮政编码', 'btu.enterpost', mep + 'enterpost', 6, '100%', true, 12),
          tools.FormCombo('所在区/县', 'btu.areaid', mep + 'areaid', tools.ComboStore('', gpersist.SELECT_MUST_VALUE), '100%', false, 13),
          {xtype:'hiddenfield',name:'btu.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('通讯地址', 'btu.enteraddr', mep + 'enteraddr', 50, '100%', true, 16, 4)
    ];

    me.disNews = ['testedid'];
    me.disEdits = ['testedid'];
    me.enNews = [ 'testedname', 'enterlegal', 'enteraddr', 'enterpost', 'entertele', 'entertype', 'enterscale', 'comtype', 
    'provinceid', 'cityid', 'areaid','enterfax','plantcorps', 'plantarea'];
    me.enEdits = ['testedname', 'enterlegal', 'enteraddr', 'enterpost', 'entertele', 'entertype', 'enterscale', 'comtype', 
    'provinceid', 'cityid', 'areaid','enterfax','plantcorps', 'plantarea'];
    
    Ext.getCmp(mep + 'provinceid').on('change',me.OnCitySelect,me);
    Ext.getCmp(mep + 'cityid').on('change',me.OnAreaSelect,me);
    Ext.getCmp(mep + 'comtype').on('blur',me.OnComtypeSelect,me);
    
  },
  
//  OnComtypeSelect:function(){
//     var me = this, mep = me.tranPrefix;
//     var comtype = Ext.getCmp(mep + 'comtype').getValue();
//     if(comtype == '02' && me.dataDeal != gpersist.DATA_DEAL_SELECT){ // 02为农产品
//        tools.Enabled(mep + 'plantcorps');
//        tools.Enabled(mep + 'plantarea');
//     }else{
//        tools.Disabled(mep + 'plantcorps');
//        tools.Disabled(mep + 'plantarea');
//        Ext.getCmp(mep +'plantcorps').reset();
//        Ext.getCmp(mep +'plantarea').reset();
//     }
//  },
  
  OnCitySelect:function(){
    var me = this, mep = me.tranPrefix;
    var id = Ext.getCmp(mep+'provinceid').getValue();
    me.OnGetCity(id);
  },
  
  OnAreaSelect:function(){
    var me = this, mep = me.tranPrefix;
    var id = Ext.getCmp(mep+'cityid').getValue();
    me.OnGetArea(id);
  },
  
  OnGetCity: function (id) {
    var me = this, mep = me.tranPrefix;
    
    var cityid = Ext.getCmp(mep + 'cityid');
    
    if (cityid) {
      if (cityid == gpersist.SELECT_MUST_VALUE) {
        cityid.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetCityCombo(gpersist.SELECT_MUST_VALUE, id, mep + 'cityid');
      }
    }
  },
  
  OnGetArea: function (id) {
    var me = this, mep = me.tranPrefix;
    
    var areaid = Ext.getCmp(mep + 'areaid');
    
    if (areaid) {
      if (areaid == gpersist.SELECT_MUST_VALUE) {
        areaid.bindStore(tools.GetNullStore());
      }
      else {
        alms.SetAreaCombo(gpersist.SELECT_MUST_VALUE, id, mep + 'areaid');
      }
    }
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'enterscale', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'entertype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'comtype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'provinceid', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'areaid', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'cityid', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '企业名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
//      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
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
          'btu.testedname': tools.GetEncode(tools.GetValue(mep + 'searchtestedname'))
        });
      });
    };
  },
   
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    if(record && !Ext.isEmpty(record.testedid)){
      var item = tools.JsonGet(tools.GetUrl('BasGetBusTestedUnit.do?btu.testedid=') + record.testedid);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'enterlegal', item.enterlegal);
      tools.SetValue(mep + 'enteraddr', item.enteraddr);
      tools.SetValue(mep + 'enterpost', item.enterpost);
      tools.SetValue(mep + 'entertele', item.entertele);
      tools.SetValue(mep + 'enterfax', item.enterfax);
      tools.SetValue(mep + 'entertype', item.entertype);
      tools.SetValue(mep + 'enterscale', item.enterscale);
      tools.SetValue(mep + 'comtype', item.comtype);
      tools.SetValue(mep + 'plantcorps', item.plantcorps);
      tools.SetValue(mep + 'plantarea', item.plantarea);
      tools.SetValue(mep + 'provinceid', item.provinceid);
      me.OnCitySelect();
      tools.SetValue(mep + 'cityid', item.cityid);
      me.OnAreaSelect();
      tools.SetValue(mep + 'areaid', item.areaid);
      me.OnComtypeSelect();
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var entertype = Ext.getCmp(mep+'entertype').getValue();
    var enterscale = Ext.getCmp(mep+'enterscale').getValue();
    var comtype = Ext.getCmp(mep+'comtype').getValue();
    var provinceid = Ext.getCmp(mep+'provinceid').getValue();
    var cityid = Ext.getCmp(mep+'cityid').getValue();
    var areaid = Ext.getCmp(mep+'areaid').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
//      Ext.each(me.plEdit.getForm().items[0],function(item,index){ 
//        Ext.each(item.items,function(it,index){
//            Ext.each(it.items,function(i,index){
//               if(i.xtype == 'combobox' && i.value == gpersist.SELECT_MUST_VALUE ){
//                   tools.alert('请选择'+i.fieldLabel);
//                    return;
//               }
//            })
//        })
//      });
      if(entertype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择单位性质！');
        return;
      }
      if(enterscale == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择单位规模！');
        return;
      }
      if(comtype == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择受检类别！');
        return;
      }
      if(provinceid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择所在省！');
        return;
      }
      if(cityid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择所在市！');
        return;
      }
      if(areaid == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择所在区/县！');
        return;
      }
    }
    
    return true;
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'testedid').reset();
    Ext.getCmp(mep + 'testedid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.testedid) {
          tools.SetValue(mep + 'testedid', action.result.data.testedid);
        }
      }
    }
  }
   
});