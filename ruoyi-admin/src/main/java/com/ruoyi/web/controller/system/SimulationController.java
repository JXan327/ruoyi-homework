package com.ruoyi.web.controller.system;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ISimulationService;

/**
 * 仿真服务Controller
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
@RestController
@RequestMapping("/system/simulation")
public class SimulationController extends BaseController
{
    @Autowired
    private ISimulationService simulationService;

    /**
     * 开始仿真任务
     */
    @PreAuthorize("@ss.hasPermi('system:simulation:start')")
    @Log(title = "仿真任务", businessType = BusinessType.OTHER)
    @PostMapping("/start/{taskId}")
    public AjaxResult startSimulation(@PathVariable("taskId") Long taskId)
    {
        boolean result = simulationService.startSimulation(taskId);
        return result ? success("仿真任务已开始") : error("仿真任务启动失败");
    }

    /**
     * 暂停仿真任务
     */
    @PreAuthorize("@ss.hasPermi('system:simulation:pause')")
    @Log(title = "仿真任务", businessType = BusinessType.OTHER)
    @PostMapping("/pause/{taskId}")
    public AjaxResult pauseSimulation(@PathVariable("taskId") Long taskId)
    {
        boolean result = simulationService.pauseSimulation(taskId);
        return result ? success("仿真任务已暂停") : error("仿真任务暂停失败");
    }

    /**
     * 停止仿真任务
     */
    @PreAuthorize("@ss.hasPermi('system:simulation:stop')")
    @Log(title = "仿真任务", businessType = BusinessType.OTHER)
    @PostMapping("/stop/{taskId}")
    public AjaxResult stopSimulation(@PathVariable("taskId") Long taskId)
    {
        boolean result = simulationService.stopSimulation(taskId);
        return result ? success("仿真任务已停止") : error("仿真任务停止失败");
    }

    /**
     * 获取仿真状态
     */
    @PreAuthorize("@ss.hasPermi('system:simulation:status')")
    @GetMapping("/status/{taskId}")
    public AjaxResult getSimulationStatus(@PathVariable("taskId") Long taskId)
    {
        String status = simulationService.getSimulationStatus(taskId);
        return success(status);
    }

    /**
     * 分析仿真数据
     */
    @PreAuthorize("@ss.hasPermi('system:simulation:analyze')")
    @GetMapping("/analyze/{taskId}")
    public AjaxResult analyzeSimulationData(@PathVariable("taskId") Long taskId)
    {
        String analysis = simulationService.analyzeSimulationData(taskId);
        return success(analysis);
    }
}