package com.ruoyi.system.service.impl;


import com.ruoyi.common.core.domain.entity.SysIm;
import com.ruoyi.system.mapper.SysImMapper;
import com.ruoyi.system.service.ISysImService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.List;

/**
 * poi 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysImServiceImpl implements ISysImService
{
    private static final Logger log = LoggerFactory.getLogger(SysImServiceImpl.class);

    @Autowired
    private SysImMapper imMapper;


    @Autowired
    protected Validator validator;

    /**
     * 查询poi信息
     * @return 结果
     */
    @Override
    public List<SysIm> queryIm()
    {
        return imMapper.queryIm();
    }

    @Override
    public int addIm(SysIm im){return imMapper.addIm(im);}

}
