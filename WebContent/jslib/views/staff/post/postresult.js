    Ext.define('alms.postresult',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'人员上岗考核结果',
          winWidth:750,
          winHeight:400,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_userexamresult',
          storeUrl:'StaffSearchUserExamResult.do',
          saveUrl:'StaffSaveUserExamResult.do',
          expUrl:'StaffSearchUserExamResult.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasSubmit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [
      ' ', { xtype: 'textfield', fieldLabel: '考核编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchexamid', id: mep + 'searchexamid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '被考核人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchusername', id: mep + 'searchusername', allowBlank: true }
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
     
     var urldev = '';
     urldev = 'DevGetListBasDevByLab.do'
     me.devParStore = new Ext.data.JsonStore({
      proxy:{
        type:'ajax',
        url:urldev,
        reader:{
          type:'json',
          root:'data'
        }
      },
      fields:[{name:'id',mapping:'devid'},{name:'name',mapping:'devname'}],
      autoLoad:false
    });
     
     if(gpersist.UserInfo.dept.deptpid == '8000'){
       me.devParStore.load({url:'DevGetListBasDevByLab.do?bd.labid=' + gpersist.UserInfo.dept.deptid});
     }else{
       me.devParStore.load({url:'DevGetListBasDevByLab.do'});
     }
     
     var urlparm = '';
     urlparm = 'BasGetListBasParameterByLab.do'
     me.paramParStore = new Ext.data.JsonStore({
      proxy:{
        type:'ajax',
        url:urlparm,
        reader:{
          type:'json',
          root:'data'
        }
      },
      fields:[{name:'id',mapping:'parameterid'},{name:'name',mapping:'parametername'}],
      autoLoad:false
    });
     
     if(gpersist.UserInfo.dept.deptpid == '8000'){
       me.paramParStore.load({url:'BasGetListBasParameterByLab.do?bpara.labid=' + gpersist.UserInfo.dept.deptid});
     }else{
       me.paramParStore.load({url:'BasGetListBasParameterByLab.do'});
     }
     
     
     var examitems = [
      ' ', { xtype: 'textfield', fieldLabel: '考核编号', labelWidth: 60, width: 200, maxLength: 40, name: 'searchsubexamid', id: mep + 'searchsubexamid1', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '被考核人', labelWidth: 60, width: 200, maxLength: 40, name: 'searchusername', id: mep + 'searchusername1', allowBlank: true },
      ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnExamReportSearch, scope: me }
     ];
     
     var examid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '考核编号', name: 'examresult.examid', id: mep + 'examid', winTitle: '考核编号',
      maxLength: 20, maxLengthText: '考核编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '考核编号为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_userexamreport',
      storeUrl: 'StaffSearchUserExamReportForResult.do',
      editable:false,
      searchTools:examitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
     });
    
     examid.on('griditemclick', me.OnExamReportSelect, me);
     
     var examapprovename = tools.UserPicker(mep + 'examapprovename','examresult.examapprovename','批准人');
     
     examapprovename.on('griditemclick', me.OnUserSelect, me);
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              examid,
              tools.FormCombo('技术职务', 'examresult.userduty', mep + 'userduty', tools.ComboStore('UserDuty', gpersist.SELECT_MUST_VALUE), '96%', false,4),
              examapprovename,
              tools.FormCheckCombo('可使用仪器', 'examresult.allowdev', mep + 'allowdev', me.devParStore, '96%', true,10)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormCombo('被考核人', 'examresult.userid', mep + 'userid', tools.ComboStore('User', gpersist.SELECT_MUST_VALUE), '97%', false,2),
              tools.FormText('所学专业', 'examresult.userpro', mep + 'userpro', 20, '97%', true, 5),
              tools.FormDate('批准日期', 'examresult.examapprovedate', mep + 'examapprovedate', 'Y-m-d', '97%', true, 8,nowdate),
              tools.FormCheckCombo('可检测项目', 'examresult.allowparam', mep + 'allowparam', me.paramParStore, '97%', true,11)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormCombo('部门机构', 'examresult.deptid', mep + 'deptid', tools.ComboStore('Dept', gpersist.SELECT_MUST_VALUE), '100%', false,3),
              tools.FormCombo('岗位', 'examresult.userpost', mep + 'userpost', tools.ComboStore('UserPost', gpersist.SELECT_MUST_VALUE), '100%', false,6),
              tools.FormDate('记录日期', 'examresult.crtdate', mep + 'crtdate', 'Y-m-d', '100%', false, 9,nowdate),
              tools.FormCheckCombo('可检测样品', 'examresult.allowsample', mep + 'allowsample', tools.ComboStore('SampleCate'), '100%', true,11),
              {xtype:'hiddenfield',name:'examresult.examapprove',id: mep + 'examapprove'},
              {xtype:'hiddenfield',name:'examresult.allowdevname',id: mep + 'allowdevname'},
              {xtype:'hiddenfield',name:'examresult.allowparamname',id: mep + 'allowparamname'},
              {xtype:'hiddenfield',name:'examresult.allowsamplename',id: mep + 'allowsamplename'},
              {xtype:'hiddenfield',name:'examresult.tranid',id: mep + 'tranid'},
              {xtype:'hiddenfield',name:'examresult.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('业务范围', 'examresult.busscope', mep + 'busscope', 400, '100%', true, 12,15)
     ];
     me.disNews = ['tranid','crtdate', 'userid', 'userpost', 'userduty', 'deptid'];
     me.disEdits = ['tranid','examid', 'crtdate', 'userid', 'userpost', 'userduty', 'deptid'];
     me.enNews = ['examid', 'userpro', 'busscope', 'allowdev', 'allowdevname', 'allowparam', 'allowparamname',
                  'examapprove', 'examapprovename', 'examapprovedate', 'allowsample'],
     me.enEdits = ['userpro', 'busscope',  'allowdev', 'allowdevname', 'allowparam', 'allowparamname',
                   'examapprove', 'examapprovename', 'examapprovedate', 'allowsample'];
     
     Ext.getCmp(mep + 'allowdev').on('change', me.SetDevName, me);
     Ext.getCmp(mep + 'allowparam').on('change', me.SetParamName, me);
     Ext.getCmp(mep + 'allowsample').on('change', me.SetSampleName, me);
  },
  
  SetDevName: function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'allowdevname', Ext.getCmp(mep + 'allowdev').getDisplayValue());
  },
  
  SetParamName: function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'allowparamname', Ext.getCmp(mep + 'allowparam').getDisplayValue());
  },
  
  SetSampleName: function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'allowsamplename', Ext.getCmp(mep + 'allowsample').getDisplayValue());
  },
  
   OnUserSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if (item) {
      tools.SetValue(mep+"examapprove",item.userid);
      tools.SetValue(mep+"examapprovename",item.username);
    }
   },
   
   OnExamReportSearch:function(){
      var me = this;
      var mep = me.tranPrefix;
      me.OnExamReportBeforeLoad();
      var examid = Ext.getCmp(mep + 'examid');
      examid.store.loadPage(1);
   },
   
   OnExamReportBeforeLoad:function(){
      var me = this;
      var mep = me.tranPrefix;
      var examid = Ext.getCmp(mep + 'examid');
      if(examid.store){
        examid.store.on('beforeload',function(store,options){
            Ext.apply(store.proxy.extraParams,{
              'examreport.examid':tools.GetValueEncode(mep + 'searchsubexamid1'),
              'examreport.username':tools.GetValueEncode(mep + 'searchusername1')
            });
        });
      }
   },
   
   OnExamReportSelect:function(render,item){
     var me = this;
     var mep = me.tranPrefix;
     if(item && item.examid){
        tools.SetValue(mep+"examid",item.examid);
        tools.SetValue(mep+"userduty",item.userduty);
        tools.SetValue(mep+"userpost",item.userpost);
        tools.SetValue(mep+"userid",item.userid);
        tools.SetValue(mep+"deptid",item.deptid);
     }
   },
   
   OnAfterCreateEdit:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.plEdit = Ext.create('Ext.form.Panel', {
      id : mep + 'pledit',
      header : false,
      height:'100%',
      autoScroll: true,
      region : 'north',
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
  },
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'examresult.examid':tools.GetValueEncode(mep+'searchexamid'),
           'examresult.username':tools.GetValueEncode(mep+'searchusername')
         })
       });
     }
   },
   
  OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'examid', item.examid);
    tools.SetValue(mep + 'userid', item.userid);
    tools.SetValue(mep + 'userpost', item.userpost);
    tools.SetValue(mep + 'userpro', item.userpro);
    tools.SetValue(mep + 'busscope', item.busscope);
    tools.SetValue(mep + 'userduty', item.userduty);
    tools.SetValue(mep + 'deptid', item.deptid);

    if(gpersist.UserInfo.dept.deptpid == '8000'){
      me.devParStore.load({url:'DevGetListBasDevByLab.do?bd.labid=' + gpersist.UserInfo.dept.deptid});
    }else{
      me.devParStore.load({url:'DevGetListBasDevByLab.do'});
    }
    
    tools.SetValue(mep + 'allowdev', item.allowdev.split(','));
    tools.SetValue(mep + 'allowdevname', item.allowdevname);
    
    if(gpersist.UserInfo.dept.deptpid == '8000'){
      me.paramParStore.load({url:'BasGetListBasParameterByLab.do?bpara.labid=' + gpersist.UserInfo.dept.deptid});
    }else{
      me.paramParStore.load({url:'BasGetListBasParameterByLab.do'});
    }
    
    tools.SetValue(mep + 'allowparam', item.allowparam.split(','));
    tools.SetValue(mep + 'allowparamname', item.allowparamname);
    tools.SetValue(mep + 'allowsample', item.allowsample.split(','));
    tools.SetValue(mep + 'allowsamplename', item.allowsamplename);
    tools.SetValue(mep + 'examapprove', item.examapprove);
    tools.SetValue(mep + 'examapprovename', item.examapprovename);
    tools.SetValue(mep + 'examapprovedate', item.examapprovedate);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'crtdate', item.crtdate);

    return true;
   },
   
  OnAfterCopy: function () {
    var me = this;
    var mep = me.tranPrefix;
    Ext.getCmp(mep + 'tranid').reset();
    Ext.getCmp(mep + 'examid').reset();
    Ext.getCmp(mep + 'examid').focus(true, 500);
  },
