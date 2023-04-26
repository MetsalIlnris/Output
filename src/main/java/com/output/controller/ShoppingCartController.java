package com.output.controller;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.ShoppingCartItemVO;
import com.output.entity.ShoppingCartItem;
import com.output.entity.User;
import com.output.service.ShoppingCartService;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.output.common.Exception;

@Controller
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    @ResponseBody
    public Result cartListPage(@RequestParam("userId") Long userId) {
        int itemsTotal = 0;
        int priceTotal = 0;
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userId);
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(ShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                Exception.fail("购物项不能为空");
            }
            //总价
            for (ShoppingCartItemVO ShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += ShoppingCartItemVO.getGoodsCount() * ShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                Exception.fail("购物项价格异常");
            }
        }
//        request.setAttribute("itemsTotal", itemsTotal);
//        request.setAttribute("priceTotal", priceTotal);
        Result result = ResultGenerator.genSuccessResult(myShoppingCartItems);
        return result;
    }

    @PostMapping("/cart/save")
    @ResponseBody
    public Result saveShoppingCartItem(@RequestBody ShoppingCartItem ShoppingCartItem) {
        String saveResult = shoppingCartService.saveCartItem(ShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/cart/update")
    @ResponseBody
    public Result updateShoppingCartItem(@RequestBody ShoppingCartItem ShoppingCartItem) {
        String updateResult = shoppingCartService.updateCartItem(ShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/cart/{ShoppingCartItemId}")
    @ResponseBody
    public Result updateShoppingCartItem(@PathVariable("ShoppingCartItemId") Long ShoppingCartItemId) {
        Boolean deleteResult = shoppingCartService.deleteById(ShoppingCartItemId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/cart/settle")
    @ResponseBody
    public Result settlePage(@RequestParam("userId") Long userId) {
        int priceTotal = 0;
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userId);
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //总价
            for (ShoppingCartItemVO ShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += ShoppingCartItemVO.getGoodsCount() * ShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                Exception.fail("购物项价格异常");
            }
        }
        return ResultGenerator.genSuccessResult();
    }
}
