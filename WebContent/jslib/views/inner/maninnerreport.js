Ext.define('alms.maninnerreport', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: [],
      editInfo: '内审报告',
      winWidth: 750,
      winHeight: '100%',
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_innerreport',
      storeUrl: 'InnerSearchInnerReport.do',
      saveUrl: 'InnerSaveInnerReport.do',
      expUrl: 'InnerSearchInnerReport.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'reportid',
      hasSubmit: true,
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var auditleadname = tools.UserPicker(mep + 'auditleadname','innerreport.auditleadname','审核组长',90);
     
    auditleadname.on('griditemclick', me.OnUserSelect, me);
    
    var groupitems = [
     ' ', { xtype: 'textfield', fieldLabel: '小组名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchgroupname', id: mep + 'searchgroupname', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnGroupSearch, scope: me }
    ];
    var groupname = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '小组', name: 'innerreport.groupname', id: mep + 'groupname', winTitle: '选择小组',
      maxLength: 20, maxLengthText: '小组名称不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '小组为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_innergroup',
      storeUrl: 'InnerSearchInnerGroup.do',
      editable:false,
      searchTools:groupitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    groupname.on('griditemclick', me.OnGroupSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('报告编号', 'innerreport.reportid', mep + 'reportid',20, '96%', false, 1),
          groupname,
          tools.FormDate('审核日期', 'innerreport.auditdate', mep + 'auditdate', 'Y-m-d', '96%', false, 5,''),
          tools.FormText('业务员', 'innerreport.tranusername', mep + 'tranusername', 40, '96%', false, 7)
          
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormCombo('部门', 'innerreport.auditeddept', mep + 'auditeddept', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '96%', false, 2,90),
          auditleadname,
          tools.FormDate('组长签字时间', 'innerreport.leaddate', mep + 'leaddate', 'Y-m-d', '96%', false, 6,'',90),
          tools.FormDate('记录日期', 'innerreport.trandate', mep + 'trandate', 'Y-m-d', '96%', false, 8,nowdate,90),
          {xtype:'hiddenfield',name:'innerreport.tranuser',id: mep + 'tranuser'},
          {xtype:'hiddenfield',name:'innerreport.groupid',id: mep + 'groupid'},
          {xtype:'hiddenfield',name:'innerreport.auditlead',id: mep + 'auditlead'},
          {xtype:'hiddenfield',name:'innerreport.auditeddeptname',id: mep + 'auditeddeptname'},
          {xtype:'hiddenfield',name:'innerreport.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('审核要素', 'innerreport.auditelem', mep + 'auditelem', 100, '98%', true,9,5),
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: "htmleditor", name: "innerreport.auditby", id: mep + 'auditby', tabIndex: 9, enableFont: true,
          fieldLabel: "审核依据", height: 140, width:'98%', labelWidth: 80, margin: '0 0 10 0', fontFamilies: ["宋体", "隶书", "黑体"]}
      ]},
      tools.FormTextArea('审核目的', 'innerreport.auditgoal', mep + 'auditgoal', 200, '98%', true,11,3),
      tools.FormTextArea('内审情况', 'innerreport.auditdesc', mep + 'auditdesc', 200, '98%', true,12,5),
      tools.FormTextArea('不合格项', 'innerreport.belowstandard', mep + 'belowstandard', 100, '98%', true,13,5),
      tools.FormTextArea('首次执行情况', 'innerreport.beforedesc', mep + 'beforedesc', 100, '98%', true,14,5),
      tools.FormTextArea('质量体系运行与评价', 'innerreport.inneradv', mep + 'inneradv', 200, '98%', true,15,5)
    ];
    
    me.disNews = ['reportid', 'tranuser', 'tranusername', 'trandate'];
    me.disEdits = ['reportid', 'tranuser', 'tranusername', 'trandate'];
    me.enNews = ['groupname', 'groupid', 'auditelem', 'auditeddept', 'auditeddeptname', 'auditby', 'auditdate', 'auditgoal', 'auditlead', 'auditleadname', 
                 'leaddate', 'auditdesc', 'belowstandard', 'beforedesc', 'inneradv'];
    me.enEdits = ['groupname', 'groupid', 'auditelem', 'auditeddept', 'auditeddeptname', 'auditby', 'auditdate', 'auditgoal', 'auditlead', 'auditleadname', 
                 'leaddate', 'auditdesc', 'belowstandard', 'beforedesc', 'inneradv'];
    //给部门名称赋值
    Ext.getCmp(mep + 'auditeddept').on('select',me.OnDeptSelect,me);
  },
  
  OnDeptSelect:function(combo, records, eOpts){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep+"auditeddeptname",records[0].data.name);
  },
  
  OnGroupSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnGroupBeforeLoad();
      var groupname = Ext.getCmp(mep + 'groupname');
      groupname.store.loadPage(1);
   },
   
   OnGroupBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var groupname = Ext.getCmp(mep + 'groupname');
      if(groupname.store){
        groupname.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'innergroup.groupname':tools.GetValueEncode(mep + 'searchgroupname')
            });
        });
      }
  },
  
  OnGroupSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.groupid){
        tools.SetValue(mep+"groupid",item.groupid);
        tools.SetValue(mep+"groupname",item.groupname);
     }
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
  
  OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"auditlead",item.userid);
      tools.SetValue(mep+"auditleadname",item.username);
    }
   },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '报告编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchreportid', id: mep + 'searchreportid', allowBlank: true },
      ' ', tools.GetToolBarCombo('searchdeptid', mep + 'searchdeptid', 210, '部门', 70, tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE))
     ];
     
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(searchitems,mep);
    tools.SetToolbar(items, mep);
     
    var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
    me.tbGrid.add(searchtoolbar);
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'innerreport.reportid':tools.GetValueEncode(mep+'searchreportid'),
           'innerreport.auditeddept':tools.GetValueEncode(mep+'searchdeptid')
         })
       });
     }
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
    tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'reportid', item.reportid);
    tools.SetValue(mep + 'groupid', item.groupid);
    tools.SetValue(mep + 'groupname', item.groupname);
    tools.SetValue(mep + 'auditelem', item.auditelem);
    tools.SetValue(mep + 'auditeddept', item.auditeddept);
    tools.SetValue(mep + 'auditeddeptname', item.auditeddeptname);
    tools.SetValue(mep + 'auditby',  Ext.util.Format.htmlDecode(item.auditby));
    tools.SetValue(mep + 'auditdate', item.auditdate);
    tools.SetValue(mep + 'auditgoal', item.auditgoal);
    tools.SetValue(mep + 'auditlead', item.auditlead);
    tools.SetValue(mep + 'auditleadname', item.auditleadname);
    tools.SetValue(mep + 'leaddate', item.leaddate);
    tools.SetValue(mep + 'auditdesc', item.auditdesc);
    tools.SetValue(mep + 'belowstandard', item.belowstandard);
    tools.SetValue(mep + 'beforedesc', item.beforedesc);
    tools.SetValue(mep + 'inneradv', item.inneradv);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    return true;
  },
  
  OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     var auditby = Ext.util.Format.htmlEncode(Ext.getCmp(mep + 'auditby').getValue()); 
     me.saveParams = { auditby:auditby};
   },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'reportid').reset();
    Ext.getCmp(mep + 'reportid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.reportid) {
          tools.SetValue(mep + 'reportid', action.result.data.reportid);
        }
      }
    }
  }
  
});