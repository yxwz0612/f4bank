package ltd.cclol.pojo.wallet;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class WalletLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walletLogId;
    @NotBlank
    private Integer fromWalletId;
    @NotBlank
    private String actionType;
    @NotBlank
    private String aboutBalance;
    @CreatedDate
    private Date actionDate;

    public WalletLog(Integer fromWalletId, String actionType, String aboutBalance) {
        this.fromWalletId = fromWalletId;
        this.actionType = actionType;
        this.aboutBalance = aboutBalance;
    }
}
