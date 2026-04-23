import request from '@/utils/request'

export function listTask(query) {
  return request({
    url: '/system/task/list',
    method: 'get',
    params: query
  })
}

export function getTask(taskId) {
  return request({
    url: '/system/task/' + taskId,
    method: 'get'
  })
}

export function addTask(data) {
  return request({
    url: '/system/task',
    method: 'post',
    data
  })
}

export function updateTask(data) {
  return request({
    url: '/system/task',
    method: 'put',
    data
  })
}

export function delTask(taskId) {
  return request({
    url: '/system/task/' + taskId,
    method: 'delete'
  })
}

export function startTask(taskId) {
  return request({
    url: '/system/simulation/start/' + taskId,
    method: 'post'
  })
}

export function stopTask(taskId) {
  return request({
    url: '/system/simulation/stop/' + taskId,
    method: 'post'
  })
}

export function getTaskStatus(taskId) {
  return request({
    url: '/system/simulation/status/' + taskId,
    method: 'get'
  })
}
