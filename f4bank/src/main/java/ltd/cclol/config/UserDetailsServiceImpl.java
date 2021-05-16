package ltd.cclol.config;

import ltd.cclol.dao.BankManagementDao;
import ltd.cclol.pojo.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BankManagementDao bankManagementDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> user = bankManagementDao.findById(Integer.valueOf(s));//根据用户输入的用户名从数据库查找
        if (s !=null && user.isPresent()){//如果用户输入不为空 并且查到该账户存在
            //返回一个用户对象供下一步进行密码验证和权限赋予
            return new UserDetailsImpl(user.get().getAccountId().toString(),user.get().getAccountPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user,ROLE_admin"));
        }
        throw new UsernameNotFoundException("没有找到该用户");
    }
}
