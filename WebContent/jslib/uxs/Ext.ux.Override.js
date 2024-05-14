//处理回退键
Ext.EventManager.on((Ext.isIE ? document : window), 'keydown', function (e, t) {
  if (e.getKey() == e.BACKSPACE && ((!/^input$/i.test(t.tagName) && !/^textarea/i.test(t.tagName)) || t.disabled || t.readOnly)) {
    e.stopEvent();
  }
});

//GRID 可复制
if(typeof Ext != 'undefined'){
  Ext.core.Element.prototype.unselectable = function(){return this;};  
  Ext.view.TableChunker.metaRowTpl = [
    '<tr class="' + Ext.baseCSSPrefix + 'grid-row {addlSelector} {[this.embedRowCls()]}" {[this.embedRowAttr()]}>',
    '<tpl for="columns">',
    '<td class="{cls} ' + Ext.baseCSSPrefix + 'grid-cell ' + Ext.baseCSSPrefix + 'grid-cell-{columnId} {{id}-modified} {{id}-tdCls} {[this.firstOrLastCls(xindex, xcount)]}" {{id}-tdAttr}><div class="' + Ext.baseCSSPrefix + 'grid-cell-inner ' + Ext.baseCSSPrefix + 'unselectable" style="{{id}-style}; text-align: {align};">{{id}}</div></td>',
    '</tpl>',
    '</tr>'
  ];
}

Ext.override(Ext.toolbar.Paging, {
  getPageData : function(){
    var store = this.store,
    totalCount = store.getTotalCount();

    return {
        total : totalCount,
        currentPage : store.currentPage,
        pageCount: Math.ceil(totalCount / store.pageSize),
        fromRecord: ((store.currentPage - 1) * store.pageSize) + 1,
        toRecord: Math.min(store.currentPage * store.pageSize, totalCount)
    
    };
  }
});

Ext.override(Ext.layout.component.field.Field, {
  beginLayout: function(ownerContext) {
    var me = this,
    owner = me.owner,
    widthModel = ownerContext.widthModel,
    width;
    
    me.callParent(arguments);
    
    ownerContext.labelStrategy = me.getLabelStrategy();
    ownerContext.errorStrategy = me.getErrorStrategy();
    
    ownerContext.labelContext = ownerContext.getEl('labelEl');
    ownerContext.bodyCellContext = ownerContext.getEl('bodyEl');
    ownerContext.inputContext = ownerContext.getEl('inputEl');
    ownerContext.errorContext = ownerContext.getEl('errorEl');
    ownerContext.inputRow = ownerContext.getEl('inputRow');
    
    // width:100% on an element inside a table in IE6/7 "strict" sizes the content box.
    // store the input element's border and padding info so that subclasses can take it into consideration if needed
    if ((Ext.isIE6 || Ext.isIE7) && Ext.isStrict && ownerContext.inputContext) {
      me.ieInputWidthAdjustment = ownerContext.inputContext.getPaddingInfo().width + ownerContext.inputContext.getBorderInfo().width;
    }

    // perform preparation on the label and error (setting css classes, qtips, etc.)
    ownerContext.labelStrategy.prepare(ownerContext, owner);
    ownerContext.errorStrategy.prepare(ownerContext, owner);
    
    // Body cell must stretch to use up available width unless the field is auto width
    if (widthModel.shrinkWrap) {
      // When the width needs to be auto, table-layout cannot be fixed
      me.beginLayoutShrinkWrap(ownerContext);
    } else if (widthModel.natural) {
      
      // When a size specified, natural becomes fixed width
      if (typeof owner.size == 'number') {
        me.beginLayoutFixed(ownerContext, (width = owner.size * 6.5 + 20), 'px');
      }
      
      // Otherwise it is the same as shrinkWrap
      else {
        me.beginLayoutShrinkWrap(ownerContext);
      }
      ownerContext.setWidth(width, false);
    } else {
      me.beginLayoutFixed(ownerContext, '100', '%');
    }
  },

  beginLayoutShrinkWrap: function (ownerContext) {
    var owner = ownerContext.target;
    
    if (owner.inputEl && owner.inputEl.dom) {
      owner.inputEl.dom.removeAttribute('size');
    }
    
    owner.el.setStyle('table-layout', 'auto');
    owner.bodyEl.setStyle('width', '');
  }
});

