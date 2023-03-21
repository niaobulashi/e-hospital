import request from '@/utils/request'

// 查询支付单流水列表
export function listPayment(query) {
  return request({
    // url: '/escort/payment/list',
    url: '/escort/payment/memlist',
    method: 'get',
    params: query
  })
}

// 查询支付单流水详细
export function getPayment(paymentId) {
  return request({
    url: '/escort/payment/' + paymentId,
    method: 'get'
  })
}

// 新增支付单流水
export function addPayment(data) {
  return request({
    url: '/escort/payment',
    method: 'post',
    data: data
  })
}

// 修改支付单流水
export function updatePayment(data) {
  return request({
    url: '/escort/payment',
    method: 'put',
    data: data
  })
}

// 删除支付单流水
export function delPayment(paymentId) {
  return request({
    url: '/escort/payment/' + paymentId,
    method: 'delete'
  })
}

// 查询指定日期的支付流水
export function queryPaymentByDate(query) {
  return request({
    url: '/escort/payment/queryPaymentByDate',
    method: 'get',
    params: query
  })
}

// 查询支付单流水列表
export function paySumAmount() {
  return request({
    url: '/escort/payment/paySumAmount',
    method: 'get'
  })
}

// 支付流水列表查询，统计订单数量、支付金额总数
export function queryEscortPaymentSumAmount(query) {
  return request({
    url: '/escort/payment/queryEscortPaymentSumAmount',
    method: 'get',
    params: query
  })
}
