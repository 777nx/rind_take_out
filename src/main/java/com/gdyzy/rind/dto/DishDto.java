package com.gdyzy.rind.dto;

import com.gdyzy.rind.entity.Dish;
import com.gdyzy.rind.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
