<template>
  <div class="app-container">
    <el-form
      ref="queryForm"
      :model="queryParams"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="80px"
    >
      <el-form-item label="任务编号" prop="taskCode">
        <el-input v-model="queryParams.taskCode" placeholder="请输入任务编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="queryParams.taskName" placeholder="请输入任务名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任务类型" prop="taskType">
        <el-select v-model="queryParams.taskType" placeholder="请选择任务类型" clearable>
          <el-option v-for="item in taskTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="场景" prop="sceneId">
        <el-select v-model="queryParams.sceneId" placeholder="请选择场景" clearable filterable>
          <el-option v-for="item in sceneOptions" :key="item.sceneId" :label="item.sceneName" :value="item.sceneId" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:task:add']" type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:task:edit']" type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:task:remove']" type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:task:export']" type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:start']" type="info" plain icon="el-icon-video-play" size="mini" :disabled="single" @click="handleStart">启动</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:stop']" type="info" plain icon="el-icon-video-pause" size="mini" :disabled="single" @click="handleStop">停止</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务ID" align="center" prop="taskId" width="80" />
      <el-table-column label="任务编号" align="center" prop="taskCode" />
      <el-table-column label="任务名称" align="center" prop="taskName" min-width="160" show-overflow-tooltip />
      <el-table-column label="任务类型" align="center" prop="taskType">
        <template slot-scope="{ row }">
          <span>{{ formatTaskType(row.taskType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联场景" align="center">
        <template slot-scope="{ row }">
          <span>{{ formatSceneName(row.sceneId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行船只" align="center">
        <template slot-scope="{ row }">
          <span>{{ formatShipName(row.shipId || row.boatId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务状态" align="center" prop="status">
        <template slot-scope="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ formatStatus(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="进度" align="center" prop="progress" width="160">
        <template slot-scope="{ row }">
          <el-progress :percentage="row.progress || 0" :stroke-width="10" />
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
      <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
      <el-table-column label="任务结果" align="center" prop="taskResult" min-width="160" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template slot-scope="{ row }">
          <el-button v-hasPermi="['system:task:edit']" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(row)">修改</el-button>
          <el-button v-hasPermi="['system:task:remove']" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(row)">删除</el-button>
          <el-button
            v-hasPermi="['system:simulation:start']"
            size="mini"
            type="text"
            icon="el-icon-video-play"
            @click="handleStart(row)"
            v-if="row.status === '0' || row.status === '3' || row.status === '4'"
          >启动</el-button>
          <el-button
            v-hasPermi="['system:simulation:stop']"
            size="mini"
            type="text"
            icon="el-icon-video-pause"
            @click="handleStop(row)"
            v-if="row.status === '1'"
          >停止</el-button>
          <el-button
            v-hasPermi="['system:simulation:status']"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleStatus(row)"
          >状态</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="任务编号" prop="taskCode">
              <el-input v-model="form.taskCode" placeholder="请输入任务编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务名称" prop="taskName">
              <el-input v-model="form.taskName" placeholder="请输入任务名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务类型" prop="taskType">
              <el-select v-model="form.taskType" placeholder="请选择任务类型" style="width: 100%">
                <el-option v-for="item in taskTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择任务状态" style="width: 100%">
                <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联场景" prop="sceneId">
              <el-select v-model="form.sceneId" placeholder="请选择场景" filterable style="width: 100%">
                <el-option v-for="item in sceneOptions" :key="item.sceneId" :label="item.sceneName" :value="item.sceneId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执行船只" prop="shipId">
              <el-select v-model="form.shipId" placeholder="请选择船只" filterable style="width: 100%">
                <el-option v-for="item in shipOptions" :key="item.shipId" :label="item.shipName" :value="item.shipId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择开始时间" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择结束时间" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="任务描述" prop="taskDesc">
              <el-input v-model="form.taskDesc" type="textarea" :rows="3" placeholder="请输入任务描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTask, getTask, delTask, addTask, updateTask, startTask, stopTask, getTaskStatus } from "@/api/system/task"
import { listSimScene } from "@/api/system/simScene"
import { listShip } from "@/api/system/ship"

export default {
  name: "Task",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      taskList: [],
      sceneOptions: [],
      shipOptions: [],
      title: "",
      open: false,
      taskTypeOptions: [
        { label: "巡逻任务", value: "1" },
        { label: "救援任务", value: "2" },
        { label: "科研任务", value: "3" },
        { label: "其他任务", value: "4" }
      ],
      statusOptions: [
        { label: "待执行", value: "0" },
        { label: "执行中", value: "1" },
        { label: "已完成", value: "2" },
        { label: "失败", value: "3" },
        { label: "暂停", value: "4" }
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskCode: null,
        taskName: null,
        taskType: null,
        sceneId: null,
        status: null
      },
      form: {},
      rules: {
        taskCode: [{ required: true, message: "任务编号不能为空", trigger: "blur" }],
        taskName: [{ required: true, message: "任务名称不能为空", trigger: "blur" }],
        taskType: [{ required: true, message: "请选择任务类型", trigger: "change" }],
        sceneId: [{ required: true, message: "请选择场景", trigger: "change" }],
        shipId: [{ required: true, message: "请选择船只", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
    this.loadOptions()
  },
  methods: {
    getList() {
      this.loading = true
      listTask(this.queryParams).then(response => {
        this.taskList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    loadOptions() {
      listSimScene({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.sceneOptions = response.rows || []
      })
      listShip({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.shipOptions = response.rows || []
      })
    },
    formatTaskType(value) {
      const matched = this.taskTypeOptions.find(item => item.value === value)
      return matched ? matched.label : value || "-"
    },
    formatStatus(value) {
      const matched = this.statusOptions.find(item => item.value === value)
      return matched ? matched.label : value || "-"
    },
    statusTagType(value) {
      if (value === "1") return "warning"
      if (value === "2") return "success"
      if (value === "3") return "danger"
      if (value === "4") return "info"
      return ""
    },
    formatSceneName(sceneId) {
      const matched = this.sceneOptions.find(item => item.sceneId === sceneId)
      return matched ? matched.sceneName : sceneId || "-"
    },
    formatShipName(shipId) {
      const matched = this.shipOptions.find(item => item.shipId === shipId)
      return matched ? matched.shipName : shipId || "-"
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        taskId: null,
        taskCode: null,
        taskName: null,
        taskType: "1",
        sceneId: null,
        shipId: null,
        startTime: null,
        endTime: null,
        taskDesc: null,
        status: "0",
        progress: 0,
        taskResult: null,
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增任务"
    },
    handleUpdate(row) {
      this.reset()
      const taskId = row.taskId || this.ids[0]
      getTask(taskId).then(response => {
        const data = response.data || {}
        data.shipId = data.shipId || data.boatId || null
        this.form = Object.assign({}, this.form, data)
        this.open = true
        this.title = "修改任务"
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const submitData = Object.assign({}, this.form, { boatId: this.form.shipId })
        const request = submitData.taskId ? updateTask(submitData) : addTask(submitData)
        request.then(() => {
          this.$modal.msgSuccess(submitData.taskId ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const taskIds = row.taskId || this.ids
      this.$modal.confirm('是否确认删除任务编号为 "' + taskIds + '" 的数据项？').then(() => {
        return delTask(taskIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    resolveTaskId(row) {
      return row && row.taskId ? row.taskId : this.ids[0]
    },
    handleStart(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      startTask(taskId).then(() => {
        this.$modal.msgSuccess("启动成功")
        this.getList()
      })
    },
    handleStop(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      stopTask(taskId).then(() => {
        this.$modal.msgSuccess("停止成功")
        this.getList()
      })
    },
    handleStatus(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      getTaskStatus(taskId).then(response => {
        this.$modal.msgInfo("当前状态：" + (response.data || "未知"))
      })
    },
    handleExport() {
      this.download("system/task/export", { ...this.queryParams }, `task_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
