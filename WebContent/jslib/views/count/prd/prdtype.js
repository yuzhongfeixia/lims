Ext.define('alms.prdtype', {
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
      var prds = tools.JsonGet('FormGetPrdType.do').data;
      var titledata = new Array();
      var prdsdata = new Array();
      for(var i = 0; i < prds.length; i++){
        titledata.push(prds[i].prdtypename);
        prdsdata.push({value:prds[i].prdcount,name:prds[i].prdtypename})
      }
      me.chart = tools.PrdsType(titledata,prdsdata);
    }
  
});