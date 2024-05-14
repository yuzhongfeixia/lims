Ext.define('alms.manbusreport', {
  extend : 'gpersist.base.busform',
  
  constructor : function(config) {
    var me = this;
    Ext.apply(config, {
      editinfo : '试验报告编制',
      winWidth : 750,
      winHeight : 350,
      hasColumnUrl : true,
      columnUrl : 'SysSqlColumn.do?sqlid=p_get_busreportaudit',
      storeUrl : 'DatSearchBusReport.do',
      saveUrl : 'DatSaveBusReport.do',
      expUrl : 'DatSearchBusReport.do',
      urlDetailColumn : 'SysSqlColumn.do?sqlid=p_get_bustaskresult',
      urlDetail : 'LabGetListBusTaskResultForReport.do',
      hasDetail : true,
      hasDetailEdit : true,
      detailTitle : '检测参数明细',
      hasPage : true,
      hasExit : true,
      hasDetailGrid : true,
      hasDetailEditTool : true,
      idPrevNext : 'reportid',
      hasGridSelect : true,
      hasDetailCheck : false,
      hasPageDetail : false,
      detailTabs : 2,
      hasDetailGridSelect : false
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit : function() {
    var me = this;
    var mep = this.tranPrefix;
    
    me.editControls =
      [{
        xtype : 'container',
        anchor : '100%',
        layout : 'column',
        items :
          [{
            xtype : 'container',
            columnWidth : .25,
            layout : 'anchor',
            items :
              [tools.FormText('样品名称', 'breport.samplename', mep + 'samplename', 20, '96%', false, 1),tools.FormText('样品数量', 'breport.samplecount', mep + 'samplecount', 40, '96%', true, 4),tools.FormText('生产单位', 'breport.factname', mep + 'factname', 40, '96%', true, 4),tools.FormText('检测依据', 'breport.teststandardname', mep + 'teststandardname', 100, '96%', true, 4)]
          },

          {
            xtype : 'container',
            columnWidth : .25,
            layout : 'anchor',
            items :
              [tools.FormText('型号规格', 'breport.samplestand', mep + 'samplestand', 50, '96%', true, 2),tools.FormText('样品状态', 'breport.samplestatus', mep + 'samplestatus', 40, '96%', true, 5),tools.FormText('抽(送)样者', 'breport.testeduser', mep + 'testeduser', 40, '96%', true, 5),tools.FormText('检验类别', 'breport.testtypename', mep + 'testtypename', 40, '96%', true, 4)]
          },

          {
            xtype : 'container',
            columnWidth : .25,
            layout : 'anchor',
            items :
              [tools.FormText('样品编号', 'breport.samplecode', mep + 'samplecode', 40, '96%', true, 4),tools.FormText('抽样地点', 'breport.getaddr', mep + 'getaddr', 200, '96%', true, 7),tools.FormText('商标', 'breport.trademark', mep + 'trademark', 20, '96%', true, 7),tools.FormCheckCombo('使用设备', 'breport.devids', mep + 'devids', tools.GetNullStore(), '96%', true, 8)]
          },{
            xtype : 'container',
            columnWidth : .25,
            layout : 'anchor',
            items :
              [tools.FormText('受检单位', 'breport.testedname', mep + 'testedname', 20, '100%', true, 7),tools.FormText('生产批号', 'breport.prdcode', mep + 'prdcode', 20, '100%', true, 7),tools.FormText('抽样基数', 'breport.samplebase', mep + 'samplebase', 20, '100%', true, 7),{
                xtype : 'hiddenfield',
                name : 'breport.reportid',
                id : mep + 'reportid'
              },{
                xtype : 'hiddenfield',
                name : 'breport.taskid',
                id : mep + 'taskid'
              },{
                xtype : 'hiddenfield',
                name : 'breport.reportstatus',
                id : mep + 'reportstatus'
              },{
                xtype : 'hiddenfield',
                name : 'breport.modifydesc',
                id : mep + 'modifydesc'
              },{
                xtype : 'hiddenfield',
                name : 'breport.devnames',
                id : mep + 'devnames'
              },{
                xtype : 'hiddenfield',
                name : 'breport.labuser',
                id : mep + 'labuser'
              },{
                xtype : 'hiddenfield',
                name : 'breport.deal.action',
                id : mep + 'datadeal'
              }]
          }]
      },tools.FormTextArea('检测项目', 'breport.testitems', mep + 'testitems', 500, '100%', true, 8, 4),tools.FormTextArea('试验环境条件', 'breport.testenv', mep + 'testenv', 100, '100%', true, 8, 4),tools.FormTextArea('检验结论', 'breport.reportresult', mep + 'reportresult', 500, '100%', true, 8, 4),tools.FormTextArea('备注', 'breport.reportrequest', mep + 'reportrequest', 200, '100%', true, 9, 4)];
    me.disNews = [];
    me.disEdits = [];
    me.enNews =
      ['testitems','testenv','reportresult','reportrequest','samplebase','prdcode','testedname','devnames','trademark','getaddr','testeduser','samplestatus','samplestand','factname','samplecount','samplename','devids','samplecode','testtypename','teststandardname'];
    me.enEdits =
      ['testitems','testenv','reportresult','reportrequest','samplebase','prdcode','testedname','devnames','trademark','getaddr','testeduser','samplestatus','samplestand','factname','samplecount','samplename','devids','samplecode','testtypename','teststandardname'];
    
    Ext.getCmp(mep + 'devids').on('select', me.OnDevSelect, me);
    
  },
  
  OnDevSelect : function() {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'devnames', Ext.getCmp(mep + 'devids').getDisplayValue());
  },
  
  OnGetDevs : function(samplecode) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.isEmpty(samplecode) || (samplecode == gpersist.SELECT_MUST_VALUE))
      return;
    
    var devs = Ext.getCmp(mep + 'devids');
    
    if (devs) {
      if (samplecode == gpersist.SELECT_MUST_VALUE) {
        devs.bindStore(tools.GetNullStore());
      } else {
        me.SetDevRecordCombo(null, samplecode, mep + 'devids');
      }
    }
  },
  
  SetDevRecordCombo : function(type, samplecode, id) {
    var typedata = tools.GetTypeList(type);
    var items = tools.JsonGet('LabGetListBusTaskDevBySamplecode.do?btd.samplecode=' + samplecode);
    
    if (items && items.data)
      Ext.each(items.data, function(item, index) {
        typedata.push({
          id : item.devid,
          name : item.devname
        });
      });
    
    alms.SetStoreToCombo(type, id, typedata);
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items =
      [' ',{
        xtype : 'textfield',
        fieldLabel : '样品编号',
        labelWidth : 60,
        width : 200,
        maxLength : 40,
        name : 'searchsamplecode',
        id : mep + 'searchsamplecode',
        allowBlank : true
      },' ',tools.GetToolBarCombo('searchreportstatus', mep + 'searchreportstatus', 180, '报告状态', 60, tools.ComboStore('ReportStatus', gpersist.SELECT_MUST_VALUE)),' ',tools.GetToolBarCombo('searchispass', mep + 'searchispass', 180, '是否合格', 60, tools.ComboStore('SendType', gpersist.SELECT_MUST_VALUE)),'-',{
        id : mep + 'btnSearch',
        text : gpersist.STR_BTN_SEARCH,
        iconCls : 'icon-find',
        handler : me.OnSearch,
        scope : me
      },
      // '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls:
      // 'icon-add', handler: me.OnNew,scope: me },
      ' ',{
        id : mep + 'btnEdit',
        text : gpersist.STR_BTN_EDIT,
        iconCls : 'icon-edit',
        handler : me.OnEdit,
        scope : me
      },'-',' ',{
        tooltip : gpersist.STR_BTN_REFRESH_NOW,
        iconCls : 'icon-pagerefresh',
        handler : me.OnResetForm,
        scope : me
      }];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {
      items : items,
      border : false
    });
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSearch : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function(store, options) {
        Ext.apply(store.proxy.extraParams, {
          'breport.reportstatus' : tools.GetEncode(tools.GetValue(mep + 'searchreportstatus')),
          'breport.ispass' : tools.GetEncode(tools.GetValue(mep + 'searchispass') == '-2' ? '' : (tools.GetValue(mep + 'searchispass') == '1' ? '合格' : '不合格')),
          'breport.samplecode' : tools.GetEncode(tools.GetValue(mep + 'searchsamplecode'))
        });
      });
    }
    ;
  },
  
  OnLoadData : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    var reportinfo = tools.JsonGet(tools.GetUrl('DatGetBusReport.do?breport.reportid=') + tools.GetEncode(tools.GetEncode(record.reportid)));
    var busgetinfo = tools.JsonGet('LabGetBusGetBySampleCode.do?bg.samplecode=' + record.samplecode);
    var busgetdetails = tools.JsonGet('LabGetListBusGetDetail.do?bgd.tranid=' + busgetinfo.tranid);
    var flag = true;
    for (var j = 0; j < busgetdetails.data.length; j++) {
      
      if (busgetdetails.data[j].samplename.indexOf(',') != -1) {
        flag = false;
        break;
      }
      
    }
    if ((reportinfo.formid == '0000000110' || reportinfo.formid == '0000000111') && flag) {
      var taskinfo = tools.JsonGet('LabGetBusTaskSingleByTaskid.do?btsg.taskid=' + record.taskid + '&btsg.samplecode=' + record.samplecode);
      
      var devinfos = tools.JsonGet('LabGetListBusTaskDevBySamplecode.do?btd.samplecode=' + record.samplecode).data;
      
      var samplename = '';
      var prdcode = '';
      var samplebase = '';
      var getaddr = '';
      var trademark = '';
      var samplestatus = '';
      var samplecount = '';
      var factname = '';
      var testitems = '';
      
      for (var i = 0; i < busgetdetails.data.length; i++) {
        
        if (!Ext.isEmpty(busgetdetails.data[i].samplename)) {
          samplename = busgetdetails.data[i].samplename + ',' + samplename;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].prdcode)) {
          prdcode = busgetdetails.data[i].prdcode + ',' + prdcode;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].getaddr)) {
          getaddr = busgetdetails.data[i].getaddr + ',' + getaddr;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].samplebase)) {
          samplebase = busgetdetails.data[i].samplebase + ',' + samplebase;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].trademark)) {
          trademark = busgetdetails.data[i].trademark + ',' + trademark;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].samplestatus)) {
          samplestatus = busgetdetails.data[i].samplestatus + ',' + samplestatus;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].samplecount)) {
          samplecount = busgetdetails.data[i].samplecount + ',' + samplecount;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].factname)) {
          factname = busgetdetails.data[i].factname + ',' + factname;
        }
        if (!Ext.isEmpty(busgetdetails.data[i].testitems)) {
          testitems = busgetdetails.data[i].testitems + ',' + testitems;
        }
      }
      
      tools.SetValue(mep + 'reportid', record.reportid);
      tools.SetValue(mep + 'samplename', samplename);
      tools.SetValue(mep + 'taskid', record.taskid);
      tools.SetValue(mep + 'samplecode', record.samplecode);
      tools.SetValue(mep + 'testenv', taskinfo.testenv);
      tools.SetValue(mep + 'testtypename', taskinfo.testpropname);
      tools.SetValue(mep + 'prdcode', prdcode);
      tools.SetValue(mep + 'testedname', busgetinfo.testedname);
      tools.SetValue(mep + 'samplebase', samplebase);
      tools.SetValue(mep + 'getaddr', getaddr);
      tools.SetValue(mep + 'trademark', trademark);
      tools.SetValue(mep + 'samplestatus', samplestatus);
      tools.SetValue(mep + 'samplestand', taskinfo.samplestand);
      tools.SetValue(mep + 'samplecount', samplecount);
      tools.SetValue(mep + 'factname', factname);
      tools.SetValue(mep + 'teststandardname', taskinfo.teststandardname);
      tools.SetValue(mep + 'reportresult', reportinfo.reportresult);
      tools.SetValue(mep + 'reportrequest', reportinfo.reportrequest);
      tools.SetValue(mep + 'modifydesc', reportinfo.modifydesc);
      tools.SetValue(mep + 'reportstatus', reportinfo.reportstatus);
      tools.SetValue(mep + 'testitems', testitems);
      tools.SetValue(mep + 'testeduser', busgetinfo.testeduser);
      tools.SetValue(mep + 'labuser', taskinfo.labuser);
      
      // 获取设备的名称、编号
      var devids = '';
      var devnames = '';
      if (devinfos) {
        if (devinfos.length > 0) {
          for (var i = 0; i < devinfos.length; i++) {
            if (i == 0) {
              devids = devinfos[i].devid;
              devnames = devinfos[i].devname;
            } else {
              devids = devids + ',' + devinfos[i].devid;
              devnames = devnames + ',' + devinfos[i].devname;
            }
          }
        }
      }
      // alert (devnames)
      me.OnGetDevs(record.samplecode);
      tools.SetValue(mep + 'devids', devids.split(','));
      
      tools.SetValue(mep + 'devnames', devnames);
      // 填写检验结论字段
      if (reportinfo.reportresult == '') {
        // 统计检测结果不合格的项目
        var judgeno = '';
        var judgenos = '';
        var taskjudges = tools.JsonGet('LabGetListBusTaskJudgeForNo.do?btj.samplecode=' + record.samplecode).data;
        if (taskjudges) {
          if (taskjudges.length > 0) {
            for (var i = 0; i < taskjudges.length; i++) {
              if (i == 0) {
                judgenos = taskjudges[i].parametername;
              } else {
                judgenos = judgenos + ',' + taskjudges[i].parametername;
              }
            }
            var reportresult = '该样品检验结果中' + judgenos + '的检验结果不符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)抽查检验不合格。';
          } else {
            var reportresult = '该样品检验结果符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)抽查检验合格。';
          }
        }
      }
      
      tools.SetValue(mep + 'reportresult', reportresult);
      me.OnDetailRefresh();
      
      return true;
      
    } else {
      var taskinfo = tools.JsonGet('LabGetBusTaskSingleByTaskid.do?btsg.taskid=' + record.taskid + '&btsg.samplecode=' + record.samplecode);
      var busgetinfo = tools.JsonGet('LabGetBusGetBySampleCode.do?bg.samplecode=' + record.samplecode);
      var busgetdetail = tools.JsonGet('LabGetBusGetDetailBySampleCode.do?bgd.samplecode=' + record.samplecode);
      var bassamplecatalog = tools.JsonGet('BasGetBasSampleCatalogBySampleID.do?bscatalog.sampleid=' + record.sampleid);
      var devinfos = tools.JsonGet('LabGetListBusTaskDevBySamplecode.do?btd.samplecode=' + record.samplecode).data;
      
      if (reportinfo && !Ext.isEmpty(reportinfo.reportid)) {
        tools.SetValue(mep + 'reportid', record.reportid);
        tools.SetValue(mep + 'samplename', busgetdetail.samplename);
        tools.SetValue(mep + 'taskid', record.taskid);
        tools.SetValue(mep + 'samplecode', record.samplecode);
        tools.SetValue(mep + 'testenv', taskinfo.testenv);
        tools.SetValue(mep + 'testtypename', taskinfo.testpropname);
        tools.SetValue(mep + 'prdcode', busgetdetail.prdcode);
        tools.SetValue(mep + 'testedname', busgetinfo.testedname);
        tools.SetValue(mep + 'samplebase', busgetdetail.samplebase);
        tools.SetValue(mep + 'getaddr', busgetdetail.getaddr);
        tools.SetValue(mep + 'trademark', busgetdetail.trademark);
        tools.SetValue(mep + 'samplestatus', busgetdetail.samplestatus);
        tools.SetValue(mep + 'samplestand', taskinfo.samplestand);
        tools.SetValue(mep + 'samplecount', busgetdetail.samplecount);
        tools.SetValue(mep + 'factname', busgetdetail.factname);
        tools.SetValue(mep + 'teststandardname', taskinfo.teststandardname);
        tools.SetValue(mep + 'reportresult', reportinfo.reportresult);
        tools.SetValue(mep + 'reportrequest', reportinfo.reportrequest);
        tools.SetValue(mep + 'modifydesc', reportinfo.modifydesc);
        tools.SetValue(mep + 'reportstatus', reportinfo.reportstatus);
        tools.SetValue(mep + 'testitems', busgetdetail.testitems);
        tools.SetValue(mep + 'testeduser', busgetinfo.testeduser);
        tools.SetValue(mep + 'labuser', taskinfo.labuser);
        
        // 获取设备的名称、编号
        var devids = '';
        var devnames = '';
        if (devinfos) {
          if (devinfos.length > 0) {
            for (var i = 0; i < devinfos.length; i++) {
              if (i == 0) {
                devids = devinfos[i].devid;
                devnames = devinfos[i].devname;
              } else {
                devids = devids + ',' + devinfos[i].devid;
                devnames = devnames + ',' + devinfos[i].devname;
              }
            }
          }
        }
        // alert (devnames)
        me.OnGetDevs(record.samplecode);
        tools.SetValue(mep + 'devids', devids.split(','));
        
        tools.SetValue(mep + 'devnames', devnames);
        
        // 样品的所有检测参数
        var allparameters = tools.JsonGet('BasGetListBasSampleParameter.do?bsampara.sampleid=' + record.sampleid).data

        // 监督抽查 时判断是否全检
        var isallcheck = ((tools.JsonGet('LabGetListBusTaskResultForReport.do?btr.samplecode=' + tools.GetValue(mep + 'samplecode')).data.length) == allparameters.length);
        // 填写检验结论字段
        if (reportinfo.reportresult == '') {
          // 统计检测结果不合格的项目
          var judgeno = '';
          var judgenos = '';
          var taskjudges = tools.JsonGet('LabGetListBusTaskJudgeForNo.do?btj.samplecode=' + record.samplecode).data;
          if (taskjudges) {
            if (taskjudges.length > 0) {
              for (var i = 0; i < taskjudges.length; i++) {
                if (i == 0) {
                  judgenos = taskjudges[i].parametername;
                } else {
                  judgenos = judgenos + ',' + taskjudges[i].parametername;
                }
              }
            }
          }
          
          // 统计合格的样品，针对转基因的。统计出未检测出的检测参数
          var judgeyes = '';
          var judgeyess = '';
          var judges = tools.JsonGet('LabGetListBusTaskJudgeForYes.do?btj.samplecode=' + record.samplecode).data;
          if (judges) {
            if (judges.length > 0) {
              for (var i = 0; i < judges.length; i++) {
                if (i == 0) {
                  judgeyess = judges[i].parametername;
                } else {
                  judgeyess = judgeyess + ',' + judges[i].parametername;
                }
              }
            }
          }
          
          if (bassamplecatalog.samplecatalog != '0401') {
            var tasksamples = tools.JsonGet('LabGetBusTaskSampleForJudge.do?bts.samplecode=' + record.samplecode).data;
            // 监督检验
            if (taskinfo.testprop == '03') {
              
              if (tasksamples) {
                // 抽样单检测结果不合格
                if (tasksamples.length > 0) {
                  if (isallcheck) {
                    var reportresult = '该样品检验结果中' + judgenos + '的检验结果不符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)抽查检验不合格。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  } else {
                    
                    var reportresult = '该样品所检项目中' + judgenos + '的检验结果不符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)抽查检验不合格。';
                    tools.SetValue(mep + 'reportresult', reportresult);
                  }
                } else {
                  // 抽样单检测结果合格
                  if (isallcheck) {
                    var reportresult = '该样品检验结果符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)抽查检验合格。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  } else {
                    var reportresult = '该样品所检项目的检验结果符合' + taskinfo.teststandardname + '的技术要求，该产品本次(或本批)所检项目抽查检验合格。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  }
                }
              }
            }
            // 委托检验
            else if (taskinfo.testprop == '02') {
              if (tasksamples) {
                
                var reportresult = '';
                // 委托检验检测结果不合格
                if (tasksamples.length > 0) {
                  reportresult = '该样品检验结果中' + judgenos + '的检验结果不符合' + taskinfo.teststandardname + '的技术要求，该样品本次检验不合格。本结论仅对来样负责。';
                  
                  tools.SetValue(mep + 'reportresult', reportresult);
                } else {
                  // 委托检验检测结果合格
                  if (bassamplecatalog.samplecatalog == '0101') {
                    
                    reportresult = '该批次产品依据“农业部办公厅关于印发茄果类蔬菜等58类无公害农产品检测目录的通知(' + taskinfo.teststandardname + ')的要求检验，结果符合规定”。';
                    
                  } else if (bassamplecatalog.samplecatalog == '0102') {
                    
                    reportresult = '根据' + taskinfo.teststandardname + '标准，在NY/T393所规定的范围内，所测项目全部合格，该批次产品符合绿色食品' + taskinfo.teststandardname + '要求。';
                    
                  }
                }
                
                tools.SetValue(mep + 'reportresult', reportresult);
              }
            }

            // 仲裁抽样
            else if (taskinfo.testprop == '06') {
              if (busgetinfo.gettype == '10') {
                if (tasksamples) {
                  // 送检产品检测结果不合格
                  if (tasksamples.length > 0) {
                    var reportresult = '该送检产(样)品(或货物)经检验符合' + taskinfo.teststandardname + '规定。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  } else {
                    // 送检产品检测结果合格
                    var reportresult = '该送检产(样)品(或货物)经检验不符合' + taskinfo.teststandardname + '规定。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  }
                }
              } else {
                if (tasksamples) {
                  // 中心抽样单检测结果不合格
                  if (tasksamples.length > 0) {
                    var reportresult = '该批产品(或货物)经检验符合' + taskinfo.teststandardname + '规定。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  } else {
                    // 中心抽样单检测结果合格
                    var reportresult = '该批产品(或货物)经检验符合' + taskinfo.teststandardname + '规定。';
                    
                    tools.SetValue(mep + 'reportresult', reportresult);
                  }
                }
              }
            }
            // 委托检验检测结果不合格
            else {
              if (tasksamples) {
                if (tasksamples.length > 0) {
                  var reportresult = '该样品检验结果中' + judgenos + '的检验结果不符合' + taskinfo.teststandardname + '的技术要求，该样品本次检验不合格。本结论仅对来样负责。';
                  
                  tools.SetValue(mep + 'reportresult', reportresult);
                } else {
                  // 委托检验检测结果合格
                  var reportresult = '根据' + taskinfo.teststandardname + '标准，所测项目全部合格，符合' + taskinfo.teststandardname + '要求。';
                  
                  tools.SetValue(mep + 'reportresult', reportresult);
                }
              }
            }
          } else if (judgeyess != '') {
            var reportresult = '该样品所检项目中未检出' + judgeyess + '。';
            tools.SetValue(mep + 'reportresult', reportresult);
          }
        }
        
        me.OnDetailRefresh();
        
        return true;
      } else {
        tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
      }
    }
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'reportid', action.result.data.reportid);
        }
      }
    }
    
    tools.BtnsEnable(
      ['Submit'], mep);
  },
  
  // 修改编辑面的按钮菜单
  OnAfterCreateEditToolBar : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems =
      [' ',{
        id : mep + 'btnSave',
        text : gpersist.STR_BTN_SAVE,
        iconCls : 'icon-save',
        handler : me.OnSave,
        scope : me
      },'-',' ',{
        id : mep + 'btnFormEdit',
        text : gpersist.STR_BTN_EDIT,
        iconCls : 'icon-edit',
        handler : me.OnFormEdit,
        scope : me
      },'-',' ',{
        id : mep + 'btnMake',
        text : '制报告',
        iconCls : 'icon-deal',
        handler : me.OnMake,
        scope : me
      },'-',{
        id : mep + 'Submit',
        text : '提交签名',
        iconCls : 'icon-deal',
        handler : me.OnSubmit,
        scope : me
      },'-',' ',{
        id : mep + 'btnResult',
        text : '查看报告',
        iconCls : 'icon-outlook',
        handler : me.OnResult,
        scope : me
      },'-',' ',{
        id : mep + 'btnBack',
        text : '查看驳回意见',
        iconCls : 'icon-outlook',
        handler : me.OnBack,
        scope : me
      },'-',' ',{
        id : mep + 'btnRecordBack',
        text : '原始记录驳回',
        iconCls : 'icon-chartpie',
        handler : me.OnRecordBack,
        scope : me
      },'-',' ',{
        id : mep + 'btnReportOther',
        text : gpersist.STR_BTN_REPORTOTHER,
        iconCls : 'icon-outlook',
        handler : me.OnReportOther,
        scope : me
      },'-',' ',{
        id : mep + 'btnClose',
        text : gpersist.STR_BTN_RETURNLIST,
        iconCls : 'icon-close',
        handler : me.OnFormClose,
        scope : me
      }];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3,
        ['-',' ',{
          id : mep + 'btnPrevRecord',
          text : gpersist.STR_BTN_PREVRECORD,
          iconCls : 'icon-prev',
          handler : me.OnPrevRecord,
          scope : me
        },' ',{
          id : mep + 'btnNextRecord',
          text : gpersist.STR_BTN_NEXTRECORD,
          iconCls : 'icon-next',
          handler : me.OnNextRecord,
          scope : me
        }]);
    }
  },
  
  OnRecordBack : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var reportstatus = tools.GetValue(mep + 'reportstatus');
    
    if (reportstatus == '08' || reportstatus == '04') {
      tools.alert('该试验报告已复核提交,不能进行驳回操作....');
      return false;
    }
    
     if(reportstatus == '12'){
     tools.alert('该样品试验报告已打印完成，不能进行复核操作.......');
     return false;
     }
        
    me.OnCreateCheckWin();
    
    if (me.winCheck) {
      me.winCheck.show();
    }
    ;
    
    tools.SetValue(mep + 'datadeal', '11');
    
  },
  OnCreateCheckWin : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.winWidth = 500;
    me.winHeight = 250;
    
    if (Ext.getCmp(mep + 'checkwin')) {
      Ext.getCmp(mep + 'checkwin').destroy();
    }
    ;
    
    var checkitems =
      [' ',{
        id : mep + 'btnCheckSave',
        text : '提交',
        iconCls : 'icon-save',
        handler : me.OnBackRecord,
        scope : me
      },'-',' ',{
        id : mep + 'btnCheckClose',
        text : gpersist.STR_BTN_CLOSE,
        iconCls : 'icon-close',
        handler : function() {
          me.winCheck.hide();
          me.detailEditType = 1;
        }
      }];
    
    me.editCheckControls =
      [tools.FormCheckCombo('检测室', 'getsource', mep + 'getsource', tools.ComboStore('Lab', ''), '96%', false),tools.FormTextArea('', 'checkdesc', mep + 'wincheckdesc', 200, '100%', true, 18, 12)];
    
    me.plCheckEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'plcheckedit',
      columnWidth : 1,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:5px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {
        items : checkitems
      }),
      items : me.editCheckControls
    });
    
    me.winCheck = Ext.create('Ext.Window', {
      id : mep + 'checkwin',
      title : '驳回意见',
      width : me.winWidth,
      height : me.winHeight,
      maximizable : true,
      closeAction : 'hide',
      modal : true,
      layout : 'fit',
      plain : false,
      closable : true,
      draggable : true,
      constrain : true,
      items :
        [me.plCheckEdit]
    });
  },
  OnBack : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnCreateWinCheck();
    
    var checkdesc = tools.GetValue(mep + 'modifydesc').replace(/\n/g, "\n");
    tools.SetValue(mep + 'wincheckdesc', checkdesc);
    
    if (me.winCheck) {
      me.winCheck.show();
    }
    ;
  },
  OnReportOther : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var reportid = tools.GetValue(mep+'reportid');
    var samplecode = tools.GetValue(mep+'samplecode');
    if (!me.winpreview) {
      me.winpreview = tools.GetPopupWindow('alms', 'reportotherpreview', 
        {mid:me.mid, tranPrefix:me.tranPrefix, childPrefix: 're', dataDeal: gpersist.DATA_DEAL_NEW});
      
      me.winpreview.OnFormLoad();
    }
    else
      me.winpreview.OnFormShow();
      
    me.winpreview.OnSetData(reportid,samplecode);
  },
  
  OnItemClick : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var record = tools.OnGridLoad(me.plGrid);
    if (record.reportstatus == '06' || record.reportstatus == '10') {
      tools.BtnsEnable(
        ['btnBack'], mep);
    } else {
      tools.BtnsDisable(
        ['btnBack'], mep);
    }
    ;
  },
  
  OnCreateWinCheck : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.winWidth = 500;
    me.winHeight = 250;
    
    if (Ext.getCmp(mep + 'checkwin')) {
      Ext.getCmp(mep + 'checkwin').destroy();
    }
    ;
    
    me.editCheckControls =
      [tools.FormTextArea('', '', mep + 'wincheckdesc', 200, '100%', false, 18, 14)];
    
    me.disNews = [];
    me.disEdits = [];
    me.enNews =
      ['wincheckdesc'];
    me.enEdits =
      ['wincheckdesc'];
    
    me.plCheckEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'plcheckedit',
      columnWidth : 1,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:5px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      // tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items : me.editCheckControls
    });
    
    me.winCheck = Ext.create('Ext.Window', {
      id : mep + 'checkwin',
      title : '驳回意见',
      width : me.winWidth,
      height : me.winHeight,
      maximizable : true,
      closeAction : 'hide',
      modal : true,
      layout : 'fit',
      plain : false,
      closable : true,
      draggable : true,
      constrain : true,
      items :
        [me.plCheckEdit]
    });
  },
  
  OnCreatePrompt : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'winPrompt')) {
      Ext.getCmp(mep + 'winPrompt').destroy();
    }
    
    if (!me.plwinPrompt) {
      me.plwinPrompt = Ext.create('Ext.form.Panel', {
        id : mep + 'plwinPrompt',
        fieldDefaults : {
          width : 370,
          labelSeparator : '：',
          labelWidth : 90,
          labelPad : 0,
          labelStyle : 'font-weight:bold;'
        },
        title : '',
        border : false,
        bodyStyle : 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width : 400,
        defaultType : 'textfield',
        items :
          [{
            fieldLabel : '密码',
            name : 'signerpassword',
            id : mep + 'signer_passwd',
            type : 'password',
            inputType : 'password',
            selectOnFocus : true,
            labelStyle : 'font-weight:bold;',
            blankText : Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_PASSWD),
            allowBlank : false
          }],
        listeners : {
          afterRender : function(form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter : me.OnSavePasswd,
              scope : me
            });
          }
        }
      });
    }
    
    if (!me.winPrompt) {
      me.winPrompt = Ext.create('Ext.window.Window', {
        id : mep + 'winPrompt',
        title : '签名密码',
        width : 400,
        height : 100,
        maximizable : true,
        modal : true,
        layout : 'fit',
        plain : false,
        buttonAlign : 'center',
        closable : true,
        closeAction : 'hide',
        border : false,
        items :
          [me.plwinPrompt],
        buttons :
          [{
            text : gpersist.STR_BTN_SUBMIT,
            id : mep + 'btnSavePassword',
            handler : me.OnCommit,
            scope : me
          },{
            text : gpersist.STR_BTN_CLOSE,
            handler : function() {
              me.winPrompt.hide();
            }
          }]
      });
    }
  },
  
  OnBackRecord : function() {
    var me = this, mep = me.tranPrefix;
    
    me.saveParams = {
      reportid : Ext.getCmp(mep + 'reportid').getValue(),
      getsource : Ext.getCmp(mep + 'getsource').getValue(),
      wincheckdesc : Ext.getCmp(mep + 'wincheckdesc').getValue()
    };
    
    console.log(me.saveParams)

    me.plEdit.form.submit({
      clientValidation : false,
      url : tools.GetUrl('DatBackBusRecord.do'),
      params : me.saveParams,
      async : false,
      method : 'POST',
      waitMsg : '请稍等,正在退回报告！',
      waitTitle : gpersist.STR_DATA_WAIT_TITLE,
      timeout : 3000,
      success : function(form, action) {
        tools.ErrorAlert('退回成功');
        
      },
      failure : function(form, action) {
        tools.ErrorAlert(action);
      }
    });
    
  },
  OnMake : function() {
    var me = this, mep = me.tranPrefix;
    
    // tools.SetValue(reportresult+'<br>jyjlgz',reportresult);
    // if (!me.OnBeforeSubmit())
    // return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      
      // me.OnGetCommitParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl('DatMakeBusReport.do'),
        // params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : '请稍等,正在生成报告！',
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          tools.ErrorAlert(action);
          // me.OnAfterCommit(action);
          me.OnSearch();
          me.OnResult();
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
    
  },
  
  OnSubmit : function() {
    var me = this, mep = me.tranPrefix;
    
    Ext.Msg.confirm(gpersist.CONFIRM_TITLE, "确定签名并提交审核吗？", function(btn) {
      if (btn == 'yes') {
        
        if (!me.winPrompt)
          me.OnCreatePrompt();
        
        if (me.winPrompt) {
          me.winPrompt.show();
          Ext.getCmp(mep + 'signer_passwd').reset();
        }
      }
    });
  },
  
  OnCommit : function() {
    var me = this, mep = me.tranPrefix;
    
    if (!me.OnBeforeSubmit())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      
      me.OnGetCommitParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl('DatSubmitBusReport.do'),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          tools.ErrorAlert(action);
          me.OnAfterCommit(action);
          me.OnSearch();
          me.OnResult();
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnGetCommitParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.saveParams = {
      signerpassword : Ext.getCmp(mep + 'signer_passwd').getValue()
    };
  },
  
  OnBeforeSubmit : function() {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'reportstatus', '02');
    tools.SetValue(mep + 'datadeal', '16');
    return true;
  },
  
  OnAfterCommit : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    tools.BtnsDisable(
      ['Submit'], mep);
    
    me.winPrompt.close();
  },
  
  OnResult : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var taskid = tools.GetValue(mep + 'taskid');
    var samplecode = tools.GetValue(mep + 'samplecode');
    var reportid = tools.GetValue(mep + 'reportid');
    
    me.OnCreateDetailResult();
    
    if (me.winDetailResult) {
      me.winDetailResult.show();
      me.winDetailResult.maximize();
      me.onScanResult(reportid, taskid, samplecode);
    }
    ;
  },
  
  OnCreateDetailResult : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwinresult')) {
      Ext.getCmp(mep + 'detailwinresult').destroy();
    }
    ;
    
    var items =
      [
      // ' ', { id: mep + 'btnPrintRecord', text: '打印检测任务单', iconCls:
      // 'icon-print', handler: me.OnPrintResult, scope: me },
      ' ',{
        id : mep + 'btnDetailResultClose',
        text : gpersist.STR_BTN_CLOSE,
        iconCls : 'icon-close',
        handler : function() {
          me.winDetailResult.hide();
        }
      }];
    
    me.editDetailControls =
      [{
        xtype : 'label',
        id : mep + 'labresulthtml',
        html : ''
      }];
    
    me.plDetailEditResult = Ext.create('Ext.form.Panel', {
      id : mep + 'pldetaileditresult',
      columnWidth : 1,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:5px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      autoScroll : true,
      scope : me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {
        items : items
      }),
      items : me.editDetailControls
    });
    
    me.winDetailResult = Ext.create('Ext.Window', {
      id : mep + 'detailwinresult',
      title : '检测判定结果',
      maximizable : true,
      closeAction : 'hide',
      modal : true,
      layout : 'fit',
      plain : false,
      closable : true,
      draggable : true,
      constrain : true,
      items :
        [me.plDetailEditResult]
    });
  },
  
  onScanResult : function(reportid, taskid, samplecode) {
    var me = this;
    var mep = me.tranPrefix;
    
    var records = tools.JsonGet(tools.GetUrl('DatGetBusReportByTask.do?btsg.samplecode=') + samplecode);
    if (records && records.data) {
      var nowdate = new Date();
      var i = 0, j = 0;
      var record;
      var rowspan = 0, colspan = 0;
      var height = 25, width = 36, nowheight = 0, nowwidth = 0;
      var form = '';
      var celltext = '';
      var style = '';
      var align = '';
      var zhuanhuan = '';
      var zhuanhuan1 = '';
      var zhuanhuan2 = '';
      
      for (var n = 0; n < records.data.length; n++) {
        var item = tools.JsonGet(tools.GetUrl('DatGetSetBusReport.do?breport.reportid=') + reportid);
        var busget = tools.JsonGet(tools.GetUrl('BasSearchBasSample.do?bsample.sampleid=')+ item.record.sampleid +'&page=1&start=0&limit=50').data[0];
        var bggz;
        if(busget.samplecatalog=='0301'){
          bggz='ny';
        }else if(busget.samplecatalog=='0201'){
          bggz='hf';
        }else{
          bggz='jy';
        }
        console.log(bggz)
        if (item && item.record && !Ext.isEmpty(item.record.reportid)) {
          for (var page = 0; page < item.details.length; page++) {
            var nowdetails;
            var fl1;
            var fl2;
            if (page == 0) {
              fl1 = 2 * item.form.formlength / 3;
              console.log(item)
              if ((item.form.formid = '0000000110')&(item.details.length>0)) {
                //                item.details.length=1;
                fl1 = item.details[page].datas[(item.details[page].datas.length) - 1].endrow + 1;
              }
              
              nowdetails = item.details[page].datas;
              if (page == item.details.length - 1) {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px;page-break-after: always; border-collapse: collapse;">';
              }
              form += '<tr>';
              for (i = 0; i < item.form.formwidth; i++) {
                form += '<td width="' + width + 'px"; height="' + height + 'px" ></td>'; 
              }
              form += '</tr>';
             
              for (i = 1; i <= fl1; i++) {
                 
                if(i==40){
                //调整内容页距离上边距
                  form += '<tr><td width="36px"; height="' + item.record.onespacing + 'px" ></td></tr>';
//                  form += '<tr><td width="36px"; height="75px" ></td></tr>';
                }
                
                form += '<tr><td ></td>';
                
                for (j = 0; j < nowdetails.length; j++) {
                  record = nowdetails[j];
                  if (record.beginrow == i) {
                    rowspan = record.endrow - record.beginrow + 1;
                    nowheight = height * rowspan;
                    colspan = record.endcolumn - record.begincolumn + 1;
                    nowwidth = width * colspan;
                    celltext = '';
                    style = '';
                    switch (record.valuesource) {
                      case '02':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'reportresult') {
                          var date = new Date();
                          celltext ='<br/>&emsp;&emsp;'+ record.celltext + '<br/><image width=\"170px\" height=\"170px\"  src=\"images/sign/' + bggz + '.png\"style=\"top:50px;left:300px; position:relative; z-index:1;\" /><br/>' + '&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;' + '&emsp;&emsp;&emsp;' + '（检验报告专用章）<br/>' + '&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;' + '&emsp;&emsp;&emsp;&emsp;&emsp;' + '签 发 日 期：' + date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日<br/><br/>';
                        }
                        
                        if((record.fieldcode == 'tranidcn' )&&(record.cellserial == '7' )) {
                          celltext = '<b><u>' + record.celltext + '</u></b>';
                        }
                        
                        break;
                      case '03':
                        celltext = '';
                        break;
                      case '01':
                        celltext = record.cellname.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'reportrequest') {
                          celltext ='&emsp;&emsp;'+ record.cellname;
                          }
                        break;
                      case '04':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      case '05':
                        zhuanhuan = record.cellname.replace(new RegExp("tips1"), "<image width=\"110px\" height=\"100px\" src=\"images/sign/tip1.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan1 = zhuanhuan.replace(new RegExp("tips2"), "<image width=\"210px\" height=\"100px\" src=\"images/sign/tip2.png\"  style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                        zhuanhuan2 = zhuanhuan1.replace(new RegExp("jczxbz"), "<image width=\"200px\" height=\"180px\" src=\"images/sign/reportlogo.png\"style=\"top:40px;left:0px; position:relative; z-index:1;\" />");
                        celltext = zhuanhuan2.replace(new RegExp("footjpg"), "<image width=\"170px\" height=\"170px\" src=\"images/sign/1.png\"style=\"top:110px;left:0px; position:relative; z-index:1;\" />");
                        celltext = celltext.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      default:
                        break;
                    }
                    celltext = record.prefixtext + celltext + record.postfixtext;
                    
                    align = '';
                    switch (record.aligntype) {
                      case '1':
                        align = ' align="left" ';
                        if ((record.fieldcode == 'reportrequest')||(record.fieldcode == 'reportresult')) {
                          style += 'padding-left:20px;';
                          style += 'padding-right:20px;';
                        }else{
                          style += 'padding-left:3px;';
                        }
                       
                        break;
                      case '2':
                        align = ' align="center" ';
                        break;
                      case '3':
                        align = ' align="right" ';
                        style += 'padding-right:3px;';
                        break;
                      default:
                        break;
                    }
                    
                    if (record.isborder)
                      style += 'border: solid 1px Black;';
                      
                    
                    if (record.isline && (record.isborder <= 0))
                      style += 'border-bottom: solid 1px Black;';
                    
                    if (record.isbold)
                      style += 'font-weight:bold;';
                    
                    style += 'font-size:' + record.fontsize + 'px;';
                    //字体
                    style += 'font-family:' + record.fonttypesign + ';';
                    
                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' + ' style="' + style + '" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                    
                    if (record.valuesource == '04') {
                      if (record.fieldcode == '{actreportid}') {
                        if (!Ext.isEmpty(celltext))
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                        else
                          form += cellhtml;
                      } else if (Ext.isEmpty(record.classsource)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + 40 + 'px" height="' + 25 + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      } else if (!Ext.isEmpty(celltext)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle" style="padding-top:3px;">' + '<image width="' + 80 + 'px" height="' + 50 + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                      } else
                        form += cellhtml;
                    } else {
                      form += cellhtml;
                    }
                    
                  }
                }
                form += '</tr>';
                
              }
              
              
            } else {
              fl2 = item.form.formlength / 3;
              nowdetails = item.details[page].datas;
              if (page == item.details.length - 1) {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; border-collapse: collapse;">';
              } else {
                form += '<table cellspacing="0" align = "center" width="92%" cellpadding="0" border="0" style="font-size:12px; page-break-after: always; border-collapse: collapse;">';
              }
              form += '<tr>';
              for (i = 0; i < item.form.formwidth; i++) {
                form += '<td width="' + width + 'px"; height="0px" ></td>';
              }
              form += '</tr>';
              //调整内容页距离上边距
              form += '<tr><td width="36px"; height="' + item.record.twospacing + 'px" ></td></tr>';
             
//              form += '<tr><td width="36px" ; height="54px"></td></tr>';
              for (i = fl1 + 1; i <= fl1 + fl2; i++) {
                form += '<tr><td></td>';
                
                for (j = 0; j < nowdetails.length; j++) {
                  record = nowdetails[j];
                  if (record.beginrow == i) {
                    rowspan = record.endrow - record.beginrow + 1;
                    nowheight = height * rowspan;
                    colspan = record.endcolumn - record.begincolumn + 1;
                    nowwidth = width * colspan;
                    celltext = '';
                    switch (record.valuesource) {
                      case '02':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");
                        if (record.fieldcode == 'tranidcn') {
                          celltext = '<b>' + record.celltext + '</b>';
                        }
                        break;
                      case '03':
                        celltext = '';
                        break;
                      case '01':
                        celltext = record.cellname.replace(new RegExp("br", "gm"), "<br/>");
                        break;
                      case '04':
                        celltext = record.celltext.replace(new RegExp("br", "gm"), "<br/>");

                        break;
                      default:
                        break;
                    }
                    celltext = record.prefixtext + celltext + record.postfixtext;
                    style = '';
                    align = '';
                    switch (record.aligntype) {
                      case '1':
                        align = ' align="left" ';
                        style += 'padding-left:3px;';
                        break;
                      case '2':
                        align = ' align="center" ';
                        break;
                      case '3':
                        align = ' align="right" ';
                        style += 'padding-right:3px;';
                        break;
                      default:
                        break;
                    }
                    
                    if (record.isborder)
                      style += 'border: solid 1px Black;';
                    
                    if (record.isline && (record.isborder <= 0))
                      style += 'border-bottom: solid 1px Black;';
                    
                    if (record.isbold)
                      style += 'font-weight:bold;';
                    
                    style += 'font-size:' + record.fontsize + 'px;';
                    style += 'font-family:' + record.fonttypesign + ';';
                    
                    cellhtml = '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" ' + ' style="' + style + '" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + (record.valuesource == '03' ? celltext : (Ext.isEmpty(celltext) ? '/' : celltext)) + '</td>';
                    
                    if (record.valuesource == '04') {
                      if (  record.fieldcode == '{actreportid}') {
                        if (!Ext.isEmpty(celltext))
                          form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + nowheight + 'px" src="barcode?msg=' + celltext + '&type=code128&fmt=png&height=12&hrsize=3mm" /></td>';
                        else
                          form += cellhtml;
                      } else if (Ext.isEmpty(record.classsource)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image width="' + nowwidth + 'px" height="' + 20 + 'px" src="images/sign/' + record.fieldcode + '.jpg" /></td>';
                      } else if (!Ext.isEmpty(celltext)) {
                        form += '<td width="' + nowwidth + 'px" height="' + nowheight + 'px" border="0" ' + (rowspan == 1 ? '' : 'rowspan ="' + rowspan + '" ') + (colspan == 1 ? '' : 'colspan ="' + colspan + '" ') + align + ' valign="middle">' + '<image height="' + 20 + 'px" src="images/sign/' + celltext + '.jpg" /></td>';
                      } else
                        form += cellhtml;
                    } else {
                      form += cellhtml;
                    }
                  }
                }
                form += '</tr>';
              }
              
              fl1 = fl1 + fl2;
            }
            
            form += '</table>';
          }
          
        } else {
          tools.alert(Ext.String.format(gpersist.STR_FMT_READ, '试验报告'));
          return '';
        }
        
      }
      
      me.disphtml += form.replace(/\<br \/\>/g, '');
      
      var html = '<table cellspacing="0" cellpadding="0" border="0" style="width:98%;">' + 
        '<tr><td align="center">' + form + '</td></tr></table>';
      Ext.fly(Ext.getCmp(mep + 'labresulthtml').getEl()).update(html);
        
    }
    
    return true;
  },
  
  OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(
          ['btnFormEdit'], mep);
        tools.BtnsDisable(
          ['btnSave','Submit'], mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(
          ['Submit','btnFormEdit'], mep);
        tools.BtnsEnable(
          ['btnSave'], mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
  
  OnSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (!me.OnBeforeSave())
      return;
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      tools.SetValue(mep + 'datadeal', me.dataDeal);
      
      me.OnGetSaveParams();
      
      me.plEdit.form.submit({
        clientValidation : false,
        url : tools.GetUrl(me.saveUrl),
        params : me.saveParams,
        async : false,
        method : 'POST',
        waitMsg : gpersist.STR_DATA_WAIT_MESSAGE,
        waitTitle : gpersist.STR_DATA_WAIT_TITLE,
        timeout : 3000,
        success : function(form, action) {
          me.isNowCopy = false;
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnAfterSave(action);
          me.OnSearch();
          me.OnFormValidShow();
          tools.alert(Ext.String.format(gpersist.STR_FMT_SAVE_OK, me.editInfo));
        },
        failure : function(form, action) {
          tools.ErrorAlert(action);
        }
      });
    }
  },
  
  OnBeforeSave : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var reportstatus = tools.GetValue(mep + 'reportstatus');
    
    // if(reportstatus=='01'||reportstatus=='06'||reportstatus=='10'){
    //      
    // }else{
    // tools.alert('该试验报告审批中或审核完成，不能进行修改操作...');
    // return false;
    // }
    return true;
  },
  
  OnAfterCreateDetail : function() {
    var me = this, mep = this.tranPrefix;
    
    var fileColumn = [];
    var fileField = [];
    
    tools.GetGridColumnsByUrl(tools.GetUrl('SysSqlColumn.do?sqlid=p_get_bustaskattach'), fileColumn, fileField, mep + '_recordfile_');
    
    me.fileStore = tools.CreateGridStore(tools.GetUrl('LabGetListBusReportFile.do'), fileField);
    
    me.fileGrid = Ext.create('Ext.grid.Panel', {
      region : 'center',
      title : '试验报告附件',
      autoScroll : true,
      frame : false,
      border : false,
      margins : '0 0 0 0',
      padding : '0 0 0 0',
      loadMask : true,
      columnLines : true,
      viewConfig : {
        autoFill : true,
        stripeRows : true
      },
      columns : fileColumn,
      store : me.fileStore,
      selModel : new Ext.selection.CheckboxModel({
        injectCheckbox : 1,
        checkOnly : true
      }),
      listeners : {
        'itemdblclick' : {
          fn : me.OnListSelectFile,
          scope : me
        }
      }
    });
    me.fileStore.load();
    me.plDetail.add(me.fileGrid);
    me.fileitemstar =
      [' ',{
        id : mep + 'btnFileAdd',
        text : gpersist.STR_BTN_NEW,
        iconCls : 'icon-add',
        handler : me.OnListNewFile,
        scope : me
      },' ',{
        id : mep + 'btnFileDelete',
        text : gpersist.STR_BTN_DELETE,
        iconCls : 'icon-delete',
        handler : me.OnListDeleteFile,
        scope : me
      }];
    
    tools.SetToolbar(me.fileitemstar, mep);
    
    var tbfile = Ext.create('Ext.toolbar.Toolbar', {
      dock : 'top',
      items : me.fileitemstar
    });
    me.fileGrid.insertDocked(0, tbfile);
  },
  
  OnBeforeCreateDetailEditFile : function() {
    var me = this;
    var mep = this.tranPrefix;
    
    me.OnInitGridToolBar();
    me.editDetailControlsFile =
      [{
        xtype : 'hiddenfield',
        name : 'attachtype',
        id : mep + 'attachtypefile'
      },{
        xtype : 'hiddenfield',
        name : 'attachurl',
        id : mep + 'attachurlfile'
      },{
        xtype : 'hiddenfield',
        name : 'attachname',
        id : mep + 'attachnamefile'
      }];
    me.disDetailControlsFile =
      ['attachnamefile'];
    me.enDetailControlsFile =
      ['attachtypefile','attachurlfile'];
  },
  
  OnCreateDetailWinFile : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwinfile')) {
      Ext.getCmp(mep + 'detailwinfile').destroy();
    }
    ;
    
    var itemsfile =
      [' ',{
        id : mep + 'btnDetailSaveFile',
        text : gpersist.STR_BTN_SAVE,
        iconCls : 'icon-save',
        handler : me.OnListSaveFile,
        scope : me
      }
      // '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE,
      // iconCls: 'icon-close', handler: function () { me.winDetail.hide(); }}
      ];
    
    me.OnBeforeCreateDetailEditFile();
    
    me.plDetailEditFile = Ext.create('Ext.form.Panel', {
      id : mep + 'pldetaileditfile',
      region : 'north',
      // height : '18%',
      columnWidth : 1,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      bodyStyle : 'padding:5px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {
        items : itemsfile
      }),
      items : me.editDetailControlsFile
    });
    
    var uploadFile = tools.SwfUpload();
    me.plFileDetailGrid = Ext.create('Ext.form.Panel', {
      id : mep + 'filedetailgrid',
      region : 'center',
      columnWidth : 1,
      scope : me,
      items :
        [uploadFile]
    });
    
    uploadFile.on('showdetail', me.OnShowDetailFile, me);
    uploadFile.on('closewin', me.OnCloseWinFile, me);
    
    me.winDetailFile = Ext.create('Ext.Window', {
      id : mep + 'detailwinfile',
      title : '试验报告附件',
      width : 600,
      height : 370,
      maximizable : false,
      closeAction : 'hide',
      modal : true,
      layout : 'border',
      plain : false,
      closable : true,
      draggable : true,
      constrain : true,
      items :
        [me.plDetailEditFile,me.plFileDetailGrid]
    });
  },
  
  OnListNewFile : function() {
    var me = this;
    me.OnCreateDetailWinFile();
    if (me.winDetailFile) {
      me.winDetailFile.show();
    }
    ;
  },
  
  OnCloseWinFile : function() {
    var me = this;
    var mep = this.tranPrefix;
    me.winDetailFile.hide();
  },
  
  OnListSelectFile : function(view, record) {
    alms.PopupFileShow(record.data.attachname + '附件预览', 'FileDownFile.do', record.data.attachurl, record.data.attachname);
  },
  
  OnShowDetailFile : function(render, item) {
    var me = this;
    var mep = this.tranPrefix;
    
    var attachname = Ext.getCmp(mep + 'attachnamefile').getValue();
    var attachurl = Ext.getCmp(mep + 'attachurlfile').getValue();
    
    if (item) {
      if (attachname == "") {
        attachname = item.name;
      } else {
        attachname = attachname + ',' + item.name
      }
      ;
      if (attachurl == "") {
        attachurl = item.url;
      } else {
        attachurl = attachurl + ',' + item.url;
      }
      ;
      tools.SetValue(mep + 'attachnamefile', attachname);
      tools.SetValue(mep + 'attachurlfile', attachurl);
    }
    ;
  },
  
  OnListSaveFile : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    var attachname = Ext.getCmp(mep + 'attachnamefile').getValue();
    var attachurl = Ext.getCmp(mep + 'attachurlfile').getValue();
    var attachtype = Ext.getCmp(mep + 'attachtypefile').getValue();
    
    if (tools.InvalidForm(me.plDetailEditFile.getForm())) {
      if (attachname == "" || attachname == null || attachname == undefined) {
        tools.alert('请上传附件！');
        return;
      } else {
        if (me.detailEditType == 1) {
          // 可能有多个附件的情况
          var attachnames = attachname.split(",");
          var attachurls = attachurl.split(",");
          for (i = 0; i < attachnames.length; i++) {
            var record = me.fileGrid.store.model.create({});
            record.data.attachname = attachnames[i];
            record.data.attachtype = '03';
            record.data.attachtypename = '试验报告附件';
            record.data.attachurl = attachurls[i];
            me.fileGrid.store.insert(me.fileGrid.store.getCount(), record);
          }
          ;
        } else {
          me.OnBeforeListSaveFile(me.detailRecord);
          me.fileGrid.getView().refresh();
        }
        ;
      }
      ;
      me.winDetailFile.hide();
    }
    ;
  },
  
  OnListDeleteFile : function() {
    var me = this;
    var j = me.fileGrid.selModel.selected.items.length;
    
    for (var i = 0; i < j; i++) {
      me.fileGrid.store.remove(me.fileGrid.selModel.selected.items[0]);
    }
    ;
    
    me.fileGrid.getView().refresh();
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('LabGetListBusTaskResultForReport.do?btr.samplecode=' + tools.GetValue(mep + 'samplecode'));
      me.plDetailGrid.store.load();
    }
    ;
    
    if (me.fileGrid && me.fileGrid.store) {
      me.fileGrid.store.proxy.url = tools.GetUrl('LabGetListBusReportFile.do?bref.tranid=') + tools.GetValue(mep + 'reportid');
      me.fileGrid.store.load();
    }
  },
  
  OnGetSaveParams : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.hasDetail) {
      var details = [];
      for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
        details.push(me.plDetailGrid.store.getAt(i).data);
      }
      
      var filedetails = [];
      for (var i = 0; i < me.fileGrid.store.getCount(); i++) {
        filedetails.push(me.fileGrid.store.getAt(i).data);
      }
      
      me.saveParams = {
        details : Ext.encode(details),
        filedetails : Ext.encode(filedetails)
      };
    }
  },
  
  OnListSelect : function() {
    
  },
  
  OnAfterCreateDetailToolBar : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.deitems = [];
  }

});
