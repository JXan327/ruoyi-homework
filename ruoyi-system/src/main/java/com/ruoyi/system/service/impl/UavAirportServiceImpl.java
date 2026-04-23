package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UavAirportMapper;
import com.ruoyi.system.domain.UavAirport;
import com.ruoyi.system.service.IUavAirportService;

/**
 * 无人机场信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-16
 */
@Service
public class UavAirportServiceImpl implements IUavAirportService 
{
    @Autowired
    private UavAirportMapper uavAirportMapper;

    /**
     * 查询无人机场信息
     * 
     * @param airportId 无人机场信息主键
     * @return 无人机场信息
     */
    @Override
    public UavAirport selectUavAirportByAirportId(Long airportId)
    {
        return uavAirportMapper.selectUavAirportByAirportId(airportId);
    }

    /**
     * 查询无人机场信息列表
     * 
     * @param uavAirport 无人机场信息
     * @return 无人机场信息
     */
    @Override
    public List<UavAirport> selectUavAirportList(UavAirport uavAirport)
    {
        return uavAirportMapper.selectUavAirportList(uavAirport);
    }

    /**
     * 新增无人机场信息
     * 
     * @param uavAirport 无人机场信息
     * @return 结果
     */
    @Override
    public int insertUavAirport(UavAirport uavAirport)
    {
        uavAirport.setCreateTime(DateUtils.getNowDate());
        return uavAirportMapper.insertUavAirport(uavAirport);
    }

    /**
     * 修改无人机场信息
     * 
     * @param uavAirport 无人机场信息
     * @return 结果
     */
    @Override
    public int updateUavAirport(UavAirport uavAirport)
    {
        uavAirport.setUpdateTime(DateUtils.getNowDate());
        return uavAirportMapper.updateUavAirport(uavAirport);
    }

    /**
     * 批量删除无人机场信息
     * 
     * @param airportIds 需要删除的无人机场信息主键
     * @return 结果
     */
    @Override
    public int deleteUavAirportByAirportIds(Long[] airportIds)
    {
        return uavAirportMapper.deleteUavAirportByAirportIds(airportIds);
    }

    /**
     * 删除无人机场信息信息
     * 
     * @param airportId 无人机场信息主键
     * @return 结果
     */
    @Override
    public int deleteUavAirportByAirportId(Long airportId)
    {
        return uavAirportMapper.deleteUavAirportByAirportId(airportId);
    }
}
