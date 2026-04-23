package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 无人机场信息对象 uav_airport
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
public class UavAirport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 无人机场ID */
    private Long airportId;

    /** 机场编号 */
    @Excel(name = "机场编号")
    private String airportCode;

    /** 机场名称 */
    @Excel(name = "机场名称")
    private String airportName;

    /** 机场类型（1固定式 2移动式） */
    @Excel(name = "机场类型", readConverterExp = "1=固定式,2=移动式")
    private String airportType;

    /** 省 */
    @Excel(name = "省")
    private String province;

    /** 市 */
    @Excel(name = "市")
    private String city;

    /** 区/县 */
    @Excel(name = "区/县")
    private String district;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /** 海拔高度(米) */
    @Excel(name = "海拔高度(米)")
    private BigDecimal altitude;

    /** 覆盖半径(米) */
    @Excel(name = "覆盖半径(米)")
    private BigDecimal coverRadius;

    /** 最大容纳无人机数量 */
    @Excel(name = "最大容纳无人机数量")
    private Long maxUavCount;

    /** 负责人 */
    @Excel(name = "负责人")
    private String managerName;

    /** 负责人电话 */
    @Excel(name = "负责人电话")
    private String managerPhone;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 在线状态（0在线 1离线） */
    @Excel(name = "在线状态", readConverterExp = "0=在线,1=离线")
    private String onlineStatus;

    /** 删除标志（0存在 2删除） */
    private String delFlag;

    public void setAirportId(Long airportId) 
    {
        this.airportId = airportId;
    }

    public Long getAirportId() 
    {
        return airportId;
    }

    public void setAirportCode(String airportCode) 
    {
        this.airportCode = airportCode;
    }

    public String getAirportCode() 
    {
        return airportCode;
    }

    public void setAirportName(String airportName) 
    {
        this.airportName = airportName;
    }

    public String getAirportName() 
    {
        return airportName;
    }

    public void setAirportType(String airportType) 
    {
        this.airportType = airportType;
    }

    public String getAirportType() 
    {
        return airportType;
    }

    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }

    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }

    public void setDistrict(String district) 
    {
        this.district = district;
    }

    public String getDistrict() 
    {
        return district;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }

    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }

    public void setAltitude(BigDecimal altitude) 
    {
        this.altitude = altitude;
    }

    public BigDecimal getAltitude() 
    {
        return altitude;
    }

    public void setCoverRadius(BigDecimal coverRadius) 
    {
        this.coverRadius = coverRadius;
    }

    public BigDecimal getCoverRadius() 
    {
        return coverRadius;
    }

    public void setMaxUavCount(Long maxUavCount) 
    {
        this.maxUavCount = maxUavCount;
    }

    public Long getMaxUavCount() 
    {
        return maxUavCount;
    }

    public void setManagerName(String managerName) 
    {
        this.managerName = managerName;
    }

    public String getManagerName() 
    {
        return managerName;
    }

    public void setManagerPhone(String managerPhone) 
    {
        this.managerPhone = managerPhone;
    }

    public String getManagerPhone() 
    {
        return managerPhone;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setOnlineStatus(String onlineStatus) 
    {
        this.onlineStatus = onlineStatus;
    }

    public String getOnlineStatus() 
    {
        return onlineStatus;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("airportId", getAirportId())
            .append("airportCode", getAirportCode())
            .append("airportName", getAirportName())
            .append("airportType", getAirportType())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .append("address", getAddress())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("altitude", getAltitude())
            .append("coverRadius", getCoverRadius())
            .append("maxUavCount", getMaxUavCount())
            .append("managerName", getManagerName())
            .append("managerPhone", getManagerPhone())
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
