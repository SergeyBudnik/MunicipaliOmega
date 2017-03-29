package acropollis.municipali.omega.user.data.dto.report;

import lombok.Data;

@Data
public class Report {
    private Long id;
    private String userId;
    private double latitude, longitude;
    private String comment;
}
