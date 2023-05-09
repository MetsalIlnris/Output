
package com.output.dao;

import com.output.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(ShoppingCartItem record);

    int insertSelective(ShoppingCartItem record);

    ShoppingCartItem selectByPrimaryKey(Long cartItemId);

    ShoppingCartItem selectByUserIdAndGoodsId(@Param("UserId") Long UserId, @Param("goodsId") Long goodsId);

    List<ShoppingCartItem> selectByUserId(@Param("UserId") Long UserId, @Param("number") int number);

    int selectCountByUserId(Long UserId);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int updateByPrimaryKey(ShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}