Ext.override(Ext.window.MessageBox, {
  initComponent: function() {
    var me = this,
    baseId = me.id,
    i, button,
    tbLayout;
    
    me.title = '&#160;';
    
    me.topContainer = new Ext.container.Container({
      layout: 'hbox',
      style: {
        padding: '10px',
        overflow: 'hidden'
      },
      items: [
              me.iconComponent = new Ext.Component({
                cls: me.baseCls + '-icon',
                width: 50,
                height: me.iconHeight
              }),
            me.promptContainer = new Ext.container.Container({
                flex: 1,
                layout: {
                    type: 'anchor'
                },
                items: [
                    me.msg = new Ext.form.field.Display({
                        id: baseId + '-displayfield',
                        cls: me.baseCls + '-text'
                    }),
                    me.textField = new Ext.form.field.Text({
                        id: baseId + '-testfield',
                        anchor: '100%',
                        enableKeyEvents: true,
                        listeners: {
                            keydown: me.onPromptKey,
                            scope: me
                        }
                    }),
                    me.textArea = new Ext.form.field.TextArea({
                        id: baseId + '-textarea',
                        anchor: '100%',
                        height: 75
                    })
                ]
            })
        ]
    });
    
    me.progressBar = new Ext.ProgressBar({
      id: baseId + '-progressbar',
      margins: '0 10 0 10'
    });
    
    me.items = [me.topContainer, me.progressBar];
    
    // Create the buttons based upon passed bitwise config
    me.msgButtons = [];
    for (i = 0; i < 4; i++) {
      button = me.makeButton(i);
      me.msgButtons[button.itemId] = button;
      me.msgButtons.push(button);
    }
    me.bottomTb = new Ext.toolbar.Toolbar({
      id: baseId + '-toolbar',
      ui: 'footer',
      dock: 'bottom',
      layout: {
        pack: 'center'
      },
      items: [
              me.msgButtons[0],
              me.msgButtons[1],
              me.msgButtons[2],
              me.msgButtons[3]
              ],
              border: false
    });
    me.dockedItems = [me.bottomTb];

    // Get control at Toolbar's finishedLayout call and snag the contentWidth to contribute to our auto width calculation
    tbLayout = me.bottomTb.getLayout();
    tbLayout.finishedLayout = Ext.Function.createInterceptor(tbLayout.finishedLayout, function(ownerContext) {
      me.tbWidth = ownerContext.getProp('contentWidth');
    });
    me.on('close', me.onClose, me);
    
    me.callParent();
  }
});

Ext.override(Ext.util.Bindable, {
  bindStore: function(store, initial){
    var me = this,
    oldStore = me.store;
    
    if (!initial && me.store) {
      if (store !== oldStore && oldStore.autoDestroy) {
        oldStore.destroyStore();
      } else {
        me.unbindStoreListeners(oldStore);
      }
      me.onUnbindStore(oldStore, initial);
    }
    if (store) {
      store = Ext.data.StoreManager.lookup(store);
      me.bindStoreListeners(store);
      me.onBindStore(store, initial);
    }
    me.store = store || null;
    return me;
  }
});

//修复grid的加载数据遮罩层问题
Ext.override(Ext.view.AbstractView, {
  onRender: function() {
    var me = this;
    me.callOverridden(arguments);
    
    // modify
    if (me.loadMask && Ext.isObject(me.store))
      me.setMaskBind(me.store);
  }
});

Ext.override(Ext.form.field.Base, {
  //当编辑控件只读时不进行校验
  validateValue: function(value) {
    var me = this,
    errors = me.getErrors(value),
    isValid = Ext.isEmpty(errors);
    
    // modify
    if (me.readOnly)
      isValid = true;
    
    if (!me.preventMark) {
      if (isValid) {
        me.clearInvalid();
      } else {
        me.markInvalid(errors);
      }
    }
    
    return isValid;
  },
  
  //修复当控件不可见时不回送后台的问题
  getSubmitData: function() {
    var me = this,
    data = null,
    val;
    if (me.submitValue && !me.isFileUpload()) {
      val = me.getSubmitValue();
      if (val !== null) {
        data = {};
        data[me.getName()] = val;
      }
    }
    return data;
  }
});

