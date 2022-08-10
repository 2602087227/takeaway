package com.cjj.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.takeaway.dto.DishDto;
import com.cjj.takeaway.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void deleteWithFlavor(List<Long> ids);
}
