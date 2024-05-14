Ext.define("Ext.ux.TPChartsComponent", {  
	extend: "Ext.Component",  
    alias: "widget.echartscomponent",
    width: '100%',
    height: '100%',
    border: false,
    config: {  
      option: null  
    },  
    
    initComponent: function () {  
    	var me = this;  
    	if (!me.height) {  
    		throw new Error("图表组件需显式设置高度!");  
    	}  
    	me.on("resize", function () {
    		me.echarts = echarts.init(me.getEl().dom);
    		me.echarts.setOption({
    	    title : {
            text: '检测员检测参数统计,总共检测了'+me.paratotals+'个检测参数',
            x:'center',
            y:'5%'
    	    },
    	    tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    toolbox: {
            feature: {
                saveAsImage: {}
            }
    	    },
    	    legend: {
            orient: 'vertical',
            left: '10%',
            top:'5%',
            data: me.titledata
    	    },
    	    series : [
            {
              name: '检测参数统计',
              type: 'pie',
              radius : '55%',
              center: ['50%', '60%'],
              data:me.testerdata,
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
    		});  
    	});  
      me.callParent();  
    }
});  