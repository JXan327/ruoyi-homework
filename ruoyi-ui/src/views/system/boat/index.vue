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
      <el-form-item label="船编号" prop="shipCode">
        <el-input v-model="queryParams.shipCode" placeholder="请输入船编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="船名称" prop="shipName">
        <el-input v-model="queryParams.shipName" placeholder="请输入船名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="船类型" prop="shipType">
        <el-select v-model="queryParams.shipType" placeholder="请选择船类型" clearable>
          <el-option v-for="item in shipTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属机场" prop="airportId">
        <el-select v-model="queryParams.airportId" placeholder="请选择所属机场" clearable filterable>
          <el-option v-for="item in airportOptions" :key="item.airportId" :label="item.airportName" :value="item.airportId" />
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
        <el-button v-hasPermi="['system:boat:add']" type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:boat:edit']" type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:boat:remove']" type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['system:boat:export']" type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="shipList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="无人船ID" align="center" prop="shipId" />
      <el-table-column label="船编号" align="center" prop="shipCode" />
      <el-table-column label="船名称" align="center" prop="shipName" />
      <el-table-column label="船类型" align="center" prop="shipType">
        <template slot-scope="{ row }">
          <span>{{ formatShipType(row.shipType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属机场" align="center">
        <template slot-scope="{ row }">
          <span>{{ formatAirportName(row.airportId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="经度" align="center" prop="longitude" />
      <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="速度" align="center" prop="speed" />
      <el-table-column label="航向" align="center" prop="course" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="{ row }">
          <el-tag :type="row.status === '0' ? 'success' : row.status === '1' ? 'info' : 'warning'">
            {{ formatStatus(row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="在线状态" align="center" prop="onlineStatus">
        <template slot-scope="{ row }">
          <el-tag :type="row.onlineStatus === '0' ? 'success' : 'danger'">
            {{ row.onlineStatus === '0' ? '在线' : '离线' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{ row }">
          <el-button v-hasPermi="['system:boat:edit']" size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(row)">修改</el-button>
          <el-button v-hasPermi="['system:boat:remove']" size="mini" type="text" icon="el-icon-delete" @click="handleDelete(row)">删除</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="船编号" prop="shipCode">
              <el-input v-model="form.shipCode" placeholder="请输入船编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船名称" prop="shipName">
              <el-input v-model="form.shipName" placeholder="请输入船名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="船类型" prop="shipType">
              <el-select v-model="form.shipType" placeholder="请选择船类型" style="width: 100%">
                <el-option v-for="item in shipTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属机场" prop="airportId">
              <el-select v-model="form.airportId" placeholder="请选择所属机场" filterable style="width: 100%">
                <el-option v-for="item in airportOptions" :key="item.airportId" :label="item.airportName" :value="item.airportId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入经度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model="form.latitude" placeholder="请输入纬度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="速度" prop="speed">
              <el-input v-model="form.speed" placeholder="请输入速度" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="航向" prop="course">
              <el-input v-model="form.course" placeholder="请输入航向" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="在线状态" prop="onlineStatus">
              <el-select v-model="form.onlineStatus" placeholder="请选择在线状态" style="width: 100%">
                <el-option label="在线" value="0" />
                <el-option label="离线" value="1" />
              </el-select>
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
import { listShip, getShip, delShip, addShip, updateShip } from "@/api/system/ship"
import { listAirport } from "@/api/system/airport"

export default {
  name: "Boat",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      shipList: [],
      airportOptions: [],
      title: "",
      open: false,
      shipTypeOptions: [
        { label: "巡逻船", value: "1" },
        { label: "救援船", value: "2" },
        { label: "科研船", value: "3" },
        { label: "其他", value: "4" }
      ],
      statusOptions: [
        { label: "正常", value: "0" },
        { label: "停用", value: "1" },
        { label: "维护", value: "2" }
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipCode: null,
        shipName: null,
        shipType: null,
        airportId: null,
        status: null
      },
      form: {},
      rules: {
        shipCode: [{ required: true, message: "船编号不能为空", trigger: "blur" }],
        shipName: [{ required: true, message: "船名称不能为空", trigger: "blur" }],
        shipType: [{ required: true, message: "请选择船类型", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
    this.loadAirports()
  },
  methods: {
    getList() {
      this.loading = true
      listShip(this.queryParams).then(response => {
        this.shipList = response.rows || []
        this.total = response.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    loadAirports() {
      listAirport({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.airportOptions = response.rows || []
      })
    },
    formatShipType(value) {
      const matched = this.shipTypeOptions.find(item => item.value === value)
      return matched ? matched.label : value || "-"
    },
    formatStatus(value) {
      const matched = this.statusOptions.find(item => item.value === value)
      return matched ? matched.label : value || "-"
    },
    formatAirportName(airportId) {
      const matched = this.airportOptions.find(item => item.airportId === airportId)
      return matched ? matched.airportName : airportId || "-"
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        shipId: null,
        shipCode: null,
        shipName: null,
        shipType: "1",
        airportId: null,
        longitude: null,
        latitude: null,
        speed: null,
        course: null,
        status: "0",
        onlineStatus: "0",
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
      this.ids = selection.map(item => item.shipId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "新增无人船"
    },
    handleUpdate(row) {
      this.reset()
      const shipId = row.shipId || this.ids[0]
      getShip(shipId).then(response => {
        this.form = Object.assign({}, this.form, response.data)
        this.open = true
        this.title = "修改无人船"
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const request = this.form.shipId ? updateShip(this.form) : addShip(this.form)
        request.then(() => {
          this.$modal.msgSuccess(this.form.shipId ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const shipIds = row.shipId || this.ids
      this.$modal.confirm('是否确认删除无人船编号为 "' + shipIds + '" 的数据项？').then(() => {
        return delShip(shipIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleExport() {
      this.download("system/boat/export", { ...this.queryParams }, `boat_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
