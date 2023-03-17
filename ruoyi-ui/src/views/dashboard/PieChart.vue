<template>
  <div id="pieCharId" :class="className" :style="{height:height,width:width}"/>
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
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    var chartDom = document.getElementById('pieCharId');
    var myChart = echarts.init(chartDom);
    var option;
    option = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        left: 'center',
        bottom: '10'
      },
      series: [
        {
          name: '当月完成订单类型',
          type: 'pie',
          roseType: 'radius',
          radius: [15, 95],
          center: ['50%', '38%'],
          animationEasing: 'cubicInOut',
          animationDuration: 2600
        }
      ]
    }
    request.get("/escort/order/projectTypeInfo").then(res => {
      option.legend.data = res.data.projectName
      option.series[0].data = res.data.projectTypeInfo
      myChart.setOption(option);
    })

  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  }
}
</script>
