Ext.namespace('gpersist');

gpersist = {
  PANEL_COLOR: '#ced9e7',
  LOGIN_TOP_COLOR: '#66dcfa',
  LOGIN_BOTTOM_COLOR: '#d0f0fd',
  
  HAS_MESSAGE_ALERT: true,
  
  DEFAULT_TREE_ROOT: '0000',
  
  VALID_STATUS: '1',
  
  SELECT_ALL_VALUE: '-1',
  SELECT_MUST_VALUE: '-2',
  SELECT_NULL_VALUE: '-3',
  SELECT_CUSTOM_VALUE: '-4',

  DATA_DEAL_SELECT: 1,
  DATA_DEAL_NEW: 2,
  DATA_DEAL_EDIT: 3,
  DATA_DEAL_DELETE: 4,
  DATA_DEAL_DEAL: 7,
  DATA_DEAL_VALID: 8,
  DATA_DEAL_INVALID: 9,
  DATA_DEAL_CHECK: 10,
  DATA_DEAL_UNCHECK: 11,
  DATA_DEAL_SUBMIT: 16,

  CONFIRM_TITLE:'提示',
  CONFIRM_MSG_EXITWIN: '有未保存的数据，是否继续关闭当前窗口？',
  CONFIRM_MSG_EXITLIST: '有未保存的数据，是否继续返回列表？',
  CONFIRM_MSG_UNSAVE: '有未保存的数据，是否继续加载数据？',
  CONFIRM_MSG_CONTINUE: '有未保存的数据，是否继续？',
  
  DEFAULT_PAGESIZE: 50,
  DEFAULT_AUTOLOAD: 120000,
  LIST_MODEL: 1,
  DETAIL_MODEL: 2,

  DEFAULT_LABELWIDTH: 80,
  
  DEFAULT_TOOL_WIDTH: 205,

  TRAN_SEARCH_DATEPART: -2,

  Months: [
    ['01', '01'],
    ['02', '02'],
    ['03', '03'],
    ['04', '04'],
    ['05', '05'],
    ['06', '06'],
    ['07', '07'],
    ['08', '08'],
    ['09', '09'],
    ['10', '10'],
    ['11', '11'],
    ['12', '12']
  ],

  GetMonths: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
  
  // Urls
  URL_BASE_LIB: 'jslib/',
  URL_BASE_CLASS: 'public/baseclass.js',
  URL_LOGIN: 'public/login.js',
  URL_MAIN: 'public/main.js',
  URL_HMAIN: 'public/hmain.js',
  
  // Class Names
  CLASS_BASE: 'gpersist.base',
  CLASS_LOGIN: 'gpersist.login',
  CLASS_MAIN: 'gpersist.main',
  CLASS_HMAIN: 'gpersist.hmain',
  
  // Actions
  ACTION_GET_ONLINEINFO: 'UserOnlineInfo.do',
  ACTION_USER_LOGIN: 'UserLogin.do',
  ACTION_USER_MUSTCHANGEPWD: 'UserMustChangePwd.do',
  ACTION_USER_LOGOUT: 'UserLoginOut.do',
  ACTION_USER_GETMENU: 'UserGetMenu.do',
  ACTION_USER_CHANGEPWD: 'UserChangePwd.do',
  ACTION_USER_GETNAME: 'UserGetUserName.do',
  ACTION_USER_UNLOCK: 'UserSetUnLock.do',
  ACTION_USER_SETPASSWD: 'UserSetPasswd.do',
  ACTION_USER_SAVE: 'SysSaveUser.do',
  ACTION_USER_SEARCH: 'SysSearchUser.do',
  
  ACTION_SYS_LISTSELECTS: 'SysListSelects.do',

  ACTION_COMPANY_LIST: 'OrgGetListCompany.do',
  ACTION_COMPANY_GET: 'OrgGetCompany.do',
  ACTION_COMPANY_SAVE: 'OrgSaveCompany.do',

  ACTION_DEPT_UNSET : 'OrgGetUnSetDept.do',
  ACTION_DEPT_SET: 'OrgGetSetDept.do',  
  ACTION_DEPT_TREEBYUSER: 'OrgDeptTreeByUser.do',
  ACTION_DEPT_TREE: 'OrgDeptTreeByCo.do',
  ACTION_DEPT_GET: 'OrgGetDept.do',
  ACTION_DEPT_SAVE: 'OrgSaveDept.do',
  
  ACTION_SEARCH_LOGIN: 'SysSearchLoginLog.do',
  ACTION_SEARCH_TRAN: 'SysSearchTranLog.do',
  
  ACTION_ROLE_GET: 'UserGetRole.do',
  ACTION_ROLE_GETDETAIL: 'UserGetRoleDetail.do',
  ACTION_ROLE_SAVE: 'UserSaveRole.do',
  ACTION_ROLE_UNSET : 'UserGetUnSetRole.do',
  ACTION_ROLE_SET: 'UserGetSetRole.do',
  ACTION_ROLE_SERSAVE: 'UserSaveSetRole.do',
  
  ACTION_PMT_LIST: 'SysListPmt.do',
  ACTION_PMT_EXPORT: 'SysExcelPmtTable.do',
  ACTION_PMT_TABLE: 'SysListPmtTable.do',
  ACTION_PMT_SQL: 'SysSqlPmtTable.do',
  ACTION_PMT_ALLSQL: 'SysSqlAllPmtTable.do',
  ACTION_PMT_SAVE: 'SysSavePmt.do',
  
  // Consts
  CON_WAIT_TIMEOUT: 3000,
  CON_PAGESIZE_MIN: 20,
  CON_PAGESIZE_MAX: 200,
  CON_PAGESIZE_STEP: 10,
  CON_PAGESIZE_LABLE: 35,
  CON_PAGESIZE_WIDTH: 90,
  CON_DEFAULT_LABELWIDTH: 80,
  
  // Strings
  STR_SYS_NAME: 'alms',
//STR_SYS_TITLE: '农业农村部农产品质量安全监督检验测试中心（南京）实验室管理系统',
  STR_SYS_TITLE: '农产品质量检测实验室LIMS系统',
  STR_LOGIN_WAIT_MESSAGE: '请稍等,正在登录!',
  STR_LOGIN_WAIT_TITLE: '登录处理',
  STR_TIP_PASSWD_VALID: '密码将在10天后过期！',
  STR_ERR_CHANGE_PASSWD1: '新密码两次输入不同，请确认后再进行保存！',
  STR_ERR_CHANGE_PASSWD2: '新密码最少8位、要求有数字、字母、大小写！',
  STR_ERR_CHANGE_PASSWD3: '新密码不能和旧密码一样！',
  STR_CHANGEPWD_WAIT_MESSAGE: '正在修改密码!',
  STR_MAX_TABPANEL: '您打开了太多的业务界面，会影响系统的使用性能，请关闭不需要的窗口后再次进行操作！',
  STR_DATA_WAIT_TITLE: '数据处理',
  STR_DATA_WAIT_MESSAGE: '请稍等,正在保存数据！',
  STR_CLOSE_WIN: '关闭该窗口',
  STR_FUNC_TITLE: '功能组',
  STR_WIN_TITLE: '窗口',  
  STR_MSG_EXCEL: '生成电子表格中...',
  STR_MSG_FILE: '生成文件中...',
  STR_MSG_NOEXP: '未进行查询或者查询无数据，不能进行导出操作！',
  STR_WIN_UNLOCK: '用户解锁',
  STR_WIN_RESETPASSWD: '重置用户密码',
  STR_NO_DATA: '没有数据！',
  STR_WIN_ROLEUNSET: '未分配角色',
  STR_WIN_ROLESET: '已分配角色',
  STR_WIN_ROLESETINFO: '角色分配信息',
  STR_TIP_SETROLEOK: '角色分配成功！',
  STR_TIP_NOSELECTPMT: '没有选择参数表！',
  STR_MSG_NEWCANCEL: '在新增或复制时取消操作将退出编辑状态，是否确认？',
  STR_MSG_BATCHDELETE: '是否需要进行批量删除？',
  STR_MSG_BATCHDELETETIP: '批量删除前请先选择需要进行删除的数据！',
  
  STR_COL_USER: '用户',
  STR_COL_PASSWD: '密码',
  STR_COL_OLDPASSWD: '原密码',
  STR_COL_NEWPASSWD: '新密码',
  STR_COL_REPASSWD: '确认新密码',
  STR_COL_COMPANY: '公司',
  STR_COL_DEPT: '机构',
  STR_COL_DEPTID: '机构编码',
  STR_COL_DEPTPID: '上级机构',
  STR_COL_CITYCODE: '城市代码',
  STR_COL_DEPTNAME: '机构名称',
  STR_COL_DEPTLEVEL: '机构级别',
  STR_COL_DEPTSTATUS: '机构状态',
  STR_COL_DEPTSHORT: '机构简称',
  STR_COL_DEPTTYPE: '机构类型',
  STR_COL_REMARK: '备注',
  STR_COL_USERID: '用户编号',
  STR_COL_USERNAME: '用户名称',
  STR_COL_USERTITLE: '岗位',
  STR_COL_USERBIRTHDAY: '生日',
  STR_COL_USERTELE: '联系电话',
  STR_COL_USERPOST: '类型',
  STR_COL_DEPTWORK : '入职日期',
  STR_COL_ISADMIN: '是否管理员',
  STR_COL_STATUS: '状态',
  STR_COL_ROLE: '角色',
  STR_COL_ROLEID: '角色编号',
  STR_COL_ROLENAME: '角色名称',
  STR_COL_ROLEDESC: '角色说明',
  STR_COL_PMT: '系统参数',
  STR_COL_SEX: '性别',
  
  STR_BTN_LOGIN: '登录',
  STR_BTN_RESET: '重置',
  STR_BTN_CLOSE: '关闭',
  STR_BTN_SAVE: '保存',
  STR_BTN_SUBMIT: '提交',
  STR_BTN_REFRESH_WIN: '刷新主界面',
  STR_BTN_REFRESH: '刷新',
  STR_BTN_CHANGE_PWD: '修改密码',
  STR_BTN_EXIT: '退出',
  STR_BTN_EDIT: '修改',
  STR_BTN_DELETE: '删除',
  STR_BTN_CANCEL: '取消',
  STR_BTN_REFRESH_NOW: '刷新界面',
  STR_BTN_NEW: '新增',
  STR_BTN_COPY: '复制',
  STR_BTN_ADD_SAME: '增加同级',
  STR_BTN_ADD_SUB: '增加下级',
  STR_BTN_BEGINDATE: '开始日期',
  STR_BTN_ENDDATE: '结束日期',
  STR_BTN_PREVDAY: '上一天',
  STR_BTN_NEXTDAY: '下一天',
  STR_BTN_PREVRECORD: '上一条',
  STR_BTN_NEXTRECORD: '下一条',
  STR_BTN_EXPORT: '导出',
  STR_BTN_UNLOCK: '解除锁定',
  STR_BTN_RESETPASSWD: '重置密码',
  STR_BTN_PRINT: '打印',
  STR_BTN_PAGESIZE: '每页',
  STR_BTN_SEARCH: '查询',
  STR_BTN_DEAL: '处理',
  STR_BTN_RETURNLIST: '返回列表',
  //报告其他的材料（委托书任务单原始记录）按钮名称为原始记录
  STR_BTN_REPORTOTHER: '原始记录',
  STR_BTN_VIEW: '查看',
  STR_BTN_INSERT: '插入',
  
  STR_PAGE_FMT: '{0} - {1} / {2}',
  STR_FMT_NOTNUL: '{0}不能为空！',
  STR_FMT_MAXLEN: '{0}长度不能超过{1}个字符！',
  STR_FMT_VALID: '请输入有效的{0}！',
  STR_FMT_MODIFY_OK: '{0}修改成功！',
  STR_FMT_LIST: '{0}列表',
  STR_FMT_DETAIL: '{0}详细信息',
  STR_FMT_MANAGER: '{0}管理',
  STR_FMT_READ: '取读{0}信息出错！',
  STR_FMT_NOLOAD: '未选择需要加载的{0}信息!', 
  STR_FMT_ADDSAME: '请先选择需要增加同级的{0}！',
  STR_FMT_ADDSUB: '请先选择需要增加下级的{0}！',
  STR_FMT_EDIT: '请先选择需要修改的{0}！',
  STR_FMT_SAVE_OK: '{0}保存成功！',
  STR_FMT_SUBMIT_OK: '{0}提交成功！',
  STR_FMT_SET_OK: '{0}设置成功！',
  STR_FMT_SAVE_CONFIRM: '是否需要保存{0}？',
  
  STR_LEN_14: '14',
  STR_VALID: 'Y',
  STR_INVALID: 'N',
  
  STR_CHECK: 'Y',
  STR_UNCHECK: 'N'
};

