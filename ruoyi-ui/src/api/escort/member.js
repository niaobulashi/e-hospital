import request from '@/utils/request'

// 查询会员信息列表
export function listMember(query) {
  return request({
    url: '/escort/member/list',
    method: 'get',
    params: query
  })
}

// 查询会员信息详细
export function getMember(memberId) {
  return request({
    url: '/escort/member/' + memberId,
    method: 'get'
  })
}

// 新增会员信息
export function addMember(data) {
  return request({
    url: '/escort/member',
    method: 'post',
    data: data
  })
}

// 修改会员信息
export function updateMember(data) {
  return request({
    url: '/escort/member',
    method: 'put',
    data: data
  })
}

// 删除会员信息
export function delMember(memberId) {
  return request({
    url: '/escort/member/' + memberId,
    method: 'delete'
  })
}
