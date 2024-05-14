Ext.define("Ext.ux.DUCChartsComponent", {  
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
            text: me.nowyear+'年设备使用次数排名(从大到小)'
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
            name:'设备使用次数'
        },
        yAxis: {
            type: 'category',
            data: me.devnames,
            name:'设备名称'
        },
        series: [
            {
              name: '使用次数',
              type: 'bar',
              data: me.devusecount
            }
        ]
    		});
    	}); 
    	me.callParent();
    }
    
});  