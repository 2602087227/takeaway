package com.cjj.takeaway.dto;

import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
