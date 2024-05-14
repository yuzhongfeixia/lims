Ext.define("Ext.ux.SOChartsComponent", {  
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
            text: me.nowyear+'年检测员检测参数统计排名(已生成报告)'
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
        legend: {
            data: [me.nowyear+'年'],
            top:23
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
            name:'检测参数数量'
        },
        yAxis: {
            type: 'category',
            data: me.testusernames,
            name:'检测员姓名'
        },
        series: [
            {
                name: me.nowyear+'年',
                type: 'bar',
                data: me.paramcounts
            }
        ]
    		});
    	}); 
    	me.callParent();
    }
    
});  