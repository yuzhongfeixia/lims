Ext.define("Ext.ux.CCChartsComponent", {  
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
            text: me.nowyear+'年受检企业送样次数排名'
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
            name:'送样次数'
        },
        yAxis: {
            type: 'category',
            data: me.testednames,
            name:'送检企业名称'
        },
        series: [
            {
              name: '送样次数',
              type: 'bar',
              data: me.counts
            }
        ]
    		});
    	}); 
    	me.callParent();
    }
    
});  