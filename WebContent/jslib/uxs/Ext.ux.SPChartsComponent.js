Ext.define("Ext.ux.SPChartsComponent", {  
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
            text: '同一类产品合格和不合格统计(个)'
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
            data:['合格样品个数','不合格样品个数']
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
                  name: '产品种类',
                  data : me.kindnames
                }
            ],
        yAxis: [
                {
                  type : 'value',
                  name: '样品数量'
              }
          ],
        grid: {
            left: 100
        },
        
        series: [
            {
              name: '合格样品个数',
              type: 'bar',
              stack: 'one',
              data:me.sampass
            },
            {
              name: '不合格样品个数',
              type: 'bar',
              stack: 'one',
              data:me.samnopass
            }
        ]
    		  });  
    	  });  
        me.callParent();  
      }
});  