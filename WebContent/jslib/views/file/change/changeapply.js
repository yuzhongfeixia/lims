Ext.define('alms.changeapply', {
  extend: 'gpersist.base.listeditform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '文件更改申请',
      winWidth: 750,
      winHeight: 420,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_changeapply',
      storeUrl: 'IncFileSearchChangeApply.do',
      saveUrl: 'IncFileSaveChangeApply.do',
      hasGridSelect: true,
      expUrl: 'IncFileSearchChangeApply.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid'
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var devitems = [
                    ' ', { xtype: 'textfield', fieldLabel: '文件编号', labelWidth: 70, width: 200, maxLength: 40, name: 'searchfileid1', id: mep + 'searchfileid1', allowBlank: true },
                    ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 70, width: 200, maxLength: 40, name: 'searchfilename1', id: mep + 'searchfilename1', allowBlank: true },
                    ' ', { text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnDevSearch, scope: this }];
                  
    var fileid = Ext.create('Ext.ux.GridPicker', {
      fieldLabel: '拟更改文件', name: 'ca.changefileid', id: mep + 'changefileid', winTitle: '选择文件',
      maxLength: 20, maxLengthText: '供应商编号不能超过20字！', selectOnFocus: false, labelWidth: 80,
      blankText: '设备编号不能为空！', allowBlank: false, anchor: '100%', tabIndex: 2,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basfile',
      storeUrl: 'IncFileSearchBasFile.do',
      editable:false,
      searchTools:devitems,
      hasPage: true, pickerWidth: 600, pickerHeight: 500
    });
    fileid.on('griditemclick', me.OnFileSelect, me);
    
    var replacefilename = Ext.create('Ext.form.TextField', {
      fieldLabel: '替代文件', name: 'ca.replacefilename', id: mep + 'replacefilename',
      maxLength: 60, maxLengthText: '替代文件长度不能超过60个字符！', selectOnFocus: false, labelWidth: 80,
      blankText: '替代文件不能为空！', allowBlank: false, anchor: '100%',  tabIndex: 4
    });
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('业务编号', 'ca.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('文件名称', 'ca.changefilename', mep + 'changefilename', 60, '96%', false, 3),
          tools.FormCombo('会办部门', 'ca.dealdept', mep + 'dealdept', tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE), '96%', false, 5)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          fileid,
          replacefilename,
          {xtype:'hiddenfield',name:'ca.deal.action',id: mep + 'datadeal'},
          {xtype: 'hiddenfield', name: 'ca.filename', id: mep + 'filename' },
          {xtype: 'hiddenfield', name: 'ca.fileurl', id: mep + 'fileurl' }
        ]}
      ]},
      tools.FormTextArea('更改理由', 'ca.changereason', mep + 'changereason', 200, '100%', true, 9, 6),
      tools.FormTextArea('更改后内容', 'ca.changedesc', mep + 'changedesc', 200, '100%', true, 10, 6)
    ];
    me.disNews = ['changefilename','tranid','replacefilename'];
    me.disEdits = ['changefilename','tranid','replacefilename'];
    me.enNews = ['changefileid','dealdept','changereason','changedesc'];
    me.enEdits = ['changefileid','dealdept','changereason','changedesc'];
  },
  OnDevSearch:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.OnDevBeforeLoad();
    var changefileid = Ext.getCmp(mep + 'changefileid');
    changefileid.store.loadPage(1);
 },
 
