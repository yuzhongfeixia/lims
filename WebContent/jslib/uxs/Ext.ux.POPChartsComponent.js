Ext.define("Ext.ux.POPChartsComponent", {  
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
            text: me.nowyear+'年耗材试剂出入库数量统计'
        },
        tooltip : {
            trigger: 'axis'
        },
        grid: {
          left: '12%',
          right: '12%',
          bottom: '12%',
          containLabel: true
        },
        backgroundColor: '#eee',
        legend: {
            data:['入库量','出库量']
        },
        toolbox: {
            feature: {
                magicType: {
                    type: ['stack', 'tiled']
                },
                saveAsImage : {show: true},
                dataView: {}
            }
        },
        tooltip: {
//          trigger: 'axis'
        },
        xAxis : [
                {
                  type : 'category',
                  name: '月份',
                  data : me.monthinfo
                }
            ],
        yAxis: [
                {
                  type : 'value',
                  name: '耗材试剂数量'
              }
          ],
        grid: {
            left: 100
        },
        
        series: [
            {
              name: '入库量',
              type: 'bar',
              stack: 'one',
              data:me.prdinsyear
            },
            {
              name: '出库量',
              type: 'bar',
              stack: 'one',
              data:me.prdoutsyear
            }
        ]
    		  });  
    	  });  
        me.callParent();  
      }
});  