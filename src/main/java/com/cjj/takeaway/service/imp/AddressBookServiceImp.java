package com.cjj.takeaway.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjj.takeaway.entity.AddressBook;
import com.cjj.takeaway.mapper.AddressBookMapper;
import com.cjj.takeaway.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImp extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService {
}