Ext.define('gpersist.mtreemodel', {
  extend: 'Ext.data.Model',
  fields: [
    { name: 'mid', type: 'string' },
    { name: 'text', type: 'string' },
    { name: 'url', type: 'string' },
    { name: 'tooltip', type: 'string' },
    { name: 'mnormalicon', type: 'string' },
    { name: 'mcode', type: 'string' },
    { name: 'istab', type: 'boolean' },
    { name: 'iconCls', type: 'string' }
  ]
});

Ext.define('gpersist.treemodel', {
  extend: 'Ext.data.Model',   
  fields: [{name: 'id',  type: 'string'},{ name: 'text', type: 'string' }]   
}); 

Ext.define('gpersist.gfdeptmodel', {
  extend: 'Ext.data.Model',   
  fields: [{name: 'id',  type: 'string'},{ name: 'text', type: 'string' }]   
}); 

gpersist.JsToLoad = undefined;
gpersist.JsLoadCallBack = undefined;
gpersist.JsLoadCallBackParam = undefined;

gpersist.LoadJs = function(js, callback, param) {
  gpersist.JsToLoad = js;
  gpersist.JsLoadCallBack = callback;
  gpersist.JsLoadCallBackParam = param;
  gpersist.LoadJsByArray();
};

gpersist.LoadJsByArray = function() {
  var js = gpersist.JsToLoad;
  var callback = gpersist.JsLoadCallBack;

  if (Ext.type(gpersist.JsToLoad) != 'string') {
    if (gpersist.JsToLoad.length == 1) {
      js = gpersist.JsToLoad[0];
      callback = gpersist.JsLoadCallBack;
    } else {
      js = gpersist.JsToLoad.shift();
      callback = gpersist.LoadJsByArray;
    }
  }
  
  Ext.Ajax.request({
    url : gpersist.URL_BASE_LIB + js,
    success : gpersist.LoadJsSuccess,
    method : 'GET',
    scope : callback
  });
};

