package com.ruoyi.common.core.domain;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;

@Data
public class TestManTest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long testID;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String testName;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String type;


}

