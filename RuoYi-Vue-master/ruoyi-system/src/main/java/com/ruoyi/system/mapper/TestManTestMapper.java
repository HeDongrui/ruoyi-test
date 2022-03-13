package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.TestManTest;
import com.ruoyi.common.core.domain.entity.SysDictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典表 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface TestManTestMapper
{
    /**
     * 根据条件分页查询字典类型
     * 
     * @param test 字典类型信息
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
     * 通过字典ID删除字典信息
     * 
     * @param dictId 字典ID
     * @return 结果
     */
    public int deleteDictTypeById(Long dictId);

    /**
     * 批量删除字典类型信息
     * 
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    public int deleteDictTypeByIds(Long[] testID);

    /**
     * 新增字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int insertDictType(TestManTest test);

    /**
     * 修改字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    public int updateDictType(TestManTest test);

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dictType 字典类型
     * @return 结果
     */
    public int checkDictTypeUnique(Long testID);
}
