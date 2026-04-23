<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="任务编号" prop="taskCode">
        <el-input v-model="queryParams.taskCode" placeholder="请输入任务编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="queryParams.taskName" placeholder="请输入任务名称" clearable @keyup.enter.native="handleQuery" />
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
        <el-button v-hasPermi="['system:simulation:start']" type="primary" plain icon="el-icon-video-play" size="mini" :disabled="single" @click="handleStart">启动</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:pause']" type="warning" plain icon="el-icon-video-pause" size="mini" :disabled="single" @click="handlePause">暂停</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:stop']" type="danger" plain icon="el-icon-video-stop" size="mini" :disabled="single" @click="handleStop">停止</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:status']" type="info" plain icon="el-icon-view" size="mini" :disabled="single" @click="handleStatus">状态</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:simulation:analyze']" type="success" plain icon="el-icon-data-analysis" size="mini" :disabled="single" @click="handleAnalyze">分析</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="simulationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务ID" align="center" prop="taskId" width="80" />
      <el-table-column label="任务编号" align="center" prop="taskCode" />
      <el-table-column label="任务名称" align="center" prop="taskName" min-width="160" show-overflow-tooltip />
      <el-table-column label="场景" align="center">
        <template slot-scope="{ row }">
          <span>{{ formatSceneName(row.sceneId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="船只" align="center">
        <template slot-scope="{ row }">
          <span>{{ formatShipName(row.shipId || row.boatId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
      <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ formatStatus(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="进度" align="center" width="160">
        <template slot-scope="{ row }">
          <el-progress :percentage="row.progress || 0" :stroke-width="10" />
        </template>
      </el-table-column>
      <el-table-column label="仿真结果" align="center" prop="taskResult" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template slot-scope="{ row }">
          <el-button v-hasPermi="['system:simulation:start']" size="mini" type="text" icon="el-icon-video-play" @click="handleStart(row)">启动</el-button>
          <el-button v-hasPermi="['system:simulation:pause']" size="mini" type="text" icon="el-icon-video-pause" @click="handlePause(row)">暂停</el-button>
          <el-button v-hasPermi="['system:simulation:stop']" size="mini" type="text" icon="el-icon-video-stop" @click="handleStop(row)">停止</el-button>
          <el-button v-hasPermi="['system:simulation:status']" size="mini" type="text" icon="el-icon-view" @click="handleStatus(row)">状态</el-button>
          <el-button v-hasPermi="['system:simulation:analyze']" size="mini" type="text" icon="el-icon-data-analysis" @click="handleAnalyze(row)">分析</el-button>
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
  </div>
</template>

<script>
import { listTask } from "@/api/system/task"
import { listSimScene } from "@/api/system/simScene"
import { listShip } from "@/api/system/ship"
import { startSimulation, pauseSimulation, stopSimulation, getSimulationStatus, analyzeSimulation } from "@/api/system/simulation"

export default {
  name: "Simulation",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      showSearch: true,
      total: 0,
      simulationList: [],
      sceneOptions: [],
      shipOptions: [],
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
        status: null
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
        this.simulationList = response.rows || []
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
    },
    resolveTaskId(row) {
      return row && row.taskId ? row.taskId : this.ids[0]
    },
    formatSceneName(sceneId) {
      const matched = this.sceneOptions.find(item => item.sceneId === sceneId)
      return matched ? matched.sceneName : sceneId || "-"
    },
    formatShipName(shipId) {
      const matched = this.shipOptions.find(item => item.shipId === shipId)
      return matched ? matched.shipName : shipId || "-"
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
    handleStart(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      startSimulation(taskId).then(() => {
        this.$modal.msgSuccess("启动成功")
        this.getList()
      })
    },
    handlePause(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      pauseSimulation(taskId).then(() => {
        this.$modal.msgSuccess("暂停成功")
        this.getList()
      })
    },
    handleStop(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      stopSimulation(taskId).then(() => {
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
      getSimulationStatus(taskId).then(response => {
        this.$modal.msgInfo("当前状态：" + (response.data || "未知"))
      })
    },
    handleAnalyze(row) {
      const taskId = this.resolveTaskId(row)
      if (!taskId) {
        this.$modal.msgError("请选择一条任务")
        return
      }
      analyzeSimulation(taskId).then(response => {
        this.$modal.msgSuccess(response.data || "分析完成")
        this.getList()
      })
    }
  }
}
</script>
