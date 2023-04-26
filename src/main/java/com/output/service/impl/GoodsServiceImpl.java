package com.output.service.impl;

import com.output.common.ServiceResultEnum;
import com.output.controller.vo.SearchGoodsVO;
import com.output.dao.GoodsMapper;
import com.output.entity.Goods;
import com.output.service.GoodsService;
import com.output.util.BeanUtil;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;
import com.output.common.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public PageResult getGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveGoods(Goods goods) {
        return null;
    }

    @Override
    public void batchSaveGoods(List<Goods> newBeeGoodsList) {

    }

    @Override
    public String updateGoods(Goods goods) {
        return null;
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods Goods = goodsMapper.selectByPrimaryKey(id);
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
        List<Goods> goodsList = goodsMapper.findGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalGoodsBySearch(pageUtil);
        List<SearchGoodsVO> searchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            searchGoodsVOS = BeanUtil.copyList(goodsList, SearchGoodsVO.class);
            for (SearchGoodsVO searchGoodsVO : searchGoodsVOS) {
                String goodsName = searchGoodsVO.getGoodsName();
                String goodsIntro = searchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    searchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    searchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
