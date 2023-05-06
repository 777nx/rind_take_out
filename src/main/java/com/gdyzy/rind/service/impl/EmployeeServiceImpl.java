package com.gdyzy.rind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdyzy.rind.entity.Employee;
import com.gdyzy.rind.mapper.EmployeeMapper;
import com.gdyzy.rind.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
