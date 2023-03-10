import request from '@/utils/request'

// 查询订单列表列表
export function listOrder(query) {
  return request({
    url: '/escort/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单列表详细
export function getOrder(orderId) {
  return request({
    url: '/escort/order/' + orderId,
    method: 'get'
  })
}

// 新增订单列表
export function addOrder(data) {
  return request({
    url: '/escort/order',
    method: 'post',
    data: data
  })
}

// 修改订单列表
export function updateOrder(data) {
  return request({
    url: '/escort/order',
    method: 'put',
    data: data
  })
}

// 删除订单列表
export function delOrder(orderId) {
  return request({
    url: '/escort/order/' + orderId,
    method: 'delete'
  })
}
