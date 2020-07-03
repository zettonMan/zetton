package com.zetton.thymeleaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zetton.thymeleaf.common.enums.DataSourceEnum;
import com.zetton.thymeleaf.common.retenion.DataSource;
import com.zetton.thymeleaf.entity.Teacher;
import com.zetton.thymeleaf.mapper.TeacherMapper;
import com.zetton.thymeleaf.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper,Teacher> implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @DataSource(value = DataSourceEnum.SLAVE)
    public int insert(Teacher entity) {
        return teacherMapper.insert(entity);
    }

    @DataSource(DataSourceEnum.SLAVE)
    public int deleteById(Serializable id) {
        return teacherMapper.deleteById(id);
    }

    @Override
    @DataSource(DataSourceEnum.SLAVE)
    public boolean updateById(Teacher entity) {
        return super.updateById(entity);
    }

    @Override
    @DataSource(DataSourceEnum.MASTER)
    public Teacher getById(Serializable id) {
        return teacherMapper.selectById(id);
    }

    @Override
    @DataSource(DataSourceEnum.SLAVE)
    public List<Teacher> list(Wrapper<Teacher> wrapper) {
        return super.list(wrapper);
    }

    @Override
    public <E extends IPage<Teacher>> E page(E page) {
        return super.page(page);
    }

    @Override
    public <E extends IPage<Teacher>> E page(E page, Wrapper<Teacher> wrapper) {
        return super.page(page, wrapper);
    }
}