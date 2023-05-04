package com.output.service.impl;

import com.output.common.Exception;
import com.output.common.ServiceResultEnum;
import com.output.dao.NewsMapper;
import com.output.entity.News;
import com.output.service.NewsService;
import com.output.util.OtherUtils;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        news.setNewsTitle(OtherUtils.cleanString(news.getNewsTitle()));
        news.setTag(OtherUtils.cleanString(news.getTag()));
        if (newsMapper.insertSelective(news) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNews(News news) {

        News temp = newsMapper.selectByPrimaryKey(news.getNewsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        news.setNewsTitle(OtherUtils.cleanString(news.getNewsTitle()));
        news.setTag(OtherUtils.cleanString(news.getTag()));
        news.setUpdateTime(new Date());
        if (newsMapper.updateByPrimaryKeySelective(news) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();

    }

    @Override
    public News getNewsById(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null) {
            Exception.fail("news not exist");
        }
        return news;
    }

    @Override
    public String deleteNews(Long id) {
        newsMapper.deleteByPrimaryKey(id);
        return ServiceResultEnum.SUCCESS.getResult();
    }
}
