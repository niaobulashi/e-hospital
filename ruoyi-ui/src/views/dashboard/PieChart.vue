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
      default: '300px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    let chartDom = document.getElementById('pieCharId');
    echarts.init(chartDom).setOption({
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {d}%'
      },
      legend: {
        left: 'center',
        bottom: '10',
        data: ['4h陪诊', '8h陪诊', '代办问诊', '代办买药', '代办约号', '检查预约', '代取报告', 'VIP陪诊']
      },
      series: [
        {
          name: '当月完成订单类型',
          type: 'pie',
          roseType: false,
          radius: [15, 95],
          center: ['50%', '38%'],
          animationEasing: 'cubicInOut',
          animationDuration: 2600,
          data: [
            {value: 35.08, name: '4h陪诊'},
            {value: 20.17, name: '8h陪诊'},
            {value: 9.83, name: '代办问诊'},
            {value: 12.60, name: '代办买药'},
            {value: 6.32, name: '代办约号'},
            {value: 5.28, name: '检查预约'},
            {value: 4.07, name: '代取报告'},
            {value: 6.65, name: 'VIP陪诊'}
          ]
        }
      ]
    });

    // request.get("/escort/order/projectTypeInfo").then(res => {
    //   option.legend.data = res.data.projectName
    //   option.series[0].data = res.data.projectTypeInfo
    //   myChart.setOption(option);
    // })
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
