package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.common.CustomException;
import com.cjj.takeaway.entity.Category;
import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.entity.Setmeal;
import com.cjj.takeaway.mapper.CategoryMapper;
import com.cjj.takeaway.service.CategoryService;
import com.cjj.takeaway.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishServiceImp dishServiceImp;

    @Autowired
    private SetmealService setmealService;

    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishServiceImp.count(dishLambdaQueryWrapper);

        if (count1 > 0) {
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}
