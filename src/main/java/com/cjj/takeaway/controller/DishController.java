package com.cjj.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.takeaway.common.R;
import com.cjj.takeaway.dto.DishDto;
import com.cjj.takeaway.entity.Category;
import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.service.CategoryService;
import com.cjj.takeaway.service.DishFlavorService;
import com.cjj.takeaway.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //dish查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);

        //将pageInfo的类容拷贝到dishDtoPage忽略records
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        //取出pageInfo中的records
        List<Dish> records = pageInfo.getRecords();

        //遍历pageInfo中的records
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            //将records里面的类容拷贝给DishDtoPage的records
            BeanUtils.copyProperties(item, dishDto);

            //给DishDtoPage里面的categoryName赋值
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        //给dishDtoPage的records赋值
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功！");
    }
}
