/**
 * 通用工具方法混入
 * 将 ruoyi.js 中的工具方法注入到所有组件中
 */
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree
} from '@/utils/ruoyi'

export default {
  methods: {
    // 日期格式化
    parseTime,
    // 表单重置
    resetForm,
    // 添加日期范围
    addDateRange,
    // 回显数据字典
    selectDictLabel,
    // 回显数据字典（字符串、数组）
    selectDictLabels,
    // 构造树型结构数据
    handleTree
  }
}
