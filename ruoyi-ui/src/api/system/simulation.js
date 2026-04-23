import request from '@/utils/request'

// 启动仿真
export function startSimulation(taskId) {
  return request({
    url: '/system/simulation/start/' + taskId,
    method: 'post'
  })
}

// 暂停仿真
export function pauseSimulation(taskId) {
  return request({
    url: '/system/simulation/pause/' + taskId,
    method: 'post'
  })
}

// 停止仿真
export function stopSimulation(taskId) {
  return request({
    url: '/system/simulation/stop/' + taskId,
    method: 'post'
  })
}

// 获取仿真状态
export function getSimulationStatus(taskId) {
  return request({
    url: '/system/simulation/status/' + taskId,
    method: 'get'
  })
}

// 分析仿真结果
export function analyzeSimulation(taskId) {
  return request({
    url: '/system/simulation/analyze/' + taskId,
    method: 'get'
  })
}