//grid锁定列的时候显示错行
Ext.override(Ext.grid.Lockable, {
  injectLockable: function() {
    // ensure lockable is set to true in the TablePanel
    this.lockable = true;
    // Instruct the TablePanel it already has a view and not to create one.
    // We are going to aggregate 2 copies of whatever TablePanel we are using
    this.hasView = true;

    var me = this,
        // If the OS does not show a space-taking scrollbar, the locked view can be overflow:auto
        scrollLocked = Ext.getScrollbarSize().width === 0,
        store = me.store = Ext.StoreManager.lookup(me.store),
        // xtype of this class, 'treepanel' or 'gridpanel'
        // (Note: this makes it a requirement that any subclass that wants to use lockable functionality needs to register an
        // alias.)
        xtype = me.determineXTypeToCreate(),
        // share the selection model
        selModel = me.getSelectionModel(),
        lockedFeatures = me.prepareFeatures(),
        normalFeatures = me.prepareFeatures(),
        lockedGrid,
        normalGrid,
        i = 0, len,
        columns,
        lockedHeaderCt,
        normalHeaderCt,
        lockedView,
        normalView,
        listeners;

    // Each Feature must have a reference to its counterpart on the opposite side of the locking view
    for (i = 0, len = (lockedFeatures ? lockedFeatures.length : 0); i < len; i++) {
        lockedFeatures[i].lockingPartner = normalFeatures[i];
        normalFeatures[i].lockingPartner = lockedFeatures[i];
    }

    lockedGrid = Ext.apply({
        xtype: xtype,
        store: store,
        scrollerOwner: false,
        // Lockable does NOT support animations for Tree
        enableAnimations: false,
        scroll: scrollLocked ? 'vertical' : false,
        selModel: selModel,
        border: false,
        cls: Ext.baseCSSPrefix + 'grid-inner-locked',
        isLayoutRoot: function() {
            return false;
        },
        features: lockedFeatures
    }, me.lockedGridConfig);

    normalGrid = Ext.apply({
        xtype: xtype,
        store: store,
        scrollerOwner: false,
        enableAnimations: false,
        selModel: selModel,
        border: false,
        isLayoutRoot: function() {
            return false;
        },
        features: normalFeatures
    }, me.normalGridConfig);

    me.addCls(Ext.baseCSSPrefix + 'grid-locked');

    // copy appropriate configurations to the respective
    // aggregated tablepanel instances and then delete them
    // from the master tablepanel.
    Ext.copyTo(normalGrid, me, me.bothCfgCopy);
    Ext.copyTo(lockedGrid, me, me.bothCfgCopy);
    Ext.copyTo(normalGrid, me, me.normalCfgCopy);
    Ext.copyTo(lockedGrid, me, me.lockedCfgCopy);
    for (i = 0; i < me.normalCfgCopy.length; i++) {
        delete me[me.normalCfgCopy[i]];
    }
    for (i = 0; i < me.lockedCfgCopy.length; i++) {
        delete me[me.lockedCfgCopy[i]];
    }

    me.addEvents(
        /**
         * @event lockcolumn
         * Fires when a column is locked.
         * @param {Ext.grid.Panel} this The gridpanel.
         * @param {Ext.grid.column.Column} column The column being locked.
         */
        'lockcolumn',

        /**
         * @event unlockcolumn
         * Fires when a column is unlocked.
         * @param {Ext.grid.Panel} this The gridpanel.
         * @param {Ext.grid.column.Column} column The column being unlocked.
         */
        'unlockcolumn'
    );

    me.addStateEvents(['lockcolumn', 'unlockcolumn']);

    me.lockedHeights = [];
    me.normalHeights = [];

    columns = me.processColumns(me.columns);

    // modify cloudy
    lockedGrid.width = columns.lockedWidth + Ext.num(selModel.headerWidth, 0) + 1;
    lockedGrid.columns = columns.locked;
    normalGrid.columns = columns.normal;

    // normal grid should flex the rest of the width
    normalGrid.flex = 1;
    lockedGrid.viewConfig = me.lockedViewConfig || {};
    lockedGrid.viewConfig.loadingUseMsg = false;
    normalGrid.viewConfig = me.normalViewConfig || {};

    Ext.applyIf(lockedGrid.viewConfig, me.viewConfig);
    Ext.applyIf(normalGrid.viewConfig, me.viewConfig);

    me.normalGrid = Ext.ComponentManager.create(normalGrid);
    me.lockedGrid = Ext.ComponentManager.create(lockedGrid);

    me.view = new Ext.grid.LockingView({
        locked: me.lockedGrid,
        normal: me.normalGrid,
        panel: me
    });

    lockedView = me.lockedGrid.getView();
    normalView = me.normalGrid.getView();

    // Set up listeners for the locked view
    // If the OS does not show a space-taking scrollbar, the locked view can be overflow:auto
    // And therefore we can listen for the DOM scroll event on its element
    if (scrollLocked) {
        listeners = {
            scroll: {
                fn: me.onLockedViewScroll,
                element: 'el',
                scope: me
            }
        };
    }
    // If there are scrollbars, we have to monitor the mousewheel and fake a scroll
    else {
        listeners = {
            mousewheel: {
                fn: me.onLockedViewMouseWheel,
                element: 'el',
                scope: me
            }
        };
    }
    if (me.syncRowHeight) {
        listeners.refresh = me.onLockedViewRefresh;
        listeners.itemupdate = me.onLockedViewItemUpdate;
        listeners.scope = me;
    }
    lockedView.on(listeners);

    // Set up listeners for the normal view
    listeners = {
        scroll: {
            fn: me.onNormalViewScroll,
            element: 'el',
            scope: me
        },
        refresh: me.syncRowHeight ? me.onNormalViewRefresh : me.updateSpacer,
        scope: me
    };
    normalView.on(listeners);

    lockedHeaderCt = me.lockedGrid.headerCt;
    normalHeaderCt = me.normalGrid.headerCt;

    lockedHeaderCt.lockedCt = true;
    lockedHeaderCt.lockableInjected = true;
    normalHeaderCt.lockableInjected = true;

    lockedHeaderCt.on({
        columnshow: me.onLockedHeaderShow,
        columnhide: me.onLockedHeaderHide,
        columnmove: me.onLockedHeaderMove,
        sortchange: me.onLockedHeaderSortChange,
        columnresize: me.onLockedHeaderResize,
        scope: me
    });

    normalHeaderCt.on({
        columnmove: me.onNormalHeaderMove,
        sortchange: me.onNormalHeaderSortChange,
        scope: me
    });

    me.modifyHeaderCt();
    me.items = [me.lockedGrid, me.normalGrid];

    me.relayHeaderCtEvents(lockedHeaderCt);
    me.relayHeaderCtEvents(normalHeaderCt);

    me.layout = {
        type: 'hbox',
        align: 'stretch'
    };
    
    me.lockedGrid.on({
      afterlayout: me.afterLockedViewLayout,
      scope: me
    });
  },

  afterLockedViewLayout: function () {
    var me = this,
      lockedView = me.lockedGrid.getView(),
      normalView = me.normalGrid.getView(),
      lockedViewEl = lockedView.el,
      lockedHeaderCt = me.lockedGrid.headerCt,
      normalHeaderCt = me.normalGrid.headerCt;
    
    //alert(lockedHeaderCt.getFullWidth() + '-' + lockedHeaderCt.getWidth());
    if (normalHeaderCt.getFullWidth() > normalHeaderCt.getWidth())
      lockedViewEl.dom.style.overflowX = 'scroll';
    else
      lockedViewEl.dom.style.overflowX = '';
  },
  
  syncLockedWidth: function() {
    var me = this,
        locked = me.lockedGrid,
        width = locked.headerCt.getFullWidth(true) + 1; // modify cloudy

    Ext.suspendLayouts();
    if (width > 0) {
        locked.setWidth(width);
        locked.show();
    } else {
        locked.hide();
    }
    Ext.resumeLayouts(true);
    
    return width > 0;
  },
  
  updateSpacer: function() {
    var me   = this,
    // This affects scrolling all the way to the bottom of a locked grid
    // additional test, sort a column and make sure it synchronizes
    lockedViewEl   = me.lockedGrid.getView().el,
    normalViewEl = me.normalGrid.getView().el.dom,
    spacerId = lockedViewEl.dom.id + '-spacer',
    spacerHeight = (normalViewEl.offsetHeight - normalViewEl.clientHeight) + 'px';
    
    // modify
    return;
    me.spacerEl = Ext.getDom(spacerId);
    
    if (me.spacerEl) {
      me.spacerEl.style.height = spacerHeight;
    } else {
      Ext.core.DomHelper.append(lockedViewEl, {
        id: spacerId,
        style: 'height: ' + spacerHeight
      });
    }
  }
});

