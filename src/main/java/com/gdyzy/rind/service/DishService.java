package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.dto.DishDto;
import com.gdyzy.rind.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void deleteByIds(List<Long> ids);
}
