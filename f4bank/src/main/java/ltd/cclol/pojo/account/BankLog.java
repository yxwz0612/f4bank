package ltd.cclol.pojo.account;

import org.springframework.data.annotation.CreatedDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//银行管理日志的pojo
@Entity//映射该类到数据库
public class BankLog {
    @Id//该属性设置为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;
    private String actionType;
    private Integer actionTarget;
    @CreatedDate
    private Date actionDate;
}
