package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.User;
import com.cjj.takeaway.mapper.UserMapper;
import com.cjj.takeaway.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper,User> implements UserService {
}
