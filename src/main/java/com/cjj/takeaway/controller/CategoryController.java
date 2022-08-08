package com.cjj.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjj.takeaway.common.R;
import com.cjj.takeaway.entity.Category;
import com.cjj.takeaway.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("保存");
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页显示
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {

        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);

    }

    /**
     * 删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类");
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    /**
     * 修改套餐分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("修改成功");
    }

//    @GetMapping("/list")
//    public R<List<Category>> list(int type){
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(Category::getType,type);
//        List<Category> list = categoryService.list(queryWrapper);
//
//        return R.success(list);
//    }

    /**
     * 添加菜品的分类
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        queryWrapper.orderByDesc(Category::getSort);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
