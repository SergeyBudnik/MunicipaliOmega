package acropollis.municipali.omega.admin.data.request.statistics;

import lombok.Data;

@Data
public class GetFullStatisticsRequest {
    private long from, to;
}
