package ltd.cclol.controller;

import ltd.cclol.common.ResultCodeModel;
import ltd.cclol.common.R;
import ltd.cclol.common.StatusCode;
import ltd.cclol.pojo.account.Account;
import ltd.cclol.service.impl.BankManagementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* 1.响应银行管理api的controller,需要admin权限
* 2.返回R类型
*
* */
@RestController
@RequestMapping("/banks")
public class BankController {
    @Autowired
    BankManagementServiceImpl bankManagementServiceImpl;

    /*
    * 创建新的银行账户
    * @Post
    * */
    @PostMapping("/accounts/{accountType}/{accountPassword}/{name}/{personalId}/{email}")
    public R createAccount(@PathVariable String accountType, @PathVariable String accountPassword, @PathVariable String name, @PathVariable String personalId, @PathVariable String email){
        R createResult = bankManagementServiceImpl.createAccount(accountType, accountPassword, name, personalId, email);
        return createResult;
    }

    /*
    * 根据id删除某个账号
    * @Delete
    * */
    @DeleteMapping("/accounts/{id}")
    public R deleteAccountById(@PathVariable Integer id){
        StatusCode code = bankManagementServiceImpl.deleteAccountById(id);
        return new R(code);
    }

    /*
    * 根据id更改账户中的姓名
    * @Put
    * */
    @PutMapping("/accounts/name/{id}/{name}")
    public R updateAccountNameById(@PathVariable Integer id, @PathVariable String name){
        StatusCode code = bankManagementServiceImpl.updateAccountNameById(id,name);
        return new R(code);
    }

    /*
    * 根据id更改账户的email地址
    * @Put
    * */
    @PutMapping("/accounts/email/{id}/{email}")
    public R updateAccountEmailById(@PathVariable Integer id, @PathVariable String email){
        StatusCode code = bankManagementServiceImpl.updateEmailById(id,email);
        return new R(code);
    }

    /*
     * 根据id更改账户的身份证号
     * @Put
     * */
    @PutMapping("/accounts/personalId/{id}/{personalId}")
    public R updatePersonalId(@PathVariable Integer id, @PathVariable String personalId){
        StatusCode code = bankManagementServiceImpl.updatePersonalIdById(id,personalId);
        return new R(code);
    }

    /*
     * 根据id查询某个账户
     * @Get
     * */
    @GetMapping("/accounts/{id}")
    public R checkAccountById(@PathVariable Integer id){
        Optional<Account> optionalAccount = bankManagementServiceImpl.checkAccountById(id);
        if (optionalAccount.isPresent()){
            return new R(optionalAccount);
        }
        return new R(ResultCodeModel.FAILED_NULL);
    }

    /*
     * 查询所有账户
     * @Get
     * */
    @GetMapping("/allaccounts")
    public R checkAllAccounts(){
        List<Account> accounts = bankManagementServiceImpl.checkAllAccounts();
        return new R(accounts);
    }

    /*
     * 分页查询所有账户
     * @Get
     * */
    @GetMapping("/accountsforpage/{pageNo}/{pageSize}")
    public R checkAllAccounts(Integer pageNO,Integer pageSiz){
        List<Account> accounts = bankManagementServiceImpl.checkAllAccounts();
        return new R(accounts);
    }

    /*
     * 测试api:创建几个新账号
     * @Get
     * */
    @GetMapping("/texts")
    public R createTextTable(){
        bankManagementServiceImpl.createTextTable();
        return new R(ResultCodeModel.SUCCESS);
    }
}
