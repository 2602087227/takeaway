package com.cjj.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.takeaway.entity.Category;

public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
