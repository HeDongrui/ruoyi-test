package com.ruoyi.web.controller.testMan;

import com.alibaba.druid.util.StringUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.TestManTest;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ITestManTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据字典信息
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/testMan/test/type")
public class TestManController extends BaseController
{
    @Autowired
    private ITestManTestService service;

    @PreAuthorize("@ss.hasPermi('testMan:test:list')")
    @GetMapping("/list")
    public TableDataInfo list(TestManTest test)
    {
        startPage();
        List<TestManTest> list = service.selectDictTypeList(test);
        return getDataTable(list);
    }

    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('testMan:test:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestManTest test)
    {
        List<TestManTest> list = service.selectDictTypeList(test);
        list.forEach(tmp -> {
            tmp.setType(StringUtils.equals(tmp.getType(), "0") ? "正常" : "停止");
        });
        ExcelUtil<TestManTest> util = new ExcelUtil<TestManTest>(TestManTest.class);
        util.exportExcel(response, list, "测试导出");
    }

    /**
     * 查询字典类型详细
     */
    @PreAuthorize("@ss.hasPermi('testMan:test:query')")
    @GetMapping(value = "/{testId}")
    public AjaxResult getInfo(@PathVariable Long testId)
    {
        return AjaxResult.success(service.selectDictTypeById(testId));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize("@ss.hasPermi('testMan:test:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
        public AjaxResult add(@Validated @RequestBody TestManTest test)
    {
        if (UserConstants.NOT_UNIQUE.equals(service.checkDictTypeUnique(test)))
        {
            return AjaxResult.error("新增字典'" + test.getTestName() + "'失败，字典类型已存在");
        }
        test.setCreateBy(getUsername());
        return toAjax(service.insertDictType(test));
    }

    /**
     * 修改字典类型
     */
    @PreAuthorize("@ss.hasPermi('testMan:test:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TestManTest test)
    {
//        if (UserConstants.NOT_UNIQUE.equals(service.checkDictTypeUnique(test)))
//        {
//            return AjaxResult.error("修改字典'" + test.getTestName() + "'失败，字典类型已存在");
//        }
        test.setUpdateBy(getUsername());
        return toAjax(service.updateDictType(test));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize("@ss.hasPermi('testMan:test:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{testID}")
    public AjaxResult remove(@PathVariable Long[] testID)
    {
        service.deleteDictTypeByIds(testID);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    @PreAuthorize("@ss.hasPermi('testMan:test:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        service.resetDictCache();
        return AjaxResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<SysDictType> dictTypes = service.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<TestManTest> util = new ExcelUtil<TestManTest>(TestManTest.class);
        util.importTemplateExcel(response, "测试数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('testMan:test:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<TestManTest> util = new ExcelUtil<TestManTest>(TestManTest.class);
        List<TestManTest> testManTests = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = service.importUser(testManTests, updateSupport, operName);
        return AjaxResult.success(message);
    }
}
