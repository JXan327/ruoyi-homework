// 全局工具类
import { getDicts } from "@/api/system/dict/data"
import { getConfigKey } from "@/api/system/config"
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi"
import { download } from '@/utils/request'

export const globalUtils = {
  // 字典相关
  getDicts: (dictType) => {
    return getDicts(dictType)
  },
  
  // 配置相关
  getConfigKey: (configKey) => {
    return getConfigKey(configKey)
  },
  
  // 时间处理
  parseTime: (time, pattern) => {
    return parseTime(time, pattern)
  },
  
  // 表单处理
  resetForm: (formName) => {
    return resetForm(formName)
  },
  
  // 日期范围
  addDateRange: (params, dateRange, propName) => {
    return addDateRange(params, dateRange, propName)
  },
  
  // 字典标签
  selectDictLabel: (dict, value) => {
    return selectDictLabel(dict, value)
  },
  
  // 字典标签数组
  selectDictLabels: (dict, values, separator) => {
    return selectDictLabels(dict, values, separator)
  },
  
  // 下载
  download: (url, params, filename, config) => {
    return download(url, params, filename, config)
  },
  
  // 树结构处理
  handleTree: (data, id, parentId, children) => {
    return handleTree(data, id, parentId, children)
  }
}
