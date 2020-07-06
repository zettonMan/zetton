package com.zetton.thymeleaf.service.impl;

import com.zetton.thymeleaf.entity.Student;
import com.zetton.thymeleaf.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testCache() {
        studentService.createStudent(Student.builder()
                .name("tom")
                .age(18)
                .build());
        Student student = studentService.getFirstStudent();
        int id = student.getId();
        Student student1 = studentService.getById(id); // 第1次访问
        assertEquals(student1.getName(), "tom");
        Student student2 = studentService.getById(id); // 第2次访问
        assertEquals(student2.getName(), "tom");
        student.setAge(16);
        studentService.updateStudent(student);
        Student student3 = studentService.getById(id); // 第3次访问
        assertEquals(student3.getAge(), 16);
        studentService.deleteById(id);
        assertNull(studentService.getById(id));
    }
}