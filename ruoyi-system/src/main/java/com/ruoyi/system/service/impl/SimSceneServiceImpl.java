package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SimSceneMapper;
import com.ruoyi.system.domain.SimScene;
import com.ruoyi.system.service.ISimSceneService;
import com.ruoyi.common.utils.DateUtils;

/**
 * 仿真场景信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-23
 */
@Service
public class SimSceneServiceImpl implements ISimSceneService 
{
    @Autowired
    private SimSceneMapper simSceneMapper;

    /**
     * 查询仿真场景信息
     * 
     * @param sceneId 仿真场景信息主键
     * @return 仿真场景信息
     */
    @Override
    public SimScene selectSimSceneBySceneId(Long sceneId)
    {
        return simSceneMapper.selectSimSceneBySceneId(sceneId);
    }

    /**
     * 查询仿真场景信息列表
     * 
     * @param simScene 仿真场景信息
     * @return 仿真场景信息集合
     */
    @Override
    public List<SimScene> selectSimSceneList(SimScene simScene)
    {
        return simSceneMapper.selectSimSceneList(simScene);
    }

    /**
     * 新增仿真场景信息
     * 
     * @param simScene 仿真场景信息
     * @return 结果
     */
    @Override
    public int insertSimScene(SimScene simScene)
    {
        simScene.setCreateTime(DateUtils.getNowDate());
        return simSceneMapper.insertSimScene(simScene);
    }

    /**
     * 修改仿真场景信息
     * 
     * @param simScene 仿真场景信息
     * @return 结果
     */
    @Override
    public int updateSimScene(SimScene simScene)
    {
        simScene.setUpdateTime(DateUtils.getNowDate());
        return simSceneMapper.updateSimScene(simScene);
    }

    /**
     * 批量删除仿真场景信息
     * 
     * @param sceneIds 需要删除的仿真场景信息主键集合
     * @return 结果
     */
    @Override
    public int deleteSimSceneBySceneIds(Long[] sceneIds)
    {
        return simSceneMapper.deleteSimSceneBySceneIds(sceneIds);
    }

    /**
     * 删除仿真场景信息信息
     * 
     * @param sceneId 仿真场景信息主键
     * @return 结果
     */
    @Override
    public int deleteSimSceneBySceneId(Long sceneId)
    {
        return simSceneMapper.deleteSimSceneBySceneId(sceneId);
    }
}