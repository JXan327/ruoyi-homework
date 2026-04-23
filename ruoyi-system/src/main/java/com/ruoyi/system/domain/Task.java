package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class Task extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long taskId;
    @Excel(name = "任务编号")
    private String taskCode;
    @Excel(name = "任务名称")
    private String taskName;
    @Excel(name = "任务类型")
    private String taskType;
    @Excel(name = "场景ID")
    private Long sceneId;
    @Excel(name = "船只ID")
    private Long boatId;
    @Excel(name = "状态", readConverterExp = "0=待执行,1=执行中,2=已完成,3=失败,4=暂停")
    private String status;
    @Excel(name = "进度")
    private Integer progress;
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Excel(name = "任务结果")
    private String taskResult;
    @Excel(name = "任务描述")
    private String taskDescription;
    private String delFlag;

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskCode()
    {
        return taskCode;
    }

    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskType()
    {
        return taskType;
    }

    public void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }

    public Long getSceneId()
    {
        return sceneId;
    }

    public void setSceneId(Long sceneId)
    {
        this.sceneId = sceneId;
    }

    public Long getBoatId()
    {
        return boatId;
    }

    public void setBoatId(Long boatId)
    {
        this.boatId = boatId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getProgress()
    {
        return progress;
    }

    public void setProgress(Integer progress)
    {
        this.progress = progress;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getTaskResult()
    {
        return taskResult;
    }

    public void setTaskResult(String taskResult)
    {
        this.taskResult = taskResult;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getShipId()
    {
        return boatId;
    }

    public void setShipId(Long shipId)
    {
        this.boatId = shipId;
    }

    public String getTaskDesc()
    {
        return taskDescription;
    }

    public void setTaskDesc(String taskDesc)
    {
        this.taskDescription = taskDesc;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskCode", getTaskCode())
            .append("taskName", getTaskName())
            .append("taskType", getTaskType())
            .append("sceneId", getSceneId())
            .append("boatId", getBoatId())
            .append("status", getStatus())
            .append("progress", getProgress())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("taskResult", getTaskResult())
            .append("taskDescription", getTaskDescription())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
