package com.zetton.thymeleaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zetton.thymeleaf.entity.ManagerInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ManagerInfoMapper extends BaseMapper<ManagerInfo> {

    ManagerInfo findByUserName(String username);

    @Select("SELECT * FROM t_manager where id = #{id} ")
    ManagerInfo getManagerInfoById(@Param("id") int id);

    @Select("SELECT * FROM t_manager")
    List<ManagerInfo> getAllManagerInfos();
}
