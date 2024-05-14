Ext.define('gpersist.searchtranlog', {
  extend: 'gpersist.base.listdateform',  
  
  constructor : function(config) {
    var me = this;
    
    Ext.apply(config, {
      columnUrl: 'p_get_tranlog', 
      storeUrl: gpersist.ACTION_SEARCH_TRAN, 
      expUrl: gpersist.ACTION_SEARCH_TRAN
    });

    me.callParent(arguments);
  },
  
  OnBeforePrint: function (params) {
    Ext.ux.grid.Printer.printTitle = '操作日志';
  },
  //单击展示
// OnItemClick: function () {
//  var me = this;
//  var mep = me.tranPrefix;
//  var datas = me.plGrid.getView().getSelectionModel().getSelection();
//  console.log(datas)
//  },
  //双击展示
  OnShow: function () {
    var me = this;
    var mep = me.tranPrefix;
    var datas = me.plGrid.getView().getSelectionModel().getSelection()[0].data;
    var alertmsg;
    alertmsg='操作人员：'+datas.username;
    alertmsg=alertmsg+'<br>操作部门：'+datas.deptname;
    var date = Ext.Date.format(datas.trandate,"Y-m-d H:i:s");
    alertmsg=alertmsg+'<br>操作日期：'+date;
    alertmsg=alertmsg+'<br>操作菜单：'+datas.mname;
    alertmsg=alertmsg+'<br>处理名称：'+datas.tranactionname;
    alertmsg=alertmsg+'<br>处理编号：'+datas.trandesc;
    if(!Ext.isEmpty(datas.trandescdetail)){
      alertmsg=alertmsg+'<br>操作详细：';
      var items = datas.trandescdetail.split(';');
      for (var i = 0; i < items.length; i++) {
        alertmsg=alertmsg+'<br>&#8195;&#8195;&#8195;&#8195;'+items[i];
      }
    }
    tools.tranlogalert(alertmsg);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent();
    
    me.plGrid.store.on('beforeload', function (store, options) {      
      Ext.apply(store.proxy.extraParams, {
        'req.begindate': tools.GetValue(mep + 'searchbegindate') ? Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d') : '', 
        'req.enddate': tools.GetValue(mep + 'searchenddate') ? Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d') : ''});
    });
  }
});