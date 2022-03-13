package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.TestManTest;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.mapper.TestManTestMapper;
import com.ruoyi.system.service.ITestManTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.util.List;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TestManTestServiceImpl implements ITestManTestService
{

    private static final Logger log = LoggerFactory.getLogger(TestManTestServiceImpl.class);
    @Autowired
    private TestManTestMapper mapper;

    @Autowired
    protected Validator validator;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<TestManTest> selectDictTypeList(TestManTest test)
    {
        return mapper.selectDictTypeList(test);
    }

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll()
    {
        return mapper.selectDictTypeAll();
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return null;
    }


    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public TestManTest selectDictTypeById(Long testId)
    {
        return mapper.selectDictTypeById(testId);
    }

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType)
    {
        return mapper.selectDictTypeByType(dictType);
    }

    @Override
    public void deleteDictTypeByIds(Long[] testIDS)
    {
       mapper.deleteDictTypeByIds(testIDS);
    }

    @Override
    public void loadingDictCache() {

    }


    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache()
    {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache()
    {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     * 
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(TestManTest test)
    {
        int row = mapper.insertDictType(test);

        return row;
    }

    @Override
    @Transactional
    public int updateDictType(TestManTest test)
    {
        int row = mapper.updateDictType(test);

        return row;
    }


    /**
     * 校验字典类型称是否唯一
     * 
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(TestManTest test)
    {
        int count = mapper.checkDictTypeUnique(test.getTestID());
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    @Override
    public String importUser(List<TestManTest> testManTests, boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(testManTests) || testManTests.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TestManTest tmp : testManTests)
        {
            try
            {
                // 验证是否存在这个用户
                TestManTest test = mapper.selectDictTypeById(tmp.getTestID());
                if (StringUtils.isNull(test))
                {
                    BeanValidators.validateWithException(validator, tmp);
                    tmp.setCreateBy(operName);
                    this.insertDictType(tmp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + tmp.getTestName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, tmp);
                    tmp.setUpdateBy(operName);
                    this.updateDictType(tmp);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + tmp.getTestName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + tmp.getTestName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + tmp.getTestName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
