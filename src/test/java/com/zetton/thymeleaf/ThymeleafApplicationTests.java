package com.zetton.thymeleaf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zetton.thymeleaf.controller.StudentController;
import com.zetton.thymeleaf.entity.Student;
import com.zetton.thymeleaf.entity.Teacher;
import com.zetton.thymeleaf.mapper.StudentMapper;
import com.zetton.thymeleaf.service.StudentService;
import com.zetton.thymeleaf.service.TeacherService;
import io.swagger.models.auth.In;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ThymeleafApplicationTests {

    @Resource
    private TeacherService teacherService;

    @Mock
    private StudentController studentController;

    @Test
    void test() {
        Teacher teacher = teacherService.getById(1);
        Assert.assertNotNull(teacher);
        teacher = teacherService.getById(2);
        Assert.assertNull(teacher);
    }

    @Test
    void testMock(){
        /*QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("id","name","age");
        wrapper.eq("id",1);*/
        List<Student> mockList = new ArrayList();
        mockList.add(Student.builder()
                .id(1)
                .age(18)
                .name("tom")
                //.classname("fox")
                .build());
        when(studentController.list()).thenReturn(mockList);

        List<Student> returnList = studentController.list();
        Assert.assertEquals(returnList.get(0).getId(),new Integer(1));
        System.out.println(returnList.get(0).toString());
    }

    @Test
    void testDynamicDataSource(){
        teacherService.getById(1);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",1);
        teacherService.list(wrapper);
    }
}
