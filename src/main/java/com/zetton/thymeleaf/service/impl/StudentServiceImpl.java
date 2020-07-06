package com.zetton.thymeleaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zetton.thymeleaf.entity.Student;
import com.zetton.thymeleaf.mapper.StudentMapper;
import com.zetton.thymeleaf.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    StudentMapper studentMapper;

    @Resource
    private RedisTemplate<String, Student> redisTemplate;

    @Override
    public Student getFirstStudent() {
        return studentMapper.getFirstStudent();
    }

    /**
     * 创建用户
     * 不会对缓存做任何操作
     */
    @Override
    public void createStudent(Student student) {
        logger.info("创建用户start...");
        studentMapper.insert(student);
    }

    /**
     * 获取用户信息
     * 如果缓存存在，从缓存中获取城市信息
     * 如果缓存不存在，从 DB 中获取信息，然后插入缓存
     *
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public Student getById(int id) {
        logger.info("获取用户start...");
        // 从缓存中获取用户信息
        String key = "student_" + id;
        ValueOperations<String, Student> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Student student = operations.get(key);
            logger.info("从缓存中获取了用户 id = " + id);
            return student;
        }

        // 缓存不存在，从 DB 中获取
        Student student = studentMapper.selectById(id);
        // 插入缓存
        operations.set(key, student, 10, TimeUnit.SECONDS);

        return student;
    }

    /**
     * 更新用户
     * 如果缓存存在，删除
     * 如果缓存不存在，不操作
     *
     * @param student 用户
     */
    @Override
    public void updateStudent(Student student) {
        logger.info("更新用户start...");
        studentMapper.updateById(student);
        int studentId = student.getId();
        // 缓存存在，删除缓存
        String key = "student_" + studentId;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            logger.info("更新用户时候，从缓存中删除用户 >> " + studentId);
        }
    }

    /**
     * 删除用户
     * 如果缓存中存在，删除
     */
    @Override
    public void deleteById(int id) {
        logger.info("删除用户start...");
        studentMapper.deleteById(id);

        // 缓存存在，删除缓存
        String key = "student_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);
            logger.info("更新用户时候，从缓存中删除用户 >> " + id);
        }
    }
}