//数据请求返回登录时处理
Ext.override(Ext.data.Connection, {
  onComplete : function(request) {
    var me = this,
    options = request.options,
    result,
    success,
    response;
    
    try {
      result = me.parseStatus(request.xhr.status);
    } catch (e) {
      // in some browsers we can't access the status if the readyState is not 4, so the request has failed
      result = {
        success : false,
        isException : false
      };
    }
    success = result.success;
    
    try {
      // modify
      if (request.xhr && request.xhr.responseText && (request.xhr.responseText == 'login')) {
        if (gpersist) {
          gpersist.UserInfo = null;
          
          if (gpersist.AllParams)
            gpersist.AllParams.clear();
          
          //gpersist.OnLogin();
          location.reload(true);
        }
        
        return null;
      }
    } catch (e) { };
    
    if (success) {
      response = me.createResponse(request);
      me.fireEvent('requestcomplete', me, response, options);
      Ext.callback(options.success, options.scope, [response, options]);
    } else {
      if (result.isException || request.aborted || request.timedout) {
        response = me.createException(request);
      } else {
        response = me.createResponse(request);
      }
      me.fireEvent('requestexception', me, response, options);
      Ext.callback(options.failure, options.scope, [response, options]);
    }
    
    Ext.callback(options.callback, options.scope, [options, success, response]);
    delete me.requests[request.id];
    return response;
  }
});

