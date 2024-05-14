Ext.define('gpersist.base.stkform', {
  extend: 'gpersist.base.listdetailform',

  ismodify: false,
  hasPrevNextDay: true,
  idBeginDate: 'searchbegindate',
  idEndDate: 'searchenddate',
  getUrl: '',
  
  OnInitConfig : function() {
    var me = this;
    
    me.callParent(arguments);
    
    me.hasPrevNextDay = true;
    me.idBeginDate = 'searchbegindate';
    me.idEndDate = 'searchenddate';
    me.getUrl = '';
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      ' ', { id: mep + 'btnDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnDelete,scope: me},
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    if (me.hasPrevNextDay) {
      Ext.Array.insert(items, items.length - 2, [' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
        ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me }, '-']);
    }
    
    if (!me.hasOtherSearch)
      Ext.Array.insert(items, 0, [' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }, '-']);
        
    if (me.hasPage)
      Ext.Array.insert(items, items.length - 2, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX}]);
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
    
  OnPrevSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, -1));
    tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, -1));

    me.OnSearch();
  }, 
  
  OnNextSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + me.idBeginDate, Ext.Date.add(tools.GetValue(mep + me.idBeginDate), Ext.Date.DAY, 1));
    tools.SetValue(mep + me.idEndDate, Ext.Date.add(tools.GetValue(mep + me.idEndDate), Ext.Date.DAY, 1));

    me.OnSearch();
  },
  
  OnSearchDateCheck: function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.GetValue(mep + me.idBeginDate) > tools.GetValue(mep + me.idEndDate)) {
      tools.alert('开始日期不能大于结束日期！');
      return true;
    }
    
    return false;
  },

  OnGetDetailFunction: function () {
    var me = this;

    me.detailFuncs.add('parender', function parender(value, metaData, record, rowIdx, colIdx, store, view) {
      return tools.CnMoney(record.get('prdnumber') * record.get('prdprice'), true);
    });

    me.detailFuncs.add('pastype', function pastype(records) {
      var i = 0, length = records.length, total = 0, record;
      for (; i < length; ++i) {
        record = records[i];
        total += record.get('prdnumber') * record.get('prdprice');
      }
      return total;
    });

    me.detailFuncs.add('pasrender', function pasrender(value, summaryData, dataIndex) {
      return '合计: ' + tools.CnMoney(value, true);
    });
  },

  OnBeforeCreateDetail: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.each(me.columnDetails, function (col) {      
      if (col.dataIndex == 'prdid') {
       
        col.editor = {
          xtype: 'prdpicker', name: mep + 'selectprdid', id: mep + 'selectprdid', winTitle: '选择耗材',
          maxLength: 8, maxLengthText: '耗材编号长度不能超过8个字符！', selectOnFocus: false,
          blankText: '耗材编号不能为空！', allowBlank: false, editable: false, 
          columnUrl: 'SysSqlColumn.do?sqlid=p_select_stkprd',
          storeUrl: 'PrdSearchBasReagent.do', prefix: mep,
          
          listeners: {
            
            'griditemclick': { fn: me.OnPrdSelect, scope: me }
          }
        };
      }
    });    
  },
  
  OnPrdSelect: function (render, items) {
    var me = this;

    if (items && items.length > 0) {
    
      if (me.cellEditing) {
        var now = me.cellEditing.getActiveRecord();
       

        if (now) {
          me.cellEditing.getActiveEditor().setValue(Ext.util.Format.trim(items[0].prdid));
          
          now.data.prdname = Ext.util.Format.trim(items[0].prdname);
          
          now.data.prdprice = items[0].prdprice;
          now.data.prdnumber = 1;
        }
        
        for (var i = 1; i < items.length; i++) {
          var record = me.plDetailGrid.store.model.create({});
          
          record.data.prdid = items[i].prdid;
          record.data.prdname = items[i].prdname;
         
          record.data.prdprice = items[i].prdprice;
          record.data.prdnumber = 1;
          
          me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
        }
        
        me.plDetailGrid.getView().refresh();
      }
    }
    
  },  

  OnLoadData: function (record) {
    var me = this;

    if (record && !Ext.isEmpty(record.tranid)) {
      var item = tools.JsonGet(tools.GetUrl(me.getUrl) + record.tranid);
      
      if (item && !Ext.isEmpty(item.tranid)) {
        me.OnSetData(item);
        me.OnDetailRefresh();
  
        if (item.ismodify) {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT);
          tools.alert('该' + me.editInfo + '已经锁定，不能被修改！');
        }
        return true;
      }
      else
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
    else
      tools.alert(Ext.String.format(gpersist.STR_FMT_NOLOAD, me.editInfo));

    return false;
  },

  OnCheckPrevNext: function (item) {
    var me = this;
    var mep = me.tranPrefix;

    return (item.tranid == tools.GetValue(mep + 'tranid'));
  },

  OnFormEdit: function () {
    var me = this;
    var mep = this.tranPrefix;

    var item = tools.JsonGet(tools.GetUrl(me.getUrl) + tools.GetValue(mep + 'tranid'));
    
    if (item && !Ext.isEmpty(item.tranid)) {
      if (item.ismodify) {
        tools.alert('该' + me.editInfo + '已经锁定，不能被修改！');
        return;
      }
    }
    
    me.callParent();
  },

  OnBeforeSave: function () {
    var me = this;
    var mep = this.tranPrefix;

    if ((me.dataDeal == gpersist.DATA_DEAL_EDIT) && Ext.isEmpty(tools.GetValue(mep + 'tranid'))) {
      tools.alert('业务编号为空，不能执行修改操作！');
      return false;
    }

    tools.SetValue(mep + 'datadeal', me.dataDeal);
    
    var json = [];
    for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {

      me.plDetailGrid.store.getAt(i).data.prdamount = tools.CnMoney(me.plDetailGrid.store.getAt(i).data.prdnumber * me.plDetailGrid.store.getAt(i).data.prdprice, false);

      json.push(me.plDetailGrid.store.getAt(i).data);
    }

    me.saveParams = { details: Ext.encode(json) };

    return true;
  },

  OnAfterSave: function (action) {
    var me = this;
    var mep = this.tranPrefix;

    if ((me.dataDeal == gpersist.DATA_DEAL_NEW) && action.result && action.result.data) {      
      tools.SetValue(mep + 'tranid', action.result.data.tranid);      
    }
  }
});