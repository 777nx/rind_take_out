package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.entity.Orders;

public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

}
