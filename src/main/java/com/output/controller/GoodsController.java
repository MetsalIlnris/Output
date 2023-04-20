
package com.output.controller;

import com.output.common.Constants;
import com.output.common.IndexConfigTypeEnum;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.GoodsDetailVO;
import com.output.entity.MallGoods;
import com.output.service.GoodsService;
import com.output.util.BeanUtil;
import com.output.util.PageQueryUtil;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.output.common.Exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;

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
    public GoodsDetailVO detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            Exception.fail("参数异常");
        }
        MallGoods goods = goodsService.getGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            Exception.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        Result<GoodsDetailVO> res=new Result<>(1,"success");
        res.setData(goodsDetailVO);
        return goodsDetailVO;
    }

}
