package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.SetmealDish;
import com.cjj.takeaway.mapper.SetmealDishMapper;
import com.cjj.takeaway.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImp extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
