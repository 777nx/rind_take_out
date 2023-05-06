package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public void clean();
}
