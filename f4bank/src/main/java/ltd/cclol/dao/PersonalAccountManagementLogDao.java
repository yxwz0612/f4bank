package ltd.cclol.dao;

import ltd.cclol.pojo.wallet.PersonalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//用户日志保存dao
public interface PersonalAccountManagementLogDao extends JpaRepository<PersonalLog,Integer>, JpaSpecificationExecutor<PersonalLog> {
}