gpersist.LoadJsSuccess = function(response) {
  try{
    eval(response.responseText); 
  }
  catch(ex) {
    alert(ex.toString());
  }

  this();
};

gpersist.NullCallBack = function() {
  
};

gpersist.CreateCallBack = function() {
  if (gpersist.JsLoadCallBackParam)  
    Ext.create(gpersist.JsLoadCallBackParam);
};

// Oninit Methods
gpersist.OnInit = function() {
  //if (!eval(gpersist.CLASS_BASE))
  //  gpersist.LoadJs([gpersist.URL_BASE_CLASS], gpersist.NullCallBack, '');
  
  if (!Ext.isDefined(gpersist.AllParams))
    gpersist.AllParams = Ext.create('Ext.util.MixedCollection');
  
  gpersist.GetSessionUser();
};

gpersist.InitUserInfo = function() {
  gpersist.UserInfo = null;
};

gpersist.GetUserInfo = function() {
  if (gpersist.UserInfo)
    return gpersist.UserInfo;
   else
    return null;
};

gpersist.GetSessionUser = function () {
  if (!Ext.isDefined(gpersist.UserInfo)) {
    var record = tools.JsonGet(gpersist.ACTION_GET_ONLINEINFO);
    
    if (record && record.data)
      gpersist.UserInfo = record.data;
  }
};

