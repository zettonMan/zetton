package com.zetton.thymeleaf.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zetton.thymeleaf.entity.Student;
import com.zetton.thymeleaf.service.impl.StudentServiceImpl;
import com.zetton.thymeleaf.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("对学生表CRUD")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @ApiOperation("添加学生")
    @PostMapping("/add")
    public String add(@RequestBody StudentVo student){
        Student stu = Student.builder().build();
        stu.setName(student.getName());
        stu.setAge(student.getAge());
        stu.setClassName(student.getClassName());
        return studentService.save(stu)?"添加成功":"添加失败";
    }

    @ApiOperation("删除学生")
    @DeleteMapping("/delete/{id}")
    public String delete(@ApiParam("学生的主键id")@PathVariable(value = "id") Integer id){
        return studentService.removeById(id)?"删除成功":"删除失败";
    }

    @ApiOperation("修改学生")
    @PostMapping("/update")
    public String update(@RequestBody Student student){
        return studentService.updateById(student)?"修改成功":"修改失败";
    }

    @ApiOperation(value = "查询学生")
    @GetMapping("/list")
    public List<Student> list(){
        Wrapper<Student> wrapper = new QueryWrapper<>();
        return studentService.list(wrapper);
    }

    @ApiOperation(value = "查询第一个学生")
    @GetMapping("/first")
    public Student first(){
        return studentService.getFirstStudent();
    }
}