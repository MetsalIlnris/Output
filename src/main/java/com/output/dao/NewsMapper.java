package com.output.dao;

import com.output.entity.News;
import com.output.util.PageQueryUtil;

import java.util.List;

public interface NewsMapper {
    int deleteByPrimaryKey(Long newsId);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long newsId);

    int updateByPrimaryKey(News record);

    int updateByPrimaryKeySelective(News record);

    int getTotalNews(PageQueryUtil pageUtil);

    List<News> findNewsList(PageQueryUtil pageUtil);
}
