 Ext.define('alms.mancrmsurvey',{
   extend:'gpersist.base.busform',
   
   constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'满意度调查',
          winWidth:750,
          winHeight:200,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_crmsurvey',
          storeUrl:'CrmSearchCrmSurvey.do',
          saveUrl:'CrmSaveCrmSurvey.do',
          expUrl:'CrmSearchCrmSurvey.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true,
          hasEdit: true,
          hasDetail: true,
          hasSubmit: true,
          detailTitle: '调查项目',
          urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_crmsurveydetail',
          urlDetail: 'CrmGetListCrmSurveyDetail.do',
          detailTabs: 1,
          hasDateSearch: true,
          hasDetailEdit: true
      });
      me.callParent(arguments);
   },
   
   OnBeforeFormLoad:function(){
     var me = this;
     var mep = me.tranPrefix;
     me.OnInitGridToolBar();
     
     var searchitems = [ 
       ' ', { xtype: 'textfield', fieldLabel: '调查编号', labelWidth: 80, width: 200, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
       ' ', { xtype: 'textfield', fieldLabel: '客户名称', labelWidth: 80, width: 200, maxLength: 40, name: 'searchcustname', id: mep + 'searchcustname', allowBlank: true }
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
     
     me.editControls=[
        { xtype: 'container', anchor: '100%', layout: 'column', items: [
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('调查编号', 'crmsurvey.tranid', mep + 'tranid', 20, '97%', false, 1),
              tools.FormText('地址', 'crmsurvey.custaddr', mep + 'custaddr', 30, '97%', false, 4),
              tools.FormText('业务员', 'crmsurvey.tranusername', mep + 'tranusername', 10, '97%', false, 7)
          ]},
          { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
              tools.FormText('客户名称', 'crmsurvey.custname', mep + 'custname', 10, '97%', false, 2),
              tools.FormText('联系电话', 'crmsurvey.linktele', mep + 'linktele', 30, '97%', false, 5),
              tools.FormDate('记录时间', 'crmsurvey.trandate', mep + 'trandate', 'Y-m-d', '97%', false,8,nowdate)
          ]},
          { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
              tools.FormText('检测项目', 'crmsurvey.testitem', mep + 'testitem', 20, '100%', false, 3),
              tools.FormDate('调查日期', 'crmsurvey.custdate', mep + 'custdate', 'Y-m-d', '100%', false, 6,nowdate),
              {xtype:'hiddenfield',name:'crmsurvey.tranuser',id: mep + 'tranuser'},
              {xtype:'hiddenfield',name:'crmsurvey.deal.action',id: mep + 'datadeal'}
           ]}
         ]},
        tools.FormTextArea('备注', 'crmsurvey.remark', mep + 'remark', 300, '100%', true, 10,4)
     ];
     me.disNews = ['tranid', 'tranuser', 'tranusername', 'trandate'];
     me.disEdits = ['tranid', 'tranuser', 'tranusername', 'trandate' ];
     me.enNews = ['custname', 'testitem', 'custaddr', 'linktele', 'custdate', 'remark'],
     me.enEdits = ['custname', 'testitem', 'custaddr', 'linktele', 'custdate', 'remark'];
   },
   
   //修改编辑面的按钮菜单
   OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SAVE, iconCls : 'icon-save', handler : me.OnSave, scope : me },  
     // '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
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
   
   OnBeforeSearch:function(){
     var me = this;
     var mep = me.tranPrefix;
     if(me.gridStore){
       me.gridStore.on('beforeload',function(store,options){
         Ext.apply(store.proxy.extraParams,{
           'crmsurvey.tranid':tools.GetValueEncode(mep+'searchtranid'),
           'crmsurvey.custname':tools.GetValueEncode(mep+'searchcustname')
         })
       });
     }
   },
   
   OnLoadData:function(item){
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'tranid', item.tranid);
    tools.SetValue(mep + 'custname', item.custname);
    tools.SetValue(mep + 'testitem', item.testitem);
    tools.SetValue(mep + 'custaddr', item.custaddr);
    tools.SetValue(mep + 'linktele', item.linktele);
    tools.SetValue(mep + 'custdate', item.custdate);
    tools.SetValue(mep + 'remark', item.remark);
    tools.SetValue(mep + 'tranuser', item.tranuser);
    tools.SetValue(mep + 'tranusername', item.tranusername);
    tools.SetValue(mep + 'trandate', item.trandate);
    me.OnDetailRefresh();
    return true;
   },
   
   OnInitData : function() {
     var me = this;
     var mep = me.tranPrefix;
     me.callParent(arguments);
     tools.SetValue(mep + 'tranuser', gpersist.UserInfo.user.userid);
     tools.SetValue(mep + 'tranusername', gpersist.UserInfo.user.username );
   },
   
   OnBeforeCreateDetailEdit: function () {
     var me = this;
     var mep = me.tranPrefix;
     var nowdate = new Date();
     
//     var surveyitem = Ext.create('Ext.ux.GridPicker', {
//      fieldLabel: '调查内容', name: 'surveydetail.surveyitemname', id: mep + 'surveyitemname', winTitle: '选择调查内容',
//      maxLength: 40, maxLengthText: '考核项目不能超过40字！', selectOnFocus: false, labelWidth: 80,
//      blankText: '考核项目为空！', allowBlank: false, anchor: '96%', tabIndex: 4,
//      columnUrl: 'SysSqlColumn.do?sqlid=p_get_crmsurveyitem',
//      storeUrl: 'CrmSearchCrmSurveyItem.do',
//      editable:false,
//      hasPage: true, pickerWidth: 600, pickerHeight: 500
//     });
//    
//     surveyitem.on('griditemclick', me.OnItemSelect, me);
//     surveyitem.on('gridbeforeload', me.OnItemLoad, me);
     
     me.editDetailControls = [
       {xtype: 'container', anchor: '100%', layout: 'column', items: [
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
//            surveyitem
//            tools.FormText('调查内容', 'surveydetail.surveyitemname', mep + 'surveyitemname',20, '96%', false, 1)
              tools.FormCombo('调查内容', 'surveydetail.surveyitemname', mep + 'surveyitemname', tools.ComboStore('SurveyItem', gpersist.SELECT_MUST_VALUE), '96%', false, 7)
         ]},
         {xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
            tools.FormCombo('调查评分', 'surveydetail.surveyscore', mep + 'surveyscore', tools.ComboStore('SurveyScore', gpersist.SELECT_MUST_VALUE), '96%', false, 7),
            {xtype:'hiddenfield',name:'surveydetail.surveyitem',id: mep + 'surveyitem'}
        ]}
       ]},      
       tools.FormTextArea('评分说明', 'surveydetail.surveydesc', mep + 'surveydesc', 100, '98%', true, 6,4)
    ];

     me.disDetailControls = [];
     me.enDetailControls = ['surveyitem', 'surveyscore', 'surveydesc','surveyitemname'];
     
  },
  
  OnItemSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if(item && item.surveyitem){
        tools.SetValue(mep+"surveyitem",item.surveyitem);
        tools.SetValue(mep+"surveyitemname",item.surveyitemname);
     }
  },
  
  OnItemLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var surveyitemname = Ext.getCmp(mep + 'surveyitemname');
    if(surveyitemname.store && surveyitemname){
      surveyitemname.store.load({ callback: function(records, options, success){  
           for(var i = 0; i<surveyitemname.store.getCount(); i++){
               for(var j = 0; j<me.plDetailGrid.store.getCount(); j++){
                   if(me.plDetailGrid.store.getAt(j).get('surveyitem') == surveyitemname.store.getAt(i).get('surveyitem')){
                     surveyitemname.store.remove(surveyitemname.store.getAt(i));
                     break;
                   }
               }
           }
         }
     });
    }
  },
   
  OnLoadDetailData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'surveyitem', item.surveyitem);
    //tools.SetValue(mep + 'surveyitemname', item.surveyitemname);
    Ext.getCmp(mep + 'surveyitemname').setValue(item.surveyitem)
    tools.SetValue(mep + 'surveyscore', item.surveyscore);
    tools.SetValue(mep + 'surveydesc', item.surveydesc);
   },
   
  OnListNew: function() {
    var me = this;
    
    var date = new Date();
    
    me.OnCreateDetailWin();
    
    if(me.winDetail){      
      me.winDetail.show();
      me.detailEditType = 1;
      me.OnInitDetailData();   
      me.OnAuthDetailEditForm(false);
    }
  },
   
   OnBeforeListSave: function (record) {
     var me = this;
     var mep = me.tranPrefix;
     record.data.surveyitem = Ext.getCmp(mep + 'surveyitemname').getValue();
     record.data.surveyitemname = Ext.getCmp(mep + 'surveyitemname').getRawValue();
     record.data.surveyscore = Ext.getCmp(mep + 'surveyscore').getValue();
     record.data.surveyscorename = Ext.getCmp(mep + 'surveyscore').getDisplayValue();
     record.data.surveydesc = Ext.getCmp(mep + 'surveydesc').getValue();
   },
   
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      var surveyscore = Ext.getCmp(mep+'surveyscore').getValue();
      if(surveyscore == gpersist.SELECT_MUST_VALUE ){
        tools.alert('请选择调查评分！');
        return;
      }
      
      if (me.detailEditType == 1) {
        var record = me.plDetailGrid.store.model.create({});
        var surveyitem = Ext.getCmp(mep+'surveyitem').getValue();
        for(var i=0;i<me.plDetailGrid.store.getCount();i++){
          alert(me.plDetailGrid.store.getAt(i).get('surveyitem'))
          if(me.plDetailGrid.store.getAt(i).get('surveyitem') ==surveyitem){
            tools.alert('该调查内容已存在');
            return;
          }
        }
        me.OnBeforeListSave(record);
        
        me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);      
      }
      else {
        me.OnBeforeListSave(me.detailRecord);
        
        me.plDetailGrid.getView().refresh();
      }
      
      me.winDetail.hide();
    }
  },
   
   OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;
    tools.SetValue(mep + 'surveyscore', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'surveyitem', gpersist.SELECT_MUST_VALUE);
   },
   
   OnDetailRefresh: function () {
     var me = this;
     var mep = me.tranPrefix;
     
     if (me.plDetailGrid && me.plDetailGrid.store) {
        me.plDetailGrid.store.proxy.url = tools.GetUrl('CrmGetListCrmSurveyDetail.do?surveydetail.tranid=') + tools.GetValue(mep + 'tranid');
        me.plDetailGrid.store.load();
     }
   },
   
  OnGetSaveParams: function () {
     var me = this;
     var mep = me.tranPrefix;

     var details = [];
     for (var i = 0; i < me.plDetailGrid.store.getCount(); i++) {
       details.push(me.plDetailGrid.store.getAt(i).data);
     }
     me.saveParams = { details: Ext.encode(details) };
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
    me.OnDetailRefresh();
  },
  
  //提交后单击gird 按钮判断
//  OnItemClick:function(){
//    var me = this;
//    var mep = me.tranPrefix;
//    var record = tools.OnGridLoad(me.plGrid);
//    
//    if(record.flowstatus != '01'){
//      tools.BtnsDisable(['btnEdit'], mep);
//    } else{
//      tools.BtnsEnable(['btnEdit'], mep);
//    }
//  },
  
  //双击grid 按钮判断
//  OnAfterShow:function(record){
//    var me = this;
//    var mep = me.tranPrefix;
//     if(record.flowstatus != '01'){
//      tools.BtnsDisable(['btnFormEdit'], mep);
//      tools.BtnsDisable(['btnSubmit'], mep);
//    } else{
//      tools.BtnsEnable(['btnFormEdit'], mep);
//      tools.BtnsEnable(['btnSubmit'], mep);
//    }
//  },
  
  //提交后按钮判断
  OnAfterSubmit:function(){
    var me = this;
    var mep = me.tranPrefix;
    tools.BtnsDisable(['btnFormEdit'], mep);
    tools.BtnsDisable(['btnSubmit'], mep);
  }
});