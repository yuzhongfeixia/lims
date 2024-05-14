Ext.define('alms.datclasssource', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '数据类来源',
      winWidth: 750,
      winHeight: 120,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_datclasssource',
      storeUrl: 'DatSearchDatClassSource.do',
      saveUrl: 'DatSaveDatClassSource.do',
      expUrl: 'DatSearchDatClassSource.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'classsource'
    });

    me.callParent(arguments);
  },
   
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText(tools.MustTitle('类来源编号'), 'dcs.classsource', mep + 'classsource', 20, '96%', false, 1)
                                                                               
        ]},
        {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormText(tools.MustTitle('类来源名称'), 'dcs.classsourcename', mep + 'classsourcename', 20, '100%', false, 2),
           {xtype: 'hiddenfield', name: 'dcs.deal.action', id: mep + 'datadeal'}                                                                                                                                    
        ]} 
     ]}                                                        
   ];
    
    
    me.disNews = [];
    me.disEdits = ['classsource'];
    me.enNews = ['classsource', 'classsourcename'];
    me.enEdits = ['classsourcename'];
  },

  OnSetData: function (id, record) {
    var me = this;
    var mep = me.tranPrefix;
    
    var item = tools.JsonGet(tools.GetUrl('DatGetDatClassSource.do?dcs.classsource=') + id);
    
    if (item && !Ext.isEmpty(item.classsource)) {
      tools.SetValue(mep + 'classsource', item.classsource);
      tools.SetValue(mep + 'classsourcename', item.classsourcename);
      return true;
    }
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'classsource').reset();
    Ext.getCmp(mep + 'classsource').focus(true, 500);
  }
});