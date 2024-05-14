Ext.define('Ext.ux.uploadPanel.UploadPanel',{
  extend : 'Ext.grid.Panel',
  alias : 'widget.uploadpanel',
  xtype: 'uploadPanel',
  
  width : 600,
  height : 300,
  
  //绑定事件，initComponent初始化itemsFF
  initComponent: function() {
    this.callParent();

    this.addEvents(
      'showdetail',
      'closewin'
    );    
  },

  columns : [
    {	xtype : 'rownumberer' }, 
    { text : '文件名', width : 100,	dataIndex : 'name' }, 
    { text : '替换文件名', width : 130,	dataIndex : 'filename' }, 
    { text : '类型', 	width : 70, dataIndex : 'type' }, 
    { text : '大小',	width : 70,	dataIndex : 'size',	
      renderer : function(v) {
		    return Ext.util.Format.fileSize(v);
		  }
		}, 
		{ text : '进度',	width : 130, dataIndex : 'percent',
      renderer : function(v) {
        var stml = '<div>' + '<div style="border:1px solid #008000;height:10px;width:115px;margin:2px 0px 1px 0px;float:left;">' 
          + '<div style="float:left;background:#FFCC66;width:' + v + '%;height:8px;"><div></div></div>' + '</div>' + '</div>';
        return stml;
      }
    }, 
    {	text : '状态',	width : 80,	dataIndex : 'status',	
      renderer : function(v) {
        var status;
			
        if (v == -1) {
				  status = "等待上传";
				} else if (v == -2) {
				  status = "上传中...";
				} else if (v == -3) {
				  status = "<div style='color:red;'>上传失败</div>";
				} else if (v == -4) {
				  status = "上传成功";
				} else if (v == -5) {
				  status = "停止上传";
				}
				
        return status;
			}
		}, 
		{ xtype : 'actioncolumn',	text : '操作',	width : 50,	items : [{
      icon : 'jslib/swfupload/delete.gif',
			tooltip : 'Remove',
			handler : function(grid, rowIndex, colIndex) {
        grid.store.remove(grid.store.getAt(rowIndex));
			}}]
		}
	],
	
  plugins: [Ext.create('Ext.grid.plugin.CellEditing', { clicksToEdit: 1 })],
      
  store : Ext.create('Ext.data.JsonStore', {
    autoLoad : false,
    fields : ['id','name','type','size','percent','status','filename']
  }),
  
  addFileBtnText : '选择文件...',
  uploadBtnText : '上传',
  removeBtnText : '移除所有',
  cancelBtnText : '取消上传',
  
  debug : false,
  
  file_size_limit : 100, //MB
  file_types : '*.*',
  file_types_description : '所有文件',
  file_upload_limit : 50,
  file_queue_limit : 0,
  
  post_params : {},
  upload_url : 'UploadFileUpLoad.do',
  flash_url : "jslib/swfupload/swfupload.swf",
  flash9_url : "jslib/swfupload/swfupload_fp9.swf",
  
  initComponent : function() {
    var me = this;
         
    me.dockedItems = [{ xtype: 'toolbar', dock: 'top', items: [
      { xtype:'button', itemId: 'addFileBtn', icon : 'jslib/swfupload/add.gif', text : me.addFileBtnText },
      { xtype: 'tbseparator' },
      { xtype : 'button', itemId : 'uploadBtn', icon : 'jslib/swfupload/up.gif', text : me.uploadBtnText,
          scope : this, handler : me.onUpload },
      { xtype: 'tbseparator' },
      { xtype : 'button', itemId : 'removeBtn', icon : 'jslib/swfupload/trash.gif', text : me.removeBtnText,
          scope : this, handler : me.onRemove },
      { xtype: 'tbseparator' },
      { xtype : 'button', itemId : 'cancelBtn', icon : 'jslib/swfupload/cancel.gif', disabled : true, text : me.cancelBtnText,
        scope : this, handler : me.onCancelUpload },
      { xtype : 'button', itemId : 'closeBtn', icon : 'jslib/swfupload/back.png', text : '关闭', 
        scope : this, handler : me.onCloseUpload }]
    }];
         
    me.callParent();
    
    me.down('button[itemId=addFileBtn]').on({  
      afterrender : function(btn) {
        var config = me.getSWFConfig(btn);
        
        me.swfupload = new SWFUpload(config);
  
        Ext.get(me.swfupload.movieName).setStyle({ position : 'absolute', top : 0, left : -2 }); 
      }, scope : this, buffer:300
    });
  },

  getSWFConfig : function(btn) {
    var me = this;
    var placeHolderId = Ext.id();
    var em = btn.getEl().child('em');
    
    if (em == null) {
      em = Ext.get(btn.getId() + '-btnWrap');
    }
    
    em.setStyle({
      position : 'relative',
      display : 'block'
    });
    
    em.createChild({
      tag : 'div',
      id : placeHolderId
    });
    
    return {
      debug : me.debug,
      flash_url : me.flash_url,
      flash9_url : me.flash9_url,
      upload_url : me.upload_url,
      file_post_name : 'file',
      post_params : me.post_params || { savePath : 'upload\\' },
      file_size_limit : (me.file_size_limit * 1024),
      file_types : me.file_types,
      file_types_description : me.file_types_description,
      file_upload_limit : me.file_upload_limit,
      file_queue_limit : me.file_queue_limit,
      button_width : em.getWidth(),
      button_height : em.getHeight(),
      button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
      button_cursor : SWFUpload.CURSOR.HAND,
      button_placeholder_id : placeHolderId,
      custom_settings : { scope_handler : me },
      swfupload_preload_handler : me.swfupload_preload_handler,
      file_queue_error_handler : me.file_queue_error_handler,
      swfupload_load_failed_handler : me.swfupload_load_failed_handler,
      upload_start_handler : me.upload_start_handler,
      upload_progress_handler : me.upload_progress_handler,
      upload_error_handler : me.upload_error_handler,
      upload_success_handler : me.upload_success_handler,
      upload_complete_handler : me.upload_complete_handler,
      file_queued_handler : me.file_queued_handler
      /*, file_dialog_complete_handler : me.file_dialog_complete_handler*/
    };
  },


  swfupload_preload_handler : function() {
    if (!this.support.loading) {
      Ext.Msg.show({
        title : '提示',
        msg : '浏览器Flash Player版本太低,不能使用该上传功能！',
        width : 250,
        icon : Ext.Msg.ERROR,
        buttons : Ext.Msg.OK
      });
      return false;
    }
  },

  file_queue_error_handler : function(file, errorCode, message) {
    switch(errorCode) {
      case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED :
        msg('上传文件列表数量超限,不能选择！');
        break;
        
      case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT :
        msg('文件大小超过限制, 不能选择！');
        break;
        
      case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE :
        msg('该文件大小为0,不能选择！');
        break;
        
      case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE :
        msg('该文件类型不允许上传！');
        break;
    }
    
    function msg(info) {
      Ext.Msg.show({
        title : '提示',
        msg : info,
        width : 250,
        icon : Ext.Msg.WARNING,
        buttons : Ext.Msg.OK
      });
    }
  },


  swfupload_load_failed_handler : function() {
    Ext.Msg.show({
      title : '提示',
      msg : '文件上传加载失败！',
      width : 180,
      icon : Ext.Msg.ERROR,
      buttons : Ext.Msg.OK
    });
  },


  upload_start_handler : function(file) {
    var me = this.settings.custom_settings.scope_handler;
    me.down('#cancelBtn').setDisabled(false);
  },


  upload_progress_handler : function(file, bytesLoaded, bytesTotal) {
    var me = this.settings.custom_settings.scope_handler;
    var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
    percent = percent == 100 ? 99 : percent;
    var rec = me.store.getById(file.id);
    
    if (rec) {
      rec.set('percent', percent);
      rec.set('status', file.filestatus);
      rec.commit();
    }
  },


  upload_error_handler : function(file, errorCode, message) {
    var me = this.settings.custom_settings.scope_handler;
    var rec = me.store.getById(file.id);
    
    if (rec) {
      rec.set('percent', 0);
      rec.set('status', file.filestatus);
      rec.commit();
    }
  },


  upload_success_handler : function(file, serverData, responseReceived) {
    var me = this.settings.custom_settings.scope_handler;
    var rec = me.store.getById(file.id);
    var rtn = Ext.JSON.decode(serverData);
    
    if (rtn && rec) {
      if (rtn.success) {
        rec.set('percent', 100);
        rec.set('filename', rtn.ufile);
        rec.set('status', file.filestatus);
      } else {
        rec.set('percent', 0);
        rec.set('filename', '');
        rec.set('status', SWFUpload.FILE_STATUS.ERROR);
      }
      
      rec.commit();    
  
      if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
        this.startUpload();
      } else {
        me.showBtn(me, true);
      }
  
      // 修正了传输失败时候不抛出事件
      if (Ext.JSON.decode(serverData).success) {
        file.url = Ext.JSON.decode(serverData).url;
        me.fireEvent('showdetail', me, file);
      }
    }
  },


  upload_complete_handler : function(file){

  },


  file_queued_handler : function(file) {
    var me = this.settings.custom_settings.scope_handler;
    me.store.add({
      id : file.id,
      name : file.name,
      filename : file.name,
      size : file.size,
      type : file.type.replace('.', ''), //去掉文件分隔符
      status : file.filestatus,
      percent : 0
    });
  },


  onUpload : function() {
    if (this.swfupload && this.store.getCount() > 0) {
      if (this.swfupload.getStats().files_queued > 0) {
        this.showBtn(this, false);
        this.swfupload.uploadStopped = false;
        this.swfupload.startUpload();
      }
    }
  },


  showBtn : function(me,bl) {
    me.down('#addFileBtn').setDisabled(!bl);
    me.down('#uploadBtn').setDisabled(!bl);
    me.down('#removeBtn').setDisabled(!bl);
    me.down('#cancelBtn').setDisabled(bl);
    if (bl) {
      me.down('actioncolumn').show();
    } else {
      me.down('actioncolumn').hide();
    }
  },


  onRemove : function() {
    var ds = this.store;
    for (var i = 0; i < ds.getCount(); i++) {
      var record = ds.getAt(i);
      var file_id = record.get('id');
      this.swfupload.cancelUpload(file_id, false);
    }
    ds.removeAll();
    this.swfupload.uploadStopped = false;
  },


  onCancelUpload : function() {
    if (this.swfupload) {
      this.swfupload.uploadStopped = true;
      this.swfupload.stopUpload();
      this.showBtn(this, true);
    }
  }, 
  
  onCloseUpload:function() {
    var me = this;
    
    me.onRemove();
    
    me.fireEvent('closewin', me);
  }
});