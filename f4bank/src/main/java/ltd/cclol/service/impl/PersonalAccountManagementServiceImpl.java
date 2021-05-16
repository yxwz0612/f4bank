package ltd.cclol.service.impl;

import ltd.cclol.common.R;
import ltd.cclol.common.ResultCode;
import ltd.cclol.common.ResultCodeModel;
import ltd.cclol.common.StatusCode;
import ltd.cclol.dao.BankManagementDao;
import ltd.cclol.dao.PersonalAccountManagementDao;
import ltd.cclol.dao.PersonalAccountManagementLogDao;
import ltd.cclol.dao.WalletLogDao;
import ltd.cclol.pojo.account.Account;
import ltd.cclol.pojo.wallet.*;
import ltd.cclol.service.PersonalAccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalAccountManagementServiceImpl implements PersonalAccountManagementService {
    @Autowired
    PersonalAccountManagementDao personalAccountManagementDao;
    @Autowired
    BankManagementDao bankManagementDao;
    @Autowired
    PersonalAccountManagementLogDao personalAccountManagementLogDao;
    @Autowired
    WalletLogDao walletLogDao;

    @Override
    public StatusCode createWallet(String walletType,Integer fromAccountId) {
        switch (walletType){
            case "DebitWallet":
                Wallet newDebitWallet = new DebitWallet(fromAccountId);
                personalAccountManagementDao.save(newDebitWallet);
                break;
            case "PayWallet":
                Wallet newPayWallet = new PayWallet(fromAccountId);
                personalAccountManagementDao.save(newPayWallet);
                break;
            default:
                return new ResultCode("创建钱包类型错误");
        }
        return ResultCodeModel.SUCCESS;
    }

    /*
    * 注销指定钱包
    * 余额必须为0
    * */
    @Override
    public StatusCode deleteWalletById(Integer walletId) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            if (result.get().getBalance().equals(new BigDecimal("0"))){
                personalAccountManagementDao.delete(result.get());
                walletLogDao.save(new WalletLog(walletId,"注销钱包","0"));
                return ResultCodeModel.SUCCESS;
            }
           return new ResultCode("余额不为空无法注销钱包");
        }
        return ResultCodeModel.FAILED_NULL;
    }

    @Override
    public R checkWalletById(Integer walletId) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            return new R(result);
        }
        return new R(ResultCodeModel.FAILED_NULL);
    }

    @Override
    public R checkAllWallets(Integer fromAccountId) {
        List<Wallet> result = personalAccountManagementDao.findAll();
        if (!result.isEmpty()){
            return new R(result);
        }
        return new R(ResultCodeModel.FAILED_NULL);
    }

    @Override
    public R checkWalletBalanceById(Integer walletId) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            BigDecimal balanceResult = result.get().getBalance();
            return new R(balanceResult);
        }
        return new R(ResultCodeModel.FAILED_NULL);
    }

    @Override
    public StatusCode depositById(Integer walletId, BigDecimal money) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            StatusCode depositResult = result.get().deposit(money);
            if (depositResult.getCode()==1000){
                walletLogDao.save(new WalletLog(walletId,"存入",money.toString()));
            }
            return depositResult;
        }
        return new ResultCode("没有找到该钱包");
    }

    @Override
    public StatusCode withdrawById(Integer walletId,BigDecimal money) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            StatusCode withdrawResult = result.get().withdraw(money);
            if (withdrawResult.getCode() == 1000){
                walletLogDao.save(new WalletLog(walletId,"取出",money.toString()));
            }
            return withdrawResult;
        }
        return new ResultCode("没有找到该钱包");
    }

    @Override
    @Transactional//声明事务
    public StatusCode transferTo(Integer walletId,Integer toWalletId ,BigDecimal money) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        Optional<Wallet> toResult = personalAccountManagementDao.findById(toWalletId);
        if (result.isPresent() && toResult.isPresent()){
            if (result.get().canWithdraw(money)){
                result.get().withdraw(money);
                toResult.get().deposit(money);
                walletLogDao.save(new WalletLog(walletId,"转出",money.toString()));
                walletLogDao.save(new WalletLog(toWalletId,"转入",money.toString()));
                return ResultCodeModel.SUCCESS;
            }
            return new ResultCode("余额不足或今日取款已达上限");
        }
        return ResultCodeModel.FAILED_NULL;
    }

    @Override
    public StatusCode setWalletLimitById(Integer walletId,BigDecimal newLimit) {
        Optional<Wallet> result = personalAccountManagementDao.findById(walletId);
        if (result.isPresent()){
            walletLogDao.save(new WalletLog(walletId,"设置每日上限",newLimit.toString()));
            return result.get().setLimitMax(newLimit);
        }
        return ResultCodeModel.FAILED_NULL;
    }

    @Override
    public StatusCode setPasswordById(Integer AccountId, String newPassword) {
        Optional<Account> result = bankManagementDao.findById(AccountId);
        if (result.isPresent()){
            result.get().setAccountPassword(newPassword);
            return ResultCodeModel.SUCCESS;
        }
        return ResultCodeModel.FAILED_NULL;
    }

    @Override
    public R printLog(Integer walletId) {
        List<PersonalLog> result = personalAccountManagementLogDao.findAll();
        return new R(result);
    }

    public void createTextWallet(){
        DebitWallet debitWallet = new DebitWallet(1);
        DebitWallet debitWallet2 = new DebitWallet(1);
        DebitWallet debitWallet3 = new DebitWallet(1);
        personalAccountManagementDao.save(debitWallet);
        personalAccountManagementDao.save(debitWallet2);
        personalAccountManagementDao.save(debitWallet3);
    }
}
