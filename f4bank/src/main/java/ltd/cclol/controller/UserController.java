package ltd.cclol.controller;

import ltd.cclol.common.R;
import ltd.cclol.common.ResultCodeModel;
import ltd.cclol.service.PersonalAccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/*
* 1.响应用户请求api的controller,需要user权限
* 2.返回R
* 3.因为所有银行账户都在一张表内,所有钱包也在一张表内,所以这一层要统一检查api要操作账户和操作的钱包是否属于该账号
* */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    PersonalAccountManagementService personalAccountManagementService;

    /*
     *测试api
     * */
    @RequestMapping("/hi")
    public R sayHi(){
        return new R("hi!");
    }

    /*
    * 新建一个钱包
    * @Post
    * */
    @PostMapping("/wallets/{walletType}")
    public R createWallet(String walletType,Integer fromAccountId){
        return new R(personalAccountManagementService.createWallet(walletType,fromAccountId));
    }

    /*
     * 删除一个钱包
     * @Delete
     * */
    @DeleteMapping("/wallets/{walletId}")
    public R deleteWallet(Integer walletId){
        return new R(personalAccountManagementService.deleteWalletById(walletId));
    }

    /*
     * 获得本账户所有钱包信息
     * @Get
     * */
    @GetMapping("/wallets")
    public R CheckAllWallet(Integer fromAccountId){
        return personalAccountManagementService.checkAllWallets(fromAccountId);
    }

    /*
     * 得到本账户某一个钱包信息
     * @Get
     * */
    @GetMapping("/wallets/{walletId}")
    public R CheckWalletById(Integer walletId){
        return  personalAccountManagementService.checkWalletById(walletId);
    }

    /*
     * 查询本账户某余额
     * @Get
     * */
    @GetMapping("/wallets/balance/{walletId}")
    public R CheckWalletBalanceById(Integer walletId){
        return  personalAccountManagementService.checkWalletBalanceById(walletId);
    }


    /*
     * 存入某钱包xx钱
     * @Put
     * */
    @PutMapping("/deposit/{walletId}/{money}")
    public R depositById(Integer walletId,BigDecimal money){
        return new R(personalAccountManagementService.depositById(walletId,money));
    }

    /*
     * 取出某钱包xx钱
     * @Put
     * */
    @PutMapping("/withdraw/{walletId}/{money}")
    public R withdrawById(Integer walletId,BigDecimal money){
        return new R(personalAccountManagementService.withdrawById(walletId,money));
    }

    /*
     * 从某钱包给某钱包转账
     * @Put
     * */
    @PutMapping("/transferTo/{walletId}/{toWalletId}/{money}")
    public R transferTo(Integer walletId, Integer toWalletId, BigDecimal money){
        return  new R(personalAccountManagementService.transferTo(walletId,toWalletId,money));
    }

    /*
     * 修改某一个钱包每日取款限额
     * @Put
     * */
    @PutMapping("/limit/{walletId}/{newLimit}")
    public R limit(Integer walletId, BigDecimal newLimit){
        return new R(personalAccountManagementService.setWalletLimitById(walletId,newLimit));
    }

    /*
     * 修改账户密码
     * @Put
     * */
    @PutMapping("/accountPassword/{newPassword}")
    public R accountPassword(Integer AccountId, String newPassword){
        return new R(personalAccountManagementService.setPasswordById(AccountId,newPassword));
    }

    /*
     * 测试api,创建几个属于ID是1号账户的钱包
     * @Get
     * */
    @GetMapping("/texts")
    public R createTextWallet(){
        personalAccountManagementService.createTextWallet();
        return new R(ResultCodeModel.SUCCESS);
    }
}
