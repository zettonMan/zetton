package com.zetton.thymeleaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zetton.thymeleaf.common.exception.ForbiddenUserException;
import com.zetton.thymeleaf.entity.ManagerInfo;

public interface ManagerInfoService extends IService<ManagerInfo> {
    ManagerInfo findByUserName(String username) ;
}