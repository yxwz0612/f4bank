package ltd.cclol.pojo.account;

import javax.persistence.Entity;

//银行账户德一个实现类pojo
@Entity
public class IndividualAccount extends Account {

    public IndividualAccount() {
    }

    public IndividualAccount(String accountPassword, String name, String personalId, String email) {
        super(accountPassword, name, personalId, email);
    }
}
