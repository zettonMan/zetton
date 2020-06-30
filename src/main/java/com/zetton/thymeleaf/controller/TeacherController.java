package com.zetton.thymeleaf.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zetton.thymeleaf.entity.Teacher;
import com.zetton.thymeleaf.service.TeacherService;
import com.zetton.thymeleaf.vo.TeacherVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "对老师表CRUD")
@ApiResponses(value={
        @ApiResponse(code=200,message = "成功"),
        @ApiResponse(code=400,message = "失败")
})
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "添加老师", tags = "老师管理业务接口")
    @PostMapping("/add")
    public String add(@RequestBody TeacherVo teacher){
        Teacher tea = new Teacher();
        tea.setName(teacher.getName());
        tea.setAge(teacher.getAge());
        tea.setSubject(teacher.getSubject());
        return teacherService.save(tea)?"添加成功":"添加失败";
    }

    @ApiOperation(value = "删除老师",notes = "删除操作")
    @DeleteMapping("/delete/{id}")
    public String delete(@ApiParam("老师的主键id")@PathVariable(value = "id") Integer id){
        return teacherService.removeById(id)?"删除成功":"删除失败";
    }

    @ApiOperation(value = "修改老师", produces ="application/json")
    @PostMapping("/update")
    public String update(@RequestBody Teacher teacher){
        return teacherService.updateById(teacher)?"修改成功":"修改失败";
    }

    @ApiOperation(value = "查询老师")
    @GetMapping("/list")
    public List<Teacher> list(){
        Wrapper<Teacher> wrapper = new QueryWrapper<>();
        return teacherService.list(wrapper);
    }

    @ApiOperation(value = "查询指定老师")
    @GetMapping("/getById/{id}")
    public Teacher queryTeacherById(@ApiParam("老师的主键id")@PathVariable(value = "id") Integer id){
        return teacherService.getById(id);
    }
}