package com.ruoyi.system.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SimScene;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.domain.UavBoat;
import com.ruoyi.system.service.ISimSceneService;
import com.ruoyi.system.service.ISimulationService;
import com.ruoyi.system.service.ITaskService;
import com.ruoyi.system.service.IUavBoatService;

@Service
public class SimulationServiceImpl implements ISimulationService
{
    @Autowired
    private ITaskService taskService;

    @Autowired
    private IUavBoatService uavBoatService;

    @Autowired
    private ISimSceneService simSceneService;

    private final Map<Long, String> simulationStatusMap = new ConcurrentHashMap<>();

    @Override
    public boolean startSimulation(Long taskId)
    {
        Task task = taskService.selectTaskByTaskId(taskId);
        if (task == null)
        {
            return false;
        }

        task.setStatus("1");
        task.setStartTime(DateUtils.getNowDate());
        task.setProgress(0);
        taskService.updateTask(task);
        simulationStatusMap.put(taskId, "running");

        new Thread(() -> {
            try
            {
                simulateTask(task);
            }
            catch (Exception exception)
            {
                task.setStatus("3");
                task.setTaskResult("仿真失败: " + exception.getMessage());
                taskService.updateTask(task);
                simulationStatusMap.put(taskId, "failed");
            }
        }).start();

        return true;
    }

    @Override
    public boolean pauseSimulation(Long taskId)
    {
        Task task = taskService.selectTaskByTaskId(taskId);
        if (task == null)
        {
            return false;
        }

        task.setStatus("4");
        taskService.updateTask(task);
        simulationStatusMap.put(taskId, "paused");
        return true;
    }

    @Override
    public boolean stopSimulation(Long taskId)
    {
        Task task = taskService.selectTaskByTaskId(taskId);
        if (task == null)
        {
            return false;
        }

        task.setStatus("2");
        task.setEndTime(DateUtils.getNowDate());
        task.setProgress(100);
        task.setTaskResult("任务已手动停止");
        taskService.updateTask(task);
        simulationStatusMap.put(taskId, "stopped");
        return true;
    }

    @Override
    public String getSimulationStatus(Long taskId)
    {
        return simulationStatusMap.getOrDefault(taskId, "not_started");
    }

    @Override
    public String calculatePath(UavBoat boat, SimScene scene, Task task)
    {
        return "路径规划结果: 起点 -> 中间点1 -> 中间点2 -> 终点";
    }

    @Override
    public boolean scheduleTask(Task task)
    {
        UavBoat boat = uavBoatService.selectUavBoatByBoatId(task.getBoatId());
        if (boat == null || "1".equals(boat.getStatus()))
        {
            return false;
        }

        SimScene scene = simSceneService.selectSimSceneBySceneId(task.getSceneId());
        if (scene == null || "1".equals(scene.getStatus()))
        {
            return false;
        }

        task.setStatus("0");
        taskService.updateTask(task);
        return true;
    }

    @Override
    public String analyzeSimulationData(Long taskId)
    {
        Task task = taskService.selectTaskByTaskId(taskId);
        if (task == null)
        {
            return "任务不存在";
        }
        return "仿真数据分析结果: 任务完成时间: " + task.getEndTime() + ", 任务状态: " + getStatusText(task.getStatus());
    }

    private void simulateTask(Task task) throws InterruptedException
    {
        Thread.sleep(5000);
        task.setStatus("2");
        task.setEndTime(DateUtils.getNowDate());
        task.setProgress(100);
        task.setTaskResult("仿真任务执行成功");
        taskService.updateTask(task);
        simulationStatusMap.put(task.getTaskId(), "completed");
    }

    private String getStatusText(String status)
    {
        switch (status)
        {
            case "0":
                return "待执行";
            case "1":
                return "执行中";
            case "2":
                return "已完成";
            case "3":
                return "失败";
            case "4":
                return "暂停";
            default:
                return "未知";
        }
    }
}
