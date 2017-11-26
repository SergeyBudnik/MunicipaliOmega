package acropollis.municipali.omega.health_check.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReloadHealth extends CommonHealth {
    private int amount;
}
