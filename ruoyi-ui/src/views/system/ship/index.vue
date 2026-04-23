<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="船编号" prop="shipCode">
        <el-input
          v-model="queryParams.shipCode"
          placeholder="请输入船编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="船名称" prop="shipName">
        <el-input
          v-model="queryParams.shipName"
          placeholder="请输入船名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="船类型" prop="shipType">
        <el-select
          v-model="queryParams.shipType"
          placeholder="请选择船类型"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="巡逻船" value="1" />
          <el-option label="救援船" value="2" />
          <el-option label="科研船" value="3" />
          <el-option label="其他" value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
          <el-option label="维护" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="所属机场" prop="airportId">
        <el-input
          v-model="queryParams.airportId"
          placeholder="请输入所属机场ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:ship:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:ship:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:ship:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:ship:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="shipList" 
      @selection-change="handleSelectionChange"
      height="600"
      border
      stripe
      :row-key="row => row.shipId"
      :highlight-current-row="true"
      :virtual-scroll="true"
      :virtual-item-height="50"
      :virtual-slot-height="100"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="无人船ID" align="center" prop="shipId" />
      <el-table-column label="船编号" align="center" prop="shipCode" />
      <el-table-column label="船名称" align="center" prop="shipName" />
      <el-table-column label="船类型" align="center" prop="shipType">
        <template slot-scope="scope">
          <span v-if="scope.row.shipType === '1'">巡逻船</span>
          <span v-else-if="scope.row.shipType === '2'">救援船</span>
          <span v-else-if="scope.row.shipType === '3'">科研船</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column label="所属机场ID" align="center" prop="airportId" />
      <el-table-column label="经度" align="center" prop="longitude" />
      <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="速度" align="center" prop="speed" />
      <el-table-column label="航向" align="center" prop="course" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === '0'">正常</span>
          <span v-else-if="scope.row.status === '1'">停用</span>
          <span v-else>维护</span>
        </template>
      </el-table-column>
      <el-table-column label="在线状态" align="center" prop="onlineStatus">
        <template slot-scope="scope">
          <span v-if="scope.row.onlineStatus === '0'">在线</span>
          <span v-else>离线</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:ship:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:ship:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改无人船信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="船编号" prop="shipCode">
              <el-input v-model="form.shipCode" placeholder="请输入船编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="船名称" prop="shipName">
              <el-input v-model="form.shipName" placeholder="请输入船名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="船类型" prop="shipType">
              <el-select v-model="form.shipType" placeholder="请选择船类型">
                <el-option label="巡逻船" value="1" />
                <el-option label="救援船" value="2" />
                <el-option label="科研船" value="3" />
                <el-option label="其他" value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所属机场ID" prop="airportId">
              <el-input v-model="form.airportId" placeholder="请输入所属机场ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="经度" prop="longitude">
              <el-input v-model="form.longitude" placeholder="请输入经度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model="form.latitude" placeholder="请输入纬度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="速度" prop="speed">
              <el-input v-model="form.speed" placeholder="请输入速度" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="航向" prop="course">
              <el-input v-model="form.course" placeholder="请输入航向" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="正常" value="0" />
                <el-option label="停用" value="1" />
                <el-option label="维护" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="在线状态" prop="onlineStatus">
              <el-select v-model="form.onlineStatus" placeholder="请选择在线状态">
                <el-option label="在线" value="0" />
                <el-option label="离线" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

export default {
  name: "Ship",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 无人船信息表格数据
      shipList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shipCode: null,
        shipName: null,
        shipType: null,
        airportId: null,
        status: null,
        onlineStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shipCode: [
          { required: true, message: "船编号不能为空", trigger: "blur" }
        ],
        shipName: [
          { required: true, message: "船名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询无人船信息列表 */
    getList() {
      this.loading = true
      listShip(this.queryParams).then(response => {
        this.shipList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        shipId: null,
        shipCode: null,
        shipName: null,
        shipType: null,
        airportId: null,
        longitude: null,
        latitude: null,
        speed: null,
        course: null,
        status: null,
        onlineStatus: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.shipId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加无人船信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const shipId = row.shipId || this.ids
      getShip(shipId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改无人船信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.shipId != null) {
            updateShip(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addShip(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const shipIds = row.shipId || this.ids
      this.$modal.confirm('是否确认删除无人船信息编号为"' + shipIds + '"的数据项？').then(function() {
        return delShip(shipIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$utils.download('system/ship/export', {
        ...this.queryParams
      }, `ship_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
