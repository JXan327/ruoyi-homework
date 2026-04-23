// 错误处理工具
import { Message, MessageBox } from 'element-ui'

class ErrorHandler {
  constructor() {
    this.init()
  }

  init() {
    // 全局错误捕获
    window.addEventListener('error', this.handleGlobalError.bind(this))
    window.addEventListener('unhandledrejection', this.handleUnhandledRejection.bind(this))
  }

  // 处理全局错误
  handleGlobalError(event) {
    const error = event.error
    if (error) {
      this.reportError('Global Error', error)
    }
  }

  // 处理未处理的Promise拒绝
  handleUnhandledRejection(event) {
    this.reportError('Unhandled Rejection', event.reason)
  }

  // 报告错误
  reportError(source, error) {
    console.error(`[${source}]`, error)
    
    // 可以在这里添加错误上报逻辑，例如发送到服务器
    // this.sendErrorToServer(source, error)
  }

  // 发送错误到服务器
  sendErrorToServer(source, error) {
    try {
      const errorData = {
        source,
        message: error.message,
        stack: error.stack,
        url: window.location.href,
        userAgent: navigator.userAgent,
        timestamp: new Date().toISOString()
      }

      // 这里可以使用axios或fetch发送错误数据
      // 例如：
      // import axios from 'axios'
      // axios.post('/api/error/report', errorData)
      
      console.log('Error reported to server:', errorData)
    } catch (reportError) {
      console.error('Failed to report error:', reportError)
    }
  }

  // 处理API错误
  handleApiError(error) {
    if (!error) return

    let message = '网络请求失败'
    
    if (error.message) {
      if (error.message === 'Network Error') {
        message = '后端接口连接异常'
      } else if (error.message.includes('timeout')) {
        message = '系统接口请求超时'
      } else if (error.message.includes('Request failed with status code')) {
        message = '系统接口' + error.message.slice(-3) + '异常'
      } else {
        message = error.message
      }
    }

    Message({ 
      message: message, 
      type: 'error', 
      duration: 5 * 1000 
    })

    this.reportError('API Error', error)
  }

  // 处理表单验证错误
  handleValidationError(errors) {
    if (!errors || !errors.length) return

    const errorMessages = errors.map(error => error.message).join('\n')
    Message({ 
      message: errorMessages, 
      type: 'warning', 
      duration: 3 * 1000 
    })
  }

  // 处理权限错误
  handlePermissionError() {
    Message({ 
      message: '没有操作权限', 
      type: 'warning', 
      duration: 3 * 1000 
    })
  }

  // 处理登录过期
  handleLoginExpired() {
    MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      // 跳转到登录页面
      location.href = '/login'
    })
  }

  // 处理未知错误
  handleUnknownError(error) {
    console.error('Unknown error:', error)
    Message({ 
      message: '系统出现未知错误，请稍后重试', 
      type: 'error', 
      duration: 5 * 1000 
    })
    this.reportError('Unknown Error', error)
  }
}

// 导出单例实例
export const errorHandler = new ErrorHandler()
export default errorHandler
