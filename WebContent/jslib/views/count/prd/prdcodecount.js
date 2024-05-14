Ext.define('alms.prdcodecount', {
  extend: 'gpersist.base.baseform',
  
  OnFormLoad: function () {
      var me = this;
      var mep = me.tranPrefix;

      if (!Ext.isDefined(Ext.getCmp('tpanel' + me.mid)))
        return;
      
      Ext.getCmp('tpanel' + me.mid).removeAll();
      
      me.OnServerCountyChart();
      var chart = Ext.create('Ext.ux.PCChartsComponent');
      
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
    
    OnServerCountyChart:function(){
      var me = this;
      var mep = me.tranPrefix;
      
      var prdcodeinfo = tools.JsonGet('FormGetPrdCodeCount.do').data;
      var prdnames = new Array();
      var lastnumbers = new Array();

      for(var i = prdcodeinfo.length - 1; i >= 0; i--){
        prdnames.push(prdcodeinfo[i].prdname);
        lastnumbers.push(prdcodeinfo[i].lastnumber);
      }

      me.chart = tools.PrdsCodeCount(prdnames,lastnumbers);
    }
  
});