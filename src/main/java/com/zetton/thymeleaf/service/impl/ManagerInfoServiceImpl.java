package com.zetton.thymeleaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zetton.thymeleaf.common.entity.Manager;
import com.zetton.thymeleaf.common.exception.ForbiddenUserException;
import com.zetton.thymeleaf.entity.ManagerInfo;
import com.zetton.thymeleaf.mapper.ManagerInfoMapper;
import com.zetton.thymeleaf.service.ManagerInfoService;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;


@Service
public class ManagerInfoServiceImpl extends ServiceImpl<ManagerInfoMapper, ManagerInfo> implements ManagerInfoService {
    @Resource
    private ManagerInfoMapper managerInfoMapper;

    /**
     * 通过名称查找用户
     * @param username
     * @return
     */
    public ManagerInfo findByUserName(String username) {
        ManagerInfo managerInfo =  managerInfoMapper.findByUserName(username);
        if (managerInfo == null) {
            throw new UnknownAccountException();
        }
        if (managerInfo.getState() == 2) {
            throw new ForbiddenUserException();
        }
        if (managerInfo.getPidsList() == null) {
            managerInfo.setPidsList(Collections.singletonList(0));
        } else if (managerInfo.getPidsList().size() == 0) {
            managerInfo.getPidsList().add(0);
        }
        return managerInfo;
    }
}