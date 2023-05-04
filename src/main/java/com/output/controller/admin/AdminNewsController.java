package com.output.controller.admin;

import com.output.common.ServiceResultEnum;
import com.output.entity.News;
import com.output.service.NewsService;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminNewsController {
    
    @Autowired
    NewsService newsService;

    /**
     * 添加
     */
    @RequestMapping(value = "/news/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody News News) {
        if (!StringUtils.hasText(News.getNewsTitle())
                || !StringUtils.hasText(News.getTag())
                || !StringUtils.hasText(News.getNewsCoverImg())
                || !StringUtils.hasText(News.getNewsContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newsService.saveNews(News);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/news/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody News News) {
        if (Objects.isNull(News.getNewsId())
                || !StringUtils.hasText(News.getTag())
                || !StringUtils.hasText(News.getNewsCoverImg())
                || !StringUtils.hasText(News.getNewsContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newsService.updateNews(News);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/news/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam Long newsId){
        newsService.deleteNews(newsId);
        return ResultGenerator.genSuccessResult();
    }
}
