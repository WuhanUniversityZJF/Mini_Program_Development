package com.ruoyi.web.controller.im;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysIm;
import com.ruoyi.common.core.domain.entity.SysPoi;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ISysImService;
import com.ruoyi.system.service.ISysPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/im")
public class SysImController extends BaseController
{
    @Autowired
    private ISysImService imService;

    /**
     * 查询消息
     */
    @Log(title = "Im", businessType = BusinessType.INSERT)
    @GetMapping("queryIm")
    public AjaxResult query()
    {
        List<SysIm> pois = imService.queryIm();
        System.out.println(pois);
        return success(pois);
    }

    /**
     * 新增消息
     */
    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @PostMapping("addIm")
    public AjaxResult add(@Validated @RequestBody SysIm im)
    {
        System.out.println("addIm---------");
        System.out.println(im);
        return toAjax(imService.addIm(im));
    }
}
