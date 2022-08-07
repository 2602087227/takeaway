package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.Setmeal;
import com.cjj.takeaway.mapper.SetmealMapper;
import com.cjj.takeaway.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImp extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
