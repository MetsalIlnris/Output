
package com.output.service.impl;

import com.output.common.Constants;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.ShoppingCartItemVO;
import com.output.dao.GoodsMapper;
import com.output.dao.ShoppingCartItemMapper;
import com.output.entity.Goods;
import com.output.entity.ShoppingCartItem;
import com.output.service.ShoppingCartService;
import com.output.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public String saveCartItem(ShoppingCartItem ShoppingCartItem) {
        ShoppingCartItem temp = shoppingCartItemMapper.selectByUserIdAndGoodsId(ShoppingCartItem.getUserId(), ShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(ShoppingCartItem.getGoodsCount());
            return updateCartItem(temp);
        }
        Goods Goods = goodsMapper.selectByPrimaryKey(ShoppingCartItem.getGoodsId());
        //商品为空
        if (Goods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = shoppingCartItemMapper.selectCountByUserId(ShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (ShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (shoppingCartItemMapper.insertSelective(ShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCartItem(ShoppingCartItem ShoppingCartItem) {
        ShoppingCartItem ShoppingCartItemUpdate = shoppingCartItemMapper.selectByPrimaryKey(ShoppingCartItem.getCartItemId());
        if (ShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (ShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!ShoppingCartItemUpdate.getUserId().equals(ShoppingCartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (ShoppingCartItem.getGoodsCount().equals(ShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        ShoppingCartItemUpdate.setGoodsCount(ShoppingCartItem.getGoodsCount());
        ShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (shoppingCartItemMapper.updateByPrimaryKeySelective(ShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public ShoppingCartItem getCartItemById(Long ShoppingCartItemId) {
        return shoppingCartItemMapper.selectByPrimaryKey(ShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId) {
        ShoppingCartItem ShoppingCartItem = shoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (ShoppingCartItem == null) {
            return false;
        }
        return shoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<ShoppingCartItemVO> getMyShoppingCartItems(Long UserId) {
        List<ShoppingCartItemVO> ShoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> ShoppingCartItems = shoppingCartItemMapper.selectByUserId(UserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(ShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> GoodsIds = ShoppingCartItems.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<Goods> goods = goodsMapper.selectByPrimaryKeys(GoodsIds);
            Map<Long, com.output.entity.Goods> GoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(goods)) {
                GoodsMap = goods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (ShoppingCartItem ShoppingCartItem : ShoppingCartItems) {
                ShoppingCartItemVO ShoppingCartItemVO = new ShoppingCartItemVO();
                BeanUtil.copyProperties(ShoppingCartItem, ShoppingCartItemVO);
                if (GoodsMap.containsKey(ShoppingCartItem.getGoodsId())) {
                    Goods GoodsTemp = GoodsMap.get(ShoppingCartItem.getGoodsId());
                    ShoppingCartItemVO.setGoodsCoverImg(GoodsTemp.getGoodsCoverImg());
                    String goodsName = GoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    ShoppingCartItemVO.setGoodsName(goodsName);
                    ShoppingCartItemVO.setSellingPrice(GoodsTemp.getSellingPrice());
                    ShoppingCartItemVOS.add(ShoppingCartItemVO);
                }
            }
        }
        return ShoppingCartItemVOS;
    }
}
