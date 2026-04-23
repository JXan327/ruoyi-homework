package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class UavBoat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long boatId;
    @Excel(name = "船编号")
    private String boatCode;
    @Excel(name = "船名称")
    private String boatName;
    @Excel(name = "船类型")
    private String boatType;
    @Excel(name = "所属机场ID")
    private Long airportId;
    @Excel(name = "经度")
    private BigDecimal longitude;
    @Excel(name = "纬度")
    private BigDecimal latitude;
    @Excel(name = "速度")
    private BigDecimal speed;
    @Excel(name = "航向")
    private BigDecimal course;
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用,2=维护")
    private String status;
    @Excel(name = "在线状态", readConverterExp = "0=在线,1=离线")
    private String onlineStatus;
    private String delFlag;

    public Long getBoatId()
    {
        return boatId;
    }

    public void setBoatId(Long boatId)
    {
        this.boatId = boatId;
    }

    public String getBoatCode()
    {
        return boatCode;
    }

    public void setBoatCode(String boatCode)
    {
        this.boatCode = boatCode;
    }

    public String getBoatName()
    {
        return boatName;
    }

    public void setBoatName(String boatName)
    {
        this.boatName = boatName;
    }

    public String getBoatType()
    {
        return boatType;
    }

    public void setBoatType(String boatType)
    {
        this.boatType = boatType;
    }

    public Long getAirportId()
    {
        return airportId;
    }

    public void setAirportId(Long airportId)
    {
        this.airportId = airportId;
    }

    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }

    public BigDecimal getSpeed()
    {
        return speed;
    }

    public void setSpeed(BigDecimal speed)
    {
        this.speed = speed;
    }

    public BigDecimal getCourse()
    {
        return course;
    }

    public void setCourse(BigDecimal course)
    {
        this.course = course;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getOnlineStatus()
    {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus)
    {
        this.onlineStatus = onlineStatus;
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

    public String getShipCode()
    {
        return boatCode;
    }

    public void setShipCode(String shipCode)
    {
        this.boatCode = shipCode;
    }

    public String getShipName()
    {
        return boatName;
    }

    public void setShipName(String shipName)
    {
        this.boatName = shipName;
    }

    public String getShipType()
    {
        return boatType;
    }

    public void setShipType(String shipType)
    {
        this.boatType = shipType;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("boatId", getBoatId())
            .append("boatCode", getBoatCode())
            .append("boatName", getBoatName())
            .append("boatType", getBoatType())
            .append("airportId", getAirportId())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("speed", getSpeed())
            .append("course", getCourse())
            .append("status", getStatus())
            .append("onlineStatus", getOnlineStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
