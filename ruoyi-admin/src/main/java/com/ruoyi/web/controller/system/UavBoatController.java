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
import com.ruoyi.system.domain.UavBoat;
import com.ruoyi.system.service.IUavBoatService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 无人船信息Controller
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
@RestController
@RequestMapping("/system/boat")
public class UavBoatController extends BaseController {
  @Autowired
  private IUavBoatService uavBoatService;

  /**
   * 查询无人船信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:boat:list')")
  @GetMapping("/list")
  public TableDataInfo list(UavBoat uavBoat) {
    startPage();
    List<UavBoat> list = uavBoatService.selectUavBoatList(uavBoat);
    return getDataTable(list);
  }

  /**
   * 导出无人船信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:boat:export')")
  @Log(title = "无人船信息", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, UavBoat uavBoat) {
    List<UavBoat> list = uavBoatService.selectUavBoatList(uavBoat);
    ExcelUtil<UavBoat> util = new ExcelUtil<UavBoat>(UavBoat.class);
    util.exportExcel(response, list, "无人船信息数据");
  }

  /**
   * 获取无人船信息详细信息
   */
  @PreAuthorize("@ss.hasPermi('system:boat:query')")
  @GetMapping(value = "/{boatId}")
  public AjaxResult getInfo(@PathVariable("boatId") Long boatId) {
    return success(uavBoatService.selectUavBoatByBoatId(boatId));
  }

  /**
   * 新增无人船信息
   */
  @PreAuthorize("@ss.hasPermi('system:boat:add')")
  @Log(title = "无人船信息", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody UavBoat uavBoat) {
    return toAjax(uavBoatService.insertUavBoat(uavBoat));
  }

  /**
   * 修改无人船信息
   */
  @PreAuthorize("@ss.hasPermi('system:boat:edit')")
  @Log(title = "无人船信息", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody UavBoat uavBoat) {
    return toAjax(uavBoatService.updateUavBoat(uavBoat));
  }

  /**
   * 删除无人船信息
   */
  @PreAuthorize("@ss.hasPermi('system:boat:remove')")
  @Log(title = "无人船信息", businessType = BusinessType.DELETE)
  @DeleteMapping("/{boatIds}")
  public AjaxResult remove(@PathVariable Long[] boatIds) {
    return toAjax(uavBoatService.deleteUavBoatByBoatIds(boatIds));
  }
}