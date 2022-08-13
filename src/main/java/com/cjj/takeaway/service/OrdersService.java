package com.cjj.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjj.takeaway.entity.Orders;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
