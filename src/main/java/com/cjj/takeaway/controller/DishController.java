package com.cjj.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.takeaway.common.R;
import com.cjj.takeaway.dto.DishDto;
import com.cjj.takeaway.entity.Category;
import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.entity.DishFlavor;
import com.cjj.takeaway.service.CategoryService;
import com.cjj.takeaway.service.DishFlavorService;
import com.cjj.takeaway.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        //清理所有redis
//        Set keys = redisTemplate.keys("dish*");
//        redisTemplate.delete(keys);
        //精确清理redis
        String key ="dish"+ dishDto.getCategoryId()+"-"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("新增菜品成功");
    }

    /**
     * 分页查询菜品
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
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

    /**
     * 修改时回传菜品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);
        //精确清理redis
        String key ="dish"+ dishDto.getCategoryId()+"-"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("修改成功！");
    }

    /**
     * 修改菜品状态
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> setStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> dishList = dishService.list(queryWrapper);
        dishList.stream().map((item) -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());

        dishService.updateBatchById(dishList);
        return R.success("状态修改成功");
    }

    /**
     * 新增套餐时回传菜品信息
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        //为redis创建一个key;
        String key ="dish"+dish.getCategoryId()+"_"+dish.getStatus();
        List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoList!=null){
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            //将list里面的类容拷贝给DishDto
            BeanUtils.copyProperties(item, dishDto);

            //给DishDto里面的categoryName赋值
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            LambdaQueryWrapper<DishFlavor> dishFlavorQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorQueryWrapper.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;

        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key,dishDtoList);

        return R.success(dishDtoList);
    }


    /**
     * 删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.deleteWithFlavor(ids);
        return R.success("删除菜品成功");
    }
}
