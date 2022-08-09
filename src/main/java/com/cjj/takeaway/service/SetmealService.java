package com.cjj.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.takeaway.dto.SetmealDto;
import com.cjj.takeaway.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
}
