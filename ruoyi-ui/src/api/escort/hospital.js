import request from '@/utils/request'

// 查询医院管理列表
export function listHospital(query) {
  return request({
    url: '/escort/hospital/list',
    method: 'get',
    params: query
  })
}

// 查询医院管理详细
export function getHospital(hospitalId) {
  return request({
    url: '/escort/hospital/' + hospitalId,
    method: 'get'
  })
}

// 新增医院管理
export function addHospital(data) {
  return request({
    url: '/escort/hospital',
    method: 'post',
    data: data
  })
}

// 修改医院管理
export function updateHospital(data) {
  return request({
    url: '/escort/hospital',
    method: 'put',
    data: data
  })
}

// 删除医院管理
export function delHospital(hospitalId) {
  return request({
    url: '/escort/hospital/' + hospitalId,
    method: 'delete'
  })
}

// 医院状态修改
export function changeHospitalStatus(hospitalId, hospitalStatus) {
  const data = {
    hospitalId,
    hospitalStatus
  }
  return request({
    url: '/escort/hospital/changeStatus',
    method: 'put',
    data: data
  })
}
