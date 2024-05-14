Ext.define("Ext.ux.COChartsComponent", {  
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
      

      function randomValue() {
          return Math.round(Math.random()*1000);
      }
      
      me.on("resize", function () {
        me.echarts = echarts.init(me.getEl().dom);
        me.echarts.setOption({
        backgroundColor: '#404a59',
          animation: true,
          animationDuration: 1000,
          animationEasing: 'cubicInOut',
          animationDurationUpdate: 1000,
          animationEasingUpdate: 'cubicInOut',
          tooltip: {},
          visualMap: {
            min: 0,
            max: me.maxcount,
            text:['High','Low'],
            realtime: true,
            calculable: true,
            seriesIndex: [1],
            inRange: {
              color: ['lightskyblue','yellow', 'orangered']
            }
          },
          grid: {
              right: 40,
              top: 100,
              bottom: 40,
              width: '30%'
            },
            xAxis: {
                type: 'value',
                min: 0,
                scale: true,
                position: 'top',
                boundaryGap: false,
                splitLine: {show: false},
                axisLine: {show: false},
                axisTick: {show: false},
                axisLabel: {margin: 2, textStyle: {color: '#aaa'}}
            },
            yAxis: {
                type: 'category',
                name: me.nowyear+'年客户委托次数前30名',
                size:20,
                nameGap: 16,
                axisLine: {show: false, lineStyle: {color: '#ddd'}},
                axisTick: {show: false, lineStyle: {color: '#ddd'}},
                axisLabel: {interval: 0, textStyle: {color: '#ddd'}},
                data: me.provincenames
            },
            series : [{
               type: 'scatter',
               coordinateSystem: 'geo',
               data: [],
               symbolSize: 20,
               symbol: 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z',
               symbolRotate: 35,
               label: {
                   normal: {
                       formatter: '{b}',
                       position: 'right',
                       show: false
                   },
                   emphasis: {
                       show: true
                   }
               },
               itemStyle: {
                   normal: {
                        color: '#F06C00'
                   }
               }
            },
            {
              name: '客户委托次数',
              type: 'map',
              map: 'china',
              roam: true,
              left: '10',
              right: '35%',
              center: [109.000062,34.422205],
              zoom: 0.9,
              geoIndex: 0,
              label: {
                normal: {
                   show: true,
                   textStyle: {
                   color: 'rgba(0,0,0,5)'
                   }
                 }
               },
                // tooltip: {show: false},
                data:me.serverdata
              },
              {
                id: 'bar',
                zlevel: 2,
                type: 'bar',
                symbol: 'none',
                itemStyle: {
                  normal: {
                    color: '#ddb926'
                  }
                },
                data:me.procounts
             }
          ]
        });
      }); 
      me.callParent();
    }
});  