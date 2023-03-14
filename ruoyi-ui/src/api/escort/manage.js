import request from '@/utils/request'

// 查询陪诊师管理列表
export function listManage(query) {
  return request({
    url: '/escort/manage/list',
    method: 'get',
    params: query
  })
}

// 查询陪诊师管理详细
export function getManage(escortId) {
  return request({
    url: '/escort/manage/' + escortId,
    method: 'get'
  })
}

// 新增陪诊师管理
export function addManage(data) {
  return request({
    url: '/escort/manage',
    method: 'post',
    data: data
  })
}

// 修改陪诊师管理
export function updateManage(data) {
  return request({
    url: '/escort/manage',
    method: 'put',
    data: data
  })
}

// 删除陪诊师管理
export function delManage(escortId) {
  return request({
    url: '/escort/manage/' + escortId,
    method: 'delete'
  })
}
