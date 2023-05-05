package com.output.service.impl;

import com.output.OutputApplication;
import com.output.dao.NewsMapper;
import com.output.dao.NewsMapper;
import com.output.entity.News;
import com.output.entity.News;
import com.output.service.NewsService;
import com.output.service.NewsService;
import com.output.util.PageQueryUtil;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OutputApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NewsServiceImplTest {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsMapper newsMapper;

    @Test
    void Test_News_001() {
        //getNewsPage
        Map<String, Object> params = new HashMap<>();
        params.put("limit",(long) 1);
        params.put("page",(long) 1);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        newsService.getNewsPage(pageQueryUtil);
    }

    @Test
    void Test_News_002() {
        //saveNews
        News news = new News();
        news.setNewsTitle("测试用发帖");
        news.setNewsId((long) 19999);
        news.setNewsContent("<p>this is a test news</p>");
        newsService.saveNews(news);
        Assert.assertEquals(newsMapper.selectByPrimaryKey((long) 19999).getNewsContent(),"<p>this is a test news</p>");
    }

    @Test
    void Test_News_003() {
        //updateNews
        News news = new News();
        news.setNewsTitle("测试用发帖");
        news.setNewsId((long) 19999);
        news.setNewsContent("<p>this is a test updated news</p>");
        newsService.updateNews(news);
        Assert.assertEquals(newsMapper.selectByPrimaryKey((long) 19999).getNewsContent(),"<p>this is a test updated news</p>");
    }

    @Test
    void Test_News_004() {
        //getNewsById
        News news = newsService.getNewsById((long) 19999);
        Assert.assertEquals(newsMapper.selectByPrimaryKey((long) 19999).getNewsTitle(),news.getNewsTitle());
        Assert.assertEquals(newsMapper.selectByPrimaryKey((long) 19999).getNewsContent(),news.getNewsContent());
    }

    @Test
    void Test_News_005() {
        //deleteNews
        newsService.deleteNews((long) 19999);
        Assert.assertNull(newsMapper.selectByPrimaryKey((long) 19999));
    }
}