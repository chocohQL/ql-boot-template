package com.chocoh.ql.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author chocoh
 * @createTime 2024-04-10 17:38
 */
public class PageUtil {
    public static <T> IPage<T> getPageBean(List<T> records, int page, int pageSize) {
        if (CollUtil.isEmpty(records) || page <= 0) {
            return new Page<>();
        }
        int startNumber =  (page - 1) * pageSize;
        int totalNumber = records.size();
        if (totalNumber < startNumber) {
            return new Page<>();
        }
        Page<T> pageBean = new Page<>(page, pageSize, totalNumber);
        pageBean.setRecords(records.subList(startNumber, Math.min(startNumber + pageSize, totalNumber)));
        pageBean.setPages((totalNumber % page == 0) ? (totalNumber / pageSize) : (totalNumber / pageSize + 1));
        return pageBean;
    }
}
