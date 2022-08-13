package com.cjj.takeaway.dto;


import com.cjj.takeaway.entity.Setmeal;
import com.cjj.takeaway.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
