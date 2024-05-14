Ext.define('alms.manbasuser', {
  extend: 'gpersist.base.busform',
  
  constructor: function (config) {
    var me = this;
    Ext.apply(config, {
      editinfo: '人员档案管理',
      winWidth: 1000,
      winHeight: 450,
      hasColumnUrl: true,
      columnUrl: 'SysSqlColumn.do?sqlid=p_get_basuser',
      storeUrl: 'StaffSearchBasUser.do',
      saveUrl: 'StaffSaveBasUser.do',
      hasGridSelect: true,
      expUrl: 'StaffSearchBasUser.do',
      hasPage: true,
      hasPrevNext: true,
      idPrevNext: 'tranid',
      hasEdit:true,
      hasDetail: true,
      detailTitle: '档案文件',
      urlDetailColumn: 'SysSqlColumn.do?sqlid=p_get_basuserfile',
      urlDetail: 'StaffGetListBasUserFile.do',
      detailTabs: 1,
      hasDateSearch: true,
      hasDetailEdit: true
    });
    me.callParent(arguments);
  },
  
  OnBeforeCreateEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    var username = tools.UserPicker(mep + 'username','bu.username','人员姓名');
    
    username.on('griditemclick', me.OnUserSelect, me);
    
    me.editControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
          tools.FormText('业务编号', 'bu.tranid', mep + 'tranid', 20, '96%', false, 1),
          tools.FormText('所学专业', 'bu.usermajor', mep + 'usermajor', 20, '96%', true, 4),
          tools.FormText('毕业院校', 'bu.hometele', mep + 'hometele', 30, '96%', true, 7),
          tools.FormText('健康状况', 'bu.altername', mep + 'altername', 10, '96%', true, 10),
          tools.FormText('党团时间', 'bu.useridentity', mep + 'useridentity', 20, '96%', true, 13),
          tools.FormCombo('政治面貌', 'bu.userpolity', mep + 'userpolity', tools.ComboStore('UserPolity', gpersist.SELECT_MUST_VALUE), '96%', false, 16),
          tools.FormCombo('人员职称', 'bu.usertitle', mep + 'usertitle', tools.ComboStore('UserTitle', gpersist.SELECT_MUST_VALUE), '96%', false, 19)
          
        ]},
        
        { xtype: 'container', columnWidth: .33, layout: 'anchor', items: [
//          tools.FormText('人员姓名', 'bu.username', mep + 'username', 20, '96%', false, 2),
          username,
          tools.FormText('从事本领域年限', 'bu.usertele', mep + 'usertele', 30, '96%', true, 6),
          tools.FormCombo('性    别', 'bu.usersex', mep + 'usersex', tools.ComboStore('UserSex', gpersist.SELECT_MUST_VALUE), '96%', false, 8),
          tools.FormDate('出生日期', 'bu.borndate', mep + 'borndate', 'Y-m-d', '96%', true, 11),
          tools.FormCombo('最高学历', 'bu.useredu', mep + 'useredu', tools.ComboStore('UserEdu', gpersist.SELECT_MUST_VALUE), '96%', false, 14),
          tools.FormCombo('职务', 'bu.opertype', mep + 'opertype', tools.ComboStore('OperType', gpersist.SELECT_MUST_VALUE), '96%', false, 17),
          tools.FormCombo('人员状态', 'bu.userstatus', mep + 'userstatus', tools.ComboStore('UserStatus', gpersist.SELECT_MUST_VALUE), '96%', false, 20)
          
        ]},
        { xtype: 'container', columnWidth: .34, layout: 'anchor', items: [
          tools.FormCombo('所属部门', 'bu.deptid', mep + 'deptid', tools.ComboStore('DeptID', gpersist.SELECT_MUST_VALUE), '100%', false, 3),
          tools.FormText('本岗位年限', 'bu.useremail', mep + 'useremail', 30, '100%', true, 6),
          tools.FormText('民    族', 'bu.usernative', mep + 'usernative', 10, '100%', false, 9),
          tools.FormText('籍贯', 'bu.bornaddress', mep + 'bornaddress', 20, '100%', false, 12),
          tools.FormCombo('现在部门岗位', 'bu.userdegree', mep + 'userdegree', tools.ComboStore('UserDegree', gpersist.SELECT_MUST_VALUE), '100%', false, 15),
          tools.FormCombo('学位或专业技术职务', 'bu.userduty', mep + 'userduty', tools.ComboStore('UserDuty', gpersist.SELECT_MUST_VALUE), '100%', false, 18),
          tools.FormDate('参加工作时间', 'bu.begintest', mep + 'begintest', 'Y-m-d', '100%', false, 10,nowdate,80),
          {xtype:'hiddenfield',name:'bu.userid',id: mep + 'userid'},
          {xtype:'hiddenfield',name:'bu.deal.action',id: mep + 'datadeal'}
        ]}
      ]},
      //修改字段  用homeaddress、homeaddress代替新的意思
      tools.FormTextArea('技术工作简历', 'bu.homeaddress', mep + 'homeaddress', 600, '100%', true, 9, 4),
      tools.FormTextArea('主要业绩', 'bu.userremark', mep + 'userremark', 600, '100%', true, 9, 4)
    ];
    me.disNews = ['tranid'];
    me.disEdits = ['tranid'];
    me.enNews = ['username','deptid','usermajor','usertele','useremail',
                 'opertype','usersex','usernative','altername','borndate',
                 'bornaddress','useridentity','useredu','userdegree','userpolity',
                 'homeaddress','hometele','userremark','userstatus','usertitle',
                 'userduty','begintest','crtdate','modifydate'];
    me.enEdits = ['username','deptid','usermajor','usertele','useremail',
                  'opertype','usersex','usernative','altername','borndate',
                  'bornaddress','useridentity','useredu','userdegree','userpolity',
                  'homeaddress','hometele','userremark','userstatus','usertitle',
                  'userduty','begintest','crtdate','modifydate'];
    
  },
  
  OnUserSelect:function(render,item){
    var me = this;
    var mep = me.tranPrefix;
    if (item && item.userid) {
      var user = tools.JsonGet('UserGetUser.do?userid='+item.userid);

      tools.SetValue(mep+"userid",item.userid);
      tools.SetValue(mep+"username",item.username);
      tools.SetValue(mep+"deptid",item.deptid);
      tools.SetValue(mep+"userpost",item.userpost);
      tools.SetValue(mep+"usermajor",user.usermajor);
      tools.SetValue(mep+"usertele",user.usertele);
      tools.SetValue(mep+"useremail",user.useremail);
      tools.SetValue(mep+"usersex",user.usersex);
      tools.SetValue(mep+"borndate",user.birthdate);
      tools.SetValue(mep+"useredu",user.useredu);
      tools.SetValue(mep+"userduty",user.worktitle);
      tools.SetValue(mep+"usertitle",user.usertitle);
      tools.SetValue(mep+"userstatus",user.userstatus);
      tools.SetValue(mep+"useremail",user.postyear);
      tools.SetValue(mep+"usertele",user.workyear);
      tools.SetValue(mep+"usernative","汉");
      tools.SetValue(mep+"altername","良好");
   }
  },
  
  OnInitData : function() {
    var me = this;
    var mep = me.tranPrefix;
    me.callParent(arguments);
    tools.SetValue(mep + 'opertype', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userpolity', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'usertitle', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'usersex', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'useredu', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userduty', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userdegree', gpersist.SELECT_MUST_VALUE);
    tools.SetValue(mep + 'userstatus', gpersist.SELECT_MUST_VALUE);
  },
  
  OnBeforeFormLoad : function () {
    var me = this;
    var mep = me.tranPrefix;
    
    me.OnInitGridToolBar();
    
    var items = [
      ' ', { xtype: 'textfield', fieldLabel: '人员编号', labelWidth: 60, width: 180, maxLength: 40, name: 'searchtranid', id: mep + 'searchtranid', allowBlank: true },
      ' ', { xtype: 'textfield', fieldLabel: '人员名称', labelWidth: 60, width: 180, maxLength: 40, name: 'searchusername', id: mep + 'searchusername', allowBlank: true },
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
  
  OnBeforeSearch: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (me.gridStore) {
      me.gridStore.on('beforeload', function (store, options) {      
        Ext.apply(store.proxy.extraParams, {
          'bu.tranid': tools.GetEncode(tools.GetValue(mep + 'searchtranid')),
          'bu.username': tools.GetEncode(tools.GetValue(mep + 'searchusername'))
        });
      });
    };
  },
   
  OnLoadData: function (record) {
    var me = this;
    var mep = me.tranPrefix;
    if(record && !Ext.isEmpty(record.tranid)){
      var item = tools.JsonGet(tools.GetUrl('StaffGetBasUser.do?bu.tranid=') + record.tranid);
      tools.SetValue(mep + 'tranid', item.tranid);
      tools.SetValue(mep + 'userid', item.userid);
      tools.SetValue(mep + 'username', item.username);
      tools.SetValue(mep + 'deptid', item.deptid);
      tools.SetValue(mep + 'usermajor', item.usermajor);
      tools.SetValue(mep + 'usertele', item.usertele);
      tools.SetValue(mep + 'useremail', item.useremail);
      tools.SetValue(mep + 'opertype', item.opertype);
      tools.SetValue(mep + 'usersex', item.usersex);
      tools.SetValue(mep + 'usernative', item.usernative);
      tools.SetValue(mep + 'altername', item.altername);
      tools.SetValue(mep + 'borndate', item.borndate);
      tools.SetValue(mep + 'bornaddress', item.bornaddress);
      tools.SetValue(mep + 'useridentity', item.useridentity);
      tools.SetValue(mep + 'useredu', item.useredu);
      tools.SetValue(mep + 'userdegree', item.userdegree);
      tools.SetValue(mep + 'userpolity', item.userpolity);
      tools.SetValue(mep + 'homeaddress', item.homeaddress);
      tools.SetValue(mep + 'hometele', item.hometele);
      tools.SetValue(mep + 'userremark', item.userremark);
      tools.SetValue(mep + 'userstatus', item.userstatus);
      tools.SetValue(mep + 'usertitle', item.usertitle);
      tools.SetValue(mep + 'userduty', item.userduty);
      tools.SetValue(mep + 'begintest', item.begintest);
      tools.SetValue(mep + 'crtdate', item.crtdate);
      tools.SetValue(mep + 'modifydate', item.modifydate);
      me.OnDetailRefresh();
      return true;
    } else{
      tools.alert(Ext.String.format(gpersist.STR_FMT_READ, me.editInfo));
    }
  },
  
  OnBeforeSave: function(){
    var me = this;
    var mep = me.tranPrefix;
    
    if(tools.GetValue(mep + 'userpolity') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择政治面貌！');
      return false;
    }
    
    if(tools.GetValue(mep + 'usertitle') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择人员职称！');
      return false;
    }
    
    if(tools.GetValue(mep + 'usersex') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择性别！');
      return false;
    }
    
    if(tools.GetValue(mep + 'useredu') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择最高学历！');
      return false;
    }
    
    if(tools.GetValue(mep + 'userduty') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择学位或专业技术职务！');
      return false;
    }
    if(tools.GetValue(mep + 'userstatus') == gpersist.SELECT_MUST_VALUE){
      tools.alert('请选择人员状态！');
      return false;
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
          tools.SetValue(mep + 'tranid', action.result.data.tranid);
        }
      }
    }
    me.OnDetailRefresh();
  },
  
  OnDetailRefresh : function() {
    var me = this;
    var mep = me.tranPrefix;
    if (me.plDetailGrid && me.plDetailGrid.store) {
      me.plDetailGrid.store.proxy.url = tools.GetUrl('StaffGetListBasUserFile.do?buf.tranid=') + tools.GetValue(mep + 'tranid');
      me.plDetailGrid.store.load();
    }
  },
  
  // 附件上传
  OnCreateDetailWin: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    if (Ext.getCmp(mep + 'detailwin')) {
      Ext.getCmp(mep + 'detailwin').destroy();
    }
    
    var items = [
      ' ', { id: mep + 'btnDetailSave', text: gpersist.STR_BTN_SAVE, iconCls: 'icon-save', handler: me.OnListSave, scope: me },
      '-', ' ', { id: mep + 'btnDetailClose', text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: me.OnListCloseAtt, scope:me}
    ];
    
    me.OnBeforeCreateDetailEdit();
    
    me.plDetailEdit = Ext.create('Ext.form.Panel', {
      id:mep + 'pldetailedit',
      region : 'north',
      height : '40%',
      columnWidth:1,
      fieldDefaults: {
        labelSeparator: '：',
        labelWidth: gpersist.CON_DEFAULT_LABELWIDTH,
        labelPad: 0,
        labelStyle: 'font-weight:bold;'
      },
      frame: true,
      bodyStyle: 'padding:5px;background:#FFFFFF',
      defaultType: 'textfield',
      closable: false,
      header: false,
      unstyled: true,
      scope: me,
      tbar : Ext.create('Ext.toolbar.Toolbar', {items: items}),
      items: me.editDetailControls    
    });
    
    var upload = tools.SwfUpload();
    me.plAttDetailGrid = Ext.create('Ext.form.Panel', {
      id:mep + 'attdetailgrid',
      region : 'center',
      title:'上传文件',
      columnWidth:1,
      scope: me,
      items: [upload]    
    });
    
    upload.on('showdetail',me.OnShowDetail,me);
    
    me.winDetail = Ext.create('Ext.Window', {
      id: mep + 'detailwin',
      title: me.detailTitle,
      width: 600,
      height: 500,
      maximizable: false,
      closeAction: 'hide',
      modal: true,
      layout: 'border',
      plain: false,
      closable: false,
      draggable: true,
      constrain: true,
      items : [me.plDetailEdit,me.plAttDetailGrid]
    });
    
  },
  
  OnListNew : function(){
    var me = this;
    me.OnCreateDetailWin();
    me.OnInitDetailData();
    if(me.winDetail){      
      me.winDetail.show();
    }
  },
  
  OnAfterCreateDetailToolBar:function(){
    var me = this;
    var mep = this.tranPrefix;
    me.plDetailGrid.plugins = [];
    me.deitems = [
      ' ', { id : mep + 'btnDetailAdd', text : gpersist.STR_BTN_NEW, iconCls : 'icon-add', handler : me.OnListNew, scope : me },
      ' ', { id : mep + 'btnDetailDelete', text : gpersist.STR_BTN_DELETE, iconCls : 'icon-delete', handler : me.OnListDelete, scope : me },
      '-',' ', { id: mep + 'btnDownAll', text: '下载所有文件', iconCls: 'icon-down', handler: me.OnDownZip, scope: me }
    ];
  },
  
  OnListSelect: function (view, record) {
    var str = record.data.fileurl.split(".");
    console.log(record.data.filename+"."+str[str.length-1])
    alms.PopupFileShow('证书预览', 'FileDownFile.do', record.data.fileurl, record.data.filename+"."+str[str.length-1]);
  },
  
  OnDownZip: function () {
    var me = this, mep = me.tranPrefix;
    
    var fileurls = [];
    
    for (i = 0; i < me.plDetailGrid.store.getCount(); i++) {
      fileurls.push(me.plDetailGrid.store.getAt(i).data.filename + ':' +me.plDetailGrid.store.getAt(i).data.fileurl);
    }
    
    if (fileurls.length <= 0) {
      tools.alert('没有需要下载的文件！');
      return;      
    }
    
    var filename = tools.GetValue(mep + 'username') + "_证书";
    var iframe = document.getElementById('export');
    var plExport = Ext.getCmp('plexport');
    
    plExport.form.submit({
      url: 'FileDownZipFile.do',
      params: { filename: filename, fileurl: fileurls.join() },
      target: 'export'
    });
  },
  
  OnBeforeCreateDetailEdit: function () {
    var me = this;
    var mep = this.tranPrefix;
    var nowdate = new Date();
    
    me.OnInitGridToolBar();
    me.editDetailControls = [
      { xtype: 'container', anchor: '100%', layout: 'column', items: [
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('证书名称', 'bbd.filename', mep + 'filename', 20, '96%', true, 5),
          tools.FormText('证书编号', 'bbd.filenumber', mep + 'filenumber', 20, '96%', true, 5),
          tools.FormDate('获取时间', 'bbd.gettime', mep + 'gettime', 'Y-m-d', '96%', true, 8,nowdate)
        ]},
        { xtype: 'container', columnWidth: .5, layout: 'anchor', items: [
          tools.FormText('所学专业', 'bbd.major', mep + 'major', 20, '100%', true, 5),
          tools.FormText('证书类型', 'bbd.filetypename', mep + 'filetypename', 20, '100%', true, 5),
          tools.FormDate('有效时间', 'bbd.validtime', mep + 'validtime', 'Y-m-d', '100%', true, 8,nowdate)
        ]}                                                                 
      ]},
      { xtype: 'hiddenfield', name: 'bbd.fileurl', id: mep + 'fileurl' },
      { xtype: 'hiddenfield', name: 'bbd.fileno', id: mep + 'fileno' },
      tools.FormTextArea('备注', 'bbd.fileremark', mep + 'fileremark', 200, '100%', true, 103, 3)
      
    ]
    me.disDetailControls = [ 'filename'];
    me.enDetailControls = [ 'filenumber', 'gettime', 'major', 'filetypename','validtime', 'fileremark'];  
  },
  
  OnListSave: function () {
    var me = this;
    var mep = me.tranPrefix;
    
    var filename = Ext.getCmp(mep+ 'filename').getValue();
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    
    if (tools.InvalidForm(me.plDetailEdit.getForm())) {
      if(filename == "" || filename == null || filename == undefined){
        tools.alert('请上传附件！');
        return;
      }else{
        if (me.detailEditType == 1) {
          //可能有多个附件的情况
          var fileurls = fileurl.split(";");
          for(i = 0; i <fileurls.length; i++){
            var record = me.plDetailGrid.store.model.create({});
            record.data.filename = Ext.getCmp(mep + 'filename').getValue();
            record.data.fileurl = fileurls[i];
            record.data.major = Ext.getCmp(mep + 'major').getValue();
            record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
            record.data.filenumber = Ext.getCmp(mep + 'filenumber').getValue();
            record.data.validtime = Ext.getCmp(mep + 'validtime').getValue();
            record.data.filetypename = Ext.getCmp(mep + 'filetypename').getValue();
            record.data.gettime = Ext.getCmp(mep + 'gettime').getValue();
            record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
            record.data.fileno = i + parseInt(Ext.getCmp(mep + 'fileno').getValue());
            me.plDetailGrid.store.insert(me.plDetailGrid.store.getCount(), record);
          }
        }
        else {
          me.OnBeforeListSave(me.detailRecord);
          me.plDetailGrid.getView().refresh();
        }
      }
      me.winDetail.hide();
    }
  },

  OnShowDetail:function(render, item){
    var me = this;
    var mep = this.tranPrefix;
    
    var fileurl = Ext.getCmp(mep+'fileurl').getValue();
    
    if(item){
     
      if(fileurl == ""){
        fileurl = item.url;
      }else{
        fileurl = fileurl+';'+item.url;
      }
      tools.SetValue(mep + 'fileurl',fileurl);
    };
  },
  
  OnListDelete:function(){
    var me = this;
    var j = me.plDetailGrid.selModel.selected.items.length;
    
    for ( var i = 0; i < j; i++) {
      me.plDetailGrid.store.remove(me.plDetailGrid.selModel.selected.items[0]);
    }
    
    me.plDetailGrid.getView().refresh();
  },
  
  OnListCloseAtt:function(){
    var me = this;
    var mep = me.tranPrefix;
    var fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    fileurl = tools.GetEncode(tools.GetEncode(fileurl));
    tools.DoAction(tools.GetUrl('UploadFileDeleteByFileUrl.do?fileurl=') + fileurl);
    me.winDetail.hide();
    me.detailEditType = 1;
  },
  
  OnInitDetailData: function () {
    var me = this;
    var mep = me.tranPrefix;   
    var serial = me.plDetailGrid.store.getCount() + 1;
    tools.SetValue(mep + 'fileno', serial);
  },

  OnBeforeListSave : function(record) {
    var me = this;
    var mep = me.tranPrefix;
    record.data.major = Ext.getCmp(mep + 'major').getValue();
    record.data.filename = Ext.getCmp(mep + 'filename').getValue();
    record.data.filetypename = Ext.getCmp(mep + 'filetypename').getValue();
    record.data.fileurl = Ext.getCmp(mep + 'fileurl').getValue();
    record.data.fileno = Ext.getCmp(mep + 'fileno').getValue();
    record.data.filenumber = Ext.getCmp(mep + 'filenumber').getValue();
    record.data.validtime = Ext.getCmp(mep + 'validtime').getValue();
    record.data.gettime = Ext.getCmp(mep + 'gettime').getValue();
    record.data.fileremark = Ext.getCmp(mep + 'fileremark').getValue();
  }
   
});