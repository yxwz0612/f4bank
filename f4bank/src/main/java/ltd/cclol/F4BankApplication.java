package ltd.cclol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing//使pojo变量的@CreatedDate生效
@SpringBootApplication
public class F4BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(F4BankApplication.class, args);
	}

}
