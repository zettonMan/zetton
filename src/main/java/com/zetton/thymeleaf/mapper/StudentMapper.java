package com.zetton.thymeleaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zetton.thymeleaf.entity.Student;

public interface StudentMapper extends BaseMapper<Student> {
    Student getFirstStudent();
}