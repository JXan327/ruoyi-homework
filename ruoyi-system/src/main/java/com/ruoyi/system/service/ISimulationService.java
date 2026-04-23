package com.ruoyi.system.service;

import com.ruoyi.system.domain.Task;
import com.ruoyi.system.domain.UavBoat;
import com.ruoyi.system.domain.SimScene;

/**
 * 仿真服务接口
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
public interface ISimulationService 
{
    /**
     * 开始仿真任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public boolean startSimulation(Long taskId);

    /**
     * 暂停仿真任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public boolean pauseSimulation(Long taskId);

    /**
     * 停止仿真任务
     * 
     * @param taskId 任务ID
     * @return 结果
     */
    public boolean stopSimulation(Long taskId);

    /**
     * 获取仿真状态
     * 
     * @param taskId 任务ID
     * @return 仿真状态
     */
    public String getSimulationStatus(Long taskId);

    /**
     * 计算无人船路径
     * 
     * @param boat 无人船信息
     * @param scene 仿真场景
     * @param task 任务信息
     * @return 路径规划结果
     */
    public String calculatePath(UavBoat boat, SimScene scene, Task task);

    /**
     * 任务调度
     * 
     * @param task 任务信息
     * @return 调度结果
     */
    public boolean scheduleTask(Task task);

    /**
     * 分析仿真数据
     * 
     * @param taskId 任务ID
     * @return 分析结果
     */
    public String analyzeSimulationData(Long taskId);
}