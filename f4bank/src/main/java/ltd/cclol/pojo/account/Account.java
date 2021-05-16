package ltd.cclol.pojo.account;

import ltd.cclol.utils.MyMd5;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//银行账户的pojo抽闲类
@Entity//映射该类到数据库
public abstract class Account {

    @Id//该属性设置为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer accountId;
    @NotBlank
    private String accountPassword;
    @NotBlank
    private String name;
    @NotBlank
    private String personalId;
    @Email//验证是否是email,暂时不起作用需要controller层配合@valid
    private String email;

    public Account() {
    }

    /*
    * 生成新的账号
    * 密码加盐
    * */
    public Account(String accountPassword, String name, String personalId, String email) {
        this.accountPassword = new MyMd5().toMd5WithSalt(accountPassword);
        this.name = name;
        this.personalId = personalId;
        this.email = email;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = new MyMd5().toMd5WithSalt(accountPassword);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountPassword='" + accountPassword + '\'' +
                ", name='" + name + '\'' +
                ", personalId='" + personalId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
