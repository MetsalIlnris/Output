package com.output.service.impl;

import com.output.common.ServiceResultEnum;
import com.output.controller.vo.SearchGoodsVO;
import com.output.dao.GoodsCategoryMapper;
import com.output.dao.GoodsMapper;
import com.output.entity.Goods;
import com.output.entity.GoodsCategory;
import com.output.service.GoodsService;
import com.output.util.BeanUtil;
import com.output.util.OtherUtils;
import com.output.util.PageQueryUtil;
import com.output.util.PageResult;
import com.output.common.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getGoodsPage(PageQueryUtil pageUtil) {
        List<Goods> goodsList = goodsMapper.findMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveGoods(Goods goods) {

        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在，则该参数字段异常
        if (goodsCategory == null ) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setGoodsName(OtherUtils.cleanString(goods.getGoodsName()));
        goods.setGoodsIntro(OtherUtils.cleanString(goods.getGoodsIntro()));
        goods.setTag(OtherUtils.cleanString(goods.getTag()));
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveGoods(List<Goods> goodsList) {
        if (!CollectionUtils.isEmpty(goodsList)) {
            goodsMapper.batchInsert(goodsList);
        }
    }

    @Override
    public String updateGoods(Goods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在，则该参数字段异常
        if (goodsCategory == null) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        Goods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        Goods temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setGoodsName(OtherUtils.cleanString(goods.getGoodsName()));
        goods.setGoodsIntro(OtherUtils.cleanString(goods.getGoodsIntro()));
        goods.setTag(OtherUtils.cleanString(goods.getTag()));
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
        
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
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
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
