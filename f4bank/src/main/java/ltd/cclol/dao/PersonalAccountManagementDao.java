package ltd.cclol.dao;

import ltd.cclol.pojo.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//用户账户管理dao
public interface PersonalAccountManagementDao extends JpaRepository<Wallet,Integer>, JpaSpecificationExecutor<Wallet> {
}
