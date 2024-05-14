Ext.define('alms.meetsign', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '管理评审会议签到',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewmeetsign',
      storeUrl: 'RewSearchMeetSign.do',
      saveUrl: 'RewSaveMeetSign.do',
      hasGridSelect: true,
      expUrl: 'RewSearchMeetSign.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'meetid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    var noticeid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '评审通知', name: 'ms.noticeid', id: mep + 'noticeid', winTitle: '选择评审计划',
      maxLength: 20, maxLengthText: '评审计划编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '评审计划编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_reviewnotice',
      storeUrl: 'RewSearchReviewNoticeForMeet.do',
      editable:false,
//      searchTools:partsitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    noticeid.on('griditemclick', me.OnNoticeSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .50, layout: 'anchor', items: [
          tools.FormText('会议编号', 'ms.meetid', mep + 'meetid', 20, '96%', false, 1),
          tools.FormText('会议地点', 'ms.meetplace', mep + 'meetplace', 30, '96%', false, 3),
          tools.FormText('会议主题', 'ms.meettopic', mep + 'meettopic', 30, '96%', false, 5)
        ]},
       { xtype: 'container', columnWidth: .50, layout: 'anchor', items: [
         noticeid,                                                                
         tools.FormDate('会议时间', 'ms.meetdate', mep + 'meetdate', 'Y-m-d', '100%', false, 4),
         tools.FormCheckCombo('参与人员', 'partusers', mep + 'partusers', tools.ComboStore('User'), '100%', false, 12),
        {xtype:'hiddenfield',name:'ms.deal.action',id: mep + 'datadeal'}
      ]}   
      ]}
      
    ];
    
    me.disNews = ['meetid','meetdate','meetplace'];
    me.disEdits = ['meetid','meetdate','meetplace'];
    me.enNews = ['meettopic','partusers','noticeid'];
    me.enEdits = ['meettopic','partusers','noticeid'];
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '会议编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchmeetid', id: mep + 'searchmeetid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '会议主题', labelWidth: 70, width: 200, maxLength: 40, name: 'searchmeettopic', id: mep + 'searchmeettopic', allowBlank: true },
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
          'ms.meetid': tools.GetEncode(tools.GetValue(mep + 'searchmeetid')),
          'ms.meettopic': tools.GetEncode(tools.GetValue(mep + 'searchmeettopic'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.JsonGet(tools.GetUrl('RewGetMeetSign.do?ms.meetid=') + item.meetid);
    if(item && !Ext.isEmpty(item.meetid)){
      tools.SetValue(mep + 'meetid', item.meetid);
      tools.SetValue(mep + 'noticeid', record.noticeid);
      tools.SetValue(mep + 'meettopic', item.meettopic);
      tools.SetValue(mep + 'meetdate', item.meetdate);
      tools.SetValue(mep + 'meetplace', item.meetplace);
      var partusers = tools.JsonGet(tools.GetUrl('RewGetListReviewMeetUser.do?mu.meetid=')+item.meetid).data;
      var partuser = [];
      for(var i = 0; i < partusers.length; i++){
        partuser.push(partusers[i].partuser);
      }
      tools.SetValue(mep + 'partusers', partuser);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnGetSaveParams: function () {
    var me = this;
    var mep = me.tranPrefix;
    var partuser = [];
    var partusers = Ext.getCmp(mep + 'partusers').getValue().split(',');
     Ext.each(partusers, function (item, index) { 
         partuser.push({ partuser: item});
    });
     me.saveParams = { details: Ext.encode(partuser) };
  },
  
  
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'meetid').reset();
    Ext.getCmp(mep + 'meetid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.meetid) {
          tools.SetValue(mep + 'meetid', action.result.data.meetid);
        }
      }
    }
//    me.OnDetailRefresh();
  },
  
  OnNoticeSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.tranid) {
      tools.SetValue(mep+'noticeid',item.tranid);
      tools.SetValue(mep+'meetdate',item.reivewdate);
      tools.SetValue(mep+'meetplace',item.reviewplace);
    }
  }
  
});