Ext.define('alms.devtype', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;

      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      
      Ext.getCmp('tpanel' + me.mid).removeAll();
      
      me.OnGoodsChart();
      
      me.plMain = Ext.create('Ext.Panel', {
        frame:false,
        autoScroll : false,
        region: 'center',
        width:'100%',
        height:'100%',
        items:me.chart,
        title: me.editTitle,
        margins: '2 2 2 5',
        padding: '0 0 0 0'
      });
      Ext.getCmp('tpanel' + me.mid).add(me.plMain); 
    },
    
    OnGoodsChart:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var deptid = gpersist.GetUserInfo().dept.deptid;
      var devs = tools.JsonGet('FormGetDevType.do?dt.deptid='+deptid).data;
      var titledata = new Array();
      var devsdata = new Array();
      for(var i = 0; i < devs.length; i++){
        titledata.push(devs[i].devtypename);
        devsdata.push({value:devs[i].devcount,name:devs[i].devtypename})
      }
      me.chart = tools.DevsType(titledata,devsdata);
    }
  
});