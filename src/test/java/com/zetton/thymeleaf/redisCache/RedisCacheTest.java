package com.zetton.thymeleaf.redisCache;

import com.zetton.thymeleaf.entity.ManagerInfo;
import com.zetton.thymeleaf.service.impl.ManagerInfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RedisCacheTest {
    @Autowired
    private ManagerInfoServiceImpl managerInfoService;

    @Test
    public void cacheTest(){
        //创建新的管理员
        ManagerInfo managerInfoA = new ManagerInfo();
        managerInfoA.setId(4);
        managerInfoA.setUsername("redisA");
        managerInfoA.setPassword("testA");
        managerInfoService.createManagerInfo(managerInfoA);

        ManagerInfo managerInfoB = new ManagerInfo();
        managerInfoB.setId(5);
        managerInfoB.setUsername("redisB");
        managerInfoB.setPassword("testB");
        managerInfoService.createManagerInfo(managerInfoB);

        // 查询所有用户列表
        List<ManagerInfo> list = managerInfoService.getAllManagerInfos();
        assertEquals(list.size(), 5);

        // 两次访问看看缓存命中情况
        //第一次
        ManagerInfo redis1 = managerInfoService.getManagerInfoById(4);
        assertEquals(redis1.getPassword(), "testA");
        //第二次 日志中可以看出第二次查询时不再查询数据库
        ManagerInfo redis2 = managerInfoService.getManagerInfoById(4);
        assertEquals(redis2.getPassword(), "testA");

        //更新用户密码
        redis2.setPassword("changePassWord");
        managerInfoService.updateManagerInfo(redis2);

        //更新完成后再次访问用户，日志中可以看出重新查询了数据库
        ManagerInfo changePassword = managerInfoService.getManagerInfoById(4);
        assertEquals(changePassword.getPassword(),"changePassWord");

        //删除用户
        managerInfoService.deleteManagerInfoById(4);
        assertNull(managerInfoService.getManagerInfoById(4));
    }
}
