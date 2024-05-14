Ext.define("Ext.ux.SSChartsComponent", {  
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
            text: me.nowyear+'年受检企业送样类型统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
            data:['质检抽样','委托抽样','农药抽样','环境抽样','无公害抽样','国家例行监测抽样','地理标识抽样','监督抽样','江苏省例行监测抽样','委托检验'],
            top: 30
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
                name : '送样类型次数'
            }
        ],
        series : [
            {
                name:'质检抽样',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.sendyearone
            },
            {
                name:'委托抽样',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.sendyeartwo
            },
            {
                name:'农药抽样',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.sendyearthree
            },
            {
                name:'环境抽样',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:me.sendyearfour
            },
            {
                name:'无公害抽样',
                type:'line',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                areaStyle: {normal: {}},
                data:me.sendyearfive
            },
            
            {
              name:'国家例行监测抽样',
              type:'line',
              stack: '总量',
              areaStyle: {normal: {}},
              data:me.sendyearsix
          },
          {
              name:'地理标识抽样',
              type:'line',
              stack: '总量',
              areaStyle: {normal: {}},
              data:me.sendyearseven
          },
          {
              name:'监督抽样',
              type:'line',
              stack: '总量',
              areaStyle: {normal: {}},
              data:me.sendyeareight
          },
          {
              name:'江苏省例行监测抽样',
              type:'line',
              stack: '总量',
              areaStyle: {normal: {}},
              data:me.sendyearnine
          },
          {
              name:'委托检验',
              type:'line',
              stack: '总量',
              label: {
                  normal: {
                      show: true,
                      position: 'top'
                  }
              },
              areaStyle: {normal: {}},
              data:me.sendyearten
          }
        ]
    		  });  
    	  });  
        me.callParent();  
      }
});  