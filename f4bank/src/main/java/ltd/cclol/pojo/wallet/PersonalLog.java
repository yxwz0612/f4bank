package ltd.cclol.pojo.wallet;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class PersonalLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personalLogId;
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String actionType;
    @NotBlank
    private String aboutBalance;
    @CreatedDate
    private Date actionDate;
}
