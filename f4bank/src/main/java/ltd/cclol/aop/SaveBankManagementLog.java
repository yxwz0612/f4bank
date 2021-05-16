package ltd.cclol.aop;

import ltd.cclol.dao.BankManagementLogDao;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//动态代理自动保存银行管理层面的日志
@Aspect//注册为切面
@Component//加载到springBoot容器
public class SaveBankManagementLog {

    @Autowired
    BankManagementLogDao bankManagementLogDao;
    //后通知,切入点
    @After("execution(* ltd.cclol.service.impl.BankManagementServiceImpl.* (..))")
    public void saveBankManagementLog(){

    }
}
