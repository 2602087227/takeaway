package com.cjj.takeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjj.takeaway.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
