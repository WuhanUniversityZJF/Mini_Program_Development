package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysPoi;
import com.ruoyi.common.core.domain.entity.SysSearch;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface ISysPoiService
{

    /**
     * 新增poi信息
     *
     * @param poi poi信息
     * @return 结果
     */
    public int insertPoi(SysPoi poi);

    /**
     * 查询poi信息
     *
     * @return 结果
     */
    public List<SysPoi> queryPoi();

    /**
     * 查询指定类别poi信息
     *
     * @return 结果
     */
    public List<SysPoi> typePoi(SysSearch search);

    /**
     * flagTrue
     *
     * @param type poi信息
     * @return 结果
     */
    public int flagTrue(String type);

    public int flagFalse(String type);
}
