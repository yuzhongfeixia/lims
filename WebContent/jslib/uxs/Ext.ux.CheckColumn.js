/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

 */
/**
 * @class Ext.ux.CheckColumn
 * @extends Ext.grid.column.Column
 * <p>A Header subclass which renders a checkbox in each column cell which toggles the truthiness of the associated data field on click.</p>
 * <p><b>Note. As of ExtJS 3.3 this no longer has to be configured as a plugin of the GridPanel.</b></p>
 * <p>Example usage:</p>
 * <pre><code>
 // create the grid
 var grid = Ext.create('Ext.grid.Panel', {
 ...
 columns: [{
 text: 'Foo',
 ...
 },{
 xtype: 'checkcolumn',
 text: 'Indoor?',
 dataIndex: 'indoor',
 width: 55
 }
 ]
 ...
 });
 * </code></pre>
 * In addition to toggling a Boolean value within the record data, this
 * class adds or removes a css class <tt>'x-grid-checked'</tt> on the td
 * based on whether or not it is checked to alter the background image used
 * for a column.
 */
Ext.define('Ext.ux.CheckColumn', {
  extend : 'Ext.grid.column.Column',
  alias : 'widget.checkcolumn',
  
  isCancel : true,
  
  stopSelection : true,
  
  tdCls : Ext.baseCSSPrefix + 'grid-cell-checkcolumn',
  
  constructor : function() {
    this.addEvents(
    /**
     * @event beforecheckchange
     * Fires when before checked state of a row changes.
     * The change may be vetoed by returning `false` from a listener.
     * @param {Ext.ux.CheckColumn} this CheckColumn
     * @param {Number} rowIndex The row index
     * @param {Boolean} checked True if the box is to be checked
     */
    'beforecheckchange',
    /**
     * @event checkchange
     * Fires when the checked state of a row changes
     * @param {Ext.ux.CheckColumn} this CheckColumn
     * @param {Number} rowIndex The row index
     * @param {Boolean} checked True if the box is now checked
     */
    'checkchange');
    this.callParent(arguments);
  },
  
  /**
   * @private
   * Process and refire events routed from the GridView's processEvent method.
   */
  processEvent : function(type, view, cell, recordIndex, cellIndex, e) {
    var me = this, key = type === 'keydown' && e.getKey(), mousedown = type == 'mousedown';
    
    if (mousedown || (key == e.ENTER || key == e.SPACE)) {
      var record = view.panel.store.getAt(recordIndex), dataIndex = me.dataIndex, checked = !record.get(dataIndex);
      
      // Allow apps to hook beforecheckchange
      if (me.fireEvent('beforecheck', me, recordIndex, checked) !== false) {
        if (this.isCancel)
          return false;
        
        record.set(dataIndex, checked);
        me.fireEvent('checkchange', me, recordIndex, checked);
        
        if (checked && (dataIndex == 'CheckNum')) {
          view.panel.selModel.selected.add(recordIndex, record);
        } else
          view.panel.selModel.selected.remove(record);
        
        // Mousedown on the now nonexistent cell causes the view to blur, so stop it continuing.
        if (mousedown) {
          e.stopEvent();
        }
        
        // Selection will not proceed after this because of the DOM update caused by the record modification
        // Invoke the SelectionModel unless configured not to do so
        if (!me.stopSelection) {
          view.selModel.selectByPosition({
            row : recordIndex,
            column : cellIndex
          });
        }
        
        // Prevent the view from propagating the event to the selection model - we have done that job.
        return false;
      } else {
        // Prevent the view from propagating the event to the selection model if configured to do so.
        return !me.stopSelection;
      }
    } else {
      return me.callParent(arguments);
    }
  },
  
  // Note: class names are not placed on the prototype bc renderer scope
  // is not in the header.
  renderer : function(value) {
    var cssPrefix = Ext.baseCSSPrefix, cls = [
      cssPrefix + 'grid-checkheader'];
    
    if (value) {
      cls.push(cssPrefix + 'grid-checkheader-checked');
    }

    return '<div class="' + cls.join(' ') + '">&#160;</div>';
  }
});
