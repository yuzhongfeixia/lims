Ext.define('alms.mainconsole', {
  extend: 'gpersist.base.baseform',
  
  disnews: [],
  ennews: [],
  disedits: [],
  enedits: [],
  
  plTree: null,
  plMain: null,
  plEdit: null,
  
  titleLab: '项目',
  
  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      selectParams: []
    });

    me.callParent(arguments);
  },
  
  OnInitConfig: function () {
    var me = this;

    me.callParent(arguments);

    me.plTree = null;
    me.plMain = null;
    me.plEdit = null;
    me.cbCompany = null;
  },
  
  OnFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;

    if (!Ext.isDefined(Ext.getCmp('mainconsole')))
      return;
    
    gpersist.nowlabid = '';
    gpersist.nowlabtype = '';
    gpersist.nowlabname = '';
            
    Ext.getCmp('mainconsole').removeAll();

    me.plTree = Ext.create('Ext.tree.Panel', {     
      id:mep + 'projectlist',
      rootVisible: false,
      region: 'west',
      title: me.titleLab + '列表',
      width: 250,
      minWidth: 200,
      maxWidth: 400,
      autoScroll: true,
      border: true,
      margins: '2 0 2 2',
      split: true,
      layout: 'fit',
      animate: false,
      tools: me.OnGetTreeTool(),
      store: Ext.create('Ext.data.TreeStore', {
        model:'gpersist.treemodel',
        proxy: {
          type: 'ajax',
          url: tools.GetUrl('BasBusProjectByUser.do') 
        }
      }),
      listeners: {
        'itemclick': {fn: function (view, record) {
          me.OnLoadData(record);
        }, scope:me}
      } 
    });    

    me.plMain = Ext.create('Ext.app.PortalPanel', {
      frame : false,
      autoScroll : false,
      region : 'center',
      border : true,
      layout : 'column',
      title : '',
      bodyCls: 'portalbg',
      margins : '2 2 2 0',
      padding : '0 0 0 0',
      items: [
        {
                        id: 'col-1',
                        items: [{
                            id: 'portlet-1',
                            title: '插件3',
                            html: '<div class="portlet-content">asdasdasd</div>',
                            listeners: {
                                'close': Ext.bind(this.OnPortletClose, this)
                            }
                        }]
                    },{
                        id: 'col-2',
                        items: [{
                            id: 'portlet-3',
                            title: '插件1',
                            html: '<div class="portlet-content">asdasdasd</div>',
                            listeners: {
                                'close': Ext.bind(this.OnPortletClose, this)
                            }
                        }]
                    },{
                        id: 'col-3',
                        items: [{
                            id: 'portlet-4',
                            title: '插件2',
                            tools: this.getTools(),
                            html: '<div class="portlet-content">ewrwew</div>',
                            listeners: {
                                'close': Ext.bind(this.OnPortletClose, this)
                            }
                        }]
                    }
      ]
    });
    
    me.plMain.removeAll();
    Ext.getCmp('mainconsole').add(me.plTree);
    Ext.getCmp('mainconsole').add(me.plMain); 
  },
  
  OnGetTreeTool : function() {
    var me = this;
    
    return [{
      xtype : 'tool',
      type : 'refresh',
      tooltip: '刷新工程项目',
      handler : me.OnTreeRefresh,
      scope: me
    }];
  },
  
  OnTreeRefresh: function (e, target, panelHeader, tool) {
    var me = this;
    
    if (me.plTree) {
      var tabs = Ext.getCmp("mainTabs");
      
      if (tabs.items.length > 1) {
        Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '刷新工程项目列表将关闭正在打开的窗口，是否继续？', function (btn) {
          if (btn == 'yes') {
            me.plTree.getRootNode().removeAll(false);
            me.plTree.store.load();
            
            Ext.getCmp('mainsysinfo').setText('');
            gpersist.nowlabid = '';
            gpersist.nowlabtype = '';
            gpersist.nowlabname = '';
            
            for (var i = tabs.items.length - 1; i >= 1; i--) {
              tabs.items.getAt(i).close();
            }
          }
        });        
      }
    }
  },
  
  OnLoadData: function (record) {
    var me = this;
    
    var sysinfo = Ext.getCmp('mainsysinfo');
    
    if (sysinfo) {     
      if (!Ext.isEmpty(record.data.id)) {
        var ids = record.data.id.split('-');
        
        if (ids.length == 2) {
          if ((ids[1] == gpersist.nowlabid) && (ids[0] == gpersist.nowlabtype)) {
            // 相同什么都不处理
          }
          else {         
            var tabs = Ext.getCmp("mainTabs");
      
            if (tabs.items.length > 1) {
              Ext.Msg.confirm(gpersist.CONFIRM_TITLE, '改变工程项目列表将关闭正在打开的窗口，是否继续？', function (btn) {
                if (btn == 'yes') {                  
                  for (var i = tabs.items.length - 1; i >= 1; i--) {
                    tabs.items.getAt(i).close();
                  }
                }
              });        
            }
            
            sysinfo.setText('当前项目：' + record.data.text);
            gpersist.nowlabid = ids[1];
            gpersist.nowlabtype = ids[0];
            gpersist.nowlabname = record.data.text; 
          }
        }
        else
          tools.alert('项目数据格式错误！');
      }
    }
  },
  
  getTools : function() {
    return [{
      xtype : 'tool',
      type : 'gear',
      handler : function(e, target, panelHeader, tool) {
        var portlet = panelHeader.ownerCt;
        portlet.setLoading('数据加载...');
        Ext.defer(function() {
          portlet.setLoading(false);
        }, 2000);
      }
    }];
  },
  
  OnPortletClose: function (portlet) {
  
  },
    
  OnRefresh: function () {
    var me = this;
    
    if (me.plTree) {
      me.plTree.getRootNode().removeAll(false);
      me.plTree.store.load();
    }
  }
});