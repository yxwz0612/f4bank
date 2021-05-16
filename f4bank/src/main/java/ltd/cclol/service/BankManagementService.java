package ltd.cclol.service;


/*
 * 1.创建个人账户
 * 2.注销个人账户
 * 3.查询个人账户
 * 4.修改个人账户的个人信息
 *      1.名字
 *      2.身份证号
 *      3.email
 * 4.打印日志
 * */

import ltd.cclol.common.R;
import ltd.cclol.common.StatusCode;
import ltd.cclol.pojo.account.Account;
import ltd.cclol.pojo.account.BankLog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
/*
* 1.创建账号
* 2.删除账号
* 3.查询账号
* 4.查询所有账号
* 5.分页查询批量账号
* 6.
*
* */
public interface BankManagementService {
    R createAccount(String accountType, String accountPassword, String name, String personalId, String email);
    StatusCode deleteAccountById(Integer accountId);
    Optional<Account> checkAccountById(Integer accountId);
    List<Account> checkAllAccounts();
    Page<Account> checkAllAccountsForPage(Integer pageNo, Integer pageSize);
    StatusCode updateAccountNameById(Integer accountId, String name);
    StatusCode updatePersonalIdById(Integer accountId, String personalId);
    StatusCode updateEmailById(Integer accountId, String email);
    List<BankLog> printLog();
    void createTextTable();

}
