Ext.define('gpersist.base.flowform', {
  extend : 'gpersist.base.busform',
  
  plflow: null,
  plcontent: null,
  busflow: '',
  tranid: '',
  
  OnInitConfig: function () {
    var me = this;
    
    me.callParent(arguments);
    
    me.plflow = null;
    me.plcontent = null;
    me.busflow = '';
  },

  OnFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    var date = new Date();
    var mainpanel = Ext.getCmp('tpanel' + me.mid);
    
    if (!Ext.isDefined(mainpanel))
      return;

    mainpanel.removeAll();
    tools.log('OnFormLoad1', date);
    // 生成列表的字段属性
    me.columns = [];
    me.fields = []; 
    tools.GetGridColumnsByUrl(this.columnUrl, me.columns, me.fields, mep + '_l_'); tools.log('OnFormLoad2', date);
    me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields); tools.log('OnFormLoad2', date);
    
    // 加载主页面前
    me.OnBeforeFormLoad();
    tools.log('OnFormLoad2', date);
    // 生成列表
    me.plGrid = Ext.create('Ext.grid.Panel', {
      id : mep + 'grid',
      region : 'center',
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : me.columns,
      store : me.gridStore,
      tbar : me.tbGrid,
      enableColumnMove: false, 
      enableColumnHide: false,
      suspendLayout: false,
      selModel: me.hasGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1 }) : {},
      listeners : {
        'itemdblclick' : { fn : me.OnShow, scope : me },
        'itemclick' : { fn : me.OnItemClick, scope : me }
      }
    });
    
    // 分页处理
    if (me.hasPage) {
      me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
        store : me.gridStore,
        displayInfo : true,
        displayMsg : gpersist.STR_PAGE_FMT,
        emptyMsg : gpersist.STR_NO_DATA, suspendLayout: true,
        dock : 'bottom'
      }));
    }
    tools.log('OnFormLoad2-0', date);
    // 主界面生成后处理
    me.OnAfterFormLoad();
    tools.log('OnFormLoad2', date);
    // 建立编辑界面
    me.OnCreateEditWin();
    tools.log('OnFormLoad3', date);
    
    var pleditview = Ext.create('Ext.Panel', {
      title: '业务处理',
      frame : false,
      autoScroll : false,
      region : 'center',
      border : false,
      layout : 'border',
      margins : '0 0 0 0',
      padding : '0 0 0 0'
    });
    
    if (me.hasEdit)
      pleditview.add(me.plEdit);
      
    if (me.hasDetail) {
      if (me.plDetail && !me.hasEdit)
        me.plDetail.region = 'center';
        
      pleditview.add(me.plDetail);
    }
    
    var tbflow = Ext.create('Ext.toolbar.Toolbar', {
      items : [' ', { id: mep + 'btnChangeDetail', text: '返回', iconCls: 'icon-list', handler: me.OnChangeDetail, scope: me }]
    });
    
    me.plflow = Ext.create('Ext.Panel', {
      title: '业务流程',
      frame : false,
      autoScroll : false,
      bodyStyle : 'padding:5px;background:#FFFFFF',
      region : 'center',
      border : false,
      layout : 'border',
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      tbar: tbflow,
      items: [{xtype: 'label', id: mep + 'flowinfo', html: ''}]
    });
    
    me.plcontent = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'        
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : me.deferredRender,
      items :
        [pleditview, me.plflow]
    });
    
    tools.log('OnFormLoad4', date);
    
    me.tabMain = Ext.create('Ext.tab.Panel', {
      border : false,
      activeTab : 0,
      bodyBorder : false,
      defaults : {
        bodyStyle : 'border:0px;padding:0px;'
      },
      margins : '0 0 0 0',
      region : 'center',
      deferredRender : me.deferredRender,
      items :
        [me.plGrid, me.plcontent]
    });    
    
    tools.log('OnFormLoad6', date);
    
    me.tabMain.getTabBar().setVisible(false);    
    me.plcontent.getTabBar().setVisible(false); 
    
    tools.log('OnFormLoad5', date);
    mainpanel.add(me.tabMain);
    tools.log('OnFormLoad7', date);
    
    if (me.hasAutoLoad)
      me.OnSearch();
      
    tools.log('OnFormLoad8', date);
  },
  
  OnCreateEditWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnBeforeCreateEditToolBar();
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnFormCheck', text: '审核', iconCls: 'icon-audit', handler: me.OnFormCheck, scope: me },
      '-', ' ', { id: mep + 'btnChangeFlow', text: '业务流程', iconCls: 'icon-list', handler: me.OnChangeFlow, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
    if (me.hasSubmit) {
      Ext.Array.insert(me.editToolItems, 2, [
        ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
      ]);
    }
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
    
    me.OnAfterCreateEditToolBar();
    
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {
      items : me.editToolItems
    });
    
    me.OnBeforeCreateEdit();
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
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
    
    me.OnAfterCreateEdit();
    
    if (me.hasDetail) {
      me.detailFuncs = Ext.create('Ext.util.MixedCollection');
      me.columnDetails = [];
      me.fieldDetails = [];
      
      me.OnGetDetailFunction();      

      tools.GetGridColumnsByUrl(me.urlDetailColumn, me.columnDetails, me.fieldDetails, mep + '_d_', me.detailFuncs, me.hasDetailCheck);

      me.gridDetailStore = tools.CreateGridStore(tools.GetUrl(this.urlDetail), me.fieldDetails);

      Ext.each(me.columnDetails, function (col, index) {
        if (col.xtype == 'checkcolumn') {
          col.on('beforecheck', function(check) { 
            if (me.dataDeal == gpersist.DATA_DEAL_SELECT) 
              check.isCancel = true;
            else 
              check.isCancel = false;
          });
        }
      });

      me.OnBeforeCreateDetail();
      
      plugins = [];
      
      if (me.hasDetailGridEdit) {
        me.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
          clicksToEdit: 1,
          listeners: {
            edit: { fn: me.OnDetailEdit, scope: me},
            beforeedit: function (e, editor) {
              if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
                return false;
              
              if (me.OnBeforeDetailEdit(e, editor))
                return false;
            }
          }
        });

        plugins = [me.cellEditing];
      }

      me.plDetailGrid = Ext.create('Ext.grid.Panel', {
        id : mep + 'detailgrid',
        region : 'center',
        title : me.detailTitle,
        autoScroll : true,
        frame : false,
        border : false,
        margins : '0 0 0 0',
        padding : '0 0 0 0',
        loadMask : true,
        columnLines : true,
        viewConfig : {
          autoFill : true,
          stripeRows : true
        },
        features : [{ ftype : 'summary' }],
        columns : me.columnDetails,
        store : me.gridDetailStore,
        selModel: me.hasDetailGridSelect ? new Ext.selection.CheckboxModel({ injectCheckbox: 1, checkOnly: true }) : {},
        plugins: plugins,
        listeners : {
          'itemdblclick' : { fn : me.OnListSelect, scope : me }
        }        
      });

      me.deitems = [
        ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
        ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me }
      ];
      
      me.OnAfterCreateDetailToolBar();
      
      tools.SetToolbar(me.deitems, mep);
        
      var tbdetailedit = Ext.create('Ext.toolbar.Toolbar', {
        dock : 'top',
        items : me.deitems
      });
        
      if (me.hasDetailEdit)
        me.plDetailGrid.insertDocked(0, tbdetailedit);
      
      if (me.hasPageDetail) {
        me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
          store : me.gridDetailStore,
          displayInfo : true,
          displayMsg : gpersist.STR_PAGE_FMT,
          emptyMsg : gpersist.STR_NO_DATA,
          dock : 'bottom'
        }));
      }
      
      if (me.detailTabs > 1) {
        me.plDetail = Ext.create('Ext.tab.Panel', {
          border : false,
          activeTab : 0,
          bodyBorder : false,
          defaults : {
            bodyStyle : 'border:0px;padding:0px;'
          },
          margins : '0 0 0 0',
          region : 'center',
          deferredRender : false,
          items : [me.plDetailGrid]
        });
      }
      else
        me.plDetail = me.plDetailGrid;
        
      me.OnAfterCreateDetail();
    }
  },
  
  OnChangeFlow: function () {
    var me = this;
    
    me.plcontent.setActiveTab(1);
    
    me.OnGetFlowHtml();
  },
  
  OnChangeDetail: function () {
    this.plcontent.setActiveTab(0);
  },
  
  OnGetFlowHtml: function () {
    var me = this, mep = this.tranPrefix;
    
    if (Ext.isEmpty(me.tranid))
      me.tranid = me.idPrevNext;
      
    var html = tools.HtmlGet('FlowGetTodoHtmlByTran.do?todo.busflow=' + me.busflow + '&todo.tranid=' + tools.GetValue(mep + me.tranid));
    
    html = html || '';
    
    Ext.fly(Ext.getCmp(mep + 'flowinfo').getEl()).update(html);
  }
});