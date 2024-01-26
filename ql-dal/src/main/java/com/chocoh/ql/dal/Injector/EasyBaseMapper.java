package com.chocoh.ql.dal.Injector;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.Collection;

/**
 * @author chocoh
 */
public interface EasyBaseMapper <T> extends BaseMapper<T> {
    /**
     * 批量插入
     * @param entityList 实体类计划的
     * @return 插入条数
     */
    int insertBatchSomeColumn(Collection<T> entityList);
}
