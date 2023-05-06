package com.gdyzy.rind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdyzy.rind.common.CustomException;
import com.gdyzy.rind.entity.Category;
import com.gdyzy.rind.entity.Dish;
import com.gdyzy.rind.entity.Setmeal;
import com.gdyzy.rind.mapper.CategoryMapper;
import com.gdyzy.rind.service.CategoryService;
import com.gdyzy.rind.service.DishService;
import com.gdyzy.rind.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getId, ids);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId, ids);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            throw new CustomException("当前分类下关联了菜单，不能删除");
        }
        super.removeById(ids);
    }
}
