package com.zetton.thymeleaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zetton.thymeleaf.entity.Student;
import com.zetton.thymeleaf.mapper.StudentMapper;
import com.zetton.thymeleaf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student getFirstStudent() {
        return studentMapper.getFirstStudent();
    }
}
