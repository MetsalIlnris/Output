/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.output.service;

import com.output.controller.vo.ShoppingCartItemVO;
import com.output.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param ShoppingCartItem
     * @return
     */
    String saveCartItem(ShoppingCartItem ShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param ShoppingCartItem
     * @return
     */
    String updateCartItem(ShoppingCartItem ShoppingCartItem);

    /**
     * 获取购物项详情
     *
     * @param ShoppingCartItemId
     * @return
     */
    ShoppingCartItem getCartItemById(Long ShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param UserId
     * @return
     */
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long UserId);
}
