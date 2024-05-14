Ext.define('gpersist.base.tranform', {
    extend: 'gpersist.base.listform',

    tabMain: null,
    plEdit: null,
    winEdit: null,
    gridToolItems: [],
    gridOtherTools: [],
    dataDeal: gpersist.DATA_DEAL_SELECT,
    disNews: [],
    disEdits: [],
    enNews: [],
    enEdits: [],
    saveUrl: '',
    editInfo: '',
    editToolItems: [],
    tbEdit: null,
    editControls: [],
    hasCopy: false,
    hasPrevNext: false,
    winWidth: 600,
    winHeight: 250,
    hasExitPrompt: true,
    saveParams: {},
    hasOtherSearch: false,
    hasDetailList: false,
    plDetailGrid: null,
    urlDetail: '',
    urlDetailColumn: '',
    columnDetails: [],
    fieldDetails: [],
    gridDetailStore: null,
    detailTitle: '',
    hasPageDetail: false,
    detailFuncs: null,
    hasDetailEdit: false,
    hasDetailEditTool: true,
    cellEditing: null,
    pinfoStore: null,
    plPInfo: null,
    urlPrdSelect: '',
    urlPrdColumns: '',
    hasFirst: true,
    hasPrdGrid: false,
    startMonth: -1,
    editPadding: 0,

    OnInitConfig: function () {
        var me = this;

        me.callParent(arguments);

        me.plGrid = null;
        me.plEdit = null;
        me.winEdit = null;
        me.dataDeal = gpersist.DATA_DEAL_SELECT;
        me.disNews = [];
        me.disEdits = [];
        me.enNews = [];
        me.enEdits = [];
        me.saveUrl = '';
        me.editInfo = '';
        me.editToolItems = [];
        me.gridOtherTools = [];
        me.tbEdit = null;
        me.editControls = [];
        me.hasCopy = false;
        me.hasPrevNext = false;
        me.winWidth = 600;
        me.winHeight = 250;
        me.hasExitPrompt = true;
        me.saveParams = {};
        me.hasOtherSearch = false;
        me.gridToolItems = [];
        me.plDetailGrid = null;
        me.urlDetail = '';
        me.urlDetailColumn = '';
        me.columnDetails = [];
        me.fieldDetails = [];
        me.hasDetailList = false;
        me.gridDetailStore = null;
        me.detailTitle = '';
        me.hasPageDetail = false;
        me.detailFuncs = null;
        me.hasDetailEdit = false;
        me.cellEditing = null;
        me.pinfoStore = null;
        me.plPInfo = null;
        me.urlPrdSelect = '';
        me.urlPrdColumns = '';
        me.hasFirst = true;
        me.hasPrdGrid = false;
        me.hasDetailEditTool = true;
        me.startMonth = -1;
        me.editPadding = 0;
    },

    OnBeforeFormLoad: function () {
        var me = this;
        var mep = me.tranPrefix;

        me.OnInitGridToolBar();

        var items = [
    			' ', { id: mep + 'btnPrev', text: gpersist.STR_BTN_PREVDAY, iconCls: 'icon-prev', handler: me.OnPrevSearch, scope: me },
          ' ', { id: mep + 'btnNext', text: gpersist.STR_BTN_NEXTDAY, iconCls: 'icon-next', handler: me.OnNextSearch, scope: me },
    			' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew, scope: me },
          ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit, scope: me },
          ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnEdit, scope: me },
          ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
          ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
          ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
        ];

        if (me.hasPage)
            Ext.Array.insert(items, items.length, [' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
                id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.CON_PAGESIZE_TRAN, minValue: gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX
            }]);

        if (me.gridOtherTools.length > 0)
            Ext.Array.insert(items, items.length, me.gridOtherTools);

        tools.SetToolbar(items, mep);

        tools.DeleteToolbarItem(mep, items, 'btnAdd', 5);
        tools.DeleteToolbarItem(mep, items, 'btnEdit', 7);
        tools.DeleteToolbarItem(mep, items, 'btnDeal', 9);

        var toolbar = Ext.create('Ext.toolbar.Toolbar', { items: items, border: false });

        var nowdate = new Date();

        var otheritems = [
    			' ', { xtype: 'datefield', fieldLabel: gpersist.STR_BTN_BEGINDATE, labelWidth: 60, width: 160, name: 'begindate', id: mep + 'begindate',
    			    format: 'Y-m-d', value: Ext.Date.add(nowdate, Ext.Date.MONTH, me.startMonth), selectOnFocus: false, allowBlank: true
    			},
          ' ', { xtype: 'datefield', fieldLabel: gpersist.STR_BTN_ENDDATE, labelWidth: 60, width: 160, name: 'enddate', id: mep + 'enddate',
              format: 'Y-m-d', value: nowdate, selectOnFocus: false, allowBlank: true
          },
    			' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me }
    		];

        Ext.Array.insert(otheritems, 4, me.gridToolItems);

        var othertoolbar = Ext.create('Ext.toolbar.Toolbar', { items: otheritems, border: false });

        me.tbGrid.add(toolbar);
        me.tbGrid.add(othertoolbar);
    },

    OnFormLoad: function () {
        var me = this;
        var mep = me.tranPrefix;

        if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
            return;

        Ext.getCmp('tpanel' + me.mid).removeAll();

        tools.GetGridColumns(this.columnUrl, me.columns, me.fields, mep + '_l_');
        me.gridStore = tools.CreateGridStore(tools.GetUrl(this.storeUrl), me.fields);

        me.OnBeforeFormLoad();

        me.plGrid = Ext.create('Ext.grid.Panel', {
            id: mep + 'grid',
            region: 'center',
            frame: false,
            border: false,
            margins: '0 0 0 0',
            padding: '0 0 0 0',
            loadMask: true,
            columnLines: true,
            viewConfig: {
                autoFill: true,
                stripeRows: true
            },
            columns: me.columns,
            store: me.gridStore,
            tbar: me.tbGrid,
            listeners: { 'itemdblclick': { fn: me.OnShow, scope: me },
                'render': { fn: me.OnAutoRefresh, scope: me, delay: gpersist.DEFAULT_AUTOLOAD }
            }
        });

        if (me.hasPage) {
            me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
                store: me.gridStore,
                displayInfo: true,
                displayMsg: gpersist.STR_PAGE_FMT,
                emptyMsg: gpersist.STR_NO_DATA,
                dock: 'bottom'
            }));
        }

        me.OnAfterFormLoad();

        me.OnCreateEditWin();

        var pleditview = Ext.create('Ext.Panel', {
            frame: false,
            autoScroll: false,
            region: 'center',
            border: false,
            items: [me.plEdit],
            layout: "border",
            margins: '0 0 0 0',
            padding: '0 0 0 0'
        });

        if (me.hasDetailList)
            pleditview.add(me.plDetailGrid);

        me.tabMain = Ext.create('Ext.tab.Panel', {
            border: false,
            activeTab: 0,
            bodyBorder: false,
            defaults: { bodyStyle: 'border:0px;padding:0px;' },
            margins: '0 0 0 0', region: 'center',
            deferredRender: false,
            items: [me.plGrid, pleditview]
        });

        Ext.getCmp('tpanel' + me.mid).add(me.tabMain);
        me.tabMain.getTabBar().setVisible(false);
        if (me.hasAutoLoad)
            me.OnSearch();
    },

    OnAutoRefresh: function () {
        var me = this;

        Ext.TaskManager.start({ run: function () {
            var activetab = me.tabMain.getActiveTab();
            if (me.tabMain.items.findIndex('id', activetab.id) == 0)
                me.OnSearch();
        }, interval: gpersist.DEFAULT_AUTOLOAD
        });
    },

    OnPrevSearch: function () {
        var me = this;
        var mep = me.tranPrefix;
        tools.SetValue(mep + 'begindate', Ext.Date.add(tools.GetValue(mep + 'begindate'), Ext.Date.DAY, -1));
        tools.SetValue(mep + 'enddate', Ext.Date.add(tools.GetValue(mep + 'enddate'), Ext.Date.DAY, -1));

        me.OnSearch();
    },

    OnNextSearch: function () {
        var me = this;
        var mep = me.tranPrefix;
        tools.SetValue(mep + 'begindate', Ext.Date.add(tools.GetValue(mep + 'begindate'), Ext.Date.DAY, 1));
        tools.SetValue(mep + 'enddate', Ext.Date.add(tools.GetValue(mep + 'enddate'), Ext.Date.DAY, 1));

        me.OnSearch();
    },

    OnBeforeCreateEditToolBar: function () {

    },

    OnBeforeCreateEdit: function () {

    },

    OnAfterCreateEdit: function () {

    },

    OnBeforeCreateDetail: function () {

    },

    OnAfterCreateDetail: function () {

    },

    OnGetDetailFunction: function () {

    },

    OnDisplayPrdGrid: function () {
        var me = this;
        var mep = this.tranPrefix;

        if (Ext.get(mep + 'PrdInfoList') && me.hasFirst) {
            me.hasFirst = false;
            me.plPInfo.render(mep + 'PrdInfoList');
        }

        if (Ext.getCmp(mep + 'SelectPrd'))
            Ext.getCmp(mep + 'SelectPrd').getPicker().setHeight(300);
    },

    OnReloadPrdGrid: function (qe) {
        var me = this;

        qe.combo.expand();

        me.pinfoStore.clearFilter();

        if (qe.query.length == 0)
            qe.query = qe.combo.getDisplayValue();

        if (qe.query.length >= 0) {
            var reg = new RegExp(qe.query, '');
            me.pinfoStore.filter([{ property: 'PrdCN', value: reg}]);
        }

        return false;
    },

    OnGetSelectPrd: function (grid) {
        var me = this;
        var mep = me.tranPrefix;
        var selModel = grid.getSelectionModel();
        if (selModel.hasSelection()) {
            var selected = selModel.getSelection();

            var now = me.cellEditing.getActiveRecord();

            if (now) {
                me.cellEditing.getActiveEditor().setValue(selected[0].data.PrdID);
                now.data.PrdID = selected[0].data.PrdID;
                now.data.PrdName = selected[0].data.PrdName;
                now.data.PrdUnitName = selected[0].data.PrdUnitName;
                now.data.PrdPrice = selected[0].data.PriceSale;
                now.data.CheckID = selected[0].data.PrdID;
                me.plDetailGrid.getView().refresh();

                Ext.getCmp(mep + 'SelectPrd').collapse();
            }
        }
    },

    OnCreateEditWin: function () {
        var me = this;
        var mep = me.tranPrefix;

        me.OnBeforeCreateEditToolBar();

        var toolbar = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },
			' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me },
      ' ', { id: mep + 'btnFormDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnFormEdit, scope: me },
			' ', { id: mep + 'btnReset', text: gpersist.STR_BTN_RESET, iconCls: 'icon-reset', handler: me.OnFormReset, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_RETURNLIST, iconCls: 'icon-list', handler: me.OnFormClose, scope: me }
    ];

        Ext.Array.insert(toolbar, 6, me.editToolItems);

        if (me.hasPrevNext) {
            Ext.Array.insert(toolbar, toolbar.length, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me },
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me}]);
        }

        tools.SetToolbar(toolbar, mep);

        me.tbEdit = Ext.create('Ext.toolbar.Toolbar', { items: toolbar });

        me.OnBeforeCreateEdit();

        me.plEdit = Ext.create('Ext.form.Panel', {
            id: mep + 'pledit', header: false,
            region: 'north',
            fieldDefaults: {
                labelSeparator: '：',
                labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
                labelPad: 0,
                labelStyle: 'font-weight:bold;'
            },
            frame: true,
            title: '',
            bodyStyle: 'padding:' + me.editPadding + 'px;background:#FFFFFF',
            defaultType: 'textfield',
            closable: false,
            header: false,
            unstyled: true,
            scope: me,
            tbar: me.tbEdit,
            items: me.editControls
        });

        me.OnAfterCreateEdit();

        if (me.hasDetailList) {
            me.detailFuncs = Ext.create('Ext.util.MixedCollection');

            me.OnGetDetailFunction();

            tools.GetGridColumns(this.urlDetailColumn, me.columnDetails, me.fieldDetails, mep + '_d_', me.detailFuncs, me.hasDetailEdit);
            me.gridDetailStore = tools.CreateGridStore(tools.GetUrl(this.urlDetail), me.fieldDetails);

            me.OnBeforeCreateDetail();

            Ext.each(me.columnDetails, function (col) {
                if (col.dataIndex == 'CheckNum') {
                    col.on('beforecheck', function () {
                        if (me.dataDeal == gpersist.DATA_DEAL_SELECT)
                            this.isCancel = true;
                        else
                            this.isCancel = false;
                    });

                    return false;
                }
            });

            if (me.hasDetailEdit) {
                Ext.each(me.columnDetails, function (col) {
                    if ((col.dataIndex == 'PrdID') && me.hasPrdGrid) {
                        var pinfocols = [];
                        var pinfofields = [];

                        tools.GetGridColumns(me.urlPrdColumns, pinfocols, pinfofields, mep + 'sp_');
                        me.pinfoStore = tools.CreateGridStore(tools.GetUrl(me.urlPrdSelect), pinfofields);

                        me.plPInfo = Ext.create('Ext.grid.Panel', {
                            frame: false,
                            border: false,
                            margin: '0 0 0 0',
                            padding: '0 0 0 0',
                            loadMask: true,
                            columnLines: true,
                            viewConfig: {
                                autoFill: true,
                                stripeRows: true
                            },
                            columns: pinfocols,
                            store: me.pinfoStore,
                            height: 300,
                            listeners: { itemdblclick: { fn: me.OnGetSelectPrd, scope: me} }
                        });

                        me.pinfoStore.load();

                        col.editor = {
                            xtype: 'combo',
                            id: mep + 'SelectPrd',
                            name: 'SelectPrd',
                            store: new Ext.data.SimpleStore({ fields: ['id', 'name'], data: [['', '']] }),
                            valueField: 'id',
                            displayField: 'name',
                            typeAhead: true,
                            queryMode: 'local',
                            triggerAction: 'all',
                            matchFieldWidth: false,
                            listConfig: {
                                renderTpl: ['<div id="' + mep + 'PrdInfoList"></div><div id="{id}-listEl" class="list-ct"></div>'],
                                width: 500,
                                height: 299
                            },
                            listeners: {
                                'expand': { fn: me.OnDisplayPrdGrid, scope: me },
                                'beforequery': { fn: me.OnReloadPrdGrid, scope: me }
                            }
                        };

                        col.renderer = function (value, metaData, record, rowIdx, colIdx, store, view) { return record.get('CheckID') };
                        return false;
                    }
                });
            }

            var plugins = [];

            if (me.hasDetailEdit) {
                me.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 1,
                    listeners: {
                        edit: function () { me.plDetailGrid.getView().refresh(); },
                        beforeedit: function (e, editor) { if (me.dataDeal == gpersist.DATA_DEAL_SELECT) return false; }
                    }
                });

                plugins = [me.cellEditing];
            }

            me.plDetailGrid = Ext.create('Ext.grid.Panel', {
                id: mep + 'detailgrid',
                region: 'center',
                title: me.detailTitle,
                autoScroll: true,
                frame: false,
                border: false,
                margins: '0 0 0 0',
                padding: '0 0 0 0',
                loadMask: true,
                columnLines: true,
                viewConfig: {
                    autoFill: true,
                    stripeRows: true
                },
                features: [{
                    ftype: 'summary'
                }],
                columns: me.columnDetails,
                store: me.gridDetailStore,
                selModel: { selType: 'cellmodel' },
                plugins: plugins
            });

            if (me.hasDetailEdit && me.hasDetailEditTool) {
                
                var deitems = [' ', { id: mep + 'btnDetailAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnListNew, scope: me },
						' ', { id: mep + 'btnDetailDelete', text: gpersist.STR_BTN_DELETE, iconCls: 'icon-delete', handler: me.OnListDelete, scope: me },
						' ', { id: mep + 'btnDetailReset', text: gpersist.STR_BTN_RESET, iconCls: 'icon-reset', handler: me.OnDetailRefresh, scope: me }
                        ];
                tools.SetToolbar(deitems, mep);
                var tbdetailedit = Ext.create('Ext.toolbar.Toolbar', { dock: 'top',
                    items: deitems
                });

                me.plDetailGrid.insertDocked(0, tbdetailedit);
            }

            if (me.hasPageDetail) {
                me.plGrid.insertDocked(0, Ext.create('Ext.PagingToolbar', {
                    store: me.gridDetailStore,
                    displayInfo: true,
                    displayMsg: gpersist.STR_PAGE_FMT,
                    emptyMsg: gpersist.STR_NO_DATA,
                    dock: 'bottom'
                }));
            }

            me.OnAfterCreateDetail();
        }
    },

    OnFormClose: function () {
        var me = this;

        if (me.tabMain) {
            if (me.hasExitPrompt) {
                if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
                    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_EXITLIST, function (btn) {
                        if (btn == 'yes') {
                            me.dataDeal = gpersist.DATA_DEAL_SELECT;
                            me.tabMain.setActiveTab(0);
                        }
                    });
                }
                else
                    me.tabMain.setActiveTab(0);
            }
        }
    },

    OnAuthEditForm: function (type, islayout) {
        var me = this;
        var mep = this.tranPrefix;

        me.dataDeal = type;

        if (islayout)
            me.plEdit.suspendLayouts();

        switch (type) {
            case gpersist.DATA_DEAL_SELECT:
                tools.FormDisable(me.disEdits, me.enEdits, mep);
                tools.BtnsEnable(['btnFormEdit', 'btnCopy'], mep);
                tools.BtnsDisable(['btnSave'], mep);
                if (!me.hasDetailEdit)
                    tools.BtnsDisable(['btnDetailAdd', 'btnDetailDelete', 'btnDetailReset'], mep);

                break;

            case gpersist.DATA_DEAL_NEW:
                tools.FormInit(me.disNews, me.enNews, mep);
                tools.BtnsDisable(['btnFormEdit', 'btnCopy'], mep);
                tools.BtnsEnable(['btnSave'], mep);
                if (me.hasDetailEdit)
                    tools.BtnsEnable(['btnDetailAdd', 'btnDetailDelete', 'btnDetailReset'], mep);
                break;

            case gpersist.DATA_DEAL_EDIT:
                tools.FormInit(me.disEdits, me.enEdits, mep);
                tools.BtnsDisable(['btnFormEdit', 'btnCopy'], mep);
                tools.BtnsEnable(['btnSave'], mep);
                if (me.hasDetailEdit)
                    tools.BtnsEnable(['btnDetailAdd', 'btnDetailDelete', 'btnDetailReset'], mep);
                break;
        }

        if (islayout) {
            me.plEdit.resumeLayouts(true);
            me.plEdit.doLayout();
        }
    },

    OnDetailRefresh: function () {
        var me = this;
        me.plDetailGrid.store.load();
    },

    OnShow: function () {
        var me = this;
        var record = tools.OnGridLoad(me.plGrid);

        if (me.tabMain && record) {

            me.dataDeal = gpersist.DATA_DEAL_SELECT;
            if (!me.OnLoadData(record)) {
                return;
            };


            me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
            me.tabMain.setActiveTab(1);
        }
    },

    OnInitData: function () {
        var me = this;
        var mep = this.tranPrefix;

        tools.ResetForm(me.plEdit.getForm());

        me.OnDetailRefresh();
    },

    OnLoadData: function (record) {
        return true;
    },

    OnSetData: function (item) {

    },

    OnNew: function () {
        var me = this;

        if (me.tabMain) {


            // modify 20130501

            me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);
            me.tabMain.setActiveTab(1);
            me.OnInitData();

        }
    },

    OnEdit: function () {
        var me = this;
        var record = tools.OnGridLoad(me.plGrid);

        if (me.tabMain && record) {

            me.dataDeal = gpersist.DATA_DEAL_EDIT;
            if (!me.OnLoadData(record)) {
                return;
            };

            me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
            me.tabMain.setActiveTab(1);
        }
    },

    OnDelete: function () {

    },

    OnFormEdit: function () {
        var me = this;

        me.OnAuthEditForm(gpersist.DATA_DEAL_EDIT, true);
    },

    OnFormReset: function () {

    },

    OnAfterCopy: function () {

    },

    OnCopy: function () {
        var me = this;

        if (me.dataDeal != gpersist.DATA_DEAL_SELECT)
            return;

        me.OnAuthEditForm(gpersist.DATA_DEAL_NEW, true);

        me.OnAfterCopy();
    },

    OnCheckPrevNext: function (record) {

        return false;
    },

    OnPrevRecord: function () {
        var me = this;

        if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
            Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
                if (btn == 'yes') {
                    me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
                    me.OnPrevRecord();
                }
            });
        }
        else {
            var j = me.plGrid.store.getCount(), record;
            for (var i = 0; i < j; i++) {
                record = me.plGrid.store.getAt(i).data;

                if (me.OnCheckPrevNext(record)) {
                    if (i == 0) return;

                    me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
                    return;
                }
            }
        }
    },

    OnNextRecord: function () {
        var me = this;

        if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
            Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function (btn) {
                if (btn == 'yes') {
                    me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
                    me.OnNextRecord();
                }
            });
        }
        else {
            var j = me.plGrid.store.getCount(), record;
            for (var i = 0; i < j; i++) {
                record = me.plGrid.store.getAt(i).data;

                if (me.OnCheckPrevNext(record)) {
                    if (i == j - 1) return;

                    me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
                    return;
                }
            }
        }
    },

    OnBeforeSave: function () {
        return true;
    },

    OnAfterSave: function (action) {

    },

    OnGetSaveParams: function () {

    },

    OnSave: function () {
        var me = this;
        var mep = this.tranPrefix;

        if (!me.OnBeforeSave())
            return;

        if (tools.InvalidForm(me.plEdit.getForm())) {
            me.OnGetSaveParams();

            me.plEdit.form.submit({
                clientValidation: false,
                url: tools.GetUrl(me.saveUrl) + "/" + me.dataDeal,
                params: me.saveParams,
                async: false,
                method: 'POST',
                waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
                waitTitle: gpersist.STR_DATA_WAIT_TITLE,
                timeout: 3000,
                success: function (form, action) {
                    me.OnAfterSave(action);
                    me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
                    me.OnSearch();
                    tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
                },
                failure: function (form, action) {
                    tools.ErrorAlert(action);
                }
            });
        }
    },

    OnBeforeListNew: function () {

    },

    OnListNew: function () {
        var me = this;

        var record = me.plDetailGrid.store.model.create({});
        me.OnBeforeListNew(record);
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
        if (me.cellEditing)
            me.cellEditing.startEditByPosition({ row: me.plDetailGrid.store.getCount() - 1, column: 2 });
    },

    OnListDelete: function () {
        var me = this;

        var j = me.plDetailGrid.selModel.selected.items.length;
        for (var i = 0; i < j; i++) {
            me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
        }
    },

    OnBeforePrint: function () {
        Ext.ux.grid.Printer.printTitle = this.editInfo;
    }
});