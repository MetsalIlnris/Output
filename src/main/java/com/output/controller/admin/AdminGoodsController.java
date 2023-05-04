package com.output.controller.admin;

import com.output.common.Constants;
import com.output.common.ServiceResultEnum;
import com.output.entity.Goods;
import com.output.service.CategoryService;
import com.output.service.GoodsService;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class AdminGoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Goods Goods) {
        if (!StringUtils.hasText(Goods.getGoodsName())
                || !StringUtils.hasText(Goods.getGoodsIntro())
                || !StringUtils.hasText(Goods.getTag())
                || Objects.isNull(Goods.getOriginalPrice())
                || Objects.isNull(Goods.getGoodsCategoryId())
                || Objects.isNull(Goods.getSellingPrice())
                || Objects.isNull(Goods.getStockNum())
                || Objects.isNull(Goods.getGoodsSellStatus())
                || !StringUtils.hasText(Goods.getGoodsCoverImg())
                || !StringUtils.hasText(Goods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = goodsService.saveGoods(Goods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Goods Goods) {
        if (Objects.isNull(Goods.getGoodsId())
                || !StringUtils.hasText(Goods.getGoodsName())
                || !StringUtils.hasText(Goods.getGoodsIntro())
                || !StringUtils.hasText(Goods.getTag())
                || Objects.isNull(Goods.getOriginalPrice())
                || Objects.isNull(Goods.getSellingPrice())
                || Objects.isNull(Goods.getGoodsCategoryId())
                || Objects.isNull(Goods.getStockNum())
                || Objects.isNull(Goods.getGoodsSellStatus())
                || !StringUtils.hasText(Goods.getGoodsCoverImg())
                || !StringUtils.hasText(Goods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = goodsService.updateGoods(Goods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        Goods goods = goodsService.getGoodsById(id);
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (goodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}