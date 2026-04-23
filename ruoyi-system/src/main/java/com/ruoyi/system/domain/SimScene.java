package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SimScene extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long sceneId;
    @Excel(name = "场景名称")
    private String sceneName;
    @Excel(name = "场景类型")
    private String sceneType;
    @Excel(name = "场景描述")
    private String description;
    @Excel(name = "地图地址")
    private String mapUrl;
    @Excel(name = "天气条件")
    private String weatherConditions;
    @Excel(name = "海况")
    private String seaConditions;
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
    private String delFlag;

    public Long getSceneId()
    {
        return sceneId;
    }

    public void setSceneId(Long sceneId)
    {
        this.sceneId = sceneId;
    }

    public String getSceneName()
    {
        return sceneName;
    }

    public void setSceneName(String sceneName)
    {
        this.sceneName = sceneName;
    }

    public String getSceneType()
    {
        return sceneType;
    }

    public void setSceneType(String sceneType)
    {
        this.sceneType = sceneType;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMapUrl()
    {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl)
    {
        this.mapUrl = mapUrl;
    }

    public String getWeatherConditions()
    {
        return weatherConditions;
    }

    public void setWeatherConditions(String weatherConditions)
    {
        this.weatherConditions = weatherConditions;
    }

    public String getSeaConditions()
    {
        return seaConditions;
    }

    public void setSeaConditions(String seaConditions)
    {
        this.seaConditions = seaConditions;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("sceneId", getSceneId())
            .append("sceneName", getSceneName())
            .append("sceneType", getSceneType())
            .append("description", getDescription())
            .append("mapUrl", getMapUrl())
            .append("weatherConditions", getWeatherConditions())
            .append("seaConditions", getSeaConditions())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
