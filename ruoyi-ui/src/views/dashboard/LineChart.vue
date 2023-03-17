<template>
  <div id="main" :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import request from "@/utils/request";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      // 版本号
      version: "3.8.2",
    };
  },
  mounted() {
    //example 折线图
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    var option;
    option = {
      xAxis: {
        data: [],
        type: 'category',
        label: {
          show: true, //开启显示数值
          position: 'top', //数值在上方显示
          textStyle: {  //数值样式
            color: '#D1DBFF',   //字体颜色
            fontSize: 14  //字体大小
          }
        },
        axisLabel: {
          interval: 0, //隔几项显示一个标签
          rotate: "15" //标签倾斜的角度，旋转的角度是-90到90度
        },
        boundaryGap: false,
        axisTick: {
          show: false
        }
      },
      grid: {
        left: 10,
        right: 10,
        bottom: 20,
        top: 30,
        containLabel: true
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        },
        padding: [5, 10]
      },
      yAxis: {
        axisTick: {
          show: false
        }
      },
      legend: {
        data: ['营业额']
      },
      series: [{
        name: '营业额',
        itemStyle: {
          normal: {
            color: '#FF005A',
            lineStyle: {
              color: '#FF005A',
              width: 2
            }
          }
        },
        label: {
          show: false, //开启显示数值
          position: 'left', //数值在上方显示
          textStyle: {  //数值样式
            color: '#f13163',   //字体颜色
            fontSize: 14  //字体大小
          }
        },
        smooth: true,
        type: 'line',
        data: [],
        animationDuration: 2800,
        animationEasing: 'cubicInOut'
      }]
    };

    request.get("/escort/payment/paySumAmount").then(res =>{
      option.xAxis.data=res.data.paymentTimeStr
      option.series[0].data=res.data.paymentAmount
      myChart.setOption(option);
    })
  }
}
</script>
