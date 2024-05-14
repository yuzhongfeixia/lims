Ext.define('alms.basprd', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    
    Ext.apply(config, {
      selectParams: ['PrdStatus', 'PrdUnit', 'PrdType'],
      editInfo: '耗材管理',
      winWidth: 750,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basprd',
      storeUrl: 'PrdSearchBasPrd.do',
      saveUrl: 'PrdSaveBasPrd.do',
      expUrl: 'PrdSearchBasPrd.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'prdid',
      hasGridSelect: true
    });
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    me.callParent(arguments);    
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('试剂耗材编号', 'basprd.prdid', mep + 'prdid', 30, '96%', true, 1,'',90),
          tools.FormCombo('耗材分类', 'basprd.prdtype', mep + 'prdtype', tools.ComboStore('PrdType', gpersist.SELECT_MUST_VALUE), '96%', false, 3,90),
          tools.FormText('最大库存量', 'basprd.prdmax', mep + 'prdmax', 30, '96%', false, 1,'isnumber',90),
          tools.FormText('参考价格(元)', 'basprd.prdprice', mep + 'prdprice', 14, '96%', true, 5,'is15p2',90),
          tools.FormText('生产厂商', 'basprd.factoryname', mep + 'factoryname',40, '96%', true, 28,'',90),
          tools.FormDate('生产日期', 'basprd.factorydate', mep + 'factorydate', 'Y-m-d', '96%', true, 27,nowdate,90),
          tools.FormCombo('购买人', 'basprd.buyuser', mep + 'buyuser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '96%', false,29,90),
          tools.FormCombo('所在仓库', 'basprd.storeid', mep + 'storeid', tools.ComboStore('Store', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90),
          tools.FormCombo('是否需要验收 ', 'basprd.isverify', mep + 'isverify', tools.ComboStore('IsVerify', gpersist.SELECT_MUST_VALUE), '96%', false, 4,90)
          
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('试剂耗材名称', 'basprd.prdname', mep + 'prdname', 20, '100%', false, 2,'',100),
          tools.FormCombo('计量单位', 'basprd.prdunit', mep + 'prdunit', tools.ComboStore('PrdUnit', gpersist.SELECT_MUST_VALUE), '100%', false, 4,100),
          tools.FormText('最小库存量', 'basprd.prdmin', mep + 'prdmin', 20, '100%', false, 2,'isnumber',100),
          tools.FormText('默认有效期(月)', 'basprd.validmonth', mep + 'validmonth', 14, '100%', true, 7,'isnumber',100),
          tools.FormText('供应商', 'basprd.tradename', mep + 'tradename',12, '100%', true,26,'',100),
          tools.FormText('规格型号', 'basprd.prdstandard', mep + 'prdstandard', 30, '100%', true, 1,'',100),
          tools.FormDate('购买日期', 'basprd.buydate', mep + 'buydate', 'Y-m-d', '100%', true, 31,nowdate,100),
          tools.FormCombo('耗材管理员', 'basprd.prduser', mep + 'prduser', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '100%', false, 4,100),
          {xtype:'hiddenfield',name:'basprd.buyusername',id: mep + 'buyusername'},
          {xtype:'hiddenfield',name:'basprd.prdusername',id: mep + 'prdusername'},
          {xtype:'hiddenfield',name:'basprd.deal.action',id: mep + 'datadeal'}
        ]}     
      ]},
      tools.FormTextArea('存放条件', 'basprd.prdenv', mep + 'prdenv', 40, '100%', true,10,2,90),
      tools.FormTextArea('试剂耗材作用', 'basprd.prdeffect', mep + 'prdeffect', 40, '100%', true,10,2,90),
      tools.FormTextArea('备注', 'basprd.remark', mep + 'remark', 200, '100%', true,11,3,90)
    ];
    
    me.disNews = ['prdid'];
    me.disEdits = ['prdid'];
    me.enNews = [ 'prdname', 'prdtype', 'prdstandard', 'prdunit', 'prdprice', 'prdenv', 'validmonth',  'isscrap', 
                   'remark', 'prdmax',  'prdmin', 'prdeffect','storeid','prduser','tradename', 
                   'factoryname', 'factorydate', 'buyuser', 'buyusername', 'buydate','isverify'];
    me.enEdits = [ 'prdname', 'prdtype', 'prdstandard', 'prdunit', 'prdprice', 'prdenv', 'validmonth', 'isscrap', 
                   'remark', 'prdmax',  'prdmin', 'prdeffect','storeid','prduser','tradename', 
                   'factoryname', 'factorydate', 'buyuser', 'buyusername', 'buydate','isverify'];
    
    Ext.getCmp(mep+'prduser').on('select',function(){
      tools.SetValue(mep+'prdusername',Ext.getCmp(mep+'prduser').getDisplayValue());
    });
    
    Ext.getCmp(mep+'buyuser').on('select',function(){
      tools.SetValue(mep+'buyusername',Ext.getCmp(mep+'buyuser').getDisplayValue());
    });
    
    Ext.getCmp(mep+'prdmax').on('blur',function(){
      var prdmax = parseFloat(tools.GetValue(mep+'prdmax'));
      var prdmin = parseFloat(tools.GetValue(mep+'prdmin'));
      
      if(prdmax<prdmin){
        tools.alert('最大库存量不能小于最小库存量,请修改！');
        Ext.getCmp(mep+'prdmax').reset();
        return;
      }
    });
    
    Ext.getCmp(mep+'prdmin').on('blur',function(){
      var prdmax = parseFloat(tools.GetValue(mep+'prdmax'));
      var prdmin = parseFloat(tools.GetValue(mep+'prdmin'));
      
      if(prdmin>prdmax){
        tools.alert('最小库存量不能大于最大库存量,请修改！');
        Ext.getCmp(mep+'prdmin').reset();
        return;
      }
    });
    
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '耗材编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchprdid', id: mep + 'searchprdid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '耗材名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchprdname', id: mep + 'searchprdname', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me},
      '-', { id : mep + 'btnFormPrint', text : '耗材二维码', iconCls : 'icon-print', handler : me.OnFormPrint, scope : me },
      '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//      ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnInitData: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.callParent(arguments); 
    tools.SetValue(mep + 'prdtype',gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdunit',gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'prdmax',0);
    tools.SetValue(mep + 'prdmin',0);

  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'basprd.prdid': tools.GetEncode(tools.GetValue(mep + 'searchprdid')),
          'basprd.prdname': tools.GetEncode(tools.GetValue(mep + 'searchprdname'))
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      ' ', { id: mep + 'btnFormCancel', text: gpersist.STR_BTN_CANCEL, iconCls: 'icon-cancel', handler: me.OnFormCancel, scope: me },
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    var prdtype = Ext.getCmp(mep+'prdtype').getValue();
    var prdunit = Ext.getCmp(mep+'prdunit').getValue();
    
    if (tools.InvalidForm(me.plEdit.getForm())) {
      if(prdtype == gpersist.SELECT_MUST_VALUE ){
          tools.alert('请选择耗材类别！');
          return;
      }
      
      if(prdunit == gpersist.SELECT_MUST_VALUE ){
          tools.alert('请选择计量单位！');
          return;
       }
    }
    return true;
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'prdid', item.prdid);
  	tools.SetValue(mep + 'prdname', item.prdname);
  	tools.SetValue(mep + 'prdtype', item.prdtype);
  	tools.SetValue(mep + 'prdstandard', item.prdstandard);
  	tools.SetValue(mep + 'prdunit', item.prdunit);
  	tools.SetValue(mep + 'prdprice', item.prdprice);
  	tools.SetValue(mep + 'prdenv', item.prdenv);
  	tools.SetValue(mep + 'validmonth', item.validmonth);
  	tools.SetValue(mep + 'isscrap', item.isscrap);
  	tools.SetValue(mep + 'remark', item.remark);
  	tools.SetValue(mep + 'prdmax', item.prdmax);
    tools.SetValue(mep + 'prdmin', item.prdmin);
    tools.SetValue(mep + 'prdeffect', item.prdeffect); 
    tools.SetValue(mep + 'storeid', item.storeid);
    tools.SetValue(mep + 'prdusername', item.prdusername);
    tools.SetValue(mep + 'prduser', item.prduser);
    tools.SetValue(mep+"factoryname",item.factoryname);
    tools.SetValue(mep+"tradename",item.tradename);
    tools.SetValue(mep+"buydate",item.buydate);
    tools.SetValue(mep+"buyuser",item.buyuser);
    tools.SetValue(mep+"factorydate",item.factorydate);
    tools.SetValue(mep+"isverify",item.isverify);
    return true;
  },

  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    Ext.getCmp(mep + 'prdid').reset();
    Ext.getCmp(mep + 'prdid').focus(true, 500);
  },
  
  OnAfterSave : function(action) {
    var me = this;
    var mep = me.tranPrefix;
    
    if (action.result && action.result.data) {
      if (me.dataDeal == gpersist.DATA_DEAL_NEW) {
        if (action.result.data.prdid) {
          tools.SetValue(mep + 'prdid', action.result.data.prdid);
        }
      }
    }
  },
  
  OnFormPrint:function(){
    var me = this, mep = this.tranPrefix;
    
    var html = '';
    var datas = me.plGrid.getView().getSelectionModel().getSelection();
    
    for(var i=0;i<datas.length;i++){
      var record = datas[i].data;
      if (Ext.isEmpty(record.prdid)) return;
    
      html += '<table style="page-break-after: always;">';
      html += '<tr><td style="padding-left:20px; padding-top:180px;"><image width="120px" src="qrcode.do?width=95&code=' + record.prdid + '"></td>' + 
        '<td width="160" style="font-size:15px;font-weight:bold;padding-left:4px;word-wrap:break-word;padding-top:180px;" valign="top" align="left">' + record.prdid + 
        '<br />' + record.prdname + '</td></tr>';
      html += '</table>';
    }
    
   if (!Ext.isEmpty(html)) {
      var LODOP = getLodop();
      LODOP.SET_LICENSES("北京金禾天成科技有限公司","BCA6DBCB8EE17DECE3C5A74CA683CA08","","");
      LODOP.PRINT_INIT("试验报告打印");
      LODOP.GET_PRINTER_NAME(0);
      LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
      LODOP.ADD_PRINT_HTM(20, 40, '100%', '100%', html);
      LODOP.SET_PRINTER_INDEXA("Godex ZA12X");
      LODOP.PREVIEW();//预览功能
//      LODOP.PRINT();//打印功能
   }
  }
  
});