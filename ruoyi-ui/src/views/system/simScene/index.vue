<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="场景编号" prop="sceneCode">
        <el-input
          v-model="queryParams.sceneCode"
          placeholder="请输入场景编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景名称" prop="sceneName">
        <el-input
          v-model="queryParams.sceneName"
          placeholder="请输入场景名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景类型" prop="sceneType">
        <el-select
          v-model="queryParams.sceneType"
          placeholder="请选择场景类型"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="河流场景" value="1" />
          <el-option label="湖泊场景" value="2" />
          <el-option label="海洋场景" value="3" />
          <el-option label="港口场景" value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="启用" value="0" />
          <el-option label="禁用" value="1" />
        </el-select>
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
          v-hasPermi="['system:simScene:add']"
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
          v-hasPermi="['system:simScene:edit']"
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
          v-hasPermi="['system:simScene:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:simScene:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="simSceneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="场景ID" align="center" prop="sceneId" />
      <el-table-column label="场景编号" align="center" prop="sceneCode" />
      <el-table-column label="场景名称" align="center" prop="sceneName" />
      <el-table-column label="场景类型" align="center" prop="sceneType">
        <template slot-scope="scope">
          <span v-if="scope.row.sceneType === '1'">河流场景</span>
          <span v-else-if="scope.row.sceneType === '2'">湖泊场景</span>
          <span v-else-if="scope.row.sceneType === '3'">海洋场景</span>
          <span v-else>港口场景</span>
        </template>
      </el-table-column>
      <el-table-column label="场景描述" align="center" prop="sceneDesc" />
      <el-table-column label="地图范围" align="center" prop="mapRange" />
      <el-table-column label="天气条件" align="center" prop="weatherCondition" />
      <el-table-column label="海况条件" align="center" prop="seaCondition" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === '0'">启用</span>
          <span v-else>禁用</span>
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
            v-hasPermi="['system:simScene:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:simScene:remove']"
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

    <!-- 添加或修改仿真场景信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="场景编号" prop="sceneCode">
              <el-input v-model="form.sceneCode" placeholder="请输入场景编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="场景名称" prop="sceneName">
              <el-input v-model="form.sceneName" placeholder="请输入场景名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="场景类型" prop="sceneType">
              <el-select v-model="form.sceneType" placeholder="请选择场景类型">
                <el-option label="河流场景" value="1" />
                <el-option label="湖泊场景" value="2" />
                <el-option label="海洋场景" value="3" />
                <el-option label="港口场景" value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="场景描述" prop="sceneDesc">
              <el-input v-model="form.sceneDesc" type="textarea" placeholder="请输入场景描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="地图范围" prop="mapRange">
              <el-input v-model="form.mapRange" placeholder="请输入地图范围" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="天气条件" prop="weatherCondition">
              <el-input v-model="form.weatherCondition" placeholder="请输入天气条件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="海况条件" prop="seaCondition">
              <el-input v-model="form.seaCondition" placeholder="请输入海况条件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="启用" value="0" />
                <el-option label="禁用" value="1" />
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
import { listSimScene, getSimScene, delSimScene, addSimScene, updateSimScene } from "@/api/system/simScene"

export default {
  name: "SimScene",
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
      // 仿真场景信息表格数据
      simSceneList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sceneCode: null,
        sceneName: null,
        sceneType: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sceneCode: [
          { required: true, message: "场景编号不能为空", trigger: "blur" }
        ],
        sceneName: [
          { required: true, message: "场景名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询仿真场景信息列表 */
    getList() {
      this.loading = true
      listSimScene(this.queryParams).then(response => {
        this.simSceneList = response.rows
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
        sceneId: null,
        sceneCode: null,
        sceneName: null,
        sceneType: null,
        sceneDesc: null,
        mapRange: null,
        weatherCondition: null,
        seaCondition: null,
        status: null,
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
      this.ids = selection.map(item => item.sceneId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加仿真场景信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const sceneId = row.sceneId || this.ids
      getSimScene(sceneId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改仿真场景信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sceneId != null) {
            updateSimScene(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSimScene(this.form).then(response => {
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
      const sceneIds = row.sceneId || this.ids
      this.$modal.confirm('是否确认删除仿真场景信息编号为"' + sceneIds + '"的数据项？').then(function() {
        return delSimScene(sceneIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/simScene/export', {
        ...this.queryParams
      }, `simScene_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
