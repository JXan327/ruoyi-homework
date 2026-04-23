<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          <el-option label="近海" value="1" />
          <el-option label="港口" value="2" />
          <el-option label="远海" value="3" />
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
          v-hasPermi="['system:scene:add']"
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
          v-hasPermi="['system:scene:edit']"
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
          v-hasPermi="['system:scene:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:scene:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sceneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="场景ID" align="center" prop="sceneId" />
      <el-table-column label="场景名称" align="center" prop="sceneName" />
      <el-table-column label="场景类型" align="center" prop="sceneType">
        <template slot-scope="scope">
          <span v-if="scope.row.sceneType === '1'">近海</span>
          <span v-else-if="scope.row.sceneType === '2'">港口</span>
          <span v-else>远海</span>
        </template>
      </el-table-column>
      <el-table-column label="场景描述" align="center" prop="description" />
      <el-table-column label="地图URL" align="center" prop="mapUrl" />
      <el-table-column label="天气条件" align="center" prop="weatherConditions" />
      <el-table-column label="海况" align="center" prop="seaConditions" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:scene:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:scene:remove']"
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

    <!-- 添加或修改场景信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="场景名称" prop="sceneName">
              <el-input v-model="form.sceneName" placeholder="请输入场景名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="场景类型" prop="sceneType">
              <el-select v-model="form.sceneType" placeholder="请选择场景类型">
                <el-option label="近海" value="1" />
                <el-option label="港口" value="2" />
                <el-option label="远海" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="场景描述" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入场景描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="地图URL" prop="mapUrl">
              <el-input v-model="form.mapUrl" placeholder="请输入地图URL" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="天气条件" prop="weatherConditions">
              <el-input v-model="form.weatherConditions" placeholder="请输入天气条件" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="海况" prop="seaConditions">
              <el-input v-model="form.seaConditions" placeholder="请输入海况" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
  name: "Scene",
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
      // 场景信息表格数据
      sceneList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sceneName: null,
        sceneType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
    /** 查询场景信息列表 */
    getList() {
      this.loading = true
      listSimScene(this.queryParams).then(response => {
        this.sceneList = response.rows
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
        sceneName: null,
        sceneType: null,
        description: null,
        mapUrl: null,
        weatherConditions: null,
        seaConditions: null,
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
      this.title = "添加场景信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const sceneId = row.sceneId || this.ids
      getSimScene(sceneId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改场景信息"
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
      this.$modal.confirm('是否确认删除场景信息编号为"' + sceneIds + '"的数据项？').then(function() {
        return delSimScene(sceneIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$utils.download('system/simScene/export', {
        ...this.queryParams
      }, `scene_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
