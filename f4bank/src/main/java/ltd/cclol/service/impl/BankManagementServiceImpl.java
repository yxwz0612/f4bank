package ltd.cclol.service.impl;

import ltd.cclol.common.R;
import ltd.cclol.common.ResultCode;
import ltd.cclol.common.ResultCodeModel;
import ltd.cclol.common.StatusCode;
import ltd.cclol.dao.BankManagementDao;
import ltd.cclol.pojo.account.Account;
import ltd.cclol.pojo.account.BankLog;
import ltd.cclol.pojo.account.IndividualAccount;
import ltd.cclol.service.BankManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
* 这一层返回数据包或者错误码
* */
@Service
public class BankManagementServiceImpl implements BankManagementService {

    @Autowired
    private BankManagementDao bankManagementDao;

    @Override
    public R createAccount(String accountType, String accountPassword, String name, String personalId, String email) {
        if (accountType != null && accountPassword != null && name != null && personalId != null && email != null){
            if (accountType.equals("Individual")){
                IndividualAccount individualAccount = bankManagementDao.save(new IndividualAccount(accountPassword, name, personalId, email));
                return new R(individualAccount);
            }else {
                //其他类型账号
            }
        }
        return new R(new ResultCode("参数不合法,请重新输入"));
    }

    @Override
    public StatusCode deleteAccountById(Integer accountId) {
        bankManagementDao.deleteById(accountId);
        return ResultCodeModel.SUCCESS;
    }

    //id查询
    @Override
    public Optional<Account> checkAccountById(Integer accountId) {
        return bankManagementDao.findById(accountId);
    }

    //查询所有
    @Override
    public List<Account> checkAllAccounts() {
        return bankManagementDao.findAll();
    }

    //分页查询
    @Override
    public Page<Account> checkAllAccountsForPage(Integer pageNo, Integer pageSize) {
        Page<Account> accountPage = bankManagementDao.findAll(PageRequest.of(pageNo,pageSize));
        return accountPage;
    }

    @Override
    public StatusCode updateAccountNameById(Integer accountId, String name) {
        if (bankManagementDao.findById(accountId).isPresent()){
            bankManagementDao.findById(accountId).get().setName(name);
            return ResultCodeModel.SUCCESS;
        }else {
            return ResultCodeModel.FAILED_NULL;
        }
    }

    @Override
    public StatusCode updatePersonalIdById(Integer accountId, String personalId) {
        if(bankManagementDao.findById(accountId).isPresent()){
            bankManagementDao.findById(accountId).get().setPersonalId(personalId);
            return ResultCodeModel.SUCCESS;
        }else {
            return ResultCodeModel.FAILED_NULL;
        }
    }

    @Override
    public StatusCode updateEmailById(Integer accountId, String email) {
        if(bankManagementDao.findById(accountId).isPresent()){
            bankManagementDao.findById(accountId).get().setEmail(email);
            return ResultCodeModel.SUCCESS;
        }else {
            return ResultCodeModel.FAILED_NULL;
        }
    }

    @Override
    public List<BankLog> printLog() {
        return null;

    }

    @Override
    public void createTextTable() {
        Account individualAccount = new IndividualAccount("1","aaaaaa","11111","111@qq.com");
        Account individualAccount2 = new IndividualAccount("1","bbbbbb","22222","1211@qq.com");
        Account individualAccount3 = new IndividualAccount("1","cccccc","3333333","1131@qq.com");
        Account individualAccount4 = new IndividualAccount("1","dddddd","44444444","1411@qq.com");
        bankManagementDao.save(individualAccount);
        bankManagementDao.save(individualAccount2);
        bankManagementDao.save(individualAccount3);
        bankManagementDao.save(individualAccount4);

       // return ResultCodeModel.SUCCESS;
    }
}
