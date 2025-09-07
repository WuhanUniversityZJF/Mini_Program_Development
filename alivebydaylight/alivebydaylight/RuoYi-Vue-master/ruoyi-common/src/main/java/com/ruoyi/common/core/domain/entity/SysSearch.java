package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 部门表 sys_dept
 *
 * @author ruoyi
 */
public class SysSearch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public String value;
    public String type;

    private SysSearch() {
    }

    public static SysSearch createSysSearch() {
        return new SysSearch();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //建造者模式
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("value",getValue())
                .append("type", getType())
                .toString();
    }
}
