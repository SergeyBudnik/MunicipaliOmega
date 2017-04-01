package acropollis.municipali.omega.common.dto.report;

import lombok.Data;

@Data
public class Report {
    private Long id;
    private String userId;
    private double latitude, longitude;
    private String comment;
}
