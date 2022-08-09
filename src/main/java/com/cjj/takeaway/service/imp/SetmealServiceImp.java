package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.dto.SetmealDto;
import com.cjj.takeaway.entity.Setmeal;
import com.cjj.takeaway.entity.SetmealDish;
import com.cjj.takeaway.mapper.SetmealMapper;
import com.cjj.takeaway.service.SetmealDishService;
import com.cjj.takeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImp extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }
}
