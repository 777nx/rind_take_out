package com.gdyzy.rind.dto;

import com.gdyzy.rind.entity.Setmeal;
import com.gdyzy.rind.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
