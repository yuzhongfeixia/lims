Ext.define('Ext.ux.layout.component.BoundList', {
  extend : 'Ext.layout.component.BoundList',
  alias : 'layout.boundlist-checkbox',
  
  beginLayout : function(ownerContext) {
    this.callParent(arguments);
    ownerContext.listContext = ownerContext.getEl('outerEl');
  },
  
  measureContentHeight : function(ownerContext) {
    return this.owner.outerEl.getHeight();
  }
});

Ext.define('Ext.ux.CheckCombo', {
  extend : 'Ext.form.field.ComboBox',
  alias : 'widget.checkcombo',
  multiSelect : true,
  addAllSelector : false,
  allText : 'All',
  delim : ',',
  isSelectAll : false,
  
  initComponent : function() {
    this.listConfig = this.listConfig || {};
    var me = this;
    Ext.apply(this.listConfig, {
      tpl : [
        '<ul><tpl for=".">','<li role="option" class="' + Ext.baseCSSPrefix + 'boundlist-item"><span class="' + Ext.baseCSSPrefix + 'combo-checker">&nbsp;</span> {' + this.displayField + '}</li>','</tpl></ul>'],
      childEls : [
        'listEl','outerEl','checkAllEl'],
      renderTpl : [
        '<div id="{id}-outerEl" style="overflow:auto" width=auto;>',(this.addAllSelector ? '<div id="{id}-checkAllEl" class="' + Ext.baseCSSPrefix + 'boundlist-item" role="option"><span class="' + Ext.baseCSSPrefix + 'combo-checker">&nbsp;</span> ' + this.allText + '</div>' : ''),'<div id="{id}-listEl" class="{baseCls}-list-ct"></div>','{%','var me=values.$comp, pagingToolbar=me.pagingToolbar;','if (pagingToolbar) {','pagingToolbar.ownerLayout = me.componentLayout;','Ext.DomHelper.generateMarkup(pagingToolbar.getRenderTree(), out);','}','%}','</div>',{
          disableFormats : true
        }],
      componentLayout : 'boundlist-checkbox',
      onDestroy : function() {
        Ext.destroyMembers(this, 'pagingToolbar', 'outerEl', 'listEl');
        this.callParent();
      }
    });
    
    this.callParent(arguments);
  },
  
  unClear : function() {
    var me = this;
    
    me.setValue('');
    
    if (me.addAllSelector == true && me.checkAllEl) {
      if (me.allSelector.hasCls('x-boundlist-selected')) {
        me.allSelector.removeCls('x-boundlist-selected');
      }
    }
  },
  
  createPicker : function() {
    var me = this;
    var picker = this.callParent(arguments);
    picker.on('render', function(picker) {
      if (picker.checkAllEl && me.addAllSelector == true) {
        picker.checkAllEl.addClsOnOver(Ext.baseCSSPrefix + 'boundlist-item-over');
        
        picker.checkAllEl.on('click', function(e) {
          if (picker.checkAllEl.hasCls(Ext.baseCSSPrefix + 'boundlist-selected')) {
            this.setValue('');
            picker.checkAllEl.removeCls(Ext.baseCSSPrefix + 'boundlist-selected');
            this.fireEvent('select', this, []);
          } else {
            var records = [];
            this.store.each(function(record) {
              records.push(record);
            });
            this.select(records);
            picker.checkAllEl.addCls(Ext.baseCSSPrefix + 'boundlist-selected');
            this.fireEvent('select', this, records);
          }
        }, this);
      }
    }, this, {
      single : true
    });
    
    me.mon(picker, {
      itemclick : me.onItemClick,
      refresh : me.onListRefresh,
      scope : me
    });
    
    me.mon(picker.getSelectionModel(), {
      'beforeselect' : me.onBeforeSelect,
      'beforedeselect' : me.onBeforeDeselect,
      'selectionchange' : me.onListSelectionChange,
      scope : me
    });
    
    return picker;
  },
  
  getValue : function() {
    return this.value.join(this.delim);
  },
  
  selectAll : function() {
    var me = this;
    
    var picker = me.getPicker();
    
    if (picker) {
      var records = [];
      this.store.each(function(record) {
        records.push(record);
      });
      this.select(records);
      this.fireEvent('select', this, records);
      me.isSelectAll = true;
    }
  },
  
  getValueAsArray : function() {
    return this.value;
  },
  
  getSubmitValue : function() {
    return this.getValue();
  },
  
  onListSelectionChange : function(list, selectedRecords) {
    this.callParent(arguments);
    
    var checker = this.getPicker().checkAllEl;
    if (checker) {
      if (selectedRecords.length == this.store.getCount())
        checker.addCls(Ext.baseCSSPrefix + 'boundlist-selected');
      else
        checker.removeCls(Ext.baseCSSPrefix + 'boundlist-selected');
    }
  }
});