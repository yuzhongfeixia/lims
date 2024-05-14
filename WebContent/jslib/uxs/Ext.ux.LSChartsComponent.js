Ext.define("Ext.ux.LSChartsComponent", {  
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
            text: me.nowyear+'年检测室检测样品统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
            data:['检测一室','检测二室','检测三室','检测四室','检测五室'],
            top:25 
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
            top: '12%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : me.monthinfo,
                name:'月份'
            }
        ],
        yAxis : [
            {
                type : 'value',
                name : '样品数量'
            }
        ],
        series : [
            {
                name:'检测一室',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.labyearone
            },
            {
                name:'检测二室',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.labyeartwo
            },
            {
                name:'检测三室',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.labyearthree
            },
            {
                name:'检测四室',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.labyearfour
            },
            {
                name:'检测五室',
                type:'line',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                areaStyle: {normal: {}},
                data:me.labyearfive
            }
        ]
    		  });  
    	  });  
        me.callParent();  
      }
});  