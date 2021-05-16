package ltd.cclol.aop;

import ltd.cclol.dao.PersonalAccountManagementLogDao;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//动态代理自动保存用户操作的日志信息
@Aspect//注册切面
@Component
public class SavePersonalAccountManagementLog {

    @Autowired
    PersonalAccountManagementLogDao personalAccountManagementLogDao;
   // 后通知,切入点
   @After("execution(* ltd.cclol.service.impl.PersonalAccountManagementServiceImpl.*(..))")
    public void savePersonaLog(){
       // System.out.println("savePersonalLog生效");
    }

/*    @Before("Aspect()")
    public void beforeAspect(){
        System.out.println("这是切面aop  前置方法");
    }

    @After("Aspect()")
    public void afterAspect(){
        System.out.println("这是切面aop  后置方法");
    }*/

}
