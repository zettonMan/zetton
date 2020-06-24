package com.zetton.thymeleaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zetton.thymeleaf.entity.ManagerInfo;

public interface ManagerInfoMapper extends BaseMapper<ManagerInfo> {
    ManagerInfo findByUserName(String username);
}
