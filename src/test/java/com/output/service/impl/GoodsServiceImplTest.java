package com.output.service.impl;

import com.output.OutputApplication;
import com.output.dao.GoodsMapper;
import com.output.entity.Goods;
import com.output.service.GoodsService;
import com.output.util.PageQueryUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OutputApplication.class)
class GoodsServiceImplTest {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    void getGoodsPage() {
        Map<String, Object> params = new HashMap<>();
        params.put("limit",(long) 1);
        params.put("page",(long) 1);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        goodsService.getGoodsPage(pageQueryUtil);
    }

    @Test
    void saveGoods() {
        Goods goods = new Goods();
        goods.setGoodsName("测试用键盘");
        goods.setGoodsCategoryId((long)10);
        goods.setGoodsDetailContent("<p>this is a test keyboard</p>");
        goodsService.saveGoods(goods);
        Assert.assertEquals(goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsDetailContent(),"<p>this is a test keyboard</p>");
    }

    @Test
    void updateGoods() {
        Goods goods = new Goods();
        goods.setGoodsName("测试用键盘");
        goods.setGoodsId(goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsId());
        goods.setGoodsCategoryId((long)10);
        goods.setGoodsDetailContent("<p>this is a test keyboard</p>");
        goodsService.updateGoods(goods);
        Assert.assertEquals(goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsDetailContent(),"<p>this is a test keyboard</p>");
    }

    @Test
    void getGoodsById() {
        Goods goods = goodsService.getGoodsById(goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsId());
        Assert.assertEquals(goods.getGoodsName(),goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsName());
        Assert.assertEquals(goods.getGoodsIntro(),goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsIntro());
        Assert.assertEquals(goods.getGoodsCoverImg(),goodsMapper.selectByCategoryIdAndName("测试用键盘",(long)10).getGoodsCoverImg());

    }

    @Test
    void searchGoods() {
        Map<String, Object> params = new HashMap<>();
        params.put("limit",(long) 1);
        params.put("page",(long) 1);
        params.put("keyword","output");
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        Assert.assertThat(goodsService.searchGoods(pageQueryUtil).toString(),containsString("output"));

    }
}