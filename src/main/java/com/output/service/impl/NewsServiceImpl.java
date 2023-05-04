package com.output.service.impl;

import com.output.common.Exception;
import com.output.dao.NewsMapper;
import com.output.entity.News;
import com.output.service.NewsService;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsMapper newsMapper;
    
    @Override
    public PageResult getNewsPage(PageQueryUtil pageUtil) {
        List<News> newsList = newsMapper.findNewsList(pageUtil);
        int total = newsMapper.getTotalNews(pageUtil);
        PageResult pageResult = new PageResult(newsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveNews(News news) {
        return null;
    }

    @Override
    public String updateNews(News news) {
        return null;
    }

    @Override
    public News getNewsById(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null) {
            Exception.fail("news not exist");
        }
        return news;
    }
}
