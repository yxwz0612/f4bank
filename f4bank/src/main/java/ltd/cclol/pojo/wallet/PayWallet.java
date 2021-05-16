package ltd.cclol.pojo.wallet;

import javax.persistence.Entity;

@Entity
public class PayWallet extends Wallet {
    public PayWallet() {
    }

    public PayWallet(Integer fromAccountId) {
        super(fromAccountId);
    }

    public PayWallet(String walletType, Integer fromAccountId) {
        super(fromAccountId);
    }
}
