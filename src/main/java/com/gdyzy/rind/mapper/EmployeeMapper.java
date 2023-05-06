package com.gdyzy.rind.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdyzy.rind.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
