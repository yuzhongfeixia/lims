Ext.define('alms.intinterface', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '界面定义管理',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_intinterface',
      storeUrl: 'FormSearchIntInterface.do',
      saveUrl: 'FormSaveIntInterface.do',
      expUrl: 'FormSearchIntInterface.do',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_intfield',
      urlDetail: 'FormGetListIntField.do',
      hasDetail: true,
      hasDetailEdit: true,
      detailTitle: '界面定义字段明细',
      hasPage: true,
      hasExit: true,
      hasDetailGrid:true,
      hasDetailEditTool: true,
      idPrevNext: 'intid',
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
      ' ', { xtype: 'textfield', fieldLabel: '界面名称', labelWidth: 60, width: 200, maxLength: 40, name: 'searchinterface', id: mep + 'searchinterface', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy, scope: me },
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
    
//    var url = '';
//    url = 'BasGetListBasSampleParameterDetail.do'
//    me.samParStore = new Ext.data.JsonStore({
//     proxy:{
//       type:'ajax',
//       url:url,
//       reader:{
//         type:'json',
//         root:'data'
//       }
//     },
//     fields:[{name:'id',mapping:'parameterid'},{name:'name',mapping:'parametername'}],
//     autoLoad:true
//    });
    
    me.editControls = [      
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('界面编号', 'iif.intid', mep + 'intid', 20, '96%', true,null,null,90),
          tools.FormCombo('检测参数', 'iif.parameterid', mep + 'parameterid', tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90),
          tools.FormText('试件数量', 'iif.specserial', mep + 'specserial', 20, '96%', true,null,null,90)
          
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
//          tools.FormCombo('样品名称', 'iif.sampleid', mep + 'sampleid', tools.ComboStore('BasSample', gpersist.SELECT_MUST_VALUE), '100%', false, 3,90),
          tools.FormCombo('系统类型', 'iif.inttype', mep + 'inttype', tools.ComboStore('IntType', gpersist.SELECT_MUST_VALUE), '100%', false, 3,90),
          tools.FormText('方法值', 'iif.methodcode', mep + 'methodcode', 20, '100%', true,null,null,90),
          tools.FormText('排序序号', 'iif.intorder', mep + 'intorder', 20, '100%', true,null,null,90),
//          tools.FormCheck('多点检测', 'iif.ismutil', mep + 'ismutil', false, 18),
          {xtype:'hiddenfield',name:'iif.intcode',id: mep + 'intcode'},
          {xtype:'hiddenfield',name:'iif.intname',id: mep + 'intname'},
          {xtype:'hiddenfield',name:'iif.deal.action',id: mep + 'datadeal'}
        ]}
      ]}
    ];
    me.disNews = ['intid'];
    me.disEdits = ['intid'];
    me.enNews = ['intname', 'intcode', 'specserial', 'inttype', 'methodcode', 'ismutil', 'intorder','parameterid'];
    me.enEdits = ['intname', 'intcode', 'specserial', 'inttype', 'methodcode', 'ismutil', 'intorder','parameterid'];
    
