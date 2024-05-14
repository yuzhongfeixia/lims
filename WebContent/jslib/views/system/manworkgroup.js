Ext.define('gpersist.manworkgroup', {
  extend: 'gpersist.base.listeditform',
  
  authGrid : null,
  
  constructor : function(config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '工作组',
      winWidth: 750,
      winHeight: 160,
      columnUrl: 'p_get_workgroup',
      storeUrl: 'UserSearchWorkGroup.do',
      expUrl: 'UserSearchWorkGroup.do',
      saveUrl: 'UserSaveWorkGroup.do',
      hasPage: true
    });
    
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit : function() {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText('工作组编号', 'wgroup.workgroup', mep + 'workgroup', 6, '96%', false, 1)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('工作组名称', 'wgroup.workgroupname', mep + 'workgroupname', 16, '100%', false, 2),                             
          {xtype:'hiddenfield',name:'wgroup.deal.action',id: mep + 'datadeal'}]
        }]
     },
     me.authGrid
    ];
    
    me.disNews = [];
    me.disEdits = ['workgroup'];
    me.enNews = ['workgroup','workgroupname'];
    me.enEdits = ['workgroupname'];
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (item && !Ext.isEmpty(item.workgroup)) {
      tools.SetValue(mep + 'workgroup', item.workgroup);
      tools.SetValue(mep + 'workgroupname', item.workgroupname);
    }
    else 
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '工作组'));
  }
 });