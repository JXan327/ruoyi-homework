package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UavBoat;

/**
 * 无人船信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
public interface UavBoatMapper 
{
    /**
     * 查询无人船信息
     * 
     * @param boatId 无人船信息主键
     * @return 无人船信息
     */
    public UavBoat selectUavBoatByBoatId(Long boatId);

    /**
     * 查询无人船信息列表
     * 
     * @param uavBoat 无人船信息
     * @return 无人船信息集合
     */
    public List<UavBoat> selectUavBoatList(UavBoat uavBoat);

    /**
     * 新增无人船信息
     * 
     * @param uavBoat 无人船信息
     * @return 结果
     */
    public int insertUavBoat(UavBoat uavBoat);

    /**
     * 修改无人船信息
     * 
     * @param uavBoat 无人船信息
     * @return 结果
     */
    public int updateUavBoat(UavBoat uavBoat);

    /**
     * 删除无人船信息
     * 
     * @param boatId 无人船信息主键
     * @return 结果
     */
    public int deleteUavBoatByBoatId(Long boatId);

    /**
     * 批量删除无人船信息
     * 
     * @param boatIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUavBoatByBoatIds(Long[] boatIds);
}