<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机场编号" prop="airportCode">
        <el-input
          v-model="queryParams.airportCode"
          placeholder="请输入机场编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机场名称" prop="airportName">
        <el-input
          v-model="queryParams.airportName"
          placeholder="请输入机场名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机场类型" prop="airportType">
        <el-select
          v-model="queryParams.airportType"
          placeholder="请选择机场类型"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="固定机场" value="1" />
          <el-option label="移动机场" value="2" />
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
        </el-select>
      </el-form-item>
      <el-form-item label="在线状态" prop="onlineStatus">
        <el-select
          v-model="queryParams.onlineStatus"
          placeholder="请选择在线状态"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="在线" value="0" />
          <el-option label="离线" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="省" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入省"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="市" prop="city">
        <el-input
          v-model="queryParams.city"
          placeholder="请输入市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区/县" prop="district">
        <el-input
          v-model="queryParams.district"
          placeholder="请输入区/县"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入详细地址"
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
          v-hasPermi="['system:airport:add']"
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
          v-hasPermi="['system:airport:edit']"
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
          v-hasPermi="['system:airport:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:airport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="airportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="无人机场ID" align="center" prop="airportId" />
      <el-table-column label="机场编号" align="center" prop="airportCode" />
      <el-table-column label="机场名称" align="center" prop="airportName" />
      <el-table-column label="机场类型" align="center" prop="airportType" />
      <el-table-column label="省" align="center" prop="province" />
      <el-table-column label="市" align="center" prop="city" />
      <el-table-column label="区/县" align="center" prop="district" />
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="经度" align="center" prop="longitude" />
      <el-table-column label="纬度" align="center" prop="latitude" />
      <el-table-column label="海拔高度(米)" align="center" prop="altitude" />
      <el-table-column label="覆盖半径(米)" align="center" prop="coverRadius" />
      <el-table-column label="最大容纳无人机数量" align="center" prop="maxUavCount" />
      <el-table-column label="负责人" align="center" prop="managerName" />
      <el-table-column label="负责人电话" align="center" prop="managerPhone" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="在线状态" align="center" prop="onlineStatus" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:airport:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:airport:remove']"
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

    <!-- 添加或修改无人机场信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="机场编号" prop="airportCode">
              <el-input v-model="form.airportCode" placeholder="请输入机场编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="机场名称" prop="airportName">
              <el-input v-model="form.airportName" placeholder="请输入机场名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="机场类型" prop="airportType">
              <el-select v-model="form.airportType" placeholder="请选择机场类型">
                <el-option label="固定机场" value="1" />
                <el-option label="移动机场" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="正常" value="0" />
                <el-option label="停用" value="1" />
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
            <el-form-item label="省" prop="province">
              <el-input v-model="form.province" placeholder="请输入省" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="市" prop="city">
              <el-input v-model="form.city" placeholder="请输入市" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="区/县" prop="district">
              <el-input v-model="form.district" placeholder="请输入区/县" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入详细地址" />
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
            <el-form-item label="海拔高度(米)" prop="altitude">
              <el-input v-model="form.altitude" placeholder="请输入海拔高度(米)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="覆盖半径(米)" prop="coverRadius">
              <el-input v-model="form.coverRadius" placeholder="请输入覆盖半径(米)" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="最大容纳无人机数量" prop="maxUavCount">
              <el-input v-model="form.maxUavCount" placeholder="请输入最大容纳无人机数量" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="负责人" prop="managerName">
              <el-input v-model="form.managerName" placeholder="请输入负责人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="负责人电话" prop="managerPhone">
              <el-input v-model="form.managerPhone" placeholder="请输入负责人电话" />
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
import { listAirport, getAirport, delAirport, addAirport, updateAirport } from "@/api/system/airport"

export default {
  name: "Airport",
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
      // 无人机场信息表格数据
      airportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        airportCode: null,
        airportName: null,
        airportType: null,
        province: null,
        city: null,
        district: null,
        address: null,
        longitude: null,
        latitude: null,
        altitude: null,
        coverRadius: null,
        maxUavCount: null,
        managerName: null,
        managerPhone: null,
        status: null,
        onlineStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        airportCode: [
          { required: true, message: "机场编号不能为空", trigger: "blur" }
        ],
        airportName: [
          { required: true, message: "机场名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询无人机场信息列表 */
    getList() {
      this.loading = true
      listAirport(this.queryParams).then(response => {
        this.airportList = response.rows
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
        airportId: null,
        airportCode: null,
        airportName: null,
        airportType: null,
        province: null,
        city: null,
        district: null,
        address: null,
        longitude: null,
        latitude: null,
        altitude: null,
        coverRadius: null,
        maxUavCount: null,
        managerName: null,
        managerPhone: null,
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
      this.ids = selection.map(item => item.airportId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加无人机场信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const airportId = row.airportId || this.ids
      getAirport(airportId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改无人机场信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.airportId != null) {
            updateAirport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAirport(this.form).then(response => {
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
      const airportIds = row.airportId || this.ids
      this.$modal.confirm('是否确认删除无人机场信息编号为"' + airportIds + '"的数据项？').then(function() {
        return delAirport(airportIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/airport/export', {
        ...this.queryParams
      }, `airport_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
