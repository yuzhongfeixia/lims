Ext.define('alms.nonstdview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '非标检测方法查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_stdnonstd',
      storeUrl: 'StdSearchStdNonstd.do',
      expUrl: 'StdSearchStdNonstd.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
     
    });
    me.callParent(arguments);
  },
  
  
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '检测方法名称', labelWidth: 90, width: 180, maxLength: 40, name: 'searchtestmethname', id: mep + 'searchtestmethname', allowBlank: true },           
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'nonstdhtml', html: '' },
      {xtype:'hiddenfield',name:'stdnonstd.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
      ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
  },
  
  
//  OnBeforeSearch: function () {
//    var me = this;
//    var mep = me.tranPrefix;
//    
//    if (me.gridStore) {
//      me.gridStore.on('beforeload', function (store, options) {      
//        Ext.apply(store.proxy.extraParams, {
//          'bgn.flowstatus': '02'
//        });
//      });
//    };
//  },
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'stdnonstd.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'stdnonstd.testmethname': tools.GetEncode(tools.GetValue(mep + 'searchtestmethname'))
        });
      });
    };
  },
  OnCreateEditWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
    ];
    
    me.OnAfterCreateEditToolBar();
    tools.SetEditToolbar(me.editToolItems, mep);
    
    me.OnBeforeCreateEdit();
    me.tbEdit = Ext.create('Ext.toolbar.Toolbar', {items: me.editToolItems});
    
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      region : 'north',
      height:'100%',
      autoScroll:true,
      fieldDefaults : {
        labelSeparator : '：',
        labelWidth : gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad : 0,
        labelStyle : 'font-weight:bold;'
      },
      frame : true,
      title : '',
      bodyStyle : 'padding:' + me.editPadding + 'px;background:#FFFFFF',
      defaultType : 'textfield',
      closable : false,
      header : false,
      unstyled : true,
      scope : me,
      tbar : me.tbEdit,
      items : me.editControls
    });
    me.OnAfterCreateEdit();
  },
  
  OnShow:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid, '请选择设备购买申请！');
    if (!record) 
      return;
    
    if (me.tabMain && record) {      
      me.dataDeal = gpersist.DATA_DEAL_SELECT;
      if (!me.OnLoadData(record)) {
        return;
      };
      me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, false);
      me.cancelRecord = record;
    };
  },
  
  OnShowStdNonStdView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowStdNonStdHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'nonstdhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowStdNonStdView(item);
      viewFile = function (fileurl,filename) {
          alms.PopupFileShow('文件预览', 'FileDownFile.do', fileurl, filename);
      };
    }
    return true;
  },
  
  ShowStdNonStdHtml: function(record){
    var nonstd = tools.JsonGet('StdGetStdNonstd.do?stdnonstd.tranid='+record.tranid);
    
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>非标检测方法查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(nonstd.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">检测方法名称</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(nonstd.testmethname)+'</td>'+
      '<td class="infoat" align="center" width="10%">提出人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(nonstd.propuser)+'</td>'+
      '<td class="infoat" align="center" width="10%">提出人姓名</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(nonstd.propusername)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">提议日期</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="15%">' +alms.GetItemDateData(nonstd.propdate)+ '</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">方法来源</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(nonstd.methsource)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">理由</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(nonstd.reason)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">试验记录</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(nonstd.trialrecord)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
    '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(nonstd.remark)+'</td></tr>';
    
    html += '</table>';
    
    var attaches = tools.JsonGet(tools.GetUrl('LabGetListBusTaskAttach.do?bta.tranid=') + record.tranid).data;
    if(attaches){
      if(attaches.length>0){
        html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">图谱明细附件信息</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
          '<td class="infoat" align="center" width="10%">附件名称</td>' +
          '<td class="infoat" align="center" width="10%">附件类型</td>' +
          '<td class="infoat" align="center" width="10%">预览</td></tr>';

        for(var i=0;i<attaches.length;i++){
        	var attachname = attaches[i].attachname;
        	var attachtype = attaches[i].attachtypename;
        	var attachurl = attaches[i].attachurl;
          html += '<tr class="infotr">' +
          '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
        }
      }
    }
    html += '</table>';
    
    
    var attaches = tools.JsonGet(tools.GetUrl('LabGetListBusRecordFile.do?brf.tranid=') + record.tranid).data;
    if(attaches){
      if(attaches.length>0){
        html += '<table align = "center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="90%">';
        html += '<tr class="infotr" ><td class="infoat" colspan=20 align="center" width="100%" style="font-size:14px;">原始记录表</td></tr>';
        
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">序号</td>' +
          '<td class="infoat" align="center" width="10%">附件名称</td>' +
          '<td class="infoat" align="center" width="10%">附件类型</td>' +
          '<td class="infoat" align="center" width="10%">预览</td></tr>';

        for(var i=0;i<attaches.length;i++){
        	var attachname = attaches[i].attachname;
        	var attachtype = attaches[i].attachtypename;
        	var attachurl = attaches[i].attachurl;
          html += '<tr class="infotr">' +
          '<td class="infoc infoleft"  align="center" width="10%">' +(i+1)+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachname+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%">' +attachtype+ '</td>' +
          '<td class="infoc infoleft"  align="center" width="10%"><a href="javascript:void(0);" style="cursor:pointer;" onclick="viewFile('+"'"+attachurl+"','"+attachname+"'"+')">'+'预览</a></td></tr>';
        }
      }
    }
    html += '</table>';
    return html
    
  }
  
  

  
});
