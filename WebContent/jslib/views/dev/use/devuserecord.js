 Ext.define('alms.devuserecord', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '设备使用记录',
      winWidth: 750,
      winHeight: 320,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devuse',
      storeUrl: 'DevSearchDevUse.do',
      saveUrl: 'DevSaveDevUse.do',
      hasGridSelect: true,
      expUrl: 'DevSearchDevUse.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var nowdate = new Date();
    
    var devitems = [
//      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevforuserid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevforusername', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: me }
    ];
    
    var devname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '设备名称', name: 'du.devname', id: mep + 'devname', winTitle: '选择设备',
      maxLength: 40, maxLengthText: '设备名称不能超过40个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '设备名称不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devforuse',
      storeUrl: 'DevSearchBasDevForUse.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 850, pickerHeight: 450
    });
    
    devname.on('griditemclick', me.OnDevSelect, me);
    
    var sampleid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'samplename', id: mep + 'samplename', winTitle: '选择样品',
      maxLength: 40, maxLengthText: '样品名称不能超过30个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '样品名称不能为空！', allowBlank: false, anchor: '96%', tabIndex: 5,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
//      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 450
    });
    
    sampleid.on('griditemclick', me.OnSampleSelect, me);
    
    var url = '';
    url = 'BasGetListBasSampleParameterDetail.do'
    me.samParStore = new Ext.data.JsonStore({
     proxy:{
       type:'ajax',
       url:url,
       reader:{
         type:'json',
         root:'data'
       }
     },
     fields:[{name:'id',mapping:'parameterid'},{name:'name',mapping:'parametername'},{name:'standvalue',mapping:'standvalue'}],
     autoLoad:true
   });
    
    var samParCombo =tools.FormCheckCombo('检测项目', 'du.parameterid', mep + 'parameterid', me.samParStore,'100%', false, 6);
    
//    samParCombo.on('select',me.OnSelectFinAmount,me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'du.tranid', mep + 'tranid', 30, '96%', false, 1),
          tools.FormDate('开机时间', 'du.usebegin', mep + 'usebegin', 'Y-m-d', '96%', false, 3),
          sampleid,
          tools.FormCombo('使用前状况', 'du.beforestatus', mep + 'beforestatus', tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 7)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          devname,
          tools.FormDate('关机时间', 'du.useend', mep + 'useend', 'Y-m-d', '100%', false, 4),
          samParCombo,
          tools.FormCombo('使用后状态', 'du.afterstatus', mep + 'afterstatus', tools.ComboStore('DevStatus', gpersist.SELECT_MUST_VALUE), '100%', false, 8),
          {xtype:'hiddenfield',name:'du.devid',id: mep + 'devid'},
          {xtype:'hiddenfield',name:'du.sampletran',id: mep + 'sampletran'},
          {xtype:'hiddenfield',name:'du.sampleid',id: mep + 'sampleid'},
          {xtype:'hiddenfield',name:'du.parametername',id: mep + 'parametername'},
          {xtype:'hiddenfield',name:'du.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('使用功能', 'du.usefunction', mep + 'usefunction', 100, '100%', true, 9, 4),
      tools.FormTextArea('备注', 'du.remark', mep + 'remark', 100, '100%', true, 10, 4)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['devname','usebegin','samplename','beforestatus','useend',
                 'parameterid','afterstatus','usefunction','remark'];
    me.enEdits = ['devname','usebegin','samplename','beforestatus','useend',
                  'parameterid','afterstatus','usefunction','remark'];
    
    Ext.getCmp(mep+'parameterid').on('change',function(){
      tools.SetValue(mep+'parametername',Ext.getCmp(mep+'parameterid').getDisplayValue());
    })
  },
  
  OnDevSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;

    if(item && !Ext.isEmpty(item.devid)){
      var parameterids ='';
//      var record = tools.JsonGet('LabGetListBusTaskDevByDevID.do?btd.devid=' + tools.GetEncode(tools.GetEncode(item.devid))+'&btd.sampletran='+item.sampletran).data;
//      if(record.length>0){
//        for(var i=0;i<record.length;i++){
//          if(i==0){
//            parameterids = record[i].parameterids;
//          }else{
//            parameterids =parameterids+','+ record[i].parameterids;
//          }
//        }
//      }
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'usebegin', item.devuse);
      tools.SetValue(mep + 'useend', item.devconfirm);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'sampletran', item.sampletran);
      tools.SetValue(mep + 'parameterid', item.parameterids);
      me.samParStore.load({url:'BasGetListBasSampleParameterDetail.do',params:{sampleid:item.sampleid}});
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    
    tools.SetValue(mep + 'beforestatus', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'afterstatus', gpersist.SELECT_MUST_VALUE);
    
    if (me.samParStore)
      me.samParStore.load({params:{sampleid:''}});
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
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
          'du.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'du.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(item && !Ext.isEmpty(item.tranid)){
//      var item = tools.JsonGet(tools.GetUrl('DevGetBasDev.do?bd.devid=') + record.devid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'devname', item.devname);
      tools.SetValue(mep + 'devid', item.devid);
      tools.SetValue(mep + 'usebegin', item.usebegin);
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'beforestatus', item.beforestatus);
      tools.SetValue(mep + 'useend', item.useend);
      tools.SetValue(mep + 'afterstatus', item.afterstatus);
      tools.SetValue(mep + 'usefunction', item.usefunction);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'sampletran', item.sampletran);
      me.samParStore.load({url:'BasGetListBasSampleParameterDetail.do',params:{sampleid:item.sampleid}});
      tools.SetValue(mep + 'parameterid', item.parameterid.split(','));
      tools.SetValue(mep + 'parametername', item.parametername);
     
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
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
  },
  
  OnDevSearch:function(){
    var me = this, mep = me.tranPrefix ;
    var dev = Ext.getCmp(mep+'devname');
    me.OnDevBeforeLoad();
    dev.store.loadPage(1);    
  },
    
  OnDevBeforeLoad:function(){
    var me = this, mep = me.tranPrefix;
    var dev = Ext.getCmp(mep+'devname');
    if(dev.store) {      
      dev.store.on('beforeload', function (store, options) {
        Ext.apply(store.proxy.extraParams, {
//          'am.devid': tools.GetValueEncode(mep + 'searchdevforuserid'),
          'btd.devname': tools.GetValueEncode(mep + 'searchdevforusername')
        });
      });
    };
  },
  
  OnSampleSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var sampleid = item.sampleid;
    
    if(item && !Ext.isEmpty(item.sampleid)){
      tools.SetValue(mep + 'sampleid', item.sampleid);
      tools.SetValue(mep + 'samplename', item.samplename);
      tools.SetValue(mep + 'parameterid', '');
      me.samParStore.load({params:{sampleid:sampleid}});
      
      var parameterid = Ext.getCmp(mep + 'parameterid');        
      if (parameterid) {
        parameterid.reset();
      }
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  }
  
   
});