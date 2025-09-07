package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysPoi;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysSearch;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface SysPoiMapper
{
    /**
     * 新增用户信息
     * 
     * @param poi 用户信息
     * @return 结果
     */
    public int insertPoi(SysPoi poi);

    /**
     * 查询poi信息
     *
     *
     * @return 结果
     */
    public List<SysPoi> queryPoi();

    public List<SysPoi> idPoi(String id);

    /**
     * 查询指定类别poi信息
     *
     * @param search 查询信息
     *
     * @return 结果
     */
    public List<SysPoi> typePoi(SysSearch search);

    public int flagTrue(String id);

    public int flagAllFalse(String poiId);

    public int flagFalse(String id);
}
