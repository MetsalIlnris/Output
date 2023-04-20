package com.output.service.impl;

import com.output.common.ServiceResultEnum;
import com.output.dao.GoodsMapper;
import com.output.entity.MallGoods;
import com.output.service.GoodsService;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;
import com.output.common.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public PageResult getGoodsPage(PageQueryUtil pageUtil) {
        return null;
    }

    @Override
    public String saveGoods(MallGoods goods) {
        return null;
    }

    @Override
    public void batchSaveGoods(List<MallGoods> newBeeMallGoodsList) {

    }

    @Override
    public String updateGoods(MallGoods goods) {
        return null;
    }

    @Override
    public MallGoods getGoodsById(Long id) {
        MallGoods Goods = goodsMapper.selectByPrimaryKey(id);
        if (Goods == null) {
            Exception.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return Goods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return null;
    }

    @Override
    public PageResult searchGoods(PageQueryUtil pageUtil) {
        return null;
    }
}
