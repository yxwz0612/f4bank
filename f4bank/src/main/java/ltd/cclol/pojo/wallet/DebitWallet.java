package ltd.cclol.pojo.wallet;

import ltd.cclol.common.ResultCode;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class DebitWallet extends Wallet {
    public DebitWallet() {
    }

    public DebitWallet(Integer fromAccountId) {
        super(fromAccountId);
    }

    @Override
    public ResultCode withdraw(BigDecimal money) {
        BigDecimal max = this.getLimitMax();
        BigDecimal used = this.getLimitUsed();
        //要取的钱<=(最大支出额度-今日已支付额度) && 预取款可以进行
        if (money.compareTo(max.subtract(used)) <= 0 && canWithdraw(money)){
            //增加本日已取款额
            addLimitUsed(money);
            //之后调用父类方法执行检查余额和取款方法
            super.withdraw(money);
        }
        return new ResultCode("今日取款数已达上限");
    }

    @Override
    public boolean canWithdraw(BigDecimal money) {
        BigDecimal max = this.getLimitMax();
        BigDecimal used = this.getLimitUsed();
        //要取的钱<=(最大支出额度-今日已支付额度)
        if (money.compareTo(max.subtract(used)) <= 0){
            //之后调用父类方法执行检查余额和取款方法
            super.canWithdraw(money);
        }
        return false;
    }
}
