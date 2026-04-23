package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.UavAirport;

/**
 * 无人机场信息Service接口
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
public interface IUavAirportService 
{
    /**
     * 查询无人机场信息
     * 
     * @param airportId 无人机场信息主键
     * @return 无人机场信息
     */
    public UavAirport selectUavAirportByAirportId(Long airportId);

    /**
     * 查询无人机场信息列表
     * 
     * @param uavAirport 无人机场信息
     * @return 无人机场信息集合
     */
    public List<UavAirport> selectUavAirportList(UavAirport uavAirport);

    /**
     * 新增无人机场信息
     * 
     * @param uavAirport 无人机场信息
     * @return 结果
     */
    public int insertUavAirport(UavAirport uavAirport);

    /**
     * 修改无人机场信息
     * 
     * @param uavAirport 无人机场信息
     * @return 结果
     */
    public int updateUavAirport(UavAirport uavAirport);

    /**
     * 批量删除无人机场信息
     * 
     * @param airportIds 需要删除的无人机场信息主键集合
     * @return 结果
     */
    public int deleteUavAirportByAirportIds(Long[] airportIds);

    /**
     * 删除无人机场信息信息
     * 
     * @param airportId 无人机场信息主键
     * @return 结果
     */
    public int deleteUavAirportByAirportId(Long airportId);
}