// Login Methods
gpersist.OnLogin = function() {
  if (eval(gpersist.CLASS_LOGIN)) 
    Ext.create(gpersist.CLASS_LOGIN);
  else
    gpersist.LoadJs([gpersist.URL_LOGIN], gpersist.CreateCallBack, gpersist.CLASS_LOGIN);
};

gpersist.OnLogined = function () {
  return Ext.isDefined(gpersist.UserInfo) && Ext.isDefined(gpersist.UserInfo.user) 
    && !Ext.isEmpty(gpersist.UserInfo.user.userid);
};

// Main Methods
gpersist.OnMain = function() {
  if (eval(gpersist.CLASS_MAIN)) 
    Ext.create(gpersist.CLASS_MAIN);
  else
    gpersist.LoadJs([gpersist.URL_MAIN], gpersist.CreateCallBack, gpersist.CLASS_MAIN);
};

gpersist.OnHMain = function() {
  if (eval(gpersist.CLASS_HMAIN)) 
    Ext.create(gpersist.CLASS_HMAIN);
  else 
    gpersist.LoadJs([gpersist.URL_HMAIN], gpersist.CreateCallBack, gpersist.CLASS_HMAIN);
};

gpersist.DefaultTranCallBack = function() {
  var menu = gpersist.JsLoadCallBackParam;
  
  if (menu && menu.url) {
    var menubean = menu.url.lastIndexOf("/") >= 0 ? menu.url.substring(menu.url.lastIndexOf("/") + 1) : menu.url;
    
    var prefix = gpersist.STR_SYS_NAME + '.';

    if (menu.mid >= 8000)
      prefix = 'gpersist.';
    
    if (eval(prefix + menubean)) {
      Ext.create(prefix + menubean, {mid:menu.mid,tranPrefix:menu.mcode});
    }
  }
};

gpersist.OnMenuClick = function(menu) {
  if (menu && menu.url) {
    var menubean = menu.url.lastIndexOf("/") >= 0 ? menu.url.substring(menu.url.lastIndexOf("/") + 1) : menu.url;
    var prefix = gpersist.STR_SYS_NAME + '.';
    
    if (menu.mid >= 8000)
      prefix = 'gpersist.';
    
    if (eval(prefix + menubean)) {
      Ext.create(prefix + menubean, {mid:menu.mid,tranPrefix:menu.mcode});
    }
    else {
      gpersist.LoadJs([menu.url + '.js'], gpersist.DefaultTranCallBack, menu);
    }
  }
};
