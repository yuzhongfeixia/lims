Ext.define('alms.editshowflowfile',{
   extend:'gpersist.base.busform',
   
  constructor:function(config){
      var me = this;
      Ext.apply(config,{
          editInfo:'流程附件',
          winWidth:750,
          winHeight:250,
          hasColumnUrl:true,
          columnUrl:'SysSqlColumn.do?sqlid=p_get_bustaskattach',
          storeUrl:'LabGetListBusTaskAttach.do?bta.attachtype=05',
          expUrl:'LabGetListBusTaskAttach.do',
          idPrevNext:'tranid',
          hasGridSelect:true,
          hasPage:true,
          hasPrevNext : true
      });
      me.callParent(arguments);
   },
   
   
   OnBeforeFormLoad : function () {
	    var me = this;
	    var mep = me.tranPrefix;
	    
	    me.OnInitGridToolBar();
	    
	     
	    var items = [
	                 '-', ' ', { id : mep + 'btnClose', text : gpersist.STR_BTN_RETURNLIST, iconCls : 'icon-close', handler : me.OnFormClose, scope : me }

	              ]; 
	    
	    
	    tools.SetToolbar(items, mep);
	     
	     var toolbar = Ext.create('Ext.toolbar.Toolbar', {items: items, border: false});
	     
	     me.tbGrid.add(toolbar);
	  },
	  
	  
	  
	  OnFormClose : function() {
		    var me = this;
		    
		    var mainpanel = Ext.getCmp('tpanel' + me.mid);// 获取tab
			// 如果已经存在tab
//			me.tabPanel.setActiveTab(mainpanel);// 激活要跳转的tab
		  },
   
   OnShow: function () {
	    var me = this;
	    var busfilesss = tools.OnGridLoad(me.plGrid, '请选择需要查看的数据！');
	    if(!Ext.isEmpty(busfilesss)){
	    	var str = busfilesss.attachurl.split(".");
	        alms.PopupFileShow(busfilesss.attachtypename+'附件预览', 'FileDownFile.do', busfilesss.attachurl,busfilesss.attachname+"."+str[str.length-1]);
	      	
	    }else{
	    	tools.alert('没有附件');
	        return false;
	    }
	  }
 
  
});

