    Ext.define('alms.manmonitwork',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'监督工作',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_quanmonitwork',
          storeUrl:'QuanSearchQuanMonitWork.do',
          saveUrl:'QuanSaveQuanMonitWork.do',
          expUrl:'QuanSearchQuanMonitWork.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '实验员姓名', labelWidth: 80, width: 250, maxLength: 40, name: 'searchmonitname', id: mep + 'searchmonitname', allowBlank: true }
     ];
     var items = [
        ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
        '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
        ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
        ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
        '-', ' ', { id: mep + 'btnDeal', text: gpersist.STR_BTN_DEAL, iconCls: 'icon-deal', handler: me.OnDeal, scope: me },
        '-', ' ', { id: mep + 'btnExport', text: gpersist.STR_BTN_EXPORT, iconCls: 'icon-export', handler: me.OnExport, scope: me },
//        ' ', { id: mep + 'btnPrint', text: gpersist.STR_BTN_PRINT, iconCls: 'icon-print', handler: me.OnPrint, scope: me },
        '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
     ];
    
     tools.SetToolbar(searchitems,mep);
     tools.SetToolbar(items, mep);
     
     var searchtoolbar = Ext.create('Ext.toolbar.Toolbar', {items: searchitems, border: false});
     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
     
     me.tbGrid.add(searchtoolbar);
     me.tbGrid.add(toolbar);
   },
   
   OnBeforeCreateEdit:function(){
     var me = this;
     var mep = this.tranPrefix;
     var nowdate = new Date();
     
     var monituser = tools.UserPicker(mep + 'monitusername','qmw.monitusername','质量监督员');
     
     monituser.on('griditemclick', me.OnUserSelect, me);
     
     var trialuser = tools.UserPicker(mep + 'trialusername','qmw.trialusername','试验员');
     
     trialuser.on('griditemclick', me.OnTrailSelect, me);
     
     var sampitems = [
     //' ', { xtype: 'textfield', fieldLabel: '样品编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsampleid', id: mep + 'searchsampleid', allowBlank: true },
     ' ', { xtype: 'textfield', fieldLabel: '样品名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchsample', id: mep + 'searchsample', allowBlank: true },
     ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSampleSearch, scope: me }
     ];
     
     var samplename = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '样品名称', name: 'qmw.samplename', id: mep + 'samplename', winTitle: '样品名称',
      maxLength: 20, maxLengthText: '样品名称不能超过20字！', selectOnFocus: false, labelWidth: 90,
      blankText: '样品名称！', allowBlank: false, anchor: '100%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_bassample',
      storeUrl: 'BasSearchBasSample.do',
      editable:false,
      searchTools:sampitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     samplename.on('griditemclick', me.OnSampleSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('业务编号', 'qmw.tranid', mep + 'tranid', 20, '96%', false, 1,'',90),
              tools.FormDate('监督结束时间', 'qmw.monitend', mep + 'monitend', 'Y-m-d', '96%', false,4,nowdate,90),
              tools.FormCheckCombo('工作类型', 'qmw.worktype', mep + 'worktype', tools.ComboStore('WorkType'), '96%', false, 3,90),
              tools.FormDate('时效结束', 'qmw.validend', mep + 'validend', 'Y-m-d', '96%', true,10,nowdate,90)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              monituser,
              trialuser,
              tools.FormText('报告份数', 'qmw.reportnum', mep + 'reportnum', 10, '96%', true, 8,'isnumber'),
              tools.FormText('报告类别', 'qmw.reportcata', mep + 'reportcata', 10, '96%', true, 11)
           ]},
           { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormDate('监督开始时间', 'qmw.monitstart', mep + 'monitstart', 'Y-m-d', '100%', false,3,nowdate,90),
              samplename,
              tools.FormDate('时效起始', 'qmw.validstart', mep + 'validstart', 'Y-m-d', '100%', true,9,nowdate,90),
              {xtype:'hiddenfield',name:'qmw.sampleid',id: mep + 'sampleid'},
              {xtype:'hiddenfield',name:'qmw.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'qmw.monituser',id: mep + 'monituser'},
              {xtype:'hiddenfield',name:'qmw.trialuser',id: mep + 'trialuser'},
              {xtype:'hiddenfield',name:'qmw.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('备注', 'qmw.remark', mep + 'remark', 200, '100%', true, 14,5,90),
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: 1, layout: 'anchor', id: mep + 'testdata', items: [
           
        ]}
      ]}
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.enNews = ['monituser', 'monitusername', 'monitstart', 'monitend', 'trialuser', 'trialusername', 'sampleid', 'samplename', 'worktype', 'reportnum', 'validstart', 'validend', 'reportcata', 'remark'],
     me.enEdits = [ 'monituser', 'monitusername', 'monitstart', 'monitend', 'trialuser', 'trialusername', 'sampleid', 'samplename', 'worktype', 'reportnum', 'validstart', 'validend', 'reportcata', 'remark'];
   },
   
   OnSampleSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnSampleBeforeLoad();
      var samplename = Ext.getCmp(mep + 'samplename');
      samplename.store.loadPage(1);
   },
   
  OnSampleBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var samplename = Ext.getCmp(mep + 'samplename');
      if(samplename.store){
        samplename.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'bsample.sampleid':tools.GetValueEncode(mep + 'searchsampleid'),
              'bsample.samplename':tools.GetValueEncode(mep + 'searchsample')
            });
        });
      }
   },
   
  OnSampleSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.sampleid){
        tools.SetValue(mep+"samplename",item.samplename);
        tools.SetValue(mep+"sampleid",item.sampleid);
     }
   },
   
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"monituser",item.userid);
      tools.SetValue(mep+"monitusername",item.username);
    }
   },
   
   OnTrailSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"trialuser",item.userid);
      tools.SetValue(mep+"trialusername",item.username);
    }
   },
   
   //修改编辑面的按钮菜单
   OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
   },
   
  OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      height:'100%',
      autoScroll: true,
      region : 'center',
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
    me.OnCreateWork();
  },
   
  OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'qmw.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'qmw.monitusername':tools.GetValueEncode(mep+'searchmonitname')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'monituser', item.monituser);
    tools.SetValue(mep + 'monitusername', item.monitusername);
    tools.SetValue(mep + 'monitstart', item.monitstart);
    tools.SetValue(mep + 'monitend', item.monitend);
    tools.SetValue(mep + 'trialuser', item.trialuser);
    tools.SetValue(mep + 'trialusername', item.trialusername);
    tools.SetValue(mep + 'sampleid', item.sampleid);
    tools.SetValue(mep + 'samplename', item.samplename);
    tools.SetValue(mep + 'worktype', item.worktype.split(','));
    tools.SetValue(mep + 'reportnum', item.reportnum);
    tools.SetValue(mep + 'validstart', item.validstart);
    tools.SetValue(mep + 'validend', item.validend);
    tools.SetValue(mep + 'reportcata', item.reportcata);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);

    var samitems = tools.JsonGet(tools.GetUrl('QuanGetListQuanMonitWorkDetail.do?qmwk.tranid=')+item.tranid).data;
     if (samitems ) {
      for (var i = 0; i < samitems.length; i++) {
        var samitem = samitems[i];
        if(samitem.optionid != ''){
          tools.SetValue(mep + 'd' + samitem.samitemid + '-' + samitem.serial, samitem.optionid);
        }else{
          tools.SetValue(mep + 'd' + samitem.samitemid + '-' + samitem.serial, samitem.badbehave);
        }
      }  
    }
    return true;
  },
   
  OnCreateWork:function(){
    var me = this;
    var mep = me.tranPrefix;
    var bigitems = tools.JsonGet(tools.GetUrl('QuanGetListQuanMonitBigItem.do')).data; 
    var testdata = Ext.getCmp(mep + 'testdata');
    me.formdatas = [];
    testdata.removeAll();
    
    var  i = 0, j = 0, idx = 0;
    var start = new Date().getTime();
    for(i = 0; i < bigitems.length; i++){
        var bigitem = bigitems[i];
        var samitems = tools.JsonGet(tools.GetUrl('QuanGetListQuanMonitSamItem.do?qms.bigitemid=')+bigitem.bigitemid).data;
        
        var fieldset = Ext.create('Ext.form.FieldSet', { id: mep + 'g' + bigitem.bigitemid, hidden: false, collapsible: true, collapsed:true,
          title: bigitem.bigitemname, anchor: '100%'});
        
//        var mbox = Ext.create('Ext.container.Container', { id: mep + 'con' + bigitem.bigitemid + '_' + 1 + '_' + j, margin:2, anchor: '100%', layout: 'column' });
        
        for (j = 0; j < samitems.length; j++) {
          
          var samitem = samitems[j];
          var bbox = bbox || {};
//          if(j % 3 == 0){
             bbox = Ext.create('Ext.container.Container', { id: mep + 'co' + samitem.samitemid + '_' + 2 + '_'+j, layout: 'anchor' });
//             mbox.add(bbox);
//          }
          var options = tools.JsonGet(tools.GetUrl('QuanGetListQuanMonitOption.do?qmo.samitemid=')+samitem.samitemid).data;
 
          if (samitem.ischoose == true) {
            var ccombo = tools.AutoFormComboFlex(samitem.samitemname, 'data.' + samitem.samitemid + '-' + j, 
              mep + 'd' + samitem.samitemid + '-' + j, 
              tools.GetNullStore(), true, (i +1) * 100 + idx, 80, true,1);
              
//           var test = Ext.getCmp( mep + 'd' + samitem.samitemid + '-' + j);  
           var testda = tools.GetTypeList(gpersist.SELECT_MUST_VALUE);
           if (options){
             Ext.each(options, function (item, index) { 
                     testda.push({ id: item.optionid, name: item.optionname });
              });
           }  
           ccombo.bindStore(new Ext.data.JsonStore({fields: ['id', 'name'], data: testda}))
           ccombo.setValue(gpersist.SELECT_MUST_VALUE); 
           
           bbox.add(ccombo);
          }
          else {
//            var ctext = tools.AutoFormTextFlex(samitem.samitemname, 'data.' + samitem.samitemid  + '-' + j, 
//              mep + 'd' + samitem.samitemid + '-' + j, 100,
//              true, (i +1) * 100 + idx, null, 80,1);
            
            var ctext = Ext.create('Ext.form.TextArea', {
                  fieldLabel: samitem.samitemname, name: 'data.' + samitem.samitemid  + '-' + j, id: mep + 'd' + samitem.samitemid + '-' + j,
                  maxLength: 100, maxLengthText:  '长度不能超过' + 100 + '个字符！', selectOnFocus: false, labelWidth: 84,
                  blankText:  '不能为空！', allowBlank: true, anchor: '100%', tabIndex: 14, rows: 2, labelAlign: 'right',flex: 1
            });
              
            bbox.add(ctext);
            
          }
          me.formdatas.push({bigitemid: bigitem.bigitemid, samitemid: samitem.samitemid, serial: j, samitemname:samitem.samitemname, 
                      optionid: '', badbehave: '',ischoose:samitem.ischoose}); 
          idx++;
          fieldset.add(bbox);
        }  
//        fieldset.add(mbox);
        testdata.add(fieldset); 
      }
      
//     var end = new Date().getTime();
//     var a = (end - start)/1000;
   },
   
   OnAuthEditForm : function(type, islayout) {
    var me = this;
    var mep = this.tranPrefix;
    
    me.dataDeal = type;
    
    if (islayout)
      me.plEdit.suspendLayouts();
    
    var fields = [];
    if (me.formdatas) {
      for (var i = 0; i < me.formdatas.length; i++) {
          var formdata = me.formdatas[i];
          fields.push('d' + formdata.samitemid + '-' + formdata.serial);
      }
    }
    
    switch (type) {
      case gpersist.DATA_DEAL_SELECT:
        tools.FormDisable(me.disEdits, me.enEdits, mep);
        tools.BtnsEnable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);        
        tools.BtnsDisable(['btnSave'], mep);
        tools.Disabled(fields, mep); 
        break;
      
      case gpersist.DATA_DEAL_NEW:
        tools.FormInit(me.disNews, me.enNews, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.Enabled(fields, mep);
        break;
      
      case gpersist.DATA_DEAL_EDIT:
        tools.FormInit(me.disEdits, me.enEdits, mep);
        tools.BtnsDisable(['btnFormEdit','btnSubmit','btnFormCopy','btnFormCheck','btnPrevRecord','btnNextRecord'], mep);
        tools.BtnsEnable(['btnSave'], mep);
        tools.Enabled(fields, mep);
        break;
    }
    
    me.plEdit.updateLayout();
    
    if (islayout) {
      me.plEdit.resumeLayouts(true);
      me.plEdit.doLayout();
    }
  },
   
  OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
   },
   
   OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;
     if (me.formdatas) {
      for (var i = 0; i < me.formdatas.length; i++) {
        var formdata = me.formdatas[i];
        if (formdata.ischoose == true) {
          formdata.optionid = tools.GetValue(mep + 'd' + formdata.samitemid + '-' + formdata.serial);
        }
        else{
          formdata.badbehave = tools.GetValue(mep + 'd' + formdata.samitemid + '-' + formdata.serial);
        }
      }
      me.saveParams = { details: Ext.encode(me.formdatas) };
    }   
  },
   
  OnBeforeSave:function(){
    var me = this;
    var mep = me.tranPrefix;
    if (me.formdatas) {
      for (var i = 0; i < me.formdatas.length; i++) {
         var formdata = me.formdatas[i];
         var samitemname = Ext.getCmp(mep + 'd' + formdata.samitemid + '-' + formdata.serial).getValue();
         if(samitemname == gpersist.SELECT_MUST_VALUE){
           tools.alert('请选择'+formdata.samitemname +'的结果');
           return;
         }
      }
    }
    return true;
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
          tools.SetValue(mep + 'tranid', action.result.data.tranid)
        }
      }
    }
  }
});
