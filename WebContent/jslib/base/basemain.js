Ext.define('gpersist.base.basemain', {
  plChangePwd: null,  
  winChangePwd: null,  
  topHeight: 30,  
  mainMargin: '0 2 2 0',
  outlookStyle: true,
  mainIcon: 'icon-outlook',
  mainTip: '左右布局',
  
  constructor : function() {
    var me = this;
    
    var viewport = Ext.getCmp('mainView');

    var clock = Ext.create('Ext.toolbar.TextItem', {id:'clock', text: Ext.Date.format(new Date(), 'Y-m-d G:i:s') });
    var sysinfo = Ext.create('Ext.toolbar.TextItem', {id:'mainsysinfo', text: '' });
    var userinfo = '';
    
    if (!gpersist.UserInfo) 
      gpersist.GetSessionUser();
    
    if (gpersist.UserInfo) {
      userinfo = gpersist.UserInfo.user.userid + '-' + gpersist.UserInfo.user.username;
    }

    var toolbar = Ext.createWidget('toolbar', {id:'mainTopBar', items: [
      '<b>' + gpersist.STR_SYS_TITLE + '</b>', '-', userinfo, '-', sysinfo, '->', clock, '-',
//      {tooltip: '待办事宜', iconCls: 'icon-export', id: 'maintodo', handler: me.OnBusTodo, scope: me }, '-',
      {tooltip: '待办事宜', iconCls: 'icon-export', id: 'maintodo', handler: me.OnTodoClick, scope: me }, '-',
      {tooltip: '关闭窗口', iconCls: 'icon-closewin', handler: me.OnCloseWindow, scope: me }, 
      {tooltip: me.mainTip, iconCls: me.mainIcon, handler: me.OnSwitch, scope: me }, 
      {tooltip: gpersist.STR_BTN_REFRESH_WIN, iconCls: 'icon-pagerefresh', handler: function () { window.location.reload(); }}, 
      '-', {text: '<b>' + gpersist.STR_BTN_CHANGE_PWD + '</b>', id: 'changepwd',iconCls: 'icon-passwd', handler: me.OnChangePwd, scope: me },
      {text: '<b>' + gpersist.STR_BTN_EXIT + '</b>', id: 'exit',iconCls: 'icon-exit', handler: function () { 
        Ext.Ajax.request({
          url: tools.GetUrl(gpersist.ACTION_USER_LOGOUT),
          async: false,
          method: 'POST',
          success: function (response, opts) {                
          },
          failure: function (response) {
          }
        });
        
        var win = Ext.getCmp('alertmessage');
        if (win) win.hide();
        
        gpersist.UserInfo = null;
        if (gpersist.AllParams)
          gpersist.AllParams.clear();
        
        gpersist.OnLogin(); 
      }}
    ]});
    
    var topToolbar = Ext.create('Ext.container.Container', {
      
    });
    
    topToolbar.add(toolbar);
    
    if (!me.outlookStyle)
      topToolbar.add(me.OnCretaeMenu());
    
    var plTop = Ext.create('Ext.Panel', {
      border: false, 
      margins: '2 2 0 2', 
      bodyStyle: 'background-color:#dfe8f6;',
      region: 'north', 
      tbar: topToolbar,
      height: me.topHeight, 
      split: false,
      listeners: {
        render: {
          fn: function () {
            Ext.TaskManager.start({
              run: function () {
                Ext.fly(Ext.getCmp('clock').getEl()).update(Ext.Date.format(new Date(), 'Y-m-d G:i:s'));
              },
              interval: 1000
            });
          },
          delay: 100
        }
      }
    });
    
    var plconsole = new Ext.Panel({
      id: 'mainconsole',
      autoScroll: true,
      titleCollapse: true,
      closable: false,
      border: false,
      title: '主控台',
      iconCls: 'miorg', 
      autoDestroy:true,
      layout: {type: 'border',padding: '0 0 0 0'},
      listeners: { 'close': function() {if(Ext.isIE) { CollectGarbage(); }} }
    });
    
    var plTabMain = Ext.create('Ext.TabPanel',{
      id: 'mainTabs',
      tabWidth: 80,
      minTabWidth: 100,
      resizeTabs: true,
      enableTabScroll: true,
      region: 'center',
      margins: '0 2 2 2',
      activeTab: 0,
      //bodyCls: 'tabbgimage',
      plugins: [{
        ptype: 'tabscrollermenu',
        menuPrefixText: gpersist.STR_WIN_TITLE,
        maxText: 15,
        pageSize: 5
      }],
      listeners: { render: { fn: me.OnMessageAlert, delay: 1000, scope: me } }
    });
    
//    Ext.getCmp("mainTabs").add(plconsole);

    viewport.removeAll();
    viewport.add(plTop);

    if (me.outlookStyle)
      viewport.add(me.OnCretaeMenu());

    viewport.add(plTabMain);

    var plExcel = Ext.create('Ext.form.Panel', { 
      id: 'plexport',
      standardSubmit: true,
      title: 'ex'
    });
    
    Ext.DomHelper.append(document.body, {
      tag: 'div',
      id: 'exportdiv',
      html: '<iframe name="export" id="export"></iframe>',
      style: 'display:none'
    });
    
    plExcel.render('exportdiv');

//    var mainconsole = tools.GetFormByPath('alms', 'views/', 'mainconsole', {} )

//    mainconsole.OnFormLoad();
  },
  
  OnSwitch: function () {
    
  },
  
  
  OnCretaeMenu: function () {
    return null;
  },
  
  OnCloseWindow: function () {
    var tabs = Ext.getCmp("mainTabs");
    
    if (tabs) {
      for (var i = tabs.items.length - 1; i >= 0; i--) {
        tabs.items.getAt(i).close();
      }
    }
  },
  
  OnMenuClick: function(data) {
    if (data.leaf) {
      if (!data.istab) {
        gpersist.OnMenuClick(data);
        return;
      }
      var pl = Ext.getCmp('tpanel' + data.mid);

      if (pl) {
        
      }
      else {
        if (data.url == '')
          return;

        if (Ext.getCmp("mainTabs").items.getCount() >= 10) {
          tools.alert(gpersist.STR_MAX_TABPANEL);
          return;
        }

        var panelicon = 'icon-component';
        if (!Ext.isEmpty(data.mnormalicon))
          panelicon = data.mnormalicon;

        pl = new Ext.Panel({
          id: 'tpanel' + data.mid,
          autoScroll: true,
          titleCollapse: true,
          closable: true,
          border: false,
          closeText: gpersist.STR_CLOSE_WIN,
          title: data.text,
          tooltip: data.tooltip,
          iconCls: panelicon, 
          autoDestroy:true,
          layout: {type: 'border',padding: '0 0 0 0'},
          listeners: {'close': function() {if(Ext.isIE) { CollectGarbage(); }},
            'afterrender': function() { gpersist.OnMenuClick(data); }}
        });

        Ext.getCmp("mainTabs").add(pl);
      }

      Ext.getCmp("mainTabs").setActiveTab(pl);
    }
  },
  
//  OnBusTodo: function () {
//	 
//  },
//  
  OnTran: function (mid) { 
    var me = this;

    var data = {};
    
    var pl = Ext.getCmp('tpanel' + data.mid);
    
    if (pl) {
      Ext.getCmp("mainTabs").remove(pl);
    }

    if (Ext.getCmp("mainTabs").items.getCount() >= 10) {
      tools.alert(gpersist.STR_MAX_TABPANEL);
      return;
    }
    
    var panelicon = 'icon-component';
    if (!Ext.isEmpty(data.mnormalicon))
      panelicon = data.mnormalicon;
    
    pl = new Ext.Panel({
      id: 'tpanel' + data.mid,
      autoScroll: true,
      titleCollapse: true,
      closable: true,
      border: false,
      closeText: gpersist.STR_CLOSE_WIN,
      title: data.text,
      tooltip: data.tooltip,
      iconCls: panelicon, 
      autoDestroy:true,
      layout: {type: 'border',padding: '0 0 0 0'},
      listeners: {'close': function() {if(Ext.isIE) { CollectGarbage(); }},
        'afterrender': function() { gpersist.OnMenuClick(data); }}
    });
    
    Ext.getCmp("mainTabs").add(pl);
    Ext.getCmp("mainTabs").setActiveTab(pl);
  },
  
  OnChangePwd: function() {
    var me = this;
    
    if (!me.plChangePwd) {
    
      me.plChangePwd = Ext.create('Ext.form.Panel', {
        id: 'main_plchangepwd',
        fieldDefaults: {
          width: 370,
          labelSeparator: '：',
          labelWidth: 90,
          labelPad: 0,
          labelStyle: 'font-weight:bold;'
        },
        title: '',
        border: false,
        bodyStyle: 'background-color:' + gpersist.PANEL_COLOR + ';padding:10px;',
        width: 400,
        defaultType: 'textfield',
        items: [{
          fieldLabel: gpersist.STR_COL_OLDPASSWD,
          name: 'user.username',
          id: 'main_oldpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_OLDPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_NEWPASSWD,
          name: 'user.userpassword',
          id: 'main_newpasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_NEWPASSWD),
          allowBlank: false
        },
        {
          fieldLabel: gpersist.STR_COL_REPASSWD,
          name: 'user.remark',
          id: 'main_repasswd',
          type: 'password',
          inputType: 'password',
          selectOnFocus: true,
          labelStyle: 'font-weight:bold;',
          blankText: Ext.String.format(gpersist.STR_FMT_VALID, gpersist.STR_COL_REPASSWD),
          allowBlank: false
        }],
        listeners: {
          afterRender: function (form, options) {
            this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
              enter: me.OnSavePasswd,
              scope: me
            });
          }
        }
      });
    }

    if (!me.winChangePwd) {
      me.winChangePwd = Ext.create('Ext.window.Window', {
        id: 'main_changepwd',
        title: gpersist.STR_BTN_CHANGE_PWD,
        width: 400,
        height: 150,
        maximizable: true,
        modal: true,
        layout: 'fit',
        plain: false,
        buttonAlign: 'center',
        closable: true,
        closeAction: 'hide', border: false,
        items: [me.plChangePwd],
        buttons: [{ text: gpersist.STR_BTN_SAVE, id: 'btnSave', handler: me.OnSavePasswd, scope: me },
                  { text: gpersist.STR_BTN_CLOSE, handler: function () { me.winChangePwd.hide(); } }]
      });

  
    }
    
    me.winChangePwd.show();
    Ext.getCmp('main_oldpasswd').reset();
    Ext.getCmp('main_newpasswd').reset();
    Ext.getCmp('main_repasswd').reset();
  },
  
  OnSavePasswd: function () {
    var me = this;

    if (tools.GetValue('main_newpasswd') != tools.GetValue('main_repasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD1);
      return;
    }
    
    if (!tools.CheckPasswd(tools.GetValue('main_newpasswd'))) {
      //tools.alert(gpersist.STR_ERR_CHANGE_PASSWD2);
      //return;
    }

    if (tools.GetValue('main_newpasswd') == tools.GetValue('main_oldpasswd')) {
      tools.alert(gpersist.STR_ERR_CHANGE_PASSWD3);
      return;
    }
    
    if(me.plChangePwd) {
      if (me.plChangePwd.form.isValid()) {
        me.plChangePwd.form.submit({
          url: tools.GetUrl(gpersist.ACTION_USER_CHANGEPWD),
          waitMsg: gpersist.STR_CHANGEPWD_WAIT_MESSAGE,
          waitTitle: gpersist.STR_DATA_WAIT_TITLE,
          success: function (form, action) {
            tools.alert(Ext.String.format(gpersist.STR_FMT_MODIFY_OK, gpersist.STR_COL_PASSWD));
            me.winChangePwd.hide();
          },
          failure: function (form, action) {
            tools.ErrorAlert(action);
          }
        });
      }
    }
  },
  
  autoHide: 2,
  lastDate: null,
  columns: [],
  
  OnMessageAlert: function () {
    var me = this;
    var i = 0;
    var closeall;
    
    var task = {
      run: function () {
        var me = this;
        var rtn, lists = [];
        var win = Ext.getCmp('alertmessage');
        
        Ext.Ajax.request({
          url: 'FlowGetMyTodoAlert.do',
          async: false,
          method: 'POST',
          success: function (response, opts) {
            rtn = Ext.decode(response.responseText);      
          },
          failure: function (response) {
          }
        });
        if (rtn && rtn.data && (rtn.data.length > 0)) {
          for (var i = 0; i < rtn.data.length; i++)
            lists.push({ tododesc: rtn.data[i].tododesc, todotype: rtn.data[i].todotype });
        }
        else {
          if (win)
            win.hide();
            
          return;
        }        
        
//        var todos = tools.JsonGet('FlowGetMyTodoCount.do');
//        if ((todos == null) || (todos.search.total <= 0)) {
//          if (win)
//            win.hide();
//            
//          return;
//        }
        
        // 判断是否在待办事宜中
        var maintabs = Ext.getCmp("mainTabs");
        
        if (maintabs) {
          var activepl = maintabs.getActiveTab();
          
          if (activepl && (activepl.getId() == 'tpanel901'))
            return;
        }
        
        me.lastDate = new Date();
        
        if(!win) {    
          tools.SimpleColumnsByUrl('SysSqlColumn.do?sqlid=p_get_alertmsg', me.columns, 'malt_', null, false);
          
          var plalert = Ext.create('Ext.grid.Panel', {
            id: 'mainplalert',
            region: 'center',
            frame: false,
            border: false,
            margins: '0 0 0 0',
            padding: '0 0 0 0',
            loadMask: true,
            columnLines: true,
            viewConfig: {
              autoFill: true,
              stripeRows: true
            },
            scroll: 'vertical',
            hideHeaders: true,
            columns: me.columns,
            listeners: {'itemdblclick' : {fn: me.OnAlertClick, scope:me } }
          });
    
          win = new Ext.Window({
            id: 'alertmessage',
            title:'消息',
            width:300,
            height:120,   
            modal : false,  
            layout: 'fit',
            plain: true,  
            shadow:false, //去除阴影  
            draggable:false, //默认不可拖拽  
            resizable:false,  
            closable: true,
            closeAction:'hide', //默认关闭为隐藏  
            //items: [{xtype:'label', id:'todocount', html: '',
            //  listeners: { render: {fn: me.OnSetTodo, scope: me} } }],
            items: [plalert],
            constructor: function(conf){ 
              win.superclass.constructor.call(this,conf);
              this.initPosition(true); 
            },
            initEvents: function() {  
              win.superclass.initEvents.call(this);
              this.initPosition(true); 
              
              this.on('close', function() { closeall = true; } );
              this.on('beforeshow', this.showTips);  
              this.on('beforehide', this.hideTips); 
              Ext.EventManager.onWindowResize(this.initPosition, this); //window大小改变时，重新设置坐标  
              Ext.EventManager.on(window, 'scroll', this.initPosition, this); //window移动滚动条时，重新设置坐标  
            },  
            
            //参数: flag - true时强制更新位置  
            initPosition: function(flag) {
              if(true !== flag && this.hidden){ //不可见时，不调整坐标  
                return false;  
              }  
              var doc = document, bd = (doc.body || doc.documentElement);
              //ext取可视范围宽高(与上面方法取的值相同), 加上滚动坐标  
              var left = doc.body.scrollWidth - bd.scrollLeft-this.width-4;
              var top = doc.body.scrollHeight - bd.scrollTop - this.height-4;
              this.setPosition(left, top);
            },  
            
            showTips: function() { 
              var self = this;  
              if(!self.hidden){return false;}  
              self.initPosition(true); //初始化坐标  
              self.el.slideIn('b', {  
                callback: function() {  
                  //显示完成后,手动触发show事件,并将hidden属性设置false,否则将不能触发hide事件  
                  self.fireEvent('show', self);  
                  self.hidden = false;  
                }  
              });  
              return true; //不执行默认的show  
            },  
            
            hideTips: function() { 
              var self = this;  
              if(self.hidden) { return false; }  
              self.el.slideOut('b', {  
                callback: function() {  
                 //渐隐动作执行完成时,手动触发hide事件,并将hidden属性设置true  
                  self.fireEvent('hide', self);  
                  self.hidden = true; 
                }  
              });  
              return true; //不执行默认的hide  
            }
          });
        }       
               
        var al = Ext.getCmp('mainplalert');
        
        al.reconfigure(new Ext.data.JsonStore({
          fields: ['tododesc', 'todotype'],
          data: lists
        }));
  
        win.show();
        
        /*
        if (Ext.getCmp('todocount')) {
          Ext.fly(Ext.getCmp('todocount').getEl()).update('&nbsp;&nbsp;您有&nbsp;&nbsp;<b><font color="Red">' + todos.search.total + '</font></b>&nbsp;&nbsp;项待办事宜，请尽快处理！');
        }*/
        
      },
      interval: 120000, scope: me
    };
    
    Ext.TaskManager.start(task);
  },
  
  OnAlertClick: function (v, record) {
    var me = this;
    var url = '';
    var mcode = '';
    var mid = 0;
    var title = '';
    
    if (record.data.todotype == '1') {
      url = 'views/flow/manbustodo';
      mcode = '0901';
      mid = 901;
      title = '待办事宜';
    }
    else if (record.data.todotype == '2') {
      url = 'views/bus/qualifiedtodo';
      mcode = '0169';
      mid = 169;
      title = '待办合格报告';
    }
    else if (record.data.todotype == '3') {
      url = 'views/bus/unqualifiedtodo';
      mcode = '0169';
      mid = 169;
      title = '待办不合格报告';
    }
    else if (record.data.todotype == '4') {
      url = 'views/file/manfiletodo';
      mcode = '1702';
      mid = 1702;
      title = '文件查阅授权';
    }
    else if (record.data.todotype == '5') {
      url = 'views/glp/manglptodo';
      mcode = '2302';
      mid = 2302;
      title = 'GLP文件查阅授权审核';
    }
    else if (record.data.todotype == '6') {
      url = 'views/lab/getnoticerecv';
      mcode = '0105';
      mid = 105;
      title = '抽样通知单接单';
    }
    else if (record.data.todotype == '7') {
      url = 'views/lab/bustaskacc';
      mcode = '0145';
      mid = 145;
      title = '任务单接收';
    }
    else if (record.data.todotype == '8') {
      url = 'views/lab/buslabready';
      mcode = '0153';
      mid = 153;
      title = '试验准备';
    }
    else if (record.data.todotype == '9') {
      url = 'views/lab/manbuslab';
      mcode = '0155';
      mid = 155;
      title = '试验';
    }
    else if (record.data.todotype == '10') {
      url = 'views/lab/manaccsample';
      mcode = '0137';
      mid = 137;
      title = '收样管理';
    }else if (record.data.todotype == '11') {
      url = 'views/dev/calibplan/devcalibplan';
      mcode = '1040';
      mid = 1040;
      title = '设备检定计划';
    }else if (record.data.todotype == '12') {
      url = 'views/dev/checkplan/devcheckplan';
      mcode = '1047';
      mid = 1047;
      title = '设备期间核查计划';
    }else if (record.data.todotype == '14') {
      url = 'views/lab/manbusrecord';
      mcode = '0163';
      mid = 163;
      title = '原始记录表管理';
    }else if (record.data.todotype == '15') {
      url = 'views/lab/approvebusrecord';
      mcode = '0164';
      mid = 164;
      title = '原始记录表复核';
    }else if (record.data.todotype == '16') {
      url = 'views/lab/auditbusrecord';
      mcode = '0166';
      mid = 166;
      title = '原始记录表审核';
    }else if (record.data.todotype == '17') {
      url = 'views/lab/labreportapprove';
      mcode = '0173';
      mid = 173;
      title = '试验报告审批';
    }else if (record.data.todotype == '18') {
      url = 'views/lab/labreportaudit';
      mcode = '0174';
      mid = 174;
      title = '试验报告审核';
    }else if (record.data.todotype == '19') {
      url = 'views/lab/labreportprint';
      mcode = '0178';
      mid = 178;
      title = '试验报告打印';
    }else if (record.data.todotype == '20') {
      url = 'views/lab/manbusreport';
      mcode = '0172';
      mid = 172;
      title = '试验报告编制';
    }else if (record.data.todotype == '21') {
      url = 'views/lab/labbackupaudit';
      mcode = '0176';
      mid = 176;
      title = '申领备份样品';
    }else if (record.data.todotype == '22') {
      url = 'views/lab/manbustask';
      mcode = '0143';
      mid = 143;
      title = '任务单下达';
    }else if (record.data.todotype == '23') {
      url = 'views/lab/labreportapprove';
      mcode = '0173';
      mid = 173;
      title = '试验报告审批';
    }else if (record.data.todotype == '24') {
      url = 'views/lab/labreportaudit';
      mcode = '0174';
      mid = 174;
      title = '试验报告审核';
    }else if (record.data.todotype == '13') {
      url = 'views/lab/bustaskacc';
      mcode = '0145';
      mid = 145;
      title = '任务单接收';
    }else if (record.data.todotype == '25') {
      url = 'views/prd/prdlack';
      mcode = '1106';
      mid = 1106;
      title = '耗材库存不足查询';
    }else if (record.data.todotype == '26') {
      url = 'views/lab/manbustask';
      mcode = '0143';
      mid = 143;
      title = '单样品任务单下达';
    }else if (record.data.todotype == '27') {
        url = 'views/lab/sampsrecordapprove';
        mcode = '0170';
        mid = 170;
        title = '多样品原始记录复核';
      }else if (record.data.todotype == '28') {
          url = 'views/lab/samplesbusrecordaudit';
          mcode = '0177';
          mid = 177;
          title = '多样品原始记录审核';
     }else if (record.data.todotype == '29') {
        url = 'views/lab/samplesrecord';
        mcode = '0168';
        mid = 168;
        title = '多样品原始记录';
     } else if (record.data.todotype == '30') {
          url = 'views/lab/bustaskacc';
          mcode = '0145';
          mid = 145;
          title = '任务单接收';
        }
    
    
    
    if (mid == 0) return;
    
    var todomenu = {mid: mid, text: title, url: url,tooltip: '',
      mnormalicon: '', mcode: mcode, istab:true, iconCls:'', parentId:'root', index:0, depth:1, expanded:false, expandable:true,
      checked:false, leaf:true, cls:'', icon:'' };
      
    me.OnMenuClick(todomenu);
    
    var win = Ext.getCmp('alertmessage');
    
    if (win)
      win.hide();
  },
  
  OnSetTodo: function () {
    var me = this;
    
    Ext.fly(Ext.getCmp('todocount').getEl()).on('click', me.OnTodoClick, me);
  },
  
  OnTodoClick: function () {
    var me = this;
    
    var todomenu = {mid: 401, text:'待办事宜', url:'views/flow/manbustodo',tooltip: '',
      mnormalicon: '', mcode: '901', istab:true, iconCls:'', parentId:'root', index:0, depth:1, expanded:false, expandable:true,
      checked:false, leaf:true, cls:'', icon:'' };
      
    me.OnMenuClick(todomenu);
    
    var win = Ext.getCmp('alertmessage');
    
    if (win)
      win.hide();
  }
});
