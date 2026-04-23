<template>
  <div class="app-container">
    <el-row :gutter="16" class="mb16">
      <el-col :span="6" v-for="card in summaryCards" :key="card.label">
        <el-card shadow="hover" class="summary-card">
          <div class="summary-card__value">{{ card.value }}</div>
          <div class="summary-card__label">{{ card.label }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header">任务状态统计</div>
          <div v-for="item in taskStatusRows" :key="item.label" class="stats-row">
            <span>{{ item.label }}</span>
            <span>{{ item.value }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header">无人船状态统计</div>
          <div v-for="item in shipStatusRows" :key="item.label" class="stats-row">
            <span>{{ item.label }}</span>
            <span>{{ item.value }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="mt16">
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header">场景使用分布</div>
          <div v-for="item in sceneTypeRows" :key="item.label" class="stats-row">
            <span>{{ item.label }}</span>
            <span>{{ item.value }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <div slot="header">执行建议</div>
          <div class="tips-box">
            <div>在线船只：{{ onlineShipCount }} / {{ summary.shipCount }}</div>
            <div>进行中任务：{{ runningTaskCount }}</div>
            <div>可用场景：{{ enabledSceneCount }}</div>
            <div>当前建议：{{ recommendation }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover" class="mt16">
      <div slot="header">最近任务</div>
      <el-table :data="recentTasks" v-loading="loading">
        <el-table-column label="任务编号" prop="taskCode" />
        <el-table-column label="任务名称" prop="taskName" min-width="160" show-overflow-tooltip />
        <el-table-column label="任务类型">
          <template slot-scope="{ row }">{{ formatTaskType(row.taskType) }}</template>
        </el-table-column>
        <el-table-column label="场景">
          <template slot-scope="{ row }">{{ formatSceneName(row.sceneId) }}</template>
        </el-table-column>
        <el-table-column label="船只">
          <template slot-scope="{ row }">{{ formatShipName(row.shipId || row.boatId) }}</template>
        </el-table-column>
        <el-table-column label="状态">
          <template slot-scope="{ row }">
            <el-tag :type="statusTagType(row.status)">{{ formatStatus(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="进度" width="160">
          <template slot-scope="{ row }">
            <el-progress :percentage="row.progress || 0" :stroke-width="10" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { listAirport } from "@/api/system/airport"
import { listShip } from "@/api/system/ship"
import { listSimScene } from "@/api/system/simScene"
import { listTask } from "@/api/system/task"

export default {
  name: "Analysis",
  data() {
    return {
      loading: false,
      airports: [],
      ships: [],
      scenes: [],
      tasks: [],
      summary: {
        airportCount: 0,
        shipCount: 0,
        sceneCount: 0,
        taskCount: 0
      }
    }
  },
  computed: {
    summaryCards() {
      return [
        { label: "机场数量", value: this.summary.airportCount },
        { label: "无人船数量", value: this.summary.shipCount },
        { label: "场景数量", value: this.summary.sceneCount },
        { label: "任务数量", value: this.summary.taskCount }
      ]
    },
    taskStatusRows() {
      return [
        { label: "待执行", value: this.countTasksByStatus("0") },
        { label: "执行中", value: this.countTasksByStatus("1") },
        { label: "已完成", value: this.countTasksByStatus("2") },
        { label: "失败", value: this.countTasksByStatus("3") },
        { label: "暂停", value: this.countTasksByStatus("4") }
      ]
    },
    shipStatusRows() {
      return [
        { label: "正常", value: this.ships.filter(item => item.status === "0").length },
        { label: "停用", value: this.ships.filter(item => item.status === "1").length },
        { label: "维护", value: this.ships.filter(item => item.status === "2").length },
        { label: "在线", value: this.ships.filter(item => item.onlineStatus === "0").length },
        { label: "离线", value: this.ships.filter(item => item.onlineStatus === "1").length }
      ]
    },
    sceneTypeRows() {
      return [
        { label: "近海", value: this.scenes.filter(item => item.sceneType === "1").length },
        { label: "港口", value: this.scenes.filter(item => item.sceneType === "2").length },
        { label: "远海", value: this.scenes.filter(item => item.sceneType === "3").length }
      ]
    },
    recentTasks() {
      return this.tasks.slice(0, 8)
    },
    onlineShipCount() {
      return this.ships.filter(item => item.onlineStatus === "0").length
    },
    runningTaskCount() {
      return this.countTasksByStatus("1")
    },
    enabledSceneCount() {
      return this.scenes.filter(item => item.status === "0").length
    },
    recommendation() {
      if (this.onlineShipCount === 0) {
        return "先恢复在线船只，再启动仿真任务。"
      }
      if (this.runningTaskCount > this.onlineShipCount) {
        return "当前执行任务偏多，建议补充船只或暂停部分任务。"
      }
      if (this.enabledSceneCount === 0) {
        return "缺少可用场景，建议先启用场景配置。"
      }
      return "资源匹配正常，可继续扩展任务调度。"
    }
  },
  created() {
    this.loadDashboard()
  },
  methods: {
    loadDashboard() {
      this.loading = true
      Promise.all([
        listAirport({ pageNum: 1, pageSize: 1000 }),
        listShip({ pageNum: 1, pageSize: 1000 }),
        listSimScene({ pageNum: 1, pageSize: 1000 }),
        listTask({ pageNum: 1, pageSize: 1000 })
      ]).then(([airportRes, shipRes, sceneRes, taskRes]) => {
        this.airports = airportRes.rows || []
        this.ships = shipRes.rows || []
        this.scenes = sceneRes.rows || []
        this.tasks = taskRes.rows || []
        this.summary = {
          airportCount: airportRes.total || this.airports.length,
          shipCount: shipRes.total || this.ships.length,
          sceneCount: sceneRes.total || this.scenes.length,
          taskCount: taskRes.total || this.tasks.length
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    countTasksByStatus(status) {
      return this.tasks.filter(item => item.status === status).length
    },
    formatTaskType(value) {
      const map = {
        "1": "巡逻任务",
        "2": "救援任务",
        "3": "科研任务",
        "4": "其他任务"
      }
      return map[value] || value || "-"
    },
    formatStatus(value) {
      const map = {
        "0": "待执行",
        "1": "执行中",
        "2": "已完成",
        "3": "失败",
        "4": "暂停"
      }
      return map[value] || value || "-"
    },
    statusTagType(value) {
      if (value === "1") return "warning"
      if (value === "2") return "success"
      if (value === "3") return "danger"
      if (value === "4") return "info"
      return ""
    },
    formatSceneName(sceneId) {
      const matched = this.scenes.find(item => item.sceneId === sceneId)
      return matched ? matched.sceneName : sceneId || "-"
    },
    formatShipName(shipId) {
      const matched = this.ships.find(item => item.shipId === shipId)
      return matched ? matched.shipName : shipId || "-"
    }
  }
}
</script>

<style scoped>
.summary-card {
  text-align: center;
}

.summary-card__value {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  line-height: 1.4;
}

.summary-card__label {
  color: #606266;
}

.stats-row {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #f0f2f5;
}

.stats-row:last-child {
  border-bottom: none;
}

.tips-box {
  line-height: 2;
  color: #606266;
}

.mb16 {
  margin-bottom: 16px;
}

.mt16 {
  margin-top: 16px;
}
</style>