//修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-deal', handler : me.OnSave, scope : me },
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }
    ];
    
    if (me.hasSubmit) {
      Ext.Array.insert(me.editToolItems, 2, [
        ' ', { id: mep + 'btnSubmit', text: gpersist.STR_BTN_SUBMIT, iconCls: 'icon-deal', handler: me.OnSubmit, scope: me }
      ]);
    }
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 3, [
        '-', ' ', { id : mep + 'btnPrevRecord', text : gpersist.STR_BTN_PREVRECORD, iconCls : 'icon-prev', handler : me.OnPrevRecord, scope : me },
        ' ', { id : mep + 'btnNextRecord', text : gpersist.STR_BTN_NEXTRECORD, iconCls : 'icon-next', handler : me.OnNextRecord, scope : me }
      ]);
    }
  },
  OnPrevRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnPrevRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
    
        if (me.OnCheckPrevNext(record)) {
          if (i == 0) {
            tools.alert('已经是当前列表第一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i - 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i - 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i - 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  OnNextRecord : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.dataDeal != gpersist.DATA_DEAL_SELECT) {
      Ext.Msg.confirm(gpersist.CONFIRM_TITLE, gpersist.CONFIRM_MSG_CONTINUE, function(btn) {
        if (btn == 'yes') {
          me.OnAuthEditForm(gpersist.DATA_DEAL_SELECT, true);
          me.OnNextRecord();
        }
      });
    } else {
      var j = me.plGrid.store.getCount(), record;
      for ( var i = 0; i < j; i++) {
        record = me.plGrid.store.getAt(i).data;
      
        if (me.OnCheckPrevNext(record)) {
          if (i == j - 1) {
            tools.alert('已经是当前列表最后一条数据！');
            return;
          }
          if(me.plGrid.store.getAt(i + 1).data.flowstatus != '01'){
            tools.BtnsDisable(['btnFormEdit'], mep);
            tools.BtnsDisable(['btnSubmit'], mep);
          } else{
            tools.BtnsEnable(['btnFormEdit'], mep);
            tools.BtnsEnable(['btnSubmit'], mep);
          }
          me.cancelRecord= me.plGrid.store.getAt(i + 1).data;
          me.OnLoadData(me.plGrid.store.getAt(i + 1).data);
          me.OnFormValidShow();
          return;
        }
      }
    }
  },
  
  //  提交后单击gird 按钮判断
  OnItemClick:function(){
    var me = this;
    var mep = me.tranPrefix;
    var record = tools.OnGridLoad(me.plGrid);
    
    if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnEdit'], mep);
    } else{
      tools.BtnsEnable(['btnEdit'], mep);
    }
  },
  
  // 双击grid 按钮判断
  OnAfterShow:function(record){
    var me = this;
    var mep = me.tranPrefix;
     if(record.flowstatus != '01'){
      tools.BtnsDisable(['btnFormEdit'], mep);
      tools.BtnsDisable(['btnSubmit'], mep);
    } else{
      tools.BtnsEnable(['btnFormEdit'], mep);
      tools.BtnsEnable(['btnSubmit'], mep);
    }
  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  }
});