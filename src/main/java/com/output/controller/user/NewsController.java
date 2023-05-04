package com.output.controller.user;

import com.output.common.Constants;
import com.output.common.Exception;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.GoodsDetailVO;
import com.output.entity.Goods;
import com.output.entity.News;
import com.output.service.NewsService;
import com.output.util.BeanUtil;
import com.output.util.PageQueryUtil;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class NewsController {

    @Autowired
    private  NewsService newsService;

    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newsService.getNewsPage(pageUtil));
    }

    @GetMapping("/news/detail/{newsId}")
    @ResponseBody
    public Result detailPage(@PathVariable("newsId") Long newsId, HttpServletRequest request) {
        if (newsId < 1) {
            Exception.fail("参数异常");
        }
        News news = newsService.getNewsById(newsId);
        return ResultGenerator.genSuccessResult(news);
    }
}
