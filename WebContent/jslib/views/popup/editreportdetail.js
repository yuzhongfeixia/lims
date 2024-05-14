 Ext.define('alms.editreport', {
      extend: 'gpersist.base.busform',

      winpreview: null,
      
      constructor: function (config) {
        var me = this;
        Ext.apply(config, {
          selectParams: ['BasSample', 'TestType', 'FormDirect', 'User', 'ValueSource', 'ValueType', 'AlignType','FontType'],
          editInfo: '试验报告',
          columnUrl: 'SysSqlColumn.do?sqlid=p_get_busreportaudit',
          storeUrl: 'DatSearchBusReportPrint.do',
          saveUrl: 'FormSaveBusPrintReport.do',
          idPrevNext: 'reportid',
          hasEdit: true,
          hasDetail: true,
          hasSubmit: false,
          detailTitle: '试验报告记录明细',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_busrecorddetail',
          urlDetail: 'FormGetListBusReportDetail.do',
          detailTabs: 1,
          hasDateSearch: false,
          hasDetailEdit: true,
          winWidth: 850,
          winHeight: 300
        });    

        me.callParent(arguments);
      },
      
      OnInitConfig: function () {
        var me = this;
        
        me.callParent(arguments);
        
        me.winpreview = null;
      },
      
      OnBeforeFormLoad: function () {
        var me = this;
        var mep = me.tranPrefix;
        
        me.OnInitGridToolBar();
        
        var items = [
          ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
          '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
          ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
          '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
        ];
            
        if (me.hasPage && me.hasPageSize)
          Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
            id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
        
        tools.SetToolbar(items, mep);
        
        var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
        
        me.tbGrid.add(toolbar);
      },
      
      OnGetSearchParam: function () {
        var me = this;
        var mep = me.tranPrefix;
        
        return {
          'breport.formname': tools.GetValueEncode(mep + 'searchformname')
        };
      },
      
      OnBeforeCreateEdit: function () {
        var me = this;
        var mep = this.tranPrefix;
        
        var nowdate = new Date();
        
        me.editControls = [
          {xtype: 'container', anchor: '100%', layout: 'column', items: [
            {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormCombo(tools.MustTitle('样品名称'), 'breport.sampleid', mep + 'sampleid', tools.ComboStore('BasSample'), '96%', true, 5),
              tools.FormText(tools.MustTitle('第一页与第二页间距'), 'breport.prefixtext', mep + 'prefixtext', 20, '96%', false, 1)
            ]},
            {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
              tools.FormText(tools.MustTitle('报告编号'), 'breport.reportid', mep + 'reportid', 20, '96%', false, 1),
              tools.FormText(tools.MustTitle('第二页与第三页间距'), 'breport.postfixtext', mep + 'postfixtext', 20, '96%', false, 1),
              {xtype: 'hiddenfield', name: 'breport.deal.action', id: mep + 'datadeal'},
              {xtype: 'hiddenfield', name: 'breport.modiyserial', id: mep + 'modiyserial'}
            ]}
           
         ]}   
       ];
         
        me.disNews = [];
        me.disEdits = ['reportid','sampleid'];
        me.enNews = ['prefixtext','postfixtext'];
        me.enEdits = ['prefixtext','postfixtext'];
      },
      
      
      OnBeforeCreateDetailEdit: function () {
        var me = this;
        var mep = me.tranPrefix;
        
        me.editDetailControls = [
          {xtype: 'container', anchor: '100%', layout: 'column', items: [
            {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText(tools.MustTitle('序号'), 'redetail.cellserial', mep + 'cellserial', 8, '96%', false, 1, 'isnumber'),
              tools.FormText(tools.MustTitle('开始行数'), 'redetail.beginrow', mep + 'beginrow', 8, '96%', false, 4, 'isnumber'),
              tools.FormText(tools.MustTitle('开始列数'), 'redetail.begincolumn', mep + 'begincolumn', 8, '96%', false, 7, 'isnumber'),
              tools.FormText('所属类编号', 'redetail.classsource', mep + 'classsource', 20, '96%', true, 10),
              tools.FormText('显示数据', 'redetail.celltext', mep + 'celltext', 200, '96%', true, 13),
              tools.FormCombo(tools.MustTitle('对齐方式'), 'redetail.aligntype', mep + 'aligntype', tools.ComboStore('AlignType', gpersist.SELECT_MUST_VALUE), '96%', false, 16),
              { xtype: 'fieldcontainer', fieldLabel: '', layout: 'hbox', defaults: { labelStyle: 'font-weight:bold;', margins: '0 15 0 0' }, items: [
                tools.FormCheck(tools.MustTitle('是否有线框'), 'redetail.isborder', mep + 'isborder', false, 19),
                tools.FormCheck(tools.MustTitle('是否有下划线'), 'redetail.isline', mep + 'isline', false, 20),
                tools.FormCheck(tools.MustTitle('是否加粗'), 'redetail.isbold', mep + 'isbold', false, 21),
                tools.FormCheck(tools.MustTitle('多点检测'), 'redetail.ismutil', mep + 'ismutil', false, 22)]}
            ]},
            {xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText(tools.MustTitle('项目名称'), 'redetail.cellname', mep + 'cellname', 200, '96%', false, 2),
              tools.FormText(tools.MustTitle('结束行数'), 'redetail.endrow', mep + 'endrow', 8, '96%', false, 5, 'isnumber'),
              tools.FormText(tools.MustTitle('结束列数'), 'redetail.endcolumn', mep + 'endcolumn', 8, '96%', false, 8, 'isnumber'),
              tools.FormText(tools.MustTitle('组号'), 'redetail.groupserial', mep + 'groupserial', 8, '96%', false, 11, 'isnumber'),
              tools.FormText('显示格式', 'redetail.cellformat', mep + 'cellformat', 20, '96%', true, 14),
              tools.FormText('前缀', 'redetail.prefixtext', mep + 'prefixtext', 10, '96%', true, 17),
              tools.FormCombo(tools.MustTitle('展示字体'), 'redetail.fonttype', mep + 'fonttype', tools.ComboStore('FontType', gpersist.SELECT_MUST_VALUE), '96%', false, 16)
            ]},
            {xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('字段编号', 'redetail.fieldcode', mep + 'fieldcode', 20, '100%', true, 3),
              tools.FormCombo(tools.MustTitle('数据来源'), 'redetail.valuesource', mep + 'valuesource', tools.ComboStore('ValueSource', gpersist.SELECT_MUST_VALUE), '100%', false, 6),
              tools.FormCombo(tools.MustTitle('数据类型'), 'redetail.valuetype', mep + 'valuetype', tools.ComboStore('ValueType', gpersist.SELECT_MUST_VALUE), '100%', false, 9),
              tools.FormText(tools.MustTitle('试件号'), 'redetail.specserial', mep + 'specserial', 8, '100%', false, 12, 'isnumber'),
              tools.FormText(tools.MustTitle('字体大小'), 'redetail.fontsize', mep + 'fontsize', 8, '100%', false, 15, 'isnumber'), 
              tools.FormText('后缀', 'redetail.postfixtext', mep + 'postfixtext', 10, '100%', true, 18)
              
            ]}
          ]}, 
          {xtype:'hiddenfield',name:'redetail.formserial',id: mep + 'formserial'}
       ];

        me.disDetailControls = ['cellserial'];
        me.enDetailControls = [ 'beginrow', 'endrow', 'begincolumn', 'endcolumn', 'cellname', 'valuesource', 
          'valuetype', 'classsource', 'fieldcode', 'groupserial', 'specserial', 'celltext', 'cellformat', 'isborder', 'isline', 
          'isbold', 'ismutil', 'fontsize', 'aligntype','fonttype', 'prefixtext', 'postfixtext','formserial'];
      },
      
      OnLoadDetailData: function (item) {
        var me = this;
        var mep = me.tranPrefix;
        tools.SetValue(mep + 'cellserial', item.cellserial);
        tools.SetValue(mep + 'formserial', item.formserial);
        tools.SetValue(mep + 'beginrow', item.beginrow);
        tools.SetValue(mep + 'endrow', item.endrow);
        tools.SetValue(mep + 'begincolumn', item.begincolumn);
        tools.SetValue(mep + 'endcolumn', item.endcolumn);
        tools.SetValue(mep + 'cellname', item.cellname);
        tools.SetValue(mep + 'valuesource', item.valuesource);
        tools.SetValue(mep + 'valuetype', item.valuetype);
        tools.SetValue(mep + 'classsource', item.classsource);
        tools.SetValue(mep + 'fieldcode', item.fieldcode);
        tools.SetValue(mep + 'groupserial', item.groupserial);
        tools.SetValue(mep + 'specserial', item.specserial);
        tools.SetValue(mep + 'celltext', item.celltext);
        tools.SetValue(mep + 'cellformat', item.cellformat);
        tools.SetValue(mep + 'isborder', item.isborder);
        tools.SetValue(mep + 'isline', item.isline);
        tools.SetValue(mep + 'isbold', item.isbold);
        tools.SetValue(mep + 'fontsize', item.fontsize);
        tools.SetValue(mep + 'aligntype', item.aligntype);
        tools.SetValue(mep + 'fonttype', item.fonttype);
        tools.SetValue(mep + 'prefixtext', item.prefixtext);
        tools.SetValue(mep + 'postfixtext', item.postfixtext);
        tools.SetValue(mep + 'ismutil', item.ismutil);
      },
      
      OnBeforeListSave: function (record) {
        var me = this;
        var mep = me.tranPrefix;
        record.data.cellserial = tools.GetValue(mep + 'cellserial');
        record.data.formserial = tools.GetValue(mep + 'formserial');
        record.data.beginrow = tools.GetValue(mep + 'beginrow');
        record.data.endrow = tools.GetValue(mep + 'endrow');
        record.data.begincolumn = tools.GetValue(mep + 'begincolumn');
        record.data.endcolumn = tools.GetValue(mep + 'endcolumn');
        record.data.cellname = tools.GetValue(mep + 'cellname');
        record.data.valuesource = tools.GetValue(mep + 'valuesource');
        record.data.valuesourcename = Ext.getCmp(mep + 'valuesource').getDisplayValue();
        record.data.valuetype = tools.GetValue(mep + 'valuetype');
        record.data.valuetypename = Ext.getCmp(mep + 'valuetype').getDisplayValue();
        record.data.classsource = tools.GetValue(mep + 'classsource');
        record.data.fieldcode = tools.GetValue(mep + 'fieldcode');
        record.data.groupserial = tools.GetValue(mep + 'groupserial');
        record.data.specserial = tools.GetValue(mep + 'specserial');
        record.data.celltext = tools.GetValue(mep + 'celltext');
        record.data.cellformat = tools.GetValue(mep + 'cellformat');
        record.data.isborder = tools.GetValue(mep + 'isborder');
        record.data.isline = tools.GetValue(mep + 'isline');
        record.data.isbold = tools.GetValue(mep + 'isbold');
        record.data.fontsize = tools.GetValue(mep + 'fontsize');
        record.data.aligntype = tools.GetValue(mep + 'aligntype');
        record.data.aligntypename = Ext.getCmp(mep + 'aligntype').getDisplayValue();
        record.data.fonttype = tools.GetValue(mep + 'fonttype');
        record.data.fonttypename = Ext.getCmp(mep + 'fonttype').getDisplayValue();
        record.data.prefixtext = tools.GetValue(mep + 'prefixtext');
        record.data.postfixtext = tools.GetValue(mep + 'postfixtext');
        record.data.ismutil = tools.GetValue(mep + 'ismutil');
      },
      
      OnDetailRefresh: function () {
        var me = this;
        var mep = me.tranPrefix;
        
        if (me.plDetailGrid && me.plDetailGrid.store) {
          me.plDetailGrid.store.on('beforeload', function (store, options) {      
            Ext.apply(store.proxy.extraParams, {
              'breport.reportid': tools.GetValue(mep + 'reportid')
            });
          });
        }
        
        me.callParent();
      },
      
      
      
      OnAfterCreateEditToolBar: function () {
        var me = this;
        var mep = me.tranPrefix;
        
        me.editToolItems = [
          ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },      
          '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
          '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
        ];
        
        if (me.hasPrevNext) {
          Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
            '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
            ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
          ]);
        }
      },
      
      
      OnSetData: function (id, record) {
        var me = this;
        var mep = me.tranPrefix;
        
        var item = tools.JsonGet(tools.GetUrl('DatGetBusReport.do?breport.reportid=') + id);
        console.log(item)
        
        if (item && !Ext.isEmpty(item.reportid)) {
          tools.SetValue(mep + 'reportid', item.reportid);
          tools.SetValue(mep + 'sampleid', item.sampleid);
          
          tools.SetValue(mep + 'prefixtext', item.onespacing);
          tools.SetValue(mep + 'postfixtext', item.twospacing);
          
          me.OnDetailRefresh();
          return true;
        }
        else {
          me.dataDeal == gpersist.DATA_DEAL_SELECT;
          tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
        }
      }
      
      
      
    });