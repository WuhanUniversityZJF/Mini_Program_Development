package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysIm;
import com.ruoyi.common.core.domain.entity.SysPoi;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface ISysImService
{
    public List<SysIm> queryIm();

    public int addIm(SysIm im);

}
