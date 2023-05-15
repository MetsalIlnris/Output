
package com.output.dao;

import com.output.entity.Goods;
import com.output.entity.StockNumDTO;
import com.output.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods goodsRecord);

    int insertSelective(Goods goodsRecord);

    Goods selectByPrimaryKey(Long goodsId);

    Goods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(Goods goodsRecord);

    int updateByPrimaryKeyWithBLOBs(Goods goodsRecord);

    int updateByPrimaryKey(Goods goodsRecord);

    List<Goods> findMallGoodsList(PageQueryUtil pageUtil);

    int getTotalMallGoods(PageQueryUtil pageUtil);

    List<Goods> selectByPrimaryKeys(List<Long> goodsIds);

    List<Goods> findGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("MallGoodsList") List<Goods> goodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}