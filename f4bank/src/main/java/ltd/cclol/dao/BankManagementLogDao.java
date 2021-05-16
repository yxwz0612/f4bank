package ltd.cclol.dao;

import ltd.cclol.pojo.account.BankLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//银行管理日志保存的dao
public interface BankManagementLogDao extends JpaRepository<BankLog,Integer>, JpaSpecificationExecutor<BankLog> {
}
