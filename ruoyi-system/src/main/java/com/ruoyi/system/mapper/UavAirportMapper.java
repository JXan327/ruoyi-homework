package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UavAirport;

/**
 * 无人机场信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
public interface UavAirportMapper 
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
     * 删除无人机场信息
     * 
     * @param airportId 无人机场信息主键
     * @return 结果
     */
    public int deleteUavAirportByAirportId(Long airportId);

    /**
     * 批量删除无人机场信息
     * 
     * @param airportIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUavAirportByAirportIds(Long[] airportIds);
}
