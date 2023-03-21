import Vue from 'vue'

/**
 * 金额/数字格式化，用逗号隔开，可指定小数位
 * 例如：{{ paymentCount |money(0) }}
 */
Vue.filter('money', function (value, decimal = 2) {
  if (!value) return '0.00'
  let result = parseFloat(value).toFixed(decimal)
  return result.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
})

