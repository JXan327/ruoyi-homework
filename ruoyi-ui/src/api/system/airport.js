import request from '@/utils/request'

// 查询无人机场信息列表
export function listAirport(query) {
  return request({
    url: '/system/airport/list',
    method: 'get',
    params: query
  })
}

// 查询无人机场信息详细
export function getAirport(airportId) {
  return request({
    url: '/system/airport/' + airportId,
    method: 'get'
  })
}

// 新增无人机场信息
export function addAirport(data) {
  return request({
    url: '/system/airport',
    method: 'post',
    data: data
  })
}

// 修改无人机场信息
export function updateAirport(data) {
  return request({
    url: '/system/airport',
    method: 'put',
    data: data
  })
}

// 删除无人机场信息
export function delAirport(airportId) {
  return request({
    url: '/system/airport/' + airportId,
    method: 'delete'
  })
}
