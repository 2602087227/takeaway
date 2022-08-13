package com.cjj.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.cjj.takeaway.common.BaseContext;
import com.cjj.takeaway.common.R;
import com.cjj.takeaway.entity.ShoppingCart;
import com.cjj.takeaway.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {

        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if (dishId!=null){
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        } else{
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if (cartServiceOne!=null){
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);

    }
//    @PostMapping("/sub")
//    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
//
//        Long currentId = BaseContext.getCurrentId();
//        shoppingCart.setUserId(currentId);
//        Long dishId = shoppingCart.getDishId();
//
//        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(ShoppingCart::getUserId,currentId);
//
//        if (dishId!=null){
//            queryWrapper.eq(ShoppingCart::getDishId,dishId);
//        } else{
//            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
//        }
//
//        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
//
//        if (cartServiceOne!=null){
//            Integer number = cartServiceOne.getNumber();
//            cartServiceOne.setNumber(number-1);
//            shoppingCartService.updateById(cartServiceOne);
//        }else {
//            shoppingCart.setNumber(1);
//            shoppingCart.setCreateTime(LocalDateTime.now());
//            shoppingCartService.save(shoppingCart);
//            cartServiceOne = shoppingCart;
//        }
//        return R.success(cartServiceOne);
//
//    }
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){

        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        shoppingCartService.removeById(BaseContext.getCurrentId());
        return R.success("清理成功");
    }



}
