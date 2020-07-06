package com.zetton.thymeleaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zetton.thymeleaf.entity.Student;

public interface StudentService extends IService<Student> {

    public void createStudent(Student student);

    public Student getById(int id);

    public void updateStudent(Student student);

    public void deleteById(int id);

    public Student getFirstStudent();
}