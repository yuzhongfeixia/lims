Ext.define('alms.devacceptview', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '供应商查看',
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_devacceptmanage',
      storeUrl: 'DevSearchAcceptManage.do',
      expUrl: 'DevSearchAcceptManage.do',
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
      ' ', { xtype: 'textfield', fieldLabel: '设备编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevid', id: mep + 'searchdevid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '设备名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchdevname', id: mep + 'searchdevname', allowBlank: true },
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
      { xtype: 'label', id: mep + 'devacceptviewhtml', html: '' },
      {xtype:'hiddenfield',name:'am.tranid',id: mep + 'tranid'}
    ];
      
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnPrintRecord', text: '打印', iconCls: 'icon-print', handler: me.OnPrintTask, scope: me },
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
          'am.devid': tools.GetEncode(tools.GetValue(mep + 'searchdevid')),
          'am.devname': tools.GetEncode(tools.GetValue(mep + 'searchdevname'))
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
    var record = tools.OnGridLoad(me.plGrid, '请选择设备！');
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
  
  OnShowDevAcceptView:function(record){
    var me = this;
    var mep = me.tranPrefix;
     me.html = me.ShowDevAcceptViewHtml(record);
    Ext.fly(Ext.getCmp(me.tranPrefix + 'devacceptviewhtml').getEl()).update(me.html);
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    
    if(!Ext.isEmpty(item.tranid)) {
      tools.SetValue(mep + 'tranid',item.tranid);
      me.tabMain.setActiveTab(1);
      me.OnShowDevAcceptView(item);
    }
    return true;
  },
  
  //预览html内容
  ShowDevAcceptViewHtml:function(item){
     var me = this;
     var mep = me.tranPrefix;
     var record = tools.JsonGet('DevGetAcceptManage.do?am.tranid='+item.tranid);
     var acceptfiledetail = tools.JsonGet('DevGetListAcceptFileDetail.do?afd.tranid=' + record.tranid).data;
     var acceptpartdetail = tools.JsonGet('DevGetListAcceptPartsDetail.do?apd.acceptid=' + record.tranid).data;
     
    var html = '<table align = "center" cellspacing="0" cellpadding="0" border="0" height="15%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotrheader"><td colspan="10" align="center" width="100%" style="height: 40px;font-size:20px"><b>设备验证(收)安装调试记录</b></td></tr>';
     html+='<tr class="infotrheader" border=0><td colspan="5" align="left" width="50%" >NO：</td><td colspan="5" align="right" width="50%" >QCR4-5-2</td></tr>';
     html += '</table>';
     
     html += '<table cellspacing="0" cellpadding="0"  border="1" align="center" height="20%" style="font-size:12px;" width="90%">';
     html += '<tr class="infotr"  ><td class="infoat" align="center" width="10%">设备名称</td>' +
       '<td class="infoc infoleft" align="left"  width="40%">'+alms.GetItemData(record.devname) +'</td>'+
       '<td class="infoat" align="center"  " width="10%">出厂编号</td>' +
       '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(record.factorycode) +'</td></tr>';

     html += '<tr class="infotr"><td class="infoat" align="center"   width="10%">型号规格</td>' +
       '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(record.devstandard) +'</td>'+
       '<td class="infoat" align="center"  width="10%">价    格</td>' +
       '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(record.devprice) +'（万元）</td></tr>';
     
     html += '<tr class="infotr"><td class="infoat" align="center"   width="10%">生产厂家</td>' +
       '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(record.factoryname) +'</td>'+
       '<td class="infoat" align="center" " width="10%">进场日期</td>' +
       '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemDateData(record.enterdate) +'</td></tr>';
     
     html += '<tr class="infotr" style="height: 60px"><td class="infoat" align="center"  width="10%">主要技术参数</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(record.devrange) +'</td></tr>';

  
     html += '<tr class="infotr" style="height: 60px"><td class="infoat" align="center"  width="10%">随机附件及数量</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+ me.GetParts(acceptpartdetail)  +'</td></tr>';
     html += '<tr class="infotr" style="height: 60px"><td class="infoat" align="center"  width="10%">随机资料</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+me.GetFiles(acceptfiledetail) +'</td></tr>';

     html += '<tr class="infotr"><td class="infoat" align="center"   width="10%">安装单位</td>' +
     '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(record.instalunit) +'</td>'+
     '<td class="infoat" align="center"  width="10%">安 装 时 间</td>' +
     '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemDateData(record.instaldate) +'</td></tr>';
     
     html += '<tr style="height: 90px"class="infotr"><td class="infoat" align="center"  width="10%">设备安装调试情况</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(record.instaldesc) +'</td></tr>';

     html += '<tr class="infotr" style="height: 40px"><td class="infoat" align="center"  width="10%">安装调试人</td>' +
     '<td class="infocs" align="left"  colspan=4>' + record.instaluser + '</td></tr>';
    
     html += '<tr class="infotr" style="height: 70px"><td class="infoat" align="center"  width="10%">设备验收结论（不合格时处理结果）</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(record.acceptremark)+'</td></tr>';
    
     html += '<tr class="infotr" style="height: 50px"><td class="infoat" align="center"  width="10%">参加验收人员</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(record.devoperatorsname)+'</td></tr>';
     var tech = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevAccept&htodo.flownode=tech');
     var approver = tools.JsonGet('FlowGetHBusTodo.do?htodo.tranid='+item.tranid+'&htodo.busflow=DevAccept&htodo.flownode=approve');
     
     html += '<tr class="infotr"><td class="infoat" align="center"  width="10%">备注</td>' +
     '<td class="infoc infoleft" align="left" colspan=4  width="40%">'+alms.GetItemData(record.remark)+'</td></tr>';
     
     (JSON.stringify(approver) == "{}")? html += '<tr class="infotr"><td class="infoat" align="center"  " width="10%">使用部门签名</td>' +
     '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(item.labname) +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=2px color="blue">日期：</font>'+alms.GetItemDateData(record.instaldate)+'</td>'+
     '<td class="infoat" align="center"  width="10%">技术负责人签名 </td>' +'<td class="infoc infoleft" align="left" width="40%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=2px color="blue">日期：</font></td></tr>'
     : html += '<tr class="infotr"><td class="infoat" align="center"  " width="10%">使用部门签名</td>' +
     '<td class="infoc infoleft" align="left" width="40%">'+alms.GetItemData(item.labname) +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=2px color="blue">日期：</font>'+alms.GetItemDateData(record.instaldate)+'</td>'+
     '<td class="infoat" align="center"  width="10%">技术负责人签名 </td>' +
     '<td class="infoc infoleft" align="left" width="40%">'+'<image height="35px" src="images/sign/' +alms.GetItemData(tech.tranuser) + '.jpg" />' +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<font size=2px color="blue">日期：</font>'+alms.GetItemDateData(record.instaldate) +'</td></tr>';
     
     html += '</table>';
     html + '</td></tr>';
     html += '</table>';
     return html;
   },
   
   OnPrintTask:function(){
     var me = this;
     var mep = me.tranPrefix;
     Ext.ux.Print.LimsPrint.print(me.html);
   },
   
   GetParts:function(parts){
     result ='';
     if(parts.length>0){
       for(var i = 0; i < parts.length; i++){
         if(i==0){
           result += (parts[i].partsname +'('+parts[i].partscount +')');
         }else {
           result +=(',' + parts[i].partsname +'('+parts[i].partscount +')');
         }
       }
     }
     return result;
   },
   
   GetFiles:function(files){
     if(files.length>0){
       result ='';
       for(var i = 0; i < files.length; i++){
         if(i==0){
           result += (files[i].filename +'('+files[i].filecount +')');
         }else {
           result +=(',' + files[i].filename +'('+files[i].filecount +')');
         }
       }
     }
     return result;
   }
  
});
