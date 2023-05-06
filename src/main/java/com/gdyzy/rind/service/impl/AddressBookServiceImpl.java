package com.gdyzy.rind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdyzy.rind.entity.AddressBook;
import com.gdyzy.rind.mapper.AddressBookMapper;
import com.gdyzy.rind.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
