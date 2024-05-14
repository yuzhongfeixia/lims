Ext.define('alms.workview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员岗位考核查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userexamrecord',
      storeUrl: 'StaffSearchUserExamRecord.do',
      expUrl: 'StaffSearchUserExamRecord.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'examid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();

    var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-deal', handler: me.OnShow, scope: me },
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
      { xtype: 'label', id: mep + 'workexamhtml', html: '' },
      {xtype:'hiddenfield',name:'examrecord.examid',id: mep + 'examid'}
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
    var record = tools.OnGridLoad(me.plGrid, '请选择人员岗位考核！');
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
  
  OnShowUserExamRecordView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowUserExamRecordHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'workexamhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.examid)) {
      tools.SetValue(mep + 'examid',item.examid);
      me.tabMain.setActiveTab(1);
      me.OnShowUserExamRecordView(item);
    }
    return true;
  },
  
  ShowUserExamRecordHtml: function(record){
    var workexam = tools.JsonGet('StaffGetUserExamRecord.do?examrecord.examid='+record.examid);
    var workexamdetail = tools.JsonGet('StaffGetListUserExamDetail.do?examdetail.examid=' + record.examid).data;
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>人员岗位考核查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(workexam.examid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">批准人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexam.username)+'</td>'+
      '<td class="infoat" align="center" width="10%">所属部门</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexam.deptname)+'</td>'+
      '<td class="infoat" align="center" width="10%">人员岗位</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexam.userpostname)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemDateData(workexam.examdate)+ '</td>' +
      '<td class="infoat" align="center" width="10%">综合考核意见</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexam.examadvicename)+'</td>'+
      '<td class="infoat" align="center" width="10%">批准人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexam.approveusername)+'</td>'+
      '<td class="infoat" align="center" width="10%">批准日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(workexam.approvedate)+'</td></tr>';
  
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核内容</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(workexam.examcontent)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">需要整改的问题</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(workexam.examdesc)+'</td></tr>';
    
    html += '</table>';
    
    if(workexamdetail.length>0){
      for(var i = 0; i < workexamdetail.length; i++){
        html += '<br/>';
        html += '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">考核项目</td>' +
          '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(workexamdetail[i].examitemname)+ '</td>' +
          '<td class="infoat" align="center" width="10%">考核成绩</td>' +
          '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(workexamdetail[i].examscorename)+'</td></tr>';
        
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
          '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(workexamdetail[i].examitemdesc)+'</td></tr>';
       
        html += '</table>';
      }
    }
    
    
    return html
    
  }
  
});
