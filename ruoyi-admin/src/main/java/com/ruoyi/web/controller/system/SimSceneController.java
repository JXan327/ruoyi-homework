package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SimScene;
import com.ruoyi.system.service.ISimSceneService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仿真场景信息Controller
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
@RestController
@RequestMapping("/system/scene")
public class SimSceneController extends BaseController {
  @Autowired
  private ISimSceneService simSceneService;

  /**
   * 查询仿真场景信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:scene:list')")
  @GetMapping("/list")
  public TableDataInfo list(SimScene simScene) {
    startPage();
    List<SimScene> list = simSceneService.selectSimSceneList(simScene);
    return getDataTable(list);
  }

  /**
   * 导出仿真场景信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:scene:export')")
  @Log(title = "仿真场景信息", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, SimScene simScene) {
    List<SimScene> list = simSceneService.selectSimSceneList(simScene);
    ExcelUtil<SimScene> util = new ExcelUtil<SimScene>(SimScene.class);
    util.exportExcel(response, list, "仿真场景信息数据");
  }

  /**
   * 获取仿真场景信息详细信息
   */
  @PreAuthorize("@ss.hasPermi('system:scene:query')")
  @GetMapping(value = "/{sceneId}")
  public AjaxResult getInfo(@PathVariable("sceneId") Long sceneId) {
    return success(simSceneService.selectSimSceneBySceneId(sceneId));
  }

  /**
   * 新增仿真场景信息
   */
  @PreAuthorize("@ss.hasPermi('system:scene:add')")
  @Log(title = "仿真场景信息", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody SimScene simScene) {
    return toAjax(simSceneService.insertSimScene(simScene));
  }

  /**
   * 修改仿真场景信息
   */
  @PreAuthorize("@ss.hasPermi('system:scene:edit')")
  @Log(title = "仿真场景信息", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody SimScene simScene) {
    return toAjax(simSceneService.updateSimScene(simScene));
  }

  /**
   * 删除仿真场景信息
   */
  @PreAuthorize("@ss.hasPermi('system:scene:remove')")
  @Log(title = "仿真场景信息", businessType = BusinessType.DELETE)
  @DeleteMapping("/{sceneIds}")
  public AjaxResult remove(@PathVariable Long[] sceneIds) {
    return toAjax(simSceneService.deleteSimSceneBySceneIds(sceneIds));
  }
}