package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.dto.SetmealDto;
import com.gdyzy.rind.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

    public void updateSetmealStatusById(Integer status, List<Long> ids);

    public SetmealDto getData(Long id);
}
