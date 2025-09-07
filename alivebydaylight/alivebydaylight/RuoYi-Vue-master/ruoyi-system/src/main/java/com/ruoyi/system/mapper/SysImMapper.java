package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysIm;
import com.ruoyi.common.core.domain.entity.SysPoi;

import java.util.List;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface SysImMapper
{
    /**
     * 查询poi信息
     *
     *
     * @return 结果
     */
    public List<SysIm> queryIm();

    public int addIm(SysIm im);
}
