package acropollis.municipali.omega.admin.data.dto.customer;

import lombok.Data;

@Data
public class CustomerToken {
    private String token;
    private long validTill;
}
