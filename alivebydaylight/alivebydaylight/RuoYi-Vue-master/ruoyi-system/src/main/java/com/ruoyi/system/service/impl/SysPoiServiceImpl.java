package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.domain.entity.SysPoi;
import com.ruoyi.common.core.domain.entity.SysSearch;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPoiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * poi 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysPoiServiceImpl implements ISysPoiService
{
    private static final Logger log = LoggerFactory.getLogger(SysPoiServiceImpl.class);

    @Autowired
    private SysPoiMapper poiMapper;

    @Autowired
    protected Validator validator;


    /**
     * 新增保存poi信息
     * 
     * @param poi 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPoi(SysPoi poi)
    {
        // 新增poi信息
        int rows = poiMapper.insertPoi(poi);
        return rows;
    }

    /**
     * 查询poi信息
     * @return 结果
     */
    @Override
    public List<SysPoi> queryPoi()
    {
        return poiMapper.queryPoi();
    }

    /**
     * 查询指定类型poi信息
     *
     * @param type 用户信息
     * @return 结果
     */
    @Override
    public List<SysPoi> typePoi(SysSearch search)
    {
        System.out.println(search+"search------");
        return poiMapper.typePoi(search);
    }

    /**
     * 查询指定类型poi信息
     *
     * @param poiId 用户信息
     * @return 结果
     */
    @Override
    public int flagTrue(String poiId)
    {
        List<SysPoi> id= poiMapper.idPoi(poiId);
        String poiID=id.get(0).getPoiId();
        poiMapper.flagAllFalse(poiID);
        return poiMapper.flagTrue(poiId);
    }

    @Override
    public int flagFalse(String poiId)
    {
        return poiMapper.flagFalse(poiId);
    }


}