//    Ext.getCmp(mep+'sampleid').on('select',function(render, item){
//      var sampleid = item[0].data.id;
//      me.samParStore.load({params:{sampleid:sampleid}});
//       
//      var parameterid = Ext.getCmp(mep + 'parameterid');        
//      if (parameterid) {
//        parameterid.reset();
//      }
//    });
    
    Ext.getCmp(mep+'parameterid').on('select',function(render, item){
      tools.SetValue(mep+'intname',Ext.getCmp(mep+'parameterid').getDisplayValue());
    });
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.winWidth=900,
    me.winHeight=240,
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('段落', 'fieldtable', mep + 'fieldtable', 20, '96%', true),
          tools.FormText('所在列数', 'fieldcolumn', mep + 'fieldcolumn', 20, '96%', true),
          tools.FormText('标签长度', 'labelwidth', mep + 'labelwidth', 20, '96%', true),
          tools.FormText('试样序号', 'fieldserial', mep + 'fieldserial', 20, '96%', true),
          tools.FormText('默认值', 'defaultvalue', mep + 'defaultvalue', 1000, '96%', true)
        ]},
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('所在行数', 'fieldrow', mep + 'fieldrow', 20, '96%', true),
          tools.FormText('列宽比例', 'fieldwidth', mep + 'fieldwidth', 20, '96%', true),
          tools.FormCombo('字段类型', 'fieldtype', mep + 'fieldtype', tools.ComboStore('FieldType', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
          tools.FormText('字段长度', 'fieldmax', mep + 'fieldmax', 20, '96%', true),
          tools.FormCheck('是否可为空', 'isnull', mep + 'isnull', false, 18)
        ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormText('本行列数', 'fieldrowcount', mep + 'fieldrowcount', 20, '100%', true),
          tools.FormText('字段标签', 'fieldlabel', mep + 'fieldlabel', 100, '100%', true),
          tools.FormText('字段代码', 'fieldcode', mep + 'fieldcode', 20, '100%', true),
          tools.FormText('字段行数', 'fieldlines', mep + 'fieldlines', 20, '100%', true)
        ]}
      ]}
    ];
    
    me.disDetailControls = [];
    me.enDetailControls = ['fieldtable', 'fieldrow', 'fieldrowcount', 'fieldcolumn', 
                           'fieldwidth', 'fieldlabel', 'labelwidth', 'fieldtype', 
                           'fieldcode', 'fieldserial', 'isnull', 'fieldmax', 
                           'fieldlines', 'defaultvalue']; 
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
  
  OnListNew : function(){
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnCreateDetailWin();
    if(me.winDetail){
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnAuthDetailEditForm(false);
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
  
  OnBeforeListSave:function(record){
    var me = this;
    var mep = me.tranPrefix;
    record.data.fieldtable = tools.GetValue(mep+'fieldtable');
    record.data.fieldrow = tools.GetValue(mep+'fieldrow');
    record.data.fieldrowcount = tools.GetValue(mep+'fieldrowcount');
    record.data.fieldcolumn = tools.GetValue(mep+'fieldcolumn');
    record.data.fieldwidth = tools.GetValue(mep+'fieldwidth');
    record.data.fieldlabel = tools.GetValue(mep+'fieldlabel');
    record.data.labelwidth = tools.GetValue(mep+'labelwidth');
    record.data.fieldtype = tools.GetValue(mep+'fieldtype');
    record.data.fieldtypename = Ext.getCmp(mep+'fieldtype').getDisplayValue();
    record.data.fieldcode = tools.GetValue(mep+'fieldcode');
    record.data.fieldserial = tools.GetValue(mep+'fieldserial');
    record.data.isnull = tools.GetValue(mep+'isnull');
    record.data.fieldmax = tools.GetValue(mep+'fieldmax');
    record.data.fieldlines = tools.GetValue(mep+'fieldlines');
    record.data.defaultvalue = tools.GetValue(mep+'defaultvalue');
  },
  
  OnLoadDetailData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.SetValue(mep + 'intid', record.intid);
    tools.SetValue(mep + 'fieldtable', record.fieldtable);
    tools.SetValue(mep + 'fieldrow', record.fieldrow);
    tools.SetValue(mep + 'fieldrowcount', record.fieldrowcount);
    tools.SetValue(mep + 'fieldcolumn', record.fieldcolumn);
    tools.SetValue(mep + 'fieldwidth', record.fieldwidth);
    tools.SetValue(mep + 'fieldlabel', record.fieldlabel);
    tools.SetValue(mep + 'labelwidth', record.labelwidth);
    tools.SetValue(mep + 'fieldtype', record.fieldtype);
    tools.SetValue(mep + 'fieldcode', record.fieldcode);
    tools.SetValue(mep + 'fieldserial', record.fieldserial);
    tools.SetValue(mep + 'isnull', record.isnull);
    tools.SetValue(mep + 'fieldmax', record.fieldmax);
    tools.SetValue(mep + 'fieldlines', record.fieldlines);
    tools.SetValue(mep + 'defaultvalue', record.defaultvalue);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'iif.intname': tools.GetEncode(tools.GetValue(mep + 'searchinterface'))
        });
      });
    };
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('FormGetListIntField.do?ifd.intid=') + tools.GetValue(mep + 'intid');
      me.plDetailGrid.store.load();
    };
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    
   me.callParent(arguments);
    
    tools.SetValue(mep + 'specserial', '1');
    tools.SetValue(mep + 'methodcode', '01');
    tools.SetValue(mep + 'intorder', '0');
  },
  
  OnLoadData : function(item) {
    var me = this;
    var mep = me.tranPrefix;

    if (item && !Ext.isEmpty(item.intid)) {
      
//      me.samParStore.load({url:'BasGetListBasSampleParameterDetail.do',params:{sampleid:item.sampleid}});
      
      tools.SetValue(mep + 'intid', item.intid);
      tools.SetValue(mep + 'intname', item.intname);
      tools.SetValue(mep + 'intcode', item.intcode);
      tools.SetValue(mep + 'specserial', item.specserial==''?'1':item.specserial);
      tools.SetValue(mep + 'inttype', item.inttype);
      tools.SetValue(mep + 'methodcode', item.methodcode==''?'01':item.methodcode);
      tools.SetValue(mep + 'ismutil', item.ismutil);
      tools.SetValue(mep + 'intorder', item.intorder==''?'0':item.intorder);
//      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'parameterid', item.parameterid);

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
        if (action.result.data.intid) {
          tools.SetValue(mep + 'intid', action.result.data.intid);
        };
      };
    };
    me.OnDetailRefresh();
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    Ext.getCmp(mep + 'intid').reset();
    Ext.getCmp(mep + 'intid').focus(true, 500);
  }
  
});
