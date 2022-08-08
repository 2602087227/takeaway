package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.DishFlavor;
import com.cjj.takeaway.mapper.DishFlavorMapper;
import com.cjj.takeaway.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImp extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
