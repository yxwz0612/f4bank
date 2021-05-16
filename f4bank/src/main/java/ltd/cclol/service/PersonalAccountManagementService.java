package ltd.cclol.service;

import ltd.cclol.common.R;
import ltd.cclol.common.StatusCode;

import java.math.BigDecimal;

/*
 * 1.创建钱包
 * 2.注销钱包
 * 3.查询余额
 * 4.存款
 * 5.取款
 * 6.转账
 * 7.修改每日存取上限
 * 8.修改密码
 * 9.打印流水
 *      1.按时间
 *      2.按钱包
 *      3.按操作类型
 * */
public interface PersonalAccountManagementService {
    StatusCode createWallet(String walletType,Integer fromAccountId);
    StatusCode deleteWalletById(Integer walletId);
    R checkWalletById(Integer walletId);
    R checkAllWallets(Integer fromAccountId);
    R checkWalletBalanceById(Integer walletId);
    StatusCode depositById(Integer walletId,BigDecimal money);
    StatusCode withdrawById(Integer walletId,BigDecimal money);
    StatusCode transferTo(Integer walletId, Integer toWalletId, BigDecimal money);
    StatusCode setWalletLimitById(Integer walletId, BigDecimal newLimit);
    StatusCode setPasswordById(Integer AccountId, String newPassword);
    void createTextWallet();

    R printLog(Integer walletId);
}
