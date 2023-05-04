package com.output.service;

import com.output.entity.News;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;

public interface NewsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewsPage(PageQueryUtil pageUtil);

    /**
     * 添加动态
     *
     * @param news
     * @return
     */
    String saveNews(News news);

    /**
     * 修改动态信息
     *
     * @param news
     * @return
     */
    String updateNews(News news);

    /**
     * 获取动态详情
     *
     * @param id
     * @return
     */
    News getNewsById(Long id);

    /**
     * 删除动态
     *
     * @param id
     * @return
     */
    String deleteNews(Long id);
}
