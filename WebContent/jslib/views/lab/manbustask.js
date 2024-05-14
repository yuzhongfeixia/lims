Ext.define('alms.manbustask', {
    extend: 'gpersist.base.busform',
    constructor: function (config) {
        var me = this;
        Ext.apply(config, {
            editinfo: '单任务单下达',
            columnUrl: 'SysSqlColumn.do?sqlid=p_get_busaccsample',
            storeUrl: 'LabSearchBusAccSample.do',
            saveUrl: 'LabSaveBusTaskSingle.do',
            saveListUrl: 'LabSaveBusTaskSingleList.do',
            expUrl: 'LabSearchBusAccSample.do',
            urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_bussampleparam',
            urlDetail: 'LabGetListBusSampleParam.do',
            hasDetail: true,
            hasDetailEdit: true,
            detailTitle: '检测项目',
            hasPage: true,
            hasExit: true,
            hasDetailGrid: true,
            hasDetailEditTool: true,
            idPrevNext: 'tranid',
            hasGridSelect: true,
            hasDetailCheck: false,
            hasPageDetail: false
        });
        me.callParent(arguments);
    },

    OnBeforeFormLoad: function () {
        var me = this;
        var mep = me.tranPrefix;
        var nowdate = new Date();
        me.OnInitGridToolBar();

        var items = [
       ' ', {
                xtype: 'textfield',
                fieldLabel: '样品编号',
                labelWidth: 60,
                width: 180,
                maxLength: 40,
                name: 'searchsamplecode',
                id: mep + 'searchsamplecode',
                allowBlank: true
            },
       ' ', {
                xtype: 'textfield',
                fieldLabel: '样品名称',
                labelWidth: 60,
                width: 180,
                maxLength: 40,
                name: 'searchsamplename',
                id: mep + 'searchsamplename',
                allowBlank: true
            },
       ' ', {
                xtype: 'textfield',
                fieldLabel: '受检单位',
                labelWidth: 60,
                width: 180,
                maxLength: 40,
                name: 'searchtestedname',
                id: mep + 'searchtestedname',
                allowBlank: true
            },
       '-', tools.GetToolBarCombo('searchgettype', mep + 'searchgettype', 160, '取样类型', 60, tools.ComboStore('GetType',
                gpersist.SELECT_MUST_VALUE)),
       '-', tools.GetToolBarCombo('searchissend', mep + 'searchissend', 160, '下达状态', 70, tools.ComboStore('IsSend',
                gpersist.SELECT_MUST_VALUE)),
       '-', tools.GetToolBarCombo('searchtype', mep + 'searchtype', 160, '样品类型', 70, tools.ComboStore('SampleType',
                gpersist.SELECT_MUST_VALUE))
    ];

        var items1 = [
      '-', {
                xtype: 'datefield',
                fieldLabel: '收样开始日期',
                labelWidth: 90,
                width: 200,
                name: 'searchbegindate',
                id: mep + 'searchbegindate',
                format: 'Y-m-d',
                value: Ext.Date.add(nowdate, Ext.Date.MONTH, -1),
                selectOnFocus: false,
                allowBlank: true
            },
      '-', {
                xtype: 'datefield',
                fieldLabel: '收样结束时间',
                labelWidth: 90,
                width: 200,
                name: 'searchenddate',
                id: mep + 'searchenddate',
                format: 'Y-m-d',
                value: nowdate,
                selectOnFocus: false,
                allowBlank: true
            },
      ' ', {
                id: mep + 'btnSearch',
                text: gpersist.STR_BTN_SEARCH,
                iconCls: 'icon-find',
                handler: me.OnSearch,
                scope: me
            },
      '-', {
                id: mep + 'btnEdit',
                text: '处理',
                iconCls: 'icon-deal',
                handler: me.OnEdit,
                scope: me
            },
      '-', {
                id: mep + 'btnCancel',
                text: '任务单撤销',
                iconCls: 'icon-deal',
                handler: me.OnCancel,
                scope: me
            },
      ' ', {
                id: mep + 'btnListSave',
                text: '批量下达',
                iconCls: 'icon-save',
                handler: me.OnListTask,
                scope: me
            },
      '-', {
                text: gpersist.STR_BTN_REFRESH_NOW,
                iconCls: 'icon-pagerefresh',
                handler: me.OnResetForm,
                scope: me
            }
    ];

        tools.SetToolbar(items, mep);
        tools.SetToolbar(items1, mep);
        var toolbar = Ext.create('Ext.toolbar.Toolbar', {
            items: items,
            border: false
        });
        var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {
            items: items1,
            border: false
        });
        me.tbGrid.add(toolbar, toolbar1);
    },

    OnBeforeCreateEdit: function () {
        var me = this;
        var mep = this.tranPrefix;
        var nowdate = new Date();
        me.OnInitGridToolBar();

        me.editControls = [
            {
                xtype: 'container',
                anchor: '100%',
                layout: 'column',
                items: [
                    {
                        xtype: 'container',
                        columnWidth: .25,
                        layout: 'anchor',
                        items: [
          tools.FormText('样品名称 ', 'bt.samplename', mep + 'samplename', 20, '96%', true, null, null, 90),
          tools.FormText('规格型号', 'bt.samplestand', mep + 'samplestand', 200, '96%', true, null, null, 90)
        ]
                    },
                    {
                        xtype: 'container',
                        columnWidth: .25,
                        layout: 'anchor',
                        items: [
          tools.FormCombo('检验性质', 'bt.testprop', mep + 'testprop', tools.ComboStore('TestProp', ''), '96%', false, 2,
                                90),
          tools.FormDate('要求完成时间', 'bt.reqdate', mep + 'reqdate', 'Y-m-d', '96%', true, 7, nowdate, 90)

        ]
                    },
                    {
                        xtype: 'container',
                        columnWidth: .25,
                        layout: 'anchor',
                        items: [
          tools.FormText('检验依据', 'bt.teststandardname', mep + 'teststandardname', 500, '96%', true, null, null, 90),
          tools.FormDate('下达日期', 'bt.senddate', mep + 'senddate', 'Y-m-d', '96%', true, null, nowdate, 90)
        ]
                    },
                    {
                        xtype: 'container',
                        columnWidth: .25,
                        layout: 'anchor',
                        items: [
          tools.FormText('样品状态 ', 'bt.samplestatus', mep + 'samplestatus', 20, '100%', true, null, null, 90),
          tools.FormCombo('下达人', 'bt.senduser', mep + 'senduser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE),
                                '100%', false, 2, 90)
        ]
                    }
      ]
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.taskid',
                id: mep + 'taskid'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.tranid',
                id: mep + 'tranid'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.accsampleid',
                id: mep + 'accsampleid'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.sampleid',
                id: mep + 'sampleid'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.modifydesc',
                id: mep + 'modifydesc'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.samplecode',
                id: mep + 'samplecode'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.sampletype',
                id: mep + 'sampletype'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.issend',
                id: mep + 'issend'
            },
            {
                xtype: 'hiddenfield',
                name: 'bt.deal.action',
                id: mep + 'datadeal'
            }
    ];

        me.disNews = [];
        me.disEdits = [];
        me.enNews = ['reqdate', 'testprop', 'labid', 'samplestatus',
                 'samplecode', 'samplename', 'teststandardname',
                 'samplestand', 'labuser', 'senduser', 'senddate'];
        me.enEdits = ['reqdate', 'testprop', 'labid', 'samplestatus',
                  'samplecode', 'samplename', 'teststandardname',
                  'samplestand', 'labuser', 'senduser', 'senddate'];

    },

    OnBeforeSearch: function () {
        var me = this;
        var mep = me.tranPrefix;
        if (me.gridStore) {
            me.gridStore.on('beforeload', function (store, options) {
                Ext.apply(store.proxy.extraParams, {
                    //          'bas.sampletype': '01',
                    'bas.samplename': tools.GetEncode(tools.GetValue(mep +
                        'searchsamplename')),
                    'bas.samplecode': tools.GetEncode(tools.GetValue(mep +
                        'searchsamplecode')),
                    'bas.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'),
                        'Y-m-d'),
                    'bas.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'),
                        'Y-m-d'),
                    'bas.gettype': tools.GetEncode(tools.GetValue(mep + 'searchgettype')),
                    'bas.issendstatus': tools.GetEncode(tools.GetValue(mep + 'searchissend')),
                    'bas.sampletype': tools.GetEncode(tools.GetValue(mep + 'searchtype'))
                });
            });
        };
    },

    OnAfterCreateEditToolBar: function () {
        var me = this;
        var mep = me.tranPrefix;

        me.editToolItems = [
      ' ', {
                id: mep + 'btnSave',
                text: '下达',
                iconCls: 'icon-save',
                handler: me.OnSave,
                scope: me
            },
      '-', ' ', {
                id: mep + 'btnFormEdit',
                text: '处理',
                iconCls: 'icon-deal',
                handler: me.OnFormEdit,
                scope: me
            },
      '-', ' ', {
                id: mep + 'btnPrevRecord',
                text: gpersist.STR_BTN_PREVRECORD,
                iconCls: 'icon-prev',
                handler: me.OnPrevRecord,
                scope: me
            },
      ' ', {
                id: mep + 'btnNextRecord',
                text: gpersist.STR_BTN_NEXTRECORD,
                iconCls: 'icon-next',
                handler: me.OnNextRecord,
                scope: me
            },
      '-', ' ', {
                id: mep + 'btnClose',
                text: gpersist.STR_BTN_RETURNLIST,
                iconCls: 'icon-close',
                handler: me.OnFormClose,
                scope: me
            }
    ];
    },

    OnBeforeCreateDetailEdit: function () {
        var me = this;
        var mep = this.tranPrefix;
        me.winWidth = 600,
            me.winHeight = 260,
            me.OnInitGridToolBar();
        me.editDetailControls = [
            {
                xtype: 'container',
                anchor: '100%',
                layout: 'column',
                items: [
                    {
                        xtype: 'container',
                        columnWidth: .5,
                        layout: 'anchor',
                        items: [
          tools.FormCombo('检测参数', 'parameterid', mep + 'parameterid', tools.GetNullStore(), '96%', false, 2),
          tools.FormCombo('判定方法', 'testjudge', mep + 'testjudge', tools.ComboStore('JudgeType', gpersist.SELECT_MUST_VALUE),
                                '96%', true, 3),
          tools.FormCombo('检测方法', 'teststandard', mep + 'teststandard', tools.ComboStore('TestStandard', gpersist.SELECT_MUST_VALUE),
                                '96%', true, 3),
                                
           tools.FormText('检测限', 'deteclimit', mep + 'deteclimit', 10, '96%', true,3,'isdecimal6')
        ]
                    },
                    {
                        xtype: 'container',
                        columnWidth: .5,
                        layout: 'anchor',
                        items: [
          tools.FormText('单位', 'paramunit', mep + 'paramunit', 20, '96%', true),
          tools.FormText('标准值', 'standvalue', mep + 'standvalue', 200, '96%', true),
          tools.FormCombo('检测依据', 'judgetandard', mep + 'judgestandard', tools.ComboStore('JudgeStandard', gpersist.SELECT_MUST_VALUE),
                                '96%', true, 3),
         tools.FormText('参数排序', 'parameterorder', mep + 'parameterorder', 2, '96%', false,3,'isinteger2')
        ]
                    },

                    {
                        xtype: 'hiddenfield',
                        name: 'parametername',
                        id: mep + 'parametername'
                    },
                    {
                        xtype: 'hiddenfield',
                        name: 'teststandardcode',
                        id: mep + 'teststandardcode'
                    },
                    {
                        xtype: 'hiddenfield',
                        name: 'judgestandardcode',
                        id: mep + 'judgestandardcode'
                    }
     ]
            }
    ];
        me.disDetailControls = [];
        me.enDetailControls = ['parameterid', 'testjudge', 'teststandard', 'paramunit','deteclimit', 'parameterorder',  'standvalue',
            'judgestandard'];
    },

    OnListNew: function () {
        var me = this;
        var mep = me.tranPrefix;
        me.OnCreateDetailWin();
        if (me.winDetail) {
            me.winDetail.show();
            me.detailEditType = 1;
            me.OnAuthDetailEditForm(false);
        };
        me.OnInitComboParam();
    },

    OnInitComboParam: function () {
        var me = this;
        var mep = me.tranPrefix;
        var paramdata = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
//        var samplecode = tools.GetValue(mep + 'samplecode');
//        var params = tools.JsonGet('LabGetBusSampleParamBySample.do?bmp.samplecode=' + samplecode);
        var params = tools.JsonGet('LabGetBusSampleParamBySample.do');
        var param = Ext.getCmp(mep + 'parameterid');
        if (params) {
            Ext.each(params.data, function (item, index) {
                paramdata.push({
                    id: item.parameterid,
                    name: item.parametername
                });
            });
        }
        param.bindStore(new Ext.data.JsonStore({
            fields: ['id', 'name'],
            data: paramdata
        }))
        tools.SetValue(mep + 'parameterid', gpersist.SELECT_MUST_VALUE);
    },

    OnListSave: function () {
        var me = this;
        var mep = me.tranPrefix;

        var samplecode = tools.GetValue(mep + 'samplecode');
        var parameterid = tools.GetValue(mep + 'parameterid');
        var store = me.plDetailGrid.store;

        //    var param = tools.JsonGet('LabGetBusSampleParamBySample.do?bmp.samplecode='+samplecode+'&bmp.parameterid='+parameterid);
        if (tools.InvalidForm(me.plDetailEdit.getForm())) {
            if (me.detailEditType == 1) {

                for (var i = 0; i < store.getCount(); i++) {
                    if (store.getAt(i).data.parameterid == parameterid) {
                        tools.alert('当前检测参数已存在！');
                        return;
                    }
                }

                var record = me.plDetailGrid.store.model.create({});
                record.data.parameterid = tools.GetValue(mep + 'parameterid');
                record.data.testjudge = tools.GetValue(mep + 'testjudge');
                record.data.standvalue = tools.GetValue(mep + 'standvalue');
                record.data.teststandard = tools.GetValue(mep + 'teststandard');
                record.data.judgestandard = tools.GetValue(mep + 'judgestandard');
                record.data.paramunit = tools.GetValue(mep + 'paramunit');
                record.data.deteclimit = tools.GetValue(mep + 'deteclimit');
                record.data.parameterorder = tools.GetValue(mep + 'parameterorder');
                record.data.parametername = Ext.getCmp(mep + 'parameterid').getDisplayValue();
                record.data.teststandardcode = Ext.getCmp(mep + 'teststandard').getDisplayValue();
                record.data.judgestandardcode = Ext.getCmp(mep + 'judgestandard').getDisplayValue();
                me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
            } else {
                me.OnBeforeListSave(me.detailRecord);
                me.plDetailGrid.getView().refresh();
            };
            me.winDetail.hide();
        };
    },
    OnListDelete: function () {
        var me = this;
        var j = me.plDetailGrid.selModel.selected.items.length;

        for (var i = 0; i < j; i++) {
            me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
        };

        me.plDetailGrid.getView().refresh();
    },

    OnDetailRefresh: function () {
        var me = this;
        var mep = me.tranPrefix;
        if (me.plDetailGrid && me.plDetailGrid.store) {
            me.plDetailGrid.store.proxy.url = tools.GetUrl(
                'LabGetListBusSampleParamByAcc.do?bsp.samplecode=' + tools.GetValue(mep + 'samplecode') +
                '&bsp.tranid=' + tools.GetValue(mep + 'tranid'));
            me.plDetailGrid.store.load();
        };
    },

    OnInitData: function () {
        var me = this;
        var mep = me.tranPrefix;

        me.callParent(arguments);
        tools.SetValue(mep + 'labid', gpersist.SELECT_MUST_VALUE);
        tools.SetValue(mep + 'testtype', gpersist.SELECT_MUST_VALUE);
    },

    OnLoadDetailData: function (record) {
        var me = this;
        var mep = me.tranPrefix;

        me.OnInitComboParam();
        tools.SetValue(mep + 'parametername', record.parametername);
        tools.SetValue(mep + 'parameterid', record.parameterid);
        tools.SetValue(mep + 'testjudge', record.testjudge);
        tools.SetValue(mep + 'standvalue', record.standvalue);
        tools.SetValue(mep + 'teststandardcode', record.teststandardcode);
        tools.SetValue(mep + 'judgestandardcode', record.judgestandardcode);
        tools.SetValue(mep + 'teststandard', record.teststandard);
        tools.SetValue(mep + 'judgestandard', record.judgestandard);
        tools.SetValue(mep + 'paramunit', record.paramunit);
        tools.SetValue(mep + 'parameterorder', record.parameterorder);
        tools.SetValue(mep + 'deteclimit', record.deteclimit);
    },

    OnLoadData: function (record) {
        var me = this;
        var mep = me.tranPrefix;
        var ldate = Ext.Date.add(new Date(), Ext.Date.DAY, 15);
        var item = tools.JsonGet('LabGetBusAccSampleByTranID.do?bas.sampletype=' + record.sampletype +
            '&bas.tranid=' + record.tranid);
        var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode=' + record.samplecode);

        var userinfo = gpersist.GetUserInfo();
        var sampleparam = [];

        console.log(record)

        if (record.flowstatus == '89') {
            tools.alert('撤销原因:<br/>              ' +
                record.modifydesc);
            return false;
        }
        if (item && !Ext.isEmpty(item.tranid)) {
            tools.SetValue(mep + 'sampleid', item.sampleid);
            tools.SetValue(mep + 'tranid', record.tranid);
            tools.SetValue(mep + 'samplecode', item.samplecode);

            //填写样品状态
            if (record.issend) {
                tools.SetValue(mep + 'samplestatus', item.samplestatus);
            } else {
                tools.SetValue(mep + 'samplestatus', busgetdetail.samplestatus);
            }

            tools.SetValue(mep + 'samplename', item.samplename);
            tools.SetValue(mep + 'samplestand', busgetdetail.samplestand);
            tools.SetValue(mep + 'labid', gpersist.SELECT_MUST_VALUE);
            tools.SetValue(mep + 'teststandardname', item.mainstandname);
            tools.SetValue(mep + 'testprop', item.testprop);
            tools.SetValue(mep + 'senduser', userinfo.user.userid);
            tools.SetValue(mep + 'senddate', item.senddate == null ? new Date() : item.senddate);
            tools.SetValue(mep + 'reqdate', item.reqdate == null ? ldate : item.reqdate);
            tools.SetValue(mep + 'accsampleid', record.tranid);
            tools.SetValue(mep + 'issend', record.issend);
            tools.SetValue(mep + 'sampletype', record.sampletype);
            //      //判断委托书是否填写检测那些参数 
            //      if(record.gettype == '10'){
            //        sampleparam = tools.JsonGet('LabGetListBusSampleParamForTask.do?bsp.samplecode='+ item.samplecode+'&bsp.tranid='+ record.tranid).data ;
            //      }

            if (record.issend) {
                me.OnDetailRefresh();
            } else {
                me.OnInitParam(record.gettype);
            }
            return true;
        } else {
            tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
        };
    },

    OnInitParam: function (gettype) {
        var me = this;
        var mep = me.tranPrefix;
        var samplecode = tools.GetValue(mep + 'samplecode');

        if (gettype == '10') {
            var param = tools.JsonGet('LabGetBusSampleParamByConsign.do?bmp.samplecode=' + samplecode);

        } else {
            var param = tools.JsonGet('LabGetBusSampleParamBySample.do?bmp.samplecode=' + samplecode);
        }

        me.plDetailGrid.store.removeAll();
        for (var i = 0; i < param.data.length; i++) {
            var record = me.plDetailGrid.store.model.create({});
            record.data.parametername = param.data[i].parametername;
            record.data.testjudge = param.data[i].testjudge
            record.data.standvalue = param.data[i].standvalue;
            record.data.teststandardcode = param.data[i].teststandardcode;
            record.data.judgestandardcode = param.data[i].judgestandardcode;
            record.data.samplecode = param.data[i].samplecode;
            record.data.sampleid = param.data[i].sampleid;
            record.data.parameterid = param.data[i].parameterid;
            record.data.teststandard = param.data[i].teststandard;
            record.data.judgestandard = param.data[i].judgestandard;
            record.data.paramunit = param.data[i].paramunit;
            record.data.deteclimit = param.data[i].deteclimit;
            record.data.parameterorder = param.data[i].parameterorder;
            me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
        }
    },

    OnBeforeListSave: function (record) {
        var me = this;
        var mep = me.tranPrefix;
        record.data.parameterid = tools.GetValue(mep + 'parameterid');
        record.data.testjudge = tools.GetValue(mep + 'testjudge');
        record.data.standvalue = tools.GetValue(mep + 'standvalue');
        record.data.teststandard = tools.GetValue(mep + 'teststandard');
        record.data.judgestandard = tools.GetValue(mep + 'judgestandard');
        record.data.paramunit = tools.GetValue(mep + 'paramunit');
        record.data.deteclimit = tools.GetValue(mep + 'deteclimit');
        record.data.parameterorder = tools.GetValue(mep + 'parameterorder');
//
//        record.data.checkdesc = tools.GetValue(mep + 'checkdesc');

        record.data.parametername = Ext.getCmp(mep + 'parameterid').getDisplayValue();
        record.data.teststandardcode = Ext.getCmp(mep + 'teststandard').getDisplayValue();
        record.data.judgestandardcode = Ext.getCmp(mep + 'judgestandard').getDisplayValue();
    },

    OnBeforeSave: function () {
        var me = this;
        var mep = me.tranPrefix;
        var issend = tools.GetValue(mep + 'issend');
        if (issend == 'true') {
            tools.alert("该样品任务单已下达到相应检测室.....");
            return false;
        } else {
            return true;
        }
    },

    OnAfterSave: function (action) {
        var me = this;
        var mep = me.tranPrefix;
        if (action.result && action.result.data) {
            if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
                if (action.result.data.taskid) {
                    tools.SetValue(mep + 'taskid', action.result.data.taskid);
                };
            };
        };
        me.OnDetailRefresh();
    },

    OnAfterCreateEdit: function () {
        var me = this;
        var mep = me.tranPrefix;
        me.plEdit.height = '15%';
    },
    OnSingleCancel: function (action) {
        var me = this;
        var mep = me.tranPrefix;
        var datas = me.plGrid.getView().getSelectionModel().getSelection();


        //	    for (var i = 0; i < datas.length; i++) {
        var tranid = me.plGrid.selModel.selected.items[0].data.tranid;
        var datadeal = tools.GetValue(mep + 'datadeal');
        var tranid = me.plGrid.selModel.selected.items[0].data.tranid;
        var modifydesc = tools.GetValue(mep + 'wincheckdesc')
        //	        if(datadeal == '9'){
        //	       
        //	          tools.SetValue(mep+'flowstatus','89');
        //	        }


        var param = tools.JsonGet('LabCancelBusTaskSingle.do?bt.tranid=' + tranid + '&bt.modifydesc=' +
            modifydesc);
        console.log(param + 'param')

        tools.alert(param.msg);
        //	  	    }

        me.winCheck.hide();
    },




    OnCancel: function () {
        var me = this;
        var mep = me.tranPrefix;
        var datas = me.plGrid.getView().getSelectionModel().getSelection();

        if (datas.length < 1) {
            tools.alert('请选择任务单........');
            return false;

        } else if (datas.length > 1) {
            tools.alert('请选择单项任务单........');
            return false;
        } else {
            var flowstatus = me.plGrid.selModel.selected.items[0].data.flowstatus;

            if (flowstatus == '89') {
                tools.alert('该任务单已撤销........');
                return false;
            }
            if (flowstatus == '85') {
                tools.alert('该任务单还未下达，无需撤销........');
                return false;
            }
            me.OnCreateCheckWin();
            if (me.winCheck) {
                me.winCheck.show();
            };

            tools.SetValue(mep + 'datadeal', '9');
        }


    },

    OnCreateCheckWin: function () {
        var me = this;
        var mep = me.tranPrefix;

        me.winWidth = 500;
        me.winHeight = 250;

        if (Ext.getCmp(mep + 'checkwin')) {
            Ext.getCmp(mep + 'checkwin').destroy();
        };

        var checkitems = [
			      ' ', {
                id: mep + 'btnCheckSave',
                text: '提交',
                iconCls: 'icon-save',
                handler: me.OnSingleCancel,
                scope: me
            },
			      '-', ' ', {
                id: mep + 'btnCheckClose',
                text: gpersist.STR_BTN_CLOSE,
                iconCls: 'icon-close',
                handler: function () {
                    me.winCheck.hide();
                    me.detailEditType = 1;
                }
            }
			    ];

        me.editCheckControls = [
			      tools.FormTextArea('', 'checkdesc', mep + 'wincheckdesc', 200, '100%', true, 18, 12)
			    ];

        me.disNews = [];
        me.disEdits = [];
        me.enNews = ['wincheckdesc'];
        me.enEdits = ['wincheckdesc'];

        me.plCheckEdit = Ext.create('Ext.form.Panel', {
            id: mep + 'plcheckedit',
            columnWidth: 1,
            fieldDefaults: {
                labelSeparator: '：',
                labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
                labelPad: 0,
                labelStyle: 'font-weight:bold;'
            },
            frame: true,
            title: '',
            bodyStyle: 'padding:5px;background:#FFFFFF',
            defaultType: 'textfield',
            closable: false,
            header: false,
            unstyled: true,
            scope: me,
            tbar: Ext.create('Ext.toolbar.Toolbar', {
                items: checkitems
            }),
            items: me.editCheckControls
        });

        me.winCheck = Ext.create('Ext.Window', {
            id: mep + 'checkwin',
            title: '撤销理由',
            width: me.winWidth,
            height: me.winHeight,
            maximizable: true,
            closeAction: 'hide',
            modal: true,
            layout: 'fit',
            plain: false,
            closable: true,
            draggable: true,
            constrain: true,
            items: [me.plCheckEdit]
        });
    },


    OnBeforeReset: function () {
        return true;
    },

    OnResetForm: function () {
        var me = this;

        if (!me.OnBeforeReset())
            return;

        me.OnFormLoad(me.mid);
    },
   
    OnListTask: function () {
        var me = this;
        var mep = me.tranPrefix;
        var datas = me.plGrid.getView().getSelectionModel().getSelection();
        var BusSampleParam = [];
        var BusAccSample = [];

        var enddate = new Date();
        enddate.setDate(enddate.getDate() + 15); //设置天数 -1 天
        var reqdate = Ext.Date.format(enddate, "Y-m-d");
        var sendate = new Date();
        var senddate = Ext.Date.format(sendate, "Y-m-d");


        for (var i = 0; i < datas.length; i++) {
            var issend = me.plGrid.selModel.selected.items[i].data.issend;

            if (issend == true) {
                tools.alert("您选择的样品任务单含有已下达任务单.....");
                return false;
            }
            var param1 = {
                samplecode: me.plGrid.selModel.selected.items[i].data.samplecode,
                tranid: me.plGrid.selModel.selected.items[i].data.tranid,
                sampleid: me.plGrid.selModel.selected.items[i].data.sampleid
            };
            var param2 = {
                tranid: me.plGrid.selModel.selected.items[i].data.tranid,
                sampletype: me.plGrid.selModel.selected.items[i].data.sampletype,
                reqdate: reqdate,
                senddate: senddate,
                getid: me.plGrid.selModel.selected.items[i].data.getid,
                accuser: me.plGrid.selModel.selected.items[i].data.accuser,
                issend: me.plGrid.selModel.selected.items[i].data.issend
            };

            BusSampleParam.push(param1);
            BusAccSample.push(param2)

        }
        me.plEdit.form.submit({
            clientValidation: false,
            url: tools.GetUrl(me.saveListUrl),
            params: {
                bspsss: Ext.encode(BusSampleParam),
                basss: Ext.encode(BusAccSample)
            },
            async: false,
            method: 'POST',
            waitMsg: gpersist.STR_DATA_WAIT_MESSAGE,
            waitTitle: gpersist.STR_DATA_WAIT_TITLE,
            timeout: 3000,
            success: function (form, action) {
                me.OnResetForm();
                tools.alert(Ext.String.format("下达成功", me.editInfo));


            },
            failure: function (form, action) {
                tools.ErrorAlert(action);
            }
        })
    }
});