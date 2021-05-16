package ltd.cclol.dao;

import ltd.cclol.pojo.wallet.WalletLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WalletLogDao extends JpaRepository<WalletLog,Integer>, JpaSpecificationExecutor<WalletLog> {
}
