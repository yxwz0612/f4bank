package ltd.cclol.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

//自定义工具类
@Component
public class MyMd5 {
    //盐值
    //@Value("${md5.salt}") 这个类装配在抽象类中不生效,先写死
    private String mySalt = "jt,!**^$T";

    public String getMySalt() {
        return mySalt;
    }

    public void setMySalt(String mySalt) {
        this.mySalt = mySalt;
    }

    //按传入的密码返回一个加盐的md5密码
    public String toMd5WithSalt(String password){
        String newPassWord = password+mySalt;
        return DigestUtils.md5DigestAsHex(newPassWord.getBytes());
    }
}
