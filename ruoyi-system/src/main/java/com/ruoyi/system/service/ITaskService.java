package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Task;

/**
 * 任务信息Service接口
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
public interface ITaskService 
{
    /**
     * 查询任务信息
     * 
     * @param taskId 任务信息主键
     * @return 任务信息
     */
    public Task selectTaskByTaskId(Long taskId);

    /**
     * 查询任务信息列表
     * 
     * @param task 任务信息
     * @return 任务信息集合
     */
    public List<Task> selectTaskList(Task task);

    /**
     * 新增任务信息
     * 
     * @param task 任务信息
     * @return 结果
     */
    public int insertTask(Task task);

    /**
     * 修改任务信息
     * 
     * @param task 任务信息
     * @return 结果
     */
    public int updateTask(Task task);

    /**
     * 批量删除任务信息
     * 
     * @param taskIds 需要删除的任务信息主键集合
     * @return 结果
     */
    public int deleteTaskByTaskIds(Long[] taskIds);

    /**
     * 删除任务信息信息
     * 
     * @param taskId 任务信息主键
     * @return 结果
     */
    public int deleteTaskByTaskId(Long taskId);
}