
package com.output.controller;

import com.output.common.Constants;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.GoodsDetailVO;
import com.output.entity.MallGoods;
import com.output.service.GoodsService;
import com.output.util.BeanUtil;
import com.output.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.output.common.Exception;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/goods")
    @ResponseBody
    public String goods(){
        return("yes");
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
