package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.TestManTest;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;

import java.util.List;

/**
 * 字典 业务层
 * 
 * @author ruoyi
 */
public interface ITestManTestService
{
    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public List<TestManTest> selectDictTypeList(TestManTest test);

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    public TestManTest selectDictTypeById(Long testId);

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType 字典类型
     * @return 字典类型
     */
    public SysDictType selectDictTypeByType(String dictType);

    /**
     * 批量删除字典信息
     * 
     * @param dictIds 需要删除的字典ID
     */
    public void deleteDictTypeByIds(Long[] testIDS);

    /**
     * 加载字典缓存数据
     */
    public void loadingDictCache();

    /**
     * 清空字典缓存数据
     */
    public void clearDictCache();

    /**
     * 重置字典缓存数据
     */
    public void resetDictCache();

    /**
     * 新增保存字典类型信息
     * 
     * @param test 字典类型信息
     * @return 结果
     */
    public int insertDictType(TestManTest test);

    /**
     * 修改保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(TestManTest test);

    /**
     * 校验字典类型称是否唯一
     * 
     * @param test 字典类型
     * @return 结果
     */
    public String checkDictTypeUnique(TestManTest test);

    String importUser(List<TestManTest> testManTests, boolean updateSupport, String operName);
}
