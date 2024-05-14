Ext.define('gpersist.base.basepicker', {
  extend: "Ext.form.field.Picker",
  alias: 'widget.basepicker',
  
  triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
  
  displayField: null,
  
  valueField: null,
  
  matchFieldWidth: false,
  
  store: null,
  
  columns: [],
  
  fileds: [],
  
  pickerWidth: 400,
  
  pickerHeight: 300,
  
  editable: true,
  
  hasPage: true,
  
  columnUrl: '',
  
  storeUrl: '',
  
  pageSize: 40,
  
  searchTools: [],
  
  hasSelect: false,
  
  hasChild: false,
  
  multiToolBar: false,
  
  winTitle: '',
  
  includeTran: false,
  
  prickergrid: null,
  
  prefix: '',
  
  selectRecords: [],
  hasDelete: false,
  keyname: '',
  
  initComponent: function() {
    
    this.callParent();
    
    this.addEvents(
      'griditemclick', 
      'gridbeforeload',
      'valuevalid'
    );    
  },
  
  initEvents : function(){
    var me = this,
      el = me.inputEl;
   
    this.callParent();
    
    me.mon(el, {
      scope: me,
      keypress: me.onKeyPress
    }); 
    
    me.on('specialkey', me.onSpecialKey, me);
    me.on('blur', me.onBlurHandler, me);
  },
  
  onSpecialKey: function(field, e) {
    var me = this;

    if ((e.getKey() == e.DELETE) || (e.getKey() == e.BACKSPACE))
      me.hasSelect = false;
  },
  
  onKeyPress: function() {
    var me = this;

    me.hasSelect = false;
  },
  
  onBlurHandler: function() {
    var me = this;

    if (!me.hasSelect)
      me.fireEvent('valuevalid', me);
  },
  
  createPicker: function() {
    var me = this, picker = me.createComponent();    
    
    return picker;
  },
  
  createGridColumns: function() {
    var me = this;
    
    me.columns = [];
    me.fields = [];
    
    var numberer = new Ext.grid.RowNumberer({
      header: '序号',
      dataIndex: 'IndexNum',
      locked: false,
      width: 40,
      sortable: false
    });
    
    me.columns.push(numberer);
    
    Ext.Ajax.request({
      url: me.columnUrl,
      scope: this,
      async: false,
      success: function (response, opts) {
        var items = Ext.decode(response.responseText);
        var col;
        var field;
        var xtype = 'Ext.grid.Column';
        
        Ext.each(items, function (item, index) {
          if (item.isdisplay) {
            switch (item.colext) {
              case 'bool':
                xtype = 'Ext.ux.CheckColumn';
                break;

              default:
                xtype = 'Ext.grid.Column';
            }
            
            col = Ext.create(xtype, {
              header: item.colname,
              dataIndex: item.colcode,
              sortable: item.isorder,
              locked: item.islock,
              align: 'left',
              width: item.colwidth
            });
            
            if (item.colformat != '') {
              switch (item.colext) {
                case 'float':
                  col.renderer = Ext.util.Format.numberRenderer(item.colformat);
                  break;
                case 'date':
                  col.renderer = Ext.util.Format.dateRenderer(item.colformat);
                  break;
              }
            }

            me.columns.push(col);
          }
          
          field = new Ext.data.Field({
            name: item.colcode,
            mapping: item.colcode,
            type: item.colext
          });

          me.fields.push(field);
        });        
      },
      failure: function () { }
    });        
  },
  
  createGridStore: function () {
    var me = this;
    var size = me.pageSize || 20;

    me.store = new Ext.data.JsonStore({
      proxy: {
        type: 'ajax',
        url: me.storeUrl,
        timeout: 180000,
        reader: {
          type: 'json',
          root: 'data',
          totalProperty: 'total'
        }
      },
      autoLoad: false,
      sortOnLoad: true,
      fields: me.fields,
      pageSize: size
    });
  },
  
  createComponent: function() {
    var me = this;
    
    me.createGridColumns();
    me.createGridStore();
    
    var tbgrid = Ext.create('Ext.container.Container', {
      listeners: {
        afterRender: function (form, options) {
          this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
            enter: me.onGridLoad,
            scope: me
          });
        }
      }
    });
    
    //tbgrid.add(Ext.create('Ext.toolbar.Toolbar', {items: items}));
    
    me.prickergrid = Ext.create("Ext.grid.Panel", {
      floating : false, store: me.store, columns : me.columns, region: 'center', margins: '0 0 0 0', padding: '0 0 0 0', loadMask: false,
      columnLines: true, viewConfig: { autoFill: true, stripeRows: true }, border: false, 
      tbar: tbgrid,
      selModel: new Ext.selection.CheckboxModel({ injectCheckbox: 0, mode: 'MULTI', checkOnly: false,
        listeners: {
          'select': {fn: function (select, record, index) {
            me.onSelectRecord(record);
          }, scope:me},
          'deselect': {fn: function (select, record, index) {
            me.onDeSelectRecord(record);
          }, scope:me},
          'beforedeselectall': {fn: function (select, record, index) {
            //me.hasDelete = false;
          }, scope:me}
        }
      }),
      bbar: Ext.create('Ext.PagingToolbar', {store: me.store, displayInfo: true })
    });
    
    me.prickergrid.on("itemdblclick", me.onItemClick, me);   
    me.store.on('load', me.onGridSelectRecord, me);
    
    var pmain = Ext.create('Ext.Panel', {
      frame: false, autoScroll : false, region: 'center', border: true,
      items:[me.prickergrid], layout:'border', margins: '2 2 2 5', padding: '0 0 0 0'
    });
    
    var pickerwin = Ext.create('Ext.Window', {
      title: me.winTitle, width: me.pickerWidth, height: pickerHeight, maximizable: true, closeAction: 'hide', modal: true,
      layout: {type: 'border',padding: '0 0 0 0'}, plain: false, closable: true, draggable: true, floating : true,
      items : [pmain]
    });
    
    me.selectRecords = [];
    
    return pickerwin;
  },
  
  onSelectRecord: function (record) {
    var me = this;
    
    for (var j = 0; j < me.selectRecords.length; j++) {
      if (eval('me.selectRecords[j].' + me.keyname) == eval('record.data.' + me.keyname)) 
        return;
    }
    
    me.selectRecords.push(record.data);
  },
  
  onDeSelectRecord: function (record) {
    var me = this;

    if (!me.hasDelete) return;
    
    for (var j = 0; j < me.selectRecords.length; j++) {
      if (eval('me.selectRecords[j].' + me.keyname) == eval('record.data.' + me.keyname)) {
        Ext.Array.remove(me.selectRecords, me.selectRecords[j]);
        return;
      }
    }
  },
  
  onItemClick: function(view, record, item, index, e, eOpts ) {
    var me = this;
    me.setValue(record.get(me.valueField));
    
    me.fireEvent('griditemclick', me, [record.data]);
    me.getPicker().hide();
    me.isExpanded = false;
    me.inputEl.focus();
    
    me.hasSelect = true;
  },
  
  onUserSelected: function () {
    var me = this;
    
    if (me.selectRecords.length > 0) {
      me.fireEvent('griditemclick', me, me.selectRecords);
      me.getPicker().hide();
      me.isExpanded = false;
      me.inputEl.focus();
      
      me.hasSelect = true;
    }
  },
  
  onGridSelectRecord: function () {
    var me = this;
        
    me.hasDelete = true;

    for (var i = 0; i < me.store.getCount(); i++) {
      for (var j = 0; j < me.selectRecords.length; j++) {
        if (eval('me.selectRecords[j].' + me.keyname) == eval('me.store.getAt(i).data.' + me.keyname)) {
          me.prickergrid.getSelectionModel().select(me.store.getAt(i), true);
          break;
        }
      }
    }
  },
  
  onGridLoad: function () {
    var me = this;
    
    if (me.fireEvent('gridbeforeload', me, me.store) !== false) {      
      if (me.store) {
        if (me.hasPage) 
          me.store.loadPage(1);
        else
          me.store.load();
      }
    }    
  },
  
  withChildPicker: function(e) {
    var withChild = false;
    
    if( this.picker.rendered ){
      var fields = this.picker.query('pickerfield');
    
      if(fields) {
        Ext.Array.each(fields, function(combo) {
          var picker = combo.getPicker();
          
          if(picker && picker.rendered && e.within(picker.el) ){
            withChild = true;
            return;
          }
        });
      }
    }
    
    return withChild;
  },
    
  mimicBlur: function(e) {
    if (this.hasChild) {
      this.hasChild = false;
      return;
    }
    this.callParent(arguments);
  },
  
  collapseIf: function(e) {
    var i = 0;
    
    var combos = this.picker.query('combobox');   
    
    if (combos && Ext.isArray(combos)) {
      for (i = 0; i < combos.length; i++) {
        var picker = combos[i].getPicker();
        
        if (picker.rendered && e.within(picker.el)) {
          this.hasChild = true;
          return;
        } 
      }
    }
    
    var dates = this.picker.query('datefield');   
    
    if (dates && Ext.isArray(dates)) {
      for (i = 0; i < dates.length; i++) {
        var picker = dates[i].getPicker();
        
        if (picker.rendered && e.within(picker.el)) {
          this.hasChild = true;
          return;
        } 
      }
    }    
    
    this.callParent(arguments);
  },
  
  onExpand: function () {
    this.onGridLoad();
  },
  
  getHasSelect: function () {
    var me = this;
    
    return me.hasSelect;
  },
  
  getValue: function() {
    return this.getRawValue();   
  },
  
  getSubmitValue: function(){
    return this.getRawValue();    
  },
  
  alignPicker: function() {
    var me = this,
        picker = me.getPicker();
    
    return;
    if (me.isExpanded) {
        if (me.matchFieldWidth) {
            // Auto the height (it will be constrained by min and max width) unless there are no records to display.
            picker.setWidth(me.bodyEl.getWidth());
        }
        if (picker.isFloating()) {
            me.doAlign();
        }
    }
  },
  
  doAlign: function(){
    var me = this,
        picker = me.picker,
        aboveSfx = '-above',
        isAbove;

    me.picker.alignTo(me.inputEl, me.pickerAlign, me.pickerOffset);
    // add the {openCls}-above class if the picker was aligned above
    // the field due to hitting the bottom of the viewport
    isAbove = picker.el.getY() < me.inputEl.getY();
    me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls + aboveSfx);
    picker[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
  }
});