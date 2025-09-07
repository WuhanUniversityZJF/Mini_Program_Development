package com.ruoyi.web.controller.poi;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.*;
import com.ruoyi.system.service.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/poi")
public class SysPoiController extends BaseController
{
    @Autowired
    private ISysPoiService poiService;

    /**
     * 新增poi
     */
    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @PostMapping("addPoi")
    public AjaxResult add(@Validated @RequestBody SysPoi poi)
    {
        System.out.println("addpoi---------");
        System.out.println(poi);
        return toAjax(poiService.insertPoi(poi));
    }

    /**
     * 查询poi
     */
    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @GetMapping("queryPoi")
    public AjaxResult query()
    {
        List<SysPoi> pois = poiService.queryPoi();
        System.out.println(pois);
        return success(pois);
    }

    /**
     * 查询类型poi
     */
    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @PostMapping("typePoi")
    public AjaxResult type(@RequestBody SysSearch search)
    {
        List<SysPoi> pois = poiService.typePoi(search);
        System.out.println(pois);
        return success(pois);
    }


    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @PostMapping("flagTrue")
    public AjaxResult flagTrue(@RequestBody String poiId)
    {
        int pois = poiService.flagTrue(poiId);
        System.out.println(pois);
        return success(pois);
    }

    @Log(title = "Poi", businessType = BusinessType.INSERT)
    @PostMapping("flagFalse")
    public AjaxResult flagFalse(@RequestBody String poiId)
    {
        int pois = poiService.flagFalse(poiId);
        System.out.println(pois+"FlagFalse");
        return success(pois);
    }

}
