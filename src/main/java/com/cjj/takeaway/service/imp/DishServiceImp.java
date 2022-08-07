package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.Dish;
import com.cjj.takeaway.mapper.DishMapper;
import com.cjj.takeaway.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImp extends ServiceImpl<DishMapper, Dish> implements DishService {
}
