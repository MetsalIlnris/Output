package com.output.service.impl;

import com.output.OutputApplication;
import com.output.controller.vo.ShoppingCartItemVO;
import com.output.dao.ShoppingCartItemMapper;
import com.output.entity.ShoppingCartItem;
import com.output.service.ShoppingCartService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OutputApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ShoppingCartServiceImplTest {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ShoppingCartItemMapper shoppingCartItemMapper;

    ShoppingCartItem shoppingCartItemLocal = new ShoppingCartItem();



    @Test
    void Test_Cart_001() {
        //saveCartItem
        shoppingCartItemLocal.setGoodsId((long) 10003);
        shoppingCartItemLocal.setGoodsCount(4);
        shoppingCartItemLocal.setCartItemId((long) 100);
        shoppingCartItemLocal.setUserId((long) 1);
        shoppingCartService.saveCartItem(shoppingCartItemLocal);
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getGoodsId(),shoppingCartItemLocal.getGoodsId());
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getGoodsCount(),shoppingCartItemLocal.getGoodsCount());
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getUserId(),shoppingCartItemLocal.getUserId());
    }

    @Test
    void Test_Cart_002() {
        //getMyShoppingCartItems
        List<ShoppingCartItemVO> list = shoppingCartService.getMyShoppingCartItems((long) 1);
        ShoppingCartItemVO shoppingCartItemVOcac = new ShoppingCartItemVO();
        for (ShoppingCartItemVO VO : list) {
            if (VO.getCartItemId().equals((long) 100)) {
                shoppingCartItemVOcac = VO;
                break;
            }
        }
        Assert.assertNotNull(shoppingCartItemVOcac);
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getGoodsId(),shoppingCartItemVOcac.getGoodsId());
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getGoodsCount(),shoppingCartItemVOcac.getGoodsCount());


    }

    @Test
    void Test_Cart_003() {
        //updateCartItem
        shoppingCartItemLocal.setGoodsId((long) 10003);
        shoppingCartItemLocal.setGoodsCount(5);
        shoppingCartItemLocal.setCartItemId((long) 100);
        shoppingCartItemLocal.setUserId((long) 1);
        shoppingCartService.updateCartItem(shoppingCartItemLocal);
        Assert.assertEquals(shoppingCartItemMapper.selectByPrimaryKey((long) 100).getGoodsCount(),shoppingCartItemLocal.getGoodsCount());
    }

    @Test
    void Test_Cart_004() {
        //getCartItemById
        shoppingCartItemLocal.setCartItemId((long) 100);
        ShoppingCartItem target = shoppingCartService.getCartItemById(shoppingCartItemLocal.getCartItemId());
        ShoppingCartItem actual = shoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemLocal.getCartItemId());
        Assert.assertEquals(target.getGoodsId(),actual.getGoodsId());
        Assert.assertEquals(target.getGoodsCount(),actual.getGoodsCount());
    }

    @Test
    void Test_Cart_005() {
        //deleteById
        shoppingCartItemLocal.setCartItemId((long) 100);
        shoppingCartService.deleteById(shoppingCartItemLocal.getCartItemId());
        ShoppingCartItem target = shoppingCartService.getCartItemById(shoppingCartItemLocal.getCartItemId());
        Assert.assertNull(target);
    }

    @Test
    void Test_Cart_006(){

    }

}