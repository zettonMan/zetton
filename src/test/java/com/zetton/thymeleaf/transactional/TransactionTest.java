package com.zetton.thymeleaf.transactional;

import com.zetton.thymeleaf.common.exception.NoRollBackAException;
import com.zetton.thymeleaf.entity.ManagerInfo;
import com.zetton.thymeleaf.service.impl.ManagerInfoServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTest {
    @Autowired
    ManagerInfoServiceImpl managerInfoService;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void updateUserError() {
        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setId(3);
        managerInfo.setUsername("testChange");
        RuntimeException exception = Assert.assertThrows("runtime-----RollBack", RuntimeException.class, () ->{
            managerInfoService.updateUserError(managerInfo);
        });
        Assert.assertTrue(exception.getMessage().contains("runtime-----RollBack"));
    }

    @Test
    public void updateUserErrorNoRollback() {
        ManagerInfo managerInfo = new ManagerInfo();
        managerInfo.setId(3);
        managerInfo.setUsername("testNoRollback");
        exception.expect(NoRollBackAException.class);
        exception.expectMessage("runtime-----NoRollBack");
        managerInfoService.updateUserErrorNoRollback(managerInfo);
    }
}
