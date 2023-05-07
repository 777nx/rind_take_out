package com.gdyzy.rind.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdyzy.rind.common.BaseContext;
import com.gdyzy.rind.common.R;
import com.gdyzy.rind.dto.OrdersDto;
import com.gdyzy.rind.entity.OrderDetail;
import com.gdyzy.rind.entity.Orders;
import com.gdyzy.rind.entity.ShoppingCart;
import com.gdyzy.rind.service.OrderDetailService;
import com.gdyzy.rind.service.OrderService;
import com.gdyzy.rind.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime){

        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime)
                .lt(StringUtils.isNotEmpty(endTime), Orders::getOrderTime, endTime);
        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /*@GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }*/

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize){
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageDto = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);

        LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();
        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> ordersDtoList = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            Long orderId = item.getId();
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailListByOrderId(orderId);
            BeanUtils.copyProperties(item, ordersDto);
            ordersDto.setOrderDetails(orderDetailList);
            return ordersDto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(pageInfo, pageDto, "records");
        pageDto.setRecords(ordersDtoList);
        return R.success(pageDto);
    }

    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String, String> map){

        String ids = map.get("id");
        long id = Long.parseLong(ids);

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, id);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        shoppingCartService.clean();

        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map((item) -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());

            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();

            if (dishId != null){
                shoppingCart.setDishId(dishId);
            }else {
                shoppingCart.setSetmealId(setmealId);
            }

            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        shoppingCartService.saveBatch(shoppingCartList);

        return R.success("操作成功");
    }

    @PutMapping
    public R<String> orderStatusChange(@RequestBody Map<String, String> map){

        String id = map.get("id");
        Long orderId = Long.parseLong(id);
        Integer status = Integer.parseInt(map.get("status"));

        if (orderId == null || status == null){
            return R.error("传入信息不合法");
        }

        Orders orders = orderService.getById(orderId);
        orders.setStatus(status);
        orderService.updateById(orders);

        return R.success("订单状态修改成功");
    }
}
