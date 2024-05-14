Ext.define('alms.datsampletest', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;

    Ext.apply(config, {
      editInfo: '样品数据计算设置',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_datsampletest',
      storeUrl: 'DatSearchDatSampleTest.do',
      saveUrl: 'DatSaveDatSampleTest.do',
      expUrl: 'DatSearchDatSampleTest.do',
      deleteUrl: 'DatDeleteDatSampleTest.do',
      hasPage: true,
      hasPrevNext: false,
      hasGridSelect: true,
      idPrevNext: 'parameterid'
    });    

    me.callParent(arguments);
  },
  
  OnInitGridToolBar: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.callParent();
    
    var items = [
      ' ', tools.GetToolBarCombo('parameter', mep + 'parameter', 280, '检测参数名称', 90, tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE), gpersist.SELECT_MUST_VALUE),
      ' ', tools.GetToolBarCombo('source', mep + 'source', 200, '数据来源', 60, tools.ComboStore('ClassSource', gpersist.SELECT_MUST_VALUE), gpersist.SELECT_MUST_VALUE)
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls = [
      {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
           tools.FormCombo('检测参数', 'dst.parameterid', mep + 'parameterid', tools.ComboStore('BasParameter', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
           tools.FormCombo('数据来源', 'dst.classsource', mep + 'classsource', tools.ComboStore('ClassSource', gpersist.SELECT_MUST_VALUE), '96%', false, 3),
           tools.FormText('平均系数', 'dst.avefactor', mep + 'avefactor', 10, '96%', false, 7, 'ismoney'),
           tools.FormText('执行序号', 'dst.testserial', mep + 'testserial', 10, '96%', false, 8)
           
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [                 
           tools.FormText('数据标识', 'dst.classfield', mep + 'classfield', 20, '100%', false, 4),
           tools.FormText('小数位数', 'dst.digitnumber', mep + 'digitnumber', 10, '100%', false, 6, 'isnumber'),
           tools.FormText('对比系数', 'dst.comparefactor', mep + 'comparefactor', 10, '100%', false, 8, 'ismoney'),
           tools.FormText('缺省值', 'dst.defvalue', mep + 'defvalue', 10, '100%', false, 9),
           {xtype: 'hiddenfield', name: 'dst.deal.action', id: mep + 'datadeal'}
         ]}
      ]},
      tools.FormCombo('检测方法', 'dst.teststandard', mep + 'teststandard', tools.ComboStore('BasTest', gpersist.SELECT_MUST_VALUE), '100%', true, 11),
      tools.FormText('计算公式', 'dst.actformula', mep + 'actformula', 200, '100%', true, 12),
      tools.FormCombo('计算函数', 'dst.funcformula', mep + 'funcformula', tools.ComboStore('DatFuncMath', gpersist.SELECT_NULL_VALUE), '100%', false, 5)
    ];
    
    me.disNews = [];
    me.disEdits = ['parameterid','classsource','classfield'];
    me.enNews = ['parameterid','sampleid', 'classsource', 'classfield', 'funcvalue', 'testserial', 'avefactor', 'defvalue', 'digitnumber', 
       'comparefactor', 'teststandard', 'funcformula', 'actformula'];
    me.enEdits = ['parameterid','funcvalue', 'testserial','teststandard', 'avefactor', 'defvalue', 'digitnumber', 'comparefactor', 'funcformula', 'actformula'];
  
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var teststandard = tools.GetValue(mep+'teststandard');
    if(teststandard == '-3'){
      tools.SetValue(mep + 'teststandard', '');
    }
    return true;
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    tools.SetValue(mep + 'classsource', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'testserial', 1);
    tools.SetValue(mep + 'funcvalue', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'digitnumber', 0);
    tools.SetValue(mep + 'comparefactor', 0);
    tools.SetValue(mep + 'avefactor', 0);
    tools.SetValue(mep + 'defvalue', '/');
    tools.SetValue(mep + 'funcformula', gpersist.SELECT_NULL_VALUE);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'dst.parameterid': tools.GetEncode(tools.GetValue(mep + 'parameter')),
          'dst.classsource': tools.GetEncode(tools.GetValue(mep + 'source'))
        });
      });
    };
  },
  
  OnLoadData: function(record) {
    var me = this;
    var mep = me.tranPrefix;   
    
    if (record && !Ext.isEmpty(record.parameterid)) {
      tools.SetValue(mep + 'parameterid', record.parameterid);
      tools.SetValue(mep + 'classsource', record.classsource);
      tools.SetValue(mep + 'funcvalue', record.funcvalue);
      tools.SetValue(mep + 'classfield', record.classfield);
      tools.SetValue(mep + 'teststandard', record.teststandard);
      tools.SetValue(mep + 'avefactor', record.avefactor);
      tools.SetValue(mep + 'defvalue', record.defvalue);
      tools.SetValue(mep + 'testserial', record.testserial);
      tools.SetValue(mep + 'digitnumber', record.digitnumber);
      tools.SetValue(mep + 'comparefactor', record.comparefactor);
      tools.SetValue(mep + 'actformula', record.actformula);
      tools.SetValue(mep + 'funcformula', Ext.isEmpty(record.funcformula) ? gpersist.SELECT_NULL_VALUE : record.funcformula);
      return true;
    } 
    else {
      me.dataDeal == gpersist.DATA_DEAL_SELECT;
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
    
    return false;
  },
  
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'parameterid').focus(true, 500);
  },
  
  OnBeforeDelete: function (datas) {
    var me = this;
    var json = [];
    for (var i = 0; i < datas.length; i++) {
      json.push({parameterid: datas[i].data.parameterid, classsource: datas[i].data.classsource, 
        classfield: datas[i].data.classfield, teststandard: datas[i].data.teststandard });
    }
    me.deleteParams = {deletes : Ext.encode(json)};
    return true;
  }
  
});