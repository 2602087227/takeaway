package com.cjj.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cjj.takeaway.common.R;
import com.cjj.takeaway.dto.SetmealDto;
import com.cjj.takeaway.entity.Category;
import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.entity.SetmealDish;
import com.cjj.takeaway.service.DishService;
import com.cjj.takeaway.service.SetmealDishService;
import com.cjj.takeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("新增成功");
    }


}
