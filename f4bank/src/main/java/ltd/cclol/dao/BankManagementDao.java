package ltd.cclol.dao;

import ltd.cclol.pojo.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

//用于银行管理的dao层
public interface BankManagementDao extends JpaRepository<Account,Integer>, JpaSpecificationExecutor<Account> {
}
