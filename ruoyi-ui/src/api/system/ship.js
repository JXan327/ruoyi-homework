import request from '@/utils/request'

// 查询无人船信息列表
export function listShip(query) {
  return request({
    url: '/system/boat/list',
    method: 'get',
    params: query
  })
}

// 查询无人船信息详细
export function getShip(shipId) {
  return request({
    url: '/system/boat/' + shipId,
    method: 'get'
  })
}

// 新增无人船信息
export function addShip(data) {
  return request({
    url: '/system/boat',
    method: 'post',
    data: data
  })
}

// 修改无人船信息
export function updateShip(data) {
  return request({
    url: '/system/boat',
    method: 'put',
    data: data
  })
}

// 删除无人船信息
export function delShip(shipId) {
  return request({
    url: '/system/boat/' + shipId,
    method: 'delete'
  })
}