//修复后台日期类型与前台的冲突
Ext.override(Ext.data.JsonReader, {
  getResponseData: function (response) {
    var data, error;
    
    try {
      //alert(response.responseText.replace(/\r\n/g,'<br/>').replace(/\n/g,'<br/>').replace(/\\/g,'').replace(/\"(new Date\(\d+\))\"/g,'$1'));
      data = Ext.decode(response.responseText.replace(/\r\n/g,'<br/>').replace(/\n/g,'<br/>').replace(/\\/g,'').replace(/\"(new Date\((-*)\d+\))\"/g,'$1'));
      return this.readRecords(data);
    } catch (ex) {
      error = new Ext.data.ResultSet({
        total  : 0,
        count  : 0,
        records: [],
        success: false,
        message: ex.message
      });
      
      this.fireEvent('exception', this, response, error);
      
      Ext.Logger.warn('Unable to parse the JSON returned by the server');
      
      return error;
    }
  }
});

//修复后台日期类型与前台的冲突
Ext.override(Ext.form.action.Submit, {
  handleResponse: function(response) {
    var form = this.form,
    errorReader = form.errorReader,
    rs, errors, i, len, records;
    if (errorReader) {
      rs = errorReader.read(response);
      records = rs.records;
      errors = [];
      if (records) {
        for(i = 0, len = records.length; i < len; i++) {
          errors[i] = records[i].data;
        }
      }
      if (errors.length < 1) {
        errors = null;
      }
      return {
        success : rs.success,
        errors : errors
      };
    }
    
    try {
      return Ext.decode(response.responseText.replace(/\r\n/g,'<br/>').replace(/\n/g,'<br/>').replace(/\\/g,'').replace(/\"(new Date\((-*)\d+\))\"/g,'$1'));
    }
    catch (ex) {alert(ex);
      return Ext.decode("{\"success\":false,\"msg\":\"错误的数据格式！\"}");
    }
    
  }
});

Ext.override(Ext.view.Table, {
  walkCells: function (pos, direction, e, preventWrap, verifierFn, scope) {
    if (!pos)
      pos = { row: 0, column: 0 };

    var me = this,
    row = pos.row,
    column = pos.column,
    rowCount = me.store.getCount(),
    firstCol = me.getFirstVisibleColumnIndex(),
    lastCol = me.getLastVisibleColumnIndex(),
    newPos = { row: row, column: column },
    activeHeader = me.headerCt.getHeaderAtIndex(column);
    
    // no active header or its currently hidden
    if (!activeHeader || activeHeader.hidden) {
      return false;
    }
    
    e = e || {};
    direction = direction.toLowerCase();
    switch (direction) {
      case 'right':
        // has the potential to wrap if its last
        if (column === lastCol) {
          // if bottom row and last column, deny right
          if (preventWrap || row === rowCount - 1) {
            //return false;
            newPos.row = 0;
            newPos.column = firstCol;
          }
          else if (!e.ctrlKey) {
            // otherwise wrap to nextRow and firstCol
            newPos.row = row + 1;
            newPos.column = firstCol;
          }
          // go right
        } else {
          if (!e.ctrlKey) {
            newPos.column = column + me.getRightGap(activeHeader);
          } else {
            newPos.column = lastCol;
          }
        }
        break;
        
      case 'left':
        // has the potential to wrap
        if (column === firstCol) {
          // if top row and first column, deny left
          if (preventWrap || row === 0) {
            //return false;
            newPos.row = rowCount - 1;
            newPos.column = lastCol;
          }
          else if (!e.ctrlKey) {
            // otherwise wrap to prevRow and lastCol
            newPos.row = row - 1;
            newPos.column = lastCol;
          }
          // go left
        } else {
          if (!e.ctrlKey) {
            newPos.column = column + me.getLeftGap(activeHeader);
          } else {
            newPos.column = firstCol;
          }
        }
        break;
        
      case 'up':
        // if top row, deny up
        if (row === 0) {
          newPos.row = rowCount - 1;
          // go up
        } else {
          newPos.row = row - 1;
        }
        break;
        
      case 'down':
        // if bottom row, deny down
        if (row === rowCount - 1) {
          newPos.row = 0;
          // go down
        } else {
          newPos.row = row + 1;
        }
        break;
    }
    
    if (verifierFn && verifierFn.call(scope || window, newPos) !== true) {
      return false;
    } else {
      return newPos;
    }
  },

  getFirstVisibleColumnIndex: function () {
    var headerCt = this.getHeaderCt(),
    allColumns = headerCt.getGridColumns(),
    visHeaders = Ext.ComponentQuery.query(':not([hidden])', allColumns),
    firstHeader = visHeaders[0];
    
    if ((firstHeader.dataIndex == 'IndexNum') && (visHeaders.length >= 2))
      firstHeader = visHeaders[1];
    
    return headerCt.getHeaderIndex(firstHeader);
  },
  
  getPosition: function(record, header) {
    var me = this,
        store = me.store,
        gridCols = me.headerCt.getGridColumns();

    return {
        row: store.indexOf(record),
        column: Ext.Array.indexOf(gridCols, header)
    };
  }
});

//行选择时的单元格遍历
Ext.override(Ext.selection.RowModel, {
  onEditorTab: function(editingPlugin, e) {
    var me = this;
    
    // modify
    
    var view = me.views[1];
    
    if (me.views.length > 1)
      view = me.views[1];
      
    var record = editingPlugin.getActiveRecord(),
    header = editingPlugin.getActiveColumn(),
    position = view.getPosition(record, header),    
    direction = e.shiftKey ? 'left' : 'right';
    if (e.getKey() === e.DOWN)
      direction = 'down';
    
    if (e.getKey() === e.UP)
      direction = 'up';

    do {
      position  = view.walkCells(position, direction, e, me.preventWrap);
      
    } while(position && !view.headerCt.getHeaderAtIndex(position.column).getEditor());
        
    if (position) {
      editingPlugin.startEditByPosition(position);
    }
  },

  onEditorUp: function (editingPlugin, e) {
    var me = this;
    me.onEditorTab(editingPlugin, e);
  },
  
  //当为最后一行时自动增加一行，待测，这个针对RowModel时候在使用CellEditing时触发
  onEditorDown: function (editingPlugin, e) {
    var me = this;
    
    if (Ext.isDefined(me.nodownadd) && !me.nodownadd)
      return;

    var view = me.views[0],
    record = editingPlugin.getActiveRecord(),
    header = editingPlugin.getActiveColumn(),
    position = view.getPosition(record, header),
    store = me.view.getStore();
    
    if (position.row == store.getCount() - 1) {
      var record = store.model.create({});
      store.insert(store.getCount(), record);
    }
    
    me.onEditorTab(editingPlugin, e);
  }
});

Ext.override(Ext.grid.plugin.CellEditing, {
  startEdit: function(record, columnHeader) {
    var me = this,
    context = me.getEditingContext(record, columnHeader),
    value, ed;
    
    // Complete the edit now, before getting the editor's target
    // cell DOM element. Completing the edit causes a row refresh.
    // Also allows any post-edit events to take effect before continuing
    me.completeEdit();
    
    // Cancel editing if EditingContext could not be found (possibly because record has been deleted by an intervening listener), or if the grid view is not currently visible
    if (!context || !me.grid.view.isVisible(true)) {
      return false;
    }
    
    record = context.record;
    columnHeader = context.column;
    
    // See if the field is editable for the requested record
    if (columnHeader && !columnHeader.getEditor(record)) {
      return false;
    }
    
    value = record.get(columnHeader.dataIndex);
    context.originalValue = context.value = value;
    if (me.beforeEdit(context) === false || me.fireEvent('beforeedit', me, context) === false || context.cancel) {
      return false;
    }
    
    ed = me.getEditor(record, columnHeader);
    
    // Whether we are going to edit or not, ensure the edit cell is scrolled into view
    me.grid.view.cancelFocus();
    me.view.focusCell({
      row: context.row,
      column: context.colIdx
    });
    if (ed) {
      me.context = context;
      me.setActiveEditor(ed);
      me.setActiveRecord(record);
      me.setActiveColumn(columnHeader);
      
      // Defer, so we have some time between view scroll to sync up the editor
      // modify
      me.editTask.delay(50, ed.startEdit, ed, [me.getCell(record, columnHeader), value]);
      me.editing = true;
      me.scroll = me.view.el.getScroll();
      return true;
    }
    return false;
  },
  
  startEditByPosition: function(position) {
    var sm = this.grid.getSelectionModel();
    
    // Coerce the column position to the closest visible column
    position.column = this.view.getHeaderCt().getVisibleHeaderClosestToIndex(position.column).getIndex();
    if (sm.selectByPosition) {
      // modify
      sm.selectByPosition(position);
    }

    return this.startEdit(position.row, position.column);
  },
  
  onSpecialKey: function (ed, field, e) {
    var grid = this.grid,
    sm;
    if (e.getKey() === e.TAB) {
      e.stopEvent();
      
      sm = grid.getSelectionModel();
      if (sm.onEditorTab) {
        sm.onEditorTab(this, e);
      }
    }
    
    if (e.getKey() === e.DOWN) {
      e.stopEvent();
      sm = grid.getSelectionModel();
      if (sm.onEditorDown) {
        sm.onEditorDown(this, e);
      }
    }
    
    if (e.getKey() === e.UP) {
      e.stopEvent();
      sm = grid.getSelectionModel();
      if (sm.onEditorUp) {
        sm.onEditorUp(this, e);
      }
    }
  }
});

Ext.override(Ext.selection.CheckboxModel, {
  addCheckbox: function(initial){
    var me = this,
        checkbox = me.injectCheckbox,
        view = me.views[0],
        headerCt = view.headerCt;

    // Preserve behaviour of false, but not clear why that would ever be done.
    if (checkbox !== false) {
        if (checkbox == 'first') {
            checkbox = 0;
        } else if (checkbox == 'last') {
            checkbox = headerCt.getColumnCount();
        }
        Ext.suspendLayouts();
        headerCt.add(checkbox,  me.getHeaderConfig());
        Ext.resumeLayouts();
    }

    if (initial !== true) {
        view.refresh();
    }
  }
});

Ext.override(Ext.grid.RowNumberer, {
  renderer: function(value, metaData, record, rowIdx, colIdx, store) {
    if (this.rowspan){
        metaData.cellAttr = 'rowspan="'+this.rowspan+'"';
    }

    metaData.tdCls = Ext.baseCSSPrefix + 'grid-cell-special';

    return rowIdx + 1;
  }
});

Ext.override(Ext.form.field.Picker, {
  alignPicker: function() {
    var me = this,
        picker = me.getPicker();

    if (me.isExpanded) {
        if (me.matchFieldWidth) {
            // Auto the height (it will be constrained by min and max width) unless there are no records to display.
            picker.setWidth(me.bodyEl.getWidth());
        }
        
        if (picker.isFloating()) {
            me.doAlign();
        }
    }
  }
});

Ext.override(Ext.toolbar.Toolbar, {
  lookupComponent: function(c) {
    if (typeof c == 'string') {
        var T = Ext.toolbar.Toolbar,
            shortcut = T.shortcutsHV[this.vertical ? 1 : 0][c] || T.shortcuts[c];

        if (typeof shortcut == 'string') {
          if (shortcut == 'tbseparator')
            c = {
              xtype: shortcut,
              border: 1
            };
          else
            c = {
                xtype: shortcut
            };
        } else if (shortcut) {
            c = Ext.apply({}, shortcut);
        } else {
            c = {
                xtype: 'tbtext',
                text: c
            };
        }

        this.applyDefaults(c);
    }

    return this.callParent(arguments);
  }
});

Ext.override(Ext.form.field.File, {
  extractFileInput: function() {
    var me = this,
        fileInput = me.fileInputEl.dom,
        clone = fileInput.cloneNode(true);

    fileInput.parentNode.replaceChild(clone, fileInput);
    me.fileInputEl = Ext.get(clone);
    return fileInput;
  }
});

Ext.override(Ext.selection.Model, {
  constructor: function(cfg) {
    var me = this;

    cfg = cfg || {};
    Ext.apply(me, cfg);

    me.addEvents(
        /**
         * @event
         * Fired after a selection change has occurred
         * @param {Ext.selection.Model} this
         * @param {Ext.data.Model[]} selected The selected records
         */
        'selectionchange',
        /**
         * @event
         * Fired when a row is focused
         * @param {Ext.selection.Model} this
         * @param {Ext.data.Model} oldFocused The previously focused record
         * @param {Ext.data.Model} newFocused The newly focused record
         */
        'focuschange',        
        'beforedeselectall'
    );

    me.modes = {
        SINGLE: true,
        SIMPLE: true,
        MULTI: true
    };

    // sets this.selectionMode
    me.setSelectionMode(cfg.mode || me.mode);

    // maintains the currently selected records.
    me.selected = new Ext.util.MixedCollection();

    me.callParent(arguments);
  },

  deselectAll: function(suppressEvent) {
    var me = this,
        selections = me.getSelection(),
        i = 0,
        len = selections.length,
        start = me.getSelection().length;

    me.fireEvent('beforedeselectall', me);
    me.bulkChange = true;
    for (; i < len; i++) {
        me.doDeselect(selections[i], suppressEvent);
    }
    delete me.bulkChange;
    // fire selection change only if the number of selections differs
    me.maybeFireSelectionChange(me.getSelection().length !== start);
  }
});

