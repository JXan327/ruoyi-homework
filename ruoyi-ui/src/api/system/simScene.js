import request from '@/utils/request'

// 查询仿真场景信息列表
export function listSimScene(query) {
  return request({
    url: '/system/scene/list',
    method: 'get',
    params: query
  })
}

// 查询仿真场景信息详细
export function getSimScene(sceneId) {
  return request({
    url: '/system/scene/' + sceneId,
    method: 'get'
  })
}

// 新增仿真场景信息
export function addSimScene(data) {
  return request({
    url: '/system/scene',
    method: 'post',
    data: data
  })
}

// 修改仿真场景信息
export function updateSimScene(data) {
  return request({
    url: '/system/scene',
    method: 'put',
    data: data
  })
}

// 删除仿真场景信息
export function delSimScene(sceneId) {
  return request({
    url: '/system/scene/' + sceneId,
    method: 'delete'
  })
}
