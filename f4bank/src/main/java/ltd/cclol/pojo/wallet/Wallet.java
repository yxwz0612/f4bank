package ltd.cclol.pojo.wallet;

import ltd.cclol.common.ResultCode;
import ltd.cclol.common.ResultCodeModel;
import ltd.cclol.common.StatusCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)//使用创建时间的@CreatedDate生效
public abstract class Wallet {
    @Id//注册为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer walletId;
    @NotBlank//不起作用,生效要在controller层接收的的参数前加上@valid
    private String walletType;
    @NotBlank
    private Integer fromAccountId;
    @NotBlank
    private BigDecimal balance;
    @NotBlank
    private BigDecimal limitMax;
    @NotBlank
    private BigDecimal limitUsed;
    @CreatedDate
    private Date createdDate;

    public Wallet() {
    }

    public StatusCode deposit(BigDecimal money) {
        this.balance.add(money);
        return ResultCodeModel.SUCCESS;
    }

    public Wallet(Integer fromAccountId) {
        this.walletType = this.getClass().getName().replace("ltd.cclol.pojo.wallet.","");//可能是坑
        this.fromAccountId = fromAccountId;
        this.balance = new BigDecimal("0");
        switch (walletType){
            case "DebitWallet":
                this.setLimitMax(new BigDecimal("2000"));
                this.setLimitUsed(new BigDecimal("0"));
                break;
            case "PayWallet":
                this.setLimitMax(new BigDecimal("0"));
                this.setLimitUsed(new BigDecimal("0"));
        }
    }

    public StatusCode withdraw(BigDecimal money) {
        int result = this.balance.compareTo(money);
        if (money.compareTo(new BigDecimal("0")) > 0){
            if (result>=0){
                this.balance.subtract(money);
                return ResultCodeModel.SUCCESS;
            }
            return new ResultCode("余额不足");
        }
        return new ResultCode("取款数要大于0");
    }

    public boolean canWithdraw(BigDecimal money) {
        int result = this.balance.compareTo(money);
        if (money.compareTo(new BigDecimal("0")) > 0){
            if (result>=0){
                return true;
            }
            return false;
        }
        return false;
    }

    public BigDecimal getLimitMax() {
        return limitMax;
    }

    public StatusCode setLimitMax(BigDecimal limitMax) {
        if (this.walletType != "DebitWallet"){
            if (limitMax.compareTo(new BigDecimal("0")) >= 0){
                this.limitMax = limitMax;
                return ResultCodeModel.SUCCESS;
            }
            return new ResultCode("支付额上限不能小于0");
        }
        return new ResultCode("该钱包没有限额");
    }

    public BigDecimal getLimitUsed() {
        return limitUsed;
    }

    public void setLimitUsed(BigDecimal limitUsed) {
        this.limitUsed = limitUsed;
    }

    public void addLimitUsed(BigDecimal money){
        this.limitUsed.add(limitUsed);
    }

    public Integer getWalletId() {
        return walletId;
    }

    public String getWalletType() {
        return walletType;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", walletType='" + walletType + '\'' +
                ", fromAccountId=" + fromAccountId +
                ", balance=" + balance +
                ", limitMax=" + limitMax +
                ", limitUsed=" + limitUsed +
                ", createdDate=" + createdDate +
                '}';
    }
}
