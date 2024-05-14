Ext.define("Ext.ux.PCChartsComponent", {  
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
    		  title: {
            text: '耗材库存剩余量统计排名(从小到大)'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        toolbox: {
          feature: {
              saveAsImage: {}
          }
        },
        grid: {
            left: '12%',
            right: '12%',
            bottom: '12%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            name:'耗材库存剩余量'
        },
        yAxis: {
            type: 'category',
            data: me.prdnames,
            name:'耗材试剂名称'
        },
        series: [
            {
              name: '剩余量',
              type: 'bar',
              data: me.lastnumbers
            }
        ]
    		});
    	}); 
    	me.callParent();
    }
    
});  