OnDevBeforeLoad:function(){
    var me = this;
    var mep = me.tranPrefix;
    var applyid = Ext.getCmp(mep + 'changefileid');
    if(applyid.store){
      applyid.store.on('beforeload',function(store,options){
          Ext.apply(store.proxy.extraParams,{
            'bf.fileid':tools.GetValueEncode(mep + 'searchfileid1'),
            'bf.filename':tools.GetValueEncode(mep + 'searchfilename1')
          });
      });
    }
 },
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'dealdept', gpersist.UserInfo.user.deptid);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '业务编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '文件名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchfilename', id: mep + 'searchfilename', allowBlank: true },
      ' ', { id: mep + 'btnSearch', text: gpersist.STR_BTN_SEARCH, iconCls: 'icon-find', handler: me.OnSearch, scope: me },
      '-', ' ', { id: mep + 'btnAdd', text: gpersist.STR_BTN_NEW, iconCls: 'icon-add', handler: me.OnNew,scope: me },
      ' ', { id: mep + 'btnCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnCopy,scope: me},
      ' ', { id: mep + 'btnEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnEdit,scope: me}, 
      '-', ' ', { tooltip: gpersist.STR_BTN_REFRESH_NOW, iconCls: 'icon-pagerefresh', handler: me.OnResetForm, scope: me }
    ];    
    
    tools.SetToolbar(items, mep);
    
    var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
    
    me.tbGrid.add(toolbar);
  },
  
  OnBeforeSave: function(){
    var me = this;
    var mep = me.tranPrefix;
    if(tools.GetValue(mep + 'dealdept') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择会办部门！');
      return false;
    }
    
    var replacefilename = Ext.getCmp(mep+'replacefilename').getValue();
    if(replacefilename==null||replacefilename==''){
      tools.alert('替代文件不能为空，请上传替代文件！');
      return;
    }
    
    return true;
  },
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'ca.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'ca.changefilename': tools.GetEncode(tools.GetValue(mep + 'searchfilename'))
        });
      });
    };
  },
  
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id: mep + 'btnSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnSave, scope: me },  
      '-', ' ', { id: mep + 'btnFormEdit', text: gpersist.STR_BTN_EDIT, iconCls: 'icon-edit', handler: me.OnFormEdit, scope: me }, 
      ' ', { id: mep + 'btnDetailUpload', text: '上传', iconCls: 'icon-deal', handler: me.OnListUploadAtt, scope: me },
      '-', ' ', { id: mep + 'btnClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    if (me.hasPrevNext) {
      Ext.Array.insert(me.editToolItems, me.editToolItems.length - 2, ['-', ' ', { id: mep + 'btnPrevRecord', text: gpersist.STR_BTN_PREVRECORD, iconCls: 'icon-prev', handler: me.OnPrevRecord, scope: me }, 
        ' ', { id: mep + 'btnNextRecord', text: gpersist.STR_BTN_NEXTRECORD, iconCls: 'icon-next', handler: me.OnNextRecord, scope: me }]);
    }
  },
  
  OnListUploadAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var upload = Ext.create('Ext.ux.uploadPanel.UploadPanel',{  
      addFileBtnText : '选择文件...',  
      uploadBtnText : '上传',  
      removeBtnText : '移除所有',  
      cancelBtnText : '取消上传',
      file_upload_limit : 1
    });
    
    me.winUpload = Ext.create('Ext.Window', {
      id: mep + 'uploadwin',
      title: '上传',
      width: 700,
      height: 260,
      maximizable: true,
      closeAction: 'destroy',
      modal: true,
      layout: 'fit',
      plain: false,
      closable: true,
      draggable: true,
      constrain: true,
      items : [upload]
    });
    
    upload.store.removeAll();
    upload.on('showdetail',me.OnShowDetail,me);
    upload.on('closewin',me.OnCloseWin,me);
    
    me.winUpload.show();
  },
  
  OnShowDetail: function(render, item){
    var me = this;
    var mep = me.tranPrefix;
    var filename = Ext.getCmp(mep+'filename').getValue();
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    filename = item.name;
    fileurl = item.url;
    tools.SetValue(mep + 'replacefilename',filename);
    tools.SetValue(mep + 'fileurl',fileurl);
  },
  
  OnCloseWin:function(){
    var me = this;
    var mep = me.tranPrefix;
    
    me.winUpload.hide();
    if (Ext.getCmp(mep + 'uploadwin')) {
      Ext.getCmp(mep + 'uploadwin').destroy();
    };
  },
  
  OnLoadData: function (item) {
    var me = this;
    var mep = me.tranPrefix;
    if(item && !Ext.isEmpty(item.tranid)){
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'dealdept', item.dealdept);
      tools.SetValue(mep + 'changefilename', item.changefilename);
      tools.SetValue(mep + 'changefileid', item.changefileid);
      tools.SetValue(mep + 'replacefilename', item.replacefilename);
      tools.SetValue(mep + 'changereason', item.changereason);
      tools.SetValue(mep + 'changedesc', item.changedesc);

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
  
  OnFileSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.fileid) {
      tools.SetValue(mep+'changefileid',item.fileid);
      tools.SetValue(mep+'changefilename',item.filename);
    }
  },
  
//修改编辑面的按钮菜单
  OnAfterCreateEditToolBar:function(){
    var me = this;
    var mep = me.tranPrefix;
    me.editToolItems = [
      ' ', { id : mep + 'btnSave', text : gpersist.STR_BTN_SUBMIT, iconCls : 'icon-deal', handler : me.OnSave, scope : me },  
     // '-',  { id: mep + 'btnSubmit', text: '提交', iconCls: 'icon-deal', handler: me.OnSubmit, scope: me },
      '-', ' ', { id : mep + 'btnFormEdit', text : gpersist.STR_BTN_EDIT, iconCls : 'icon-edit', handler : me.OnFormEdit, scope : me },
      ' ', { id: mep + 'btnFormCopy', text: gpersist.STR_BTN_COPY, iconCls: 'icon-add', handler: me.OnFormCopy, scope: me }, 
      ' ', { id: mep + 'btnDetailUpload', text: '上传', iconCls: 'icon-deal', handler: me.OnListUploadAtt, scope: me },
      '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close',  handler: function () { me.winEdit.hide(); }, scope : me }
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
    
   }
   

   
});