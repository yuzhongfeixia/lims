Ext.define('alms.manaccsample', {
  extend: 'gpersist.base.listeditform',

  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '收样管理',
      winWidth: 750,
      winHeight: 300,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_busaccsample',
      storeUrl: 'LabSearchBusAccSampleAll.do',
      saveUrl: 'LabSaveBusAccSample.do',
      hasGridSelect: true,
      expUrl: 'LabSearchBusAccSample.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var accitems = [
      ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 70, width: 200, maxLength: 200, name: 'searchtested', id: mep + 'searchtested', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnTestedSearch, scope: me }
    ];
    
    var tested = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '受检单位', name: 'bas.testedname', id: mep + 'testedname', winTitle: '受检单位',
      maxLength: 200, maxLengthText: '受检单位不能超过200字！', selectOnFocus: false, labelWidth: 100,
      blankText: '受检单位不能为空！', allowBlank: false, anchor: '100%', tabIndex: 1,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_allbusget',
      storeUrl: 'LabSearchBusGetForAcc.do',
      editable:false,
      searchTools:accitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    
    tested.on('griditemclick', me.OnTestedSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormDate('收样日期', 'bas.accdate', mep + 'accdate', 'Y-m-d', '96%', true, 7, nowdate,90),
          tools.FormText('联系人', 'bas.testeduser', mep + 'testeduser', 40, '96%', false, 9,null,90),
          tools.FormText('检验费用(元)', 'bas.testfee', mep + 'testfee', 40, '96%', false, 9,null,90)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tested,
          tools.FormText('咨询电话', 'bas.acctele', mep + 'acctele', 40, '100%', true, 4,null,100),
          tools.FormText('优惠后收费(元)', 'bas.feestatus', mep + 'feestatus', 40, '100%', true, 9,null,100),
          {xtype:'hiddenfield',name:'bas.tranid',id: mep + 'tranid'},
          {xtype:'hiddenfield',name:'bas.getid',id: mep + 'getid'},
          {xtype:'hiddenfield',name:'bas.gettype',id: mep + 'gettype'},
          {xtype:'hiddenfield',name:'bas.testedid',id: mep + 'testedid'},
          {xtype:'hiddenfield',name:'bas.entertele',id: mep + 'entertele'},
          {xtype:'hiddenfield',name:'issend',id: mep + 'issend'},
          {xtype:'hiddenfield',name:'bas.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      tools.FormTextArea('样品存放地点', 'bas.sampleplace', mep + 'sampleplace', 200, '100%', true, null, 4, 90),
      tools.FormTextArea('备注', 'bas.remark', mep + 'remark', 200, '100%', true, null, 4, 90)
    ];
//    me.disNews = ['accdate','testeduser'];
//    me.disEdits = ['accdate','testeduser'];
    me.disNews = ['testeduser'];
    me.disEdits = ['testeduser'];
    me.enNews = ['testedname','feestatus','remark','acctele','testfee','sampleplace'];
    me.enEdits = ['testedname','feestatus','remark','acctele','testfee','sampleplace'];
    
    
    Ext.getCmp(mep +'testfee').on('change',me.OnPrdNumber,me);
  },
  
  OnPrdNumber:function(){
     var me = this;
     var mep = me.tranPrefix;
     var testfee = tools.GetValue(mep + 'testfee');
     var feestatus;
     
     if(!isNaN(testfee)){
    	 feestatus = testfee ;
     }else{
       factnumber = 0;
     }
     
     tools.SetValue(mep + 'feestatus',feestatus);
  },
	   
  
  OnTestedSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var tested = Ext.getCmp(mep+'testedname');

    if(tested.store){
      tested.store.on('beforeload', function (store, options) {   
        Ext.apply(store.proxy.extraParams, {
          'bg.testedname': tools.GetValueEncode(mep + 'searchtested')
        });
      });
    }
    tested.store.loadPage(1);
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments);
    
    tools.SetValue(mep + 'acctele','025—86229784');
    tools.SetValue(mep + 'sampleplace','收样室');
    tools.SetValue(mep + 'testfee','/');
    tools.SetValue(mep + 'feestatus','/');
    tools.SetValue(mep + 'remark','');
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    var nowdate =new Date();
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchsamplename', id: mep + 'searchsamplename', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '受检单位', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtestedname', id: mep + 'searchtestedname', allowBlank: true },
      '-', tools.GetToolBarCombo('searchgettype', mep + 'searchgettype', 160, '取样类型', 60, tools.ComboStore('GetType', gpersist.SELECT_MUST_VALUE)),
      '-', { xtype:'datefield',fieldLabel:'收样开始日期',labelWidth:90,width:180,name:'searchbegindate',id:mep + 'searchbegindate',
        format:'Y-m-d',value:Ext.Date.add(nowdate, Ext.Date.MONTH, -1),selectOnFocus: false, allowBlank: true},
      '-', {xtype:'datefield',fieldLabel:'收样结束日期',labelWidth:90,width:180,name:'searchenddate',id:mep + 'searchenddate',
        format:'Y-m-d',value:nowdate,selectOnFocus:false,allowBlank:true}
    ];
    
    var items1 = [
       ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
       '-', ' ', { id: mep + 'btnAdd', text: '新增收样', iconCls: 'icon-add', handler: me.OnNew,scope: me },
       ' ', { id: mep + 'btnManyConsign', text: '同批次收样', iconCls: 'icon-add', handler: me.OnManyNew,scope: me},
       '-', { id : mep + 'btnFormPrint', text : '样品二维码打印', iconCls : 'icon-print', handler : me.OnFormPrint, scope : me },
       '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, text: '刷新页面', iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
    tools.SetToolbar(items, mep);
    tools.SetToolbar(items1, mep);
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    var toolbar1 = Ext.create('Ext.toolbar.Toolbar', {items: items1, border: false});
    me.tbGrid.add(toolbar,toolbar1);
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bas.samplename': tools.GetEncode(tools.GetValue(mep + 'searchsamplename')),
          'bas.testedname': tools.GetEncode(tools.GetValue(mep + 'searchtestedname')),
          'bas.startdate': Ext.Date.format(tools.GetValue(mep + 'searchbegindate'), 'Y-m-d'),
          'bas.enddate': Ext.Date.format(tools.GetValue(mep + 'searchenddate'), 'Y-m-d'),
          'bas.gettype': tools.GetEncode(tools.GetValue(mep + 'searchgettype'))
        });
      });
    };
  },
   
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'accdate', item.accdate);
      tools.SetValue(mep + 'testeduser', item.testeduser);
      tools.SetValue(mep + 'acctele', item.acctele);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'getid', item.getid);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'feestatus', item.feestatus);
      tools.SetValue(mep + 'remark', item.remark);
      tools.SetValue(mep + 'issend', item.issend);
      tools.SetValue(mep + 'gettype', item.gettype);
      tools.SetValue(mep + 'entertele', item.entertele);
      tools.SetValue(mep + 'testfee', item.testfee);
      tools.SetValue(mep + 'sampleplace', item.sampleplace);
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'tranid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.tranid) {
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
  },
  
  OnTestedSelect : function(render, item){
    var me = this;
    var mep = me.tranPrefix;

    if(item){
      tools.SetValue(mep + 'getid', item.tranid);
      tools.SetValue(mep + 'testedid', item.testedid);
      tools.SetValue(mep + 'testedname', item.testedname);
      tools.SetValue(mep + 'entertele', item.entertele);
      tools.SetValue(mep + 'testeduser', item.testeduser);
      tools.SetValue(mep + 'gettype', item.gettype);
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    var issend =  tools.GetValue(mep + 'issend');
    if(issend=='true'){
      tools.alert('该样品任务单已下达，不能进行修改操作！！');
      return false;
    }
    
    return true;
  },
  
  OnManyNew:function(){
	  var me = this;
	  var mep = me.tranPrefix;
	  
	  var datas = me.plGrid.getView().getSelectionModel().getSelection();
	  
	  if (datas.length < 1) {
          tools.alert('请选择一个样品批次........');
          return false;

      } else if (datas.length > 1) {
          tools.alert('只能选择一个样品批次........');
          return false;
      } else {
          
    	  var tranid = me.plGrid.selModel.selected.items[0].data.getid;
          
          var param = tools.JsonGet('LabManAccsample.do?bg.tranid=' + tranid );

         tools.alert(param.msg);

      }
	  
	 
  },
  
//提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var sample = tools.OnGridLoad(me.plGrid);

    if(sample.flowstatus == '85'){
      tools.BtnsEnable(['btnFormEdit'], mep);
    } else{
      tools.BtnsDisable(['btnFormEdit'], mep);
    }
  },
  
  OnFormPrint:function(){
    var me = this, mep = this.tranPrefix;
    var html = '';
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    
    if(datas.length==1){
    	
    	  html += '<table style="page-break-after: always;">';

          html += '<tr><td  style="padding-left:45px; padding-top:160px;"><image width="100px" src="qrcode.do?width=95&code=' + datas[0].data.samplecode + '"></td>' + 
            '<td width="250" style="font-size:15px;font-weight:bold;padding-left:4px; padding-top:160px; word-wrap:break-word;" valign="top" align="left">'+datas[0].data.sampletypename+ 
            '<br/>'+ datas[0].data.samplename +'<br/>未检：<input type="checkbox"  /><br/>在检：<input type="checkbox"/><br/>已检：<input type="checkbox"/></td></tr>'+
            '<tr><td colspan="2" style="font-size:15px;font-weight:bold;padding-left:45px; padding-top:0px; word-wrap:break-word;" valign="top" align="left">样品编号:'+datas[0].data.samplecode+'</td></tr>';

          html += '</table>';
    }else{
    	tools.alert("请选择一个样品进行打印");
    }
    if (!Ext.isEmpty(html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.GET_PRINTER_NAME(0);
      LODOP.PRINT_INIT("样品二维码打印");
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(40, 40, 400, 400, html);
      LODOP.SET_PRINTER_INDEXA("Godex ZA12X");
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
   }
  }
  
});
