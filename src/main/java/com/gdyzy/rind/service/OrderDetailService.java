package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService extends IService<OrderDetail> {
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId);
}
