package ltd.cclol;

import ltd.cclol.dao.BankManagementDao;
import ltd.cclol.pojo.account.Account;
import ltd.cclol.pojo.account.IndividualAccount;
import ltd.cclol.service.BankManagementService;
import ltd.cclol.service.impl.PersonalAccountManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class F4BankApplicationTests {
	@Autowired
	private BankManagementDao bankManagementDao;
	@Autowired
	private PersonalAccountManagementServiceImpl personalAccountManagementService;
	@Autowired
	private BankManagementService bankManagementService;

	@Test
	void contextLoads() {
		Account individualAccount = new IndividualAccount("1","aaaaaa","11111","111@qq.com");
		Account individualAccount2 = new IndividualAccount("1","bbbbbb","22222","1211@qq.com");
		Account individualAccount3 = new IndividualAccount("1","cccccc","3333333","1131@qq.com");
		Account individualAccount4 = new IndividualAccount("1","dddddd","44444444","1411@qq.com");
		bankManagementDao.save(individualAccount);
		bankManagementDao.save(individualAccount2);
		bankManagementDao.save(individualAccount3);
		bankManagementDao.save(individualAccount4);

	}

}
