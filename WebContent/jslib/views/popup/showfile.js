Ext.define('alms.showfile', {
  extend: 'Ext.util.Observable',
  
  title: '',
  fileaction: '',
  fileurl: '',
  filename: '',
  winEdit: null,
  winWidth: 800,
  winHeight: 600,
  
  constructor : function(config) {
    var me = this;
    
    me.callParent(arguments);
    
    me.OnInitConfig();
    Ext.apply(me, config);
  },
  
  OnInitConfig: function () {
    var me = this;
       
    me.title = '';
    me.fileurl = '';
    me.fileaction = '';
    me.winEdit = null;
    me.winWidth = 800;
    me.winHeight = 600;
    me.filename = '';
  },
  
  OnFormLoad: function () {
    var me = this;
   
    if (Ext.getCmp('winfileshow')) {
      Ext.getCmp('winfileshow').destroy();
    }
    
    var items = [
      ' ', { text: '下载文件', iconCls: 'icon-down', handler: me.OnDownLoadFile, scope: me },          
      '-', ' ', { text: gpersist.STR_BTN_CLOSE, iconCls: 'icon-close', handler: function () { me.winEdit.hide(); } }
    ];
    
    me.winEdit = Ext.create('Ext.Window', {
      id: 'winfileshow',
      title: Ext.isEmpty(me.title) ? '文件预览' : me.title,
      width: me.winWidth,
      height: me.winHeight,
      maximizable: true,
      closeAction: 'hide',
      modal: true,
      layout: 'fit',
      plain: false,
      maximized: true,
      closable: true,
      tbar:Ext.create('Ext.toolbar.Toolbar', { items: items })
    });

    me.winEdit.show();
  },
  
  OnFormShow: function () {
    var me = this;
    
    if (me.winEdit)
      me.winEdit.show();
  },
  
  OnDownLoadFile: function () {
    var me = this;

    if (!Ext.isEmpty(me.fileaction)) {
      var iframe = document.getElementById('export');
  
      var plExport = Ext.getCmp('plexport');
      
      plExport.form.submit({
        url: me.fileaction,
        params: { filename: me.filename, fileurl: me.fileurl },
        target: 'export'
      });
    };
  },
  
  OnSetData: function (title, fileaction, fileurl, filename) {
    var me = this;
    
    me.fileurl = fileurl;
    me.fileaction = fileaction;
    me.filename = filename;
    me.winEdit.setTitle(title);
    me.winEdit.update('<iframe frameborder=0 width="100%" height="100%" allowtransparency="true" scrolling=auto src="jslib/flexpaper/flexpaper.jsp?file=' + encodeURIComponent(fileurl) +'"></iframe>');    
  }
});