Ext.define('alms.planview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '年度培训计划查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userplanyear',
      storeUrl: 'StaffSearchUserPlanYearApproved.do',
      expUrl: 'StaffSearchUserPlanYearApproved.do',
      hasPage: true,
      hasExit: true,
      idPrevNext: 'tranid',
      hasGridSelect: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '年度', labelWidth: 40, width: 200, maxLength: 40, name: 'searchtranyear', id: mep + 'searchtranyear', allowBlank: true }
     ];
     var items = [
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnView', text: '查看', iconCls: 'icon-outlook', handler: me.OnShow, scope: me },
      '-', ' ', { xtype: 'numberfield', fieldLabel: gpersist.STR_BTN_PAGESIZE, editable: false, labelWidth: gpersist.CON_PAGESIZE_LABLE, width: gpersist.CON_PAGESIZE_WIDTH,
        id: mep + 'btnPageSize', name: 'btnPageSize', step: gpersist.CON_PAGESIZE_STEP, value: gpersist.DEFAULT_PAGESIZE, minValue:gpersist.CON_PAGESIZE_MIN, maxValue: gpersist.CON_PAGESIZE_MAX},
      ' ', { text: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];
    
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
                   
    me.editControls = [
      { xtype: 'label', id: mep + 'planapplyhtml', html: '' },
      {xtype:'hiddenfield',name:'da.tranid',id: mep + 'tranid'}
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
  
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
           'userplan.tranyear':tools.GetValueEncode(mep+'searchtranyear'),
           'userplan.tranid':tools.GetValueEncode(mep+'searchtranid')
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
    var record = tools.OnGridLoad(me.plGrid, '请选择年度计划申请！');
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
  
  OnShowUserPlanYearView:function(record){
    var me = this;
    var mep = me.tranPrefix;
    var html = me.ShowUserPlanYearHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'planapplyhtml').getEl()).update(html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowUserPlanYearView(item);
    }
    return true;
  },
  
  ShowUserPlanYearHtml: function(record){
    var planapply = tools.JsonGet('StaffGetUserPlanYear.do?userplan.tranid='+record.tranid);
    var planapplydetail = tools.JsonGet('StaffGetListUserPlanYearDetail.do?plandetail.relaid=' + record.tranid).data;
    var html = '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
    html += '<tr class="infotrheader"><td colspan="8" align="center" width="100%" style="height: 40px;font-size:20px"><b>'+record.tranyear+'年度培训计划查看</b></td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">业务编号</td>' +
      '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(planapply.tranid)+ '</td>' +
      '<td class="infoat" align="center" width="10%">计划年度</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(planapply.tranyear)+'</td>'+
      '<td class="infoat" align="center" width="10%">申请人</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(planapply.tranusername)+'</td>' +
      '<td class="infoat" align="center" width="10%">申请日期</td>' +
      '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(planapply.trandate)+'</td></tr>';
    
    html += '<tr class="infotr"><td class="infoat" align="center" width="10%">申请说明</td>' +
      '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapply.tranremark)+'</td></tr>';
    
    html += '</table>';
    
    if(planapplydetail.length>0){
      for(var i = 0; i < planapplydetail.length; i++){
        html += '<br/>';
        html += '<table align="center" cellspacing="0" cellpadding="0" border="0" style="font-size:12px;" width="80%">';
        html += '<tr class="infotr"><td class="infoat" align="center" width="10%">检测室</td>' +
          '<td class="infoc infoleft" align="left" width="15%">' +alms.GetItemData(planapplydetail[i].deptname)+ '</td>' +
          '<td class="infoat" align="center" width="10%">培训形式</td>' +
          '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(planapplydetail[i].traintypename)+'</td>'+
          '<td class="infoat" align="center" width="10%">计划建立人</td>' +
          '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemData(planapplydetail[i].tranusername)+'</td>'+
          '<td class="infoat" align="center" width="10%">培训时间</td>' +
          '<td class="infoc infoleft" align="left" width="15%">'+alms.GetItemDateData(planapplydetail[i].traindate)+'</td></tr>';
        
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">经费预算(元)</td>' +
          '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].trainfee)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">总体目标</td>' +
         '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].traintarget)+'</td></tr>';
      
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">原则与要求</td>' +
          '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].trainobject)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">培训内容与方式</td>' +
         '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].traincontent)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">监督与考核</td>' +
         '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].trainrequest)+'</td></tr>';
       
       html += '<tr class="infotr"><td class="infoat" align="center" width="10%">备注</td>' +
         '<td class="infoc infoleft" colspan=7  align="left" width="90%">'+alms.GetItemData(planapplydetail[i].trainremark)+'</td></tr>';
       
       
        html += '</table>';
      }
    }
    
    
    return html
    
  }
  
});
