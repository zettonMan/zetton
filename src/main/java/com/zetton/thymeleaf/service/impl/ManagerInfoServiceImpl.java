package com.zetton.thymeleaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zetton.thymeleaf.common.exception.ForbiddenUserException;
import com.zetton.thymeleaf.common.exception.NoRollBackAException;
import com.zetton.thymeleaf.entity.ManagerInfo;
import com.zetton.thymeleaf.mapper.ManagerInfoMapper;
import com.zetton.thymeleaf.service.ManagerInfoService;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;


@Service
public class ManagerInfoServiceImpl extends ServiceImpl<ManagerInfoMapper, ManagerInfo> implements ManagerInfoService {
    Logger logger = LoggerFactory.getLogger(ManagerInfoServiceImpl.class);

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


    @Transactional(readOnly = false)
    public void updateUserError(ManagerInfo managerInfo){
        managerInfoMapper.updateById(managerInfo);
        errMethod(); // 执行一个会抛出异常的方法
    }

    private void errMethod() {
        System.out.println("error"+"-----RollBack");
        throw new RuntimeException("runtime"+"-----RollBack");
    }

    @Transactional(readOnly = false, noRollbackFor = {NoRollBackAException.class})
    public void updateUserErrorNoRollback(ManagerInfo managerInfo) {
        managerInfoMapper.updateById(managerInfo);
        errMethod2(); // 执行一个会抛出自定义异常的方法
    }

    private void errMethod2() {
        System.out.println("error"+"-----NoRollBack");
        throw new NoRollBackAException("runtime"+"-----NoRollBack");
    }

    /**
     * cacheNames 设置缓存的值
     * key：指定缓存的key，这是指参数id值。key可以使用spEl表达式
     *
     * @param id
     * @return
     */
    @Cacheable(value = "userCache", key = "#id", unless="#result == null")
    public ManagerInfo getManagerInfoById(int id) {
        logger.info("查询一个getManagerInfoById from database start ... ");
        return managerInfoMapper.getManagerInfoById(id);
    }

    @Cacheable(value = "allUsersCache", unless = "#result.size() == 0")
    public List<ManagerInfo> getAllManagerInfos() {
        logger.info("查询所有getAllManagerInfos from database start ... ");
        return managerInfoMapper.getAllManagerInfos();
    }

    /**
     * 创建用户，同时使用新的返回值的替换缓存中的值
     * 创建用户后会将allUsersCache缓存全部清空
     */
    @Caching(
        put = {@CachePut(value = "userCache", key = "#managerInfo.id")},
        evict = {@CacheEvict(value = "allUsersCache", allEntries = true)}
    )
    public ManagerInfo createManagerInfo(ManagerInfo managerInfo) {
        logger.info("创建createManagerInfo from database start ... ");
        managerInfoMapper.insert(managerInfo);
        return managerInfo;
    }

    /**
     * 更新用户，同时使用新的返回值的替换缓存中的值
     * 更新用户后会将allUsersCache缓存全部清空
     */
    @Caching(
        put = {@CachePut(value = "userCache", key = "#managerInfo.id")},
        evict = {@CacheEvict(value = "allUsersCache", allEntries = true)}
    )
    public ManagerInfo updateManagerInfo(ManagerInfo managerInfo) {
        logger.info("更新updateManagerInfo from database start ... ");
        /*UpdateWrapper<ManagerInfo> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("password",managerInfo.getPassword());
        super.update(managerInfo,updateWrapper);*/
        managerInfoMapper.updateById(managerInfo);
        return managerInfo;
    }

    /**
     * 对符合key条件的记录从缓存中移除
     * 删除用户后会将allUsersCache缓存全部清空
     */
    @Caching(
        evict = {
                @CacheEvict(value = "userCache", key = "#id"),
                @CacheEvict(value = "allUsersCache", allEntries = true)
        }
    )
    public void deleteManagerInfoById(int id) {
        logger.info("删除deleteManagerInfoById from database start ... ");
        managerInfoMapper.deleteById(id);
    }
}