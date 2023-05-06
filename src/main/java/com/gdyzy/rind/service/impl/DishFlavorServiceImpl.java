package com.gdyzy.rind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdyzy.rind.entity.DishFlavor;
import com.gdyzy.rind.mapper.DishFlavorMapper;
import com.gdyzy.rind.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
