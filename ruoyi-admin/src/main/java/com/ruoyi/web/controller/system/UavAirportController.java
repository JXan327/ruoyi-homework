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
import com.ruoyi.system.domain.UavAirport;
import com.ruoyi.system.service.IUavAirportService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 无人机场信息Controller
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
@RestController
@RequestMapping("/system/airport")
public class UavAirportController extends BaseController {
  @Autowired
  private IUavAirportService uavAirportService;

  /**
   * 查询无人机场信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:airport:list')")
  @GetMapping("/list")
  public TableDataInfo list(UavAirport uavAirport) {
    startPage();
    List<UavAirport> list = uavAirportService.selectUavAirportList(uavAirport);
    return getDataTable(list);
  }

  /**
   * 导出无人机场信息列表
   */
  @PreAuthorize("@ss.hasPermi('system:airport:export')")
  @Log(title = "无人机场信息", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, UavAirport uavAirport) {
    List<UavAirport> list = uavAirportService.selectUavAirportList(uavAirport);
    ExcelUtil<UavAirport> util = new ExcelUtil<UavAirport>(UavAirport.class);
    util.exportExcel(response, list, "无人机场信息数据");
  }

  /**
   * 获取无人机场信息详细信息
   */
  @PreAuthorize("@ss.hasPermi('system:airport:query')")
  @GetMapping(value = "/{airportId}")
  public AjaxResult getInfo(@PathVariable("airportId") Long airportId) {
    return success(uavAirportService.selectUavAirportByAirportId(airportId));
  }

  /**
   * 新增无人机场信息
   */
  @PreAuthorize("@ss.hasPermi('system:airport:add')")
  @Log(title = "无人机场信息", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody UavAirport uavAirport) {
    return toAjax(uavAirportService.insertUavAirport(uavAirport));
  }

  /**
   * 修改无人机场信息
   */
  @PreAuthorize("@ss.hasPermi('system:airport:edit')")
  @Log(title = "无人机场信息", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody UavAirport uavAirport) {
    return toAjax(uavAirportService.updateUavAirport(uavAirport));
  }

  /**
   * 删除无人机场信息
   */
  @PreAuthorize("@ss.hasPermi('system:airport:remove')")
  @Log(title = "无人机场信息", businessType = BusinessType.DELETE)
  @DeleteMapping("/{airportIds}")
  public AjaxResult remove(@PathVariable Long[] airportIds) {
    return toAjax(uavAirportService.deleteUavAirportByAirportIds(airportIds));
  }
}