// 性能监控工具
class PerformanceMonitor {
  constructor() {
    this.metrics = {
      navigationStart: performance.timing.navigationStart,
      resources: [],
      apiRequests: []
    }
    this.init()
  }

  init() {
    // 监听资源加载完成
    if (typeof window.performance !== 'undefined' && window.performance.getEntriesByType) {
      window.addEventListener('load', () => {
        this.collectResourceMetrics()
        this.reportMetrics()
      })
    }

    // 监听API请求
    this.monitorApiRequests()
  }

  // 收集资源加载指标
  collectResourceMetrics() {
    const resources = performance.getEntriesByType('resource')
    this.metrics.resources = resources.map(resource => ({
      name: resource.name,
      duration: resource.duration.toFixed(2),
      startTime: resource.startTime.toFixed(2),
      responseEnd: resource.responseEnd.toFixed(2)
    }))
  }

  // 监控API请求
  monitorApiRequests() {
    const originalFetch = window.fetch
    window.fetch = async (...args) => {
      const start = Date.now()
      try {
        const response = await originalFetch(...args)
        const end = Date.now()
        this.metrics.apiRequests.push({
          url: args[0],
          method: args[1]?.method || 'GET',
          duration: end - start,
          status: response.status
        })
        return response
      } catch (error) {
        const end = Date.now()
        this.metrics.apiRequests.push({
          url: args[0],
          method: args[1]?.method || 'GET',
          duration: end - start,
          error: error.message
        })
        throw error
      }
    }
  }

  // 记录页面性能指标
  getPagePerformance() {
    if (!window.performance || !window.performance.timing) {
      return null
    }

    const timing = window.performance.timing
    const loadTime = timing.loadEventEnd && timing.loadEventEnd > timing.navigationStart 
      ? (timing.loadEventEnd - timing.navigationStart).toFixed(2) 
      : 'N/A'
    const domContentLoadedTime = timing.domContentLoadedEventEnd && timing.domContentLoadedEventEnd > timing.navigationStart 
      ? (timing.domContentLoadedEventEnd - timing.navigationStart).toFixed(2) 
      : 'N/A'

    return {
      navigationStart: timing.navigationStart,
      loadEventEnd: timing.loadEventEnd,
      domContentLoadedEventEnd: timing.domContentLoadedEventEnd,
      firstPaint: performance.getEntriesByType('paint')?.find(entry => entry.name === 'first-paint')?.startTime || 0,
      firstContentfulPaint: performance.getEntriesByType('paint')?.find(entry => entry.name === 'first-contentful-paint')?.startTime || 0,
      loadTime: loadTime,
      domContentLoadedTime: domContentLoadedTime
    }
  }

  // 报告性能指标
  reportMetrics() {
    const pagePerformance = this.getPagePerformance()
    if (pagePerformance) {
      console.log('=== 页面性能指标 ===')
      console.log('加载时间:', pagePerformance.loadTime, 'ms')
      console.log('DOM加载完成时间:', pagePerformance.domContentLoadedTime, 'ms')
      console.log('首次绘制时间:', pagePerformance.firstPaint, 'ms')
      console.log('首次内容绘制时间:', pagePerformance.firstContentfulPaint, 'ms')
    }

    if (this.metrics.resources.length > 0) {
      console.log('=== 资源加载指标 ===')
      this.metrics.resources.forEach(resource => {
        if (resource.duration > 1000) {
          console.log(`⚠️  资源加载缓慢: ${resource.name} (${resource.duration}ms)`)
        }
      })
    }

    if (this.metrics.apiRequests.length > 0) {
      console.log('=== API请求指标 ===')
      this.metrics.apiRequests.forEach(request => {
        if (request.duration > 2000) {
          console.log(`⚠️  API请求缓慢: ${request.url} (${request.duration}ms)`)
        }
      })
    }
  }

  // 手动记录性能指标
  recordMetric(name, value) {
    if (!this.metrics.custom) {
      this.metrics.custom = {}
    }
    this.metrics.custom[name] = value
  }

  // 获取所有性能指标
  getAllMetrics() {
    return {
      ...this.metrics,
      page: this.getPagePerformance()
    }
  }
}

// 导出单例实例
export const performanceMonitor = new PerformanceMonitor()
export default performanceMonitor
