package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SimScene;

/**
 * 仿真场景信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
public interface SimSceneMapper 
{
    /**
     * 查询仿真场景信息
     * 
     * @param sceneId 仿真场景信息主键
     * @return 仿真场景信息
     */
    public SimScene selectSimSceneBySceneId(Long sceneId);

    /**
     * 查询仿真场景信息列表
     * 
     * @param simScene 仿真场景信息
     * @return 仿真场景信息集合
     */
    public List<SimScene> selectSimSceneList(SimScene simScene);

    /**
     * 新增仿真场景信息
     * 
     * @param simScene 仿真场景信息
     * @return 结果
     */
    public int insertSimScene(SimScene simScene);

    /**
     * 修改仿真场景信息
     * 
     * @param simScene 仿真场景信息
     * @return 结果
     */
    public int updateSimScene(SimScene simScene);

    /**
     * 删除仿真场景信息
     * 
     * @param sceneId 仿真场景信息主键
     * @return 结果
     */
    public int deleteSimSceneBySceneId(Long sceneId);

    /**
     * 批量删除仿真场景信息
     * 
     * @param sceneIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSimSceneBySceneIds(Long[] sceneIds);
}