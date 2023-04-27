package com.output.controller.user;

import com.output.common.Constants;
import com.output.common.OrderStatusEnum;
import com.output.common.ServiceResultEnum;
import com.output.controller.vo.OrderDetailVO;
import com.output.controller.vo.ShoppingCartItemVO;
import com.output.controller.vo.UserVO;
import com.output.entity.Order;
import com.output.entity.User;
import com.output.service.OrderService;
import com.output.service.ShoppingCartService;
import com.output.common.Exception;
import com.output.util.PageQueryUtil;
import com.output.util.Result;
import com.output.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private OrderService orderService;

    @GetMapping("/orders/{orderNo}")
    @ResponseBody
    public Result orderDetailPage(@PathVariable("orderNo") String orderNo,@RequestParam("userId") Long userId) {
        OrderDetailVO orderDetailVO = orderService.getOrderDetailByOrderNo(orderNo, userId);
        return ResultGenerator.genSuccessResult(orderDetailVO);
    }

    @GetMapping("/ordersList")
    @ResponseBody
    public Result orderListPage(@RequestParam Map<String, Object> params) {
        if (ObjectUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(orderService.getMyOrders(pageUtil));
    }

    @GetMapping("/saveCartOrder")
    @ResponseBody
    public Result saveOrder(@RequestBody UserVO userInfo) {
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userInfo.getUserId());
        if (!StringUtils.hasText(userInfo.getAddress().trim())) {
            //无收货地址
            Exception.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            Exception.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = orderService.saveOrder(userInfo, myShoppingCartItems);
        return ResultGenerator.genSuccessResult(saveOrderResult);
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo,@RequestParam("userId") Long userId) {
        String cancelOrderResult = orderService.cancelOrder(orderNo, userId);
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, @RequestParam("userId") Long userId) {
        String finishOrderResult = orderService.finishOrder(orderNo, userId);
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    @ResponseBody
    public Result selectPayType( @RequestParam("orderNo") String orderNo, @RequestParam("userId") Long userId) {
        Order Order = orderService.getOrderByOrderNo(orderNo);
        //判断订单userId
        if (!userId.equals(Order.getUserId())) {
            Exception.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (Order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            Exception.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        String payResult = orderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
