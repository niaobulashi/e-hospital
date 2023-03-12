import request from '@/utils/requestMock'
const api = {
  //  楼宇下拉列表
  getProvince: '/address/getProvince',
  getCity: '/address/getCity',
  getArea: '/address/getArea'
}
export default api

export function getProvince() {
  return request({
    url: api.getProvince,
    method: 'get'
  })
}
export function getCity(params) {
  return request({
    url: api.getCity,
    method: 'get',
    params: params
  })
}
export function getArea(params) {
  return request({
    url: api.getArea,
    method: 'get',
    params: params
  })
}
