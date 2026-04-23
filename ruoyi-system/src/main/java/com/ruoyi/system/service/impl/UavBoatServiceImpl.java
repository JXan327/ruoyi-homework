package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UavBoatMapper;
import com.ruoyi.system.domain.UavBoat;
import com.ruoyi.system.service.IUavBoatService;
import com.ruoyi.common.utils.DateUtils;

/**
 * 无人船信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
@Service
public class UavBoatServiceImpl implements IUavBoatService 
{
    @Autowired
    private UavBoatMapper uavBoatMapper;

    /**
     * 查询无人船信息
     * 
     * @param boatId 无人船信息主键
     * @return 无人船信息
     */
    @Override
    public UavBoat selectUavBoatByBoatId(Long boatId)
    {
        return uavBoatMapper.selectUavBoatByBoatId(boatId);
    }

    /**
     * 查询无人船信息列表
     * 
     * @param uavBoat 无人船信息
     * @return 无人船信息集合
     */
    @Override
    public List<UavBoat> selectUavBoatList(UavBoat uavBoat)
    {
        return uavBoatMapper.selectUavBoatList(uavBoat);
    }

    /**
     * 新增无人船信息
     * 
     * @param uavBoat 无人船信息
     * @return 结果
     */
    @Override
    public int insertUavBoat(UavBoat uavBoat)
    {
        uavBoat.setCreateTime(DateUtils.getNowDate());
        return uavBoatMapper.insertUavBoat(uavBoat);
    }

    /**
     * 修改无人船信息
     * 
     * @param uavBoat 无人船信息
     * @return 结果
     */
    @Override
    public int updateUavBoat(UavBoat uavBoat)
    {
        uavBoat.setUpdateTime(DateUtils.getNowDate());
        return uavBoatMapper.updateUavBoat(uavBoat);
    }

    /**
     * 批量删除无人船信息
     * 
     * @param boatIds 需要删除的无人船信息主键集合
     * @return 结果
     */
    @Override
    public int deleteUavBoatByBoatIds(Long[] boatIds)
    {
        return uavBoatMapper.deleteUavBoatByBoatIds(boatIds);
    }

    /**
     * 删除无人船信息信息
     * 
     * @param boatId 无人船信息主键
     * @return 结果
     */
    @Override
    public int deleteUavBoatByBoatId(Long boatId)
    {
        return uavBoatMapper.deleteUavBoatByBoatId(boatId);
    }
}