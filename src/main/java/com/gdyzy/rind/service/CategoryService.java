package com.gdyzy.rind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdyzy.rind.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
