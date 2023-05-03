
package com.output.controller.user;

import com.output.common.Constants;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.GoodsDetailVO;
import com.output.controller.vo.SearchPageCategoryVO;
import com.output.entity.Goods;
import com.output.service.CategoryService;
import com.output.service.GoodsService;
import com.output.util.BeanUtil;
import com.output.util.PageQueryUtil;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.output.common.Exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page")) || ObjectUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsService.getGoodsPage(pageUtil));
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ResponseBody
    public Result detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            Exception.fail("参数异常");
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            Exception.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

    @GetMapping({"/search"})
    @ResponseBody
    public Result searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && StringUtils.hasText((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsService.searchGoods(pageUtil));
    }
}
